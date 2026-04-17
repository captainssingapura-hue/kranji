package kranji.json.dto;

import java.util.List;

/**
 * Wire shape for a 独体字 — a standalone Chinese character that is both a Zi
 * (semantic identity) and a singular structural block.
 *
 * @param glyph      the character (e.g. {@code "水"}); required
 * @param codepoint  Unicode label (e.g. {@code "U+6C34"}); nullable
 * @param name       conventional name / romanization; nullable
 * @param strokes    total stroke count; nullable
 * @param radicalNo  Kangxi radical number (1–214); nullable
 * @param pinyinText display-form pinyin (e.g. {@code "shuǐ"}); nullable
 * @param pinyin     structured syllables; defensively copied, never null
 * @param meaning    English gloss; nullable
 * @param etymology  etymological category (六书); nullable
 */
public record SingularZiJson(
        String glyph,
        String codepoint,
        String name,
        Integer strokes,
        Integer radicalNo,
        String pinyinText,
        List<PinyinJson> pinyin,
        String meaning,
        EtymologyJson etymology
) {
    public SingularZiJson {
        pinyin = pinyin == null ? List.of() : List.copyOf(pinyin);
    }
}
