package kranji.codegen.phonic;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Pins the correction table.
 *
 * <p>This file rewrites the corpus, so a row appearing or vanishing changes
 * what every consumer reads. A table nothing asserts is a table that can lose
 * a row to a bad merge and show up as a corpus that quietly reverted — which
 * is a long way from the edit that caused it.</p>
 */
class SourceCorrectionsTest {

    private static final int HENG = 21756;   // 哼 U+54FC
    private static final int AI   = 27448;   // 欸 U+6B38

    @Test
    void exactlyTheExpectedCharactersAreCorrected() {
        assertEquals(List.of(HENG, AI), SourceCorrections.corrected(),
                "a correction appearing or vanishing rewrites the corpus");
        assertEquals(5, SourceCorrections.size(),
                "one reading dropped and four respelt");
    }

    @Test
    void aDroppedReadingLeavesTheRestInSourceOrder() {
        // hng was notation, not a second reading. 哼 reads hēng.
        assertEquals(List.of("hēng"),
                SourceCorrections.apply(HENG, List.of("hēng", "hng")));
    }

    @Test
    void aRespeltReadingKeepsItsPlace() {
        // The whole point: 欸 has five readings, not one. Respelling them and
        // dropping them are opposite decisions, and reading order is what the
        // principal is chosen from.
        assertEquals(List.of("ǎi", "ēi", "éi", "ěi", "èi"),
                SourceCorrections.apply(AI, List.of("ǎi", "ê̄", "ế", "ê̌", "ề")));
    }

    @Test
    void aCharacterWithNoCorrectionIsHandedBackUntouched() {
        // Identity, not a copy: this runs for every character in the source and
        // the answer for almost all of them is "nothing to do".
        List<String> hao = List.of("hǎo", "hào");
        assertSame(hao, SourceCorrections.apply(0x597D, hao));
    }
}
