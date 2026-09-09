# The Markdown Kranji Reads

*The contract between an author and the reader. Not "markdown support" — a
named subset, plus five extensions that exist because the reader needs
something markdown has no way to say.*

The rule that makes it a contract: **anything not listed here is an error.** An
article containing it does not serve, and the author is told which line. The
alternative — accepting a file and silently dropping the parts we cannot draw —
produces a page that is quietly not what was written, which is the failure this
codebase refuses everywhere else.

`.txt` articles are unaffected, for ever. The file extension chooses the parser.

**And the extension is `.kmd`, not `.md`.** What this document specifies is not
CommonMark: it adds five things markdown has no way to say, and it *refuses*
tables, links, images and raw HTML rather than passing them through. A file
named `.md` tells everything downstream — an editor, a viewer, a linter, the
next person's script — that it is markdown, and each of them is then quietly
wrong about all of it. The cost is one line of editor configuration to get
highlighting back; what it buys is a name that is true.

---

## The conflict you must know about first

> **In markdown, a line break inside a paragraph means nothing.**

The `.txt` format says *one line is a paragraph and several lines are a verse*,
so a poem needs no markup at all. Markdown says the opposite: consecutive lines
are one paragraph, and every renderer in the world joins them.

We follow markdown, because a file that previews wrongly in every other tool is
not markdown. **A poem therefore needs a fence** — extension E4 below. This is
the one place where moving a `.txt` article to `.kmd` changes its meaning, and it
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
| `‹text›` | keep — **E3** | optional; marks where `*italic*` applies |
| non-Chinese, **unwrapped** | keep | one square per character, like everything else |
| `**bold**` | keep — **E5** | emphasis, `strong` |
| `*italic*` **inside `‹…›`** | keep — **E5** | emphasis, `em` |
| `*italic*` **anywhere else** | **drop, warn** | the text, unemphasised |
| an unpaired `*` | **warn** | the star, as written |
| `` `text` `` | **error** | not a construct here; see E3 |
| `[text](url)` | **error** | a link the reader cannot follow is a lie |
| `![alt](file)` | **error, reserved** | see below |
| `~~strike~~`, `==mark==` | **error** | — |

**Emphasis depends on which mark, not on where — see E5.** Bold works over
Chinese and italic cannot, for a reason about typefaces rather than about
permission.

**Images are reserved, not forgotten.** `Block.Illustration(file, alt, caption)`
and `ImageRef` already exist in the model and are unused. When images are
accepted, `![alt](id)` will map to them and the id will have to resolve to an
`ImageRef` the collection declares — checked, not guessed. Until then it is an
error, so that nobody writes one and wonders where it went.

---

## The five extensions

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

### E3 — Non-Chinese run: `‹…›`

**`‹` U+2039 and `›` U+203A** bracket a run that is not Chinese to be practised.
Everything between them is ordinary typography.

```
警察出生点在‹Tunnel›附近，属于‹**Premier**›之外的图。
```

The delimiters are markup: they do not appear in the reading. Inside, `**bold**`
and `*italic*` are parsed and kept.

#### Why it is delimited, and not detected

The obvious alternative is to take any maximal run of non-Han as a run
automatically — the scanner already groups that way. **It cannot work, and the
reason is emphasis.**

`**` is itself non-Han. In `属于 **Premier** 之外`, the maximal non-Han run is
` **Premier** ` *including the asterisks*, so the marker and its content land in
the same undifferentiated blob. Automatic detection cannot tell the emphasis
from the text it emphasises, which is exactly the distinction E3 exists to make.
A delimiter that is not markdown syntax settles it: what is inside is parsed,
what is outside is not.

#### It used to be required. It is not any more

**This is the biggest thing that has changed about the subset, so it is worth
stating what it was.** Anything not Chinese and not a mark was an **error**
unless it was wrapped. Not a warning — the argument was that a square holds one
character, so something has to decide that `markdown` is one word rather than
eight things, and the only party who can say so is the author.

```
会像 markdown 那样接在一起。      ← was an error: write ‹markdown›
Valve 在 1999 年做了 Beta 版。     ← was three errors, one per stretch
```

The argument was sound and its premise stopped being true. **The arrangement is
one character to a square — every character, including the marks** — so
`markdown` is eight squares because it is eight characters, and nothing has to
decide anything. `1999` is four. `‹Dust II›` is seven, the space being the gap
that already exists between two squares.

So all of this now renders exactly as written, and `‹…›` is **optional
markup**. What it still does is E5: it is the only place `*italic*` means
anything, because Latin italicises and Chinese does not.

| | | |
|---|---|---|
| **Every character** | 一 `a` `1` `。` `—` | one square each, 每个标点占一格 |
| **Whitespace** | | not a square — it is the gap between two |
| **The delimiters** | `‹` `›` | markup; they never reach the page |

What went with the rule is a good deal of machinery: a run was one unit several
squares wide, measured against a Helvetica advance table, placed whole, moved
whole when it did not fit, hyphenated when it could never fit, and backed by
placeholder squares because RelationGrid has no merged cells. Marks shared boxes
and hung past the margin. **A rule that fits in one sentence beat a model that
needed six**, and it costs the author nothing to write.

#### A typed model for the marks

The rules used to be three strings — `Cells.CLOSING`, `Cells.OPENING`, a
`STANDING` constant — read with `contains()` wherever a decision was needed.
That holds until the questions multiply, and they did: *may this begin a line,
may it end one, may it share a square, and with what.* Four questions asked of
three overlapping strings in five places is how `……` nearly ended up packed into
one square and how `（` ended up stranded at a margin.

`Mark` is an enum; each mark names its `Role`, and the role carries the rules:

| Role | Side | May begin a line | May end a line |
|---|---|---|---|
| `STOP` — 。，、；：？！ | trailing | no | yes |
| `CLOSE` — ）】》」』’” | trailing | no | yes |
| `OPEN` — （【《「『‘“ | leading | yes | no |
| `DOUBLED` — `—` `―` `…` | alone | no | yes |
| `JOINER` — `～` `·` `／` | alone | no | yes |

The `side` column is gone from the code: it said which marks shared a square,
and nothing shares one now. What is left is the two 禁则 columns.

`DOUBLED.mayBeginLine = false` is the field to look at if it ever seems wrong.
The strict rule in GB/T 15834 is only that `——` must not be *split* across two
rows, and many houses do let 破折号 open a line. Barring it delivers the strict
rule for nothing: the second half hangs beside the first, so the pair cannot come
apart. Allowing it would mean moving both halves down together, which is a rule
nothing else here needs.

The reader's tables are still the reader's; `MarkTest` asserts the two agree
about the *rule* rather than about the membership — every character in
`Cells.CLOSING` is a `Mark` that may not begin a line, and every one in
`Cells.OPENING` is a `Mark` that may not end one.

#### 每个标点占一格

Every mark has a square. That was not always so — a closing mark used to ride
in the corner of the character before it and an opening mark in the corner of
the one after, which made 禁则 free, because a row cannot break inside a square.

It was clever and it was inconsistent. A mark's square depended on what happened
to be beside it: 。 after a character was invisible in the model, 。 after a
Latin run was a square, and 。 opening a line was a third thing. Two rules
existed only to paper over it — one making a bracket join its run, one making
`、` end a run so `Ancient、Anubis、Cache、…` did not become a single run fifty
characters wide. **Both are gone.** No full-width mark is inside a run at all
any more.

#### One mark, one square

Marks used to double up: `”。` went in one box, because two boxes there leaves a
hole in the line, and that is genuinely what a hand does on 稿纸. Up to three
would share, of the same class only.

**They do not any more.** One character, one square, marks included — and where
the author wrote two marks there are now two boxes, hole and all. What that
bought is that a mark's square no longer depends on what happens to be beside
it, which was the source of most of the arrangement's complexity.

#### 禁则, which now has to be written down

A mark must not begin a line. This used to come free: **a mark that found no
room hung past the right edge**, the row's last cell running a little wider to
take it — which is what you do writing a composition when a full stop arrives at
the margin. Nothing moved, so a row's contents never depended on what came after
them, and `——` could not be split because the second dash hung beside the first.

With every mark in a square of its own, hanging is gone and the rule has to move
something:

- A **closing** mark belongs to what precedes it, so the character before it
  comes down *with* it. Not just one character: the whole trailing run of
  closing marks and the square they all attach to, or solving `。` would leave
  `”` at the head of a row instead.
- An **opening** mark belongs to what follows it, so one left at the end of a
  row is carried onto the next.

`——` still cannot be split, but for a different reason: the second dash drags
the first down under the closing rule.

Neither can be decided where the mark is placed — whether `（` ends a row depends
on what comes after it, and whether `。` begins one depends on what came before —
so both are decided at the row boundary, which is the only place that knows.

#### Why these two characters

They are not markdown syntax, so nothing competes for them. They are paired,
which backticks are not. And they clash with nothing here: `‹` and `›` appear in
neither `ARTICLE_CLOSING` (`。，、！？：；）】》」』’”…`) nor `ARTICLE_OPENING`,
and occur in none of the 608 published articles.

`〈…〉` (U+3008/U+3009) would have been the wrong choice for the opposite reason:
`《` and `》` are already in those punctuation tables and get 禁则 handling, and
the two pairs are easy to confuse at reading size.

An earlier draft used backticks, borrowing markdown's code span. That was wrong
and the reason is worth keeping: a code span's contents are *literal* by
definition — CommonMark does no emphasis parsing inside one — so
`` `**Premier**` `` means the asterisks. The delimiter forbade the one thing the
extension is for. **Backticks are now simply an error**, like any other
construct not listed above.

#### Width: a run has none, because a character is a square

A run used to occupy `ceil(runWidth / cellWidth)` squares, measured from its
rendered text rather than its character count — `Apartments` was not ten squares
but 3.46 of them at a 62px cell holding a 42px glyph, so four. `SquareWidth`
computed that from a Helvetica advance table, and the workbench confirmed it
against a real browser: 3.51 drawn, every ceiling agreeing.

**That is all gone.** `Apartments` is ten squares because it is ten characters.
The measurement was accurate, validated, and answering a question nobody needs
asked once a square holds one character — so `SquareWidth` is deleted rather
than left sitting there looking like wiring.

What went with it: the run's width, the `Cont` placeholders that made up its
claim, moving a run whole to the next row, and hyphenating one too wide for any
row. A row is full when it has as many squares as it has columns.

The render does wait. `RelGridCellsModule` builds one `div` per column and
`RelGridLayoutModule` sizes them through `<col>` elements — there is no span or
merge anywhere in the grid, so a run cannot yet occupy the *n* squares it has
asked for. **That is a RelationGrid enhancement — merged cells — and it belongs
to the framework, not here.** Until it lands, the run draws in one cell; the
number it carries is correct in the model whether or not the renderer can honour
it yet.

#### What the model needs, and what it got

`Token` is sealed over `Zi` and `Plain`, and `Plain(String text)` carries
neither styling nor width. A run needs both. This note used to propose a third
`Token.Run` case beside them, which would mean a new arm in every switch the
reader has, on behalf of a format the reader does not yet serve.

**The workbench took the other road**, and it is worth writing down which.
`kranji.studio.articles.Square` is a second, studio-local model: `Zi`, `Punct`,
`Run`, `Cont`, `Marker`, `Indent`. `GridPlanner` builds it from the parsed
blocks, and it reuses the reading model's `Cells.CLOSING` and `Cells.OPENING`
rather than copying them, because two 禁则 tables that must agree should be one
table.

The duplication is real and deliberate: the reader's model describes squares
that are all one wide, and widening it is a change to the product that ought to
wait until the product serves `.kmd`. When it does, `Token.Run` is still the
right shape and `Square.Run` is what it will be built from.

#### Placeholders, until the grid can merge

`RelationGrid` builds one element per column and sizes them through `<col>`;
there is no span or merge. So a run four squares wide is drawn as **one head
plus three placeholders** — `Square.Cont`, hatched, carrying the head's id.

They are in the model, not just in the rendering, which is what keeps a row's
arithmetic honest: every square is a position, except one hanging past the
right edge, which by definition is not.
When merged cells arrive the placeholders collapse into the head and nothing
else moves — which is why the width lives on the head rather than being implied
by counting them.

#### When a run cannot fit at all

A run that does not fit in what is left of a row moves to the next row whole.
That is what Chinese typesetting does with a Latin word, and it needs no hyphen.

A run wider than the *entire* row has nowhere to move to. Only then is it cut,
and the pieces carry a hyphen the author did not write — marked as `broken`, and
keeping the run's id so the pieces are still recognisably one run. The cut is at
whatever character fits, which is not where a dictionary would hyphenate: real
hyphenation needs a language and a pattern table, and guessing badly at one is
worse for a child than an obviously mechanical break in a word that was never
going to fit.

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

### E5 — Bold everywhere; italic only inside a run

| | inside `‹…›` | outside |
|---|---|---|
| `**bold**` | `<strong>` | `<strong>` |
| `*italic*` | `<em>` | **dropped, warned** |

**Bold survives over Chinese** because nothing about the grid objects to it: a
heavier character is the same character in the same square. This is the half
that changed — emphasis over Chinese used to be dropped wholesale.

#### Why italic still does not

**No CJK face has an italic.** A browser fakes one by shearing the glyph, which
both looks wrong and pushes the character out of the square it is supposed to
sit in. So the obvious rendering is unavailable, not merely ugly.

Chinese does have a mark for this: the **着重号**, a dot under each emphasised
character. It solves the mechanical problem exactly — no width, so the grid is
untouched; underneath, so it never argues with the pinyin above. It was
implemented, and then removed, for a reason no amount of correctness fixes: **at
reading size it is too faint to notice.** An emphasis nobody sees is worse than
an emphasis that was declined, because the author believes it landed.

One way to say something is better than two, and `**` is the one that works. An
author who wants emphasis in a practice grid writes bold.

#### Where the decision lives

The parser records `emphasis` on every span and decides nothing about how it
looks. The renderer knows whether a span is inside a run and picks the mark.
Keeping the split there is what lets stage two draw the same document as a grid
of squares — where a 着重号 works and a slant never could — without the parser
changing.

It is also why `Span` carries an `emphasis` *field* rather than an emphasis
*kind*. A weight and a pinned reading are two questions about one character, not
two things it could be: 这个**字{zì}**需要两个答案.

#### An unpaired star

`**很重要` closes nothing. It is left exactly as written and warned about — a
typo rather than a construct, and not the sort of thing an author should first
notice as a stray asterisk in the middle of a sentence.

---

## Errors and warnings

**An error means the article does not serve.** The reader shows *"did not
parse"* rather than a page missing something the author wrote. Errors are for
structure we cannot represent — a table, a link, a fourth heading level — and
for anything factually wrong, like a reading the character does not have or a
`#` title disagreeing with the catalogue.

**A warning means it served, and that something in it is probably not what the
author meant.** There are two: a `---` that had nothing to separate, and a `*`
that closes nothing. They land in `target/article-warnings.txt` beside the
guessed readings, which is the file an author already has to read.

The two categories divide on one question: *would serving this misrepresent what
the author wrote?* A rule between two headings does not — they were already
separated. A missing table does.

The warning list used to be longer. Dropped emphasis was on it, and E5 explains
why it should not have been: the fix for something we could not draw was to
learn how Chinese draws it, not to warn about discarding it.

---

## A worked line

```markdown
# CS2 地图详解：Italy {#cs2-italy}

## 三、整体结构 {#zheng-ti-jie-gou}

警察出生点在地{dì}图较低的一侧，靠近‹Tunnel›入口。

它不在‹**Premier**›的现役地图池里，这一点*很重要*。

- 市场
- 中路大街

```verse
石头街道，拱门，
晾着衣服的阳台。
```
```

One title checked against the catalogue, two pinned ids, one reading override,
two `‹…›` runs, a list, and two lines whose breaks survive. The commas and 。
need no delimiters and get none.

Two emphases, drawn two ways: **Premier** is bold Latin inside a run, and
*很重要* is Chinese outside one, so it is set with a 着重号 rather than sheared
into a fake italic.

Everything else in the file would be an error or a warning, and the author is
told which line.
