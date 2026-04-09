package kranji.layout;

import kranji.classification.CharacterComposition;
import kranji.classification.CharacterComposition.*;
import kranji.classification.StructuralNode;
import kranji.component.Component;
import kranji.entry.ChineseCharacterEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Recursively partitions a character's unit square [0,1]² into blocks
 * for each leaf component, based on the composition structure and
 * component metrics.
 *
 * <p>The algorithm is purely top-down: starting from the full [0,1]²
 * bounding box, each composition type subdivides its region among its
 * children according to their {@link ComponentMetrics} weights. Leaf
 * components (Part / Zi) produce a single {@link Block}.</p>
 *
 * <h3>Partition rules by composition type:</h3>
 * <ul>
 *   <li><b>LeftRight</b> — horizontal split by width weights</li>
 *   <li><b>TopBottom</b> — vertical split by height weights</li>
 *   <li><b>LeftMiddleRight</b> — three-way horizontal split</li>
 *   <li><b>TopMiddleBottom</b> — three-way vertical split</li>
 *   <li><b>FullEnclosure</b> — outer frame + centered inner (with margin)</li>
 *   <li><b>Semi-enclosures</b> — wrapper + content with type-specific insets</li>
 *   <li><b>Mosaic</b> — triangular arrangement (top-center, bottom-left, bottom-right)</li>
 * </ul>
 */
public final class BlockLayoutEngine {

    /** Margin fraction for full enclosure inner content. */
    private static final double ENCL_MARGIN = 0.13;

    /**
     * Compression factor for single-leaf components with default (SQUARE)
     * metrics when placed beside a multi-block composition.
     *
     * <p>In Chinese calligraphy, simpler components yield space to more
     * complex siblings (避让原则 — the "yielding" principle). A lone 日
     * beside a stacked 音 (= 立 + 日) should be narrower and give way.</p>
     *
     * <p>Only applied to leaves whose registered weight is SQUARE (1.0);
     * components that already have explicit narrow/short metrics (亻, 氵,
     * 宀, etc.) are left unchanged.</p>
     */
    private static final double LEAF_COMPRESSION = 0.65;

    /**
     * Height-weight boost for top components in a top-bottom split.
     *
     * <p>In Chinese calligraphy the top component typically occupies more
     * vertical space than the bottom (e.g. 立 over 日 in 音). When both
     * components have equal default height weights, the top's weight is
     * multiplied by this factor to give it a larger share.</p>
     */
    private static final double TOP_BIAS = 1.20;

    private BlockLayoutEngine() {}

    /**
     * Compute the block layout for a character entry, with gravity centering.
     *
     * @return an ordered list of {@link Block}s, one per leaf component
     */

    // ════════════════════════════════════════════════════════════
    //  Block count estimation
    // ════════════════════════════════════════════════════════════

    /**
     * Recursively count the total number of leaf blocks a structural node
     * would produce. Used as weight for proportional splits — subtrees
     * with more blocks get more space.
     */
    static int countBlocks(StructuralNode node) {
        if (node instanceof HintedComponent) {
            return 1;
        }
        if (node instanceof Component) {
            return 1;
        }
        if (node instanceof CharacterComposition comp) {
            return switch (comp) {
                case Singular() -> 1;
                case LeftRight(var l, var r) -> countBlocks(l) + countBlocks(r);
                case TopBottom(var t, var b) -> countBlocks(t) + countBlocks(b);
                case LeftMiddleRight(var l, var m, var r) ->
                        countBlocks(l) + countBlocks(m) + countBlocks(r);
                case TopMiddleBottom(var t, var m, var b) ->
                        countBlocks(t) + countBlocks(m) + countBlocks(b);
                case FullEnclosure(var o, var i) -> 1 + countBlocks(i); // outer = 1 frame block
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
        List<Block> raw = layoutNode(entry.composition(), 0, 0, 1, 1, 0, BlockRole.SINGULAR, entry.character());
        return applyGravity(raw);
    }

    // ════════════════════════════════════════════════════════════
    //  Gravity centering
    // ════════════════════════════════════════════════════════════

    /**
     * Shift all blocks so the combined center of gravity sits at the
     * center of the [0,1]² canvas.
     *
     * <p>Mass = block area (w × h). CG = area-weighted average of block
     * centers. Wrapper/frame blocks are excluded from the mass calculation
     * (they overlap content) but are shifted along with everything else.</p>
     *
     * <p>The shift is clamped so no block moves outside [0,1]².</p>
     */
    static List<Block> applyGravity(List<Block> blocks) {
        if (blocks.isEmpty()) return blocks;

        // Compute CG from content blocks only (exclude wrappers to avoid double-counting)
        double totalMass = 0;
        double cgX = 0, cgY = 0;
        for (Block b : blocks) {
            if (b.role() == BlockRole.WRAPPER || b.role() == BlockRole.OUTER_FRAME) continue;
            double mass = b.area();
            cgX += b.cx() * mass;
            cgY += b.cy() * mass;
            totalMass += mass;
        }
        if (totalMass == 0) return blocks;
        cgX /= totalMass;
        cgY /= totalMass;

        // Desired shift to center CG at (0.5, 0.5)
        double dx = 0.5 - cgX;
        double dy = 0.5 - cgY;

        // Clamp shift so no block goes out of bounds
        double minX = Double.MAX_VALUE, maxXW = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE, maxYH = Double.MIN_VALUE;
        for (Block b : blocks) {
            minX = Math.min(minX, b.x());
            minY = Math.min(minY, b.y());
            maxXW = Math.max(maxXW, b.x() + b.w());
            maxYH = Math.max(maxYH, b.y() + b.h());
        }
        dx = Math.max(dx, -minX);           // don't push left edge below 0
        dx = Math.min(dx, 1.0 - maxXW);     // don't push right edge above 1
        dy = Math.max(dy, -minY);            // don't push top edge below 0
        dy = Math.min(dy, 1.0 - maxYH);     // don't push bottom edge above 1

        // Skip if shift is negligible
        if (Math.abs(dx) < 0.001 && Math.abs(dy) < 0.001) return blocks;

        double fdx = dx, fdy = dy;
        return blocks.stream()
                .map(b -> new Block(b.glyph(), b.x() + fdx, b.y() + fdy, b.w(), b.h(), b.depth(), b.role()))
                .toList();
    }

    // ════════════════════════════════════════════════════════════
    //  Glyph attraction
    // ════════════════════════════════════════════════════════════

    // ════════════════════════════════════════════════════════════
    //  Recursive dispatch
    // ════════════════════════════════════════════════════════════

    private static List<Block> layoutNode(
            StructuralNode node,
            double x, double y, double w, double h,
            int depth, BlockRole role, String fallbackGlyph
    ) {
        if (node instanceof HintedComponent hc) {
            var hint = hc.hints().get(role);
            String glyph = (hint != null && hint.glyph() != null) ? hint.glyph() : hc.glyph();
            return List.of(new Block(glyph, x, y, w, h, depth, role));
        }
        if (node instanceof Component comp) {
            return List.of(new Block(comp.glyph(), x, y, w, h, depth, role));
        }
        if (node instanceof CharacterComposition comp) {
            return layoutComposition(comp, x, y, w, h, depth, fallbackGlyph);
        }
        return List.of();
    }

    private static List<Block> layoutComposition(
            CharacterComposition comp,
            double x, double y, double w, double h,
            int depth, String fallbackGlyph
    ) {
        List<Block> result = new ArrayList<>();

        switch (comp) {
            case Singular() ->
                    result.add(new Block(fallbackGlyph != null ? fallbackGlyph : "?", x, y, w, h, depth, BlockRole.SINGULAR));

            case LeftRight(var left, var right) -> {
                double lw = weightW(left, BlockRole.LEFT);
                double rw = weightW(right, BlockRole.RIGHT);
                int lBlocks = countBlocks(left), rBlocks = countBlocks(right);
                // Yielding: single-leaf with default weight compresses beside complex sibling
                // Skip if the component has explicit role-specific metrics or instance hints
                if (!hasHintOrRoleMetrics(left, BlockRole.LEFT) && lBlocks == 1 && rBlocks > 1 && lw >= 0.9)
                    lw *= LEAF_COMPRESSION;
                if (!hasHintOrRoleMetrics(right, BlockRole.RIGHT) && rBlocks == 1 && lBlocks > 1 && rw >= 0.9)
                    rw *= LEAF_COMPRESSION;
                double total = lw + rw;
                double splitX = w * lw / total;
                result.addAll(layoutNode(left, x, y, splitX, h, depth + 1, BlockRole.LEFT, null));
                result.addAll(layoutNode(right, x + splitX, y, w - splitX, h, depth + 1, BlockRole.RIGHT, null));
            }

            case TopBottom(var top, var bottom) -> {
                double th = weightH(top, BlockRole.TOP);
                double bh = weightH(bottom, BlockRole.BOTTOM);
                int tBlocks = countBlocks(top), bBlocks = countBlocks(bottom);
                boolean tExplicit = hasHintOrRoleMetrics(top, BlockRole.TOP);
                boolean bExplicit = hasHintOrRoleMetrics(bottom, BlockRole.BOTTOM);
                // Yielding: single-leaf with default weight compresses beside complex sibling
                if (!tExplicit && tBlocks == 1 && bBlocks > 1 && th >= 0.9) th *= LEAF_COMPRESSION;
                if (!bExplicit && bBlocks == 1 && tBlocks > 1 && bh >= 0.9) bh *= LEAF_COMPRESSION;
                // Top bias: top component gets more vertical space (only when no explicit metrics)
                if (!tExplicit && !bExplicit && th >= 0.9 && bh >= 0.9) th *= TOP_BIAS;
                double total = th + bh;
                double splitY = h * th / total;
                result.addAll(layoutNode(top, x, y, w, splitY, depth + 1, BlockRole.TOP, null));
                result.addAll(layoutNode(bottom, x, y + splitY, w, h - splitY, depth + 1, BlockRole.BOTTOM, null));
            }

            case LeftMiddleRight(var l, var m, var r) -> {
                // Width proportional to stroke count
                double sl = countBlocks(l), sm = countBlocks(m), sr = countBlocks(r);
                double total = sl + sm + sr;
                double w1 = w * sl / total;
                double w2 = w * sm / total;
                double w3 = w * sr / total;
                result.addAll(layoutNode(l, x, y, w1, h, depth + 1, BlockRole.LEFT_OF_THREE, null));
                result.addAll(layoutNode(m, x + w1, y, w2, h, depth + 1, BlockRole.MIDDLE_H, null));
                result.addAll(layoutNode(r, x + w1 + w2, y, w3, h, depth + 1, BlockRole.RIGHT_OF_THREE, null));
            }

            case TopMiddleBottom(var t, var m, var b) -> {
                double th = weightH(t, BlockRole.TOP_OF_THREE);
                double mh = weightH(m, BlockRole.MIDDLE_V);
                double bh = weightH(b, BlockRole.BOTTOM_OF_THREE);
                double total = th + mh + bh;
                double h1 = h * th / total;
                double h2 = h * mh / total;
                double h3 = h * bh / total;
                result.addAll(layoutNode(t, x, y, w, h1, depth + 1, BlockRole.TOP_OF_THREE, null));
                result.addAll(layoutNode(m, x, y + h1, w, h2, depth + 1, BlockRole.MIDDLE_V, null));
                result.addAll(layoutNode(b, x, y + h1 + h2, w, h3, depth + 1, BlockRole.BOTTOM_OF_THREE, null));
            }

            case FullEnclosure(var outer, var inner) -> {
                // Outer frame fills the full box
                String outerGlyph = glyphOf(outer);
                result.add(new Block(outerGlyph, x, y, w, h, depth + 1, BlockRole.OUTER_FRAME));
                // Inner content with margin
                double mx = w * ENCL_MARGIN, my = h * ENCL_MARGIN;
                result.addAll(layoutNode(inner, x + mx, y + my, w - 2 * mx, h - 2 * my,
                        depth + 1, BlockRole.INNER, null));
            }

            case SemiEnclosureBottomLeft(var wrapper, var content) -> {
                // Wrapper (e.g. 辶): L-shape — complement of a top-right square.
                // Content anchored at top-right. Width ≈ 71%, height ≈ 82% (15% taller).
                // Leaves an L-shaped region for 辶 along left and bottom.
                String wGlyph = glyphOf(wrapper);
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, BlockRole.WRAPPER));
                double contentW = Math.sqrt(0.50);      // ≈ 0.707
                double contentH = contentW * 1.15;       // ≈ 0.813
                double insetL = w * (1 - contentW);
                double insetB = h * (1 - contentH);
                result.addAll(layoutNode(content, x + insetL, y, w - insetL, h - insetB,
                        depth + 1, BlockRole.CONTENT, null));
            }

            case SemiEnclosureUpperLeft(var wrapper, var content) -> {
                // Wrapper (e.g. 疒): ⌐ shape along top and left
                String wGlyph = glyphOf(wrapper);
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, BlockRole.WRAPPER));
                double insetL = w * 0.25;
                double insetT = h * 0.30;
                result.addAll(layoutNode(content, x + insetL, y + insetT, w - insetL, h - insetT,
                        depth + 1, BlockRole.CONTENT, null));
            }

            case SemiEnclosureUpperRight(var wrapper, var content) -> {
                // Wrapper (e.g. 勹): ⌐ mirrored, top and right
                String wGlyph = glyphOf(wrapper);
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, BlockRole.WRAPPER));
                double insetR = w * 0.25;
                double insetT = h * 0.30;
                result.addAll(layoutNode(content, x, y + insetT, w - insetR, h - insetT,
                        depth + 1, BlockRole.CONTENT, null));
            }

            case SemiEnclosureTopThree(var wrapper, var content) -> {
                // Wrapper (e.g. 门/冂): ∏ shape — top + left + right
                String wGlyph = glyphOf(wrapper);
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, BlockRole.WRAPPER));
                double insetX = w * 0.12;
                double insetT = h * 0.18;
                result.addAll(layoutNode(content, x + insetX, y + insetT, w - 2 * insetX, h - insetT,
                        depth + 1, BlockRole.CONTENT, null));
            }

            case SemiEnclosureBottomThree(var wrapper, var content) -> {
                // Wrapper (e.g. 凵): ∪ shape — bottom + left + right
                String wGlyph = glyphOf(wrapper);
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, BlockRole.WRAPPER));
                double insetX = w * 0.12;
                double insetB = h * 0.25;
                result.addAll(layoutNode(content, x + insetX, y, w - 2 * insetX, h - insetB,
                        depth + 1, BlockRole.CONTENT, null));
            }

            case SemiEnclosureLeftThree(var wrapper, var content) -> {
                // Wrapper (e.g. 匚): ⊏ shape — top + left + bottom
                String wGlyph = glyphOf(wrapper);
                result.add(new Block(wGlyph, x, y, w, h, depth + 1, BlockRole.WRAPPER));
                double insetL = w * 0.18;
                double insetY = h * 0.12;
                result.addAll(layoutNode(content, x + insetL, y + insetY, w - insetL, h - 2 * insetY,
                        depth + 1, BlockRole.CONTENT, null));
            }

            case Mosaic(var element) -> {
                // Top-bottom: top full width, bottom is equal left-right split
                // Height: top 1/3, bottom 2/3
                double topH = h / 3;
                double botH = h - topH;
                double halfW = w / 2;
                result.addAll(layoutNode(element, x, y, w, topH,
                        depth + 1, BlockRole.TOP, null));
                result.addAll(layoutNode(element, x, y + topH, halfW, botH,
                        depth + 1, BlockRole.LEFT, null));
                result.addAll(layoutNode(element, x + halfW, y + topH, halfW, botH,
                        depth + 1, BlockRole.RIGHT, null));
            }
        }

        return result;
    }

    // ════════════════════════════════════════════════════════════
    //  Metrics extraction
    // ════════════════════════════════════════════════════════════

    /**
     * Get the effective width weight for a node at a given position.
     *
     * <p>Priority chain:
     * HintedComponent instance hint → ComponentMetrics(glyph, role) →
     * ComponentMetrics(glyph) → SQUARE.</p>
     */
    private static double weightW(StructuralNode node, BlockRole role) {
        if (node instanceof HintedComponent hc) {
            var hint = hc.hints().get(role);
            if (hint != null && !Double.isNaN(hint.widthWeight()))
                return hint.widthWeight();
            return ComponentMetrics.forGlyph(hc.glyph(), role).widthWeight();
        }
        if (node instanceof Component comp) {
            return ComponentMetrics.forGlyph(comp.glyph(), role).widthWeight();
        }
        return ComponentMetrics.SQUARE.widthWeight();
    }

    /**
     * Get the effective height weight for a node at a given position.
     */
    private static double weightH(StructuralNode node, BlockRole role) {
        if (node instanceof HintedComponent hc) {
            var hint = hc.hints().get(role);
            if (hint != null && !Double.isNaN(hint.heightWeight()))
                return hint.heightWeight();
            return ComponentMetrics.forGlyph(hc.glyph(), role).heightWeight();
        }
        if (node instanceof Component comp) {
            return ComponentMetrics.forGlyph(comp.glyph(), role).heightWeight();
        }
        return ComponentMetrics.SQUARE.heightWeight();
    }

    /**
     * True if the node has an instance hint or role-specific metrics for this role.
     * Used to skip global heuristics (LEAF_COMPRESSION, TOP_BIAS) when explicit
     * scaling is available.
     */
    private static boolean hasHintOrRoleMetrics(StructuralNode node, BlockRole role) {
        if (node instanceof HintedComponent hc) {
            if (hc.hints().containsKey(role)) return true;
            return hasRoleMetrics(hc.glyph(), role);
        }
        if (node instanceof Component comp) {
            return hasRoleMetrics(comp.glyph(), role);
        }
        return false;
    }

    private static boolean hasRoleMetrics(String glyph, BlockRole role) {
        // Role-specific metrics exist if forGlyph(glyph, role) differs from forGlyph(glyph)
        return ComponentMetrics.forGlyph(glyph, role) != ComponentMetrics.forGlyph(glyph);
    }

    private static String glyphOf(StructuralNode node) {
        if (node instanceof HintedComponent hc) {
            return hc.glyph();
        }
        if (node instanceof Component comp) {
            return comp.glyph();
        }
        return null;
    }
}
