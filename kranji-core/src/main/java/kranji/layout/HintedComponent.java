package kranji.layout;

import kranji.classification.BlockRole;
import kranji.zi.SingularBlock;

/**
 * A SingularBlock bound to a specific structural role for rendering.
 *
 * <p>Created via {@link SingularBlock#as(BlockRole)}. The layout engine
 * resolves the effective hint by querying the leaf's intrinsic
 * {@link SingularBlock#hintFor(BlockRole)} method.</p>
 *
 * <p>This is a layout/rendering wrapper — it does NOT implement
 * {@link kranji.zi.Zi}.</p>
 *
 * @param leaf the wrapped SingularBlock
 * @param role the structural position this component is bound to
 */
public record HintedComponent(
        SingularBlock leaf,
        BlockRole role
) {

    /** Delegate to the wrapped leaf's glyph, with possible hint substitution. */
    public String glyph() {
        var hint = leaf.hintFor(role);
        if (hint != null && hint.glyph() != null) return hint.glyph().value();
        return leaf.glyph();
    }

    /**
     * Resolve the intrinsic layout hint for the bound role.
     *
     * @return the hint from the leaf's {@code hintFor(role)}, or {@code null}
     */
    public LayoutHint hint() {
        return leaf.hintFor(role);
    }
}
