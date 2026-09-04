package kranji.reading.app.phonic;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.phonic.PhonicPartitions;
import kranji.phonic.SourceFindings;
import kranji.phonic.SourceReadings;
import kranji.pinyin.Initial;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Serves one phonic source partition as an ES module, addressed by initial.
 *
 * <p>Same bargain as {@code ZiDataGetAction}: data as code, reached with a
 * dynamic {@code import('/phonic-source?initial=h')}, deliberately outside the
 * crate and outside conformance because the payload is computed rather than
 * authored and is almost entirely CJK. The exemption is paid for the same way —
 * <b>data literals only, never behaviour</b>, asserted by
 * {@code PhonicSourceGetActionTest}.</p>
 *
 * <p>Partitioning is what makes this viable. The source is 8,100 characters;
 * an initial is a few hundred rows, which is a grid a person can actually look
 * at and a payload that arrives without ceremony.</p>
 */
public final class PhonicSourceGetAction
        implements GetAction<RoutingContext, PhonicSourceGetAction.Query,
                             EmptyParam.NoHeaders, DocContent> {

    /** Route this action is mounted on. */
    public static final String PATH = "/phonic-source";

    private static final String JS = "text/javascript; charset=utf-8";

    /** @param initial partition segment, e.g. {@code h} or {@code zero} */
    public record Query(String initial) implements Param._QueryString {}

    @Override
    public ParamMarshaller._QueryString<RoutingContext, Query> queryStrMarshaller() {
        return ctx -> new Query(ctx.request().getParam("initial"));
    }

    @Override
    public ParamMarshaller._Header<RoutingContext, EmptyParam.NoHeaders> headerMarshaller() {
        return ctx -> new EmptyParam.NoHeaders();
    }

    @Override
    public CompletableFuture<DocContent> execute(Query query, EmptyParam.NoHeaders headers) {
        return CompletableFuture.completedFuture(
                new DocContent(moduleFor(query.initial()), JS));
    }

    /** Visible for testing — the module text for one partition segment. */
    public static String moduleFor(String segment) {
        if (segment == null || segment.isBlank()) {
            return errorModule("no initial named");
        }
        Initial initial = null;
        for (Initial candidate : Initial.values()) {
            if (PhonicPartitions.segment(candidate).equals(segment)) {
                initial = candidate;
                break;
            }
        }
        if (initial == null) return errorModule("no initial named '" + segment + "'");

        List<SourceReadings> rows = PhonicPartitions.load(initial);
        Map<String, String> review = reviewBy(rows);

        var js = new StringBuilder();
        js.append("// Generated from the Kranji phonic source partitions. Data only - no behaviour.\n");
        js.append("export const initial = ").append(quote(segment)).append(";\n");
        js.append("export const label = ").append(quote(labelFor(initial))).append(";\n");
        // The five columns between principal and alternates decompose the
        // principal reading: 声母 韵头 韵腹 韵尾 声调. Nothing is computed to
        // produce them - PinyinSyllable is already (Initial, Final, Tone) and
        // Final is already (Head, Body, Tail), so this reads the structure the
        // parse produced rather than taking the syllable apart again.
        js.append("export const columns = [\"glyph\", \"codePoint\", \"principal\", ")
          .append("\"initial\", \"medial\", \"nucleus\", \"coda\", \"tone\", ")
          .append("\"alternates\", \"readings\", \"mandarin\", \"evidence\", \"review\"];\n");
        js.append("export const rows = [\n");
        for (int i = 0; i < rows.size(); i++) {
            SourceReadings r = rows.get(i);
            js.append("  { pk: ").append(quote(r.zi().codePointLabel()))
              .append(", glyph: ").append(quote(r.zi().value()))
              .append(", codePoint: ").append(quote(r.zi().codePointLabel()))
              .append(", principal: ").append(quote(r.principal().numbered()))
              .append(", initial: ").append(quote(initialOf(r.principal())))
              .append(", medial: ").append(quote(symbolOr(r.principal().fin().head().symbol())))
              .append(", nucleus: ").append(quote(symbolOr(r.principal().fin().body().symbol())))
              .append(", coda: ").append(quote(symbolOr(r.principal().fin().tail().symbol())))
              .append(", tone: ").append(r.principal().tone().number())
              .append(", alternates: ").append(quote(String.join(" ", r.alternates().stream()
                      .map(s -> s.numbered()).toList())))
              .append(", readings: ").append(r.readingCount())
              .append(", mandarin: ").append(quote(r.mandarin()))
              .append(", evidence: ").append(quote(evidenceOf(r)))
              .append(", review: ").append(quote(review.getOrDefault(r.zi().codePointLabel(), "")))
              .append(" }").append(i + 1 < rows.size() ? "," : "").append("\n");
        }
        js.append("];\n");
        return js.toString();
    }

    /** One line of review text per character that has any finding. */
    private static Map<String, String> reviewBy(List<SourceReadings> rows) {
        Map<String, String> out = new LinkedHashMap<>();
        for (SourceFindings.Finding f : SourceFindings.check(rows)) {
            String key = f.row().zi().codePointLabel();
            String line = f.kind() + ": " + f.detail();
            out.merge(key, line, (a, b) -> a + " | " + b);
        }
        return out;
    }

    /** The frequency evidence, most-observed first, or empty when there is none. */
    private static String evidenceOf(SourceReadings r) {
        if (r.frequency().isEmpty()) return "";
        var sb = new StringBuilder();
        for (String reading : r.frequency().byDescendingCount()) {
            if (sb.length() > 0) sb.append("  ");
            sb.append(reading).append(' ').append(r.frequency().countOf(reading));
        }
        return sb.toString();
    }

    /**
     * The initial as a segment. The zero initial has no letters, so it takes
     * the name the corpus already uses for its partition rather than showing
     * an empty cell that reads as missing data.
     */
    private static String initialOf(kranji.pinyin.PinyinSyllable syllable) {
        return PhonicPartitions.segment(syllable.initial());
    }

    /**
     * A part of a final, or a dash when it has none.
     *
     * <p>An open mouth (开口呼) has no medial, an open syllable has no coda,
     * and a syllabic fricative has no true nucleus. All three spell empty,
     * which in a grid cell is indistinguishable from data that failed to
     * arrive - so absence is shown rather than left blank.</p>
     */
    private static String symbolOr(String symbol) {
        return symbol.isEmpty() ? "-" : symbol;
    }

    private static String labelFor(Initial initial) {
        return initial == Initial.ZERO ? "no initial" : initial.pinyin() + "-";
    }

    private static String errorModule(String problem) {
        return "// No partition could be served.\n"
             + "export const initial = \"\";\n"
             + "export const label = \"\";\n"
             + "export const columns = [];\n"
             + "export const rows = [];\n"
             + "export const problem = " + quote(problem) + ";\n";
    }

    /** JSON-style quoting; the payload is CJK, so only the structural characters escape. */
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
