package kranji.studio.articles;

import kranji.studio.articles.MdDocument.Block;
import kranji.studio.articles.MdDocument.Span;

import java.util.List;

/**
 * A parsed document, as JSON.
 *
 * <h2>One serialiser, two callers</h2>
 *
 * <p>The workbench route and the generator emit the same bytes, and that is the
 * point of them sharing this rather than each having their own. What an author
 * checks in the bench is what ships; two serialisers that agreed today would
 * be two serialisers that disagreed later, and the disagreement would show up
 * as a document that previewed one way and read another.</p>
 *
 * <h2>Short names</h2>
 *
 * <p>An article is a few dozen blocks and several thousand spans. {@code "kind"}
 * spelled out on every one of them is a good part of the payload, so a span is
 * {@code k}/{@code t} and its optional parts are omitted rather than sent
 * empty.</p>
 *
 * <p>Written by hand into a {@code StringBuilder}. The shape is four fields
 * wide and a mapping library would be a dependency to describe it.</p>
 */
public final class MdJson {

    private MdJson() {}

    /** A document's blocks, as a JSON array. */
    public static String blocks(List<Block> blocks) {
        var js = new StringBuilder();
        blocks(js, blocks);
        return js.toString();
    }

    public static void blocks(StringBuilder js, List<Block> blocks) {
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
     * A line's spans.
     *
     * @see MdDocument.Span for what {@code r}, {@code e} and {@code run} mean
     */
    public static void spans(StringBuilder js, List<Span> line) {
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

    /** A JSON string. Nothing a draft can contain escapes it. */
    public static String quote(String raw) {
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
