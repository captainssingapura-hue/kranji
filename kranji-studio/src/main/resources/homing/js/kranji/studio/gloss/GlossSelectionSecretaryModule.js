// =============================================================================
// GlossSelectionSecretaryModule — the Secretary for the gloss workbench's
// selection bus.
//
// ONE bus for every entity type, not one per type. A message names the entity
// it concerns, and each widget reacts only to the entity it sits below. Six
// parties would mean six declarations, six joins per widget, and a widget that
// forgot one would go quiet with nothing to show for it.
//
// The whole state is carried on every broadcast: { entity, pks, all }. A widget
// that mounted after a selection was made is correct on the next change rather
// than needing a resync handshake - the same reason the known-set bus carries
// its whole set.
//
// Pure (state, envelope) -> { newState, actions }. No DOM, no captures.
// =============================================================================

var GlossSelectionSecretary = {

    initial: {
        // entity name -> array of selected pks. Absent means "never selected",
        // which is not the same as "selected nothing" - a widget with no
        // upstream selection yet shows everything; one whose upstream was
        // cleared shows nothing.
        byEntity: {},
        recentUnknown: []
    },

    behavior: function (state, envelope) {
        var msg = envelope.message;

        if (msg.kind === "PksSelected") {
            var next = {};
            for (var k in state.byEntity) next[k] = state.byEntity[k];
            next[msg.entity] = (msg.pks || []).slice();

            return {
                newState: {
                    byEntity: next,
                    recentUnknown: state.recentUnknown
                },
                actions: [{
                    kind: "BroadcastToMembers",
                    message: {
                        kind:   "SelectionChanged",
                        entity: msg.entity,
                        pks:    next[msg.entity],
                        // The whole picture, so a widget can scope itself
                        // correctly the moment it arrives rather than waiting
                        // for its own upstream to change again.
                        all:    next
                    }
                }]
            };
        }

        // Not "selected nothing" - "not selecting". The entry is REMOVED, so
        // the entity goes back to never-selected and everything below it shows
        // whole again.
        //
        // The distinction is the whole reason this kind exists. A sound picker
        // with nothing ticked is a filter nobody has applied, and answering
        // that with [] would empty the demand grid - which reads as "no
        // character has any of these readings" rather than "you have not
        // chosen any readings yet".
        if (msg.kind === "SelectionCleared") {
            var rest = {};
            for (var e in state.byEntity) {
                if (e !== msg.entity) rest[e] = state.byEntity[e];
            }
            return {
                newState: {
                    byEntity: rest,
                    recentUnknown: state.recentUnknown
                },
                actions: [{
                    kind: "BroadcastToMembers",
                    message: {
                        kind:   "SelectionChanged",
                        entity: msg.entity,
                        // undefined, not []. Downstream reads the two apart.
                        pks:    undefined,
                        all:    rest
                    }
                }]
            };
        }

        // A widget that mounts after the selection was made has missed the
        // broadcast, and nothing else would ever tell it. Asking is how it
        // catches up - the same move the known set makes with WhatIsKnown.
        //
        // The reply names entity "*", so it cannot be mistaken for a change to
        // any one relation: it carries the whole map and nothing moved.
        if (msg.kind === "WhatIsSelected") {
            return {
                newState: state,
                actions: [{
                    kind: "BroadcastToMembers",
                    message: {
                        kind:   "SelectionChanged",
                        entity: "*",
                        pks:    [],
                        all:    state.byEntity
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
                byEntity: state.byEntity,
                recentUnknown: seen.slice(-10)
            },
            actions: []
        };
    }
};
