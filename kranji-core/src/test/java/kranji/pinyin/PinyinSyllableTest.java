package kranji.pinyin;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Focused coverage of {@link PinyinSyllable#parse(String)} and
 * {@link PinyinSyllable#toDiacritic()} — the typed-to-string and
 * string-to-typed boundary that anchors the whole catalog.
 *
 * <p>The big claim this test carries is the round-trip invariant:
 * {@code parse(toDiacritic(syllable)).equals(syllable)} for every syllable
 * the catalog can contain, and conversely {@code toDiacritic(parse(s))}
 * equals the canonical display form of {@code s}. The sample set below is
 * picked to hit each orthographic rule exactly once.</p>
 */
final class PinyinSyllableTest {

    /** Canonical diacritic strings drawn from real catalog entries. */
    private static final List<String> DIACRITIC_SAMPLES = List.of(
            // Basic initials + tones
            "mā", "má", "mǎ", "mà",
            "shuǐ",  // sh + ui
            "huǒ",   // h + uo
            "qīng",  // q + ing
            "mù",    // m + u
            "kǒu",   // k + ou
            "rén",   // r + en
            "pǐn",   // p + in
            "sēn",   // s + en

            // Syllabic consonants
            "zì", "zhī", "chī", "shì", "sī", "rì",

            // Zero-initial y-/w-
            "yǐ", "yīn", "yīng", "yè", "yáo", "wǔ", "wáng", "wèi",
            "yuè", "yuán", "yún", "ér",

            // Abbreviated finals
            "liú",   // iu → iou
            "guì",   // ui → uei
            "lún",   // un → uen

            // j/q/x with ü written as u
            "jǐ", "jué", "quán", "xià", "xiū",

            // ü body (catalog keeps real ü characters)
            "lǜ", "nǚ",

            // Neutral tone
            "ma", "de"
    );

    @Test
    void roundTrip_parseThenToDiacritic_returnsCanonicalInput() {
        for (String s : DIACRITIC_SAMPLES) {
            PinyinSyllable syl = PinyinSyllable.parse(s);
            assertEquals(s, syl.toDiacritic(),
                    () -> "round-trip broken for '" + s + "' — typed=" + syl);
        }
    }

    @Test
    void roundTrip_numberedFormEqualsParseThenNumberedTone() {
        // Every diacritic sample has a numbered equivalent; parsing either
        // form must yield the same PinyinSyllable.
        for (String diacritic : DIACRITIC_SAMPLES) {
            PinyinSyllable fromDiacritic = PinyinSyllable.parse(diacritic);
            PinyinSyllable fromNumbered  = PinyinSyllable.parse(fromDiacritic.numberedTone());
            assertEquals(fromDiacritic, fromNumbered,
                    () -> "diacritic '" + diacritic + "' != its numbered round-trip");
        }
    }

    @Test
    void toDiacritic_ma3_isMǎ() {
        var mǎ = new PinyinSyllable(
                Initial.M,
                new Final(Head.OPEN, Body.A, Tail.NONE),
                Tone.THIRD);
        assertEquals("mǎ", mǎ.toDiacritic());
        assertEquals("ma3", mǎ.numberedTone());
    }

    @Test
    void toDiacritic_qing1_marksTheI() {
        // Placement rule: no a/o/e → mark the last vowel (here 'i')
        var qīng = new PinyinSyllable(
                Initial.Q,
                new Final(Head.OPEN, Body.I, Tail.NG),
                Tone.FIRST);
        assertEquals("qīng", qīng.toDiacritic());
    }

    @Test
    void toDiacritic_liu2_marksTheU_notTheI() {
        // "iu" is spelled with tone on the u (abbreviation for "iou")
        var liú = new PinyinSyllable(
                Initial.L,
                new Final(Head.I, Body.O, Tail.VOWEL_U),
                Tone.SECOND);
        assertEquals("liú", liú.toDiacritic());
    }

    @Test
    void toDiacritic_gui4_marksTheI_notTheU() {
        // "ui" is spelled with tone on the i (abbreviation for "uei")
        var guì = new PinyinSyllable(
                Initial.G,
                new Final(Head.U, Body.E, Tail.VOWEL_I),
                Tone.FOURTH);
        assertEquals("guì", guì.toDiacritic());
    }

    @Test
    void toDiacritic_xun2_writesUnNotUen() {
        // j/q/x + ü → written as u; abbreviation ün → un
        var xún = new PinyinSyllable(
                Initial.X,
                new Final(Head.V, Body.E, Tail.N),
                Tone.SECOND);
        assertEquals("xún", xún.toDiacritic());
    }

    @Test
    void toDiacritic_lv4_writesAsLü() {
        // l and n keep the ü
        var lǜ = new PinyinSyllable(
                Initial.L,
                new Final(Head.V, Body.V, Tail.NONE),
                Tone.FOURTH);
        assertEquals("lǜ", lǜ.toDiacritic());
    }

    @Test
    void toDiacritic_zeroInitialYuePattern() {
        // ü-medial zero-initial → yue
        var yuè = new PinyinSyllable(
                Initial.ZERO,
                new Final(Head.V, Body.E_CARON, Tail.NONE),
                Tone.FOURTH);
        assertEquals("yuè", yuè.toDiacritic());

        // u-medial zero-initial → wo
        var wǒ = new PinyinSyllable(
                Initial.ZERO,
                new Final(Head.U, Body.O, Tail.NONE),
                Tone.THIRD);
        assertEquals("wǒ", wǒ.toDiacritic());

        // i-medial zero-initial → yi
        var yī = new PinyinSyllable(
                Initial.ZERO,
                new Final(Head.OPEN, Body.I, Tail.NONE),
                Tone.FIRST);
        assertEquals("yī", yī.toDiacritic());
    }

    @Test
    void toDiacritic_syllabicConsonant_hasNoVowelMark() {
        // zhī = zh + syllabic-i; the phonemic spelling is empty; the
        // conventional written form is "zhi"
        var zhī = new PinyinSyllable(
                Initial.ZH,
                new Final(Head.OPEN, Body.NULL, Tail.NONE),
                Tone.FIRST);
        assertEquals("zhī", zhī.toDiacritic());
    }

    @Test
    void toDiacritic_neutralTone_omitsDiacritic() {
        var ma = new PinyinSyllable(
                Initial.M,
                new Final(Head.OPEN, Body.A, Tail.NONE),
                Tone.NEUTRAL);
        assertEquals("ma", ma.toDiacritic());
    }
}
