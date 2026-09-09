package kranji.reading.app.read;

import hue.captains.singapura.js.homing.ssjs.test.JsModuleTestBase;
import kranji.reading.content.Articles;
import kranji.reading.content.DemoLibrary;
import kranji.reading.model.Article;
import kranji.reading.library.ArticleCollection;
import kranji.reading.library.ArticleRef;
import kranji.reading.model.Block;
import kranji.reading.model.Cell;
import kranji.reading.model.Cells;
import kranji.reading.model.Token;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The cell rule as the browser applies it.
 *
 * <p>The rule moved to JavaScript when the wire stopped carrying pre-assembled
 * cells, so it is tested the way the Java was: on GraalVM, under plain JUnit,
 * in the same build. {@link #agreesWithTheJavaItWasPortedFrom} is the one that
 * matters — it reads both implementations over the real articles rather than
 * over cases I thought to invent.</p>
 */
class ArticleScannerTest extends JsModuleTestBase {

    private static final String MODULE =
            "/homing/js/kranji/reading/app/read/ArticleScannerModule.js";

    /**
     * The scanner's one import: it canonicalises an authored reading, because
     * the wire carries the file as written and {@code dì} has to become
     * {@code di4} before anything below the display layer sees it.
     *
     * <p>Loaded by hand here because this harness resolves nothing — in the
     * browser the module manifest does it. Mirroring that is the price of
     * testing the module rather than a copy of it.</p>
     */
    private static final String SWF =
            "/homing/js/kranji/reading/app/ui/PinyinSwfModule.js";

    private Value scanner;

    @BeforeEach
    void load() {
        loadModule(SWF);
        loadModule(MODULE);
        scanner = global("createArticleScanner").execute();
    }

    /** The cells for one line, as the module returns them. */
    private List<Value> scan(String line) {
        Value out = scanner.getMember("scan").execute(line);
        var cells = new ArrayList<Value>();
        for (long i = 0; i < out.getArraySize(); i++) cells.add(out.getArrayElement(i));
        return cells;
    }

    private static String member(Value cell, String name) {
        Value v = cell.getMember(name);
        return v == null || v.isNull() ? null : v.asString();
    }

    // ── The tables cannot drift from the model ─────────────────────────

    @Test
    void carriesTheSamePunctuationTablesAsTheModel() {
        // The port duplicates the rule; it must not also duplicate the data
        // silently. If Cells gains a mark and the module does not, this fails.
        assertEquals(Cells.CLOSING, scanner.getMember("closing").asString(),
                "closing marks must match kranji.reading.model.Cells");
        assertEquals(Cells.OPENING, scanner.getMember("opening").asString(),
                "opening marks must match kranji.reading.model.Cells");
    }

    // ── The rule ───────────────────────────────────────────────────────

    @Test
    void givesEachCharacterItsOwnSquare() {
        List<Value> cells = scan("床前明月光");
        assertEquals(5, cells.size());
        assertEquals("床", member(cells.get(0), "z"));
        assertEquals("光", member(cells.get(4), "z"));
    }

    @Test
    void aClosingMarkJoinsTheCharacterBeforeIt() {
        List<Value> cells = scan("光，");
        assertEquals(1, cells.size(), "the mark shares the square, it does not take one");
        assertEquals("光", member(cells.get(0), "z"));
        assertEquals("，", member(cells.get(0), "p"));
    }

    @Test
    void anOpeningMarkJoinsTheCharacterAfterIt() {
        List<Value> cells = scan("「床」");
        assertEquals(1, cells.size());
        assertEquals("「", member(cells.get(0), "lp"));
        assertEquals("床", member(cells.get(0), "z"));
        assertEquals("」", member(cells.get(0), "p"));
    }

    @Test
    void aClosingMarkWithNothingToJoinKeepsItsOwnCell() {
        // Rather than being dropped. A line may not begin with one, but if the
        // source does it the reader should show what was written.
        List<Value> cells = scan("。床");
        assertEquals(2, cells.size());
        assertEquals("。", member(cells.get(0), "t"));
        assertEquals("床", member(cells.get(1), "z"));
    }

    // ── Authored readings ──────────────────────────────────────────────

    @Test
    void parsesAnAuthoredReadingAndMarksIt() {
        // 地{dì} - the one reading the corpus cannot supply, because the
        // article is choosing against the principal.
        List<Value> cells = scan("地{dì}上");
        assertEquals(2, cells.size(), "the braces are syntax, not a cell");
        assertEquals("地", member(cells.get(0), "z"));
        assertEquals("di4", member(cells.get(0), "r"),
                "canonicalised on the way in: the file says dì, the system says di4");
        assertTrue(cells.get(0).getMember("o").asBoolean());
        assertEquals("上", member(cells.get(1), "z"));
        assertFalse(cells.get(1).hasMember("r"),
                "an unannotated character carries no reading - the map has it");
    }

    @Test
    void anAuthoredReadingSurvivesTrailingPunctuation() {
        List<Value> cells = scan("地{dì}。");
        assertEquals(1, cells.size());
        assertEquals("di4", member(cells.get(0), "r"),
                "canonicalised on the way in: the file says dì, the system says di4");
        assertEquals("。", member(cells.get(0), "p"));
    }

    // ── The bug a hand-rolled scanner ships with ───────────────────────

    @Test
    void treatsACharacterOutsideTheBasicPlaneAsOneCell() {
        // U+2CE93 is in the corpus range. Iterating UTF-16 units would split it
        // into two halves of a surrogate pair and render two empty squares.
        String astral = new String(Character.toChars(0x2CE93));
        assertEquals(2, astral.length(), "precondition: this is a surrogate pair in Java");

        List<Value> cells = scan(astral + "。");
        assertEquals(1, cells.size(), "one character is one square");
        assertEquals(astral, member(cells.get(0), "z"));
        assertEquals("。", member(cells.get(0), "p"));
    }

    // ── Parity with the model ──────────────────────────────────────────

    @Test
    void agreesWithTheJavaItWasPortedFrom() {
        int linesChecked = 0;
        for (ArticleCollection collection : DemoLibrary.INSTANCE.tree().collections()) {
          for (ArticleRef ref : collection.articles()) {
            Article article = Articles.read(collection.address(ref.id()), ref)
                                      .orElseThrow().orThrow();
            for (Block block : article.blocks()) {
                for (List<Token> tokens : linesOf(block)) {
                    List<Cell> java = Cells.of(tokens);
                    String source = render(java);
                    assertEquals(describe(java), describeJs(scan(source)),
                            () -> "the scanner disagrees with Cells on: " + source);
                    linesChecked++;
                }
            }
          }
        }
        assertTrue(linesChecked >= 40,
                "expected the bundled articles to contribute lines, got " + linesChecked);
    }

    @Test
    void everyReadingIsCanonicalWhenTheFileIsScannedAsWritten() {
        // The check that lets the wire carry the file unchanged.
        //
        // It used to be the server's: /article parsed the source and rebuilt
        // each line from tokens, and the rebuild wrote the canonical form. On
        // the bundled articles that rewrote 26 lines in 88 - every one an
        // authored reading, dì becoming di4. Send the file instead and the
        // diacritic arrives at a cell, which is exactly where PinyinSwfModule
        // says it must never be: below the display layer a reading is a key,
        // in IndexedDB and in a Java registry, and two spellings are two keys.
        //
        // So the scanner canonicalises, and this is what says it did. Scanned
        // from the SOURCE rather than from a reconstruction of it, because a
        // reconstruction is the thing being removed.
        Value swf = global("createPinyinSwf").execute();
        int authored = 0;

        for (ArticleCollection collection : DemoLibrary.INSTANCE.tree().collections()) {
            for (ArticleRef ref : collection.articles()) {
                String source = Articles.sourceOf(ref).orElseThrow();
                for (String line : source.split("\\R")) {
                    if (line.isBlank()) continue;
                    for (Value cell : scan(line.strip())) {
                        String reading = member(cell, "r");
                        if (reading == null) continue;
                        authored++;
                        assertTrue(swf.getMember("isCanonical").execute(reading).asBoolean(),
                                () -> "'" + reading + "' reached a cell uncanonicalised, "
                                    + "from: " + line.strip());
                    }
                }
            }
        }
        assertTrue(authored >= 20, "the bundled articles should carry authored readings, "
                + "or this passes by finding none: " + authored);
    }

    private static List<List<Token>> linesOf(Block block) {
        return switch (block) {
            case Block.Paragraph p -> List.of(p.tokens());
            case Block.Verse v -> v.lines();
            case Block.Illustration i -> List.of(i.caption());
        };
    }

    /** The source line these cells were written from. */
    private static String render(List<Cell> cells) {
        var sb = new StringBuilder();
        for (Cell cell : cells) {
            switch (cell) {
                case Cell.Char c -> sb.append(zi(c.character()));
                case Cell.CharWithPunctuation c ->
                        sb.append(c.leading()).append(zi(c.character())).append(c.trailing());
                case Cell.Plain p -> sb.append(p.text());
            }
        }
        return sb.toString();
    }

    private static String zi(Token.Zi zi) {
        return zi.authored()
                ? zi.zi().value() + "{" + zi.reading().toDiacritic() + "}"
                : zi.zi().value();
    }

    /** A comparable rendering of either side's cells. */
    private static String describe(List<Cell> cells) {
        var out = new ArrayList<String>();
        for (Cell cell : cells) {
            out.add(switch (cell) {
                case Cell.Char c -> cellText("", c.character(), "");
                case Cell.CharWithPunctuation c ->
                        cellText(c.leading(), c.character(), c.trailing());
                case Cell.Plain p -> "t=" + p.text();
            });
        }
        return String.join("|", out);
    }

    private static String describeJs(List<Value> cells) {
        var out = new ArrayList<String>();
        for (Value cell : cells) {
            String z = member(cell, "z");
            if (z == null) {
                out.add("t=" + member(cell, "t"));
                continue;
            }
            String reading = member(cell, "r");
            String lead = member(cell, "lp");
            String trail = member(cell, "p");
            out.add("lp=" + (lead == null ? "" : lead)
                  + " z=" + z
                  + " r=" + (reading == null ? "" : reading)
                  + " p=" + (trail == null ? "" : trail));
        }
        return String.join("|", out);
    }

    private static String cellText(String leading, Token.Zi zi, String trailing) {
        // Only an authored reading is compared - the rest the map supplies, and
        // the scanner is not expected to know them.
        return "lp=" + leading
             + " z=" + zi.zi().value()
             + " r=" + (zi.authored() ? zi.reading().numbered() : "")
             + " p=" + trailing;
    }
}
