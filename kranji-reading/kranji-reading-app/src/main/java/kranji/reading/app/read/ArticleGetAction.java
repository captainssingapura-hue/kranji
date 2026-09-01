package kranji.reading.app.read;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.reading.content.ClasspathArticles;
import kranji.reading.content.ParsedArticle;
import kranji.reading.model.Article;
import kranji.reading.model.ArticleId;
import kranji.reading.model.Block;
import kranji.reading.model.Cell;
import kranji.reading.model.Cells;
import kranji.reading.model.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Serves one article as an ES module, already resolved into cells.
 *
 * <p>The model is a flat list of tokens; a <b>cell</b> is a rendering unit —
 * one character plus any punctuation glued to it. Merging happens here rather
 * than in the browser so the rule is written once, in Java, where it can be
 * tested.</p>
 *
 * <h2>Why punctuation is glued</h2>
 *
 * <p>禁则: 。，、 must never begin a line and 「（ must never end one. Making the
 * pair one atomic cell is the whole mechanism — a table row cannot break inside
 * a cell, so the rule holds without any line-breaking code.</p>
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

    /** Visible for testing — the module text for one article. */
    public static String moduleFor(String rawId) {
        ArticleId id;
        try {
            id = new ArticleId(rawId == null ? "" : rawId);
        } catch (RuntimeException e) {
            return errorModule("not an article id: '" + rawId + "'");
        }
        Optional<ParsedArticle> found = ClasspathArticles.INSTANCE.find(id);
        if (found.isEmpty()) return errorModule("no article '" + id + "'");
        ParsedArticle parsed = found.get();
        if (parsed.article().isEmpty()) {
            return errorModule("article '" + id + "' did not parse");
        }
        Article article = parsed.article().get();

        var js = new StringBuilder();
        js.append("// Generated from the Kranji corpus. Data only - no behaviour.\n");
        js.append("export const id = ").append(quote(article.id().value())).append(";\n");
        js.append("export const title = ").append(quote(article.title())).append(";\n");
        js.append("export const length = ").append(article.length()).append(";\n");

        // Every article carries the catalogue, so a reader can offer the others
        // without a second request. It is small and changes only when the
        // bundled set does.
        js.append("export const catalogue = [");
        List<ArticleId> all = ClasspathArticles.INSTANCE.catalogue();
        for (int i = 0; i < all.size(); i++) {
            js.append(i > 0 ? ", " : "").append(quote(all.get(i).value()));
        }
        js.append("];\n");

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
            case Block.Paragraph p -> {
                js.append("  { kind: \"p\", cells: ");
                appendCells(js, p.tokens());
                js.append(" }");
            }
            case Block.Verse v -> {
                js.append("  { kind: \"verse\", lines: [\n");
                List<List<Token>> lines = v.lines();
                for (int i = 0; i < lines.size(); i++) {
                    js.append("    ");
                    appendCells(js, lines.get(i));
                    js.append(i + 1 < lines.size() ? "," : "").append("\n");
                }
                js.append("  ] }");
            }
            case Block.Illustration i -> {
                js.append("  { kind: \"img\", src: ")
                  .append(quote("/article-asset?file=" + i.file()))
                  .append(", alt: ").append(quote(i.alt())).append(", caption: ");
                appendCells(js, i.caption());
                js.append(" }");
            }
        }
    }

    /**
     * Serialises the cells for one line. The merge rule itself lives in
     * {@link Cells}, in the model, where it is tested without a browser.
     */
    private static void appendCells(StringBuilder js, List<Token> tokens) {
        List<String> out = new ArrayList<>();
        for (Cell cell : Cells.of(tokens)) {
            out.add(switch (cell) {
                case Cell.Char c -> character(c.character(), "", "");
                case Cell.CharWithPunctuation c ->
                        character(c.character(), c.leading(), c.trailing());
                case Cell.Plain p -> "{ t: " + quote(p.text()) + " }";
            });
        }
        js.append("[").append(String.join(", ", out)).append("]");
    }

    private static String character(Token.Zi zi, String leading, String trailing) {
        var cell = new StringBuilder("{ z: ").append(quote(zi.zi().value()))
                .append(", c: ").append(quote(zi.zi().codePointLabel()))
                .append(", r: ").append(quote(zi.reading().toDiacritic()));
        if (zi.authored())        cell.append(", o: true");
        if (!leading.isEmpty())   cell.append(", lp: ").append(quote(leading));
        if (!trailing.isEmpty())  cell.append(", p: ").append(quote(trailing));
        return cell.append(" }").toString();
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
