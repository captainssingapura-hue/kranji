# Reading — Domain Model

Two independent trees. **Content** is what the article says; **profile** is what
this reader knows. They meet only at render time, and keeping them apart is what
lets one article serve every reader.

## Content

A piece is a sequence of blocks. Blocks hold tokens. Two sealed sums, both
small, and the renderer handles each exhaustively.

```
Article
├── id · title
└── blocks
      ├── Paragraph  ── tokens
      ├── Verse      ── lines ── tokens
      └── Illustration
```

| Block | Carries |
|---|---|
| `Paragraph` | a run of tokens |
| `Verse` | lines of tokens, kept as one unit |
| `Illustration` | a file, required alt text, an optional annotated caption |

| Token | Carries | Annotatable |
|---|---|---|
| `Zi` | the character, its reading **here**, whether that reading was authored | yes |
| `Plain` | punctuation, spaces, Latin, digits — carried through untouched | no |

`Verse` earns its place because 儿歌 and 古诗 are staples of what children read,
and a poem is one thing with four lines rather than four paragraphs. Flattening
it loses the grouping and the layout that goes with it.

`Illustration` sits between blocks, never inside one. A caption is Chinese, so
it is tokens rather than a string — a caption a child reads gets annotated like
any other text. Alt text is separate and always required: it is for a reader who
cannot see the picture, which is a different job from a caption.

### What changed from the earlier shape

**Sentences are gone.** They were a level between passage and token. Chinese
sentence boundaries are punctuation — 。！？ — so a stored sentence level is
structure the author has to maintain and the parser has to guess, in order to
hold something derivable. Anything that needs sentences (per-sentence
readability, say) can segment on demand.

**Four token kinds became two.** The original argument for splitting them was
that the renderer should never ask "is this annotatable" — the type should have
answered. A two-way split answers it completely. `PunctuationToken`,
`LatinToken` and `WhitespaceToken` never differed in any behaviour, so they were
three types and a classification step buying one bit that `Zi` already carries.

The split is now enforced rather than asserted: `ZiCharUTF8` refuses a non-Han
codepoint, so 。 and `3` *cannot* be constructed as annotatable. The invariant
lives in the type instead of in the parser's care.

**Illustrations exist.** The earlier model had no place for a picture, which a
children's reading app plainly needs.

## Readings are per-occurrence, not per-character

The detail that looks small and is not. 多音字 — characters with more than one
reading — are common in exactly the material children read:

| Character | Reading | Context |
|---|---|---|
| 行 | xíng | 行走 — to walk |
| 行 | háng | 银行 — bank |
| 了 | le | 好了 — aspect particle |
| 了 | liǎo | 了解 — to understand |
| 长 | cháng | 很长 — long |
| 长 | zhǎng | 长大 — to grow |

A per-character corpus lookup gives the *default* reading. If the reader is
shown `háng` over 行 in 行走, the annotation is not merely unhelpful — it teaches
something false.

So `HanToken` carries an **optional reading that overrides the corpus default**,
resolved when the article is prepared rather than when it is rendered. The corpus
supplies the default; the article supplies the truth for this occurrence.

This has a cost, and it should be stated plainly: articles cannot be dropped in
as raw text and rendered correctly. Preparation has to disambiguate 多音字. How
that happens — hand annotation, a segmenter with a dictionary, or a review step
over machine output — is an open question in the plan.

## Two tiers behind a token

`Zi` resolves against the corpus in two stages, and the token model does not
care which stage answered.

| Tier | Supplies | Coverage |
|---|---|---|
| **Phonic** (`SourceReadings` / `SyllableIndex`) | every reading, which is principal, frequency evidence | 8,100 — the whole standard set |
| **Structural** (`Zi` in `kranji-core`) | composition, etymology, strokes, radical | 2,532 — deep, curated |

Meaning is *not* in either tier. It is keyed on `(character, reading)` and lives
in its own store — see **CD-002**, which also records why a per-character
meaning field was removed rather than kept for convenience.

The simple tier is the floor, so **every character has a default reading**. An
article never has to supply a reading for a character merely because Kranji has
not modelled it structurally; it supplies *overrides*, and only where the
default is wrong.

A character present in the simple tier but not the structural one renders,
annotates, and shows its meaning on tap. It cannot explain its own composition —
that is the single feature that degrades, and it degrades quietly.

An earlier draft of this document put the fallback on the article author. That
was the wrong shape: it made the catalogue hostage to corpus growth. See *The
Simple Zi Layer*.

## Profile

```
LearnerProfile
├── name          a label on this device, not an account
├── knownSet      glyph → CharacterState
└── settings      pinyin mode, review preferences
```

`CharacterState` is a small sealed sum — `UNSEEN`, `LEARNING`, `KNOWN` — with
per-character counters for review scheduling. Details in *Known Characters and
Review*.

The profile keys on **glyph string**, not on a corpus record. A reader can know
a character Kranji does not model. Tying the profile to corpus identity would
make progress vanish when the corpus is refactored, which is unacceptable for
something a child accumulates over months.

## Where the two trees meet

```
Article  ×  LearnerProfile  ──▶  rendered page
```

Content stays immutable and shared. Profile stays local and private. Rendering
is a pure function of the pair, which means the same article serves a beginner
and a fluent reader with no separate editions, and a rendering bug can be
reproduced from a `(article, profile)` pair alone.

The same function, aggregated instead of rendered, gives readability — see
*Article Catalogue and Readability*.
