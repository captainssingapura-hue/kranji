package kranji.studio.articles;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;
import kranji.reading.content.ParseFinding;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The drafts on disk, and one of them rendered.
 *
 * <p>One route, two questions, told apart by whether a draft is named. Without
 * {@code ?id=} it lists the folder; with one it parses that draft through
 * {@link MdSubsetParser} and returns the HTML and everything the subset had to
 * say about it.</p>
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

    /** @param id the id the listing gave a draft, or absent to list the folder */
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
        String id = query.id();
        String body = (id == null || id.isBlank()) ? listing() : preview(id);
        return CompletableFuture.completedFuture(new DocContent(body, JSON));
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

    /** Visible for testing — one draft, parsed. */
    static String preview(String id) {
        Optional<String> source = MdSourceFolder.read(id);
        String name = MdSourceFolder.draft(id).map(MdSourceFolder.Draft::name).orElse(id);
        if (source.isEmpty()) {
            return "{\"name\":" + quote(name) + ",\"ok\":false,\"html\":\"\",\"title\":\"\","
                 + "\"findings\":[{\"severity\":\"ERROR\",\"line\":0,\"message\":"
                 + quote("no draft with id '" + id + "' in " + MdSourceFolder.dir()) + "}]}";
        }
        MdSubsetParser.Parsed parsed = MdSubsetParser.parse(source.get());

        var js = new StringBuilder("{\"name\":").append(quote(name))
                .append(",\"title\":").append(quote(parsed.title()))
                .append(",\"ok\":").append(parsed.ok())
                .append(",\"html\":").append(quote(parsed.html().orElse("")))
                .append(",\"findings\":[");
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
