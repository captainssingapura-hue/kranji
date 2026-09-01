package kranji.reading.app.zi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the per-character module.
 *
 * <p>Outside the crate and outside conformance like the other data actions, so
 * the literals-only guarantee is asserted here rather than by a rule set.</p>
 */
class ZiDetailGetActionTest {

    // ── The guarantee that buys the conformance exemption ──────────────

    @Test
    void emitsDataOnlyAndNeverBehaviour() {
        String js = ZiDetailGetAction.moduleFor("U+597D");

        for (String construct : new String[]{
                "function", "=>", "class ", "if(", "if (", "for(", "for (",
                "while", "return", "eval", "import", "require", "Function"}) {
            assertFalse(js.contains(construct),
                    () -> "a data module must contain no behaviour, found: " + construct);
        }
        js.lines()
          .filter(l -> !l.isBlank() && !l.startsWith("//"))
          .forEach(l -> assertTrue(
                  l.startsWith("export const") || l.startsWith("  ") || l.equals("];"),
                  () -> "unexpected line in a data module: " + l));
    }

    // ── Identity ───────────────────────────────────────────────────────

    @Test
    void aCharacterIsAddressedByCodepoint() {
        String js = ZiDetailGetAction.moduleFor("U+597D");

        assertTrue(js.contains("export const glyph = \"好\""));
        assertTrue(js.contains("export const codePoint = \"U+597D\""));
        assertTrue(js.contains("export const principal = \"hǎo\""));
        assertTrue(js.contains("export const polyphonic = true"));
    }

    @Test
    void theCharacterItselfIsAcceptedAsAnAddress() {
        // A caller holding the glyph should not have to convert it first.
        assertTrue(ZiDetailGetAction.moduleFor("好").contains("codePoint = \"U+597D\""));
        assertTrue(ZiDetailGetAction.moduleFor("597D").contains("glyph = \"好\""),
                "bare hex too");
    }

    // ── Readings ───────────────────────────────────────────────────────

    @Test
    void everyReadingIsCarriedDecomposedAndAddressed() {
        String js = ZiDetailGetAction.moduleFor("U+597D");

        assertTrue(js.contains("reading: \"hǎo\", address: \"h.ao.3\", principal: true"));
        assertTrue(js.contains("reading: \"hào\", address: \"h.ao.4\", principal: false"));
        assertTrue(js.contains("nucleus: \"a\", coda: \"u\""), "the coda is phonemic");
    }

    @Test
    void aReadingKnowsHowManyOthersShareIt() {
        // hǎo is read by 好 and 郝, so from 好 there is one other.
        String js = ZiDetailGetAction.moduleFor("U+597D");
        assertTrue(js.contains("homophones: 1"));
    }

    @Test
    void frequencyEvidenceIsCarriedPerReadingAndInSummary() {
        String js = ZiDetailGetAction.moduleFor("U+597D");

        assertTrue(js.contains("observed: 6060"), "hǎo's own count");
        assertTrue(js.contains("export const evidence = \"hǎo 6060  hāo 142  hào 115\""));
    }

    @Test
    void aGlideReadingIsAddressedUnderItsGlideBranch() {
        // 一 is yī, which the tree files under y- rather than zero.
        String js = ZiDetailGetAction.moduleFor("U+4E00");
        assertTrue(js.contains("address: \"y.i.1\""));
    }

    @Test
    void anUnmodelledReadingIsNamedRatherThanHidden() {
        // 哼 keeps hēng; hng is beyond what this build can parse.
        String js = ZiDetailGetAction.moduleFor("U+54FC");
        assertTrue(js.contains("export const unmodelled = \"hng\""));
    }

    // ── Unknown input ──────────────────────────────────────────────────

    @Test
    void anUnknownCharacterStillYieldsAValidModule() {
        String js = ZiDetailGetAction.moduleFor("U+0041");   // 'A'

        assertTrue(js.contains("export const readings = [];"));
        assertTrue(js.contains("export const problem ="));
    }

    @Test
    void aMalformedAddressYieldsAValidModule() {
        String js = ZiDetailGetAction.moduleFor("not a codepoint");

        assertTrue(js.contains("export const readings = [];"));
        assertTrue(js.contains("export const problem ="));
    }
}
