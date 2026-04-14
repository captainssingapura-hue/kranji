package kranji.zi;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.PinyinSyllable;

import java.util.List;

/**
 * Concrete implementation of {@link Zi} — a fully classified Chinese character entry.
 *
 * @param character  the glyph (e.g. "清")
 * @param codepoint  the Unicode code point (e.g. "U+6E05")
 * @param pinyin     Mandarin reading(s)
 * @param strokes    total stroke count
 * @param radicalNo  Kangxi radical number (1–214)
 * @param meaning    English semantic meaning
 * @param structure  internal structural decomposition
 * @param etymology  etymological classification (六书)
 */
public record ZiEntry(
        String character,
        String codepoint,
        List<PinyinSyllable> pinyin,
        int strokes,
        int radicalNo,
        String meaning,
        BlockStructure structure,
        EtymologicalCategory etymology
) implements Zi {

    /** Returns the Unicode code point string for the character (e.g. "U+6E05"). */
    public static String toCodepoint(String glyph) {
        return "U+" + String.format("%04X", glyph.codePointAt(0));
    }
}
