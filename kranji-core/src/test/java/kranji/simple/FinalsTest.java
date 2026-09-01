package kranji.simple;

import kranji.pinyin.Final;
import kranji.pinyin.PinyinSyllable;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Pins {@link Finals} to the parser.
 *
 * <p>The constants were derived from {@link PinyinSyllable#parse} rather than
 * reasoned out, and several are not what intuition suggests. This test is what
 * keeps them honest: if a constant is ever edited to the "obvious" triple, the
 * spelling stops matching and the build says so.</p>
 */
class FinalsTest {

    /** Every final and the spelling it must produce. */
    private static Map<String, Final> expected() {
        var m = new LinkedHashMap<String, Final>();
        m.put("a", Finals.A);       m.put("o", Finals.O);       m.put("e", Finals.E);
        m.put("er", Finals.ER);     m.put("ai", Finals.AI);     m.put("ei", Finals.EI);
        m.put("ao", Finals.AO);     m.put("ou", Finals.OU);     m.put("an", Finals.AN);
        m.put("en", Finals.EN);     m.put("ang", Finals.ANG);   m.put("eng", Finals.ENG);
        m.put("ong", Finals.ONG);
        m.put("i", Finals.I);       m.put("ia", Finals.IA);     m.put("ie", Finals.IE);
        m.put("iao", Finals.IAO);   m.put("iou", Finals.IOU);   m.put("ian", Finals.IAN);
        m.put("in", Finals.IN);     m.put("iang", Finals.IANG); m.put("ing", Finals.ING);
        m.put("iong", Finals.IONG);
        m.put("u", Finals.U);       m.put("ua", Finals.UA);     m.put("uo", Finals.UO);
        m.put("uai", Finals.UAI);   m.put("uei", Finals.UEI);   m.put("uan", Finals.UAN);
        m.put("uen", Finals.UEN);   m.put("uang", Finals.UANG); m.put("ueng", Finals.UENG);
        m.put("ü", Finals.V);  m.put("üe", Finals.VE);
        m.put("üan", Finals.VAN); m.put("üen", Finals.VN);
        return m;
    }

    @TestFactory
    Stream<DynamicTest> everyFinalSpellsAsExpected() {
        return expected().entrySet().stream().map(e ->
                DynamicTest.dynamicTest(e.getKey(),
                        () -> assertEquals(e.getKey(), e.getValue().spelling(),
                                "final constant no longer spells as expected")));
    }

    @Test
    void allThirtySixFinalsArePresent() {
        assertEquals(36, expected().size(), "Mandarin has 36 finals");
    }

    @Test
    void finalsAreDistinct() {
        List<Final> all = List.copyOf(expected().values());
        assertEquals(all.size(), all.stream().distinct().count(),
                "two named finals resolve to the same triple");
    }

    @Test
    void inAndIangDifferInHeadNotOnlyInTail() {
        // The pair most likely to be "corrected" into being wrong: `in` is
        // open-headed with an I body, while `ian` carries an I head.
        assertNotEquals(Finals.IN.head(), Finals.IAN.head());
        assertEquals("in", Finals.IN.spelling());
        assertEquals("ian", Finals.IAN.spelling());
    }

    @Test
    void syllabicFinalIsEmptyButDisplaysAPlaceholder() {
        assertEquals("", Finals.SYLLABIC.spelling(), "the syllabic nucleus is empty");
        // PinyinSyllable supplies the written `i` for zhi/chi/shi/ri/zi/ci/si.
        assertEquals("zhī",
                new PinyinSyllable(kranji.pinyin.Initial.ZH, Finals.SYLLABIC,
                        kranji.pinyin.Tone.FIRST).toDiacritic());
    }
}
