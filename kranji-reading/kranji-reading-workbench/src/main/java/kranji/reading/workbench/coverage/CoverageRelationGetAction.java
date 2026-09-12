package kranji.reading.workbench.coverage;

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
 * One coverage relation, as JSON, optionally narrowed to a parent selection.
 *
 * <p>{@code parent} repeats, one per key — an address can hold characters a
 * separator would split on. See {@link Relation#moduleFor} for what an absent
 * {@code parent} means against a bare one.</p>
 */
public final class CoverageRelationGetAction
        implements GetAction<RoutingContext, CoverageRelationGetAction.Query,
                             EmptyParam.NoHeaders, DocContent> {

    public static final String PATH = "/coverage-relation";

    /** JSON, not a module: data, fetched with no-store so Refresh really re-asks. */
    private static final String JSON = "application/json; charset=utf-8";

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
        return CompletableFuture.completedFuture(
                new DocContent(moduleFor(query.name(), query.parents(), query.from()), JSON));
    }

    public static String moduleFor(String name, List<String> parents, String from) {
        return Relation.moduleFor(CoverageRelations.INSTANCE, name, parents, from);
    }

    public static String moduleFor(String name) {
        return moduleFor(name, List.of(), null);
    }
}
