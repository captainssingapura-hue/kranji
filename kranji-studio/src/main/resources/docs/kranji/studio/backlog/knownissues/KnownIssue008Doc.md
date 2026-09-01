# KI-008 — NodeName.slug collapses any CJK label to "n"

**Severity:** low · **Area:** framework interaction, addressing · **Status:** open, currently avoided

## What it is

Homing's `NodeName.slug(String)` builds an address segment by lowercasing and
replacing every character outside `[a-z0-9]` with `-`, then trimming. **A label
made entirely of Chinese characters therefore has nothing left**, and the
method's blank-guard returns the literal name `"n"`.

```java
public static NodeName slug(String label) {
    if (label == null) return new NodeName("n");
    String s = label.toLowerCase()
            .replaceAll("[^a-z0-9]+", "-")
            .replaceAll("(^-+)|(-+$)", "");
    if (s.isBlank()) s = "n";
    ...
}
```

Every CJK label produces `"n"`. Two such siblings collide silently.

## Why it has not bitten

Kranji does not use it for anything Chinese. Phonic tree segments are pinyin
(`h`, `ao`, `3`) and characters are addressed by **codepoint** (`u597d`), a
choice made for a different and better reason: a reading is corrigible data and
a codepoint is not.

So this is a hazard avoided by an unrelated decision, which is exactly the kind
that reappears when someone makes a reasonable-looking choice later.

## What it costs to leave

Nothing now. It bites the first time anyone builds a tree whose node names come
from Chinese labels — a catalogue of articles by title, a component index by
glyph, a known-characters view grouped by radical.

## What would fix it

Nothing in Kranji. **Never pass a Chinese label to `NodeName.slug`.** Derive
segments from something already address-safe: a codepoint, a pinyin syllable,
or an explicit identifier.

Whether the framework should reject rather than silently collapse is a question
for Homing, not for this repo.
