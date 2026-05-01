package kranji.ui.threed;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kranji.ui.threed.graph.ComponentGraph;
import kranji.ui.threed.graph.ComponentGraphRenderer;
import kranji.ui.threed.graph.ForceLayout3d;
import kranji.ui.threed.graph.LiveSpringMode;
import kranji.ui.threed.graph.StructureGraph;
import kranji.ui.threed.graph.StructureGraphRenderer;
import kranji.zi.Zi;

import java.util.List;
import java.util.Map;

/**
 * Standalone JavaFX 3D viewer for Chinese-character composition.
 *
 * <p><b>Stage 1</b>: layered block extrusion. Each block in the
 * composition becomes a coloured slab in 3D, with deeper nesting
 * levels stepping toward the camera. Mouse drag rotates, wheel zooms,
 * an on-canvas slider controls the depth spacing.</p>
 *
 * <p>UI shape: just the 3D viewport, with a thin floating control
 * strip at the top (glyph input + depth-offset slider). No sidebar —
 * the visual is the focus.</p>
 */
public final class Kranji3dApp extends Application {

    private static final String DEFAULT_GLYPH = "锁";
    private static final String MODE_EXTRUSION = "Layered extrusion";
    private static final String MODE_GRAPH     = "Component graph";
    private static final String MODE_SPRINGS   = "Spring network";
    private static final String MODE_LIVE      = "Interactive (drag)";

    private Map<String, Zi> byGlyph;
    private Map<String, List<Zi>> immediateUsersByGlyph;
    private Group worldRoot;

    // Live UI state.
    private Zi currentZi;
    private double depthSpacing = BlockExtrusionRenderer.DEFAULT_DEPTH_SPACING;
    private Color glyphColor = BlockExtrusionRenderer.DEFAULT_GLYPH_COLOR;
    private boolean exploded = false;
    private String mode = MODE_EXTRUSION;
    private LiveSpringMode<kranji.zi.BlockStructure> liveMode;
    private SubScene sub;
    private CameraRig rig;

    @Override
    public void start(Stage stage) {
        // ── Data: same corpus as the 2D explorer ──
        var indexes = CorpusIndexes.loadAndBuild();
        byGlyph = indexes.byGlyph();
        immediateUsersByGlyph = indexes.immediateUsersByGlyph();

        // ── 3D viewport ──
        worldRoot = new Group();
        var lights = new Group(
                new AmbientLight(Color.gray(0.55)),
                makePointLight(Color.WHITE, 0.6, 200, -300, -800));
        var sceneRoot = new Group(lights, worldRoot);

        sub = new SubScene(sceneRoot, 800, 600, true, SceneAntialiasing.BALANCED);
        sub.setFill(Color.web("#F5F5F7"));
        rig = new CameraRig();
        sub.setCamera(rig.camera());
        rig.install(sub);

        // ── Floating overlay (glyph + depth slider) ──
        var glyphField = new TextField(DEFAULT_GLYPH);
        glyphField.setPromptText("Glyph");
        glyphField.setPrefColumnCount(4);
        glyphField.setStyle(
                "-fx-background-color: rgba(255,255,255,0.85);"
                + " -fx-border-color: #CCC; -fx-border-radius: 3;"
                + " -fx-font-size: 14;");
        glyphField.setOnAction(e -> renderGlyph(glyphField.getText()));

        var depthSlider = new Slider(0, 150, depthSpacing);
        depthSlider.setShowTickMarks(true);
        depthSlider.setShowTickLabels(true);
        depthSlider.setMajorTickUnit(50);
        depthSlider.setBlockIncrement(5);
        depthSlider.setPrefWidth(280);
        var depthValueLabel = new Label(String.format("%.0f px", depthSpacing));
        depthValueLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #444;"
                + " -fx-min-width: 50;");
        depthSlider.valueProperty().addListener((obs, old, val) -> {
            depthSpacing = val.doubleValue();
            depthValueLabel.setText(String.format("%.0f px", depthSpacing));
            rerender();
        });

        var depthLabel = new Label("Depth offset");
        depthLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #444;");

        var colorLabel = new Label("Color");
        colorLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #444;");
        var colorPicker = new ColorPicker(glyphColor);
        colorPicker.setStyle("-fx-color-label-visible: false;");
        colorPicker.valueProperty().addListener((obs, old, val) -> {
            glyphColor = val;
            rerender();
        });

        var modeChoice = new ChoiceBox<String>();
        modeChoice.getItems().addAll(MODE_EXTRUSION, MODE_GRAPH, MODE_SPRINGS, MODE_LIVE);
        modeChoice.setValue(mode);
        modeChoice.valueProperty().addListener((obs, old, val) -> {
            mode = val;
            rerender();
        });

        var explodedToggle = new CheckBox("Exploded");
        explodedToggle.setSelected(exploded);
        explodedToggle.setStyle("-fx-font-size: 12; -fx-text-fill: #444;");
        explodedToggle.selectedProperty().addListener((obs, old, val) -> {
            exploded = val;
            rerender();
        });

        var hint = new Label("drag to orbit · scroll to zoom");
        hint.setStyle("-fx-font-size: 11; -fx-text-fill: #888;");

        var bar = new HBox(12, modeChoice, glyphField,
                depthLabel, depthSlider, depthValueLabel,
                colorLabel, colorPicker,
                explodedToggle,
                hint);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(8, 14, 8, 14));
        bar.setStyle(
                "-fx-background-color: rgba(250,250,250,0.86);"
                + " -fx-border-color: #DDD;"
                + " -fx-border-width: 0 0 1 0;");
        // Don't let the bar steal mouse drags from the 3D scene.
        bar.setMaxHeight(Double.NEGATIVE_INFINITY);  // shrink to fit
        bar.setPickOnBounds(false);

        // ── Stack: 3D fills the window; bar floats at the top ──
        var subHolder = new StackPane(sub);
        sub.widthProperty().bind(subHolder.widthProperty());
        sub.heightProperty().bind(subHolder.heightProperty());

        var topAnchor = new VBox(bar);
        topAnchor.setPickOnBounds(false);

        var root = new StackPane(subHolder, topAnchor);
        StackPane.setAlignment(topAnchor, Pos.TOP_CENTER);

        var scene = new Scene(root, 1100, 700);
        stage.setTitle("Kranji 3D — Layered Block Extrusion");
        stage.setScene(scene);
        stage.show();

        renderGlyph(DEFAULT_GLYPH);
    }

    /** Look up {@code glyph} in the corpus, store as currentZi, render. */
    private void renderGlyph(String glyph) {
        if (glyph == null || glyph.isEmpty()) return;
        Zi z = byGlyph.get(glyph.trim());
        if (z == null) return;
        currentZi = z;
        rerender();
    }

    /** Re-render the active visualization for the current Zi. */
    private void rerender() {
        // Stop any previous live mode and remove its scene nodes.
        if (liveMode != null) {
            liveMode.stop();
            liveMode = null;
        }
        worldRoot.getChildren().clear();
        if (currentZi == null) return;

        if (MODE_LIVE.equals(mode)) {
            // Start head-on so the layout matches the 2D simulation
            // plane, but leave orbit-on-drag enabled. Node clicks
            // consume events at the node level, so dragging a node
            // moves the node and dragging empty space orbits.
            rig.resetHeadOn();
            liveMode = makeStructureLiveMode(currentZi);
            liveMode.start();
            return;
        }

        // Re-enable orbit-on-drag for the static modes.
        rig.unlock();
        Group view = switch (mode) {
            case MODE_GRAPH    -> renderComponentGraph(currentZi);
            case MODE_SPRINGS  -> renderSpringNetwork(currentZi);
            default            -> BlockExtrusionRenderer.render(
                                    currentZi, depthSpacing, glyphColor, exploded);
        };
        worldRoot.getChildren().add(view);
    }

    /** Corpus-mode: focal Zi + its immediate components + immediate users. */
    private Group renderComponentGraph(Zi focal) {
        var graph = ComponentGraph.buildFocal(focal, immediateUsersByGlyph, byGlyph);
        var positions = ForceLayout3d.layout(
                graph.nodes(), graph.edges(), graph.focal(),
                ForceLayout3d.Params.defaults());
        return ComponentGraphRenderer.render(graph, positions, this::renderFocal);
    }

    /** Intra-character mode: every named component connected by springs. */
    private Group renderSpringNetwork(Zi focal) {
        var graph = StructureGraph.buildFor(focal);
        var positions = ForceLayout3d.layout(
                graph.nodes(), graph.edges(), graph.root(),
                ForceLayout3d.Params.defaults());
        return StructureGraphRenderer.render(graph, positions, glyphColor);
    }

    /** Build a {@link LiveSpringMode} configured for the structural graph of {@code focal}. */
    private LiveSpringMode<kranji.zi.BlockStructure> makeStructureLiveMode(Zi focal) {
        var graph = StructureGraph.buildFor(focal);
        int n = graph.nodes().size();
        // Random sphere initial positions; root recentered to origin below
        // by passing it as the anchor (LiveSpringMode pins it in place).
        double[][] init = randomSphere(n, 60 * Math.sqrt(Math.max(1, n)));
        if (n > 0) { init[0][0] = 0; init[0][1] = 0; init[0][2] = 0; }
        return new LiveSpringMode<>(sub, worldRoot,
                graph.nodes(), graph.edges(),
                graph.root() == null ? java.util.List.of() : java.util.List.of(graph.root()),
                init, kranji.zi.BlockStructure::glyph,
                glyphColor, glyphColor.deriveColor(0, 1, 1, 0.45));
    }

    private static double[][] randomSphere(int n, double r) {
        var rng = new java.util.Random(42);
        double[][] p = new double[n][3];
        for (int i = 0; i < n; i++) {
            double x = rng.nextGaussian(), y = rng.nextGaussian(), z = rng.nextGaussian();
            double m = Math.max(0.001, Math.sqrt(x*x + y*y + z*z));
            p[i][0] = (x/m) * r; p[i][1] = (y/m) * r; p[i][2] = (z/m) * r;
        }
        return p;
    }

    /** Refocus the graph on a different Zi (called by node clicks). */
    private void renderFocal(Zi z) {
        currentZi = z;
        rerender();
    }

    private static PointLight makePointLight(Color color, double intensity, double x, double y, double z) {
        var l = new PointLight(color.deriveColor(0, 1, intensity, 1));
        l.setTranslateX(x); l.setTranslateY(y); l.setTranslateZ(z);
        return l;
    }
}
