package kranji.reading.app.gloss;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the bulk glossary module.
 *
 * <p>Outside the crate and outside conformance like the other data actions, so
 * the literals-only guarantee is asserted here rather than by a rule set.</p>
 */
class ZiGlossGetActionTest {

    // ── The guarantee that buys the conformance exemption ──────────────

    @Test
    void emitsDataOnlyAndNeverBehaviour() {
        String js = ZiGlossGetAction.module();

        // Outside the string literals, for the reason ZiDetailGetActionTest
        // gives at length: this module is almost entirely English prose, and
        // English says "important" and "to return".
        for (String construct : new String[]{
                "function", "=>", "class ", "if(", "if (", "for(", "for (",
                "while", "return", "eval", "import", "require", "Function"}) {
            assertFalse(outsideStrings(js).contains(construct),
                    () -> "a data module must contain no behaviour, found: " + construct);
        }
        js.lines()
          .filter(l -> !l.isBlank() && !l.startsWith("//"))
          .forEach(l -> assertTrue(
                  l.startsWith("export const") || l.startsWith("  ") || l.equals("};"),
                  () -> "unexpected line in a data module: " + l));
    }

    // ── What it carries ────────────────────────────────────────────────

    @Test
    void theCountIsStatedRatherThanLeftToBeMeasured() {
        // A view that finds nothing has to tell a person whether the glossary
        // is empty or whether their character is simply not in it. Those look
        // identical from a lookup that missed, so the module says which.
        String js = ZiGlossGetAction.module();

        assertTrue(js.contains("export const glossed = "));
        assertFalse(js.contains("export const glossed = 0;"),
                "a collection is deployed, so the count is not zero");
    }

    @Test
    void aPairIsKeyedTheWayEveryOtherTierKeysIt() {
        // codepoint:reading, canonical - the key the known set stores, the
        // census counts, and the gloss tier files under. The Known grid does a
        // lookup with a key it already holds, and this is what says it can.
        String js = ZiGlossGetAction.module();

        assertTrue(js.contains("\"" + "地".codePointAt(0) + ":di4\": "), js.substring(0, 200));
        assertTrue(js.contains("\"" + "地".codePointAt(0) + ":de0\": "),
                "both readings, separately - that is the point of the pair key");
    }

    @Test
    void aReadingCarriesItsPrimaryMeaningAndNotAllOfThem() {
        // This feeds a table cell. 忙 mang2 is written as "busy" then "to
        // hurry"; the cell gets the first. Anything wanting both asks
        // /zi-detail, which has the room.
        String js = ZiGlossGetAction.module();
        int mang = "忙".codePointAt(0);

        assertTrue(js.contains("\"" + mang + ":mang2\": \"busy\""), "the primary");
        assertFalse(js.contains("\"" + mang + ":mang2\": \"busy|to hurry\""),
                "and only the primary");
    }

    @Test
    void theOrderIsStableSoTheModuleCachesCleanly() {
        // Map.copyOf would have made this fail intermittently: its iteration
        // order is unspecified, so identical data could emit a different file
        // on every run and defeat the caching the whole design rests on.
        assertTrue(ZiGlossGetAction.module().equals(ZiGlossGetAction.module()));
    }

    /** The module with every double-quoted literal removed. */
    private static String outsideStrings(String js) {
        var out = new StringBuilder();
        boolean inString = false;
        for (int i = 0; i < js.length(); i++) {
            char c = js.charAt(i);
            if (inString) {
                if (c == '\\') { i++; continue; }
                if (c == '"') inString = false;
            } else if (c == '"') {
                inString = true;
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
}
