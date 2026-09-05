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
 * The counting, and the words the counting is delivered in.
 *
 * <p>The wording is tested as carefully as the arithmetic, because it is the
 * part a child actually receives. A tracker that says the wrong true thing —
 * that a month of work is 0.6%, that nothing has happened — is worse than no
 * tracker, and nothing about it would throw.</p>
 */
class KnownProgressModuleTest extends JsModuleTestBase {

    private static final String READABILITY =
            "/homing/js/kranji/reading/app/read/ReadabilityModule.js";
    private static final String MODULE =
            "/homing/js/kranji/reading/app/known/KnownProgressModule.js";

    /** Three stories of ten characters each, needing different amounts. */
    private static final String CENSUS = "{"
            + "'c:a': { total: 10, title: 'One', pairs: { '1:yi4': 9, '2:ba1': 1 } },"
            + "'c:b': { total: 10, title: 'Two', pairs: { '1:yi4': 5, '3:de0': 5 } },"
            + "'c:c': { total: 10, title: 'Three', pairs: { '4:xi1': 10 } } }";

    private static final String INDEX = "["
            + "{ r: 'yi4', p: 'y.i.4', l: 'yi', i: 'y', il: 'y-', c: 2 },"
            + "{ r: 'ba1', p: 'b.a.1', l: 'ba', i: 'b', il: 'b-', c: 1 },"
            + "{ r: 'de0', p: 'd.e.0', l: 'de', i: 'd', il: 'd-', c: 1 },"
            + "{ r: 'xi1', p: 'x.i.1', l: 'xi', i: 'x', il: 'x-', c: 4 }]";

    private Value progress;

    @BeforeEach
    void load() {
        loadModule(READABILITY);
        loadModule(MODULE);
        progress = global("createKnownProgress").execute(global("createReadability").execute());
    }

    private Value summary(String known) {
        return progress.getMember("summarise").execute(
                js.eval("js", "(" + CENSUS + ")"),
                js.eval("js", "(" + INDEX + ")"),
                js.eval("js", "(" + known + ")"));
    }

    private String headline(String known) {
        return progress.getMember("headline").execute(summary(known)).asString();
    }

    private String cheer(String known) {
        return progress.getMember("encouragement").execute(summary(known)).asString();
    }

    // ── The counting ───────────────────────────────────────────────────

    @Test
    void charactersAndReadingsAreDifferentNumbers() {
        // A character read two ways that is half learnt is one character and
        // one reading, and quoting either as the other flatters or undersells.
        Value p = summary("['1:yi4','1:yi2','2:ba1']");
        assertEquals(2, p.getMember("characters").asInt());
        assertEquals(3, p.getMember("readings").asInt());
    }

    @Test
    void aStoryIsReadableAtNinetyPercentOfItsCharacters() {
        // 'One' is nine tenths yi4. Claiming that one reading carries it.
        Value p = summary("['1:yi4']");
        assertEquals(1, p.getMember("ready").asInt());
        assertEquals(3, p.getMember("stories").asInt());
    }

    @Test
    void anEmptyRecordCountsEverythingAsNotYetReadable() {
        Value p = summary("[]");
        assertEquals(0, p.getMember("ready").asInt());
        assertEquals(0, p.getMember("readings").asInt());
        assertEquals(3, p.getMember("stories").asInt());
    }

    // ── The near lists ─────────────────────────────────────────────────

    @Test
    void storiesAreOrderedByHowManyReadingsTheyNeed() {
        // Not by how readable they already are. 'Three' needs one reading and
        // is at 0%; 'Two' needs two and is at 50%. The one-reading job leads,
        // because it is the one somebody can finish today.
        Value near = progress.getMember("nearestStories").execute(summary("[]"), 5);
        assertEquals("Three", near.getArrayElement(0).getMember("title").asString());
        assertEquals(1, near.getArrayElement(0).getMember("unknown").asInt());
    }

    @Test
    void aReadableStoryIsNotInTheNearList() {
        Value near = progress.getMember("nearestStories").execute(summary("['1:yi4']"), 5);
        for (int i = 0; i < near.getArraySize(); i++) {
            assertFalse("One".equals(near.getArrayElement(i).getMember("title").asString()),
                    "a story already readable is not something to be near");
        }
    }

    @Test
    void onlySoundsAlreadyBegunAreSuggested() {
        // Suggesting an untouched sound would be advice about what to learn.
        // This list is a nudge to finish something, which is a much smaller ask.
        Value sounds = progress.getMember("nearlyDoneSounds").execute(
                js.eval("js", "(" + INDEX + ")"), js.eval("js", "(['1:yi4'])"), 5);
        assertEquals(1, sounds.getArraySize());
        assertEquals("yi", sounds.getArrayElement(0).getMember("label").asString());
        assertEquals(1, sounds.getArrayElement(0).getMember("left").asInt());
    }

    @Test
    void aFinishedSoundIsNotNearlyDone() {
        Value sounds = progress.getMember("nearlyDoneSounds").execute(
                js.eval("js", "(" + INDEX + ")"), js.eval("js", "(['2:ba1'])"), 5);
        assertEquals(0, sounds.getArraySize());
    }

    // ── The wording ────────────────────────────────────────────────────

    @Test
    void anEmptyRecordIsAnInvitationAndNotAZero() {
        assertEquals("Ready when you are.", headline("[]"));
        assertTrue(cheer("[]").contains("Everyone begins here"), cheer("[]"));
        assertFalse(headline("[]").contains("0"), "a zero is not the first thing to say");
    }

    @Test
    void theHeadlineLeadsWithWhatCanBeReadNotWithAFraction() {
        String said = headline("['1:yi4']");
        assertEquals("One story you can read on your own.", said);
        // The corpus fraction is the thing that makes a month of work look like
        // nothing. It must not appear in the line somebody reads first.
        assertFalse(said.contains("%"), said);
    }

    @Test
    void nothingScolds() {
        // No word that measures somebody against what they have not done.
        String[] known = { "[]", "['1:yi4']", "['1:yi4','2:ba1','3:de0']" };
        for (String k : known) {
            String said = (headline(k) + " " + cheer(k)).toLowerCase(Locale.ROOT);
            for (String scold : new String[] { "only", "still", "fail", "behind",
                                               "should", "must", "haven't", "not enough" }) {
                assertFalse(said.contains(scold),
                        () -> "'" + scold + "' appears in: " + said);
            }
        }
    }

    @Test
    void aSingleStoryIsSaidInWordsAndNotAsOne() {
        assertTrue(headline("['1:yi4']").startsWith("One story"),
                "the first one is worth naming properly");
    }

    @Test
    void pluralsAgreeAtEveryCount() {
        // Trivial, and the kind of thing that undoes the tone of a whole pane.
        assertTrue(headline("['1:yi4','4:xi1']").contains("2 stories"),
                headline("['1:yi4','4:xi1']"));
        assertFalse(headline("['1:yi4','4:xi1']").contains("2 story"));
    }
}
