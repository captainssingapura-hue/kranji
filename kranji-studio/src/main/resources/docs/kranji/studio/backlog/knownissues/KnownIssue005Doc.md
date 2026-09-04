# KI-005 — The syllable data action rescans the whole registry

**Severity:** low · **Area:** reading app, serving · **Status:** **resolved**

## What it is

`ZiDataGetAction` answers `/zi-data?syllable=h.ao.3` by walking the entire
`SimpleZiRegistry` and testing every character's readings against the requested
segments. There is no index; the scan runs on every request.

```java
for (SimpleZi zi : SimpleZiRegistry.ALL) {
    for (PinyinSyllable phonic : zi.allPhonics()) {
        if (matchesSegments(phonic, initialSeg, finalSeg, tone)) { ... }
    }
}
```

## What it costs to leave

At 79 characters, nothing measurable. At 8,100 characters and 1,284 syllables
it is a full-corpus walk per character-panel activation, which the reader will
do every time a child opens a syllable.

Worth being honest about scale: this is thousands of comparisons, not millions.
It is wasteful rather than slow, and it has not been profiled.

## Resolved

`SyllableIndex` replaced the scan. `PinyinSyllable` is a record of
`(Initial, Final, Tone)` with structural equality, so the address IS the map
key - the action resolves a syllable and looks it up, with no walk at all.

The index is built once from the source partitions, measured at 83 ms for
8,100 characters, and held for the life of the process.

## What would have fixed it

Build the syllable index once — a `Map<PinyinSyllable, List<ZiCharUTF8>>` — and
serve from it. The phonic projection already computes exactly this grouping to
build the tree, so the index may simply be the projection's own intermediate,
lifted rather than recomputed.

## Related

Phase `pc-6` of Full Phonic Coverage, alongside KI-002.
