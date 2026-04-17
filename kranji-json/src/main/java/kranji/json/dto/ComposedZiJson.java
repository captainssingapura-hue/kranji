package kranji.json.dto;

import java.util.List;

/**
 * Wire shape for a 合体字 — a composed Chinese character.
 *
 * @param glyph       the character (e.g. {@code "清"}); required
 * @param ziCharForm  how the character was spelled in source (literal vs unicode); nullable
 * @param strokes     total stroke count; nullable
 * @param radicalNo   Kangxi radical number (1–214); nullable
 * @param pinyin      structured syllables; defensively copied, never null
 * @param meaning     English gloss; nullable
 * @param composition spatial decomposition; required for a meaningful composed entry
 * @param etymology   etymological category (六书); nullable
 */
public record ComposedZiJson(
        String glyph,
        ZiCharForm ziCharForm,
        Integer strokes,
        Integer radicalNo,
        List<PinyinJson> pinyin,
        String meaning,
        ComposedBlockJson composition,
        EtymologyJson etymology
) {
    public ComposedZiJson {
        pinyin = pinyin == null ? List.of() : List.copyOf(pinyin);
    }
}
