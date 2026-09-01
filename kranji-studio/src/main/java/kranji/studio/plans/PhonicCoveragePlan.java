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
 * Live tracker for taking the phonic catalogue from a seed to a working
 * corpus - 79 characters today, all 8,105 of the standard set as the target.
 *
 * <p>This is rp9 of {@link KranjiReadingPlan} pulled out into its own plan.
 * It earned that because it is not a UI phase with a long tail of data entry;
 * it is a generation pipeline with an editorial layer on top, and the two
 * need tracking separately.</p>
 *
 * <p>Every number quoted in the decisions below was measured against the
 * vendored {@code Unihan_Readings.txt} rather than estimated. Where a phase
 * says a thing is bounded, the bound was checked.</p>
 */
public final class PhonicCoveragePlan implements Plan {

    public static final PhonicCoveragePlan INSTANCE = new PhonicCoveragePlan();

    private PhonicCoveragePlan() {}

    @Override public String kicker()   { return "DATA"; }
    @Override public String name()     { return "Full Phonic Coverage"; }
    @Override public String subtitle() {
        return "Source of truth: PhonicCoveragePlan.java. Edit, recompile, restart the server.";
    }
    @Override public String summary() {
        return "Take the phonic catalogue from 79 characters to the full 8,105-character standard "
             + "set, deriving readings from Unihan into the existing DSL partitions - with the "
             + "principal reading chosen by evidence, and every disagreement reported rather "
             + "than silently resolved.";
    }

    @Override
    public List<Objective> objectives() {
        return List.of(
                new Objective("Coverage without hand-authoring",
                        "79 to 8,105 is a hundred-fold. Hand entry is not the mechanism. The "
                      + "readings come from a dataset; the DSL partitions become a generated "
                      + "artefact, so the authored form stays reviewable and diffable while "
                      + "nobody types eight thousand characters."),

                new Objective("The principal reading stays a judgement",
                        "A dataset can say which readings exist. Which one a child should see "
                      + "first is editorial. Evidence ranks it, disagreements surface as "
                      + "findings, and a hand override always wins and survives regeneration."),

                new Objective("The tree must not grow",
                        "The projection is bounded by Mandarin phonology, not by corpus size. "
                      + "That was the design claim at 79 characters. At 8,105 it becomes a "
                      + "measurable acceptance gate rather than an argument."));
    }

    @Override
    public List<Decision> decisions() {
        return List.of(
                new Decision("pc1",
                        "Which dataset supplies the readings?",
                        "kTGHZ2013 alone supplies the readings. kMandarin only picks which of "
                      + "them is principal.",
                        "kTGHZ2013 only",
                        DecisionStatus.RESOLVED,
                        "Measured against the vendored file rather than assumed. kTGHZ2013 is "
                      + "keyed to the Table of General Standard Chinese Characters and carries "
                      + "exactly 8,105 entries, so it supplies both the universe and the "
                      + "readings: 8,759 appearances over 1,284 distinct toned syllables, with "
                      + "592 polyphonic characters. Every one of the 8,105 also has a kMandarin "
                      + "value, so no character lacks a principal-reading candidate.\n\n"
                      + "The tempting move is to union in kXHC1983 and kHanyuPinyin for wider "
                      + "coverage. Measuring what they actually add shows that would be a "
                      + "defect, not coverage. kXHC1983 adds a reading for 451 characters, but "
                      + "among the commonest ones what it adds is TONE SANDHI: it gives yi as "
                      + "yi1/yi2/yi4 and bu as bu4/bu2. Those are not separate readings - they "
                      + "are one reading whose tone shifts by context. Taking them would file "
                      + "yi under three different syllables in the tree and tell a child it is "
                      + "polyphonic when it is not. kHanyuPinyin is worse for this purpose: it "
                      + "would add readings to 2,031 characters, but they are literary and "
                      + "historical (bu also as fou3, fou1, fu1). Neither belongs in a "
                      + "children's reading app.",
                        "The conservatism of kTGHZ2013 is the reason to use it, not a limitation "
                      + "to work around. It is prescriptive where the others are descriptive, "
                      + "and prescriptive is what a learner needs.\n\n"
                      + "Cost of the choice, stated plainly: against the three-source union it "
                      + "gives up 516 appearances, 28 syllables, and 427 of the 1,019 characters "
                      + "the union would call polyphonic. Most of that gap is sandhi and "
                      + "literary readings, but not all of it - if a real second reading turns "
                      + "out to be missing, the override file from pc5 adds it by hand.\n\n"
                      + "kHanyuPinlu is a fourth field, covering 2,829 of the 8,105 with "
                      + "empirical frequency counts (hao gets 6060/142/115 across its three "
                      + "readings). It supplies no readings, only evidence for which one is "
                      + "principal - see pc5."),

                new Decision("pc2",
                        "May the dataset be redistributed, and is the build reproducible?",
                        "Read the Unicode terms, record attribution, and commit the input or "
                      + "pin its retrieval.",
                        "",
                        DecisionStatus.OPEN,
                        "Two separate problems wearing one coat. The licence question is "
                      + "probably benign - Unicode's terms permit redistribution with the "
                      + "notice retained - but it has not actually been read, and a plan that "
                      + "assumes it has is the kind that gets discovered late. The "
                      + "reproducibility question is concrete and already true: input/ is "
                      + "untracked, so a fresh clone cannot regenerate the partitions. Since "
                      + "the generated Java IS committed, this does not break the build - but "
                      + "it does mean nobody else can reproduce or audit the generation.",
                        "If redistribution turns out to be restricted, the generated partitions "
                      + "are still fine to ship - they are derived data in a different form. "
                      + "Only the regeneration step would need the file fetched locally."),

                new Decision("pc3",
                        "How many characters, and which?",
                        "All 8,105 - the whole standard set.",
                        "All 8,105",
                        DecisionStatus.RESOLVED,
                        "This is the only decision with real cost attached, and it is the "
                      + "user's to make. The standard itself is stratified into three tiers - "
                      + "level 1 at 3,500 characters (the common set), level 2 at 3,000, and "
                      + "level 3 at 1,605, summing to 8,105. So there are principled cut "
                      + "points, but 5,000 is not one of them: the natural boundaries are "
                      + "3,500, 6,500, and 8,105.\n\n"
                      + "Two of those three cost extra to define. The tier is NOT recoverable "
                      + "from Unihan - kTGHZ2013's value is a dictionary page and entry "
                      + "number (482.140:zhou4), which is pinyin-collation order and carries "
                      + "no tier. Splitting level 1 from level 2 needs the published table "
                      + "sourced as a second input, with its own licensing and provenance "
                      + "question. Taking all 8,105 needs nothing beyond what is already on "
                      + "disk.\n\n"
                      + "And the marginal cost of the full set is close to nothing, because "
                      + "the tree is bounded by phonology: going from 6,500 to 8,105 adds "
                      + "roughly 150 terminals, not 1,605. The partition files grow by glyph "
                      + "literals inside lines that already exist.",
                        "Resolved in favour of everything. That decision pays for itself "
                      + "immediately: pc-3 collapses from 'source and licence a second dataset "
                      + "to recover the tier split, then implement a ranking' to 'the target "
                      + "set is kTGHZ2013's key set' - which is a filter that already exists. "
                      + "It is the single largest effort saving in this plan.\n\n"
                      + "pc-3 still makes the target an input rather than a constant, so "
                      + "narrowing later is a one-line change rather than a migration."),

                new Decision("pc4",
                        "Generated Java source, or load the dataset at runtime?",
                        "Generate the DSL partitions as committed Java.",
                        "Generated source, committed",
                        DecisionStatus.RESOLVED,
                        "The authored form is the review surface. A generated H.java diffs "
                      + "line by line when the dataset or the policy changes, so an editorial "
                      + "correction is visible in git history the same way a hand edit is. "
                      + "Runtime loading would move 5,000 characters behind a parser and put "
                      + "them out of reach of PhonicDeclarations, the compiler, and code "
                      + "review all at once. Partition sizing was checked and does not force "
                      + "the issue: the heaviest initial is the zero initial at 130 "
                      + "declaration lines, so the existing one-file-per-initial scheme holds "
                      + "unchanged at full coverage.",
                        "Measured per-initial declaration lines at 8,105 coverage: zero=130, "
                      + "L=93, T=71, H=67, CH=67, D=66 ... R=31, F=30. No chunking needed."),

                new Decision("pc5",
                        "How is the principal reading chosen?",
                        "kMandarin's first value, cross-checked against kHanyuPinlu frequency, "
                      + "with a hand-override file that wins.",
                        "Evidence-ranked, hand-overridable",
                        DecisionStatus.RESOLVED,
                        "kMandarin is Unihan's own answer to exactly this question, and for "
                      + "101 characters it carries two values where the first is the mainland "
                      + "preference - so the rule is well defined. Where kHanyuPinlu also "
                      + "exists it gives an empirical answer from a real corpus, and the two "
                      + "agreeing is worth more than either alone. Where they disagree the "
                      + "honest move is a WARNING finding, not a silent tiebreak: for a "
                      + "children's reading app the principal reading is what the child sees "
                      + "by default, and getting it wrong is the most visible failure this "
                      + "data can produce.",
                        "The review this implies is bounded, and the bound was measured rather "
                      + "than hoped for. 7,513 of the 8,105 are monophonic - there is no choice "
                      + "to make at all. Of the 592 polyphonic, 370 carry frequency data, and "
                      + "for 355 of those kMandarin already agrees with the most frequent "
                      + "reading. So the whole editorial review is 15 frequency disagreements "
                      + "plus the 26 below: 41 characters, an hour of work, not a data-entry "
                      + "project.\n\n"
                      + "The 15 are worth reading rather than rubber-stamping, because they "
                      + "are systematic: for the commonest polyphonic characters the most "
                      + "frequent reading in running text is the neutral-tone grammatical "
                      + "particle (de for de2, le, zhe, lou), while kMandarin gives the "
                      + "citation reading. For a reading app the particle form is what a child "
                      + "actually meets on the page. That is exactly the kind of call a dataset "
                      + "will not make correctly and the override file exists for.\n\n"
                      + "One case the rule does not cover, found by measuring: for 26 of the "
                      + "8,105, kMandarin's principal is not among kTGHZ2013's readings at all "
                      + "- zheng1 against zheng4 for zheng, ning2 against ning4 for ning, "
                      + "ting3 against ding1 for ting. These are genuine disagreements between "
                      + "two authorities rather than an ordering question, and they cluster in "
                      + "rare characters, so at a 3,500-character target most would not arise. "
                      + "The rule is that kTGHZ2013 wins on which readings EXIST (pc1), so the "
                      + "principal falls back to its first entry and the conflict is reported "
                      + "as a WARNING for review. 26 is small enough to review by hand once.\n\n"
                      + "The override file is the editorial layer. It is hand-maintained, small, "
                      + "and applied after generation, so regenerating never discards a human "
                      + "judgement. The hand-authored H partition's existing choices seed it."),

                new Decision("pc6",
                        "What happens to readings that will not parse?",
                        "Exclude them with an ERROR finding and a baseline; drop the 5 "
                      + "characters they orphan.",
                        "Excluded and baselined",
                        DecisionStatus.RESOLVED,
                        "15 of the 1,327 readings fail PinyinSyllable.parse, and they are all "
                      + "genuinely marginal: syllabic nasals (hng, ng, m with tone marks), the "
                      + "bare vowel e-circumflex, and yo. These are interjections, not "
                      + "vocabulary a child meets in an article. Only 9 characters carry any "
                      + "such reading and only 5 have it as their principal, so excluding them "
                      + "costs 5 characters out of 8,105. Extending PinyinSyllable to model "
                      + "syllabic nasals would be real work in the core type for no reader-"
                      + "facing gain.",
                        "Baselining rather than filtering silently: the count is pinned by a "
                      + "test, so if a future dataset revision changes it somebody finds out."));
    }

    @Override
    public List<Phase> phases() {
        return List.of(
                new Phase("pc-1", "Reading extraction",
                        "Done - and visible, on a grid.",
                        "Three pieces. PhonicPartitionMain splits Unihan's 291,259 lines into "
                      + "22 committed partition files - 264 KB, a few hundred rows each - "
                      + "keeping only the standard set and the three fields Kranji reads. "
                      + "PhonicPartitions reads one off the classpath into SourceReadings, "
                      + "parsing there rather than at partition time so a reading this build "
                      + "cannot model stays visible as data instead of going missing. "
                      + "SourceFindings turns what is left into a review queue.\n\n"
                      + "And a widget, which is what makes the rest worth having: the "
                      + "extraction output on a RelationGrid, a partition at a time, with the "
                      + "conflicts in a column beside the characters they concern. The "
                      + "generator reads exactly these rows, so anything wrong here is wrong "
                      + "in the corpus before a line of it is emitted.",
                        PhaseStatus.DONE,
                        List.of(
                                new Task("Partition Unihan into 22 committed files, filed by principal reading", true),
                                new Task("Read a partition off the classpath, parsing readings there", true),
                                new Task("Keep unmodellable readings rather than dropping them", true),
                                new Task("Report conflicts as findings, not exceptions", true),
                                new Task("Serve a partition as an ES module, data literals only", true),
                                new Task("Render it on a RelationGrid, partition-switchable", true),
                                new Task("Pin the counts: 8,100 characters, 8,759 appearances, 1,284 syllables, 592 polyphonic", true)),
                        List.of(),
                        "8,100 characters load, the 5 unservable are named, and the grid shows "
                      + "any partition on demand. 211 tests green in kranji-core, 23 in the app.",
                        "The partition files are generated and committed; regenerating is "
                      + "idempotent, and reverting the commit reverts the source data.",
                        "S",
                        "Built as a widget on purpose. A headless extractor is checked by asking "
                      + "it questions you already thought of; a grid is checked by looking. The "
                      + "partitioning is what makes that possible - 8,100 rows is not a view, "
                      + "400 is.\n\n"
                      + "Also the one number in this plan that moved once the code existed: the "
                      + "review queue is 35 characters, not the 41 estimated. The first count "
                      + "treated the corpus writing an unstressed form - wu for wu3, r for "
                      + "erhua - as a disagreement, when it is the same reading spoken lightly. "
                      + "Requiring the observed form to be one the standard actually lists "
                      + "leaves 9 real frequency conflicts beside the 26 authority ones."),

                new Phase("pc-2", "Principal-reading policy",
                        "The editorial layer, with its disagreements visible.",
                        "Rank the readings of each character: kMandarin first value wins, "
                      + "cross-checked against kHanyuPinlu where it exists. Emit a WARNING "
                      + "finding wherever the two disagree. Apply a hand-maintained override "
                      + "file last, so a human judgement is never lost to regeneration.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Rank by kMandarin, cross-check kHanyuPinlu", false),
                                new Task("Emit WARNING findings on disagreement", false),
                                new Task("Hand-override file, applied after ranking", false),
                                new Task("Seed the overrides from the hand-authored H partition", false),
                                new Task("Review the disagreement list for the most frequent characters", false)),
                        List.of(new Dependency("pc-1", "Needs the readings and the frequency data.")),
                        "Every character has exactly one principal reading; the disagreement "
                      + "list is reviewed rather than merely produced.",
                        "The override file is data. Reverting it reverts the editorial layer "
                      + "without touching the pipeline.",
                        "S",
                        "The review task is the one that cannot be automated, and it is 41 characters: "
                      + "15 frequency disagreements and 26 authority conflicts. Small enough to do "
                      + "properly rather than sample, and the frequent end of it is where a wrong "
                      + "default would actually be seen."),

                new Phase("pc-3", "Target set selection",
                        "Make the target a parameter, not a constant.",
                        "The set of characters to cover is an input to generation, not a "
                      + "hardcoded number - but with pc3 resolved to the full set, that input "
                      + "is simply 'every codepoint carrying a kTGHZ2013 field', which pc-1 "
                      + "already computes. What is left is the coverage report: what is in, "
                      + "what is out, and why.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Target set as an input, defaulting to the kTGHZ2013 key set", false),
                                new Task("Coverage report: covered, excluded, and the reason for each", false)),
                        List.of(new Dependency("pc-1", "Needs the character universe.")),
                        "The report accounts for all 8,105: 8,100 covered, 5 excluded by name.",
                        "Parameterised by construction, so reverting means changing the input "
                      + "back.",
                        "XS",
                        "Collapsed to near-nothing by pc3 resolving to the full set - no "
                      + "external table to source, no ranking to implement, no tier split to "
                      + "recover. This phase was sized M when the target was 5,000; taking "
                      + "everything is what made it trivial."),

                new Phase("pc-4", "Partition generator",
                        "Emit the DSL, in the shape a human would have written.",
                        "Generate the 22 per-initial partition files in the existing "
                      + "SimpleZiDsl form - syllable-first, one line per toned syllable, "
                      + "ZiDecl.alt for non-principal appearances with the principal reading "
                      + "named in a trailing comment, exactly as the hand-authored H does "
                      + "today. Deterministic ordering so regeneration produces no spurious "
                      + "diff.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Emit per-initial partitions in the existing DSL shape", false),
                                new Task("Deterministic ordering: final by spelling, then tone, then codepoint", false),
                                new Task("Trailing comment naming the principal reading on every alt", false),
                                new Task("Regeneration is a no-op diff when inputs are unchanged", false)),
                        List.of(new Dependency("pc-2", "Needs to know which appearance is principal."),
                                new Dependency("pc-3", "Needs to know which characters to emit.")),
                        "Regenerating twice yields byte-identical files, and the output compiles "
                      + "against the unchanged DSL.",
                        "The generated files are committed. Reverting the commit reverts the "
                      + "corpus.",
                        "M",
                        "The generator writes the form the H exemplar established rather than "
                      + "inventing a machine-shaped one. That is what keeps a generated corpus "
                      + "reviewable - a diff should read like an edit somebody made."),

                new Phase("pc-5", "Validation at scale",
                        "PhonicDeclarations already checks the right things; wire it into the "
                      + "build.",
                        "The emphasis invariant - exactly one principal reading per character - "
                      + "is already enforced and already tested. What is missing at 5,000 is "
                      + "that a bad regeneration should fail the build rather than surface in "
                      + "the browser, and that accepted anomalies need somewhere to sit. A "
                      + "baseline file, mirroring the approach the conformance layer already "
                      + "uses.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Run PhonicDeclarations.check over the generated corpus in the build", false),
                                new Task("Baseline file for accepted findings, with the count pinned", false),
                                new Task("Fail on any new ERROR; report WARNINGs without failing", false)),
                        List.of(new Dependency("pc-4", "Needs a generated corpus to validate.")),
                        "A deliberately corrupted regeneration fails the build with the "
                      + "offending character named.",
                        "Validation only reports. Disabling the gate cannot corrupt data.",
                        "S",
                        "The checker exists and is green over the 79-character seed. This phase "
                      + "is about where its output goes, not about writing it."),

                new Phase("pc-6", "Serving at scale",
                        "Two things measured to break between 79 and 5,000.",
                        "First, a tree node's note lists every glyph in its syllable - fine at "
                      + "a median of 5, wrong at the heaviest syllable's 74, and 57 syllables "
                      + "exceed 20. Cap the note and let the characters widget carry the full "
                      + "list. Second, ZiDataGetAction scans the whole registry per request, "
                      + "which is acceptable at 79 and wasteful at 5,000 - precompute the "
                      + "syllable index once. Also resolve the badge that currently reads '83 "
                      + "characters' when 83 is an appearance count.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Cap the tree note; full list stays in the characters widget", false),
                                new Task("Precompute the syllable index in ZiDataGetAction", false),
                                new Task("Decide and fix the root badge: appearances or distinct glyphs", false),
                                new Task("Check the served tree payload size at full coverage", false)),
                        List.of(new Dependency("pc-4", "Needs the real corpus to size against.")),
                        "The heaviest syllable renders correctly and the tree payload stays "
                      + "within a sensible budget.",
                        "Display and indexing only. No data is affected.",
                        "S",
                        "Both problems were found by measuring rather than by hitting them, so "
                      + "they can be fixed before the corpus lands rather than after.\n\n"
                      + "This is also the one phase that could grow. The served tree is "
                      + "currently 21 KB for 77 terminals; at 1,284 terminals the same shape "
                      + "extrapolates to roughly 350 KB in a single payload. Capping the note "
                      + "should take a large bite out of that, since notes are most of the "
                      + "per-terminal bytes. If it does not, the tree needs lazily loaded "
                      + "children - which is a real feature, not a tweak, and would add a "
                      + "phase. Measure before assuming either way."),

                new Phase("pc-7", "Reconcile with the typed layer",
                        "Most characters will have a reading and no composition.",
                        "At 5,000 phonic against 2,532 typed, roughly 2,500 characters have a "
                      + "reading and no composition record. Per CD-001 that is legal - "
                      + "ZiCharUTF8 is the hub and the catalogues are independent indexes, so "
                      + "neither has to be complete for the other to work. What it means in "
                      + "practice is that the characters widget must degrade gracefully rather "
                      + "than assume a composition exists, and the gap should be visible as a "
                      + "number somebody can act on.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Characters widget shows composition when present, nothing when absent", false),
                                new Task("Structural-coverage report: phonic-only, typed-only, both", false)),
                        List.of(new Dependency("pc-4", "Needs the corpus to measure the gap.")),
                        "A phonic-only character renders without error and without an empty "
                      + "placeholder.",
                        "Widget-level. Reverting restores the current behaviour.",
                        "S",
                        "This is CD-001 being cashed in. If the hub design is right, this phase "
                      + "is small - and it being small is the evidence."),

                new Phase("pc-8", "Cut over",
                        "Replace the seed, keep the judgement.",
                        "The generated tier supersedes the hand-seeded common2000 partitions. "
                      + "The H exemplar's editorial choices - which reading is principal for "
                      + "hao, he, hai - move into the override file first, so the cutover loses "
                      + "no human decision. Then the seed partitions go.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Migrate the H exemplar's choices into the override file", false),
                                new Task("Swap SimpleZiRegistry onto the generated tier", false),
                                new Task("Delete the superseded seed partitions", false),
                                new Task("Verify the served tree end to end at full coverage", false)),
                        List.of(new Dependency("pc-5", "Validation must gate the corpus first."),
                                new Dependency("pc-6", "Serving must handle the volume first."),
                                new Dependency("pc-2", "The override file must exist to migrate into.")),
                        "The syllable catalogue serves 5,000 characters, and hao still reads "
                      + "principally hao3 because the override says so.",
                        "The seed partitions are deleted in their own commit, so restoring them "
                      + "is a revert.",
                        "S",
                        "Sequenced last on purpose. The seed is the only thing currently proving "
                      + "the pipeline works end to end, so it should not be removed until the "
                      + "replacement is serving."));
    }

    @Override
    public List<Acceptance> acceptance() {
        return List.of(
                new Acceptance("Coverage",
                        "All 8,100 servable characters resolve to a principal reading, and "
                      + "PhonicDeclarations.check returns no ERROR over the generated corpus.",
                        false),

                new Acceptance("The tree stayed bounded",
                        "The projection holds under 2,000 nodes at full coverage - measured at "
                      + "1,284 distinct toned syllables for the entire 8,105-character standard "
                      + "set, against 77 today. This is the design claim of the phonic "
                      + "catalogue and the one number that would falsify it.",
                        false),

                new Acceptance("Regeneration is deterministic",
                        "Running the generator twice over unchanged inputs produces byte-"
                      + "identical partition files, so a real diff always means a real change.",
                        false),

                new Acceptance("Editorial judgement survives",
                        "Every override in the hand-maintained file is still applied after a "
                      + "full regeneration, verified by a test that would fail if the file were "
                      + "ignored.",
                        false),

                new Acceptance("Polyphony still works at volume",
                        "A polyphonic character appears under each of its readings, marked "
                      + "principal at exactly one - the property proved at 79 characters, "
                      + "re-checked at scale over the 535 characters with two readings and the "
                      + "57 with three or more.",
                        false),

                new Acceptance("The heaviest syllable renders",
                        "The syllable holding the most characters serves and displays correctly "
                      + "- 74 characters in one terminal at full coverage, against 4 today.",
                        false));
    }

    /** Where the numbers stand, and where this plan expects them to land. */
    public List<Metric> metrics() {
        return List.of(
                new Metric("Characters covered", "79", "8,100", "+8,021"),
                new Metric("Appearances in the tree", "83", "8,759", "+8,676"),
                new Metric("Terminals (toned syllables)", "77", "1,284", "+1,207"),
                new Metric("Partition files", "22", "22", "unchanged"),
                new Metric("Chars in the heaviest syllable", "4", "74", "+70"),
                new Metric("Polyphonic characters", "3", "592", "+589"));
    }
}
