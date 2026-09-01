package kranji.reading.content;

import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.LibraryTree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The bundled library, checked as content rather than as code.
 *
 * <p>An error in one of these is not a unit failure somewhere abstract — it is
 * a page a child would open and find broken, so it is worth failing the build
 * over.</p>
 */
class BundledArticlesTest {

    private static final LibraryTree TREE = DemoLibrary.INSTANCE.tree();

    private static List<ArticleCollection> collections() { return TREE.collections(); }

    @Test
    void theLibraryIsInternallyConsistent() {
        // Collection ids globally unique, local ids unique within each.
        TREE.validate();
        assertEquals(7, collections().size());
    }

    @Test
    void everyArticleResourceExists() {
        var missing = new ArrayList<String>();
        for (ArticleCollection c : collections()) {
            for (ArticleRef ref : c.articles()) {
                if (Articles.sourceOf(ref).isEmpty()) {
                    missing.add(c.id() + ":" + ref.id() + " -> " + ref.resource());
                }
            }
        }
        assertEquals(List.of(), missing,
                () -> "articles whose resource is not on the classpath:\n"
                    + String.join("\n", missing));
    }

    @Test
    void everyArticleParsesWithoutErrors() {
        var broken = new ArrayList<String>();
        for (ArticleCollection c : collections()) {
            for (ArticleRef ref : c.articles()) {
                ParsedArticle parsed =
                        Articles.read(c.address(ref.id()), ref).orElseThrow();
                for (ParseFinding f : parsed.findings()) {
                    if (f.severity() == ParseFinding.Severity.ERROR) {
                        broken.add(c.id() + ":" + ref.id() + " " + f);
                    }
                }
            }
        }
        assertEquals(List.of(), broken,
                () -> "articles with errors:\n" + String.join("\n", broken));
    }

    /**
     * How many polyphonic characters take the corpus principal unpinned.
     *
     * <p>Held still rather than at zero. The principal is usually right — 石 in
     * 石室 really is shí — so demanding an override for all of them would add
     * hundreds of annotations restating what the corpus already knows, and bury
     * the few that matter. A change to this number means a reading nobody has
     * looked at; read the list the failure prints before adjusting it.</p>
     */
    private static final int REVIEWED_UNPINNED = 324;

    @Test
    void theUnpinnedReadingsAreTheOnesAlreadyReviewed() {
        var unpinned = new ArrayList<String>();
        for (ArticleCollection c : collections()) {
            for (ArticleRef ref : c.articles()) {
                ParsedArticle parsed =
                        Articles.read(c.address(ref.id()), ref).orElseThrow();
                for (ParseFinding f : parsed.findings()) {
                    if (f.severity() == ParseFinding.Severity.WARNING) {
                        unpinned.add(c.id() + ":" + ref.id() + " " + f);
                    }
                }
            }
        }
        assertEquals(REVIEWED_UNPINNED, unpinned.size(),
                () -> "the set of unpinned readings changed - read these, then adjust"
                    + " REVIEWED_UNPINNED:\n" + String.join("\n", unpinned));
    }

    /**
     * The most articles a collection may hold.
     *
     * <p>A collection is mounted whole, so it has to be small enough that
     * mounting it is a real choice. 唐诗三百首 is coherent, closed and canonical,
     * and at three hundred it is still not a collection — it is a shelf of
     * them, wanting subdivision by form or by poet.</p>
     *
     * <p>Thirty comfortably holds one poet's selection or a themed set, and
     * forces anything canonical into a structure a reader can navigate.</p>
     */
    private static final int MAX_PER_COLLECTION = 30;

    @Test
    void noCollectionGrowsTooBigToMountWhole() {
        var oversized = new ArrayList<String>();
        for (ArticleCollection c : collections()) {
            if (c.articles().size() > MAX_PER_COLLECTION) {
                oversized.add(c.id().value() + " holds " + c.articles().size());
            }
        }
        assertEquals(List.of(), oversized,
                () -> "classify these into sub-collections, or partition them if no real"
                    + " distinction exists:\n" + String.join("\n", oversized));
    }

    @Test
    void everyCollectionHasSomethingInIt() {
        var empty = new ArrayList<String>();
        for (ArticleCollection c : collections()) {
            if (c.articles().isEmpty()) empty.add(c.id().value());
        }
        assertEquals(List.of(), empty, () -> "collections with no articles: " + empty);
    }

    @Test
    void theSameSlugMayAppearInTwoCollections() {
        // 守株待兔 is a modern retelling in 寓言故事 and its 韩非子 original in
        // 文言启蒙. The collection is the uniqueness boundary, so both are
        // legitimate - and reading them side by side is the point.
        var holders = new ArrayList<String>();
        for (ArticleCollection c : collections()) {
            if (c.articles().stream().anyMatch(a -> a.id().value().equals("shou-zhu-dai-tu"))) {
                holders.add(c.id().value());
            }
        }
        assertEquals(2, holders.size(), () -> "expected two, got " + holders);
        assertFalse(holders.get(0).equals(holders.get(1)));
    }

    @Test
    void everyArticleHasATitleAndAResource() {
        for (ArticleCollection c : collections()) {
            for (ArticleRef ref : c.articles()) {
                assertTrue(!ref.title().isBlank(), () -> ref.id() + " needs a title");
                assertTrue(ref.resource().startsWith("/articles/"),
                        () -> ref.id() + " resource should be under /articles/");
            }
        }
    }
}
