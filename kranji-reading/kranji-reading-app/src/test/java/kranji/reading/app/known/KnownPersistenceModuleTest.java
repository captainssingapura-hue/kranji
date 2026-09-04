package kranji.reading.app.known;

import hue.captains.singapura.js.homing.ssjs.test.JsModuleTestBase;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Getting the set on and off the device, in the right order.
 *
 * <p>These cover a bug that shipped and was caught only by reloading the page:
 * the party answers a new member immediately with an empty set, and persisting
 * that reply erased the record on every visit. Nothing about the screen looked
 * wrong — the set came back empty on the <em>next</em> load. Which is exactly
 * why the rule was pulled out of the panes and put here.</p>
 */
class KnownPersistenceModuleTest extends JsModuleTestBase {

    private static final String MODULE =
            "/homing/js/kranji/reading/app/known/KnownPersistenceModule.js";

    /** The display boundary, which the migration on load reads keys with. */
    private static final String SWF =
            "/homing/js/kranji/reading/app/ui/PinyinSwfModule.js";

    @BeforeEach
    void load() {
        loadModule(SWF);
        loadModule(MODULE);
        // A store whose load can be resolved by hand, so the window between
        // mounting and the disk answering is a window the test controls.
        js.eval("js", """
            globalThis.mk = function (stored, failLoad, failSave) {
                var t = { told: [], saved: [], problems: [], release: null };
                t.store = {
                    load: function () {
                        return new Promise(function (ok, no) {
                            t.release = function () {
                                if (failLoad) no(new Error('no disk')); else ok(stored);
                            };
                        });
                    },
                    save: function (p, known, lastImport) {
                        if (failSave) return Promise.reject(new Error('read only'));
                        t.saved.push({ known: known.slice(),
                                       lastImport: (lastImport || []).slice() });
                        return Promise.resolve();
                    }
                };
                t.p = createKnownPersistence({
                    store: t.store,
                    tell: function (m) { t.told.push(m); },
                    onProblem: function (b) { t.problems.push(b); }
                });
                return t;
            };
            """);
    }

    /** A rig whose load resolves with the given set only when released. */
    private Value rig(String storedKeys, String storedBatch) {
        return js.eval("js", "mk({ known: [" + storedKeys + "], lastImport: ["
                + storedBatch + "] }, false, false)");
    }

    private Value rigFailing(boolean failLoad, boolean failSave) {
        return js.eval("js", "mk({ known: [], lastImport: [] }, "
                + failLoad + ", " + failSave + ")");
    }

    /** Let the disk answer, then drain the promise queue. */
    private void release(Value t) {
        t.getMember("release").execute();
        drain();
    }

    private void drain() {
        // Three turns is more than the deepest then-chain here.
        for (int i = 0; i < 3; i++) js.eval("js", "Promise.resolve()");
    }

    private void changed(Value t, String keys) {
        t.getMember("p").getMember("changed").execute(js.eval("js", "([" + keys + "])"));
        drain();
    }

    private Value saved(Value t) { return t.getMember("saved"); }

    // ── The bug ────────────────────────────────────────────────────────

    @Test
    void writesNothingBeforeTheDeviceHasAnswered() {
        // The party's reply to a new member arrives first, carrying an empty
        // set. Writing it would erase the record.
        Value t = rig("'22320:de'", "");
        t.getMember("p").getMember("start").execute();
        changed(t, "");                        // the empty reply

        assertEquals(0, saved(t).getArraySize(), "the disk has not answered yet");
    }

    @Test
    void theStoredSetSurvivesThatEmptyReply() {
        Value t = rig("'22320:de0'", "");
        t.getMember("p").getMember("start").execute();
        changed(t, "");
        release(t);

        Value told = t.getMember("told");
        assertEquals(1, told.getArraySize());
        assertEquals("SeedKnown", told.getArrayElement(0).getMember("kind").asString());
        assertEquals("22320:de0",
                told.getArrayElement(0).getMember("known").getArrayElement(0).asString());
    }

    @Test
    void aMarkMadeWhileTheDiskWasReadIsHeldNotDropped() {
        // A child marks something in the second before the load returns. It
        // must reach the device, not vanish.
        Value t = rig("", "");
        t.getMember("p").getMember("start").execute();
        changed(t, "'22320:de'");
        release(t);

        assertEquals(1, saved(t).getArraySize());
        assertEquals("22320:de",
                saved(t).getArrayElement(0).getMember("known").getArrayElement(0).asString());
    }

    @Test
    void onlyTheLatestHeldChangeIsWritten() {
        // Two marks before the disk answers is one write of the later set,
        // not two writes racing to be last.
        Value t = rig("", "");
        t.getMember("p").getMember("start").execute();
        changed(t, "'22320:de'");
        changed(t, "'22320:de', '26376:yuè'");
        release(t);

        assertEquals(1, saved(t).getArraySize());
        assertEquals(2, saved(t).getArrayElement(0).getMember("known").getArraySize());
    }

    // ── Ordinary running ───────────────────────────────────────────────

    @Test
    void writesImmediatelyOnceLoaded() {
        Value t = rig("", "");
        t.getMember("p").getMember("start").execute();
        release(t);
        changed(t, "'22320:de'");

        assertEquals(1, saved(t).getArraySize());
    }

    @Test
    void anEmptyDeviceSeedsNothing() {
        // A device that has never held a set must not wake every pane.
        Value t = rig("", "");
        t.getMember("p").getMember("start").execute();
        release(t);

        assertEquals(0, t.getMember("told").getArraySize());
    }

    // ── A record written before the canonical form existed ─────────────

    @Test
    void aRecordStoredInTheDisplayFormIsReadAsCanonical() {
        // This is not hypothetical: it is what was on the device, and it made
        // every article read 0% with five readings marked. The census says
        // "24202:chuang2"; the record said "24202:chuáng"; the intersection
        // was empty and nothing threw.
        Value t = rig("'24202:chuáng','22320:dì','22320:de'", "");
        t.getMember("p").getMember("start").execute();
        release(t);

        Value known = t.getMember("told").getArrayElement(0).getMember("known");
        assertEquals("24202:chuang2", known.getArrayElement(0).asString());
        assertEquals("22320:di4", known.getArrayElement(1).asString());
        assertEquals("22320:de0", known.getArrayElement(2).asString(),
                "unmarked in the display form means neutral");
    }

    @Test
    void theRepairedRecordIsWrittenBackWithoutWaitingForAChange() {
        // Otherwise the conversion runs again on every visit forever, and the
        // device keeps a record no other part of the system can read.
        Value t = rig("'24202:chuáng'", "");
        t.getMember("p").getMember("start").execute();
        release(t);

        assertEquals(1, saved(t).getArraySize(), "repaired on load, not on edit");
    }

    @Test
    void aRecordAlreadyCanonicalIsNotRewritten() {
        // The counterpart, and the one that keeps this from being a write on
        // every boot for every reader forever.
        Value t = rig("'24202:chuang2'", "");
        t.getMember("p").getMember("start").execute();
        release(t);

        assertEquals(0, saved(t).getArraySize(), "nothing to repair, nothing written");
    }

    @Test
    void aMigrationThatCollidesKeepsOneKey() {
        // 地 marked as both "dì" and "di4" - possible for anyone who marked a
        // reading either side of the change. Two keys in, one key out.
        Value t = rig("'22320:dì','22320:di4'", "");
        t.getMember("p").getMember("start").execute();
        release(t);

        Value known = t.getMember("told").getArrayElement(0).getMember("known");
        assertEquals(1, known.getArraySize());
        assertEquals("22320:di4", known.getArrayElement(0).asString());
    }

    @Test
    void aKeyThatIsNotAReadingIsKeptAsItIs() {
        // Somebody's record. Dropping a line of it is a worse answer than
        // carrying one that matches nothing.
        Value t = rig("'22320:???'", "");
        t.getMember("p").getMember("start").execute();
        release(t);

        assertEquals("22320:???", t.getMember("told").getArrayElement(0)
                .getMember("known").getArrayElement(0).asString());
    }

    @Test
    void theStoredBatchIsSeededWithTheSet() {
        // So that an optimistic import can still be taken back days later.
        Value t = rig("'22320:de'", "'22320:de'");
        t.getMember("p").getMember("start").execute();
        release(t);

        assertEquals(1, t.getMember("told").getArrayElement(0)
                .getMember("lastImport").getArraySize());
    }

    // ── When the device cannot be relied on ────────────────────────────

    @Test
    void aFailedLoadIsReportedAndDoesNotBlockWriting() {
        // Reading failed, so there is nothing left to lose by writing.
        Value t = rigFailing(true, false);
        t.getMember("p").getMember("start").execute();
        changed(t, "'22320:de'");
        release(t);

        assertTrue(t.getMember("problems").getArrayElement(0).asBoolean());
        assertEquals(1, saved(t).getArraySize());
    }

    @Test
    void aFailedSaveIsReported() {
        // Marking looks identical whether or not it reached the disk, so the
        // one thing that must not happen is silence.
        Value t = rigFailing(false, true);
        t.getMember("p").getMember("start").execute();
        release(t);
        changed(t, "'22320:de'");

        Value problems = t.getMember("problems");
        assertTrue(problems.getArraySize() > 0
                && problems.getArrayElement(problems.getArraySize() - 1).asBoolean());
    }

    @Test
    void aProblemIsReportedOnceNotOnEveryWrite() {
        Value t = rigFailing(false, true);
        t.getMember("p").getMember("start").execute();
        release(t);
        changed(t, "'22320:de'");
        changed(t, "'22320:de', '26376:yuè'");

        assertEquals(1, t.getMember("problems").getArraySize(),
                "one report, not one per keystroke");
    }

    @Test
    void recoveryIsReportedToo() {
        // The tally says NOT SAVED; when a write succeeds again it must stop
        // saying so.
        Value t = js.eval("js", """
            (function () {
                var r = mk({ known: [], lastImport: [] }, false, false);
                var fail = true;
                r.store.save = function () {
                    return fail ? Promise.reject(new Error('x')) : Promise.resolve();
                };
                r.p = createKnownPersistence({
                    store: r.store,
                    tell: function (m) { r.told.push(m); },
                    onProblem: function (b) { r.problems.push(b); }
                });
                r.recover = function () { fail = false; };
                return r;
            })()
            """);
        t.getMember("p").getMember("start").execute();
        release(t);
        changed(t, "'22320:de'");
        t.getMember("recover").execute();
        changed(t, "'22320:de', '26376:yuè'");

        Value problems = t.getMember("problems");
        assertEquals(2, problems.getArraySize());
        assertTrue(problems.getArrayElement(0).asBoolean());
        assertFalse(problems.getArrayElement(1).asBoolean());
    }
}
