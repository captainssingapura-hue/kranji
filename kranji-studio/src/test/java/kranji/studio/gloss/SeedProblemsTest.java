package kranji.studio.gloss;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The worklist, and the one state in it nobody decided.
 *
 * <p>These pin what a reviewer is shown. A problem silently leaving the queue
 * is the failure that matters here — it is indistinguishable from work having
 * been done.</p>
 */
class SeedProblemsTest {

    /** 得 U+5F97, queued by the seeder and split by hand at dé, de and děi. */
    private static final int DE = 0x5F97;

    /** 陑 U+9651, unseeded and glossed by nobody. */
    private static final int ER = 0x9651;

    @Test
    void whatTheHandCraftedSetAnswersIsNotInTheWorklist() {
        // The seeder queues a polyphone because IT cannot split one. It has no
        // way to know somebody already did, so 66 characters arrive as problems
        // that are finished work.
        assertEquals(SeedProblems.rows().size(),
                SeedProblems.outstanding().size() + SeedProblems.answered().size());

        assertFalse(SeedProblems.answered().isEmpty(),
                "the hand-crafted set overlaps the seed; some of it is already done");
        for (SeedProblems.Row row : SeedProblems.answered()) {
            assertEquals(SeedProblems.State.ANSWERED, row.state());
        }
    }

    @Test
    void aPolyphoneSplitByHandCountsAsAnswered() {
        // 得 is the useful case: three readings, all glossed by a person, and
        // the seeder queued it anyway. Being right about the COUNT would not be
        // enough - the test behind this compares the readings themselves.
        var de = SeedProblems.rows().stream().filter(r -> r.codePoint() == DE).toList();

        assertEquals(1, de.size());
        assertEquals(SeedProblems.State.ANSWERED, de.get(0).state());
        assertTrue(SeedProblems.outstanding().stream().noneMatch(r -> r.codePoint() == DE));
    }

    @Test
    void aCharacterNobodyHasGlossedStaysInTheWorklist() {
        var er = SeedProblems.outstanding().stream()
                .filter(r -> r.codePoint() == ER).toList();

        assertEquals(1, er.size(), "陑 has no gloss from anybody and is real work");
        assertEquals(SeedProblems.State.OPEN, er.get(0).state());
    }

    @Test
    void aRecordedVerdictWinsOverBeingAnswered() {
        // Both can be true: 氏 was split by hand AND carries a FIXED verdict.
        // The verdict is the half somebody wrote down, so it is the half shown -
        // and the row stays visible, because a decision is worth seeing and a
        // coincidence of coverage is not.
        var shi = SeedProblems.rows().stream()
                .filter(r -> r.codePoint() == 0x6C0F).toList();

        assertEquals(1, shi.size());
        assertEquals(SeedProblems.State.DONE, shi.get(0).state());
        assertTrue(SeedProblems.outstanding().contains(shi.get(0)));
    }

    @Test
    void theRelationShowsEveryProblemAndHidesNone() {
        // A grid holding 1,365 rows while reporting 1,299 would be wrong in a
        // way nobody catches: the count reads plausible either way. The state
        // column carries the distinction instead.
        var rows = GlossRelations.rowsOf("problem", java.util.List.of(), java.util.List.of());
        assertEquals(SeedProblems.rows().size(), rows.size());
    }

    @Test
    void theUnscopedViewIsOrderedBySound() {
        // Partition order is the generator's - modulo 101 scatters homophones
        // across every file. A reviewer wants bai2 after bai2, because the
        // judgement about one is usually the judgement just made about the last.
        var readings = GlossRelations.rowsOf("problem", java.util.List.of(), java.util.List.of())
                .stream().map(r -> (String) r.values().get(1)).toList();

        var sorted = readings.stream().sorted().toList();
        assertEquals(sorted, readings, "the problem grid is not in reading order");

        // And not in the order the files happen to be read in, which is what
        // it would be if nobody had sorted it.
        assertFalse(readings.equals(SeedProblems.rows().stream()
                        .map(SeedProblems.Row::reading).toList()),
                "sorting made no difference - is the sort actually applied?");
    }
}
