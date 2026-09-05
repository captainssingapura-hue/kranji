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

    private String icon(int n) {
        return progress.getMember("icon").execute(n).asString();
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
        assertEquals("The everyday characters", headline(2500));
        assertEquals("Semi-pro", headline(3500));
        assertEquals("Pro", headline(5000));
        assertEquals("Pro plus", headline(6500));
        assertEquals("Guru", headline(8000));
        assertEquals("Guru", headline(8100));
    }

    @Test
    void theClimbAboveTheStandardListIsFourBandsAndNotOne() {
        // 3,500 upwards was a single band once, which said the difference
        // between a reader of 3,600 characters and one of 8,000 was nothing
        // worth naming. It is most of a decade, and it is the stretch where
        // nothing else can show movement.
        var named = new java.util.HashSet<String>();
        for (int n : new int[] { 3600, 5200, 7000, 8050 }) named.add(headline(n));
        assertEquals(4, named.size(), () -> "the pro range collapsed into " + named);
    }

    @Test
    void theNamesTurnFromMovingToStandingAtTheProRange() {
        // Below the standard list the names are about being on the road, which
        // is what those months are; nobody wants a rank for still walking. From
        // 3,500 they say what somebody is rather than what they are doing.
        assertTrue(headline(2499).contains(" "), "the road names are phrases");
        assertEquals("Semi-pro", headline(3500));
        assertEquals("Guru", headline(9000));
    }

    @Test
    void theTopBandIsInsideThisCorpus() {
        // A band nobody can reach is a promise the app cannot keep. Checked
        // against the corpus itself rather than a number copied into a comment,
        // because the corpus is what would move.
        int counted = 0;
        for (kranji.phonic.SourceReadings row : kranji.phonic.PhonicPartitions.loadAll()) {
            if (row != null) counted++;
        }
        final int corpus = counted;
        Value bands = progress.getMember("bands");
        final int top = bands.getArrayElement(0).getMember("from").asInt();
        assertTrue(top <= corpus,
                () -> "the top band opens at " + top + " and the corpus holds " + corpus);
    }

    @Test
    void theBandsRiseAndNeverRepeatANameOrAMark() {
        Value bands = progress.getMember("bands");
        var names = new java.util.HashSet<String>();
        var icons = new java.util.HashSet<String>();
        int previous = Integer.MAX_VALUE;
        for (int i = 0; i < bands.getArraySize(); i++) {
            int from = bands.getArrayElement(i).getMember("from").asInt();
            String name = bands.getArrayElement(i).getMember("name").asString();
            String icon = bands.getArrayElement(i).getMember("icon").asString();
            assertTrue(from < previous, () -> "bands must descend: " + from);
            assertTrue(names.add(name), () -> "two bands are called " + name);
            // A repeated mark makes two different places look like one, which
            // is exactly what somebody glancing at the pane would read it as.
            assertTrue(icons.add(icon), () -> "two bands are marked " + icon);
            assertFalse(icon.isBlank(), () -> name + " has no mark");
            previous = from;
        }
        assertEquals(0, previous, "the lowest band has to catch a reader at zero");
    }

    @Test
    void everyCountHasAMarkAndItChangesWithTheBand() {
        assertEquals(icon(0), icon(0));
        assertFalse(icon(0).equals(icon(1)), "crossing into a band changes the mark");
        assertFalse(icon(2499).equals(icon(2500)));
        assertFalse(icon(8000).equals(icon(7999)));
        for (int n : new int[] { 0, 1, 49, 250, 2600, 4000, 7000, 9000 }) {
            assertFalse(icon(n).isBlank(), () -> "no mark at " + n);
        }
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
        // eventually notices. Below it there always is something.
        assertFalse(nextLine(8050).contains("more and"), nextLine(8050));
        assertTrue(progress.getMember("next").execute(8050).isNull());

        assertTrue(nextLine(4000).contains("more and"),
                "4,000 is no longer the top - there is a name above it now");
        assertFalse(progress.getMember("next").execute(4000).isNull());
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
        // 8,100 is the corpus. It is the number that makes a term of work look
        // like nothing, so it never reaches the page.
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
