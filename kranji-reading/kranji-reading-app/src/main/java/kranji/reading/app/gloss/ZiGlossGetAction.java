package kranji.reading.app.gloss;

import hue.captains.singapura.js.homing.server.EmptyParam;
import hue.captains.singapura.js.homing.studio.base.DocContent;
import hue.captains.singapura.tao.http.action.GetAction;
import hue.captains.singapura.tao.http.action.Param;
import hue.captains.singapura.tao.http.action.ParamMarshaller;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Every glossed reading and what it means — the whole lot, once.
 *
 * <h2>Why the whole thing rather than one at a time</h2>
 *
 * <p>The same bargain the article census makes, for the same reason. A grid
 * whose rows are readings wants a meaning per row, and a request per row is a
 * few hundred round trips to draw one list. The glosses are small, they depend
 * on nothing about the reader, and they change only when the collections do —
 * so the browser caches the module by URL and a second visit pays nothing.</p>
 *
 * <p>Keyed on {@code codepoint:reading}, which is the key the known set, the
 * census and the gloss tier already share. A view holding a known-set key can
 * therefore look a meaning up without composing anything, and without learning
 * how the other side spells its keys.</p>
 *
 * <h2>The primary only</h2>
 *
 * <p>One meaning per pair, not the whole sense list. This feeds a table cell,
 * and a cell has room for the one meaning that answers the question — which is
 * exactly what a reading's primary sense is defined to be. A pane with room for
 * more asks {@code /zi-detail}, which carries every sense of every reading of
 * one character.</p>
 *
 * <p>Same bargain as the other data actions: outside the crate, outside
 * conformance, <b>data literals only</b>, asserted by
 * {@code ZiGlossGetActionTest}.</p>
 *
 * <p>No CJK appears in this file. Characters arrive from the collections.</p>
 */
public final class ZiGlossGetAction
        implements GetAction<RoutingContext, ZiGlossGetAction.Query,
                             EmptyParam.NoHeaders, DocContent> {

    /** Route this action is mounted on. */
    public static final String PATH = "/zi-gloss";

    private static final String JS = "text/javascript; charset=utf-8";

    /** No query — the glossary is small and always wanted whole. */
    public record Query() implements Param._QueryString {}

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
        return CompletableFuture.completedFuture(new DocContent(module(), JS));
    }

    /** Visible for testing — the module text. */
    public static String module() {
        Map<String, String> primaries = ZiGlossary.primaries();

        var js = new StringBuilder();
        js.append("// Generated from the Kranji gloss collections. "
                + "Data only - no behaviour.\n");
        // The count is stated rather than left to be measured. A view that finds
        // nothing needs to tell a person whether the glossary is empty or
        // whether their character simply is not in it, and those read the same
        // from a lookup that missed.
        js.append("export const glossed = ").append(primaries.size()).append(";\n");
        js.append("export const meanings = {\n");
        int i = 0;
        for (Map.Entry<String, String> pair : primaries.entrySet()) {
            js.append("  ").append(quote(pair.getKey())).append(": ")
              .append(quote(pair.getValue()))
              .append(++i < primaries.size() ? "," : "").append("\n");
        }
        js.append("};\n");
        return js.toString();
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
