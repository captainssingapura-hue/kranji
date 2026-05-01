package kranji.ui.threed.graph;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * D3-style interactive force-directed view, generic in node type {@code <N>}.
 * Verlet FR step every frame + hard collision constraint. Drag any
 * node; anchors hold position under force but can still be dragged.
 */
public final class LiveSpringMode<N> {

    /** Default spring rest-length / ideal edge distance. Override per call. */
    public static final double DEFAULT_K_DISTANCE = 100;
    private static final double GLYPH_SIZE = 64;
    /** Hard collision radius — no two characters can sit closer than this. */
    private static final double MIN_SEPARATION = GLYPH_SIZE;
    private static final int    GLYPH_LAYERS = 8;
    private static final double SPRING_R   = 1.2;
    private static final double WORLD_PER_PX = 0.9;
    private static final double FORCE_SCALE  = 0.012;
    private static final double V_MAX        = 25;
    private static final double ALPHA_DECAY  = 0.0228;
    private static final double ALPHA_MIN    = 0.001;
    private static final double ALPHA_DRAG   = 0.3;
    private static final double VELOCITY_DECAY = 0.4;

    // Graph (immutable after construction).
    private final List<N> nodes;
    private final List<int[]> edgePairs;     // each entry = {fromIdx, toIdx}
    private final Set<Integer> anchorIdxs;   // never moves (force or drag)
    private double kDistance;                // ideal edge length — live-tunable
    private final String fontFamily;         // glyph font (null = default)

    // Mutable simulation state.
    private final double[][] pos, vel, disp;
    private final boolean[] pinned;
    private final boolean[] anchored;
    private double alpha = 1.0;
    private double alphaTarget = 0.0;
    /** Per-frame strength of edge-attraction. Lower = stretchier springs. */
    private double attractionScale = FORCE_SCALE;

    // JavaFX refs (parallel arrays indexed by node / edge index).
    private final Group worldRoot;
    private final Group[] nodeViews;
    private final Cylinder[] edgeCyls;

    // Drag tracking — camera basis captured at press so mouse delta projects onto the view plane.
    private final SubScene sub;
    private int dragIdx = -1;
    private double dragMouseStartX, dragMouseStartY;
    private double dragNodeStartX, dragNodeStartY, dragNodeStartZ;
    private Point3D dragRightAxis, dragDownAxis;

    private final AnimationTimer timer;

    /** Convenience overload using {@link #DEFAULT_K_DISTANCE}. */
    public LiveSpringMode(SubScene sub, Group worldRoot,
                          List<N> nodes, List<Edge<N>> edges,
                          Collection<N> anchors,
                          double[][] initialPos,
                          Function<N, String> labelFn,
                          Paint glyphPaint, Color springColor) {
        this(sub, worldRoot, nodes, edges, anchors, initialPos,
                labelFn, glyphPaint, springColor, DEFAULT_K_DISTANCE);
    }

    public LiveSpringMode(SubScene sub, Group worldRoot,
                          List<N> nodes, List<Edge<N>> edges,
                          Collection<N> anchors,
                          double[][] initialPos,
                          Function<N, String> labelFn,
                          Paint glyphPaint, Color springColor,
                          double kDistance) {
        this(sub, worldRoot, nodes, edges, anchors, initialPos,
                labelFn, glyphPaint, springColor, kDistance, null);
    }

    public LiveSpringMode(SubScene sub, Group worldRoot,
                          List<N> nodes, List<Edge<N>> edges,
                          Collection<N> anchors,
                          double[][] initialPos,
                          Function<N, String> labelFn,
                          Paint glyphPaint, Color springColor,
                          double kDistance, String fontFamily) {
        this.sub = sub;
        this.worldRoot = worldRoot;
        this.nodes = List.copyOf(nodes);
        this.kDistance = kDistance;
        this.fontFamily = fontFamily;
        int n = nodes.size();

        Map<N, Integer> idx = new HashMap<>();
        for (int i = 0; i < n; i++) idx.put(nodes.get(i), i);
        var anchorSet = new HashSet<Integer>();
        if (anchors != null) {
            for (N a : anchors) {
                Integer ai = idx.get(a);
                if (ai != null) anchorSet.add(ai);
            }
        }
        this.anchorIdxs = Set.copyOf(anchorSet);

        this.edgePairs = new ArrayList<>();
        for (Edge<N> e : edges) {
            Integer a = idx.get(e.from()), b = idx.get(e.to());
            if (a != null && b != null) edgePairs.add(new int[] {a, b});
        }

        this.pos = new double[n][3];
        for (int i = 0; i < n; i++) {
            pos[i][0] = initialPos[i][0]; pos[i][1] = initialPos[i][1]; pos[i][2] = initialPos[i][2];
        }
        this.vel = new double[n][3];
        this.disp = new double[n][3];
        this.pinned = new boolean[n];
        this.anchored = new boolean[n];
        for (int ai : anchorIdxs) { pinned[ai] = true; anchored[ai] = true; }

        this.nodeViews = new Group[n];
        for (int i = 0; i < n; i++) {
            Group view = GlyphNode3d.extrudedGlyph(
                    labelFn.apply(nodes.get(i)), glyphPaint, GLYPH_SIZE,
                    GLYPH_LAYERS, 1.0, fontFamily);
            if (view == null) continue;
            view.setTranslateX(pos[i][0]); view.setTranslateY(pos[i][1]); view.setTranslateZ(pos[i][2]);
            attachDragHandlers(view, i);
            nodeViews[i] = view;
            worldRoot.getChildren().add(view);
        }

        this.edgeCyls = new Cylinder[edgePairs.size()];
        for (int e = 0; e < edgePairs.size(); e++) {
            Cylinder c = new Cylinder(SPRING_R, 1);
            c.setMaterial(new PhongMaterial(springColor));
            edgeCyls[e] = c;
            worldRoot.getChildren().add(c);
        }

        this.timer = new AnimationTimer() {
            @Override public void handle(long now) { tick(); }
        };
    }

    public void start() { timer.start(); }

    /**
     * Live-update the ideal edge length and reheat the sim so the new
     * equilibrium is sought immediately. Smaller {@code k} pulls
     * connected nodes closer; larger {@code k} stretches them apart.
     */
    public void setKDistance(double k) {
        if (k <= 0 || k == this.kDistance) return;
        this.kDistance = k;
        this.alpha = Math.max(this.alpha, 0.5);   // reheat
    }

    public double getKDistance() { return kDistance; }

    /**
     * Live-update the spring stiffness. Pass a multiplier where
     * {@code 1.0} = default, {@code <1} = stretchier (more elastic),
     * {@code >1} = stiffer (more rigid). Rest length is unchanged.
     */
    public void setStiffnessMultiplier(double mul) {
        if (mul <= 0) return;
        this.attractionScale = FORCE_SCALE * mul;
        this.alpha = Math.max(this.alpha, 0.3);   // mild reheat
    }

    public void stop() {
        timer.stop();
        for (Group v : nodeViews) if (v != null) worldRoot.getChildren().remove(v);
        for (Cylinder c : edgeCyls) if (c != null) worldRoot.getChildren().remove(c);
    }

    /** One simulation tick. Skips entirely when settled and idle. */
    private void tick() {
        if (alpha < ALPHA_MIN && dragIdx < 0) return;
        alpha += (alphaTarget - alpha) * ALPHA_DECAY;
        int n = nodes.size();
        ForceSim.zero(disp);
        ForceSim.applyRepulsion(pos, disp, n, kDistance, FORCE_SCALE);
        ForceSim.applyAttraction(pos, disp, edgePairs, kDistance, attractionScale);
        ForceSim.integrate(pos, vel, disp, pinned, alpha, VELOCITY_DECAY, V_MAX);
        ForceSim.enforceMinSeparation(pos, pinned, anchored, n, MIN_SEPARATION);
        updateViews();
    }

    private void updateViews() {
        for (int i = 0; i < nodes.size(); i++) {
            Group v = nodeViews[i];
            if (v == null) continue;
            v.setTranslateX(pos[i][0]); v.setTranslateY(pos[i][1]); v.setTranslateZ(pos[i][2]);
        }
        for (int e = 0; e < edgePairs.size(); e++) {
            int a = edgePairs.get(e)[0], b = edgePairs.get(e)[1];
            GlyphNode3d.positionSpring(edgeCyls[e],
                    pos[a][0], pos[a][1], pos[a][2],
                    pos[b][0], pos[b][1], pos[b][2]);
        }
    }

    private void attachDragHandlers(Group view, int idx) {
        view.setOnMousePressed(ev -> {
            // Anchors are force-pinned but can still be dragged by hand
            // — the integrator never moves them, only mouse drags do.
            dragIdx = idx; pinned[idx] = true;
            dragMouseStartX = ev.getSceneX(); dragMouseStartY = ev.getSceneY();
            dragNodeStartX = pos[idx][0]; dragNodeStartY = pos[idx][1]; dragNodeStartZ = pos[idx][2];
            Transform t = sub.getCamera().getLocalToSceneTransform();
            dragRightAxis = t.deltaTransform(new Point3D(1, 0, 0));
            dragDownAxis  = t.deltaTransform(new Point3D(0, 1, 0));
            alphaTarget = ALPHA_DRAG;
            if (alpha < ALPHA_DRAG) alpha = ALPHA_DRAG;
            ev.consume();
        });
        view.setOnMouseDragged(ev -> {
            if (dragIdx < 0) return;
            double dx = (ev.getSceneX() - dragMouseStartX) * WORLD_PER_PX;
            double dy = (ev.getSceneY() - dragMouseStartY) * WORLD_PER_PX;
            pos[dragIdx][0] = dragNodeStartX + dragRightAxis.getX()*dx + dragDownAxis.getX()*dy;
            pos[dragIdx][1] = dragNodeStartY + dragRightAxis.getY()*dx + dragDownAxis.getY()*dy;
            pos[dragIdx][2] = dragNodeStartZ + dragRightAxis.getZ()*dx + dragDownAxis.getZ()*dy;
            ev.consume();
        });
        view.setOnMouseReleased(ev -> {
            if (dragIdx >= 0 && !anchorIdxs.contains(dragIdx)) pinned[dragIdx] = false;
            dragIdx = -1; alphaTarget = 0.0; ev.consume();
        });
    }

}
