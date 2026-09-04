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

        // Scanned OUTSIDE the string literals. The module now carries English
        // prose - meanings - and English contains "important" and "to return".
        // A raw substring scan called both of those behaviour, which was a
        // false alarm about the data and, worse, a check that would have had
        // to be weakened to let real glosses through. Stripping the literals
        // first keeps the guarantee exactly where it belongs: no behaviour in
        // any position that could execute.
        for (String construct : new String[]{
                "function", "=>", "class ", "if(", "if (", "for(", "for (",
                "while", "return", "eval", "import", "require", "Function"}) {
            assertFalse(outsideStrings(js).contains(construct),
                    () -> "a data module must contain no behaviour, found: " + construct);
        }
        js.lines()
          .filter(l -> !l.isBlank() && !l.startsWith("//"))
          .forEach(l -> assertTrue(
                  l.startsWith("export const") || l.startsWith("  ") || l.equals("];"),
                  () -> "unexpected line in a data module: " + l));
    }

    @Test
    void aMeaningMayContainAWordThatLooksLikeCode() {
        // The reason the scan strips literals rather than trusting the data.
        // These are real glosses, and a check that failed on them would push
        // somebody to reword a meaning to satisfy a test - which is the tail
        // wagging the dog.
        assertTrue(outsideStrings("export const a = \"important\";").isBlank()
                || !outsideStrings("export const a = \"important\";").contains("import"),
                "prose inside a literal is data");
        assertTrue(outsideStrings("export const a = \"to return; to go back\";")
                        .contains("return") == false,
                "and so is a meaning that happens to say 'return'");
        // But behaviour outside one is still caught, which is the whole point.
        assertTrue(outsideStrings("export const a = \"x\"; return 1;").contains("return"));
    }

    /**
     * The module with every double-quoted literal removed.
     *
     * <p>Walks rather than regexes, because an escaped quote inside a meaning
     * would end the literal early for a naive pattern and let the rest of the
     * prose back into the scan.</p>
     */
    private static String outsideStrings(String js) {
        var out = new StringBuilder();
        boolean inString = false;
        for (int i = 0; i < js.length(); i++) {
            char c = js.charAt(i);
            if (inString) {
                if (c == '\\') { i++; continue; }        // the escaped char, whatever it is
                if (c == '"') inString = false;
            } else if (c == '"') {
                inString = true;
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }

    // ── Identity ───────────────────────────────────────────────────────

    @Test
    void aCharacterIsAddressedByCodepoint() {
        String js = ZiDetailGetAction.moduleFor("U+597D");

        assertTrue(js.contains("export const glyph = \"好\""));
        assertTrue(js.contains("export const codePoint = \"U+597D\""));
        assertTrue(js.contains("export const principal = \"hao3\""));
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

        assertTrue(js.contains("reading: \"hao3\", address: \"h.ao.3\", principal: true"));
        assertTrue(js.contains("reading: \"hao4\", address: \"h.ao.4\", principal: false"));
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
