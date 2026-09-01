package kranji.studio.plans;

import hue.captains.singapura.js.homing.studio.base.tracker.Acceptance;
import hue.captains.singapura.js.homing.studio.base.tracker.Decision;
import hue.captains.singapura.js.homing.studio.base.tracker.DecisionStatus;
import hue.captains.singapura.js.homing.studio.base.tracker.Objective;
import hue.captains.singapura.js.homing.studio.base.tracker.Phase;
import hue.captains.singapura.js.homing.studio.base.tracker.PhaseStatus;
import hue.captains.singapura.js.homing.studio.base.tracker.Plan;
import hue.captains.singapura.js.homing.studio.base.tracker.Task;

import java.util.List;

/**
 * What goes into the library, and in what order.
 *
 * <p>Separate from {@code KranjiReadingPlan} because content and application
 * are now genuinely separable: a collection is built, versioned and shipped as
 * its own jar, so adding material is a content release rather than an
 * application release. The two plans move at different speeds.</p>
 *
 * <p>Design is in <i>Reading — Article Library</i>. This file tracks what is
 * actually being added.</p>
 */
public final class ContentPlan implements Plan {

    public static final ContentPlan INSTANCE = new ContentPlan();

    private ContentPlan() {}

    @Override public String kicker()   { return "CONTENT"; }
    @Override public String name()     { return "Article Content"; }
    @Override public String subtitle() {
        return "Source of truth: ContentPlan.java. Edit, recompile, restart the server.";
    }
    @Override public String summary() {
        return "What the library holds and what goes in next - bounded collections of "
             + "public-domain and original material, each mounted whole.";
    }

    @Override
    public List<Objective> objectives() {
        return List.of(
                new Objective("Enough to read every day",
                        "A child who reads one piece a day should not run out. That is the "
                      + "only volume target worth having, and it is a lot more than the "
                      + "twenty-three pieces the demo ships."),
                new Objective("Every collection reviewable",
                        "A collection is mounted whole, so it must be small enough that "
                      + "someone can read all of it and say it is right. Thirty is the "
                      + "ceiling; a canonical set larger than that is a shelf, not a "
                      + "collection."),
                new Objective("Provenance that survives distribution",
                        "Public-domain or written for the project, recorded per collection. "
                      + "A collection that cannot state where its text came from cannot "
                      + "ship."),
                new Objective("Readings correct in context",
                        "Every polyphonic character in every article read once, in context, "
                      + "and pinned where the corpus principal is wrong. This is the work "
                      + "that does not scale automatically and has to be planned for.")
        );
    }

    @Override
    public List<Decision> decisions() {
        return List.of(
                new Decision("c1",
                        "How is 唐诗三百首 brought in, given the thirty-article ceiling?",
                        "By its own traditional divisions, then by poet where a division is "
                      + "still too large.",
                        "Classify, then partition - and the anthology already carries its "
                      + "classification",
                        DecisionStatus.RESOLVED,
                        "蘅塘退士 organised it by form: 五言古诗, 七言古诗, 乐府, 五言律诗, "
                      + "七言律诗, 五言绝句, 七言绝句. That is a real distinction a reader can "
                      + "navigate by, so it is preferred to any numbering we would invent. "
                      + "Two of those divisions still exceed thirty - 五言律诗 at about "
                      + "eighty, 七言绝句 at about fifty - and they subdivide by poet, which "
                      + "is again meaningful rather than arbitrary.",
                        "Mounted under a 唐诗三百首 branch, so the anthology stays legible as "
                      + "one thing even though it is many collections."),
                new Decision("c2",
                        "Is 唐诗三百首 free to use?",
                        "Yes. Both the poems and the compilation are long out of copyright.",
                        "No permission needed, and no attribution obligation beyond honesty",
                        DecisionStatus.RESOLVED,
                        "The poems are Tang, seventh to tenth century. The anthology is "
                      + "蘅塘退士's, compiled 1763. Both are far outside any copyright term. "
                      + "The caution that does apply is to the source text we transcribe "
                      + "from: a modern annotated edition's apparatus - its notes, its "
                      + "punctuation choices, its glosses - can carry rights even where the "
                      + "poem does not. Transcribe the poem, not the edition.",
                        "Contrast 施氏食狮史, already in the demo set: 赵元任 died in 1982, so "
                      + "it is in copyright until the 2050s and should not ship."),
                new Decision("c3",
                        "What happens to the three open-bucket demo collections?",
                        "Undecided - they need names that anticipate their subdivision.",
                        "儿歌, 生活记叙 and 科普说明 grow every time we write another piece",
                        DecisionStatus.OPEN,
                        "Identity is collection:local and a local id is immutable once "
                      + "published, so splitting an overgrown collection later changes the "
                      + "identity of everything in it. Nothing has published these ids yet, "
                      + "which makes renaming free today and a migration tomorrow. The "
                      + "candidates are classification - 科普说明 into animals and natural "
                      + "phenomena, 生活记叙 into school and home - or an edition number "
                      + "where no distinction exists, which is 儿歌's case.",
                        "Blocks cp3. Cheap now, a migration later.")
        );
    }

    @Override
    public List<Phase> phases() {
        return List.of(
                new Phase("cp1", "Demo set",
                        "One collection per form, enough to exercise the library.",
                        "Seven collections, twenty-three articles: 唐诗启蒙, 儿歌, 寓言故事, "
                      + "成语故事, 生活记叙, 科普说明, 文言启蒙.",
                        PhaseStatus.DONE,
                        List.of(
                                new Task("Seven collections, each mounted whole", true),
                                new Task("Tang poems and 文言 originals - public domain", true),
                                new Task("Fables and idiom stories - retold for the project", true),
                                new Task("Rhymes, prose and exposition - original", true),
                                new Task("Every polyphonic reading reviewed in context", true),
                                new Task("Nine wrong principals pinned", true)),
                        List.of(),
                        "The library renders, every article parses, and the reviewed-reading "
                      + "count is held still at 324.",
                        "",
                        "M",
                        "The review is the part that does not scale: 349 unpinned readings "
                      + "across 23 articles, of which nine were actually wrong. Roughly one "
                      + "genuine correction every two and a half articles."),

                new Phase("cp2", "唐诗三百首",
                        "The anthology, by its own divisions.",
                        "About 310 poems in seven form-collections, subdividing by poet where "
                      + "a form exceeds thirty. The first real test of whether the library "
                      + "scales past a demo.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Choose a public-domain source text to transcribe from", false),
                                new Task("五言绝句 - about 29, one collection", false),
                                new Task("七言绝句 - about 51, subdivide by poet", false),
                                new Task("五言律诗 - about 80, subdivide by poet", false),
                                new Task("七言律诗 - about 53, subdivide by poet", false),
                                new Task("五言古诗 - about 35, subdivide", false),
                                new Task("七言古诗 and 乐府", false),
                                new Task("Mount the form-collections under a 唐诗三百首 branch", false),
                                new Task("Review every polyphonic reading in context", false)),
                        List.of(),
                        "Every poem parses, every collection is at or under thirty, and the "
                      + "reviewed-reading count is recorded rather than merely raised.",
                        "",
                        "L",
                        "The reading review is the cost here, not the transcription. At the "
                      + "demo's rate that is on the order of a hundred and twenty genuine "
                      + "corrections to find among several thousand warnings - and classical "
                      + "verse has a higher rate of contextually-wrong principals than modern "
                      + "prose, on the evidence of 施氏食狮史 and the 文言 pieces."),

                new Phase("cp3", "Graded primary material",
                        "Enough everyday reading to fill a year.",
                        "The open-bucket collections, resolved into whatever c3 settles, and "
                      + "then grown deliberately rather than by accretion.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("Settle c3 - rename before any id is published", false),
                                new Task("儿歌 - toward thirty", false),
                                new Task("生活记叙 - school and home", false),
                                new Task("科普说明 - animals and natural phenomena", false),
                                new Task("寓言 and 成语 - toward the usual canon", false)),
                        List.of(),
                        "A reader has something new every day for a term without repeating.",
                        "",
                        "L",
                        "Original writing, so the cost is authorship rather than transcription "
                      + "- but the reading review is cheaper, because we choose the words."),

                new Phase("cp4", "Illustrations",
                        "Pictures, provided by the collection that uses them.",
                        "ImageRef exists and is tested; nothing populates it yet. A collection "
                      + "that ships images is the first exercise of the asset half of the "
                      + "library.",
                        PhaseStatus.NOT_STARTED,
                        List.of(
                                new Task("A collection that ships at least one illustration", false),
                                new Task("Serving - the generic content path, not a bespoke action", false),
                                new Task("Alt text reviewed as content, not filled in mechanically", false),
                                new Task("Licensing recorded per image", false)),
                        List.of(),
                        "An article renders with an illustration and the image is addressed as "
                      + "collection:local like everything else.",
                        "",
                        "M",
                        "Deliberately last. The model is in place, so this is not blocked - it "
                      + "is simply worth less than more text to read. The one external "
                      + "dependency is on the app side rather than on any phase here: "
                      + "illustrations should ride the generic content path, which is the same "
                      + "work that would retire ArticleGetAction.")
        );
    }

    @Override
    public List<Acceptance> acceptance() {
        return List.of(
                new Acceptance("Every collection is within the ceiling",
                        "No collection exceeds thirty articles, enforced by "
                      + "BundledArticlesTest.", true),
                new Acceptance("Every article parses",
                        "No article in any bundled collection produces a parse error.", true),
                new Acceptance("Readings are reviewed, not assumed",
                        "The unpinned-reading count is held still, so a new article forces "
                      + "someone to read its polyphonic characters in context.", true),
                new Acceptance("Provenance is stated",
                        "Every collection records where its text came from and on what "
                      + "footing it may be used.", false),
                new Acceptance("Enough to read daily",
                        "The library holds enough that a child reading one piece a day does "
                      + "not exhaust it within a term.", false)
        );
    }
}
