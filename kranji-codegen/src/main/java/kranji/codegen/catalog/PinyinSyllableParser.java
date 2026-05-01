package kranji.codegen.catalog;

import java.util.Locale;
import java.util.Map;

/**
 * Translates a {@code (initial, finalTone)} pair from the catalog
 * (e.g. {@code ("b", "a1")}, {@code ("zh", "ong3")}, {@code ("zero", "iang2")})
 * into the {@code (Initial, Head, Body, Tail, Tone)} enum-name strings
 * needed to construct a {@code PinyinSyllable}.
 *
 * <p>The mapping is a hand-curated table built from the conventions
 * already in use across hand-authored records (see e.g.
 * {@code kranji.common.perclass.zh.ong1.ZhongLoyal}, {@code zero.er3.ErYouArchaic},
 * {@code j.ie4.JieBorrow}). Notable irregularities the table captures:</p>
 *
 * <ul>
 *   <li>{@code "ie"} / {@code "ve"} use {@code Body.E_CARON} (not {@code E}).</li>
 *   <li>{@code "er"} uses {@code Body.ER}.</li>
 *   <li>{@code "in"}, {@code "ing"} use head {@code OPEN} (not {@code I}).</li>
 *   <li>{@code "u"} (after a consonant or as syllable {@code wu}) uses
 *       {@code (Head.U, Body.U, Tail.NONE)} — see {@code zh.u4.ZhuLive}, {@code p.u1.PuPounce}.</li>
 * </ul>
 */
public final class PinyinSyllableParser {

    public record Parts(String initial, String head, String body, String tail, String tone) {}

    private PinyinSyllableParser() {}

    public static Parts parse(String initial, String finalTone) {
        String initialEnum = initialEnumOf(initial);
        char toneChar = finalTone.charAt(finalTone.length() - 1);
        String toneEnum = toneEnumOf(toneChar);
        String bareFinal = finalTone.substring(0, finalTone.length() - 1);
        String[] hbt = FINALS.get(bareFinal);
        if (hbt == null) {
            throw new IllegalArgumentException("Unrecognised pinyin final: '"
                    + bareFinal + "' from finalTone '" + finalTone + "'");
        }
        return new Parts(initialEnum, hbt[0], hbt[1], hbt[2], toneEnum);
    }

    private static String initialEnumOf(String initial) {
        if (initial.equals("zero")) return "ZERO";
        return initial.toUpperCase(Locale.ROOT);
    }

    private static String toneEnumOf(char digit) {
        return switch (digit) {
            case '1' -> "FIRST";
            case '2' -> "SECOND";
            case '3' -> "THIRD";
            case '4' -> "FOURTH";
            case '5' -> "NEUTRAL";
            default -> throw new IllegalArgumentException("Bad tone digit: " + digit);
        };
    }

    /**
     * bare-final → {Head, Body, Tail} enum-name strings. Single source of truth.
     */
    private static final Map<String, String[]> FINALS = Map.<String, String[]>ofEntries(
            // a-finals
            Map.entry("a",   new String[]{"OPEN", "A", "NONE"}),
            Map.entry("ai",  new String[]{"OPEN", "A", "VOWEL_I"}),
            Map.entry("an",  new String[]{"OPEN", "A", "N"}),
            Map.entry("ang", new String[]{"OPEN", "A", "NG"}),
            Map.entry("ao",  new String[]{"OPEN", "A", "VOWEL_U"}),
            // e-finals
            Map.entry("e",   new String[]{"OPEN", "E", "NONE"}),
            Map.entry("ei",  new String[]{"OPEN", "E", "VOWEL_I"}),
            Map.entry("en",  new String[]{"OPEN", "E", "N"}),
            Map.entry("eng", new String[]{"OPEN", "E", "NG"}),
            Map.entry("er",  new String[]{"OPEN", "ER", "NONE"}),
            // i-finals
            Map.entry("i",    new String[]{"OPEN", "I", "NONE"}),
            Map.entry("ia",   new String[]{"I", "A", "NONE"}),
            Map.entry("ian",  new String[]{"I", "A", "N"}),
            Map.entry("iang", new String[]{"I", "A", "NG"}),
            Map.entry("iao",  new String[]{"I", "A", "VOWEL_U"}),
            Map.entry("ie",   new String[]{"I", "E_CARON", "NONE"}),
            Map.entry("in",   new String[]{"OPEN", "I", "N"}),
            Map.entry("ing",  new String[]{"OPEN", "I", "NG"}),
            Map.entry("iong", new String[]{"I", "O", "NG"}),
            Map.entry("iou",  new String[]{"I", "O", "VOWEL_U"}),
            // o-finals
            Map.entry("o",    new String[]{"OPEN", "O", "NONE"}),
            Map.entry("ong",  new String[]{"OPEN", "O", "NG"}),
            Map.entry("ou",   new String[]{"OPEN", "O", "VOWEL_U"}),
            // u-finals
            Map.entry("u",    new String[]{"U", "U", "NONE"}),
            Map.entry("ua",   new String[]{"U", "A", "NONE"}),
            Map.entry("uai",  new String[]{"U", "A", "VOWEL_I"}),
            Map.entry("uan",  new String[]{"U", "A", "N"}),
            Map.entry("uang", new String[]{"U", "A", "NG"}),
            Map.entry("uei",  new String[]{"U", "E", "VOWEL_I"}),
            Map.entry("uen",  new String[]{"U", "E", "N"}),
            Map.entry("ueng", new String[]{"U", "E", "NG"}),
            Map.entry("uo",   new String[]{"U", "O", "NONE"}),
            // v-finals (ü)
            Map.entry("v",    new String[]{"V", "U", "NONE"}),
            Map.entry("van",  new String[]{"V", "A", "N"}),
            Map.entry("ve",   new String[]{"V", "E_CARON", "NONE"}),
            Map.entry("ven",  new String[]{"V", "E", "N"})
    );
}
