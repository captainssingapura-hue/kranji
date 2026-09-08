// =============================================================================
// ArticleShelfSecretaryModule — the Secretary for the article workspace's shelf.
//
// Three panes, one conversation. Roots picks a folder; Navigator picks a draft
// inside it; Drafts shows what was picked. PickRoot and PickDraft go in,
// ShelfChanged comes back out to everybody.
//
// ONE bus, not two. Two parties would mean two declarations and two joins per
// widget, and a widget that forgot one would go quiet with nothing to show for
// it. `what` says which half moved, so a pane reacts only to its own.
//
// The whole state rides on every broadcast: { what, root, draft, name }. A pane
// opened after a root was chosen is correct on the next change rather than
// needing a resync handshake - the same reason the gloss bus carries its whole
// selection.
//
// Pure (state, envelope) -> { newState, actions }. No DOM, no captures.
// =============================================================================

var ArticleShelfSecretary = {

    initial: {
        // '' is "nothing chosen yet", which a pane shows as its own default -
        // the first root on the shelf - rather than as an empty screen.
        root: '',
        draft: '',
        name: '',
        recentUnknown: []
    },

    behavior: function (state, envelope) {
        var msg = envelope.message;

        // Choosing a root CLEARS the draft. Draft ids are relative to their
        // root, so an id from one folder means nothing in another - and the
        // bad case is not that it resolves to nothing, it is that it resolves
        // to a different file sitting at the same relative path.
        if (msg.kind === "PickRoot") {
            var next = { root: msg.root || '', draft: '', name: '',
                         recentUnknown: state.recentUnknown };
            return {
                newState: next,
                actions: [{
                    kind: "BroadcastToMembers",
                    message: {
                        kind: "ShelfChanged", what: "root",
                        root: next.root, draft: '', name: ''
                    }
                }]
            };
        }

        if (msg.kind === "PickDraft") {
            // The root travels with the draft rather than being read from
            // state, because the pane that picked it knows which tree it was
            // looking at and this actor does not.
            var withDraft = {
                root: msg.root || state.root,
                draft: msg.draft || '',
                name: msg.name || '',
                recentUnknown: state.recentUnknown
            };
            return {
                newState: withDraft,
                actions: [{
                    kind: "BroadcastToMembers",
                    message: {
                        kind: "ShelfChanged", what: "draft",
                        root: withDraft.root,
                        draft: withDraft.draft,
                        name: withDraft.name
                    }
                }]
            };
        }

        // For a pane that arrived late and would rather ask than wait for the
        // next change. Nothing moves; the whole picture is re-broadcast.
        if (msg.kind === "WhatIsChosen") {
            return {
                newState: state,
                actions: [{
                    kind: "BroadcastToMembers",
                    message: {
                        kind: "ShelfChanged", what: "resync",
                        root: state.root, draft: state.draft, name: state.name
                    }
                }]
            };
        }

        // Unknown kinds are remembered and otherwise ignored. A bus that threw
        // would couple every producer to every consumer's vocabulary.
        var seen = state.recentUnknown.concat([
            { kind: msg.kind, from: envelope.from }
        ]);
        return {
            newState: {
                root: state.root,
                draft: state.draft,
                name: state.name,
                recentUnknown: seen.slice(-10)
            },
            actions: []
        };
    }
};
