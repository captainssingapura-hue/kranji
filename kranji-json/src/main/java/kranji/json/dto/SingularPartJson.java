package kranji.json.dto;

/**
 * Wire shape for a 偏旁 — a radical variant or component part that is not
 * a standalone character.
 *
 * @param glyph       the visual glyph (e.g. {@code "氵"}); required
 * @param name        conventional Chinese name (e.g. {@code "三点水"}); nullable
 * @param strokes     stroke count of this component form; nullable
 * @param pinyinText  pinyin of the standalone form (e.g. {@code "shuǐ"}); nullable
 * @param meaning     English gloss; nullable
 * @param derivedFrom glyph of the standalone character this derives from
 *                    (e.g. {@code "水"} for 氵); nullable
 */
public record SingularPartJson(
        String glyph,
        String name,
        Integer strokes,
        String pinyinText,
        String meaning,
        String derivedFrom
) {}
