package kranji.json.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Wire form of the source-code character representation of a {@link ComposedZiJson}.
 *
 * <ul>
 *   <li>{@code LITERAL}  — the glyph appears directly in source (e.g. {@code "清"})</li>
 *   <li>{@code UNICODE}  — a Unicode escape was used (e.g. {@code "\u6E05"})</li>
 * </ul>
 *
 * <p>Both forms resolve to the same glyph at runtime; the distinction exists for
 * lossless round-trip of the original authoring style.</p>
 */
public enum ZiCharForm {
    LITERAL,
    UNICODE;

    @JsonValue
    public String wire() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static ZiCharForm fromWire(String s) {
        if (s == null) return null;
        return ZiCharForm.valueOf(s.toUpperCase());
    }
}
