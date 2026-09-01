package kranji.reading.content;

import kranji.reading.model.ArticleClass;
import kranji.reading.model.ArticleHeader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The bundled set, checked as content rather than as code.
 *
 * <p>These are the articles the app ships with. An error in one is not a unit
 * failure somewhere abstract — it is a page a child would open and find
 * broken, so it is worth failing the build over.</p>
 */
class BundledArticlesTest {

    @Test
    void everyHeaderIsWellFormed() {
        // headers() throws, naming the file, on a missing uuid or unknown type.
        List<ArticleHeader> headers = ClasspathArticles.INSTANCE.headers();
        assertFalse(headers.isEmpty(), "the app ships with articles");
    }

    @Test
    void everyUuidIsDistinct() {
        Set<String> seen = new HashSet<>();
        for (ArticleHeader h : ClasspathArticles.INSTANCE.headers()) {
            assertTrue(seen.add(h.uuid().toString()),
                    () -> "two articles share a uuid: " + h.uuid()
                        + " - it is an identity, not a label");
        }
    }

    @Test
    void everyArticleParsesWithoutErrors() {
        var broken = new ArrayList<String>();
        for (ArticleHeader h : ClasspathArticles.INSTANCE.headers()) {
            ParsedArticle parsed = ClasspathArticles.INSTANCE.find(h.id()).orElseThrow();
            for (ParseFinding f : parsed.findings()) {
                if (f.severity() == ParseFinding.Severity.ERROR) {
                    broken.add(h.id().value() + ": " + f);
                }
            }
        }
        assertEquals(List.of(), broken, () -> "articles with errors:\n" + String.join("\n", broken));
    }

    /**
     * How many polyphonic characters take the corpus principal unpinned.
     *
     * <p>Each one in the bundled set has been read in context once. Raise this
     * only after reading the list the failure prints — a change means a
     * reading nobody has looked at.</p>
     */
    private static final int REVIEWED_UNPINNED = 115;

    /**
     * An unpinned polyphonic character takes the corpus principal, which is
     * usually right — 石 in 石室 really is shí. Demanding an override for all
     * of them would add a hundred annotations restating what the corpus
     * already knows, and bury the few that matter among them.
     *
     * <p>So the list is not held at zero; it is held <em>still</em>. The eight
     * that were wrong — 一只{@code {zhī}}, 跑得{@code {de}}, 种{@code {zhòng}}田,
     * 地{@code {dì}}上, 很长{@code {cháng}} — are pinned in the source.</p>
     */
    @Test
    void theUnpinnedReadingsAreTheOnesAlreadyReviewed() {
        var unpinned = new ArrayList<String>();
        for (ArticleHeader h : ClasspathArticles.INSTANCE.headers()) {
            ParsedArticle parsed = ClasspathArticles.INSTANCE.find(h.id()).orElseThrow();
            for (ParseFinding f : parsed.findings()) {
                if (f.severity() == ParseFinding.Severity.WARNING) {
                    unpinned.add(h.id().value() + " " + f);
                }
            }
        }
        assertEquals(REVIEWED_UNPINNED, unpinned.size(),
                () -> "the set of unpinned readings changed - read these, then adjust"
                    + " REVIEWED_UNPINNED:\n" + String.join("\n", unpinned));
    }

    @Test
    void theDemoSetCoversEveryClass() {
        var missing = new ArrayList<String>();
        for (ArticleClass type : ArticleClass.values()) {
            if (ClasspathArticles.INSTANCE.of(type).isEmpty()) {
                missing.add(type.wireId() + " (" + type.englishName() + ")");
            }
        }
        assertEquals(List.of(), missing,
                () -> "classes with no article to demonstrate them: " + missing);
    }
}
