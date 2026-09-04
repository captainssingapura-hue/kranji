package kranji.reading.app.read;

import hue.captains.singapura.js.homing.ssjs.test.JsModuleTestBase;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The number that decides what a child is offered to read.
 *
 * <p>The arithmetic is trivial, which is exactly why it is worth pinning: a
 * ratio that counts distinct readings instead of occurrences, or a band
 * boundary off by one, produces a plausible number that sends a reader to the
 * wrong story. Nothing throws either way.</p>
 */
class ReadabilityModuleTest extends JsModuleTestBase {

    private static final String MODULE =
            "/homing/js/kranji/reading/app/read/ReadabilityModule.js";

    private Value readability;

    @BeforeEach
    void load() {
        loadModule(MODULE);
        readability = global("createReadability").execute();
    }

    private Value of(String censusJs, String knownJs) {
        return readability.getMember("of").execute(
                js.eval("js", "(" + censusJs + ")"), js.eval("js", "([" + knownJs + "])"));
    }

    private double ratio(Value r) { return r.getMember("ratio").asDouble(); }
    private String band(Value r)  { return r.getMember("band").getMember("id").asString(); }

    // ── The ratio ──────────────────────────────────────────────────────

    @Test
    void countsOccurrencesNotDistinctReadings() {
        // A character met forty times is forty moments of support. Counting
        // distinct readings would call this article half readable when the
        // child can in fact read nine words in ten.
        Value r = of("{ total: 10, pairs: { '1:a': 9, '2:b': 1 } }", "'1:a'");

        assertEquals(0.9, ratio(r), 1e-9);
        assertEquals(9, r.getMember("known").asInt());
    }

    @Test
    void reportsDistinctUnknownReadingsSeparately() {
        // The more useful figure of the two: this is the count of things left
        // to learn, not the count of times they turn up.
        Value r = of("{ total: 10, pairs: { '1:a': 7, '2:b': 2, '3:c': 1 } }", "'1:a'");

        assertEquals(2, r.getMember("unknown").asInt());
    }

    @Test
    void knowingNothingIsZero() {
        assertEquals(0.0, ratio(of("{ total: 4, pairs: { '1:a': 4 } }", "")), 1e-9);
    }

    @Test
    void knowingEverythingIsOne() {
        Value r = of("{ total: 4, pairs: { '1:a': 3, '2:b': 1 } }", "'1:a', '2:b'");

        assertEquals(1.0, ratio(r), 1e-9);
        assertEquals(0, r.getMember("unknown").asInt());
    }

    @Test
    void aReadingKnownButAbsentChangesNothing() {
        // The set is the whole reader's, not this article's.
        Value r = of("{ total: 2, pairs: { '1:a': 2 } }", "'9:z', '1:a'");

        assertEquals(1.0, ratio(r), 1e-9);
    }

    @Test
    void anArticleOfNoCharactersReadsAsReadable() {
        // Not a divide by zero, and not 0% - there is nothing in it a reader
        // could fail to read, so it must not be filed under Too hard.
        Value r = of("{ total: 0, pairs: {} }", "");

        assertEquals(1.0, ratio(r), 1e-9);
        assertEquals("comfortable", band(r));
    }

    // ── The bands ──────────────────────────────────────────────────────

    @Test
    void bandsFallOnTheirStatedBoundaries() {
        // Inclusive at the bottom of each band, per the catalogue design.
        assertEquals("comfortable", band(of("{ total: 100, pairs: { '1:a': 98 } }", "'1:a'")));
        assertEquals("just-right",  band(of("{ total: 100, pairs: { '1:a': 90 } }", "'1:a'")));
        assertEquals("stretch",     band(of("{ total: 100, pairs: { '1:a': 75 } }", "'1:a'")));
        assertEquals("too-hard",    band(of("{ total: 100, pairs: { '1:a': 74 } }", "'1:a'")));
    }

    @Test
    void justBelowComfortableIsStillJustRight() {
        assertEquals("just-right", band(of("{ total: 100, pairs: { '1:a': 97 } }", "'1:a'")));
    }

    // ── Ordering ───────────────────────────────────────────────────────

    @Test
    void bestFitBeatsEasiest() {
        // The whole point of ordering by fit: a story the child can already
        // read entirely teaches nothing, and ranking by readability descending
        // would put it first and bury the one worth reading.
        Value easy = of("{ total: 100, pairs: { '1:a': 100 } }", "'1:a'");
        Value fit  = of("{ total: 100, pairs: { '1:a': 94, '2:b': 6 } }", "'1:a'");

        assertTrue(readability.getMember("compare").execute(fit, easy).asDouble() < 0,
                "the 94% article should be offered before the 100% one");
    }

    @Test
    void tooHardSortsBelowBothOfThem() {
        Value fit  = of("{ total: 100, pairs: { '1:a': 94, '2:b': 6 } }", "'1:a'");
        Value hard = of("{ total: 100, pairs: { '1:a': 40, '2:b': 60 } }", "'1:a'");

        assertTrue(readability.getMember("compare").execute(fit, hard).asDouble() < 0);
    }

    @Test
    void aMissingCensusIsNotACrash() {
        // An article the census does not carry - newly added content, a stale
        // cached module - must leave the catalogue drawable.
        assertTrue(readability.getMember("of")
                .execute(js.eval("js", "(null)"), js.eval("js", "([])")).isNull());
    }
}
