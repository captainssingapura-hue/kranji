# KI-010 — ArticleReadingsModule has no tests

**Severity:** medium · **Area:** reading app · **Status:** open

## What it is

`ArticleReadingsModule` is the module that decides **which reading a child
sees**. An article now carries only its text plus the readings its author
pinned; everything else this module looks up from `/syllable-map` and fills in.

It was deliberately built to be testable — the partition loader is injected
rather than hardcoded, precisely so it can run under GraalVM in ordinary JUnit
with no browser and no network:

```js
var readings = createArticleReadings({
    load: function (p) { return import('/syllable-map?p=' + p); }
});
```

That seam exists and is unused. The module shipped with no tests at all.

## Why it matters more than the line count suggests

The sibling module got this right. `ArticleScannerModule` has nine GraalVM
tests including a parity check that runs it and `Cells` over the real articles,
so the port cannot drift from the Java it came from. This module has the same
harness available and nothing written against it.

A wrong reading here is not a crash. It is a page that renders perfectly and
teaches a child the wrong pronunciation — the exact failure `NoInlineGlyphRule`
exists to prevent, arrived at from the other direction.

## What is uncovered

- **Partition arithmetic.** `((cp % 101) + 101) % 101` must agree with
  `SyllableMapGetAction.PARTITIONS`. Nothing checks the two constants match; if
  they diverge, every lookup silently misses and every character loses its
  pinyin.
- **Override precedence.** `fill` must leave an authored `cell.r` alone. That is
  the whole reason 地`{dì}` survives against a corpus principal of `de`. It is
  currently asserted only by my having looked at a screenshot.
- **Astral-plane characters.** `partitionsFor` uses `Array.from` to iterate code
  points. A regression to UTF-16 units would request the wrong partitions for
  anything above the BMP, and the corpus reaches U+2CE93.
- **Idempotence.** `ensure` must not re-pull a partition it holds, and repeated
  `fill` on the same cells must not change them. A band change re-renders, so
  `fill` runs many times over the same article.
- **Missing characters.** A character outside the 8,105 set must yield an empty
  annotation rather than `undefined` in the cell.

## Evidence

Verified by hand in the browser on 2026-09-01: 静夜思 rendered 地 as `dì`, and
大熊猫 rendered 只 `zhī`, 长 `cháng`, 熊 `xióng`. That is a real check, and it is
one check, run once, by a person.

## What would fix it

An `ArticleReadingsTest extends JsModuleTestBase` passing a stub `load` that
returns a literal `{ characters: {...} }`. No network, no browser, and it would
cover all five points above. Roughly the size of `ArticleScannerTest`.

Add one assertion that the JS constant equals `SyllableMapGetAction.PARTITIONS`,
the same way `ArticleScannerTest` pins its punctuation tables to `Cells`.

## Cost of leaving it

The demo set is seven articles that have been looked at. The risk arrives with
the eighth, and with any later edit to this module — which now has no way to
fail loudly.
