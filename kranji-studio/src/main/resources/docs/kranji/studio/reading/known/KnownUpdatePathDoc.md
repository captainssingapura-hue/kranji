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

Two faults account for the milliseconds. The sweep that followed found nine
more that cost nothing measurable and matter more — they are inventoried below.

### There is no set, so everyone builds one

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

This looked at first like a hoisting mistake — a loop-invariant left inside the
loop. It is not. The known set is a **plain array**, and `KnownSetModule.has()`
is `known.indexOf(k) >= 0`. There is no membership structure anywhere, so every
consumer that needs one builds it: readability per article, the toggle before it
sends, the Secretary again to apply it. Hoisting the loop would fix one of those
four. The array is the fault.

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

## Eleven misuses, one mistake

A sweep of every site that marks, watches, seeds, saves or repaints against the
known set turned up eleven. The first two are correctness, not architecture.

### Correctness

**1. Whether a mark reaches the disk depends on which panes are open.** Seven
components start a persistence engine; exactly two ever call `store.changed(…)`
— `KnownTransferWidget` and `ZiDetailWidget`. The Reader *claims*
(`claims: true`) and never saves. Panes mount and unmount at runtime — they have
`leave()` and `dispose()` — and nothing anywhere asserts that a saver is
present. If this works today it works by coincidence of layout.

**2. The save failure is swallowed.** `createKnownWatch` starts persistence with
`onProblem: function () {}`, in the two panes most likely to be open. That is
invariant 5 below, already broken.

### The party is the store

**3.** The set lives in `KnownSecretary.initial` — party infrastructure, so
nothing can read it without joining a bus.

**4.** A write is a message (`MarkKnown`), not a call. There is no store API.

**5.** A read is a message *too*: `WhatIsKnown` returns nothing and triggers a
broadcast **to every member**. Six panes ask on join, so opening a workspace
costs six full fan-outs before anything is marked.

**6.** Seven persistence engines on one database, each with its own `loaded`,
`pending` and `broken`, each `put`-ing the whole profile row.

### The message shape

**7.** A delta is published and nobody reads it. The Secretary sets
`changed: key` on every broadcast; no subscriber references `msg.changed`. All
six read `msg.known` and re-derive from scratch.

**8.** Every broadcast carries the whole set — ~2,000 strings, per member,
including on every join. That is what makes a late pane correct today, so it
cannot simply be deleted.

### No set

**9.** It is an array, everywhere: `indexOf` in `has()`, in mark, in unmark, in
`without()`, and an O(n·m) union in `SeedKnown`, `ImportKnown` and `UndoImport`.

**10.** So each consumer builds its own index — including `toggle()`, which
scans to decide direction before sending, after which the Secretary scans again
to apply it. Two O(n) passes to flip one key.

### Repainting

**11.** `ArticleCatalogueWidget.draw()` deep-clones the tree, measures all 475
articles — including collapsed ones — and rebuilds the DOM.
`KnownSoundsWidget.render()` destroys and rebuilds 1,288 rows.

### They are one mistake

The known set is durable shared state modelled as a conversation between UI
panes. Once that is the frame, all eleven follow: durability rides on a pane so
it is optional; the error handler is a pane's business so a pane can decline it;
the state lives in the bus that carries it; a read becomes a broadcast; seven
panes each keep their own copy of the persistence rule; the delta is published
as a courtesy and ignored, because subscribers were never given a store to apply
it to; membership is a scan because an array is what serialises onto a message;
and each subscriber repaints, because a message says *something happened*
rather than *this value is now that*.

The counter-example is in this codebase and it is right. The Reader's own
handler is `onChanged: function () { … applyCells(); fit.refresh(); }`, and its
comment says: *a change is a restyle, not a reload — applyCells repaints the
squares in place, so pinyin leaving every 行走 cannot move the child's place on
the page*. The pattern was already here. It just was not followed outside that
pane.

## The shape it should have

### A claim is a store mutation, not a UI event

The pieces of a store exist and none of them owns anything. `KnownSecretary`
holds an in-memory copy inside a message bus, `KnownStore` can write IndexedDB,
and `KnownPersistence` holds the rule for reconciling them — instantiated seven
times, by whichever panes happen to be open. What is missing is the thing that
**owns** the set, the disk and the failure, and answers a read by returning.
Because nothing does, every pane derives its own facts:

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

## How we will get there: demolish, then repair

The first version of this plan was six incremental phases, each revertible, each
leaving the app working throughout. That was the wrong shape, and the inventory
is what showed it: eleven items that are one mistake cannot be repaired eleven
times, because each repair would be shaped by the mistake it was repairing. An
incremental migration also needs the old frame to keep working while the new one
arrives, so both exist at once and the bridging compromises outlive the
migration.

So: **build the store, remove all eleven at once, and let it break.**

1. **Make every break loud.** Two of the eleven are already silent failures. A
   demolition that adds more of those cannot be repaired from what it broke, so
   this comes first and stays afterwards.
2. **Stand up the store.** Wired to nothing — dead code at the end of the phase,
   which is what makes the next step a removal rather than a rewrite.
3. **Remove the misuses.** All of them. Features stop working; that is the
   expected outcome, not a sign it went wrong. The hardest instruction in the
   plan is *do not repair anything in this phase*.
4. **Take stock.** Write down what actually broke, ranked by what a reader would
   miss first. Observed, not predicted — guessing this list in advance would
   have made the exercise pointless.
5. **Repair from that list**, one feature per commit.

The grid update path, the membership structure and the single owner are not
phases in this plan. They are what the code looks like the second time it is
written.

The break window is one branch. Nothing between the demolition and the end of
the repair goes near `main`.

## What must not break

Features will break here, deliberately and temporarily. These are the things
that must survive the whole exercise — a rewrite that ends with a faster app
that reads worse has cost more than it bought.

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
   Already broken — see misuse 2 — so this is a repair, not a guard.
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
