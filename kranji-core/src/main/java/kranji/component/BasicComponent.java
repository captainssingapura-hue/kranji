package kranji.component;

import kranji.classification.BlockRole;
import kranji.layout.HintedComponent;
import kranji.layout.LayoutHint;
import kranji.layout.Politeness;

/**
 * 基本部件 — A basic building block of Chinese characters.
 *
 * <p>This is the foundation of the ~500 atomic components from which all
 * Chinese characters are composed. Each component is modeled as a stateless
 * record implementing this interface, grouped into family classes by radical
 * (e.g. {@code WaterFamily}, {@code PersonFamily}).</p>
 *
 * <p>Not sealed — the set of basic components is large (~500) and open
 * for extension as more components are catalogued.</p>
 *
 * <p>Implements {@link LeafNode} so it can be used directly as a leaf
 * in composition trees ({@link kranji.classification.StructuralNode}).</p>
 *
 * <p>Layout hints are intrinsic: each component knows its own preferred
 * scaling and glyph form for every structural position via {@link #hintFor}.
 * No external hints are passed.</p>
 */
public interface BasicComponent extends LeafNode {

    /** The visual glyph form (e.g. "氵"). */
    @Override String glyph();

    /** The conventional Chinese name (e.g. "三点水"). */
    String name();

    /** The full standalone character this derives from (e.g. "水"). */
    String standalone();

    /** English semantic meaning (e.g. "water"). */
    String meaning();

    /** Pinyin of the standalone form (e.g. "shuǐ"). */
    String pinyin();

    /** Stroke count of this component form. */
    int strokes();

    /**
     * Position-specific layout hint intrinsic to this component.
     *
     * <p>Override in concrete records to provide role-aware scaling or
     * glyph substitution. Returns {@code null} by default (no special
     * handling — the layout engine uses default politeness).</p>
     *
     * @param role the structural position this component occupies
     * @return a layout hint, or {@code null} for default behavior
     */
    default LayoutHint hintFor(BlockRole role) {
        return null;
    }

    /**
     * Position-specific politeness level for this component.
     *
     * <p>Determines how much space this component yields to siblings
     * during block layout. Override in concrete records for components
     * that should be narrower or shorter in specific positions.</p>
     *
     * @param role the structural position this component occupies
     * @return the politeness level (default {@link Politeness#NEUTRAL})
     */
    default Politeness politeness(BlockRole role) {
        return Politeness.NEUTRAL;
    }

    /**
     * Bind this component to a specific structural role.
     *
     * <p>Returns a {@link HintedComponent} that carries both this leaf
     * and the bound role. The layout engine resolves the hint from
     * {@link #hintFor} at render time.</p>
     *
     * @param role the structural position (e.g. {@code LeftRight.LEFT})
     */
    default HintedComponent as(BlockRole role) {
        return new HintedComponent(this, role);
    }
}
