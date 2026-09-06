package kranji.reading.app.read;

import hue.captains.singapura.js.homing.ssjs.test.JsModuleTestBase;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * What an article asks of a reader — counted in the browser this time.
 *
 * <p>The same questions {@code ArticleCensusTest} asks of the server's
 * {@code ArticleCensus.of}, asked of the JavaScript that now does the counting
 * for the reader. Deliberately the same cases rather than a fresh set: two
 * implementations of one rule can only be trusted to agree if they are asked
 * the same things, and the ones worth asking are what gets counted and under
 * which key.</p>
 *
 * <p>A census counted by character rather than by reading, or keyed a hair
 * differently from the known set, produces a number that is wrong without being
 * obviously wrong — every article at 0%, or an article reported easier than the
 * child will find it. Nothing throws either way.</p>
 *
 * <p>The inputs differ because the two sides hold the article differently: the
 * server has parsed tokens, the browser has the cells it scanned to draw with.
 * A cell is {@code { z: glyph, r: reading }}, and one with no reading is the
 * browser's equivalent of a {@code Token.Plain} — punctuation, the padding past
 * the end of a line, or a character the corpus does not model.</p>
 */
class ArticleCensusModuleTest extends JsModuleTestBase {

    private static final String KNOWN_SET =
            "/homing/js/kranji/reading/app/known/KnownSetModule.js";
    private static final String MODULE =
            "/homing/js/kranji/reading/app/read/ArticleCensusModule.js";

    private Value census;

    @BeforeEach
    void load() {
        loadModule(KNOWN_SET);
        loadModule(MODULE);
        census = global("createArticleCensus").execute();
    }

    /** `cells` is JS array source, e.g. "{z:'好',r:'hao3'}". */
    private Value of(String cells) {
        return census.getMember("ofCells").execute(js.eval("js", "([" + cells + "])"));
    }

    private int total(Value c) { return c.getMember("total").asInt(); }

    private int distinct(Value c) {
        return (int) c.getMember("pairs").getMemberKeys().stream().count();
    }

    private int occurrences(Value c, String key) {
        Value pairs = c.getMember("pairs");
        return pairs.hasMember(key) ? pairs.getMember(key).asInt() : 0;
    }

    // ── The key ────────────────────────────────────────────────────────

    @Test
    void keysExactlyAsTheKnownSetDoes() {
        // The same two pins as the server's test. The measure is a set
        // intersection with the known set's keys; one character of difference
        // makes every article read 0% and nothing throws.
        assertEquals(1, occurrences(of("{z:'行',r:'xing2'}"), "34892:xing2"));
        assertEquals(1, occurrences(of("{z:'好',r:'hao3'}"), "22909:hao3"));
    }

    @Test
    void countsByReadingNotByCharacter() {
        // An article using 行 as háng asks nothing of a reader who learnt
        // xíng. Folding them together would report a readability the child
        // cannot actually achieve.
        Value c = of("{z:'行',r:'xing2'},{z:'行',r:'hang2'}");

        assertEquals(2, distinct(c));
        assertEquals(1, occurrences(c, "34892:xing2"));
        assertEquals(1, occurrences(c, "34892:hang2"));
    }

    // ── What is counted ────────────────────────────────────────────────

    @Test
    void repeatsCountTowardsTheTotal() {
        // The ratio should reflect the reading experience: a character met
        // three times is three moments of support.
        Value c = of("{z:'好',r:'hao3'},{z:'好',r:'hao3'},{z:'好',r:'hao3'}");

        assertEquals(3, total(c));
        assertEquals(1, distinct(c), "and one reading to learn");
        assertEquals(3, occurrences(c, "22909:hao3"));
    }

    @Test
    void punctuationIsNotCountedAtAll() {
        // A cell with no reading is the browser's Token.Plain. Counting them
        // would inflate every article that used a lot of commas.
        Value c = of("{z:'好',r:'hao3'},{z:'，',r:''},{z:' ',r:''}");

        assertEquals(1, total(c));
        assertEquals(1, distinct(c));
    }

    @Test
    void aCharacterTheCorpusCannotReadIsLeftOut() {
        // fill() leaves r empty when the map has no reading for a codepoint.
        // The server drops the same character rather than guessing one, so
        // both sides count an article of one unreadable glyph as empty.
        Value c = of("{z:'好',r:'hao3'},{z:'兀',r:''}");

        assertEquals(1, total(c));
        assertEquals(1, distinct(c));
    }

    @Test
    void paddingPastTheEndOfALineIsNotACharacter() {
        // The board pads rows to keep the column grid. Those cells carry
        // neither glyph nor reading.
        Value c = of("{z:'好',r:'hao3'},{t:''},{t:''}");

        assertEquals(1, total(c));
    }

    @Test
    void countsAcrossEveryLine() {
        // ofLines is the whole article: the server counts across every block,
        // and this counts across every line the pane hands it.
        Value byLines = census.getMember("ofLines").execute(
                js.eval("js", "(['a','b'])"),
                js.eval("js", "(function (line) { return line === 'a'"
                            + " ? [{z:'好',r:'hao3'}]"
                            + " : [{z:'月',r:'yue4'},{z:'好',r:'hao3'}]; })"));

        assertEquals(3, total(byLines));
        assertEquals(2, distinct(byLines));
        assertEquals(2, occurrences(byLines, "22909:hao3"));
    }

    @Test
    void anArticleOfNoCharactersIsCountable() {
        // Not a crash and not a divide-by-zero waiting to happen downstream.
        Value c = of("{z:'.',r:''}");

        assertEquals(0, total(c));
        assertEquals(0, distinct(c));
    }

    @Test
    void nothingAtAllIsCountable() {
        // An article that has not arrived yet, and an empty line, take the
        // same path as one with no Han in it.
        assertEquals(0, total(of("")));
        assertEquals(0, total(census.getMember("ofCells").execute(js.eval("js", "(null)"))));
    }
}
