package kranji.common.depth3;

import kranji.zi.BlockStructures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Every member of {@link Depth3#ALL} must have composition depth 3 —
 * see {@link BlockStructures#depthOf(kranji.zi.BlockStructure)} for the
 * counting rule.
 */
class Depth3DepthTest {

    @Test
    void every_record_has_depth_three() {
        java.util.List<String> mismatches = new java.util.ArrayList<>();
        for (var z : Depth3.ALL) {
            int d = BlockStructures.depthOf(z);
            if (d != 3) {
                String ch = z.character();
                int cp = ch.codePointAt(0);
                mismatches.add(String.format("U+%04X (%s) depth=%d", cp, ch, d));
            }
        }
        assertEquals(java.util.List.of(), mismatches,
                "Depth3.ALL members with wrong depth:\n  "
                        + String.join("\n  ", mismatches));
    }
}
