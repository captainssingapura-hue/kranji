# Adding Reading Content

*The rules for writing articles for the Kranji reader. Lives here, beside the
library modules it governs, so it is versioned with them — if the code and
this file disagree, the code is right and this file is a bug.*

All paths are relative to the repository root. Everything here is checkable:
nothing in this file asks you to be careful about something the build cannot
see, and where a rule matters the command that enforces it is named.

---

## 0. Your boundary

You own **`kranji-library/**` and nothing else.**

```
kranji-library/
  kranji-library-shici/     古诗词   — verse
  kranji-library-gushi/     故事     — story
  kranji-library-kepu/      科普读物 — expository
  kranji-library-xiaohua/   笑话     — jokes
  kranji-library-kouyu/     口语     — tongue-twisters, spoken drills
  kranji-library-zhinan/    使用指南 — the reader's own instructions
  kranji-library-all/       the root: arrangement only, no text
  kranji-library-testkit/   the article check, as a base class - no text
```

Do **not** edit `kranji-reading/**`, `kranji-core/**`, `kranji-singulars/**`,
`kranji-gloss*/**`, or anything under `homing/js/`. Another session works
there. Staying inside `kranji-library/` is what makes the two streams
mergeable without conflicts.

The one file outside a group module you may need is
`kranji-library-all/.../KranjiLibrary.java`, and only when adding a whole new
group (§7). Say so in the commit when you do.

---

## 1. How a library is put together

Four things, and the split between them is the reason a 476-article
catalogue can be listed without opening a single file.

| Thing | Is | Lives in |
|---|---|---|
| **Root** | the arrangement — what sits beside what | `kranji-library-all` |
| **Group** | the unit of *authorship* | one Maven module each |
| **Collection** (shelf) | the unit of *curation* | a `static final` in `…Collections.java` |
| **Article** | a title, an author, and a path | an `ArticleRef` — **metadata in Java** |

The **body** of an article is a `.txt` resource. It is opened once, when a
reader actually clicks the title. That is why `ArticleRef` carries the title
and the tree does not carry the text: listing the library parses nothing.

**The tree never holds an article.** Articles hang off collections, and
collections are what the tree arranges. This is why rearranging shelves
cannot lose or duplicate an article.

---

## 2. The article file format

A body, and nothing else. No front matter — the id, title and author are
declared in Java, so that an article's identity does not live in the part
that is meant to be editable.

```
这是一个读中文的地{dì}方。你现在读的，就是这里的一篇文章。

不认识的字多，很正常。谁都是从一个字都不认识开始的。
```

### Blocks come from line structure, not from syntax

A blank line ends a block. Within a block:

- **one line → a paragraph.** It wraps to the pane. Prose is written as one
  long line per paragraph.
- **several lines → a verse.** The line breaks are kept as written.

> **This is the one thing that silently changes how your article looks.**
> A hard-wrapped paragraph becomes a verse. If your prose comes out broken
> at the places you happened to press Enter, that is what happened. There is
> no error and no warning — the format has nothing to learn, and this is
> what it costs.

Check it if you are unsure: `/article?id=…` reports each block's `kind` as
`"p"` or `"verse"` (§9).

### Reading overrides

`{…}` immediately after the character it corrects:

```
疑是地{dì}上霜。          both forms are accepted:
在看我睡觉{jiào}。         {háng} and {hang2}
```

An override is **checked against the corpus** — it must be a reading that
character actually has. A typo here is an error, not a silent wrong sound.

### Mechanics

- UTF-8. Line endings are handled by `.gitattributes` (`* text=auto eol=lf`)
  — do not fight it, and do not add a BOM.
- Punctuation, Latin and digits pass through untouched. Existing articles use
  Latin about three times in 476 (`U字形`); keep it that rare.
- File name is the article's slug plus `.txt`, under
  `<module>/src/main/resources/kranji/articles/<group>/`.

---

## 3. The rule everything else hangs off

> **Every Han character must be in the corpus.**

The corpus is ~8,100 characters. A character outside it is a **parse error**:
`ArticleGetAction` refuses to serve the article and the reader is shown
*"did not parse"*. There is no partial render and no fallback.

You do not have to guess which characters those are. §9 tells you.

---

## 4. Readings: the principal, and when it is wrong

Every character takes **the corpus principal reading** unless you override it.

The principals are the modern common readings, which is usually what you
want:

| | principal | |
|---|---|---|
| 的 | `de` | not `dì` |
| 还 | `hái` | not `huán` |
| 着 | `zhe` | not `zhuó` |
| 谁 | `shéi` | not `shuí` |
| 车 | `chē` | not `jū` |
| 行 | `xíng` | not `háng` |

**But the principal is a fact about the character, not about your sentence.**

Worked example, from writing `从这里开始`. The article opens
`这是一个读中文的地方` — *this is a place for reading Chinese*. 地方 is
**dìfang**. The corpus principal for 地 is **`de`**, the adverbial particle,
because that is how 地 is most often read. Left alone, the reader would have
annotated the first line of the app's own guide with the wrong sound.

Nothing about that is visible in the text. It was caught because the build
writes down every reading it guessed (§9), and 36 polyphonic characters in
that article were read one at a time against what the sentence meant. One of
the 36 was wrong. That ratio is normal.

> **Read your article's entry in the warning report, line by line. Every
> time.** It is the only step in this workflow that a machine cannot do for
> you, and it is the one that decides whether a child learns the right sound.

---

## 5. Declaring it in the catalogue

`…Collections.java` in the group module. The pattern is the same in all six:

```java
private record Bundle(CollectionId id, String title, String summary,
                      List<ArticleRef> articles) implements ArticleCollection {}

private static ArticleCollection bundle(String id, String title, String summary,
                                        ArticleRef... articles) {
    return new Bundle(CollectionId.named("kranji.library.kepu." + id),
            title, summary, List.of(articles));
}

private static ArticleRef a(String slug, String title) {
    return ArticleRef.of(slug, title, "/kranji/articles/kepu/" + slug + ".txt");
}

public static final ArticleCollection TIAN_WEN = bundle("tianwen",
        "天文", "The sky, which is the first thing anybody wonders about.",
        a("yue-liang", "月亮"), a("tai-yang", "太阳"), a("xing-xing", "星星"));

/** Every collection this group declares — the mounting check reads it. */
public static List<ArticleCollection> all() { return List.of(TIAN_WEN, …); }
```

Use `ArticleRef.by(slug, title, author, resource)` where there is an author;
`of(…)` leaves it empty.

### Ids

Lowercase, dot-separated segments; each segment starts with a letter and
continues with letters, digits or hyphens. Enforced by `Names` — a capital
letter throws at class-init, so you find out on the first test run.

> **An id is an address and is immutable once published.** The article's
> segment in the tree *is* its id, and that is what a reader bookmarks and
> what `ArticleSelected` carries. Renaming a slug breaks the link silently:
> the tree still works, the old URL points at nothing. Retitle freely;
> re-slug never.

Collection ids must be unique **across every jar**, because two groups
released separately cannot see each other's. `RootLibraryTest` is what
catches a collision.

### Titles and authors are Java string literals

CJK in `…Collections.java` is fine — these are ordinary server classes, not
served JS modules, and the no-inline-glyph conformance rule does not reach
them. (It does reach anything under `homing/js/`, which you are not editing.)

> **A title cannot carry a reading override, and nothing warns you.**
> `ArticleParser` never parses the title — it takes it as a string and hands
> it through, and the readings are worked out in the browser from the raw
> text, where `{…}` means nothing and would be shown as literal braces. So
> every Han character in a title takes the corpus principal, whatever your
> title means.
>
> **Check your title against §4 before you choose it.** 音乐课 reads 乐 as
> `lè`; 请假条 reads 假 as `jiǎ`; 睡觉 reads 觉 as `jué`. None of those can be
> fixed from here — the only move available to an author is to pick a title
> that does not need fixing. Twenty-eight titles already in the library have
> this fault, 地球 and 扫地 among them: 地's principal is `de`, which is the
> example §4 opens with.
>
> Fixing it properly means an override path for titles, which lives in
> `kranji-reading/**`. Say so rather than reaching for it.

### `summary` is no longer displayed

The library tree stopped rendering shelf summaries — they were an English
sentence a learner who could not read the shelf's name could not use. The
field still exists and the constructor still takes one. Write it as a note to
the next curator, or pass `""`. Nothing shows it to a reader.

---

## 6. Adding an article to an existing shelf — the common case

1. Write `<module>/src/main/resources/kranji/articles/<group>/<slug>.txt`.
2. Add one `a("<slug>", "<标题>")` to the right collection in
   `…Collections.java`.
3. Run the inner loop in §9.
4. Read your article's block in `target/article-warnings.txt` and add
   `{…}` overrides where the guessed reading is wrong for your sentence.
5. Re-run until the only warnings left are readings you have personally
   agreed with.

---

## 6b. One work, several tellings — the umbrella

Some works are worth having more than one way. A 成语 story has the
classical text it is short for and a retelling a child can read first. A
科普 piece can be told at three depths — the same ants, with more or less
said about them. Those are **one work**, and the tree should say so rather
than scattering three similarly-named articles across a shelf.

An **umbrella** is one entry with one title and, under it, a map from a
**classifier** to the article that is that telling:

```java
private static ArticleUmbrella<Classifier.Provenance> retoldAndOriginal(
        String slug, String title, ArticleRef retold, ArticleRef original) {
    return ArticleUmbrella.of(slug, title, Map.of(
            new Classifier.Retold(1), retold,
            new Classifier.Original(), original));
}

public static final ArticleCollection CHENG_YU = bundle("chengyu",
        "成语故事", "",
        a("hua-she-tian-zu", "画蛇添足"),
        retoldAndOriginal("ke-zhou-qiu-jian", "刻舟求剑",
                a("ke-zhou-qiu-jian", "刻舟求剑"),
                ArticleRef.by("ke-zhou-qiu-jian-yuanwen", "刻舟求剑", "吕氏春秋",
                        "/kranji/articles/chengyu/ke-zhou-qiu-jian-yuanwen.txt")));
```

The tree then draws:

```
▾ 成语故事(chéng yǔ gù shi)
    画蛇添足(huà shé tiān zú)
  ▾ 刻舟求剑(kè zhōu qiú jiàn)
      白话(bái huà)
      原文(yuán wén)                吕氏春秋
```

### The classifiers

Two families exist. **Pick the one whose axis your work varies along**; an
umbrella is typed on one family and the compiler will not let you mix them.

| Family | Cases | For |
|---|---|---|
| `Classifier.Provenance` | `Original`, `Retold(n)` | a work with a source text — fables, idioms, poems |
| `Classifier.Depth` | `Level(n)` | the same subject told with more or less — 科普 |

Levels start at 1, the plainest. Editions are **shown in rank order
regardless of how you declare them**: `Retold(1)`, `Retold(2)`, … then
`Original` last, because a reader arrives at 文言 by way of the retelling.

The labels a reader sees — 白话, 原文, 第一级 — come from the classifier, not
from you. If your work needs a third axis, that is a new family in
`Classifier`, which is outside your boundary: say so.

### The three rules

1. **Every telling is still an ordinary `ArticleRef`** with its own slug, its
   own `.txt`, and its own address `collection:local`. The umbrella holds
   no text and is not something a reader opens.
2. **All editions of one work live in one collection.** The address is
   `collection:local`, so a work cannot span shelves.
3. **Turning a solo article into an umbrella must not move its address.** Give
   the umbrella the article's slug, and keep that slug on the telling a reader
   was already opening. Bookmarks survive; only the tree gains a level. The
   new telling gets a new slug (`-yuanwen`, `-2`, …).

### What your `bundle()` needs

Your group's `…Collections.java` has a `bundle(…, ArticleRef...)`. It needs
to take entries and derive the flat list — the demo library's is the model:

```java
private record Bundle(CollectionId id, String title, String summary,
                      List<? extends ArticleEntry> entries) implements ArticleCollection {
    @Override public List<ArticleRef> articles() {
        var out = new ArrayList<ArticleRef>();
        for (ArticleEntry e : entries) out.addAll(e.articles());
        return List.copyOf(out);
    }
}
private static ArticleCollection bundle(String id, String title, String summary,
                                        ArticleEntry... entries) { … }
```

Every existing `a(…)` still compiles against it — `ArticleRef` *is* an
`ArticleEntry` — so this is a change to one record and one signature, not to
the shelves. `LibraryTree.validate()` refuses a collection whose `entries()`
and `articles()` disagree, so a bundle that derives one from the other is the
one that cannot be caught.

---

## 7. Adding a shelf, or a group

**A new shelf** is one more `bundle(…)` plus a line in `all()` plus a
`LibraryTree.shelf(…)` in the group's `…Library.java`. `CatalogueTest`
fails if you declare a collection and forget to mount it — that check exists
because a shelf nobody can reach is indistinguishable from one nobody wrote.

**A new group** is a Maven module. Copy `kranji-library-zhinan`, which is the
smallest complete example: `pom.xml`, `…Library.java` (the tree),
`…Collections.java`, one article, a `CatalogueTest` and an `ArticlesTest`.
Then:

- add `<module>` to `kranji-library/pom.xml`
- add the dependency to `kranji-library-all/pom.xml`
- graft the tree in `KranjiLibrary.java`
- point the new `ArticlesTest` at the new tree — four lines, §9
- update `RootLibraryTest.theArrangementIsTheOneWritten`, which asserts the
  top-level order exactly

A group whose tree is a single shelf can be grafted as
`LibraryTree.shelf(…)` rather than as a branch — a heading above one shelf is
a level a reader clicks through for no reason. `ZhiNanLibrary` does this.

---

## 8. What a good article looks like

Read a dozen before writing one. The house style is short and plain.

| | characters |
|---|---|
| shortest | 19 |
| median | **38** |
| 90th percentile | 62 |
| longest | 451 |

Verse runs shorter than the table: 古诗词 is 22 at its shortest and 32 at its
median, and its longest is 曹操's 短歌行 at 160.

> **This table was wrong until 2026-09**, and said 59 / 118 / 188 / 1,387.
> Those are the same articles counted in *bytes*: `wc -m` counts bytes in the
> shells this repository is built in, and a Han character is three of them.
> Anything measured that way reads three times its real length. To count
> characters:
>
> ```bash
> sed 's/{[^}]*}//g' file.txt | tr -d '\n' | LC_ALL=C.UTF-8 grep -o . | wc -l
> ```

Written for this project. Nothing here is translated or adapted from a
source, which is why the library modules carry no third-party licence — keep
it that way, and keep the facts the plain ones a reader can check.

---

## 9. Verifying

### The inner loop — run this constantly

Your group, which is the one you are editing:

```bash
mvn -o -pl kranji-library/kranji-library-shici -am test
```

A second or two. It parses **every article of that group**, one test case per
article, and fails on anything a reader would be shown an error for: a
character outside the corpus, an override that is not one of that character's
readings, an unclosed brace, an empty body. The count is the size of your
shelf, and a broken poem is one red case naming that poem rather than one red
method with the damage buried in its message.

The whole library — every root on one classpath, which is what the
application actually serves:

```bash
mvn -o -pl kranji-reading/kranji-reading-app -am test -Dtest=LibraryArticlesTest -Dsurefire.failIfNoSpecifiedTests=false
```

> **`-am` is not optional.** Without it, `-pl` resolves the library jars from
> `~/.m2` and your edit is not in them — the test passes against the last
> installed jar and tells you nothing. With it, every upstream module is
> rebuilt from source first. The long property name is what this repo's
> older surefire wants; the short `-DfailIfNoTests=false` fails in
> `kranji-core`.

Both are the same check. It lives in `kranji-library/kranji-library-testkit`
as `LibraryArticlesTestBase`; each group module has a short `ArticlesTest`
naming its own tree, and the application has one naming every discovered
root. Adding a group means adding that subclass — §7.

Each writes **`target/article-warnings.txt`** under its own module, listing
every reading that was chosen for you without an override:

```
kranji.library.zhinan.shiyong:cong-zhe-li-kai-shi  (/kranji/articles/zhinan/…)
    line 1: 个 is read 2 ways; using gè - override if that is wrong here
    line 1: 地 is read 2 ways; using de - override if that is wrong here
    …
```

So `kranji-library/kranji-library-shici/target/article-warnings.txt` holds
only 古诗词, and `kranji-reading/kranji-reading-app/target/article-warnings.txt`
holds all of it. The group's is the one to read while writing; they say the
same thing about the same article.

An article listed there is **not wrong — it is unreviewed**. 511 of 514
articles appear, because 的 and 不 and 一 are polyphonic and in every
sentence ever written. Find your own address in the file and read its lines.
That is step 4 of §6, and it is not optional.

### The catalogue checks

Your group's `CatalogueTest` runs with the inner loop above — same module,
same command, no extra run. What that does not cover is the graft:

```bash
mvn -o -pl kranji-library/kranji-library-all test          # the graft
```

`CatalogueTest` checks that every ref resolves to a file that exists, that
every declared collection is mounted, and that no resource is mounted twice.
`RootLibraryTest` checks the same across all jars, plus id uniqueness and the
top-level arrangement. Run it when you have added or moved a shelf, and
always before you push.

### Everything, before you push

```bash
mvn -o install
```

---

## 10. Seeing it

`.claude/launch.json` has a `reading` configuration on **port 8102**. The
Library pane shows the tree; the Reader pane shows an article. Useful for
checking that a paragraph came out as prose rather than as verse, and that a
title reads the way you meant it to.

Two endpoints answer without a browser:

```bash
curl -s http://localhost:8102/article-tree | head -c 400
curl -s "http://localhost:8102/article?id=<collection>:<local>"
```

The second is the quickest way to see block kinds. Note that the server
holds the library in memory: **restart it after a rebuild**, or you are
reading the old jar.

---

## 11. Conventions

- **Commit messages carry no `Co-Authored-By` trailer.** House rule.
- **No Python helper scripts.** Use `sed`, or edit the file directly.
- One commit per coherent addition — a shelf, or a batch that belongs
  together. Say in the message what was added and why it sits where it does.
- Do not commit `target/`. `article-warnings.txt` is a build product; read it,
  do not check it in.

---

## 12. Things that will bite you

| Symptom | Cause |
|---|---|
| Prose renders as ragged verse | The paragraph was hard-wrapped. One line per paragraph. |
| Reader says *"did not parse"* | A character outside the corpus, or a bad override. §9 names it. |
| A reading is subtly wrong and nothing complains | The principal was right for the character and wrong for your word. §4. |
| A bookmark stops working | A slug was renamed. Ids are addresses. |
| `RootLibraryTest` fails on a fresh group | The tree was grafted but the arrangement assertion was not updated. |
| Two articles, one count | Two refs share a `resource` path. `noTwoArticlesShareAResource` catches it. |
| Class-init throws on an id | Uppercase, or a segment starting with a digit. `Names` is strict on purpose. |

---

## 13. Where the rules actually live

If this file and the code disagree, the code is right — and these are the
files to read:

| | |
|---|---|
| The format, and every finding it can raise | `kranji-reading-content/…/ArticleParser.java` |
| What is served to the reader | `kranji-reading-app/…/read/ArticleGetAction.java` |
| The tree the Library pane draws | `kranji-reading-app/…/read/ArticleTreeGetAction.java` |
| Ids, refs, collections, tree | `kranji-reading-model/…/library/` |
| The check that gates your work | `kranji-library-testkit/…/LibraryArticlesTestBase.java`, plus each group’s `ArticlesTest` |
| The smallest complete group | `kranji-library/kranji-library-zhinan/` |
