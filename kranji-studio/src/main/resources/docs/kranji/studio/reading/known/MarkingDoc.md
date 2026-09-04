# Marking a Character Known

```
Article  ──tap a character──▶  Mark Known  ──one toggle per reading──▶  in the set
```

The marking control lives in a **pane of its own**, not in the reader.

## One toggle per reading

The pane lists *every* reading the selected character has, each with its own
toggle, because the set is keyed on the pair. 地 offers `de` and `dì`
separately; claiming one says nothing about the other.

Each reading carries its corpus evidence beside the claim — whether it is the
usual reading, and how often it is observed. A reading seen 7,394 times is a
different proposition from one seen 12, and the person marking should see which
before deciding.

**This is why marking is a pane rather than a gesture.** The claim being made —
*this reading, not that one* — needs the readings laid out side by side to be
made honestly, and the reader has no room for that beside the text.

## Why not tap-to-mark in the reader

Tapping a character in the article would be one gesture instead of two, and that
is exactly the problem. A claim that a child can read a character should cost
looking at the character.

Going to a pane means the reader sees the glyph at size and its readings laid
out before claiming one — which is the difference between "I recognise that
shape" and "I can read that". It also makes the accidental case impossible: a
stray tap in the middle of an article moves a selection, it does not alter the
record.

The two-step is not friction to be optimised away later. It is the deliberation.

## The wiring already exists

Tapping a character in the reader publishes `ZiSelected` on the `ziSelection`
party, and Mark Known listens on it exactly as the character pane does. Nothing
new was needed to get from an article to a character.

That the reader does not know Mark Known exists is the usual benefit: a search
result, the character browser, or the known-set review can all drive the pane
the same way, and marking works from all of them without any of them naming
each other. Mark Known likewise never names the reader or the viewer — it
produces on the `knownSet` party, and whoever is listening repaints.

## What marking does

Adds the pair to the set. Idempotent — claiming a reading already in the set is
not an error, not a duplicate, and produces no broadcast, so panes do not
repaint for a change that did not happen.

Each toggle shows its reading's current membership, so the pane reads as a
statement of fact rather than a row of buttons that might already have been
pressed.

## Bulk marking

Import does not go through this flow — it is a file, chosen in this same pane,
and what it adds is remembered as a batch so it can be taken back as a unit. See
*Removing a Character*.

A mark carries no record of which of the two routes it arrived by: channel
provenance is cut, and the reasons are in *The Known Set*.
