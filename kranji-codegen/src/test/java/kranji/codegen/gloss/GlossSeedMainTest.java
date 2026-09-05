package kranji.codegen.gloss;

import kranji.gloss.tsv.GlossTsv;
import kranji.simple.gloss.Priority;
import kranji.simple.gloss.ZiGloss;
import kranji.zi.ZiPartition;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The generator, against the real corpus and a stubbed dictionary.
 *
 * <p>The definitions are passed in rather than read from the 8MB drop, so these
 * run on a clean checkout where {@code input/} does not exist. The corpus is
 * the real one — that is the half worth not faking, because the partition rule
 * and the polyphone split are both properties of it.</p>
 */
class GlossSeedMainTest {

    /** 丞 U+4E1E, monophonic and in partition 0. */
    private static final int CHENG = 0x4E1E;

    /** 位 U+4F4D, monophonic, partition 0, four senses on offer. */
    private static final int WEI = 0x4F4D;

    @Test
    void theOutputParsesUnderTheReaderTheHandCraftedSetUses() {
        // The point of building through the model rather than printing rows.
        // If this ever fails, the seeder is writing a file that looks like a
        // gloss file and is not one.
        var result = GlossSeedMain.seed(0, Map.of(
                CHENG, "assist, aid, rescue",
                WEI, "throne; position, post; rank, status; seat"));

        var read = GlossTsv.readSenses("p000.tsv", result.tsv());

        assertTrue(read.ok(), () -> "problems: " + read.problems());
        assertFalse(read.entries().isEmpty());
    }

    @Test
    void everySeededRowIsInThePartitionItWasAskedFor() {
        // A generator that quietly wrote a neighbouring character would produce
        // a partition that reviews clean and leaves a hole somewhere else.
        var result = GlossSeedMain.seed(7, Map.of(CHENG, "assist"));
        var read = GlossTsv.readSenses("p007.tsv", result.tsv());

        for (ZiGloss entry : read.entries()) {
            assertEquals(7, ZiPartition.of(entry.zi().codePoint()),
                    () -> entry.zi().value() + " is not in partition 7");
        }
    }

    @Test
    void polyphonesAreQueuedRatherThanGuessed() {
        // The whole reason the seeder is monophone-only. A per-character field
        // cannot say which sense belongs to which reading, and a wrong split is
        // worse than an absent one because it looks finished.
        var result = GlossSeedMain.seed(0, Map.of());

        assertFalse(result.queuedPolyphone().isEmpty(),
                "partition 0 does contain polyphones");
        var read = GlossTsv.readSenses("p000.tsv", result.tsv());
        for (ZiGloss entry : read.entries()) {
            assertEquals(1, entry.sounds().size(),
                    () -> entry.zi().value() + " was seeded at more than one reading");
        }
    }

    @Test
    void aCharacterWithNoDefinitionIsCountedRatherThanInvented() {
        // Seeding nothing is the honest answer; the character goes on a list
        // for somebody to write.
        var result = GlossSeedMain.seed(0, Map.of());

        assertFalse(result.noSource().isEmpty());
        assertEquals(0, result.seededPairs(),
                "with no definitions there is nothing to seed");
    }

    @Test
    void theBandsFollowThePositionTheSourceOfferedThemIn() {
        // A dictionary field says which gloss it leads with and ranks nothing
        // after that. Asserting a considered priority would be the generator
        // inventing one, so the band follows position and a reviewer re-ranks.
        var result = GlossSeedMain.seed(0, Map.of(WEI, "throne; position; rank"));
        var read = GlossTsv.readSenses("p000.tsv", result.tsv());

        var sound = read.entries().get(0).sounds().get(0);
        assertEquals(Priority.PRIMARY,
                sound.senseOf(kranji.simple.gloss.Meaning.of("throne")).ranking().priority());
        assertEquals(Priority.SECONDARY,
                sound.senseOf(kranji.simple.gloss.Meaning.of("position")).ranking().priority());
        assertEquals(Priority.AUXILIARY,
                sound.senseOf(kranji.simple.gloss.Meaning.of("rank")).ranking().priority());
    }

    @Test
    void regeneratingTheSameInputProducesTheSameBytes() {
        // A generator whose output churns cannot be re-run, and one that cannot
        // be re-run is one nobody re-runs when the source moves.
        var definitions = Map.of(CHENG, "assist, aid, rescue",
                                 WEI, "throne; position, post");

        assertEquals(GlossSeedMain.seed(0, definitions).tsv(),
                     GlossSeedMain.seed(0, definitions).tsv());
    }

    @Test
    void aPartitionFileIsNamedSoTheySortInOrder() {
        // p007 before p010. Unpadded, "10" sorts before "7" and the review
        // queue reads in an order nobody chose.
        assertEquals("p000", GlossSeedMain.name(0));
        assertEquals("p007", GlossSeedMain.name(7));
        assertEquals("p100", GlossSeedMain.name(100));
        assertTrue(GlossSeedMain.name(7).compareTo(GlossSeedMain.name(10)) < 0);
    }
}
