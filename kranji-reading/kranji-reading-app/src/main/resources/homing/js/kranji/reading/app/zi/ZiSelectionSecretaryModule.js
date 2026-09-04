// =============================================================================
// ZiSelectionSecretaryModule — the Secretary for the character-selection
// Party.
//
// A second bus, separate from navigation on purpose. The navigator relays
// tree positions; a character is not a tree position (terminals hold
// characters as content, not as children), so a codepoint travelling on that
// bus would be read as a path by the syllable panes.
//
// Just a redirect: an incoming ZiSelected is rebroadcast to every member as
// ShowZi. Pure (state, envelope) -> Step; no DOM, no console, no captures.
// =============================================================================

var ZiSelectionSecretary = {

    initial: {
        lastSelected:  null,
        recentUnknown: []
    },

    /**
     * Pure routing function. (state, envelope) -> { newState, actions }.
     */
    behavior: function (state, envelope) {
        var msg = envelope.message;

        if (msg.kind === "ZiSelected") {
            return {
                newState: {
                    lastSelected:  msg.zi,
                    recentUnknown: state.recentUnknown
                },
                actions: [{
                    kind:    "BroadcastToMembers",
                    message: { kind: "ShowZi", zi: msg.zi }
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
                lastSelected:  state.lastSelected,
                recentUnknown: seen.slice(-10)
            },
            actions: []
        };
    }
};
