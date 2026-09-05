package kranji.reading.app.read;

import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.Libraries;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void carriesEveryArticleUnderItsFullAddress() {
        // collection:local, the same identity the reader and the tree use, so
        // a node's segment indexes straight into the census with no lookup.
        assertTrue(CENSUS.contains("\"kranji.library.shici.libai-wuyan:libai-jing-ye-si\""),
                "one address written out, to pin the format");

        // And no article left out. censusJs skips anything that will not parse,
        // which is the right call at run time - a broken text should not empty
        // the catalogue - but it means a parse defect would otherwise show up
        // as an article that silently has no readability figure.
        var missing = new ArrayList<String>();
        for (ArticleCollection c : Libraries.mounted().tree().collections()) {
            for (ArticleRef ref : c.articles()) {
                String address = c.address(ref.id()).toString();
                if (!CENSUS.contains('"' + address + '"')) missing.add(address);
            }
        }
        assertEquals(List.of(), missing, "articles the census left out: " + missing);
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
    void staysCheapPerArticleSoItCanBeSentWhole() {
        // Sent once for the entire library rather than per article, so its
        // size is what makes that decision defensible. This used to be a flat
        // ceiling, which measured two things at once and failed the moment the
        // library grew from 23 articles to 475 - a change that was the point,
        // not a regression. What must not grow is the cost of one article,
        // which is where a field nobody needs would show up.
        int articles = Libraries.mounted().tree().collections().stream()
                .mapToInt(c -> c.articles().size()).sum();
        int each = CENSUS.length() / articles;
        assertTrue(each < 900,
                () -> "census is " + CENSUS.length() + " chars for " + articles
                    + " articles, " + each + " each");
    }

    @Test
    void isValidModuleSyntax() {
        assertTrue(CENSUS.startsWith("// Generated from the Kranji corpus"), CENSUS);
        assertTrue(CENSUS.contains("export const articles = {"), CENSUS);
        assertTrue(CENSUS.trim().endsWith("};"), CENSUS);
    }
}
