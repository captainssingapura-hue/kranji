# CF-001 — What the corpus holds

Measured 2026-09-02, against the 23 bundled demo articles.

Recorded because the team's working assumption — that the corpus knows a lot
about a few thousand characters — is right in outline and wrong in a way that
matters: the tiers differ by an order of magnitude, and *which* tier a character
falls in decides what any new widget can say about it.

## The four tiers

| Tier | Module | Size | Carries |
|---|---|---:|---|
| Phonic `SimpleZi` | `kranji-core` | **8,105** | glyph + readings |
| Singular `SingularZi` | `kranji-singulars` | **549** | glyph, meaning, pinyin |
| Components | `kranji-core` | **30** | glyph, 名, standalone, meaning, pinyin |
| Composed `ComposedZiT` | `kranji-common-perclass` | **800** | composition, 六书 etymology, strokes, radical |

Distinct characters with *something* structural: **1,101** by codepoint (the
tiers overlap; 800 + 579 with duplicates removed).

## Against what a child actually reads

The 23 bundled articles: **437 distinct characters, 1,408 Han tokens**.

| | Distinct | Occurrences |
|---|---:|---:|
| Has a structural or singular record | 224 (51%) | **834 (59%)** |
| Decomposable — has a composed record | 135 (31%) | **382 (27%)** |

**Occurrences matter more than distinct counts here.** The gap between 51% and
59% is the covered set being the *common* characters, which is the useful
direction: coverage is better than a headline "1,101 of 8,105" suggests.

But 27% is the number that governs a composition widget. On a page of a hundred
characters it has something to say about twenty-seven of them. That is worth
building and is **not** worth pretending otherwise: the empty state is the
common case, not the exception, and needs designing rather than apologising for.

## How to re-measure

The census endpoint carries every `codePoint:reading` in the library with
occurrence counts, and is pure ASCII — which sidesteps the UTF-8 handling that
makes this awkward from a shell:

```
curl -s http://localhost:8712/article-census \
  | grep -o '"[0-9]\{4,6\}:[^"]*": [0-9]*' | sed 's/^"//;s/:[^:]*": / /'
```

Composed-record glyphs are `\uXXXX` escapes in the generated sources; singular
glyphs are literal UTF-8. Both need decoding to codepoints before intersecting.

## What follows

- A composition widget is worth building at 27%, and its empty state is a
  first-class design problem rather than an edge case.
- Raising that number is `rp9` — populating the simple Zi tiers — which this
  finding gives a concrete target for.
- The reading app has **no dependency** on `kranji-common-perclass` or
  `kranji-singulars` today. Reaching the structural tier is an architectural
  decision, not UI work. See CF-003.
