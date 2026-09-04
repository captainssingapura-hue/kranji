# Article Preparation

The open decision (r3), stated properly.

## The problem

To annotate a character you need its reading *in this sentence*. The corpus
gives a per-character default, and for polyphonic characters — 多音字 — the
default is wrong often enough to matter. These are not exotic; they are among
the most common characters children read.

| Character | Readings | Disambiguated by |
|---|---|---|
| 行 | xíng / háng | 行走 vs 银行 |
| 了 | le / liǎo | 好了 vs 了解 |
| 长 | cháng / zhǎng | 很长 vs 长大 |
| 大 | dà / dài | 大人 vs 大夫 |
| 还 | hái / huán | 还有 vs 还书 |
| 觉 | jué / jiào | 觉得 vs 睡觉 |
| 得 | dé / de / děi | 得到 vs 跑得快 vs 得走了 |

A wrong annotation is worse than none. Showing `háng` over 行 in 行走 does not
merely fail to help — it teaches a false reading, with the app's authority
behind it. This is the one place in the app where being wrong is actively
harmful, so it deserves a decision rather than a default.

## What disambiguates

Almost always the **word**, not the sentence. 银行 fixes háng; 行走 fixes xíng.
So the problem is largely word segmentation plus a word-to-reading dictionary —
a well-trodden problem with mature solutions, none of which are free of cost.

A residue genuinely needs sentence context or human judgement (得 is the usual
offender), and any honest approach has to surface that residue rather than
silently pick.

## Four paths

**A — Hand annotation.** The author supplies the reading for every ambiguous
occurrence.

*For:* highest quality, no new dependency, no infrastructure. Entirely feasible
at v1 scale — a few dozen short articles.
*Against:* does not scale, and quality depends on the annotator being careful
every time.

**B — Segmenter plus dictionary.** Segment into words, look up readings.

*For:* scales to any volume; near-zero marginal cost per article.
*Against:* a real dependency to choose, carry, and license. Segmentation errors
cluster in short, common, ambiguous text — which is exactly what children read.
It will be wrong sometimes and will not say so.

**C — Segmenter proposes, human confirms.** Machine annotates; a person reviews
only the ambiguous positions.

*For:* best quality-per-effort. Polyphonic characters are a small fraction of
any text, so the review load is a fraction of full hand annotation while
retaining a human check where it counts.
*Against:* still needs the dependency from B, plus a review tool.

**D — Avoid polyphonic characters entirely.** Only ship articles that contain
none.

Not viable, and worth stating so it is visibly rejected: 了, 的, 得, 大, 还, 行
appear in essentially any children's text. This would rule out the corpus of
material we exist to serve.

## Resolved — the source format

Article source is **plain text plus reading overrides, and nothing else**.

```
他去银行{háng}存钱。
今天天气很好，我们出去走走。
```

A `{reading}` follows a character **only where the corpus default is wrong**.
Everything else is the text as written. No word boundaries, no structured
wrapper, no per-token markup.

Consequences of that, stated plainly:

- **Diffs are readable.** An article is a paragraph of Chinese, not a tree of
  JSON. Someone reviewing a pull request can read the story.
- **The burden is proportional to genuine ambiguity.** 了 as `le` needs nothing;
  只 了解 gets `了{liǎo}`. Most sentences carry no annotation at all.
- **Line breaking gets slightly worse.** Without word boundaries a line may
  break mid-word. Chinese tolerates this and CSS handles the common case; the
  cost is a occasionally awkward break, not a correctness problem.
- **Readability and the almost-ready view are unaffected.** Both are
  character-level, not word-level. An earlier draft of this document claimed
  they needed word boundaries; that was wrong.

## Resolved — who fills the overrides

**Hand annotation for v1.** A few dozen short articles is hours of work, and it
avoids committing to a segmentation dependency before the catalogue's shape is
known.

The format is chosen so this can change later without reworking content: moving
to machine-proposed annotation changes only what *fills* the overrides, never
the file format or the guarantee. That migration touches tooling, not articles.

## The polyphony baseline

Dropping word boundaries removes the validator's ability to *prove* a reading
from context. It cannot see that 银行 fixes `háng`, because it cannot see that
银行 is a word. So the guarantee has to come from somewhere else.

The mechanism is the one the framework already uses for conformance findings: a
**committed baseline of reviewed occurrences**.

Preparation scans each article for polyphonic characters and, for each
occurrence, records a fingerprint — the character, its resolved reading, and
enough surrounding context to identify the position:

```
xiaogu.txt  行  háng   他去银行{háng}存钱。
xiaogu.txt  了  le     天亮了，鸟儿开始唱歌。
xiaogu.txt  得  de     他跑得很快。
```

That file is committed and reviewed like any other source. Then:

- An occurrence **in the baseline** is one a human has already signed off.
- An occurrence **not in the baseline** fails the build.

Editing an article changes its fingerprints, so the new occurrences surface as
build failures and get reviewed. Nothing new reaches a child without someone
having looked at it.

This is weaker than proof and stronger than trust. It cannot stop an annotator
approving a wrong reading, but it makes it impossible for an ambiguous
occurrence to reach the reader **without ever having been considered**. That is
the property actually worth having, and it is the same bargain the conformance
gate makes: findings are graded against what has been reviewed, and only new
ones break the build.

The validator needs one input: a list of polyphonic characters and their
readings. Small, stable, and worth owning directly rather than pulling from a
dependency.

## Still to settle

- **Where preparation runs.** A build-time step producing prepared artifacts, or
  parsing at load time in `-content`. Build-time is preferred — it is what makes
  the baseline check a build failure rather than a runtime warning.
- **Baseline granularity.** One file per catalogue, or one per article.
  Per-article keeps diffs local to the story being edited; per-catalogue is one
  file to review. Leaning per-article.
