package kranji.common.depth2;

import kranji.zi.BlockStructures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Every member of {@link Depth2#ALL} must have composition depth 2 —
 * see {@link BlockStructures#depthOf(kranji.zi.BlockStructure)} for the
 * counting rule.
 */
class Depth2DepthTest {

    @Test
    void every_record_has_depth_two() {
        java.util.List<String> mismatches = new java.util.ArrayList<>();
        for (var z : Depth2.ALL) {
            int d = BlockStructures.depthOf(z);
            if (d != 2) {
                String ch = z.character();
                int cp = ch.codePointAt(0);
                mismatches.add(String.format("U+%04X (%s) depth=%d", cp, ch, d));
            }
        }
        assertEquals(java.util.List.of(), mismatches,
                "Depth2.ALL members with wrong depth:\n  "
                        + String.join("\n  ", mismatches));
    }
}
