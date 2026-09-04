package kranji.reading.app.known;

import hue.captains.singapura.js.homing.ssjs.test.JsModuleTestBase;
import org.graalvm.polyglot.Value;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The file a family keeps.
 *
 * <p>Worth testing hard because this is the only copy of the record that
 * survives clearing the browser, and because the file is meant to be opened and
 * edited by hand — so the input is not what this code wrote, it is what a
 * person typed.</p>
 */
class KnownTransferModuleTest extends JsModuleTestBase {

    private static final String MODULE =
            "/homing/js/kranji/reading/app/known/KnownTransferModule.js";

    /** The display boundary, which this module reads incoming readings with. */
    private static final String SWF =
            "/homing/js/kranji/reading/app/ui/PinyinSwfModule.js";

    private Value transfer;

    @BeforeEach
    void load() {
        loadModule(SWF);
        loadModule(MODULE);
        transfer = global("createKnownTransfer").execute();
    }

    private String toText(String... keys) {
        Value arr = js.eval("js", "([])");
        for (int i = 0; i < keys.length; i++) arr.setArrayElement(i, keys[i]);
        return transfer.getMember("toText").execute(arr).asString();
    }

    private Value fromText(String text) {
        return transfer.getMember("fromText").execute(text);
    }

    private List<String> keysOf(Value read) {
        Value keys = read.getMember("keys");
        List<String> out = new ArrayList<>();
        for (int i = 0; i < keys.getArraySize(); i++) {
            out.add(keys.getArrayElement(i).asString());
        }
        return out;
    }

    // ── Writing ────────────────────────────────────────────────────────

    @Test
    void writesTheGlyphNotTheCodepoint() {
        // The file is for a person to read. 34892 is not.
        assertTrue(toText("34892:xing2").contains("行	xíng"));
    }

    @Test
    void isSortedRegardlessOfWhenThingsWereMarked() {
        // Two exports of nearly the same record should differ only where the
        // record differs - a diff should show what was learnt, not what order
        // it was clicked in.
        String late = toText("26376:yue4", "22320:di4", "22320:de0");
        String early = toText("22320:de0", "22320:di4", "26376:yue4");

        assertEquals(early, late);
        int de = late.indexOf("地	de"), di = late.indexOf("地	dì"),
            yue = late.indexOf("月	yuè");
        assertTrue(de < di && di < yue, "codepoint, then reading");
    }

    @Test
    void writesTheReadingTheWayAPersonReadsIt() {
        // The one place the set leaves the system, so it crosses the display
        // boundary like anything else a person looks at. The set itself is
        // keyed "chuang2"; the file says chuáng.
        String text = toText("24202:chuang2", "22320:de0");

        assertTrue(text.contains("chuáng"), text);
        assertTrue(text.contains("地\tde"), "neutral loses the digit and takes no mark");
        assertFalse(text.contains("chuang2"), "the canonical form does not reach the file");
    }

    @Test
    void countsBothNumbersInTheHeader() {
        Value arr = js.eval("js", "(['22320:de0', '22320:di4', '26376:yue4'])");
        String text = transfer.getMember("toText").execute(arr, 2).asString();

        assertTrue(text.contains("# 2 characters, 3 readings"), text);
    }

    @Test
    void anEmptySetIsStillAFile() {
        // Exporting nothing must not produce something that fails to import.
        assertEquals(0, keysOf(fromText(toText())).size());
    }

    // ── Reading back ───────────────────────────────────────────────────

    @Test
    void roundTripsExactly() {
        String[] set = { "22320:de0", "22320:di4", "34892:xing2", "183955:cuan2" };
        assertEquals(List.of("22320:de0", "22320:di4", "34892:xing2", "183955:cuan2"),
                keysOf(fromText(toText(set))));
    }

    @Test
    void aSupplementaryCharacterIsOneCharacter() {
        // Two UTF-16 units, one character. Counting units would reject it.
        String astral = new String(Character.toChars(0x2CE93));
        assertEquals(List.of("183955:cuan2"), keysOf(fromText(astral + "	cuan2")));
    }

    @Test
    void skipsCommentsAndBlankLines() {
        String file = "# Kranji reading record\n\n地\tde\n\n# a note a parent added\n月\tyuè\n";
        assertEquals(List.of("22320:de0", "26376:yue4"), keysOf(fromText(file)));
    }

    @Test
    void acceptsSpacesWhereATabWasMeant() {
        // A parent who retyped a line should not have their record refused
        // over an invisible character.
        assertEquals(List.of("22320:de0"), keysOf(fromText("地   de")));
    }

    @Test
    void aRepeatedLineIsNotAnError() {
        assertEquals(List.of("22320:de0"), keysOf(fromText("地\tde\n地\tde\n")));
    }

    // ── What it refuses, and says so ───────────────────────────────────

    @Test
    void reportsALineWithNoReading() {
        // The half that decides whether pinyin is shown. Without it the key
        // would match nothing and quietly withhold support.
        Value read = fromText("地\tde\n月\n");

        assertEquals(List.of("22320:de0"), keysOf(read));
        Value skipped = read.getMember("skipped");
        assertEquals(1, skipped.getArraySize());
        assertEquals(2, skipped.getArrayElement(0).getMember("line").asInt(),
                "the line number, so the parent can find it");
    }

    @Test
    void takesAFileWrittenInEitherForm() {
        // A file exported before the canonical form existed says "chuáng",
        // and so does a line a parent typed from what they saw on screen.
        // Neither is a mistake; both mean chuang2. The set is keyed one way,
        // so the conversion happens here rather than at every comparison.
        assertEquals(List.of("24202:chuang2", "22320:di4", "22320:de0"),
                keysOf(fromText("床\tchuáng\n地\tdì\n地\tde\n")));

        assertEquals(List.of("24202:chuang2"),
                keysOf(fromText("床\tchuang2\n")), "and the canonical form as written");
    }

    @Test
    void reportsAReadingThatIsNotOne() {
        // It would otherwise become a key that matches nothing - support
        // quietly withheld for a line the parent believes was taken.
        Value read = fromText("地\tde\n月\t???\n");

        assertEquals(List.of("22320:de0"), keysOf(read));
        assertEquals("not a reading",
                read.getMember("skipped").getArrayElement(0).getMember("why").asString());
    }

    @Test
    void reportsAWordWhereACharacterBelongs() {
        Value read = fromText("银行\tháng\n");

        assertEquals(0, keysOf(read).size());
        assertEquals("not one character",
                read.getMember("skipped").getArrayElement(0).getMember("why").asString());
    }

    @Test
    void aBadLineDoesNotCostTheGoodOnes() {
        // The whole reason import reports rather than throws: one mistyped
        // line in an eight-hundred-line record must not lose the other 799.
        Value read = fromText("地\tde\nnonsense\n月\tyuè\n");

        assertEquals(List.of("22320:de0", "26376:yue4"), keysOf(read));
        assertEquals(1, read.getMember("skipped").getArraySize());
    }

    @Test
    void survivesWindowsLineEndings() {
        // The file goes through storage the family already trusts - email, a
        // USB stick, a Windows laptop.
        assertEquals(List.of("22320:de0", "26376:yue4"),
                keysOf(fromText("地\tde\r\n月\tyuè\r\n")));
    }

    // ── What the person is told ────────────────────────────────────────

    private String describe(String text, int added, String name) {
        return transfer.getMember("describeImport")
                .execute(fromText(text), added, name).asString();
    }

    @Test
    void reportsWhatWasNewAgainstWhatTheFileHeld() {
        // Both numbers, because "imported 12" against a file of 800 is the
        // difference between a restore that worked and one that did not.
        String says = describe("地\tde\n月\tyuè\n", 1, "known.txt");

        assertTrue(says.contains("1 new reading"), says);
        assertTrue(says.contains("from 2"), says);
        assertTrue(says.contains("known.txt"), says);
    }

    @Test
    void namesTheLinesItCouldNotRead() {
        // A count alone sends somebody hunting through eight hundred lines.
        String says = describe("地\tde\nnonsense\n", 1, "known.txt");

        assertTrue(says.contains("line 2"), says);
        assertTrue(says.contains("1 line could not be read"), says);
    }

    @Test
    void namesOnlyTheFirstFewBadLines() {
        String says = describe("a\nb\nc\nd\ne\n", 0, "known.txt");

        assertTrue(says.contains("5 lines could not be read"), says);
        assertTrue(says.contains("…"), "says there are more without listing them: " + says);
        assertTrue(says.contains("line 3") && !says.contains("line 4"), says);
    }

    @Test
    void saysNothingAboutBadLinesWhenThereAreNone() {
        assertTrue(describe("地\tde\n", 1, "known.txt").endsWith("known.txt."));
    }

    @Test
    void survivesNothingAtAll() {
        assertEquals(0, keysOf(fromText("")).size());
        assertEquals(0, keysOf(fromText(null)).size());
    }
}
