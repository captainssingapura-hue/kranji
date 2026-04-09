package kranji.entry;

import kranji.classification.CharacterComposition;
import kranji.classification.EtymologicalCategory;
import kranji.pinyin.PinyinSyllable;

import java.util.List;

/**
 * A fully classified Chinese character entry, combining both orthogonal axes
 * of classification with metadata.
 *
 * @param character   the glyph (e.g. "清")
 * @param codepoint   the Unicode code point (e.g. "U+6E05")
 * @param pinyin      Mandarin reading(s)
 * @param strokes     total stroke count
 * @param radicalNo   Kangxi radical number (1–214)
 * @param composition Axis 1: structural composition (字形结构) — encodes both structure and components
 * @param etymology   Axis 2: etymological category (六书)
 */
public record ChineseCharacterEntry(
        String character,
        String codepoint,
        List<PinyinSyllable> pinyin,
        int strokes,
        int radicalNo,
        CharacterComposition composition,
        EtymologicalCategory etymology
) {

    /** Returns the Unicode code point string for the character (e.g. "U+6E05"). */
    public static String toCodepoint(String glyph) {
        return "U+" + String.format("%04X", glyph.codePointAt(0));
    }
}
