package kranji.simple.gloss;

import kranji.pinyin.PinyinSyllable;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kranji.simple.gloss.GlossDsl.zi;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The authoring surface.
 *
 * <p>Most of what this DSL guarantees is guaranteed by the compiler and cannot
 * be tested from here — {@code zi("大").means(…)} is not a failing test, it is
 * a file that does not build. What is left is that the chain assembles the tree
 * it reads like, and that the invariants types cannot reach are enforced at
 * construction.</p>
 */
class GlossDslTest {

    private static EgRef phrase(String s) { return EgRef.to(s); }

    // ── The chain builds the tree it reads like ────────────────────────

    @Test
    void theSimpleCaseIsOneLine() {
        ZiGloss big = zi("大").read("da4").means("big").eg("大人").build();

        assertEquals(1, big.sounds().size());
        assertEquals("dà", big.sounds().get(0).reading().toDiacritic());
        assertEquals("big", big.sounds().get(0).primary().text());
    }

    @Test
    void doneClosesExactlyOneLevel() {
        // Two senses on one reading: the first done() returns the reading, not
        // the character, so the second means() lands on the same reading.
        ZiGloss xing = zi("行")
                .read("xing2")
                    .means("to go; to walk").eg("行走").done()
                    .means("all right; OK").eg("不行").done()
                .build();

        assertEquals(1, xing.sounds().size(), "both senses belong to one reading");
        assertEquals(2, xing.sounds().get(0).senses().size());
    }

    @Test
    void aSecondDoneReturnsTheCharacter() {
        ZiGloss xing = zi("行")
                .read("xing2").means("to go; to walk").eg("行走").done()
                .done()
                .read("hang2").means("a row; a line").eg("一行字").done()
                .build();

        assertEquals(2, xing.sounds().size());
        assertEquals("xíng", xing.sounds().get(0).reading().toDiacritic());
        assertEquals("háng", xing.sounds().get(1).reading().toDiacritic());
    }

    @Test
    void buildFromASenseClosesEverythingBeneathIt() {
        ZiGloss de = zi("得")
                .read("de0").means("(joins a verb to how it is done)").eg("跑得快").done()
                .done()
                .read("de2").means("to get; to obtain").eg("得到").build();

        assertEquals(2, de.sounds().size());
        assertEquals(1, de.sounds().get(1).senses().size());
    }

    // ── Order is stamped, at both levels ───────────────────────────────

    @Test
    void theBuilderStampsTheOrderOfSenses() {
        // The first sense written is the primary, by construction. Nothing is
        // typed to say so and there is no invariant to break.
        ZiGloss xing = zi("行").read("xing2")
                .means("to go; to walk").eg("行走").done()
                .means("all right; OK").eg("不行").done()
                .build();

        assertEquals(List.of(Meaning.of("to go; to walk"), Meaning.of("all right; OK")),
                xing.sounds().get(0).orderedMeanings());
        assertEquals("to go; to walk", xing.sounds().get(0).primary().text());
    }

    @Test
    void theBuilderStampsTheOrderOfExamples() {
        // Stamped at done(), so nothing downstream has to preserve list
        // position — Map.copyOf makes no ordering promise and does not need to.
        ZiGloss de = zi("得").read("de2").means("to get; to obtain")
                .eg("得到").eg("得奖").build();

        var sense = de.sounds().get(0).senseOf(Meaning.of("to get; to obtain"));
        assertEquals(List.of(phrase("得到"), phrase("得奖")), sense.orderedExamples());
        assertEquals(phrase("得到"), sense.bestExample());
    }

    @Test
    void reorderingTheSourceReordersTheRanking() {
        // Deliberate: the order IS the ranking, so a diff that moves a line is
        // a diff that changes what a child sees. Visible in review, which a
        // silently positional default would not have been.
        ZiGloss other = zi("行").read("xing2")
                .means("all right; OK").eg("不行").done()
                .means("to go; to walk").eg("行走").done()
                .build();

        assertEquals("all right; OK", other.sounds().get(0).primary().text());
    }

    // ── Identity is the phrase, and the meaning ────────────────────────

    @Test
    void aRepeatedExampleCannotExistTwice() {
        // EgKey is the phrase and nothing else, so a repeat is one key. There
        // is no check to write - which was the point of splitting identity
        // from content.
        ZiGloss hao = zi("好").read("hao3").means("good; well")
                .eg("很好").eg("很好").build();

        assertEquals(1, hao.sounds().get(0).senseOf(Meaning.of("good; well"))
                .examples().size());
    }

    @Test
    void aRepeatedMeaningIsRefusedRatherThanAbsorbed() {
        // The asymmetry with examples is deliberate. An example written twice
        // says nothing new, so it collapses. A SENSE written twice means the
        // author meant two different ones and wrote one twice — collapsing it
        // would leave the reading a sense short and say nothing about it.
        var repeated = zi("好").read("hao3")
                .means("good; well").eg("很好").done()
                .means("good; well").eg("你好").done();

        assertThrows(IllegalArgumentException.class, repeated::build);
    }

    @Test
    void aCharacterCannotDeclareOneReadingTwice() {
        var repeated = zi("行")
                .read("xing2").means("to go").eg("行走").done().done()
                .read("xing2").means("all right").eg("不行").done();

        assertThrows(IllegalArgumentException.class, repeated::build);
    }

    @Test
    void anEmptyEntryIsRefused() {
        assertThrows(IllegalArgumentException.class, () -> zi("大").build());
    }

    @Test
    void aRankingCanCarryItsReason() {
        ZiGloss hao = zi("好")
                .read("hao3").means("good; well").eg("很好").done()
                .means("easy to; pleasant to").because("only before a verb")
                       .eg("好吃").build();

        var sense = hao.sounds().get(0).senseOf(Meaning.of("easy to; pleasant to"));
        assertEquals("only before a verb", sense.ranking().because().orElseThrow());
        assertEquals(1, sense.ranking().order());
    }

    @Test
    void orderIsWithinAReadingNotAcrossThem() {
        // The pane asks "which sense of THIS reading", so a reading's only
        // sense is its primary whatever the character does elsewhere. 地 dì is
        // far rarer than 地 de, and earth is still dì's primary.
        ZiGloss di = zi("地")
                .read("de0").means("(makes a word describe how something is done)")
                           .eg("慢慢地走").done()
                .done()
                .read("di4").means("earth; ground").eg("地上").build();

        assertTrue(di.sounds().get(1).senseOf(Meaning.of("earth; ground")).isPrimary());
    }

    @Test
    void theEntryFindsItsOwnReading() {
        ZiGloss hao = zi("好")
                .read("hao3").means("good; well").eg("很好").done()
                .done()
                .read("hao4").means("to be fond of").eg("爱好").build();

        assertEquals("to be fond of",
                hao.at(PinyinSyllable.parse("hao4")).orElseThrow().primary().text());
    }
}
