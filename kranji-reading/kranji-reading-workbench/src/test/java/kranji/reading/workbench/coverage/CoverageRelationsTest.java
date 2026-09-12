package kranji.reading.workbench.coverage;

import kranji.reading.workbench.relation.Relation;
import kranji.reading.workbench.relation.RelationSet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The coverage chain, as arithmetic rather than as clicking.
 *
 * <p>The widgets do one thing each — take a set of parent keys and ask for the
 * rows under them — so everything that decides what a person sees is in
 * {@link CoverageRelations#rowsOf} and {@link Relation#under}, and it can be
 * walked here over whatever library this module's classpath holds. That is
 * the demonstration set in this module; the same walk over a real library
 * happens wherever a library's own workbench module runs it.</p>
 */
class CoverageRelationsTest {

    private static final RelationSet SET = CoverageRelations.INSTANCE;

    private static List<Relation.Row> rows(String relation) { return SET.rowsOf(relation); }

    @Test
    void theChainIsShelfThenArticleThenMissing() {
        assertEquals(List.of("shelf", "article", "missing"), SET.relations());
        assertNull(SET.upstreamOf("shelf"), "shelf is the root");
        assertEquals("shelf", SET.upstreamOf("article"));
        assertEquals("article", SET.upstreamOf("missing"));
        for (String relation : SET.relations()) {
            assertFalse(SET.columnsOf(relation).isEmpty(), relation + " has columns");
        }
        assertTrue(SET.columnsOf("nonsense").isEmpty(), "an unknown relation has none");
    }

    @Test
    void everyArticleHangsUnderAShelfThatExists() {
        // The containment edge. A row whose parent key names no row upstream
        // is unreachable once the upstream grid is in the chain. Article ->
        // shelf is keyed on the collection id, which is the one thing about a
        // shelf that cannot be shared with another under a different heading.
        var shelves = rows("shelf").stream().map(Relation.Row::pk).toList();
        assertFalse(shelves.isEmpty(), "the mounted library has shelves");
        for (Relation.Row article : rows("article")) {
            assertTrue(shelves.contains(article.up()),
                    () -> article.pk() + " hangs under shelf '" + article.up() + "', which is not a row");
        }
    }

    @Test
    void everyGapHangsUnderAnArticleThatExists() {
        var articles = rows("article").stream().map(Relation.Row::pk).toList();
        for (Relation.Row gap : rows("missing")) {
            assertTrue(articles.contains(gap.up()),
                    () -> gap.pk() + " hangs under article '" + gap.up() + "', which is not a row");
        }
    }

    @Test
    void pickingAShelfNarrowsToItsArticlesAndNothingElse() {
        Relation.Row shelf = rows("shelf").get(0);
        var under = Relation.under(rows("article"), List.of(shelf.pk()));
        assertFalse(under.isEmpty(), "a shelf has articles");
        for (Relation.Row article : under) assertEquals(shelf.pk(), article.up());
        // And the count the shelf row states is the count the chain delivers.
        int articlesColumn = SET.columnsOf("shelf").indexOf("articles");
        assertEquals(shelf.values().get(articlesColumn), under.size(),
                "the shelf row's article count must be what picking it shows");
    }

    @Test
    void anArticlesMissingCountIsTheNumberOfGapRowsUnderIt() {
        int missingColumn = SET.columnsOf("article").indexOf("missing");
        for (Relation.Row article : rows("article")) {
            var gaps = Relation.under(rows("missing"), List.of(article.pk()));
            assertEquals(article.values().get(missingColumn), gaps.size(),
                    () -> article.label() + " states " + article.values().get(missingColumn)
                        + " missing but has " + gaps.size() + " gap rows");
        }
    }

    @Test
    void theRootCarriesTheWholeLibraryFigureAndNothingElseDoes() {
        // The status line above the shelf grid says what fraction of the whole
        // library is covered; no shelf row could, and it is the first number a
        // person opening the bench wants. The other two say nothing there and
        // keep their row count alone.
        String note = SET.noteOf("shelf");
        assertTrue(note != null && note.contains("% of reads"), () -> "note: " + note);
        assertNull(SET.noteOf("article"));
        assertNull(SET.noteOf("missing"));
    }

    @Test
    void theModuleOnTheWireCarriesTheNoteAndScopesByParent() {
        String whole = CoverageRelationGetAction.moduleFor("shelf");
        assertTrue(whole.contains("\"note\": \""), "the shelf module carries its note");
        assertTrue(whole.contains("\"upstream\": null"), "and says it is a root");

        Relation.Row shelf = rows("shelf").get(0);
        String scoped = CoverageRelationGetAction.moduleFor("article", List.of(shelf.pk()), null);
        assertTrue(scoped.contains("\"upstream\": \"shelf\""));
        assertTrue(scoped.contains("\"up\": " + Relation.quote(shelf.pk())),
                "scoped rows hang under the shelf asked for");

        String nothing = CoverageRelationGetAction.moduleFor("article", List.of(""), null);
        assertTrue(nothing.contains("\"rows\": [\n  ]"),
                "a bare parent= is 'nothing selected', which shows nothing");
    }
}
