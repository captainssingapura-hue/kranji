package kranji.graph;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Positional metadata for composition edges, describing where a component
 * sits within its parent character.
 */
public enum Position {

    // ── Binary splits ───────────────────────────────────────────────────

    @JsonProperty("left")             POS_LEFT,
    @JsonProperty("right")            POS_RIGHT,
    @JsonProperty("top")              POS_TOP,
    @JsonProperty("bottom")           POS_BOTTOM,

    // ── Ternary splits ──────────────────────────────────────────────────

    @JsonProperty("left_ternary")     POS_LEFT_TERNARY,
    @JsonProperty("middle")           POS_MIDDLE,
    @JsonProperty("right_ternary")    POS_RIGHT_TERNARY,
    @JsonProperty("top_ternary")      POS_TOP_TERNARY,
    @JsonProperty("middle_v")         POS_MIDDLE_V,
    @JsonProperty("bottom_ternary")   POS_BOTTOM_TERNARY,

    // ── Enclosure ───────────────────────────────────────────────────────

    @JsonProperty("outer")            POS_OUTER,
    @JsonProperty("inner")            POS_INNER,
    @JsonProperty("wrapper")          POS_WRAPPER,
    @JsonProperty("content")          POS_CONTENT
}
