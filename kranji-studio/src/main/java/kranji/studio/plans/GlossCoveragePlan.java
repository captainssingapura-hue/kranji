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
 * Live tracker for glosses at corpus scale — the 8,763 pairs, not the 447.
 *
 * <p>{@code GlossPlan} covers the hand-crafted set that makes the bundled
 * library self-contained, and its {@code gp5} reserves a phase for "the ported
 * tail — if it is ever wanted". This is that phase, opened out into a plan of
 * its own, because it is the same shape of work {@code PhonicCoveragePlan}
 * already did for readings: one corpus, 101 partitions, taken one at a time.</p>
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
        return "A meaning for every (character, reading) the phonic corpus lists - 8,763 pairs "
             + "across 8,100 characters. Seeded from Unihan in one pass, all 101 partitions; "
             + "reviewed partition by partition, by triage rather than by reading all of it.";
    }

    @Override
    public List<Objective> objectives() {
        return List.of(
                new Objective("Cover the corpus, not the library",
                        "GlossPlan's target is the 447 pairs the 23 demo articles use. This "
                      + "one's is every pair the corpus has a reading for: 8,763 appearances "
                      + "across 8,100 characters, 593 of them polyphonic. The reader stops "
                      + "wherever it stops, and a set sized to the bundled articles has "
                      + "nothing to say the moment somebody adds an article.\n\n"
                      + "These now agree with PhonicCoveragePlan, and for a while they did "
                      + "not. This plan counted the partition files as written - 8,764 pairs, "
                      + "594 polyphones - and that one counted what the model could parse out "
                      + "of them, 8,759 and 592. The gap was exactly two characters: 哼 listed "
                      + "hng and 欸 listed four ê forms, notations the standard replaced, so "
                      + "the files carried five readings nothing could read.\n\n"
                      + "It was not a rounding difference and it was not harmless. 欸 read as "
                      + "a monophone, so the seeder gave one gloss to all five of its "
                      + "readings. SourceCorrections respells the four and drops the one, and "
                      + "there is now a single number for a slice of the corpus rather than "
                      + "two defensible ones."),

                new Objective("A partition is a unit of work and a unit of trust",
                        "Not a shard for convenience. A finished partition is importable, "
                      + "countable, reviewable and shippable on its own, so progress is a list "
                      + "of partitions that are done rather than a percentage that never "
                      + "arrives. ZiPartition makes that free - the reader already fetches by the same cut."),

                new Objective("Triage, because nobody reviews 8,763 rows",
                        "At five seconds a row an exhaustive pass is a twelve-hour sitting, and "
                      + "a twelve-hour sitting is not a review - it is a rubber stamp with "
                      + "extra steps. The machine has to decide what deserves a person: the "
                      + "frequent, the polyphonic, and whatever fails a check."),

                new Objective("A verdict outlives the row it was about",
                        "The imported data is GENERATED, so it will be regenerated - a better "
                      + "sense-selection policy, a newer Unihan drop. A review that lived in the "
                      + "generated file would be destroyed every time, and a review keyed on a "
                      + "line number would be worse: it would survive and be wrong."),

                new Objective("Provenance stays visible",
                        "Decision g2 settled this and its answer CHANGED, so the old form of "
                      + "this objective - ported data in its own module under its own licence "
                      + "- describes something that no longer exists. Unihan seeds the tail, "
                      + "Unicode's terms are permissive, and the whole build stays under the "
                      + "project's own licence.\n\n"
                      + "What survives the change is the property that actually mattered: a "
                      + "gloss can be told apart by where it came from. That is now answered "
                      + "in two places, and they answer different questions.\n\n"
                      + "The MODULE says which collection a gloss came from - hand-written or "
                      + "machine-seeded - and says it in the dependency graph, where nobody "
                      + "has to remember it and a build can drop one side entirely. The ROW "
                      + "has to answer the finer one a reviewer actually asks: has a person "
                      + "checked THIS one. A module cannot answer that, because the day the "
                      + "seed is half reviewed it is neither wholly checked nor wholly not, "
                      + "and that day is every day from the first verdict onward.\n\n"
                      + "The module boundary exists. The row-level mark is still open.")
        );
    }

    @Override
    public List<Decision> decisions() {
        return List.of(
                new Decision("gc1",
                        "What is a partition?",
                        "101 slices, codepoint modulo 101 - the same cut the reader already "
                      + "fetches by. ZiPartition owns the rule.",
                        "One cut for the whole system, not one per consumer",
                        DecisionStatus.RESOLVED,
                        "REVERSED. This used to answer 'the 22 phonic partitions', reusing the "
                      + "by-initial files under kranji/phonic. Two things decided against it.\n\n"
                      + "Balance. Those 22 run from 121 pairs to 1,183 - a 9.8x spread - so a "
                      + "unit of work meant anything from a coffee break to a weekend, and "
                      + "there was no cheap fix: sub-splitting on a phonetic key leaves zero's "
                      + "largest child at 739, because pinyin is itself skewed. Modulo 101 "
                      + "runs 68 to 102, a spread of 1.5x. Every partition is the same "
                      + "sitting.\n\n"
                      + "Duplication. The reader was ALREADY slicing this corpus 101 ways by "
                      + "codepoint to fetch the syllable map. Reviewing on a second axis meant "
                      + "two definitions of 'a slice of the corpus', and the failure mode is "
                      + "specific: a reviewer signs off a partition the reader never "
                      + "receives.\n\n"
                      + "The old objection to codepoint was 'arbitrary, and it splits "
                      + "polyphones from nothing in particular'. The first half is fair and "
                      + "was accepted knowingly. The second half was simply wrong: the slice "
                      + "is computed from the CHARACTER, so all of a character's readings go "
                      + "with it - the same property the by-initial scheme was praised for.\n\n"
                      + "What codepoint order does cost is real. Contiguous ranges would have "
                      + "grouped the corpus by radical, since the Unified Ideographs block is "
                      + "ordered radical-then-stroke - forty consecutive codepoints are forty "
                      + "hand-verbs, then forty feathers. Modulo scatters that. It was weighed "
                      + "and set aside: the grouping helps a reviewer who needs context to "
                      + "judge a gloss, and a Chinese-language expert does not.\n\n"
                      + "The frequency-band alternative is still rejected on its original "
                      + "grounds, which have not weakened: the bands move when the frequency "
                      + "source is updated, and a unit of work that redefines itself cannot be "
                      + "finished.",
                        "A partition means one thing in the reader, the codegen and the "
                      + "workbench, because all three call ZiPartition. Coverage per partition "
                      + "is a subtraction rather than a join.\n\n"
                      + "The Sound picker's initial filter is no longer a partition selector - "
                      + "it filters the sound relation, which is a different axis and stays "
                      + "useful for what it was built for. Partition navigation needs its own "
                      + "control.\n\n"
                      + "One property survives the change unchanged, and it is the one that "
                      + "mattered: a character is reviewed once, with all its readings in "
                      + "front of you. 行 carries both xíng and háng into whichever slice its "
                      + "codepoint lands in."),

                new Decision("gc2",
                        "Which partition first, and in what order after that?",
                        "Any partition first - they are all the same size now. Then by "
                      + "frequency weight, not by index.",
                        "Order by where a reader actually stops",
                        DecisionStatus.OPEN,
                        "Half of this decision dissolved when gc1 was reversed. It used to "
                      + "read 'r first, because it is the smallest at 117' - a pipeline test "
                      + "sized so that a wrong sense-selection policy cost 117 rows of rework "
                      + "rather than 1,115. Under modulo 101 every slice is 68 to 102 pairs, "
                      + "so there is no cheapest one to start with and nothing to choose. Take "
                      + "partition 0.\n\n"
                      + "The other half stands. Ordering by index is indefensible for the same "
                      + "reason alphabetical was - it is unrelated to what a child reads. "
                      + "Ordering by summed kHanyuPinlu frequency puts the value first, and "
                      + "the frequency data is already in the corpus.\n\n"
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
                        "The hand-crafted set. The seeded set fills gaps and never overrides.",
                        "Hand-crafted beats seeded, always",
                        DecisionStatus.RESOLVED,
                        "The SPI composes whatever is on the classpath through "
                      + "Glosses.of(GlossSource...), which keeps the FIRST source that has a "
                      + "character and shadows the rest. With one collection that was "
                      + "invisible. The seed collides with 341 of the hand-crafted "
                      + "characters.\n\n"
                      + "Hand-crafted winning is the only defensible rule: those pairs were "
                      + "written for the register a child reads, reviewed against the articles "
                      + "that use them, and are the ones the demand test holds at 100%. A "
                      + "seeded gloss silently displacing one would regress the app's most "
                      + "visible text and no test would notice.",
                        "Stated in code. ZiCollection.precedence() is 0 for hand-authored and "
                      + "1 for the seed; ZiCollections.load() sorts by it, breaking ties on "
                      + "name; ZiCollectionSpiTest asserts the resulting ORDER, not just the "
                      + "membership.\n\n"
                      + "That last part is the load-bearing one. ServiceLoader does not "
                      + "specify the order it yields providers - it follows the classpath, "
                      + "which differs between a jar, an IDE and a shaded build - so before "
                      + "the sort the same build could have answered differently on another "
                      + "machine, and the symptom would have been one character quietly "
                      + "showing a machine's guess.\n\n"
                      + "Still needed: the workbench has to SHOW which collection answered, "
                      + "or a reviewer will spend time on rows the app never displays.")
        );
    }

    @Override
    public List<Phase> phases() {
        return List.of(
                new Phase("gc-1", "The second collection",
                        "An empty collection that composes correctly before it has any data.",
                        "A seeded collection beside the hand-crafted one, registered through "
                      + "the ZiCollection SPI, with precedence defined so a hand-checked gloss "
                      + "wins every collision. Wiring first and data second, because a "
                      + "composition bug found after 8,000 rows exist is found in the wrong "
                      + "place.\n\n"
                      + "It IS a separate module, kranji-gloss-seed, and that answer has now "
                      + "been given twice in opposite directions. The first time the reason "
                      + "was licence - ported data under someone else's terms - and when g2 "
                      + "removed the ported data the reason went with it, so the two "
                      + "collections shared kranji-gloss.\n\n"
                      + "They no longer do, and the reason is different: SEPARABILITY. One "
                      + "module is 437 pairs somebody wrote and reviewed against the articles "
                      + "that use them; the other is a machine's first guess at 7,121 "
                      + "characters. Sharing a jar made 'how much of this build has a person "
                      + "read?' a question you answer by remembering. It is now a question "
                      + "the dependency graph answers, and dropping the seed is one line.\n\n"
                      + "The boundary is real, not decorative: kranji-gloss-seed depends on "
                      + "kranji-core and nothing else. A seed that could not ship without the "
                      + "hand-crafted set would not be separate in the way that matters, so "
                      + "GlossTsv - the format both read - moved down into core rather than "
                      + "becoming a dependency of one collection on the other.",
                        PhaseStatus.IN_PROGRESS,
                        List.of(
                                new Task("Declare the project's own licence first (gp5 task, still open)", false),
                                new Task("A seeded ZiCollection, discovered alongside HandCrafted", true),
                                new Task("Provenance on the row: seeded, or checked by a person", false),
                                new Task("Precedence in Glosses.of: hand-checked wins, with a test", true),
                                new Task("Reading app and studio both still green with the seed absent", true)),
                        List.of(),
                        "Two collections are discovered, a collision resolves to the "
                      + "hand-checked gloss, and removing the seed leaves a build that still "
                      + "passes.",
                        "Keep one collection and hand-craft on demand, as GlossPlan gp3 does.",
                        "S",
                        "Encodes decision gc5 - a hand-checked gloss wins every collision - "
                      + "which is the one thing here that cannot be retrofitted once 8,000 "
                      + "seeded rows exist, because by then every regression it prevents is "
                      + "invisible."),

                new Phase("gc-2", "The seeder, on one partition",
                        "One partition of the 101: around 80 characters and 87 pairs.",
                        "Unihan kDefinition in, one senses.tsv partition out. The "
                      + "sense-selection policy is the whole difficulty - kDefinition packs "
                      + "several senses into one field in a scholarly register, and this model "
                      + "wants a few ranked senses per READING in a register a child reads. "
                      + "Proving that on one partition is the point of doing one first.\n\n"
                      + "A partition rather than an initial: ZiPartition slices the corpus "
                      + "101 ways by codepoint, the same way the reader fetches it, so a "
                      + "partition means one thing everywhere. The old plan said 'r, the "
                      + "smallest at 117' - that was the by-initial axis, which ran 121 to "
                      + "1,183 pairs and is no longer how the corpus is cut.",
                        PhaseStatus.DONE,
                        List.of(
                                new Task("Seed the monophonic characters of one partition; leave polyphones queued", true),
                                new Task("Sense-selection policy: how many, which, and in what priority", true),
                                new Task("Emit the partition in the existing senses.tsv shape", true),
                                new Task("Regeneration is a no-op diff when inputs are unchanged", true),
                                new Task("Coverage for the partition: seeded / queued / no source", true)),
                        List.of(new Dependency("gc-1", "Needs somewhere to put the output.")),
                        "One partition seeds, parses under the existing TSV reader, and its "
                      + "coverage is a number with a reason for every gap.",
                        "Seed per character on demand rather than per partition.",
                        "M",
                        "Two things carried over from when this was a CC-CEDICT importer.\n\n"
                      + "The numbered-pinyin rendering had to become correct on its own "
                      + "account and now is - the collapses iou-iu, uei-ui, uen-un round-trip "
                      + "across all 1,288 corpus syllables. Seeding from Unihan does not need "
                      + "it, because the readings are already ours, but the hazard is the "
                      + "same: a reading that matches nothing produces a gloss belonging to no "
                      + "pair, and it is gc's validity check that catches it.\n\n"
                      + "And the sense-count heuristic still holds against the new source. An "
                      + "entry packing many senses is where mechanical selection fails, so the "
                      + "count is worth flagging on rather than trusting."),

                new Phase("gc-3", "Triage",
                        "The checks that decide what a person looks at.",
                        "Every automatic check that can flag a row before a human sees it, so "
                      + "the review queue is a few hundred rows and not 8,763. These are "
                      + "cheap, and each one is a rule the hand-crafted set already learned "
                      + "the hard way.",
                        PhaseStatus.IN_PROGRESS,
                        List.of(
                                new Task("No gloss at all for a pair the corpus lists", true),
                                new Task("Length beyond what a child mid-story reads", true),
                                new Task("One gloss reused across two readings of a polyphone - the cardinality bug", false),
                                new Task("A semicolon list long enough to be several senses in one row", true),
                                new Task("A gloss naming a reading the phonic corpus does not list", false),
                                new Task("Register markers: grammatical vocabulary, 'used in', 'variant of'", true),
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
                        "101 partitions seeded in one pass; reviewed partition by partition.",
                        "Seeding and reviewing came apart, and that is the change worth "
                      + "recording. This phase was planned as one grind - import, triage, "
                      + "review, mark done, next - with each partition landing as its own "
                      + "commit.\n\n"
                      + "The seeding half turned out to be a single deterministic run, so it "
                      + "was done for all 101 at once: 7,121 pairs seeded, 593 polyphones "
                      + "queued, 386 characters with nothing usable to seed from, and 386 "
                      + "seeded rows flagged. The bar for that pass was deliberately low - "
                      + "the SPI loads it, the reader and the studio run on it - because a "
                      + "seed nobody has checked is a starting point either way, and having "
                      + "all of it lets the review order be chosen rather than forced by what "
                      + "happens to be imported.\n\n"
                      + "So a partition is no longer 'imported or not'. It is seeded, and "
                      + "reviewed or not, and that second axis is what remains. The per-"
                      + "partition commit still applies to review, where the work actually is.",
                        PhaseStatus.IN_PROGRESS,
                        List.of(
                                new Task("Seed every partition; log what could not be done cleanly, per partition", true),
                                new Task("A partition's log is a file the workbench can load, not a console dump", true),
                                new Task("Review the first partition, to establish a real cost per row", false),
                                new Task("Choose the review order once that cost is known", false),
                                new Task("The remaining partitions, reviewed", false),
                                new Task("The 592 queued polyphones, split by hand", false)),
                        List.of(new Dependency("gc-4",
                                "Needs the tool - to REVIEW. Seeding did not, which is why it "
                              + "ran first.")),
                        "Every partition is seeded and its queue drained to a stated standard, "
                      + "and the corpus coverage number is one a person can defend.",
                        "Stop after the partitions that carry the frequent characters; the tail "
                      + "keeps its unreviewed seed, which the app already shows.",
                        "L",
                        "The plan deliberately does not promise all 101 reviewed. The app "
                      + "degrades silently where there is no gloss - ZiGlossary answers empty "
                      + "and the column stays blank - so a half-reviewed corpus is a working "
                      + "app with less that has been checked, not a broken one. That is what "
                      + "makes stopping a decision rather than a failure.\n\n"
                      + "What the whole-corpus seed changes is the shape of the risk. Before, "
                      + "an unswept partition said nothing; now it says something nobody has "
                      + "read. That is the trade that was taken knowingly, and it is why "
                      + "provenance on the row (gc-1) matters more than it did."),

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
                      + "gloss, and a test holds it.", true),
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
