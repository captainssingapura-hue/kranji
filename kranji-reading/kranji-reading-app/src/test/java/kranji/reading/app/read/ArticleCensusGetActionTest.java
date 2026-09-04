package kranji.reading.app.read;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The half of readability the server is allowed to know.
 *
 * <p>The census says what each article asks; the known set says what the reader
 * brings, and never leaves the device. These tests guard the seam: that the
 * wire carries what the browser needs to do the arithmetic, and that it carries
 * nothing about any reader.</p>
 */
class ArticleCensusGetActionTest {

    private static final String CENSUS = ArticleCensusGetAction.censusJs();

    @Test
    void carriesEveryBundledArticleUnderItsFullAddress() {
        // collection:local, the same identity the reader and the tree use, so
        // a node's segment indexes straight into the census with no lookup.
        assertTrue(CENSUS.contains("\"kranji.reader.demo.tangshi:jing-ye-si\""), CENSUS);
        assertTrue(CENSUS.contains("\"kranji.reader.demo.erge:xiao-yu-dian\""), CENSUS);
    }

    @Test
    void keysReadingsTheWayTheKnownSetDoes() {
        // codePoint:reading. A difference of one character here makes every
        // article read 0% in the browser and nothing throws on either side.
        assertTrue(CENSUS.contains("\"24202:chuang2\""),
                "expected the key for the first character of 静夜思");
    }

    @Test
    void countsRepeatsRatherThanRepeatingKeys() {
        // 静夜思 uses 明, 月 and 头 twice each. The wire should say so once with
        // a count, not twice - it is the difference between a census that
        // scales and one that ships the article again.
        assertTrue(CENSUS.contains("\"26376:yue4\": 2"), "月 appears twice in 静夜思");
        assertTrue(CENSUS.contains("\"22836:tou2\": 2"), "头 appears twice in 静夜思");
    }

    @Test
    void carriesTheTotalSoTheRatioHasADenominator() {
        assertTrue(CENSUS.contains("total: 20"), "静夜思 is twenty characters");
    }

    @Test
    void saysNothingAboutAnyReader() {
        // The whole reason the arithmetic happens in the browser. If any of
        // these ever appears here, profile state has reached the server.
        for (String leak : new String[] { "known", "profile", "readability", "band" }) {
            assertFalse(CENSUS.contains(leak),
                    () -> "the census must carry nothing reader-specific, found: " + leak);
        }
    }

    @Test
    void isASmallEnoughModuleToSendWhole() {
        // Sent once for the entire library rather than per article, so its
        // size is the thing that makes that decision defensible. A regression
        // here means the census has started carrying something it should not.
        assertTrue(CENSUS.length() < 64_000,
                () -> "census grew to " + CENSUS.length() + " chars");
    }

    @Test
    void isValidModuleSyntax() {
        assertTrue(CENSUS.startsWith("// Generated from the Kranji corpus"), CENSUS);
        assertTrue(CENSUS.contains("export const articles = {"), CENSUS);
        assertTrue(CENSUS.trim().endsWith("};"), CENSUS);
    }
}
