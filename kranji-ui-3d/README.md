# kranji-ui-3d

Two JavaFX 3D applications sharing a common rendering toolkit:

1. **Kranji 3D Explorer** — visualizes individual characters from the corpus
   in four different 3D modes.
2. **Couplet Playground** — type two lines of a Chinese couplet (上联 /
   下联) and watch the characters arrange themselves under a live force
   simulation you can drag, restyle, and refont.

Both share the same low-level kit under `graph/`: glyph extrusion, gradient
textures, font probing, and a D3-style continuous force simulation
(`LiveSpringMode`) extracted into pure-array primitives (`ForceSim`).

## Launching

### Kranji 3D Explorer (default)

```bash
mvn -pl kranji-ui-3d javafx:run
```

### Couplet Playground (`couplet` profile)

```bash
mvn -pl kranji-ui-3d -Pcouplet javafx:run
```

Both apps require Java 21 + Maven 3.9+ and pull JavaFX in via the parent
POM.

---

## Kranji 3D Explorer

A floating top bar lets you switch glyph, depth offset, color, and
visualization mode. Drag empty space to orbit, scroll to zoom.

### Modes

| Mode | What it shows |
|---|---|
| **Layered extrusion** | Each block in the composition becomes a coloured slab; deeper nesting steps toward the camera. The depth-offset slider controls slab spacing. *Exploded* mode pulls the slabs apart for clarity. |
| **Component graph** | Focal character + its immediate components + immediate users, laid out by a one-shot 3D force-directed algorithm. Click any neighbor to refocus. |
| **Spring network** | Every named sub-block of the focal character connected by springs (intra-character only). |
| **Interactive (drag)** | Same intra-character graph, but the force simulation runs continuously and you can drag any node. The root component is anchored. |

### Controls

- **Glyph** field — type a single character and press Enter.
- **Depth offset** slider — only affects Layered extrusion.
- **Color** picker — glyph fill colour.
- **Mode** — see table above.
- **Exploded** checkbox — Layered extrusion only.

### Camera

`CameraRig` provides orbit-on-drag and wheel zoom for static modes; the
Interactive mode resets to head-on at start so the simulation plane is
visible immediately. Node clicks consume the event so dragging a node
moves the node and dragging empty space orbits.

---

## Couplet Playground

A 3D rendition of a 对联. Type the upper (上联) and lower (下联) lines,
press **Render**, and the characters appear as extruded glyphs in two
columns connected by faint springs. Drag any character to reshape the
structure — the rest reflows under the same simulation used by the
explorer's Interactive mode.

The two lines are **independent chains** (no horizontal pair edges), so
dragging one line doesn't pull the other.

### Live controls

| Control | Effect |
|---|---|
| **上联 / 下联** text fields | The two lines of the couplet. Press Enter or click Render to update. |
| **Elasticity** slider | Spring stiffness only — rest length stays put. Left = rigid (10× stiffness), middle = default, right = stretchy (0.1×). The simulation reheats on every change. |
| **Texture** drop-down | Procedural gradient applied to each glyph stroke: *Solid*, *Ink wash*, *Cinnabar*, *Jade*, *Gold leaf*. |
| **Font** drop-down | Lists CJK-capable fonts actually installed on the machine (probed via `Font.getFamilies()`). |

### Defaults

- Default couplet: 天增岁月人增寿 / 春满乾坤福满园 (a classic Spring Festival 春联).
- Default texture: Solid (uses the glyph color).
- Default font: first available from a curated CJK list (typically
  *Microsoft YaHei* on Windows).

### Installing more fonts

The Font drop-down is just an intersection of `Font.getFamilies()` with a
curated CJK list. To get more options:

- **Windows**: install via *Settings → Personalization → Fonts* (drop a
  `.ttf`/`.otf` file). Recommended free CJK fonts:
  - [Noto Sans CJK SC](https://fonts.google.com/noto/specimen/Noto+Sans+SC)
  - [Noto Serif CJK SC](https://fonts.google.com/noto/specimen/Noto+Serif+SC)
  - [Source Han Sans / Serif](https://github.com/adobe-fonts/source-han-sans/releases)
- **macOS**: double-click the font file → *Install Font*.
- **Linux**: drop into `~/.local/share/fonts/` and run `fc-cache -fv`.

After install, restart the app — JavaFX reads the system font registry
once at startup. New fonts that match the curated CJK list (see
`GlyphFonts.CANDIDATES`) appear automatically.

---

## Architecture

```
graph/                          shared 3D kit
├── GlyphNode3d.java            stacked-Text glyph extrusion + spring cylinder
├── GlyphTextures.java          gradient paint catalogue (no asset files)
├── GlyphFonts.java             CJK-capable font probing + curated list
├── ForceSim.java               pure-array force primitives (repulsion,
│                               attraction, integration, separation)
├── ForceLayout3d.java          one-shot static layout for the explorer's
│                               Component graph + Spring network modes
├── LiveSpringMode<N>.java      D3-style continuous force sim, generic in
│                               node type — drives both Interactive mode
│                               and the Couplet Playground
├── ComponentGraph + Renderer   focal-character corpus graph
├── StructureGraph + Renderer   intra-character spring network
└── Edge<N>.java                tiny edge record

couplet/                        couplet playground
├── CoupletApp.java             top bar + 3D viewport + live controls
├── CoupletGraph.java           string → Position graph + initial layout
└── CoupletLauncher.java        entry point for the -Pcouplet profile

Kranji3dApp.java                explorer top-level — mode switching
Kranji3dLauncher.java           explorer entry point (default profile)
BlockExtrusionRenderer.java     layered slab extrusion
CorpusIndexes.java              corpus + immediate-users index
CameraRig.java                  orbit + zoom + reset-head-on camera
```

### Why share `LiveSpringMode<N>`?

Both the explorer's Interactive mode and the Couplet Playground need:
- Continuous force-directed simulation
- Per-frame friction + alpha decay (D3-style)
- Hard rigid-body collision constraint (no two glyphs overlap)
- Camera-aligned drag projection (mouse delta → world plane)
- Anchor / pin semantics (anchors only move under user drag)

`LiveSpringMode<N>` is generic in the node type. The explorer plugs in
`BlockStructure`; the couplet plugs in `CoupletGraph.Position`. Force
math, collision, and drag handling are reused unchanged.

### File-size guideline

Per the project convention, individual files stay under ~250 LOC.
`LiveSpringMode` is the most feature-dense at the moment; if it grows
further, the next factor-out target is the camera-projected drag handler
and the alpha/temperature controller.

---

## Related

- [Root README](../README.md) — corpus, type system, build instructions.
- [kranji-ui-demo](../kranji-ui-demo/README.md) — the 2D explorer.
