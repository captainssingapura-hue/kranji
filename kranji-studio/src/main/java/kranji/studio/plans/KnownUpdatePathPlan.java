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
 * <p>Design is captured in <em>The Update Path</em>, under Reading &rarr; Known
 * Characters. This file tracks execution. It is the single source of truth:
 * edit, recompile, restart the server.</p>
 *
 * <p>The plan changed shape once the inventory was done. It began as six
 * incremental phases, each revertible, each leaving the app working. That was
 * the wrong shape: eleven separate misuses all turned out to be one mistake -
 * durable shared state modelled as a conversation between UI panes - and
 * repairing them one at a time would have preserved the frame that produced
 * them. Learning a new walk badly and forgetting the old one is the failure
 * this plan exists to avoid, and eleven careful local repairs holding a wrong
 * shape upright is that failure wearing a diligent face.</p>
 *
 * <p>So the order is now: name the misuses, make failure loud, build the store
 * they should have been using, remove them all and let things break, write
 * down what actually broke, and repair from that list. The break is deliberate
 * and its window is one branch.</p>
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
             + "the library and the child's progress. The cost is a symptom: the known set is "
             + "durable shared state modelled as messages between panes. Build the store, "
             + "remove all eleven misuses at once, and repair against what actually breaks.";
    }

    @Override
    public List<Objective> objectives() {
        return List.of(
                new Objective("The known set is a store, not a conversation",
                        "One thing owns the set, the disk and the failure, and answers a read "
                      + "by returning rather than by broadcasting. Every other fault on the "
                      + "list is downstream of that one being untrue."),
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
                new Objective("The break is bounded and visible",
                        "This plan deliberately breaks working features, which is only "
                      + "defensible if every break announces itself and none of it reaches "
                      + "main. Silence is the enemy: two of the eleven misuses are already "
                      + "silent failures, and a demolition that adds more of those cannot be "
                      + "repaired from what it broke.")
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
                        "Since resolved, the inventory showed why the loop exists at all: the "
                      + "set is a plain array with no membership structure, so every consumer "
                      + "invents its own index. The fix belongs to the store, not to the "
                      + "readability module."),

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
                        "Cheaper under a store than under a bus: a pane that can call the store "
                      + "gets its snapshot by asking, so joining stops being an event that "
                      + "makes every other pane repaint."),

                new Decision("u4",
                        "Where does the derived state live?",
                        "In the store. It has to exist anyway, and it is the only thing holding "
                      + "both inputs.",
                        "Derive once, apply many",
                        DecisionStatus.RESOLVED,
                        "Claimed-ness per pair, known count per syllable, readability per "
                      + "article and the totals are all functions of the same set and the same "
                      + "corpus, recomputed independently by six panes. This was left open "
                      + "pending measurement; the inventory closed it instead. Once the store "
                      + "owns the set with real membership it is the only place holding what "
                      + "the derivation needs, and putting the derivation anywhere else means "
                      + "handing the set back out - which is how the panes came to derive it in "
                      + "the first place.",
                        "Evaluation stays a pure function of (census, membership); the store "
                      + "decides when to call it and what to do with the answer. Triggering and "
                      + "evaluation must not merge back together."),

                new Decision("u5",
                        "A worker - and if so, holding what?",
                        "Undecided. The case rests on a number not yet measured.",
                        "Measure the big library first",
                        DecisionStatus.OPEN,
                        "The pure modules would run in a worker unchanged, and IndexedDB works "
                      + "in workers, so the store could move with the arithmetic. Against that: "
                      + "there is no worker anywhere in the codebase, no framework story for "
                      + "serving a module graph into one, and after the store lands there may "
                      + "be little left worth moving.",
                        "The deciding measurement is a claim against a synthetic library ten "
                      + "times today's size, and it comes before any worker code, so the answer "
                      + "is evidence rather than taste."),

                new Decision("u6",
                        "Remove the misuses first, or migrate incrementally?",
                        "Remove them first, and accept the breakage.",
                        "Demolish, then repair from the rubble",
                        DecisionStatus.RESOLVED,
                        "An incremental migration keeps every feature alive at each step, which "
                      + "sounds strictly safer and is not: it requires the old frame to keep "
                      + "working while the new one arrives, so both exist at once and the "
                      + "compromises needed to bridge them outlive the migration. The eleven "
                      + "items are one mistake, so eleven local repairs would each have been "
                      + "shaped by the mistake they were repairing.",
                        "The cost is a period where the app is knowingly broken, on a branch, "
                      + "with the breaks catalogued rather than guessed. That is what up2 buys, "
                      + "and why it comes before the demolition rather than after it."),

                new Decision("u7",
                        "Is the store a module, or the Secretary with its state taken out?",
                        "A module the panes call. The known party goes away.",
                        "A store you call, not a bus you join",
                        DecisionStatus.RESOLVED,
                        "The Secretary is a bus, and modelling the store as one is precisely "
                      + "what turned a read into a broadcast: WhatIsKnown returns nothing and "
                      + "fans the whole set to every member, so six panes opening a workspace "
                      + "cost six full fan-outs before anything is marked. A module holding the "
                      + "set can answer a read by returning it. The other three parties stay as "
                      + "they are - navigation, ziSelection and articleSelection really are "
                      + "selections relayed and forgotten, which is what a bus is for.",
                        "Needs one instance shared across panes rather than a factory each pane "
                      + "calls, so the mechanism is worth checking early: an ES module that "
                      + "exports an instance is cached per URL, which gives one store per page. "
                      + "Verify that under the widget loader before the demolition depends on "
                      + "it.")
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
                                new Metric("Sounds known - DOM", "~29 ms", "-", "the target of the repair"))),

                new Phase("up1", "Name every misuse",
                        "Sweep the reading app and list them, with evidence.",
                        "Read every site that marks, watches, seeds, saves or repaints against "
                      + "the known set, and classify what is wrong with each. The list is what "
                      + "the demolition works from, so it has to be a survey rather than a "
                      + "recollection - and it is what turns 'the architecture is wrong' into "
                      + "something with line numbers.",
                        PhaseStatus.DONE,
                        List.of(
                                new Task("Enumerate every known-set message kind and its sites", true),
                                new Task("Trace who owns the set, the disk and the failure", true),
                                new Task("Find every consumer that rebuilds its own index", true),
                                new Task("Find every subscriber that repaints rather than updates", true),
                                new Task("Separate correctness faults from performance faults", true),
                                new Task("Check each one literally, not by inference", true)),
                        List.of(new Dependency("up0", "The baseline said where to look")),
                        "Eleven items, each with a file and a line, grouped into correctness, "
                      + "party-as-store, message shape, membership and repainting.",
                        "Nothing to roll back - reading only.",
                        "S",
                        "The two at the top are correctness, not architecture: durability is "
                      + "attached to panes that can be closed, and the save failure is swallowed "
                      + "in the two panes most likely to be open.",
                        List.of(
                                new Metric("Misuses found", "-", "11", "2 correctness, 9 design"),
                                new Metric("Persistence engines", "-", "7", "5 direct, 2 via the watch"),
                                new Metric("Panes that actually save", "-", "2 of 7", "neither is the one that claims"))),

                new Phase("up2", "Make every break loud",
                        "Demolition only works if failure announces itself.",
                        "Two of the eleven are silent: a claim that never reaches the disk looks "
                      + "identical to one that did, and the failure handler in the watch is an "
                      + "empty function. Repairing from what broke needs breaking to be "
                      + "observable, so this comes first and stays afterwards.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Replace the empty onProblem with a real announcement", false),
                                new Task("Make a claim nobody will write fail loudly, not quietly", false),
                                new Task("Assert one store instance per page, and fail if there are two", false),
                                new Task("Add a test that marks with no pane open and expects a save", false),
                                new Task("Confirm the new checks fail today, before anything is removed", false)),
                        List.of(new Dependency("up1", "Needs the list of what is silent")),
                        "Each new check fails against today's code for the reason it names. A "
                      + "check that passes before the fix is not checking anything.",
                        "Pure additions - tests and an error path. Revert the commit and "
                      + "nothing else notices.",
                        "S",
                        "This is the phase that makes demolition defensible rather than "
                      + "reckless. It is also the smallest.",
                        List.of(
                                new Metric("Silent failure paths", "2", "-", "target: 0"),
                                new Metric("New checks failing before the fix", "-", "-", "all of them, by design"))),

                new Phase("up3", "Stand up the store",
                        "One thing that owns the set, the disk and the failure.",
                        "A module, not a Secretary - it answers a read by returning. It holds "
                      + "membership in a structure built for it, owns the single IndexedDB "
                      + "copy, owns the failure announcement, hands a snapshot to whoever asks, "
                      + "and publishes changes as values. Wired to nothing yet, so nothing "
                      + "breaks in this phase.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Verify one shared instance across panes under the widget loader", false),
                                new Task("Hold membership as a lookup, not an array scanned per query", false),
                                new Task("Move load, save and the failure announcement inside it", false),
                                new Task("Snapshot on ask; changes published as values afterwards", false),
                                new Task("Keep evaluation a pure function it calls, never absorbs", false),
                                new Task("Test it under GraalVM against fakes, as the pure modules are", false),
                                new Task("Leave the party in place and untouched for now", false)),
                        List.of(new Dependency("up2", "Its failure path is the one that must be loud"),
                                new Dependency("up1", "It has to cover every item on the list")),
                        "The store passes its own tests, and the app still runs unchanged "
                      + "because nothing calls it yet.",
                        "Additive. Delete the new module and the app is exactly where it was.",
                        "M",
                        "Deliberately dead code at the end of this phase. That is what makes "
                      + "the next phase a removal rather than a rewrite.",
                        List.of(
                                new Metric("Owners of the set", "1 Secretary + 7 engines", "-", "target: 1"),
                                new Metric("Membership cost per query", "O(n) scan", "-", "target: O(1)"))),

                new Phase("up4", "Remove the misuses",
                        "All eleven, at once, and let it break.",
                        "Take the state out of the Secretary and the known party out of the "
                      + "workspace. Delete the seven persistence engines, WhatIsKnown, the "
                      + "whole-set broadcast, the per-consumer indexes and the repaint-on-change "
                      + "subscriptions. Point what remains at the store. Features will stop "
                      + "working; that is the expected outcome of this phase, not a sign it "
                      + "went wrong.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Take the set out of the Secretary; retire the known party", false),
                                new Task("Delete all seven persistence instantiations", false),
                                new Task("Remove WhatIsKnown and the whole-set broadcast", false),
                                new Task("Remove every locally built index and linear membership scan", false),
                                new Task("Remove the rebuild-on-change subscriptions", false),
                                new Task("Point marking and reading at the store", false),
                                new Task("Do not repair anything in this phase, however tempting", false)),
                        List.of(new Dependency("up3", "Removal needs somewhere for the set to land"),
                                new Dependency("up2", "Breakage has to be loud before it is caused")),
                        "The misuse list is empty. The app builds. Some of it does not work, "
                      + "and what does not work is visible rather than silent.",
                        "One branch, one revert. This is the phase the branch exists for, and "
                      + "the reason nothing here goes near main until up6 closes.",
                        "M",
                        "The hardest instruction in the plan is the last task: notice what "
                      + "broke, write it down, and do not fix it yet. Fixing while demolishing "
                      + "is how the old frame gets rebuilt by hand.",
                        List.of(
                                new Metric("Misuses remaining", "11", "-", "target: 0"),
                                new Metric("Persistence engines", "7", "-", "target: 1"),
                                new Metric("Known-set message kinds", "6", "-", "target: 0"))),

                new Phase("up5", "Take stock of what broke",
                        "Observed, not predicted.",
                        "Walk both workspaces and the test suite and write down what actually "
                      + "stopped working, ranked by what a reader would miss first. The value "
                      + "of demolishing is that this list is evidence; guessing it in advance "
                      + "would have made the whole exercise pointless.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Run the suite; list every failure with its cause", false),
                                new Task("Walk both workspaces pane by pane and record what is dead", false),
                                new Task("Rank by what a reader loses, not by what is easy", false),
                                new Task("Mark anything that broke silently as a defect in up2", false),
                                new Task("Turn the list into up6's tasks", false)),
                        List.of(new Dependency("up4", "There is nothing to take stock of until it breaks")),
                        "A ranked list of breaks with causes, and up6's task list generated "
                      + "from it rather than from imagination.",
                        "Nothing to roll back - observation only.",
                        "S",
                        "A break that nothing here catches is a hole in up2, and belongs on "
                      + "that phase's record rather than being quietly fixed.",
                        List.of(
                                new Metric("Breaks found", "-", "-", "the input to up6"),
                                new Metric("Breaks that were silent", "-", "-", "each one is an up2 defect"))),

                new Phase("up6", "Repair, one feature at a time",
                        "Rebuild against the store, in the order a reader would miss things.",
                        "Each feature comes back as an application of published values rather "
                      + "than a derivation from a broadcast set - which is where the grid "
                      + "update path, the hoisted membership and the single owner all land, not "
                      + "as phases of their own but as the natural way to write them the second "
                      + "time.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Repair in ranked order, one commit per feature", false),
                                new Task("Sounds known: build the grid once, update the rows that move", false),
                                new Task("Known: addRow and removeRow for the one row that changes", false),
                                new Task("Library: rank from published values, no per-article index", false),
                                new Task("Check the grid cursor survives a claim", false),
                                new Task("Confirm a late-mounting pane agrees with an early one", false),
                                new Task("Re-measure both workspaces", false)),
                        List.of(new Dependency("up5", "The task list comes from what broke")),
                        "Every break repaired, both workspaces under one frame per claim, and "
                      + "the acceptance gates below all green.",
                        "Per feature, one commit each, so a repair that goes wrong reverts "
                      + "without taking the others. The branch does not merge until this phase "
                      + "closes.",
                        "L",
                        "The old up1 and up2 - hoist the claimed set, stop rebuilding grids - "
                      + "live here now. They stopped being phases once the store existed, "
                      + "because with the store they are simply how the code gets written.",
                        List.of(
                                new Metric("Claim - Reading workspace", "13.3 ms", "-", "target < 16 ms"),
                                new Metric("Claim - Known Characters", "43.1 ms", "-", "target < 16 ms"),
                                new Metric("Rank library, 2,000 known", "23.0 ms", "-", "expect under 1 ms"),
                                new Metric("Grid rows rebuilt per claim", "1,288", "-", "target: the ones that moved"))),

                new Phase("up7", "Measure a library ten times the size",
                        "Get the number the worker decision rests on.",
                        "Synthesise a census of several thousand articles, run a claim through "
                      + "the repaired path, and see what is actually left. This is the phase "
                      + "that decides whether up8 happens at all.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Synthesise a census at 10x and 20x today's size", false),
                                new Task("Measure a claim on the repaired path", false),
                                new Task("Measure first load and census parse separately", false),
                                new Task("Measure a whole-record import", false),
                                new Task("Record the numbers against decision u5", false)),
                        List.of(new Dependency("up6", "Must measure the repaired path, not the broken one")),
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

                new Phase("up8", "Move the store off the main thread",
                        "Only if up7 says there is something worth moving.",
                        "The store becomes a worker: it holds the set, its IndexedDB copy and "
                      + "the derived computation, and posts batches of values. The pure modules "
                      + "run there unchanged. Far cheaper to attempt once the store is one "
                      + "thing rather than seven.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Serve the module graph to a worker", false),
                                new Task("Move the pure modules across unchanged - no edits", false),
                                new Task("Move the durable copy with it", false),
                                new Task("Keep the failed-save warning reaching the reader", false),
                                new Task("Keep everything on the device - no endpoint, no sync", false),
                                new Task("Re-measure first load, import and a claim", false)),
                        List.of(new Dependency("up6", "Needs a single store to move"),
                                new Dependency("up7", "Needs the evidence that it is worth it")),
                        "First load and whole-record import stop blocking paint, and the set "
                      + "has one home rather than two.",
                        "Runs beside the main-thread store behind a switch until it has been "
                      + "correct for a while. If it is dropped, everything before it keeps "
                      + "every gain.",
                        "L",
                        "No worker exists anywhere in the codebase today, and no framework story "
                      + "for serving a module graph into one. This is new infrastructure, not a "
                      + "refactor, which is why it is last and conditional.")
        );
    }

    @Override
    public List<Acceptance> acceptance() {
        return List.of(
                new Acceptance("The set has one owner",
                        "One store holds the set, the durable copy and the failure. No pane "
                      + "holds any of the three, and there is no known-set bus.", false),
                new Acceptance("A claim is saved whatever is open",
                        "Marking persists without depending on some pane that happens to save, "
                      + "and a failed save is announced without being asked for.", false),
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
                      + "still on the device.", false),
                new Acceptance("The break did not escape",
                        "Every break was catalogued in up5 and repaired in up6, and no commit "
                      + "between up4 and up6 reached main.", false)
        );
    }

}
