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
| `**bold**` **over Chinese** | **drop, warn** | the text, unemphasised |
| `**bold**` **inside a plain run** | keep — **E3** | emphasised text |
| `*italic*` | as bold, by the same rule | |
| `` `text` `` | **reserved** | see E3 — not the plain-run marker |
| `[text](url)` | **error** | a link the reader cannot follow is a lie |
| `![alt](file)` | **error, reserved** | see below |
| `~~strike~~`, `==mark==` | **error** | — |

**Emphasis depends on what it is over, and that is the whole point of E3.**
Over Chinese it is dropped: a bold run in a square practice grid is one heavier
square, competing with the pinyin directly above it, and it says nothing. Inside
a plain run there is no grid and no pinyin — it is ordinary typography, where
bold over `Premier` means exactly what it means anywhere else. So it is kept.

Dropping silently is what we refuse, so the dropped case warns and names the
line. The sample document has 24 bold runs and this rule keeps the ones that
mean something.

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

### E3 — Plain runs

**A maximal run of non-Han characters is a plain run. There is no syntax for
it.**

```
警察出生点在 Tunnel 附近，属于 **Premier** 之外的图。
```

`Tunnel` is a plain run; so is `Premier`, and its bold survives. The scanner
already groups exactly this way — `ArticleScannerModule.flushPlain` emits one
unit per maximal non-Han run — so the concept exists, it simply had no name and
no width.

#### Why there is no marker

An earlier draft made this `` `Apartments` `` — backticks, borrowing markdown's
code span. **That was wrong, and the reason is worth recording.** A code span's
contents are *literal* by definition: CommonMark does no emphasis parsing inside
one. So `` `**Premier**` `` means the asterisks, and the very thing E3 is for —
keeping emphasis where emphasis still means something — is the one thing that
delimiter forbids.

Backticks stay **reserved**. They may later mean *treat this as plain even
though it is Han* — "do not practise this" — which is a real and different need.
Nothing is invented for it yet.

#### Width: a run is *n* squares

A plain run occupies `ceil(runWidth / cellWidth)` squares, measured from the
text rather than from its character count — `Apartments` is not ten squares, it
is however many its rendered width needs, which at a 62px cell is about four.

**Everything up to the final render works on this today.** Parsing, the token
model, segmentation, the census and the length count all treat a plain run as
one unit with a width; nothing about them waits on the grid.

The render does wait. `RelGridCellsModule` builds one `div` per column and
`RelGridLayoutModule` sizes them through `<col>` elements — there is no span or
merge anywhere in the grid, so a run cannot yet occupy the *n* squares it has
asked for. **That is a RelationGrid enhancement — merged cells — and it belongs
to the framework, not here.** Until it lands, a plain run draws in one cell; the
number it carries is correct in the model whether or not the renderer can honour
it yet.

#### What the model needs

`Token.Plain(String text)` carries no styling and no width. Both are needed:
emphasis inside the run, and the square count. That is the concrete model change
E3 asks for, and it is small — a `Plain` that carries marks and a width, or a
third token case beside `Zi` and `Plain`.

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

警察出生点在地{dì}图较低的一侧，靠近 **Tunnel** 入口。

- 市场
- 中路大街

```verse
石头街道，拱门，
晾着衣服的阳台。
```
```

One title checked against the catalogue, two pinned ids, one reading override,
a plain run carrying bold that survives because it is not over Chinese, a list,
and two lines whose breaks survive. Everything else in the file would be an
error or a warning, and the author is told which.
