package kranji.reading.library;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The library's identity and composition rules.
 *
 * <p>These are the rules that are cheap now and expensive later: a bad id or a
 * silent duplicate is discovered by someone opening the wrong article, months
 * on, not by anything that runs.</p>
 */
class LibraryModelTest {

    private record Fixed(CollectionId id, String title, List<ArticleRef> articles)
            implements ArticleCollection {}

    private static ArticleCollection collection(String id, ArticleRef... articles) {
        return new Fixed(CollectionId.named(id), id, List.of(articles));
    }

    // ── Named ids ──────────────────────────────────────────────────────

    @Test
    void acceptsADottedName() {
        assertEquals("kranji.reader.demo.tangshi",
                CollectionId.named("kranji.reader.demo.tangshi").value());
    }

    @Test
    void acceptsDigitsAndHyphensAfterTheFirstLetter() {
        assertEquals("kranji.reader.demo.abc123", CollectionId.named("kranji.reader.demo.abc123").value());
        assertEquals("jing-ye-si", LocalId.named("jing-ye-si").value());
    }

    @Test
    void rejectsUppercase() {
        // Not a style preference. Kranji.Demo and kranji.demo resolving as two
        // collections is a collision that reads as a typo.
        var e = assertThrows(IllegalArgumentException.class,
                () -> CollectionId.named("Kranji.Demo"));
        assertTrue(e.getMessage().contains("lowercase"), e.getMessage());
    }

    @Test
    void rejectsASegmentStartingWithADigit() {
        assertThrows(IllegalArgumentException.class, () -> CollectionId.named("kranji.2tang"));
    }

    @Test
    void rejectsEmptySegments() {
        assertThrows(IllegalArgumentException.class, () -> CollectionId.named("kranji..demo"));
        assertThrows(IllegalArgumentException.class, () -> CollectionId.named(".kranji"));
        assertThrows(IllegalArgumentException.class, () -> CollectionId.named("kranji."));
    }

    @Test
    void rejectsUnderscoresAndSpaces() {
        assertThrows(IllegalArgumentException.class, () -> CollectionId.named("kranji.my_demo"));
        assertThrows(IllegalArgumentException.class, () -> CollectionId.named("kranji.my demo"));
    }

    @Test
    void aCollectionIdAndALocalIdAreDifferentTypes() {
        // The point of two types rather than one: this is a compile error, not
        // a runtime surprise.
        //   ArticleAddress bad = new ArticleAddress(LocalId.named("a"), LocalId.named("b"));
        var address = new ArticleAddress(CollectionId.named("kranji.reader.demo.tangshi"),
                                         LocalId.named("jing-ye-si"));
        assertEquals("kranji.reader.demo.tangshi:jing-ye-si", address.toString());
    }

    @Test
    void aUuidIdNeedsNoNamingAuthority() {
        UUID raw = UUID.fromString("5eafb69f-feaa-4d5e-8428-111b0735491c");
        assertEquals(raw.toString(), CollectionId.of(raw).value());
    }

    // ── Composition ────────────────────────────────────────────────────

    @Test
    void gathersCollectionsInMountOrder() {
        LibraryTree tree = LibraryTree.of("Reading",
                LibraryTree.branch("诗歌", collection("kranji.a"), collection("kranji.b")),
                LibraryTree.branch("故事", collection("kranji.c")));

        assertEquals(List.of("kranji.a", "kranji.b", "kranji.c"),
                tree.collections().stream().map(c -> c.id().value()).toList());
    }

    @Test
    void resolvesAnAddressThroughTheTree() {
        ArticleRef poem = ArticleRef.by("jing-ye-si", "静夜思", "李白", "/articles/jing-ye-si.txt");
        LibraryTree tree = LibraryTree.branch("诗歌",
                collection("kranji.reader.demo.tangshi", poem));

        var found = tree.find(new ArticleAddress(
                CollectionId.named("kranji.reader.demo.tangshi"), LocalId.named("jing-ye-si")));
        assertTrue(found.isPresent());
        assertEquals("静夜思", found.get().title());
    }

    // ── Collisions stop start-up ───────────────────────────────────────

    @Test
    void twoCollectionsClaimingOneIdIsAnError() {
        LibraryTree tree = LibraryTree.branch("诗歌",
                collection("kranji.reader.demo.tangshi"),
                collection("kranji.reader.demo.tangshi"));

        var e = assertThrows(IllegalStateException.class, tree::validate);
        assertTrue(e.getMessage().contains("kranji.reader.demo.tangshi"), e.getMessage());
    }

    @Test
    void twoArticlesClaimingOneLocalIdIsAnError() {
        LibraryTree tree = LibraryTree.branch("诗歌",
                collection("kranji.reader.demo.tangshi",
                        ArticleRef.of("jing-ye-si", "静夜思", "/a.txt"),
                        ArticleRef.of("jing-ye-si", "静夜思 (again)", "/b.txt")));

        var e = assertThrows(IllegalStateException.class, tree::validate);
        assertTrue(e.getMessage().contains("jing-ye-si"), e.getMessage());
    }

    @Test
    void theSameLocalIdInTwoCollectionsIsFine() {
        // The collection is the uniqueness boundary - that is what lets a slug
        // be the id at all.
        LibraryTree tree = LibraryTree.branch("诗歌",
                collection("kranji.a", ArticleRef.of("chun-tian", "春天来了", "/a.txt")),
                collection("kranji.b", ArticleRef.of("chun-tian", "春天", "/b.txt")));

        tree.validate();
        assertEquals(2, tree.collections().size());
    }

    // ── Refs ───────────────────────────────────────────────────────────

    @Test
    void anIllustrationMustDescribeItself() {
        // An unlabelled image is unusable to part of the audience and the
        // omission is invisible to everyone else, so it is not optional.
        assertThrows(IllegalArgumentException.class,
                () -> ImageRef.of("panda", "  ", "/img/panda.png"));
    }

    @Test
    void anArticleNeedsATitleAndAResource() {
        assertThrows(IllegalArgumentException.class,
                () -> ArticleRef.of("x", "", "/a.txt"));
        assertThrows(IllegalArgumentException.class,
                () -> ArticleRef.of("x", "静夜思", ""));
    }
}
