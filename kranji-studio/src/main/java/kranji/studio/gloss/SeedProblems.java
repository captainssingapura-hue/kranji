package kranji.studio.gloss;

import kranji.phonic.SyllableIndex;
import kranji.pinyin.PinyinSyllable;
import kranji.simple.gloss.SoundGloss;
import kranji.simple.gloss.ZiCollection;
import kranji.simple.gloss.ZiCollections;
import kranji.simple.gloss.ZiGloss;
import kranji.zi.ZiCharUTF8;
import kranji.zi.ZiPartition;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Every seeded character the generator could not settle, with what a person has
 * since decided about it.
 *
 * <h2>Read from the generated files, not from a copy</h2>
 *
 * <p>The rows come from {@code pNNN.flags.tsv} on the classpath — the seeder's
 * own output — joined to the senses it did write in {@code pNNN.tsv}. There is
 * no snapshot in between. A second copy of generated data is a thing that goes
 * quietly stale the first time somebody regenerates and forgets, and the whole
 * point of this widget is to be trusted about what is outstanding.</p>
 *
 * <h2>Five states, and two of them are about drift</h2>
 *
 * <p>A problem is {@code open} until somebody records a verdict, and
 * {@code done} once they have. {@code stale} is a verdict that exists while
 * the generator now raises a <em>different</em> problem for that pair — what
 * was signed off is not what is there. {@code cleared} is a verdict whose
 * problem has gone away entirely, which has no row of its own in the generated
 * data and would otherwise be invisible; those are listed so a decision can be
 * retired rather than left pointing at nothing.</p>
 *
 * <p>{@code answered} is the odd one out: not a decision at all, but the
 * hand-crafted set already covering the character at every reading — 66 of the
 * 1,365, raised by a seeder that cannot see the other collection.</p>
 *
 * <p>The grid shows all five. {@link #outstanding()} is the count of real work
 * and the thing to measure progress against, but a view that quietly held
 * 1,365 rows while reporting 1,299 would be the sort of honest-looking lie a
 * reviewer only catches by accident. The state is a column instead, and typing
 * a state into the filter is how you get one bucket.</p>
 *
 * <h2>Scoped by character, not by pair</h2>
 *
 * <p>Every row's {@code up} is the codepoint alone, where every other relation
 * carries its parent's whole key. That is deliberate: a problem belongs to a
 * <b>Zi</b>. 欸 is queued because its five readings need splitting by hand, and
 * that is one job — filed under one of the five, and the wrong answer to look
 * for under the other four.</p>
 *
 * <p>Nothing hangs off this in turn. The chain is a way to walk the data; a
 * worklist that also drove three other grids would be two jobs in one
 * widget.</p>
 */
public final class SeedProblems {

    private static final String FLAGS = "/kranji/seed/p%03d.flags.tsv";
    private static final String SENSES = "/kranji/seed/p%03d.tsv";

    private SeedProblems() {}

    /** The state of one problem, once a verdict is taken into account. */
    public enum State {
        /** Nobody has looked at it. */
        OPEN,
        /** Reviewed, against the problem that is still there. */
        DONE,
        /** Reviewed, but the problem changed underneath the verdict. */
        STALE,
        /** The verdict's problem is gone; the decision can be retired. */
        CLEARED,
        /**
         * Not a decision anybody made — a fact about two collections.
         *
         * <p>The hand-crafted set already glosses this character at every
         * reading the corpus lists, so {@code Glosses.of} never shows the
         * seeded row and the seeder's doubt is about text no reader reaches.
         * The 20 queued polyphones here are ones somebody had already split;
         * the seeder queues them because it cannot see the other collection.</p>
         *
         * <p><b>Computed, never stamped.</b> These could have been 66 verdicts
         * and should not be: a verdict is a decision about a row, and the day
         * a hand-crafted row is deleted those 66 would quietly outlive their
         * subject — the exact failure the verdicts file exists to prevent.
         * Recomputing costs nothing and self-corrects when either side
         * moves.</p>
         */
        ANSWERED
    }

    /**
     * One row of the worklist.
     *
     * @param kept   the senses the seeder did write, priority-tagged, or empty
     * @param detail the raw source field, or the reading list for a polyphone
     */
    public record Row(int partition, int codePoint, String glyph, String reading,
                      String kind, String doubts, State state, String verdict,
                      String kept, String detail) {

        public String pairKey() { return codePoint + ":" + reading; }
    }

    /**
     * Every pair a person has glossed, from the collections at precedence 0.
     *
     * <p>Declared BEFORE {@link #ROWS}, and that is load-bearing rather than
     * tidy: static initialisers run in textual order, and {@code load()} asks
     * this the moment it classifies its first row. Below it, the field would
     * still be null and every problem would read as unanswered.</p>
     */
    private static final Set<String> HAND_AUTHORED = handAuthored();

    private static Set<String> handAuthored() {
        // Through layers(), not straight at the discovered collection. What is
        // on the classpath now is one LAYERED collection whose precedence is 0
        // because its top layer is hand-authored - reading that number without
        // descending would call all 7,146 seeded rows hand-written and empty
        // this queue in a single stroke.
        //
        // Still selected by precedence rather than by naming HandCrafted: 0 is
        // exactly the property being asked about - somebody decided this row.
        var out = new LinkedHashSet<String>();
        for (ZiCollection c : ZiCollections.discovered()) {
            for (ZiCollection layer : c.layers()) {
                if (layer.precedence() != 0) continue;
                for (ZiGloss g : layer.characters().all()) {
                    for (SoundGloss s : g.sounds()) out.add(s.key());
                }
            }
        }
        return out;
    }

    /**
     * Whether a person has glossed this character at <b>every</b> reading the
     * corpus lists.
     *
     * <p>Every reading, not a count of them. Two collections can hold the same
     * NUMBER of readings for a character and disagree about which, and a count
     * would call that covered — 得 has three, and being right about three is
     * not the same as being right about dé, de and děi.</p>
     */
    private static boolean answeredByHand(int codePoint) {
        var readings = SyllableIndex.instance().readingsOf(new ZiCharUTF8(codePoint));
        if (readings.isEmpty()) return false;
        for (PinyinSyllable syllable : readings.get().all()) {
            if (!HAND_AUTHORED.contains(codePoint + ":" + syllable.numbered())) return false;
        }
        return true;
    }

    private static final List<Row> ROWS = load();

    private static List<Row> load() {
        var out = new ArrayList<Row>();
        var seen = new LinkedHashSet<String>();

        for (int p = 0; p < ZiPartition.COUNT; p++) {
            String flags = read(FLAGS.formatted(p));
            if (flags == null) continue;             // not seeded yet; normal
            Map<Integer, String> kept = keptSenses(read(SENSES.formatted(p)));

            for (String line : flags.split("\n")) {
                String row = line.strip();
                if (row.isEmpty() || row.charAt(0) == '#') continue;
                String[] f = line.split("\t", -1);
                if (f.length < 6) continue;

                int codePoint = Integer.parseInt(f[0].strip());
                String reading = f[2].strip();
                String kind = f[3].strip();
                String doubts = f[4].strip();

                // A verdict wins over ANSWERED. Both can be true at once - 氏
                // was split by hand AND has a FIXED verdict - and of the two
                // the verdict is the one somebody wrote down.
                int cp = codePoint;
                var verdict = SeedVerdicts.find(codePoint, reading);
                State state = verdict.map(v -> v.covers(kind, doubts)
                                ? State.DONE : State.STALE)
                        .orElseGet(() -> answeredByHand(cp) ? State.ANSWERED : State.OPEN);

                seen.add(codePoint + ":" + reading);
                out.add(new Row(p, codePoint, f[1].strip(), reading, kind, doubts,
                        state, verdict.map(v -> v.verdict().name()).orElse(""),
                        kept.getOrDefault(codePoint, ""), f[5].strip()));
            }
        }

        // A verdict whose problem is gone. Listed rather than dropped: it is
        // either a fix that landed, in which case retiring the row is the last
        // step, or a problem that moved, in which case somebody needs to know
        // their decision no longer applies to anything.
        for (SeedVerdicts.Entry entry : SeedVerdicts.all().values()) {
            if (seen.contains(entry.pairKey())) continue;
            out.add(new Row(ZiPartition.of(entry.codePoint()), entry.codePoint(),
                    new String(Character.toChars(entry.codePoint())), entry.reading(),
                    entry.kind(), entry.doubts(), State.CLEARED,
                    entry.verdict().name(), "", entry.note()));
        }
        return List.copyOf(out);
    }

    /** {@code codepoint -> "P meaning / S meaning"}, for the rows that were seeded. */
    private static Map<Integer, String> keptSenses(String senses) {
        var out = new LinkedHashMap<Integer, String>();
        if (senses == null) return out;
        for (String line : senses.split("\n")) {
            if (line.isEmpty() || line.charAt(0) == '#') continue;
            String[] f = line.split("\t", -1);
            if (f.length < 5) continue;
            int codePoint = Integer.parseInt(f[0].strip());
            String sense = f[3].strip() + " " + f[4].strip();
            out.merge(codePoint, sense, (a, b) -> a + " / " + b);
        }
        return out;
    }

    private static String read(String resource) {
        try (InputStream in = SeedProblems.class.getResourceAsStream(resource)) {
            if (in == null) return null;
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("could not read " + resource, e);
        }
    }

    /** Every problem the seeder raised, in partition order — answered or not. */
    public static List<Row> rows() { return ROWS; }

    /**
     * The worklist: everything except what the hand-crafted set already
     * answers.
     *
     * <p>Filtered here rather than in the grid, so the count a reviewer reads
     * is the count of what is left. A grid that held all of it and hid most
     * would report a number nobody can act on.</p>
     */
    public static List<Row> outstanding() {
        return ROWS.stream().filter(r -> r.state() != State.ANSWERED).toList();
    }

    /** The ones a person had already finished before the seeder ever ran. */
    public static List<Row> answered() {
        return ROWS.stream().filter(r -> r.state() == State.ANSWERED).toList();
    }

    /** How many are still nobody's decision. */
    public static long open() {
        return ROWS.stream().filter(r -> r.state() == State.OPEN).count();
    }

    /** How many rows are in a state that needs a person's attention again. */
    public static Set<State> statesPresent() {
        var out = new LinkedHashSet<State>();
        for (Row r : ROWS) out.add(r.state());
        return out;
    }
}
