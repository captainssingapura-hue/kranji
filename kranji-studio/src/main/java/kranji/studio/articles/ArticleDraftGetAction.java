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
        MdJson.blocks(js, blocks);
        // The same document, arranged. Both views travel together because a
        // workbench exists to compare them: the question a squares view answers
        // is what the reader will do with what the document says.
        js.append(",\"plan\":");
        GridJson.plan(js, GridPlanner.plan(blocks, columns));
        // And the same document as a tree. Three views, one fetch: a workbench
        // exists to hold them against each other.
        js.append(",\"tree\":");
        segment(js, Segments.of(blocks));
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

    // ── The tree, as JSON ──────────────────────────────────────────────

    /**
     * One segment and everything under it.
     *
     * <p>{@code path} is a position and {@code id} is an address; a segment
     * with no {@code id} has no address yet, which is what a workbench should
     * be able to point at.</p>
     */
    private static void segment(StringBuilder js, Segment segment) {
        js.append("{\"path\":").append(quote(segment.path()))
          .append(",\"id\":").append(quote(segment.id()))
          .append(",\"level\":").append(segment.level())
          .append(",\"title\":").append(quote(segment.title()))
          .append(",\"chars\":").append(segment.chars())
          .append(",\"total\":").append(segment.total())
          .append(",\"blocks\":").append(segment.blocks().size())
          .append(",\"children\":[");
        List<Segment> children = segment.children();
        for (int i = 0; i < children.size(); i++) {
            if (i > 0) js.append(',');
            segment(js, children.get(i));
        }
        js.append("]}");
    }

    // ── The arrangement, as JSON ───────────────────────────────────────

    /** Everything a draft can contain, safely inside a JSON string. */
    private static String quote(String raw) { return MdJson.quote(raw); }
}
