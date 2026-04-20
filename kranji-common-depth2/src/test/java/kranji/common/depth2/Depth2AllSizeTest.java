package kranji.common.depth2;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Sanity assertions on {@link Depth2#ALL}.
 *
 * <p>Locked at 53 records — the count established when Batch 4 moved the
 * depth-2 records out of {@code kranji-common}. The forthcoming per-pinyin
 * codegen migration must preserve that count exactly. Any future additions
 * to the depth-2 catalog should bump this assertion in lockstep — leaving
 * a deliberate gate against accidental losses during a codegen
 * round-trip.</p>
 *
 * <p>Also asserts that every member is non-null and that the list contains
 * no duplicates by reference identity.</p>
 */
class Depth2AllSizeTest {

    @Test
    void all_has_exactly_58_records() {
        assertEquals(58, Depth2.ALL.size(),
                "Depth2.ALL.size() drifted — codegen round-trip lost or "
                        + "added records. Re-snapshot via Depth2SnapshotMain "
                        + "and re-run Depth2GenerateMain to investigate.");
    }

    @Test
    void all_records_are_nonnull_and_distinct() {
        Set<Object> seen = new HashSet<>();
        for (var z : Depth2.ALL) {
            assertNotNull(z, "Depth2.ALL contains a null entry");
            assertEquals(true, seen.add(System.identityHashCode(z) + ":" + z.character()),
                    "Duplicate record in Depth2.ALL: " + z.character());
        }
    }
}
