package kranji.graph;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Labels for directed edges in the character navigation graph.
 */
public enum EdgeLabel {

    // ── Structural edges (leaf ↔ leaf) ──────────────────────────────────

    /** char → component (with position metadata). */
    @JsonProperty("composed_of")       COMPOSED_OF,

    /** component → char (inverse of COMPOSED_OF). */
    @JsonProperty("appears_in")        APPEARS_IN,

    // ── Spoke edges (leaf → hub) ────────────────────────────────────────

    /** char → PinyinHub. */
    @JsonProperty("has_pinyin")        HAS_PINYIN,

    /** char → InitialHub. */
    @JsonProperty("has_initial")       HAS_INITIAL,

    /** char → FinalHub. */
    @JsonProperty("has_final")         HAS_FINAL,

    /** char → PhoneticHub (the 声旁 of a 形声 character). */
    @JsonProperty("has_phonetic_part") HAS_PHONETIC_PART,

    /** char → SemanticHub (the 形旁 of a 形声 character). */
    @JsonProperty("has_semantic_part") HAS_SEMANTIC_PART,

    /** char → SemanticFieldHub. */
    @JsonProperty("in_semantic_field") IN_SEMANTIC_FIELD,

    // ── Hub hierarchy edges (hub → hub) ─────────────────────────────────

    /** PinyinHub → InitialHub. */
    @JsonProperty("refines_initial")   REFINES_INITIAL,

    /** PinyinHub → FinalHub. */
    @JsonProperty("refines_final")     REFINES_FINAL,

    /** SemanticHub → SemanticFieldHub. */
    @JsonProperty("belongs_to_field")  BELONGS_TO_FIELD,

    // ── Derivation edges (leaf → leaf, rare) ────────────────────────────

    /** part → char (e.g. 氵 → 水). */
    @JsonProperty("part_derived_from") PART_DERIVED_FROM,

    /** char ↔ char (转注, symmetric). */
    @JsonProperty("cognate_of")        COGNATE_OF,

    /** char → char (假借, directed). */
    @JsonProperty("loan_of")           LOAN_OF
}
