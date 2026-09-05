# The Update Path

What happens between a child tapping *Known* and the screen agreeing with them.

Marking is the action a reader repeats most, so it is the one place where the
app must not stutter. Today it does, and the reason is not that the work is
hard — it is that the work is done six times, on the main thread, and half of
it rebuilds markup that did not change.

## Where it stands

Measured in the running app, one claim, real events.

| Workspace | Panes open | Synchronous |
|---|---|---|
| Reading | Library, Reader, Progress | **13.3 ms** |
| Known Characters | Sounds known, Characters, Character, Progress | **43.1 ms** |

A frame is 16 ms. The second figure is three frames dropped on the commonest
gesture in the app, on a desktop; the tablet a child actually holds is slower.

Two separate faults produce it.

### The claimed set is rebuilt once per article

`ReadabilityModule.of()` opens with

```js
var claimed = {};
for (var i = 0; i < k.length; i++) claimed[k[i]] = true;
```

and the catalogue calls `of()` once per article. The cost is therefore
**O(articles × readings-known)** — not O(articles × pairs-in-article), which is
what the arithmetic actually needs. Ranking the whole library:

| Readings known | As written | Claimed set hoisted |
|---|---|---|
| 0 | 0.8 ms | 0.3 ms |
| 500 | 5.8 ms | 0.4 ms |
| 1,000 | 8.9 ms | 0.5 ms |
| 2,000 | **23.0 ms** | **0.4 ms** |

The left column grows with the child's progress *and* with the library. Both
multipliers are live: the library went from 23 articles to 475 in one sitting,
and a reader who sticks with it passes 2,000 readings. Extrapolating the shape,
5,000 articles at 2,000 known is around a quarter of a second per tap.

### The grids are rebuilt when nothing structural changed

`KnownSoundsWidget.render()` destroys its grid and constructs a new one on every
`KnownChanged`:

```js
if (grid) { grid.destroy(); grid = null; }
...
grid = new RelationGrid({ container: host, branch: freshCellsBranch(), ... });
```

1,288 rows torn down and rebuilt to change two numbers in one of them. The
arithmetic behind those rows is not the problem — measured at **1.2 ms** for all
1,288 syllables against 1,768 known readings. The other ~29 ms is DOM.

Nothing about the structure changed. The same 1,288 syllables are present before
and after, in the same order, with the same columns.

## The three faults are separable

**One.** The claimed set is rebuilt per article. An algorithm error; nothing to
do with architecture.

**Two.** Structure is rebuilt when only values changed. A misuse of the grid,
which already offers the right call.

**Three.** Six subscribers each derive overlapping facts from one broadcast, on
the main thread. An architecture question, and the only one of the three that
needs new machinery.

They are listed in that order deliberately. Fixing three without one and two
would move a wrong algorithm somewhere less visible and keep rebuilding the
markup once it arrived.

## The shape it should have

### A claim is a store mutation, not a UI event

The known set already *is* a store — `KnownSecretary` holds the authoritative
in-memory copy, `KnownStore` holds the durable one in IndexedDB, and
`KnownPersistence` reconciles them. What it lacks is a single place that turns a
mutation into **derived facts**, so every pane derives its own:

| Pane | derives |
|---|---|
| Reader | is this (character, reading) claimed |
| Library | readability, band and unknown count per article |
| Sounds known | claimed count per syllable |
| Progress | characters, band, distance to the next |
| Known | one row per claimed reading |
| Character | claimed state of ≤ 4 rows |

Every one of those is a function of the same set and the same corpus. Computed
once at the owner and published, a pane applies rather than derives.

### An update is a value, not a repaint

`RelationGrid` already draws the distinction, in its own words:

```
── the direct update path (domain → cell; no layout, no lookup) ──
/** Queue a domain-pushed value; coalesced per frame, last write wins. */
updateCell(pk, col, newValue)
/** Drain the batch NOW — every read-path (copy, export) must, because a
 *  hidden page gets no frames and would otherwise read stale cell state. */
flushNow()
```

Structure changes go through `addRow` / `removeRow`; values go through
`updateCell`, batched per frame. The published payload from the owner is then
close to literally a list of `(pk, column, value)` — which is what the grid
consumes, so the seam falls out rather than being invented.

### Then, and only then, a worker

Once the owner is a pure function from (set, corpus) to values, it can run off
the main thread. The pieces are already shaped for it: `KnownSetModule`,
`KnownSoundsModule`, `KnownProgressModule` and `ReadabilityModule` are each
documented and tested as *pure — no DOM, no fetch, no clock*, precisely so they
run under GraalVM in ordinary JUnit. They would run in a worker unchanged.

What a worker is **not** for here: making 23 ms of wrong arithmetic finish
elsewhere. What it *is* for:

- **Bulk arrivals.** Import of a whole record, or first load, where the census
  must be parsed and every article ranked before anything can paint. At 475
  articles the census is 264 KB; at ten thousand it is megabytes, and the parse
  blocks before the arithmetic starts.
- **Headroom.** Hoisted ranking is O(total pairs in the library) — 0.4 ms at 475
  articles, single-digit ms at ten thousand. Comfortable on a desktop, worth
  moving on a tablet.
- **Owning the store.** IndexedDB works in workers, so the worker can hold the
  set *and* its durable copy, which removes the reconciliation between two
  in-memory truths. That is a correctness argument before it is a speed one.

There is no worker anywhere in this codebase today — no `Worker`, no
`postMessage`, no `BroadcastChannel`, and no framework story for serving a
module graph into one. That is new infrastructure, and it is why it comes last.

## What must not break

The reason to write this down is that the app works now. A rewrite that ends
with a faster app that reads worse has cost more than it bought.

1. **The set stays keyed on (character, reading).** No re-graining, in any
   layer. See *The Known Set*.
2. **Marking stays explicit and reversible.** No derived state ever writes back
   into the set. The owner computes *from* the set and never *to* it.
3. **Nothing leaves the device.** A worker is still the device; this must not
   become an excuse for a sync endpoint. See *Where Progress Lives*.
4. **A pane that mounts late is still correct.** Guaranteed today because every
   broadcast carries the whole set. Any incremental scheme must keep it: a late
   joiner gets a snapshot, never a diff it missed.
5. **A failed save is still announced unprompted.** Marking looks identical
   whether or not it persisted, and what is lost is discovered a week later.
6. **The cursor survives an update.** A rebuilt grid resets its cursor and
   selection; a reader who has arrowed to a row and marks it should not be sent
   back to the top. Today's rebuild loses this, so it is a fix, not a risk — but
   it must be checked rather than assumed.

## How we will know

Each step carries a before and an after, measured the same way in the same two
workspaces. The gate is arithmetic, not judgement:

- ms per claim, Reading workspace — **13.3 ms** today
- ms per claim, Known Characters — **43.1 ms** today
- ms to rank the library at 2,000 readings known — **23.0 ms** today
- ms per claim against a synthetic library ten times the size — not yet measured

The last one is deliberately unmeasured. It is the number that decides whether
the worker is worth its infrastructure, and it should be a measurement before it
is an argument.
