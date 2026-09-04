# Layout & Codegen Pipeline

Two independent pipelines. One turns a typed tree into pixels; the other turns a
JSON catalogue into typed source.

## Stage 1 — block layout

`BlockLayoutEngine` recursively partitions the unit square `[0,1]²` according to
the composition structure. The interesting part is how it decides *where* to
split.

Rather than hardcoding a width ratio per component, each component carries a
**politeness** level describing how much space it yields:

```
gap      = B.politeness - A.politeness
A_share  = 0.5 + gap * 0.10
```

Four levels — `ASSERTIVE`, `NEUTRAL`, `YIELDING`, `DEFERENTIAL` — let narrow
radicals (亻, 氵, 扌) shrink gracefully beside a dominant component. A new
component gets sensible proportions for free by declaring its politeness; nobody
tunes a table of magic numbers.

## Stage 2 — SVG rendering

`BlockSvgRenderer` fits glyphs into the computed blocks using each component's
inner scale and offset hints. It supports both font-based text rendering and
custom SVG paths for components whose standard font forms are inadequate — 提土旁
(the 土 radical in left position) being the canonical case.

The renderer emits a plain SVG string, which is why it ports cleanly to any
front end: the browser gets finished SVG rather than a layout algorithm.

## The codegen pipeline

The JSON catalogue under `kranji-codegen` is the source of truth. Every typed
record is emitted mechanically:

```
catalog/depth{1,2,3,5}/*.json
        │
        ▼  PerclassGenerateMain
   generated typed records
        │
        ├─ kranji-singulars-perclass   (singulars and parts)
        └─ kranji-common-perclass      (composed characters)
```

Hand-editing generated source breaks the invariant. Change the JSON and
regenerate.

## Lifecycle modules

Composed records live in one of three modules depending on maturity:

| Module | Role |
|---|---|
| `kranji-common-perclass` | Hand-authored canonical records |
| `kranji-common-perclass-promoted` | Generated records that passed review |
| `kranji-common-perclass-staging` | Newest generated records, churn-prone |

`AllZiRecords.ALL` concatenates all three in a stable order, so downstream
consumers see one population and never care which stage a record is in.
