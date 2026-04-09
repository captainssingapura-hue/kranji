package kranji.demos;

import kranji.pinyin.*;

import java.util.*;

/**
 * Parses standard pinyin strings (e.g. "qing1") into the phonemic decomposition
 * used by the Kranji pinyin model (Initial, Head, Body, Tail, Tone).
 *
 * <p>Handles all standard Mandarin orthographic conventions: y/w zero-initial
 * spelling, abbreviated finals (ui=uei, iu=iou, un=uen), ü→u after j/q/x,
 * and syllabic consonants (zi, ci, si, zhi, chi, shi, ri).</p>
 */
public final class PinyinParser {

    private PinyinParser() {}

    /** Parsed result. */
    public record Decomposition(Initial initial, Head head, Body body, Tail tail, Tone tone) {}

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
     * Parse a numbered-tone pinyin string (e.g. "qing1", "lv4") into its
     * phonemic decomposition.
     *
     * @param numbered standard pinyin with tone number suffix (1-5)
     * @return the full decomposition
     * @throws IllegalArgumentException if the pinyin cannot be parsed
     */
    public static Decomposition parse(String numbered) {
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
        Initial initial = null;
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

    // ── Self-test ──────────────────────────────────────────────────────

    /**
     * Verify the parser against all ExampleCharacters pinyin decompositions.
     * Run: mvn -pl kranji-core-demos exec:java -Dexec.mainClass=kranji.demos.PinyinParser
     */
    public static void main(String[] args) {
        // Test cases from ExampleCharacters
        verify("ren2",   Initial.R,    Head.OPEN, Body.E,       Tail.N,       Tone.SECOND);
        verify("shan1",  Initial.SH,   Head.OPEN, Body.A,       Tail.N,       Tone.FIRST);
        verify("ri4",    Initial.R,    Head.OPEN, Body.NULL,    Tail.NONE,    Tone.FOURTH);
        verify("yue4",   Initial.ZERO, Head.V,    Body.E_CARON, Tail.NONE,    Tone.FOURTH);
        verify("shui3",  Initial.SH,   Head.U,    Body.E,       Tail.VOWEL_I, Tone.THIRD);
        verify("mu4",    Initial.M,    Head.U,    Body.U,       Tail.NONE,    Tone.FOURTH);
        verify("kou3",   Initial.K,    Head.OPEN, Body.O,       Tail.VOWEL_U, Tone.THIRD);
        verify("huo3",   Initial.H,    Head.U,    Body.O,       Tail.NONE,    Tone.THIRD);
        verify("shang4", Initial.SH,   Head.OPEN, Body.A,       Tail.NG,      Tone.FOURTH);
        verify("xia4",   Initial.X,    Head.I,    Body.A,       Tail.NONE,    Tone.FOURTH);
        verify("yi3",    Initial.ZERO, Head.OPEN, Body.I,       Tail.NONE,    Tone.THIRD);
        verify("ji3",    Initial.J,    Head.OPEN, Body.I,       Tail.NONE,    Tone.THIRD);
        verify("ming2",  Initial.M,    Head.OPEN, Body.I,       Tail.NG,      Tone.SECOND);
        verify("xiu1",   Initial.X,    Head.I,    Body.O,       Tail.VOWEL_U, Tone.FIRST);
        verify("kong3",  Initial.K,    Head.OPEN, Body.O,       Tail.NG,      Tone.THIRD);
        verify("lin2",   Initial.L,    Head.OPEN, Body.I,       Tail.N,       Tone.SECOND);
        verify("qing1",  Initial.Q,    Head.OPEN, Body.I,       Tail.NG,      Tone.FIRST);
        verify("qing3",  Initial.Q,    Head.OPEN, Body.I,       Tail.NG,      Tone.THIRD);
        verify("qing2",  Initial.Q,    Head.OPEN, Body.I,       Tail.NG,      Tone.SECOND);
        verify("zi4",    Initial.Z,    Head.OPEN, Body.NULL,    Tail.NONE,    Tone.FOURTH);
        verify("hua1",   Initial.H,    Head.U,    Body.A,       Tail.NONE,    Tone.FIRST);
        verify("guo2",   Initial.G,    Head.U,    Body.O,       Tail.NONE,    Tone.SECOND);
        verify("pin3",   Initial.P,    Head.OPEN, Body.I,       Tail.N,       Tone.THIRD);
        verify("sen1",   Initial.S,    Head.OPEN, Body.E,       Tail.N,       Tone.FIRST);
        verify("ao2",    Initial.ZERO, Head.OPEN, Body.A,       Tail.VOWEL_U, Tone.SECOND);
        verify("biang2", Initial.B,    Head.I,    Body.A,       Tail.NG,      Tone.SECOND);

        // Additional coverage
        verify("zhi1",   Initial.ZH,   Head.OPEN, Body.NULL,    Tail.NONE,    Tone.FIRST);
        verify("chi1",   Initial.CH,   Head.OPEN, Body.NULL,    Tail.NONE,    Tone.FIRST);
        verify("shi4",   Initial.SH,   Head.OPEN, Body.NULL,    Tail.NONE,    Tone.FOURTH);
        verify("si1",    Initial.S,    Head.OPEN, Body.NULL,    Tail.NONE,    Tone.FIRST);
        verify("wu3",    Initial.ZERO, Head.U,    Body.U,       Tail.NONE,    Tone.THIRD);
        verify("wang2",  Initial.ZERO, Head.U,    Body.A,       Tail.NG,      Tone.SECOND);
        verify("wei4",   Initial.ZERO, Head.U,    Body.E,       Tail.VOWEL_I, Tone.FOURTH);
        verify("wen2",   Initial.ZERO, Head.U,    Body.E,       Tail.N,       Tone.SECOND);
        verify("you3",   Initial.ZERO, Head.I,    Body.O,       Tail.VOWEL_U, Tone.THIRD);
        verify("yan2",   Initial.ZERO, Head.I,    Body.A,       Tail.N,       Tone.SECOND);
        verify("yin1",   Initial.ZERO, Head.OPEN, Body.I,       Tail.N,       Tone.FIRST);
        verify("ying2",  Initial.ZERO, Head.OPEN, Body.I,       Tail.NG,      Tone.SECOND);
        verify("yong3",  Initial.ZERO, Head.I,    Body.O,       Tail.NG,      Tone.THIRD);
        verify("lv4",    Initial.L,    Head.V,    Body.V,       Tail.NONE,    Tone.FOURTH);
        verify("nv3",    Initial.N,    Head.V,    Body.V,       Tail.NONE,    Tone.THIRD);
        verify("jue2",   Initial.J,    Head.V,    Body.E_CARON, Tail.NONE,    Tone.SECOND);
        verify("quan2",  Initial.Q,    Head.V,    Body.A,       Tail.N,       Tone.SECOND);
        verify("xun2",   Initial.X,    Head.V,    Body.E,       Tail.N,       Tone.SECOND);
        verify("gui4",   Initial.G,    Head.U,    Body.E,       Tail.VOWEL_I, Tone.FOURTH);
        verify("dui4",   Initial.D,    Head.U,    Body.E,       Tail.VOWEL_I, Tone.FOURTH);
        verify("liu2",   Initial.L,    Head.I,    Body.O,       Tail.VOWEL_U, Tone.SECOND);
        verify("jiu3",   Initial.J,    Head.I,    Body.O,       Tail.VOWEL_U, Tone.THIRD);
        verify("duan4",  Initial.D,    Head.U,    Body.A,       Tail.N,       Tone.FOURTH);
        verify("lun2",   Initial.L,    Head.U,    Body.E,       Tail.N,       Tone.SECOND);
        verify("guang1", Initial.G,    Head.U,    Body.A,       Tail.NG,      Tone.FIRST);
        verify("er2",    Initial.ZERO, Head.OPEN, Body.ER,      Tail.NONE,    Tone.SECOND);
        verify("ye4",    Initial.ZERO, Head.I,    Body.E_CARON, Tail.NONE,    Tone.FOURTH);
        verify("yue4",   Initial.ZERO, Head.V,    Body.E_CARON, Tail.NONE,    Tone.FOURTH);
        verify("yuan2",  Initial.ZERO, Head.V,    Body.A,       Tail.N,       Tone.SECOND);
        verify("yun2",   Initial.ZERO, Head.V,    Body.E,       Tail.N,       Tone.SECOND);

        System.out.println("\nAll " + testCount + " pinyin parse tests passed!");
    }

    private static int testCount = 0;

    private static void verify(String pinyin, Initial ini, Head h, Body b, Tail t, Tone tone) {
        testCount++;
        var d = parse(pinyin);
        if (d.initial != ini || d.head != h || d.body != b || d.tail != t || d.tone != tone) {
            System.err.printf("FAIL: %s%n  expected: %s, %s, %s, %s, %s%n  got:      %s, %s, %s, %s, %s%n",
                    pinyin, ini, h, b, t, tone, d.initial, d.head, d.body, d.tail, d.tone);
            throw new AssertionError("Pinyin parse failed for: " + pinyin);
        }
        System.out.printf("  OK  %-10s → %s, %s, %s, %s, %s%n", pinyin, ini, h, b, t, tone);
    }
}
