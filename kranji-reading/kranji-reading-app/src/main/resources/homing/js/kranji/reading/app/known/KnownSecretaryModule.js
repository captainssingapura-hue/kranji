// =============================================================================
// KnownSecretaryModule — the Secretary for the known-set Party.
//
// The set is one fact the whole workspace shares: the control marks a reading,
// the viewer lists it, and the reader stops annotating it. Holding it in the
// Secretary means none of those three has to know the others exist.
//
// A fourth bus, alongside navigation, ziSelection and articleSelection. This
// one is different in kind: the others relay a selection and forget it, while
// this one IS the state. That is why the whole set rides on every broadcast -
// a member that joins late, or one that was not listening when a reading was
// marked, is correct as soon as the next change arrives.
//
// Pure (state, envelope) -> Step; no DOM, no console, no captures. The set is
// an array of "codePoint:reading" strings, which is already serialisable, so
// the Secretary's state needs no encoding of its own.
// =============================================================================

var KnownSecretary = {

    initial: {
        known:         [],
        lastImport:    [],
        recentUnknown: []
    },

    /**
     * Pure routing function. (state, envelope) -> { newState, actions }.
     */
    behavior: function (state, envelope) {
        var msg = envelope.message;

        /** Every broadcast carries the set whole, and the batch that can be undone. */
        function changed(known, lastImport, key) {
            return {
                newState: {
                    known:         known,
                    lastImport:    lastImport,
                    recentUnknown: state.recentUnknown
                },
                actions: [{
                    kind:    "BroadcastToMembers",
                    message: {
                        kind:       "KnownChanged",
                        known:      known,
                        lastImport: lastImport,
                        changed:    key === undefined ? null : key
                    }
                }]
            };
        }

        function without(list, key) {
            var at = list.indexOf(key);
            return at < 0 ? list : list.slice(0, at).concat(list.slice(at + 1));
        }

        if (msg.kind === "MarkKnown" || msg.kind === "UnmarkKnown") {
            var key = msg.key;
            if (!key) return { newState: state, actions: [] };

            var at = state.known.indexOf(key);
            var next;
            if (msg.kind === "MarkKnown") {
                // Idempotent, so a second claim is a no-op rather than a
                // duplicate - marking is meant to be cheap to do.
                if (at >= 0) return { newState: state, actions: [] };
                next = state.known.concat([key]);
            } else {
                if (at < 0) return { newState: state, actions: [] };
                next = state.known.slice(0, at).concat(state.known.slice(at + 1));
            }

            // Touching a reading by hand takes it out of the import's batch.
            // Undoing an optimistic import must not destroy the marks the
            // child actually earned since - and a deliberate mark is exactly
            // that, whether or not the import had claimed it first.
            return changed(next, without(state.lastImport, key), key);
        }

        // What was on the device, rejoining the party.
        //
        // A UNION, never a replace, and that is what makes it safe for every
        // pane to seed. Whichever loads first wins nothing; a second seed of
        // the same rows changes nothing and says nothing, so the panes need no
        // election between them and no agreed order.
        //
        // Union also means a seed arriving late - a slow disk, a pane opened
        // after marking began - cannot take back a reading claimed in the
        // meantime. A replace could.
        if (msg.kind === "SeedKnown") {
            var seed = msg.known || [];
            var merged = state.known.slice();
            for (var i = 0; i < seed.length; i++) {
                if (merged.indexOf(seed[i]) < 0) merged.push(seed[i]);
            }

            // The stored batch comes back too, because the realisation that an
            // import claimed too much arrives days later, not in the same
            // session. Only when this session has no batch of its own: a fresh
            // import always outranks a remembered one.
            var batch = state.lastImport;
            if (batch.length === 0 && msg.lastImport && msg.lastImport.length) {
                batch = msg.lastImport.slice();
            }

            if (merged.length === state.known.length && batch === state.lastImport) {
                return { newState: state, actions: [] };
            }
            return changed(merged, batch, null);
        }

        // A bulk list arriving from a file.
        //
        // A union like a seed, but it remembers WHAT IT ADDED, which is what
        // makes it undoable as a unit. A parent who imports eight hundred
        // readings on the strength of "she finished first grade" and finds it
        // optimistic should be able to take back that import, not unpick it one
        // reading at a time.
        //
        // What it added, not what it contained: a reading already claimed was
        // not this import's doing, and undoing must not take it.
        if (msg.kind === "ImportKnown") {
            var incoming = msg.known || [];
            var after = state.known.slice();
            var added = [];
            for (var j = 0; j < incoming.length; j++) {
                if (after.indexOf(incoming[j]) < 0) {
                    after.push(incoming[j]);
                    added.push(incoming[j]);
                }
            }
            // An import that claimed nothing new leaves the previous batch
            // alone - there is nothing of its own to undo.
            if (added.length === 0) return { newState: state, actions: [] };
            return changed(after, added, null);
        }

        // Take back the last import, whole.
        if (msg.kind === "UndoImport") {
            if (state.lastImport.length === 0) return { newState: state, actions: [] };
            var kept = [];
            for (var k = 0; k < state.known.length; k++) {
                if (state.lastImport.indexOf(state.known[k]) < 0) kept.push(state.known[k]);
            }
            return changed(kept, [], null);
        }

        // The record on the device was rewritten from outside this bus.
        //
        // An import replaced it or merged into it, and what is held here is now
        // an old copy. The message carries no set - it is news that something
        // happened, not the thing that happened - so this FORGETS rather than
        // updating, and the device becomes the only account of what is claimed.
        //
        // Forgetting is what makes replace work. A seed is a union, so a set
        // that kept its old keys would put back every reading the replace was
        // meant to remove, and the next save would write them to the device.
        // Emptied first, the union lands on nothing and reproduces the file.
        //
        // The event goes out as well as the reset, because a pane may want to
        // know for its own reasons - to re-seed, to say it is behind, or to
        // ignore it entirely.
        if (msg.kind === "KnownRecordRewritten") {
            return {
                newState: {
                    known:         [],
                    lastImport:    [],
                    recentUnknown: state.recentUnknown
                },
                actions: [{ kind: "BroadcastToMembers", message: msg }]
            };
        }

        // A member that has just joined asks for the set rather than waiting
        // for somebody else to change it.
        if (msg.kind === "WhatIsKnown") {
            return {
                newState: state,
                actions: [{
                    kind:    "BroadcastToMembers",
                    message: {
                        kind:       "KnownChanged",
                        known:      state.known,
                        lastImport: state.lastImport,
                        changed:    null
                    }
                }]
            };
        }

        // Anything else is remembered for observability and otherwise ignored -
        // a bus that throws on an unknown kind couples every producer to every
        // consumer's vocabulary.
        var seen = state.recentUnknown.concat([
            { kind: msg.kind, from: envelope.from }
        ]);
        return {
            newState: {
                known:         state.known,
                lastImport:    state.lastImport,
                recentUnknown: seen.slice(-10)
            },
            actions: []
        };
    }
};
