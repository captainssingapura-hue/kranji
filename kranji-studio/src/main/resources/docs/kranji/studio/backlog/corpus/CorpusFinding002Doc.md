# CF-002 — Meaning sits at the wrong cardinality

CD-001 makes `ZiCharUTF8` the hub and every other dimension a projection over
it. Its diagram already shows `meaning` as a spoke keyed on
`(character, phonic)`, and it records that meaning *left* `SimpleZi` for exactly
that reason.

**The decision was applied to the phonic tier and never to the structural one.**

## The state of it

| | `meaning()` | |
|---|---|---|
| `SimpleZi` (8,105) | removed | correct, and argued in the type |
| `Zi` interface | `String meaning()` | a field on a record |
| Singulars (549) | populated | one gloss per character |
| Components (30) | populated | one gloss per component |
| Composed (801) | `""` — every one | a field nobody could fill |

`SimpleZi` states the argument plainly:

> Meaning attaches to a *reading*, not to a character: 好 hǎo is *good* while 好
> hào is *to be fond of*… A per-character field sits at the wrong cardinality
> and can only be filled by merging senses that belong to different readings.
>
> It belongs to a facility keyed on `(character, phonic)`, supplied from outside
> this type.

**That facility does not exist.** No `Meaning` projection, no map keyed on the
pair, nothing queued in any plan. The two files matching `*Meaning*` are
per-class records for the character 义 and are unrelated.

## The 801 empty strings are the evidence, not the problem

They read as unpopulated data and are not. 好 is `LeftRight(女, 子)` with
`meaning()` returning `""`, and the phonic corpus declares it twice —
`D.syl(AO, THIRD, "好")` and `D.syl(AO, FOURTH, ZiDecl.alt("好"))`. Filling that
slot would force a choice between *good* and *to be fond of*, or a merge of the
two. Nobody filled them because the slot cannot be filled honestly.

CD-001's own rule is that **a new dimension is added as a projection function,
not as a field on an existing record**. `Zi.meaning()` is that field.

## The key already agrees

Three facilities arrived independently at the same pair:

```
known set        codePoint:reading
article census   codePoint:reading
meaning          (character, phonic)
```

Corroboration that the hub decision is right, and practically useful: a meanings
projection would join the known set and the census on a key all three already
speak, with no new format and no new dependency on the structural tier.

## Decided: leave it, do not use it

The existing stack stays untouched. `Zi.meaning()` keeps its 801 empty strings
and its 579 per-character glosses; nothing is migrated, nothing is deleted.

New work simply **does not read meanings from it**. A meanings facility, when it
is built, is keyed on the pair and sourced separately — meaning is
language-dependent, audience-dependent, and licensed apart from phonic data,
which is the other half of why it does not belong on these records.

The cost of that deferral, stated so it is a decision and not a surprise: the
549 singular glosses are real, correct data that a meanings facility will have
to re-source or import across the `String`/`ZiCharUTF8` seam, because they live
on the untouched side of it.

## Open

**May a composition hint use the component glosses?** 氵 *water*, 青
*blue-green* — these come from `SingularPart` and component records, not from a
composed record's empty field, and they are the payload that makes a hint say
something rather than merely draw a tree. They are also the same `meaning()`
method on the same untouched stack. Unresolved; needs a line drawn deliberately
rather than assumed either way.

**Are class-name glosses legitimate?** `QingClear` → *clear*, `BaoTreasure` →
*treasure*. Available for all 801, and a codegen artefact of unrecorded
provenance carrying one word, no part of speech and no register. Adjacent to
KI-001 on Unihan provenance. Not used; not ruled out.
