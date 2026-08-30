package kranji.ui.threed.couplet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kranji.ui.threed.CameraRig;
import kranji.ui.threed.couplet.CoupletGraph.Position;
import kranji.ui.threed.graph.GlyphFonts;
import kranji.ui.threed.graph.GlyphTextures;
import kranji.ui.threed.graph.LiveSpringMode;

/**
 * 3D Couplet Playground.
 *
 * <p>Type the upper (上联) and lower (下联) lines, press Render. The
 * characters appear as extruded glyphs in two vertical columns with
 * barely-visible springs connecting them (vertical chains within each
 * line + horizontal pairs across lines). Drag any character to play
 * with the structure — the rest reflows under the same D3-style force
 * simulation used by the explorer.</p>
 */
public final class CoupletApp extends Application {

    private static final String DEFAULT_UPPER = "天增岁月人增寿";
    private static final String DEFAULT_LOWER = "春满乾坤福满园";
    private static final Color GLYPH_COLOR  = Color.rgb(20, 20, 20);
    /** Springs are intentionally faint so the characters dominate. */
    private static final Color SPRING_COLOR = Color.rgb(160, 160, 160, 0.18);

    private SubScene sub;
    private Group worldRoot;
    private CameraRig rig;
    private LiveSpringMode<Position> playground;
    private String currentTexture = GlyphTextures.SOLID;
    private String currentFont = GlyphFonts.defaultFamily();

    @Override
    public void start(Stage stage) {
        // ── 3D viewport ──
        worldRoot = new Group();
        var lights = new Group(
                new AmbientLight(Color.gray(0.6)),
                makePointLight(Color.WHITE, 0.5, 200, -300, -800));
        var sceneRoot = new Group(lights, worldRoot);

        sub = new SubScene(sceneRoot, 900, 700, true, SceneAntialiasing.BALANCED);
        sub.setFill(Color.web("#FAFAFA"));
        rig = new CameraRig();
        sub.setCamera(rig.camera());
        rig.install(sub);

        // ── Top control bar: two text fields + render button ──
        var upperField = new TextField(DEFAULT_UPPER);
        upperField.setPromptText("上联 (upper line)");
        upperField.setPrefColumnCount(14);
        upperField.setStyle("-fx-font-size: 14;");

        var lowerField = new TextField(DEFAULT_LOWER);
        lowerField.setPromptText("下联 (lower line)");
        lowerField.setPrefColumnCount(14);
        lowerField.setStyle("-fx-font-size: 14;");

        var renderBtn = new Button("Render");
        renderBtn.setDefaultButton(true);
        Runnable doRender = () -> renderCouplet(upperField.getText(), lowerField.getText());
        renderBtn.setOnAction(e -> doRender.run());
        upperField.setOnAction(e -> doRender.run());
        lowerField.setOnAction(e -> doRender.run());

        // ── Elasticity slider: low = rigid springs, high = stretchy ──
        // Slider value u ∈ [0, 100] maps logarithmically to a stiffness
        // multiplier in [10, 0.1] — symmetric around the default at u=50.
        var elasticitySlider = new Slider(0, 100, 50);
        elasticitySlider.setPrefWidth(160);
        elasticitySlider.setShowTickMarks(true);
        elasticitySlider.setMajorTickUnit(25);
        var elasticityValue = new Label("1.0×");
        elasticityValue.setStyle("-fx-font-size: 11; -fx-text-fill: #444; -fx-min-width: 44;");
        elasticitySlider.valueProperty().addListener((obs, old, val) -> {
            double u = val.doubleValue();
            double mul = Math.pow(10.0, (50.0 - u) / 50.0);   // 10× at 0, 1× at 50, 0.1× at 100
            elasticityValue.setText(String.format("%.2f×", mul));
            if (playground != null) playground.setStiffnessMultiplier(mul);
        });
        var elasticityLabel = new Label("Elasticity");
        elasticityLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #444;");

        // ── Texture picker — re-renders to repaint glyph fills ──
        var textureChoice = new ChoiceBox<String>();
        textureChoice.getItems().addAll(GlyphTextures.ALL);
        textureChoice.setValue(currentTexture);
        textureChoice.valueProperty().addListener((obs, old, val) -> {
            if (val == null || val.equals(currentTexture)) return;
            currentTexture = val;
            renderCouplet(upperField.getText(), lowerField.getText());
        });
        var textureLabel = new Label("Texture");
        textureLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #444;");

        // ── Font picker — also re-renders ──
        var fontChoice = new ChoiceBox<String>();
        fontChoice.getItems().addAll(GlyphFonts.available());
        fontChoice.setValue(currentFont);
        fontChoice.valueProperty().addListener((obs, old, val) -> {
            if (val == null || val.equals(currentFont)) return;
            currentFont = val;
            renderCouplet(upperField.getText(), lowerField.getText());
        });
        var fontLabel = new Label("Font");
        fontLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #444;");

        var hint = new Label("drag a character to play · drag empty space to orbit · scroll to zoom");
        hint.setStyle("-fx-font-size: 11; -fx-text-fill: #888;");

        var bar = new HBox(10,
                new Label("上联"), upperField,
                new Label("下联"), lowerField,
                renderBtn,
                elasticityLabel, elasticitySlider, elasticityValue,
                textureLabel, textureChoice,
                fontLabel, fontChoice,
                hint);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(10, 14, 10, 14));
        bar.setStyle("-fx-background-color: rgba(250,250,250,0.92);"
                + " -fx-border-color: #DDD; -fx-border-width: 0 0 1 0;");
        bar.setPickOnBounds(false);

        // ── Stack: 3D viewport + floating top bar ──
        var subHolder = new StackPane(sub);
        sub.widthProperty().bind(subHolder.widthProperty());
        sub.heightProperty().bind(subHolder.heightProperty());

        var topAnchor = new VBox(bar);
        topAnchor.setPickOnBounds(false);

        var root = new StackPane(subHolder, topAnchor);
        StackPane.setAlignment(topAnchor, Pos.TOP_CENTER);

        var scene = new Scene(root, 1100, 760);
        stage.setTitle("Kranji — 3D Couplet Playground");
        stage.setScene(scene);
        stage.show();

        // Initial render.
        doRender.run();
    }

    private void renderCouplet(String upper, String lower) {
        if (playground != null) { playground.stop(); playground = null; }
        worldRoot.getChildren().clear();
        rig.resetHeadOn();

        var graph = CoupletGraph.build(
                upper == null ? "" : upper,
                lower == null ? "" : lower);
        if (graph.nodes().isEmpty()) return;

        var glyphPaint = GlyphTextures.forName(currentTexture, GLYPH_COLOR);
        playground = new LiveSpringMode<>(sub, worldRoot,
                graph.nodes(), graph.edges(), graph.anchors(),
                graph.initialPositions(),
                Position::character,
                glyphPaint, SPRING_COLOR,
                COUPLET_K_DISTANCE, currentFont);
        playground.start();
    }

    /** ~1/3 of the previous tight value — characters sit close together. */
    private static final double COUPLET_K_DISTANCE = 25;

    private static PointLight makePointLight(Color c, double i, double x, double y, double z) {
        var l = new PointLight(c.deriveColor(0, 1, i, 1));
        l.setTranslateX(x); l.setTranslateY(y); l.setTranslateZ(z);
        return l;
    }
}
