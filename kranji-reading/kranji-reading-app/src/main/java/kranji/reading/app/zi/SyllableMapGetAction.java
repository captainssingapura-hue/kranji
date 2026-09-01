package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.phonic.PhonicPartitions;
import kranji.phonic.SourceReadings;
import kranji.pinyin.PinyinSyllable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Serves the corpus as codepoint → readings, in hash partitions.
 *
 * <p>A front end that has a character and wants its readings should not need a
 * round trip. This ships the map instead, split so no single module is large.</p>
 *
 * <h2>Why hash rather than range</h2>
 *
 * <p>The corpus is not spread evenly over the codepoint space — 7,827 of 8,100
 * characters sit in {@code U+4E00..9FFF} and the rest are scattered from
 * {@code U+3447} to {@code U+2CE93}. Uniform ranges therefore give a median
 * partition of a dozen characters and a maximum of several hundred.</p>
 *
 * <p>{@code codePoint % PARTITIONS} divides evenly regardless, and needs no
 * boundary index: the client computes the partition from the character it
 * already holds. It assumes nothing about how characters are accessed, which
 * is the point — the access pattern is not yet known.</p>
 *
 * <h2>Readings are written out, not interned</h2>
 *
 * <p>An earlier shape interned every reading into a table and stored ids. That
 * pays at corpus scale — 8,759 appearances over 1,284 distinct readings, 6.8×
 * — but hashing is precisely what destroys the locality it needs. Scattering
 * characters uniformly puts the ones that share a reading in different
 * partitions, so <em>within</em> a partition the ratio measures 1.1: almost
 * every character has a reading no sibling repeats.</p>
 *
 * <p>At 1.1× an id plus its share of a table costs more than the string it
 * stands for, so the table is gone. A module is now about 1.5 KB rather than
 * 10.5 KB, needs no second module to resolve against, and hands the client
 * {@code characters[cp]} as the readings themselves.</p>
 *
 * <h2>Readings only</h2>
 *
 * <p>A syllable's decomposition — 声母 韵头 韵腹 韵尾 声调 — is a property of the
 * <em>syllable</em>, so it is looked up with one: {@code /zi-detail} and
 * {@code /phonic-source} serve it, scoped to what is on screen.</p>
 *
 * <p>Data literals only, like the other data actions.</p>
 */
public final class SyllableMapGetAction
        implements GetAction<RoutingContext, SyllableMapGetAction.Query,
                             EmptyParam.NoHeaders, DocContent> {

    /** Route this action is mounted on. */
    public static final String PATH = "/syllable-map";

    /**
     * How many partitions the map is split into.
     *
     * <p><b>Prime.</b> Codepoints are not random - Han characters run in dense
     * contiguous blocks, and radical-ordered ranges land on regular strides. A
     * power-of-two modulus keeps the low bits and inherits that structure; a
     * prime one does not, so the split stays even for reasons that do not
     * depend on how Unicode happens to be laid out.</p>
     *
     * <p>Exported in every module so a client can check the constant it built
     * against — a mismatch means it would compute the wrong partition for
     * every character, which is worth failing loudly on.</p>
     */
    public static final int PARTITIONS = 101;

    private static final String JS = "text/javascript; charset=utf-8";

    /** @param p partition index, {@code 0 <= p < PARTITIONS} */
    public record Query(String p) implements Param._QueryString {}

    @Override
    public ParamMarshaller._QueryString<RoutingContext, Query> queryStrMarshaller() {
        return ctx -> new Query(ctx.request().getParam("p"));
    }

    @Override
    public ParamMarshaller._Header<RoutingContext, EmptyParam.NoHeaders> headerMarshaller() {
        return ctx -> new EmptyParam.NoHeaders();
    }

    @Override
    public CompletableFuture<DocContent> execute(Query query, EmptyParam.NoHeaders headers) {
        return CompletableFuture.completedFuture(
                new DocContent(moduleFor(query.p()), JS));
    }

    /** Visible for testing — the module text for one partition. */
    public static String moduleFor(String rawPartition) {
        int partition;
        try {
            partition = Integer.parseInt(rawPartition == null ? "" : rawPartition.trim());
        } catch (NumberFormatException e) {
            return errorModule("not a partition index: '" + rawPartition + "'");
        }
        if (partition < 0 || partition >= PARTITIONS) {
            return errorModule("partition " + partition + " is outside 0.." + (PARTITIONS - 1));
        }

        var js = new StringBuilder();
        js.append("// Generated from the Kranji corpus. Data only - no behaviour.\n");
        js.append("export const partition = ").append(partition).append(";\n");
        js.append("export const partitions = ").append(PARTITIONS).append(";\n");

        // This partition's characters, principal reading first.
        js.append("export const characters = {\n");
        boolean first = true;
        int count = 0;
        for (SourceReadings row : PhonicPartitions.loadAll()) {
            int cp = row.zi().codePoint();
            if (Math.floorMod(cp, PARTITIONS) != partition) continue;
            if (!first) js.append(",\n");
            first = false;
            count++;
            js.append("  ").append(cp).append(":[");
            List<PinyinSyllable> rs = row.all();
            for (int k = 0; k < rs.size(); k++) {
                js.append(k > 0 ? "," : "").append(quote(rs.get(k).toDiacritic()));
            }
            js.append(']');
        }
        js.append("\n};\n");
        js.append("export const count = ").append(count).append(";\n");
        return js.toString();
    }

    private static String errorModule(String problem) {
        return "// No partition could be served.\n"
             + "export const partition = -1;\n"
             + "export const partitions = " + PARTITIONS + ";\n"
             + "export const characters = {};\n"
             + "export const count = 0;\n"
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
