package kranji.ui.demo;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
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
import kranji.common.perclass.AllPerclassRecords;
import kranji.zi.*;
import kranji.zi.CompositionLayout.*;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tone;
import kranji.layout.BlockSvgRenderer;
import kranji.library.BasicSet;
import kranji.library.ZiLibrary;
import kranji.singular.SingularFamiliesPerclass;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class KranjiDemoApp extends Application {

    /**
     * Lightweight adapter presenting a {@link SingularPart} as a {@link Zi}
     * for display in the character table. Parts are radical variants (偏旁)
     * that don't carry full Zi metadata.
     */
    private record PartAsZi(SingularPart part) implements Zi {
        @Override public String character()             { return part.glyph(); }
        @Override public PinyinSyllable pinyin()        { return null; }
        @Override public int strokes()                  { return part.strokes(); }
        @Override public int radicalNo()                { return 0; }
        @Override public String meaning()               { return part.meaning(); }
        @Override public BlockStructure structure()      { return part; }
        @Override public EtymologicalCategory etymology() {
            return new EtymologicalCategory.Pictograph();
        }
    }

    private static final String SRC_ALL          = "All";
    private static final String SRC_SINGULAR_ZI  = "Singular Zi (\u72ec\u4f53\u5b57)";
    private static final String SRC_PARTS        = "Parts (\u504f\u65c1)";
    private static final String SRC_COMPOSED     = "Composed Examples";
    private static final String SRC_TYPED        = "Typed Per-Class (507)";

    private ObservableList<Zi> backingList;
    private FilteredList<Zi> filteredList;

    /** Cached sub-lists built once at startup. */
    private List<Zi> singularZiList;
    private List<Zi> partsList;
    private List<Zi> composedList;
    private List<Zi> typedList;
    private List<Zi> allList;

    /**
     * Depth-routing index: per-Zi {@code (depth, initial-label, final+tone-label)}
     * triple. Built from all five per-depth modules ({@link Depth1} through
     * {@link Depth5}). A Zi absent from the map is treated as "depth = unknown" —
     * true for radical parts and any singular-only records that aren't yet
     * tagged with a depth bucket.
     *
     * <p>Identity-keyed because two distinct {@code ComposedZi} instances may
     * carry equal field content but represent different records.</p>
     */
    private IdentityHashMap<Zi, RouteKey> routeIndex;
    /** Reverse map: depth → initial label → set of (final+tone) labels. */
    private TreeMap<Integer, TreeMap<String, TreeSet<String>>> routeIndexByDepth;

    /**
     * Routing tuple for a Zi. Labels are human-readable pinyin strings
     * computed directly from the syllable — see {@link #initialLabel}
     * and {@link #finalToneLabel}.
     */
    private record RouteKey(int depth, String initialLabel, String finalToneLabel) {}

    private static final String ALL_DEPTHS    = "All Depths";
    private static final String ALL_PY_INIT   = "All PY Initials";
    private static final String ALL_PY_FINAL  = "All PY Finals";

    private TextField searchField;
    private ComboBox<String> sourceFilter;
    private ComboBox<String> initialFilter;
    private ComboBox<String> toneFilter;
    private ComboBox<String> compositionFilter;
    private ComboBox<String> etymologyFilter;
    // ── New: Depth → Pinyin partition cascading filters ────────────────
    private ComboBox<String> depthFilter;
    private ComboBox<String> pyInitialFilter;
    private ComboBox<String> pyFinalFilter;
    /** Suppresses cascade-rebuild listeners during programmatic resets. */
    private boolean cascadeMuted;

    @Override
    public void start(Stage stage) {
        // Load component libraries
        SingularFamiliesPerclass.registerInto(BasicSet.INSTANCE);
        ZiLibrary.load(BasicSet.INSTANCE);

        // Build categorised lists from the library
        buildLists();

        // Wrap data in a FilteredList for dynamic filtering — start with all
        backingList = FXCollections.observableArrayList(allList);
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

    // ── Data loading ──────────────────────────────────────────────────

    private void buildLists() {
        var singulars = new ArrayList<Zi>();
        var parts = new ArrayList<Zi>();

        for (var member : BasicSet.INSTANCE.components()) {
            if (member instanceof SingularZi sz) {
                singulars.add(sz);
            } else if (member instanceof SingularPart sp) {
                parts.add(new PartAsZi(sp));
            }
        }

        // Sort by stroke count, then by glyph codepoint
        Comparator<Zi> byStrokes = Comparator.comparingInt(Zi::strokes)
                .thenComparing(z -> z.character().codePointAt(0));
        singulars.sort(byStrokes);
        parts.sort(byStrokes);

        singularZiList = List.copyOf(singulars);
        partsList = List.copyOf(parts);

        // Composed records: the typed per-class registry is now the single
        // source of truth (kranji-common-perclass). Each record is both a
        // ComposedZiT (typed layout) and a Zi, so it plugs into the UI
        // with no adaptation. Depth is derived structurally from the
        // block tree via {@link BlockStructures#depthOf(BlockStructure)},
        // which keeps the Depth → Pinyin cascade filter working without
        // needing pre-partitioned per-depth modules.
        var composed = new ArrayList<Zi>(AllPerclassRecords.ALL);
        composed.sort(byStrokes);
        composedList = List.copyOf(composed);
        // "Typed" source is an alias onto the same data today — retained
        // as a separate entry so users can still select it explicitly
        // when they want to be sure they're seeing the typed form.
        typedList = composedList;

        var all = new ArrayList<Zi>();
        all.addAll(singularZiList);
        all.addAll(partsList);
        all.addAll(composedList);
        allList = List.copyOf(all);

        // Build the depth-routing index (used by the Depth → Pinyin filter group).
        // Each Zi carries its own PinyinSyllable, so grouping/filtering is a
        // pure function of (Initial, Final, Tone). Depth is derived from the
        // record's structure tree — no external routing table required.
        routeIndex = new IdentityHashMap<>();
        routeIndexByDepth = new TreeMap<>();
        for (var z : composedList) {
            indexRecord(BlockStructures.depthOf(z.structure()), z);
        }
    }

    private void indexRecord(int depth, Zi z) {
        var py = z.pinyin();
        var initialLabel = initialLabel(py.initial());
        var finalToneLabel = finalToneLabel(py);
        routeIndex.put(z, new RouteKey(depth, initialLabel, finalToneLabel));
        routeIndexByDepth
                .computeIfAbsent(depth, d -> new TreeMap<>())
                .computeIfAbsent(initialLabel, s -> new TreeSet<>())
                .add(finalToneLabel);
    }

    /**
     * Display label for a pinyin initial. Matches the label convention used
     * by the row-2 Initial dropdown so the cascade reads consistently with
     * the standalone filter.
     */
    private static String initialLabel(Initial i) {
        return i == Initial.ZERO ? "\u2205 (zero)" : i.pinyin();
    }

    /**
     * Display label for a (Final, Tone) pair in its numbered-pinyin form —
     * e.g. {@code "ing2"}, {@code "ai4"}, {@code "ü4"}. Zero-nucleus
     * syllables like zhi/chi/shi/ri decompose to an empty
     * {@code Final.spelling()}; they're surfaced as {@code "i<tone>"} per
     * pinyin orthography (the "i" in "zhi" is an orthographic convention
     * for the syllabic fricative).
     */
    private static String finalToneLabel(PinyinSyllable py) {
        var s = py.fin().spelling();
        if (s.isEmpty()) s = "i";
        return s + py.tone().number();
    }

    private void switchSource(String source) {
        var items = switch (source) {
            case SRC_SINGULAR_ZI -> singularZiList;
            case SRC_PARTS       -> partsList;
            case SRC_COMPOSED    -> composedList;
            case SRC_TYPED       -> typedList;
            default              -> allList;
        };
        backingList.setAll(items);
        applyFilters();
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
        sourceFilter.getItems().addAll(
                SRC_ALL, SRC_SINGULAR_ZI, SRC_PARTS, SRC_COMPOSED, SRC_TYPED);
        sourceFilter.setValue(SRC_ALL);
        sourceFilter.setOnAction(e -> switchSource(sourceFilter.getValue()));

        // Count tracks the live filtered size. Binding to Bindings.size(...)
        // covers BOTH update paths: predicate changes (applyFilters) AND
        // backing-list swaps (switchSource → backingList.setAll). A plain
        // predicateProperty listener misses the latter, which is why the
        // source dropdown used to leave the count stuck at its old value.
        var countLabel = new Label();
        countLabel.textProperty().bind(
                Bindings.size(filteredList).asString("%d chars"));
        countLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #888;");

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

        // ── Row 3: Depth → Pinyin partition cascading filters ──────────
        // Mirrors the structural depth of each record (computed via
        // BlockStructures.depthOf) plus its pinyin initial / (final+tone).
        // Picking a depth restricts to records with that nesting depth;
        // picking an initial cascades the final dropdown to only the
        // (final+tone) classes that actually exist for that (depth,
        // initial) pair.
        depthFilter = new ComboBox<>();
        depthFilter.getItems().add(ALL_DEPTHS);
        for (var d : routeIndexByDepth.keySet()) {
            depthFilter.getItems().add("Depth " + d);
        }
        depthFilter.setValue(ALL_DEPTHS);

        pyInitialFilter = new ComboBox<>();
        pyInitialFilter.getItems().add(ALL_PY_INIT);
        pyInitialFilter.setValue(ALL_PY_INIT);

        pyFinalFilter = new ComboBox<>();
        pyFinalFilter.getItems().add(ALL_PY_FINAL);
        pyFinalFilter.setValue(ALL_PY_FINAL);

        depthFilter.setOnAction(e -> {
            if (cascadeMuted) return;
            rebuildPyInitialOptions();
            rebuildPyFinalOptions();
            applyFilters();
        });
        pyInitialFilter.setOnAction(e -> {
            if (cascadeMuted) return;
            rebuildPyFinalOptions();
            applyFilters();
        });
        pyFinalFilter.setOnAction(e -> {
            if (cascadeMuted) return;
            applyFilters();
        });

        depthFilter.setStyle(style);
        pyInitialFilter.setStyle(style);
        pyFinalFilter.setStyle(style);

        var depthLabel = new Label("Group:");
        depthLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #666;");
        var depthRow = new HBox(6,
                depthLabel, depthFilter, pyInitialFilter, pyFinalFilter);
        depthRow.setAlignment(Pos.CENTER_LEFT);

        var bar = new VBox(4, topRow, bottomRow, depthRow);
        bar.setPadding(new Insets(6, 6, 4, 6));
        bar.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ddd; -fx-border-width: 0 0 1 0;");

        // Populate cascading dropdowns from the union of all depths so
        // the user can drill into Initial/Final without first picking Depth.
        rebuildPyInitialOptions();
        rebuildPyFinalOptions();

        return bar;
    }

    /**
     * Repopulate the Pinyin-initial dropdown from the currently-selected
     * depth, preserving the prior selection if it's still valid (otherwise
     * resetting to "All").
     */
    private void rebuildPyInitialOptions() {
        var prior = pyInitialFilter.getValue();
        cascadeMuted = true;
        try {
            pyInitialFilter.getItems().setAll(ALL_PY_INIT);
            for (var subPkg : initialsForSelectedDepth()) {
                pyInitialFilter.getItems().add(subPkg);
            }
            pyInitialFilter.setValue(
                    pyInitialFilter.getItems().contains(prior) ? prior : ALL_PY_INIT);
        } finally {
            cascadeMuted = false;
        }
    }

    /** Repopulate the Pinyin-final dropdown from (depth, initial). */
    private void rebuildPyFinalOptions() {
        var prior = pyFinalFilter.getValue();
        cascadeMuted = true;
        try {
            pyFinalFilter.getItems().setAll(ALL_PY_FINAL);
            for (var cls : finalsForSelected()) {
                pyFinalFilter.getItems().add(cls);
            }
            pyFinalFilter.setValue(
                    pyFinalFilter.getItems().contains(prior) ? prior : ALL_PY_FINAL);
        } finally {
            cascadeMuted = false;
        }
    }

    /** Initials available for the selected depth (union across all when "All"). */
    private Set<String> initialsForSelectedDepth() {
        var d = selectedDepth();
        if (d != null) {
            var byInitial = routeIndexByDepth.get(d);
            return byInitial == null ? Set.of() : byInitial.keySet();
        }
        var all = new TreeSet<String>();
        for (var byInitial : routeIndexByDepth.values()) {
            all.addAll(byInitial.keySet());
        }
        return all;
    }

    /** Final-classes available for the (depth, initial) currently selected. */
    private Set<String> finalsForSelected() {
        var initial = selectedPyInitial();
        if (initial == null) return Set.of();
        var d = selectedDepth();
        if (d != null) {
            var byInitial = routeIndexByDepth.get(d);
            var classes = byInitial == null ? null : byInitial.get(initial);
            return classes == null ? Set.of() : classes;
        }
        var all = new TreeSet<String>();
        for (var byInitial : routeIndexByDepth.values()) {
            var classes = byInitial.get(initial);
            if (classes != null) all.addAll(classes);
        }
        return all;
    }

    /** Selected depth as Integer, or null when "All Depths". */
    private Integer selectedDepth() {
        var v = depthFilter.getValue();
        if (v == null || v.equals(ALL_DEPTHS)) return null;
        // Format: "Depth N"
        return Integer.parseInt(v.substring(6).trim());
    }

    /** Selected pinyin initial sub-package, or null when "All". */
    private String selectedPyInitial() {
        var v = pyInitialFilter.getValue();
        return (v == null || v.equals(ALL_PY_INIT)) ? null : v;
    }

    /** Selected pinyin final class name, or null when "All". */
    private String selectedPyFinal() {
        var v = pyFinalFilter.getValue();
        return (v == null || v.equals(ALL_PY_FINAL)) ? null : v;
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
            predicate = predicate.and(e -> {
                var py = e.pinyin();
                if (py == null) return false;
                if (selInitial.startsWith("\u2205")) return py.initial() == Initial.ZERO;
                return py.initial().pinyin().equals(selInitial);
            });
        }

        // Tone filter
        var selTone = toneFilter.getValue();
        if (selTone != null && !selTone.equals("All Tones")) {
            predicate = predicate.and(e -> {
                var py = e.pinyin();
                if (py == null) return false;
                var label = py.tone().diacritic() + " " + py.tone().chinese();
                return label.equals(selTone);
            });
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

        // ── Depth → Pinyin partition cascading filters ────────────────
        // Depth=N implies "must be in routeIndex with that depth"
        // (i.e., a record from Depth<N>.ALL). Initial/Final further
        // constrain by the pinyin triple the record carries — the
        // cascade is a pure function of each Zi's PinyinSyllable.
        var selDepth   = selectedDepth();
        var selPyInit  = selectedPyInitial();
        var selPyFinal = selectedPyFinal();
        if (selDepth != null || selPyInit != null || selPyFinal != null) {
            predicate = predicate.and(e -> {
                var key = routeIndex.get(e);
                if (key == null) return false; // not in any per-pinyin partition
                if (selDepth != null && key.depth() != selDepth) return false;
                if (selPyInit != null && !key.initialLabel().equals(selPyInit)) return false;
                if (selPyFinal != null && !key.finalToneLabel().equals(selPyFinal)) return false;
                return true;
            });
        }

        filteredList.setPredicate(predicate);
    }

    private static boolean matchesCompositionFilter(BlockStructure structure, String filter) {
        CompositionLayout layout = (structure instanceof ComposedBlock cb) ? cb.composition() : null;
        return switch (filter) {
            case "Singular" -> structure instanceof SingularBlock;
            case "Left-Right" -> layout instanceof LeftRight;
            case "Top-Bottom" -> layout instanceof TopBottom;
            case "L-M-R" -> layout instanceof LeftMiddleRight;
            case "T-M-B" -> layout instanceof TopMiddleBottom;
            case "Full Encl." -> layout instanceof FullEnclosure;
            case "Semi-Enclosure" -> layout instanceof SemiEnclosureUpperLeft
                    || layout instanceof SemiEnclosureUpperRight
                    || layout instanceof SemiEnclosureBottomLeft
                    || layout instanceof SemiEnclosureTopThree
                    || layout instanceof SemiEnclosureBottomThree
                    || layout instanceof SemiEnclosureLeftThree;
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

        // ── Typed layout (only for ComposedZiT records) ─────────────────
        // Shows the precise parameterized layout interface, e.g.
        // "LeftRightT<Ri, Yue>" — the invariant the type system now
        // enforces at compile time. Reflects on the record's generic
        // interfaces at render time; no instance state required.
        if (e instanceof ComposedZiT zt) {
            var typedLabel = typedLayoutSignature(zt);
            if (typedLabel != null) {
                var typedSection = section("TYPED LAYOUT (compile-time verified)",
                        renderTypedLayout(typedLabel, zt.getClass()));
                box.getChildren().add(typedSection);
            }
        }

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
                switch (comp.composition()) {
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

    private static TreeItem<String> slotItem(String role, BlockStructure resolved) {
        if (resolved instanceof ComposedBlock) {
            // Nested composed structure — recurse
            var sub = buildTreeItem(resolved);
            sub.setValue(role + ":  " + sub.getValue());
            return sub;
        }
        // Leaf node
        return new TreeItem<>(role + ":  " + singularLabel(resolved));
    }

    private static void addWrapperContentItems(TreeItem<String> parent, BlockStructure wrapper, BlockStructure content) {
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
                box.getChildren().add(fieldLabel("Semantic (\u5f62\u65c1): " + renderBlock(ps.semanticPart())
                        + "  \u2192  hints at meaning"));
                box.getChildren().add(fieldLabel("Phonetic (\u58f0\u65c1): " + renderBlock(ps.phoneticPart())
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
        var py = e.pinyin();
        if (py != null) {
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

    // ── Typed layout rendering ─────────────────────────────────────────

    /**
     * Returns a readable rendering of the record's typed layout interface,
     * e.g. {@code "LeftRightT<Ri, Yue>"} or {@code "SemiEnclosureBottomLeftT<ZouZhiDi,
     * TopMiddleBottomPartT<Xue, ...>>"}. Returns {@code null} if no typed
     * layout interface is declared (defensive — the invariant test
     * guarantees every ComposedZiT has one).
     */
    private static String typedLayoutSignature(ComposedZiT record) {
        for (Type iface : record.getClass().getGenericInterfaces()) {
            if (!(iface instanceof ParameterizedType pt)) continue;
            var raw = (Class<?>) pt.getRawType();
            // Typed layout interfaces all live in kranji.zi and extend
            // CompositionLayoutT. Skip ComposedZiT itself (non-parameterized).
            if (!CompositionLayoutT.class.isAssignableFrom(raw)) continue;
            return renderTypeSignature(pt);
        }
        return null;
    }

    private static String renderTypeSignature(Type t) {
        if (t instanceof Class<?> c) return c.getSimpleName();
        if (t instanceof ParameterizedType pt) {
            var raw = (Class<?>) pt.getRawType();
            var sb = new StringBuilder(raw.getSimpleName()).append('<');
            var args = pt.getActualTypeArguments();
            for (int i = 0; i < args.length; i++) {
                if (i > 0) sb.append(", ");
                sb.append(renderTypeSignature(args[i]));
            }
            return sb.append('>').toString();
        }
        return t.getTypeName();
    }

    private VBox renderTypedLayout(String signature, Class<?> recordClass) {
        var box = new VBox(4);
        // Interface signature — monospace for readability of the nested
        // generic arguments (can get quite deep, e.g. Biang).
        var sigLabel = new Label(signature);
        sigLabel.setFont(Font.font("Consolas", 13));
        sigLabel.setWrapText(true);
        sigLabel.setStyle("-fx-text-fill: #2563EB;");
        box.getChildren().add(sigLabel);

        // Fully-qualified record location (helps users jump to source).
        var fqnLabel = fieldLabel("record: " + recordClass.getName());
        fqnLabel.setStyle(fqnLabel.getStyle() + " -fx-text-fill: #888;");
        box.getChildren().add(fqnLabel);
        return box;
    }

    // ── BlockStructure → display string ──────────────────────────────────

    private static String renderBlock(BlockStructure node) {
        return switch (node) {
            case ComposedBlock comp -> "[" + compositionLabel(comp) + "]";
            default -> {
                if (node instanceof SingularBlock sb
                        && (!sb.name().equals(sb.glyph()) || !sb.meaning().isEmpty())) {
                    yield sb.glyph() + "  " + sb.name() + " \u2014 " + sb.meaning()
                            + " (\u2190 " + sb.standalone() + " " + sb.pinyinText() + ")";
                }
                yield node.glyph();
            }
        };
    }

    // ── Label helpers ───────────────────────────────────────────────────

    private static String compositionLabel(BlockStructure structure) {
        return switch (structure) {
            case ComposedBlock comp -> switch (comp.composition()) {
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
            case ComposedBlock comp -> switch (comp.composition()) {
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
            return switch (comp.composition()) {
                case LeftRight lr -> glyphsOf(lr.left()) + glyphsOf(lr.right());
                case TopBottom tb -> glyphsOf(tb.top()) + glyphsOf(tb.bottom());
                case LeftMiddleRight lmr -> glyphsOf(lmr.left()) + glyphsOf(lmr.middle()) + glyphsOf(lmr.right());
                case TopMiddleBottom tmb -> glyphsOf(tmb.top()) + glyphsOf(tmb.middle()) + glyphsOf(tmb.bottom());
                case FullEnclosure fe -> glyphsOf(fe.outer()) + glyphsOf(fe.inner());
                case SemiEnclosureUpperLeft se -> glyphsOf(se.wrapper()) + glyphsOf(se.content());
                case SemiEnclosureUpperRight se -> glyphsOf(se.wrapper()) + glyphsOf(se.content());
                case SemiEnclosureBottomLeft se -> glyphsOf(se.wrapper()) + glyphsOf(se.content());
                case SemiEnclosureTopThree se -> glyphsOf(se.wrapper()) + glyphsOf(se.content());
                case SemiEnclosureBottomThree se -> glyphsOf(se.wrapper()) + glyphsOf(se.content());
                case SemiEnclosureLeftThree se -> glyphsOf(se.wrapper()) + glyphsOf(se.content());
            };
        }
        return node.glyph();
    }

    private static String formatPinyin(Zi e) {
        var py = e.pinyin();
        return py == null ? "" : py.numberedTone();
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
