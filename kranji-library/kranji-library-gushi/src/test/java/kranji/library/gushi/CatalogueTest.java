package kranji.library.gushi;

import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.ArticleUmbrella;
import kranji.reading.library.Classifier;
import kranji.reading.library.LocalId;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The catalogue points at things that exist.
 *
 * <p>A tree is metadata and the texts are files, which is what lets the whole
 * library be listed without opening any of them - and is exactly why a
 * reference can rot without anything noticing until a reader clicks it.</p>
 */
class CatalogueTest {

    @Test
    void aStoryWithAnOriginalIsOneEntryAndTwoArticles() {
        // 韩非子 is the shelf named after the book, so the book's own words
        // belong on it. The retelling keeps the slug it always had - the
        // check that matters, because that slug is an address somebody may
        // have bookmarked - and the original takes -yuanwen beside it.
        var umbrellas = GuShiCollections.HAN_FEI.entries().stream()
                .filter(e -> e instanceof ArticleUmbrella<?>)
                .map(e -> (ArticleUmbrella<?>) e)
                .toList();

        assertEquals(3, umbrellas.size(), "three stories carry their original");
        for (ArticleUmbrella<?> u : umbrellas) {
            assertEquals(2, u.editions().size(), u.title() + " should have a retelling and an original");
            assertTrue(GuShiCollections.HAN_FEI.article(u.id()).isPresent(),
                    u.title() + ": the retelling must keep the umbrella's slug");
            assertTrue(GuShiCollections.HAN_FEI
                            .article(LocalId.named(u.id().value() + "-yuanwen")).isPresent(),
                    u.title() + ": the original is addressable beside it");
            // Retelling first: a child arrives at 文言 by way of 白话.
            assertEquals("白话", ((Classifier) u.editions().keySet().iterator().next()).label());
        }
    }

    @Test
    void everyArticleResolvesToAResource() {
        var missing = new ArrayList<String>();
        for (ArticleCollection c : GuShiLibrary.TREE.collections()) {
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
        for (ArticleCollection c : GuShiLibrary.TREE.collections()) {
            mounted.add(c.id().toString());
            assertFalse(c.articles().isEmpty(), c.title() + " is an empty shelf");
        }
        // Declared but never hung on the tree is the other half: a collection
        // nobody can reach is indistinguishable from one nobody wrote.
        for (ArticleCollection c : GuShiCollections.all()) {
            assertFalse(!mounted.contains(c.id().toString()),
                    c.title() + " is declared but not mounted on the tree");
        }
    }

    @Test
    void noTwoArticlesShareAResource() {
        var seen = new LinkedHashSet<String>();
        var repeated = new ArrayList<String>();
        for (ArticleCollection c : GuShiLibrary.TREE.collections()) {
            for (ArticleRef ref : c.articles()) {
                if (!seen.add(ref.resource())) repeated.add(ref.resource());
            }
        }
        assertEquals(java.util.List.of(), repeated,
                "one text mounted twice, which makes a count of the library wrong: " + repeated);
    }
}
