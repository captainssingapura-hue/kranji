// =============================================================================
// KnownEventSecretaryModule — the read channel for the known set.
//
// It carries what has already happened, and one request for a starting point:
//
//   WhatIsKnown                 // a pane that has just mounted, asking
//   KnownSnapshot { keys }      // the whole set, for whoever just asked
//   KnownAdded    { key }       // this pair is now claimed
//   KnownRemoved  { key }       // this pair is not
//
// KnownAdded rather than KnownChanged with a boolean, because the name of a
// fact should be the fact. A pane reading `claimed: true` has to think about
// what false would have meant; a pane reading KnownAdded does not.
//
// Every one of these is published by KnownService and by nothing else. A pane
// updates its badges here and only here - never when it sends a command,
// because a request is not an outcome. The set is on the device, the device
// can refuse, and a badge that changed on the request would be showing the
// child something that had not happened.
//
// WhatIsKnown lives on this channel rather than the write one, because it
// changes nothing. Read-only panes therefore need the read party and nothing
// else, which is what makes "cannot write" a fact about their wiring rather
// than a promise in their documentation.
//
// Pure (state, envelope) -> Step; no DOM, no console, no captures.
// =============================================================================

var KnownEventSecretary = {

    initial: {
        recentUnknown: []
    },

    /**
     * Pure routing function. (state, envelope) -> { newState, actions }.
     */
    behavior: function (state, envelope) {
        var msg = envelope.message;

        var carried = msg.kind === 'WhatIsKnown'
                   || msg.kind === 'KnownSnapshot'
                   || msg.kind === 'KnownAdded'
                   || msg.kind === 'KnownRemoved';

        if (carried) {
            return {
                newState: state,
                actions: [{ kind: 'BroadcastToMembers', message: msg }]
            };
        }

        // Anything else is remembered for observability and otherwise ignored -
        // a bus that throws on an unknown kind couples every producer to every
        // consumer's vocabulary.
        var seen = state.recentUnknown.concat([
            { kind: msg.kind, from: envelope.from }
        ]);
        return {
            newState: { recentUnknown: seen.slice(-10) },
            actions: []
        };
    }
};
