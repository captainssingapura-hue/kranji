# Adaptive Pinyin

The core mechanic. A reading is shown only when this reader needs it.

## Four modes

| Mode | Shows pinyin for | For |
|---|---|---|
| `ALL` | every Han character | earliest readers; matches familiar 注音 material |
| `ADAPTIVE` | readings not in the known set | the default, and the reason the app exists |
| `ON_TAP` | nothing until tapped | self-testing — "do I actually know this?" |
| `NONE` | nothing | fluency check, or reading aloud to an adult |

Four modes rather than a toggle because they map to genuinely different
activities. `ON_TAP` is not "off" — the annotation is *available*, which changes
how willing a child is to attempt a hard sentence.

## The rule

For a cell carrying character `z` and reading `r`, with mode `m` and known set
`k`:

```
ALL       → annotate
NONE      → bare
ON_TAP    → bare, reveal on tap
ADAPTIVE  → annotate unless (z, r) is in k
```

**The test is on the pair, not the character.** The cell already knows which
reading it is showing — that is what the annotation says — so the test is the
pair it already has. Two occurrences of 行 in one article get different answers
when only xíng has been marked: 行走 loses its pinyin, 银行 keeps it. A
character-keyed test could not express that, and would withdraw support from the
harder of the two. See *The Known Set*.

There are no states. An earlier design had `UNSEEN → LEARNING → KNOWN` with
promotion and demotion; it was cut, and with it the question of when support is
withdrawn. A reading is in the set or it is not.

The rule is deliberately trivial. Everything interesting lives in how a reading
enters the set, which is the subject of the next document.

## Presentation

Annotations sit above the character, in the annotation row of the cell.

**Not ruby.** Ruby spreads its base characters apart to fit the annotation, so
spacing would depend on which readings this reader knows — the page would
re-space as a child learns, and the same article would have a different shape in
June than in January. That is the one thing this mechanic must not do. Each line
is a two-row table instead: annotations above, characters below, every column
one square. See the Reader phase, where ruby was tested and rejected on
evidence.

Three properties matter:

- **Line rhythm is stable.** Space for the annotation is reserved whether or not
  a given cell uses it, so toggling modes does not reflow the page. Text that
  jumps when a setting changes is disorienting for a child.
- **The annotation is visually subordinate.** Smaller and lighter than the
  character. The character is what is being learned; pinyin is scaffolding and
  should look like scaffolding.
- **Marking never reflows either.** Hidden with `visibility`, never `display`,
  so a cell keeps its height. This matters more here than for the manual modes:
  marking a reading changes every occurrence of it in the article, potentially
  several lines apart. If that reflowed, the child's place on the page would
  move as a reward for knowing something.

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
3. **It connects to what the reader already has.** If 青 is in the known set, say so. A
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
