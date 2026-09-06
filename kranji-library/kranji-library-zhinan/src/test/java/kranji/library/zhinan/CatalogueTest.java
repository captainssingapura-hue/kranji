package kranji.library.zhinan;

import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * The catalogue points at things that exist.
 *
 * <p>Same three checks the other groups keep, for the same reason: a tree is
 * metadata and the texts are files, so a reference can rot without anything
 * noticing until a reader clicks it. It matters more here than elsewhere —
 * this is the shelf a reader is sent to first, and a guide that will not open
 * is the worst first impression the library can make.</p>
 */
class CatalogueTest {

    @Test
    void everyArticleResolvesToAResource() {
        var missing = new ArrayList<String>();
        for (ArticleCollection c : ZhiNanLibrary.TREE.collections()) {
            for (ArticleRef ref : c.articles()) {
                try (InputStream in = getClass().getResourceAsStream(ref.resource())) {
                    if (in == null) missing.add(ref.title() + " -> " + ref.resource());
                } catch (Exception e) {
                    missing.add(ref.title() + " -> " + e);
                }
            }
        }
        assertEquals(List.of(), missing, "articles with no text: " + missing);
    }

    @Test
    void everyCollectionIsMountedAndHasArticles() {
        var mounted = new LinkedHashSet<String>();
        for (ArticleCollection c : ZhiNanLibrary.TREE.collections()) {
            mounted.add(c.id().toString());
            assertFalse(c.articles().isEmpty(), c.title() + " is an empty shelf");
        }
        for (ArticleCollection c : ZhiNanCollections.all()) {
            assertFalse(!mounted.contains(c.id().toString()),
                    c.title() + " is declared but not mounted on the tree");
        }
    }

    @Test
    void noTwoArticlesShareAResource() {
        var seen = new LinkedHashSet<String>();
        var repeated = new ArrayList<String>();
        for (ArticleCollection c : ZhiNanLibrary.TREE.collections()) {
            for (ArticleRef ref : c.articles()) {
                if (!seen.add(ref.resource())) repeated.add(ref.resource());
            }
        }
        assertEquals(List.of(), repeated,
                "one text mounted twice, which makes a count of the library wrong: " + repeated);
    }
}
