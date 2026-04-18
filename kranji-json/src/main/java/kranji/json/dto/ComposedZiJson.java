package kranji.json.dto;

/**
 * Wire shape for a 合体字 — a composed Chinese character.
 *
 * @param glyph       the character (e.g. {@code "清"}); required
 * @param ziCharForm  how the character was spelled in source (literal vs unicode); nullable
 * @param strokes     total stroke count; nullable
 * @param radicalNo   Kangxi radical number (1–214); nullable
 * @param pinyin      structured syllable; nullable
 * @param meaning     English gloss; nullable
 * @param composition spatial decomposition; required for a meaningful composed entry
 * @param etymology   etymological category (六书); nullable
 */
public record ComposedZiJson(
        String glyph,
        ZiCharForm ziCharForm,
        Integer strokes,
        Integer radicalNo,
        PinyinJson pinyin,
        String meaning,
        ComposedBlockJson composition,
        EtymologyJson etymology
) {}
