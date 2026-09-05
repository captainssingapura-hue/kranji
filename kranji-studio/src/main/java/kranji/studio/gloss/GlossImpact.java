package kranji.studio.gloss;

import kranji.simple.gloss.SoundGloss;
import kranji.simple.gloss.ZiGloss;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * What each unsettled problem costs a reader of the mounted library.
 *
 * <h2>Why a count of problems is the wrong worklist</h2>
 *
 * <p>929 problems are open and they are not equally worth an hour. Ordered by
 * partition they arrive in the generator's order, which is modulo 101 and means
 * nothing; ordered by sound they arrive in a reviewer's order, which is better
 * but still says nothing about whether anybody reads them. Against the library
 * actually mounted, three quarters of the queue turns out never to be read at
 * all.</p>
 *
 * <h2>Read and blind are different questions</h2>
 *
 * <p>The obvious measure — how often the library reads this character — is a
 * trap, and it ranks 的 first with 366 uses. 的 is glossed: somebody wrote it by
 * hand, the reader shows it, and settling its seed row changes nothing anybody
 * sees. What matters is the part of that demand <b>no collection can answer</b>,
 * which is what {@link #blind} counts.</p>
 *
 * <p>So every row carries both. {@code read} is the character's weight in the
 * library and says whether the work is worth doing at all; {@code blind} is the
 * part of that weight a reader currently meets with nothing, and is the number
 * to sort by. A row that is read often and blind never is finished work that
 * has not been ticked off.</p>
 *
 * <p>FLAGGED rows are the case where the two come apart in the other direction.
 * A flagged row <em>has</em> a meaning — the doubt is whether it is the right
 * one — so its blind count is 0 and its read count is the number of times a
 * reader is shown something nobody has checked. Zero blind there means "not
 * missing", never "not worth looking at".</p>
 *
 * <p>Computed from the same {@link GlossDemand} walk the demand relation uses,
 * and from the collections passed in rather than from a named one, so what
 * counts as answered is whatever is on the classpath.</p>
 */
public final class GlossImpact {

    private GlossImpact() {}

    /**
     * One problem, weighed.
     *
     * @param blind    reading-events of this character no collection explains
     * @param read     reading-events of this character in the whole library
     * @param articles how many articles read it
     * @param groups   the library's top-level branches it appears under
     */
    public record Row(int partition, int codePoint, String glyph, String reading,
                      String kind, String state, int blind, int read,
                      int articles, String groups) {

        public String pairKey() { return codePoint + ":" + reading; }
    }

    /**
     * One place one problem is met: an article, and the reading it uses there.
     *
     * <p>Per (article, reading) rather than per article, because a polyphone is
     * the usual subject here and 数 shǔ in one article and 数 shù in another are
     * not the same gap. The status column says which of them a reader can
     * currently answer.</p>
     */
    public record Place(String problem, String address, String article, String group,
                        String reading, int times, String status) {}

    /** Every problem, heaviest first. */
    public static List<Row> rows(List<ZiGloss> glosses) {
        Map<String, String> byPair = byPair(glosses);
        Map<Integer, List<GlossDemand.Where>> byCharacter = byCharacter();

        var out = new ArrayList<Row>();
        for (SeedProblems.Row problem : SeedProblems.rows()) {
            int blind = 0, read = 0;
            var articles = new LinkedHashSet<String>();
            var groups = new LinkedHashSet<String>();
            for (GlossDemand.Where where : places(byCharacter, problem.codePoint())) {
                read += where.times();
                articles.add(where.address());
                groups.add(where.group());
                if (!byPair.containsKey(where.pairKey())) blind += where.times();
            }
            out.add(new Row(problem.partition(), problem.codePoint(), problem.glyph(),
                    problem.reading(), problem.kind(),
                    problem.state().name().toLowerCase(java.util.Locale.ROOT),
                    blind, read, articles.size(), String.join(" ", groups)));
        }
        out.sort(Comparator.comparingInt(Row::blind).reversed()
                           .thenComparing(Comparator.comparingInt(Row::read).reversed())
                           .thenComparing(Row::reading)
                           .thenComparingInt(Row::codePoint));
        return List.copyOf(out);
    }

    /** Where each problem is met, heaviest article first within each. */
    public static List<Place> places(List<ZiGloss> glosses) {
        Map<String, String> byPair = byPair(glosses);
        Map<Integer, List<GlossDemand.Where>> byCharacter = byCharacter();

        var out = new ArrayList<Place>();
        for (Row row : rows(glosses)) {
            for (GlossDemand.Where where : places(byCharacter, row.codePoint())) {
                out.add(new Place(row.pairKey(), where.address(), where.article(),
                        where.group(), where.reading(), where.times(),
                        byPair.containsKey(where.pairKey()) ? "covered" : "blind"));
            }
        }
        return List.copyOf(out);
    }

    /**
     * Every place a character is read, at any reading.
     *
     * <p>The character, not the pair the problem is filed under. A queued
     * polyphone is one job covering all its readings — 数 is not half-settled
     * by writing shù — so its cost has to be the whole character's.</p>
     */
    private static List<GlossDemand.Where> places(
            Map<Integer, List<GlossDemand.Where>> byCharacter, int codePoint) {
        return byCharacter.getOrDefault(codePoint, List.of());
    }

    /** The demand re-keyed on the character, once per call rather than per row. */
    private static Map<Integer, List<GlossDemand.Where>> byCharacter() {
        var out = new LinkedHashMap<Integer, List<GlossDemand.Where>>();
        for (var places : GlossDemand.demand().where().values()) {
            for (GlossDemand.Where where : places) {
                out.computeIfAbsent(where.codePoint(), k -> new ArrayList<>()).add(where);
            }
        }
        out.values().forEach(list -> list.sort(
                Comparator.comparingInt(GlossDemand.Where::times).reversed()
                          .thenComparing(GlossDemand.Where::article)));
        return out;
    }

    private static Map<String, String> byPair(List<ZiGloss> glosses) {
        var out = new LinkedHashMap<String, String>();
        for (ZiGloss g : glosses) {
            for (SoundGloss s : g.sounds()) out.put(s.key(), s.primary().text());
        }
        return out;
    }
}
