package kranji.studio.gloss;

import kranji.reading.content.Articles;
import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.Libraries;
import kranji.reading.library.LibraryTree;
import kranji.reading.model.ArticleCensus;
import kranji.simple.gloss.SoundGloss;
import kranji.simple.gloss.ZiGloss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * What the bundled library asks for, beside what has been written.
 *
 * <h2>Why the workbench needs this</h2>
 *
 * <p>The gloss relations show what exists. For a set that is mostly unwritten
 * that is the less useful half — 28 rows tell you nothing about the 420 still
 * owed, and a tool that only shows finished work cannot be the place the work
 * happens.</p>
 *
 * <p>So this relation is the demand: one row per (character, reading) the
 * articles actually use, carrying its meaning when there is one and standing
 * empty when there is not. Filtering it for {@code todo} is the worklist.</p>
 *
 * <h2>Computed, never written down</h2>
 *
 * <p>From the same {@link ArticleCensus} the reader uses. A constant would be
 * wrong the first time an article was added and would go on looking right.</p>
 */
public final class GlossDemand {

    private GlossDemand() {}

    /** A demanded pair, and what the collections can say about it. */
    public record Row(int codePoint, String glyph, String reading,
                      String meaning, String status) {}

    /**
     * One place one pair is read, and how often there.
     *
     * @param group the top-level branch of the mounted library the article
     *              hangs under — the coarsest heading a reader would name it
     *              by, taken from the tree rather than from a list here, so a
     *              root that arranges itself differently attributes itself
     *              differently and this class never learns any titles
     */
    public record Where(String group, String address, String article,
                        int codePoint, String reading, int times) {

        public String pairKey() { return codePoint + ":" + reading; }
    }

    /**
     * What the library asks for, counted.
     *
     * @param uses  pair to how many times it is read, in walk order
     * @param where every place every pair is read, most-read article first
     */
    public record Demand(Map<String, Integer> uses, Map<String, List<Where>> where) {}

    /**
     * The demand, walked once and held.
     *
     * <p>Held, unlike {@link CuratedSource}, and for the opposite reason. That
     * reads files somebody is editing and must never cache; this reads a
     * library that arrives as jars and cannot change while the process runs.
     * Recomputing it would re-parse 475 articles on every grid request — the
     * cost was invisible at 23 and is not now.</p>
     */
    private static final class Held {
        static final Demand DEMAND = walk();
    }

    /** Every (codepoint, reading) the mounted library uses, in reading order. */
    public static Set<String> pairs() {
        return Held.DEMAND.uses().keySet();
    }

    /** The same, with counts and with where each one is read. */
    public static Demand demand() { return Held.DEMAND; }

    private static Demand walk() {
        var uses = new LinkedHashMap<String, Integer>();
        var where = new LinkedHashMap<String, List<Where>>();

        for (LibraryTree group : topLevel()) {
            for (ArticleCollection c : group.collections()) {
                for (ArticleRef ref : c.articles()) {
                    var address = c.address(ref.id());
                    var parsed = Articles.read(address, ref).flatMap(p -> p.article());
                    if (parsed.isEmpty()) continue;
                    for (var pair : ArticleCensus.of(parsed.get()).pairs().entrySet()) {
                        uses.merge(pair.getKey(), pair.getValue(), Integer::sum);
                        int colon = pair.getKey().indexOf(':');
                        where.computeIfAbsent(pair.getKey(), k -> new ArrayList<>())
                             .add(new Where(group.title(), address.toString(), ref.title(),
                                     Integer.parseInt(pair.getKey().substring(0, colon)),
                                     pair.getKey().substring(colon + 1), pair.getValue()));
                    }
                }
            }
        }
        where.values().forEach(list -> list.sort(
                Comparator.comparingInt(Where::times).reversed()
                          .thenComparing(Where::article)));
        return new Demand(Collections.unmodifiableMap(uses), Collections.unmodifiableMap(where));
    }

    /**
     * The branches an article is attributed to.
     *
     * <p>The root's own children, whatever they are. A library of one branch
     * attributes everything to it, which is right — there is nothing to
     * distinguish.</p>
     */
    private static List<LibraryTree> topLevel() {
        LibraryTree root = Libraries.mounted().tree();
        return root instanceof LibraryTree.Branch b ? b.children() : List.of(root);
    }

    /**
     * Every pair at the grain the model is keyed on — demanded, glossed, or
     * both.
     *
     * <p>The <b>union</b>, not the demand alone. This relation replaced the
     * separate character and reading views, so anything it omits becomes
     * unreachable in the workbench — and a pair glossed without being demanded
     * is exactly the kind that would vanish quietly. 得 dei3 is one: nothing in
     * the library reads it, and it is written because a polyphone glossed at
     * some readings and not others implies the rest are meaningless.</p>
     *
     * <p>Three states, each a word rather than an absence. An empty cell reads
     * as "nothing to say"; only a word can be filtered for.</p>
     *
     * <ul>
     *   <li>{@code todo} — the library asks for it and nothing explains it</li>
     *   <li>empty — asked for and answered</li>
     *   <li>{@code extra} — answered though never asked; deliberate, but worth
     *       being able to count</li>
     * </ul>
     */
    public static List<Row> rows(List<ZiGloss> glosses) {
        Map<String, String> byPair = new LinkedHashMap<>();
        for (ZiGloss g : glosses) {
            for (SoundGloss s : g.sounds()) byPair.put(s.key(), s.primary().text());
        }

        var out = new ArrayList<Row>();
        var seen = new LinkedHashSet<String>();
        for (String pair : pairs()) {
            String meaning = byPair.get(pair);
            out.add(row(pair, meaning, meaning == null ? "todo" : ""));
            seen.add(pair);
        }
        for (var glossed : byPair.entrySet()) {
            if (seen.add(glossed.getKey())) {
                out.add(row(glossed.getKey(), glossed.getValue(), "extra"));
            }
        }
        return List.copyOf(out);
    }

    private static Row row(String pair, String meaning, String status) {
        int colon = pair.indexOf(':');
        int cp = Integer.parseInt(pair.substring(0, colon));
        return new Row(cp, new String(Character.toChars(cp)), pair.substring(colon + 1),
                meaning == null ? "" : meaning, status);
    }

    /** How many demanded pairs a collection can explain. */
    public static int covered(List<ZiGloss> glosses) {
        return (int) rows(glosses).stream().filter(r -> r.status().isEmpty()).count();
    }
}
