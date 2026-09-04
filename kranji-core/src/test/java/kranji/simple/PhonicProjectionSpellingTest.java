package kranji.simple;

import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.zi.tree.ZiBranch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A final's branch reads as the syllable it forms, not as a bare final.
 *
 * <p>The cases that matter are the ones where the answer is not concatenation
 * — pinyin spells a final differently depending on what precedes it.</p>
 */
class PhonicProjectionSpellingTest {

    private static final PhonicProjection P = PhonicProjection.INSTANCE;

    private static String spelling(String reading) {
        PinyinSyllable s = PinyinSyllable.parse(reading);
        return PhonicProjection.spellingOf(s.initial(), s.fin());
    }

    @Test
    void aPlainSyllableIsJustTheJoin() {
        assertEquals("fa", spelling("fā"));
        assertEquals("hao", spelling("hǎo"));
    }

    @Test
    void jqxDropTheUmlaut() {
        assertEquals("ju", spelling("jū"));
        assertEquals("xue", spelling("xuě"));
        assertEquals("lü", spelling("lǘ"), "but l keeps it - the fold is initial-dependent");
    }

    @Test
    void theZeroInitialGrowsAGlide() {
        assertEquals("yi", spelling("yī"));
        assertEquals("wu", spelling("wǔ"));
        assertEquals("yuan", spelling("yuán"));
    }

    @Test
    void abbreviatedFinalsAreWrittenShort() {
        assertEquals("liu", spelling("liù"), "iou is written iu after an initial");
        assertEquals("hui", spelling("huí"), "uei is written ui");
        assertEquals("chun", spelling("chūn"), "uen is written un");
    }

    @Test
    void theSyllabicFricativeGetsItsPlaceholder() {
        assertEquals("zhi", spelling("zhī"), "空韵 - the final is empty, the syllable is not");
    }

    @Test
    void theTreeUsesItWhileTheFinalSegmentIsUnchanged() {
        ZiBranch f = P.branchFor(Initial.F);
        var a = f.children().stream()
                .filter(c -> c.segment().equals("a")).findFirst().orElseThrow();

        assertEquals("a", a.segment(), "the final's address must not move");
        assertEquals("fa", a.label(), "but the label reads as a syllable");

        // yi lives under y-, not under zero: the glide is a branch of its own.
        var i = P.branchFor("y").children().stream()
                .filter(c -> c.segment().equals("i")).findFirst().orElseThrow();
        assertEquals("yi", i.label());
        assertTrue(i.label().length() > 1, "a one-letter row is what this fixes");

        assertTrue(P.branchFor(Initial.ZERO).children().stream()
                        .noneMatch(c -> c.segment().equals("i")),
                "zero is literal now - nothing written with a glide remains in it");
    }
}
