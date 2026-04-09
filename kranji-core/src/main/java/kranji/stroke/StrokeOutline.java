package kranji.stroke;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Converts a {@link StrokeInstance} into an SVG path data string by sampling
 * offset curves along the Bézier spine.
 *
 * <p>Algorithm: for each segment, sample N+1 points along the spine at
 * t = 0, 1/N, 2/N, …, 1. At each sample, compute the unit normal and
 * offset left/right by half the modulated width. The outline is formed by
 * traversing the left offsets forward, then the right offsets backward,
 * closing the shape.</p>
 *
 * <p>For multi-segment strokes, the outlines of consecutive segments are
 * joined with bevel-style connections (the last point of one segment's
 * offsets flows into the first point of the next).</p>
 */
public final class StrokeOutline {

    /** Number of samples per Bézier segment. */
    private static final int SAMPLES_PER_SEGMENT = 20;

    /** Minimum half-width to prevent invisible strokes at deep nesting. */
    private static final double MIN_HALF_WIDTH = 0.1;

    private StrokeOutline() {}

    /**
     * Generate an SVG {@code <path>} "d" attribute string for a stroke.
     *
     * @param stroke a fully transformed stroke (in final SVG coordinates)
     * @return SVG path data string (e.g. "M ... L ... Z")
     */
    public static String toPathData(StrokeInstance stroke) {
        WidthProfile profile = stroke.effectiveProfile();
        double baseWidth = stroke.baseWidth();
        List<CubicBezier> segments = stroke.segments();

        List<Vec2> leftPoints = new ArrayList<>();
        List<Vec2> rightPoints = new ArrayList<>();

        for (int s = 0; s < segments.size(); s++) {
            CubicBezier seg = segments.get(s);
            int startI = (s == 0) ? 0 : 1; // avoid duplicating join points

            for (int i = startI; i <= SAMPLES_PER_SEGMENT; i++) {
                double tLocal = (double) i / SAMPLES_PER_SEGMENT;

                // For multi-segment strokes, map local t to global t for width profile
                double tGlobal = (s + tLocal) / segments.size();

                double halfWidth = Math.max(baseWidth * profile.at(tGlobal) / 2.0, MIN_HALF_WIDTH);

                Vec2 point = seg.at(tLocal);
                Vec2 normal = seg.normalAt(tLocal);

                leftPoints.add(point.add(normal.scale(halfWidth)));
                rightPoints.add(point.add(normal.scale(-halfWidth)));
            }
        }

        // Build outline: left forward, then right backward
        Collections.reverse(rightPoints);

        StringBuilder sb = new StringBuilder();
        appendMoveTo(sb, leftPoints.get(0));
        for (int i = 1; i < leftPoints.size(); i++) {
            appendLineTo(sb, leftPoints.get(i));
        }
        for (Vec2 rp : rightPoints) {
            appendLineTo(sb, rp);
        }
        sb.append('Z');

        return sb.toString();
    }

    /**
     * Generate a complete SVG {@code <path>} element string.
     *
     * @param stroke a fully transformed stroke
     * @param fill   CSS fill color (e.g. "#1a1a1a")
     * @return a complete {@code <path>} SVG element
     */
    public static String toSvgPath(StrokeInstance stroke, String fill) {
        return "<path d=\"" + toPathData(stroke) + "\" fill=\"" + fill + "\"/>";
    }

    private static void appendMoveTo(StringBuilder sb, Vec2 p) {
        sb.append('M').append(fmt(p.x())).append(' ').append(fmt(p.y()));
    }

    private static void appendLineTo(StringBuilder sb, Vec2 p) {
        sb.append('L').append(fmt(p.x())).append(' ').append(fmt(p.y()));
    }

    private static String fmt(double v) {
        // 2 decimal places is sufficient for SVG rendering
        return String.format("%.2f", v);
    }
}
