# KI-007 — One principal reading per character strains at 得

**Severity:** low · **Area:** corpus model · **Status:** open, a question rather than a defect

## What it is

`PhonicDeclarations` enforces that every character has **exactly one** principal
reading. The invariant is what makes the reader able to decide what to show
when nothing overrides it, and for 8,099 of 8,100 characters it is comfortable.

得 is the one that argues with it.

## Evidence

得 has three readings, all common, and the corpus counts are not close to a
tie-break — they point at the reading the model would *not* choose:

```
得  standard=dé, de, děi
    corpus: de 5096 · dé 1496 · děi 637
```

- 得到 *dédào* — to obtain
- 觉得 *juéde* — to feel
- 得走了 *děi zǒule* — must go

There is no reading here that is "the" reading of 得. Whichever is principal,
the catalogue will be wrong about the character two-thirds of the time.

Two more sit nearby without being as sharp: 谁 (*shéi* spoken, *shuí* written,
corpus shows *shuí* 1065) and 似 (*sì* in 似乎, *shì* in 似的, corpus 599 to 401).

## What it costs to leave

Very little in the catalogue, which is a reference — a child looking up 得
should reasonably find *dé*, the citation reading. It costs more in the
**reader**, where showing *dé* over 觉得 would simply be wrong.

## The question it actually raises

Whether "principal reading" is a property of a character at all, or only ever
a default for display in the absence of context. If the latter, the annotation
layer should carry more of the load and the catalogue's principal is a
convenience rather than a fact.

The current answer — principal in the catalogue, per-occurrence `{háng}`
markup in articles — is probably right. 得 is the case that will test it, and
it is worth revisiting once real articles exist rather than deciding now.

## Related

Decision `pc5` of Full Phonic Coverage. The override file exists so this can be
changed without regenerating anything.
