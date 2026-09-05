package kranji.reading.app.known;

import hue.captains.singapura.js.homing.ssjs.test.JsModuleTestBase;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Where the record meets the corpus.
 *
 * <p>The arithmetic is small and the failure is silent, which is why it is
 * pinned here rather than looked at. A bucketing that keys on the wrong half of
 * the key, or a count that is not clamped, produces a plausible number — and a
 * plausible number is what somebody will use to decide what to spend an
 * afternoon on.</p>
 */
class KnownSoundsModuleTest extends JsModuleTestBase {

    private static final String MODULE =
            "/homing/js/kranji/reading/app/known/KnownSoundsModule.js";

    /** Two sounds, deliberately different sizes. */
    private static final String INDEX = "["
            + "{ r: 'yi4', p: 'y.i.4', l: 'yi', i: 'y', il: 'y-', c: 3 },"
            + "{ r: 'ba1', p: 'b.a.1', l: 'ba', i: 'b', il: 'b-', c: 1 },"
            + "{ r: 'de0', p: 'd.e.0', l: 'de', i: 'd', il: 'd-', c: 2 }]";

    private Value sounds;

    @BeforeEach
    void load() {
        loadModule(MODULE);
        sounds = global("createKnownSounds").execute();
    }

    private Value rows(String known, String opts) {
        return sounds.getMember("rowsOf").execute(
                js.eval("js", "(" + INDEX + ")"),
                js.eval("js", "(" + known + ")"),
                js.eval("js", "(" + opts + ")"));
    }

    private Value summary(String known) {
        return sounds.getMember("summarise").execute(
                js.eval("js", "(" + INDEX + ")"), js.eval("js", "(" + known + ")"));
    }

    private static String cell(Value rows, int i, String column) {
        return rows.getArrayElement(i).getMember(column).toString();
    }

    // ── The join ───────────────────────────────────────────────────────

    @Test
    void bucketsTheSetOnTheBackHalfOfEachKey() {
        // The whole reason no lookup is needed: the reading is in the key.
        Value counts = sounds.getMember("byReading")
                .execute(js.eval("js", "(['20320:yi4', '20114:yi4', '19968:ba1'])"));
        assertEquals(2, counts.getMember("yi4").asInt());
        assertEquals(1, counts.getMember("ba1").asInt());
    }

    @Test
    void anEntryWithNoReadingIsIgnoredRatherThanCounted() {
        // An imported file can carry a malformed line. Counting it would
        // inflate a total that somebody is reading as progress.
        Value counts = sounds.getMember("byReading")
                .execute(js.eval("js", "(['20320:yi4', 'rubbish', ''])"));
        assertEquals(1, counts.getMember("yi4").asInt());
    }

    @Test
    void aClaimIsClampedToTheSizeOfItsSound() {
        // A reader can hold a claim the corpus does not file under this
        // syllable - an import from another corpus version, a reading since
        // corrected. Unclamped, the row would read 4 of 3 and every total
        // above it would be slightly wrong.
        Value rows = rows("['1:yi4','2:yi4','3:yi4','4:yi4']", "{}");
        // Found rather than indexed: clamped to full, yi4 has nothing left and
        // therefore sorts to the bottom - which is the ordering working.
        int yi = rowFor(rows, "yi4");
        assertEquals("3", cell(rows, yi, "known"));
        assertEquals("3", cell(rows, yi, "characters"));
        assertEquals("0", cell(rows, yi, "left"));
    }

    /** The row for a reading, wherever the ordering put it. */
    private static int rowFor(Value rows, String reading) {
        for (int i = 0; i < rows.getArraySize(); i++) {
            if (reading.equals(rows.getArrayElement(i).getMember("reading").asString())) return i;
        }
        throw new AssertionError(reading + " is not in the rows");
    }

    // ── The ordering ───────────────────────────────────────────────────

    @Test
    void ordersByWhatIsLeftAndNotByWhatIsDone() {
        // yi4 has 3 characters and 2 claimed; de0 has 2 and none. One reading
        // left against two, so de0 leads - ordering by what is finished would
        // put the nearly-complete sound first and bury the work.
        Value rows = rows("['1:yi4','2:yi4']", "{}");
        assertEquals("de", cell(rows, 0, "sound").split(" ")[0]);
        assertEquals("2", cell(rows, 0, "left"));
        assertEquals("yi", cell(rows, 1, "sound").split(" ")[0]);
        assertEquals("1", cell(rows, 1, "left"));
    }

    @Test
    void theSoundCellCarriesBothFormsSoOneCanBeTyped() {
        Value rows = rows("[]", "{}");
        assertTrue(cell(rows, 0, "sound").contains("yi"), cell(rows, 0, "sound"));
        assertTrue(cell(rows, 0, "sound").contains("yi4"),
                "the canonical key is what a person can type into the filter");
    }

    // ── The filters ────────────────────────────────────────────────────

    @Test
    void startedExcludesBothUntouchedAndFinished() {
        // The state with work in progress. A sound not begun is not started,
        // and neither is one already done - filters that let either through
        // would make the list the thing it is meant to narrow.
        Value rows = rows("['1:yi4','2:ba1']", "{ only: 'started' }");
        assertEquals(1, rows.getArraySize());
        assertTrue(cell(rows, 0, "sound").startsWith("yi"), cell(rows, 0, "sound"));
    }

    @Test
    void untouchedIsNothingClaimedAndCompleteIsNothingLeft() {
        assertEquals(1, rows("['1:yi4','2:ba1']", "{ only: 'untouched' }").getArraySize());
        assertEquals("de",
                cell(rows("['1:yi4','2:ba1']", "{ only: 'untouched' }"), 0, "sound")
                        .split(" ")[0]);

        Value complete = rows("['1:ba1']", "{ only: 'done' }");
        assertEquals(1, complete.getArraySize());
        assertEquals("ba", cell(complete, 0, "sound").split(" ")[0]);
    }

    @Test
    void findMatchesTheReadingTheLabelAndTheInitial() {
        assertEquals(1, rows("[]", "{ find: 'ba1' }").getArraySize());
        assertEquals(1, rows("[]", "{ find: 'ba' }").getArraySize());
        assertEquals(1, rows("[]", "{ find: 'b-' }").getArraySize());
        assertEquals(0, rows("[]", "{ find: 'zzz' }").getArraySize());
    }

    // ── The summary ────────────────────────────────────────────────────

    @Test
    void countsSoundsStartedAndFinishedSeparately() {
        // Two different figures, and quoting either as the other would either
        // flatter or undersell what somebody has actually done.
        Value sum = summary("['1:yi4','2:ba1']");
        assertEquals(3, sum.getMember("sounds").asInt());
        assertEquals(2, sum.getMember("started").asInt());
        assertEquals(1, sum.getMember("complete").asInt());
        assertEquals(2, sum.getMember("known").asInt());
        assertEquals(6, sum.getMember("characters").asInt());
    }

    @Test
    void anEmptyRecordIsZeroEverywhereAndNotAnError() {
        Value sum = summary("[]");
        assertEquals(0, sum.getMember("started").asInt());
        assertEquals(0, sum.getMember("known").asInt());
        assertEquals(6, sum.getMember("characters").asInt());
        assertEquals(3, rows("[]", "{}").getArraySize());
    }
}
