package kranji.reading.model;

import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * What an article contains, counted by reading.
 *
 * <p>A property of the article alone, with nothing about any reader in it.
 * That is what it was built for and it is why it survived the thing it was
 * built for: a measure that graded a reader against an article has been
 * retired, and this — which only ever counted the article — is now what tells
 * the gloss worklist which readings the library actually demands.</p>
 *
 * <h2>Counted by reading, not by character</h2>
 *
 * <p>Because that is how the known set is keyed, and because it is the
 * truthful count. An article using 行 as háng asks nothing of a reader who has
 * learnt xíng, and a gloss written for one is not a gloss for the other —
 * folding them together would report a demand that is already met.</p>
 *
 * <h2>Two numbers, not one</h2>
 *
 * <p>{@link #total} counts every Han token including repeats, so a character
 * met forty times weighs forty times. {@link #pairs} counts distinct readings.
 * The gloss worklist wants both: how often a reading appears is how much a
 * missing meaning costs, and how many distinct readings are missing is how
 * much work is left.</p>
 *
 * <p>Punctuation, spaces, Latin and digits are not counted at all. They are
 * {@link Token.Plain}, never annotated, and including them would quietly
 * inflate every article that used a lot of commas.</p>
 */
public record ArticleCensus(int total, Map<String, Integer> pairs) implements ValueObject {

    public ArticleCensus {
        if (total < 0) throw new IllegalArgumentException("a census cannot count backwards");
        pairs = Map.copyOf(Objects.requireNonNull(pairs, "pairs"));
    }

    /**
     * The key a reading is counted under.
     *
     * <p><strong>This must match {@code KnownSetModule.keyOf} exactly.</strong>
     * The whole measure is a set intersection between these keys and the keys
     * in a known set; a difference of one character in the format makes every
     * article read as 0% and nothing throws. That agreement is pinned by
     * test.</p>
     *
     * <p>The codepoint rather than the glyph so the key is fixed-width ASCII
     * and cannot be broken by a surrogate pair; the reading in the canonical
     * numbered form - {@code di4}, not {@code dì} - which is ASCII apart from
     * ü and so carries no combining marks for two writers to normalise
     * differently.</p>
     */
    public static String keyOf(Token.Zi token) {
        return token.zi().value().codePointAt(0) + ":" + token.reading().numbered();
    }

    /** Counts an article. Insertion-ordered, so the wire is stable across builds. */
    public static ArticleCensus of(Article article) {
        Objects.requireNonNull(article, "article");
        Map<String, Integer> counts = new LinkedHashMap<>();
        int total = 0;
        for (Token.Zi token : article.characters()) {
            counts.merge(keyOf(token), 1, Integer::sum);
            total++;
        }
        return new ArticleCensus(total, counts);
    }

    /** How many distinct readings the article asks for. */
    public int distinct() { return pairs.size(); }
}
