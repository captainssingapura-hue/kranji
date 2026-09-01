# The Known Set

The app measures one thing: **which characters this child reads without pinyin.**

Not a learning model. We do not teach, schedule, or assess. A child reads; the
annotation gets out of the way for what they already know. That single fact is
what changes the page, and nothing else needs to be recorded to change it.

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
channel     where the claim came from
batch       which bulk operation it arrived in, when it arrived in one
```

**Provenance survives the cut, for one reason:** a bulk import has to be
undoable as a unit. A parent who imports eight hundred characters on the
strength of "she finished first grade" and finds it optimistic should be able to
take back that import, not unpick it one character at a time.

The earlier design also argued provenance because channels differ in
*reliability* — practice being better evidence than an import. That argument
belonged to the learning model and goes with it. A character is in the set or it
is not; we do not grade the claim.

## Channels

| Channel | Where |
|---|---|
| `ZI_DETAIL` | the Add control on the character's own page |
| `MANAGER` | the known-set review |
| `IMPORT` | a bulk list |
| `SEED` | initial setup for a reader who is not a beginner |

`PRACTICE` is gone with the learning model. `READER` is gone because marking no
longer happens in the reader — see *Marking a Character Known*.
