package kranji.studio.articles;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import hue.captains.singapura.tao.http.action.PostAction;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.concurrent.CompletableFuture;

/**
 * Adding a folder to the shelf, and taking one off it.
 *
 * <h2>Why this is not a GET</h2>
 *
 * <p>It writes. A GET that writes is one a link, a prefetch, a browser's
 * back button or a Refresh can fire without anybody meaning to, and the shelf
 * would then gain or lose a row for no reason a person could point at.</p>
 *
 * <h2>What comes back</h2>
 *
 * <p>The whole shelf, not an acknowledgement. The pane has to redraw either
 * way, and a route that answered {@code {"ok":true}} would make it fetch the
 * list again immediately — two round trips to learn one thing.</p>
 *
 * <p>A refusal is {@code ok:false} with a message somebody can act on, and it
 * still carries the shelf. Typing a path that is not a folder is an ordinary
 * mistake, not an error condition: the pane says what was wrong and leaves
 * everything else where it was.</p>
 */
public final class ArticleRootsPostAction
        implements PostAction<RoutingContext, ArticleRootsPostAction.Body,
                              EmptyParam.NoHeaders, DocContent> {

    /** Same path as the listing. The verb is what tells them apart. */
    public static final String PATH = ArticleRootsGetAction.PATH;

    private static final String JSON = "application/json; charset=utf-8";

    /**
     * @param action {@code add} or {@code remove}
     * @param path   the folder to add
     * @param id     the root to remove
     */
    public record Body(String action, String path, String id) implements Param._Post {}

    @Override
    public ParamMarshaller._Post<RoutingContext, Body> postMarshaller() {
        return ctx -> {
            String raw = ctx.body() == null ? null : ctx.body().asString();
            if (raw == null || raw.isBlank()) return new Body("", "", "");
            try {
                var json = new JsonObject(raw);
                return new Body(json.getString("action", ""),
                                json.getString("path", ""),
                                json.getString("id", ""));
            } catch (RuntimeException notJson) {
                return new Body("", "", "");
            }
        };
    }

    @Override
    public ParamMarshaller._Header<RoutingContext, EmptyParam.NoHeaders> headerMarshaller() {
        return ctx -> new EmptyParam.NoHeaders();
    }

    @Override
    public CompletableFuture<DocContent> execute(Body body, EmptyParam.NoHeaders headers) {
        return CompletableFuture.completedFuture(new DocContent(apply(body), JSON));
    }

    /** Visible for testing — the change, and the shelf that came of it. */
    static String apply(Body body) {
        ArticleRoots.Change change = switch (body.action() == null ? "" : body.action()) {
            case "add"    -> ArticleRoots.add(body.path());
            case "remove" -> ArticleRoots.remove(body.id());
            default       -> new ArticleRoots.Change(false, "no such action");
        };

        // The shelf comes back either way, because the pane redraws either way.
        String shelf = ArticleRootsGetAction.listing();
        return "{\"ok\":" + change.ok()
             + ",\"message\":" + MdJson.quote(change.message())
             + ",\"shelf\":" + shelf + "}";
    }
}
