# Removing a Character

Any reading can leave the set. It is a first-class operation, not an edge
case — a bulk import claimed too much, a reading was marked in a moment of
confidence and has since gone, a parent is correcting a child's self-assessment,
or the reader simply wants the pinyin back for a while.

**A removal takes back one reading, never a character.** Giving back 地 `dì`
leaves 地 `de` exactly where it was. This is the same key that makes marking
honest, working in the other direction: a character half learnt stays half
learnt.

**Because removal is available, marking can be generous.** The two depend on
each other: an irreversible mark makes every decision heavy, and a heavy
decision does not get made at all.

## Hidden, not hard

In the known-set review the remove control is **not shown by default**. It
appears on a deliberate act — a long press, a reveal toggle, an explicit edit
mode — rather than sitting under the thumb.

This is fat-finger protection, and it matters more here than the usual case: the
review is a dense grid of rows, so the target of a stray tap is a claim someone
made on purpose. A term's reading record should not be one mis-tap from a hole
in it.

**As built**, the review is read-only, which is the same argument taken one step
further. Withdrawal happens where the claim was made: in Mark Known, beside the
character's other readings, where what is being given back is legible. In a
dense grid it would be one press away from whichever row the cursor happened to
be on, and what a stray press destroys is a claim somebody made on purpose.

The reveal control described above is still the target for when bulk removal
arrives. Until then the review answers *what has been claimed* and does not
arbitrate claims.

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

A bulk import carries a batch, so it can be taken back whole.

The batch is all that is recorded. Per-mark channel provenance was designed and
then cut, because this — the only thing it was still being kept for — turned out
not to need it. See *The Known Set*.

It removes only what that import **added** — not what the file contained. A
reading marked from Mark Known before the import was not that import's doing,
and undoing must not take it. Nor is one re-marked deliberately since: touching
a reading by hand removes it from the batch, because a deliberate mark is a
claim of its own whether or not the import had claimed it first.

Otherwise undoing an optimistic import would quietly destroy the marks the child
actually earned.

**As built.** The batch is the last import, and it is stored on the device
alongside the set — the realisation that an import claimed too much arrives days
later, not in the same session, so an undo that died with the workspace would be
one nobody could reach in time. The control appears only when there is an import
to take back, and says how many readings that is; a button that is always there
but usually does nothing teaches people to ignore it.

**One batch, not a history.** A second import replaces the first as the
undoable one, and the earlier import's readings become indistinguishable from
readings the child earned. That is a stated limit rather than an oversight — the
undo exists for the mistake caught soon after it is made, and a history would
cost the shape of the set and of the export file. *The Known Set* records the
trade and the cheap fix if it ever needs one.

Once taken back, the batch is spent. A second press does nothing rather than
starting on readings that were never part of it.
