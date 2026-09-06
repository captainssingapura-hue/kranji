package kranji.reading.app.read;

import kranji.reading.content.Articles;
import kranji.reading.content.ParseFinding;
import kranji.reading.content.ParsedArticle;
import kranji.reading.library.ArticleAddress;
import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleLibrary;
import kranji.reading.library.ArticleRef;
import kranji.reading.library.Libraries;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Every article in every library on the classpath actually reads.
 *
 * <h2>The failure this exists to stop</h2>
 *
 * <p>{@link kranji.reading.content.ArticleParser} already finds everything an
 * author can get wrong — a character the corpus cannot read, an override that
 * is not one of that character's readings, a brace left open. Nothing ran it
 * over the library. The catalogue tests check that a resource <em>exists</em>;
 * whether the text inside it can be served was discovered by a child clicking
 * the title and getting "did not parse".</p>
 *
 * <p>That is the worst shape a content bug can have. It is invisible to the
 * author, invisible to review, invisible to the build, and it arrives as a
 * blank pane in front of the reader. This is the module where both libraries
 * are on one classpath, so it is where the question can be asked of all of
 * them at once.</p>
 *
 * <h2>Errors gate; warnings are reported</h2>
 *
 * <p>An error means the article cannot be served, so it fails the build.</p>
 *
 * <p>A warning means a judgement was made on the author's behalf — nearly
 * always "this character has more than one reading and I used the principal".
 * There are thousands, because 的 and 不 and 一 are polyphonic and appear in
 * every sentence ever written. Gating on them would mean overriding the
 * corpus into agreeing with itself, so they are written to
 * {@code target/article-warnings.txt} instead, where an author can find their
 * own article and check the readings that were guessed for it.</p>
 */
class LibraryArticlesTest {

    /** Where the warnings are left for an author to read. */
    private static final Path REPORT = Path.of("target", "article-warnings.txt");

    private record Checked(String address, String resource, ParsedArticle parsed) {}

    /** Every article of every discovered root, parsed. */
    private static List<Checked> readAll() {
        var out = new ArrayList<Checked>();
        for (ArticleLibrary library : Libraries.discovered()) {
            for (ArticleCollection c : library.tree().collections()) {
                for (ArticleRef ref : c.articles()) {
                    ArticleAddress address = c.address(ref.id());
                    Optional<ParsedArticle> parsed = Articles.read(address, ref);
                    assertTrue(parsed.isPresent(),
                            address + " has no text at " + ref.resource());
                    out.add(new Checked(address.toString(), ref.resource(), parsed.get()));
                }
            }
        }
        return out;
    }

    @Test
    void everyArticleCanBeServed() {
        List<Checked> all = readAll();
        assertTrue(all.size() > 100, "the libraries did not load: " + all.size() + " articles");

        var broken = new ArrayList<String>();
        for (Checked ch : all) {
            for (ParseFinding f : ch.parsed().findings()) {
                if (f.severity() == ParseFinding.Severity.ERROR) {
                    broken.add(ch.resource() + " line " + f.line() + ": " + f.message());
                }
            }
            // Belt and braces: servable() is what the action asks, and an
            // article can also fail to build for a reason that is not a
            // finding - an empty body reaches here as one.
            if (ch.parsed().article().isEmpty() && ch.parsed().findings().isEmpty()) {
                broken.add(ch.resource() + ": did not parse, and said nothing about why");
            }
        }
        assertEquals(List.of(), broken,
                broken.size() + " article(s) a reader would be shown an error for");
    }

    @Test
    void theReadingsGuessedForAnAuthorAreWrittenDown() throws IOException {
        List<Checked> all = readAll();

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

        Files.createDirectories(REPORT.getParent());
        Files.writeString(REPORT,
                "Readings chosen without an override, for the author to check.\n"
              + "An article listed here is not wrong; it is unreviewed.\n"
              + warned + " of " + all.size() + " articles.\n\n"
              + String.join("\n", lines),
                StandardCharsets.UTF_8);

        // No assertion on the count. This test exists to produce the file; it
        // fails only if writing it fails, which is the one outcome that would
        // leave an author with nothing to check.
        assertTrue(Files.exists(REPORT), "no warning report at " + REPORT.toAbsolutePath());
    }
}
