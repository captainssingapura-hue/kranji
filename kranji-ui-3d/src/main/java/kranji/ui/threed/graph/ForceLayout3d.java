package kranji.ui.threed.graph;

import javafx.geometry.Point3D;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 3D Fruchterman-Reingold force-directed layout, generic in node type.
 *
 * <p>Public API is a pure function {@code layout(nodes, edges, pinned,
 * params)}: returns an immutable {@code Map<N, Point3D>} with no
 * observable side effects. Internally uses primitive arrays for the
 * iteration step; the mutation never escapes.</p>
 *
 * <p>Used by both the corpus-graph mode (N = Zi) and the structure-
 * graph mode (N = BlockStructure).</p>
 */
public final class ForceLayout3d {

    /** Tuning knobs for the simulation. */
    public record Params(
            double area,            // bounding box area, drives ideal edge length
            int iterations,
            double initialTemp,     // max move per step on iteration 0
            double cooling          // temperature multiplier per iteration
    ) {
        public static Params defaults() {
            return new Params(600.0 * 600.0, 200, 30.0, 0.97);
        }
    }

    private ForceLayout3d() {}

    /**
     * Run the simulation; return positions keyed by node.
     *
     * @param nodes  every node to lay out (deterministic ordering)
     * @param edges  parent-child / source-target connections; only
     *               nodes referenced here participate in attraction
     * @param pinned recenter the final layout so this node sits at
     *               world origin; pass {@code null} to skip
     * @param params tuning
     */
    public static <N> Map<N, Point3D> layout(List<N> nodes,
                                             List<Edge<N>> edges,
                                             N pinned,
                                             Params params) {
        if (nodes.isEmpty()) return Map.of();

        int n = nodes.size();
        Map<N, Integer> index = indexOf(nodes);
        double k = Math.cbrt(params.area() / Math.max(1, n));   // ideal distance

        double[][] pos = initialPositions(n);
        double[][] disp = new double[n][3];
        double temp = params.initialTemp();

        for (int it = 0; it < params.iterations(); it++) {
            zero(disp);
            applyRepulsion(pos, disp, k, n);
            applyAttraction(pos, disp, edges, index, k);
            applyDisplacement(pos, disp, temp, n);
            temp *= params.cooling();
        }

        if (pinned != null) {
            Integer idx = index.get(pinned);
            if (idx != null) recenterOn(pos, idx, n);
        }
        return toMap(nodes, pos);
    }

    // ── Iteration steps (each is a small, focused mutation on disp/pos) ──

    private static void applyRepulsion(double[][] pos, double[][] disp,
                                       double k, int n) {
        double k2 = k * k;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dx = pos[i][0] - pos[j][0];
                double dy = pos[i][1] - pos[j][1];
                double dz = pos[i][2] - pos[j][2];
                double dist = Math.max(0.01, Math.sqrt(dx*dx + dy*dy + dz*dz));
                double force = k2 / dist;
                double fx = (dx / dist) * force;
                double fy = (dy / dist) * force;
                double fz = (dz / dist) * force;
                disp[i][0] += fx; disp[i][1] += fy; disp[i][2] += fz;
                disp[j][0] -= fx; disp[j][1] -= fy; disp[j][2] -= fz;
            }
        }
    }

    private static <N> void applyAttraction(double[][] pos, double[][] disp,
                                            List<Edge<N>> edges,
                                            Map<N, Integer> index, double k) {
        for (Edge<N> e : edges) {
            Integer ai = index.get(e.from());
            Integer bi = index.get(e.to());
            if (ai == null || bi == null) continue;
            int a = ai, b = bi;
            double dx = pos[a][0] - pos[b][0];
            double dy = pos[a][1] - pos[b][1];
            double dz = pos[a][2] - pos[b][2];
            double dist = Math.max(0.01, Math.sqrt(dx*dx + dy*dy + dz*dz));
            double force = (dist * dist) / k;
            double fx = (dx / dist) * force;
            double fy = (dy / dist) * force;
            double fz = (dz / dist) * force;
            disp[a][0] -= fx; disp[a][1] -= fy; disp[a][2] -= fz;
            disp[b][0] += fx; disp[b][1] += fy; disp[b][2] += fz;
        }
    }

    private static void applyDisplacement(double[][] pos, double[][] disp,
                                          double temp, int n) {
        for (int i = 0; i < n; i++) {
            double mag = Math.sqrt(disp[i][0]*disp[i][0]
                                 + disp[i][1]*disp[i][1]
                                 + disp[i][2]*disp[i][2]);
            if (mag <= 0) continue;
            double scale = Math.min(temp, mag) / mag;
            pos[i][0] += disp[i][0] * scale;
            pos[i][1] += disp[i][1] * scale;
            pos[i][2] += disp[i][2] * scale;
        }
    }

    // ── Helpers ────────────────────────────────────────────────────

    private static <N> Map<N, Integer> indexOf(List<N> nodes) {
        var idx = new HashMap<N, Integer>();
        for (int i = 0; i < nodes.size(); i++) idx.put(nodes.get(i), i);
        return idx;
    }

    private static double[][] initialPositions(int n) {
        // Uniform random points on a sphere whose radius scales with n.
        double r = 50 * Math.sqrt(Math.max(1, n));
        Random rng = new Random(42);   // deterministic for reproducible layouts
        double[][] pos = new double[n][3];
        for (int i = 0; i < n; i++) {
            double x = rng.nextGaussian();
            double y = rng.nextGaussian();
            double z = rng.nextGaussian();
            double mag = Math.max(0.001, Math.sqrt(x*x + y*y + z*z));
            pos[i][0] = (x / mag) * r;
            pos[i][1] = (y / mag) * r;
            pos[i][2] = (z / mag) * r;
        }
        return pos;
    }

    private static void zero(double[][] disp) {
        for (double[] row : disp) Arrays.fill(row, 0);
    }

    private static void recenterOn(double[][] pos, int pinIdx, int n) {
        double dx = pos[pinIdx][0], dy = pos[pinIdx][1], dz = pos[pinIdx][2];
        for (int i = 0; i < n; i++) {
            pos[i][0] -= dx; pos[i][1] -= dy; pos[i][2] -= dz;
        }
    }

    private static <N> Map<N, Point3D> toMap(List<N> nodes, double[][] pos) {
        var out = new LinkedHashMap<N, Point3D>();
        for (int i = 0; i < nodes.size(); i++) {
            out.put(nodes.get(i), new Point3D(pos[i][0], pos[i][1], pos[i][2]));
        }
        return Map.copyOf(out);
    }
}
