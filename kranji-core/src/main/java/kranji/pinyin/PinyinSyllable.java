package kranji.pinyin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A fully decomposed Mandarin syllable: initial (声母) + final (韵母) + tone (声调).
 *
 * <p>This is the canonical typed form. Two display forms round-trip
 * through {@link #parse(String)} and {@link #toDiacritic()} /
 * {@link #numberedTone()} — the catalog stores syllables as diacritic
 * strings ({@code "mǎ"}), and the typed form is the single source of
 * truth for filtering, rendering, and graph construction.</p>
 *
 * @param initial the onset consonant
 * @param fin     the final (韵母), named {@code fin} to avoid the Java reserved word
 * @param tone    the tone
 */
public record PinyinSyllable(
        Initial initial,
        @JsonProperty("final") Final fin,
        Tone tone
) {

    /**
     * Returns the numbered-tone representation (e.g. {@code "qing1"},
     * {@code "zhi1"}, {@code "lü4"}). Natural key for PinyinHub vertex
     * IDs and the canonical round-trip form fed back into
     * {@link #parse(String)}.
     *
     * <p>Syllabic consonants ({@code Body.NULL}) get the conventional
     * placeholder {@code "i"} so {@code zhī → "zhi1"} round-trips cleanly.</p>
     */
    public String numberedTone() {
        return base() + tone.number();
    }

    /**
     * Returns the orthographic spelling without tone number
     * (e.g. {@code "qing"}, {@code "zhi"}, {@code "lü"}).
     */
    public String base() {
        String spell = fin.spelling();
        // Syllabic consonant: nucleus is acoustically empty, but pinyin
        // writes a placeholder "i" (zhi, chi, shi, ri, zi, ci, si).
        if (spell.isEmpty() && isSyllabicConsonantInitial(initial)) {
            spell = "i";
        }
        return initial.pinyin() + spell;
    }

    private static boolean isSyllabicConsonantInitial(Initial i) {
        return i == Initial.Z || i == Initial.C || i == Initial.S
            || i == Initial.ZH || i == Initial.CH || i == Initial.SH
            || i == Initial.R;
    }

    // ── Typed ↔ String round-trip ──────────────────────────────────────

    /**
     * Parse a pinyin string in either numbered ({@code "ma3"}) or
     * diacritic ({@code "mǎ"}) form into a typed syllable.
     *
     * <p>Delegates to {@link PinyinParser}; see that class for the set of
     * accepted orthographic conventions.</p>
     *
     * @throws IllegalArgumentException if the input is null, blank, or not
     *                                  a recognisable pinyin syllable
     */
    public static PinyinSyllable parse(String pinyin) {
        return PinyinParser.parse(pinyin).toSyllable();
    }

    /**
     * Render this syllable in diacritic tone form (e.g. {@code "mǎ"},
     * {@code "qīng"}, {@code "nǚ"}). The inverse of {@link #parse(String)}
     * for any well-formed syllable.
     *
     * <p>Applies standard display-form rules:</p>
     * <ul>
     *   <li>After {@code j/q/x}, the medial {@code ü} is written as {@code u}.</li>
     *   <li>Zero-initial syllables gain a {@code y-} or {@code w-} prefix
     *       per the conventional spelling ({@code üe → yue}, {@code uo → wo},
     *       {@code i → yi}, etc.).</li>
     *   <li>The tone mark is placed on the "main" vowel following the
     *       standard rule (a &gt; o &gt; e, otherwise the last of i/u/ü,
     *       with the "iu/ui" exception).</li>
     *   <li>Neutral tone (5) carries no diacritic.</li>
     * </ul>
     */
    public String toDiacritic() {
        String spelling = applyDisplayOrthography(initial, fin);
        // Syllabic consonant — spelling() yielded "", but the display form
        // writes a placeholder "i" and the tone mark sits on that i.
        if (spelling.isEmpty() && isSyllabicConsonantInitial(initial)) {
            spelling = "i";
        }
        if (tone == Tone.NEUTRAL) {
            // Join initial + spelling without a tone mark.
            return initial.pinyin() + spelling;
        }
        String marked = placeTone(spelling, tone);
        return initial.pinyin() + marked;
    }

    // ── Display-orthography helpers (pure; kept here because they are
    //    the inverse of the parser's normalisations) ────────────────────

    /**
     * Produce the orthographic (display) final for an (initial, final) pair —
     * i.e. undo the parser's "ju→jü", "yu→ü", "wo→uo", "iu→iou" style
     * normalisations so the returned string is what a human would write.
     */
    private static String applyDisplayOrthography(Initial initial, Final fin) {
        String spell = fin.spelling();   // phonemic spelling, e.g. "üan", "uen", "iou"

        // Abbreviations
        // "iou" → "iu" (but only after a consonant initial; zero-initial "iou" stays → later becomes "you")
        if (initial != Initial.ZERO && spell.equals("iou")) spell = "iu";
        // "uei" → "ui" (only after consonant initial)
        if (initial != Initial.ZERO && spell.equals("uei")) spell = "ui";
        // "uen" → "un" (only after consonant initial)
        if (initial != Initial.ZERO && spell.equals("uen")) spell = "un";
        // "üen" is j/q/x+ün in display — keep as "ün" here; jqx ü→u will
        // convert to "un" in the j/q/x branch below. Zero-initial "üen"
        // is routed through zeroInitialDisplay ("yun") instead, so leave
        // it alone in that case.
        if (initial != Initial.ZERO && spell.equals("üen")) spell = "ün";

        if (initial == Initial.J || initial == Initial.Q || initial == Initial.X) {
            // ü-medial is written as plain u after j/q/x
            if (spell.startsWith("ü")) spell = "u" + spell.substring("ü".length());
        }

        if (initial == Initial.ZERO) {
            spell = zeroInitialDisplay(spell);
        }

        return spell;
    }

    /**
     * Zero-initial spellings take a conventional y- or w- prefix.
     *  i/in/ing → yi/yin/ying
     *  ia/ie/iao/ian/iang/iou/iong → ya/ye/yao/yan/yang/you/yong
     *  u → wu
     *  ua/uo/uai/uei/uan/uen/uang/ueng → wa/wo/wai/wei/wan/wen/wang/weng
     *  ü/üe/üan/üen → yu/yue/yuan/yun
     */
    private static String zeroInitialDisplay(String spell) {
        // ü-medial zero-initials first (most specific)
        if (spell.equals("ü"))    return "yu";
        if (spell.equals("üe"))   return "yue";
        if (spell.equals("üan"))  return "yuan";
        if (spell.equals("üen"))  return "yun";   // phonemic form
        if (spell.equals("ün"))   return "yun";   // pre-abbreviated form — defensive
        if (spell.startsWith("ü")) {
            // Defensive — no other ü-forms in zero-initial inventory
            return "y" + spell.substring("ü".length());
        }

        // i-medial
        if (spell.equals("i"))    return "yi";
        if (spell.equals("in"))   return "yin";
        if (spell.equals("ing"))  return "ying";
        if (spell.equals("iou"))  return "you";
        if (spell.startsWith("i")) return "y" + spell.substring(1);

        // u-medial
        if (spell.equals("u"))    return "wu";
        if (spell.startsWith("u")) return "w" + spell.substring(1);

        // Pure-vowel (a, o, e, ai, ei, ao, ou, an, en, ang, eng, er, ong)
        return spell;
    }

    /** Place the tone mark on the correct vowel of a toneless spelling. */
    private static String placeTone(String spelling, Tone tone) {
        int idx = toneMarkIndex(spelling);
        if (idx < 0) {
            // No vowel to mark (e.g. syllabic consonant "i" stripped to "")
            // — no-op.
            return spelling;
        }
        char vowel = spelling.charAt(idx);
        char marked = withTone(vowel, tone);
        return spelling.substring(0, idx) + marked + spelling.substring(idx + 1);
    }

    /**
     * Rules (standard Hanyu Pinyin placement):
     * 1) If the spelling contains 'a', mark the 'a'.
     * 2) Else if it contains 'o', mark the 'o'.
     * 3) Else if it contains 'e', mark the 'e'.
     * 4) Else mark the LAST vowel (handles "iu" → mark u, "ui" → mark i).
     * 5) 'ü' is marked if it is the only vowel.
     */
    private static int toneMarkIndex(String spelling) {
        int a = spelling.indexOf('a');
        if (a >= 0) return a;
        int o = spelling.indexOf('o');
        if (o >= 0) return o;
        int e = spelling.indexOf('e');
        if (e >= 0) return e;
        // Last vowel
        for (int i = spelling.length() - 1; i >= 0; i--) {
            char c = spelling.charAt(i);
            if (c == 'i' || c == 'u' || c == 'ü') return i;
        }
        return -1;
    }

    private static char withTone(char vowel, Tone tone) {
        return switch (vowel) {
            case 'a' -> switch (tone) { case FIRST -> 'ā'; case SECOND -> 'á'; case THIRD -> 'ǎ'; case FOURTH -> 'à'; default -> 'a'; };
            case 'o' -> switch (tone) { case FIRST -> 'ō'; case SECOND -> 'ó'; case THIRD -> 'ǒ'; case FOURTH -> 'ò'; default -> 'o'; };
            case 'e' -> switch (tone) { case FIRST -> 'ē'; case SECOND -> 'é'; case THIRD -> 'ě'; case FOURTH -> 'è'; default -> 'e'; };
            case 'i' -> switch (tone) { case FIRST -> 'ī'; case SECOND -> 'í'; case THIRD -> 'ǐ'; case FOURTH -> 'ì'; default -> 'i'; };
            case 'u' -> switch (tone) { case FIRST -> 'ū'; case SECOND -> 'ú'; case THIRD -> 'ǔ'; case FOURTH -> 'ù'; default -> 'u'; };
            case 'ü' -> switch (tone) { case FIRST -> 'ǖ'; case SECOND -> 'ǘ'; case THIRD -> 'ǚ'; case FOURTH -> 'ǜ'; default -> 'ü'; };
            default -> vowel;
        };
    }
}
