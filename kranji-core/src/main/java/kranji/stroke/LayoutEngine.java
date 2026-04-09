package kranji.stroke;

import kranji.classification.CharacterComposition;
import kranji.classification.CharacterComposition.*;
import kranji.classification.StructuralNode;
import kranji.component.Component;

import java.util.*;

/**
 * Two-pass layout engine for recursive character structure.
 *
 * <p><b>Pass 1 — Measure (bottom-up DFS):</b> computes a {@link LayoutBox}
 * for every node. Leaves get a fixed base size; parents aggregate children.</p>
 *
 * <p><b>Pass 2 — Place (top-down DFS):</b> given the root's absolute position,
 * computes an {@link AffineTransform2D} mapping each leaf's [0,1]² glyph space
 * into final SVG coordinates.</p>
 */
public final class LayoutEngine {

    /** Margin around content inside enclosure wrappers (fraction of content size). */
    private static final double ENCLOSURE_MARGIN_FRAC = 0.25;

    private LayoutEngine() {}

    // ═══════════════════════════════════════════════════════════════
    //  Pass 1 — Measure (bottom-up)
    // ═══════════════════════════════════════════════════════════════

    /** Compute the bounding box for a node and all its descendants. */
    public static LayoutBox measure(StructuralNode node) {
        if (node instanceof Component) {
            return LayoutBox.LEAF;
        }
        if (node instanceof CharacterComposition comp) {
            return measureComposition(comp);
        }
        return LayoutBox.LEAF;
    }

    private static LayoutBox measureComposition(CharacterComposition comp) {
        return switch (comp) {
            case Singular() -> LayoutBox.LEAF;

            case LeftRight(var left, var right) -> {
                LayoutBox lb = measure(left), rb = measure(right);
                yield new LayoutBox(lb.width() + rb.width(), Math.max(lb.height(), rb.height()));
            }

            case TopBottom(var top, var bottom) -> {
                LayoutBox tb = measure(top), bb = measure(bottom);
                yield new LayoutBox(Math.max(tb.width(), bb.width()), tb.height() + bb.height());
            }

            case LeftMiddleRight(var l, var m, var r) -> {
                LayoutBox lb = measure(l), mb = measure(m), rb = measure(r);
                yield new LayoutBox(lb.width() + mb.width() + rb.width(),
                        Math.max(Math.max(lb.height(), mb.height()), rb.height()));
            }

            case TopMiddleBottom(var t, var m, var b) -> {
                LayoutBox tb = measure(t), mb = measure(m), bb = measure(b);
                yield new LayoutBox(Math.max(Math.max(tb.width(), mb.width()), bb.width()),
                        tb.height() + mb.height() + bb.height());
            }

            case FullEnclosure(var outer, var inner) -> {
                LayoutBox ib = measure(inner);
                double margin = LayoutBox.BASE_SIZE * ENCLOSURE_MARGIN_FRAC;
                yield new LayoutBox(ib.width() + 2 * margin, ib.height() + 2 * margin);
            }

            case SemiEnclosureBottomLeft(var wrapper, var content) -> {
                LayoutBox cb = measure(content);
                double leftM = LayoutBox.BASE_SIZE * 0.15;
                double bottomM = LayoutBox.BASE_SIZE * 0.20;
                yield new LayoutBox(cb.width() + leftM, cb.height() + bottomM);
            }

            case SemiEnclosureUpperLeft(var wrapper, var content) -> {
                LayoutBox cb = measure(content);
                double leftM = LayoutBox.BASE_SIZE * 0.20;
                double topM = LayoutBox.BASE_SIZE * 0.20;
                yield new LayoutBox(cb.width() + leftM, cb.height() + topM);
            }

            case SemiEnclosureUpperRight(var wrapper, var content) -> {
                LayoutBox cb = measure(content);
                double rightM = LayoutBox.BASE_SIZE * 0.20;
                double topM = LayoutBox.BASE_SIZE * 0.20;
                yield new LayoutBox(cb.width() + rightM, cb.height() + topM);
            }

            case SemiEnclosureTopThree(var wrapper, var content) -> {
                LayoutBox cb = measure(content);
                double margin = LayoutBox.BASE_SIZE * 0.20;
                yield new LayoutBox(cb.width() + 2 * margin, cb.height() + margin);
            }

            case SemiEnclosureBottomThree(var wrapper, var content) -> {
                LayoutBox cb = measure(content);
                double margin = LayoutBox.BASE_SIZE * 0.20;
                yield new LayoutBox(cb.width() + 2 * margin, cb.height() + margin);
            }

            case SemiEnclosureLeftThree(var wrapper, var content) -> {
                LayoutBox cb = measure(content);
                double margin = LayoutBox.BASE_SIZE * 0.20;
                yield new LayoutBox(cb.width() + margin, cb.height() + 2 * margin);
            }

            case Mosaic(var element) -> {
                LayoutBox eb = measure(element);
                yield new LayoutBox(2 * eb.width(), 2 * eb.height());
            }
        };
    }

    // ═══════════════════════════════════════════════════════════════
    //  Pass 2 — Place (top-down)
    // ═══════════════════════════════════════════════════════════════

    /**
     * A placed leaf: its glyph string and the transform from [0,1]² to SVG coords.
     */
    public record PlacedGlyph(String glyph, AffineTransform2D transform) {}

    /**
     * Place all leaves of the node tree, returning their transforms.
     *
     * @param node  the structural node
     * @param x     absolute x offset of this node's top-left corner
     * @param y     absolute y offset of this node's top-left corner
     * @param glyph the parent's glyph string (used for Singular leaves)
     * @return list of placed glyphs with their transforms
     */
    public static List<PlacedGlyph> place(StructuralNode node, double x, double y, String glyph) {
        if (node instanceof Component comp) {
            String g = extractGlyph(comp);
            LayoutBox box = LayoutBox.LEAF;
            AffineTransform2D transform = AffineTransform2D.rect(x, y, box.width(), box.height());
            return List.of(new PlacedGlyph(g, transform));
        }
        if (node instanceof CharacterComposition comp) {
            return placeComposition(comp, x, y, glyph);
        }
        return List.of();
    }

    private static List<PlacedGlyph> placeComposition(CharacterComposition comp, double x, double y, String glyph) {
        List<PlacedGlyph> result = new ArrayList<>();

        switch (comp) {
            case Singular() -> {
                AffineTransform2D transform = AffineTransform2D.rect(x, y, LayoutBox.BASE_SIZE, LayoutBox.BASE_SIZE);
                result.add(new PlacedGlyph(glyph != null ? glyph : "?", transform));
            }

            case LeftRight(var left, var right) -> {
                LayoutBox lb = measure(left), rb = measure(right);
                double h = Math.max(lb.height(), rb.height());
                result.addAll(place(left, x, y + (h - lb.height()) / 2, null));
                result.addAll(place(right, x + lb.width(), y + (h - rb.height()) / 2, null));
            }

            case TopBottom(var top, var bottom) -> {
                LayoutBox tb = measure(top), bb = measure(bottom);
                double w = Math.max(tb.width(), bb.width());
                result.addAll(place(top, x + (w - tb.width()) / 2, y, null));
                result.addAll(place(bottom, x + (w - bb.width()) / 2, y + tb.height(), null));
            }

            case LeftMiddleRight(var l, var m, var r) -> {
                LayoutBox lb = measure(l), mb = measure(m), rb = measure(r);
                double h = Math.max(Math.max(lb.height(), mb.height()), rb.height());
                result.addAll(place(l, x, y + (h - lb.height()) / 2, null));
                result.addAll(place(m, x + lb.width(), y + (h - mb.height()) / 2, null));
                result.addAll(place(r, x + lb.width() + mb.width(), y + (h - rb.height()) / 2, null));
            }

            case TopMiddleBottom(var t, var m, var b) -> {
                LayoutBox tb = measure(t), mb = measure(m), bb = measure(b);
                double w = Math.max(Math.max(tb.width(), mb.width()), bb.width());
                result.addAll(place(t, x + (w - tb.width()) / 2, y, null));
                result.addAll(place(m, x + (w - mb.width()) / 2, y + tb.height(), null));
                result.addAll(place(b, x + (w - bb.width()) / 2, y + tb.height() + mb.height(), null));
            }

            case FullEnclosure(var outer, var inner) -> {
                LayoutBox ib = measure(inner);
                double margin = LayoutBox.BASE_SIZE * ENCLOSURE_MARGIN_FRAC;
                double totalW = ib.width() + 2 * margin;
                double totalH = ib.height() + 2 * margin;
                // Outer wrapper fills the full box
                AffineTransform2D outerTransform = AffineTransform2D.rect(x, y, totalW, totalH);
                String outerGlyph = (outer instanceof Component c) ? extractGlyph(c) : null;
                result.add(new PlacedGlyph(outerGlyph, outerTransform));
                // Inner content centered
                result.addAll(place(inner, x + margin, y + margin, null));
            }

            case SemiEnclosureBottomLeft(var wrapper, var content) -> {
                LayoutBox cb = measure(content);
                double leftM = LayoutBox.BASE_SIZE * 0.15;
                double bottomM = LayoutBox.BASE_SIZE * 0.20;
                double totalW = cb.width() + leftM;
                double totalH = cb.height() + bottomM;
                // Wrapper (e.g. 辶) fills the full box
                AffineTransform2D wrapperTransform = AffineTransform2D.rect(x, y, totalW, totalH);
                String wrapperGlyph = (wrapper instanceof Component c) ? extractGlyph(c) : null;
                result.add(new PlacedGlyph(wrapperGlyph, wrapperTransform));
                // Content positioned at top-right
                result.addAll(place(content, x + leftM, y, null));
            }

            case SemiEnclosureUpperLeft(var wrapper, var content) -> {
                LayoutBox cb = measure(content);
                double leftM = LayoutBox.BASE_SIZE * 0.20;
                double topM = LayoutBox.BASE_SIZE * 0.20;
                double totalW = cb.width() + leftM;
                double totalH = cb.height() + topM;
                AffineTransform2D wrapperTransform = AffineTransform2D.rect(x, y, totalW, totalH);
                String wrapperGlyph = (wrapper instanceof Component c) ? extractGlyph(c) : null;
                result.add(new PlacedGlyph(wrapperGlyph, wrapperTransform));
                result.addAll(place(content, x + leftM, y + topM, null));
            }

            case SemiEnclosureUpperRight(var wrapper, var content) -> {
                LayoutBox cb = measure(content);
                double rightM = LayoutBox.BASE_SIZE * 0.20;
                double topM = LayoutBox.BASE_SIZE * 0.20;
                double totalW = cb.width() + rightM;
                double totalH = cb.height() + topM;
                AffineTransform2D wrapperTransform = AffineTransform2D.rect(x, y, totalW, totalH);
                String wrapperGlyph = (wrapper instanceof Component c) ? extractGlyph(c) : null;
                result.add(new PlacedGlyph(wrapperGlyph, wrapperTransform));
                result.addAll(place(content, x, y + topM, null));
            }

            case SemiEnclosureTopThree(var wrapper, var content) -> {
                LayoutBox cb = measure(content);
                double margin = LayoutBox.BASE_SIZE * 0.20;
                double totalW = cb.width() + 2 * margin;
                double totalH = cb.height() + margin;
                AffineTransform2D wrapperTransform = AffineTransform2D.rect(x, y, totalW, totalH);
                String wrapperGlyph = (wrapper instanceof Component c) ? extractGlyph(c) : null;
                result.add(new PlacedGlyph(wrapperGlyph, wrapperTransform));
                result.addAll(place(content, x + margin, y + margin, null));
            }

            case SemiEnclosureBottomThree(var wrapper, var content) -> {
                LayoutBox cb = measure(content);
                double margin = LayoutBox.BASE_SIZE * 0.20;
                double totalW = cb.width() + 2 * margin;
                double totalH = cb.height() + margin;
                AffineTransform2D wrapperTransform = AffineTransform2D.rect(x, y, totalW, totalH);
                String wrapperGlyph = (wrapper instanceof Component c) ? extractGlyph(c) : null;
                result.add(new PlacedGlyph(wrapperGlyph, wrapperTransform));
                result.addAll(place(content, x + margin, y, null));
            }

            case SemiEnclosureLeftThree(var wrapper, var content) -> {
                LayoutBox cb = measure(content);
                double margin = LayoutBox.BASE_SIZE * 0.20;
                double totalW = cb.width() + margin;
                double totalH = cb.height() + 2 * margin;
                AffineTransform2D wrapperTransform = AffineTransform2D.rect(x, y, totalW, totalH);
                String wrapperGlyph = (wrapper instanceof Component c) ? extractGlyph(c) : null;
                result.add(new PlacedGlyph(wrapperGlyph, wrapperTransform));
                result.addAll(place(content, x + margin, y + margin, null));
            }

            case Mosaic(var element) -> {
                LayoutBox eb = measure(element);
                // Top-center
                result.addAll(place(element, x + eb.width() / 2, y, null));
                // Bottom-left
                result.addAll(place(element, x, y + eb.height(), null));
                // Bottom-right
                result.addAll(place(element, x + eb.width(), y + eb.height(), null));
            }
        }

        return result;
    }

    private static String extractGlyph(Component comp) {
        return switch (comp) {
            case Component.Zi zi -> zi.glyph();
            case Component.Part part -> part.glyph();
        };
    }
}
