package kranji.pinyin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A fully decomposed Mandarin syllable: initial (声母) + final (韵母) + tone (声调).
 *
 * @param initial the onset consonant
 * @param fin     the final (韵母), named {@code fin} to avoid the Java reserved word
 * @param tone    the tone
 */
public record PinyinSyllable(
        Initial initial,
        @JsonProperty("final") Final fin,
        Tone tone
) {

    /**
     * Returns the numbered-tone ASCII representation (e.g. "qing1").
     * Used as the natural key for PinyinHub vertex IDs.
     */
    public String numberedTone() {
        return initial.pinyin() + fin.spelling() + tone.number();
    }

    /** Returns the pinyin spelling without tone number (e.g. "qing"). */
    public String base() {
        return initial.pinyin() + fin.spelling();
    }
}
