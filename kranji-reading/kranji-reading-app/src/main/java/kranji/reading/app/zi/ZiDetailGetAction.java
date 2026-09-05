package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.phonic.SourceReadings;
import kranji.phonic.SyllableIndex;
import kranji.pinyin.PinyinSyllable;
import kranji.reading.app.gloss.ZiGlossary;
import kranji.simple.PhonicProjection;
import kranji.zi.ZiCharUTF8;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Serves everything known about one character as an ES module.
 *
 * <p>Addressed by codepoint — {@code /zi-detail?codepoint=U+597D} — because
 * that is the character's identity. A reading is corrigible data and would be
 * the wrong key; per CD-001 the codepoint is the hub everything joins on.</p>
 *
 * <p>Same bargain as the other data actions: outside the crate, outside
 * conformance, <b>data literals only</b>, asserted by
 * {@code ZiDetailGetActionTest}.</p>
 */
public final class ZiDetailGetAction
        implements GetAction<RoutingContext, ZiDetailGetAction.Query,
                             EmptyParam.NoHeaders, DocContent> {

    /** Route this action is mounted on. */
    public static final String PATH = "/zi-detail";

    private static final String JS = "text/javascript; charset=utf-8";

    /** @param codepoint the character's identity, e.g. {@code U+597D} */
    public record Query(String codepoint) implements Param._QueryString {}

    @Override
    public ParamMarshaller._QueryString<RoutingContext, Query> queryStrMarshaller() {
        return ctx -> new Query(ctx.request().getParam("codepoint"));
    }

    @Override
    public ParamMarshaller._Header<RoutingContext, EmptyParam.NoHeaders> headerMarshaller() {
        return ctx -> new EmptyParam.NoHeaders();
    }

    @Override
    public CompletableFuture<DocContent> execute(Query query, EmptyParam.NoHeaders headers) {
        return CompletableFuture.completedFuture(
                new DocContent(moduleFor(query.codepoint()), JS));
    }

    /** Visible for testing — the module text for one codepoint. */
    public static String moduleFor(String codepoint) {
        Optional<ZiCharUTF8> parsed = parse(codepoint);
        if (parsed.isEmpty()) {
            return errorModule("not a Chinese character: '" + codepoint + "'");
        }
        ZiCharUTF8 zi = parsed.get();
        Optional<SourceReadings> found = SyllableIndex.instance().readingsOf(zi);
        if (found.isEmpty()) {
            return errorModule("no character " + zi.codePointLabel() + " in the corpus");
        }
        SourceReadings row = found.get();

        var js = new StringBuilder();
        js.append("// Generated from the Kranji corpus. Data only - no behaviour.\n");
        js.append("export const glyph = ").append(quote(zi.value())).append(";\n");
        js.append("export const codePoint = ").append(quote(zi.codePointLabel())).append(";\n");
        js.append("export const supplementary = ").append(zi.isSupplementary()).append(";\n");
        js.append("export const principal = ")
          .append(quote(row.principal().numbered())).append(";\n");
        js.append("export const polyphonic = ").append(row.isPolyphonic()).append(";\n");

        // Every reading, decomposed. The parse already produced these parts, so
        // this reports structure rather than taking the syllable apart again.
        //
        // And what each one MEANS, from whichever collections are on the
        // classpath. Meaning belongs on the reading rather than on the
        // character: the whole reason a pane lists readings side by side is to
        // make "this one, not that one" a choice somebody can make, and until
        // now that choice was between two sounds with nothing to tell them
        // apart. A reading nothing explains carries an empty string, which is
        // the same shape as one that is explained and simply says less.
        js.append("export const readings = [\n");
        List<PinyinSyllable> all = row.all();
        for (int i = 0; i < all.size(); i++) {
            PinyinSyllable s = all.get(i);
            List<String> meanings = ZiGlossary.meaningsOf(zi.codePoint(), s.numbered());
            js.append("  { reading: ").append(quote(s.numbered()))
              .append(", address: ").append(quote(addressOf(s)))
              .append(", principal: ").append(i == 0)
              .append(", initial: ").append(quote(PhonicProjection.onsetSegment(s)))
              .append(", medial: ").append(quote(dash(s.fin().head().symbol())))
              .append(", nucleus: ").append(quote(dash(s.fin().body().symbol())))
              .append(", coda: ").append(quote(dash(s.fin().tail().symbol())))
              .append(", tone: ").append(s.tone().number())
              .append(", observed: ").append(row.frequency().countOf(s.toDiacritic()))
              .append(", homophones: ")
              .append(SyllableIndex.instance().charactersOf(s).size() - 1)
              .append(", meaning: ")
              .append(quote(meanings.isEmpty() ? "" : meanings.get(0)))
              .append(", meanings: ").append(list(meanings))
              // The grain the grid is keyed on: a row per sense, with the
              // phrases that show THAT sense rather than the reading's pooled
              // list. meanings stays beside it - a caller wanting the short
              // answer should not have to walk a structure to get it.
              .append(", senses: ").append(senses(zi.codePoint(), s.numbered()))
              .append(" }").append(i + 1 < all.size() ? "," : "").append("\n");
        }
        js.append("];\n");

        js.append("export const mandarin = ").append(quote(row.mandarin())).append(";\n");
        js.append("export const evidence = ").append(quote(evidenceOf(row))).append(";\n");
        js.append("export const unmodelled = ")
          .append(quote(String.join(", ", row.unparseable()))).append(";\n");
        return js.toString();
    }

    /** The tree address a reading occupies, so the view can point at it. */
    private static String addressOf(PinyinSyllable s) {
        return PhonicProjection.onsetSegment(s) + "."
             + PhonicProjection.segmentFor(s.fin()) + "."
             + s.tone().number();
    }

    private static String evidenceOf(SourceReadings row) {
        if (row.frequency().isEmpty()) return "";
        var sb = new StringBuilder();
        for (String reading : row.frequency().byDescendingCount()) {
            if (sb.length() > 0) sb.append("  ");
            sb.append(reading).append(' ').append(row.frequency().countOf(reading));
        }
        return sb.toString();
    }

    /** A JS array literal of strings, on one line — data, like everything here. */
    private static String list(List<String> values) {
        var sb = new StringBuilder("[");
        for (int i = 0; i < values.size(); i++) {
            sb.append(i > 0 ? ", " : "").append(quote(values.get(i)));
        }
        return sb.append(']').toString();
    }

    /**
     * A reading's senses, each with the phrases that show it.
     *
     * <p>Empty for a reading nothing has glossed, which is a state and not a
     * gap: the grid still needs its row, because a reading with no meaning is
     * still a reading somebody can claim.</p>
     */
    private static String senses(int codePoint, String reading) {
        List<ZiGlossary.SenseOf> senses = ZiGlossary.sensesOf(codePoint, reading);
        var sb = new StringBuilder("[");
        for (int i = 0; i < senses.size(); i++) {
            ZiGlossary.SenseOf sense = senses.get(i);
            sb.append(i > 0 ? ", " : "")
              .append("{ meaning: ").append(quote(sense.meaning()))
              .append(", examples: ").append(list(sense.examples()))
              .append(" }");
        }
        return sb.append(']').toString();
    }

    /** An absent part reads as absent; a blank cell reads as missing data. */
    private static String dash(String symbol) {
        return symbol.isEmpty() ? "-" : symbol;
    }

    /**
     * Accepts {@code U+597D}, {@code 597D}, or the character itself, because a
     * caller that has the glyph should not have to convert it first.
     *
     * <p>Empty for anything that is not a Han character. {@link ZiCharUTF8}
     * refuses to hold one and says so by throwing, which is right for a
     * constructor and wrong for a query string — a URL is arriving from
     * outside, so a bad one is an answer to give rather than a fault to
     * raise.</p>
     */
    private static Optional<ZiCharUTF8> parse(String raw) {
        if (raw == null || raw.isBlank()) return Optional.empty();
        String s = raw.trim();
        String hex = s.regionMatches(true, 0, "U+", 0, 2) ? s.substring(2) : s;
        try {
            int cp = Integer.parseInt(hex, 16);
            if (Character.isValidCodePoint(cp)) return Optional.of(new ZiCharUTF8(cp));
        } catch (NumberFormatException notHex) {
            // fall through and try it as the character itself
        } catch (IllegalArgumentException notHan) {
            return Optional.empty();
        }
        if (s.codePointCount(0, s.length()) == 1) {
            try {
                return Optional.of(new ZiCharUTF8(s.codePointAt(0)));
            } catch (IllegalArgumentException notHan) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private static String errorModule(String problem) {
        return "// No character could be served.\n"
             + "export const glyph = \"\";\n"
             + "export const codePoint = \"\";\n"
             + "export const supplementary = false;\n"
             + "export const principal = \"\";\n"
             + "export const polyphonic = false;\n"
             + "export const readings = [];\n"
             + "export const mandarin = \"\";\n"
             + "export const evidence = \"\";\n"
             + "export const unmodelled = \"\";\n"
             + "export const problem = " + quote(problem) + ";\n";
    }

    private static String quote(String raw) {
        var sb = new StringBuilder("\"");
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            switch (c) {
                case '"'  -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default   -> sb.append(c);
            }
        }
        return sb.append('"').toString();
    }
}
