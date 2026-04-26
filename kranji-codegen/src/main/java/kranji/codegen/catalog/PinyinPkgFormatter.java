package kranji.codegen.catalog;

import kranji.pinyin.PinyinSyllable;

import java.util.Locale;
import java.util.Optional;

/**
 * Converts a diacritic pinyin string (e.g. {@code "hái"}, {@code "qiān"})
 * into the {@code <initial>.<finalTone>} pkg form used by the catalog
 * (e.g. {@code "h.ai2"}, {@code "q.ian1"}).
 *
 * <p>Leverages {@link PinyinSyllable#parse(String)} to get the structured
 * form, then renders it back into the catalog convention. The result is
 * the inverse of {@link PinyinSyllableParser} — feeding the output of this
 * formatter into that parser yields the same {@link PinyinSyllable}.</p>
 */
public final class PinyinPkgFormatter {

    private PinyinPkgFormatter() {}

    /** Convert a diacritic pinyin string to {@code <initial>.<finalTone>} pkg form. */
    public static Optional<String> toPkg(String diacriticPinyin) {
        if (diacriticPinyin == null || diacriticPinyin.isEmpty()) return Optional.empty();
        PinyinSyllable syl;
        try {
            syl = PinyinSyllable.parse(diacriticPinyin);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
        String initial = syl.initial().pinyin();
        if (initial.isEmpty()) initial = "zero";
        String finalSpelling = syl.fin().spelling().replace("ü", "v");
        // Empty spelling — syllabic consonants like zhi/chi/zi/ri/yi: use "i" placeholder
        // matching the catalog convention.
        if (finalSpelling.isEmpty()) finalSpelling = "i";
        int tone = syl.tone().number();
        return Optional.of(initial.toLowerCase(Locale.ROOT) + "." + finalSpelling + tone);
    }
}
