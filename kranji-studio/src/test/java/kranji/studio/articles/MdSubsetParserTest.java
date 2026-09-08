package kranji.studio.articles;

import kranji.reading.content.ParseFinding;
import kranji.studio.articles.MdDocument.Block;
import kranji.studio.articles.MdDocument.Span;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The subset, enforced.
 *
 * <p>These are the cases the specification argues about, asserted rather than
 * described. The two that matter most are the ones where the answer is not
 * obvious from the syntax: emphasis means something inside a run and nothing
 * outside one, and a paragraph's line breaks are markdown's rather than the
 * {@code .txt} format's.</p>
 *
 * <p>Asserted against structure, not markup. A test matching a substring of
 * HTML passes for the wrong reasons and fails whenever the markup is
 * restyled.</p>
 */
class MdSubsetParserTest {

    private static MdSubsetParser.Parsed parse(String body) {
        return MdSubsetParser.parse("# 标题\n\n" + body + "\n");
    }

    private static List<Block> blocks(MdSubsetParser.Parsed p) {
        return p.blocks().orElseThrow(() -> new AssertionError("did not render: " + messages(p)));
    }

    /** Blocks of one kind, the title excluded — every fixture has one. */
    private static List<Block> of(MdSubsetParser.Parsed p, String kind) {
        return blocks(p).stream().filter(b -> b.kind().equals(kind)).toList();
    }

    /** What a line says, ignoring how it is divided into spans. */
    private static String said(List<Span> line) {
        return line.stream().map(Span::text).reduce("", String::concat);
    }

    private static String messages(MdSubsetParser.Parsed p) {
        return String.join(" | ", p.findings().stream().map(ParseFinding::message).toList());
    }

    // ── The title ──────────────────────────────────────────────────────

    @Test
    void aDocumentNeedsOneTitle() {
        var none = MdSubsetParser.parse("没有标题。\n");
        assertFalse(none.ok(), "a document with no # should not render");
        assertTrue(messages(none).contains("no # title"), messages(none));

        var two = MdSubsetParser.parse("# 一\n\n# 二\n");
        assertFalse(two.ok());
        assertTrue(messages(two).contains("second # title"), messages(two));
    }

    @Test
    void theTitleIsReportedSeparatelyAndIsAlsoABlock() {
        var p = parse("正文。");
        assertEquals("标题", p.title());
        assertEquals(1, of(p, "title").size());
    }

    // ── The conflict the spec leads with ───────────────────────────────

    @Test
    void linesInAParagraphAreJoinedTheWayMarkdownJoinsThem() {
        // The one place a .txt article changes meaning when it becomes .md.
        var p = parse("第一行，\n第二行。");

        List<Block> paras = of(p, "p");
        assertEquals(1, paras.size(), "two source lines, one paragraph");
        assertEquals("第一行，第二行。", said(paras.get(0).lines().get(0)));
    }

    @Test
    void aVerseFenceKeepsItsLines() {
        var p = parse("```verse\n床前明月光，\n疑是地上霜。\n```");

        Block verse = of(p, "verse").get(0);
        assertEquals(2, verse.lines().size(), "a poem's lines are its own");
        assertEquals("床前明月光，", said(verse.lines().get(0)));
        assertEquals("疑是地上霜。", said(verse.lines().get(1)));
    }

    @Test
    void onlyVerseIsAFence() {
        var p = parse("```java\nSystem.out.println();\n```");

        assertFalse(p.ok());
        assertTrue(messages(p).contains("'java' is not a fence"), messages(p));
        // And the body inside it is not read as markdown on the way past.
        assertFalse(messages(p).contains("backtick"), messages(p));
    }

    @Test
    void anUnclosedVerseFenceIsAnError() {
        var p = parse("```verse\n床前明月光，");
        assertFalse(p.ok());
        assertTrue(messages(p).contains("never closed"), messages(p));
    }

    // ── Emphasis ───────────────────────────────────────────────────────

    @Test
    void emphasisInsideARunIsKept() {
        var p = parse("它不在‹**Premier**›的池里。");

        List<Span> line = of(p, "p").get(0).lines().get(0);
        Span bold = line.stream().filter(s -> s.emphasis().equals("strong")).findFirst().orElseThrow();
        assertEquals("Premier", bold.text());
        assertTrue(bold.inRun());
        assertEquals(List.of(), p.warnings(), "nothing was dropped");
    }

    @Test
    void boldOverChineseIsKept() {
        // A heavier character is the same character in the same square, so
        // nothing about the practice grid objects to it.
        var p = parse("这是**很重要**的。");

        List<Span> line = of(p, "p").get(0).lines().get(0);
        assertEquals("这是很重要的。", said(line), "the words are unchanged");
        Span bold = line.stream().filter(s -> s.emphasis().equals("strong")).findFirst().orElseThrow();
        assertEquals("很重要", bold.text());
        assertEquals(List.of(), p.warnings());
    }

    @Test
    void italicOverChineseIsDroppedWithAWarning() {
        // No CJK face has an italic; a browser fakes one by shearing the glyph
        // out of its square. The 着重号 fits the grid and is too faint to see
        // at reading size, which is worse than not offering it at all.
        var p = parse("这是*重要*的。");

        List<Span> line = of(p, "p").get(0).lines().get(0);
        assertEquals("这是重要的。", said(line), "the words survive");
        assertTrue(line.stream().allMatch(s -> s.emphasis().isEmpty()));
        assertEquals(1, p.warnings().size());
        assertTrue(messages(p).contains("italic over Chinese was dropped"), messages(p));
    }

    @Test
    void insideARunItalicIsAnItalic() {
        // That is Latin, and Latin italicises.
        var p = parse("‹*Mid*›");

        List<Span> line = of(p, "p").get(0).lines().get(0);
        assertTrue(line.stream().anyMatch(s -> s.emphasis().equals("em") && s.inRun()));
        assertEquals(List.of(), p.warnings());
    }

    @Test
    void aCharacterCanCarryBothAReadingAndAWeight() {
        // The reason emphasis stopped being a kind of span. These are two
        // questions about one character, not two things it could be.
        List<Span> line = of(parse("这个**字{zì}**很重要。"), "p").get(0).lines().get(0);

        Span both = line.stream().filter(s -> s.kind().equals("ruby")).findFirst().orElseThrow();
        assertEquals("字", both.text());
        assertEquals("zì", both.reading());
        assertEquals("strong", both.emphasis());
    }

    @Test
    void aStarNothingClosedIsWarnedAboutRatherThanRefused() {
        var p = parse("这是**很重要的。");

        assertTrue(p.ok(), "a typo does not stop the document");
        assertEquals("这是**很重要的。", said(of(p, "p").get(0).lines().get(0)), "left as written");
        assertTrue(messages(p).contains("unpaired *"), messages(p));
    }

    // ── Runs ───────────────────────────────────────────────────────────

    @Test
    void aRunIsMarkedAndItsDelimitersAreNotContent() {
        List<Span> line = of(parse("靠近‹Tunnel›入口。"), "p").get(0).lines().get(0);

        assertTrue(line.stream().anyMatch(s -> s.inRun() && s.text().equals("Tunnel")));
        assertFalse(said(line).contains("‹"), "the delimiters are markup, not content");
        assertEquals("靠近Tunnel入口。", said(line));
    }

    @Test
    void anUnmatchedDelimiterIsAnError() {
        var p = parse("靠近‹Tunnel入口。");
        assertFalse(p.ok());
        assertTrue(messages(p).contains("unmatched"), messages(p));
    }

    @Test
    void punctuationNeedsNoRunAndGetsNone() {
        // You do not wrap a comma. The commas and 。 pass through untouched.
        List<Span> line = of(parse("一，二。"), "p").get(0).lines().get(0);

        assertTrue(line.stream().noneMatch(Span::inRun));
        assertEquals("一，二。", said(line));
    }

    // ── Overrides ──────────────────────────────────────────────────────

    @Test
    void anOverrideBecomesARubySpanCarryingItsReading() {
        List<Span> line = of(parse("读中文的地{dì}方。"), "p").get(0).lines().get(0);

        Span ruby = line.stream().filter(s -> s.kind().equals("ruby")).findFirst().orElseThrow();
        assertEquals("地", ruby.text());
        assertEquals("dì", ruby.reading());
        assertEquals("读中文的地方。", said(line), "the sentence still reads whole");
    }

    // ── Pinned ids ─────────────────────────────────────────────────────

    @Test
    void aPinnedIdIsCarriedOnTheHeading() {
        Block h = of(parse("## 整体结构 {#zheng-ti}"), "heading").get(0);

        assertEquals(2, h.level());
        assertEquals("zheng-ti", h.id());
        assertEquals("整体结构", said(h.lines().get(0)), "the pin is not part of the text");
    }

    @Test
    void anUnpinnedHeadingHasNoId() {
        // Not an error - the id is generated on first write - but visible, so
        // a workbench can show which headings have no address yet.
        assertEquals("", of(parse("## 整体结构"), "heading").get(0).id());
    }

    // ── What is refused ────────────────────────────────────────────────

    @Test
    void theRejectedConstructsAreRejected() {
        assertFalse(parse("| a | b |").ok(), "table");
        assertFalse(parse("<div>x</div>").ok(), "raw HTML");
        assertFalse(parse("* 项目").ok(), "a * bullet");
        assertFalse(parse("看[这里](http://x)。").ok(), "a link");
        assertFalse(parse("![图](x.png)").ok(), "an image");
        assertFalse(parse("这是`code`。").ok(), "a backtick");
        assertFalse(parse("~~删除~~").ok(), "strikethrough");
        assertFalse(parse("#### 太深了").ok(), "a fourth heading level");
        assertFalse(parse("  - 嵌套").ok(), "a nested list");
    }

    @Test
    void aRuleIsDroppedRatherThanRefused() {
        var p = parse("一。\n\n---\n\n二。");

        assertTrue(p.ok(), "a rule does not stop the document");
        assertEquals(1, p.warnings().size());
        assertTrue(messages(p).contains("rule was dropped"), messages(p));
    }

    @Test
    void anErrorMeansNoBlocksAtAll() {
        // The whole point of the subset: a document is not shown with its
        // table quietly missing.
        var p = parse("正文。\n\n| a |\n\n更多正文。");

        assertFalse(p.ok());
        assertTrue(p.blocks().isEmpty(), "nothing renders when something was refused");
        assertFalse(p.errors().isEmpty());
    }

    // ── The shapes that should just work ───────────────────────────────

    @Test
    void listsAndQuotesBecomeTheirOwnBlocks() {
        var p = parse("- 市场\n- 中路大街\n\n1. 第一\n2. 第二\n\n> 说明");

        assertEquals(List.of("市场", "中路大街"),
                of(p, "li").stream().map(b -> said(b.lines().get(0))).toList());
        assertEquals(List.of("第一", "第二"),
                of(p, "oli").stream().map(b -> said(b.lines().get(0))).toList());
        assertEquals("说明", said(of(p, "quote").get(0).lines().get(0)));
    }

    @Test
    void anOrderedItemKeepsTheNumberItsAuthorWrote() {
        // A renderer that counted for itself would silently renumber this to
        // 1, 2, 3 - and a workbench exists to show what the file says.
        var p = parse("3. 第三\n4. 第四");

        assertEquals(List.of(3, 4), of(p, "oli").stream().map(Block::level).toList());
    }

    @Test
    void nothingIsEscapedHereBecauseNothingIsMarkupHere() {
        // The parser hands over text. Whatever draws it decides how to put a
        // '<' on screen safely, which is the point of not returning markup.
        var p = MdSubsetParser.parse("# 标题 & <x>\n");
        assertEquals("标题 & <x>", p.title());
    }
}
