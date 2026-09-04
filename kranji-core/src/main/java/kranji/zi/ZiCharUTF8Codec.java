package kranji.zi;

import hue.captains.singapura.tao.ontology.StatelessFunctionalObject;

import java.util.Objects;
import java.util.Optional;

/**
 * Converts one {@link ZiCharUTF8} to and from an ordinary {@link String}.
 *
 * <p>The companion to the primitive, and deliberately no more than that. The
 * existing corpus speaks {@code String} — {@code Zi.character()},
 * {@code SingularPart.glyph()}, the JSON catalogue, article text — and will
 * keep doing so. Rather than migrate that, conversion is concentrated here, so
 * new code can be typed while the boundary with old code stays in one visible
 * place.</p>
 *
 * <p>Scope is a single character. Operations over <em>text</em> — extracting
 * the characters of a passage, joining a sequence back into a string — are a
 * different concern and belong to their own functional objects, added when
 * something needs them.</p>
 *
 * <p>Two conversion styles, deliberately distinct. {@link #from(String)}
 * throws, for input that a bug would explain — a hardcoded character, a
 * registry key. {@link #tryFrom(String)} returns empty, for input the world
 * supplies — a search box, an imported list, a stray punctuation mark.
 * Choosing between them is choosing whether bad input is a defect or a
 * fact.</p>
 */
public record ZiCharUTF8Codec() implements StatelessFunctionalObject {

    public static final ZiCharUTF8Codec INSTANCE = new ZiCharUTF8Codec();

    /**
     * The character in {@code s}.
     *
     * @throws IllegalArgumentException if {@code s} is not exactly one Han
     *         character — including when it is empty, two characters, or Latin
     */
    public ZiCharUTF8 from(String s) {
        Objects.requireNonNull(s, "s");
        int count = s.codePointCount(0, s.length());
        if (count != 1) {
            throw new IllegalArgumentException(
                    "expected exactly one character, got " + count + ": '" + s + "'");
        }
        return new ZiCharUTF8(s.codePointAt(0));
    }

    /** The character in {@code s}, or empty when {@code s} is not one Han character. */
    public Optional<ZiCharUTF8> tryFrom(String s) {
        if (s == null || s.isEmpty()) return Optional.empty();
        if (s.codePointCount(0, s.length()) != 1) return Optional.empty();
        int cp = s.codePointAt(0);
        return ZiCharUTF8.isHan(cp) ? Optional.of(new ZiCharUTF8(cp)) : Optional.empty();
    }

    /** The string form of {@code zi} — one codepoint, one or two {@code char}s. */
    public String to(ZiCharUTF8 zi) {
        return Objects.requireNonNull(zi, "zi").value();
    }
}
