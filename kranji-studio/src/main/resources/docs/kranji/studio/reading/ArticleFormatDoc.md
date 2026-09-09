# Reading — Article Format

Two formats, not one. What an author writes and what the browser receives are
different problems, and conflating them forces one of the two to be wrong.

The browser cannot resolve a reading — that needs the corpus, and the corpus is
8,100 characters. So **preparation resolves readings once**, and the wire
carries them already resolved. Same shape as everything else here: authored
`.tsv` → derived `SyllableIndex`, authored DSL → derived projection.

## The layering that makes input polymorphic

```
inline layer  — shared by every format:  Han text + {reading} overrides
block layer   — per format:              metadata, paragraphs, verse, images
```

`{}` is an **inline** concern. Every format shares it verbatim; formats differ
only in how they express blocks. That is what makes moving up cheap — the
sentence never changes:

```
他去银行{háng}存钱。
```

```
---
id: xiaoming-day
title: 小明的一天
---
他去银行{háng}存钱。

![一只猫](cat.png)
```

Rename, add a header, keep the body. Nobody rewrites content to gain structure.

Overrides accept **either** tone form — `{háng}` or `{hang2}` — because
`PinyinSyllable.parse` already takes both. An author needs no diacritic input
method. Uppercase and mid-string digits are rejected, and the validator says so.

## Input formats

```java
sealed interface ArticleFormat permits PlainText, Markdown {
    String extension();
    Article parse(String source);
}
```

**PlainText** — body only. Blank lines separate paragraphs. Title defaults to
the filename. No images. Rejects anything that is not text or `{}`.

**Markdown** — a strict subset: front matter, `#` heading, paragraphs,
`![alt](src)`. It **rejects** tables, links, emphasis and lists rather than
passing them through. That rejection is the strictness; permissive Markdown is
the thing being avoided.

Selection is by extension. A third format is a new `permits` entry, not a new
pipeline.

## The wire is the file

An ES module, like `/zi-data` and `/zi-detail` — but carrying the source, not a
parse of it:

```js
export const id     = "kranji.reader.demo.tangshi:jing-ye-si";
export const title  = "静夜思";
export const source = "床前明月光，\n疑是地{dì}上霜。\n举头望明月，\n低头思故乡。\n";
```

It sent tokens once — every character with its reading resolved — and the
reasoning was that the wire should be singular so that a new input format needs
no new renderer. The singularity was right and the level was wrong. A reading
the corpus knows is not the article's to state, and restating it froze a
principal into every article that used the character: correcting 地 in the
corpus would leave a thousand articles serving the old reading.

So the wire carries what only the article knows, which is its own text. The
server resolves an address, reads the resource and quotes it. `ArticleScanner`
in the browser splits blocks and squares — the block rule of `ArticleParser`,
the cell rule of `Cells`, both ported — and fills readings from the syllable
map it already holds.

Polymorphism still stops at the door: one format on the wire, and a format
whose entire structure is "a blank line ends a block, one line is a paragraph
and several are a verse" is small enough to be read in either language. That
the two agree is checked at build time, over the whole corpus, in
`ArticleScannerTest`. A serve-time parse could only reject a bad article once a
child had asked for it; a build-time one cannot ship it.

The client still decides what to display. It filters against its known-set from
IndexedDB, which keeps the adaptive rule where the profile lives and keeps an
article byte-identical and cacheable for every reader.

## Illustrations

Files beside the article, served by an action, referenced by name. Any ordinary
format — PNG, JPEG, SVG, WebP — passes through untouched.

Rejected alternatives: data URIs bloat every article load; external URLs break
the local-only, offline-capable story.

## Where "strict" lives

In the validator, producing findings exactly as `SourceFindings` does — not in
verbose syntax.

| check | severity |
|---|---|
| `{...}` attaches to a Han character | error |
| the reading parses | error |
| **the reading is one the corpus lists for that character** | error |
| every Han character is in the corpus | error |
| image file exists; alt text present | error |
| override equals the principal reading (redundant) | warning |
| polyphonic character with no override | warning |

The third earns its keep: `SyllableIndex` knows 行 reads xíng / háng / héng, so
`{hang2}` validates and `{hàn}` fails at preparation rather than in a child's
face.

Validation splits along the same seam as parsing — syntax rejection is per
format, semantic checking is shared and does not know which format produced the
article. That the semantic checks need no knowledge of the format is the test
that the seam is in the right place.

## Open

**Is prepared output committed or computed at serve time?** Leaning committed:
an article's resolved readings are exactly what is worth reviewing in a diff,
which is the same argument that made the phonic partitions committed.

**Un-overridden polyphonic character — warning or silence?** 好 in 好人 needs no
override; 行 in 银行 does. Nothing distinguishes them automatically, so a warning
is honest but noisy in proportion to how many polyphones appear.
