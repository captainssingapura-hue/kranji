package kranji.common.depth5;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Sanity assertions on {@link Depth5#ALL}.
 *
 * <p>Locked at 1 record (𰻝 biáng) — the count when depth-5 content moved
 * out of {@code kranji-common} into this module. The per-pinyin codegen
 * migration must preserve this count exactly.</p>
 */
class Depth5AllSizeTest {

    @Test
    void all_has_exactly_1_record() {
        assertEquals(1, Depth5.ALL.size(),
                "Depth5.ALL.size() drifted — codegen round-trip lost or "
                        + "added records.");
    }

    @Test
    void all_records_are_nonnull_and_distinct() {
        Set<Object> seen = new HashSet<>();
        for (var z : Depth5.ALL) {
            assertNotNull(z, "Depth5.ALL contains a null entry");
            assertEquals(true, seen.add(System.identityHashCode(z) + ":" + z.character()),
                    "Duplicate record in Depth5.ALL: " + z.character());
        }
    }
}
