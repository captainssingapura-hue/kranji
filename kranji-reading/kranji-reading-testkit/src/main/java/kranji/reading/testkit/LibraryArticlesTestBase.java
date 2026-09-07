package kranji.reading.testkit;

import kranji.reading.content.Articles;
import kranji.reading.content.ParseFinding;
import kranji.reading.content.ParsedArticle;
import kranji.reading.library.ArticleAddress;
import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.LibraryTree;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Every article of the trees a subclass names actually reads - one test case
 * per article.
 *
 * <h2>The failure this exists to stop</h2>
 *
 * <p>{@link kranji.reading.content.ArticleParser} already finds everything an
 * author can get wrong - a character the corpus cannot read, an override that
 * is not one of that character's readings, a brace left open. The catalogue
 * tests check that a resource <em>exists</em>; whether the text inside it can
 * be served was, before this existed, discovered by a child clicking the title
 * and getting "did not parse". That is the worst shape a content bug can have:
 * invisible to the author, invisible to review, invisible to the build, and it
 * arrives as a blank pane in front of the reader.</p>
 *
 * <h2>Why one case per article</h2>
 *
 * <p>A single method looping over five hundred articles reports "1 test, 1
 * failure" and puts the damage inside an assertion message. Parameterised, the
 * count is the size of the library, a broken poem is named by the failure
 * rather than buried in it, and a run says which shelf is unhealthy at a
 * glance. The case is named by the article's address, so the report reads as a
 * list of addresses rather than of indices.</p>
 *
 * <h2>Why a base class per group rather than one test over everything</h2>
 *
 * <p>A group is the unit of authorship, and it is where an author is working
 * when the check fails. Running it in the group module means the failure
 * arrives from the module being edited, in the second that module takes,
 * rather than from the application at the end of the reactor. The
 * application-level subclass is still worth having and is not a duplicate: it
 * is the only place where every root on one classpath is asked at once, which
 * is the arrangement a reader actually gets.</p>
 *
 * <h2>Errors gate; warnings are reported</h2>
 *
 * <p>An error means the article cannot be served, so it fails the build.</p>
 *
 * <p>A warning means a judgement was made on the author's behalf - nearly
 * always "this character has more than one reading and I used the principal".
 * There are thousands, because the commonest characters are polyphonic and
 * appear in every sentence ever written. Gating on them would mean overriding
 * the corpus into agreeing with itself, so they are written to
 * {@link #report()} instead, where an author can find their own article and
 * check the readings that were guessed for it.</p>
 *
 * <h2>Extending it</h2>
 *
 * <pre>
 * class ArticlesTest extends LibraryArticlesTestBase {
 *     protected List&lt;LibraryTree&gt; trees() { return List.of(ShiCiLibrary.TREE); }
 * }
 * </pre>
 *
 * <p>The members here are {@code protected} rather than package-private on
 * purpose: a subclass lives in its own group's package, and JUnit does not
 * inherit a package-private method across a package boundary. It would find no
 * tests and report success, which is the one failure mode a test framework
 * must not have.</p>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class LibraryArticlesTestBase {

    /** One article, parsed. Prints as its address, which is what names the case. */
    public record Checked(String address, String resource, ParsedArticle parsed) {
        @Override public String toString() { return address; }
    }

    /** Read once per class. {@code PER_CLASS} is what lets this be an instance field. */
    private List<Checked> read;

    /** The trees this test speaks for. Usually one, and usually the group's own. */
    protected abstract List<LibraryTree> trees();

    /**
     * The floor under {@link #theseTreesHaveArticles()}.
     *
     * <p>Guards the case where the classpath is wrong and a tree loads empty:
     * without it, zero articles is zero failures, which reads as success. A
     * group knows roughly how much it holds; the default insists only on more
     * than nothing.</p>
     */
    protected int fewestArticlesExpected() { return 1; }

    /** Where the warnings are left for an author to read. Relative to the module. */
    protected Path report() { return Path.of("target", "article-warnings.txt"); }

    /** Every article of every named tree, parsed. */
    protected final List<Checked> articles() {
        if (read != null) return read;
        var out = new ArrayList<Checked>();
        for (LibraryTree tree : trees()) {
            for (ArticleCollection c : tree.collections()) {
                for (ArticleRef ref : c.articles()) {
                    ArticleAddress address = c.address(ref.id());
                    Optional<ParsedArticle> parsed = Articles.read(address, ref);
                    assertTrue(parsed.isPresent(),
                            address + " has no text at " + ref.resource());
                    out.add(new Checked(address.toString(), ref.resource(), parsed.get()));
                }
            }
        }
        read = List.copyOf(out);
        return read;
    }

    /** The parameter source. Non-static, which {@code PER_CLASS} permits. */
    protected final Stream<Checked> everyArticle() { return articles().stream(); }

    @ParameterizedTest(name = "{0}")
    @MethodSource("everyArticle")
    protected void canBeServed(Checked article) {
        var broken = new ArrayList<String>();
        for (ParseFinding f : article.parsed().findings()) {
            if (f.severity() == ParseFinding.Severity.ERROR) {
                broken.add("line " + f.line() + ": " + f.message());
            }
        }
        // Belt and braces: servable() is what the action asks, and an article
        // can also fail to build for a reason that is not a finding - an empty
        // body reaches here as one.
        if (article.parsed().article().isEmpty() && article.parsed().findings().isEmpty()) {
            broken.add("did not parse, and said nothing about why");
        }
        assertEquals(List.of(), broken,
                article.resource() + " is one a reader would be shown an error for");
    }

    @Test
    protected void theseTreesHaveArticles() {
        assertTrue(articles().size() >= fewestArticlesExpected(),
                "expected at least " + fewestArticlesExpected() + " articles, found "
              + articles().size() + " - the library did not load");
    }

    @Test
    protected void theReadingsGuessedForAnAuthorAreWrittenDown() throws IOException {
        List<Checked> all = articles();

        var lines = new ArrayList<String>();
        int warned = 0;
        for (Checked ch : all) {
            var mine = ch.parsed().findings().stream()
                    .filter(f -> f.severity() == ParseFinding.Severity.WARNING)
                    .toList();
            if (mine.isEmpty()) continue;
            warned++;
            lines.add(ch.address() + "  (" + ch.resource() + ")");
            for (ParseFinding f : mine) lines.add("    line " + f.line() + ": " + f.message());
            lines.add("");
        }

        Path report = report();
        Files.createDirectories(report.toAbsolutePath().getParent());
        Files.writeString(report,
                "Readings chosen without an override, for the author to check.\n"
              + "An article listed here is not wrong; it is unreviewed.\n"
              + warned + " of " + all.size() + " articles.\n\n"
              + String.join("\n", lines),
                StandardCharsets.UTF_8);

        // No assertion on the count. This test exists to produce the file; it
        // fails only if writing it fails, which is the one outcome that would
        // leave an author with nothing to check.
        assertTrue(Files.exists(report), "no warning report at " + report.toAbsolutePath());
    }
}
