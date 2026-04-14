package kranji.stroke;

import kranji.zi.*;
import kranji.zi.ComposedBlock.*;

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
    //  Composable resolution
    // ═══════════════════════════════════════════════════════════════

    private static BlockStructure resolve(Composable c) {
        return switch (c) {
            case Composable.OfZi(var zi) -> zi.structure();
            case Composable.OfBlock(var block) -> block;
        };
    }

    // ═══════════════════════════════════════════════════════════════
    //  Pass 1 — Measure (bottom-up)
    // ═══════════════════════════════════════════════════════════════

    /** Compute the bounding box for a node and all its descendants. */
    public static LayoutBox measure(BlockStructure node) {
        if (node instanceof SingularBlock) {
            return LayoutBox.LEAF;
        }
        if (node instanceof ComposedBlock comp) {
            return measureComposition(comp);
        }
        return LayoutBox.LEAF;
    }

    private static LayoutBox measureComposable(Composable c) {
        return measure(resolve(c));
    }

    private static LayoutBox measureComposition(ComposedBlock comp) {
        return switch (comp) {
            case LeftRight(var left, var right) -> {
                LayoutBox lb = measureComposable(left), rb = measureComposable(right);
                yield new LayoutBox(lb.width() + rb.width(), Math.max(lb.height(), rb.height()));
            }

            case TopBottom(var top, var bottom) -> {
                LayoutBox tb = measureComposable(top), bb = measureComposable(bottom);
                yield new LayoutBox(Math.max(tb.width(), bb.width()), tb.height() + bb.height());
            }

            case LeftMiddleRight(var l, var m, var r) -> {
                LayoutBox lb = measureComposable(l), mb = measureComposable(m), rb = measureComposable(r);
                yield new LayoutBox(lb.width() + mb.width() + rb.width(),
                        Math.max(Math.max(lb.height(), mb.height()), rb.height()));
            }

            case TopMiddleBottom(var t, var m, var b) -> {
                LayoutBox tb = measureComposable(t), mb = measureComposable(m), bb = measureComposable(b);
                yield new LayoutBox(Math.max(Math.max(tb.width(), mb.width()), bb.width()),
                        tb.height() + mb.height() + bb.height());
            }

            case FullEnclosure(var outer, var inner) -> {
                LayoutBox ib = measureComposable(inner);
                double margin = LayoutBox.BASE_SIZE * ENCLOSURE_MARGIN_FRAC;
                yield new LayoutBox(ib.width() + 2 * margin, ib.height() + 2 * margin);
            }

            case SemiEnclosureBottomLeft(var wrapper, var content) -> {
                LayoutBox cb = measureComposable(content);
                double leftM = LayoutBox.BASE_SIZE * 0.15;
                double bottomM = LayoutBox.BASE_SIZE * 0.20;
                yield new LayoutBox(cb.width() + leftM, cb.height() + bottomM);
            }

            case SemiEnclosureUpperLeft(var wrapper, var content) -> {
                LayoutBox cb = measureComposable(content);
                double leftM = LayoutBox.BASE_SIZE * 0.20;
                double topM = LayoutBox.BASE_SIZE * 0.20;
                yield new LayoutBox(cb.width() + leftM, cb.height() + topM);
            }

            case SemiEnclosureUpperRight(var wrapper, var content) -> {
                LayoutBox cb = measureComposable(content);
                double rightM = LayoutBox.BASE_SIZE * 0.20;
                double topM = LayoutBox.BASE_SIZE * 0.20;
                yield new LayoutBox(cb.width() + rightM, cb.height() + topM);
            }

            case SemiEnclosureTopThree(var wrapper, var content) -> {
                LayoutBox cb = measureComposable(content);
                double margin = LayoutBox.BASE_SIZE * 0.20;
                yield new LayoutBox(cb.width() + 2 * margin, cb.height() + margin);
            }

            case SemiEnclosureBottomThree(var wrapper, var content) -> {
                LayoutBox cb = measureComposable(content);
                double margin = LayoutBox.BASE_SIZE * 0.20;
                yield new LayoutBox(cb.width() + 2 * margin, cb.height() + margin);
            }

            case SemiEnclosureLeftThree(var wrapper, var content) -> {
                LayoutBox cb = measureComposable(content);
                double margin = LayoutBox.BASE_SIZE * 0.20;
                yield new LayoutBox(cb.width() + margin, cb.height() + 2 * margin);
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
     */
    public static List<PlacedGlyph> place(BlockStructure node, double x, double y, String glyph) {
        if (node instanceof SingularBlock sb) {
            String g = sb.glyph();
            LayoutBox box = LayoutBox.LEAF;
            AffineTransform2D transform = AffineTransform2D.rect(x, y, box.width(), box.height());
            return List.of(new PlacedGlyph(g, transform));
        }
        if (node instanceof ComposedBlock comp) {
            return placeComposition(comp, x, y, glyph);
        }
        return List.of();
    }

    private static List<PlacedGlyph> placeComposable(Composable c, double x, double y, String glyph) {
        return place(resolve(c), x, y, glyph);
    }

    private static List<PlacedGlyph> placeComposition(ComposedBlock comp, double x, double y, String glyph) {
        List<PlacedGlyph> result = new ArrayList<>();

        switch (comp) {
            case LeftRight(var left, var right) -> {
                LayoutBox lb = measureComposable(left), rb = measureComposable(right);
                double h = Math.max(lb.height(), rb.height());
                result.addAll(placeComposable(left, x, y + (h - lb.height()) / 2, null));
                result.addAll(placeComposable(right, x + lb.width(), y + (h - rb.height()) / 2, null));
            }

            case TopBottom(var top, var bottom) -> {
                LayoutBox tb = measureComposable(top), bb = measureComposable(bottom);
                double w = Math.max(tb.width(), bb.width());
                result.addAll(placeComposable(top, x + (w - tb.width()) / 2, y, null));
                result.addAll(placeComposable(bottom, x + (w - bb.width()) / 2, y + tb.height(), null));
            }

            case LeftMiddleRight(var l, var m, var r) -> {
                LayoutBox lb = measureComposable(l), mb = measureComposable(m), rb = measureComposable(r);
                double h = Math.max(Math.max(lb.height(), mb.height()), rb.height());
                result.addAll(placeComposable(l, x, y + (h - lb.height()) / 2, null));
                result.addAll(placeComposable(m, x + lb.width(), y + (h - mb.height()) / 2, null));
                result.addAll(placeComposable(r, x + lb.width() + mb.width(), y + (h - rb.height()) / 2, null));
            }

            case TopMiddleBottom(var t, var m, var b) -> {
                LayoutBox tb = measureComposable(t), mb = measureComposable(m), bb = measureComposable(b);
                double w = Math.max(Math.max(tb.width(), mb.width()), bb.width());
                result.addAll(placeComposable(t, x + (w - tb.width()) / 2, y, null));
                result.addAll(placeComposable(m, x + (w - mb.width()) / 2, y + tb.height(), null));
                result.addAll(placeComposable(b, x + (w - bb.width()) / 2, y + tb.height() + mb.height(), null));
            }

            case FullEnclosure(var outer, var inner) -> {
                LayoutBox ib = measureComposable(inner);
                double margin = LayoutBox.BASE_SIZE * ENCLOSURE_MARGIN_FRAC;
                double totalW = ib.width() + 2 * margin;
                double totalH = ib.height() + 2 * margin;
                AffineTransform2D outerTransform = AffineTransform2D.rect(x, y, totalW, totalH);
                result.add(new PlacedGlyph(outer.glyph(), outerTransform));
                result.addAll(placeComposable(inner, x + margin, y + margin, null));
            }

            case SemiEnclosureBottomLeft(var wrapper, var content) -> {
                LayoutBox cb = measureComposable(content);
                double leftM = LayoutBox.BASE_SIZE * 0.15;
                double bottomM = LayoutBox.BASE_SIZE * 0.20;
                double totalW = cb.width() + leftM;
                double totalH = cb.height() + bottomM;
                AffineTransform2D wrapperTransform = AffineTransform2D.rect(x, y, totalW, totalH);
                result.add(new PlacedGlyph(wrapper.glyph(), wrapperTransform));
                result.addAll(placeComposable(content, x + leftM, y, null));
            }

            case SemiEnclosureUpperLeft(var wrapper, var content) -> {
                LayoutBox cb = measureComposable(content);
                double leftM = LayoutBox.BASE_SIZE * 0.20;
                double topM = LayoutBox.BASE_SIZE * 0.20;
                double totalW = cb.width() + leftM;
                double totalH = cb.height() + topM;
                AffineTransform2D wrapperTransform = AffineTransform2D.rect(x, y, totalW, totalH);
                result.add(new PlacedGlyph(wrapper.glyph(), wrapperTransform));
                result.addAll(placeComposable(content, x + leftM, y + topM, null));
            }

            case SemiEnclosureUpperRight(var wrapper, var content) -> {
                LayoutBox cb = measureComposable(content);
                double rightM = LayoutBox.BASE_SIZE * 0.20;
                double topM = LayoutBox.BASE_SIZE * 0.20;
                double totalW = cb.width() + rightM;
                double totalH = cb.height() + topM;
                AffineTransform2D wrapperTransform = AffineTransform2D.rect(x, y, totalW, totalH);
                result.add(new PlacedGlyph(wrapper.glyph(), wrapperTransform));
                result.addAll(placeComposable(content, x, y + topM, null));
            }

            case SemiEnclosureTopThree(var wrapper, var content) -> {
                LayoutBox cb = measureComposable(content);
                double margin = LayoutBox.BASE_SIZE * 0.20;
                double totalW = cb.width() + 2 * margin;
                double totalH = cb.height() + margin;
                AffineTransform2D wrapperTransform = AffineTransform2D.rect(x, y, totalW, totalH);
                result.add(new PlacedGlyph(wrapper.glyph(), wrapperTransform));
                result.addAll(placeComposable(content, x + margin, y + margin, null));
            }

            case SemiEnclosureBottomThree(var wrapper, var content) -> {
                LayoutBox cb = measureComposable(content);
                double margin = LayoutBox.BASE_SIZE * 0.20;
                double totalW = cb.width() + 2 * margin;
                double totalH = cb.height() + margin;
                AffineTransform2D wrapperTransform = AffineTransform2D.rect(x, y, totalW, totalH);
                result.add(new PlacedGlyph(wrapper.glyph(), wrapperTransform));
                result.addAll(placeComposable(content, x + margin, y, null));
            }

            case SemiEnclosureLeftThree(var wrapper, var content) -> {
                LayoutBox cb = measureComposable(content);
                double margin = LayoutBox.BASE_SIZE * 0.20;
                double totalW = cb.width() + margin;
                double totalH = cb.height() + 2 * margin;
                AffineTransform2D wrapperTransform = AffineTransform2D.rect(x, y, totalW, totalH);
                result.add(new PlacedGlyph(wrapper.glyph(), wrapperTransform));
                result.addAll(placeComposable(content, x + margin, y + margin, null));
            }
        }

        return result;
    }
}
