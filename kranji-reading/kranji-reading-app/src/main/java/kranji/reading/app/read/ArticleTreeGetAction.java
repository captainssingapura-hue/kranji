package kranji.reading.app.read;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.Libraries;
import kranji.reading.library.LibraryTree;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The library as a tree, for the browser to draw.
 *
 * <p>Same node shape as {@code /zi-tree}, so the same {@code TreeRenderer}
 * draws it — {@code level}, {@code segment}, a {@code display} block, and
 * {@code children}.</p>
 *
 * <h2>Collections are the leaves the author wrote</h2>
 *
 * <p>Branches and collections are declared by whichever root {@link Libraries}
 * mounted — this action never names one; the articles
 * beneath a collection are <em>not</em> — they travel with it. That is why
 * rearranging the tree cannot lose or duplicate an article: the tree never held
 * one.</p>
 *
 * <h2>What a node says about itself</h2>
 *
 * <p>Its name, sounded — see {@link PinyinLabel}. A shelf used to carry its
 * curator's summary as well, a sentence of English about the class of
 * literature on it. It was the wrong aid in the wrong place: a reader who
 * cannot read 唐诗 cannot use a sentence explaining what 唐诗 is, and it wanted
 * a column the tree does not have, so it wrapped and pushed the shelves
 * apart. The reading goes where the difficulty is, on the name itself.</p>
 *
 * <p>An article node carries its full address, {@code collection:local}, which
 * is what the reader needs and all it needs. Built from the Java catalogue, so
 * listing the whole library opens no files.</p>
 */
public final class ArticleTreeGetAction
        implements GetAction<RoutingContext, ArticleTreeGetAction.Query,
                             EmptyParam.NoHeaders, DocContent> {

    /** No query - the tree is small and always wanted whole. */
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
    public CompletableFuture<DocContent> execute(Query query, EmptyParam.NoHeaders headers) {
        return CompletableFuture.completedFuture(new DocContent(treeJson(), JSON));
    }

    /** Visible for testing — the whole tree. */
    public static String treeJson() {
        LibraryTree tree = Libraries.mounted().tree();
        int articles = tree.collections().stream().mapToInt(c -> c.articles().size()).sum();

        var js = new StringBuilder();
        appendNode(js, tree, 0, String.valueOf(articles), articles + " to read", "library");
        return js.toString();
    }

    private static void appendNode(StringBuilder js, LibraryTree node, int level,
                                   String badge, String note, String kind) {
        switch (node) {
            case LibraryTree.Branch b -> {
                open(js, level, segment(b.title()), PinyinLabel.sounded(b.title()),
                     badge, note, kind);
                List<LibraryTree> children = b.children();
                for (int i = 0; i < children.size(); i++) {
                    if (i > 0) js.append(',');
                    LibraryTree child = children.get(i);
                    appendNode(js, child, level + 1, countOf(child), "", kindOf(child));
                }
                js.append("]}");
            }
            case LibraryTree.Shelf s -> {
                ArticleCollection c = s.collection();
                open(js, level, c.id().value(), PinyinLabel.sounded(c.title()),
                     String.valueOf(c.articles().size()), "", "shelf");
                List<ArticleRef> articles = c.articles();
                for (int i = 0; i < articles.size(); i++) {
                    if (i > 0) js.append(',');
                    appendArticle(js, level + 1, c, articles.get(i));
                }
                js.append("]}");
            }
        }
    }

    private static void appendArticle(StringBuilder js, int level,
                                      ArticleCollection c, ArticleRef ref) {
        // The segment is the full address. The reader needs nothing else, and
        // an article is addressed the same way wherever its collection hangs.
        js.append("{\"level\":\"L").append(level).append("\",\"segment\":")
          .append(quote(c.address(ref.id()).toString()))
          .append(",\"display\":{\"label\":").append(quote(PinyinLabel.sounded(ref.title())))
          .append(",\"badge\":").append(quote(""))
          .append(",\"note\":").append(quote(PinyinLabel.sounded(ref.author())))
          .append(",\"kind\":\"article\"},\"dimensions\":[],\"children\":[]}");
    }

    private static void open(StringBuilder js, int level, String segment, String label,
                             String badge, String note, String kind) {
        js.append("{\"level\":\"L").append(level).append("\",\"segment\":").append(quote(segment))
          .append(",\"display\":{\"label\":").append(quote(label))
          .append(",\"badge\":").append(quote(badge))
          .append(",\"note\":").append(quote(note == null ? "" : note))
          .append(",\"kind\":\"").append(kind).append("\"},\"dimensions\":[],\"children\":[");
    }

    private static String countOf(LibraryTree node) {
        return switch (node) {
            case LibraryTree.Shelf s -> String.valueOf(s.collection().articles().size());
            case LibraryTree.Branch b -> String.valueOf(
                    b.collections().stream().mapToInt(c -> c.articles().size()).sum());
        };
    }

    private static String kindOf(LibraryTree node) {
        return node instanceof LibraryTree.Shelf ? "shelf" : "group";
    }

    /** A branch has no id of its own; its title is stable enough to address by. */
    private static String segment(String title) {
        return title;
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
