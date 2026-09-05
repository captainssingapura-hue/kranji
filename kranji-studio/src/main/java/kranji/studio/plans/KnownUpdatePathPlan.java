package kranji.studio.plans;

import hue.captains.singapura.js.homing.studio.base.tracker.Acceptance;
import hue.captains.singapura.js.homing.studio.base.tracker.Decision;
import hue.captains.singapura.js.homing.studio.base.tracker.DecisionStatus;
import hue.captains.singapura.js.homing.studio.base.tracker.Dependency;
import hue.captains.singapura.js.homing.studio.base.tracker.Metric;
import hue.captains.singapura.js.homing.studio.base.tracker.Objective;
import hue.captains.singapura.js.homing.studio.base.tracker.Phase;
import hue.captains.singapura.js.homing.studio.base.tracker.PhaseStatus;
import hue.captains.singapura.js.homing.studio.base.tracker.Plan;
import hue.captains.singapura.js.homing.studio.base.tracker.Task;

import java.util.List;

/**
 * Live tracker for the known-set update path.
 *
 * <p>Design is captured in <em>The Update Path</em>, under Reading → Known
 * Characters. This file tracks execution. It is the single source of truth:
 * edit, recompile, restart the server.</p>
 *
 * <p>Every phase carries a rollback, and that is the point of the plan rather
 * than a formality. The app works today; each step has to be revertible on its
 * own, so that a step which does not pay can be dropped without taking the ones
 * either side of it with it.</p>
 */
public final class KnownUpdatePathPlan implements Plan {

    public static final KnownUpdatePathPlan INSTANCE = new KnownUpdatePathPlan();

    private KnownUpdatePathPlan() {}

    @Override public String kicker()   { return "PERF"; }
    @Override public String name()     { return "The Update Path"; }
    @Override public String subtitle() {
        return "Source of truth: KnownUpdatePathPlan.java. Edit, recompile, restart the server.";
    }
    @Override public String summary() {
        return "Marking a reading known costs 43ms of blocked main thread and grows with both "
             + "the library and the child's progress. Fix the arithmetic, stop rebuilding "
             + "markup that did not change, give derived state one owner - and only then "
             + "consider a worker.";
    }

    @Override
    public List<Objective> objectives() {
        return List.of(
                new Objective("Marking never stutters",
                        "It is the action a child repeats most. A claim should cost less than "
                      + "one frame on the device a child actually holds, not three frames on a "
                      + "desktop."),
                new Objective("Cost stops tracking progress",
                        "Today a claim gets slower as a reader learns more, because ranking is "
                      + "O(articles x readings-known). The child who has worked hardest gets "
                      + "the worst app, which is exactly backwards."),
                new Objective("Cost stops tracking the library",
                        "The library went from 23 articles to 475 in one sitting and will grow "
                      + "again. Nothing on the marking path should be linear in article count "
                      + "if it does not have to be."),
                new Objective("Structure is not rebuilt for a value change",
                        "1,288 syllables are the same 1,288 before and after a claim. Two "
                      + "numbers move. The DOM should reflect that, and the grid already has "
                      + "the call for it."),
                new Objective("The app still works the whole way",
                        "Each step lands on its own, measured, revertible. No stage of this "
                      + "leaves the reader worse than it started - the walk we already have is "
                      + "worth more than the walk we are going to learn.")
        );
    }

    @Override
    public List<Decision> decisions() {
        return List.of(
                new Decision("u1",
                        "Fix the algorithm or move the work off-thread first?",
                        "Fix the algorithm. A worker running O(articles x known) is still "
                      + "O(articles x known).",
                        "Arithmetic before infrastructure",
                        DecisionStatus.RESOLVED,
                        "Hoisting the claimed set out of the per-article loop takes ranking the "
                      + "whole library at 2,000 readings known from 23.0ms to 0.4ms, and makes "
                      + "it independent of the reader's progress. That is a larger win than "
                      + "moving 23ms somewhere else, it is a few lines, and it needs no new "
                      + "machinery. Doing the worker first would have hidden the fault instead "
                      + "of removing it.",
                        "Measured in the running app before committing to the order. The worker "
                      + "keeps its own justification - bulk arrivals and store ownership - which "
                      + "the algorithmic fix does not touch."),

                new Decision("u2",
                        "Is the 29ms of grid cost inherent to the DOM?",
                        "No. It is structure churn where no structure changed.",
                        "updateCell, not rebuild",
                        DecisionStatus.RESOLVED,
                        "The arithmetic behind all 1,288 syllable rows measures 1.2ms; the pane "
                      + "costs ~30ms. The difference is destroying and reconstructing a grid to "
                      + "change two numbers in one row. RelationGrid already exposes a direct "
                      + "update path - updateCell(pk, col, value), coalesced per frame, "
                      + "described in its own source as 'domain to cell; no layout, no lookup' "
                      + "- and KnownSoundsWidget ignores it.",
                        "This is our bug, not a framework gap. It also fixes a defect nobody "
                      + "filed: a rebuilt grid resets its cursor, so arrowing to a row and "
                      + "marking it currently throws the reader back to the top."),

                new Decision("u3",
                        "Does a late-mounting pane still get the truth?",
                        "Yes - a snapshot on join, deltas only afterwards.",
                        "Snapshot on join, never a missed diff",
                        DecisionStatus.RESOLVED,
                        "Today every broadcast carries the whole set, so a pane that mounts late "
                      + "is correct on the next change. That property is load-bearing and easy "
                      + "to lose the moment updates become incremental. Any owner that publishes "
                      + "deltas must answer a join with a full snapshot.",
                        "Costs one extra message shape and a test that mounts a pane after a "
                      + "claim and asserts it agrees with one that was there all along."),

                new Decision("u4",
                        "Where does the derived state live?",
                        "One owner computes it; panes apply it.",
                        "Derive once, apply many",
                        DecisionStatus.OPEN,
                        "Six panes derive overlapping facts from the same set on every change - "
                      + "claimed-ness per pair, known count per syllable, readability per "
                      + "article, totals. Computing them once and publishing is strictly less "
                      + "work and removes the chance of two panes disagreeing. What is not yet "
                      + "settled is whether the owner is the existing Secretary, a module "
                      + "beside it, or the worker itself.",
                        "Deferred on purpose until phases up1 and up2 have landed: their "
                      + "measurements will say how much is left to move, and an owner designed "
                      + "against the current numbers would be designed against a fault we are "
                      + "about to remove."),

                new Decision("u5",
                        "A worker - and if so, holding what?",
                        "Undecided. The case rests on a number not yet measured.",
                        "Measure the big library first",
                        DecisionStatus.OPEN,
                        "The pure modules would run in a worker unchanged, and IndexedDB works "
                      + "in workers, so the store could move with the arithmetic and end the "
                      + "reconciliation between two in-memory truths. Against that: there is no "
                      + "worker anywhere in the codebase, no framework story for serving a "
                      + "module graph into one, and after up1 and up2 there may be little left "
                      + "worth moving.",
                        "The deciding measurement is a claim against a synthetic library ten "
                      + "times today's size. It is phase up4 and it comes before any worker "
                      + "code, so the answer is evidence rather than taste.")
        );
    }

    @Override
    public List<Phase> phases() {
        return List.of(
                new Phase("up0", "Establish the baseline",
                        "Measure before touching anything.",
                        "One claim, real events, in both workspaces, plus the arithmetic in "
                      + "isolation - so every later phase has a number to beat rather than an "
                      + "impression to argue with.",
                        PhaseStatus.DONE,
                        List.of(
                                new Task("Time a claim in the Reading workspace", true),
                                new Task("Time a claim in Known Characters", true),
                                new Task("Time library ranking at 0 / 500 / 1000 / 2000 known", true),
                                new Task("Separate arithmetic from DOM in the Sounds known cost", true),
                                new Task("Confirm the device write is already off the path", true),
                                new Task("Write the design up as a studio doc", true)),
                        List.of(),
                        "Numbers recorded in The Update Path and reproducible by the same steps.",
                        "Nothing to roll back - no production code changed.",
                        "S",
                        "The split that decided the plan: Sounds known spends 1.2ms on "
                      + "arithmetic and ~29ms on DOM, so a worker would not have touched it.",
                        List.of(
                                new Metric("Claim - Reading workspace", "13.3 ms", "-", "target < 16 ms"),
                                new Metric("Claim - Known Characters", "43.1 ms", "-", "target < 16 ms"),
                                new Metric("Sounds known - arithmetic", "1.2 ms", "-", "already cheap"),
                                new Metric("Sounds known - DOM", "~29 ms", "-", "the target of up2"))),

                new Phase("up1", "Hoist the claimed set",
                        "Stop rebuilding the claimed lookup once per article.",
                        "ReadabilityModule.of() builds its claimed map from the whole known set "
                      + "on every call, and the catalogue calls it per article. Prepare the "
                      + "lookup once per pass and hand it in. Ranking becomes O(pairs in the "
                      + "library) instead of O(articles x readings known).",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Give of() a prepared claimed set rather than the raw array", false),
                                new Task("Build it once per pass in the catalogue and the fit line", false),
                                new Task("Make a raw array impossible to pass by accident", false),
                                new Task("Extend the GraalVM tests to both call shapes", false),
                                new Task("Re-measure ranking at 0 / 500 / 1000 / 2000 known", false)),
                        List.of(new Dependency("up0", "Needs the baseline to prove the win")),
                        "Ranking the library at 2,000 readings known drops from 23.0ms to under "
                      + "1ms, and is flat as the known set grows.",
                        "One module and its two callers. Revert the commit; nothing else "
                      + "depends on the new shape.",
                        "S",
                        "The measurement already exists: a hoisted variant timed in the browser "
                      + "gave 0.3-0.5ms across every known-set size tried.",
                        List.of(
                                new Metric("Rank library, 500 known", "5.8 ms", "-", "hoisted: 0.4 ms"),
                                new Metric("Rank library, 1,000 known", "8.9 ms", "-", "hoisted: 0.5 ms"),
                                new Metric("Rank library, 2,000 known", "23.0 ms", "-", "hoisted: 0.4 ms"))),

                new Phase("up2", "Update cells, do not rebuild grids",
                        "Use the direct update path the grid already offers.",
                        "KnownSoundsWidget destroys and reconstructs a 1,288-row grid on every "
                      + "claim. Build it once; thereafter push values with updateCell, which is "
                      + "coalesced per frame. KnownZiWidget gains and loses exactly one row, "
                      + "which is addRow and removeRow.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Sounds known: build the grid once, update the changed rows", false),
                                new Task("Use the changed key the secretary already sends", false),
                                new Task("Known: addRow / removeRow for the one row that moves", false),
                                new Task("Rebuild only on a real structure change - filter, orientation", false),
                                new Task("flushNow on every read path (copy, export)", false),
                                new Task("Check the cursor survives a claim", false),
                                new Task("Re-measure a claim in Known Characters", false)),
                        List.of(new Dependency("up0", "Needs the baseline"),
                                new Dependency("up1", "Land the arithmetic first so the DOM win is legible")),
                        "A claim in Known Characters drops well under one frame, and the grid "
                      + "cursor stays where the reader left it.",
                        "Per widget. Sounds known and Known are independent of each other, so "
                      + "either can revert to a rebuild alone.",
                        "M",
                        "The secretary already publishes 'changed: key' beside the whole set. "
                      + "Every consumer currently ignores it.",
                        List.of(
                                new Metric("Claim - Known Characters", "43.1 ms", "-", "target < 16 ms"),
                                new Metric("Grid rows rebuilt per claim", "1,288", "-", "target: 1 updated"))),

                new Phase("up3", "One owner for derived state",
                        "Derive once and publish; panes apply rather than compute.",
                        "Claimed-ness per pair, known count per syllable, readability per "
                      + "article and the totals are all functions of the same set and the same "
                      + "corpus, recomputed independently by six panes. Compute them in one "
                      + "place per change and publish the values.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Name the derived facts and who consumes each", false),
                                new Task("Decide the owner - Secretary, a module beside it, or the worker", false),
                                new Task("Publish a snapshot on join and deltas thereafter", false),
                                new Task("Test that a late-mounting pane agrees with an early one", false),
                                new Task("Shape the payload as (pk, column, value) where a grid consumes it", false),
                                new Task("Re-measure both workspaces", false)),
                        List.of(new Dependency("up2", "The payload shape follows from the update path")),
                        "No pane derives a fact another pane already derived, and a pane mounted "
                      + "after a claim shows the same numbers as one mounted before it.",
                        "The owner can publish alongside the existing broadcast before anything "
                      + "depends on it, so panes migrate one at a time and the old path stays "
                      + "until the last one has moved.",
                        "M",
                        "Decision u4 is deliberately open until up1 and up2 have landed - "
                      + "designing the owner against the current numbers would be designing "
                      + "against a fault we are removing."),

                new Phase("up4", "Measure a library ten times the size",
                        "Get the number the worker decision rests on.",
                        "Synthesise a census of several thousand articles, run a claim through "
                      + "the fixed path, and see what is actually left. This is the phase that "
                      + "decides whether up5 happens at all.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Synthesise a census at 10x and 20x today's size", false),
                                new Task("Measure a claim on the fixed path", false),
                                new Task("Measure first load and census parse separately", false),
                                new Task("Measure a whole-record import", false),
                                new Task("Record the numbers against decision u5", false)),
                        List.of(new Dependency("up1", "Must measure the fixed algorithm"),
                                new Dependency("up2", "Must measure the fixed DOM path")),
                        "Decision u5 can be resolved by pointing at a number.",
                        "Nothing to roll back - measurement only.",
                        "S",
                        "Kept as its own phase so the worker is not adopted on the strength of "
                      + "an extrapolation. Parsing and import are measured apart from marking "
                      + "because they are the cases a worker actually helps.",
                        List.of(
                                new Metric("Claim at 10x library", "not measured", "-", "decides u5"),
                                new Metric("Census parse at 10x", "not measured", "-", "the worker's best case"),
                                new Metric("Whole-record import", "not measured", "-", "the other worker case"))),

                new Phase("up5", "Move the owner off the main thread",
                        "Only if up4 says there is something worth moving.",
                        "The owner becomes a worker: it holds the set, its IndexedDB copy, and "
                      + "the derived computation, and posts batches of values. The pure modules "
                      + "run there unchanged.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Serve the module graph to a worker", false),
                                new Task("Move the pure modules across unchanged - no edits", false),
                                new Task("Move the store, then remove the two-truths reconciliation", false),
                                new Task("Keep the failed-save warning reaching the reader", false),
                                new Task("Keep everything on the device - no endpoint, no sync", false),
                                new Task("Re-measure first load, import and a claim", false)),
                        List.of(new Dependency("up3", "Needs a single owner to move"),
                                new Dependency("up4", "Needs the evidence that it is worth it")),
                        "First load and whole-record import stop blocking paint, and the set has "
                      + "one in-memory home rather than two.",
                        "The largest rollback risk in the plan, so the worker runs beside the "
                      + "main-thread owner behind a switch until it has been correct for a "
                      + "while. If it is dropped, up1 through up3 keep every gain.",
                        "L",
                        "No worker exists anywhere in the codebase today, and no framework story "
                      + "for serving a module graph into one. This is new infrastructure, not a "
                      + "refactor, which is why it is last and conditional.")
        );
    }

    @Override
    public List<Acceptance> acceptance() {
        return List.of(
                new Acceptance("A claim costs less than a frame",
                        "Marking a reading known completes inside 16ms in both workspaces, "
                      + "measured the same way as the baseline.", false),
                new Acceptance("Cost is flat in the known set",
                        "The same claim costs the same at 20 readings known and at 2,000.", false),
                new Acceptance("Cost is near-flat in the library",
                        "Ten times the articles does not mean ten times the marking cost.", false),
                new Acceptance("No structure churn on a value change",
                        "Marking does not destroy or reconstruct any grid, and the cursor stays "
                      + "where the reader put it.", false),
                new Acceptance("Late panes are still correct",
                        "A pane mounted after a claim shows the same numbers as one mounted "
                      + "before it.", false),
                new Acceptance("Nothing about the record changed",
                        "Still keyed on (character, reading), still explicit and reversible, "
                      + "still on the device, and a failed save is still announced.", false),
                new Acceptance("Every step stood alone",
                        "Each phase landed measured and revertible, and the reader worked at "
                      + "every commit in between.", false)
        );
    }

}
