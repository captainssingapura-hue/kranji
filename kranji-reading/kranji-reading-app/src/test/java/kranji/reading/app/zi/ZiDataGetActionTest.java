package kranji.reading.app.zi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the syllable data modules.
 *
 * <p>The first test is the important one. These modules are served outside the
 * crate and therefore outside conformance, on the argument that they carry data
 * rather than logic. That argument only holds while it is true, so it is
 * asserted rather than assumed — this test is the substitute for the rule set
 * they do not go through.</p>
 */
class ZiDataGetActionTest {

    // ── The guarantee that buys the conformance exemption ──────────────

    @Test
    void emitsDataOnlyAndNeverBehaviour() {
        String js = ZiDataGetAction.moduleFor("h.ao.3");

        for (String construct : new String[]{
                "function", "=>", "class ", "if(", "if (", "for(", "for (",
                "while", "return", "eval", "import", "require", "Function"}) {
            assertFalse(js.contains(construct),
                    () -> "a data module must contain no behaviour, found: " + construct);
        }
        // Everything it does emit is an export binding.
        js.lines()
          .filter(l -> !l.isBlank() && !l.startsWith("//"))
          .forEach(l -> assertTrue(
                  l.startsWith("export const") || l.startsWith("  ") || l.equals("];"),
                  () -> "unexpected line in a data module: " + l));
    }

    // ── Content ────────────────────────────────────────────────────────

    @Test
    void carriesTheSyllableAndItsCharacters() {
        String js = ZiDataGetAction.moduleFor("h.ao.3");

        assertTrue(js.contains("export const syllable = \"hao3\""));
        assertTrue(js.contains("export const initial = \"h\""));
        assertTrue(js.contains("export const finalPart = \"ao\""));
        assertTrue(js.contains("export const tone = 3"));
        assertTrue(js.contains("glyph: \"好\""), "the character itself");
        assertTrue(js.contains("codePoint: \"U+597D\""));
    }

    @Test
    void carriesEveryReadingOfAPolyphonicCharacter() {
        String js = ZiDataGetAction.moduleFor("h.ao.3");
        assertTrue(js.contains("readings: [\"hao3\", \"hao4\"]"), "好 reads hǎo and hào");
        assertTrue(js.contains("polyphonic: true"));
        assertTrue(js.contains("readHereByDefault: true"));
    }

    @Test
    void findsACharacterUnderANonDefaultReading() {
        // 好 defaults to hǎo, so the tree has no hào terminal - but the data
        // layer answers for it, flagging that this is not its default reading.
        String js = ZiDataGetAction.moduleFor("h.ao.4");

        assertTrue(js.contains("glyph: \"好\""), "hào is a reading of 好");
        assertTrue(js.contains("readHereByDefault: false"));
    }

    @Test
    void handlesSyllabicConsonantsAndFoldedFinals() {
        assertTrue(ZiDataGetAction.moduleFor("zh.i.1").contains("glyph: \"知\""),
                "zhī - the syllabic consonant writes its final as i");
        assertTrue(ZiDataGetAction.moduleFor("l.v.4").contains("glyph: \"绿\""),
                "lǜ - the u-umlaut final folds to v in the module name");
    }

    // ── Unknown input ──────────────────────────────────────────────────

    @Test
    void anUnknownSyllableStillYieldsAValidModule() {
        // An import that rejects is harder to handle in the browser than one
        // resolving to nothing, so the module is always well-formed.
        //
        // The address has to be phonotactically impossible rather than merely
        // unused: over the full standard set almost every well-formed syllable
        // has characters, so "unpopulated" and "not a syllable" have collapsed
        // into each other. f- never takes the -ia final.
        String js = ZiDataGetAction.moduleFor("f.ia.1");

        assertTrue(js.contains("export const characters = [];"));
        assertTrue(js.contains("export const problem ="));
    }

    @Test
    void aRealSyllableIsNoLongerEmpty() {
        // The counterpart to the above, and the reason it had to change: qiáo
        // was an empty address at seed scale and is a populated one now.
        String js = ZiDataGetAction.moduleFor("q.iao.2");

        assertFalse(js.contains("export const characters = [];"));
        assertTrue(js.contains("export const syllable = \"qiao2\""));
    }

    @Test
    void aMalformedNameYieldsAValidModule() {
        assertTrue(ZiDataGetAction.moduleFor("nonsense").contains("export const problem ="));
        assertTrue(ZiDataGetAction.moduleFor("h.ao.x").contains("export const problem ="));
        assertTrue(ZiDataGetAction.moduleFor("h.ao.3.4").contains("export const problem ="));
    }

    @Test
    void quotingIsSafe() {
        // Nothing in the corpus needs escaping today; the test states the
        // requirement so a future entry cannot break the module silently.
        String js = ZiDataGetAction.moduleFor("h.ao.3");
        long quotes = js.chars().filter(c -> c == '"').count();
        assertTrue(quotes % 2 == 0, "every string literal is closed");
    }
}
