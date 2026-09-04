package kranji.studio.plans;

import hue.captains.singapura.js.homing.studio.base.tracker.Acceptance;
import hue.captains.singapura.js.homing.studio.base.tracker.Decision;
import hue.captains.singapura.js.homing.studio.base.tracker.DecisionStatus;
import hue.captains.singapura.js.homing.studio.base.tracker.Dependency;
import hue.captains.singapura.js.homing.studio.base.tracker.Objective;
import hue.captains.singapura.js.homing.studio.base.tracker.Phase;
import hue.captains.singapura.js.homing.studio.base.tracker.PhaseStatus;
import hue.captains.singapura.js.homing.studio.base.tracker.Plan;
import hue.captains.singapura.js.homing.studio.base.tracker.Task;

import java.util.List;

/**
 * Live tracker for bringing Kranji onto the Homing workspace.
 *
 * <p>This file is the single source of truth. Flip a {@link Task} to done,
 * change a {@link PhaseStatus}, resolve a {@link Decision} — recompile,
 * restart the studio, and the new state appears. Git history is the
 * change log.</p>
 */
public final class HomingIntegrationPlan implements Plan {

    public static final HomingIntegrationPlan INSTANCE = new HomingIntegrationPlan();

    private HomingIntegrationPlan() {}

    @Override public String kicker()   { return "INTEGRATION"; }
    @Override public String name()     { return "Kranji on Homing"; }
    @Override public String subtitle() {
        return "Source of truth: HomingIntegrationPlan.java. Edit, recompile, restart the server.";
    }
    @Override public String summary() {
        return "Stand up a Homing studio for Kranji - docs and planning first, "
             + "character visualisation widgets second.";
    }

    @Override
    public List<Objective> objectives() {
        return List.of(
                new Objective("Docs as typed artifacts",
                        "Move the project's durable documentation out of loose markdown and "
                      + "into versioned Java records with companion prose, navigable as a tree."),
                new Objective("Planning in the open",
                        "Track in-flight work with the framework's plan kit so decisions, "
                      + "phases, and ship-gates are visible rather than tacit."),
                new Objective("A path to visualisation",
                        "Reach a point where BlockSvgRenderer output can be served as a widget "
                      + "without rewriting the renderer in JavaScript.")
        );
    }

    @Override
    public List<Decision> decisions() {
        return List.of(
                new Decision("d1",
                        "Framework version - pinned release or local snapshot?",
                        "Prefer a pinned release for fresh-clone portability.",
                        "LOCAL-SNAPSHOT",
                        DecisionStatus.RESOLVED,
                        "Both 0.7.1 and LOCAL-SNAPSHOT carry the features we need (verified via "
                      + "javap: ConformanceStudioFixtures, CrateSeed, DefaultJsRulePolicy."
                      + "extendedWith). LOCAL-SNAPSHOT chosen to track framework changes closely "
                      + "during integration.",
                        "Revisit before anyone else needs to build the reactor: a machine-local "
                      + "snapshot is not resolvable on a fresh clone."),
                new Decision("d2",
                        "Where does the studio live?",
                        "A dedicated module inside the Kranji reactor.",
                        "kranji-studio (in-reactor)",
                        DecisionStatus.RESOLVED,
                        "Keeps docs beside the code they describe. The module depends only on "
                      + "the Homing framework, not on kranji-core, so the dependency graph stays "
                      + "one-directional.",
                        ""),
                new Decision("d3",
                        "How much of docs/ moves into the studio?",
                        "Promote the durable subset; leave scratch behind.",
                        "Main docs only",
                        DecisionStatus.RESOLVED,
                        "The docs/ directory is gitignored as a scratch area. Docs that matter "
                      + "become studio content and are versioned; working notes stay untracked.",
                        "Watch for drift: a promoted doc should not also live on in docs/."),
                new Decision("d4",
                        "Do the visualisation widgets reuse BlockSvgRenderer server-side?",
                        "Yes - serve SVG from a GetAction rather than porting the renderer to JS.",
                        null,
                        DecisionStatus.OPEN,
                        "The renderer is tested Java and already emits SVG. Re-implementing it "
                      + "in the browser would duplicate the layout engine and invite drift.",
                        "Needs a spike before Phase 4 to confirm the SVG survives being dropped "
                      + "into branch-created DOM unmodified.")
        );
    }

    @Override
    public List<Phase> phases() {
        return List.of(
                new Phase("p1", "Learn the framework",
                        "Read the reference downstreams and confirm what ships.",
                        "Study homing-doc-plus-demo (the KT's designated reference) and "
                      + "homing-self-studio (Homing's own docs-and-plans studio, the closest "
                      + "analogue to what we want). Establish that a pure doc studio needs no "
                      + "widgets and no WorkspaceSpec.",
                        PhaseStatus.DONE,
                        List.of(
                                new Task("Read KT.md for the fin dashboard", true),
                                new Task("Study homing-blocks scaffold (4 files, ~217 lines)", true),
                                new Task("Confirm ClasspathMarkdownDoc resource layout", true),
                                new Task("Find the plan tracker kit in homing-studio-base", true),
                                new Task("Verify framework artifacts resolve from the local repo", true)),
                        List.of(),
                        "Reference scaffold understood well enough to reproduce from memory.",
                        "",
                        "S",
                        "The KT's warning that RFC 0044 features are unreleased is stale - "
                      + "0.7.1 carries them. Recorded in d1."),

                new Phase("p2", "Scaffold the studio",
                        "A running studio with a landing page.",
                        "Create kranji-studio with the four scaffold files, register it in the "
                      + "reactor, and serve an intro doc on port 8101.",
                        PhaseStatus.DONE,
                        List.of(
                                new Task("Create module and pom", true),
                                new Task("Register in parent reactor", true),
                                new Task("Write Server / Fixtures / Studio / Catalogue", true),
                                new Task("Write the intro doc", true),
                                new Task("Compile clean", true),
                                new Task("Serve and confirm the landing page renders", true)),
                        List.of(new Dependency("p1", "Needs the scaffold shape.")),
                        "Running the server serves a landing page at port 8101.",
                        "Delete the module and drop it from the reactor.",
                        "S",
                        ""),

                new Phase("p3", "Promote the main docs",
                        "Move the durable documentation into the studio.",
                        "Architecture (type hierarchy, layout and codegen pipeline), Corpus "
                      + "(measured coverage), and Explorations (the typed-trees index). Scratch "
                      + "notes stay in the ignored docs directory.",
                        PhaseStatus.IN_PROGRESS,
                        List.of(
                                new Task("Architecture section - 2 docs", true),
                                new Task("Corpus section - coverage report", true),
                                new Task("Explorations section - typed-trees index", true),
                                new Task("Decide the fate of the remaining scratch docs", false)),
                        List.of(new Dependency("p2", "Needs a working catalogue tree.")),
                        "Every promoted doc is reachable from the L0 catalogue and renders.",
                        "Docs remain as markdown under the ignored docs directory.",
                        "M",
                        "Deliberately partial: promote what is durable, not everything."),

                new Phase("p4", "Character visualisation widgets",
                        "Put Kranji's own content on the workspace.",
                        "A Zi browser, a composition SVG panel fed by BlockSvgRenderer, a "
                      + "clickable layout signature, and an associated-characters panel - wired "
                      + "together over the party bus, mirroring the JavaFX explorer's proven UX.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Spike: serve BlockSvgRenderer output from a GetAction", false),
                                new Task("WorkspaceSpec and first widget", false),
                                new Task("Zi browser with corpus filters", false),
                                new Task("Party-bus selection flow", false)),
                        List.of(new Dependency("p2", "Needs the studio serving."),
                                new Dependency("p3", "Docs settle the shape before widgets land.")),
                        "Selecting a character in one widget refreshes the others.",
                        "Widgets are additive; the doc studio stands alone without them.",
                        "L",
                        "Blocked on d4. This phase makes kranji-core a dependency of the "
                      + "studio module, so it may warrant a separate module instead."),

                new Phase("p5", "Crate and conformance",
                        "Close the loop on the framework's own quality gate.",
                        "Declare the crate, add the gate test, and extend the policy with a "
                      + "Kranji-specific rule: a served module must not inline CJK glyph "
                      + "literals - glyphs come from the corpus over the wire. That enforces "
                      + "the project's own no-stringly-typed-glyphs tenet one layer out.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Generate the crate with CrateSeed", false),
                                new Task("Conformance config and gate test", false),
                                new Task("ZiModuleType and NoInlineGlyphRule", false),
                                new Task("Baseline the pre-existing findings", false),
                                new Task("Conformance studio server on its own port", false)),
                        List.of(new Dependency("p4", "Nothing to gate until modules are served.")),
                        "Gate passes; the conformance studio shows the zi-model type and its rules.",
                        "Drop the policy extension; the framework's default policy still applies.",
                        "M",
                        "Only meaningful once p4 ships served modules - a pure doc studio has "
                      + "no served artifacts of its own to check.")
        );
    }

    @Override
    public List<Acceptance> acceptance() {
        return List.of(
                new Acceptance("Studio serves",
                        "The landing page renders at port 8101 and the catalogue tree navigates.", true),
                new Acceptance("Main docs promoted",
                        "Architecture, Corpus, and Explorations are readable in the studio.", true),
                new Acceptance("Plan is live",
                        "This tracker renders, and editing it changes what the studio shows.", true),
                new Acceptance("Reactor stays green",
                        "A full clean install passes with the new module in the build.", true),
                new Acceptance("No coupling regressions",
                        "kranji-core gains no dependency on the studio or the framework.", true)
        );
    }
}
