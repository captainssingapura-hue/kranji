# The Markdown Kranji Reads

*The contract between an author and the reader. Not "markdown support" — a
named subset, plus four extensions that exist because the reader needs
something markdown has no way to say.*

The rule that makes it a contract: **anything not listed here is an error.** An
article containing it does not serve, and the author is told which line. The
alternative — accepting a file and silently dropping the parts we cannot draw —
produces a page that is quietly not what was written, which is the failure this
codebase refuses everywhere else.

`.txt` articles are unaffected, for ever. The file extension chooses the parser.

---

## The conflict you must know about first

> **In `.md`, a line break inside a paragraph means nothing.**

The `.txt` format says *one line is a paragraph and several lines are a verse*,
so a poem needs no markup at all. Markdown says the opposite: consecutive lines
are one paragraph, and every renderer in the world joins them.

We follow markdown, because a file that previews wrongly in every other tool is
not markdown. **A poem therefore needs a fence** — extension E4 below. This is
the one place where moving a `.txt` article to `.md` changes its meaning, and it
changes it silently unless you know.

---

## Block constructs

| Syntax | Verdict | Becomes |
|---|---|---|
| `# Title` | **required, exactly one, first line** | nothing — checked against the catalogue title |
| `## Heading` | keep | a segment, and a `Heading` block |
| `### Heading` | keep | a nested segment |
| `#### ` and deeper | **error** | — a document needing four levels wants splitting |
| Paragraph | keep | `Block.Paragraph` |
| ` ```verse ` | keep — **E4** | `Block.Verse`, lines preserved |
| `- item` | keep | a list item |
| `1. item` | keep | an ordered list item |
| `> note` | keep | an aside |
| `---` | **drop, warn** | nothing; headings already separate |
| Setext (`===` / `---` underline) | **error** | one way to write a heading |
| `*` or `+` bullets | **error** | one way to write a bullet |
| Nested lists | **error** | a list of lists is a structure the grid cannot show |
| Tables | **error** | — |
| ` ``` ` with any other info string | **error** | — |
| Raw HTML | **error** | — |
| Footnotes, definition lists | **error** | — |

## Inline constructs

| Syntax | Verdict | Becomes |
|---|---|---|
| `字{dì}` | keep — **E1** | the reading of that character |
| `` `text` `` | keep — **E3** | a plain run that flows |
| `**bold**` | **drop, warn** | the text, unemphasised |
| `*italic*` | **drop, warn** | the text, unemphasised |
| `[text](url)` | **error** | a link the reader cannot follow is a lie |
| `![alt](file)` | **error, reserved** | see below |
| `~~strike~~`, `==mark==` | **error** | — |

**Why emphasis is dropped rather than rejected.** It is too common to reject —
the sample document has 24 bold runs — and meaningless to render: a bold run
inside a square practice grid is one heavier square, competing with the pinyin
directly above it. Dropping it silently is what we refuse, so it drops with a
warning naming the line.

**Images are reserved, not forgotten.** `Block.Illustration(file, alt, caption)`
and `ImageRef` already exist in the model and are unused. When images are
accepted, `![alt](id)` will map to them and the id will have to resolve to an
`ImageRef` the collection declares — checked, not guessed. Until then it is an
error, so that nobody writes one and wonders where it went.

---

## The four extensions

Each exists because the reader needs something markdown cannot say. None is
decoration.

### E1 — Phonic override: `字{dì}`

A reading, on the character it corrects. **Identical to the `.txt` format, and
that is the point**: two formats with two override syntaxes would be a
permanent tax on every author and every tool. 608 articles already use this one.

```
这是一个读中文的地{dì}方。
```

Both forms are accepted — `{dì}` and `{di4}` — and the reading is checked
against the corpus. A reading that character does not have is an error, not a
silently wrong sound.

> **Known collision.** Pandoc and kramdown use `{...}` for attributes. We accept
> the clash rather than migrate 608 articles. The disambiguation is positional
> and already enforced: a `{` that does not immediately follow a Han character
> is an error — *"an override must follow a character, not stand alone"* — which
> the parser has said since before markdown was on the table.

### E2 — Pinned segment id: `## Heading {#slug}`

```
## 三、整体结构 {#zheng-ti-jie-gou}
```

**This exists to stop bookmarks rotting.** A segment is addressable, and an
address is immutable once published. If a segment's id were derived from its
heading text, improving a title would silently break every link to it. Pinning
the id lets the heading change freely for ever after.

The `#` inside the braces is what tells this from E1, along with its position:
end of a heading line, not after a character. Optional on first write —
generated once from the heading, then written back into the file and thereafter
checked rather than recomputed.

### E3 — Plain run: `` `Apartments` ``

Backticks mean **"this is not Chinese to be practised — let it flow"**.

```
警察出生点在`Tunnel`附近。
```

This is the one extension that solves a bug rather than adding a capability.
`ArticleScannerModule` puts an entire non-Han run into a single cell, so
`Apartments` becomes one square holding ten letters. The published library has
exactly one Latin run and it is one character long (`U字形`), and no test covers
the multi-letter case; the sample document has 37 of them.

Marking a run plain says explicitly that it leaves the grid, rather than leaving
the scanner to guess from a rule nobody has exercised. It also gives inline code
a real meaning instead of a rejection.

### E4 — Verse fence

```
```verse
床前明月光，
疑是地{dì}上霜。
```
```

Lines preserved exactly. Required for anything whose line breaks carry meaning —
poems, 儿歌, 绕口令 — because markdown proper would join them into a paragraph.

`verse` is the only info string accepted. Any other fence is an error, which is
what keeps this from being a hole through which arbitrary code blocks arrive.

---

## Errors and warnings

**An error means the article does not serve.** The reader shows *"did not
parse"* rather than a page missing something the author wrote. Errors are for
structure we cannot represent — a table, a link, a fourth heading level — and
for anything factually wrong, like a reading the character does not have or a
`#` title disagreeing with the catalogue.

**A warning means it served, with something dropped.** Warnings are for
decoration with no meaning in a practice grid: emphasis, and `---`. They land in
`target/article-warnings.txt` beside the guessed readings, which is the file an
author already has to read.

The two categories divide on one question: *would serving this misrepresent what
the author wrote?* A dropped bold run does not. A dropped table does.

---

## A worked line

```markdown
# CS2 地图详解：Italy {#cs2-italy}

## 三、整体结构 {#zheng-ti-jie-gou}

警察出生点在地{dì}图较低的一侧，靠近`Tunnel`入口。

- 市场
- 中路大街

```verse
石头街道，拱门，
晾着衣服的阳台。
```
```

One title checked against the catalogue, two pinned ids, one reading override,
one plain run that leaves the grid, a list, and two lines whose breaks survive.
Everything else in the file would be an error or a warning, and the author is
told which.
