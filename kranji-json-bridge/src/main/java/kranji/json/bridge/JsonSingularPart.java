package kranji.json.bridge;

import kranji.zi.SingularPart;
import kranji.zi.SingularZi;

import java.util.Objects;

/**
 * Plain-data implementation of {@link SingularPart} built from the fields of
 * a {@code SingularPartJson} DTO. Immutable record.
 *
 * <p>The {@link #derivedFrom()} component is the typed {@link SingularZi}
 * this radical variant derives from (e.g. 氵 → 水). It may be {@code null}
 * if the standalone character is not available as a typed adapter at
 * construction time.</p>
 */
public record JsonSingularPart(
        String glyph,
        String name,
        int strokes,
        String pinyinText,
        String meaning,
        SingularZi derivedFrom
) implements SingularPart {

    public JsonSingularPart {
        Objects.requireNonNull(glyph, "glyph");
        name       = name       == null ? glyph : name;
        pinyinText = pinyinText == null ? "" : pinyinText;
        meaning    = meaning    == null ? "" : meaning;
    }

    /** Returns the derivation glyph if known, else falls back to this glyph. */
    @Override
    public String standalone() {
        return derivedFrom != null ? derivedFrom.character() : glyph;
    }
}
