package kranji.reading.app.read;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.reading.content.Articles;
import kranji.reading.library.ArticleAddress;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.CollectionId;
import kranji.reading.library.Libraries;
import kranji.reading.library.LocalId;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Serves one article as an ES module: the file, as it was written.
 *
 * <pre>{@code
 * export const id     = "chengyu:hua-she-tian-zu";
 * export const title  = "画蛇添足";
 * export const source = "楚国有一个人…\n\n有人说…";
 * }</pre>
 *
 * <h2>The wire is the file</h2>
 *
 * <p>This once sent cells with every character's codepoint and reading
 * resolved, then source lines rebuilt from a parse. Both were restatements of
 * something the other end already had — the codepoint of what the glyph says,
 * the reading the corpus knows, and finally the file the resource holds.</p>
 *
 * <p>So it sends the file. Resolve the address, read the resource, quote it:
 * no parse, no token walk, no line rebuilt. What a browser needs to know about
 * the format it knows — {@code ArticleScannerModule} splits blocks and squares
 * with the block rule of {@link kranji.reading.content.ArticleParser} and the
 * cell rule of {@link kranji.reading.model.Cells}, both ported and both held to
 * the Java by a parity test that runs over the real corpus at build time.</p>
 *
 * <p>That is where the checking moved to, and it is the better place for it.
 * A serve-time parse could only reject a bad article once a child had asked
 * for it; a build-time one cannot ship it. Serving stays a resource read.</p>
 *
 * <p>The one thing the article still carries that nothing else can supply is
 * the reading its author chose against the principal, written {@code 地{dì}}.
 * The scanner canonicalises it on arrival, because below the display layer a
 * reading is a key — see {@code PinyinSwfModule}.</p>
 *
 * <p>Data literals only, like the other data actions.</p>
 */
public final class ArticleGetAction
        implements GetAction<RoutingContext, ArticleGetAction.Query,
                             EmptyParam.NoHeaders, DocContent> {

    /** Route this action is mounted on. */
    public static final String PATH = "/article";

    private static final String JS = "text/javascript; charset=utf-8";

    /** @param id the article slug, e.g. {@code jing-ye-si} */
    public record Query(String id) implements Param._QueryString {}


    @Override
    public ParamMarshaller._QueryString<RoutingContext, Query> queryStrMarshaller() {
        return ctx -> new Query(ctx.request().getParam("id"));
    }

    @Override
    public ParamMarshaller._Header<RoutingContext, EmptyParam.NoHeaders> headerMarshaller() {
        return ctx -> new EmptyParam.NoHeaders();
    }

    @Override
    public CompletableFuture<DocContent> execute(Query query, EmptyParam.NoHeaders headers) {
        return CompletableFuture.completedFuture(
                new DocContent(moduleFor(query.id()), JS));
    }

    public static String moduleFor(String rawAddress) {
        ArticleAddress address;
        try {
            String raw = rawAddress == null ? "" : rawAddress.trim();
            int colon = raw.lastIndexOf(':');
            if (colon <= 0) throw new IllegalArgumentException("expected collection:local");
            address = new ArticleAddress(
                    CollectionId.named(raw.substring(0, colon)),
                    LocalId.named(raw.substring(colon + 1)));
        } catch (RuntimeException e) {
            return errorModule("not an article address: '" + rawAddress + "'");
        }
        Optional<ArticleRef> found = Libraries.mounted().tree().find(address);
        if (found.isEmpty()) return errorModule("no article '" + address + "'");
        ArticleRef ref = found.get();

        Optional<String> source = Articles.sourceOf(ref);
        if (source.isEmpty()) {
            return errorModule("article '" + address + "' has no text at " + ref.resource());
        }
        // The one thing worth refusing to serve without reading the format: an
        // entry that resolves to nothing at all. A blank scan is a blank page,
        // and "no article" is a truer thing to say than an empty sheet. Whether
        // what IS there parses is the build's question, not this request's.
        if (source.get().isBlank()) {
            return errorModule("article '" + address + "' is empty");
        }

        var js = new StringBuilder();
        js.append("// Generated from the Kranji corpus. Data only - no behaviour.\n");
        js.append("export const id = ").append(quote(address.toString())).append(";\n");
        js.append("export const title = ").append(quote(ref.title())).append(";\n");
        js.append("export const source = ").append(quote(source.get())).append(";\n");
        return js.toString();
    }

    private static String errorModule(String problem) {
        return "// No article could be served.\n"
             + "export const id = \"\";\n"
             + "export const title = \"\";\n"
             + "export const source = \"\";\n"
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
