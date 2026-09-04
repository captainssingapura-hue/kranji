package kranji.studio.gloss;

import kranji.simple.gloss.EgRef;
import kranji.simple.gloss.ExampleEntry;
import kranji.simple.gloss.ExampleSense;
import kranji.simple.gloss.Meaning;
import kranji.simple.gloss.Priority;
import kranji.simple.gloss.Sense;
import kranji.simple.gloss.SoundGloss;
import kranji.simple.gloss.ZiGloss;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

    /**
     * One row, ready to be selected against.
     *
     * <p>Two keys, and the second is what makes a workbench out of six tables.
     * {@link #pk} is this row's own identity — the composite key written out,
     * never a position. {@link #up} is its <b>parent's</b> pk in the relation
     * upstream of it, which is the entire mechanism behind the cascade: a
     * downstream widget filters on {@code up ∈ selection} and needs to know
     * nothing else about what happened above it.</p>
     *
     * <p>A root relation has no parent and carries {@code ""}.</p>
     */
    public record Row(String pk, String up, String label, List<Object> values,
                      List<String> refs) {

        public Row {
            refs = List.copyOf(refs);
        }

        /** Most rows point nowhere sideways. */
        public Row(String pk, String up, String label, List<Object> values) {
            this(pk, up, label, values, List.of());
        }
    }

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

    /**
     * Rows whose own pk is one of these, <b>in the order asked for</b>.
     *
     * <p>Used when arriving by ref rather than by parent, and the order is the
     * point. A sense states its examples in the sequence its author chose, and
     * that sequence is its ranking — so the expansion of 东方|东边|山东|东西#1
     * must read in that order and not in whatever order the phrase registry
     * happens to hold them. Iterating the rows instead of the keys silently
     * reorders the answer to match a list the caller never mentioned.</p>
     */
    public static List<Row> withPks(List<Row> rows, List<String> pks) {
        var byPk = new LinkedHashMap<String, Row>();
        for (Row row : rows) byPk.put(row.pk(), row);

        var out = new ArrayList<Row>();
        for (String pk : pks) {
            Row row = byPk.get(pk);
            if (row != null) out.add(row);
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
            default            -> null;      // sound, phrase
        };
    }

    /** The relations, in the order a picker should offer them. */
    public static List<String> relations() {
        return List.of("sound", "demand", "sense", "phrase", "phraseSense");
    }

    public static List<String> columnsOf(String relation) {
        return switch (relation) {
            case "sound"       -> List.of("reading", "initial", "final", "tone",
                                          "characters", "todo");
            case "demand"      -> List.of("glyph", "reading", "meaning", "status");
            case "sense"       -> List.of("glyph", "reading", "priority", "meaning", "examples");
            case "phrase"      -> List.of("phrase", "characters", "senses", "primary meaning");
            case "phraseSense" -> List.of("phrase", "sense", "english", "pinned readings");
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

            default -> List.of();
        };
    }

    /** Rows whose parent is one of these keys. An empty selection shows nothing. */
    public static List<Row> under(List<Row> rows, List<String> parentPks) {
        var out = new ArrayList<Row>();
        for (Row row : rows) if (parentPks.contains(row.up())) out.add(row);
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
