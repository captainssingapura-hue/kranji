package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.pinyin.PinyinSyllable;
import kranji.phonic.SourceReadings;
import kranji.phonic.SyllableIndex;
import kranji.simple.PhonicProjection;
import kranji.zi.ZiCharUTF8;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Serves one syllable's characters as an ES module, addressed by module name.
 *
 * <p>Data as code. The browser reaches a syllable with a dynamic
 * {@code import('/zi-data?syllable=h.ao.3')} and receives a module of
 * {@code export const} bindings, rather than fetching JSON and parsing it. The
 * module is the data structure.</p>
 *
 * <h2>Why this is outside the crate</h2>
 *
 * <p>These modules are deliberately <b>not</b> {@code EsModule}s, so they hold
 * no crate entry and pass through no conformance rule set. That is a real
 * departure from RFC 0044's rule that a served module must be crated, and it is
 * taken on purpose: the rules exist to stop <em>authored logic</em> drifting
 * from the registry, and this content is computed from the registry on every
 * request, so it cannot drift. Applying a rule set written for logic — one
 * forbidding inline CJK, in particular — to a module whose entire payload is
 * CJK would be applying the letter against the intent.</p>
 *
 * <p>The exemption is paid for by a constraint instead: this action emits
 * <b>data literals only, never behaviour</b>. {@code ZiDataGetActionTest}
 * asserts the emitted module contains no function, arrow, or statement
 * construct. If that guarantee is ever weakened, this content needs to come
 * back inside the gate.</p>
 */
public final class ZiDataGetAction
        implements GetAction<RoutingContext, ZiDataGetAction.Query, EmptyParam.NoHeaders, DocContent> {

    /** Route this action is mounted on. */
    public static final String PATH = "/zi-data";

    /** Media type of an ES module. */
    private static final String JS = "text/javascript; charset=utf-8";

    /**
     * @param syllable dotted module name, e.g. {@code h.ao.3} — the tree path
     *                 with its separators changed, so it reads as a name rather
     *                 than a route
     */
    public record Query(String syllable) implements Param._QueryString {}

    @Override
    public ParamMarshaller._QueryString<RoutingContext, Query> queryStrMarshaller() {
        return ctx -> new Query(ctx.request().getParam("syllable"));
    }

    @Override
    public ParamMarshaller._Header<RoutingContext, EmptyParam.NoHeaders> headerMarshaller() {
        return ctx -> new EmptyParam.NoHeaders();
    }

    @Override
    public CompletableFuture<DocContent> execute(Query query, EmptyParam.NoHeaders headers) {
        String name = query.syllable();
        if (name == null || name.isBlank()) {
            return CompletableFuture.completedFuture(
                    new DocContent(errorModule("no syllable named"), JS));
        }
        return CompletableFuture.completedFuture(new DocContent(moduleFor(name), JS));
    }

    /** The module source for {@code name}, or an empty module when unknown. */
    public static String moduleFor(String name) {
        String[] parts = name.split("\\.");
        if (parts.length != 3) return errorModule("expected initial.final.tone, got '" + name + "'");

        String initialSeg = parts[0];
        String finalSeg   = parts[1];
        int tone;
        try {
            tone = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            return errorModule("tone is not a number in '" + name + "'");
        }

        // The syllable is resolved once and looked up, rather than the corpus
        // being walked to find it. SyllableIndex is keyed by PinyinSyllable,
        // which is a record of (Initial, Final, Tone) with structural equality
        // - so the address IS the key, and no scan is needed.
        PinyinSyllable target = resolve(initialSeg, finalSeg, tone);
        if (target == null) return errorModule("no syllable spelled '" + name + "'");

        SyllableIndex index = SyllableIndex.instance();
        List<ZiCharUTF8> characters = index.charactersOf(target);
        if (characters.isEmpty()) return errorModule("no characters read '" + name + "'");

        String syllable = target.numbered();
        var js = new StringBuilder();
        js.append("// Generated from the Kranji corpus. Data only - no behaviour.\n");
        js.append("export const name = ").append(quote(name)).append(";\n");
        js.append("export const syllable = ").append(quote(syllable)).append(";\n");
        js.append("export const initial = ").append(quote(initialSeg)).append(";\n");
        js.append("export const finalPart = ").append(quote(finalSeg)).append(";\n");
        js.append("export const tone = ").append(tone).append(";\n");
        js.append("export const characters = [\n");
        for (int i = 0; i < characters.size(); i++) {
            ZiCharUTF8 zi = characters.get(i);
            List<PinyinSyllable> all = index.readingsOf(zi)
                    .map(SourceReadings::all).orElse(List.of(target));

            js.append("  { glyph: ").append(quote(zi.value()))
              .append(", codePoint: ").append(quote(zi.codePointLabel()))
              .append(", readings: [");
            for (int r = 0; r < all.size(); r++) {
                if (r > 0) js.append(", ");
                js.append(quote(all.get(r).numbered()));
            }
            js.append("], readHereByDefault: ").append(index.isPrincipalAt(zi, target))
              .append(", polyphonic: ").append(all.size() > 1)
              .append(" }").append(i + 1 < characters.size() ? "," : "").append("\n");
        }
        js.append("];\n");
        return js.toString();
    }

    /**
     * The syllable an address names, or {@code null} when no syllable spells
     * that way.
     *
     * <p>Built by search over the index's own keys rather than by parsing the
     * segments back: the segments are lossy on purpose — {@code ü} folds to
     * {@code v}, an empty final writes {@code i} — so the address is matched
     * against syllables that exist rather than reconstructed into one.</p>
     */
    private static PinyinSyllable resolve(String initialSeg, String finalSeg, int tone) {
        for (PinyinSyllable s : SyllableIndex.instance().syllables()) {
            // The onset, not the initial: y- and w- are branches of their own in
            // the tree, so an address names one of those rather than 'zero'.
            if (s.tone().number() == tone
                    && PhonicProjection.onsetSegment(s).equals(initialSeg)
                    && PhonicProjection.segmentFor(s.fin()).equals(finalSeg)) {
                return s;
            }
        }
        return null;
    }

    /**
     * A well-formed but empty module. An import that fails is harder to handle
     * in the browser than one that resolves to nothing, so an unknown syllable
     * still yields a module — carrying the reason.
     */
    private static String errorModule(String reason) {
        return "// Generated from the Kranji corpus. Data only - no behaviour.\n"
             + "export const name = \"\";\n"
             + "export const syllable = \"\";\n"
             + "export const initial = \"\";\n"
             + "export const finalPart = \"\";\n"
             + "export const tone = 0;\n"
             + "export const characters = [];\n"
             + "export const problem = " + quote(reason) + ";\n";
    }

    /** A JS string literal. CJK passes through; the response is UTF-8. */
    private static String quote(String s) {
        var out = new StringBuilder(s.length() + 2).append('"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"'  -> out.append("\\\"");
                case '\\' -> out.append("\\\\");
                case '\n' -> out.append("\\n");
                case '\r' -> out.append("\\r");
                case '\t' -> out.append("\\t");
                default   -> {
                    if (c < 0x20) out.append(String.format("\\u%04x", (int) c));
                    else out.append(c);
                }
            }
        }
        return out.append('"').toString();
    }
}
