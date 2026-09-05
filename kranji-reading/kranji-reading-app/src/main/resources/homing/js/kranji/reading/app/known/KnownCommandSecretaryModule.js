// =============================================================================
// KnownCommandSecretaryModule — the write channel for the known set.
//
// It carries requests, and only requests: "add this", "take this back". It
// never carries an answer. Whether a request was accepted, and what the set
// looks like afterwards, arrives on the read channel - which is a different
// bus, with different members.
//
//   MarkKnown   { key }
//   UnmarkKnown { key }
//
// Two channels rather than one, because the direction is the thing worth
// making structural. A pane that never joins this party cannot change the set:
// not by convention, not by having no toggle method, but by having no channel
// to say it on. That is the same guarantee the old module claimed by leaving
// a method out, made real.
//
// It also removes the trap the single bus set: with commands and facts on one
// party, a pane hears its own request come back and cannot tell it apart from
// a confirmation. Here nothing a pane sends returns to it.
//
// One consumer, ever: KnownService. If a second thing starts listening here,
// the set has two owners again and the whole exercise has been undone.
//
// Pure (state, envelope) -> Step; no DOM, no console, no captures.
// =============================================================================

var KnownCommandSecretary = {

    initial: {
        recentUnknown: []
    },

    /**
     * Pure routing function. (state, envelope) -> { newState, actions }.
     */
    behavior: function (state, envelope) {
        var msg = envelope.message;

        // Mutations only. A read request is not a command and does not belong
        // here - it goes on the read channel, where its answer already lives.
        if (msg.kind === 'MarkKnown' || msg.kind === 'UnmarkKnown') {
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
