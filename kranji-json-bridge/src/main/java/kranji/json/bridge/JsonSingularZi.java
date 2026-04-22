package kranji.json.bridge;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.PinyinSyllable;
import kranji.zi.SingularZi;

import java.util.Objects;

/**
 * Plain-data implementation of {@link SingularZi} built from the fields of a
 * {@code SingularZiJson} DTO. Immutable record; no behavior beyond the
 * interface accessors.
 *
 * <p>Use this when you need a typed {@code SingularZi} view of JSON-sourced
 * data and do not have a concrete singleton record available (e.g. the
 * glyph is not present in {@code kranji-common} / {@code kranji-singulars}).</p>
 *
 * <p>Structured pinyin is derived on demand from {@code pinyinText} via
 * {@link PinyinSyllable#parse(String)} (the default implementation on
 * {@link SingularZi}).</p>
 */
public record JsonSingularZi(
        String glyph,
        String pinyinText,
        int strokes,
        int radicalNo,
        String meaning,
        EtymologicalCategory etymology
) implements SingularZi {

    public JsonSingularZi {
        Objects.requireNonNull(glyph, "glyph");
        pinyinText = pinyinText == null ? "" : pinyinText;
        meaning    = meaning    == null ? "" : meaning;
        etymology  = etymology  == null ? new EtymologicalCategory.Pictograph() : etymology;
    }
}
