package kranji.studio.gloss;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.simple.gloss.ExampleEntry;
import kranji.simple.gloss.ZiGloss;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * One relation of the gloss model, as a data-only JS module.
 *
 * <p>Six relations, served from the same route by name — the flattening the
 * grid needs already exists in {@link GlossRelations}, so this only decides
 * which one and writes it out. Data only: no behaviour crosses the wire, which
 * is what lets the module be cached and read by a person.</p>
 *
 * <p>Whole, not partitioned. The phonic source browser pages its 8,105 rows
 * because it has to; the gloss data is small enough to send at once, and a
 * relational tool where sorting silently only sorts the page you are on is
 * worse than no tool. When it outgrows that, the partitioning idiom next door
 * is the one to copy.</p>
 */
public final class GlossRelationGetAction
        implements GetAction<RoutingContext, GlossRelationGetAction.Query,
                             EmptyParam.NoHeaders, DocContent> {

    public static final String PATH = "/gloss-relation";

    private static final String JS = "text/javascript; charset=utf-8";

    /**
     * {@code parent} repeats, one per key.
     *
     * <p>It used to be one comma-separated value, which was wrong: a sense key
     * ends in its meaning, and a meaning is free English — "(makes a number an
     * order: first, second)" split into two keys that matched nothing, so the
     * citations for 第 came back empty and nothing said why. No printable
     * separator is safe against text a person writes, so there is no separator.</p>
     */
    public record Query(String name, List<String> parents, String from)
            implements Param._QueryString {}

    @Override
    public ParamMarshaller._QueryString<RoutingContext, Query> queryStrMarshaller() {
        return ctx -> new Query(ctx.request().getParam("name"),
                                ctx.queryParam("parent"),
                                ctx.request().getParam("from"));
    }

    @Override
    public ParamMarshaller._Header<RoutingContext, EmptyParam.NoHeaders> headerMarshaller() {
        return ctx -> new EmptyParam.NoHeaders();
    }

    @Override
    public CompletableFuture<DocContent> execute(Query query, EmptyParam.NoHeaders headers) {
        return CompletableFuture.completedFuture(new DocContent(moduleFor(query.name(), query.parents(), query.from()), JS));
    }

    /** The relation names this route answers to. */
    public static List<String> names() {
        return GlossRelations.relations();
    }

    /**
     * The module text for a relation, optionally narrowed to a parent selection.
     *
     * <p>{@code parents} is one key per repeated {@code parent} parameter. An
     * empty list means no {@code parent} was given at all — "everything",
     * which is what a root relation wants. A list holding only empty strings
     * is a bare {@code parent=}: "nothing is selected upstream", which yields
     * no rows. The two must not collapse — a downstream grid showing the whole
     * relation because its parent selection was cleared is a grid lying about
     * what it is scoped to.</p>
     */
    public static String moduleFor(String name, List<String> parents) {
        return moduleFor(name, parents, null);
    }

    /**
     * As above, but {@code from} names a relation whose <b>refs</b> the keys
     * are, rather than the relation directly above.
     *
     * <p>Two ways to arrive, because there are two kinds of edge. Scoped by
     * parent is containment — the senses <i>of</i> this sound. Scoped by ref is
     * a reference — the phrase sense this citation <i>points at</i>. Collapsing
     * them would make the phrase-sense relation claim a citation owns it, which
     * is exactly backwards: several citations can name the same phrase.</p>
     */
    public static String moduleFor(String name, List<String> parents, String from) {
        List<ZiGloss> glosses = GlossWorkbench.glosses().all();
        List<ExampleEntry> phrases = GlossWorkbench.examples().all();

        List<String> columns = GlossRelations.columnsOf(name == null ? "" : name);
        if (columns.isEmpty()) return problem(name);

        List<GlossRelations.Row> rows = GlossRelations.rowsOf(name, glosses, phrases);
        if (parents != null && !parents.isEmpty()) {
            // A bare `parent=` is one empty value: the caller is saying
            // "nothing is selected", which must show nothing. No `parent` at
            // all is a different statement and leaves the relation whole.
            List<String> keys = GlossRelations.scopeKeysFor(name,
                    parents.stream().filter(k -> !k.isEmpty()).toList());
            rows = (from == null || from.isEmpty())
                    ? GlossRelations.under(rows, keys)
                    : GlossRelations.withPks(rows,
                            GlossRelations.refsFrom(from, keys, glosses, phrases));
        }
        return module(name, columns, rows);
    }

    /** Whole-relation form, for a root or a picker with no upstream. */
    public static String moduleFor(String name) {
        return moduleFor(name, List.of(), null);
    }

    /**
     * Writes one relation.
     *
     * <p>Each row carries {@code pk} — its own composite identity, never a
     * position — and {@code up}, its parent's pk in the relation above. The
     * second is what a downstream grid filters on, and why the cascade needs no
     * knowledge of what happened upstream beyond a list of keys.</p>
     */
    private static String module(String name, List<String> columns,
                                 List<GlossRelations.Row> rows) {
        var js = new StringBuilder();
        js.append("// Generated from the Kranji gloss tier. Data only - no behaviour.\n");
        js.append("export const relation = ").append(quote(name)).append(";\n");
        String up = GlossRelations.upstreamOf(name);
        String refSource = GlossRelations.refSourceOf(name);
        js.append("export const upstream = ")
          .append(up == null ? "null" : quote(up)).append(";\n");
        js.append("export const refSource = ")
          .append(refSource == null ? "null" : quote(refSource)).append(";\n");
        js.append("export const columns = [");
        for (int i = 0; i < columns.size(); i++) {
            js.append(i > 0 ? ", " : "").append(quote(columns.get(i)));
        }
        js.append("];\n");
        js.append("export const rows = [\n");
        for (int r = 0; r < rows.size(); r++) {
            GlossRelations.Row row = rows.get(r);
            js.append("  { pk: ").append(quote(row.pk()))
              .append(", up: ").append(quote(row.up()))
              .append(", label: ").append(quote(row.label()));
            for (int c = 0; c < columns.size(); c++) {
                js.append(", ").append(quote(columns.get(c))).append(": ")
                  .append(value(row.values().get(c)));
            }
            js.append(" }").append(r + 1 < rows.size() ? "," : "").append("\n");
        }
        js.append("];\n");
        return js.toString();
    }

    private static String problem(String name) {
        return "// Generated from the Kranji gloss tier. Data only - no behaviour.\n"
             + "export const relation = " + quote(String.valueOf(name)) + ";\n"
             + "export const columns = [];\n"
             + "export const rows = [];\n"
             + "export const problem = " + quote("no relation named '" + name + "'") + ";\n";
    }

    private static String value(Object v) {
        return v instanceof Integer || v instanceof Long ? String.valueOf(v) : quote(String.valueOf(v));
    }

    /** JSON string escaping, enough for the values this data can hold. */
    private static String quote(String s) {
        var out = new StringBuilder("\"");
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
