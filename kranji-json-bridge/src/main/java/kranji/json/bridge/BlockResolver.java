package kranji.json.bridge;

import kranji.zi.SingularBlock;

/**
 * Resolves a glyph string to a typed {@link SingularBlock}.
 *
 * <p>Used by {@link UntypedToTyped} when expanding a
 * {@code BlockRefJson.GlyphRef} inside a composition tree: the string
 * {@code "口"} must become an actual {@link SingularBlock} node.</p>
 *
 * <p>Implementations are typically backed by a {@link TypedZiCatalog}, but
 * the interface is kept minimal so callers can supply ad-hoc resolvers
 * (e.g. a unit-test map).</p>
 */
@FunctionalInterface
public interface BlockResolver {

    /**
     * Resolve a glyph to a typed singular block.
     *
     * @param glyph the glyph string (may be an extended-BMP surrogate pair)
     * @return the singular block for this glyph, or {@code null} if unknown
     */
    SingularBlock resolve(String glyph);
}
