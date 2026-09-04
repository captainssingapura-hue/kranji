# CD-001 — The Character as Hub

**Status:** adopted
**Applies to:** the corpus, the reading app, and any catalogue over either

## The decision

`ZiCharUTF8` — a single Han codepoint — is the identity everything joins on.
Every other dimension is a **projection** over that hub, and a projection is a
**secondary index**: a total function from the character to a key, plus the
posting list that function induces.

```
                    readings (SimpleZi)
                            │
        composition ────  ZiCharUTF8  ──── known state
        (structural Zi)     │   │           (learner profile)
                            │   └── article occurrences
                       meaning
                  (character, phonic)
```

Dimensions join on the hub and never on each other.

## How it happened

Not by plan, which is worth recording because it is evidence the shape is real
rather than imposed.

Three separate decisions pushed in the same direction. The two corpus tiers were
joined **by glyph rather than by reference**, so structural coverage could grow
without either side being edited. The learner profile was keyed **on glyph
rather than on a record**, so a child's months of progress would survive a
corpus refactor. Then `meaning` was removed from `SimpleZi` because it sits at
the wrong cardinality — and in leaving, it became a spoke rather than a field.

Each was argued on its own terms. The hub is what they add up to.

## Projections are indexes

A projection catalogue is: intermediate nodes are the distinct values of an
attribute, and the leaves beneath each are the characters having that value.
That is precisely a secondary index — key to posting list. Browsing
`/cat/zi/radical/shui` *is* the lookup `radical = 氵`.

The isomorphism is useful rather than decorative:

- **It says how to build one.** In the domain module a projection is a pure
  function `ZiCharUTF8 → K` and the `Map<K, List<ZiCharUTF8>>` it induces. No
  framework, no storage, recomputable, testable with plain JUnit.
- **It says what a valid projection is.** Any total function from the hub to a
  key. A better criterion than "seems like a useful way to browse".
- **It says what a projection must not be.** A place where data lives. A
  projection owning a fact that is not derivable from the hub is a bug, not a
  feature.
- **It explains the addressing tension.** An index freely places one record in
  many indexes; a catalogue tree wants one position per navigable. Carrying the
  projection in the leaf's parameters is exactly *making the index name part of
  the key* — a principled resolution rather than a workaround.

## Where it strains

Recorded because a decision without its limits gets applied where it does not
fit.

**Multi-valued keys.** Kangxi radical is single-valued, so a key of
`(character, projection)` suffices. *Component* is not: 清 contains both 氵 and
青 and belongs in two posting lists. Under one-position-per-navigable the bucket
must join the key — `(character, projection, key)`. Since browsing by component
is among the more valuable things a structural corpus can uniquely offer, the
parameters should carry the bucket from the start rather than gain it later.

**Identity must carry the projection at every level, not only at the leaf.**
Two projections that each group by some key `h` would mint the same identity
for that partition node, and one identity cannot occupy two positions — so
the second projection fails at boot rather than at render. Identity is
therefore `(projection, key)` throughout the tree, which is why a projection
is carried as a named pair rather than as a bare root whose segment happens
to say so.

**Partial dimensions.** A projection is only as total as its data. Stroke count
lives on the structural tier alone — the simple tier has no such field — so a
by-strokes projection covers the curated set, not the whole collection. That is
acceptable; a silently short tree is not. Such a projection needs an explicit
*unknown* node or a stated omission.

**Compound rather than flat.** `initial → final+tone → character` groups on a
key *path*, so a catalogue is a compound index. Richer than the flat form, and
the reason ordering matters more here than in a database index: the path is a
navigation route, not only a lookup.

**Two hubs today.** The 2,642 existing records still key on `String`;
`ZiCharUTF8` was adopted for new code only. Until those converge there are two
identities describing the same thing, and the seam between them is where
mistakes will concentrate. The convergence is therefore not tidying — it is
completing this decision.

## Addressing follows identity, not display

A projection needs an address per node, and downstream addressing restricts
segments to a narrow ASCII charset. So a character's segment must be derived.

**It is derived from the codepoint** — `u597d` — and not from the reading.

The obvious objection is readability, and the obvious counter-proposal is
numbered pinyin, or the `ü → v` fold that Chinese input methods use (`nv3`),
which is ASCII, injective, and pleasant to read. Both are rejected, for two
reasons of unequal weight.

The lesser reason was correctness cost: our own numbered rendering emitted `nü3`
and `jüen1`, so an ASCII form meant reimplementing pinyin orthography — ü to v,
plus the written collapses `iou→iu`, `uei→ui`, `üen→un` — underneath a
permanent value.

**That reason has expired.** The numbered form was later adopted as the
system's internal representation of a reading, so it had to be made correct on
its own account: it now emits `jun1` and `liu2`, sharing its spelling rules with
the diacritic renderer, and the collapses are checked to round-trip across all
1,284 corpus syllables. Building an address from it would cost little today.

The decision stands unchanged, because it never rested on that argument.

**The real reason is stability.** A codepoint cannot change. A reading is data,
and data gets corrected. If the address is derived from a reading, then fixing a
wrong default phonic silently breaks every link to that character — the kind of
failure that is invisible at the moment it is introduced.

The general rule, which outlives this decision: **an address may be derived from
identity, never from corrigible data.** Display names are corrigible and should
be; that is why the two are separate fields on every tree node.

A by-pinyin route remains available later as its own projection, with its own
positions and parameters. That is lawful and is the right home for the idea.

## What follows from it

- A new dimension is added as a **projection function**, not as a field on an
  existing record.
- A fact belonging to more than one dimension is keyed on the hub, not stored
  twice.
- A fact belonging to a character *and* something else — meaning, which is per
  reading — is keyed on the pair, and this is expected rather than a violation.
- Catalogues over the collection are **derived views**, so no catalogue is a
  source of truth and none needs its own storage.
- Any projection must be expressible without the framework, since it lives in
  the domain module.

## Related

The reading design section covers the simple tier and the catalogue that first
made this shape explicit. The request to the Homing framework for a dynamic
catalogue facility rests on this document: if projections are indexes, then
generating a class per index bucket is a category error, which is the substance
of that ask.
