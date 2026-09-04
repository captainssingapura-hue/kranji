package kranji.reading.app.known;

import hue.captains.singapura.js.homing.ssjs.test.JsModuleTestBase;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The set that decides what pinyin a child sees.
 *
 * <p>Worth testing without a browser because a mistake here is not a crash: it
 * is a page that renders perfectly and withholds support the reader needed, or
 * shows support they had earned. Neither is visible from a screenshot.</p>
 */
class KnownSetModuleTest extends JsModuleTestBase {

    private static final String MODULE =
            "/homing/js/kranji/reading/app/known/KnownSetModule.js";

    private Value set;

    @BeforeEach
    void load() {
        loadModule(MODULE);
        set = global("createKnownSet").execute();
    }

    /** An empty set, as JS sees it. */
    private Value empty() { return js.eval("js", "([])"); }

    private Value add(Value known, String zi, String reading) {
        return set.getMember("add").execute(known, zi, reading);
    }

    private boolean has(Value known, String zi, String reading) {
        return set.getMember("has").execute(known, zi, reading).asBoolean();
    }

    private int size(Value known) { return set.getMember("size").execute(known).asInt(); }

    // ── The key ────────────────────────────────────────────────────────

    @Test
    void keysOnTheReadingNotTheCharacter() {
        // 行 is xíng in 行走 and háng in 银行. Knowing one is not knowing the
        // other, and this is the whole reason the key is a pair.
        Value known = add(empty(), "行", "xíng");

        assertTrue(has(known, "行", "xíng"));
        assertFalse(has(known, "行", "háng"),
                "knowing one reading must not claim the others");
    }

    @Test
    void aCharacterCanBePartlyKnown() {
        Value known = add(add(empty(), "行", "xíng"), "行", "háng");
        Value readings = set.getMember("readingsOf").execute(known, "行");

        assertEquals(2, readings.getArraySize());
        assertEquals("xíng", readings.getArrayElement(0).asString());
        assertEquals("háng", readings.getArrayElement(1).asString());
    }

    @Test
    void theKeyIsTheCodepointNotTheGlyph() {
        // Fixed-width ASCII, so a surrogate pair cannot break it.
        assertEquals("34892:xíng", set.getMember("keyOf").execute("行", "xíng").asString());

        String astral = new String(Character.toChars(0x2CE93));
        assertEquals("183955:x", set.getMember("keyOf").execute(astral, "x").asString());
    }

    // ── Adding and removing ────────────────────────────────────────────

    @Test
    void addingTwiceIsNotAnError() {
        // Marking is meant to be generous, so a second claim is a no-op rather
        // than a duplicate or a failure.
        Value once = add(empty(), "好", "hǎo");
        Value twice = add(once, "好", "hǎo");

        assertEquals(1, size(twice));
    }

    @Test
    void removingTakesBackOnlyTheReadingNamed() {
        Value known = add(add(empty(), "好", "hǎo"), "好", "hào");
        Value after = set.getMember("remove").execute(known, "好", "hào");

        assertTrue(has(after, "好", "hǎo"));
        assertFalse(has(after, "好", "hào"));
    }

    @Test
    void removingSomethingAbsentChangesNothing() {
        Value known = add(empty(), "好", "hǎo");
        Value after = set.getMember("remove").execute(known, "月", "yuè");

        assertEquals(1, size(after));
    }

    @Test
    void theSetIsNeverMutatedInPlace() {
        // A Secretary's behavior must be pure, so these have to return new
        // arrays rather than edit the one they were handed.
        Value known = add(empty(), "好", "hǎo");
        add(known, "月", "yuè");

        assertEquals(1, size(known), "add must not touch the set it was given");
    }

    // ── The mechanic ───────────────────────────────────────────────────

    private boolean annotates(String mode, Value known, String zi, String reading) {
        return set.getMember("annotates").execute(mode, known, zi, reading).asBoolean();
    }

    @Test
    void adaptiveHidesOnlyTheReadingThatWasMarked() {
        // The reason the app exists, and the reason the key is a pair: 行走
        // loses its pinyin while 银行 keeps it.
        Value known = add(empty(), "行", "xíng");

        assertFalse(annotates("adaptive", known, "行", "xíng"));
        assertTrue(annotates("adaptive", known, "行", "háng"),
                "the reading not yet marked must keep its support");
    }

    @Test
    void theManualModesIgnoreTheSet() {
        // They are overrides, not fallbacks - reading aloud with a parent, or
        // checking whether a child can manage with no support at all.
        Value known = add(empty(), "好", "hǎo");

        assertTrue(annotates("all", known, "好", "hǎo"));
        assertFalse(annotates("none", empty(), "好", "hǎo"));
    }

    @Test
    void anEmptySetAnnotatesEverything() {
        // Day one. Adaptive and all agree until something is marked.
        assertTrue(annotates("adaptive", empty(), "好", "hǎo"));
    }

    @Test
    void aCellWithNothingToSayIsBare() {
        // Punctuation, the squares past the end of a line, and any character
        // the corpus does not model - which is how an out-of-corpus character
        // degrades instead of breaking the page.
        assertFalse(annotates("all", empty(), "好", null));
        assertFalse(annotates("all", empty(), null, "hǎo"));
        assertFalse(annotates("adaptive", empty(), "。", ""));
    }

    @Test
    void anUnrecognisedModeAnnotates() {
        // Failing towards more support is the safe direction: pinyin nobody
        // needed costs nothing, pinyin withheld leaves a child stuck.
        assertTrue(annotates("wat", add(empty(), "好", "hǎo"), "好", "hǎo"));
    }

    @Test
    void adaptiveSurvivesAMissingSet() {
        // The reader paints before the device has answered.
        assertTrue(set.getMember("annotates").execute("adaptive", null, "好", "hǎo")
                .asBoolean());
    }

    // ── Counting ───────────────────────────────────────────────────────

    @Test
    void charactersAndReadingsAreDifferentNumbers() {
        // 行 twice over is one character and two readings. Showing either as
        // the other would overstate or understate what a child has done.
        Value known = add(add(add(empty(), "行", "xíng"), "行", "háng"), "月", "yuè");

        assertEquals(3, size(known), "three readings");
        assertEquals(2, set.getMember("characters").execute(known).getArraySize(),
                "two characters");
    }

    @Test
    void anEmptySetKnowsNothing() {
        assertFalse(has(empty(), "好", "hǎo"));
        assertEquals(0, size(empty()));
        assertEquals(0, set.getMember("readingsOf").execute(empty(), "好").getArraySize());
    }

    @Test
    void aMissingReadingIsNotAKey() {
        // Guards the caller that has a character but no reading yet - it must
        // not silently land in the set under a broken key.
        assertTrue(set.getMember("keyOf").execute("好", null).isNull());
        assertEquals(0, size(add(empty(), "好", null)));
    }
}
