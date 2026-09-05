// =============================================================================
// KnownWatchModule — following the known set without changing it.
//
// Everything a pane needs to ANSWER AGAINST the set, and nothing it would need
// to alter one. The reader hides pinyin for readings already claimed; the
// character panel will flag which components are familiar. Neither marks
// anything, and neither should be able to.
//
// One call joins the party, asks for the current set, seeds from the device,
// and keeps the answer up to date. Written out longhand in each pane, that was
// thirty lines each of party plumbing whose only interesting part - "and then
// repaint" - was buried in it.
//
// Read-only BY DEFAULT, and read-only is still enforced by what is not there:
// a caller that does not ask for claims gets no mark, no unmark, and no save,
// so it cannot write to the device even by accident.
//
// `claims: true` is the opt-in. It exists because the reader earned it: a child
// meeting a character in a sentence is the moment they know whether they can
// read it, and sending them to another pane to say so costs more than the claim
// is worth. The flag is deliberate and greppable - one call site asking for
// write access is a thing you can find, which is what "enforced by absence"
// was protecting in the first place.
//
// THE SAME FLAG DECIDES WHO SAVES. A pane that can change the set is the pane
// that keeps it. Marking used to be durable only if some other pane happened to
// be open - the reader claimed and never wrote - so a child reading with the
// library and the reader up lost the lot on closing the tab, with ticks on the
// page the whole time saying otherwise.
// =============================================================================

/**
 * opts = {
 *   party    : the knownSet party, or null when the workspace has none
 *   onChanged: fn()      // the set moved; repaint
 *   id       : string    // actor id prefix, e.g. 'reader'
 *   claims   : boolean   // opt in to mark/unmark/toggle AND to saving
 *   onProblem: fn()      // optional - the device would not give up or take
 *                        //            the record
 * }
 *
 * Returns { annotates, isKnown, set, leave } and, with claims, { toggle }.
 */
function createKnownWatch(opts) {

    var set = createKnownSet();
    var known = [];
    var party = opts.party || null;
    var actorId = null;

    // The device, and whether it has answered yet.
    //
    // NOTHING IS WRITTEN UNTIL THE DEVICE HAS ANSWERED. Writing before the
    // load returns erases the record on every visit, silently, in a way only
    // the next visit can reveal.
    var store = createKnownStore();
    var loaded = false;

    /**
     * Save the set, keeping whatever batch is already on the device.
     *
     * Read-modify-write rather than a plain save, because `lastImport` is no
     * longer this side's business: Import / Export writes it directly and undo
     * reads it back days later. A save that passed its own idea of the batch
     * would take somebody's undo away without ever mentioning it.
     */
    function persist() {
        if (!loaded) return;
        var writing = known.slice();
        store.load(null).then(function (row) {
            return store.save(null, writing, (row && row.lastImport) || []);
        }).catch(function () {
            if (opts.onProblem) opts.onProblem();
        });
    }

    if (party) {
        actorId = 'known/' + (opts.id || 'watch') + '-'
                + Math.random().toString(36).slice(2, 8);
        party.joinActor({
            id: actorId,
            parentSecretary: 'knownSet',
            reactors: {
                KnownChanged: function (msg) {
                    known = (msg && msg.known) ? msg.known : [];
                    // The pane that can change the set is the pane that keeps
                    // it. Marking used to be durable only if some OTHER pane
                    // happened to be open - the reader claimed and never saved
                    // - so a child reading with the library and the reader up
                    // lost the lot on closing the tab, with ticks on screen
                    // the whole time saying otherwise.
                    if (opts.claims) persist();
                    if (opts.onChanged) opts.onChanged();
                }
            }
        });

        // Joined late? Ask, rather than waiting for somebody else to change it.
        party.tellFrom(actorId, { kind: "WhatIsKnown" });

        // Then the device. A union at the secretary, so this races nothing -
        // and opened alone, a watching pane would otherwise behave as though
        // nothing had ever been learnt on a device read on for months.
        createKnownPersistence({
            store: store,
            tell: function (m) { party.tellFrom(actorId, m); },
            onProblem: function (broken) { if (broken && opts.onProblem) opts.onProblem(); }
        }).start().then(function () { loaded = true; },
                        function () { loaded = true; });
    }

    var api = {

        /**
         * Should this character carry its reading? The rule itself is
         * KnownSetModule's; this only supplies the set.
         */
        annotates: function (mode, zi, reading) {
            return set.annotates(mode, known, zi, reading);
        },

        /** Is this exact pair claimed? A read, so every caller gets it. */
        isKnown: function (zi, reading) {
            return set.has(known, zi, reading);
        },

        /** The set as it stands, for a pane that wants to count or list it. */
        set: function () { return known; },

        leave: function () {
            if (!actorId || !party) return;
            try { party.leave(actorId); } catch (e) {}
            actorId = null;
        }
    };

    // The opt-in. Absent unless asked for, so a pane that did not ask has
    // nothing to call - which is the guarantee the read-only note describes.
    if (opts.claims) {
        /**
         * Claim it, or give it back. One entry point rather than two, because
         * the caller is a toggle and splitting it would only move the "which
         * way is it going" question into the caller.
         *
         * The set is the source of truth for which way that is - never a
         * remembered flag, which would go stale the moment another pane
         * marked the same reading.
         */
        api.toggle = function (zi, reading) {
            var key = set.keyOf(zi, reading);
            if (!key || !party || !actorId) return;
            party.tellFrom(actorId, {
                kind: set.has(known, zi, reading) ? 'UnmarkKnown' : 'MarkKnown',
                key: key
            });
        };
    }

    return api;
}
