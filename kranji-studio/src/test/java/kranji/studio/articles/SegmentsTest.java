package kranji.studio.articles;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The cut.
 *
 * <p>Two things are being asserted and they are separable on purpose:
 * <b>headings</b> give the tree its shape, and the <b>budget</b> is a policy on
 * top that can be turned off without the tree stopping being one.</p>
 */
class SegmentsTest {

    private static Segment tree(String body, int budget) {
        var parsed = MdSubsetParser.parse("# 标题\n\n" + body + "\n");
        assertTrue(parsed.ok(), () -> "did not parse: " + parsed.findings());
        return Segments.of(parsed.blocks().orElseThrow(), budget);
    }

    private static Segment tree(String body) {
        return tree(body, Segments.NO_BUDGET);
    }

    /** The shape, as paths and titles — the readable form of a tree. */
    private static List<String> shape(Segment root) {
        return root.walk().stream()
                .map(s -> (s.path().isEmpty() ? "/" : s.path())
                        + " " + (s.part() ? "(part)" : s.title()))
                .toList();
    }

    // ── Headings give the shape ────────────────────────────────────────

    @Test
    void theDocumentIsTheRootAndItsTitleIsItsHeading() {
        Segment root = tree("正文。");

        assertEquals("标题", root.title());
        assertEquals(1, root.level());
        assertEquals("", root.path(), "the document is where a path starts, not a step in one");
        assertEquals(1, root.blocks().size(), "prose before any heading belongs to the document");
    }

    @Test
    void aSectionIsAChildAndASubsectionIsAChildOfThat() {
        Segment root = tree("## 一\n\n甲。\n\n### 一之一\n\n乙。\n\n## 二\n\n丙。");

        assertEquals(List.of("/ 标题", "1 一", "1.1 一之一", "2 二"), shape(root));
        assertEquals("甲。", said(root.children().get(0)));
        assertEquals("乙。", said(root.children().get(0).children().get(0)));
    }

    @Test
    void aSubsectionBeforeAnySectionHangsOffTheDocument() {
        // The subset allows it, so it must land somewhere rather than be lost.
        Segment root = tree("### 小标题\n\n甲。");

        assertEquals(List.of("/ 标题", "1 小标题"), shape(root));
        assertEquals(3, root.children().get(0).level());
    }

    @Test
    void aHeadingWithNoProseIsStillANode() {
        // 「## 四、关键区域」in the sample holds only ### children.
        Segment root = tree("## 四\n\n### 四之一\n\n甲。");

        Segment section = root.children().get(0);
        assertTrue(section.blocks().isEmpty());
        assertEquals(1, section.children().size());
        assertEquals("甲。", said(section.children().get(0)));
    }

    @Test
    void theDocumentKeepsItsOwnAddress() {
        // A whole document is a thing somebody links to, and its pin arrives
        // as a block after the node it belongs to has had to exist.
        var parsed = MdSubsetParser.parse("# 标题 {#biao-ti}\n\n正文。\n");
        Segment root = Segments.of(parsed.blocks().orElseThrow(), Segments.NO_BUDGET);

        assertEquals("biao-ti", root.id());
        assertTrue(root.pinned());
    }

    @Test
    void aPinnedIdIsTheSegmentsAddressAndAnUnpinnedOneHasNone() {
        Segment root = tree("## 一 {#yi}\n\n甲。\n\n## 二\n\n乙。");

        assertEquals("yi", root.children().get(0).id());
        assertTrue(root.children().get(0).pinned());
        assertEquals("", root.children().get(1).id(), "nothing invents an address");
        assertFalse(root.children().get(1).pinned());
    }

    // ── The budget is a policy on top ──────────────────────────────────

    @Test
    void aSectionUnderBudgetIsNotCut() {
        Segment root = tree("## 一\n\n甲乙丙。", 70);

        Segment section = root.children().get(0);
        assertTrue(section.children().isEmpty());
        assertFalse(section.blocks().isEmpty(), "it keeps its own prose");
    }

    @Test
    void aSectionOverBudgetBecomesPartsAtParagraphBoundaries() {
        // Three paragraphs of ten characters, budget fifteen: they cannot all
        // sit together and none of them may be cut open.
        Segment root = tree("## 一\n\n一二三四五六七八九十。\n\n"
                          + "一二三四五六七八九十。\n\n一二三四五六七八九十。", 15);

        Segment section = root.children().get(0);
        assertTrue(section.blocks().isEmpty(), "its prose moved into the parts");
        assertEquals(3, section.children().size());
        assertTrue(section.children().stream().allMatch(Segment::part));
        assertTrue(section.children().stream().allMatch(s -> s.chars() <= 15));
    }

    @Test
    void aParagraphLongerThanTheBudgetIsNeverCutOpen() {
        // A paragraph is the unit the reader already wraps. Going over budget
        // is the smaller harm.
        Segment root = tree("## 一\n\n甲。\n\n一二三四五六七八九十一二三四五六七八九十。", 5);

        Segment section = root.children().get(0);
        List<Segment> parts = section.children();
        assertTrue(parts.stream().anyMatch(s -> s.chars() > 5), "one part had to go over");
        assertTrue(parts.stream().allMatch(s -> s.blocks().size() >= 1));
        assertEquals(2, parts.size(), "one paragraph each, not one character each");
    }

    @Test
    void partsComeBeforeTheSubsectionsTheyPrecededInTheText() {
        // Reading order is the only order a tree of an article can be in.
        Segment root = tree("## 一\n\n一二三四五六七八九十。\n\n一二三四五六七八九十。\n\n"
                          + "### 一之一\n\n甲。", 15);

        Segment section = root.children().get(0);
        assertEquals(List.of(true, true, false),
                section.children().stream().map(Segment::part).toList());
        assertEquals("一之一", section.children().get(2).title());
    }

    @Test
    void pathsDescribeTheFinishedTreeRatherThanTheOneBeforeCutting() {
        // A part shifts the subsections after it, so numbering has to happen
        // once the budget has finished with the tree.
        Segment root = tree("## 一\n\n一二三四五六七八九十。\n\n一二三四五六七八九十。\n\n"
                          + "### 一之一\n\n甲。", 15);

        assertEquals(List.of("/ 标题", "1 一", "1.1 (part)", "1.2 (part)", "1.3 一之一"),
                shape(root));
    }

    @Test
    void turningTheBudgetOffLeavesATreeThatIsStillATree() {
        String body = "## 一\n\n一二三四五六七八九十。\n\n一二三四五六七八九十。";

        assertEquals(List.of("/ 标题", "1 一"), shape(tree(body, Segments.NO_BUDGET)));
        assertTrue(tree(body, Segments.NO_BUDGET).children().get(0).children().isEmpty());
    }

    // ── Nothing is lost ────────────────────────────────────────────────

    @Test
    void everyBlockOfTheDocumentEndsUpInExactlyOneSegment() {
        var parsed = MdSubsetParser.parse("""
                # 标题

                开头。

                ## 一

                一二三四五六七八九十。

                一二三四五六七八九十。

                ### 一之一

                甲。

                ## 二

                乙。
                """);
        var blocks = parsed.blocks().orElseThrow();
        Segment root = Segments.of(blocks, 15);

        long inTree = root.walk().stream().mapToLong(s -> s.blocks().size()).sum();
        long inDocument = blocks.stream()
                .filter(b -> !b.kind().equals("title") && !b.kind().equals("heading"))
                .count();
        assertEquals(inDocument, inTree, "a block belongs to one segment and no other");
    }

    @Test
    void totalCountsTheWholeSubtreeAndCharsCountsOnlyItsOwn() {
        Segment root = tree("## 一\n\n甲乙丙。\n\n### 一之一\n\n丁戊。", Segments.NO_BUDGET);

        Segment section = root.children().get(0);
        assertEquals(4, section.chars(), "甲乙丙。");
        assertEquals(7, section.total(), "and 丁戊。 underneath it");
    }

    private static String said(Segment s) {
        return s.blocks().stream()
                .flatMap(b -> b.lines().stream())
                .flatMap(List::stream)
                .map(MdDocument.Span::text)
                .reduce("", String::concat);
    }
}
