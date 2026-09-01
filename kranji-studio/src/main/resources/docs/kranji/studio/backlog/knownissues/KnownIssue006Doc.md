# KI-006 — Tone sandhi has no home

**Severity:** medium · **Area:** corpus model, reading app · **Status:** open, unowned

## What it is

一 and 不 change tone by context — 一 is *yī* alone, *yí* before a fourth tone,
*yì* before others; 不 is *bù* but *bú* before a fourth tone. This is **one
reading whose tone shifts**, not several readings.

Decision `pc1` deliberately keeps sandhi out of the catalogue. Using `kXHC1983`
would have filed 一 under three different syllables and told a child it was
polyphonic when it is not. That decision is right and stands.

But excluding it from the catalogue does not make it go away. **Nothing
downstream implements it, and nothing owns it.**

## Evidence

`kXHC1983` supplies these forms for the commonest characters:

```
一  standard=[yī]    kXHC1983 adds=[yí, yì]
不  standard=[bù]    kXHC1983 adds=[bú]
七  standard=[qī]    kXHC1983 adds=[qí]
```

The catalogue correctly holds only the first column.

## What it costs to leave

An article containing 不是 will be annotated *bù shì* when a child reading
aloud should say *bú shì*. 一 and 不 are among the most frequent characters in
Chinese, so this is not a rare-character problem — it will show up in the first
article.

## What would fix it

Sandhi is a property of a character **in a position**, not of a character. It
belongs in the reader's annotation layer, computed from the following
syllable's tone at display time — not in the catalogue and not in the article
markup, since it is derivable rather than authored.

The `{háng}` per-occurrence markup scheme handles genuine polyphony. Sandhi
needs the opposite treatment: automatic, invisible, and never hand-written.

## Related

Deferred by decision `pc1`. No plan phase owns it yet — that is the gap.
