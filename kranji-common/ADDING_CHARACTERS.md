# Adding New Composed Characters

This guide explains how to add a new composed character (`ComposedZi`) to the
`kranji-common` module.

## Prerequisites

1. **Check the character doesn't already exist.** Use the `ZiLookup` tool to
   check a batch of characters at once. Create a UTF-8 text file with
   comma-separated characters and run:

   ```bash
   mvn -pl kranji-core-demos exec:java \
       -Dexec.mainClass=kranji.demos.ZiLookup \
       -Dexec.args="zi-check.txt"
   ```

   This writes a result file (`zi-check-result.txt`) listing each character as
   Found (with its type and source class) or Missing. You can also specify an
   explicit output path as the second argument.

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
└─ 明  (Depth1StrokesHigh.MING.structure(), reusing the depth-1 明)
```

### No inline `new ComposedBlock(...)` inside a ComposedZi

Every slot in a ComposedZi constructor must be a **named reference** — never
an anonymous inline `new LeftRight(...)`, `new TopBottom(...)`, etc.  The only
`new` keywords allowed are:

- `new ComposedZi(...)` — the character itself (the top-level construction)
- `new PhonoSemantic(...)`, `new CompoundIndicative(...)`, etc. — etymology

If a sub-component is a recognised character (e.g. 却 in 脚, 佳 in 雅), use
its existing constant:

```java
// 脚 — 月 + 却   (却 already defined as Depth1Strokes7.QUE)
new LeftRight(NatureElements.YUE, Depth1Strokes7.QUE.structure())
```

If the sub-component is a structural grouping without its own character entry,
add a named constant to `CommonBlocks` (`kranji.common.CommonBlocks`):

```java
// CommonBlocks.java
public static final TopBottom DAO_XIAO =
        new TopBottom(RadicalComponents.DAO_TOP, PeopleAndRoles.XIAO);

// Then in 你's definition:
new LeftRight(DAN_REN_PANG, CommonBlocks.DAO_XIAO)
```

Run the **BlockLookup audit** after adding characters to check for violations:

```bash
mvn -pl kranji-core-demos exec:java \
    -Dexec.mainClass=kranji.demos.BlockLookup \
    -Dexec.args="--audit"
```

The audit output should show **zero** inline patterns (except for special
cases like 𰻝 that are intentionally left inline).

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
it via its stroke file, e.g. `Depth1StrokesHigh.LIN.structure()` for 林.

Each depth level is split into **per-stroke-count files**:

| Depth | Stroke file pattern | Aggregator |
|-------|---------------------|------------|
| 1 | `Depth1Strokes3`, `Depth1Strokes4`, ..., `Depth1StrokesHigh` | `Depth1.ALL` |
| 2 | `Depth2Strokes6`, `Depth2Strokes7`, `Depth2StrokesHigh` | `Depth2.ALL` |
| 3 | `Depth3` (not yet split) | `Depth3.ALL` |
| 4 | `Depth4` (not yet split) | `Depth4.ALL` |
| 5 | `Depth5` (not yet split) | `Depth5.ALL` |

Place the new constant in the stroke file matching its total stroke count.
Each stroke file has its own `ALL` list. The aggregator class combines them
automatically. If no stroke file exists yet for that count, create one
following the existing pattern (package, imports, `py()` helper, constants,
`ALL` list).

## Step 4: Add the ComposedZi constant

Simple case — all components are existing named constants:

```java
/** 萌 (méng) — sprout. TopBottom: 艹(semantic) + 明(phonetic, depth-1). */
public static final ComposedZi MENG = new ComposedZi(
        lit("萌"),                   // or uni("\u840C")
        List.of(py(Initial.M, Head.OPEN, Body.E, Tail.NG, Tone.SECOND)),
        11,       // stroke count
        140,      // radical number (Kangxi)
        "",       // meaning (optional)
        new TopBottom(CAO_ZI_TOU, Depth1StrokesHigh.MING.structure()),
        new PhonoSemantic(CAO_ZI_TOU, Depth1StrokesHigh.MING.structure())
);
```

The first argument is wrapped with `lit()` (literal glyph) or `uni()` (Unicode
escape) via the `ZiChar` sealed interface.  Both compile to the same string;
the wrapper exists for source-code readability.  Each depth file provides
these helpers:

```java
private static ZiChar lit(String s) { return new ZiChar.Literal(s); }
private static ZiChar uni(String s) { return new ZiChar.Unicode(s); }
```

Nested case — a sub-component needs a `CommonBlocks` constant:

```java
/** 品 (pǐn) — product/taste. TopBottom: 口(top) + 口口(bottom). */
public static final ComposedZi PIN = new ComposedZi(
        lit("品"),
        List.of(py(Initial.P, Head.OPEN, Body.I, Tail.N, Tone.THIRD)),
        9, 30, "",
        new TopBottom(BodyParts.KOU, CommonBlocks.DOUBLE_KOU),
        new CompoundIndicative("口(mouth) × 3 → tasting, judging quality")
);
```

Note how `CommonBlocks.DOUBLE_KOU` replaces an inline
`new LeftRight(BodyParts.KOU, BodyParts.KOU)`. See Step 4a below for when
and how to add new `CommonBlocks` entries.

Constructor parameters:
1. **character** — the character string (use Unicode escapes `\uXXXX`)
2. **pinyin** — `List<PinyinSyllable>` (use the `py()` helper)
3. **strokes** — total stroke count
4. **radicalNo** — Kangxi radical number
5. **meaning** — English meaning (can be empty)
6. **structure** — the `ComposedBlock` tree (the spatial layout)
7. **etymology** — `EtymologicalCategory` (PhonoSemantic, CompoundIndicative, etc.)

### Step 4a: No inline `new ComposedBlock(...)` — use named references

The only `new` keywords allowed inside a `ComposedZi(...)` constructor are:

| Allowed | Example |
|---------|---------|
| The top-level structure | `new TopBottom(...)` |
| Etymology categories | `new PhonoSemantic(...)`, `new CompoundIndicative(...)` |

**Not allowed:** nested `new ComposedBlock(...)` inside another ComposedBlock
slot. Every sub-component must be a named reference.

When a sub-component is itself a recognised character, reference the existing
constant directly:

```java
// 佳 already exists as AbstractConcepts.JIA_FINE (SingularZi)
new LeftRight(BodyParts.YA, AbstractConcepts.JIA_FINE)

// 却 already exists as Depth1Strokes7.QUE (ComposedZi)
new LeftRight(NatureElements.YUE, Depth1Strokes7.QUE.structure())
```

Use the **BlockLookup** tool to find existing constants for a component:

```bash
# Create input file with glyphs to look up, then:
mvn -pl kranji-core-demos exec:java \
    -Dexec.mainClass=kranji.demos.BlockLookup \
    -Dexec.args="input.txt"
```

When no named constant exists for a sub-component (i.e. it is a structural
grouping that is not a standalone character), add a constant to
`CommonBlocks` (`kranji.common.CommonBlocks`):

```java
// In CommonBlocks.java:
/** ⺈小 — knife-top over small (≈ 尔), right side of 你. */
public static final TopBottom DAO_XIAO =
        new TopBottom(RadicalComponents.DAO_TOP, PeopleAndRoles.XIAO);
```

Then reference it in the ComposedZi definition:

```java
new LeftRight(DAN_REN_PANG, CommonBlocks.DAO_XIAO)
```

After adding characters, run the **BlockLookup audit** to verify no inline
compositions remain:

```bash
mvn -pl kranji-core-demos exec:java \
    -Dexec.mainClass=kranji.demos.BlockLookup \
    -Dexec.args="--audit"
```

The output should show zero inline patterns (excluding intentional special
cases like 𰻝).

## Step 5: Add to the ALL list

Append the new constant to the `ALL` list at the bottom of the depth file.

## Step 6: Build and verify

```bash
mvn clean install -q
```

Verify the new characters are registered by running ZiLookup against an input
file containing the characters you just added:

```bash
mvn -pl kranji-core-demos exec:java \
    -Dexec.mainClass=kranji.demos.ZiLookup \
    -Dexec.args="zi-check.txt"
```

The result file should show all characters as Found.

## Step 7: Audit for inline compositions

Run the BlockLookup audit to ensure no anonymous inline `new ComposedBlock`
patterns were introduced:

```bash
mvn -pl kranji-core-demos exec:java \
    -Dexec.mainClass=kranji.demos.BlockLookup \
    -Dexec.args="--audit"
```

The output should list only the 5 known 𰻝 patterns. If new patterns appear,
extract them as described in Step 4a.

You can also use BlockLookup in **lookup mode** to find the Java constant for
any glyph:

```bash
# Create a comma-separated or newline-separated input file, then:
mvn -pl kranji-core-demos exec:java \
    -Dexec.mainClass=kranji.demos.BlockLookup \
    -Dexec.args="input.txt"
```

Finally, check the SVG rendering in the UI for correct layout and glyph
alignment:

```bash
mvn exec:java -pl kranji-ui-demo -Dexec.mainClass=kranji.ui.demo.KranjiDemoApp
```

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
