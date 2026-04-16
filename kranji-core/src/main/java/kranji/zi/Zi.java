package kranji.zi;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.PinyinSyllable;

import java.util.List;

/**
 * 字 — The semantic identity of a Chinese character.
 *
 * <p>A {@code Zi} carries everything that makes a character meaningful:
 * its glyph, pronunciation, stroke count, radical, meaning, internal
 * structure, and etymological classification.</p>
 *
 * <p>Two concrete forms:</p>
 * <ul>
 *   <li>{@link SingularZi} — a standalone character that <em>is</em> its own
 *       {@link BlockStructure} (stateless singleton records).</li>
 *   <li>{@link ComposedZi} — a composed character that delegates its structure
 *       to a {@link ComposedBlock}.</li>
 * </ul>
 */
public interface Zi {

    /** The character glyph (e.g. "清"). */
    String character();

    /** The Unicode code point string (e.g. "U+6E05"). */
    default String codepoint() {
        return "U+" + String.format("%04X", character().codePointAt(0));
    }

    /** Mandarin reading(s). */
    List<PinyinSyllable> pinyin();

    /** Total stroke count. */
    int strokes();

    /** Kangxi radical number (1–214). */
    int radicalNo();

    /** English semantic meaning. */
    String meaning();

    /** Internal structural decomposition (the grammar). */
    BlockStructure structure();

    /** Etymological classification (六书). */
    EtymologicalCategory etymology();
}
