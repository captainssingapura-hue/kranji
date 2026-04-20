# Kranji

A Java 21 library for modeling, classifying, and visualizing the internal structure of Chinese characters (汉字).

Kranji treats every character as a **typed tree** — each node is either a spatial layout (left-right, top-bottom, enclosure, ...) or a leaf component (radical, standalone character). Two orthogonal classification axes sit on top: **structural composition** (字形结构) describes *how* the parts are arranged, while **etymological category** (六书) describes *why* those parts were chosen.

## At a glance

```
清  qīng  U+6E05  11 strokes  radical 85 (水)
├─ composition: LeftRight
│  ├─ left:  氵 (three-dot water)
│  └─ right: TopBottom
│           ├─ top:    青.top  (龶)
│           └─ bottom: 月
└─ etymology: PhonoSemantic
   ├─ semantic: 氵 (water → clear)
   └─ phonetic: 青 (qīng)
```

The same data model scales from single-stroke pictographs like 一 all the way to the 58-stroke 𰻝 (biáng), which nests 4 levels deep.

## Modules

| Module | Purpose |
|---|---|
| **kranji-core** | Type system, layout engine, SVG renderer, pinyin model |
| **kranji-singulars** | Curated families of standalone characters (独体字) with semantic metadata (manual families + `SingularFamilies` aggregator) |
| **kranji-singulars-perclass** | Per-class JSON catalog + codegen for the singulars |
| **kranji-common-base** | Shared bootstrapping for the depth modules |
| **kranji-common-depth1..5** | Composed characters (合体字) partitioned by nesting depth; Java is generated from the JSON catalog |
| **kranji-common** | Legacy/staging aggregator — now empty of records; retained for transitional wiring |
| **kranji-codegen** | Per-depth JSON catalog (source of truth) + generator mains (`Depth<N>GenerateMain`, `Depth<N>SnapshotMain`) |
| **kranji-json** | `ComposedZiJson` DTOs and catalog loader |
| **kranji-json-bridge** | `TypedToUntyped` / `UntypedToTyped` converters powering the round-trip |
| **kranji-core-demos** | SVG export, ZiLookup, BlockLookup, and debugging utilities |
| **kranji-ui-demo** | Interactive JavaFX explorer with filtering and live SVG preview |
| **kranji-test-fixtures**, **kranji-util** | Cross-module test helpers and shared plumbing |

## Core concepts

### Structural composition (`CharacterComposition`)

A sealed interface with 13 variants, each naming its component slots explicitly:

| Variant | Slots | Example |
|---|---|---|
| `Singular` | — | 人, 山, 日 |
| `LeftRight` | left, right | 明, 休, 清 |
| `TopBottom` | top, bottom | 字, 花 |
| `LeftMiddleRight` | left, middle, right | 街, 班 |
| `TopMiddleBottom` | top, middle, bottom | — |
| `FullEnclosure` | outer, inner | 国, 回 |
| `SemiEnclosure*` | wrapper, content (6 variants) | 遨, 庄, 区 |
| `Mosaic` | three elements | 品, 森 |

Every slot holds a `StructuralNode` — either a leaf `Component` or another `CharacterComposition` — so compositions nest recursively.

### Etymological category (`EtymologicalCategory`)

The classical Six Writings (六书), also modeled as a sealed interface:

- **Pictograph** (象形) — stylized drawing: 日, 月, 山
- **Simple indicative** (指事) — symbol with indicator mark: 上, 下
- **Compound indicative** (会意) — meaning from combined parts: 明 = 日 + 月 → bright
- **Phono-semantic** (形声) — semantic radical + phonetic element: 清 = 氵 + 青
- **Derivative cognate** (转注) — extended meaning between related characters
- **Phonetic loan** (假借) — character borrowed for its sound

### Component system

Characters decompose into **components** — either a `Part` (偏旁, bound radical like 氵) or a `Zi` (字, standalone character like 木). Leaf components are organized into families (`WaterFamily`, `WoodFamily`, `PersonFamily`, ...) with rich metadata: glyph, meaning, pinyin, stroke count.

`SingularZi` instances carry layout hints — **politeness** (how much space to yield in a split) and **inner positioning** (scale and offset within a block) — enabling the layout engine to produce correct proportions automatically.

### Two-stage layout pipeline

1. **Block layout** (`BlockLayoutEngine`) — Recursively partitions a unit square `[0,1]²` into blocks based on composition structure and component politeness levels. A relative-gap formula shifts each split proportionally:

   ```
   gap = B.politeness - A.politeness
   A_share = 0.5 + gap * 0.10
   ```

   Four politeness levels — `ASSERTIVE`, `NEUTRAL`, `YIELDING`, `DEFERENTIAL` — let narrow radicals (亻, 氵, 扌) shrink gracefully beside dominant components.

2. **SVG rendering** (`BlockSvgRenderer`) — Fits glyphs into blocks using inner scale and offset hints. Supports both font-based text rendering and custom SVG paths for components whose standard font forms are inadequate (e.g., 提土旁 for 土 as a left radical).

## Building

Requires **Java 21** and **Maven 3.9+**.

```bash
mvn clean install
```

## Running

### SVG export (command-line)

```bash
mvn -pl kranji-core-demos exec:java \
    -Dexec.mainClass=kranji.demos.SvgExportDemo
```

Outputs three SVG variants per character into `output/`:
- `svg/` — structural decomposition diagram
- `svg-block/` — block layout with glyph rendering
- `svg-stroke/` — stroke-based visualization

### Character lookup

Check which characters already exist in the system. Pass a UTF-8 text file containing comma-separated characters:

```bash
mvn -pl kranji-core-demos exec:java \
    -Dexec.mainClass=kranji.demos.ZiLookup \
    -Dexec.args="zi-check.txt"
```

Writes `zi-check-result.txt` alongside the input with a full report (type, depth, source class for each character). You can also specify an explicit output path:

```bash
mvn -pl kranji-core-demos exec:java \
    -Dexec.mainClass=kranji.demos.ZiLookup \
    -Dexec.args="input.txt results/output.txt"
```

### Interactive explorer (JavaFX)

```bash
mvn javafx:run -pl kranji-ui-demo
```

Opens a GUI with a filterable character table (by initial, tone, composition type, etymology) and a live SVG panel showing structural decomposition with optional block overlay.

## Project structure

```
Kranji/
├── kranji-core/
│   └── src/main/java/kranji/
│       ├── classification/   # CharacterComposition, EtymologicalCategory, BlockRole
│       ├── component/        # Component, SingularZi, HintedZi, BasicComponent
│       │   └── basic/        # 20 component families (PersonFamily, WaterFamily, ...)
│       ├── entry/            # ChineseCharacterEntry record
│       ├── layout/           # BlockLayoutEngine, BlockSvgRenderer, Politeness, LayoutHint
│       ├── pinyin/           # PinyinSyllable, Initial, Final, Tone
│       ├── stroke/           # Stroke data and glyph info
│       ├── svg/              # StructuralSvgRenderer, StrokeSvgRenderer
│       └── graph/            # Structural graph (Vertex, Edge)
├── kranji-singulars/
│   └── src/main/java/kranji/singular/
│       ├── SingularFamilies.java     # Aggregator registering every family
│       ├── nature/NatureElements.java
│       ├── body/BodyParts.java
│       ├── plants/PlantsAndAgriculture.java
│       └── ...                       # 13 semantic families
├── kranji-singulars-perclass/        # Per-class JSON catalog + generated Java
├── kranji-common-depth1/             # 441 depth-1 ComposedZi (per-pinyin layout)
│   └── src/main/java/kranji/common/depth1/
│       ├── Depth1.java               # Top-level aggregator (Depth1.ALL)
│       ├── Depth1<Initial>.java      # 22 per-initial aggregators
│       └── <initial>/<Final><Tone>.java   # One class per pinyin triple
├── kranji-common-depth2/             # same shape, depth-2 records
├── kranji-common-depth3/             # same shape, depth-3 records
├── kranji-common-depth4/             # empty today; Depth4.ALL = List.of()
├── kranji-common-depth5/             # depth-5 records (currently just 𰻝)
├── kranji-codegen/
│   ├── src/main/java/kranji/codegen/depth{1,2,3,5}/   # Generators
│   └── src/main/resources/catalog/depth{1,2,3,5}/     # JSON source of truth
├── kranji-core-demos/
│   └── src/main/java/kranji/demos/
│       ├── ExampleCharacters.java    # 21 curated showcase entries
│       ├── SvgExportDemo.java        # Batch SVG export
│       ├── ZiLookup.java             # Character coverage checker (file-based I/O)
│       └── CharacterCodeGen.java     # Registry code generator
├── kranji-ui-demo/
│   └── src/main/java/kranji/ui/demo/
│       ├── KranjiDemoApp.java        # JavaFX application
│       └── KranjiDemoLauncher.java   # Entry point
└── output/                           # Generated SVG files
```

## Key design choices

- **Sealed interfaces + records** — The type system enforces exhaustive handling of all 13 composition variants and 6 etymology categories at compile time. No stringly-typed fields, no orphan enums.
- **Recursive `StructuralNode`** — A proper algebraic data type (component | composition) enables arbitrary nesting depth without special-casing.
- **Orthogonal axes** — Composition and etymology are independent concerns. A `LeftRight` character can be phono-semantic, compound-indicative, or anything else.
- **Politeness-based layout** — Instead of hardcoded width/height weights per component, a single `Politeness` level on each component drives proportional splits through a gap formula. New components get sensible layout for free.
- **ServiceLoader discovery** — `SingularZi` instances registered in `kranji-singulars` are discovered at runtime via `SingularZiProvider`, keeping the core module decoupled from the character database.

## License

All rights reserved.
