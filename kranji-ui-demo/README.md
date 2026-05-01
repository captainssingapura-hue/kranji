# kranji-ui-demo

Interactive 2D JavaFX explorer for the Kranji corpus.

A filterable character table on the left, a detail panel on the right that
shows pinyin, the live SVG render, and a clickable composition signature. The
goal is to make every record in the corpus reachable in one or two clicks
from any starting point.

## Launching

```bash
mvn javafx:run -pl kranji-ui-demo
```

Java 21 + Maven 3.9+. JavaFX is pulled in via the parent POM; no SDK install
needed.

## Features

### Source-list selector

A drop-down at the top picks which slice of the corpus the table shows:

| Source | What it contains |
|---|---|
| **All** | Every record in the project — singulars, parts, composed examples, typed records |
| **Singular Zi (独体字)** | Standalone characters from `SingularFamilies` |
| **Parts (偏旁)** | Bound radicals — 氵, 扌, 亻, 提土旁, etc. — adapted as table rows |
| **Composed Examples** | The 21 hand-curated examples from `kranji-core-demos` |
| **Typed Per-Class (507)** | The full generated `ComposedZiT` registry from `kranji-common-perclass` |

### Filtering

- **Initial** — pinyin initial (`zh`, `q`, `m`, …).
- **Final + Tone** — combined `Ing2`, `Ai4`, etc.
- **Composition** — `LeftRight`, `TopBottom`, `FullEnclosure`, …
- **Etymology** — Pictograph, Phono-semantic, Compound-indicative, …
- **Depth** — exact structural depth (1–5).

Filters compose as AND. A live count badge shows how many records remain.

### Detail panel

Selecting a row populates a tabbed detail panel:

- **Composition** — the SVG render plus a clickable layout signature
  (`LeftRight<氵, TopBottom<青.top, 月>>`). Click any glyph in the
  signature to refocus the table on that sub-record.
- **Associated** — every record in the corpus that uses the selected
  character as an immediate component. Backed by an index built once at
  startup and keyed by glyph string (so glyph identity, not Java reference,
  drives the lookup).

### Layout signature view

`LayoutSignatureView` renders a typed composition as a Java-like generic
signature with each component a clickable node:

```
LeftRight<氵, TopBottom<青.top, 月>>
```

Clicking 月 swaps the table and detail to that character. This is the
fastest way to navigate the dependency graph by hand.

## Source map

```
kranji-ui-demo/src/main/java/kranji/ui/demo/
├── KranjiDemoLauncher.java   # Entry point — launches the JavaFX Application
├── KranjiDemoApp.java        # Table + filters + detail panel + Associated tab
└── LayoutSignatureView.java  # Clickable typed-signature renderer
```

## Related

- For 3D visualizations and the couplet playground, see
  [kranji-ui-3d](../kranji-ui-3d/README.md).
- The SVG rendering is provided by `BlockSvgRenderer` in `kranji-core`.
- The Associated index is built once at startup, keyed by glyph string so
  that distinct Java records sharing the same glyph still resolve to the
  same dependents.
