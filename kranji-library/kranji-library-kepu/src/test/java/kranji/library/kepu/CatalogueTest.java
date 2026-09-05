package kranji.library.kepu;

import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * The catalogue points at things that exist.
 *
 * <p>A tree is metadata and the texts are files, which is what lets the whole
 * library be listed without opening any of them - and is exactly why a
 * reference can rot without anything noticing until a reader clicks it.</p>
 */
class CatalogueTest {

    @Test
    void everyArticleResolvesToAResource() {
        var missing = new ArrayList<String>();
        for (ArticleCollection c : KePuLibrary.TREE.collections()) {
            for (ArticleRef ref : c.articles()) {
                try (InputStream in = getClass().getResourceAsStream(ref.resource())) {
                    if (in == null) missing.add(ref.title() + " -> " + ref.resource());
                } catch (Exception e) {
                    missing.add(ref.title() + " -> " + e);
                }
            }
        }
        assertEquals(java.util.List.of(), missing, "articles with no text: " + missing);
    }

    @Test
    void everyCollectionIsMountedAndHasArticles() {
        var mounted = new LinkedHashSet<String>();
        for (ArticleCollection c : KePuLibrary.TREE.collections()) {
            mounted.add(c.id().toString());
            assertFalse(c.articles().isEmpty(), c.title() + " is an empty shelf");
        }
        // Declared but never hung on the tree is the other half: a collection
        // nobody can reach is indistinguishable from one nobody wrote.
        for (ArticleCollection c : KePuCollections.all()) {
            assertFalse(!mounted.contains(c.id().toString()),
                    c.title() + " is declared but not mounted on the tree");
        }
    }

    @Test
    void noTwoArticlesShareAResource() {
        var seen = new LinkedHashSet<String>();
        var repeated = new ArrayList<String>();
        for (ArticleCollection c : KePuLibrary.TREE.collections()) {
            for (ArticleRef ref : c.articles()) {
                if (!seen.add(ref.resource())) repeated.add(ref.resource());
            }
        }
        assertEquals(java.util.List.of(), repeated,
                "one text mounted twice, which makes a count of the library wrong: " + repeated);
    }
}
