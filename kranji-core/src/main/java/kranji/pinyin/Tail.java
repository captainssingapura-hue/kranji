package kranji.pinyin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 韵尾 — The coda (optional closing consonant or glide) of a Mandarin final.
 */
public enum Tail {

    /** No coda — open syllable. */
    @JsonProperty("none")
    NONE(""),

    /** 元音尾 i — Closes with an i glide (e.g. ai, ei). */
    @JsonProperty("i")
    VOWEL_I("i"),

    /** 元音尾 u — Closes with a u glide (e.g. ao, ou). */
    @JsonProperty("u")
    VOWEL_U("u"),

    /** 前鼻尾 — Nasal coda -n. */
    @JsonProperty("n")
    N("n"),

    /** 后鼻尾 — Nasal coda -ng. */
    @JsonProperty("ng")
    NG("ng");

    private final String symbol;

    Tail(String symbol) {
        this.symbol = symbol;
    }

    /** Returns the phonetic symbol for this coda. */
    public String symbol() {
        return symbol;
    }
}
