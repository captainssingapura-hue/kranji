package kranji.json.bridge;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.PinyinSyllable;
import kranji.zi.SingularZi;

import java.util.List;
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
 * <p>Structured {@code pinyin} is not reconstructed from the DTO's flat
 * string form — the list is accepted as-is. Callers that want
 * {@link PinyinSyllable} reconstruction should supply it explicitly. The
 * human-readable {@code pinyinText} is available via {@link #pinyinText()}.</p>
 */
public record JsonSingularZi(
        String glyph,
        String pinyinText,
        int strokes,
        int radicalNo,
        String meaning,
        List<PinyinSyllable> pinyin,
        EtymologicalCategory etymology
) implements SingularZi {

    public JsonSingularZi {
        Objects.requireNonNull(glyph, "glyph");
        pinyinText = pinyinText == null ? "" : pinyinText;
        meaning    = meaning    == null ? "" : meaning;
        pinyin     = pinyin     == null ? List.of() : List.copyOf(pinyin);
        etymology  = etymology  == null ? new EtymologicalCategory.Pictograph() : etymology;
    }
}
