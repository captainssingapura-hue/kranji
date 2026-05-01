package kranji.ui.threed.couplet;

import kranji.ui.threed.graph.Edge;

import java.util.ArrayList;
import java.util.List;

/**
 * Pure data: turn two strings (上联 + 下联) into a graph and a vertical
 * grid initial layout.
 *
 * <p>Each character occupies a {@link Position} (line, col, character)
 * — distinct even if the character repeats. Edges connect consecutive
 * characters within each line only — the two lines are independent
 * chains, not coupled to each other, so the user can reshape one
 * without dragging the other along.</p>
 *
 * <p>Initial layout: upper line as a column at {@code +X}, lower line
 * at {@code -X}, both reading top-to-bottom (smaller {@code y} = higher
 * on screen, since JavaFX uses Y-down). Anchor = first character of
 * the upper line so the structure has a fixed reference.</p>
 */
public final class CoupletGraph {

    /** A single character at a specific (line, column) of the couplet. */
    public record Position(int line, int col, String character) {}

    /**
     * Bundle returned to the playground.
     *
     * @param anchors  the first character of each line — pinned in
     *                 place so each line has its own fixed reference
     *                 and the two lines can be repositioned independently.
     */
    public record Graph(
            List<Position> nodes,
            List<Edge<Position>> edges,
            List<Position> anchors,
            double[][] initialPositions
    ) {}

    private static final double X_SPREAD  = 200;   // half-distance between the two columns
    private static final double Y_SPACING = 75;    // distance between consecutive characters

    private CoupletGraph() {}

    public static Graph build(String upper, String lower) {
        List<Position> upperPos = splitToPositions(upper, 0);
        List<Position> lowerPos = splitToPositions(lower, 1);

        var nodes = new ArrayList<Position>(upperPos.size() + lowerPos.size());
        nodes.addAll(upperPos);
        nodes.addAll(lowerPos);

        var edges = new ArrayList<Edge<Position>>();
        chainEdges(upperPos, edges);
        chainEdges(lowerPos, edges);
        // No horizontal pair edges — the two lines stay independent so
        // dragging one doesn't pull the other.

        // No anchors — every character is free to move under the simulation.
        var anchors = new ArrayList<Position>();
        double[][] init = gridLayout(upperPos.size(), lowerPos.size());

        return new Graph(List.copyOf(nodes), List.copyOf(edges), List.copyOf(anchors), init);
    }

    /** Split {@code text} into one {@link Position} per Unicode codepoint. */
    private static List<Position> splitToPositions(String text, int line) {
        var out = new ArrayList<Position>();
        if (text == null) return out;
        int i = 0;
        while (i < text.length()) {
            int cp = text.codePointAt(i);
            String s = new String(Character.toChars(cp));
            if (!s.isBlank()) out.add(new Position(line, out.size(), s));
            i += Character.charCount(cp);
        }
        return out;
    }

    private static void chainEdges(List<Position> column, List<Edge<Position>> out) {
        for (int i = 0; i + 1 < column.size(); i++) {
            out.add(new Edge<>(column.get(i), column.get(i + 1)));
        }
    }

    /**
     * Two columns reading top-to-bottom, vertically centered around the
     * world origin. JavaFX 3D uses +Y-down so smaller {@code y} = top.
     */
    private static double[][] gridLayout(int upperN, int lowerN) {
        int total = upperN + lowerN;
        double[][] p = new double[total][3];
        double upperOffsetY = -((upperN - 1) * Y_SPACING) / 2.0;
        double lowerOffsetY = -((lowerN - 1) * Y_SPACING) / 2.0;
        for (int i = 0; i < upperN; i++) {
            p[i][0] = X_SPREAD;
            p[i][1] = upperOffsetY + i * Y_SPACING;
            p[i][2] = 0;
        }
        for (int j = 0; j < lowerN; j++) {
            p[upperN + j][0] = -X_SPREAD;
            p[upperN + j][1] = lowerOffsetY + j * Y_SPACING;
            p[upperN + j][2] = 0;
        }
        return p;
    }
}
