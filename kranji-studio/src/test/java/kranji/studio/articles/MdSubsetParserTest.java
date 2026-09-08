package kranji.studio.articles;

import kranji.reading.content.ParseFinding;
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
 */
class MdSubsetParserTest {

    private static MdSubsetParser.Parsed parse(String body) {
        return MdSubsetParser.parse("# 标题\n\n" + body + "\n");
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
    void theTitleIsReportedSeparately() {
        assertEquals("标题", parse("正文。").title());
    }

    // ── The conflict the spec leads with ───────────────────────────────

    @Test
    void linesInAParagraphAreJoinedTheWayMarkdownJoinsThem() {
        // The one place a .txt article changes meaning when it becomes .md.
        var p = parse("第一行，\n第二行。");

        assertTrue(p.ok(), messages(p));
        assertTrue(p.html().orElseThrow().contains("<p>第一行，第二行。</p>"), p.html().orElseThrow());
    }

    @Test
    void aVerseFenceKeepsItsLines() {
        var p = parse("```verse\n床前明月光，\n疑是地上霜。\n```");

        String html = p.html().orElseThrow();
        assertTrue(html.contains("<div class=\"kw-verse\">"), html);
        assertTrue(html.contains("<div>床前明月光，</div>"), html);
        assertTrue(html.contains("<div>疑是地上霜。</div>"), html);
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

    // ── Emphasis depends on where it is ────────────────────────────────

    @Test
    void emphasisInsideARunIsKept() {
        var p = parse("它不在‹**Premier**›的池里。");

        String html = p.html().orElseThrow();
        assertTrue(html.contains("<span class=\"kw-run\"><strong>Premier</strong></span>"), html);
        assertEquals(List.of(), p.warnings(), "nothing was dropped");
    }

    @Test
    void emphasisOverChineseIsDroppedWithAWarning() {
        var p = parse("这是**很重要**的。");

        String html = p.html().orElseThrow();
        assertTrue(p.ok(), "it still renders");
        assertTrue(html.contains("这是很重要的。"), html);
        assertFalse(html.contains("<strong>"), "emphasis over Chinese means nothing in a square");
        assertEquals(1, p.warnings().size());
        assertTrue(messages(p).contains("cannot be drawn in a practice square"), messages(p));
    }

    @Test
    void italicFollowsTheSameRule() {
        assertTrue(parse("这是*重要*的。").warnings().size() == 1);
        assertTrue(parse("‹*Mid*›").html().orElseThrow().contains("<em>Mid</em>"));
    }

    // ── Runs ───────────────────────────────────────────────────────────

    @Test
    void aRunIsMarkedAndItsDelimitersDoNotSurvive() {
        String html = parse("靠近‹Tunnel›入口。").html().orElseThrow();

        assertTrue(html.contains("<span class=\"kw-run\">Tunnel</span>"), html);
        assertFalse(html.contains("‹"), "the delimiters are markup, not content");
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
        var p = parse("一，二。");
        assertTrue(p.ok(), messages(p));
        assertFalse(p.html().orElseThrow().contains("kw-run"), p.html().orElseThrow());
    }

    // ── Overrides ──────────────────────────────────────────────────────

    @Test
    void anOverrideIsShownAsRuby() {
        String html = parse("读中文的地{dì}方。").html().orElseThrow();
        assertTrue(html.contains("<ruby>地<rt>dì</rt></ruby>"), html);
    }

    // ── Pinned ids ─────────────────────────────────────────────────────

    @Test
    void aPinnedIdBecomesTheHeadingsId() {
        String html = parse("## 整体结构 {#zheng-ti}").html().orElseThrow();

        assertTrue(html.contains("<h2 id=\"zheng-ti\">整体结构</h2>"), html);
    }

    @Test
    void anUnpinnedHeadingIsMarkedAsSuch() {
        // Not an error - the id is generated on first write - but visible, so
        // the workbench can show which headings have no address yet.
        String html = parse("## 整体结构").html().orElseThrow();
        assertTrue(html.contains("kw-unpinned"), html);
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
    void anErrorMeansNoHtmlAtAll() {
        // The whole point of the subset: a document is not served with its
        // table quietly missing.
        var p = parse("正文。\n\n| a |\n\n更多正文。");

        assertFalse(p.ok());
        assertTrue(p.html().isEmpty(), "nothing renders when something was refused");
        assertFalse(p.errors().isEmpty());
    }

    // ── The shapes that should just work ───────────────────────────────

    @Test
    void listsAndQuotesRender() {
        String html = parse("- 市场\n- 中路大街\n\n1. 第一\n2. 第二\n\n> 说明").html().orElseThrow();

        assertTrue(html.contains("<ul>\n<li>市场</li>\n<li>中路大街</li>\n</ul>"), html);
        assertTrue(html.contains("<ol>\n<li>第一</li>"), html);
        assertTrue(html.contains("<blockquote>说明</blockquote>"), html);
    }

    @Test
    void htmlIsEscaped() {
        // The source is a file from disk; it does not get to write markup.
        var p = MdSubsetParser.parse("# 标题 & <x>\n");
        assertTrue(p.html().orElseThrow().contains("&amp;"), p.html().orElseThrow());
    }
}
