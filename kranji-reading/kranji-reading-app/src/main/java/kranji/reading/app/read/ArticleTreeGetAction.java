package kranji.reading.app.read;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.reading.content.ClasspathArticles;
import kranji.reading.model.ArticleTree;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The articles as a tree, shelved by class.
 *
 * <p>Same node shape as {@code /zi-tree}, so the same {@code TreeRenderer}
 * draws it — {@code level}, {@code segment}, a {@code display} block, and
 * {@code children}.</p>
 *
 * <h2>The segment is the identity</h2>
 *
 * <p>A title's {@code segment} is its {@link kranji.reading.model.ArticleId},
 * so activating a leaf yields the id with nothing to look up. That is the whole
 * contract between this tree and the reader: the tree says which article, the
 * party carries it, and the reader fetches it.</p>
 *
 * <p>Built from headers only. Listing every article never parses one.</p>
 */
public final class ArticleTreeGetAction
        implements GetAction<RoutingContext, ArticleTreeGetAction.Query,
                             EmptyParam.NoHeaders, DocContent> {

    /** No query - the whole tree is small and always wanted whole. */
    public record Query() implements Param._QueryString {}

    /** Route this action is mounted on. */
    public static final String PATH = "/article-tree";

    private static final String JSON = "application/json; charset=utf-8";

    @Override
    public ParamMarshaller._QueryString<RoutingContext, Query> queryStrMarshaller() {
        return ctx -> new Query();
    }

    @Override
    public ParamMarshaller._Header<RoutingContext, EmptyParam.NoHeaders> headerMarshaller() {
        return ctx -> new EmptyParam.NoHeaders();
    }

    @Override
    public CompletableFuture<DocContent> execute(Query query,
                                                 EmptyParam.NoHeaders headers) {
        return CompletableFuture.completedFuture(new DocContent(treeJson(), JSON));
    }

    /** Visible for testing — the whole tree. */
    public static String treeJson() {
        List<ArticleTree.Shelf> shelves =
                ArticleTree.shelve(ClasspathArticles.INSTANCE.headers());

        int total = shelves.stream().mapToInt(s -> s.titles().size()).sum();

        var js = new StringBuilder();
        js.append("{\"level\":\"L0\",\"segment\":\"articles\",\"display\":{")
          .append("\"label\":").append(quote("Articles"))
          .append(",\"badge\":").append(quote(String.valueOf(total)))
          .append(",\"note\":").append(quote(total + " to read"))
          .append(",\"kind\":\"library\"},\"dimensions\":[],\"children\":[");

        for (int i = 0; i < shelves.size(); i++) {
            if (i > 0) js.append(',');
            appendShelf(js, shelves.get(i));
        }
        return js.append("]}").toString();
    }

    private static void appendShelf(StringBuilder js, ArticleTree.Shelf shelf) {
        js.append("{\"level\":\"L1\",\"segment\":").append(quote(shelf.type().wireId()))
          .append(",\"display\":{\"label\":").append(quote(shelf.type().displayName()))
          .append(",\"badge\":").append(quote(String.valueOf(shelf.titles().size())))
          .append(",\"note\":").append(quote(shelf.type().summary()))
          .append(",\"kind\":\"shelf\"},\"dimensions\":[],\"children\":[");

        List<ArticleTree.Title> titles = shelf.titles();
        for (int i = 0; i < titles.size(); i++) {
            if (i > 0) js.append(',');
            appendTitle(js, titles.get(i));
        }
        js.append("]}");
    }

    private static void appendTitle(StringBuilder js, ArticleTree.Title title) {
        var header = title.header();
        js.append("{\"level\":\"L2\",\"segment\":").append(quote(header.id().value()))
          .append(",\"display\":{\"label\":").append(quote(header.title()))
          .append(",\"badge\":").append(quote(""))
          .append(",\"note\":").append(quote(header.hasAuthor() ? header.author() : ""))
          // The reader keys off this. A shelf is not openable; a title is.
          .append(",\"kind\":\"article\"},\"dimensions\":[],\"children\":[]}");
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
                default   -> {
                    if (c < 0x20) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
                }
            }
        }
        return sb.append('"').toString();
    }
}
