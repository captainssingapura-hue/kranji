package kranji.common.depth5;

import kranji.zi.BlockStructures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Every member of {@link Depth5#ALL} must have composition depth 5 —
 * see {@link BlockStructures#depthOf(kranji.zi.BlockStructure)} for the
 * counting rule.
 */
class Depth5DepthTest {

    @Test
    void every_record_has_depth_five() {
        java.util.List<String> mismatches = new java.util.ArrayList<>();
        for (var z : Depth5.ALL) {
            int d = BlockStructures.depthOf(z);
            if (d != 5) {
                String ch = z.character();
                int cp = ch.codePointAt(0);
                mismatches.add(String.format("U+%04X (%s) depth=%d", cp, ch, d));
            }
        }
        assertEquals(java.util.List.of(), mismatches,
                "Depth5.ALL members with wrong depth:\n  "
                        + String.join("\n  ", mismatches));
    }
}
