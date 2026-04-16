package kranji.zi;

/**
 * Wrapper for the character string of a {@link ComposedZi}, making the
 * source-code representation explicit.
 *
 * <ul>
 *   <li>{@link Literal} — the glyph appears directly: {@code lit("蠢")}
 *   <li>{@link Unicode} — a Unicode escape: {@code uni("\\u8822")}
 * </ul>
 *
 * <p>Both forms compile to the same {@link String} at runtime; the sealed
 * hierarchy exists purely for source-code readability.</p>
 */
public sealed interface ZiChar {

    /** The resolved character string. */
    String value();

    /** Wraps a literal glyph, e.g. {@code new Literal("蠢")}. */
    record Literal(String value) implements ZiChar {}

    /** Wraps a Unicode escape, e.g. {@code new Unicode("\u8822")}. */
    record Unicode(String value) implements ZiChar {}
}
