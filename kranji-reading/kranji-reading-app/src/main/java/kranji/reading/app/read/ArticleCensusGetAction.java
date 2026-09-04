package kranji.reading.app.read;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.reading.content.Articles;
import kranji.reading.content.ParsedArticle;
import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.Libraries;
import kranji.reading.model.ArticleCensus;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * What every article asks of a reader — the whole library at once.
 *
 * <h2>Why the arithmetic happens in the browser</h2>
 *
 * <p>Readability is <em>known ÷ total</em>, and the known set never leaves the
 * device. The server therefore cannot compute it and must not try: it ships the
 * half that depends only on the article, and the browser intersects that with a
 * set the server has never seen.</p>
 *
 * <p>This is the same trade as the syllable map. Sending the census once and
 * answering locally turns "how readable is each of these?" from a request per
 * article per profile change into an intersection over data already in hand —
 * which is what makes sorting and filtering a catalogue by fit affordable at
 * all.</p>
 *
 * <h2>The whole library in one module</h2>
 *
 * <p>Because a catalogue wants every article's figure at once, and per-article
 * requests would be twenty-three round trips to draw one list. The census is
 * profile-free and changes only when the content does, so the browser caches
 * the module by URL and a second visit pays nothing.</p>
 *
 * <p>Counts, not lists of readings: an article that uses 好 forty times sends
 * {@code "22909:hǎo": 40}, not forty entries. The distinct-reading figure the
 * catalogue shows is then the size of the object, and the ratio is a sum over
 * it.</p>
 *
 * <p>No CJK appears in this file. Characters arrive from the corpus.</p>
 */
public final class ArticleCensusGetAction
        implements GetAction<RoutingContext, ArticleCensusGetAction.Query,
                             EmptyParam.NoHeaders, DocContent> {

    /** No query — the census is small and always wanted whole. */
    public record Query() implements Param._QueryString {}

    /** Route this action is mounted on. */
    public static final String PATH = "/article-census";

    private static final String JS = "text/javascript; charset=utf-8";

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
        return CompletableFuture.completedFuture(new DocContent(censusJs(), JS));
    }

    /** Visible for testing — every article in the bundled library. */
    public static String censusJs() {
        var js = new StringBuilder();
        js.append("// Generated from the Kranji corpus. Data only - no behaviour.\n");
        js.append("export const articles = {\n");

        boolean first = true;
        for (ArticleCollection collection : Libraries.mounted().tree().collections()) {
            for (ArticleRef ref : collection.articles()) {
                var address = collection.address(ref.id());
                // An article that will not parse is a content defect, caught by the
                // bundled-article tests. It is left out of the census rather than
                // reported here, where nobody would see it.
                var parsed = Articles.read(address, ref).flatMap(ParsedArticle::article);
                if (parsed.isEmpty()) continue;

                ArticleCensus census = ArticleCensus.of(parsed.get());
                if (!first) js.append(",\n");
                first = false;
                js.append("  ").append(quote(address.toString())).append(": { total: ")
                  .append(census.total()).append(", title: ").append(quote(ref.title()))
                  .append(", pairs: {");
                appendPairs(js, census.pairs());
                js.append("} }");
            }
        }
        js.append("\n};\n");
        return js.toString();
    }

    private static void appendPairs(StringBuilder js, Map<String, Integer> pairs) {
        boolean first = true;
        for (Map.Entry<String, Integer> pair : pairs.entrySet()) {
            if (!first) js.append(", ");
            first = false;
            // The key is codePoint:reading, which is not a JS identifier, so it
            // is quoted. It has to stay byte-identical to KnownSetModule.keyOf.
            js.append(quote(pair.getKey())).append(": ").append(pair.getValue());
        }
    }

    private static String quote(String s) {
        var sb = new StringBuilder("\"");
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
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
