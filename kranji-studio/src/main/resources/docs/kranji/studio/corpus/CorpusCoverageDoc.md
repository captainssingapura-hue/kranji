# Corpus Coverage

Counted at runtime off the built registry. File counts overstate the corpus —
they include aggregators, synthetic intermediate nodes, and package
infrastructure — so these figures come from walking `AllZiRecords.ALL` and the
registered singular families.

## Headline

**2,532 distinct characters.**

| Population | Records | Distinct glyphs |
|---|---:|---:|
| SingularZi (独体字) | 445 | 445 |
| SingularPart (偏旁) | 110 | 110 |
| ComposedZi — hand-authored | 801 | — |
| ComposedZi — promoted | 1,323 | — |
| ComposedZi — staged | 0 | — |
| **ComposedZi total** | **2,124** | **2,087** |
| **Distinct Zi (singular + composed)** | | **2,532** |

Parts are listed separately because a bound radical is not a standalone
character.

## Reconciling 2,124 composed records against 2,087 glyphs

- **36** records are synthetic intermediate nodes — `SuoLock_Inner1`,
  `YingEagle_Inner2`, and similar. They carry a deliberately empty glyph because
  they name a subtree, not a character. Working as designed.
- **1** is a genuine duplicate. See below.

## Health signals

**Singular and composed populations partition cleanly.** Their glyph sets
overlap by zero — no character is registered as both a singular and a composed
record.

**Staging is empty.** The promotion pipeline is fully drained; nothing is
awaiting review.

## Known issue — one duplicate glyph

U+4ED7 (仗) is claimed by two records in `kranji-common-perclass`:

- `kranji.common.perclass.zh.ang4.ZhangBattle`
- `kranji.common.perclass.zh.ang4.ZhangLean`

This looks like one character split across its two senses — 仗 as *weapon,
battle* (仗剑) and as *rely on* (依仗). Sense is not part of a character's
identity, so one record is probably redundant.

## On the "2500" target

The 2,532 figure is what the registry contains, not a percentage against a
defined target. The `input/` directory holds per-stroke working files rather
than one authoritative list, so coverage-against-target is not currently
computable. Establishing a canonical target list would make that measurable.

## How to reproduce

Register the singular families into `BasicSet.INSTANCE`, then walk
`BasicSet.INSTANCE.components()` for singulars and parts and `AllZiRecords.ALL`
for composed records. Deduplicate on `character()`, and exclude the empty-glyph
synthetic nodes from the distinct-glyph count.
