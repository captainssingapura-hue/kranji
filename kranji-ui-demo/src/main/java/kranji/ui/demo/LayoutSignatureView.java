package kranji.ui.demo;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import kranji.zi.BlockStructure;
import kranji.zi.ComposedBlock;
import kranji.zi.CompositionLayout;
import kranji.zi.CompositionLayout.FullEnclosure;
import kranji.zi.CompositionLayout.LeftMiddleRight;
import kranji.zi.CompositionLayout.LeftRight;
import kranji.zi.CompositionLayout.SemiEnclosureBottomLeft;
import kranji.zi.CompositionLayout.SemiEnclosureBottomThree;
import kranji.zi.CompositionLayout.SemiEnclosureLeftThree;
import kranji.zi.CompositionLayout.SemiEnclosureTopThree;
import kranji.zi.CompositionLayout.SemiEnclosureUpperLeft;
import kranji.zi.CompositionLayout.SemiEnclosureUpperRight;
import kranji.zi.CompositionLayout.TopBottom;
import kranji.zi.CompositionLayout.TopMiddleBottom;
import kranji.zi.Zi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Renders a {@link BlockStructure} as a Java-like type signature with
 * inline expand/collapse and SVG dispatch on single click.
 *
 * <p><b>Interaction</b>:
 * <ul>
 *   <li><b>Single click</b> on a glyph link (collapsed state) or on a
 *       layout-name token (expanded state) calls the supplied
 *       {@code dispatcher} with the {@link Zi} so the SVG pane
 *       can re-render that record.</li>
 *   <li><b>Double click</b> on a glyph link expands it in place to
 *       its one-level decomposition. Double click on the layout-name
 *       token of an existing expansion collapses the entire
 *       {@code Layout<…>} span back to the original glyph link
 *       (using bracket-count matching, so it works even after
 *       children have been expanded).</li>
 *   <li>Anonymous wrappers ({@link kranji.zi.ComposedPart}) and
 *       synthetic empty-glyph Zi inners are always pre-expanded —
 *       there's no name to display, so no collapse target either.</li>
 * </ul>
 */
final class LayoutSignatureView {

    private static final String INDENT_UNIT = "  ";

    private LayoutSignatureView() {}

    /**
     * Build the signature flow for {@code root}.
     *
     * @param dispatcher callback invoked on single-click of a clickable
     *                   token. Receives the underlying
     *                   {@link BlockStructure} (which may be a
     *                   {@link Zi}, a {@link kranji.zi.SingularPart},
     *                   or a synthetic). The host adapts to its own
     *                   representation as needed.
     */
    static TextFlow render(BlockStructure root, double fontSize,
                           Consumer<BlockStructure> dispatcher) {
        var flow = new TextFlow();
        flow.setLineSpacing(2);
        appendInline(flow, root, fontSize, true, 0, dispatcher);
        return flow;
    }

    private static void appendInline(TextFlow flow, BlockStructure node,
                                     double fontSize, boolean isRoot, int depth,
                                     Consumer<BlockStructure> dispatcher) {
        if (node instanceof ComposedBlock comp
                && (isRoot || !isNamedZi(node))) {
            List<BlockStructure> children = comp.composition().components();

            // Layout-name token doubles as the collapse-handle when this
            // expansion is interactive (i.e. it has a Zi behind it that
            // *could* have been a collapsed glyph). For root-level or
            // anonymous expansions, no collapse target → plain Text.
            Text layoutName = layoutText(layoutShortName(comp.composition()), fontSize);
            if (!isRoot && node instanceof Zi) {
                attachLayoutNameHandlers(layoutName, node, fontSize, depth, dispatcher);
            }
            flow.getChildren().add(layoutName);
            flow.getChildren().add(syntaxText("<\n", fontSize));

            for (int i = 0; i < children.size(); i++) {
                flow.getChildren().add(syntaxText(indent(depth + 1), fontSize));
                appendInline(flow, children.get(i), fontSize, false, depth + 1, dispatcher);
                boolean last = (i == children.size() - 1);
                flow.getChildren().add(syntaxText(last ? "\n" : ",\n", fontSize));
            }

            flow.getChildren().add(syntaxText(indent(depth), fontSize));
            flow.getChildren().add(syntaxText(">", fontSize));
            return;
        }
        // Singular OR named composed Zi → clickable glyph.
        flow.getChildren().add(glyphLink(node, fontSize, depth, dispatcher));
    }

    // ── Expansion / collapse ──────────────────────────────────────

    /** Stash on a layout-name Text so we can rebuild the original glyph link on collapse. */
    private record ExpansionTag(BlockStructure node, int depth) {}

    private static void expandInPlace(Hyperlink link, BlockStructure node,
                                      double fontSize, int depth,
                                      Consumer<BlockStructure> dispatcher) {
        if (!(link.getParent() instanceof TextFlow parent)) return;
        if (!(node instanceof ComposedBlock)) return;          // atom → nothing to expand
        int idx = parent.getChildren().indexOf(link);
        if (idx < 0) return;

        var scratch = new TextFlow();
        appendInline(scratch, node, fontSize, true, depth, dispatcher);
        var expansion = new ArrayList<Node>(scratch.getChildren());
        scratch.getChildren().clear();

        // The first node of the expansion is the layout-name Text. Tag
        // it with the collapse target (the original node + depth) and
        // wire single/double-click handlers on it for re-collapse and
        // SVG dispatch.
        if (!expansion.isEmpty() && expansion.get(0) instanceof Text layoutName) {
            attachLayoutNameHandlers(layoutName, node, fontSize, depth, dispatcher);
        }

        parent.getChildren().remove(idx);
        parent.getChildren().addAll(idx, expansion);
    }

    private static void collapseAt(Text layoutName, double fontSize, Consumer<BlockStructure> dispatcher) {
        if (!(layoutName.getParent() instanceof TextFlow parent)) return;
        if (!(layoutName.getUserData() instanceof ExpansionTag tag)) return;
        int start = parent.getChildren().indexOf(layoutName);
        if (start < 0) return;
        int end = findMatchingClose(parent.getChildren(), start);
        if (end < 0) return;

        Hyperlink replacement = glyphLink(tag.node, fontSize, tag.depth, dispatcher);
        parent.getChildren().subList(start, end + 1).clear();
        parent.getChildren().add(start, replacement);
    }

    /**
     * Bracket-counting scan from the layout-name node to its matching
     * closing {@code >} Text. Counts every Text whose content contains
     * {@code "<"} as +1 and {@code ">"} as -1 (each layout-name → bracket
     * pair contributes one of each). Resilient to nested expansions
     * created by drilling into children.
     */
    private static int findMatchingClose(List<Node> nodes, int startIdx) {
        int depth = 0;
        boolean entered = false;
        for (int i = startIdx; i < nodes.size(); i++) {
            if (!(nodes.get(i) instanceof Text t)) continue;
            String s = t.getText();
            if (s == null) continue;
            if (s.contains("<")) {
                depth++;
                entered = true;
            }
            if (s.contains(">")) {
                if (entered) {
                    depth--;
                    if (depth == 0) return i;
                }
            }
        }
        return -1;
    }

    // ── Click handlers ─────────────────────────────────────────────

    private static void attachLayoutNameHandlers(Text layoutName, BlockStructure node,
                                                 double fontSize, int depth,
                                                 Consumer<BlockStructure> dispatcher) {
        layoutName.setUserData(new ExpansionTag(node, depth));
        layoutName.setStyle(layoutName.getStyle() + " -fx-cursor: hand;");
        layoutName.setOnMouseClicked(ev -> {
            if (ev.getClickCount() == 2) {
                collapseAt(layoutName, fontSize, dispatcher);
                ev.consume();
            } else if (ev.getClickCount() == 1) {
                dispatcher.accept(node);
                ev.consume();
            }
        });
    }

    // ── Node factories ─────────────────────────────────────────────

    /** Structural scaffolding — neutral text color. */
    private static Text layoutText(String s, double fontSize) {
        var t = new Text(s);
        t.setFont(Font.font("Consolas", FontWeight.NORMAL, fontSize));
        t.setStyle("-fx-fill: #222;");
        return t;
    }

    /** Brackets / commas / indent — slightly muted. */
    private static Text syntaxText(String s, double fontSize) {
        var t = new Text(s);
        t.setFont(Font.font("Consolas", FontWeight.NORMAL, fontSize));
        t.setStyle("-fx-fill: #888;");
        return t;
    }

    /** Glyphs — blue. Single click → SVG; double click → expand. */
    private static Hyperlink glyphLink(BlockStructure node, double fontSize, int depth,
                                       Consumer<BlockStructure> dispatcher) {
        var link = new Hyperlink(node.glyph());
        link.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, fontSize));
        link.setStyle(
                "-fx-text-fill: #2563EB;" +
                "-fx-border-color: transparent;" +
                "-fx-padding: 0 2 0 2;" +
                "-fx-underline: false;");
        // Disable the default Hyperlink on-action — we drive everything
        // off mouseClicked so single vs double can be distinguished.
        link.setOnAction(null);
        link.addEventFilter(MouseEvent.MOUSE_CLICKED, ev -> {
            if (ev.getClickCount() == 2) {
                expandInPlace(link, node, fontSize, depth, dispatcher);
                ev.consume();
            } else if (ev.getClickCount() == 1) {
                dispatcher.accept(node);
                ev.consume();
            }
        });
        return link;
    }

    // ── Helpers ────────────────────────────────────────────────────

    /** Named Zi = Zi instance with a non-empty glyph (rules out
     *  {@link kranji.zi.ComposedPart} and synthetic empty-glyph inners). */
    private static boolean isNamedZi(BlockStructure b) {
        if (!(b instanceof Zi)) return false;
        String g = b.glyph();
        return g != null && !g.isEmpty();
    }

    private static String indent(int depth) {
        return INDENT_UNIT.repeat(depth);
    }

    /** Map composition variants to short, human-readable layout names. */
    private static String layoutShortName(CompositionLayout c) {
        return switch (c) {
            case LeftRight lr               -> "LeftRight";
            case TopBottom tb               -> "TopDown";
            case LeftMiddleRight lmr        -> "LeftMidRight";
            case TopMiddleBottom tmb        -> "TopMidDown";
            case FullEnclosure fe           -> "FullEnclosure";
            case SemiEnclosureUpperLeft se  -> "SemiUL";
            case SemiEnclosureUpperRight se -> "SemiUR";
            case SemiEnclosureBottomLeft se -> "SemiBL";
            case SemiEnclosureTopThree se   -> "SemiTop3";
            case SemiEnclosureBottomThree se -> "SemiBot3";
            case SemiEnclosureLeftThree se  -> "SemiLeft3";
        };
    }
}
