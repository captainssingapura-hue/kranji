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
 * Live tracker for Kranji Reading - an adaptive Chinese reading app for
 * children.
 *
 * <p>Design is captured in the Reading section of the studio catalogue.
 * This file tracks execution. It is the single source of truth: edit,
 * recompile, restart the server.</p>
 */
public final class KranjiReadingPlan implements Plan {

    public static final KranjiReadingPlan INSTANCE = new KranjiReadingPlan();

    private KranjiReadingPlan() {}

    @Override public String kicker()   { return "APP"; }
    @Override public String name()     { return "Kranji Reading"; }
    @Override public String subtitle() {
        return "Source of truth: KranjiReadingPlan.java. Edit, recompile, restart the server.";
    }
    @Override public String summary() {
        return "An adaptive Chinese reading app for children - pinyin appears only where the "
             + "reader needs it, and an unfamiliar character can explain its own structure.";
    }

    @Override
    public List<Objective> objectives() {
        return List.of(
                new Objective("Annotation that recedes",
                        "Show pinyin for characters this reader has not secured, and stay out "
                      + "of the way for the ones they have. The same article should look "
                      + "different in June than it did in January."),
                new Objective("Characters that explain themselves",
                        "When a reader stops at an unfamiliar character, offer its composition "
                      + "rather than a dictionary gloss. This is the payoff for the typed corpus "
                      + "and the one thing a reader without a structural model cannot do."),
                new Objective("Tools, not enforcement",
                        "The app offers; the human decides. No algorithm silently declares a "
                      + "character known, no queue must be cleared, no streak can be broken. "
                      + "Every judgement is visible and reversible."),
                new Objective("Progress that belongs to the child",
                        "No accounts, no server-side profile, nothing transmitted. The server "
                      + "ships the application and nothing else; the reading record lives in "
                      + "the browser and exports to a file the family owns."),
                new Objective("Material at the right level",
                        "Rank articles by how much of each one this reader can already read, and "
                      + "point them at the band where reading is productive.")
        );
    }

    @Override
    public List<Decision> decisions() {
        return List.of(
                new Decision("r1",
                        "Where does the reading app live in the reactor?",
                        "A nested multi-module tree under kranji-reading.",
                        "kranji-reading, itself multi-module",
                        DecisionStatus.RESOLVED,
                        "kranji-studio deliberately does not depend on kranji-core - it documents "
                      + "the project without coupling to its model. The reading app needs the "
                      + "corpus, so it sits outside the studio. Splitting it further keeps the "
                      + "pure domain independent of the Homing UI, so the model and the article "
                      + "content can be tested and reused without a browser.",
                        "Planned split: -model (domain types plus the corpus bridge), -content "
                      + "(article source SPI and the bundled catalogue), -app (Homing "
                      + "workspaces, widgets, server), -prep (preparation and validation CLI). "
                      + "One crate per Maven module is load-bearing, so only -app carries a "
                      + "crate; if widgets later split across modules, each needs its own."),

                new Decision("r2",
                        "How does a character become KNOWN?",
                        "Explicitly, from wherever the reader already is - never by algorithm.",
                        "Explicit, reversible, unattributed",
                        DecisionStatus.RESOLVED,
                        "An algorithm inferring knowledge from silence is guessing: a child who "
                      + "did not tap for a reading may have skipped the word or guessed from "
                      + "context. Rather than model that badly, the app makes marking cheap from "
                      + "wherever the reader already is - in the reader, in the manager, in "
                      + "review, or by bulk import - and makes every mark removable.",
                        "Introduces the known-set manager as a first-class surface rather than a "
                      + "settings page, and a bad import can be undone as a unit. Demotion is a "
                      + "plain button, not a decay rule.\n\n"
                      + "AMENDED: per-mark channel provenance is cut. It was kept for exactly "
                      + "one reason - that a bulk import must be undoable whole - and that turned "
                      + "out not to need it: the import remembers the keys it ADDED and stores "
                      + "them beside the set, so undo works without knowing where any individual "
                      + "mark came from. The cost of keeping it would have been the shape of the "
                      + "set, the stored row and the export format families may already hold. "
                      + "What is given up is that only the most recent import stays "
                      + "identifiable; see The Known Set."),

                new Decision("r3",
                        "How are per-occurrence readings resolved during preparation?",
                        "Override-only source text, backed by a reviewed polyphony baseline.",
                        "Inline overrides, hand-annotated, baseline-gated",
                        DecisionStatus.RESOLVED,
                        "Article source is plain text plus a reading override only where "
                      + "the corpus default is wrong - no word boundaries, no structured "
                      + "wrapper, no per-token markup. Diffs stay readable and the "
                      + "annotation burden is proportional to genuine ambiguity. Hand "
                      + "annotation fills the overrides for v1; the format is chosen so a "
                      + "segmenter can take over later without reworking content.",
                        "Dropping word boundaries costs the validator its ability to prove "
                      + "a reading from word context, so the guarantee moves to a "
                      + "committed baseline of reviewed occurrences - the same bargain the "
                      + "conformance gate makes. An occurrence in the baseline has been "
                      + "signed off; one that is not fails the build. Line breaking gets "
                      + "marginally worse without word boundaries; readability and the "
                      + "almost-ready view are character-level and unaffected."),

                new Decision("r4",
                        "Where does learner state live?",
                        "IndexedDB in the browser, with explicit export.",
                        "IndexedDB, multi-profile, stateless server",
                        DecisionStatus.RESOLVED,
                        "The server ships the application and nothing else - it holds no profile, "
                      + "so there is nothing to breach or quietly analyse. IndexedDB rather than "
                      + "localStorage because a known set plus per-article progress will outgrow "
                      + "the localStorage budget, and IndexedDB is already the framework's "
                      + "substrate for checkpoint storage. Several profiles may share a device.",
                        "Follows the framework's State Belongs to the User doctrine. Accepted "
                      + "costs: no cross-device sync beyond file export, and clearing browser "
                      + "data loses progress - so export should be easy to reach and prompted at "
                      + "sensible moments. Profile switching is an unprotected local selector, "
                      + "which suits children but needs care that one child cannot casually "
                      + "wreck another's record."),

                new Decision("r5",
                        "Which review scheduling algorithm?",
                        "None. Provide review tools and let the human drive.",
                        "No enforcement, tools only",
                        DecisionStatus.RESOLVED,
                        "Spaced repetition optimises retention per minute for a motivated adult "
                      + "clearing a deck. For a child the failure mode to avoid is "
                      + "discouragement, and a scheduler that decides what must be reviewed "
                      + "today produces exactly that. The app surfaces what is in progress and "
                      + "makes practice easy to start; it does not schedule, nag, or score.",
                        "Consistent with r2. Ordering hints - least recently seen, most recently "
                      + "added - are fine as sort options the reader chooses. What is ruled out "
                      + "is a due-date model that makes the app the authority on what happens "
                      + "next."),

                new Decision("r6",
                        "Where do articles come from?",
                        "A catalogue behind a source SPI, with a bundled default.",
                        "ArticleSource SPI plus bundled catalogue",
                        DecisionStatus.RESOLVED,
                        "v1 ships a small curated set as classpath resources so content is "
                      + "reviewable in pull requests and nothing needs an ingestion pipeline to "
                      + "start reading. Putting a source SPI in front of it from the beginning "
                      + "means a family-authored folder, a school's own collection, or an "
                      + "imported pack can be added later without reworking the reader.",
                        "Licensing still applies to whatever a source serves: the bundled "
                      + "catalogue is limited to public-domain classical material and text "
                      + "written for the project. The SPI does not launder provenance."),

                new Decision("r7",
                        "How is the simple Zi layer stored and where does it live?",
                        "A Java DSL in kranji-core, partitioned by initial, tiered by frequency.",
                        "kranji-core, Java DSL, per-initial files, 2000 then 5000",
                        DecisionStatus.RESOLVED,
                        "In the core because the layer is generally useful rather than "
                      + "reading-specific: any tool wanting a sound for an arbitrary "
                      + "character needs it, and the structural records can be validated "
                      + "against it. As a Java DSL rather than a JSON resource because "
                      + "the values are then compiler-checked - a mistyped final is a "
                      + "build error on the offending line, not a parse failure at "
                      + "startup or a wrong reading shown to a child. It also matches how "
                      + "Kranji already works.",
                        "Reverses an earlier JSON-resource shape. Granularity is one file "
                      + "per initial - 22 files - not one per syllable: measured against "
                      + "the promoted corpus there are 717 syllable partitions for 1,566 "
                      + "characters, so a per-syllable split scatters the data across "
                      + "~900 files averaging two entries. Per-initial gives ~91 entries "
                      + "per file at the 2,000 tier and ~227 at 5,000. Watch the 64KB "
                      + "static-initialiser limit on the largest initials; split by final "
                      + "group if one outgrows it.")
        );
    }

    @Override
    public List<Phase> phases() {
        return List.of(
                new Phase("rp1", "Design and plan",
                        "Write the specification before writing the app.",
                        "Overview, domain model, adaptive pinyin, known-character management, "
                      + "article preparation, the simple Zi layer, and catalogue readability - "
                      + "captured as studio docs alongside this tracker.",
                        PhaseStatus.DONE,
                        List.of(
                                new Task("Establish framework constraints - no auth, local-first storage", true),
                                new Task("Overview and scope", true),
                                new Task("Domain model - content tree, profile, per-occurrence readings", true),
                                new Task("Adaptive pinyin - modes, rule, composition hint", true),
                                new Task("Known-character management - add, remove, import", true),
                                new Task("Article catalogue and readability", true),
                                new Task("Article preparation - polyphony and the baseline", true),
                                new Task("The simple Zi layer - two tiers, Java DSL", true),
                                new Task("Module structure", true),
                                new Task("All decisions recorded", true),
                                new Task("Reconcile docs with resolved decisions", true)),
                        List.of(),
                        "The design is readable in the studio and no doc contradicts a decision.",
                        "",
                        "M",
                        "Framework research settled the privacy posture early: no auth, no "
                      + "session identity, and a doctrine that refuses server-sync of user "
                      + "state. Adopted rather than worked around."),

                new Phase("rp2", "Reading Studio shell",
                        "Stand up the module tree and get something serving.",
                        "kranji-reading as a parent pom with -model, -content, -app and -prep "
                      + "beneath it, then the Homing scaffold in -app: studio, fixtures, server, "
                      + "workspace spec, and one trivial widget. Empty of content but running on "
                      + "its own port.",
                        PhaseStatus.DONE,
                        List.of(
                                new Task("Parent pom and reactor registration", true),
                                new Task("-model, -content, -app, -prep skeletons", true),
                                new Task("Confirm -model and -content stay free of Homing deps", true),
                                new Task("Confirm kranji-core gains no new dependencies", true),
                                new Task("Studio, fixtures, server on its own port", true),
                                new Task("Reading WorkspaceSpec and one hello-world widget", true),
                                new Task("Add the widget from the picker and see it render", true),
                                new Task("Generate the crate for -app with CrateSeed", true),
                                new Task("Conformance config and gate test", true),
                                new Task("ZiModuleType and NoInlineGlyphRule", true),
                                new Task("Baseline whatever the first run surfaces", true)),
                        List.of(new Dependency("rp1", "r1 fixes the module split.")),
                        "A workspace opens, a widget of ours renders, and the conformance gate passes.",
                        "Remove the tree and drop it from the reactor.",
                        "M",
                        "Moved ahead of the data work deliberately. The unknowns in this app are "
                      + "all in the Homing integration - ruby layout, the party bus, IndexedDB - "
                      + "not in the character data, which is well understood and merely long. "
                      + "Build the risky part first. Conformance is folded in here rather "
                      + "than left to the end: the OrphanCheck is cheapest to satisfy while "
                      + "the module holds three widgets instead of fifteen, and a baseline "
                      + "established now records a clean start rather than grandfathering drift."),

                new Phase("rp3", "Simple Zi types, a seed set, and the catalogue",
                        "The record, the DSL, the invariants - and a tree to see them in.",
                        "SimpleZi and the DSL in kranji-core, partitioned per initial, plus the "
                      + "two invariant tests. The phonic partition is already a tree, so the "
                      + "catalogue is built alongside the data rather than long after it: the "
                      + "shape lives in -model as plain records, and -app adapts it to the "
                      + "framework tree. Seeded with the characters one demo article needs.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("SimpleZi record - defaultPhonic plus additionalPhonics", false),
                                new Task("DSL helpers and per-initial partition in kranji-core", false),
                                new Task("Invariant test - declaring class must match default initial", false),
                                new Task("Consistency test - Zi.pinyin must equal SimpleZi.defaultPhonic", false),
                                new Task("Resolution order - structural record first, simple otherwise", false),
                                new Task("ZiTree shape in -model, framework-free, with tests", false),
                                new Task("Phonic projection - initial, final plus tone, character", false),
                                new Task("Adapt ZiTree to the framework tree in -app", false),
                                new Task("Character detail from both tiers", false),
                                new Task("Seed the characters of one demo article", false)),
                        List.of(new Dependency("rp2", "The catalogue needs somewhere to be served from.")),
                        "The seeded characters browse by phonic, and both invariants hold.",
                        "Types are additive; the catalogue is a separate widget.",
                        "M",
                        "The catalogue is folded in here because it validates the data as it is "
                      + "entered - a wrong default phonic shows up as a character under the wrong "
                      + "node, where a data file would hide it. It also becomes the known-set "
                      + "manager's browse surface later, so that phase adds a state overlay "
                      + "rather than a second browser. Watch the module boundary: the tree shape "
                      + "belongs in -model as plain records, and only -app may name a framework "
                      + "type."),
                new Phase("rp4", "Domain model and corpus bridge",
                        "The content half is built; the profile half is not.",
                        "Sealed token model, article records, and the join to the corpus. The "
                      + "shape settled differently from the sketch: Passage and Sentence are "
                      + "gone (Chinese sentence boundaries are punctuation, so a stored level "
                      + "held something derivable), the four token kinds collapsed to two, and "
                      + "Verse and Illustration were added. Cells - one square of the page, "
                      + "with punctuation riding in the corner of the character it belongs to - "
                      + "are a projection over tokens rather than part of the text model.",
                        PhaseStatus.IN_PROGRESS,
                        List.of(
                                new Task("Article, Block (Paragraph | Verse | Illustration), Token (Zi | Plain)", true),
                                new Task("Cell model and the punctuation-merge rule, unit-tested", true),
                                new Task("Corpus bridge - readings resolved through SyllableIndex", true),
                                new Task("Profile and the known set - no states, no channels (r2)", true),
                                new Task("Readability as a pure function of article and profile", true)),
                        List.of(new Dependency("rp2", "Needs somewhere to live."),
                                new Dependency("rp3", "Needs the corpus to resolve against.")),
                        "Model compiles and is unit-tested with no browser and no Homing runtime "
                      + "- 10 tests over the cell rule alone.",
                        "Types are additive.",
                        "M",
                        "The content model is done and proven by three articles. The profile "
                      + "half is untouched and is what rp7 needs; splitting the phase would be "
                      + "honest if it stays half-done for long."),

                new Phase("rp5", "Reader",
                        "Reads. Three articles render, including one designed to break it.",
                        "The reader widget. <b>Not ruby</b> - ruby spreads its base characters "
                      + "apart to fit the annotation, so spacing would depend on which "
                      + "characters this reader knows and the page would re-space as a child "
                      + "learns. Each line is a two-row table instead: annotations above, "
                      + "characters below, every column one square. With the grid on it looks "
                      + "like the practice book a child already writes in.",
                        PhaseStatus.IN_PROGRESS,
                        List.of(
                                new Task("Reader widget - fixed square grid, not ruby", true),
                                new Task("Reserved annotation row - no reflow when modes change", true),
                                new Task("ALL, ADAPTIVE and NONE modes", true),
                                new Task("Article GetAction serving resolved cells", true),
                                new Task("Typeface and size controls, shared with the Characters pane", true),
                                new Task("Punctuation drawn by the cell class, measured per glyph", true),
                                new Task("Paragraph wrapping in bands; verse keeps its own lines", true),
                                new Task("Tap a character - opens it in the Character pane", true),
                                new Task("ON_TAP mode", false),
                                new Task("Illustration blocks and the asset action", false)),
                        List.of(new Dependency("rp4", "Needs the token model.")),
                        "Three articles render: a poem, a prose piece, and 施氏食狮史 - 93 "
                      + "characters, every one read shi, which resolved with zero errors.",
                        "Reader is standalone; nothing else depends on it.",
                        "L",
                        "Two things were measured rather than assumed and both changed the "
                      + "design. Ruby was tested and rejected on evidence. And punctuation is "
                      + "placed per character, because canvas.measureText showed 光 fills its "
                      + "box while 月 leaves a fifth of it empty - one global offset cannot "
                      + "suit both.\n\n"
                      + "ADAPTIVE still waits on rp7; ON_TAP is trivial once the modes have "
                      + "somewhere to read a profile from."),

                new Phase("rp6", "Known Zi management",
                        "Make the known set a place, not a setting.",
                        "A workspace for the known set: browse it, add and remove, bulk-import "
                      + "a list, undo an import as a unit. This is the surface r2 chose instead "
                      + "of an inference algorithm.",
                        PhaseStatus.IN_PROGRESS,
                        List.of(
                                new Task("Known-set workspace and grid widget", true),
                                new Task("Add and remove, single and bulk", true),
                                new Task("Channel provenance per mark - CUT, see r2", true),
                                new Task("Import a character list; undo an import as a unit", true),
                                new Task("Filter by radical, stroke count, composition", false),
                                new Task("Marking wired from a reader selection", true)),
                        List.of(new Dependency("rp4", "Needs the profile model."),
                                new Dependency("rp5", "The reader is where a character is picked.")),
                        "A reading can be marked and unmarked, and an import can be taken back "
                      + "whole without touching what the child earned.",
                        "",
                        "L",
                        "Filtering by radical and composition is where the corpus makes this "
                      + "better than a flat list."),

                new Phase("rp7", "Profile storage and adaptive mode",
                        "The mechanic the app exists for.",
                        "IndexedDB-backed profiles, export and import, several profiles per "
                      + "device, ADAPTIVE mode wired to profile state, and the composition hint "
                      + "for characters that have a structural record.",
                        PhaseStatus.IN_PROGRESS,
                        List.of(
                                new Task("IndexedDB store behind a storage port", true),
                                new Task("Several profiles on one device", false),
                                new Task("Export and import a profile as a file", true),
                                new Task("ADAPTIVE mode", true),
                                new Task("Composition hint, one level deep by default", false),
                                new Task("Flag which components the reader already knows", false)),
                        List.of(new Dependency("rp6", "Needs a known set worth reading against.")),
                        "Marking a reading known removes its annotation without a reload, and "
                      + "the record survives a browser restart.",
                        "Fall back to manual modes; the reader works without a profile.",
                        "L",
                        "Taken: the stored shape carries a profile key from the start although "
                      + "only \"default\" is ever written, because a key added later is a "
                      + "migration whereas a key with one value is a word.\n\n"
                      + "The set is keyed on (character, reading), not the character - see The "
                      + "Known Set. That is what lets 行走 lose its pinyin while 银行 keeps "
                      + "it, and a character-keyed set would have withdrawn support from the "
                      + "harder of the two. The reader only watches the set; marking stays a "
                      + "deliberate act made in front of a character's other readings.\n\n"
                      + "One bug worth remembering: the party answers a new member immediately "
                      + "with an empty set, while the disk is still being read, and persisting "
                      + "that reply erased the record on every visit. Nothing on screen looked "
                      + "wrong - it came back empty only on the NEXT load. The rule now lives "
                      + "in one tested module rather than in each pane."),

                new Phase("rp8", "Article source and preparation",
                        "Turn text into articles, repeatably.",
                        "The ArticleSource SPI, a bundled classpath source, the override-only "
                      + "format, and the polyphony baseline that refuses to let an unconsidered "
                      + "ambiguity reach a reader.",
                        PhaseStatus.IN_PROGRESS,
                        List.of(
                                new Task("ArticleSource SPI", true),
                                new Task("Bundled classpath source", true),
                                new Task("Override-only source format, with findings", true),
                                new Task("Overrides checked against the corpus, not just parsed", true),
                                new Task("Prepare three articles end to end", true),
                                new Task("Settle build-time versus load-time preparation", false),
                                new Task("Committed baseline for accepted findings", false),
                                new Task("Markdown format for headings and illustrations", false)),
                        List.of(new Dependency("rp5", "The fixture proves the format first.")),
                        "Three articles parse and render with correct in-context readings.",
                        "",
                        "L",
                        "The format needed no verse syntax in the end: a blank line ends a "
                      + "block, and within a block one line is a paragraph while several are a "
                      + "verse. 静夜思 is four lines with no markup.\n\n"
                      + "The check that earns its keep is that an override must be a reading "
                      + "the corpus actually lists - so a wrong one fails at preparation rather "
                      + "than in a child's face. It has already caught 地 twice: its principal "
                      + "reading is the particle de, correct by both authorities and by "
                      + "frequency, and wrong in 地上 and 草地上.\n\n"
                      + "Evidence for the open warning question: across three articles, 26 "
                      + "warnings, 2 actionable - both 地. Roughly 8% signal."),

                new Phase("rp9", "Populate the simple Zi tiers",
                        "The long, parallelisable part.",
                        "The common 2,000 first, then the common 5,000, per initial. Sourcing "
                      + "and licence-checking the phonic and meaning data, and reviewing which "
                      + "reading is the default for the characters that appear most.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Source and license-check the phonic and meaning data", false),
                                new Task("Populate the common 2000 tier", false),
                                new Task("Review the chosen default phonic for frequent characters", false),
                                new Task("Derive the polyphonic list from additionalPhonics", false),
                                new Task("Extend to the common 5000 tier", false),
                                new Task("Report structural coverage as a query over the simple tier", false)),
                        List.of(new Dependency("rp3", "Needs the DSL and the partition scheme.")),
                        "Every character in the bundled articles resolves to a default phonic.",
                        "",
                        "L",
                        "Blocks nothing structural once rp3 fixes the shape, and can proceed "
                      + "alongside the UI phases. Sourcing needs settling before bulk entry "
                      + "starts - Unihan covers the data but its terms need reading first, and "
                      + "which reading is default is an editorial judgement a dataset will not "
                      + "make correctly."),

                new Phase("rp10", "Practice tools",
                        "Practice, offered rather than scheduled.",
                        "A practice surface over characters in progress, presented in a sentence "
                      + "from an article already read, with the composition hint offered before "
                      + "the answer. No due dates, no queue that must be cleared.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Practice widget over in-progress characters", false),
                                new Task("Present in context, not in isolation", false),
                                new Task("Composition hint before reveal", false),
                                new Task("Reader-chosen ordering, no due dates", false),
                                new Task("Confirm practice never blocks reading", false)),
                        List.of(new Dependency("rp7", "Needs profile state to practise against.")),
                        "A practice session runs, updates state, and is skippable throughout.",
                        "Drop the widget; marking still works from the reader and the manager.",
                        "M",
                        "r5 rules out scheduling. Nothing here may become something the child "
                      + "owes the app."),

                new Phase("rp11", "Catalogue and readability",
                        "Help the reader find the right story.",
                        "The themed catalogue over the article source, readability per profile, "
                      + "the bands, best-fit-first ordering, frequency-tier reach, and the "
                      + "almost-ready view.",
                        PhaseStatus.IN_PROGRESS,
                        List.of(
                                new Task("Themed catalogue over the source SPI", true),
                                new Task("Readability and distinct-new-character count", true),
                                new Task("Frequency-tier reach as a second difficulty signal", false),
                                new Task("Bands, shown on the tree and in the reader", true),
                                new Task("Best-fit-first ordering", false),
                                new Task("Almost-ready view", false),
                                new Task("Keep too-hard articles visible", true)),
                        List.of(new Dependency("rp7", "Readability needs a profile."),
                                new Dependency("rp8", "Needs prepared articles to measure.")),
                        "Every article carries its readability and its count of readings left "
                      + "to learn, on the tree and in the reader, updated as marks are made.",
                        "Fall back to a flat themed list with no readability figures.",
                        "M",
                        "The split that made this cheap: the server sends a CENSUS - what "
                      + "each article asks, counted by reading - and the browser intersects "
                      + "it with a known set the server has never seen. Readability could "
                      + "not have been computed server-side without breaking r4, and doing "
                      + "it locally means re-ranking as a child learns costs no request.\n\n"
                      + "16KB for the whole library of 23, cached by URL, so it is sent once "
                      + "rather than per article per profile change.\n\n"
                      + "Counted by READING, not by character, matching the known set. An "
                      + "article using 行 as háng asks nothing of a reader who learnt xíng, "
                      + "and folding them together would report a readability the child "
                      + "cannot achieve.\n\n"
                      + "Best-fit ordering is written and tested but not wired: the tree's "
                      + "order is the curated one an author chose, and re-sorting it by fit "
                      + "would throw that away. It belongs to a flat listing view, not to "
                      + "this tree.")
        );
    }
    @Override
    public List<Acceptance> acceptance() {
        return List.of(
                new Acceptance("Design is legible",
                        "The specification is readable in the studio and the open questions "
                      + "are named rather than glossed.", true),
                new Acceptance("Coverage is measured",
                        "We know what fraction of primary-level Chinese the corpus covers.", false),
                new Acceptance("Readings are correct in context",
                        "Prepared articles show the right reading for polyphonic characters, "
                      + "and no article ships with an unconsidered ambiguity.", false),
                new Acceptance("Annotation adapts",
                        "A reading marked known loses its annotation; an unmarked one keeps it, "
                      + "including the other reading of the same character.", true),
                new Acceptance("Marks are reversible",
                        "Any reading can be unmarked, and a bulk import can be taken back "
                      + "whole without touching the readings the child earned. Attribution "
                      + "was dropped from this criterion when channel provenance was cut - "
                      + "see r2.", true),
                new Acceptance("Characters explain themselves",
                        "Tapping an unfamiliar character in the corpus shows its composition "
                      + "and flags which components the reader already knows.", false),
                new Acceptance("Nothing leaves the device",
                        "No network transmission of profile state; the server holds no profile; "
                      + "export is explicit and user-initiated.", true),
                new Acceptance("Out-of-corpus characters degrade gracefully",
                        "An article containing characters Kranji does not model still renders "
                      + "and still annotates.", false),
                new Acceptance("Nothing is enforced",
                        "No due dates, no streaks, no queue that must be cleared before "
                      + "reading.", false)
        );
    }
}
