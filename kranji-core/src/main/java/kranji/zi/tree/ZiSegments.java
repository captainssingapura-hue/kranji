package kranji.zi.tree;

import kranji.zi.ZiCharUTF8;

/**
 * Address segments for characters.
 *
 * <p>A character cannot be its own address. Downstream addressing schemes
 * restrict path segments to a narrow ASCII charset — the Homing catalogue, for
 * one, allows only {@code [A-Za-z0-9._-]} — so a segment has to be derived.</p>
 *
 * <p>We derive it from the <b>codepoint</b>, not from pinyin. Pinyin looks like
 * the obvious choice and is a trap: the numbered form of a ü-final is
 * {@code nü3}, which is not ASCII, and {@code jūn} renders phonemically as
 * {@code jüen1} rather than {@code jun1}. Getting an ASCII romanisation right
 * means reimplementing pinyin orthography — ü to v, plus the written collapses
 * {@code iou→iu}, {@code uei→ui}, {@code üen→un} — underneath a value that
 * lives in every URL permanently.</p>
 *
 * <p>The codepoint has none of those problems: unique, stable, already the
 * character's identity. Readability is not lost, because a tree node carries a
 * display label separately from its segment — the reader sees 好 hǎo and the
 * address says {@code u597d}.</p>
 */
public final class ZiSegments {

    private ZiSegments() {}

    /** The address segment for {@code zi}, e.g. {@code u597d} for 好. */
    public static String of(ZiCharUTF8 zi) {
        String hex = Integer.toHexString(zi.codePoint());
        // Pad to the four digits the U+XXXX convention uses, so segments
        // sort sensibly and read the same way as codePointLabel().
        return "u" + (hex.length() < 4 ? "0".repeat(4 - hex.length()) + hex : hex);
    }
}
