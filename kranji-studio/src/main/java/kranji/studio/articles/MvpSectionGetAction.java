package kranji.studio.articles;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * A generated document, served the way the library will serve one.
 *
 * <h2>What is being demonstrated</h2>
 *
 * <p>Nothing here parses markdown, and nothing here could: the resources under
 * {@code /kranji/articles/mvp} were written by {@link ArticleGenerator} and hold
 * the parsed document. This route reads structure and arranges it, which is the
 * whole of what a reader has to do.</p>
 *
 * <p>The document is fixed. Choosing between documents is the library's job and
 * the library does not carry one of these yet — see the design note. One
 * document is enough to answer the question this exists to answer, which is
 * whether a generated section reads.</p>
 *
 * <h2>Why the arrangement is still computed per request</h2>
 *
 * <p>Because it depends on the reader. A plan is squares laid into rows, and
 * how many squares fit in a row is a setting — so the <b>structure</b> can be
 * generated once and the <b>arrangement</b> cannot. That is the line this route
 * sits on: it reads what was generated and does only the part that could not
 * have been.</p>
 */
public final class MvpSectionGetAction
        implements GetAction<RoutingContext, MvpSectionGetAction.Query,
                             EmptyParam.NoHeaders, DocContent> {

    /** Route this action is mounted on. */
    public static final String PATH = "/mvp-section";

    /** Where {@code ArticleGeneratorMain} was pointed. */
    public static final String RESOURCES = "/kranji/articles/mvp";

    private static final String JSON = "application/json; charset=utf-8";

    /**
     * @param id      the section, or absent for the index
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
                ? index()
                : section(id, columns(query.columns()));
        return CompletableFuture.completedFuture(new DocContent(body, JSON));
    }

    /** Visible for testing — what there is to read. */
    static String index() {
        return read("index.json");
    }

    /**
     * Visible for testing — one section, arranged.
     *
     * <p>The id is checked against the index rather than against the file
     * system. A resource path taken from a query string is a way to read
     * whatever is on the classpath, and an id the index does not list is not a
     * section however plausible it looks.</p>
     */
    static String section(String id, int columns) {
        if (!listed(id)) {
            return "{\"error\":" + MdJson.quote("no section '" + id + "'") + "}";
        }
        JsonObject section = new JsonObject(read(id + ".json"));
        List<MdDocument.Block> blocks =
                MdJson.blocksFrom(section.getJsonArray("blocks", new io.vertx.core.json.JsonArray()));

        var js = new StringBuilder("{\"id\":").append(MdJson.quote(id))
                .append(",\"title\":").append(MdJson.quote(section.getString("title", "")))
                .append(",\"plan\":");
        GridJson.plan(js, GridPlanner.plan(blocks, columns));
        return js.append('}').toString();
    }

    private static boolean listed(String id) {
        var documents = new JsonObject(index()).getJsonArray("documents");
        for (int d = 0; d < documents.size(); d++) {
            var sections = documents.getJsonObject(d).getJsonArray("sections");
            for (int s = 0; s < sections.size(); s++) {
                if (id.equals(sections.getJsonObject(s).getString("id"))) return true;
            }
        }
        return false;
    }

    /**
     * The requested row width, or 每行二十格.
     *
     * <p>Clamped rather than rejected, for the reason the workbench gives: a
     * width comes from a control, and refusing to render because somebody typed
     * 500 answers the wrong question.</p>
     */
    private static int columns(String raw) {
        if (raw == null || raw.isBlank()) return GridPlanner.DEFAULT_COLUMNS;
        try {
            return Math.clamp(Integer.parseInt(raw.strip()), 4, 60);
        } catch (NumberFormatException notANumber) {
            return GridPlanner.DEFAULT_COLUMNS;
        }
    }

    private static String read(String name) {
        String path = RESOURCES + "/" + name;
        try (InputStream in = MvpSectionGetAction.class.getResourceAsStream(path)) {
            if (in == null) throw new IllegalStateException("generated resource missing: " + path);
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("could not read " + path, e);
        }
    }

    /** The sections, in reading order. Visible for testing. */
    static Optional<String> firstSection() {
        var documents = new JsonObject(index()).getJsonArray("documents");
        if (documents.isEmpty()) return Optional.empty();
        var sections = documents.getJsonObject(0).getJsonArray("sections");
        return sections.isEmpty() ? Optional.empty()
                : Optional.of(sections.getJsonObject(0).getString("id"));
    }
}
