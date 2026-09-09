package kranji.studio.articles;

import java.util.List;

/**
 * An arrangement, as JSON.
 *
 * <h2>One serialiser, two routes</h2>
 *
 * <p>The workbench asks for a plan so an author can check it; the reader asks
 * for one so a child can read it. They must be the same plan, and the cheapest
 * way to guarantee that is for there to be one of these — see {@link MdJson},
 * which makes the same argument about the document.</p>
 *
 * <h2>One letter per kind</h2>
 *
 * <p>A page of Chinese is a few hundred squares and a document is a few
 * thousand. {@code "kind"} spelled out on each of them is most of the payload,
 * so a square is {@code k} and everything it does not have is omitted rather
 * than sent empty.</p>
 */
public final class GridJson {

    private GridJson() {}

    /** Rows of squares, and how wide the page is. */
    public static void plan(StringBuilder js, GridPlan plan) {
        js.append("{\"columns\":").append(plan.columns()).append(",\"rows\":[");
        List<GridPlan.Row> rows = plan.rows();
        for (int i = 0; i < rows.size(); i++) {
            GridPlan.Row row = rows.get(i);
            if (i > 0) js.append(',');
            js.append("{\"kind\":").append(MdJson.quote(row.kind()))
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

    public static void square(StringBuilder js, Square square) {
        switch (square) {
            // z: a character. Nothing rides on it; marks have squares.
            case Square.Zi zi -> {
                js.append("{\"k\":\"z\",\"t\":").append(MdJson.quote(zi.zi()));
                if (!zi.reading().isEmpty()) js.append(",\"r\":").append(MdJson.quote(zi.reading()));
                if (zi.bold())               js.append(",\"b\":true");
                js.append('}');
            }
            // l: one character that is not Chinese. Its own kind rather than a
            // flag on z, because it is not a character to practise and a
            // renderer has to be able to draw it differently.
            case Square.Letter letter -> {
                js.append("{\"k\":\"l\",\"t\":").append(MdJson.quote(letter.text()));
                if (letter.bold()) js.append(",\"b\":true");
                js.append('}');
            }
            case Square.Marker marker ->
                js.append("{\"k\":\"t\",\"t\":").append(MdJson.quote(marker.text())).append('}');
            // s: one punctuation mark, in a square of its own like everything
            // else. Marks used to share a box and to hang past the margin, and
            // both are gone.
            case Square.Punct punct ->
                js.append("{\"k\":\"s\",\"t\":").append(MdJson.quote(punct.mark())).append('}');
            case Square.Indent ignored -> js.append("{\"k\":\"i\"}");
        }
    }
}
