# Type Hierarchy

Kranji models a character as a recursive algebraic data type. Three concrete
kinds sit under the root abstraction.

| Kind | Meaning | Example |
|---|---|---|
| `SingularZi` | A standalone character that does not decompose further (独体字) | 人, 山, 日 |
| `ComposedZi` | A character built from other blocks (合体字) | 明, 清, 锁 |
| `SingularPart` | A bound radical — a component that is not itself a character (偏旁) | 氵, 扌, 亻 |

`SingularPart` earns its own type because a radical is not a degenerate
character. It has a glyph, a stroke count, and layout hints, but no pinyin and
no standing as a word.

## Structural composition

`CompositionLayout` is a sealed interface whose variants each name their slots
explicitly. Slots are the vocabulary — not indices.

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

Every slot holds another block, so compositions nest to arbitrary depth.

## Etymological category

The classical Six Writings (六书), also a sealed interface:

- **Pictograph** (象形) — a stylised drawing: 日, 月, 山
- **Simple indicative** (指事) — a symbol with an indicator mark: 上, 下
- **Compound indicative** (会意) — meaning from combined parts: 明 = 日 + 月
- **Phono-semantic** (形声) — semantic radical plus phonetic element: 清 = 氵 + 青
- **Derivative cognate** (转注) — extended meaning between related characters
- **Phonetic loan** (假借) — a character borrowed for its sound

## Typed slots

Each composed character implements its concrete layout interface with the slot
types filled in:

```java
public record Ming() implements ComposedZiT, LeftRightT<Ri, Yue> { }
```

The compiler now knows 明 is left-right, that its left is 日 and its right is 月.
Getting the order wrong is a compile error. A property test asserts that no
record ever degrades to a generic supertype, so the specificity cannot rot.

## Named intermediates

Deeply nested characters do not inline their whole subtree. An intermediate node
that carries meaning gets its own record and is referenced by name — the same
discipline a textbook uses when it writes *let d₁ = …* rather than substituting
the expression twice. 锁 references `SuoLock_Inner1` rather than restating it.

These synthetic nodes have no glyph of their own, which is why the registry
holds 36 records with a deliberately empty character.
