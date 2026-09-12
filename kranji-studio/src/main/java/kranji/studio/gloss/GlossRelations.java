package kranji.studio.gloss;

import kranji.reading.workbench.relation.Relation.Row;
import kranji.simple.gloss.EgRef;
import kranji.simple.gloss.ExampleEntry;
import kranji.simple.gloss.ExampleSense;
import kranji.simple.gloss.Meaning;
import kranji.simple.gloss.Priority;
import kranji.simple.gloss.Sense;
import kranji.simple.gloss.SoundGloss;
import kranji.simple.gloss.ZiGloss;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * The aggregates, flattened into the relations a grid can show.
 *
 * <p>A {@link ZiGloss} is a tree — a character holding readings holding
 * meanings holding example references. A grid is a table. This is the whole of
 * the translation, kept in one place and kept pure so it can be tested without
 * a browser.</p>
 *
 * <p>The relations are exactly those in the Gloss Data Model doc, and each row
 * carries its <b>full composite key</b> rather than a row number. That is what
 * lets a grid stay honest about identity: the key a row shows is the key the
 * repository would be asked for, so selecting a row in one grid can address the
 * same record in another without a lookup table in between.</p>
 *
 * <p>Readings are carried in the canonical form. Converting for display is the
 * boundary's job, not this one's.</p>
 */
public final class GlossRelations {

    private GlossRelations() {}

    /** One character that carries a gloss. */
    public record ZiRow(int codePoint, String glyph, int readings, int senses) {}

    /** One (character, reading) pair — the grain of the model. */
    public record SoundRow(int codePoint, String glyph, String reading,
                           String pairKey, int senses, String primary) {}

    /**
     * One meaning of one reading, with the phrases that show it written out.
     *
     * <p>{@code examples} is the phrases themselves rather than a count of
     * them. A count answers "is anything here", which the presence of text
     * answers just as well, and then leaves you opening another grid to learn
     * what it was.</p>
     *
     * <p>{@code refs} carries the same phrases as keys, for the grid that shows
     * what they mean. Shown and referenced are separate on purpose: one is text
     * a person reads, the other is identity, and letting a display string be
     * taken apart to recover keys is how the two drift.</p>
     */
    public record SenseRow(int codePoint, String glyph, String reading, String meaning,
                           Priority priority, String because, String examples, List<String> refs) {

        public SenseRow {
            refs = List.copyOf(refs);
        }

        /** How many phrases show this sense. */
        public int count() { return refs.size(); }
    }

    /** One phrase in the registry. */
    public record PhraseRow(int length, String phrase, int senses, String primary) {}

    /** One sense of one phrase, with any readings it pins. */
    public record PhraseSenseRow(String phrase, int senseIndex, String english,
                                 int order, String sounds) {}


    // ── The relations as a navigable chain ─────────────────────────────
    //
    // A row is Relation.Row - two keys, its own and its parent's - and the
    // scoping helpers are Relation.under and Relation.withPks. Both were
    // declared here until the library bench needed the same shape for a
    // second family of relations and could not reach the studio; the record
    // and the helpers moved to where both can see them, and this file kept
    // what is about the GLOSS relations: which ones there are, how they
    // chain, and what their rows hold.

    /**
     * The relation a row's {@link Row#refs} name, when it names any.
     *
     * <p>Distinct from {@link #upstreamOf}, and the distinction is the point.
     * Upstream is <b>containment</b> — a sense belongs to a sound and has no
     * life without it. A ref is a <b>reference</b> across the model to
     * something with an identity of its own: a sense points at the phrase
     * senses that show it, and several senses may point at the same one.</p>
     *
     * <p>Refs are plural because a sense cites as many phrases as it likes.
     * That is also why there is no longer a relation between the two: a join
     * table earns its place when the association carries attributes of its own,
     * and this one carried only an ordering that the sense already keeps.</p>
     */
    public static String refTargetOf(String relation) {
        return "sense".equals(relation) ? "phraseSense" : null;
    }

    /** The inverse: which relation's refs land on this one. */
    public static String refSourceOf(String relation) {
        return "phraseSense".equals(relation) ? "sense" : null;
    }

    /**
     * Follow the refs of some rows to the keys they name.
     *
     * <p>Resolved here rather than by taking a key apart in the browser: how
     * one relation's key is composed must never become a parsing contract for
     * another, or the first key format that changes breaks it silently.</p>
     */
    public static List<String> refsFrom(String fromRelation, List<String> fromPks,
                                        List<ZiGloss> glosses, List<ExampleEntry> phrases) {
        var out = new ArrayList<String>();
        for (Row row : rowsOf(fromRelation, glosses, phrases)) {
            if (!fromPks.contains(row.pk())) continue;
            for (String ref : row.refs()) if (!out.contains(ref)) out.add(ref);
        }
        return List.copyOf(out);
    }


    /** Which relation feeds which. Roots answer null. */
    public static String upstreamOf(String relation) {
        return switch (relation) {
            // Demand used to be a root. It reads better as one step down: the
            // sound relation is a filter over it, and a filter nobody has
            // touched leaves the relation whole - which is the "never selected"
            // case the bus already distinguishes from "selected nothing".
            case "demand"      -> "sound";
            case "sense"       -> "demand";
            case "phraseSense" -> "phrase";
            // Problem is demand's second child, and the first place the chain
            // branches. It scopes on the CHARACTER of the selected pair, not
            // the pair - see scopeKeysFor.
            case "problem"     -> "demand";

            // A second chain, for the curated workbench, and it is short: pick
            // a partition, see its issues and its written rows side by side.
            //
            // "issue" holds the same rows as "problem" and hangs off something
            // else, which is the whole reason both exist. The gloss workbench
            // asks "what is wrong with THIS character", reached by walking
            // sounds and pairs; the curated workbench asks "what is left in
            // THIS partition", reached by picking a file. One relation cannot
            // have two parents, and neither question is the other one.
            case "issue"       -> "partition";
            case "curated"     -> "partition";

            // Impact is the third view of a partition, and the one that
            // answers which partition to open. It hangs off partition like the
            // other two rather than becoming a fourth root, because unscoped
            // it already shows everything ranked - the bus tells a widget
            // "never selected" apart from "selected nothing", so browsing the
            // whole worklist by weight and narrowing it to one file are the
            // same widget in two states.
            case "impact"       -> "partition";
            // And the breakdown hangs off a weighed problem: one row per
            // article that reads it. A number is not actionable until you can
            // see which articles it is made of.
            case "impactArticle" -> "impact";
            default            -> null;      // sound, phrase, partition
        };
    }

    /** The relations, in the order a picker should offer them. */
    public static List<String> relations() {
        return List.of("sound", "demand", "sense", "phrase", "phraseSense", "problem",
                       "partition", "issue", "curated", "impact", "impactArticle");
    }

    public static List<String> columnsOf(String relation) {
        return switch (relation) {
            case "sound"       -> List.of("reading", "initial", "final", "tone",
                                          "characters", "todo");
            case "demand"      -> List.of("glyph", "reading", "meaning", "status");
            case "sense"       -> List.of("glyph", "reading", "priority", "meaning", "examples");
            case "phrase"      -> List.of("phrase", "characters", "senses", "primary meaning");
            case "phraseSense" -> List.of("phrase", "sense", "english", "pinned readings");
            case "problem"     -> List.of("glyph", "reading", "part", "kind", "doubts",
                                          "state", "verdict", "kept", "source");
            case "partition"   -> List.of("partition", "curated", "seeded", "issues", "open",
                                          "source");
            // Same columns as "problem", minus the one that is now the parent.
            case "issue"       -> List.of("glyph", "reading", "kind", "doubts",
                                          "state", "verdict", "kept", "source");
            case "curated"     -> List.of("glyph", "reading", "priority", "meaning", "examples");
            // blind before read, because that is the sort order and a reader of
            // the grid should meet the number it is ranked by first.
            case "impact"      -> List.of("glyph", "reading", "kind", "state",
                                          "blind", "read", "articles", "part", "groups");
            case "impactArticle" -> List.of("article", "group", "reading", "times", "status");
            default            -> List.of();
        };
    }

    /**
     * Every row of a relation, each carrying its own key and its parent's.
     *
     * <p>Built from the typed rows above rather than beside them, so there is
     * one definition of what a relation contains and the grid cannot drift
     * from what the tests check.</p>
     */
    public static List<Row> rowsOf(String relation,
                                   List<ZiGloss> glosses, List<ExampleEntry> phrases) {
        return switch (relation) {
            // The readings, one row each. A root: nothing scopes a filter.
            case "sound" -> GlossSounds.rows(glosses).stream().map(r -> new Row(
                    r.reading(), "", r.reading(),
                    List.of(r.reading(), r.initial(), r.rhyme(), r.tone(),
                            r.characters(), r.todo()))).toList();

            // The demand relation is the one row set not derived from the
            // aggregates - it comes from the articles, and its point is the
            // rows the aggregates do NOT have.
            //
            // Its parent is the reading alone, not the pair: that is what the
            // sound relation is keyed on, and scoping is a key match.
            case "demand" -> GlossDemand.rows(glosses).stream().map(r -> new Row(
                    r.codePoint() + ":" + r.reading(), r.reading(), r.glyph(),
                    List.of(r.glyph(), r.reading(), r.meaning(), r.status()))).toList();


            case "sense" -> senses(glosses).stream().map(r -> new Row(
                    sensePk(r.codePoint(), r.reading(), r.meaning()),
                    r.codePoint() + ":" + r.reading(), r.meaning(),
                    List.of(r.glyph(), r.reading(), r.priority().code(), r.meaning(), r.examples()),
                    r.refs()))
                    .toList();


            case "phrase" -> GlossRelations.phrases(phrases).stream().map(r -> new Row(
                    r.phrase(), "", r.phrase(),
                    List.of(r.phrase(), r.length(), r.senses(), r.primary()))).toList();

            case "phraseSense" -> phraseSenses(phrases).stream().map(r -> new Row(
                    r.phrase() + "#" + r.senseIndex(), r.phrase(), r.english(),
                    List.of(r.phrase(), r.senseIndex(), r.english(), r.sounds()))).toList();

            // The one relation that is not a projection of the aggregates. It
            // reads the seeder's own output and the verdicts beside it, so it
            // takes no argument from here - the others describe what the model
            // holds, and this one describes what nobody has settled yet.
            //
            // "up" is the CHARACTER, not the pair. Every other relation's up is
            // its parent's whole key; this one deliberately holds less, because
            // a problem is a character's problem. scopeKeysFor is the other
            // half of that.
            // Everything, ordered by sound. Two decisions, both about the
            // unscoped view, which is the one a person browses.
            //
            // Everything, because the state column already says which rows are
            // finished and which nobody has touched - and a grid that silently
            // held 1,365 rows while reporting 1,299 is the kind of honest-
            // looking lie this file avoids elsewhere. Type "open" in the filter
            // to get the worklist back.
            //
            // By sound, because partition order is the GENERATOR's: modulo 101
            // scatters homophones across every file. A reviewer reads bai2
            // after bai2, since the judgement they are making about one is
            // usually the judgement they just made about the last.
            case "problem" -> SeedProblems.rows().stream()
                    .sorted(Comparator.comparing(SeedProblems.Row::reading)
                            .thenComparingInt(SeedProblems.Row::codePoint))
                    .map(r -> new Row(
                            r.pairKey(), String.valueOf(r.codePoint()), r.glyph(),
                            List.of(r.glyph(), r.reading(),
                                    "p" + String.format("%03d", r.partition()),
                                    r.kind(), r.doubts(),
                                    r.state().name().toLowerCase(java.util.Locale.ROOT),
                                    r.verdict(), r.kept(), r.detail())))
                    .toList();

            // ── The curated chain ──────────────────────────────────────

            // Read fresh, so the counts and the path are what is on disk right
            // now rather than what was on the classpath when the JVM started.
            case "partition" -> Partitions.rows(CuratedSource.readAll()).stream()
                    .map(r -> new Row(
                            r.label(), "", r.label(),
                            List.of(r.label(), r.curated(), r.seeded(), r.issues(),
                                    r.open(), r.source())))
                    .toList();

            // The same rows "problem" serves, hung off a partition instead of
            // a character, and ordered by sound within it for the same reason.
            case "issue" -> SeedProblems.rows().stream()
                    .sorted(Comparator.comparing(SeedProblems.Row::reading)
                            .thenComparingInt(SeedProblems.Row::codePoint))
                    .map(r -> new Row(
                            r.pairKey(), "p%03d".formatted(r.partition()), r.glyph(),
                            List.of(r.glyph(), r.reading(), r.kind(), r.doubts(),
                                    r.state().name().toLowerCase(java.util.Locale.ROOT),
                                    r.verdict(), r.kept(), r.detail())))
                    .toList();

            // What is written in that partition's file, read out of the model
            // rather than the file - the model round-trips through GlossTsv,
            // so it IS the file, and re-parsing would be a second reader to
            // keep in step with the first.
            case "curated" -> senses(CuratedSource.readAll().stream()
                    .flatMap(p -> p.glosses().stream()).toList())
                    .stream().map(r -> new Row(
                    sensePk(r.codePoint(), r.reading(), r.meaning()),
                    "p%03d".formatted(kranji.zi.ZiPartition.of(r.codePoint())),
                    r.meaning(),
                    // No refs. The gloss workbench walks a sense sideways to the
                    // phrases that show it; this bench has no phrase widget, and
                    // refTargetOf says "curated" points at nothing - so rows
                    // carrying refs would advertise a link the module denies.
                    List.of(r.glyph(), r.reading(), r.priority().code(),
                            r.meaning(), r.examples())))
                    .toList();

            // The same problems again, weighed against what the library reads.
            // Keyed like "issue" - on the pair - so a row selected in one grid
            // addresses the same problem in the other.
            //
            // Everything, including the 701 nothing reads, for the reason the
            // problem relation shows everything: a grid that quietly held back
            // three quarters of the queue while reporting a total would be the
            // honest-looking lie this file avoids. They sort to the bottom on
            // their own, since their weight is zero.
            case "impact" -> GlossImpact.rows(glosses).stream().map(r -> new Row(
                    r.pairKey(), "p%03d".formatted(r.partition()), r.glyph(),
                    List.of(r.glyph(), r.reading(), r.kind(), r.state(),
                            r.blind(), r.read(), r.articles(),
                            "p%03d".formatted(r.partition()), r.groups())))
                    .toList();

            // One row per article that reads the problem's character. The pk
            // carries the reading as well as the address: a polyphone met at
            // two readings in one article is two facts, and one of them can be
            // answered while the other is not.
            case "impactArticle" -> GlossImpact.places(glosses).stream().map(r -> new Row(
                    r.problem() + "@" + r.address() + "#" + r.reading(), r.problem(),
                    r.article(),
                    List.of(r.article(), r.group(), r.reading(), r.times(), r.status())))
                    .toList();

            default -> List.of();
        };
    }

    /**
     * The keys a relation actually scopes on, given what was selected above it.
     *
     * <p>Identity for every relation but one, and the exception is the point of
     * the method existing. {@code problem} hangs off {@code demand}, whose key
     * is a (character, reading) pair, but a problem belongs to a
     * <b>character</b>: 地's queued split is one job whether you arrived at it
     * through {@code de0} or {@code di4}, and scoping on the pair would show
     * half a job and hide the reason it exists.</p>
     *
     * <p>Narrowed here rather than in the widget, so which keys a grid asks for
     * stays a decision testable in Java rather than one trapped in the
     * browser.</p>
     */
    public static List<String> scopeKeysFor(String relation, List<String> keys) {
        if (!"problem".equals(relation)) return keys;
        // A set: two readings of one character are one selection here, and
        // leaving the duplicate in would make an "N selected" count wrong.
        var out = new LinkedHashSet<String>();
        for (String key : keys) {
            int colon = key.indexOf(':');
            out.add(colon < 0 ? key : key.substring(0, colon));
        }
        return List.copyOf(out);
    }

    private static String sensePk(int codePoint, String reading, String meaning) {
        return codePoint + ":" + reading + "/" + meaning;
    }

    /**
     * The phrases showing a sense, in authored order, as one cell.
     *
     * <p>Separated by {@code |}, the same mark the source files list with. One
     * rule across the whole tier: a bar lists, a semicolon or comma joins
     * within one item. It also cannot be mistaken for content - an
     * {@link EgKey} is all-Han by construction, so no phrase can contain one.</p>
     *
     * <p>A phrase's sense index is shown <b>only when it is not the first</b> —
     * {@code 东西#1} is "east and west" rather than "a thing", and that
     * distinction is invisible if the index is dropped. Writing {@code #0} on
     * everything else would bury the one case that matters, the same way a
     * reading stated on every character would bury the ones worth stating.</p>
     */
    /**
     * The phrase-sense keys a sense points at, in the same order it shows them.
     *
     * <p>Derived from the same {@code orderedExamples()} as {@link #shows}, so
     * the cell a person reads and the keys a grid resolves cannot disagree
     * about which phrases are cited — only about how they are written.</p>
     */
    private static List<String> refsOf(Sense sense) {
        var out = new ArrayList<String>();
        for (EgRef ref : sense.orderedExamples()) {
            out.add(ref.phrase().phrase() + "#" + ref.sense());
        }
        return List.copyOf(out);
    }

    private static String shows(Sense sense) {
        var parts = new ArrayList<String>();
        for (EgRef ref : sense.orderedExamples()) {
            parts.add(ref.sense() == 0 ? ref.phrase().phrase()
                                       : ref.phrase().phrase() + "#" + ref.sense());
        }
        return String.join("|", parts);
    }

    // ── The character side ─────────────────────────────────────────────

    public static List<ZiRow> zi(List<ZiGloss> glosses) {
        var rows = new ArrayList<ZiRow>();
        for (ZiGloss g : glosses) {
            int senses = 0;
            for (SoundGloss s : g.sounds()) senses += s.senses().size();
            rows.add(new ZiRow(g.zi().codePoint(), g.zi().value(), g.sounds().size(), senses));
        }
        return List.copyOf(rows);
    }

    public static List<SoundRow> sounds(List<ZiGloss> glosses) {
        var rows = new ArrayList<SoundRow>();
        for (ZiGloss g : glosses) {
            for (SoundGloss s : g.sounds()) {
                rows.add(new SoundRow(
                        g.zi().codePoint(), g.zi().value(), s.reading().numbered(),
                        s.key(), s.senses().size(), s.primary().text()));
            }
        }
        return List.copyOf(rows);
    }

    public static List<SenseRow> senses(List<ZiGloss> glosses) {
        var rows = new ArrayList<SenseRow>();
        for (ZiGloss g : glosses) {
            for (SoundGloss s : g.sounds()) {
                for (Meaning m : s.orderedMeanings()) {
                    Sense sense = s.senseOf(m);
                    rows.add(new SenseRow(
                            g.zi().codePoint(), g.zi().value(), s.reading().numbered(),
                            m.text(), sense.ranking().priority(),
                            sense.ranking().because().orElse(""),
                            shows(sense), refsOf(sense)));
                }
            }
        }
        return List.copyOf(rows);
    }

    // ── The phrase side ────────────────────────────────────────────────

    public static List<PhraseRow> phrases(List<ExampleEntry> entries) {
        var rows = new ArrayList<PhraseRow>();
        for (ExampleEntry e : entries) {
            rows.add(new PhraseRow(e.key().length(), e.key().phrase(),
                    e.senses().size(), e.primary().english().text()));
        }
        return List.copyOf(rows);
    }

    public static List<PhraseSenseRow> phraseSenses(List<ExampleEntry> entries) {
        var rows = new ArrayList<PhraseSenseRow>();
        for (ExampleEntry e : entries) {
            List<ExampleSense> senses = e.senses();
            for (int i = 0; i < senses.size(); i++) {
                ExampleSense s = senses.get(i);
                rows.add(new PhraseSenseRow(e.key().phrase(), i, s.english().text(),
                        s.ranking().order(), pinned(e, s)));
            }
        }
        return List.copyOf(rows);
    }

    /**
     * The readings this sense pins, as {@code 地=di4}.
     *
     * <p>Empty for most senses, and that is the point: an entry exists only
     * where the reading is not the corpus principal, so what shows here is
     * exactly what a reader would otherwise get wrong.</p>
     */
    private static String pinned(ExampleEntry entry, ExampleSense sense) {
        var parts = new ArrayList<String>();
        for (int at = 0; at < entry.key().length(); at++) {
            int position = at;
            sense.soundAt(at).ifPresent(syllable -> parts.add(
                    entry.key().characters().get(position).value() + "=" + syllable.numbered()));
        }
        return String.join("  ", parts);
    }

}
