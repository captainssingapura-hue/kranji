package kranji.pinyin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Parses standard pinyin strings into the phonemic decomposition used by the
 * Kranji pinyin model ({@link Initial}, {@link Head}, {@link Body},
 * {@link Tail}, {@link Tone}).
 *
 * <p>Accepts two input forms:</p>
 * <ul>
 *   <li><b>Numbered tone</b> — ASCII, with a trailing tone digit 1–5
 *       (e.g. {@code "ma3"}, {@code "lv4"}, {@code "ning5"}). This is the
 *       form every syllable can be unambiguously round-tripped through.</li>
 *   <li><b>Diacritic tone</b> — the Unicode display form with combining
 *       marks (e.g. {@code "mǎ"}, {@code "lǜ"}, {@code "níng"}). Recognised
 *       by this parser as a convenience so catalog entries keep their
 *       human-readable {@code pinyinText} form.</li>
 * </ul>
 *
 * <p>Handles all standard Mandarin orthographic conventions: y/w zero-initial
 * spelling, abbreviated finals (ui=uei, iu=iou, un=uen), ü→u after j/q/x,
 * and syllabic consonants (zi, ci, si, zhi, chi, shi, ri).</p>
 *
 * <p>This class was originally a demo utility under {@code kranji-core-demos};
 * it was promoted into the core library when {@code PinyinSyllable} grew
 * typed read/write facilities ({@link PinyinSyllable#parse(String)} and
 * {@link PinyinSyllable#toDiacritic()}).</p>
 */
public final class PinyinParser {

    private PinyinParser() {}

    /** Parsed result — every field required. */
    public record Decomposition(Initial initial, Head head, Body body, Tail tail, Tone tone) {

        /** Wrap as a {@link PinyinSyllable}. */
        public PinyinSyllable toSyllable() {
            return new PinyinSyllable(initial, new Final(head, body, tail), tone);
        }
    }

    // Consonantal initials sorted longest-first for greedy matching
    private static final String[] INITIALS = {
            "zh", "ch", "sh",
            "b", "p", "m", "f",
            "d", "t", "n", "l",
            "g", "k", "h",
            "j", "q", "x",
            "z", "c", "s", "r"
    };

    // Initials that produce syllabic consonant for bare "i"
    private static final Set<Initial> SYLLABIC_INITIALS = Set.of(
            Initial.Z, Initial.C, Initial.S,
            Initial.ZH, Initial.CH, Initial.SH, Initial.R
    );

    // After j, q, x: written "u" is actually "ü"
    private static final Set<Initial> JQX = Set.of(Initial.J, Initial.Q, Initial.X);

    /**
     * Parse a pinyin string — accepts either numbered-tone ("ma3") or
     * diacritic ("mǎ") form.
     *
     * @param pinyin the input string
     * @return the full decomposition
     * @throws IllegalArgumentException if the pinyin cannot be parsed
     */
    public static Decomposition parse(String pinyin) {
        if (pinyin == null) throw new IllegalArgumentException("Null pinyin");
        String trimmed = pinyin.trim();
        if (trimmed.isEmpty()) throw new IllegalArgumentException("Empty pinyin");

        // Detect diacritic form by checking for any of the tonal-marked letters.
        // If present, convert to numbered form before the core parse.
        if (hasDiacritic(trimmed)) {
            trimmed = diacriticToNumbered(trimmed);
        }
        return parseNumbered(trimmed);
    }

    /**
     * Parse a numbered-tone pinyin string (e.g. "qing1", "lv4") into its
     * phonemic decomposition. Exposed separately for callers that never
     * produce diacritic form and want to skip the detection pass.
     */
    public static Decomposition parseNumbered(String numbered) {
        numbered = numbered.trim().toLowerCase();
        if (numbered.isEmpty()) throw new IllegalArgumentException("Empty pinyin");

        // Extract tone
        char lastChar = numbered.charAt(numbered.length() - 1);
        int toneNum;
        String base;
        if (Character.isDigit(lastChar)) {
            toneNum = lastChar - '0';
            base = numbered.substring(0, numbered.length() - 1);
        } else {
            toneNum = 5; // neutral tone
            base = numbered;
        }
        Tone tone = Tone.ofNumber(toneNum);

        // Normalize "v" → "ü" in input
        base = base.replace("v", "ü");

        // Parse initial
        Initial initial;
        String finalStr;
        for (String ini : INITIALS) {
            if (base.startsWith(ini)) {
                initial = Initial.fromPinyin(ini);
                finalStr = base.substring(ini.length());
                return resolve(initial, finalStr, tone);
            }
        }

        // Zero initial — handle y, w, or pure vowel
        initial = Initial.ZERO;
        finalStr = handleZeroInitial(base);
        return resolve(initial, finalStr, tone);
    }

    // ── Diacritic → numbered ─────────────────────────────────────────────

    /** Every tone-bearing diacritic vowel used in pinyin display form. */
    private static final Map<Character, int[]> DIACRITIC_TO_PLAIN_TONE = new HashMap<>();
    static {
        // {replacement-char, tone-number}
        putDia('ā', 'a', 1); putDia('á', 'a', 2); putDia('ǎ', 'a', 3); putDia('à', 'a', 4);
        putDia('ō', 'o', 1); putDia('ó', 'o', 2); putDia('ǒ', 'o', 3); putDia('ò', 'o', 4);
        putDia('ē', 'e', 1); putDia('é', 'e', 2); putDia('ě', 'e', 3); putDia('è', 'e', 4);
        putDia('ī', 'i', 1); putDia('í', 'i', 2); putDia('ǐ', 'i', 3); putDia('ì', 'i', 4);
        putDia('ū', 'u', 1); putDia('ú', 'u', 2); putDia('ǔ', 'u', 3); putDia('ù', 'u', 4);
        putDia('ǖ', 'ü', 1); putDia('ǘ', 'ü', 2); putDia('ǚ', 'ü', 3); putDia('ǜ', 'ü', 4);
    }

    private static void putDia(char diacritic, char plain, int tone) {
        DIACRITIC_TO_PLAIN_TONE.put(diacritic, new int[]{plain, tone});
    }

    private static boolean hasDiacritic(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (DIACRITIC_TO_PLAIN_TONE.containsKey(s.charAt(i))) return true;
        }
        return false;
    }

    /**
     * Strip the tone-bearing diacritic from a display-form syllable and
     * append the tone as a trailing digit. Neutral tone (no diacritic on
     * any vowel) becomes tone 5 and the string is returned as-is with a
     * trailing 5.
     */
    static String diacriticToNumbered(String diacritic) {
        StringBuilder plain = new StringBuilder(diacritic.length());
        int tone = 5; // neutral until proven otherwise
        for (int i = 0; i < diacritic.length(); i++) {
            char c = diacritic.charAt(i);
            int[] mapping = DIACRITIC_TO_PLAIN_TONE.get(c);
            if (mapping == null) {
                plain.append(c);
            } else {
                plain.append((char) mapping[0]);
                tone = mapping[1];
            }
        }
        plain.append(tone);
        return plain.toString();
    }

    /**
     * Convert y/w-prefixed zero-initial spellings to their phonemic final.
     */
    private static String handleZeroInitial(String base) {
        if (base.startsWith("yu")) {
            // yu, yuan, yue, yun → ü, üan, üe, ün
            String rest = base.substring(2);
            return rest.isEmpty() ? "ü" : "ü" + rest;
        }
        if (base.startsWith("yi")) {
            // yi, yin, ying → i, in, ing
            return base.substring(1);
        }
        if (base.startsWith("y")) {
            // ya, yao, yan, yang, ye, you, yong → ia, iao, ian, iang, ie, iou, iong
            return "i" + base.substring(1);
        }
        if (base.startsWith("wu")) {
            // wu → u
            return base.substring(1);
        }
        if (base.startsWith("w")) {
            // wa, wo, wai, wei, wan, wen, wang, weng → ua, uo, uai, uei, uan, uen, uang, ueng
            return "u" + base.substring(1);
        }
        // Pure vowel: a, o, e, ai, ei, ao, ou, an, en, ang, er, ...
        return base;
    }

    /**
     * Resolve a (consonant-initial, final-string) pair to the full decomposition.
     * Handles abbreviations and special cases.
     */
    private static Decomposition resolve(Initial initial, String fin, Tone tone) {
        // After j, q, x: "u" at the start of the final is really "ü"
        // (e.g. ju→jü, jue→jüe, juan→jüan, jun→jün)
        // but NOT in "iu" which is "iou" (e.g. jiu, qiu, xiu)
        if (JQX.contains(initial) && fin.startsWith("u")) {
            fin = "ü" + fin.substring(1);
        }

        // Expand standard abbreviations
        fin = expandAbbreviations(initial, fin);

        // Special case: syllabic consonant ("i" after z/c/s/zh/ch/sh/r)
        if (SYLLABIC_INITIALS.contains(initial) && fin.equals("i")) {
            return new Decomposition(initial, Head.OPEN, Body.NULL, Tail.NONE, tone);
        }

        // Parse the expanded final
        var parsed = parseFinal(fin);
        return new Decomposition(initial, parsed.head, parsed.body, parsed.tail, tone);
    }

    /**
     * Expand abbreviated finals to their full phonemic form.
     */
    private static String expandAbbreviations(Initial initial, String fin) {
        // "iu" → "iou"
        if (fin.equals("iu")) return "iou";

        // "ui" → "uei"  (or "üei" — but ü+ei doesn't occur)
        if (fin.equals("ui")) return "uei";

        // "un" → "uen" (but NOT after j/q/x where it's "ün" → "üen")
        if (fin.equals("un")) return "uen";
        if (fin.equals("ün")) return "üen";

        return fin;
    }

    /** Parsed final (Head, Body, Tail). */
    private record ParsedFinal(Head head, Body body, Tail tail) {}

    // Finals lookup table: expanded final → (Head, Body, Tail)
    private static final Map<String, ParsedFinal> FINALS = new HashMap<>();
    static {
        // Open mouth (开口呼) — no medial
        put("a",    Head.OPEN, Body.A,       Tail.NONE);
        put("o",    Head.OPEN, Body.O,       Tail.NONE);
        put("e",    Head.OPEN, Body.E,       Tail.NONE);
        put("ai",   Head.OPEN, Body.A,       Tail.VOWEL_I);
        put("ei",   Head.OPEN, Body.E,       Tail.VOWEL_I);
        put("ao",   Head.OPEN, Body.A,       Tail.VOWEL_U);
        put("ou",   Head.OPEN, Body.O,       Tail.VOWEL_U);
        put("an",   Head.OPEN, Body.A,       Tail.N);
        put("en",   Head.OPEN, Body.E,       Tail.N);
        put("ang",  Head.OPEN, Body.A,       Tail.NG);
        put("eng",  Head.OPEN, Body.E,       Tail.NG);
        put("ong",  Head.OPEN, Body.O,       Tail.NG);
        put("er",   Head.OPEN, Body.ER,      Tail.NONE);
        // Simple i, in, ing (after consonant, no medial — "i" is nucleus)
        put("i",    Head.OPEN, Body.I,       Tail.NONE);
        put("in",   Head.OPEN, Body.I,       Tail.N);
        put("ing",  Head.OPEN, Body.I,       Tail.NG);
        // Simple u (both medial and nucleus)
        put("u",    Head.U,    Body.U,       Tail.NONE);

        // I-medial (齐齿呼)
        put("ia",   Head.I,    Body.A,       Tail.NONE);
        put("iao",  Head.I,    Body.A,       Tail.VOWEL_U);
        put("ian",  Head.I,    Body.A,       Tail.N);
        put("iang", Head.I,    Body.A,       Tail.NG);
        put("ie",   Head.I,    Body.E_CARON, Tail.NONE);
        put("iou",  Head.I,    Body.O,       Tail.VOWEL_U);
        put("iong", Head.I,    Body.O,       Tail.NG);

        // U-medial (合口呼)
        put("ua",   Head.U,    Body.A,       Tail.NONE);
        put("uo",   Head.U,    Body.O,       Tail.NONE);
        put("uai",  Head.U,    Body.A,       Tail.VOWEL_I);
        put("uei",  Head.U,    Body.E,       Tail.VOWEL_I);
        put("uan",  Head.U,    Body.A,       Tail.N);
        put("uen",  Head.U,    Body.E,       Tail.N);
        put("uang", Head.U,    Body.A,       Tail.NG);
        put("ueng", Head.U,    Body.E,       Tail.NG);

        // Ü-medial (撮口呼)
        put("ü",    Head.V,    Body.V,       Tail.NONE);
        put("üe",   Head.V,    Body.E_CARON, Tail.NONE);
        put("üan",  Head.V,    Body.A,       Tail.N);
        put("üen",  Head.V,    Body.E,       Tail.N);
    }

    private static void put(String key, Head h, Body b, Tail t) {
        FINALS.put(key, new ParsedFinal(h, b, t));
    }

    private static ParsedFinal parseFinal(String fin) {
        var result = FINALS.get(fin);
        if (result == null) {
            throw new IllegalArgumentException("Unknown final: '" + fin + "'");
        }
        return result;
    }
}
