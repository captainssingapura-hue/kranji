# Marking a Character Known

```
Article  ──tap a character──▶  Zi Details  ──Add to Known──▶  in the set
```

The Add control lives on the **character's own page**, not in the reader.

## Why not tap-to-mark in the reader

Tapping a character in the article would be one gesture instead of two, and that
is exactly the problem. A claim that a child can read a character should cost
looking at the character.

Going through Zi Details means the reader sees the glyph at size, its reading,
and its composition before claiming it — which is the difference between "I
recognise that shape" and "I can read that". It also makes the accidental case
impossible: a stray tap in the middle of an article opens a pane, it does not
alter the record.

The two-step is not friction to be optimised away later. It is the deliberation.

## The wiring already exists

Tapping a character in the reader publishes `ZiSelected` on the `ziSelection`
party, and the Zi Details widget already listens. Nothing new is needed to get
from an article to a character — only the Add control on the pane that is
already there.

That the reader does not know the Add control exists is the usual benefit: a
search result, the character browser, or the known-set manager can all reach Zi
Details the same way, and marking works from all of them without any of them
naming each other.

## What Add does

Adds the character to the current profile's set with channel `ZI_DETAIL` and no
batch. Idempotent — adding a character already in the set is not an error and
not a duplicate.

The control shows the character's current membership, so the pane reads as a
statement of fact rather than a button that might already have been pressed.

## Bulk marking

`IMPORT` and `SEED` do not go through this flow; they are list operations in the
known-set manager, and they carry a batch id precisely so they can be taken back
as a unit. See *Removing a Character*.
