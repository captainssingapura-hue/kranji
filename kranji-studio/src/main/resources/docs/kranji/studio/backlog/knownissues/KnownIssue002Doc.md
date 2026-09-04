# KI-002 — The served tree payload grows with the corpus

**Severity:** low · **Area:** reading app, serving · **Status:** largely resolved

## What it is

`/zi-tree` serves the whole phonic projection in one response. Every terminal
carries a `note` listing **every glyph in that syllable**, so the payload grows
with the corpus even though the tree's *node count* does not.

## Evidence

| | now | at full coverage |
|---|---|---|
| terminals | 77 | 1,284 |
| characters | 79 | 8,100 |
| payload | **21,475 bytes** | **~350 KB** (extrapolated) |
| heaviest syllable | 4 glyphs | **74** glyphs (yì) |
| syllables over 20 glyphs | 0 | 57 |

The ~350 KB is an extrapolation from a single measured point, not a
measurement. It assumes the per-terminal cost stays roughly constant, which is
the assumption most likely to be wrong in either direction.

## What it costs to leave

At 79 characters, nothing. At full coverage a single 350 KB payload on every
tree load is wasteful, and the heaviest terminal renders a 74-glyph note into
a row that was designed for four.

## Resolved, mostly

The note is now capped at 12 glyphs with a count of the rest, and the
measured payload at full coverage is **242 KB** - not the ~350 KB extrapolated
here. The extrapolation assumed the per-terminal cost stayed constant; capping
the note is most of the per-terminal cost, so it did not.

What remains is that 242 KB is still one response, and lazily loaded children
would be the answer if it ever matters. It does not yet.

## What would fix it

Cap the note — show the first few glyphs and a count, and let the characters
widget carry the full list, which it already does. Notes are most of the
per-terminal bytes, so this should take a large bite out of the total.

If capping is not enough, the tree needs **lazily loaded children**. That is a
real feature rather than a tweak, and it would add a phase.

## Related

Phase `pc-6` of Full Phonic Coverage. Measure before choosing between the two
fixes.
