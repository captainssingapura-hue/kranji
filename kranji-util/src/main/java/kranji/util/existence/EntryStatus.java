package kranji.util.existence;

import java.util.Objects;

/**
 * Existence-check status for a single glyph in the JSON feed.
 *
 * @param glyph            the glyph from the JSON entry
 * @param pool             which pool the JSON placed this glyph in
 * @param exists           {@code true} iff a strong-typed implementation
 *                         was found for this glyph in the same pool
 * @param typedClassName   fully-qualified name of the strong-typed record
 *                         when {@link #exists} is true; {@code null} otherwise
 */
public record EntryStatus(
        String glyph,
        Pool pool,
        boolean exists,
        String typedClassName
) {

    public EntryStatus {
        Objects.requireNonNull(glyph, "glyph");
        Objects.requireNonNull(pool,  "pool");
        if (exists && typedClassName == null) {
            throw new IllegalArgumentException("exists=true requires typedClassName");
        }
        if (!exists && typedClassName != null) {
            throw new IllegalArgumentException("exists=false requires typedClassName=null");
        }
    }

    /** Identifies which pool a JSON entry came from. */
    public enum Pool {
        SINGULAR_ZI,
        SINGULAR_PART,
        COMPOSED_ZI
    }
}
