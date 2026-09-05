// =============================================================================
// KnownWatchModule — following the known set without owning it.
//
// A pane's mirror of the service's record, kept current by facts from the READ
// channel. It answers questions in constant time and it writes nothing: no
// store, no save, no device. This module used to construct a persistence
// engine of its own, with an empty failure handler, in every pane that
// watched - which is how a failed save came to be invisible in the two panes
// most likely to be open.
//
//   read channel  in : KnownSnapshot { keys }  -> rebuild the mirror
//                      KnownAdded    { key }   -> add one
//                      KnownRemoved  { key }   -> remove one
//   read channel  out: WhatIsKnown            -> ask, on mounting
//   write channel out: MarkKnown / UnmarkKnown -> only with claims: true
//
// A snapshot REPLACES rather than unions, which is the opposite of the
// service's own seed rule and right for the same reason: the service is the
// authority, this is a copy of it, and a copy that could only grow would keep
// showing a reading the reader had just taken back.
//
// NOTHING IS UPDATED WHEN A COMMAND IS SENT. toggle() asks and returns, and
// the badge changes when - and only when - the fact comes back on the read
// channel. The set lives on the device, the device can refuse, and a cell that
// changed on the request would be telling a child something that had not
// happened. It also makes the two channels worth having: with one bus a pane
// hears its own request and cannot tell it from a confirmation.
//
// Read-only is structural, not conventional. A pane that is handed no write
// party has nothing to write with - not an object with a method left off, but
// no channel at all.
// =============================================================================

/**
 * opts = {
 *   events   : the knownEvents party - facts in, questions out
 *   commands : the knownCommands party, or null - requests out. Required for
 *              claims; a pane that omits it cannot change the set.
 *   onChanged: fn()      // the mirror moved; restyle
 *   id       : string    // actor id prefix, e.g. 'reader'
 *   claims   : boolean   // opt in to toggle; omit for read-only
 * }
 *
 * Returns { annotates, isKnown, record, size, leave } and, with claims and a
 * command party, { toggle }.
 */
function createKnownWatch(opts) {

    var set = createKnownSet();
    var record = createKnownRecord();
    var events = opts.events || null;
    var commands = opts.commands || null;
    var actorId = null;
    var writerId = null;

    function moved() { if (opts.onChanged) opts.onChanged(); }

    if (events) {
        actorId = 'known/' + (opts.id || 'watch') + '-'
                + Math.random().toString(36).slice(2, 8);
        events.joinActor({
            id: actorId,
            parentSecretary: 'knownEvents',
            reactors: {
                KnownSnapshot: function (msg) {
                    record = createKnownRecord((msg && msg.keys) ? msg.keys : []);
                    moved();
                },
                KnownAdded: function (msg) {
                    if (msg && msg.key && record.add(msg.key)) moved();
                },
                KnownRemoved: function (msg) {
                    if (msg && msg.key && record.remove(msg.key)) moved();
                }
            }
        });

        // Joined late? Ask. The service answers with a snapshot, so a pane
        // opened after a morning's marking is correct immediately rather than
        // on the next change.
        events.tellFrom(actorId, { kind: 'WhatIsKnown' });
    }

    // A separate membership, because it is a separate channel. A pane holds
    // this only if it was given somewhere to write.
    if (opts.claims && commands) {
        writerId = 'known/' + (opts.id || 'watch') + '-w-'
                 + Math.random().toString(36).slice(2, 8);
        commands.joinActor({
            id: writerId,
            parentSecretary: 'knownCommands',
            reactors: {}          // it speaks here; it does not listen here
        });
    }

    var api = {

        /**
         * Should this character carry its reading? The rule itself is
         * KnownSetModule's; this only supplies the mirror.
         */
        annotates: function (mode, zi, reading) {
            return set.annotates(mode, record, zi, reading);
        },

        /** Is this exact pair claimed? A read, so every caller gets it. */
        isKnown: function (zi, reading) {
            var key = set.keyOf(zi, reading);
            return !!key && record.has(key);
        },

        /** The mirror itself, for a pane that wants to ask it many times. */
        record: function () { return record; },

        size: function () { return record.size(); },

        leave: function () {
            if (actorId && events) {
                try { events.leave(actorId); } catch (e) {}
                actorId = null;
            }
            if (writerId && commands) {
                try { commands.leave(writerId); } catch (e) {}
                writerId = null;
            }
        }
    };

    if (writerId) {
        /**
         * Ask for it, or ask for it back. One entry point rather than two,
         * because the caller is a toggle and splitting it would only move the
         * "which way is it going" question into the caller.
         *
         * The mirror decides which way to ask; the service decides whether the
         * question meant anything. It ignores a claim it already holds and a
         * release of something it does not, so a stale mirror costs one wasted
         * message and never a wrong set.
         *
         * Returns nothing, and changes nothing. The cell moves when the fact
         * arrives.
         */
        api.toggle = function (zi, reading) {
            var key = set.keyOf(zi, reading);
            if (!key) return;
            commands.tellFrom(writerId, {
                kind: record.has(key) ? 'UnmarkKnown' : 'MarkKnown',
                key: key
            });
        };
    }

    return api;
}
