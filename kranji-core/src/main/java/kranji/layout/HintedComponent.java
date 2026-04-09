package kranji.layout;

import kranji.classification.StructuralNode;
import kranji.component.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A component with per-position layout hints attached.
 *
 * <p>This is a product type: {@code Component × Map<BlockRole, LayoutHint>}.
 * It implements {@link StructuralNode} so it can be used anywhere a
 * plain {@link Component} would appear in a composition tree.</p>
 *
 * <p>Instance hints take the highest priority in the layout engine's
 * scaling resolution chain:</p>
 * <pre>
 *   HintedComponent hint → ComponentMetrics(glyph, role) → ComponentMetrics(glyph) → SQUARE
 * </pre>
 *
 * <p>Create via {@link Component#as(BlockRole, LayoutHint)}:</p>
 * <pre>
 *   zi("水").as(LEFT, glyphAndScale("氵", 0.45, 1.0))
 * </pre>
 *
 * @param component the wrapped component
 * @param hints     position-specific layout overrides
 */
public record HintedComponent(
        Component component,
        Map<BlockRole, LayoutHint> hints
) implements StructuralNode {

    /**
     * Add (or replace) a hint for a specific position, returning a new instance.
     */
    public HintedComponent as(BlockRole role, LayoutHint hint) {
        var map = new HashMap<>(hints);
        map.put(role, hint);
        return new HintedComponent(component, Collections.unmodifiableMap(map));
    }

    /** Delegate to the wrapped component's glyph. */
    public String glyph() {
        return component.glyph();
    }
}
