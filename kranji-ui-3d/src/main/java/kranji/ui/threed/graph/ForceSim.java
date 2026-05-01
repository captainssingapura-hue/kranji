package kranji.ui.threed.graph;

import java.util.List;

/**
 * Pure-array force-directed simulation primitives, factored out of
 * {@link LiveSpringMode} so the live-mode class stays focused on its
 * lifecycle and view binding.
 *
 * <p>All methods operate on {@code double[n][3]} position/velocity/
 * displacement arrays and the {@code int[2]} edge-pair list. No fields,
 * no JavaFX — just math.</p>
 */
public final class ForceSim {

    private ForceSim() {}

    /** Pairwise repulsion: {@code F = k²·scale / dist}. */
    public static void applyRepulsion(double[][] pos, double[][] disp, int n, double k, double scale) {
        double k2s = k * k * scale;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dx = pos[i][0] - pos[j][0], dy = pos[i][1] - pos[j][1], dz = pos[i][2] - pos[j][2];
                double dist = Math.max(0.01, Math.sqrt(dx*dx + dy*dy + dz*dz));
                double f = k2s / dist;
                double fx = (dx/dist)*f, fy = (dy/dist)*f, fz = (dz/dist)*f;
                disp[i][0] += fx; disp[i][1] += fy; disp[i][2] += fz;
                disp[j][0] -= fx; disp[j][1] -= fy; disp[j][2] -= fz;
            }
        }
    }

    /** Hooke-style attraction along edges: {@code F = dist²·scale / k}. */
    public static void applyAttraction(double[][] pos, double[][] disp, List<int[]> edges, double k, double scale) {
        double invK = scale / k;
        for (int[] pair : edges) {
            int a = pair[0], b = pair[1];
            double dx = pos[a][0] - pos[b][0], dy = pos[a][1] - pos[b][1], dz = pos[a][2] - pos[b][2];
            double dist = Math.max(0.01, Math.sqrt(dx*dx + dy*dy + dz*dz));
            double f = (dist * dist) * invK;
            double fx = (dx/dist)*f, fy = (dy/dist)*f, fz = (dz/dist)*f;
            disp[a][0] -= fx; disp[a][1] -= fy; disp[a][2] -= fz;
            disp[b][0] += fx; disp[b][1] += fy; disp[b][2] += fz;
        }
    }

    /**
     * Velocity-Verlet integration with friction + per-frame velocity cap.
     * Pinned nodes have their velocity zeroed and position untouched.
     */
    public static void integrate(double[][] pos, double[][] vel, double[][] disp,
                                 boolean[] pinned, double alpha, double velocityDecay, double vMax) {
        double keep = 1.0 - velocityDecay;
        for (int i = 0; i < pos.length; i++) {
            if (pinned[i]) { vel[i][0]=0; vel[i][1]=0; vel[i][2]=0; continue; }
            vel[i][0] = (vel[i][0] + disp[i][0] * alpha) * keep;
            vel[i][1] = (vel[i][1] + disp[i][1] * alpha) * keep;
            vel[i][2] = (vel[i][2] + disp[i][2] * alpha) * keep;
            double vmag = Math.sqrt(vel[i][0]*vel[i][0] + vel[i][1]*vel[i][1] + vel[i][2]*vel[i][2]);
            if (vmag > vMax) {
                double s = vMax / vmag;
                vel[i][0] *= s; vel[i][1] *= s; vel[i][2] *= s;
            }
            pos[i][0] += vel[i][0]; pos[i][1] += vel[i][1]; pos[i][2] += vel[i][2];
        }
    }

    /**
     * Position-based collision correction with a priority hierarchy:
     * <b>anchor &gt; dragged &gt; free</b>. Higher-priority node holds
     * position; the lower-priority partner absorbs the full overlap.
     * Two equals split 50/50, two anchors are skipped (unresolvable).
     *
     * <p>This means an anchor still repels a node the user is dragging
     * — the dragged node gets pushed out of the anchor's space rather
     * than passing through it.</p>
     */
    public static void enforceMinSeparation(double[][] pos, boolean[] pinned, boolean[] anchored,
                                            int n, double minSep) {
        for (int i = 0; i < n; i++) for (int j = i + 1; j < n; j++) {
            int prio_i = anchored[i] ? 2 : (pinned[i] ? 1 : 0);
            int prio_j = anchored[j] ? 2 : (pinned[j] ? 1 : 0);
            if (prio_i == 2 && prio_j == 2) continue;          // both anchors → skip
            double dx = pos[i][0]-pos[j][0], dy = pos[i][1]-pos[j][1], dz = pos[i][2]-pos[j][2];
            double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);
            if (dist >= minSep) continue;
            if (dist < 1e-3) { dx = 1; dy = 0; dz = 0; dist = 1; }
            double k = (minSep - dist) / dist;
            double iShare, jShare;
            if (prio_i > prio_j) { iShare = 0;   jShare = 1; }
            else if (prio_j > prio_i) { iShare = 1; jShare = 0; }
            else { iShare = 0.5; jShare = 0.5; }
            pos[i][0] += dx*k*iShare; pos[i][1] += dy*k*iShare; pos[i][2] += dz*k*iShare;
            pos[j][0] -= dx*k*jShare; pos[j][1] -= dy*k*jShare; pos[j][2] -= dz*k*jShare;
        }
    }

    /** Zero the displacement accumulator each frame. */
    public static void zero(double[][] d) { for (double[] r : d) { r[0]=0; r[1]=0; r[2]=0; } }
}
