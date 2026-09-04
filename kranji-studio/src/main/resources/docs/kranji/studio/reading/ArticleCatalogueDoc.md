# Article Catalogue and Readability

Finding something the child can actually read is half the problem. A story two
hundred characters beyond them is discouraging; one far below them is boring.

## Organisation

Articles form a catalogue tree, the same structure the studio itself uses:

```
Reading
├── 童话      folk tales and fables
├── 生活      everyday scenes
├── 自然      animals, plants, weather
├── 古诗      classical poems, short
└── 科普      simple science
```

Theme is the primary axis because it is what a child chooses by. Level is
metadata on each article rather than the top-level split — a browsing child asks
"what is this about", not "what grade am I".

## Readability

The computed number that makes the catalogue useful:

```
readability(article, reader) =
    known Han tokens in article ÷ total Han tokens in article
```

Reported as a percentage, alongside the count of distinct unfamiliar **readings**
— which is often the more useful figure. An article at 94% with eight distinct
new readings is a different proposition from 94% with one repeated forty times.
The second is a much better lesson.

### Counted by reading, not by character

Matching the known set. An article using 行 as háng asks nothing of a reader who
has learnt xíng, and folding the two together would report a readability the
child cannot actually achieve. See *The Known Set*.

### Computed in the browser, and it has to be

The known set never leaves the device, so the server **cannot** work this out —
and must not try. It ships the half that depends only on the article:

```
census(article) = { total, { "codePoint:reading": occurrences } }
```

A census is profile-free, so it is generated once for the whole library, cached
by URL, and intersected locally with a set the server has never seen. That is
also what makes the figures free to keep current: marking a reading re-ranks
every article without a request.

The whole bundled library is 16KB of census. Sending it per article, or
recomputing per profile change, would have made a catalogue that re-ranks as a
child learns too expensive to be worth having.

**The census key must stay byte-identical to the known set's.** The measure is a
set intersection; a difference of one character makes every article read 0% and
nothing throws. Pinned by test on both sides.

### Bands

| Readability | Band | Meaning |
|---|---:|---|
| ≥ 98% | **Comfortable** | read for pleasure; almost no support needed |
| 90–98% | **Just right** | a handful of new characters — the learning zone |
| 75–90% | **Stretch** | rewarding with an adult beside them |
| < 75% | **Too hard** | shown, but marked, and not recommended |

The 90–98% band is where the app should push readers. It is roughly the
established comprehensible-input range for extensive reading, and it is exactly
the band where adaptive annotation does its best work — enough support to keep
moving, not so much that the eye stops reading characters.

**Too-hard articles stay visible.** Hiding material a child cannot yet read
removes the thing that makes progress legible. An article marked too hard in
January and comfortable in June is the clearest evidence of progress the app can
offer.

## What the catalogue shows

Each article tile carries title, theme, length, readability for the current
reader, and the count of distinct new readings. Sorting defaults to *best fit
first* — nearest the middle of the just-right band, rather than simply highest
readability, which would trivially rank the easiest material top.

**As built**, every article in the tree carries its percentage as a badge and
its band plus readings-to-learn beside it, and the reader repeats the same
figures for the article it has open. Both come from one census and one known
set, so they cannot disagree.

Best-fit ordering is written and tested but **not wired to the tree**: a tree's
order is the curated one an author chose — 唐诗启蒙 before 儿歌, 静夜思 first
within it — and re-sorting by fit would throw that away. It belongs to a flat
listing view, which is where the almost-ready view belongs too.

One derived view worth building: **"almost ready"** — articles that would enter
the just-right band once a small number of specific characters are learned.
That turns the review queue from an abstract list into a route to a specific
story the child wants to read.

## Where articles come from

Articles are served through an **`ArticleSource` SPI**, with a bundled classpath
catalogue behind it as the default implementation.

v1 ships a small curated set as classpath resources: content is reviewable in
pull requests, preparation quality stays under our control, and nothing needs an
ingestion pipeline before anything can be read. Putting the SPI in front from
the beginning means a family-authored folder, a school's own collection, or an
imported pack can be added later without reworking the reader.

**Licensing applies to whatever a source serves.** The bundled catalogue is
limited to public-domain classical material and text written for the project.
The SPI does not launder provenance — a pluggable source is a place to put
content, not permission to redistribute it.

## Preparation is the real cost

Per the domain model, readings are per-occurrence: the default phonic is not
reliable in context for polyphonic characters. Article source carries an
override only where the default is wrong:

```
他去银行{háng}存钱。
```

Nothing else is marked up — no word boundaries, no structured wrapper. A
committed baseline of reviewed occurrences supplies the guarantee that no
ambiguity ever reaches a child unconsidered. See *Article Preparation*.

## Difficulty beyond readability

Readability answers "how much of this can this reader read". Two other signals
are cheaply available and worth showing beside it:

- **Distinct new characters** — often more informative than the percentage. An
  article at 94% with eight new characters is a different proposition from 94%
  with one new character repeated forty times. The second is the better lesson.
- **Frequency tier reach** — an article drawn entirely from the common 2,000 is
  genuinely easier than one reaching into the 5,000 set, independent of the
  reader's own known set. The simple Zi layer's tier split makes this a lookup.

Neither replaces readability; both stop it being read as a single score.
