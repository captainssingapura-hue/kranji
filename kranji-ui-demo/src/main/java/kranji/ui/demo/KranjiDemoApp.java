package kranji.ui.demo;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import kranji.classification.CharacterComposition;
import kranji.classification.CharacterComposition.*;
import kranji.classification.EtymologicalCategory;
import kranji.classification.StructuralNode;
import kranji.classification.EtymologicalCategory.*;
import kranji.component.BasicComponent;
import kranji.component.Component;
import kranji.component.Component.Part;
import kranji.component.Component.Zi;
import kranji.characters.Characters;
import kranji.demos.ExampleCharacters;
import kranji.entry.ChineseCharacterEntry;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tone;
import kranji.layout.BlockSvgRenderer;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class KranjiDemoApp extends Application {

    private FilteredList<ChineseCharacterEntry> filteredList;

    private ComboBox<String> initialFilter;
    private ComboBox<String> toneFilter;
    private ComboBox<String> compositionFilter;
    private ComboBox<String> etymologyFilter;

    @Override
    public void start(Stage stage) {
        // Wrap data in a FilteredList for dynamic filtering
        filteredList = new FilteredList<>(
                FXCollections.observableArrayList(Characters.ALL), e -> true);

        var table = createTable(filteredList);

        // ── Filter bar ──────────────────────────────────────────────────
        var filterBar = createFilterBar();

        // Table + filter bar on the left
        var leftPane = new VBox(filterBar, table);
        VBox.setVgrow(table, Priority.ALWAYS);

        // Detail panel (text-based)
        var detailScroll = new ScrollPane();
        detailScroll.setFitToWidth(true);
        var detailBox = new VBox();
        detailBox.setPadding(new Insets(20));
        detailBox.setSpacing(16);
        detailScroll.setContent(detailBox);

        // SVG WebView panel
        var svgWebView = new WebView();
        svgWebView.setMinWidth(200);
        svgWebView.setMinHeight(200);
        svgWebView.getEngine().loadContent(wrapSvgHtml(""), "text/html");

        // Block toggle checkbox
        var blockToggle = new CheckBox("Show blocks");
        blockToggle.setSelected(true);
        blockToggle.setStyle("-fx-font-size: 12; -fx-text-fill: #555;");
        blockToggle.selectedProperty().addListener((obs, old, show) -> {
            var val = show ? "inline" : "none";
            svgWebView.getEngine().executeScript(
                    "document.querySelector('svg').style.setProperty('--block-display','" + val + "')");
        });

        var svgPane = new VBox(6, blockToggle, svgWebView);
        svgPane.setPadding(new Insets(6, 6, 0, 6));
        VBox.setVgrow(svgWebView, Priority.ALWAYS);

        // Vertical split: detail text on top, SVG WebView on bottom
        var rightSplit = new SplitPane(detailScroll, svgPane);
        rightSplit.setOrientation(Orientation.VERTICAL);
        rightSplit.setDividerPositions(0.42);

        table.getSelectionModel().selectedItemProperty().addListener((obs, old, entry) -> {
            if (entry != null) {
                renderDetail(detailBox, entry);
                var svgContent = BlockSvgRenderer.render(entry);
                var showBlocks = blockToggle.isSelected();
                svgWebView.getEngine().loadContent(
                        wrapSvgHtml(svgContent, showBlocks), "text/html");
            }
        });

        var split = new SplitPane(leftPane, rightSplit);
        split.setDividerPositions(0.32);

        var scene = new Scene(split, 1150, 780);
        scene.getRoot().setStyle("-fx-font-family: 'Microsoft YaHei', 'PingFang SC', 'Noto Sans CJK SC', sans-serif;");

        stage.setTitle("Kranji \u2014 Chinese Character Explorer");
        stage.setScene(scene);
        stage.show();

        table.getSelectionModel().selectFirst();
    }

    // ── Filter bar ─────────────────────────────────────────────────────

    private HBox createFilterBar() {
        initialFilter = new ComboBox<>();
        initialFilter.getItems().add("All Initials");
        for (Initial i : Initial.values()) {
            var label = i == Initial.ZERO ? "\u2205 (zero)" : i.pinyin();
            initialFilter.getItems().add(label);
        }
        initialFilter.setValue("All Initials");
        initialFilter.setOnAction(e -> applyFilters());

        toneFilter = new ComboBox<>();
        toneFilter.getItems().add("All Tones");
        for (Tone t : Tone.values()) {
            toneFilter.getItems().add(t.diacritic() + " " + t.chinese());
        }
        toneFilter.setValue("All Tones");
        toneFilter.setOnAction(e -> applyFilters());

        compositionFilter = new ComboBox<>();
        compositionFilter.getItems().addAll(
                "All Structures", "Singular", "Left-Right", "Top-Bottom",
                "L-M-R", "T-M-B", "Full Encl.", "Semi-Enclosure", "Mosaic");
        compositionFilter.setValue("All Structures");
        compositionFilter.setOnAction(e -> applyFilters());

        etymologyFilter = new ComboBox<>();
        etymologyFilter.getItems().addAll(
                "All Etymology", "Pictograph", "Indicative", "Compound",
                "Phono-Sem.", "Cognate", "Loan");
        etymologyFilter.setValue("All Etymology");
        etymologyFilter.setOnAction(e -> applyFilters());

        var style = "-fx-font-size: 11;";
        initialFilter.setStyle(style);
        toneFilter.setStyle(style);
        compositionFilter.setStyle(style);
        etymologyFilter.setStyle(style);

        var countLabel = new Label(filteredList.size() + " chars");
        countLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #888;");
        filteredList.predicateProperty().addListener((obs, oldP, newP) ->
                countLabel.setText(filteredList.size() + " chars"));

        var bar = new HBox(6,
                initialFilter, toneFilter, compositionFilter, etymologyFilter, countLabel);
        bar.setPadding(new Insets(6, 6, 4, 6));
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ddd; -fx-border-width: 0 0 1 0;");
        return bar;
    }

    private void applyFilters() {
        Predicate<ChineseCharacterEntry> predicate = e -> true;

        // Initial filter
        var selInitial = initialFilter.getValue();
        if (selInitial != null && !selInitial.equals("All Initials")) {
            predicate = predicate.and(e -> e.pinyin().stream().anyMatch(py -> {
                if (selInitial.startsWith("\u2205")) return py.initial() == Initial.ZERO;
                return py.initial().pinyin().equals(selInitial);
            }));
        }

        // Tone filter
        var selTone = toneFilter.getValue();
        if (selTone != null && !selTone.equals("All Tones")) {
            predicate = predicate.and(e -> e.pinyin().stream().anyMatch(py -> {
                var label = py.tone().diacritic() + " " + py.tone().chinese();
                return label.equals(selTone);
            }));
        }

        // Composition filter
        var selComp = compositionFilter.getValue();
        if (selComp != null && !selComp.equals("All Structures")) {
            predicate = predicate.and(e -> matchesCompositionFilter(e.composition(), selComp));
        }

        // Etymology filter
        var selEtym = etymologyFilter.getValue();
        if (selEtym != null && !selEtym.equals("All Etymology")) {
            predicate = predicate.and(e -> etymologyLabel(e.etymology()).equals(selEtym));
        }

        filteredList.setPredicate(predicate);
    }

    private static boolean matchesCompositionFilter(CharacterComposition comp, String filter) {
        return switch (filter) {
            case "Singular" -> comp instanceof Singular;
            case "Left-Right" -> comp instanceof LeftRight;
            case "Top-Bottom" -> comp instanceof TopBottom;
            case "L-M-R" -> comp instanceof LeftMiddleRight;
            case "T-M-B" -> comp instanceof TopMiddleBottom;
            case "Full Encl." -> comp instanceof FullEnclosure;
            case "Semi-Enclosure" -> comp instanceof SemiEnclosureUpperLeft
                    || comp instanceof SemiEnclosureUpperRight
                    || comp instanceof SemiEnclosureBottomLeft
                    || comp instanceof SemiEnclosureTopThree
                    || comp instanceof SemiEnclosureBottomThree
                    || comp instanceof SemiEnclosureLeftThree;
            case "Mosaic" -> comp instanceof Mosaic;
            default -> true;
        };
    }

    /**
     * Wraps raw SVG content in an HTML page that centers it with a light background.
     */
    private static String wrapSvgHtml(String svgContent) {
        return wrapSvgHtml(svgContent, true);
    }

    private static String wrapSvgHtml(String svgContent, boolean showBlocks) {
        var blockDisplay = showBlocks ? "inline" : "none";
        return """
                <!DOCTYPE html>
                <html>
                <head><style>
                  body {
                    margin: 0; padding: 16px;
                    display: flex; justify-content: center; align-items: flex-start;
                    background: #fafafa;
                    font-family: 'Microsoft YaHei', 'PingFang SC', sans-serif;
                  }
                  svg { max-width: 100%%; height: auto; --block-display: %s; }
                </style></head>
                <body>%s</body>
                </html>
                """.formatted(blockDisplay, svgContent);
    }

    // ── Table ───────────────────────────────────────────────────────────

    private TableView<ChineseCharacterEntry> createTable(FilteredList<ChineseCharacterEntry> items) {
        var table = new TableView<ChineseCharacterEntry>();
        table.setMinWidth(360);

        var colGlyph = new TableColumn<ChineseCharacterEntry, String>("字");
        colGlyph.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().character()));
        colGlyph.setPrefWidth(50);
        colGlyph.setStyle("-fx-alignment: CENTER; -fx-font-size: 18;");

        var colPinyin = new TableColumn<ChineseCharacterEntry, String>("Pinyin");
        colPinyin.setCellValueFactory(c -> new ReadOnlyStringWrapper(formatPinyin(c.getValue())));
        colPinyin.setPrefWidth(80);

        var colStrokes = new TableColumn<ChineseCharacterEntry, String>("笔画");
        colStrokes.setCellValueFactory(c -> new ReadOnlyStringWrapper(String.valueOf(c.getValue().strokes())));
        colStrokes.setPrefWidth(45);
        colStrokes.setStyle("-fx-alignment: CENTER;");

        var colComp = new TableColumn<ChineseCharacterEntry, String>("Structure");
        colComp.setCellValueFactory(c -> new ReadOnlyStringWrapper(compositionLabel(c.getValue().composition())));
        colComp.setPrefWidth(110);

        var colEtym = new TableColumn<ChineseCharacterEntry, String>("Etymology");
        colEtym.setCellValueFactory(c -> new ReadOnlyStringWrapper(etymologyLabel(c.getValue().etymology())));
        colEtym.setPrefWidth(115);

        table.getColumns().addAll(colGlyph, colPinyin, colStrokes, colComp, colEtym);
        table.setItems(items);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        return table;
    }

    // ── Detail panel ────────────────────────────────────────────────────

    private void renderDetail(VBox box, ChineseCharacterEntry e) {
        box.getChildren().clear();

        // ── Header: large glyph + basic info ────────────────────────────
        var glyphText = new Text(e.character());
        glyphText.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 96));

        var pinyinLabel = styledLabel(formatPinyin(e), 20);
        var codeLabel = styledLabel(e.codepoint(), 13);
        codeLabel.setStyle(codeLabel.getStyle() + " -fx-text-fill: #888;");

        var headerInfo = new VBox(4, pinyinLabel, codeLabel);
        headerInfo.setAlignment(Pos.CENTER_LEFT);

        var header = new HBox(20, glyphText, headerInfo);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 8, 0));

        // ── Metadata row ────────────────────────────────────────────────
        var metaLabel = styledLabel(
                e.strokes() + " strokes  \u00b7  Kangxi radical " + e.radicalNo(),
                13
        );
        metaLabel.setStyle(metaLabel.getStyle() + " -fx-text-fill: #666;");

        // ── Composition section ─────────────────────────────────────────
        var compSection = section("COMPOSITION \u2014 " + compositionLabelChinese(e.composition()),
                renderComposition(e.composition()));

        // ── Etymology section ───────────────────────────────────────────
        var etymSection = section("ETYMOLOGY \u2014 " + etymologyLabelChinese(e.etymology()),
                renderEtymology(e.etymology()));

        // ── Pinyin decomposition ────────────────────────────────────────
        var pySection = section("PINYIN DECOMPOSITION", renderPinyinDetail(e));

        box.getChildren().addAll(header, metaLabel, separator(), compSection, etymSection, pySection);
    }

    // ── Composition rendering ───────────────────────────────────────────

    private VBox renderComposition(CharacterComposition comp) {
        var box = new VBox(4);
        switch (comp) {
            case Singular s ->
                    box.getChildren().add(fieldLabel("No sub-components \u2014 single indivisible unit"));
            case LeftRight lr -> {
                box.getChildren().add(fieldLabel("Left:   " + renderNode(lr.left())));
                box.getChildren().add(fieldLabel("Right:  " + renderNode(lr.right())));
            }
            case TopBottom tb -> {
                box.getChildren().add(fieldLabel("Top:    " + renderNode(tb.top())));
                box.getChildren().add(fieldLabel("Bottom: " + renderNode(tb.bottom())));
            }
            case LeftMiddleRight lmr -> {
                box.getChildren().add(fieldLabel("Left:   " + renderNode(lmr.left())));
                box.getChildren().add(fieldLabel("Middle: " + renderNode(lmr.middle())));
                box.getChildren().add(fieldLabel("Right:  " + renderNode(lmr.right())));
            }
            case TopMiddleBottom tmb -> {
                box.getChildren().add(fieldLabel("Top:    " + renderNode(tmb.top())));
                box.getChildren().add(fieldLabel("Middle: " + renderNode(tmb.middle())));
                box.getChildren().add(fieldLabel("Bottom: " + renderNode(tmb.bottom())));
            }
            case FullEnclosure fe -> {
                box.getChildren().add(fieldLabel("Outer:  " + renderNode(fe.outer())));
                box.getChildren().add(fieldLabel("Inner:  " + renderNode(fe.inner())));
            }
            case SemiEnclosureUpperLeft se -> renderWrapperContent(box, se.wrapper(), se.content());
            case SemiEnclosureUpperRight se -> renderWrapperContent(box, se.wrapper(), se.content());
            case SemiEnclosureBottomLeft se -> renderWrapperContent(box, se.wrapper(), se.content());
            case SemiEnclosureTopThree se -> renderWrapperContent(box, se.wrapper(), se.content());
            case SemiEnclosureBottomThree se -> renderWrapperContent(box, se.wrapper(), se.content());
            case SemiEnclosureLeftThree se -> renderWrapperContent(box, se.wrapper(), se.content());
            case Mosaic m ->
                    box.getChildren().add(fieldLabel("Element: " + renderNode(m.element()) + "  \u00d7 3"));
        }
        return box;
    }

    private void renderWrapperContent(VBox box, StructuralNode wrapper, StructuralNode content) {
        box.getChildren().add(fieldLabel("Wrapper: " + renderNode(wrapper)));
        box.getChildren().add(fieldLabel("Content: " + renderNode(content)));
    }

    // ── Etymology rendering ─────────────────────────────────────────────

    private VBox renderEtymology(EtymologicalCategory etym) {
        var box = new VBox(4);
        switch (etym) {
            case Pictograph p ->
                    box.getChildren().add(fieldLabel("Stylized drawing of the object it represents"));
            case SimpleIndicative si ->
                    box.getChildren().add(fieldLabel(si.indicatorDescription()));
            case CompoundIndicative ci ->
                    box.getChildren().add(fieldLabel(ci.meaningHint()));
            case PhonoSemantic ps -> {
                box.getChildren().add(fieldLabel("Semantic (\u5f62\u65c1): " + renderNode(ps.semanticPart())
                        + "  \u2192  hints at meaning"));
                box.getChildren().add(fieldLabel("Phonetic (\u58f0\u65c1): " + renderNode(ps.phoneticPart())
                        + "  \u2192  hints at sound"));
            }
            case DerivativeCognate dc ->
                    box.getChildren().add(fieldLabel("Cognate: " + dc.cognateGlyph()));
            case PhoneticLoan pl ->
                    box.getChildren().add(fieldLabel("Originally meant: " + pl.originalMeaning()));
        }
        return box;
    }

    // ── Pinyin detail ───────────────────────────────────────────────────

    private VBox renderPinyinDetail(ChineseCharacterEntry e) {
        var box = new VBox(4);
        for (var py : e.pinyin()) {
            var initial = py.initial().pinyin().isEmpty() ? "\u2205 (zero)" : py.initial().pinyin();
            var fin = py.fin().spelling();
            var tone = py.tone().diacritic() + " " + py.tone().chinese() + " (tone " + py.tone().number() + ")";
            box.getChildren().add(fieldLabel("Syllable:  " + py.numberedTone()));
            box.getChildren().add(fieldLabel("Initial:   " + initial
                    + "  [" + py.initial().placeOfArticulation() + "]"));
            box.getChildren().add(fieldLabel("Final:     " + fin
                    + "  [head=" + py.fin().head().chinese()
                    + "  body=" + py.fin().body().symbol()
                    + "  tail=" + py.fin().tail().symbol() + "]"));
            box.getChildren().add(fieldLabel("Tone:      " + tone));
        }
        return box;
    }

    // ── StructuralNode → display string ───────────────────────────────────

    private static String renderNode(StructuralNode node) {
        return switch (node) {
            case BasicComponent bc -> bc.glyph() + "  " + bc.name() + " \u2014 " + bc.meaning()
                    + " (\u2190 " + bc.standalone() + " " + bc.pinyin() + ")";
            case Part p -> p.glyph() + "  " + p.name() + " \u2014 " + p.meaning()
                    + " (\u2190 " + p.standalone() + " " + p.pinyin() + ")";
            case Zi z -> z.glyph();
            case CharacterComposition comp -> "[" + compositionLabel(comp) + "]";
            default -> node.toString();
        };
    }

    // ── Label helpers ───────────────────────────────────────────────────

    private static String compositionLabel(CharacterComposition comp) {
        return switch (comp) {
            case Singular s -> "Singular";
            case LeftRight lr -> "Left-Right";
            case TopBottom tb -> "Top-Bottom";
            case LeftMiddleRight lmr -> "L-M-R";
            case TopMiddleBottom tmb -> "T-M-B";
            case FullEnclosure fe -> "Full Encl.";
            case SemiEnclosureUpperLeft se -> "Semi UL";
            case SemiEnclosureUpperRight se -> "Semi UR";
            case SemiEnclosureBottomLeft se -> "Semi BL";
            case SemiEnclosureTopThree se -> "Semi Top3";
            case SemiEnclosureBottomThree se -> "Semi Bot3";
            case SemiEnclosureLeftThree se -> "Semi Left3";
            case Mosaic m -> "Mosaic";
        };
    }

    private static String compositionLabelChinese(CharacterComposition comp) {
        return switch (comp) {
            case Singular s -> "\u72ec\u4f53\u5b57";
            case LeftRight lr -> "\u5de6\u53f3\u7ed3\u6784";
            case TopBottom tb -> "\u4e0a\u4e0b\u7ed3\u6784";
            case LeftMiddleRight lmr -> "\u5de6\u4e2d\u53f3\u7ed3\u6784";
            case TopMiddleBottom tmb -> "\u4e0a\u4e2d\u4e0b\u7ed3\u6784";
            case FullEnclosure fe -> "\u5168\u5305\u56f4";
            case SemiEnclosureUpperLeft se -> "\u5de6\u4e0a\u5305\u56f4";
            case SemiEnclosureUpperRight se -> "\u53f3\u4e0a\u5305\u56f4";
            case SemiEnclosureBottomLeft se -> "\u5de6\u4e0b\u5305\u56f4";
            case SemiEnclosureTopThree se -> "\u4e0a\u4e09\u5305\u56f4";
            case SemiEnclosureBottomThree se -> "\u4e0b\u4e09\u5305\u56f4";
            case SemiEnclosureLeftThree se -> "\u5de6\u4e09\u5305\u56f4";
            case Mosaic m -> "\u54c1\u5b57\u7ed3\u6784";
        };
    }

    private static String etymologyLabel(EtymologicalCategory etym) {
        return switch (etym) {
            case Pictograph p -> "Pictograph";
            case SimpleIndicative si -> "Indicative";
            case CompoundIndicative ci -> "Compound";
            case PhonoSemantic ps -> "Phono-Sem.";
            case DerivativeCognate dc -> "Cognate";
            case PhoneticLoan pl -> "Loan";
        };
    }

    private static String etymologyLabelChinese(EtymologicalCategory etym) {
        return switch (etym) {
            case Pictograph p -> "\u8c61\u5f62";
            case SimpleIndicative si -> "\u6307\u4e8b";
            case CompoundIndicative ci -> "\u4f1a\u610f";
            case PhonoSemantic ps -> "\u5f62\u58f0";
            case DerivativeCognate dc -> "\u8f6c\u6ce8";
            case PhoneticLoan pl -> "\u5047\u501f";
        };
    }

    private static String formatPinyin(ChineseCharacterEntry e) {
        return e.pinyin().stream()
                .map(PinyinSyllable::numberedTone)
                .collect(Collectors.joining(", "));
    }

    // ── UI building blocks ──────────────────────────────────────────────

    private static VBox section(String title, VBox content) {
        var titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        titleLabel.setStyle("-fx-text-fill: #444;");

        content.setPadding(new Insets(6, 0, 0, 12));

        var section = new VBox(4, titleLabel, content);
        section.setPadding(new Insets(4, 0, 4, 0));
        return section;
    }

    private static Label fieldLabel(String text) {
        var label = new Label(text);
        label.setFont(Font.font("Consolas", 13));
        label.setWrapText(true);
        return label;
    }

    private static Label styledLabel(String text, double size) {
        var label = new Label(text);
        label.setFont(Font.font(size));
        label.setStyle("-fx-font-size: " + size + ";");
        return label;
    }

    private static Separator separator() {
        return new Separator();
    }
}
