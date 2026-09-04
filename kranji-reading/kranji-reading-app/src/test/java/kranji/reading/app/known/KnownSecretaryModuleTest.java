package kranji.reading.app.known;

import hue.captains.singapura.js.homing.ssjs.test.JsModuleTestBase;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The bus that holds the known set.
 *
 * <p>The one Secretary of the four that carries state rather than relaying a
 * selection, which is exactly why it is worth testing without a browser: a
 * broadcast that goes missing, or one that carries a stale set, shows up as a
 * pane quietly disagreeing with another pane rather than as an error.</p>
 */
class KnownSecretaryModuleTest extends JsModuleTestBase {

    private static final String MODULE =
            "/homing/js/kranji/reading/app/known/KnownSecretaryModule.js";

    private static final String XING = "34892:xíng";
    private static final String HANG = "34892:háng";

    private Value secretary;

    @BeforeEach
    void load() {
        loadModule(MODULE);
        secretary = global("KnownSecretary");
    }

    /** One turn of the bus: (state, envelope) -> { newState, actions }. */
    private Value step(Value state, String kind, String key) {
        Value envelope = js.eval("js", "({ from: 'control', message: {} })");
        envelope.getMember("message").putMember("kind", kind);
        if (key != null) envelope.getMember("message").putMember("key", key);
        return secretary.getMember("behavior").execute(state, envelope);
    }

    private Value initial() { return secretary.getMember("initial"); }

    private Value stateAfter(Value state, String kind, String key) {
        return step(state, kind, key).getMember("newState");
    }

    private Value actionsOf(Value stepResult) { return stepResult.getMember("actions"); }

    private Value onlyBroadcast(Value stepResult) {
        Value actions = actionsOf(stepResult);
        assertEquals(1, actions.getArraySize(), "expected exactly one broadcast");
        Value action = actions.getArrayElement(0);
        assertEquals("BroadcastToMembers", action.getMember("kind").asString());
        return action.getMember("message");
    }

    // ── Marking ────────────────────────────────────────────────────────

    @Test
    void markingAddsTheKeyAndTellsEveryone() {
        Value result = step(initial(), "MarkKnown", XING);

        assertEquals(1, result.getMember("newState").getMember("known").getArraySize());

        Value msg = onlyBroadcast(result);
        assertEquals("KnownChanged", msg.getMember("kind").asString());
        assertEquals(XING, msg.getMember("changed").asString());
        assertEquals(XING, msg.getMember("known").getArrayElement(0).asString());
    }

    @Test
    void everyBroadcastCarriesTheWholeSet() {
        // Not a delta. A pane that mounted late, or one that missed a message,
        // is correct on the next change rather than needing a resync.
        Value after = stateAfter(initial(), "MarkKnown", XING);
        Value msg = onlyBroadcast(step(after, "MarkKnown", HANG));

        assertEquals(2, msg.getMember("known").getArraySize());
        assertEquals(XING, msg.getMember("known").getArrayElement(0).asString());
        assertEquals(HANG, msg.getMember("known").getArrayElement(1).asString());
    }

    @Test
    void markingTwiceSaysNothing() {
        // A second claim is not news. Broadcasting it would make every pane
        // repaint for a change that did not happen.
        Value after = stateAfter(initial(), "MarkKnown", XING);
        Value again = step(after, "MarkKnown", XING);

        assertEquals(0, actionsOf(again).getArraySize());
        assertEquals(1, again.getMember("newState").getMember("known").getArraySize());
    }

    // ── Unmarking ──────────────────────────────────────────────────────

    @Test
    void unmarkingTakesBackOnlyTheReadingNamed() {
        Value both = stateAfter(stateAfter(initial(), "MarkKnown", XING), "MarkKnown", HANG);
        Value msg = onlyBroadcast(step(both, "UnmarkKnown", HANG));

        assertEquals(1, msg.getMember("known").getArraySize());
        assertEquals(XING, msg.getMember("known").getArrayElement(0).asString(),
                "the other reading of the same character must survive");
    }

    @Test
    void unmarkingSomethingAbsentSaysNothing() {
        Value after = stateAfter(initial(), "MarkKnown", XING);

        assertEquals(0, actionsOf(step(after, "UnmarkKnown", HANG)).getArraySize());
    }

    @Test
    void aMessageWithNoKeyIsIgnored() {
        Value result = step(initial(), "MarkKnown", null);

        assertEquals(0, actionsOf(result).getArraySize());
        assertEquals(0, result.getMember("newState").getMember("known").getArraySize());
    }

    // ── Seeding from the device ────────────────────────────────────────

    /** A seed carrying the given keys. */
    private Value seed(Value state, String... keys) {
        Value envelope = js.eval("js", "({ from: 'control', message: { kind: 'SeedKnown', known: [] } })");
        Value known = envelope.getMember("message").getMember("known");
        for (int i = 0; i < keys.length; i++) known.setArrayElement(i, keys[i]);
        return secretary.getMember("behavior").execute(state, envelope);
    }

    @Test
    void aSeedBringsTheStoredSetBack() {
        Value msg = onlyBroadcast(seed(initial(), XING, HANG));

        assertEquals(2, msg.getMember("known").getArraySize());
        assertTrue(msg.getMember("changed").isNull(), "a seed is not a claim being made");
    }

    @Test
    void seedingTwiceSaysNothingTheSecondTime() {
        // Why every pane may seed without electing one of them to do it.
        Value after = seed(initial(), XING, HANG).getMember("newState");

        assertEquals(0, actionsOf(seed(after, XING, HANG)).getArraySize());
    }

    @Test
    void aLateSeedCannotTakeBackWhatWasJustClaimed() {
        // The disk is slow; a reading is marked while the load is in flight.
        // A replace would undo it. A union cannot.
        Value marked = stateAfter(initial(), "MarkKnown", HANG);
        Value msg = onlyBroadcast(seed(marked, XING));

        assertEquals(2, msg.getMember("known").getArraySize());
        assertEquals(HANG, msg.getMember("known").getArrayElement(0).asString(),
                "the claim made while loading survives");
    }

    @Test
    void anEmptySeedSaysNothing() {
        // A device that has never held a set must not wake every pane.
        assertEquals(0, actionsOf(seed(initial())).getArraySize());
    }

    // ── Import, and taking it back ─────────────────────────────────────

    private static final String YUE = "26376:yuè";

    /** A bulk list arriving from a file. */
    private Value importing(Value state, String... keys) {
        Value envelope = js.eval("js",
                "({ from: 'control', message: { kind: 'ImportKnown', known: [] } })");
        Value known = envelope.getMember("message").getMember("known");
        for (int i = 0; i < keys.length; i++) known.setArrayElement(i, keys[i]);
        return secretary.getMember("behavior").execute(state, envelope);
    }

    private Value undo(Value state) { return step(state, "UndoImport", null); }

    private java.util.List<String> listOf(Value arr) {
        var out = new java.util.ArrayList<String>();
        for (int i = 0; i < arr.getArraySize(); i++) out.add(arr.getArrayElement(i).asString());
        return out;
    }

    @Test
    void anImportAddsAndRemembersWhatItAdded() {
        Value msg = onlyBroadcast(importing(initial(), XING, HANG));

        assertEquals(2, msg.getMember("known").getArraySize());
        assertEquals(java.util.List.of(XING, HANG), listOf(msg.getMember("lastImport")));
    }

    @Test
    void theBatchIsWhatWasAddedNotWhatWasInTheFile() {
        // 行 xíng was already claimed. Undoing this import must not take it -
        // it was not this import's doing.
        Value earned = stateAfter(initial(), "MarkKnown", XING);
        Value msg = onlyBroadcast(importing(earned, XING, HANG));

        assertEquals(java.util.List.of(HANG), listOf(msg.getMember("lastImport")));
    }

    @Test
    void undoTakesBackTheImportAndNothingElse() {
        // The case the whole batch exists for: eight hundred readings claimed
        // on the strength of "she finished first grade", found optimistic.
        Value earned = stateAfter(initial(), "MarkKnown", XING);
        Value imported = importing(earned, HANG, YUE).getMember("newState");
        Value msg = onlyBroadcast(undo(imported));

        assertEquals(java.util.List.of(XING), listOf(msg.getMember("known")),
                "what the child actually earned survives");
        assertEquals(0, msg.getMember("lastImport").getArraySize(), "nothing left to undo");
    }

    @Test
    void aReadingReMarkedSinceTheImportSurvivesTheUndo() {
        // Marking it by hand is a claim of its own, whether or not the import
        // had claimed it first. Undoing the import must not destroy it.
        Value imported = importing(initial(), HANG, YUE).getMember("newState");
        Value deliberate = stateAfter(stateAfter(imported, "UnmarkKnown", HANG),
                                      "MarkKnown", HANG);
        Value msg = onlyBroadcast(undo(deliberate));

        assertEquals(java.util.List.of(HANG), listOf(msg.getMember("known")));
    }

    @Test
    void anImportThatClaimsNothingNewSaysNothing() {
        Value imported = importing(initial(), XING).getMember("newState");

        assertEquals(0, actionsOf(importing(imported, XING)).getArraySize());
    }

    @Test
    void undoingNothingSaysNothing() {
        assertEquals(0, actionsOf(undo(initial())).getArraySize());
    }

    @Test
    void undoIsNotRepeatable() {
        // Once taken back, the batch is spent - a second press must not start
        // eating readings that were never part of it.
        Value imported = importing(initial(), HANG).getMember("newState");
        Value undone = undo(imported).getMember("newState");

        assertEquals(0, actionsOf(undo(undone)).getArraySize());
    }

    @Test
    void theStoredBatchComesBackWithTheSet() {
        // The realisation arrives days later, so the undo has to outlive the
        // session that made the import.
        Value envelope = js.eval("js",
                "({ from: 'viewer', message: { kind: 'SeedKnown', known: ['34892:xíng'],"
              + " lastImport: ['34892:xíng'] } })");
        Value result = secretary.getMember("behavior").execute(initial(), envelope);

        assertEquals(java.util.List.of(XING), listOf(onlyBroadcast(result).getMember("lastImport")));
    }

    @Test
    void aFreshImportOutranksARememberedOne() {
        // A seed arriving after this session imported something must not
        // replace the batch that is actually undoable now.
        Value imported = importing(initial(), YUE).getMember("newState");
        Value envelope = js.eval("js",
                "({ from: 'viewer', message: { kind: 'SeedKnown', known: ['34892:xíng'],"
              + " lastImport: ['34892:xíng'] } })");
        Value result = secretary.getMember("behavior").execute(imported, envelope);

        assertEquals(java.util.List.of(YUE), listOf(onlyBroadcast(result).getMember("lastImport")));
    }

    // ── Joining late ───────────────────────────────────────────────────

    @Test
    void aMemberCanAskForTheSet() {
        // The viewer opens after twenty readings have been marked. It asks,
        // rather than waiting for somebody else to change something.
        Value after = stateAfter(initial(), "MarkKnown", XING);
        Value result = step(after, "WhatIsKnown", null);

        Value msg = onlyBroadcast(result);
        assertEquals(1, msg.getMember("known").getArraySize());
        assertTrue(msg.getMember("changed").isNull(), "nothing changed - it was a question");
    }

    // ── Everything else ────────────────────────────────────────────────

    @Test
    void anUnknownMessageIsRememberedNotThrown() {
        // A bus that throws on an unfamiliar kind couples every producer to
        // every consumer's vocabulary. It is recorded instead, for whoever is
        // looking at why nothing happened.
        Value result = step(initial(), "SomethingElse", null);

        assertEquals(0, actionsOf(result).getArraySize());
        Value seen = result.getMember("newState").getMember("recentUnknown");
        assertEquals(1, seen.getArraySize());
        assertEquals("SomethingElse", seen.getArrayElement(0).getMember("kind").asString());
        assertEquals("control", seen.getArrayElement(0).getMember("from").asString());
    }

    @Test
    void theUnknownLogIsBounded() {
        Value state = initial();
        for (int i = 0; i < 25; i++) state = stateAfter(state, "Noise" + i, null);

        Value seen = state.getMember("recentUnknown");
        assertEquals(10, seen.getArraySize(), "bounded, or a long session leaks");
        assertEquals("Noise24", seen.getArrayElement(9).getMember("kind").asString(),
                "the most recent is what is kept");
    }

    @Test
    void theStateIsNeverMutatedInPlace() {
        // Homing replays a Secretary from its initial state; a behavior that
        // edited what it was handed would make that replay lie.
        Value first = initial();
        Value after = stateAfter(first, "MarkKnown", XING);

        assertEquals(0, first.getMember("known").getArraySize(),
                "initial must survive a turn untouched");
        assertNotSame(first, after);
    }
}
