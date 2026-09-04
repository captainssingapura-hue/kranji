package kranji.pinyin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link Head#symbol()} — the medial letter, the companion Body and Tail
 * already had.
 */
class HeadSymbolTest {

    @Test
    void everyMedialSpellsItsLetter() {
        assertEquals("",  Head.OPEN.symbol(), "开口呼 has no medial");
        assertEquals("i", Head.I.symbol());
        assertEquals("u", Head.U.symbol());
        assertEquals("ü", Head.V.symbol());
    }

    @Test
    void finalSpellingIsUnchangedByUsingIt() {
        // The switch this replaced lived inside Final.spelling(); these are the
        // cases that would break if the two ever disagreed.
        assertEquals("ao",  PinyinSyllable.parse("hǎo").fin().spelling());
        assertEquals("uang", PinyinSyllable.parse("huáng").fin().spelling());
        assertEquals("ü",   PinyinSyllable.parse("lǜ").fin().spelling());
        assertEquals("ie",  PinyinSyllable.parse("xiè").fin().spelling());
        assertEquals("",    PinyinSyllable.parse("zhī").fin().spelling(),
                "空韵 - the syllabic fricative has no written final");
    }
}
