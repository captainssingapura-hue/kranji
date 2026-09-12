package kranji.reading.app.zi;

import kranji.phonic.SyllableIndex;
import kranji.reading.app.gloss.ZiGlossary;
import kranji.zi.ZiCharUTF8;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

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
    void aCorrectedReadingReachesTheReaderInsteadOfBeingNamedUnmodelled() {
        // This used to assert unmodelled = "hng" for 哼, and 欸 used to serve
        // one reading where the standard has five. Both were notations
        // kTGHZ2013 uses and the standard replaced, so SourceCorrections fixes
        // them in the corpus and the reader has nothing left to apologise for.
        // The field stays - a later source can reintroduce the case, and
        // naming a reading this build cannot hold still beats hiding it.
        String heng = ZiDetailGetAction.moduleFor("U+54FC");
        assertTrue(heng.contains("export const unmodelled = \"\";"),
                "hng was notation, not a reading beyond this build");
        assertTrue(heng.contains("export const polyphonic = false;"));

        // 欸 is the one this mattered for: ēi hail, éi surprise, ěi
        // disagreement, èi assent, beside ǎi. One gloss covering all five is
        // the wrong split that looks finished, and it was the seeded state.
        String ai = ZiDetailGetAction.moduleFor("U+6B38");
        assertTrue(ai.contains("export const unmodelled = \"\";"));
        assertTrue(ai.contains("export const polyphonic = true;"),
                "five readings, one per interjection tone");
        for (String reading : new String[] {"ai3", "ei1", "ei2", "ei3", "ei4"}) {
            assertTrue(ai.contains("reading: \"" + reading + "\""),
                    () -> reading + " is missing from the character's readings");
        }
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

    // ── A missing meaning is said, not left blank ──────────────────────

    @Test
    void theModuleSaysWhetherTheLibraryHasTheCharacter() {
        // Two facts a card needs before it can say why it has no meaning:
        // is there a meanings library at all, and has it reached this
        // character. Both are booleans on the module, so the view never has
        // to infer "not covered" from "every reading came back empty" - which
        // is also what a character whose readings are all glossed as nothing
        // would look like.
        String js = ZiDetailGetAction.moduleFor("U+597D");
        assertTrue(js.contains("export const glossary = true;"),
                "the test classpath carries the gloss collections");
        assertTrue(js.contains("export const glossed = true;"),
                "好 is one of the first characters anybody glossed");
    }

    @Test
    void aCharacterTheLibraryHasNotReachedIsSaidToBeMissing() {
        // Found rather than named. The library grows, and a test pinned to a
        // character somebody later glossed would fail for the right reason
        // and be fixed by the wrong one - picking a rarer character - until
        // there were none left to pick. Searching the corpus for one keeps the
        // test true for as long as coverage is short of complete, and reports
        // completeness as the day it can no longer find a subject.
        Optional<ZiCharUTF8> unglossed = SyllableIndex.instance().syllables().stream()
                .flatMap(s -> SyllableIndex.instance().charactersOf(s).stream())
                .filter(zi -> !ZiGlossary.knows(zi.codePoint()))
                .findFirst();
        assumeTrue(unglossed.isPresent(), "every corpus character is glossed - nothing to test");

        String js = ZiDetailGetAction.moduleFor(unglossed.get().codePointLabel());
        assertTrue(js.contains("export const glossed = false;"),
                () -> unglossed.get().value() + " has no gloss and the module must say so: " + js);
        assertTrue(js.contains("export const glossary = true;"),
                "the library exists; it is the character that is missing");
        assertFalse(js.contains("export const problem"),
                "a missing meaning is a state of the character, not an error serving it");
    }

    @Test
    void theErrorModuleCarriesTheSameShape() {
        // A view reads glossary/glossed off every module it receives. An error
        // module that lacked them would make `mod.glossed === false` false for
        // the wrong reason - undefined - and a card would say nothing at all.
        String js = ZiDetailGetAction.moduleFor("not a codepoint");
        assertTrue(js.contains("export const glossary = false;"));
        assertTrue(js.contains("export const glossed = false;"));
    }
}
