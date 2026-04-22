package kranji.pinyin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Ports the hand-verified cases from the retired {@code PinyinParser.main()}
 * self-test into JUnit, and adds diacritic-form coverage matching the new
 * {@link PinyinParser#parse(String)} entry point.
 *
 * <p>Every {@code verify} call exercises a real orthographic edge case we've
 * hit while parsing the character catalog — syllabic consonants (zi/ci/si/
 * zhi/chi/shi/ri), y/w zero-initial spelling, abbreviated finals (ui/iu/un),
 * ü after j/q/x, and so on. If any of these regress, the assertion points
 * straight at the offending spelling.</p>
 */
final class PinyinParserTest {

    @Test
    void parseNumbered_basicInitials() {
        verify("ren2",   Initial.R,    Head.OPEN, Body.E,       Tail.N,       Tone.SECOND);
        verify("shan1",  Initial.SH,   Head.OPEN, Body.A,       Tail.N,       Tone.FIRST);
        verify("mu4",    Initial.M,    Head.U,    Body.U,       Tail.NONE,    Tone.FOURTH);
        verify("kou3",   Initial.K,    Head.OPEN, Body.O,       Tail.VOWEL_U, Tone.THIRD);
        verify("huo3",   Initial.H,    Head.U,    Body.O,       Tail.NONE,    Tone.THIRD);
        verify("sen1",   Initial.S,    Head.OPEN, Body.E,       Tail.N,       Tone.FIRST);
        verify("pin3",   Initial.P,    Head.OPEN, Body.I,       Tail.N,       Tone.THIRD);
    }

    @Test
    void parseNumbered_syllabicConsonants() {
        verify("ri4",    Initial.R,    Head.OPEN, Body.NULL,    Tail.NONE,    Tone.FOURTH);
        verify("zi4",    Initial.Z,    Head.OPEN, Body.NULL,    Tail.NONE,    Tone.FOURTH);
        verify("zhi1",   Initial.ZH,   Head.OPEN, Body.NULL,    Tail.NONE,    Tone.FIRST);
        verify("chi1",   Initial.CH,   Head.OPEN, Body.NULL,    Tail.NONE,    Tone.FIRST);
        verify("shi4",   Initial.SH,   Head.OPEN, Body.NULL,    Tail.NONE,    Tone.FOURTH);
        verify("si1",    Initial.S,    Head.OPEN, Body.NULL,    Tail.NONE,    Tone.FIRST);
    }

    @Test
    void parseNumbered_zeroInitialYW() {
        verify("yi3",    Initial.ZERO, Head.OPEN, Body.I,       Tail.NONE,    Tone.THIRD);
        verify("yin1",   Initial.ZERO, Head.OPEN, Body.I,       Tail.N,       Tone.FIRST);
        verify("ying2",  Initial.ZERO, Head.OPEN, Body.I,       Tail.NG,      Tone.SECOND);
        verify("ye4",    Initial.ZERO, Head.I,    Body.E_CARON, Tail.NONE,    Tone.FOURTH);
        verify("yao2",   Initial.ZERO, Head.I,    Body.A,       Tail.VOWEL_U, Tone.SECOND);
        verify("wu3",    Initial.ZERO, Head.U,    Body.U,       Tail.NONE,    Tone.THIRD);
        verify("wang2",  Initial.ZERO, Head.U,    Body.A,       Tail.NG,      Tone.SECOND);
        verify("wei4",   Initial.ZERO, Head.U,    Body.E,       Tail.VOWEL_I, Tone.FOURTH);
        verify("yue4",   Initial.ZERO, Head.V,    Body.E_CARON, Tail.NONE,    Tone.FOURTH);
        verify("yuan2",  Initial.ZERO, Head.V,    Body.A,       Tail.N,       Tone.SECOND);
        verify("yun2",   Initial.ZERO, Head.V,    Body.E,       Tail.N,       Tone.SECOND);
        verify("er2",    Initial.ZERO, Head.OPEN, Body.ER,      Tail.NONE,    Tone.SECOND);
    }

    @Test
    void parseNumbered_abbreviatedFinals() {
        verify("liu2",   Initial.L,    Head.I,    Body.O,       Tail.VOWEL_U, Tone.SECOND);   // iu → iou
        verify("gui4",   Initial.G,    Head.U,    Body.E,       Tail.VOWEL_I, Tone.FOURTH);   // ui → uei
        verify("lun2",   Initial.L,    Head.U,    Body.E,       Tail.N,       Tone.SECOND);   // un → uen
        verify("xun2",   Initial.X,    Head.V,    Body.E,       Tail.N,       Tone.SECOND);   // j/q/x ün → üen
    }

    @Test
    void parseNumbered_uAfterJQX() {
        verify("ji3",    Initial.J,    Head.OPEN, Body.I,       Tail.NONE,    Tone.THIRD);
        verify("jue2",   Initial.J,    Head.V,    Body.E_CARON, Tail.NONE,    Tone.SECOND);
        verify("quan2",  Initial.Q,    Head.V,    Body.A,       Tail.N,       Tone.SECOND);
        verify("xia4",   Initial.X,    Head.I,    Body.A,       Tail.NONE,    Tone.FOURTH);
        verify("xiu1",   Initial.X,    Head.I,    Body.O,       Tail.VOWEL_U, Tone.FIRST);    // iu after x stays i-medial
    }

    @Test
    void parseNumbered_lvAndNv() {
        verify("lv4",    Initial.L,    Head.V,    Body.V,       Tail.NONE,    Tone.FOURTH);
        verify("nv3",    Initial.N,    Head.V,    Body.V,       Tail.NONE,    Tone.THIRD);
    }

    // ── Diacritic-form parsing ──────────────────────────────────────────

    @Test
    void parseDiacritic_tones() {
        assertEquals(PinyinParser.parseNumbered("ma1"), PinyinParser.parse("mā"));
        assertEquals(PinyinParser.parseNumbered("ma2"), PinyinParser.parse("má"));
        assertEquals(PinyinParser.parseNumbered("ma3"), PinyinParser.parse("mǎ"));
        assertEquals(PinyinParser.parseNumbered("ma4"), PinyinParser.parse("mà"));
    }

    @Test
    void parseDiacritic_allBodies() {
        assertEquals(PinyinParser.parseNumbered("qing1"),  PinyinParser.parse("qīng"));
        assertEquals(PinyinParser.parseNumbered("shui3"),  PinyinParser.parse("shuǐ"));
        assertEquals(PinyinParser.parseNumbered("huo3"),   PinyinParser.parse("huǒ"));
        assertEquals(PinyinParser.parseNumbered("lv4"),    PinyinParser.parse("lǜ"));
        assertEquals(PinyinParser.parseNumbered("nv3"),    PinyinParser.parse("nǚ"));
        assertEquals(PinyinParser.parseNumbered("yuan2"),  PinyinParser.parse("yuán"));
    }

    @Test
    void parseDiacritic_neutralTone() {
        // No diacritic on any vowel → neutral (tone 5)
        assertEquals(Tone.NEUTRAL, PinyinParser.parse("ma").tone());
        assertEquals(Tone.NEUTRAL, PinyinParser.parse("de").tone());
    }

    // ── Error paths ─────────────────────────────────────────────────────

    @Test
    void parse_rejectsNullBlankAndUnknown() {
        assertThrows(IllegalArgumentException.class, () -> PinyinParser.parse(null));
        assertThrows(IllegalArgumentException.class, () -> PinyinParser.parse(""));
        assertThrows(IllegalArgumentException.class, () -> PinyinParser.parse("   "));
        // No such final
        assertThrows(IllegalArgumentException.class, () -> PinyinParser.parse("xq7"));
    }

    // ── Helpers ─────────────────────────────────────────────────────────

    private static void verify(String pinyin, Initial ini, Head h, Body b, Tail t, Tone tone) {
        var d = PinyinParser.parse(pinyin);
        assertEquals(ini, d.initial(), () -> pinyin + " initial");
        assertEquals(h, d.head(),      () -> pinyin + " head");
        assertEquals(b, d.body(),      () -> pinyin + " body");
        assertEquals(t, d.tail(),      () -> pinyin + " tail");
        assertEquals(tone, d.tone(),   () -> pinyin + " tone");
    }
}
