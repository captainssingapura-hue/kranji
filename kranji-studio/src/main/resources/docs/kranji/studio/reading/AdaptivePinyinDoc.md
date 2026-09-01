# Adaptive Pinyin

The core mechanic. A character carries its reading only when this reader needs
it.

## Four modes

| Mode | Shows pinyin for | For |
|---|---|---|
| `ALL` | every Han character | earliest readers; matches familiar 注音 material |
| `ADAPTIVE` | characters not yet `KNOWN` | the default, and the reason the app exists |
| `ON_TAP` | nothing until tapped | self-testing — "do I actually know this?" |
| `NONE` | nothing | fluency check, or reading aloud to an adult |

Four modes rather than a toggle because they map to genuinely different
activities. `ON_TAP` is not "off" — the annotation is *available*, which changes
how willing a child is to attempt a hard sentence.

## The rule

For a `HanToken`, with mode `m` and profile `p`:

```
ALL       → annotate
NONE      → bare
ON_TAP    → bare, reveal on tap
ADAPTIVE  → annotate unless p.state(glyph) == KNOWN
```

`UNSEEN` and `LEARNING` both annotate. A character in `LEARNING` is one the
reader has met and not yet secured; removing support at that point is precisely
when it hurts. Support is withdrawn on promotion to `KNOWN`, not before.

The rule is deliberately trivial. Everything interesting lives in how a
character reaches `KNOWN`, which is the subject of the next document.

## Presentation

Annotations sit above the character, as ruby text — the convention children's
material already uses. Three properties matter:

- **Line rhythm is stable.** Space for the annotation line is reserved whether
  or not a given line uses it, so toggling modes does not reflow the page.
  Text that jumps when a setting changes is disorienting for a child.
- **The annotation is visually subordinate.** Smaller and lighter than the
  character. The character is what is being learned; pinyin is scaffolding and
  should look like scaffolding.
- **Switching modes never reflows.** Same consequence as the first point, worth
  stating as a hard requirement because it is easy to violate.

## The composition hint

Tapping a character opens what the corpus knows. This is the part no reader
without a structural corpus can offer.

```
清  qīng   clear

  LeftRight
  ├─ 氵   water     semantic
  └─ 青   qīng      phonetic

  You already know 青.
```

Three things are happening:

1. **The reading is explained, not just given.** 清 sounds like 青 because it is
   built from 青. The child is not memorising an arbitrary pairing.
2. **The meaning is grounded.** 氵 is water; 清 is about clarity of water. The
   composition is the etymology.
3. **It connects to what the reader already has.** If 青 is `KNOWN`, say so. A
   new character built from familiar parts is a much smaller ask than a new
   shape, and telling the reader that is most of the encouragement needed.

For characters outside the corpus, the panel shows reading and meaning only. The
feature degrades; it does not fail.

### Recursion is available and mostly should not be used

Composition trees nest — 清 contains 青, which decomposes further. The panel
shows **one level by default**, expandable on request. A child wanting to read a
story does not need the full tree, and depth here would turn a reading aid into
a lesson the reader did not ask for.

## What this is not

Not a dictionary. The panel answers "why does this character look like this"
rather than "what are its nine senses". A reader mid-story wants to continue.

Not a quiz. Tapping a character has no score attached. The purpose is to remove
an obstacle, and attaching consequences to asking for help is a good way to stop
children asking.
