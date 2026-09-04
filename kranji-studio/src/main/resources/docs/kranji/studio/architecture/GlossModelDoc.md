# Gloss Data Model

A gloss answers one question: what does this character mean **when read this
way**. Three levels, and the middle one is the reason the tier exists at all.

```
        ZI                      SOUND                      SENSE
  ┌───────────────┐  1:n  ┌───────────────┐  1:n  ┌────────────────────┐
  │ codepoint  PK │──────►│ codepoint  PK │──────►│ codepoint      PK  │
  └───────────────┘       │ reading    PK │       │ reading        PK  │
                          └───────────────┘       │ meaning        PK  │
                                                  │ order              │
                                                  │ because       opt  │
                                                  └────────────────────┘
```

Every key is natural and composite: a level owns its parent's key and adds one
column. Nothing in the model carries a surrogate identifier.

| Level | Java type | Identified by |
|---|---|---|
| Zi | `ZiGloss` | the codepoint |
| Sound | `SoundGloss` | codepoint + reading |
| Sense | `Sense` | codepoint + reading + meaning |
| — | `Meaning` | its own text; a value, not an entity |

## The pair is the grain

`SoundGloss` is where the model does its work. A character does not have a
meaning; a character *read a particular way* has one. 好 hǎo is good, 好 hào is
to be fond of, and a per-character field can only be filled by merging the two
into something true of neither. CD-002 records that decision; this is its shape.

The identity is written out as a single string wherever it crosses a boundary:

```
codepoint : reading        →        24202:chuang2
```

The reading is the canonical numbered form — `di4`, not `dì`. It is ASCII apart
from `ü`, so it carries no combining marks and no two byte sequences that look
alike and compare unequal. The same key identifies a reading in the article
census and in a reader's known set, byte for byte, which is what lets the three
join without translation.

That agreement is load-bearing and silent when broken. A difference of one
character in the format does not throw; it makes every article read as nothing
known.

## Meaning is a value, Sense is a row

The distinction is easy to lose and worth stating.

`Meaning` is a short piece of English with a length cap — a value object,
compared by its text, with no identity beyond it. `Sense` is what that meaning
*is* within a particular reading: its rank, and its supporting material.

So a sound holds a map from `Meaning` to `Sense`. The meaning is the key; the
sense is the row. Two characters that happen to mean the same thing hold two
equal `Meaning` values and no shared record — meanings are near-unique in
practice, so interning them would buy almost nothing and cost a lookup on every
read.

## Order is authored, not declared

A reading's senses are ordered, and the order is the sequence they were written
in — recorded as the DSL runs and stamped into `RankingInfo` at `build()`.

| Field | Holds |
|---|---|
| `order` | position among its siblings; `0` is primary |
| `because` | optional note on why it ranks there |

Nothing declares a rank, so nothing can declare one that disagrees with the
order it was written in. The primary sense of a reading is simply the one
written first.

Note that rank is **within a reading**, never across a character. A reading's
only sense is that reading's primary sense, whether or not the character has
another reading that is commoner.

## Deliberately not in this model

Three things attach to the structure above and are described elsewhere, kept
out here so the spine stays readable:

- **Examples.** A sense cites phrases, which live in their own registry with
  their own English and their own per-position readings. They reference this
  model; they are not part of it.
- **The corpus.** Which readings a character *has* is the phonic corpus's
  answer, not this tier's. A gloss names a reading; it never invents one, and a
  validity check enforces that.
- **Provenance.** Every entry belongs to a source that names its licence, so a
  build's licence is a checkable fact. That is a property of the collection, not
  of the shape.
