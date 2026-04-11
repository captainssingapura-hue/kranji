package kranji.classification;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import kranji.component.LeafNode;

/**
 * 六书 — The Six Writings, modeled as a proper sum type where each variant
 * carries the etymological data specific to that category.
 *
 * <p>This is orthogonal to {@link CharacterComposition}: composition describes
 * the <em>spatial layout</em> of parts, while etymology describes <em>why</em>
 * those parts were chosen and what role each plays in the character's origin.</p>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "etymology")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EtymologicalCategory.Pictograph.class, name = "pictograph"),
        @JsonSubTypes.Type(value = EtymologicalCategory.SimpleIndicative.class, name = "simple_indicative"),
        @JsonSubTypes.Type(value = EtymologicalCategory.CompoundIndicative.class, name = "compound_indicative"),
        @JsonSubTypes.Type(value = EtymologicalCategory.PhonoSemantic.class, name = "phono_semantic"),
        @JsonSubTypes.Type(value = EtymologicalCategory.DerivativeCognate.class, name = "derivative_cognate"),
        @JsonSubTypes.Type(value = EtymologicalCategory.PhoneticLoan.class, name = "phonetic_loan")
})
public sealed interface EtymologicalCategory {

    /**
     * 象形 — Stylized drawing of what the character represents (e.g. 日, 月, 山, 水).
     */
    record Pictograph() implements EtymologicalCategory {}

    /**
     * 指事 — Abstract symbol or pictograph with an indicator mark (e.g. 上, 下, 本, 刃).
     *
     * @param indicatorDescription describes what the indicator mark conveys
     */
    record SimpleIndicative(String indicatorDescription) implements EtymologicalCategory {}

    /**
     * 会意 — Meaning derived from the combined meanings of component parts
     * (e.g. 明 = 日+月 → bright, 休 = 亻+木 → rest).
     *
     * @param meaningHint explains how the component meanings combine
     */
    record CompoundIndicative(String meaningHint) implements EtymologicalCategory {}

    /**
     * 形声 — One part hints at meaning (形旁), the other at sound (声旁).
     * Covers ~80%+ of modern characters.
     *
     * <p>The semantic and phonetic parts refer to the same components that appear
     * in the character's {@link CharacterComposition}, but here they are annotated
     * with their etymological <em>role</em>.</p>
     *
     * @param semanticPart the 形旁 — component that hints at meaning
     * @param phoneticPart the 声��� — component that hints at sound
     */
    record PhonoSemantic(LeafNode semanticPart, LeafNode phoneticPart) implements EtymologicalCategory {}

    /**
     * 转注 — Characters sharing an etymological root and mutually explanatory
     * (e.g. 老 ↔ 考).
     *
     * @param cognateGlyph the related character that shares the etymological root
     */
    record DerivativeCognate(String cognateGlyph) implements EtymologicalCategory {}

    /**
     * 假借 — Character borrowed for its sound; originally meant something else
     * (e.g. 来 originally "wheat" → borrowed for "come").
     *
     * @param originalMeaning what the character originally depicted before being borrowed
     */
    record PhoneticLoan(String originalMeaning) implements EtymologicalCategory {}
}
