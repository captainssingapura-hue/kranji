# Markdown Articles

*A design, not an implementation. The constructs an article may use are specified
separately, in **The Markdown Kranji Reads**; this is the shape of the thing that
reads them. Written against one real document —
`CS2地图简介-Italy详细版.md`, 3,232 characters — and every number below is
measured from it rather than estimated.*

## Why the current format has a ceiling

An article is a `.txt` file whose blocks come from line structure: a blank line
ends a block, one line is a paragraph, several lines are a verse. That format
has nothing to learn, which is its whole virtue, and it is why 608 articles
exist.

It also cannot express a document. No headings, no lists, no structure a reader
can navigate. That is fine for a poem and a wall for anything longer than one
sitting.

Markdown is how content actually arrives — written by a person, produced by a
model, pasted from somewhere. The question is not whether to accept it but
**which markdown**, and what happens to the parts we cannot draw.

## What the document measures

| | |
|---|---|
| Han characters | 2,310 (536 distinct) |
| **Outside the corpus** | **0** — every character is servable today |
| Polyphonic occurrences (readings guessed) | **553** |
| Markdown used | 15 headings, 11 bullets, 3 numbered, 2 blockquotes, 9 rules, 24 bold runs |
| Latin runs | 37, longest `Apartments` |
| Longest source line | 183 characters |

Two of those numbers decide the design.

**0 outside the corpus** means this document is servable *now*. Nothing about
the reading machinery needs to change to carry technical prose; the corpus is
wide enough. The obstacle is entirely structural.

**553 guessed readings** is the real cost. A typical article carries about 36,
and finding the one wrong reading among them (地 in 地方) took a careful pass
over all 36. Nobody reviews 553. If that number stands, the warning report
becomes decoration and the guarantee behind it quietly stops being true.

## Segmentation is the load-bearing idea

A long document should not be one article. It should be a **tree of segments**,
each the size of a thing somebody sits down to read.

Where to cut, measured three ways against the library's own distribution.

> **These figures were wrong once and are worth not getting wrong again.** An
> earlier revision quoted them 2.6× too large, because `wc -m` counts *bytes*
> unless the locale says otherwise and a Han character is three of them. Every
> number here is now characters, cross-checked against the parser's own count.
> The conclusions did not change — the ratio between a segment and an article
> is what the argument rests on, and that survived — but "8,470 characters" was
> a byte count wearing a character's name.

| | segments | median | p90 | max |
|---|---|---|---|---|
| Whole document | 1 | — | — | **3,232** |
| Split at headings (`##`/`###`) | 14 | 185 | 299 | 461 |
| Split at paragraph blocks | 30 | 80 | 168 | 216 |
| **The published library** | 608 | **51** | **71** | 489 |

This separates two problems that look like one.

**Display.** The board builds roughly one cell widget per character — 462
measured for a 392-character article. The whole document is ~2,300 cells.
Splitting at headings fixes this outright: the largest segment is 461,
already under the 489 that renders today. **For display, headings alone are
sufficient and no size policy is needed.**

**Reading session.** Here they are not. A median segment of 185 is 3.6× the
library's median of 51. That is not a broken page, it is a different kind of
sitting.

### What it actually did

`Segments` is built and the figures above were estimates worth checking. Run
over the same document, now that the subset requires its Latin to be wrapped:

| | leaves | median | p90 | max |
|---|---|---|---|---|
| Split at headings | **14** | 172 | 349 | 443 |
| Budget 70, at paragraph boundaries | **41** | **64** | 111 | 183 |

Fourteen sections against fourteen predicted, and forty-one leaves against
"roughly forty". The median lands at 64 against the library's 51 — a segment is
now the size of an article, which was the whole claim.

**One number moved and it is worth saying why.** The document measures 2,740
characters here, not 3,232. Both are right: 3,232 is the *file*, and 2,740 is
what the parser hands over — the difference is the markdown itself, the `##` and
the `**` and the readings inside `{…}`, none of which a reader ever sees. The
figures above are content.

**The max of 183 is the rule working, not failing.** It is a single paragraph
longer than the budget, and a paragraph is never cut open — see below.

So: **headings give the structure; a size budget is a separate policy on top.**
Split at headings first, then subdivide any section over budget at paragraph
boundaries — never mid-paragraph, because a paragraph is the unit the reader
already wraps. At a ~70-character budget the document yields roughly 40 leaves,
each the size of a real article. The budget wants to be a named constant with
its reason written down, the way `MAX_PER_COLLECTION = 30` already is.

Segmentation also makes the review burden tractable without reducing it: 553
guessed readings is still 553, but each unit becomes a normal-sized job of ~40,
and a partially-reviewed document can ship the sections that are done.

## The model already has the shape

`ArticleEntry` is sealed over two cases. A document is the third, and it
completes the set:

```
ArticleEntry (sealed)
├─ ArticleRef          one article                  a leaf
├─ ArticleUmbrella<C>  one work, several tellings   alternatives — pick one
└─ ArticleSeries       one work, several parts      sequence — read in order
```

Umbrella is **OR**; Series is **THEN**.

`ArticleSeries` holds `List<ArticleEntry>` rather than `List<ArticleRef>`, so a
`##` containing `###` nests with no special case, and `articles()` flattens
depth-first — which is exactly reading order. A heading with no prose of its own
(`## 四、关键区域` in the sample holds only `###` children) becomes a node with
no leaf, which the tree already draws.

`ArticleTreeGetAction` needs almost nothing: it already renders an umbrella as a
node with children. And because a series is *ordered*, "next part" finally
becomes meaningful in the reader in a way it never was for a shelf.

## The rule this collides with

> **Metadata is Java; the body is a resource. Listing therefore never parses.**

That property is load-bearing at 608 articles — it is why the whole library can
be listed without opening a file. Segments come *from* the file, so something
has to give.

**Resolve it with codegen, not runtime parsing.** A build step reads the `.md`
and emits the section catalogue as Java. Listing stays parse-free, authors keep
one file, and the repository already has `kranji-codegen` plus the seed-digest
precedent for *generated output must match its source or the build fails*.

### And the risk inside that

Ids are addresses and are immutable once published. If a segment's id derives
from its heading text, **editing a heading silently breaks a bookmark**.
Segment ids must be frozen at first generation and thereafter *checked* rather
than recomputed — otherwise the generator re-addresses the library every time
somebody improves a title.

## The subset

**Specified in [The Markdown Kranji Reads](#), and only there.** Every construct
with a verdict, the four extensions, and the rule that anything unlisted is an
error rather than a silent omission.

It is kept in one document on purpose. A subset described in two places is a
subset that will eventually be described two ways, and the one an author reads
will not be the one the parser implements.

The design consequence worth repeating here is the shape of the error policy:
**an error means the article does not serve.** That is a strong choice and it is
deliberate — a file accepted with its tables quietly missing is a page that is
not what was written, and the author has no way to find out. Warnings are
reserved for decoration that has no meaning in a practice grid.

## Headings are already built

`Block` is sealed over `Paragraph`, `Verse` and `Illustration`. Headings need a
fourth — but **not a new renderer**. A heading is a short line of Chinese that
should read as a phrase rather than as practice squares, which is exactly what
an article *title* is, and that already renders as per-character ruby,
adaptively, through `ReaderTitleModule`.

Headings are just more titles. Most of the rendering work is done, and done
consistently by construction.

Lists are closer to `Verse` — lines kept, no wrap — and may need only a marker
rather than a new block.

## The thing this document breaks

`ArticleScannerModule.flushPlain` pushes an entire non-Han run into **one
cell**: `Apartments` becomes a single square holding ten letters.

The whole published library contains exactly one Latin run and it is one
character long (`U字形`), and **no test covers a multi-letter run**. The sample
has 37.

This needs a decision before any technical content lands — one cell per run
(overflows), one cell per letter (`Apartments` = 10 squares), or Latin leaves
the grid and flows. It is a real, previously unexercised failure rather than a
matter of taste.

## Two smaller conflicts

**The `#` title versus the catalogue.** The current design keeps identity in
Java deliberately: *an article's identity does not live in content that is meant
to be editable*. A markdown `# ` line is a title. Keep Java as the source of
truth and **require the `#` line to match it**, checked at build — readable
file, one identity, no drift.

**Parse at serve time, not as a build step.** Chosen by file extension, in the
same `ArticleParser` family. A build-time `.md → .txt` conversion means two
files per article and authors editing the wrong one. This is separate from the
*catalogue* codegen above: the section index is generated, the body is not.

## Order of work

1. ~~**The markdown subset**~~ — done. `MdSubsetParser`, and the specification
   is *The Markdown Kranji Reads*.
2. ~~**The Latin rule**~~ — done, and it went further than "with a test": text
   that is not Chinese and not a mark is an **error** unless it is wrapped, and
   `Mark` is both the whitelist and the typed model the rules live in.
3. ~~**Segmentation**~~ — `Segments` builds the tree and the figures above are
   measured rather than estimated.
4. ~~**The generator**~~ — `ArticleGenerator` and `ArticleGeneratorMain`. A
   folder of `.md` becomes one JSON resource per section beside a catalogue in
   Java, so listing never parses and nothing parses markdown at request time —
   by the time a reader asks, there is no markdown left. It writes nothing while
   anything is wrong, because a half-generated library is worse than none.
5. ~~**Ids that survive**~~ — settled without a write-back. A section with no
   pinned `{#slug}` is a **build error naming the heading**, and the generator
   invents nothing. The write-back this note proposed would have been a build
   step editing sources; requiring the author to write the id once, and checking
   it for ever after, gets the same guarantee and leaves the file alone. The
   workbench shows which headings still need one.

### What the generator did not do

- **The reader cannot read the output yet.** `ArticleRef.resource` now names a
  `.json` for generated sections, and the reading path still expects `.txt`
  source lines it scans in the browser. Serving structure instead of text is the
  next piece, and it is where the format machinery has to leave
  `kranji-studio` — `MdSubsetParser` and friends live there because that is
  where they were needed first, not because that is where they belong.
- **The tree is flattened.** A `###` under a `##` becomes another article in the
  same ordered collection. `ArticleSeries` is what restores the nesting, and it
  is still the third case the sealed `ArticleEntry` wants.
- **It is a CLI, not a build binding.** Nothing runs it automatically. The
  digest precedent — *generated output must match its source or the build
  fails* — applies here and is not yet applied.
5. **A reviewed-readings ledger** — independent of all of this. Once 的 has been
   confirmed as `de` somewhere it is settled vocabulary, not a judgement; a
   checked-in ledger of confirmed `character:reading` pairs would collapse 553
   to the few dozen genuinely new pairs, and improves with every article.

## What this changes about the library

The sample is not for a six-year-old. Accepting documents of this kind gives the
library a difficulty range it does not currently have, and a shelf may want to
say who a piece is for.

It is also worth being honest about why this document is the sample: a child who
stalls on a four-line poem will read 2,300 characters about a map they play on.
Motivation beats level. That is the same observation that retired the
readability measure, and it is the argument for carrying content like this at
all.
