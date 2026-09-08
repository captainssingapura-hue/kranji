package kranji.studio.articles;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.reading.content.ParseFinding;
import kranji.studio.articles.MdDocument.Block;
import kranji.studio.articles.MdDocument.Span;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The drafts on disk, and one of them parsed.
 *
 * <p>One route, two questions, told apart by whether a draft is named. Without
 * {@code ?id=} it lists the folder; with one it puts that draft through
 * {@link MdSubsetParser} and returns its blocks and everything the subset had
 * to say about it.</p>
 *
 * <h2>Blocks, not markup</h2>
 *
 * <p>What travels is structure — see {@link MdDocument}. Markup would decide,
 * from here, that the pane must inject it, and a widget that injects markup has
 * given up the branch that owns its DOM. Structure leaves the pane free to
 * build, and leaves stage two free to build something else entirely from the
 * same response.</p>
 *
 * <p>Serialised by hand, into one {@code StringBuilder}. A workbench route is
 * not worth a mapping library, and the shape is four fields wide.</p>
 *
 * <p>JSON rather than an ES module, for the reason the gloss browser next door
 * gives: {@code import(url)} memoises on the URL and never re-evaluates, so a
 * Refresh button could not work. Re-reading a draft after saving it <i>is</i>
 * the workbench, so this had to be fetchable twice.</p>
 *
 * <p>A draft is asked for by the id the listing gave it, never by its name.
 * A Chinese file name does not survive a query string - the request arrives
 * with every Han character replaced by ? - and an id the server issued is also
 * the permission check, since one only resolves to a file the folder still
 * holds.</p>
 *
 * <p>Findings travel whether or not the document rendered. A failed parse with
 * no reason attached would leave the workbench showing an empty pane and the
 * author guessing.</p>
 */
public final class ArticleDraftGetAction
        implements GetAction<RoutingContext, ArticleDraftGetAction.Query,
                             EmptyParam.NoHeaders, DocContent> {

    /** Route this action is mounted on. */
    public static final String PATH = "/article-draft";

    private static final String JSON = "application/json; charset=utf-8";

    /**
     * @param id      the id the listing gave a draft, or absent to list the folder
     * @param columns squares per row, or absent for 每行二十格
     */
    public record Query(String id, String columns) implements Param._QueryString {}

    @Override
    public ParamMarshaller._QueryString<RoutingContext, Query> queryStrMarshaller() {
        return ctx -> new Query(ctx.request().getParam("id"), ctx.request().getParam("columns"));
    }

    @Override
    public ParamMarshaller._Header<RoutingContext, EmptyParam.NoHeaders> headerMarshaller() {
        return ctx -> new EmptyParam.NoHeaders();
    }

    @Override
    public CompletableFuture<DocContent> execute(Query query, EmptyParam.NoHeaders headers) {
        String id = query.id();
        String body = (id == null || id.isBlank())
                ? listing()
                : preview(id, columnsOf(query.columns()));
        return CompletableFuture.completedFuture(new DocContent(body, JSON));
    }

    /**
     * The requested row width, or the default.
     *
     * <p>Clamped rather than rejected. A width comes from a control in a pane,
     * and a workbench that returned an error because somebody typed 500 into a
     * box would be answering the wrong question.</p>
     */
    private static int columnsOf(String raw) {
        if (raw == null || raw.isBlank()) return GridPlanner.DEFAULT_COLUMNS;
        try {
            return Math.clamp(Integer.parseInt(raw.strip()), 4, 60);
        } catch (NumberFormatException notANumber) {
            return GridPlanner.DEFAULT_COLUMNS;
        }
    }

    /** Visible for testing — the folder, as the workbench sees it. */
    static String listing() {
        var js = new StringBuilder("{\"dir\":").append(quote(MdSourceFolder.dir().toString()))
                .append(",\"drafts\":[");
        List<MdSourceFolder.Draft> drafts = MdSourceFolder.drafts();
        for (int i = 0; i < drafts.size(); i++) {
            MdSourceFolder.Draft d = drafts.get(i);
            if (i > 0) js.append(',');
            js.append("{\"id\":").append(quote(d.id()))
              .append(",\"name\":").append(quote(d.name()))
              .append(",\"chars\":").append(d.chars())
              .append(",\"modified\":").append(d.modifiedEpochMs()).append('}');
        }
        return js.append("]}").toString();
    }

    /** Visible for testing — one draft, parsed and arranged. */
    static String preview(String id) {
        return preview(id, GridPlanner.DEFAULT_COLUMNS);
    }

    static String preview(String id, int columns) {
        Optional<String> source = MdSourceFolder.read(id);
        String name = MdSourceFolder.draft(id).map(MdSourceFolder.Draft::name).orElse(id);
        if (source.isEmpty()) {
            return "{\"name\":" + quote(name) + ",\"ok\":false,\"blocks\":[],\"title\":\"\","
                 + "\"plan\":{\"columns\":" + columns + ",\"rows\":[]},"
                 + "\"findings\":[{\"severity\":\"ERROR\",\"line\":0,\"message\":"
                 + quote("no draft with id '" + id + "' in " + MdSourceFolder.dir()) + "}]}";
        }
        MdSubsetParser.Parsed parsed = MdSubsetParser.parse(source.get());

        var js = new StringBuilder("{\"name\":").append(quote(name))
                .append(",\"title\":").append(quote(parsed.title()))
                .append(",\"ok\":").append(parsed.ok())
                .append(",\"blocks\":");
        List<Block> blocks = parsed.blocks().orElse(List.of());
        blocks(js, blocks);
        // The same document, arranged. Both views travel together because a
        // workbench exists to compare them: the question a squares view answers
        // is what the reader will do with what the document says.
        js.append(",\"plan\":");
        plan(js, GridPlanner.plan(blocks, columns));
        js.append(",\"findings\":[");
        List<ParseFinding> findings = parsed.findings();
        for (int i = 0; i < findings.size(); i++) {
            ParseFinding f = findings.get(i);
            if (i > 0) js.append(',');
            js.append("{\"severity\":\"").append(f.severity())
              .append("\",\"line\":").append(f.line())
              .append(",\"message\":").append(quote(f.message())).append('}');
        }
        return js.append("]}").toString();
    }

    // ── The arrangement, as JSON ───────────────────────────────────────

    /**
     * The plan: rows of squares, each square a one-letter kind and its content.
     *
     * <p>Short names throughout. A page of Chinese is a few thousand squares
     * and the difference between {@code "kind"} and {@code "k"} on every one of
     * them is most of the response.</p>
     */
    private static void plan(StringBuilder js, GridPlan plan) {
        js.append("{\"columns\":").append(plan.columns()).append(",\"rows\":[");
        List<GridPlan.Row> rows = plan.rows();
        for (int i = 0; i < rows.size(); i++) {
            GridPlan.Row row = rows.get(i);
            if (i > 0) js.append(',');
            js.append("{\"kind\":").append(quote(row.kind()))
              .append(",\"block\":").append(row.block())
              .append(",\"line\":").append(row.line())
              .append(",\"squares\":[");
            for (int s = 0; s < row.squares().size(); s++) {
                if (s > 0) js.append(',');
                square(js, row.squares().get(s));
            }
            js.append("]}");
        }
        js.append("]}");
    }

    private static void square(StringBuilder js, Square square) {
        switch (square) {
            // z: a character. Its reading, and the punctuation riding in its
            // corners, are omitted when it has none.
            case Square.Zi zi -> {
                js.append("{\"k\":\"z\",\"t\":").append(quote(zi.zi()));
                if (!zi.reading().isEmpty()) js.append(",\"r\":").append(quote(zi.reading()));
                if (!zi.lead().isEmpty())    js.append(",\"lp\":").append(quote(zi.lead()));
                if (!zi.tail().isEmpty())    js.append(",\"p\":").append(quote(zi.tail()));
                if (zi.bold())               js.append(",\"b\":true");
                js.append('}');
            }
            case Square.Marker marker ->
                js.append("{\"k\":\"t\",\"t\":").append(quote(marker.text())).append('}');
            // A run head carries its own spans, so a renderer can put the
            // emphasis back where the author wrote it.
            case Square.Run run -> {
                js.append("{\"k\":\"r\",\"w\":").append(run.width())
                  .append(",\"id\":").append(run.id());
                // Marked means the author wrote the delimiters. It changes no
                // arrangement and is the thing a workbench should point at.
                if (run.marked()) js.append(",\"m\":true");
                if (run.broken()) js.append(",\"cut\":true");
                js.append(",\"parts\":");
                spans(js, run.parts());
                js.append('}');
            }
            case Square.Cont cont ->
                js.append("{\"k\":\"c\",\"id\":").append(cont.id()).append('}');
            case Square.Indent ignored -> js.append("{\"k\":\"i\"}");
        }
    }

    // ── The document, as JSON ──────────────────────────────────────────

    private static void blocks(StringBuilder js, List<Block> blocks) {
        js.append('[');
        for (int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            if (i > 0) js.append(',');
            js.append("{\"kind\":").append(quote(b.kind()))
              .append(",\"level\":").append(b.level())
              .append(",\"id\":").append(quote(b.id()))
              .append(",\"lines\":[");
            for (int l = 0; l < b.lines().size(); l++) {
                if (l > 0) js.append(',');
                spans(js, b.lines().get(l));
            }
            js.append("]}");
        }
        js.append(']');
    }

    /**
     * A line's spans, under short names.
     *
     * <p>Short because these are the numerous thing: an article is a few dozen
     * blocks and several thousand spans, and {@code "kind"} spelled out on
     * every one of them is a good part of the response.</p>
     */
    private static void spans(StringBuilder js, List<Span> line) {
        js.append('[');
        for (int i = 0; i < line.size(); i++) {
            Span s = line.get(i);
            if (i > 0) js.append(',');
            js.append("{\"k\":").append(quote(s.kind()))
              .append(",\"t\":").append(quote(s.text()));
            if (!s.reading().isEmpty())  js.append(",\"r\":").append(quote(s.reading()));
            if (!s.emphasis().isEmpty()) js.append(",\"e\":").append(quote(s.emphasis()));
            if (s.inRun()) js.append(",\"run\":true");
            js.append('}');
        }
        js.append(']');
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
