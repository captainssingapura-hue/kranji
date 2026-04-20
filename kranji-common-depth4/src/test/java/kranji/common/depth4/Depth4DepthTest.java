package kranji.common.depth4;

import kranji.zi.BlockStructures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Every member of {@link Depth4#ALL} must have composition depth 4 —
 * see {@link BlockStructures#depthOf(kranji.zi.BlockStructure)} for the
 * counting rule. Currently vacuous (Depth4 is empty) but guards against
 * a future depth-mismatched add.
 */
class Depth4DepthTest {

    @Test
    void every_record_has_depth_four() {
        java.util.List<String> mismatches = new java.util.ArrayList<>();
        for (var z : Depth4.ALL) {
            int d = BlockStructures.depthOf(z);
            if (d != 4) {
                String ch = z.character();
                int cp = ch.codePointAt(0);
                mismatches.add(String.format("U+%04X (%s) depth=%d", cp, ch, d));
            }
        }
        assertEquals(java.util.List.of(), mismatches,
                "Depth4.ALL members with wrong depth:\n  "
                        + String.join("\n  ", mismatches));
    }
}
