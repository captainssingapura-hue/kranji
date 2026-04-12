package kranji.layout;

import kranji.classification.BlockRole;
import kranji.entry.ChineseCharacterEntry;
import kranji.zi.ComposedZi;
import kranji.zi.ComposedZi.*;
import kranji.zi.SingularZi;
import kranji.zi.Zi;

import java.util.ArrayList;
import java.util.List;

/**
 * Recursively partitions a character's unit square [0,1]² into blocks
 * for each leaf component, based on the composition structure and
 * component politeness levels.
 *
 * <p><b>Stage 1 of the two-stage pipeline.</b> This engine only cares
 * about block layout — it reads {@link Politeness} from components to
 * determine split ratios. Inner positioning (scale, offset) is deferred
 * entirely to Stage 2 ({@link BlockSvgRenderer}).</p>
 *
 * <h3>Politeness gap formula:</h3>
 * <p>For two siblings A (left/top) and B (right/bottom):</p>
 * <pre>
 *   gap = B.politeness.ordinal() - A.politeness.ordinal()
 *   A_share = 0.5 + gap * STEP
 *   B_share = 0.5 - gap * STEP
 * </pre>
 * <p>Each politeness level difference shifts the split by ~10%.</p>
 */
public final class BlockLayoutEngine {

    /** Margin fraction for full enclosure inner content. */
    private static final double ENCL_MARGIN = 0.18;

    /**
     * Split shift per politeness ordinal difference.
     * Each level of politeness gap shifts the split by this fraction.
     */
    private static final double STEP = 0.10;

    /** Maximum politeness ordinal (DEFERENTIAL = 3). */
    private static final int MAX_ORDINAL = Politeness.DEFERENTIAL.ordinal();

    private BlockLayoutEngine() {}

    // ════════════════════════════════════════════════════════════
    //  Block count estimation
    // ════════════════════════════════════════════════════════════

    /**
     * Recursively count the total number of leaf blocks a Zi
     * would produce.
     */
    static int countBlocks(Zi node) {
        if (node instanceof SingularZi) {
            return 1;
        }
        if (node instanceof ComposedZi comp) {
            return switch (comp) {
                case LeftRight(var l, var r) -> countBlocks(l) + countBlocks(r);
                case TopBottom(var t, var b) -> countBlocks(t) + countBlocks(b);
                case LeftMiddleRight(var l, var m, var r) ->
                        countBlocks(l) + countBlocks(m) + countBlocks(r);
                case TopMiddleBottom(var t, var m, var b) ->
                        countBlocks(t) + countBlocks(m) + countBlocks(b);
                case FullEnclosure(var o, var i) -> 1 + countBlocks(i);
                case SemiEnclosureUpperLeft(var w, var c) -> 1 + countBlocks(c);
                case SemiEnclosureUpperRight(var w, var c) -> 1 + countBlocks(c);
                case SemiEnclosureBottomLeft(var w, var c) -> 1 + countBlocks(c);
                case SemiEnclosureTopThree(var w, var c) -> 1 + countBlocks(c);
                case SemiEnclosureBottomThree(var w, var c) -> 1 + countBlocks(c);
                case SemiEnclosureLeftThree(var w, var c) -> 1 + countBlocks(c);
                case Mosaic(var e) -> countBlocks(e) * 3;
            };
        }
        return 1;
    }

    public static List<Block> layout(ChineseCharacterEntry entry) {
        List<Block> raw = layoutZi(entry.structure(), 0, 0, 1, 1, 0, null, entry.character());
        return applyGravity(raw);
    }

    // ════════════════════════════════════════════════════════════
    //  Gravity centering
    // ════════════════════════════════════════════════════════════

    static List<Block> applyGravity(List<Block> blocks) {
        if (blocks.isEmpty()) return blocks;

        double totalMass = 0;
        double cgX = 0, cgY = 0;
        for (Block b : blocks) {
            if (b.role().isOverlay()) continue;
            double mass = b.area();
            cgX += b.cx() * mass;
            cgY += b.cy() * mass;
            totalMass += mass;
        }
        if (totalMass == 0) return blocks;
        cgX /= totalMass;
        cgY /= totalMass;

        double dx = 0.5 - cgX;
        double dy = 0.5 - cgY;

        double minX = Double.MAX_VALUE, maxXW = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE, maxYH = Double.MIN_VALUE;
        for (Block b : blocks) {
            minX = Math.min(minX, b.x());
            minY = Math.min(minY, b.y());
            maxXW = Math.max(maxXW, b.x() + b.w());
            maxYH = Math.max(maxYH, b.y() + b.h());
        }
        dx = Math.max(dx, -minX);
        dx = Math.min(dx, 1.0 - maxXW);
        dy = Math.max(dy, -minY);
        dy = Math.min(dy, 1.0 - maxYH);

        if (Math.abs(dx) < 0.001 && Math.abs(dy) < 0.001) return blocks;

        double fdx = dx, fdy = dy;
        return blocks.stream()
                .map(b -> new Block(b.glyph(), b.x() + fdx, b.y() + fdy, b.w(), b.h(), b.depth(), b.role(), b.hint()))
                .toList();
    }

    // ════════════════════════════════════════════════════════════
    //  Recursive dispatch
    // ════════════════════════════════════════════════════════════

    private static List<Block> layoutZi(
            Zi node,
            double x, double y, double w, double h,
            int depth, BlockRole role, String fallbackGlyph
    ) {
        // SingularZi leaf — resolve hints and create a single block.
        if (node instanceof SingularZi sz) {
            if (role != null) {
                HintedComponent hc = sz.as(role);
                String glyph = hc.glyph();
                LayoutHint hint = hc.hint();
                return List.of(new Block(glyph, x, y, w, h, depth, role, hint));
            }
            // No role (top-level singular entry) — use glyph directly.
            String glyph = (fallbackGlyph != null) ? fallbackGlyph : sz.glyph();
            return List.of(new Block(glyph, x, y, w, h, depth, new SingularRole()));
        }
        if (node instanceof ComposedZi comp) {
            return layoutComposition(comp, x, y, w, h, depth, fallbackGlyph);
        }
        return List.of();
    }

    /** Placeholder role for top-level singular entries. */
    record SingularRole() implements BlockRole {
        @Override public String label() { return "singular"; }
    }

    private static List<Block> layoutComposition(
            ComposedZi comp,
            double x, double y, double w, double h,
            int depth, String fallbackGlyph
    ) {
        List<Block> result = new ArrayList<>();

        switch (comp) {
            case LeftRight(var left, var right) -> {
                Politeness lp = politenessOf(left, LeftRight.LEFT);
                Politeness rp = politenessOf(right, LeftRight.RIGHT);
                double[] shares = twoWaySplit(lp, rp);
                double splitX = w * shares[0];
                result.addAll(layoutZi(left, x, y, splitX, h, depth + 1, LeftRight.LEFT, null));
                result.addAll(layoutZi(right, x + splitX, y, w - splitX, h, depth + 1, LeftRight.RIGHT, null));
            }

            case TopBottom(var top, var bottom) -> {
                Politeness tp = politenessOf(top, TopBottom.TOP);
                Politeness bp = politenessOf(bottom, TopBottom.BOTTOM);
                double[] shares = twoWaySplit(tp, bp);
                double splitY = h * shares[0];
                result.addAll(layoutZi(top, x, y, w, splitY, depth + 1, TopBottom.TOP, null));
                result.addAll(layoutZi(bottom, x, y + splitY, w, h - splitY, depth + 1, TopBottom.BOTTOM, null));
            }

            case LeftMiddleRight(var l, var m, var r) -> {
                Politeness lp = politenessOf(l, LeftMiddleRight.LEFT);
                Politeness mp = politenessOf(m, LeftMiddleRight.MIDDLE);
                Politeness rp = politenessOf(r, LeftMiddleRight.RIGHT);
                double[] shares = threeWaySplit(lp, mp, rp);
                double w1 = w * shares[0];
                double w2 = w * shares[1];
                double w3 = w * shares[2];
                result.addAll(layoutZi(l, x, y, w1, h, depth + 1, LeftMiddleRight.LEFT, null));
                result.addAll(layoutZi(m, x + w1, y, w2, h, depth + 1, LeftMiddleRight.MIDDLE, null));
                result.addAll(layoutZi(r, x + w1 + w2, y, w3, h, depth + 1, LeftMiddleRight.RIGHT, null));
            }

            case TopMiddleBottom(var t, var m, var b) -> {
                Politeness tp = politenessOf(t, TopMiddleBottom.TOP);
                Politeness mp = politenessOf(m, TopMiddleBottom.MIDDLE);
                Politeness bp = politenessOf(b, TopMiddleBottom.BOTTOM);
                double[] shares = threeWaySplit(tp, mp, bp);
                double h1 = h * shares[0];
                double h2 = h * shares[1];
                double h3 = h * shares[2];
                result.addAll(layoutZi(t, x, y, w, h1, depth + 1, TopMiddleBottom.TOP, null));
                result.addAll(layoutZi(m, x, y + h1, w, h2, depth + 1, TopMiddleBottom.MIDDLE, null));
                result.addAll(layoutZi(b, x, y + h1 + h2, w, h3, depth + 1, TopMiddleBottom.BOTTOM, null));
            }

            case FullEnclosure(var outer, var inner) -> {
                String outerGlyph = glyphOf(outer);
                result.add(new Block(outerGlyph, x, y, w, h, depth + 1, FullEnclosure.OUTER));
                double mx = w * ENCL_MARGIN, my = h * ENCL_MARGIN;
                result.addAll(layoutZi(inner, x + mx, y + my, w - 2 * mx, h - 2 * my,
                        depth + 1, FullEnclosure.INNER, null));
            }

            case SemiEnclosureBottomLeft(var wrapper, var content) -> {
                String wGlyph = glyphOf(wrapper);
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, SemiEnclosureBottomLeft.WRAPPER));
                double contentW = Math.sqrt(0.50);
                double contentH = contentW * 1.15;
                double insetL = w * (1 - contentW);
                double insetB = h * (1 - contentH);
                result.addAll(layoutZi(content, x + insetL, y, w - insetL, h - insetB,
                        depth + 1, SemiEnclosureBottomLeft.CONTENT, null));
            }

            case SemiEnclosureUpperLeft(var wrapper, var content) -> {
                String wGlyph = glyphOf(wrapper);
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, SemiEnclosureUpperLeft.WRAPPER));
                double insetL = w * 0.25;
                double insetT = h * 0.30;
                result.addAll(layoutZi(content, x + insetL, y + insetT, w - insetL, h - insetT,
                        depth + 1, SemiEnclosureUpperLeft.CONTENT, null));
            }

            case SemiEnclosureUpperRight(var wrapper, var content) -> {
                String wGlyph = glyphOf(wrapper);
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, SemiEnclosureUpperRight.WRAPPER));
                double insetR = w * 0.25;
                double insetT = h * 0.30;
                result.addAll(layoutZi(content, x, y + insetT, w - insetR, h - insetT,
                        depth + 1, SemiEnclosureUpperRight.CONTENT, null));
            }

            case SemiEnclosureTopThree(var wrapper, var content) -> {
                String wGlyph = glyphOf(wrapper);
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, SemiEnclosureTopThree.WRAPPER));
                double insetX = w * 0.12;
                double insetT = h * 0.18;
                result.addAll(layoutZi(content, x + insetX, y + insetT, w - 2 * insetX, h - insetT,
                        depth + 1, SemiEnclosureTopThree.CONTENT, null));
            }

            case SemiEnclosureBottomThree(var wrapper, var content) -> {
                String wGlyph = glyphOf(wrapper);
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, SemiEnclosureBottomThree.WRAPPER));
                double insetX = w * 0.12;
                double insetB = h * 0.25;
                result.addAll(layoutZi(content, x + insetX, y, w - 2 * insetX, h - insetB,
                        depth + 1, SemiEnclosureBottomThree.CONTENT, null));
            }

            case SemiEnclosureLeftThree(var wrapper, var content) -> {
                String wGlyph = glyphOf(wrapper);
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, SemiEnclosureLeftThree.WRAPPER));
                double insetL = w * 0.18;
                double insetY = h * 0.12;
                result.addAll(layoutZi(content, x + insetL, y + insetY, w - insetL, h - 2 * insetY,
                        depth + 1, SemiEnclosureLeftThree.CONTENT, null));
            }

            case Mosaic(var element) -> {
                double topH = h / 3;
                double botH = h - topH;
                double halfW = w / 2;
                result.addAll(layoutZi(element, x, y, w, topH,
                        depth + 1, Mosaic.TOP, null));
                result.addAll(layoutZi(element, x, y + topH, halfW, botH,
                        depth + 1, Mosaic.BOTTOM_LEFT, null));
                result.addAll(layoutZi(element, x + halfW, y + topH, halfW, botH,
                        depth + 1, Mosaic.BOTTOM_RIGHT, null));
            }
        }

        return result;
    }

    // ════════════════════════════════════════════════════════════
    //  Politeness-based split calculations
    // ════════════════════════════════════════════════════════════

    /**
     * Compute two-way split shares from politeness levels.
     *
     * @return double[2] — shares for A and B, summing to 1.0
     */
    private static double[] twoWaySplit(Politeness a, Politeness b) {
        int gap = b.ordinal() - a.ordinal();
        double aShare = Math.max(0.15, Math.min(0.85, 0.5 + gap * STEP));
        return new double[]{aShare, 1.0 - aShare};
    }

    /**
     * Compute three-way split shares using inverse-weight from politeness ordinal.
     *
     * <p>Each component's weight = (MAX_ORDINAL + 1) - ordinal.
     * Shares are proportional to weights.</p>
     *
     * @return double[3] — shares for A, B, C, summing to 1.0
     */
    private static double[] threeWaySplit(Politeness a, Politeness b, Politeness c) {
        double wa = (MAX_ORDINAL + 1) - a.ordinal();
        double wb = (MAX_ORDINAL + 1) - b.ordinal();
        double wc = (MAX_ORDINAL + 1) - c.ordinal();
        double total = wa + wb + wc;
        return new double[]{wa / total, wb / total, wc / total};
    }

    // ════════════════════════════════════════════════════════════
    //  Politeness extraction
    // ════════════════════════════════════════════════════════════

    /**
     * Get the effective politeness for a Zi at a given role.
     *
     * <p>For SingularZi: checks hint's BlockHint.politeness first,
     * then falls back to component.politeness(role).</p>
     * <p>For ComposedZi: NEUTRAL (compositions don't have intrinsic politeness).</p>
     */
    private static Politeness politenessOf(Zi node, BlockRole role) {
        if (node instanceof SingularZi sz) {
            var hint = sz.hintFor(role);
            if (hint != null && hint.bh() != null && hint.bh().politeness() != null) {
                return hint.bh().politeness();
            }
            return sz.politeness(role);
        }
        return Politeness.NEUTRAL;
    }

    private static String glyphOf(Zi node) {
        return node.glyph();
    }
}
