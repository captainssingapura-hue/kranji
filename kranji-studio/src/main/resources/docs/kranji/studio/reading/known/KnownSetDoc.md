# The Known Set

The app measures one thing: **which readings this child reads without pinyin.**

Not a learning model. We do not teach, schedule, or assess. A child reads; the
annotation gets out of the way for what they already know. That single fact is
what changes the page, and nothing else needs to be recorded to change it.

## The key is (character, reading)

Not the character. 行 read xíng in 行走 may be secure while the same character
read háng in 银行 is not, and the entire job of this set is deciding whether to
show a reading — so **the reading is what has to be known**.

A character-keyed set gets this wrong in the direction that hurts: it would drop
the pinyin from 银行 the moment 行走 was learnt, withdrawing support from the
reading the child has *not* met. That is the exact failure the feature exists to
prevent, and it would be invisible — a page that renders perfectly and is
quietly harder than it should be.

The pair also makes a polyphonic character something a reader can be **partly**
through, which is what learning one actually looks like. 地 is `de` long before
it is `dì`.

**The cost is that the set is bigger than the character count, and that the two
numbers are different.** Anywhere a total is shown, both are shown: *2
characters, 3 readings*. Quoting either as the other would flatter or undersell
what a child has done.

### The key itself

```
codePoint : reading      e.g.  34892:xíng
```

The codepoint rather than the glyph, so the key is fixed-width ASCII and cannot
be broken by a surrogate pair. The reading in its diacritic form, because that
is what the corpus, the reader and the child all use. A set is a plain array of
these, which serialises unchanged into party state and into an export file.

## One set, not three states

An earlier design had `UNSEEN → LEARNING → KNOWN` with promotion and demotion.
It is cut. What remains is a set and a membership test:

```
in the set      →  no pinyin
not in the set  →  pinyin
```

Three states cost a rule for entering and leaving each one, and the awkward
state was `LEARNING`: a character got there by being *encountered*, which needs
a per-article progress record we do not keep, and which infers something from
the fact that a child's eyes passed over a word. That is guessing.

**Adding states later is additive.** A second set — characters being worked on —
does not change the meaning of this one, so nothing stored has to migrate. That
is the reason it is safe to leave out now rather than build for it.

## No timestamps

Nothing in the design reads one. There is no scheduling, no decay, no interval,
no streak. A character does not leave the set because a month passed; it leaves
when a person says so.

Storing a time we never read invites a feature that reads it — and the obvious
one is decay, which is the same inference-from-silence we just removed.

## What a mark carries

```
character   the zi
reading     which of its readings — the other half of the key
```

Nothing else. A mark is the pair, and the set is a list of pairs.

## Channel provenance is cut

An earlier design had every mark record the **channel** it came from —
`MARK_KNOWN`, `MANAGER`, `IMPORT`, `SEED` — and the **batch** it arrived in.
Both are gone.

They survived one earlier cut, and it is worth being clear about why they did
not survive this one.

The first argument for provenance was that channels differ in *reliability*:
practice is better evidence than a bulk import. That belonged to the
`UNSEEN → LEARNING → KNOWN` model and went with it. A reading is in the set or
it is not; we do not grade the claim.

The argument that remained was that **a bulk import has to be undoable as a
unit** — a parent who imports eight hundred readings on the strength of "she
finished first grade" and finds it optimistic should be able to take that import
back, not unpick it one at a time.

That is true, and it is built. But it did not need channels. The import records
the keys it actually *added* and stores them beside the set, and *Undo import*
takes back exactly those. Per-mark provenance would have bought nothing the
batch does not already buy.

**What is lost, stated plainly:** only the most recent import is identifiable.
Import two hundred readings in September and fifty in November, and November's
are undoable while September's have become indistinguishable from readings the
child earned. Nothing can tell them apart afterwards, so nothing can review or
withdraw them.

That was judged an acceptable limit rather than a defect. Two imports into one
profile is not the common case, the undo exists for the mistake that is caught
soon after it is made, and the alternative costs the shape of the set, the
stored row, and the export format that families may already hold — see
*Profiles and Storage*. If it ever needs fixing, keeping a short stack of
batches rather than one slot gets most of the value and touches neither the set
nor the file.

## Where it lives while the app is open

A party Secretary, `knownSet`, beside navigation, character selection and
article selection. It differs from all three: those relay a selection and forget
it, this one *is* the state.

Every broadcast carries the **whole set** rather than a delta. A set a child can
manage is small, and carrying it whole means a pane that mounted late — or one
that was not listening when a reading was marked — is correct as soon as the
next change arrives. There is no replay and no resync to get wrong. A member
that has just joined asks (`WhatIsKnown`) rather than waiting for somebody else
to change something.

Nothing there persists: a Secretary's state dies with the workspace. Every pane
seeds it from the device at mount and Mark Known writes back — see *Profiles and
Storage*.
