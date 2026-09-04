# CF-003 — No lookup from the hub to composition

CD-001 makes `ZiCharUTF8` the identity everything joins on. The phonic tier
honours that with an index. **The structural tier has none**, so "what is this
character made of?" cannot be answered without writing one.

## What exists

The bridge between the two hubs is already there, and is one call each way:

```java
ZiCharUTF8(int codePoint)                     // value(), codePointLabel()
ZiCharUTF8Codec.INSTANCE.from(String)         // and tryFrom -> Optional
```

So the key type is not the obstacle. The obstacle is that nothing on the
structural side is indexed.

| Tier | Registry | Lookup by hub |
|---|---|---|
| Phonic `SimpleZi` | `SimpleZiRegistry` | `Map<ZiCharUTF8, SimpleZi>`, `find(ZiCharUTF8)` and `find(String)` |
| Composed (800) | `AllPerclassRecords.ALL` | none — a `List<ComposedZiT>`, alphabetical by FQN |
| Singulars (549) | `SingularFamilies` | none — only `registerInto(BasicSet)` |

`SimpleZiRegistry` is precisely the facility the structural tier lacks, and is
the pattern to copy rather than invent.

## Two hazards

**The singulars need bootstrapping, and it is stateful.** `BasicSet` holds a
mutable list behind a `registered` latch, and the 549 singulars only arrive when
something calls `SingularFamiliesPerclass.registerInto(BasicSet.INSTANCE)` —
which today only the demos and their tests do. A server that omits that call
gets 30 components instead of 579 characters, **silently**, with no error. For a
widget that failure is indistinguishable from poor corpus coverage, which is the
worst kind of bug to have here: it looks like the thing we already expect.

**The two hubs meet here.** `ComposedZi` and `SingularZi` contain no reference
to `ZiCharUTF8` at all — they key on `String glyph()`. CD-001 records this as a
known strain and calls convergence "not tidying — it is completing this
decision". Any index built now sits astride that seam.

## It has been solved once already

`ZiLookup.buildIndex()` in `kranji-core-demos` builds this index: it walks
`BasicSet.INSTANCE.components()` for singulars and parts, then
`AllPerclassRecords.ALL` for composed records, first-writer-wins so a singular
beats a composed record on a collision. It keys on `String` and yields a
description rather than the record, but the shape and the collision rule are
worked out and worth lifting rather than rediscovering.

## Shape of the fix

One new class, **no edits to any of the 1,379 records** — which is what makes it
compatible with leaving the existing stack untouched:

```java
Optional<Zi>          find(ZiCharUTF8)          // composed or singular
Optional<ComposedZiT> composition(ZiCharUTF8)
```

Built in a static initialiser that performs the `registerInto` boot itself, so
no caller can forget it. Reading `glyph()` as `String` and converting through
the codec crosses the two-hub seam in **exactly one place** rather than
everywhere — the honest way to defer convergence rather than pretend it is not
there.

Per CF-002 it would expose composition, etymology, strokes and radical, and
would not surface `meaning()`.

## Also blocking

The reading app depends on `kranji-reading-model` → `kranji-core` only. Neither
`kranji-common-perclass` nor `kranji-singulars` is on its path. No cycle
prevents adding one — both sit on `kranji-core` — but it is a deliberate
architectural line and crossing it is a decision to take on its own terms.

Worth noting the shape it should take: the reading app has never talked to a
corpus module directly. It goes through actions serving derived data —
`/zi-detail`, `/syllable-map`, `/article-census`. Composition should arrive the
same way, which means the registry can stay in the corpus modules and only its
output crosses.
