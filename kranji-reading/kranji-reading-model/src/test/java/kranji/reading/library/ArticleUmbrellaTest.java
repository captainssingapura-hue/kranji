package kranji.reading.library;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * One work, several tellings — and nothing that already worked noticing.
 *
 * <p>The umbrella was added under a rule: no existing collection changes.
 * Half of these tests are about the new type; the other half are about the
 * old one still being what it was, because the second half is the half that
 * lets another stream keep writing shelves while this one lands.</p>
 */
class ArticleUmbrellaTest {

    private static ArticleRef ref(String slug) {
        return ArticleRef.of(slug, slug, "/x/" + slug + ".txt");
    }

    /** A collection written the old way - articles only, never heard of entries. */
    private record Plain(CollectionId id, String title, List<ArticleRef> articles)
            implements ArticleCollection {}

    /** A collection written the new way - entries, with articles derived. */
    private record Grouped(CollectionId id, String title, List<? extends ArticleEntry> entries)
            implements ArticleCollection {
        @Override public List<ArticleRef> articles() {
            var out = new ArrayList<ArticleRef>();
            for (ArticleEntry e : entries) out.addAll(e.articles());
            return List.copyOf(out);
        }
    }

    // ── The old case is the solo case ──────────────────────────────────

    @Test
    void anArticleOnItsOwnIsAnEntryAndAnswersWithItself() {
        ArticleRef a = ref("chun-xiao");

        assertTrue(a instanceof ArticleEntry, "the record every shelf uses is the solo case");
        assertEquals(List.of(a), a.articles());
    }

    @Test
    void aCollectionThatNeverHeardOfEntriesListsExactlyWhatItAlwaysDid() {
        var c = new Plain(CollectionId.named("k.plain"), "plain",
                List.of(ref("a"), ref("b")));

        // entries() defaults to the articles, unchanged and in order. This is
        // the property that lets six collection classes stay untouched.
        assertEquals(c.articles(), c.entries());
        LibraryTree.shelf(c).validate();
    }

    // ── The umbrella ───────────────────────────────────────────────────

    @Test
    void editionsAreMetInRankOrderHoweverTheyWereDeclared() {
        // Declared original-first, as the source reads; sorted retelling-first,
        // as a child reads.
        var u = ArticleUmbrella.of("ke-zhou-qiu-jian", "刻舟求剑", Map.of(
                new Classifier.Original(), ref("kzqj-yuanwen"),
                new Classifier.Retold(2), ref("kzqj-2"),
                new Classifier.Retold(1), ref("kzqj-1")));

        assertEquals(List.of("kzqj-1", "kzqj-2", "kzqj-yuanwen"),
                u.articles().stream().map(a -> a.id().value()).toList());
        assertEquals(List.of(new Classifier.Retold(1), new Classifier.Retold(2),
                             new Classifier.Original()),
                List.copyOf(u.editions().keySet()));
    }

    @Test
    void depthsOrderByLevel() {
        var u = ArticleUmbrella.of("ma-yi", "蚂蚁", Map.of(
                new Classifier.Level(3), ref("ma-yi-3"),
                new Classifier.Level(1), ref("ma-yi-1"),
                new Classifier.Level(2), ref("ma-yi-2")));

        assertEquals(List.of("ma-yi-1", "ma-yi-2", "ma-yi-3"),
                u.articles().stream().map(a -> a.id().value()).toList());
    }

    @Test
    void anEditionCanBeAskedForByWhatDistinguishesIt() {
        ArticleRef original = ref("kzqj-yuanwen");
        var u = ArticleUmbrella.of("ke-zhou-qiu-jian", "刻舟求剑", Map.of(
                new Classifier.Retold(1), ref("kzqj"),
                new Classifier.Original(), original));

        assertSame(original, u.edition(new Classifier.Original()).orElseThrow());
        assertTrue(u.edition(new Classifier.Retold(3)).isEmpty());
    }

    @Test
    void theLabelsAreWhatAReaderWillBeShown() {
        // On the classifier rather than in a pane, so no two panes can call
        // the same telling two things.
        assertEquals("原文", new Classifier.Original().label());
        assertEquals("白话", new Classifier.Retold(1).label());
        assertEquals("白话二", new Classifier.Retold(2).label());
        assertEquals("第一级", new Classifier.Level(1).label());
        assertEquals("第三级", new Classifier.Level(3).label());
    }

    @Test
    void levelsStartAtOne() {
        // Level zero would read as "below the plainest", which nothing is.
        assertThrows(IllegalArgumentException.class, () -> new Classifier.Level(0));
        assertThrows(IllegalArgumentException.class, () -> new Classifier.Retold(0));
    }

    @Test
    void anUmbrellaNeedsSomethingUnderIt() {
        assertThrows(IllegalArgumentException.class,
                () -> ArticleUmbrella.of("empty", "空", Map.of()));
        assertThrows(IllegalArgumentException.class,
                () -> ArticleUmbrella.of("blank", " ", Map.of(new Classifier.Original(), ref("x"))));
    }

    // ── The shelf that holds one ───────────────────────────────────────

    @Test
    void aGroupedShelfResolvesEveryTellingByAddress() {
        ArticleRef retold = ref("kzqj");
        ArticleRef original = ref("kzqj-yuanwen");
        var c = new Grouped(CollectionId.named("k.chengyu"), "成语", List.of(
                ref("hua-she-tian-zu"),
                ArticleUmbrella.of("ke-zhou-qiu-jian", "刻舟求剑", Map.of(
                        new Classifier.Retold(1), retold,
                        new Classifier.Original(), original))));

        // Two entries on the shelf; three addressable articles under them.
        assertEquals(2, c.entries().size());
        assertEquals(3, c.articles().size());
        assertSame(original, c.article(LocalId.named("kzqj-yuanwen")).orElseThrow());
        LibraryTree.shelf(c).validate();
    }

    @Test
    void anUmbrellaMayShareItsSlugWithTheTellingItOpensOn() {
        // The retelling keeps the address it always had; the umbrella takes
        // the same slug as its own node id. Different namespaces - articles
        // and entries - so this is allowed, and it is what keeps bookmarks
        // working when a solo article becomes an umbrella.
        var c = new Grouped(CollectionId.named("k.yuyan"), "寓言", List.of(
                ArticleUmbrella.of("shou-zhu-dai-tu", "守株待兔", Map.of(
                        new Classifier.Retold(1), ref("shou-zhu-dai-tu"),
                        new Classifier.Original(), ref("shou-zhu-dai-tu-yuanwen")))));

        LibraryTree.shelf(c).validate();
        assertTrue(c.article(LocalId.named("shou-zhu-dai-tu")).isPresent());
    }

    @Test
    void twoEntriesClaimingOneIdIsAnError() {
        var c = new Grouped(CollectionId.named("k.dup"), "dup", List.of(
                ArticleUmbrella.of("same", "一", Map.of(new Classifier.Original(), ref("a"))),
                ArticleUmbrella.of("same", "二", Map.of(new Classifier.Original(), ref("b")))));

        var ex = assertThrows(IllegalStateException.class, () -> LibraryTree.shelf(c).validate());
        assertTrue(ex.getMessage().contains("two entries with the id 'same'"), ex.getMessage());
    }

    @Test
    void entriesAndArticlesMustBeTheSameArticles() {
        // A collection that overrides entries() and forgets articles() would
        // resolve addresses for tellings the tree never shows. Caught at
        // validate(), naming the counts.
        record Forgetful(CollectionId id, String title) implements ArticleCollection {
            @Override public List<ArticleRef> articles() { return List.of(ref("only-one")); }
            @Override public List<? extends ArticleEntry> entries() {
                return List.of(ArticleUmbrella.of("u", "伞", Map.of(
                        new Classifier.Retold(1), ref("only-one"),
                        new Classifier.Original(), ref("and-another"))));
            }
        }

        var ex = assertThrows(IllegalStateException.class,
                () -> LibraryTree.shelf(new Forgetful(CollectionId.named("k.f"), "f")).validate());
        assertTrue(ex.getMessage().contains("2 article(s) through its entries but 1"), ex.getMessage());
    }
}
