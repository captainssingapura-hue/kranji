package kranji.studio.articles;

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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The generator.
 *
 * <p>Two questions, and the second is the one that matters: does it refuse the
 * right things, and is what it emits actually loadable? A generator that
 * produces plausible files nobody compiles is a generator that is wrong in the
 * next commit.</p>
 */
class ArticleGeneratorTest {

    private static final Options OPTIONS =
            Options.of("kranji.library.generated", "GeneratedCollections",
                       "kranji/articles/generated");

    private static Result generate(String... sources) {
        var drafts = new java.util.ArrayList<Draft>();
        for (int i = 0; i < sources.length; i++) {
            drafts.add(new Draft("draft" + (i + 1) + ".md", sources[i]));
        }
        return ArticleGenerator.generate(drafts, OPTIONS);
    }

    private static String file(Result result, String endingWith) {
        return result.files().stream()
                .filter(f -> f.path().endsWith(endingWith))
                .map(Emitted::content)
                .findFirst()
                .orElseThrow(() -> new AssertionError("no " + endingWith + " in "
                        + result.files().stream().map(Emitted::path).toList()));
    }

    private static final String GOOD = """
            # 长文档 {#chang}

            开头一段。

            ## 一、背景 {#bei-jing}

            背景一段。

            ## 二、结构 {#jie-gou}

            结构一段。

            ### 二之一 {#xi-jie}

            细节一段。
            """;

    // ── What it refuses ────────────────────────────────────────────────

    @Test
    void aDraftTheSubsetRefusesStopsTheWholeRun() {
        // Nothing is written while a problem stands. A half-generated library
        // is worse than none, because the half that generated looks finished.
        Result result = generate(GOOD, "# 标题 {#t}\n\n会像 markdown 那样。\n");

        assertFalse(result.ok());
        assertTrue(result.files().isEmpty(), "not even the draft that was fine");
        assertTrue(result.problems().stream().anyMatch(p -> p.message().contains("‹markdown›")),
                result.problems().toString());
    }

    @Test
    void aSectionWithNoIdIsRefusedByName() {
        // The whole of the id policy. Deriving one from the heading would
        // re-address the library every time somebody improved a title.
        Result result = generate("# 标题 {#t}\n\n开头。\n\n## 一、背景\n\n背景一段。\n");

        assertFalse(result.ok());
        String said = result.problems().toString();
        assertTrue(said.contains("一、背景"), said);
        assertTrue(said.contains("{#some-slug}"), said);
    }

    @Test
    void aHeadingWithNoProseNeedsNoIdBecauseNobodyOpensIt() {
        // 「## 四」holding only 「###」 children is a heading in the document,
        // not a thing a reader opens.
        Result result = generate("""
                # 标题 {#t}

                开头。

                ## 四

                ### 四之一 {#si-zhi-yi}

                甲。
                """);

        assertTrue(result.ok(), result.problems().toString());
    }

    // ── What it emits ──────────────────────────────────────────────────

    @Test
    void oneResourcePerSectionAndOneCatalogue() {
        Result result = generate(GOOD);
        assertTrue(result.ok(), result.problems().toString());

        List<String> paths = result.files().stream().map(Emitted::path).toList();
        assertEquals(List.of(
                "kranji/articles/generated/chang.json",
                "kranji/articles/generated/bei-jing.json",
                "kranji/articles/generated/jie-gou.json",
                "kranji/articles/generated/xi-jie.json",
                "kranji/library/generated/GeneratedCollections.java"), paths);
    }

    @Test
    void aBodyIsTheParsedDocumentRatherThanItsMarkdown() {
        // The point of generating at all. Nothing parses markdown at request
        // time, and nothing can: there is none left.
        String body = file(generate(GOOD), "bei-jing.json");

        assertTrue(body.contains("\"blocks\":"), body);
        assertTrue(body.contains("\"kind\":\"p\""), body);
        assertTrue(body.contains("\"t\":\"背景一段。\""), body);
        assertFalse(body.contains("##"), "the markdown does not travel");
    }

    @Test
    void theCatalogueNamesEverySectionAndItsResource() {
        String java = file(generate(GOOD), ".java");

        assertTrue(java.contains("package kranji.library.generated;"), java);
        assertTrue(java.contains("ArticleRef.of(\"bei-jing\", \"一、背景\", "
                + "\"/kranji/articles/generated/bei-jing.json\")"), java);
        assertTrue(java.contains("public static List<ArticleCollection> all()"), java);
    }

    @Test
    void theDocumentsOwnProseIsASectionToo() {
        // Everything before the first heading. It is what a reader meets first
        // and it would otherwise be dropped.
        Result result = generate(GOOD);

        assertTrue(file(result, "chang.json").contains("开头一段。"));
    }

    @Test
    void twoRunsOverTheSameDraftsProduceTheSameBytes() {
        // A generator whose output depends on a hash iteration order produces
        // a diff every time it runs, and nobody can then tell a real change
        // from noise.
        assertEquals(generate(GOOD).files(), generate(GOOD).files());
    }

    // ── It has to compile ──────────────────────────────────────────────

    @Test
    void theGeneratedCatalogueCompilesAgainstTheRealTypes() throws IOException {
        // The question a string assertion cannot answer. ArticleCollection and
        // ArticleRef are the product's, and the generator writes against them
        // from a distance.
        Result result = generate(GOOD);
        Path dir = Files.createTempDirectory("generated");
        Path java = dir.resolve("GeneratedCollections.java");
        Files.writeString(java, file(result, ".java"), StandardCharsets.UTF_8);

        var compiler = javax.tools.ToolProvider.getSystemJavaCompiler();
        if (compiler == null) return;   // a JRE-only run has nothing to say here

        int status = compiler.run(null, null, null,
                "-classpath", System.getProperty("java.class.path"),
                "-d", dir.toString(),
                java.toString());
        assertEquals(0, status, "the generated catalogue did not compile");

        try (Stream<Path> files = Files.walk(dir)) {
            assertTrue(files.anyMatch(p -> p.toString().endsWith("GeneratedCollections.class")));
        }
    }
}
