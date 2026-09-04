// =============================================================================
// KnownPersistenceModule — the rule for getting the set on and off the device.
//
// Small, and worth its own module for one reason: the rule is subtle, and it
// was in two panes.
//
//   NOTHING IS WRITTEN UNTIL THE DEVICE HAS ANSWERED.
//
// The party replies to a new member immediately, with whatever it holds - which
// at boot is an empty set, while the disk is still being read. Saving that
// reply erases the record on every visit, silently, in a way only the NEXT
// visit can reveal. It is the kind of mistake that passes every screenshot.
//
// A change arriving before the load completes is held, not dropped, so a
// reading marked while the disk was still being read is written as soon as it
// can be.
//
// Loading tells the party rather than returning: seeding is a union at the
// secretary, so no pane has to be elected the loader and none can clobber
// another. A pane that only reads simply never calls changed().
//
// No DOM and no globals - the store and the party arrive as arguments, so this
// runs under GraalVM against fakes.
// =============================================================================

/**
 * deps: {
 *     store    : { load(profile) -> Promise<{known, lastImport}>,
 *                  save(profile, known, lastImport) -> Promise },
 *     tell     : function (message)        // to the party
 *     onProblem: function (isBroken)       // the device cannot be relied on
 * }
 *
 * Returns { start, changed }.
 */
function createKnownPersistence(deps) {

    var loaded  = false;
    var pending = null;      // the set waiting for the device to answer
    var broken  = false;
    var swf     = createPinyinSwf();

    /**
     * A record written before the canonical form existed holds its readings as
     * a person reads them - "24202:chuáng" where the census now says
     * "24202:chuang2". The two never intersect, so every article reads 0% and
     * nothing throws: the exact silent failure the canonical form exists to
     * prevent, arriving through the one door it could still come in by.
     *
     * Converted here rather than at every comparison, because a set that is
     * sometimes one form and sometimes the other is the bug, not the fix. A
     * key that cannot be read as a syllable is kept as it is - it is somebody's
     * record, and dropping it would be a worse answer than carrying it.
     */
    function migrate(keys) {
        var out = [];
        var seen = {};
        for (var i = 0; i < (keys || []).length; i++) {
            var key = keys[i];
            var at = String(key).indexOf(":");
            if (at > 0) {
                var fixed = swf.canonical(key.slice(at + 1));
                if (fixed) key = key.slice(0, at + 1) + fixed;
            }
            // Migration can collide: 地 stored as both "de" and "dì" is two
            // keys, and so is 地 stored as "de0" and "di4" - but a set holding
            // one of each must not come back holding the same key twice.
            if (seen[key]) continue;
            seen[key] = true;
            out.push(key);
        }
        return out;
    }

    function differs(before, after) {
        if (before.length !== after.length) return true;
        for (var i = 0; i < before.length; i++) {
            if (before[i] !== after[i]) return true;
        }
        return false;
    }

    function problem(isBroken) {
        if (broken === isBroken) return;
        broken = isBroken;
        if (deps.onProblem) deps.onProblem(isBroken);
    }

    function flush() {
        if (!loaded || pending === null) return Promise.resolve();
        var write = pending;
        pending = null;
        return deps.store.save(null, write.known, write.lastImport)
                .then(function () { problem(false); },
                      function () { problem(true); });
    }

    return {

        /** Read the device and hand what it held to the party. */
        start: function () {
            return deps.store.load(null).then(function (stored) {
                loaded = true;
                var wasKnown = (stored && stored.known) || [];
                var wasBatch = (stored && stored.lastImport) || [];
                var known = migrate(wasKnown);
                var batch = migrate(wasBatch);
                if (known.length || batch.length) {
                    deps.tell({ kind: "SeedKnown", known: known, lastImport: batch });
                }
                // Repair the device too, and now rather than at the next
                // change: a record nobody happens to edit would otherwise be
                // converted afresh on every visit forever.
                if (differs(wasKnown, known) || differs(wasBatch, batch)) {
                    pending = { known: known, lastImport: batch };
                }
                return flush();
            }, function () {
                // Reading failed, so there is nothing to lose by writing - and
                // if writing fails too, the caller is told.
                loaded = true;
                problem(true);
                return flush();
            });
        },

        /** The set changed. Written now, or as soon as the device has answered. */
        changed: function (known, lastImport) {
            pending = { known: known, lastImport: lastImport || [] };
            return flush();
        }
    };
}
