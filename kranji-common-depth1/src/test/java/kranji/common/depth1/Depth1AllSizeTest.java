package kranji.common.depth1;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Sanity assertions on the regenerated {@link Depth1#ALL} list.
 *
 * <p>Locked at 441 records — the count established when Batch 2 moved the
 * depth-1 records out of {@code kranji-common}. Batch 3 (the per-pinyin
 * codegen migration) must preserve that count exactly. Any future
 * additions to the depth-1 catalog should bump this assertion in lockstep
 * — leaving a deliberate gate against accidental losses during a codegen
 * round-trip.</p>
 *
 * <p>Also asserts that every member is non-null and that the list contains
 * no duplicates by reference identity.</p>
 */
class Depth1AllSizeTest {

    @Test
    void all_has_exactly_443_records() {
        assertEquals(443, Depth1.ALL.size(),
                "Depth1.ALL.size() drifted — codegen round-trip lost or "
                        + "added records. Re-snapshot via Depth1SnapshotMain "
                        + "and re-run Depth1GenerateMain to investigate.");
    }

    @Test
    void all_records_are_nonnull_and_distinct() {
        Set<Object> seen = new HashSet<>();
        for (var z : Depth1.ALL) {
            assertNotNull(z, "Depth1.ALL contains a null entry");
            // Use System identity wrapper so two distinct ComposedZi
            // instances with equal field content still register as
            // separate (they shouldn't appear, but if they did we'd
            // want this test to fail loudly).
            assertEquals(true, seen.add(System.identityHashCode(z) + ":" + z.character()),
                    "Duplicate record in Depth1.ALL: " + z.character());
        }
    }
}
