package kranji.zi;

/**
 * 偏旁 — A radical variant or component part that is not a standalone character.
 *
 * <p>Parts like 氵 (water radical) derive from a standalone character (水)
 * but have a distinct glyph form used in composition. Each part knows
 * its derivation source via {@link #derivedFrom()}.</p>
 *
 * <p>For standalone characters that are both a Zi and a SingularBlock,
 * see {@link SingularZi}.</p>
 */
public interface SingularPart extends SingularBlock {

    /**
     * The standalone {@link SingularZi} this part derives from.
     *
     * <p>For example, 氵 (SanDianShui) derives from 水 (water).
     * Returns {@code null} if no typed derivation is available.</p>
     */
    default SingularZi derivedFrom() { return null; }
}
