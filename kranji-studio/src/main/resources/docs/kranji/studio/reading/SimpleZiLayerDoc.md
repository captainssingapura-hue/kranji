# The Simple Zi Layer

Two tiers of character data in `kranji-core`, with very different costs and very
different coverage.

## The problem with one tier

A full Kranji record carries composition, etymology, stroke count, radical,
layout politeness, and typed slots referencing other records. That depth is what
makes the composition hint possible, and it is expensive — 2,532 characters
accumulated over a long time.

The reader needs far less for its main job. To annotate a character it needs the
sound and, for the tap panel, the meaning.

Tying annotation to full-record coverage means a character Kranji has not
modelled cannot be annotated at all, which pushes the burden onto the article
author and makes the catalogue hostage to corpus growth. That was the earlier
design and it was the wrong shape.

## `SimpleZi`

```java
public record SimpleZi(
        String glyph,
        PinyinSyllable defaultPhonic,
        List<PinyinSyllable> additionalPhonics,
        String meaning
) { }
```

The default is a **field, not a position**. An ordered list whose first element
is special relies on a convention every reader and writer has to remember;
making it structural means the compiler carries it instead. `defaultPhonic` is
what the reader shows when an article supplies no override, and there is no way
to construct a `SimpleZi` that forgot to say which reading that is.

`additionalPhonics` is empty for the large majority of characters. When it is
not, the character is polyphonic — and that is the whole definition:

```
polyphonic(glyph)  ⟺  !simple(glyph).additionalPhonics().isEmpty()
```

Phonics are `PinyinSyllable`, never `String`. The existing type already
decomposes into `Initial`, `Final`, and `Tone`, carries all five tones including
neutral, parses from text, and renders back as either diacritics (`háng`) or
numbered form (`hang2`). Nothing new is needed there.

## Two tiers, one core

| | Simple | Structural |
|---|---|---|
| **Holds** | phonics, meaning | composition, etymology, strokes, radical, layout |
| **Type** | `SimpleZi` record | `Zi` / `SingularZi` / `ComposedZi` |
| **Cost to add** | minutes | hours |
| **Coverage goal** | everything a child might read | deep, curated |
| **Enables** | annotation, tap panel, readability | the composition hint |

Both live in `kranji-core`. The simple layer is not reading-specific — any tool
wanting a sound for an arbitrary character needs it, and the structural records
can be validated against it. Placing it in the reading app would strand it there.

They are not competitors. The structural tier is a strict enrichment of the
simple one, and where both describe a character they must agree.

## Composing with the structure-driven family

The two tiers are designed to work together, and the shape of that composition
is what makes this more than a lookup table.

**Fixing the single-reading limitation.** `Zi.pinyin()` returns one
`PinyinSyllable`, and nothing in the structural hierarchy models more than one.
The corpus therefore cannot represent a polyphonic character at all — 行 has a
single reading in Kranji, and whichever it is, it is wrong about half the time.
The simple layer holds the full phonic set without any change to the sealed
hierarchy; a structural record can defer to it rather than duplicate it.

**Resolution order.** A lookup returns the structural record when one exists and
the simple record otherwise. Callers that only need sound and meaning can read
the simple tier directly and ignore the distinction entirely.

**Consistency as a test.** Where both tiers describe a character, a test asserts
that the structural `pinyin()` equals the simple `defaultPhonic()`. Disagreement
is a bug in one of them, and the build should say which characters disagree
rather than leaving it to be discovered by a child.

**A clean separation, eventually.** Structure and sound are different concerns,
and the corpus currently mixes them — a `ComposedZi` declares both how it is
built and how it is read. Letting the structural record own composition and the
simple layer own phonics would remove that duplication.

There is a pleasing symmetry in that: 形声 characters are themselves the union
of a form component and a sound component, and the two-tier corpus splits along
the same seam the writing system does.

## A Java DSL, not a data file

The entries are Java, written through a small DSL, and compiled with the rest of
the project.

```java
package kranji.simple.common2000;

import static kranji.pinyin.Initial.*;
import static kranji.pinyin.Final.*;
import static kranji.pinyin.Tone.*;

/** Characters whose default phonic begins with h-. */
public final class H {
    public static final List<SimpleZi> ENTRIES = List.of(
            zi("行", ANG, SECOND, "to go; a row").also(X, ING, SECOND),
            zi("航", ANG, SECOND, "navigate"),
            zi("好", AO, THIRD,   "good").also(H, AO, FOURTH),
            zi("和", E,   SECOND, "and; harmony")
    );
}
```

Why Java rather than a JSON resource — reversing the earlier shape:

- **The values are checked by the compiler.** `ANG` and `SECOND` are enum
  constants. A mistyped final is a build error at the line that has it, not a
  parse failure at startup or, worse, a silently wrong reading shown to a child.
- **No runtime parsing and no loading failure mode.** The data is the program.
- **It matches how Kranji already works.** The project's whole discipline is
  typed records over stringly-typed data, and `kranji-common-perclass-promoted`
  already carries 1,566 generated Java files. This is the same idiom at a
  cheaper granularity.
- **The tooling is free.** Autocomplete, find-usages, rename, and review in the
  same diff as everything else.

The cost is verbosity per entry, which the DSL absorbs: the initial comes from
the enclosing class, so only the final, tone, meaning, and any additional
phonics are written.

## Partition granularity — the arithmetic matters

The obvious layout is the one the corpus already uses:
`<initial>/<FinalTone>/`. Measured against
`kranji-common-perclass-promoted`, that gives **717 distinct syllable
partitions for 1,566 characters — 2.2 characters per syllable.**

Extrapolated, a per-syllable split would produce roughly 900 files averaging
two entries for the common 2,000, and around 1,100 files averaging four for
5,000. That is not a partition, it is a scatter — and it is the layout for
one-class-per-character records, not for a DSL whose whole point is grouping.

**One file per initial** is the right granularity:

| Tier | Files | Entries per file (mean) |
|---|---:|---:|
| common 2,000 | 22 | ~91 |
| common 5,000 | 22 | ~227 |

Twenty-two files — twenty-one initials plus `zero` for the zero-initial
syllables, matching the directory names the corpus already uses. Finals and
tones group *within* the file, ordered, so a reader scanning `H.java` sees
háng, hǎo, hé in a predictable sequence.

Two cautions:

- **Initials are not evenly distributed.** `l`, `sh`, `zh`, `j`, `x`, and the
  zero-initial group carry far more characters than `f` or `r`. The largest file
  at the 5,000 tier could hold several hundred entries.
- **Static initialisers have a 64KB bytecode limit.** A single file for
  everything would hit it; per-initial will not, but the biggest files are worth
  watching. If one grows past a few hundred entries, split it by final group
  rather than restructuring the whole scheme.

## Two frequency tiers

Build order is **common 2,000 first, then common 5,000**, and the split is
structural rather than merely a sequence:

```
kranji.simple.common2000.{b,c,ch,…,zero,zh}
kranji.simple.common5000.{b,c,ch,…,zero,zh}
```

with an aggregator over both, mirroring how `AllZiRecords` already composes
hand-authored, promoted, and staged populations into one view.

Keeping the tiers apart earns two things beyond sequencing. The 2,000 set can be
completed, tested, and used while the 5,000 set is still empty. And tier
membership is itself a difficulty signal — an article drawing only on the common
2,000 is genuinely easier than one reaching further out, which is a better input
to the catalogue's bands than raw length.

## The invariant worth having

A character's file location encodes its default phonic's initial. That is
checkable:

> For every `SimpleZi`, the initial of `defaultPhonic()` equals the initial its
> declaring class stands for.

A character filed in the wrong place fails the build. Combined with the
consistency test against the structural tier, the layer's two most likely
mistakes — wrong default, wrong home — both surface mechanically rather than in
front of a reader.

## What this changes downstream

**Every character can be annotated.** Annotation depends on simple coverage,
which is cheap to extend, rather than structural coverage, which is not.

**Degradation moves up a level.** A character with only a simple entry renders,
annotates, and shows its meaning on tap. It cannot explain its structure. That
is a far better floor than "the article must supply the reading".

**Article preparation gets simpler.** With a defined default phonic for every
character, the override-only source format works everywhere: the author
annotates exceptions, not unknowns.

**The polyphony list stops being a separate artefact.** Article preparation
needs to know which characters are ambiguous; that is now a query over
`additionalPhonics`. Adding a second phonic to a character automatically brings
every existing occurrence under review, because the baseline check expects one.
Data and validation stay in step by construction rather than by discipline.

**Coverage measurement becomes a query.** Which glyphs have a structural record
and which have only a simple entry is a filter, not a research project — and the
gap, ranked by how often each character appears in real articles, is the corpus
backlog.

## Sources

Phonics and meanings for common characters are well covered by open data —
Unihan carries `kMandarin` and `kDefinition` for essentially the whole CJK
block. Two cautions:

- **Licensing still applies.** Unihan has terms; they need reading before the
  data ships, not after.
- **Which reading is the default is an editorial judgement.** A dataset's
  first-listed reading is not always the one a child meets first. The default
  matters more than the set, and it deserves review for the characters that
  appear most.
