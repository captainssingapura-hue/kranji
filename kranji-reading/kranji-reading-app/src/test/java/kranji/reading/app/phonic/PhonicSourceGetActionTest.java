package kranji.reading.app.phonic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the phonic source module.
 *
 * <p>The action sits outside the crate and outside conformance, and the price
 * of that exemption is that it emits data and never behaviour. Nothing else
 * checks it, so this test is the guarantee rather than a restatement of one.</p>
 */
class PhonicSourceGetActionTest {

    // ── The guarantee that buys the conformance exemption ──────────────

    @Test
    void emitsDataOnlyAndNeverBehaviour() {
        String js = PhonicSourceGetAction.moduleFor("h");

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

    // ── Content ────────────────────────────────────────────────────────

    @Test
    void carriesThePartitionAndItsColumns() {
        String js = PhonicSourceGetAction.moduleFor("h");

        assertTrue(js.contains("export const initial = \"h\""));
        assertTrue(js.contains("export const label = \"h-\""));
        assertTrue(js.contains("\"glyph\""));
        assertTrue(js.contains("\"principal\""));
        assertTrue(js.contains("\"review\""));
    }

    @Test
    void aRowCarriesEnoughToJudgeItWithoutTheSource() {
        String js = PhonicSourceGetAction.moduleFor("h");

        assertTrue(js.contains("pk: \"U+597D\""), "addressed by codepoint, not by reading");
        assertTrue(js.contains("glyph: \"好\""));
        assertTrue(js.contains("principal: \"hǎo\""));
        assertTrue(js.contains("alternates: \"hào\""));
        assertTrue(js.contains("readings: 2"));
    }

    // ── The decomposed principal reading ───────────────────────────────

    @Test
    void thePrincipalReadingIsDecomposedIntoItsFiveParts() {
        String js = PhonicSourceGetAction.moduleFor("h");
        assertTrue(row(js, "U+9EC4").contains(
                "principal: \"huáng\", initial: \"h\", medial: \"u\", "
              + "nucleus: \"a\", coda: \"ng\", tone: 2"),
                "声母 韵头 韵腹 韵尾 声调, all four parts present");
    }

    @Test
    void theCodaIsPhonemicRatherThanOrthographic() {
        // 好 is written "ao" but the coda is the u-glide; pinyin spells it "o".
        // The decomposition shows the phoneme, which is the point of showing it.
        String js = PhonicSourceGetAction.moduleFor("h");
        assertTrue(row(js, "U+597D").contains("nucleus: \"a\", coda: \"u\""));
    }

    @Test
    void anAbsentPartReadsAsAbsentRatherThanBlank() {
        // 知 is a syllabic fricative - 空韵, no medial, no true nucleus, no coda.
        String js = PhonicSourceGetAction.moduleFor("zh");
        assertTrue(row(js, "U+77E5").contains(
                "medial: \"-\", nucleus: \"-\", coda: \"-\""),
                "a blank cell reads as missing data; a dash reads as no such part");
    }

    @Test
    void aMedialThatDuplicatesItsNucleusStillShowsBoth() {
        // 绿 is lǜ: the ü is both medial and nucleus, collapsed only in spelling.
        String js = PhonicSourceGetAction.moduleFor("l");
        assertTrue(row(js, "U+7EFF").contains("medial: \"ü\", nucleus: \"ü\""));
    }

    @Test
    void theZeroInitialShowsItsNameNotAnEmptyCell() {
        String js = PhonicSourceGetAction.moduleFor("zero");
        assertTrue(js.contains("initial: \"zero\""));
    }

    /** The emitted line for one character, by codepoint. */
    private static String row(String js, String codePoint) {
        int at = js.indexOf("pk: \"" + codePoint + "\"");
        assertTrue(at > 0, () -> codePoint + " is not in this partition");
        return js.substring(at, js.indexOf('\n', at));
    }

    @Test
    void frequencyEvidenceRidesAlongMostObservedFirst() {
        String js = PhonicSourceGetAction.moduleFor("h");
        assertTrue(js.contains("evidence: \"hǎo 6060  hāo 142  hào 115\""));
    }

    @Test
    void theReviewColumnIsEmptyUntilThereIsSomethingToDecide() {
        // 好 needs no decision: the dictionary and the corpus agree on hǎo.
        String js = PhonicSourceGetAction.moduleFor("h");
        int row = js.indexOf("pk: \"U+597D\"");
        String line = js.substring(row, js.indexOf('\n', row));
        assertTrue(line.contains("review: \"\""), "nothing to review, so nothing shown");
    }

    @Test
    void aConflictIsCarriedInFullSoItCanBeJudgedInPlace() {
        // 得 is dé by the dictionary; running text is full of the particle de.
        String js = PhonicSourceGetAction.moduleFor("d");
        int row = js.indexOf("pk: \"U+5F97\"");
        String line = js.substring(row, js.indexOf('\n', row));

        assertTrue(line.contains("FREQUENCY_DISAGREES"));
        assertTrue(line.contains("most observed is de"));
    }

    @Test
    void theZeroInitialIsAddressedByName() {
        String js = PhonicSourceGetAction.moduleFor("zero");
        assertTrue(js.contains("export const initial = \"zero\""));
        assertTrue(js.contains("export const label = \"no initial\""));
    }

    @Test
    void anUnknownPartitionStillYieldsAValidModule() {
        String js = PhonicSourceGetAction.moduleFor("qq");

        assertTrue(js.contains("export const rows = [];"));
        assertTrue(js.contains("export const problem = \"no initial named 'qq'\""));
    }
}
