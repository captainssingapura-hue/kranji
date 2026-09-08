package kranji.studio.articles;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The shelf of folders, and what it takes to change it.
 *
 * <h2>One route, and a verb that is not a GET</h2>
 *
 * <p>Listing is a GET. Adding and removing are not — they change something on
 * disk, and a GET that writes is a GET that a link, a prefetch or a refresh can
 * fire by accident. {@link ArticleRootsPostAction} sits on the same path and
 * does the changing.</p>
 *
 * <p>The store's own path travels too. A tool that reads its settings from a
 * file somebody cannot find is a tool nobody can fix, and this is exactly the
 * kind of file that ends up in a different home directory than the one you are
 * looking in.</p>
 */
public final class ArticleRootsGetAction
        implements GetAction<RoutingContext, EmptyParam.NoQuery,
                             EmptyParam.NoHeaders, DocContent> {

    /** Route this action is mounted on. */
    public static final String PATH = "/article-roots";

    private static final String JSON = "application/json; charset=utf-8";

    @Override
    public ParamMarshaller._QueryString<RoutingContext, EmptyParam.NoQuery>
            queryStrMarshaller() {
        return ctx -> new EmptyParam.NoQuery();
    }

    @Override
    public ParamMarshaller._Header<RoutingContext, EmptyParam.NoHeaders> headerMarshaller() {
        return ctx -> new EmptyParam.NoHeaders();
    }

    @Override
    public CompletableFuture<DocContent> execute(EmptyParam.NoQuery query,
                                                 EmptyParam.NoHeaders headers) {
        return CompletableFuture.completedFuture(new DocContent(listing(), JSON));
    }

    /** Visible for testing — the shelf, as the pane sees it. */
    static String listing() {
        var js = new StringBuilder("{\"store\":")
                .append(MdJson.quote(ArticleRoots.store().toString()))
                .append(",\"roots\":[");
        List<ArticleRoots.Root> roots = ArticleRoots.roots();
        for (int i = 0; i < roots.size(); i++) {
            ArticleRoots.Root r = roots.get(i);
            if (i > 0) js.append(',');
            js.append("{\"id\":").append(MdJson.quote(r.id()))
              .append(",\"name\":").append(MdJson.quote(r.name()))
              .append(",\"path\":").append(MdJson.quote(r.path()))
              // A root on an unmounted drive is still a root somebody chose.
              // The pane says so rather than the list quietly losing it.
              .append(",\"present\":").append(r.present())
              .append('}');
        }
        return js.append("]}").toString();
    }
}
