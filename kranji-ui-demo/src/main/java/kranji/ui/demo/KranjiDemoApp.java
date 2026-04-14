package kranji.ui.demo;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import kranji.classification.EtymologicalCategory;
import kranji.classification.EtymologicalCategory.*;
import kranji.zi.*;
import kranji.zi.ComposedBlock.*;
import kranji.demos.ExampleCharacters;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tone;
import kranji.layout.BlockSvgRenderer;
import kranji.library.BasicSet;
import kranji.library.ZiLibrary;
import kranji.singular.SingularFamilies;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class KranjiDemoApp extends Application {

    private ObservableList<Zi> backingList;
    private FilteredList<Zi> filteredList;

    private TextField searchField;
    private ComboBox<String> sourceFilter;
    private ComboBox<String> initialFilter;
    private ComboBox<String> toneFilter;
    private ComboBox<String> compositionFilter;
    private ComboBox<String> etymologyFilter;

    @Override
    public void start(Stage stage) {
        // Load component libraries
        SingularFamilies.registerInto(BasicSet.INSTANCE);
        ZiLibrary.load(BasicSet.INSTANCE);

        // Wrap data in a FilteredList for dynamic filtering — start with curated set
        backingList = FXCollections.observableArrayList(ExampleCharacters.ALL);
        filteredList = new FilteredList<>(backingList, e -> true);

        var table = createTable(filteredList);

        // ── Filter bar ──────────────────────────────────────────────────
        var filterBar = createFilterBar();

        // Table + filter bar on the left
        var leftPane = new VBox(filterBar, table);
        VBox.setVgrow(table, Priority.ALWAYS);

        // Detail panel — left side (text info)
        var detailScroll = new ScrollPane();
        detailScroll.setFitToWidth(true);
        var detailBox = new VBox();
        detailBox.setPadding(new Insets(20));
        detailBox.setSpacing(16);
        detailScroll.setContent(detailBox);

        // Detail panel — right side (composition tree)
        var treeBox = new VBox();
        treeBox.setPadding(new Insets(20));
        treeBox.setSpacing(8);

        // Detail split: text info left, composition tree right
        var detailSplit = new SplitPane(detailScroll, treeBox);
        detailSplit.setDividerPositions(0.55);

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

        // Vertical split: detail split on top, SVG WebView on bottom
        var rightSplit = new SplitPane(detailSplit, svgPane);
        rightSplit.setOrientation(Orientation.VERTICAL);
        rightSplit.setDividerPositions(0.42);

        table.getSelectionModel().selectedItemProperty().addListener((obs, old, entry) -> {
            if (entry != null) {
                renderDetail(detailBox, treeBox, entry);
                var svgContent = BlockSvgRenderer.render(entry);
                var showBlocks = blockToggle.isSelected();
                svgWebView.getEngine().loadContent(
                        wrapSvgHtml(svgContent, showBlocks), "text/html");
            }
        });

        var split = new SplitPane(leftPane, rightSplit);
        split.setDividerPositions(0.32);

        var scene = new Scene(split, 1150, 780);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scene.getRoot().setStyle("-fx-font-family: 'Microsoft YaHei', 'PingFang SC', 'Noto Sans CJK SC', sans-serif;");

        stage.setTitle("Kranji \u2014 Chinese Character Explorer");
        stage.setScene(scene);
        stage.show();

        table.getSelectionModel().selectFirst();
    }

    // ── Filter bar ─────────────────────────────────────────────────────

    private VBox createFilterBar() {
        // ── Row 1: search + source selector ────────────────────────────
        searchField = new TextField();
        searchField.setPromptText("Search character, pinyin, component...");
        searchField.setPrefWidth(200);
        searchField.textProperty().addListener((obs, old, text) -> applyFilters());
        HBox.setHgrow(searchField, Priority.ALWAYS);

        sourceFilter = new ComboBox<>();
        sourceFilter.getItems().addAll("Curated Examples");
        sourceFilter.setValue("Curated Examples");

        var countLabel = new Label(filteredList.size() + " chars");
        countLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #888;");
        filteredList.predicateProperty().addListener((obs, oldP, newP) ->
                countLabel.setText(filteredList.size() + " chars"));

        var searchStyle = "-fx-font-size: 12;";
        searchField.setStyle(searchStyle);
        sourceFilter.setStyle("-fx-font-size: 11;");

        var topRow = new HBox(6, searchField, sourceFilter, countLabel);
        topRow.setAlignment(Pos.CENTER_LEFT);

        // ── Row 2: dropdown filters ────────────────────────────────────
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
                "L-M-R", "T-M-B", "Full Encl.", "Semi-Enclosure");
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

        var bottomRow = new HBox(6,
                initialFilter, toneFilter, compositionFilter, etymologyFilter);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        var bar = new VBox(4, topRow, bottomRow);
        bar.setPadding(new Insets(6, 6, 4, 6));
        bar.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ddd; -fx-border-width: 0 0 1 0;");
        return bar;
    }

    private void applyFilters() {
        Predicate<Zi> predicate = e -> true;

        // Search filter
        var query = searchField.getText();
        if (query != null && !query.isBlank()) {
            var q = query.strip().toLowerCase();
            predicate = predicate.and(e ->
                    e.character().contains(q)
                    || formatPinyin(e).toLowerCase().contains(q)
                    || glyphsOf(e.structure()).toLowerCase().contains(q)
            );
        }

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
            predicate = predicate.and(e -> matchesCompositionFilter(e.structure(), selComp));
        }

        // Etymology filter
        var selEtym = etymologyFilter.getValue();
        if (selEtym != null && !selEtym.equals("All Etymology")) {
            predicate = predicate.and(e -> etymologyLabel(e.etymology()).equals(selEtym));
        }

        filteredList.setPredicate(predicate);
    }

    private static boolean matchesCompositionFilter(BlockStructure structure, String filter) {
        return switch (filter) {
            case "Singular" -> structure instanceof SingularBlock;
            case "Left-Right" -> structure instanceof LeftRight;
            case "Top-Bottom" -> structure instanceof TopBottom;
            case "L-M-R" -> structure instanceof LeftMiddleRight;
            case "T-M-B" -> structure instanceof TopMiddleBottom;
            case "Full Encl." -> structure instanceof FullEnclosure;
            case "Semi-Enclosure" -> structure instanceof SemiEnclosureUpperLeft
                    || structure instanceof SemiEnclosureUpperRight
                    || structure instanceof SemiEnclosureBottomLeft
                    || structure instanceof SemiEnclosureTopThree
                    || structure instanceof SemiEnclosureBottomThree
                    || structure instanceof SemiEnclosureLeftThree;
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

    private TableView<Zi> createTable(FilteredList<Zi> items) {
        var table = new TableView<Zi>();
        table.setMinWidth(360);

        var colGlyph = new TableColumn<Zi, String>("字");
        colGlyph.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().character()));
        colGlyph.setPrefWidth(50);
        colGlyph.setStyle("-fx-alignment: CENTER; -fx-font-size: 18;");

        var colPinyin = new TableColumn<Zi, String>("Pinyin");
        colPinyin.setCellValueFactory(c -> new ReadOnlyStringWrapper(formatPinyin(c.getValue())));
        colPinyin.setPrefWidth(80);

        var colStrokes = new TableColumn<Zi, String>("笔画");
        colStrokes.setCellValueFactory(c -> new ReadOnlyStringWrapper(String.valueOf(c.getValue().strokes())));
        colStrokes.setPrefWidth(45);
        colStrokes.setStyle("-fx-alignment: CENTER;");

        var colComp = new TableColumn<Zi, String>("Structure");
        colComp.setCellValueFactory(c -> new ReadOnlyStringWrapper(compositionLabel(c.getValue().structure())));
        colComp.setPrefWidth(110);

        var colEtym = new TableColumn<Zi, String>("Etymology");
        colEtym.setCellValueFactory(c -> new ReadOnlyStringWrapper(etymologyLabel(c.getValue().etymology())));
        colEtym.setPrefWidth(115);

        table.getColumns().addAll(colGlyph, colPinyin, colStrokes, colComp, colEtym);
        table.setItems(items);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        return table;
    }

    // ── Detail panel ────────────────────────────────────────────────────

    private void renderDetail(VBox box, VBox treeBox, Zi e) {
        box.getChildren().clear();
        treeBox.getChildren().clear();

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

        // ── Etymology section ───────────────────────────────────────────
        var etymSection = section("ETYMOLOGY \u2014 " + etymologyLabelChinese(e.etymology()),
                renderEtymology(e.etymology()));

        // ── Pinyin decomposition ────────────────────────────────────────
        var pySection = section("PINYIN DECOMPOSITION", renderPinyinDetail(e));

        box.getChildren().addAll(header, metaLabel, separator(), etymSection, pySection);

        // ── Right side: composition tree ────────────────────────────────
        var compTitle = new Label("COMPOSITION \u2014 " + compositionLabelChinese(e.structure()));
        compTitle.setFont(Font.font("System", FontWeight.BOLD, 13));
        compTitle.setStyle("-fx-text-fill: #444;");
        var tree = renderCompositionTree(e.structure());
        VBox.setVgrow(tree, Priority.ALWAYS);
        treeBox.getChildren().addAll(compTitle, tree);
    }

    // ── Composition rendering (TreeView) ──────────────────────────────────

    private TreeView<String> renderCompositionTree(BlockStructure structure) {
        var root = buildTreeItem(structure);
        root.setExpanded(true);
        var tree = new TreeView<>(root);
        tree.setShowRoot(true);
        return tree;
    }

    private static TreeItem<String> buildTreeItem(BlockStructure node) {
        return switch (node) {
            case ComposedBlock comp -> {
                var item = new TreeItem<>(compositionLabel(comp));
                item.setExpanded(true);
                switch (comp) {
                    case LeftRight lr -> {
                        item.getChildren().add(slotItem("Left", lr.left()));
                        item.getChildren().add(slotItem("Right", lr.right()));
                    }
                    case TopBottom tb -> {
                        item.getChildren().add(slotItem("Top", tb.top()));
                        item.getChildren().add(slotItem("Bottom", tb.bottom()));
                    }
                    case LeftMiddleRight lmr -> {
                        item.getChildren().add(slotItem("Left", lmr.left()));
                        item.getChildren().add(slotItem("Middle", lmr.middle()));
                        item.getChildren().add(slotItem("Right", lmr.right()));
                    }
                    case TopMiddleBottom tmb -> {
                        item.getChildren().add(slotItem("Top", tmb.top()));
                        item.getChildren().add(slotItem("Middle", tmb.middle()));
                        item.getChildren().add(slotItem("Bottom", tmb.bottom()));
                    }
                    case FullEnclosure fe -> {
                        item.getChildren().add(slotItem("Outer", fe.outer()));
                        item.getChildren().add(slotItem("Inner", fe.inner()));
                    }
                    case SemiEnclosureUpperLeft se -> addWrapperContentItems(item, se.wrapper(), se.content());
                    case SemiEnclosureUpperRight se -> addWrapperContentItems(item, se.wrapper(), se.content());
                    case SemiEnclosureBottomLeft se -> addWrapperContentItems(item, se.wrapper(), se.content());
                    case SemiEnclosureTopThree se -> addWrapperContentItems(item, se.wrapper(), se.content());
                    case SemiEnclosureBottomThree se -> addWrapperContentItems(item, se.wrapper(), se.content());
                    case SemiEnclosureLeftThree se -> addWrapperContentItems(item, se.wrapper(), se.content());
                }
                yield item;
            }
            default -> new TreeItem<>(singularLabel(node));
        };
    }

    private static TreeItem<String> slotItem(String role, Composable c) {
        var resolved = resolve(c);
        if (resolved instanceof ComposedBlock) {
            // Nested composed structure — recurse
            var sub = buildTreeItem(resolved);
            sub.setValue(role + ":  " + sub.getValue());
            return sub;
        }
        // Leaf node
        return new TreeItem<>(role + ":  " + singularLabel(resolved));
    }

    private static void addWrapperContentItems(TreeItem<String> parent, Composable wrapper, Composable content) {
        parent.getChildren().add(slotItem("Wrapper", wrapper));
        parent.getChildren().add(slotItem("Content", content));
    }

    private static String singularLabel(BlockStructure node) {
        if (node instanceof SingularBlock sb
                && (!sb.name().equals(sb.glyph()) || !sb.meaning().isEmpty())) {
            return sb.glyph() + "  " + sb.name() + " \u2014 " + sb.meaning();
        }
        return node.glyph();
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
                box.getChildren().add(fieldLabel("Semantic (\u5f62\u65c1): " + renderComposable(ps.semanticPart())
                        + "  \u2192  hints at meaning"));
                box.getChildren().add(fieldLabel("Phonetic (\u58f0\u65c1): " + renderComposable(ps.phoneticPart())
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

    private VBox renderPinyinDetail(Zi e) {
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

    // ── BlockStructure/Composable → display string ──────────────────────

    private static String renderComposable(Composable c) {
        return renderBlock(resolve(c));
    }

    private static String renderBlock(BlockStructure node) {
        return switch (node) {
            case ComposedBlock comp -> "[" + compositionLabel(comp) + "]";
            default -> {
                if (node instanceof SingularBlock sb
                        && (!sb.name().equals(sb.glyph()) || !sb.meaning().isEmpty())) {
                    yield sb.glyph() + "  " + sb.name() + " \u2014 " + sb.meaning()
                            + " (\u2190 " + sb.standalone() + " " + sb.pinyin() + ")";
                }
                yield node.glyph();
            }
        };
    }

    private static BlockStructure resolve(Composable c) {
        return switch (c) {
            case Composable.OfZi(var zi) -> zi.structure();
            case Composable.OfBlock(var block) -> block;
        };
    }

    // ── Label helpers ───────────────────────────────────────────────────

    private static String compositionLabel(BlockStructure structure) {
        return switch (structure) {
            case ComposedBlock comp -> switch (comp) {
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
            };
            default -> "Singular";
        };
    }

    private static String compositionLabelChinese(BlockStructure structure) {
        return switch (structure) {
            case ComposedBlock comp -> switch (comp) {
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
            };
            default -> "\u72ec\u4f53\u5b57";
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

    /** Recursively collect all glyphs from a BlockStructure tree for searching. */
    private static String glyphsOf(BlockStructure node) {
        if (node instanceof ComposedBlock comp) {
            return switch (comp) {
                case LeftRight lr -> glyphsOfC(lr.left()) + glyphsOfC(lr.right());
                case TopBottom tb -> glyphsOfC(tb.top()) + glyphsOfC(tb.bottom());
                case LeftMiddleRight lmr -> glyphsOfC(lmr.left()) + glyphsOfC(lmr.middle()) + glyphsOfC(lmr.right());
                case TopMiddleBottom tmb -> glyphsOfC(tmb.top()) + glyphsOfC(tmb.middle()) + glyphsOfC(tmb.bottom());
                case FullEnclosure fe -> glyphsOfC(fe.outer()) + glyphsOfC(fe.inner());
                case SemiEnclosureUpperLeft se -> glyphsOfC(se.wrapper()) + glyphsOfC(se.content());
                case SemiEnclosureUpperRight se -> glyphsOfC(se.wrapper()) + glyphsOfC(se.content());
                case SemiEnclosureBottomLeft se -> glyphsOfC(se.wrapper()) + glyphsOfC(se.content());
                case SemiEnclosureTopThree se -> glyphsOfC(se.wrapper()) + glyphsOfC(se.content());
                case SemiEnclosureBottomThree se -> glyphsOfC(se.wrapper()) + glyphsOfC(se.content());
                case SemiEnclosureLeftThree se -> glyphsOfC(se.wrapper()) + glyphsOfC(se.content());
            };
        }
        return node.glyph();
    }

    private static String glyphsOfC(Composable c) {
        return glyphsOf(resolve(c));
    }

    private static String formatPinyin(Zi e) {
        return e.pinyin().stream()
                .map(PinyinSyllable::numberedTone)
                .collect(Collectors.joining(", "));
    }

    // ── UI building blocks ──────────────────────────────────────────────

    private static VBox section(String title, javafx.scene.Node content) {
        var titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        titleLabel.setStyle("-fx-text-fill: #444;");

        if (content instanceof VBox vbox) {
            vbox.setPadding(new Insets(6, 0, 0, 12));
        }

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
