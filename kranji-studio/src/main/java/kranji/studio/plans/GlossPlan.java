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
 * Live tracker for glosses — what a character means when read a given way.
 *
 * <p>The dimension the corpus has never had. Readings are covered for 8,100
 * characters and structure for 800; meaning is covered for none of them at the
 * right cardinality, which is the subject of CF-002.</p>
 */
public final class GlossPlan implements Plan {

    public static final GlossPlan INSTANCE = new GlossPlan();

    private GlossPlan() {}

    @Override public String kicker()   { return "CORPUS"; }
    @Override public String name()     { return "Glosses"; }
    @Override public String subtitle() {
        return "Source of truth: GlossPlan.java. Edit, recompile, restart the server.";
    }
    @Override public String summary() {
        return "A concise meaning for every (character, reading) pair a reader meets - keyed on "
             + "the pair because 好 hǎo and 好 hào are different facts.";
    }

    @Override
    public List<Objective> objectives() {
        return List.of(
                new Objective("A meaning where the reader stopped",
                        "A child mid-story who has stopped at a character wants enough to keep "
                      + "going, not a dictionary entry. A few words, in the sense the article "
                      + "is using."),
                new Objective("Keyed on the pair, always",
                        "好 hǎo is good; 好 hào is to be fond of. A per-character gloss can only "
                      + "be filled by merging senses that belong to different readings, which "
                      + "is why meaning left SimpleZi and why it comes back as its own tier."),
                new Objective("Self-contained with the content it serves",
                        "The bundled library and its glosses ship together and are complete "
                      + "together. An article whose characters cannot be explained is only "
                      + "half-prepared, and a test says so."),
                new Objective("Provenance that can be stated in one sentence",
                        "Every gloss belongs to a module whose licence and source are named. "
                      + "Hand-crafted and ported data never share a file.")
        );
    }

    @Override
    public List<Decision> decisions() {
        return List.of(
                new Decision("g1",
                        "What is a gloss keyed on?",
                        "The (character, reading) pair, as codePoint:reading - the reading in "
                      + "the canonical numbered form, di4 rather than dià.",
                        "The pair, not the character",
                        DecisionStatus.RESOLVED,
                        "SimpleZi already argues it: meaning attaches to a reading, and a "
                      + "per-character field can only be filled by merging senses across "
                      + "readings. CD-001 puts meaning on a spoke keyed on (character, phonic) "
                      + "for the same reason.\n\n"
                      + "The reading's FORM is part of the decision, not an implementation "
                      + "detail. The canonical form is ASCII apart from ü - four syllables "
                      + "carry it - so it holds no combining marks and no two byte sequences "
                      + "that look alike and compare unequal. A diacritic key has both, and one "
                      + "of the three facilities normalising differently from another is a "
                      + "failure nothing can catch at the point it happens.",
                        "The key is byte-identical to the known set's and the article census's, "
                      + "so all three join without translation - a pane holds one map and "
                      + "answers three questions. Costs 8% more entries than a per-character "
                      + "key: 8,764 pairs against 8,100 characters, of which only 664 are "
                      + "polyphonic.\n\n"
                      + "Byte-identity is not a nicety, and the flip to the canonical form "
                      + "demonstrated it: a saved known set left in the old diacritic form "
                      + "stopped intersecting the census, and the only symptom was every "
                      + "article reading 0%. Nothing threw. Whatever else changes here, the "
                      + "three keys change together or the join silently empties."),

                new Decision("g2",
                        "Where does the data come from?",
                        "Hand-crafted first, CC-CEDICT for the tail later, never in one module.",
                        "Split by provenance, module by module",
                        DecisionStatus.RESOLVED,
                        "Most per-reading sources are share-alike - CC-CEDICT, the original "
                      + "CEDICT, Wiktionary. Unihan is differently licensed but glosses per "
                      + "CHARACTER, so it cannot fill this key without hand-splitting all 664 "
                      + "polyphones. There is no permissive per-reading corpus to take.\n\n"
                      + "Hand-crafting the set the library actually needs is 447 pairs, which "
                      + "is a weekend rather than a project, carries no third-party licence at "
                      + "all, and is a learning exercise worth having.",
                        "kranji-gloss holds project-authored data under the project's own "
                      + "licence. A later kranji-gloss-cedict would hold ported data under CC "
                      + "BY-SA with its own LICENSE and attribution. A COLLECTION of separately "
                      + "licensed modules is not an adaptation, so keeping them apart is the "
                      + "difference between 'Kranji is permissive, one data module is BY-SA' "
                      + "and a claim nobody can check.\n\n"
                      + "Note for the README when it is written: share-alike IS copyleft. A "
                      + "build without the ported module is fully permissive; a build with it "
                      + "is not, and saying otherwise would be wrong."),

                new Decision("g3",
                        "How is the data authored?",
                        "A Java DSL for hand-crafted data; generated TSV for ported data.",
                        "Format follows provenance",
                        DecisionStatus.RESOLVED,
                        "The DSL's value is that the compiler checks it - a mistyped final is "
                      + "an error on the offending line rather than a gloss attached to a "
                      + "reading nothing has. That benefit is real for a person typing and "
                      + "worthless to a generator, which validates before writing a byte.\n\n"
                      + "Generated Java would also mean thousands of source files nobody reads, "
                      + "enormous regeneration diffs, and compile cost - the shape CD-001 calls "
                      + "a category error and the phonic corpus already avoided.",
                        "Hand-crafted glosses read as nested builders, each terminator naming "
                      + "where it returns to:\n\n"
                      + "    zi(\"东\").read(\"dong1\").means(\"east\")\n"
                      + "            .eg(\"东方\").eg(\"山东\").eg(\"东西\", 1).build()\n\n"
                      + "A flat call taking every field positionally - the shape first "
                      + "sketched here - cannot express a character with two readings, a "
                      + "reading with two senses, or a sense with four examples, all of which "
                      + "the data has. The alternative to nesting is one builder holding "
                      + "mutable state, which gives up the compile-time checking that was the "
                      + "reason for a DSL at all: each builder therefore returns the NEXT "
                      + "builder, not itself, and done() returns to the parent.\n\n"
                      + "Order is recorded at authoring time and stamped at build() - the "
                      + "sequence a person writes senses and examples in IS the ranking, so it "
                      + "never has to be declared, and cannot disagree with itself.\n\n"
                      + "Ported glosses would be TSV partitions with a licence line in the "
                      + "header. Not mirroring kranji/phonic's initials - see g4."),

                new Decision("g4",
                        "Where does a gloss live?",
                        "In a numbered partition. Which one carries no meaning.",
                        "Filed nowhere in particular",
                        DecisionStatus.RESOLVED,
                        "REVERSED. This was first answered 'in the partition of the reading it "
                      + "glosses', on the argument that a háng gloss filed under x/ is where "
                      + "nobody editing háng would look. Writing the first partition showed "
                      + "the cost: the entry for a character is a character with its readings "
                      + "beneath it, so filing by reading tears one entry across files - 得 "
                      + "into three, 行 into two - and no file ever shows a character whole. "
                      + "The thing a person edits is the character, and it should be in one "
                      + "place.\n\n"
                      + "The deeper reason is CD-001: the codepoint is the hub, and a reading "
                      + "is corrigible data. Deriving the file from the reading makes "
                      + "correcting a reading a file move.",
                        "Entries are keyed on the codepoint and partitions are numbered - "
                      + "Partition01, Partition02. A partition can be split as the corpus grows "
                      + "without touching the model, because nothing reads meaning into which "
                      + "file an entry sits in: a re-split is a move, not a migration.\n\n"
                      + "The filing test planned under gp2 is dropped with the rule it would "
                      + "have checked. There is nothing left to assert."),

                new Decision("g5",
                        "How are sources composed?",
                        "Explicitly, by the consumer. No ServiceLoader, no mutable registry.",
                        "Explicit composition",
                        DecisionStatus.RESOLVED,
                        "CF-003 records what the alternative costs: BasicSet holds a mutable "
                      + "list behind a latch, and its 549 singulars only arrive if something "
                      + "calls registerInto. A consumer that forgets gets partial data "
                      + "silently, and partial coverage is indistinguishable from the coverage "
                      + "gap we already expect.",
                        "Each data module exposes a List<Gloss> constant; the consumer builds "
                      + "the registry from the sources it wants. No boot order, no discovery "
                      + "magic, and a build without the ported module is a compile-time fact.")
        );
    }

    @Override
    public List<Phase> phases() {
        return List.of(
                new Phase("gp1", "The facility",
                        "The types, the DSL, and the registry - with no data in them.",
                        "Not one type but a small tier, in kranji.simple.gloss: ZiGloss holds "
                      + "SoundGlosses; a SoundGloss maps Meaning to Sense; a Sense carries "
                      + "RankingInfo and its examples. An example is referenced, not repeated - "
                      + "EgKey identifies a phrase, EgRef picks one of its senses, and "
                      + "ExampleEntry defines it once in a registry, because 大人 teaches both "
                      + "大 and 人 and should be written once.",
                        PhaseStatus.DONE,
                        List.of(
                                new Task("The gloss tier - pair key, length caps, orderings", true),
                                new Task("GlossDsl - nested typestate builders, done()/build()", true),
                                new Task("Examples registry - EgKey identity, shadowing reported", true),
                                new Task("Glosses + GlossSource - explicit composition, find(pair)", true),
                                new Task("kranji-gloss module for hand-crafted data", true)),
                        List.of(),
                        "A gloss can be declared, found by pair, and nothing is hard-coded "
                      + "about where the data came from.",
                        "",
                        "S",
                        "Two caps are enforced by the types rather than by a check: a Meaning "
                      + "is 60 characters, an EgKey 12. A gloss too long to read cannot be "
                      + "constructed, so the conciseness check planned under gp2 has nothing "
                      + "left to test."),

                new Phase("gp2", "The coverage tool",
                        "Built before the data, so the data can be judged as it lands.",
                        "Ten checks in GlossValidityTest. Three of the six first planned are "
                      + "gone - filing with the rule it checked (g4), conciseness into the "
                      + "types (gp1), corpus-coverage-per-partition with the idea that a "
                      + "partition means anything. Five arrived that were not planned, all "
                      + "about the integrity of examples, which did not exist as a concept "
                      + "when this phase was written.",
                        PhaseStatus.IN_PROGRESS,
                        List.of(
                                new Task("Validity - every gloss names a reading the corpus lists", true),
                                new Task("Polyphone completeness - all readings, or none", true),
                                new Task("Every phrase referenced is defined, and every one defined is used", true),
                                new Task("Every example contains the character it explains", true),
                                new Task("Every example READS it the way the gloss says", true),
                                new Task("Every stated reading is real, and disagrees with the corpus", true),
                                new Task("Provenance - every source names its licence", true),
                                new Task("Demand coverage - the 447 pairs the library uses", false)),
                        List.of(new Dependency("gp1", "Needs something to measure.")),
                        "The gap is a number that a test reports and a regression fails.",
                        "",
                        "S",
                        "Validity is the check that earns its keep. A gloss naming a reading "
                      + "the corpus does not list matches nothing, shows nothing, and errors "
                      + "nowhere - the same silent failure the known set and the article "
                      + "overrides each had to be guarded against.\n\n"
                      + "Polyphone completeness exists only because the key is a pair. A "
                      + "half-glossed polyphone is worse than none: it implies the ungloss'd "
                      + "reading has no meaning.\n\n"
                      + "The lesson of the unplanned five: sound annotation on examples was "
                      + "argued for on the strength of a check - that an example filed under 行 "
                      + "háng must read 行 as háng - which was then not written. It was hiding "
                      + "two wrong records. A guarantee nobody encoded is not a guarantee, and "
                      + "an argument for a mechanism is a debt until the check exists.\n\n"
                      + "Demand coverage is the one that still matters and the one still "
                      + "missing. It is deliberately last only because it will read 6% until "
                      + "gp3 is done; it must land before gp3 closes, not after."),

                new Phase("gp3", "The minimal viable set",
                        "447 pairs, hand-crafted, covering the bundled library exactly.",
                        "Every (character, reading) the 23 demo articles use, glossed by hand. "
                      + "28 pairs are written across 25 characters, referencing 33 phrases - "
                      + "419 to go. Ten genuine polyphones: 地 de/dì, 得 de/dé, 觉 jiào/jué, "
                      + "长 cháng/zhǎng, 为 wèi/wéi, 行 háng/xíng and four more.",
                        PhaseStatus.IN_PROGRESS,
                        List.of(
                                new Task("Partition01 - the first 28 pairs, as a shape to review", true),
                                new Task("Author the remaining 419", false),
                                new Task("Review the 文言 readings against their context", false),
                                new Task("Review the register of the highest-frequency particles", false),
                                new Task("Demand coverage reaches 100% and a test holds it there", false)),
                        List.of(new Dependency("gp1", "Needs the DSL."),
                                new Dependency("gp2", "Needs the gap to be visible.")),
                        "Every character in every bundled article can be explained, and adding "
                      + "an article without glossing it fails the build.",
                        "Ship partial coverage; the reader shows a gloss where it has one.",
                        "M",
                        "The review is the cost, not the typing - particularly in the 文言 "
                      + "material, where the modern first sense is often the wrong one.\n\n"
                      + "Register is the open question Partition01 raises and does not settle. "
                      + "的 is glossed '(marks what belongs to what)' rather than 'possessive "
                      + "particle', on the argument that a child mid-story is not helped by "
                      + "grammatical vocabulary. It is also the character they will meet more "
                      + "than any other, so whichever way it goes it is a few hundred "
                      + "encounters. Worth deciding once, deliberately, before the other 419 "
                      + "are written in whichever voice.\n\n"
                      + "Examples turned out to be cheap - a phrase is one line in a registry - "
                      + "so the instinct to add one per meaning was too thin. Four is not "
                      + "extravagant."),

                new Phase("gp4", "In the app",
                        "Where a stopped reader actually is.",
                        "The gloss beside the reading in the character pane, and a gloss map "
                      + "delivered the way the syllable map already is - same codepoint "
                      + "partitioning, same loader shape, because the key is the same.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Gloss in the character pane, per reading", false),
                                new Task("/gloss-map, partitioned like /syllable-map", false),
                                new Task("Gloss on the reader's character selection", false),
                                new Task("Degrade silently where there is no gloss", false)),
                        List.of(new Dependency("gp3", "Needs something to show.")),
                        "Selecting a character shows what it means in the reading being shown.",
                        "The pane omits the line; nothing else changes.",
                        "S",
                        ""),

                new Phase("gp5", "The ported tail",
                        "Coverage beyond what the library needs - if it is ever wanted.",
                        "A generator reading CC-CEDICT into TSV partitions, in kranji-gloss-"
                      + "cedict under CC BY-SA with attribution. Deliberately last: the "
                      + "hand-crafted set proves the facility, and the licence decision should "
                      + "be made when there is a reason for it rather than in advance.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Declare the project's own licence first", false),
                                new Task("Confirm CC-CEDICT's terms from the source", false),
                                new Task("u: to ü and tone 5 to 0, tested", false),
                                new Task("Sense-selection policy", false),
                                new Task("kranji-gloss-cedict with LICENSE and attribution", false)),
                        List.of(new Dependency("gp2", "The coverage tool is how its output is judged.")),
                        "Corpus coverage is a number, and every ported gloss is attributable.",
                        "Hand-craft on demand as articles are added.",
                        "L",
                        "The conversion got much cheaper while this phase sat unstarted, and "
                      + "that is worth recording rather than rediscovering. It was the real "
                      + "work here: CC-CEDICT writes numbered pinyin, and our own numbered "
                      + "rendering used to emit the UNDERLYING spelling - jüen1 for jūn, liou2 "
                      + "for liú - so importing meant reimplementing pinyin orthography "
                      + "underneath the import.\n\n"
                      + "That rendering is now the system's internal form, so it had to be "
                      + "made correct on its own account: it shares its spelling rules with the "
                      + "diacritic renderer, and the collapses iou→iu, uei→ui, üen→un are "
                      + "checked to round-trip across all 1,284 corpus syllables. What is left "
                      + "of the conversion is u: to ü and tone 5 to 0 - and 5 is already "
                      + "accepted on input.\n\n"
                      + "The hazard it guarded against is unchanged: a mis-converted reading "
                      + "produces a gloss that silently matches nothing. It is now gp2's "
                      + "validity check that catches it rather than care during import.\n\n"
                      + "Sense selection is the other half. CC-CEDICT slots mix distinct "
                      + "senses, near-synonyms, register notes and machinery, so 'the first "
                      + "two' can return two synonyms of one meaning. Filter the machinery, "
                      + "then fill a length budget, and flag high-slot-count entries for "
                      + "review - the count is a good proxy for mechanical selection having "
                      + "failed.")
        );
    }

    @Override
    public List<Acceptance> acceptance() {
        return List.of(
                new Acceptance("Keyed on the pair",
                        "A polyphonic character carries a different gloss per reading, and the "
                      + "key matches the known set's byte for byte.", false),
                new Acceptance("The library is self-contained",
                        "Every (character, reading) in every bundled article has a gloss, and "
                      + "adding an article without glossing it fails the build.", false),
                new Acceptance("No gloss points at nothing",
                        "Every gloss names a reading the phonic corpus lists.", false),
                new Acceptance("Provenance is stated per module",
                        "Every gloss belongs to a module whose licence and source are named, "
                      + "and hand-crafted data never shares a file with ported data.", false),
                new Acceptance("Concise means concise",
                        "No gloss exceeds the length a child mid-story will read.", false)
        );
    }
}
