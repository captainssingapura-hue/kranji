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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
     * @param root    the root to read under, or absent for the first on the shelf
     * @param id      the id the listing gave a draft, or absent to list the folder
     * @param columns squares per row, or absent for 每行二十格
     */
    public record Query(String root, String id, String columns) implements Param._QueryString {}

    @Override
    public ParamMarshaller._QueryString<RoutingContext, Query> queryStrMarshaller() {
        return ctx -> new Query(ctx.request().getParam("root"),
                                ctx.request().getParam("id"),
                                ctx.request().getParam("columns"));
    }

    @Override
    public ParamMarshaller._Header<RoutingContext, EmptyParam.NoHeaders> headerMarshaller() {
        return ctx -> new EmptyParam.NoHeaders();
    }

    @Override
    public CompletableFuture<DocContent> execute(Query query, EmptyParam.NoHeaders headers) {
        String id = query.id();
        String body = (id == null || id.isBlank())
                ? listing(query.root())
                : preview(query.root(), id, columnsOf(query.columns()));
        return CompletableFuture.completedFuture(new DocContent(body, JSON));
    }

    /**
     * The root a request means.
     *
     * <p>An unnamed root is the first on the shelf rather than an error, so a
     * pane that has just mounted shows something before anybody has chosen. An
     * id that is <i>not</i> on the shelf resolves to nothing at all: a root is
     * addressed by an id the server issued, which is the same rule a draft
     * follows, and it is what keeps a path in a query string from being a way
     * to read the machine.</p>
     */
    private static Optional<ArticleRoots.Root> rootOf(String id) {
        return (id == null || id.isBlank()) ? ArticleRoots.first() : ArticleRoots.root(id);
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

    /**
     * Visible for testing — the folder, as the workbench sees it.
     *
     * <p>Flat and nested both, because the pane needs both and they are one
     * walk of the disk. The tree is what is drawn; the list is what answers
     * <i>is the draft I had open still here</i> after a refresh.</p>
     */
    static String listing(String rootId) {
        Optional<ArticleRoots.Root> root = rootOf(rootId);
        if (root.isEmpty()) {
            return "{\"root\":\"\",\"dir\":\"\",\"drafts\":[],\"tree\":null,\"message\":"
                 + quote("no folder on the shelf yet") + "}";
        }
        Path dir = Path.of(root.get().path());

        var js = new StringBuilder("{\"root\":").append(quote(root.get().id()))
                .append(",\"dir\":").append(quote(root.get().path()))
                .append(",\"drafts\":[");
        List<MdSourceFolder.Draft> drafts = MdSourceFolder.drafts(dir);
        for (int i = 0; i < drafts.size(); i++) {
            MdSourceFolder.Draft d = drafts.get(i);
            if (i > 0) js.append(',');
            js.append("{\"id\":").append(quote(d.id()))
              .append(",\"name\":").append(quote(d.name()))
              .append(",\"path\":").append(quote(d.path()))
              .append(",\"chars\":").append(d.chars())
              .append(",\"modified\":").append(d.modifiedEpochMs()).append('}');
        }
        js.append("],\"tree\":");
        tree(js, root.get().name(), drafts);
        return js.append('}').toString();
    }

    // ── The folder, as the canonical tree ──────────────────────────────

    /**
     * The directory structure, in the shape {@code TreeRenderer} draws.
     *
     * <p>Which is what buys the bench arrow keys: the renderer owns the key
     * semantics for any conforming tree, so a second tree with a second idea of
     * what ArrowDown means never gets written. It is the same trade the MVP
     * reader made next door.</p>
     *
     * <p>There is no manifest and no ordering file. The folder <i>is</i> the
     * shape, so nothing can fall out of step with it — and a draft's
     * {@code segment} is its id, so choosing a node needs no second lookup. A
     * folder's segment is empty, which is also how the pane knows a folder is
     * not something to open.</p>
     */
    private static void tree(StringBuilder js, String label, List<MdSourceFolder.Draft> drafts) {
        var root = new Folder();
        for (MdSourceFolder.Draft d : drafts) root.add(d);
        root.write(js, label, 0);
    }

    /** A folder on the way to being drawn. Insertion order is path order. */
    private static final class Folder {

        private final Map<String, Folder> folders = new LinkedHashMap<>();
        private final List<MdSourceFolder.Draft> drafts = new ArrayList<>();

        void add(MdSourceFolder.Draft draft) {
            String[] parts = draft.path().split("/");
            Folder here = this;
            for (int i = 0; i < parts.length - 1; i++) {
                here = here.folders.computeIfAbsent(parts[i], unused -> new Folder());
            }
            here.drafts.add(draft);
        }

        void write(StringBuilder js, String label, int depth) {
            node(js, depth, "", label, "", "folder");
            boolean first = true;
            for (var entry : folders.entrySet()) {
                if (!first) js.append(',');
                first = false;
                entry.getValue().write(js, entry.getKey(), depth + 1);
            }
            for (MdSourceFolder.Draft d : drafts) {
                if (!first) js.append(',');
                first = false;
                // Characters, not bytes. A count belongs in a workbench, where
                // it says how much there is to check.
                node(js, depth + 1, d.id(), d.name(), d.chars() + " characters", "draft");
                js.append("]}");
            }
            js.append("]}");
        }
    }

    /** Everything but the children, which the caller closes. */
    private static void node(StringBuilder js, int depth, String segment,
                             String label, String badge, String kind) {
        js.append("{\"level\":\"L").append(depth).append('"')
          .append(",\"segment\":").append(quote(segment))
          .append(",\"display\":{\"label\":").append(quote(label))
          .append(",\"badge\":").append(quote(badge))
          .append(",\"note\":\"\",\"kind\":\"").append(kind).append("\"}")
          .append(",\"dimensions\":[],\"children\":[");
    }

    /** Visible for testing — one draft, parsed and arranged. */
    static String preview(String id) {
        return preview(null, id, GridPlanner.DEFAULT_COLUMNS);
    }

    static String preview(String rootId, String id, int columns) {
        Optional<ArticleRoots.Root> root = rootOf(rootId);
        Path dir = root.map(r -> Path.of(r.path())).orElse(null);

        Optional<String> source = dir == null
                ? Optional.empty() : MdSourceFolder.read(dir, id);
        String name = dir == null ? id
                : MdSourceFolder.draft(dir, id).map(MdSourceFolder.Draft::name).orElse(id);
        if (source.isEmpty()) {
            String where = dir == null ? "no folder on the shelf" : dir.toString();
            return "{\"name\":" + quote(name) + ",\"ok\":false,\"blocks\":[],\"title\":\"\","
                 + "\"plan\":{\"columns\":" + columns + ",\"rows\":[]},"
                 + "\"findings\":[{\"severity\":\"ERROR\",\"line\":0,\"message\":"
                 + quote("no draft with id '" + id + "' in " + where) + "}]}";
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
