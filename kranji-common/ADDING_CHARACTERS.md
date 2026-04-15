# Adding New Composed Characters

This guide explains how to add a new composed character (`ComposedZi`) to the
`kranji-common` module.

## Prerequisites

1. **Check the character doesn't already exist.** Search `kranji-singulars`
   (for standalone depth-0 characters) and `kranji-common/depth*` (for composed
   characters) before adding. A character may already be defined as a
   `SingularZi` in one of the singular families, or as a `ComposedZi` at a
   different depth level than you'd expect.

2. **All leaf components must be pre-defined.** Every component used in a
   character's structural decomposition must be an existing `SingularBlock`
   record. The `SingularBlock.plain()` fallback has been removed — all building
   blocks must be explicitly catalogued before they can be used in compositions.

## Step 1: Determine the structural decomposition

Break the character into its component tree. Each leaf must be an existing
`SingularBlock` (from `kranji-singulars` or `kranji-core/component/basic`).

Example for 萌 (méng, sprout):
```
萌 — TopBottom
├─ 艹  (CAO_ZI_TOU, from BasicComponents)
└─ 明  (Depth1.MING.structure(), reusing the depth-1 明)
```

## Step 2: Check that all leaf components exist

Search for each component glyph in:

| Module | Package | Contains |
|--------|---------|----------|
| `kranji-singulars` | `kranji.singular.*` | Standalone characters (SingularZi): 日, 月, 木, 口, ... |
| `kranji-core` | `kranji.component.basic` | Radical/part variants (SingularPart): 氵, 亻, 艹, 辶, ... |
| `kranji-singulars` | `kranji.singular.radicals` | Radical components: 乚, 卩, 丿, ... |

If a component is missing, **create it first** (see Step 2a below).

### Step 2a: Creating a new SingularBlock

Add a new record to the appropriate family class in `kranji-singulars`:

| Category | Class |
|----------|-------|
| Nature / seasons / terrain | `NatureElements` |
| Body parts | `BodyParts` |
| People / roles | `PeopleAndRoles` |
| Animals | `Animals` |
| Actions / states | `ActionsAndStates` |
| Tools / vessels | `ToolsAndVessels` |
| Materials / colours | `Materials` |
| Abstract concepts | `AbstractConcepts` |
| Numbers / measure | `NumbersAndMeasure` |
| Space / direction | `SpaceAndDirection` |
| Structures / buildings | `Structures` |
| Plants / agriculture | `PlantsAndAgriculture` |
| Radical components | `RadicalComponents` |

Follow this template:

```java
/** 春 — spring (season). */
public record Chun_Spring() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
    @Override public String glyph()      { return "\u6625"; }
    @Override public String meaning()    { return "spring"; }
    @Override public String pinyinText() { return "ch\u016Bn"; }
    @Override public int strokes()       { return 9; }
    @Override public BasicSet library()  { return BasicSet.INSTANCE; }
}
public static final Chun_Spring CHUN_SPRING = new Chun_Spring();
```

- Use `SingularZi` for standalone characters people would learn.
- Use `SingularPart` for radical variants / components not used alone.
- Add the constant to the class's `ALL` list.
- Use Unicode escapes (`\uXXXX`) for the glyph and pinyin diacritics.

### Optional: politeness and hints

For components that behave differently in certain positions, override
`politeness(BlockRole)` and/or `hintFor(BlockRole)`:

```java
@Override
public Politeness politeness(BlockRole role) {
    if (role instanceof LeftRight.Left)   return Politeness.YIELDING;
    if (role instanceof TopBottom.Bottom) return Politeness.YIELDING;
    return Politeness.NEUTRAL;
}
```

## Step 3: Determine depth and target file

Depth = max(depth of all direct components) + 1, where:
- `SingularBlock` = depth 0
- `ComposedBlock` of only singulars = depth 1
- etc.

If a component is itself a `ComposedZi` from a lower depth level, reference
it via `DepthN.CONSTANT.structure()` (e.g. `Depth1.LIN.structure()` for 林).

| Depth | File |
|-------|------|
| 1 | `kranji.common.depth1.Depth1` |
| 2 | `kranji.common.depth2.Depth2` |
| 3 | `kranji.common.depth3.Depth3` |
| 4 | `kranji.common.depth4.Depth4` |
| 5 | `kranji.common.depth5.Depth5` |

Within each depth file, characters are further organized by **stroke count**.
Place the new constant under the matching `// ── N strokes` section header.
If no section exists yet for that stroke count, create one in ascending order.

The `ALL` list at the bottom of the file also groups entries by stroke count
with inline comments. Add the new constant in the correct position.

## Step 4: Add the ComposedZi constant

```java
/** 萌 (méng) — sprout. TopBottom: 艹(semantic) + 明(phonetic, depth-1). */
public static final ComposedZi MENG = new ComposedZi(
        "萌",
        List.of(py(Initial.M, Head.OPEN, Body.E, Tail.NG, Tone.SECOND)),
        11,       // stroke count
        140,      // radical number (Kangxi)
        "",       // meaning (optional)
        new TopBottom(CAO_ZI_TOU, Depth1.MING.structure()),
        new PhonoSemantic(CAO_ZI_TOU, Depth1.MING.structure())
);
```

Constructor parameters:
1. **character** — the character string
2. **pinyin** — `List<PinyinSyllable>` (use the `py()` helper)
3. **strokes** — total stroke count
4. **radicalNo** — Kangxi radical number
5. **meaning** — English meaning (can be empty)
6. **structure** — the `ComposedBlock` tree (the spatial layout)
7. **etymology** — `EtymologicalCategory` (PhonoSemantic, CompoundIndicative, etc.)

## Step 5: Add to the ALL list

Append the new constant to the `ALL` list at the bottom of the depth file.

## Step 6: Build and verify

```bash
mvn clean install -q
mvn exec:java -pl kranji-ui-demo -Dexec.mainClass=kranji.ui.demo.KranjiDemoApp
```

Check the SVG rendering in the UI for correct layout and glyph alignment.

## Pinyin quick reference

The `py()` helper constructs a `PinyinSyllable` from:
- `Initial` — consonant onset (B, P, M, F, D, T, N, L, G, K, H, J, Q, X, ZH, CH, SH, R, Z, C, S, ZERO)
- `Head` — medial glide (OPEN, I, U, V)
- `Body` — vowel nucleus (A, O, E, E_CARON, I, U, V, ER, NULL)
- `Tail` — coda (NONE, VOWEL_I, VOWEL_U, N, NG)
- `Tone` — (FIRST, SECOND, THIRD, FOURTH, NEUTRAL)

## Composition types

| Type | Description | Example |
|------|-------------|---------|
| `LeftRight` | Side by side | 明 = 日 + 月 |
| `TopBottom` | Stacked | 字 = 宀 + 子 |
| `LeftMiddleRight` | Three columns | — |
| `TopMiddleBottom` | Three rows | 器 = 口口 + 犬 + 口口 |
| `FullEnclosure` | Outer wraps inner | 国 = 囗 + 玉 |
| `SemiEnclosureUpperLeft` | ⌐ wraps content | 魔 = 广 + ... |
| `SemiEnclosureUpperRight` | ¬ wraps content | — |
| `SemiEnclosureBottomLeft` | ∟ wraps content | 遨 = 辶 + 敖 |
| `SemiEnclosureTopThree` | ⊓ wraps content | — |
| `SemiEnclosureBottomThree` | ⊔ wraps content | — |
| `SemiEnclosureLeftThree` | ⊏ wraps content | — |
