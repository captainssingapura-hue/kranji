package kranji.studio.articles;

import kranji.reading.content.ParseFinding;
import kranji.reading.model.Cells;
import kranji.studio.articles.GridPlan.Row;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The samples, held to the rules at every width.
 *
 * <p>{@code src/test/resources/samples} is one draft per case — punctuation,
 * line ends, runs, readings, blocks. They are written to be read: point the
 * workbench at that folder with {@code -Dkranji.articles.dir} and they are the
 * drafts. Here they are the fixtures.</p>
 *
 * <h2>Why a sweep rather than expected output</h2>
 *
 * <p>Golden files would pin the arrangement exactly and break on every
 * deliberate change, which is the wrong bargain for something still being
 * designed. What these assert instead are the properties that must hold
 * whatever the arrangement is — and it was the absence of exactly this that let
 * a bug through: {@code GridPlannerTest} already checked that no opening mark
 * ends a row, but its fixture had {@code （甲）} where the bracket opens one
 * square. {@code （Tunnel）} opens three, the run wrapped, and the bracket was
 * left stranded at the margin.</p>
 *
 * <p>Every width from 4 to 24, because the interesting cases are all collisions
 * with the end of a row and which width collides depends on the sentence.</p>
 */
class SamplesTest {

    private static final Path SAMPLES = Path.of("src/test/resources/samples");
    private static final int NARROWEST = 4;
    private static final int WIDEST = 24;

    /**
     * The drafts written to be refused.
     *
     * <p>Named here rather than kept in another folder, because the point of
     * one is to be opened in the workbench and read alongside the error it
     * produces.</p>
     */
    private static final Set<String> REJECTED = Set.of("unwrapped.kmd");

    private static List<Path> all() throws IOException {
        assertTrue(Files.isDirectory(SAMPLES), () -> "no samples at " + SAMPLES.toAbsolutePath());
        try (Stream<Path> files = Files.list(SAMPLES)) {
            List<Path> kmd = files.filter(p -> p.toString().endsWith(".kmd")).sorted().toList();
            assertFalse(kmd.isEmpty(), "the samples folder is empty");
            return kmd;
        }
    }

    /** The drafts that must render. Everything below arranges these. */
    private static List<Path> samples() throws IOException {
        return all().stream()
                .filter(p -> !REJECTED.contains(p.getFileName().toString()))
                .toList();
    }

    private static MdSubsetParser.Parsed parse(Path file) throws IOException {
        return MdSubsetParser.parse(Files.readString(file, StandardCharsets.UTF_8));
    }

    /** What a row says, for a failure message somebody has to read. */
    private static String said(Row r) {
        var sb = new StringBuilder();
        for (Square s : r.squares()) {
            if (s instanceof Square.Zi z)           sb.append(z.zi());
            else if (s instanceof Square.Marker m)  sb.append(m.text());
            else if (s instanceof Square.Punct p)   sb.append(p.hanging() ? "|" : "").append(p.marks());
            else if (s instanceof Square.Run run)   sb.append('[').append(run.text()).append(']');
            else if (s instanceof Square.Cont)      sb.append('_');
            else                                    sb.append('.');
        }
        return sb.toString();
    }

    @Test
    void everySampleIsAcceptedBySubset() throws IOException {
        for (Path file : samples()) {
            var parsed = parse(file);
            assertTrue(parsed.ok(), () -> file.getFileName() + " did not render: " + parsed.errors());
        }
    }

    @Test
    void unwrappedTextIsRefusedAndTheErrorSaysWhatToWrite() throws IOException {
        // Not a warning. A square holds one character, so nobody but the author
        // can say that eight letters are one word - and a document that renders
        // on a guess is a document nobody was asked about.
        var parsed = parse(SAMPLES.resolve("unwrapped.kmd"));

        assertFalse(parsed.ok(), "it is written to be refused");
        String said = String.join(" | ",
                parsed.errors().stream().map(ParseFinding::message).toList());

        // One error per stretch, naming the fix rather than the offence.
        assertTrue(said.contains("‹markdown›"), said);
        assertTrue(said.contains("‹Valve›"), said);
        assertTrue(said.contains("‹1999›"), said);
        assertTrue(said.contains("‹3/5›"), said);
    }

    @Test
    void aMarkNeedsNoDelimitersAndAnOverrideSurvivesTheCheck() throws IOException {
        // The whitelist is Mark, so what needs wrapping and what does not are
        // the same list. An override's reading is markup rather than content
        // and must not be reported as loose Latin.
        for (Path file : samples()) {
            var parsed = parse(file);
            assertTrue(parsed.errors().isEmpty(),
                    () -> file.getFileName() + ": " + parsed.errors());
        }
    }

    @Test
    void theGeneratorRefusesASampleWhoseHeadingsHaveNoIds() throws IOException {
        // blocks.kmd is written with unpinned headings, because the workbench
        // shows them as such. The generator is the other end of that: an
        // address it cannot find is one it will not invent.
        var draft = new ArticleGenerator.Draft("blocks.kmd",
                Files.readString(SAMPLES.resolve("blocks.kmd"), StandardCharsets.UTF_8));
        var result = ArticleGenerator.generate(List.of(draft),
                ArticleGenerator.Options.of("x", "X", "x"));

        assertFalse(result.ok(), "it has headings with no {#slug}");
        assertTrue(result.files().isEmpty(), "and nothing is written while that stands");
        assertTrue(result.problems().stream().anyMatch(p -> p.message().contains("no id")),
                result.problems().toString());
    }

    @Test
    void noRowIsWiderThanThePage() throws IOException {
        for (Path file : samples()) {
            var blocks = parse(file).blocks().orElseThrow();
            for (int columns = NARROWEST; columns <= WIDEST; columns++) {
                GridPlan p = GridPlanner.plan(blocks, columns);
                for (Row r : p.rows()) {
                    int width = columns;
                    assertTrue(r.used() <= width, () -> file.getFileName()
                            + " at " + width + "/row: " + said(r) + " uses " + r.used());
                }
            }
        }
    }

    @Test
    void noMarkEverBeginsARow() throws IOException {
        // 禁则's first half. It should be impossible rather than checked - a
        // mark with no room hangs instead of wrapping - so this is here to
        // catch the day that stops being true.
        for (Path file : samples()) {
            var blocks = parse(file).blocks().orElseThrow();
            for (int columns = NARROWEST; columns <= WIDEST; columns++) {
                for (Row r : GridPlanner.plan(blocks, columns).rows()) {
                    List<Square> inside = inside(r);
                    if (inside.isEmpty()) continue;
                    if (!(inside.get(0) instanceof Square.Punct first)) continue;
                    int width = columns;
                    assertFalse(Cells.CLOSING.contains(first.first()), () -> file.getFileName()
                            + " at " + width + "/row begins with " + first.marks() + ": " + said(r));
                }
            }
        }
    }

    @Test
    void noOpeningMarkEverEndsARow() throws IOException {
        // The half that is not free. An opening mark belongs to what follows,
        // and whether that still fits is not known when the mark is placed -
        // （Tunnel） needs four squares and a run does not shrink - so the row
        // carries it down when it turns out to have been stranded.
        for (Path file : samples()) {
            var blocks = parse(file).blocks().orElseThrow();
            for (int columns = NARROWEST; columns <= WIDEST; columns++) {
                List<Row> rows = GridPlanner.plan(blocks, columns).rows();
                for (int i = 0; i < rows.size(); i++) {
                    Row r = rows.get(i);
                    List<Square> inside = inside(r);
                    if (inside.isEmpty()) continue;
                    // The author's own line ending is not a wrap. A bracket
                    // left open at the end of a paragraph is the author's.
                    if (i + 1 >= rows.size() || rows.get(i + 1).block() != r.block()) continue;
                    if (!(inside.get(inside.size() - 1) instanceof Square.Punct last)) continue;
                    int width = columns;
                    assertFalse(Cells.OPENING.contains(last.first()), () -> file.getFileName()
                            + " at " + width + "/row ends with " + last.marks() + ": " + said(r));
                }
            }
        }
    }

    @Test
    void nothingHangsExceptAtTheVeryEnd() throws IOException {
        // Hanging is a placement past the right edge. One in the middle of a
        // row would mean the arithmetic in Row.used() had stopped describing
        // anything.
        for (Path file : samples()) {
            var blocks = parse(file).blocks().orElseThrow();
            for (int columns = NARROWEST; columns <= WIDEST; columns++) {
                for (Row r : GridPlanner.plan(blocks, columns).rows()) {
                    List<Square> squares = r.squares();
                    boolean hung = false;
                    for (Square s : squares) {
                        if (s.hanging()) { hung = true; continue; }
                        assertFalse(hung, () -> file.getFileName() + ": a square follows one that "
                                + "hangs past the edge: " + said(r));
                    }
                }
            }
        }
    }

    @Test
    void everyCharacterOfTheDraftReachesTheGrid() throws IOException {
        // The one that would catch a rule quietly eating text. Character by
        // character rather than string against string: the arrangement inserts
        // its own hyphens on a cut and drops the spaces around a run, so the
        // two are not equal - but nothing an author wrote may go missing.
        for (Path file : samples()) {
            var blocks = parse(file).blocks().orElseThrow();
            String drawn = drawn(GridPlanner.plan(blocks, 12));

            blocks.stream()
                  .flatMap(b -> b.lines().stream())
                  .flatMap(List::stream)
                  .map(MdDocument.Span::text)
                  .flatMapToInt(String::codePoints)
                  .distinct()
                  .filter(cp -> !Character.isWhitespace(cp))
                  .forEach(cp -> {
                      String ch = new String(Character.toChars(cp));
                      assertTrue(drawn.contains(ch),
                              () -> file.getFileName() + " lost " + ch + " before the grid");
                  });
        }
    }

    private static String drawn(GridPlan plan) {
        var sb = new StringBuilder();
        for (Row r : plan.rows()) {
            for (Square s : r.squares()) {
                if (s instanceof Square.Zi z)          sb.append(z.zi());
                else if (s instanceof Square.Punct p)  sb.append(p.marks());
                else if (s instanceof Square.Run run)  sb.append(run.text());
            }
        }
        return sb.toString();
    }

    private static List<Square> inside(Row r) {
        var out = new ArrayList<Square>();
        for (Square s : r.squares()) if (!s.hanging()) out.add(s);
        return out;
    }
}
