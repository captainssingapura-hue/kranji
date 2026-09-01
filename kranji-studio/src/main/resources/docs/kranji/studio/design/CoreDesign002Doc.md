# CD-002 — Meaning Belongs to a Reading

**Status:** decided · **Depends on:** CD-001

## The decision

Meaning is keyed on **`(character, reading)`**, never on the character alone.

It is **seeded from Unihan's `kDefinition`** and **corrected by a hand-maintained
override file** where the seed is wrong. The seed is provenance-marked, so a
gloss nobody has checked is distinguishable from one somebody has.

## Why the key is a pair

好 is *good* when it is hǎo and *to be fond of* when it is hào. 得 is *obtain*
as dé, a structural particle as de, and *must* as děi. A single `meaning` field
on a character cannot hold that, and a field that cannot hold the truth
eventually gets filled with a convenient part of it.

This is the same reasoning that removed `String meaning` from `SimpleZi`
earlier: a per-character meaning field is at the wrong cardinality, and it leaks
because callers cannot tell which reading the value belongs to.

## Why seed from a source with the wrong cardinality anyway

Because 93% of the time the cardinality question does not arise, and where it
does not, `kDefinition` is simply correct.

| | with a gloss | without |
|---|---|---|
| monophonic characters | **7,171** | 337 |
| polyphonic characters | 581 | 11 |
| | **7,752 of 8,100** | 348 |

For the 7,171 monophonic characters with a gloss, per-character and per-reading
are the same thing — there is one reading, so the gloss belongs to it
unambiguously. That is the great majority of the corpus, available immediately,
at no cost.

Waiting for a per-reading source is waiting for something that does not exist.
Every field in the vendored file was checked — `kCantonese`, `kFanqie`,
`kHanyuPinyin`, `kXHC1983`, `kTang`, and the rest. **None carries a per-reading
gloss.** The reading-to-sense mapping is simply not in this dataset.

## Where it strains

**581 polyphonic characters get one gloss that belongs to one reading**, and
Unihan does not say which. In practice it describes the principal reading and
silently omits the others:

```
好  hǎo hào              good, excellent, fine; well
      → hào (to be fond of) is missing

和  hé hè hú huó huò huo  harmony, peace; peaceful, calm
      → huó (knead), huò (blend), hè (join in singing) are missing

得  dé de děi             obtain, get, gain, acquire
      → the particle de, and děi (must), are missing

跑  páo pǎo               run, flee, leave in hurry
      → páo (to paw, to dig) is missing
```

These are exactly the characters a child most needs disambiguated, so the strain
falls where it hurts most. It is bounded and enumerable — 581 characters — which
is what makes the decision safe rather than reckless.

Two lesser strains. The glosses are **English and in a dictionary register**,
which is a real question for an app whose reader is a Chinese-speaking child;
they may end up as an authoring aid rather than something the reader ever sees.
And a few mix metadata into the meaning — 行 reads *"go; walk; move, travel;
circulate; Kangxi radical 144"*.

## What this obliges

1. **A gloss is stored against a reading**, even when seeded from a per-character
   source. The seed writes the same text against each reading of a polyphonic
   character, or against none — see the open question below.
2. **Provenance is carried.** `seeded` and `checked` are different states, and a
   view that shows a gloss should be able to tell them apart. Shipping an unchecked
   gloss as though it were verified is the failure mode worth designing against.
3. **The override file wins and survives regeneration**, exactly as the
   principal-reading override does in the Full Phonic Coverage plan. Whether they
   are one file or two is an implementation choice; the property is the same.

## Open

Whether a polyphonic character's seed should be **applied to every reading**
(fast, visibly wrong in places, easy to correct as it is noticed) or **withheld
entirely** (never wrong, 581 characters blank until somebody writes them).

Applying it and marking it unchecked is the current preference — a wrong gloss
that is labelled unchecked invites correction, while a blank invites nothing —
but this has not been settled, and the answer may differ between the authoring
tools and what a child sees.
