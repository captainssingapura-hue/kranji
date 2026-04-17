package kranji.json.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wire shape for a single Mandarin syllable.
 *
 * @param initial onset consonant; empty string permitted for zero-initial syllables
 * @param fin     the final / 韵母 (spelled as one string); wire key is {@code "final"}
 *                (the Java parameter is named {@code fin} because {@code final} is reserved)
 * @param tone    tone number: 1, 2, 3, 4, or 5 for neutral tone
 */
public record PinyinJson(
        String initial,
        @JsonProperty("final") String fin,
        Integer tone
) {}
