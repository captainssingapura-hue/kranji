package kranji.common.depth3;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Sanity assertions on {@link Depth3#ALL}.
 *
 * <p>Locked at 1 record (魔) — the count when depth-3 content moved out of
 * {@code kranji-common} into this module. The per-pinyin codegen migration
 * must preserve this count exactly. Any future additions should bump this
 * assertion in lockstep.</p>
 */
class Depth3AllSizeTest {

    @Test
    void all_has_exactly_2_records() {
        assertEquals(2, Depth3.ALL.size(),
                "Depth3.ALL.size() drifted — codegen round-trip lost or "
                        + "added records.");
    }

    @Test
    void all_records_are_nonnull_and_distinct() {
        Set<Object> seen = new HashSet<>();
        for (var z : Depth3.ALL) {
            assertNotNull(z, "Depth3.ALL contains a null entry");
            assertEquals(true, seen.add(System.identityHashCode(z) + ":" + z.character()),
                    "Duplicate record in Depth3.ALL: " + z.character());
        }
    }
}
