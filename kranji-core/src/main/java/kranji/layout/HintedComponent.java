package kranji.layout;

import kranji.classification.BlockRole;
import kranji.classification.StructuralNode;
import kranji.component.BasicComponent;
import kranji.component.LeafNode;

/**
 * A leaf node bound to a specific structural role.
 *
 * <p>Created via {@link BasicComponent#as(BlockRole)} or
 * {@link kranji.component.Component#as(BlockRole)}. The layout engine
 * resolves the effective hint by querying the leaf's intrinsic
 * {@link BasicComponent#hintFor(BlockRole)} method.</p>
 *
 * @param leaf the wrapped leaf node (Component or BasicComponent)
 * @param role the structural position this component is bound to
 */
public record HintedComponent(
        LeafNode leaf,
        BlockRole role
) implements StructuralNode {

    /** Delegate to the wrapped leaf's glyph, with possible hint substitution. */
    public String glyph() {
        if (leaf instanceof BasicComponent bc) {
            var hint = bc.hintFor(role);
            if (hint != null && hint.glyph() != null) return hint.glyph().value();
        }
        return leaf.glyph();
    }

    /**
     * Resolve the intrinsic layout hint for the bound role.
     *
     * @return the hint from the leaf's {@code hintFor(role)}, or {@code null}
     */
    public LayoutHint hint() {
        if (leaf instanceof BasicComponent bc) {
            return bc.hintFor(role);
        }
        return null;
    }
}
