// =============================================================================
// ArticleSelectionSecretaryModule — the Secretary for the article-selection
// Party.
//
// A third bus, separate from navigation and character selection. An article is
// not a tree position and not a character: the navigator's NodeSelected is a
// path, and a path means nothing to the reader, which wants an id and nothing
// else.
//
// Just a redirect: an incoming ArticleSelected is rebroadcast to every member
// as ShowArticle. Pure (state, envelope) -> Step; no DOM, no console, no
// captures.
// =============================================================================

var ArticleSelectionSecretary = {

    initial: {
        lastSelected:  null,
        recentUnknown: []
    },

    /**
     * Pure routing function. (state, envelope) -> { newState, actions }.
     */
    behavior: function (state, envelope) {
        var msg = envelope.message;

        if (msg.kind === "ArticleSelected") {
            return {
                newState: {
                    lastSelected:  msg.article,
                    recentUnknown: state.recentUnknown
                },
                actions: [{
                    kind:    "BroadcastToMembers",
                    message: { kind: "ShowArticle", article: msg.article }
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
