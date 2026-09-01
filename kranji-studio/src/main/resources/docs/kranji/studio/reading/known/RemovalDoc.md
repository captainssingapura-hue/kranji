# Removing a Character

Any character can leave the set. It is a first-class operation, not an edge
case — a bulk import claimed too much, a character was marked in a moment of
confidence and has since gone, a parent is correcting a child's self-assessment,
or the reader simply wants the pinyin back for a while.

**Because removal is available, marking can be generous.** The two depend on
each other: an irreversible mark makes every decision heavy, and a heavy
decision does not get made at all.

## Hidden, not hard

In the known-set review the remove control is **not shown by default**. It
appears on a deliberate act — a long press, a reveal toggle, an explicit edit
mode — rather than sitting under the thumb.

This is fat-finger protection, and it matters more here than the usual case: the
review is a dense grid of characters, so the target of a stray tap is a
character, and what it destroys is a claim someone made on purpose. A term's
reading record should not be one mis-tap from a hole in it.

**Hidden from fingers, not from view.** The distinction is the whole design:

- Discoverable — a reader looking for how to remove something finds it.
- Not reachable by accident — no single careless tap removes anything.
- Not confirmed by a dialog. A modal asking "are you sure?" trains people to
  press yes, and adds a step to the deliberate case to protect against the
  accidental one. Making the control deliberate to reach does that better.

## No automatic removal

A character does not slip out because it has not been seen for a month. There is
no decay, and there are no timestamps to build one from.

Decay is inference from silence, which is the same reasoning that removed the
`LEARNING` state — with the added insult of undoing something a person
consciously claimed.

## Undo an import as a unit

A bulk import carries a batch id, so it can be taken back whole. This is the
main reason provenance is recorded at all.

It removes only what that import added: a character marked from Zi Details
before the import, or re-marked deliberately since, is not swept up in it.
Otherwise undoing an optimistic import would quietly destroy the marks the child
actually earned.
