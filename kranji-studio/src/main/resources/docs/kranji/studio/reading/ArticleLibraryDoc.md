# Reading — Article Library

How articles are packaged, identified, organised, and delivered.

The reader app should not know what content exists. A collection of articles is
built, versioned, and shipped as its own jar; the app discovers a tree of them
through an SPI and renders whatever it finds. Adding a hundred poems is a
content release, not an application release.

## The four parts

```
ArticleCollection   an atomic set of articles and illustrations, globally identified
ArticleTree         nodes the author writes; leaves are collections
ArticleLibrary      the SPI - hands over one tree
resource files      the article text and the image bytes
```

## Identity

**Collection ids are global; article and illustration ids are local to their
collection.** The full address of anything is the pair:

```
collection-id : local-id
```

That is already independent of where the collection is mounted, so nothing needs
a separate uuid on the article. Two collections may both contain a
`chun-tian`; they cannot both claim the same collection id.

Ids are a **type, not a String**. A raw `String` id is the kind of thing that
gets passed where a different id was meant and fails at runtime, in a library
where the failure is a child seeing the wrong text.

```java
public sealed interface CollectionId {
    record Uuid (java.util.UUID value) implements CollectionId {}
    record Named(String value)         implements CollectionId {}   // dotted
}

public sealed interface LocalId {
    record Uuid (java.util.UUID value) implements LocalId {}
    record Named(String value)         implements LocalId {}
}
```

Two distinct types, so a local id cannot be passed where a collection id
belongs. Sealing is what lets a third form arrive later without changing a
signature or invalidating anything already stored.

### Named ids

A dotted, package-like name, and the normal choice for anything curated:

```
kranji.reader.demo.tangshi
kranji.reader.demo.erge
```

Lexically: lowercase segments separated by dots, each starting with a letter and
continuing with letters, digits or hyphens. Lowercase is enforced rather than
conventional — `Kranji.Demo` and `kranji.demo` reading as two collections would
be a collision that looks like a typo.

The dotted form carries ownership the way a package name does. We own
`kranji.*`; anyone shipping a collection jar takes a prefix they control. That
is the whole naming authority — there is no registry, and none is wanted.

**Which to use.** A name where a human curates and a namespace owner exists; a
UUID where neither does — a generated collection, an import, a third party with
no prefix. Both are `CollectionId`, so nothing downstream cares which arrived.

### An id is not a filename

A `LocalId.Named("jing-ye-si")` is far better to author than a UUID, and the
collection already scopes it, so slugs are the normal case for articles. That
comes with one rule, and it is the rule that makes the earlier design fail:

> **A local id is immutable once published.** The resource path may move, the
> title may be corrected, the author may be fixed. The id may not change.

A profile's progress, a "continue reading" record, and any bookmark all key on
`collection:local`. If the id is treated as a filename it will eventually be
tidied, and every stored reference to it silently stops resolving. Renaming the
file is free; renaming the id is a migration.

**Collisions fail at boot.** Two jars claiming one collection id, or two
articles claiming one local id, stops start-up with both offenders named. A
silent duplicate in a library is the kind of fault nobody notices for months,
and by then the wrong one is the one being read.

## Collections are atomic

A collection is mounted **whole**. The tree's leaves are collections; an
individual article is never attached to a tree.

This is the constraint that keeps the system comprehensible at three thousand
articles. If articles could be mounted individually, the tree would become a
second, competing description of the library — one that has to be maintained in
step with the collections and that silently rots when it is not. Instead a
collection is curated once, by whoever assembled it, and organising is only the
question of *where that set belongs*.

It also means a collection is the unit of everything else: authorship, review,
licensing, versioning, and release. A jar of Tang poems is reviewed as a jar of
Tang poems.

### And therefore bounded

A collection that grows without limit makes mounting meaningless. You cannot
meaningfully *place* "everything we have written" — mounting it whole says
nothing, the tree degenerates to one node, and atomicity has bought nothing.

So a collection is **bounded**, and the library grows by adding collections
rather than by growing one. That is what the tree is for.

**唐诗三百首 is already too big.** Three hundred poems is a coherent, closed,
canonical set and it is still not a collection — it is a shelf of them. When a
set exceeds the bound there are two moves, in this order:

1. **Classify**, when a real distinction exists — by form (五言绝句, 七言律诗),
   by poet, by theme. The subdivision means something, and the tree gains a
   branch that a reader can navigate by.
2. **Partition**, when no distinction does — 卷一, 卷二. Arbitrary, and honestly
   arbitrary; a numbered edition is better than a bucket.

Classification is strongly preferred: a partition is a confession that we could
not find the structure, and it leaves a reader with no reason to pick 卷二.

**Get this right before ids are published.** An article's identity is
`collection:local`, so splitting an overgrown collection later changes the
identity of everything in it — and a local id is immutable once published. That
is the migration this whole design exists to avoid. A collection that is
obviously going to grow should be named for its eventual subdivision from the
start.

The ceiling is enforced on the bundled content by {@code BundledArticlesTest}
rather than by {@code validate()}: refusing to *load* a third-party library
because one of its collections is large is a worse failure than showing it.

```
Reading
├── 诗歌
│   ├── 唐诗启蒙      kranji.reader.demo.tangshi
│   └── 儿歌          kranji.reader.demo.erge
├── 故事
│   ├── 寓言故事      kranji.reader.demo.yuyan
│   └── 成语故事      kranji.reader.demo.chengyu
└── 生活与自然
    ├── 生活记叙      kranji.reader.demo.shenghuo
    └── 科普说明      kranji.reader.demo.kepu
```

A collection's articles are shown beneath it, but they are not *authored* into
the tree — they travel with the collection. Reordering the tree cannot lose or
duplicate an article, because the tree never held one.

## Proxies are values

An article is a record, not a class. There is no per-article type, no
`INSTANCE`, no file per item:

```java
public record ArticleRef(LocalId id, String title, String author, String resource) {}
public record ImageRef(LocalId id, String alt, String resource) {}

public interface ArticleCollection {
    CollectionId    id();
    String          title();
    List<ArticleRef> articles();
    List<ImageRef>   illustrations();
}
```

**Metadata is Java; the body is a resource.** This is the same split the studio's
own documents already use — a typed declaration pointing at classpath content.
It buys compile-time checking of the index, IDE navigation, and refactoring; it
costs one line per article.

Listing therefore never parses. A catalogue of three thousand articles is three
thousand records already in memory, not three thousand files opened to read a
title. That is the property that makes the scale workable, and it is the reason
metadata does not live in the article's own text.

**Illustrations come from the same collection** and are addressed the same way.
An image is a member of the set that uses it, so a collection is
self-contained — it can be reviewed, licensed, and shipped without reaching for
assets held somewhere else.

## The SPI hands over a tree

```java
public interface ArticleLibrary {
    ArticleTree tree();          // leaves are collections
}
```

Only the tree. Collections are reachable through it, so there is no second way
to enumerate the library and no way for the two to disagree. Composition is
ordinary Java — a jar that depends on several collection jars and writes the
tree it wants — so a school, a family, or a publisher can each arrange the same
collections differently without any of them rebuilding the reader.

## No special actions

An article's body is its source text, and the wire format *is* that text (see
*Article Format*). An illustration's body is its bytes. Both are resources named
by a Java proxy, so both are served by the generic content path that already
exists for documents.

That retires `ArticleGetAction` and `/article-asset` rather than reimplementing
them per content type. The reader fetches an article, scans it in the browser,
and fills readings from the corpus — none of which needs a bespoke endpoint.

## Authoring workflow

```
1. drop  articles/chun-tian.txt      into the collection's resources
2. add   one ArticleRef              to the collection's articles()
3. build the collection jar
```

Mounting is a separate act, done once per collection in whichever tree wants it.
Adding the hundredth poem to an existing collection touches no tree at all.

## What this replaces

The first implementation put `uuid:`, `title:`, `author:` and `type:` in the
article's own text header and derived the catalogue by parsing every file. That
inverts the split above and does not survive contact with scale: it makes
listing a parse, makes classification a property of the article rather than of
where it is mounted, and leaves the identity buried in content that is meant to
be editable.

Retired by this design: the `uuid:` and `type:` header fields, `ArticleClass`,
`ArticleHeader`, `ArticleParser.headerOnly`, `ArticleTree.shelve(headers)`,
`ArticleGetAction`, and `/article-asset`.

Kept unchanged, because they were at the right layer: `Lines` and the
wire-is-source format, `ArticleScannerModule`, `ArticleReadingsModule`, the
`articleSelection` party, and the corpus lookup through `/syllable-map`.

## Open

- **Tree delivery.** The workspace currently draws a bespoke `/article-tree`.
  Whether that becomes a framework catalogue — so the same tree also gives real
  URLs and breadcrumbs — is deferred, not decided.
- **Collection versioning.** Two jars offering different versions of one
  collection id is a collision today. Whether it should instead be a resolvable
  version is unexamined.
- **Ordering.** Within a collection, articles list in declaration order. Whether
  a curated or difficulty-derived order belongs here or in the reader is open;
  it is a projection either way, so it can wait.
