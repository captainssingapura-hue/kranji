package kranji.common.depth1;

import kranji.zi.BlockStructures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Every member of {@link Depth1#ALL} must have composition depth 1 —
 * the structural invariant that defines what belongs in this module.
 *
 * <p>Depth here is counted by
 * {@link BlockStructures#depthOf(kranji.zi.BlockStructure)}: each
 * {@link kranji.zi.ComposedBlock} layer (named {@link kranji.zi.ComposedZi}
 * or anonymous {@link kranji.zi.ComposedPart}) adds one; atomic
 * {@link kranji.zi.SingularBlock}s are depth 0.</p>
 */
class Depth1DepthTest {

    @Test
    void every_record_has_depth_one() {
        java.util.List<String> mismatches = new java.util.ArrayList<>();
        for (var z : Depth1.ALL) {
            int d = BlockStructures.depthOf(z);
            if (d != 1) {
                String ch = z.character();
                int cp = ch.codePointAt(0);
                mismatches.add(String.format("U+%04X (%s) depth=%d", cp, ch, d));
            }
        }
        assertEquals(java.util.List.of(), mismatches,
                "Depth1.ALL members with wrong depth:\n  "
                        + String.join("\n  ", mismatches));
    }
}
