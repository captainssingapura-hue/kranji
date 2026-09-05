package kranji.reading.app.known;

import hue.captains.singapura.js.homing.ssjs.test.JsModuleTestBase;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The count, the bands, and the words they are delivered in.
 *
 * <p>The wording is tested as carefully as the arithmetic, because the wording
 * is the part a child actually receives. A band that says the wrong true thing
 * — that a term's work is 3%, that nothing has happened — is worse than no
 * band, and nothing about it would throw.</p>
 */
class KnownProgressModuleTest extends JsModuleTestBase {

    private static final String KNOWN_SET =
            "/homing/js/kranji/reading/app/known/KnownSetModule.js";
    private static final String MODULE =
            "/homing/js/kranji/reading/app/known/KnownProgressModule.js";

    private Value progress;

    @BeforeEach
    void load() {
        loadModule(KNOWN_SET);
        loadModule(MODULE);
        progress = global("createKnownProgress").execute(global("createKnownSet").execute());
    }

    private int count(String known) {
        return progress.getMember("count").execute(js.eval("js", "(" + known + ")")).asInt();
    }

    private String headline(int n) {
        return progress.getMember("headline").execute(n).asString();
    }

    private String line(int n) {
        return progress.getMember("line").execute(n).asString();
    }

    private String nextLine(int n) {
        return progress.getMember("nextLine").execute(n).asString();
    }

    // ── The one number ─────────────────────────────────────────────────

    @Test
    void countsCharactersAndNotReadings() {
        // The whole point of the pane. A character claimed at both its readings
        // is one character somebody can read, and counting it twice would tell
        // a polyphone-heavy reader they are further along than they are.
        assertEquals(1, count("['20320:yi4','20320:yi2']"));
        assertEquals(2, count("['20320:yi4','20114:ba1']"));
        assertEquals(0, count("[]"));
    }

    // ── The bands ──────────────────────────────────────────────────────

    @Test
    void everyBandBoundaryLandsInTheBandItOpens() {
        // Off by one here renames somebody's achievement to the one below it.
        assertEquals("Ready when you are", headline(0));
        assertEquals("First characters", headline(1));
        assertEquals("First characters", headline(49));
        assertEquals("Getting going", headline(50));
        assertEquals("Reading along", headline(200));
        assertEquals("Hitting your stride", headline(500));
        assertEquals("Well on your way", headline(1000));
        assertEquals("The everyday characters", headline(2000));
        assertEquals("Reading freely", headline(3500));
        assertEquals("Reading freely", headline(9000));
    }

    @Test
    void theBandsRiseAndNeverRepeatAName() {
        Value bands = progress.getMember("bands");
        var seen = new java.util.HashSet<String>();
        int previous = Integer.MAX_VALUE;
        for (int i = 0; i < bands.getArraySize(); i++) {
            int from = bands.getArrayElement(i).getMember("from").asInt();
            String name = bands.getArrayElement(i).getMember("name").asString();
            assertTrue(from < previous, () -> "bands must descend: " + from);
            assertTrue(seen.add(name), () -> "two bands are called " + name);
            previous = from;
        }
        assertEquals(0, previous, "the lowest band has to catch a reader at zero");
    }

    @Test
    void theDistanceToTheNextNameIsExact() {
        assertTrue(nextLine(48).startsWith("2 more"), nextLine(48));
        assertTrue(nextLine(49).startsWith("One more"), nextLine(49));
        assertTrue(nextLine(199).startsWith("One more"), nextLine(199));
        assertTrue(nextLine(150).startsWith("50 more"), nextLine(150));
    }

    @Test
    void theTopBandDoesNotDangleAPromise() {
        // There is nothing above it, and inventing one would be a lie a reader
        // eventually notices.
        assertFalse(nextLine(4000).contains("more and"), nextLine(4000));
        assertTrue(progress.getMember("next").execute(4000).isNull());
    }

    // ── The wording ────────────────────────────────────────────────────

    @Test
    void anEmptyRecordIsAnInvitationAndNotAZero() {
        assertEquals("Ready when you are", headline(0));
        assertFalse(line(0).contains("0"), "a zero is not the first thing to say");
        assertTrue(nextLine(0).contains("First characters"), nextLine(0));
    }

    @Test
    void theFirstOneIsSaidInWords() {
        assertEquals("One character you can read.", line(1));
        assertEquals("2 characters you can read.", line(2));
    }

    @Test
    void theDenominatorNeverAppears() {
        // 248 of 8,100 is 3%, and a page that says 3% has told a child their
        // term's work rounds to nothing.
        for (int n : new int[] { 0, 1, 50, 248, 1000, 3500, 9000 }) {
            String said = headline(n) + " " + line(n) + " " + nextLine(n);
            assertFalse(said.contains("%"), said);
            assertFalse(said.contains(" of 8"), said);
            assertFalse(said.contains("8100") || said.contains("8,100"), said);
        }
    }

    @Test
    void nothingScolds() {
        // No word that measures somebody against what they have not done.
        for (int n : new int[] { 0, 1, 49, 250, 2600, 9000 }) {
            String said = (headline(n) + " " + line(n) + " " + nextLine(n))
                    .toLowerCase(Locale.ROOT);
            for (String scold : new String[] { "only", "still", "fail", "behind",
                                               "should", "must", "not enough", "left to" }) {
                assertFalse(said.contains(scold), () -> "'" + scold + "' appears in: " + said);
            }
        }
    }
}
