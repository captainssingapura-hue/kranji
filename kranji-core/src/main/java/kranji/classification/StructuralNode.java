package kranji.classification;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import kranji.component.Component;

/**
 * 结构节点 — The recursive building block of character structure.
 *
 * <p>A StructuralNode is either a leaf {@link Component} (an atomic part or
 * standalone character) or a {@link CharacterComposition} (a spatial layout
 * whose children are themselves StructuralNodes). This mutual recursion
 * captures the fractal nature of Chinese characters: any component slot
 * may itself be decomposed into further spatial structure.</p>
 *
 * <pre>
 *   StructuralNode = Component | CharacterComposition
 *                         ▲              │
 *                         └── references ┘  (in every positional slot)
 * </pre>
 *
 * <p>Not sealed (Java's sealed-across-packages restriction in unnamed modules
 * prevents it), but conceptually a closed sum of exactly two branches — both
 * of which <em>are</em> individually sealed. Pattern-match via
 * {@code instanceof Component} / {@code instanceof CharacterComposition}.</p>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "nodeType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Component.class, name = "component"),
        @JsonSubTypes.Type(value = CharacterComposition.class, name = "composition")
})
public interface StructuralNode {
}
