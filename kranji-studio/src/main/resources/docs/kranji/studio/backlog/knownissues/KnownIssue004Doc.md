# KI-004 — An unaudited gap in the typed corpus

**Severity:** medium · **Area:** corpus · **Status:** open, uninvestigated

## What it is

Two ways of counting the typed corpus disagree by roughly 320 glyphs, and
nobody has established which is right or where the difference lives.

## Evidence

What the library actually loads at runtime, via
`ZiLibrary.load(BasicSet)` plus `AllZiRecords`:

| | count |
|---|---|
| singular parts / Zi | 445 |
| composed Zi (distinct glyphs) | 2,087 |
| **distinct glyphs reachable** | **2,532** |

`AllZiRecords.ALL` holds 2,124 records against those 2,087 glyphs, so 37
characters carry more than one composition record. That part is understood.

What a raw scan of every `glyph()` literal across the per-class modules finds:

```
kranji-common-perclass-promoted   1,565
kranji-common-perclass              801
kranji-singulars                    549
kranji-singulars-perclass           525
kranji-core                          44
                        distinct   2,855
```

**2,855 distinct literals against 2,532 reachable glyphs.**

## What it costs to leave

Every coverage figure quoted for the typed layer is really "what the library
loads", and that has been stated carefully so far. But an unexplained 320-glyph
gap means nobody can answer "is this character in the corpus?" with confidence,
and any structural-coverage report built on it inherits the doubt.

## What would fix it

Establish which classes hold the unreachable literals and why. The likely
answer is staging and superseded per-class variants that were never registered
into `BasicSet` — `kranji-common-perclass` versus
`kranji-common-perclass-promoted` is the obvious suspect, as is `kranji-
singulars` versus `kranji-singulars-perclass`. If that is all it is, the fix
is a note rather than code.

It has not been checked. That is the issue.
