package kranji.component;

import kranji.classification.StructuralNode;

/**
 * A leaf node in the character structure tree — an atomic, non-decomposable
 * building block that carries a visual glyph.
 *
 * <p>Both {@link Component} (Part/Zi) and {@link BasicComponent} extend this
 * marker, enabling shared handling in the layout engine and wrapper types
 * like {@link kranji.layout.HintedComponent}.</p>
 */
public interface LeafNode extends StructuralNode {

    /** The visual glyph of this component (e.g. "氵", "日", "青"). */
    String glyph();
}
