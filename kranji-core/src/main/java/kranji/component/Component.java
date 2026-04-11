package kranji.component;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import kranji.classification.BlockRole;
import kranji.layout.HintedComponent;

/**
 * 部件 — A component that appears inside a Chinese character.
 *
 * <p>This is the foundational sum type: a component is either a {@link Part} (偏旁),
 * which exists only inside other characters, or a {@link Zi} (字), a full standalone
 * character that also serves as a component in compound characters.</p>
 *
 * <p>Key invariant: Zi ⊂ Component — a Zi can fill any Part slot,
 * but a Part can never become a Zi.</p>
 *
 * <p>Components are leaf nodes in the {@link StructuralNode} recursion —
 * they represent atomic, non-decomposable building blocks.</p>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Component.Part.class, name = "part"),
        @JsonSubTypes.Type(value = Component.Zi.class, name = "zi")
})
public sealed interface Component extends LeafNode {

    @Override String glyph();

    /**
     * Bind this component to a specific structural role.
     *
     * <p>Returns a {@link HintedComponent} that can be used anywhere
     * a {@code StructuralNode} is accepted:</p>
     * <pre>
     *   zi("日").as(LeftRight.LEFT)
     * </pre>
     */
    default HintedComponent as(BlockRole role) {
        return new HintedComponent(this, role);
    }

    /**
     * 偏旁 — A dependent component that exists only inside other characters.
     *
     * @param glyph      the visual form (e.g. "氵")
     * @param name       the conventional name (e.g. "三点水")
     * @param standalone the full standalone character this part derives from (e.g. "水")
     * @param meaning    what this part conveys semantically (e.g. "water")
     * @param pinyin     pronunciation of the standalone form (e.g. "shuǐ")
     */
    record Part(
            String glyph,
            String name,
            String standalone,
            String meaning,
            String pinyin
    ) implements Component {}

    /**
     * 字 — A full standalone character that also serves as a component.
     *
     * @param glyph the character (e.g. "日", "月", "青")
     */
    record Zi(String glyph) implements Component {}
}
