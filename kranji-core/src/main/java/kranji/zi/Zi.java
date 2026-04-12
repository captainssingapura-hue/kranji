package kranji.zi;

/**
 * 字 — The universal building block of Chinese character structure.
 *
 * <p>A {@code Zi} is either atomic ({@link SingularZi}) or composed
 * ({@link ComposedZi}). This two-branch sum type models the recursive
 * nature of Chinese characters: any slot in a composition holds a Zi,
 * which may itself be further decomposed.</p>
 *
 * <p>Not sealed — {@link SingularZi} is open for extension across
 * modules (component families define their own records).</p>
 */
public interface Zi {

    /** The visual glyph of this character or component. */
    String glyph();
}
