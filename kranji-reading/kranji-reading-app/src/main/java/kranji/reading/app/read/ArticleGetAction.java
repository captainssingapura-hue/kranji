package kranji.reading.app.read;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.reading.content.Articles;
import kranji.reading.content.ParsedArticle;
import kranji.reading.model.Article;
import kranji.reading.library.ArticleAddress;
import kranji.reading.library.CollectionId;
import kranji.reading.library.Libraries;
import kranji.reading.library.LocalId;
import kranji.reading.model.Block;
import kranji.reading.model.Lines;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Serves one article as an ES module, in the form it was authored.
 *
 * <pre>{@code
 * { kind: "verse", lines: ["床前明月光，", "疑是地{dì}上霜。"] }
 * { kind: "p",     text:  "春天来了。小草绿了，花儿开了。" }
 * }</pre>
 *
 * <h2>The wire is the source</h2>
 *
 * <p>This used to send cells with every character's codepoint and reading
 * resolved. Both were restatements: the codepoint of what the glyph already
 * says, and the reading of what the corpus already knows. Measured on the
 * bundled articles, dropping them made a module three to six times smaller —
 * but size was the lesser reason.</p>
 *
 * <p>The real cost was that a principal reading became <em>frozen into every
 * article that used the character</em>. Correcting 地 in the corpus would leave
 * every article containing 地 quietly serving the old reading. Now an article
 * carries only what nothing else can supply: its text, and the readings its
 * author chose against the principal, written {@code 地{dì}}.</p>
 *
 * <p>{@link Lines} writes that form and the parser reads it, so authoring,
 * storage, and wire are one format. A browser turns it into squares with
 * {@code ArticleScannerModule}, which is {@link kranji.reading.model.Cells}
 * ported to JavaScript and held to it by a parity test.</p>
 *
 * <p>Data literals only, like the other data actions, asserted by
 * {@code ArticleGetActionTest}.</p>
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
        Optional<ParsedArticle> found = Articles.read(Libraries.mounted(), address);
        if (found.isEmpty()) return errorModule("no article '" + address + "'");
        ParsedArticle parsed = found.get();
        if (parsed.article().isEmpty()) {
            return errorModule("article '" + address + "' did not parse");
        }
        Article article = parsed.article().get();

        var js = new StringBuilder();
        js.append("// Generated from the Kranji corpus. Data only - no behaviour.\n");
        js.append("export const id = ").append(quote(article.address().toString())).append(";\n");
        js.append("export const title = ").append(quote(article.title())).append(";\n");
        js.append("export const length = ").append(article.length()).append(";\n");

        // No catalogue here. Every article used to carry the whole list so the
        // reader's own dropdown could offer the others - one list repeated into
        // every article, to be read by nothing once /article-tree existed.
        // Choosing what to read is the Library's job now.

        js.append("export const blocks = [\n");
        List<Block> blocks = article.blocks();
        for (int b = 0; b < blocks.size(); b++) {
            appendBlock(js, blocks.get(b));
            js.append(b + 1 < blocks.size() ? "," : "").append("\n");
        }
        js.append("];\n");
        return js.toString();
    }

    private static void appendBlock(StringBuilder js, Block block) {
        switch (block) {
            case Block.Paragraph p ->
                // A paragraph is one flow that wraps, so it is one string. Verse
                // has authored breaks, so it is many. The wire mirrors the ADT
                // rather than flattening both into a single shape.
                js.append("  { kind: \"p\", text: ").append(quote(Lines.of(p.tokens())))
                  .append(" }");
            case Block.Verse v -> {
                js.append("  { kind: \"verse\", lines: [\n");
                List<String> lines = Lines.of(v);
                for (int i = 0; i < lines.size(); i++) {
                    js.append("    ").append(quote(lines.get(i)))
                      .append(i + 1 < lines.size() ? "," : "").append("\n");
                }
                js.append("  ] }");
            }
            case Block.Illustration i ->
                js.append("  { kind: \"img\", src: ")
                  .append(quote("/article-asset?file=" + i.file()))
                  .append(", alt: ").append(quote(i.alt()))
                  .append(", caption: ").append(quote(Lines.of(i.caption())))
                  .append(" }");
        }
    }

    private static String errorModule(String problem) {
        return "// No article could be served.\n"
             + "export const id = \"\";\n"
             + "export const title = \"\";\n"
             + "export const length = 0;\n"
             + "export const blocks = [];\n"
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
