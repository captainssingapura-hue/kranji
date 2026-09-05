package kranji.studio.gloss;

import kranji.simple.gloss.ZiCollections;
import kranji.simple.gloss.ZiGloss;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Impact weighs the queue against the library, and the arithmetic reconciles.
 *
 * <p>These are the checks that stop the widget being confidently wrong. A
 * ranking nobody can verify is worse than no ranking: it would send somebody to
 * spend an afternoon on the character it happened to put first.</p>
 */
class GlossImpactTest {

    private static final List<ZiGloss> GLOSSES = ZiCollections.glosses().all();
    private static final List<GlossImpact.Row> ROWS = GlossImpact.rows(GLOSSES);

    @Test
    void everyProblemIsWeighed() {
        // Including the ones nothing reads. A worklist that silently dropped
        // three quarters of the queue would report a total nobody could tie
        // back to the Issues grid beside it.
        assertEquals(SeedProblems.rows().size(), ROWS.size());
    }

    @Test
    void blindNeverExceedsRead() {
        for (GlossImpact.Row r : ROWS) {
            assertTrue(r.blind() <= r.read(),
                    () -> r.glyph() + " is blind " + r.blind() + " times in " + r.read()
                        + " readings, which is more often than it is read");
        }
    }

    @Test
    void aProblemNothingReadsWeighsNothing() {
        var unread = ROWS.stream().filter(r -> r.read() == 0).toList();
        assertFalse(unread.isEmpty(), "the queue is much larger than the library");
        for (GlossImpact.Row r : unread) {
            assertEquals(0, r.blind());
            assertEquals(0, r.articles());
            assertEquals("", r.groups());
        }
    }

    @Test
    void blindTotalsMatchTheDemandRelation() {
        // The reconciliation that matters. Impact counts reading-events with no
        // meaning by walking problems; the demand relation counts pairs with no
        // meaning by walking the library. They are different walks over the
        // same fact and must agree, or one of them is measuring the wrong set.
        Map<String, Integer> uses = GlossDemand.demand().uses();
        int viaDemand = GlossDemand.rows(GLOSSES).stream()
                .filter(r -> "todo".equals(r.status()))
                .mapToInt(r -> uses.getOrDefault(r.codePoint() + ":" + r.reading(), 0))
                .sum();
        int viaImpact = ROWS.stream().mapToInt(GlossImpact.Row::blind).sum();
        assertEquals(viaDemand, viaImpact,
                "every unglossed reading-event belongs to exactly one problem");
    }

    @Test
    void theHeaviestRowIsBlindNotMerelyBusy() {
        // The bug this ranking exists to avoid: 的 is read 366 times and is
        // glossed, so it must not lead. Whatever leads must have nothing
        // written for it.
        GlossImpact.Row first = ROWS.get(0);
        assertTrue(first.blind() > 0,
                () -> "the worklist opens on " + first.glyph() + ", which costs a reader nothing");
    }

    @Test
    void aGlossedCharacterCanStillBeAnOpenProblem() {
        // And it is reported honestly rather than hidden: read high, blind
        // zero. That row is finished work waiting to be ticked off, and the two
        // columns are what tell it apart from work not started.
        var busyButCovered = ROWS.stream()
                .filter(r -> r.read() > 0 && r.blind() == 0).toList();
        assertFalse(busyButCovered.isEmpty(),
                "the hand-crafted layer covers some of what the seeder queued");
    }

    @Test
    void everyPlaceBelongsToAWeighedProblem() {
        var problems = ROWS.stream().map(GlossImpact.Row::pairKey).collect(Collectors.toSet());
        for (GlossImpact.Place p : GlossImpact.places(GLOSSES)) {
            assertTrue(problems.contains(p.problem()),
                    () -> "a breakdown row names '" + p.problem() + "', which no problem has");
            assertTrue(p.times() > 0, "a place nobody reads is not a place");
        }
    }

    @Test
    void theBreakdownAddsUpToTheRowItExplains() {
        var byProblem = GlossImpact.places(GLOSSES).stream()
                .collect(Collectors.groupingBy(GlossImpact.Place::problem,
                        Collectors.summingInt(GlossImpact.Place::times)));
        for (GlossImpact.Row r : ROWS) {
            assertEquals(r.read(), byProblem.getOrDefault(r.pairKey(), 0),
                    () -> "the articles listed for " + r.glyph()
                        + " do not account for its " + r.read() + " readings");
        }
    }

    @Test
    void aBreakdownRowSaysWhetherThatReadingIsAnswered() {
        for (GlossImpact.Place p : GlossImpact.places(GLOSSES)) {
            assertTrue(List.of("blind", "covered").contains(p.status()), p.status());
        }
        // And the blind ones are exactly the row's blind count.
        var blindByProblem = GlossImpact.places(GLOSSES).stream()
                .filter(p -> "blind".equals(p.status()))
                .collect(Collectors.groupingBy(GlossImpact.Place::problem,
                        Collectors.summingInt(GlossImpact.Place::times)));
        for (GlossImpact.Row r : ROWS) {
            assertEquals(r.blind(), blindByProblem.getOrDefault(r.pairKey(), 0), r.glyph());
        }
    }
}
