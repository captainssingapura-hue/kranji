package kranji.layout;

import kranji.classification.BlockRole;

/**
 * A positioned rectangular region in the character layout.
 *
 * <p>All coordinates are in a normalized [0,1]² space where (0,0) is
 * the top-left corner and (1,1) is the bottom-right corner of the
 * entire character's bounding square.</p>
 *
 * <p>A Block is the output of the layout engine. Each leaf component
 * (Part or Zi) gets exactly one Block describing where it should be
 * rendered. Non-leaf compositions produce no Block themselves — they
 * only determine how their children's Blocks are positioned.</p>
 *
 * @param glyph the component's visual glyph (e.g. "氵", "青", "日")
 * @param x     left edge, normalized [0,1]
 * @param y     top edge, normalized [0,1]
 * @param w     width, normalized [0,1]
 * @param h     height, normalized [0,1]
 * @param depth nesting depth (0 = root composition's direct child)
 * @param role  the structural role this block plays in its parent
 * @param hint  layout hint for inner positioning (nullable — renderer reads scale/offset from here)
 * @param path  dotted-index path from the rendered root identifying which
 *              {@link kranji.zi.BlockStructure} this block represents.
 *              "" = root entry; "0" = first slot of root; "1.0" = first
 *              slot of second slot; etc. Used by interactive consumers
 *              (e.g. UI depth explorer) to map clicks back to the source
 *              record. Empty string is a safe default for non-interactive
 *              consumers.
 */
public record Block(
        String glyph,
        double x, double y,
        double w, double h,
        int depth,
        BlockRole role,
        LayoutHint hint,
        String path
) {

    /** Convenience constructor with no hint, default path "". */
    public Block(String glyph, double x, double y, double w, double h, int depth, BlockRole role) {
        this(glyph, x, y, w, h, depth, role, null, "");
    }

    /** Convenience constructor with hint, default path "". */
    public Block(String glyph, double x, double y, double w, double h, int depth, BlockRole role, LayoutHint hint) {
        this(glyph, x, y, w, h, depth, role, hint, "");
    }

    /** Bounding-box area (useful for sorting/debugging). */
    public double area() {
        return w * h;
    }

    /** Center x coordinate. */
    public double cx() {
        return x + w / 2;
    }

    /** Center y coordinate. */
    public double cy() {
        return y + h / 2;
    }
}
