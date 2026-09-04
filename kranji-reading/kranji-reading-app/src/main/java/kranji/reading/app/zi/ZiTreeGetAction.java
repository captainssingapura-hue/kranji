package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.js.homing.tree.NormalizedNode;
import hue.captains.singapura.js.homing.tree.RowDisplaySource;
import hue.captains.singapura.js.homing.tree.TreeNodeJsonWriter;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.simple.PhonicProjection;
import kranji.zi.tree.ZiProjectionTree;
import kranji.zi.tree.ZiTreeNode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Serves a Zi projection as the canonical tree JSON the generic renderer reads.
 *
 * <p>No bespoke wire format and no per-tree JavaScript: the framework's tree
 * renderer draws whatever {@code TreeNodeJsonWriter} emits, so a projection
 * costs one adapter and nothing on the browser side.</p>
 *
 * <p>The projection is recomputed per request. That is affordable precisely
 * because the tree terminates at a syllable — around 1,350 nodes whatever the
 * corpus grows to — and it keeps the action stateless.</p>
 */
public final class ZiTreeGetAction
        implements GetAction<RoutingContext, ZiTreeGetAction.Query, EmptyParam.NoHeaders, DocContent> {

    /** Route this action is mounted on. */
    public static final String PATH = "/zi-tree";

    public record Query(String projection) implements Param._QueryString {}

    private final TreeNodeJsonWriter writer = new TreeNodeJsonWriter();

    @Override
    public ParamMarshaller._QueryString<RoutingContext, Query> queryStrMarshaller() {
        return ctx -> new Query(ctx.request().getParam("projection"));
    }

    @Override
    public ParamMarshaller._Header<RoutingContext, EmptyParam.NoHeaders> headerMarshaller() {
        return ctx -> new EmptyParam.NoHeaders();
    }

    @Override
    public CompletableFuture<DocContent> execute(Query query, EmptyParam.NoHeaders headers) {
        try {
            ZiProjectionTree projection = PhonicProjection.INSTANCE.projection();
            NormalizedNode root = ZiTreeAdapter.toNormalized(projection);

            // Row display is looked up by segment path, which is what the
            // adapter used for identity - so the two halves agree by
            // construction rather than by convention.
            Map<String, ZiTreeNode> bySegmentPath = new HashMap<>();
            index(projection.root(), "", bySegmentPath);

            RowDisplaySource rows = node -> {
                if (!(node instanceof NormalizedNode n)) return null;
                if (!(n.identity() instanceof ZiNodeIdentity id)) return null;
                ZiTreeNode source = bySegmentPath.get(id.path());
                return source == null ? null : ZiTreeAdapter.rowFor(source);
            };

            return CompletableFuture.completedFuture(
                    new DocContent(writer.write(root, rows), "application/json; charset=utf-8"));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(
                    new IllegalStateException("failed to serialise the Zi tree: " + e.getMessage(), e));
        }
    }

    private static void index(ZiTreeNode node, String path, Map<String, ZiTreeNode> out) {
        out.put(path, node);
        for (ZiTreeNode child : node.children()) {
            index(child, path.isEmpty() ? child.segment() : path + "/" + child.segment(), out);
        }
    }
}
