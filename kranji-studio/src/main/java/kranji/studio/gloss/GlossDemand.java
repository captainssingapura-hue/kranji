package kranji.studio.gloss;

import kranji.reading.content.Articles;
import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.Libraries;
import kranji.reading.model.ArticleCensus;
import kranji.simple.gloss.SoundGloss;
import kranji.simple.gloss.ZiGloss;

import java.util.ArrayList;
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

    /** Every (codepoint, reading) the bundled articles use, in reading order. */
    public static Set<String> pairs() {
        var out = new LinkedHashSet<String>();
        for (ArticleCollection c : Libraries.mounted().tree().collections()) {
            for (ArticleRef ref : c.articles()) {
                Articles.read(c.address(ref.id()), ref).ifPresent(parsed ->
                        parsed.article().ifPresent(a ->
                                out.addAll(ArticleCensus.of(a).pairs().keySet())));
            }
        }
        return out;
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
