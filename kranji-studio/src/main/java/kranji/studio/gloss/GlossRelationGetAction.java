package kranji.studio.gloss;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.reading.workbench.relation.Relation;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * One relation of the gloss model, as JSON.
 *
 * <p>Six relations, served from the same route by name — the flattening the
 * grid needs already exists in {@link GlossRelations}, so this only decides
 * which one and writes it out. Data only: no behaviour crosses the wire, which
 * is what lets it be fetched, cached and read by a person.</p>
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

    /**
     * JSON, not an ES module.
     *
     * <p>It was a module, imported with {@code import(url)}, and that memoises
     * on the URL and never re-evaluates - so re-asking for a relation returned
     * the copy the browser already had, and a Refresh button could not work
     * without a cache-busting parameter that left one dead module behind per
     * press. Fetching data is data; nothing about this response was ever
     * behaviour, and saying so in the Content-Type costs nothing.</p>
     */
    private static final String JS = "application/json; charset=utf-8";

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
        return Relation.moduleFor(GlossRelationSet.INSTANCE, name, parents, from);
    }

    /** Whole-relation form, for a root or a picker with no upstream. */
    public static String moduleFor(String name) {
        return moduleFor(name, List.of(), null);
    }
}
