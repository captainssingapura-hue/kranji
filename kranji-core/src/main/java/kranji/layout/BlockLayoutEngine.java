package kranji.layout;

import kranji.classification.BlockRole;
import kranji.zi.*;
import kranji.zi.ComposedBlock.*;

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
     * Recursively count the total number of leaf blocks a BlockStructure
     * would produce.
     */
    static int countBlocks(BlockStructure node) {
        if (node instanceof SingularBlock) {
            return 1;
        }
        if (node instanceof ComposedBlock comp) {
            return switch (comp) {
                case LeftRight(var l, var r) -> countComposable(l) + countComposable(r);
                case TopBottom(var t, var b) -> countComposable(t) + countComposable(b);
                case LeftMiddleRight(var l, var m, var r) ->
                        countComposable(l) + countComposable(m) + countComposable(r);
                case TopMiddleBottom(var t, var m, var b) ->
                        countComposable(t) + countComposable(m) + countComposable(b);
                case FullEnclosure(var o, var i) -> 1 + countComposable(i);
                case SemiEnclosureUpperLeft(var w, var c) -> 1 + countComposable(c);
                case SemiEnclosureUpperRight(var w, var c) -> 1 + countComposable(c);
                case SemiEnclosureBottomLeft(var w, var c) -> 1 + countComposable(c);
                case SemiEnclosureTopThree(var w, var c) -> 1 + countComposable(c);
                case SemiEnclosureBottomThree(var w, var c) -> 1 + countComposable(c);
                case SemiEnclosureLeftThree(var w, var c) -> 1 + countComposable(c);
            };
        }
        return 1;
    }

    private static int countComposable(Composable c) {
        return countBlocks(resolve(c));
    }

    public static List<Block> layout(Zi entry) {
        List<Block> raw = layoutBlock(entry.structure(), 0, 0, 1, 1, 0, null, entry.character());
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
    //  Composable resolution
    // ════════════════════════════════════════════════════════════

    /**
     * Resolve a Composable to a BlockStructure for layout.
     */
    private static BlockStructure resolve(Composable c) {
        return switch (c) {
            case Composable.OfZi(var zi) -> zi.structure();
            case Composable.OfBlock(var block) -> block;
        };
    }

    // ════════════════════════════════════════════════════════════
    //  Recursive dispatch
    // ════════════════════════════════════════════════════════════

    private static List<Block> layoutBlock(
            BlockStructure node,
            double x, double y, double w, double h,
            int depth, BlockRole role, String fallbackGlyph
    ) {
        // SingularBlock leaf — resolve hints and create a single block.
        if (node instanceof SingularBlock sb) {
            if (role != null) {
                HintedComponent hc = sb.as(role);
                String glyph = hc.glyph();
                LayoutHint hint = hc.hint();
                return List.of(new Block(glyph, x, y, w, h, depth, role, hint));
            }
            // No role (top-level singular entry) — use glyph directly.
            String glyph = (fallbackGlyph != null) ? fallbackGlyph : sb.glyph();
            return List.of(new Block(glyph, x, y, w, h, depth, new SingularRole()));
        }
        if (node instanceof ComposedBlock comp) {
            return layoutComposition(comp, x, y, w, h, depth, fallbackGlyph);
        }
        return List.of();
    }

    private static List<Block> layoutComposable(
            Composable c,
            double x, double y, double w, double h,
            int depth, BlockRole role, String fallbackGlyph
    ) {
        return layoutBlock(resolve(c), x, y, w, h, depth, role, fallbackGlyph);
    }

    /** Placeholder role for top-level singular entries. */
    record SingularRole() implements BlockRole {
        @Override public String label() { return "singular"; }
    }

    private static List<Block> layoutComposition(
            ComposedBlock comp,
            double x, double y, double w, double h,
            int depth, String fallbackGlyph
    ) {
        List<Block> result = new ArrayList<>();

        switch (comp) {
            case LeftRight(var left, var right) -> {
                Politeness lp = politenessOf(resolve(left), LeftRight.LEFT);
                Politeness rp = politenessOf(resolve(right), LeftRight.RIGHT);
                double[] shares = twoWaySplit(lp, rp);
                double splitX = w * shares[0];
                result.addAll(layoutComposable(left, x, y, splitX, h, depth + 1, LeftRight.LEFT, null));
                result.addAll(layoutComposable(right, x + splitX, y, w - splitX, h, depth + 1, LeftRight.RIGHT, null));
            }

            case TopBottom(var top, var bottom) -> {
                Politeness tp = politenessOf(resolve(top), TopBottom.TOP);
                Politeness bp = politenessOf(resolve(bottom), TopBottom.BOTTOM);
                double[] shares = twoWaySplit(tp, bp);
                double splitY = h * shares[0];
                result.addAll(layoutComposable(top, x, y, w, splitY, depth + 1, TopBottom.TOP, null));
                result.addAll(layoutComposable(bottom, x, y + splitY, w, h - splitY, depth + 1, TopBottom.BOTTOM, null));
            }

            case LeftMiddleRight(var l, var m, var r) -> {
                Politeness lp = politenessOf(resolve(l), LeftMiddleRight.LEFT);
                Politeness mp = politenessOf(resolve(m), LeftMiddleRight.MIDDLE);
                Politeness rp = politenessOf(resolve(r), LeftMiddleRight.RIGHT);
                double[] shares = threeWaySplit(lp, mp, rp);
                double w1 = w * shares[0];
                double w2 = w * shares[1];
                double w3 = w * shares[2];
                result.addAll(layoutComposable(l, x, y, w1, h, depth + 1, LeftMiddleRight.LEFT, null));
                result.addAll(layoutComposable(m, x + w1, y, w2, h, depth + 1, LeftMiddleRight.MIDDLE, null));
                result.addAll(layoutComposable(r, x + w1 + w2, y, w3, h, depth + 1, LeftMiddleRight.RIGHT, null));
            }

            case TopMiddleBottom(var t, var m, var b) -> {
                Politeness tp = politenessOf(resolve(t), TopMiddleBottom.TOP);
                Politeness mp = politenessOf(resolve(m), TopMiddleBottom.MIDDLE);
                Politeness bp = politenessOf(resolve(b), TopMiddleBottom.BOTTOM);
                double[] shares = threeWaySplit(tp, mp, bp);
                double h1 = h * shares[0];
                double h2 = h * shares[1];
                double h3 = h * shares[2];
                result.addAll(layoutComposable(t, x, y, w, h1, depth + 1, TopMiddleBottom.TOP, null));
                result.addAll(layoutComposable(m, x, y + h1, w, h2, depth + 1, TopMiddleBottom.MIDDLE, null));
                result.addAll(layoutComposable(b, x, y + h1 + h2, w, h3, depth + 1, TopMiddleBottom.BOTTOM, null));
            }

            case FullEnclosure(var outer, var inner) -> {
                String outerGlyph = outer.glyph();
                result.add(new Block(outerGlyph, x, y, w, h, depth + 1, FullEnclosure.OUTER));
                double mx = w * ENCL_MARGIN, my = h * ENCL_MARGIN;
                result.addAll(layoutComposable(inner, x + mx, y + my, w - 2 * mx, h - 2 * my,
                        depth + 1, FullEnclosure.INNER, null));
            }

            case SemiEnclosureBottomLeft(var wrapper, var content) -> {
                String wGlyph = wrapper.glyph();
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, SemiEnclosureBottomLeft.WRAPPER));
                double contentW = Math.sqrt(0.50);
                double contentH = contentW * 1.15;
                double insetL = w * (1 - contentW);
                double insetB = h * (1 - contentH);
                result.addAll(layoutComposable(content, x + insetL, y, w - insetL, h - insetB,
                        depth + 1, SemiEnclosureBottomLeft.CONTENT, null));
            }

            case SemiEnclosureUpperLeft(var wrapper, var content) -> {
                String wGlyph = wrapper.glyph();
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, SemiEnclosureUpperLeft.WRAPPER));
                double insetL = w * 0.25;
                double insetT = h * 0.30;
                result.addAll(layoutComposable(content, x + insetL, y + insetT, w - insetL, h - insetT,
                        depth + 1, SemiEnclosureUpperLeft.CONTENT, null));
            }

            case SemiEnclosureUpperRight(var wrapper, var content) -> {
                String wGlyph = wrapper.glyph();
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, SemiEnclosureUpperRight.WRAPPER));
                double insetR = w * 0.25;
                double insetT = h * 0.30;
                result.addAll(layoutComposable(content, x, y + insetT, w - insetR, h - insetT,
                        depth + 1, SemiEnclosureUpperRight.CONTENT, null));
            }

            case SemiEnclosureTopThree(var wrapper, var content) -> {
                String wGlyph = wrapper.glyph();
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, SemiEnclosureTopThree.WRAPPER));
                double insetX = w * 0.12;
                double insetT = h * 0.18;
                result.addAll(layoutComposable(content, x + insetX, y + insetT, w - 2 * insetX, h - insetT,
                        depth + 1, SemiEnclosureTopThree.CONTENT, null));
            }

            case SemiEnclosureBottomThree(var wrapper, var content) -> {
                String wGlyph = wrapper.glyph();
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, SemiEnclosureBottomThree.WRAPPER));
                double insetX = w * 0.12;
                double insetB = h * 0.25;
                result.addAll(layoutComposable(content, x + insetX, y, w - 2 * insetX, h - insetB,
                        depth + 1, SemiEnclosureBottomThree.CONTENT, null));
            }

            case SemiEnclosureLeftThree(var wrapper, var content) -> {
                String wGlyph = wrapper.glyph();
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, SemiEnclosureLeftThree.WRAPPER));
                double insetL = w * 0.18;
                double insetY = h * 0.12;
                result.addAll(layoutComposable(content, x + insetL, y + insetY, w - insetL, h - 2 * insetY,
                        depth + 1, SemiEnclosureLeftThree.CONTENT, null));
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
     * Get the effective politeness for a BlockStructure at a given role.
     */
    private static Politeness politenessOf(BlockStructure node, BlockRole role) {
        if (node instanceof SingularBlock sb) {
            var hint = sb.hintFor(role);
            if (hint != null && hint.bh() != null && hint.bh().politeness() != null) {
                return hint.bh().politeness();
            }
            return sb.politeness(role);
        }
        return Politeness.NEUTRAL;
    }
}
