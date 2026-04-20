package kranji.common.depth4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Sanity assertion on {@link Depth4#ALL}.
 *
 * <p>Currently empty — depth-4 has no records in the Zi2500 seed corpus.
 * The list exists so that the registry shape is consistent across depths
 * and higher-depth codegen can still reach it through the chain.</p>
 */
class Depth4AllSizeTest {

    @Test
    void all_is_empty() {
        assertEquals(0, Depth4.ALL.size(),
                "Depth4.ALL is expected to remain empty until a depth-4 "
                        + "character is added to the corpus.");
    }
}
