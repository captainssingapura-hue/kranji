package kranji.pinyin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 韵腹 — The nucleus (main vowel) of a Mandarin final.
 */
public enum Body {

    @JsonProperty("a")   A("a"),
    @JsonProperty("o")   O("o"),
    @JsonProperty("e")   E("e"),
    // E_CARON is the phonemically-distinct "ê" nucleus used in "ie" / "üe".
    // Its orthographic symbol is "e" — pinyin writes "ie", "üe", "jue", not
    // "iê", "üê". The ê distinction is preserved in the enum value so
    // graph-level grouping and phonetic analysis can still tell (I,E) from
    // (I,E_CARON), but the display spelling is plain "e".
    @JsonProperty("eh")  E_CARON("e"),
    @JsonProperty("i")   I("i"),
    @JsonProperty("u")   U("u"),
    @JsonProperty("v")   V("ü"),
    @JsonProperty("er")  ER("er"),

    /**
     * 空韵 — The null nucleus for syllabic fricatives/approximants
     * (the "-i" in zī, chī, sī, rì is acoustic, not a true vowel).
     */
    @JsonProperty("null") NULL("");

    private final String symbol;

    Body(String symbol) {
        this.symbol = symbol;
    }

    /** Returns the phonetic symbol for this nucleus. */
    public String symbol() {
        return symbol;
    }
}
