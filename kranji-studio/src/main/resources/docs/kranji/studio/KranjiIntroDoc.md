# Kranji — Read Me First

Kranji is a Java 21 library for modelling, classifying, and visualising the
internal structure of Chinese characters (汉字).

## The one idea

Every character is a **typed tree**. Each node is either a spatial layout
(left-right, top-bottom, enclosure, …) or a leaf component (a radical, or a
standalone character that appears inside a larger one).

```
清  qīng  U+6E05  11 strokes  radical 85 (水)
├─ composition: LeftRight
│  ├─ left:  氵 (three-dot water)
│  └─ right: TopBottom
│           ├─ top:    青.top (龶)
│           └─ bottom: 月
└─ etymology: PhonoSemantic
   ├─ semantic: 氵 (water → clear)
   └─ phonetic: 青 (qīng)
```

Two orthogonal axes sit on top. **Structural composition** (字形结构) describes
*how* the parts are arranged. **Etymological category** (六书) describes *why*
those parts were chosen. They vary independently — a left-right character can be
phono-semantic, compound-indicative, or anything else.

The same model scales from single-stroke pictographs like 一 to the 58-stroke
𰻝 (biáng), which nests four levels deep.

## Why it is typed rather than stringly

A composed character is not a string with metadata attached. It is a record
whose *type signature is its structural decomposition*:

```java
Ming implements ComposedZiT, LeftRightT<Ri, Yue>
```

`LeftRightT<Yue, Ri>` is a compile error at the reference site, not a runtime
surprise. Sealed interfaces force every renderer, analyser, and exporter to
handle all composition variants exhaustively. There are no orphan enums and no
stringly-typed slots.

## How this studio is organised

**Architecture** — the type system, and the two-stage layout plus JSON-first
codegen pipeline that turns it into rendered glyphs and generated source.

**Corpus** — what the catalogue actually contains, measured off the built
registry rather than inferred from file counts.

**Explorations** — forward-looking studies asking whether the typed-composition
pattern generalises to other domains. None of it is planned Kranji work; each
write-up is meant to seed a separate project.

**Plans** — in-flight work, tracked with decisions, phases, and ship-gates.
The plan files are the source of truth: edit, recompile, refresh.
