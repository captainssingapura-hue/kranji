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
 * Live tracker for glosses at corpus scale — the 8,759 pairs, not the 447.
 *
 * <p>{@code GlossPlan} covers the hand-crafted set that makes the bundled
 * library self-contained, and its {@code gp5} reserves a phase for "the ported
 * tail — if it is ever wanted". This is that phase, opened out into a plan of
 * its own, because it is the same shape of work {@code PhonicCoveragePlan}
 * already did for readings: one corpus, 22 partitions, taken one at a time.</p>
 *
 * <p>The reason it is a sibling plan rather than a longer {@code gp5} is that
 * its hard part is not the import. It is <b>review at a scale nobody can review
 * exhaustively</b>, which needs a tool, a triage policy, and a place to keep a
 * verdict — none of which the hand-crafted set ever needed.</p>
 */
public final class GlossCoveragePlan implements Plan {

    public static final GlossCoveragePlan INSTANCE = new GlossCoveragePlan();

    private GlossCoveragePlan() {}

    @Override public String kicker()   { return "CORPUS"; }
    @Override public String name()     { return "Gloss coverage"; }
    @Override public String subtitle() {
        return "Source of truth: GlossCoveragePlan.java. Edit, recompile, restart the server.";
    }
    @Override public String summary() {
        return "A meaning for every (character, reading) the phonic corpus lists - 8,759 pairs "
             + "across 8,100 characters - imported partition by partition, and reviewed by "
             + "triage rather than by reading all of it.";
    }

    @Override
    public List<Objective> objectives() {
        return List.of(
                new Objective("Cover the corpus, not the library",
                        "GlossPlan's target is the 447 pairs the 23 demo articles use. This "
                      + "one's is every pair the corpus has a reading for: 8,759 appearances "
                      + "across 8,100 characters, 592 of them polyphonic. The reader stops "
                      + "wherever it stops, and a set sized to the bundled articles has "
                      + "nothing to say the moment somebody adds an article."),

                new Objective("A partition is a unit of work and a unit of trust",
                        "Not a shard for convenience. A finished partition is importable, "
                      + "countable, reviewable and shippable on its own, so progress is a list "
                      + "of partitions that are done rather than a percentage that never "
                      + "arrives. Reusing the phonic axis makes that free."),

                new Objective("Triage, because nobody reviews 8,759 rows",
                        "At five seconds a row an exhaustive pass is a twelve-hour sitting, and "
                      + "a twelve-hour sitting is not a review - it is a rubber stamp with "
                      + "extra steps. The machine has to decide what deserves a person: the "
                      + "frequent, the polyphonic, and whatever fails a check."),

                new Objective("A verdict outlives the row it was about",
                        "The imported data is GENERATED, so it will be regenerated - a better "
                      + "sense-selection policy, a newer CC-CEDICT. A review that lived in the "
                      + "generated file would be destroyed every time, and a review keyed on a "
                      + "line number would be worse: it would survive and be wrong."),

                new Objective("Provenance stays separable",
                        "Decision g2 already settled this and it does not get relitigated here: "
                      + "ported data lives in its own module under its own licence, and a build "
                      + "without that module is fully permissive.")
        );
    }

    @Override
    public List<Decision> decisions() {
        return List.of(
                new Decision("gc1",
                        "What is a partition?",
                        "The 22 phonic partitions - a character files under its principal "
                      + "reading's initial, so it appears in exactly one.",
                        "Reuse the phonic axis; do not invent a second one",
                        DecisionStatus.RESOLVED,
                        "The corpus is already cut this way and committed that way: 22 files "
                      + "under kranji/phonic, from zero.tsv at 1,115 characters down to r.tsv "
                      + "at 117. The cut is total and disjoint, which is the only property a "
                      + "unit of work actually needs.\n\n"
                      + "Every alternative is worse for a specific reason. By radical: the "
                      + "gloss key is phonic, so a radical partition would straddle it. By "
                      + "frequency band: the bands move when the frequency source is updated, "
                      + "and a unit of work that redefines itself cannot be finished. By "
                      + "codepoint range: arbitrary, and it splits polyphones from nothing in "
                      + "particular.",
                        "The phonic partition of an initial is exactly the universe of the "
                      + "gloss partition for that initial, so coverage per partition is a "
                      + "subtraction rather than a join. The Sound picker's initial filter is "
                      + "already a partition selector - it was built for the sound relation and "
                      + "turns out to be the navigation this needs.\n\n"
                      + "One wrinkle to state rather than discover: a partition is filed by "
                      + "PRINCIPAL reading, so a polyphone's alternate readings sit in the "
                      + "partition of its principal. 行 files under x (xíng) and its háng "
                      + "reading comes with it. That is right - a character is reviewed once, "
                      + "with all its readings in front of you - but it means a partition is "
                      + "not 'every pair whose reading starts with x'."),

                new Decision("gc2",
                        "Which partition first, and in what order after that?",
                        "r first to build the pipeline on 117 characters. Then by frequency "
                      + "weight, not alphabetically.",
                        "Cheapest first, then by where a reader actually stops",
                        DecisionStatus.OPEN,
                        "Two different orders serve two different purposes and the plan needs "
                      + "both. The first partition is a pipeline test: it should be the "
                      + "smallest, so a wrong sense-selection policy costs 117 rows of rework "
                      + "and not 1,115. r is that partition.\n\n"
                      + "After that, alphabetical order is indefensible - it would put b and c "
                      + "ahead of the partitions holding the characters children actually "
                      + "read. Ordering by summed kHanyuPinlu frequency puts the value first, "
                      + "and the frequency data is already in the corpus.\n\n"
                      + "OPEN because the weighting is not obvious: kHanyuPinlu reaches only "
                      + "2,827 of the 8,100, so two thirds of every partition has no frequency "
                      + "evidence at all. Weighting by the evidence that exists may just rank "
                      + "the partitions by how well studied they are.",
                        "Left open, the sweep still runs - it just runs in whatever order "
                      + "somebody picks, which is the status quo and costs nothing until there "
                      + "are more partitions done than not."),

                new Decision("gc3",
                        "Where does a review verdict live?",
                        "A sidecar file per partition, keyed on the pair AND the text it was a "
                      + "verdict about. Never a column in the generated data.",
                        "Keyed on content, so regeneration invalidates rather than misleads",
                        DecisionStatus.OPEN,
                        "A column in the generated TSV is destroyed by the next regeneration. A "
                      + "sidecar keyed on (codepoint, reading) survives regeneration - and "
                      + "that is the dangerous option, because it carries a verdict about text "
                      + "that no longer exists onto text nobody has read. 'Reviewed, fine' "
                      + "attached to a gloss somebody has since changed is worse than no "
                      + "review, because it is indistinguishable from a review.\n\n"
                      + "So the key includes the reviewed text. When regeneration changes a "
                      + "gloss, its verdict stops matching and the row returns to the queue by "
                      + "itself. Nothing has to remember to invalidate anything.",
                        "Verdicts are diffable, reviewable in a pull request, and countable "
                      + "without loading the gloss data. The cost is that a reworded gloss "
                      + "loses its verdict even when the rewording was cosmetic - accepted, "
                      + "because the alternative is a stamp that lies.\n\n"
                      + "Open on the mechanism: a digest of the text is compact and opaque, "
                      + "the verbatim text is legible and bulky. Legible probably wins - the "
                      + "file is for people."),

                new Decision("gc4",
                        "What can a reviewer say about a row?",
                        "A small closed set of verdicts, each naming what to DO, not how bad it "
                      + "is.",
                        "Verdicts are dispositions, not severities",
                        DecisionStatus.OPEN,
                        "A severity scale ('minor / major') produces a pile nobody can act on. "
                      + "What makes a queue drainable is that each verdict names its own next "
                      + "step: keep, drop this sense, split it into two, wrong reading, "
                      + "rewrite the register, needs a human who knows the character.\n\n"
                      + "The set has to stay small enough to pick from a two-value-style "
                      + "picker without reading a legend, because the whole point is throughput.",
                        "The workbench can show a count per verdict and a queue per verdict, "
                      + "and 'drop' and 'split' can be applied mechanically to the generated "
                      + "data on the next regeneration rather than by hand-editing."),

                new Decision("gc5",
                        "When both collections have a pair, which wins?",
                        "The hand-crafted set. The ported set fills gaps and never overrides.",
                        "Hand-crafted beats ported, always",
                        DecisionStatus.OPEN,
                        "The SPI composes whatever is on the classpath through "
                      + "Glosses.of(GlossSource...), and today nothing states what happens on "
                      + "a collision because nothing collides. Once the ported set arrives, "
                      + "every one of the 447 hand-crafted pairs collides.\n\n"
                      + "Hand-crafted winning is the only defensible rule: those pairs were "
                      + "written for the register a child reads, reviewed against the articles "
                      + "that use them, and are the ones the demand test holds at 100%. A "
                      + "ported gloss silently displacing one would regress the app's most "
                      + "visible text and no test would notice.",
                        "Needs stating in code, not in a comment - Glosses.of has to define "
                      + "precedence and a test has to hold it. Also needs the workbench to "
                      + "SHOW which collection answered, or a reviewer will spend time on rows "
                      + "the app never displays.")
        );
    }

    @Override
    public List<Phase> phases() {
        return List.of(
                new Phase("gc-1", "The second collection",
                        "An empty module that composes correctly before it has any data.",
                        "kranji-gloss-cedict with its own LICENSE and attribution, registered "
                      + "through the ZiCollection SPI, and precedence defined so the "
                      + "hand-crafted set wins every collision. Wiring first and data second, "
                      + "because a composition bug found after 8,000 rows exist is found in "
                      + "the wrong place.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Declare the project's own licence first (gp5 task, still open)", false),
                                new Task("Confirm CC-CEDICT's terms from the source", false),
                                new Task("kranji-gloss-cedict module, LICENSE, attribution in the data header", false),
                                new Task("ZiCollection service registration, discovered alongside HandCrafted", false),
                                new Task("Precedence in Glosses.of: hand-crafted wins, with a test", false),
                                new Task("Reading app and studio both still green with the module absent", false)),
                        List.of(),
                        "Two collections are discovered, a collision resolves to the "
                      + "hand-crafted gloss, and removing the module leaves a permissive build "
                      + "that still passes.",
                        "Keep one collection and hand-craft on demand, as GlossPlan gp3 does.",
                        "S",
                        "Encodes decision gc5 - hand-crafted wins every collision - which is "
                      + "the one thing here that cannot be retrofitted once 8,000 ported rows "
                      + "exist, because by then every regression it prevents is invisible."),

                new Phase("gc-2", "The importer, on one partition",
                        "r: 117 characters, the smallest partition in the corpus.",
                        "CC-CEDICT in, one senses.tsv out, for a single initial. The "
                      + "sense-selection policy is the whole difficulty - CEDICT entries are "
                      + "word entries with several glosses, and this model wants a few ranked "
                      + "senses per READING in a register a child reads. Proving that on 117 "
                      + "characters is the point of doing r first.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Numbered-pinyin conversion, u: to ue and tone 5 to 0, tested", false),
                                new Task("Match CEDICT entries to corpus pairs; report unmatched as findings", false),
                                new Task("Sense-selection policy: how many, which, and in what priority", false),
                                new Task("Emit the partition in the existing senses.tsv shape", false),
                                new Task("Regeneration is a no-op diff when inputs are unchanged", false),
                                new Task("Coverage for r, stated as covered / missing / unmatched", false)),
                        List.of(new Dependency("gc-1", "Needs somewhere to put the output.")),
                        "r imports, parses under the existing TSV reader, and its coverage is a "
                      + "number with a reason for every gap.",
                        "Import per character on demand rather than per partition.",
                        "M",
                        "The expensive half of this is already done and is worth not "
                      + "rediscovering: GlossPlan gp5 records that the numbered-pinyin "
                      + "rendering had to become correct on its own account, and it now is - "
                      + "the collapses iou-iu, uei-ui, uen-un round-trip across all 1,284 "
                      + "corpus syllables. The import no longer has to reimplement pinyin "
                      + "orthography underneath itself."),

                new Phase("gc-3", "Triage",
                        "The checks that decide what a person looks at.",
                        "Every automatic check that can flag a row before a human sees it, so "
                      + "the review queue is a few hundred rows and not 8,759. These are "
                      + "cheap, and each one is a rule the hand-crafted set already learned "
                      + "the hard way.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("No gloss at all for a pair the corpus lists", false),
                                new Task("Length beyond what a child mid-story reads", false),
                                new Task("One gloss reused across two readings of a polyphone - the cardinality bug", false),
                                new Task("A semicolon list long enough to be several senses in one row", false),
                                new Task("A gloss naming a reading the phonic corpus does not list", false),
                                new Task("Register markers: grammatical vocabulary, 'used in', 'variant of'", false),
                                new Task("Rank the queue by frequency, then by polyphony", false)),
                        List.of(new Dependency("gc-2", "Needs data to check.")),
                        "The queue for a finished partition is a small fraction of its rows, "
                      + "and every flag names the check that raised it.",
                        "Review by frequency order alone and accept that the tail is unchecked.",
                        "M",
                        "The checks are not hypothetical. Every one of them is a mistake the "
                      + "447-pair set actually made and a person caught by eye: 忙 glossed "
                      + "'busy; to hurry' as one sense when it is two, pins on the wrong "
                      + "character position, a per-character meaning attached to both readings "
                      + "of a polyphone. Automating what a person already had to notice twice "
                      + "is the cheapest work in this plan."),

                new Phase("gc-4", "The review workbench",
                        "Quick review and marking, in the studio, keyboard-first.",
                        "A relation of candidate rows with a verdict cell, scoped to one "
                      + "partition, ordered by the triage queue. Most of the parts exist: the "
                      + "Sound picker already filters by initial, and the Character pane's "
                      + "two-value picker cell is the shape a verdict cell wants - a closed "
                      + "set, one keystroke, Enter opens it.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Review relation: pair, gloss, flags, verdict, which collection answered", false),
                                new Task("Verdict cell - the KnownPickerCell shape, over the gc4 set", false),
                                new Task("Partition scope, reusing the initial filter", false),
                                new Task("Sidecar read and write, keyed per gc3", false),
                                new Task("Next-unreviewed navigation that does not lose position on save", false),
                                new Task("Counts per verdict, and a partition-done signal", false)),
                        List.of(new Dependency("gc-3", "The queue is what it shows.")),
                        "A partition can be reviewed end to end from the keyboard, and closing "
                      + "the browser loses nothing.",
                        "Review the TSV in an editor and keep verdicts in a second file by "
                      + "hand - workable, and it is what the 447 were done with.",
                        "M",
                        "Throughput is the requirement, so it is worth being explicit about "
                      + "what that means: a reviewer should never need the mouse, never wait "
                      + "for a save, and never lose their place because a save redrew the "
                      + "grid. The Character pane's own claim picker had exactly that problem "
                      + "in miniature - a mark rebuilt the grid and dropped focus - and it is "
                      + "the same fix here."),

                new Phase("gc-5", "The sweep",
                        "22 partitions, one at a time, in the gc2 order.",
                        "The grind. Import, triage, review, mark the partition done, move on. "
                      + "Each partition lands as its own commit with its own coverage number, "
                      + "so the work is legible in the history and abandonable at any point "
                      + "without leaving the corpus half-imported.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("r (117) - the pipeline proof", false),
                                new Task("The next four, to establish a per-partition cost", false),
                                new Task("Re-decide gc2's order once a real cost per row is known", false),
                                new Task("The remaining partitions", false),
                                new Task("zero (1,115) last - the largest, and the one most likely to need policy changes", false)),
                        List.of(new Dependency("gc-4", "Needs the tool.")),
                        "Every partition is imported and its queue drained to a stated "
                      + "standard, and the corpus coverage number is one a person can defend.",
                        "Stop after the partitions that carry the frequent characters; the tail "
                      + "degrades to no gloss, which the app already handles.",
                        "L",
                        "The plan deliberately does not promise all 22. The app degrades "
                      + "silently where there is no gloss - ZiGlossary answers empty and the "
                      + "column stays blank - so a half-swept corpus is a working app with "
                      + "less to say, not a broken one. That is what makes stopping a decision "
                      + "rather than a failure."),

                new Phase("gc-6", "The gate",
                        "What the build holds once the sweep is real.",
                        "Coverage per partition as a test, so a regeneration that loses rows "
                      + "fails rather than quietly shrinking, and the reviewed fraction is "
                      + "reported rather than assumed.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Per-partition coverage pinned, like PhonicPartitionsTest pins 8,100", false),
                                new Task("Reviewed fraction reported per partition", false),
                                new Task("No verdict points at text that no longer exists", false),
                                new Task("Corpus coverage doc regenerated from the numbers", false)),
                        List.of(new Dependency("gc-5", "Needs partitions to gate.")),
                        "The numbers in the docs come from the build, and a silent regression "
                      + "in coverage is impossible.",
                        "Report coverage on demand from the workbench without gating the build.",
                        "S",
                        "")
        );
    }

    @Override
    public List<Acceptance> acceptance() {
        return List.of(
                new Acceptance("A partition is a finishable thing",
                        "Import, triage and review all scope to one partition, and a finished "
                      + "partition stays finished when the next one starts.", false),
                new Acceptance("Hand-crafted glosses are never displaced",
                        "Where both collections have a pair, the app shows the hand-crafted "
                      + "gloss, and a test holds it.", false),
                new Acceptance("No verdict outlives its subject",
                        "A regenerated gloss whose text changed has no verdict, and returns to "
                      + "the queue without anyone remembering to put it there.", false),
                new Acceptance("Review is keyboard-throughput work",
                        "A reviewer can drain a queue without the mouse, without waiting for a "
                      + "save, and without losing their place.", false),
                new Acceptance("Stopping is safe",
                        "With any subset of partitions imported, the reader and the workbench "
                      + "both work and the missing glosses are absent rather than wrong.", false),
                new Acceptance("The licence claim is checkable",
                        "A build without the ported module is permissive; a build with it says "
                      + "so, per module, in a file a person can read.", false)
        );
    }
}
