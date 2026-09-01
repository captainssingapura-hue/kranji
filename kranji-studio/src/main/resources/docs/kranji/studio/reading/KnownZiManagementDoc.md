# Known Zi Management

The known set is the thing the whole app adapts to. It gets a workspace, not a
settings page.

## Why explicit, and not inferred

The tempting design is to infer knowledge: promote a character after the reader
has met it *n* times without asking for its reading. It requires nothing of the
reader and it measures behaviour rather than self-report.

It is also guessing. "Did not ask for the reading" is not "knew the character".
The child may have skipped the word, guessed from context, recognised the
phrase without the character, or simply kept going. Silence is weak evidence,
and an app that quietly promotes on weak evidence will withdraw support from
characters the reader never secured — which is the precise failure the adaptive
mechanic exists to prevent.

So marking is explicit. The cost of explicit marking is effort, and the answer
to that cost is not an algorithm — it is making marking cheap from wherever the
reader already is, and making every mark trivially reversible.

## Channels

A character can enter the known set from several places. Each mark records where
it came from.

| Channel | Where | Typical use |
|---|---|---|
| `READER` | tapping a character mid-article | "I actually know this one" |
| `MANAGER` | the known-set workspace | deliberate review of what is known |
| `PRACTICE` | confirming during a practice session | strongest evidence |
| `IMPORT` | a bulk list | "she finished first grade" |
| `SEED` | initial profile setup | starting point for a reader who is not a beginner |

Provenance matters because the channels are not equally reliable. A character
confirmed in practice is better established than one that arrived in a list of
eight hundred. Recording the channel lets the manager show that difference, lets
a parent audit what has been claimed, and — most usefully — lets an import be
undone **as a unit** when it turns out to have been optimistic.

Without provenance, a regretted bulk import can only be unpicked one character
at a time.

## Removal is a first-class operation

Any character can be moved back out of the known set, from the manager or from
the reader. This is not an edge case:

- A bulk import claimed too much.
- A character was marked in a moment of confidence and has since gone.
- A parent is correcting a child's self-assessment.
- The reader wants the annotation back for a while.

Because removal is easy, marking can be generous. The two properties depend on
each other: an irreversible mark makes every decision heavy, and a heavy
decision does not get made at all.

There is deliberately **no automatic decay**. A character does not slip out of
the known set because it has not been seen for a month. Decay is another
inference from silence, and it fails the same way — with the added insult of
undoing something the reader consciously claimed.

## The workspace

A grid of characters, filtered and sorted, with state visible at a glance.

**Filters that matter:**

- **State** — known, in progress, unseen.
- **Radical** — every character sharing 氵, 木, 亻.
- **Stroke count** — a rough proxy for difficulty.
- **Composition** — left-right characters, enclosures, singulars.
- **Channel** — everything that arrived in one import.

The radical and composition filters are where the corpus makes this better than
a flat character list. A parent can pull up every character built on 氵 and mark
the ones the child has met — grouping by structure rather than by frequency,
which is how the characters are actually related.

**Bulk operations:** select a filtered set and mark or unmark it in one action.
Undo an import as a unit. Export the known set as a plain list.

## Multiple profiles

Several children may share a device, so profiles are switchable — see the
storage section of *Known Characters and Review*. Switching is an unprotected
local selector, which suits the audience but requires care: the current profile
must be obvious at all times, and a bulk operation must never be able to land on
the wrong child's record without that being visible.

## What this is not

Not a score. The manager shows counts because they are useful for finding
things, not to be maximised. There is no target, no percentage of the corpus to
complete, and no ranking.

Not a curriculum. The app does not decide which characters ought to be learned
next. It shows what is known, what is in progress, and what a given article
would require — and leaves the sequencing to the adult and the material.
