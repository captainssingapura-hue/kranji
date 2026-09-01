package kranji.zi;

import hue.captains.singapura.tao.ontology.ValueObject;

/**
 * One Chinese character.
 *
 * <p>The foundational primitive: a single Unicode codepoint in the Han script.
 * Everything that identifies a character — registry keys, the join between the
 * simple and structural tiers, article tokens — should eventually name this
 * type rather than {@link String}.</p>
 *
 * <h2>Why not {@code char}</h2>
 *
 * <p>Java's {@code char} is a UTF-16 code unit and cannot hold a character
 * outside the Basic Multilingual Plane. That is not hypothetical here: eight
 * characters already in the corpus are surrogate pairs, among them U+30EDD
 * (𰻝, biáng), the 58-stroke character the project uses as its depth showcase.
 * A {@code char}-based type would silently exclude characters we already
 * model.</p>
 *
 * <p>The {@code UTF8} in the name marks that distinction: a variable-length
 * view of a character, not a fixed 16-bit one. The codepoint is stored as an
 * {@code int} — the canonical single-character representation, giving one
 * value per character, natural ordering, and no dependence on how any
 * particular encoding lays the character out in memory.</p>
 *
 * <h2>What counts as a character</h2>
 *
 * <p>Any codepoint whose Unicode script is {@code HAN}. Audited against the
 * whole corpus — 2,642 distinct glyphs across singulars, bound radicals, and
 * composed characters — every one satisfies it, including the five entries
 * from CJK Radicals Supplement. One predicate covers Zi and Part alike.</p>
 *
 * <p>Deliberately excluded: variation selectors. A character written with
 * U+FE0x or U+E01xx to select a glyph variant is two codepoints and will be
 * rejected. That is correct for modelling characters and wrong for modelling
 * typography; if typography ever matters, it needs its own type.</p>
 *
 * @param codePoint a Unicode codepoint in the Han script
 */
public record ZiCharUTF8(int codePoint) implements ValueObject, Comparable<ZiCharUTF8> {

    public ZiCharUTF8 {
        if (!Character.isValidCodePoint(codePoint)) {
            throw new IllegalArgumentException(
                    "not a Unicode codepoint: 0x" + Integer.toHexString(codePoint));
        }
        if (!isHan(codePoint)) {
            throw new IllegalArgumentException(
                    "not a Han character: U+" + hex(codePoint)
                  + " (script " + Character.UnicodeScript.of(codePoint) + ")");
        }
    }

    /** The character as a string — one codepoint, one or two {@code char}s. */
    public String value() {
        return new String(Character.toChars(codePoint));
    }

    /** {@code U+6E05} form, for messages and fingerprints. */
    public String codePointLabel() {
        return "U+" + hex(codePoint);
    }

    /** True when this character lives outside the BMP, so its string form is a surrogate pair. */
    public boolean isSupplementary() {
        return Character.isSupplementaryCodePoint(codePoint);
    }

    @Override
    public int compareTo(ZiCharUTF8 other) {
        return Integer.compare(codePoint, other.codePoint);
    }

    /** The character itself, so logging and string concatenation read naturally. */
    @Override
    public String toString() {
        return value();
    }

    /** Whether {@code codePoint} is a Han character — the admission predicate. */
    public static boolean isHan(int codePoint) {
        if (!Character.isValidCodePoint(codePoint)) return false;
        try {
            return Character.UnicodeScript.of(codePoint) == Character.UnicodeScript.HAN;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static String hex(int cp) {
        String h = Integer.toHexString(cp).toUpperCase();
        return h.length() < 4 ? "0".repeat(4 - h.length()) + h : h;
    }
}
