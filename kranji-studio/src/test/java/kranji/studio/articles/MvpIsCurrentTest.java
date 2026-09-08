package kranji.studio.articles;

import io.vertx.core.json.JsonObject;
import kranji.studio.articles.ArticleGenerator.Draft;
import kranji.studio.articles.ArticleGenerator.Emitted;
import kranji.studio.articles.ArticleGenerator.Options;
import kranji.studio.articles.ArticleGenerator.Result;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The committed resources are exactly what the generator produces.
 *
 * <h2>Regenerate and diff, which the seed could not do</h2>
 *
 * <p>{@code SeedIsImmutableTest} hashes what is committed rather than
 * regenerating it, because its generator needs an 8MB Unihan drop that is not
 * tracked — a regenerate-and-diff there would pass by being skipped on every
 * clean checkout. Here the input <i>is</i> tracked: {@code mvp-source.md} sits
 * beside the output it produced, so this can ask the stronger question.</p>
 *
 * <p>What it protects against is the ordinary thing: somebody improves the
 * source and forgets to regenerate, or edits a generated file by hand. Both
 * produce a reader showing something no author wrote, and both are invisible
 * without this.</p>
 */
class MvpIsCurrentTest {

    private static final Path SOURCE = Path.of("src/test/resources/samples/mvp-source.md");
    private static final Path COMMITTED = Path.of("src/main/resources");

    /** Exactly the arguments {@code ArticleGeneratorMain} was given. */
    private static final Options OPTIONS = Options.of(
            "kranji.studio.articles.mvp", "MvpCollections", "kranji/articles/mvp");

    private static Result regenerate() throws IOException {
        String source = Files.readString(SOURCE, StandardCharsets.UTF_8);
        return ArticleGenerator.generate(
                List.of(new Draft(SOURCE.getFileName().toString(), source)), OPTIONS);
    }

    @Test
    void theSourceStillGenerates() throws IOException {
        Result result = regenerate();
        assertTrue(result.ok(), () -> result.problems().toString());
    }

    @Test
    void everyCommittedResourceIsWhatTheGeneratorWouldWrite() throws IOException {
        for (Emitted file : regenerate().files()) {
            // The catalogue is generated but not committed: the library does
            // not carry this document yet, and an unused class checked in is
            // clutter that looks like wiring.
            if (file.path().endsWith(".java")) continue;

            Path committed = COMMITTED.resolve(file.path());
            assertTrue(Files.exists(committed), () -> committed + " was never committed");
            assertEquals(file.content(), Files.readString(committed, StandardCharsets.UTF_8),
                    committed.getFileName() + " is not what the generator produces. If the "
                    + "source changed, regenerate; if this file was hand-edited, do not.");
        }
    }

    @Test
    void nothingIsCommittedThatTheGeneratorDidNotWrite() throws IOException {
        List<String> expected = regenerate().files().stream()
                .map(Emitted::path)
                .filter(p -> !p.endsWith(".java"))
                .map(p -> p.substring(p.lastIndexOf('/') + 1))
                .sorted()
                .toList();

        try (var files = Files.list(COMMITTED.resolve(OPTIONS.resources()))) {
            List<String> actual = files.map(p -> p.getFileName().toString()).sorted().toList();
            assertEquals(expected, actual, "a stale section is still on the classpath");
        }
    }

    // ── What the reader is served ──────────────────────────────────────

    @Test
    void theIndexNamesEverySectionInReadingOrder() {
        var index = new JsonObject(MvpSectionGetAction.index());
        var sections = index.getJsonArray("documents").getJsonObject(0).getJsonArray("sections");

        assertEquals(6, sections.size());
        assertEquals("yu", sections.getJsonObject(0).getString("id"), "the document's own prose");
        assertEquals(1, sections.getJsonObject(0).getInteger("level"));
        assertEquals(3, sections.getJsonObject(3).getInteger("level"), "a ### under a ##");
    }

    @Test
    void theTreeNestsSubsectionsUnderTheirSection() {
        // The shape TreeRenderer draws, and the reason the reader gets arrow
        // keys without anybody writing an arrow key.
        var tree = new JsonObject(MvpSectionGetAction.tree());

        assertEquals("L0", tree.getString("level"));
        assertEquals("雨", tree.getJsonObject("display").getString("label"));

        var children = tree.getJsonArray("children");
        assertEquals(5, children.size(), "the document's prose and four sections");

        var yangZi = children.getJsonObject(2);
        assertEquals("yang-zi", yangZi.getString("segment"));
        assertEquals(1, yangZi.getJsonArray("children").size(), "二之一 sits under it");
        assertEquals("qian-hou",
                yangZi.getJsonArray("children").getJsonObject(0).getString("segment"));
    }

    @Test
    void nothingInTheTreeCountsCharacters() {
        // A count is a fact about the arrangement, not about the thing being
        // chosen. It belongs in the workbench, where it is.
        String tree = MvpSectionGetAction.tree();

        assertTrue(tree.contains("\"badge\":\"\""), tree);
        assertTrue(tree.contains("\"note\":\"\""), tree);
        assertFalse(tree.contains("chars"), tree);
    }

    @Test
    void aSectionComesBackArrangedAndNeverAsMarkdown() {
        // The whole path: a resource holding structure, read and laid into
        // rows. Nothing between the source and here has seen markdown.
        String served = MvpSectionGetAction.section("gu-ren", 20);

        assertTrue(served.contains("\"plan\":"), served);
        assertTrue(served.contains("\"columns\":20"), served);
        assertTrue(served.contains("\"kind\":\"verse\""), "the poem keeps its own lines");
        assertFalse(served.contains("```"), "no fence travels");
        assertFalse(served.contains("##"), "no heading travels");
    }

    @Test
    void anIdTheIndexDoesNotListIsRefused() {
        // A resource path from a query string is a way to read the classpath.
        assertTrue(MvpSectionGetAction.section("../../secret", 20).contains("\"error\""));
        assertTrue(MvpSectionGetAction.section("index", 20).contains("\"error\""));
    }

    @Test
    void aNarrowerPageIsADifferentArrangementOfTheSameSection() {
        String wide = MvpSectionGetAction.section("yang-zi", 20);
        String narrow = MvpSectionGetAction.section("yang-zi", 10);

        assertFalse(wide.equals(narrow), "the arrangement depends on the reader");
        assertTrue(narrow.contains("\"columns\":10"));
    }
}
