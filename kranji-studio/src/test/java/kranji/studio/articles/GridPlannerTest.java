package kranji.studio.articles;

import kranji.studio.articles.GridPlan.Row;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The arrangement.
 *
 * <p>One character, one square. Most of what used to be worth asserting here
 * was about the machinery that made that untrue — a run's claim, the
 * placeholders behind it, marks sharing a box, marks hanging past the margin —
 * and those tests went with the machinery.</p>
 *
 * <p>What is left is what a person still cannot check by looking: that nothing
 * is wider than the page, that 禁则 holds now that hanging is not doing it for
 * free, and that a block keeps its shape.</p>
 */
class GridPlannerTest {

    private static GridPlan plan(String body, int columns) {
        var parsed = MdSubsetParser.parse("# 标题\n\n" + body + "\n");
        assertTrue(parsed.ok(), () -> "did not parse: " + parsed.findings());
        // The title is a block like any other; the tests are about the body.
        var blocks = parsed.blocks().orElseThrow().stream()
                .filter(b -> !b.kind().equals("title")).toList();
        return GridPlanner.plan(blocks, columns);
    }

    private static Row row(GridPlan p, int i) { return p.rows().get(i); }

    /** What a row says. An indent is a dot; everything else is its character. */
    private static String said(Row r) {
        var sb = new StringBuilder();
        for (Square s : r.squares()) {
            if (s instanceof Square.Zi z)           sb.append(z.zi());
            else if (s instanceof Square.Letter l)  sb.append(l.text());
            else if (s instanceof Square.Marker m)  sb.append(m.text());
            else if (s instanceof Square.Punct p)   sb.append(p.mark());
            else                                    sb.append('.');
        }
        return sb.toString();
    }

    /** Verse, to get a line with no 首行缩进 in the way. */
    private static GridPlan verse(String line, int columns) {
        return plan("```verse\n" + line + "\n```", columns);
    }

    // ── One character, one square ──────────────────────────────────────

    @Test
    void everyCharacterIsOneSquareWhateverItIs() {
        GridPlan p = verse("甲a1。", 20);

        List<Square> sq = row(p, 0).squares();
        assertInstanceOf(Square.Zi.class, sq.get(2), "a 字");
        assertInstanceOf(Square.Letter.class, sq.get(3), "a letter");
        assertInstanceOf(Square.Letter.class, sq.get(4), "a digit");
        assertInstanceOf(Square.Punct.class, sq.get(5), "a mark");
        assertEquals(6, row(p, 0).used(), "four characters behind 首行缩进两格");
    }

    @Test
    void aLatinWordIsAsManySquaresAsItHasLetters() {
        // It used to be one unit claiming four squares from a width table, and
        // the author had to write ‹markdown› to say it was one word at all.
        GridPlan p = verse("markdown", 20);

        assertEquals("..markdown", said(row(p, 0)));
        assertEquals(10, row(p, 0).used());
        assertTrue(row(p, 0).squares().subList(2, 10).stream()
                .allMatch(s -> s instanceof Square.Letter));
    }

    @Test
    void unwrappedLatinIsNoLongerAnErrorAndNeedsNoBrackets() {
        // The whole reason ‹…› was compulsory was that a square held one
        // character and only the author could say how many squares eight
        // letters were. Nothing needs to know now.
        var bare = MdSubsetParser.parse("# 标题\n\n会像 markdown 那样。\n");
        assertTrue(bare.ok(), () -> bare.findings().toString());
        assertEquals(List.of(), bare.errors());
    }

    @Test
    void bracketsStillWorkAndStillDoNotTravel() {
        // ‹…› survives as optional markup - it is what says where italic
        // applies - and its delimiters were never content.
        GridPlan p = verse("靠近‹Tunnel›。", 20);

        assertEquals("..靠近Tunnel。", said(row(p, 0)));
    }

    @Test
    void aSpaceIsNotASquare() {
        // The gap between two words is the gap that already exists between two
        // squares. A box for it would be a hole the author did not write.
        GridPlan p = verse("Dust II", 20);

        assertEquals("..DustII", said(row(p, 0)));
        assertEquals(8, row(p, 0).used());
    }

    @Test
    void marksDoNotShareSquaresAnyMore() {
        // ”。 shared a box on 稿纸 and shared one here. Two squares now, which
        // is the rule that replaced three.
        GridPlan p = verse("甲”。乙", 20);

        assertEquals("..甲”。乙", said(row(p, 0)));
        assertEquals(6, row(p, 0).used(), "four characters behind 首行缩进两格");
        assertEquals("”", ((Square.Punct) row(p, 0).squares().get(3)).mark());
        assertEquals("。", ((Square.Punct) row(p, 0).squares().get(4)).mark());
    }

    @Test
    void aMarkAfterBoldIsNotItselfBold() {
        GridPlan p = verse("**甲**。", 20);

        assertTrue(((Square.Zi) row(p, 0).squares().get(2)).bold());
        assertInstanceOf(Square.Punct.class, row(p, 0).squares().get(3));
    }

    @Test
    void anOverrideKeepsItsReadingInItsSquare() {
        GridPlan p = verse("地{dì}方", 20);

        Square.Zi di = (Square.Zi) row(p, 0).squares().get(2);
        assertEquals("地", di.zi());
        assertEquals("dì", di.reading());
    }

    // ── 禁则, which now has to be written down ─────────────────────────

    @Test
    void aClosingMarkBringsTheCharacterBeforeItDownRatherThanBeginningARow() {
        // Hanging used to make this free. With every mark in its own square the
        // rule has to move something, and what it moves is the pair together.
        GridPlan p = verse("一二三四五六。", 4);

        assertEquals("..一二", said(row(p, 0)));
        assertEquals("三四五", said(row(p, 1)), "六 came down with its full stop");
        assertEquals("六。", said(row(p, 2)));
    }

    @Test
    void anOpeningMarkMovesDownRatherThanEndingARow() {
        GridPlan p = verse("一二三四（甲）", 4);

        assertEquals("..一二", said(row(p, 0)));
        assertEquals("三四", said(row(p, 1)), "the row gave up the bracket it was holding");
        assertEquals("（甲）", said(row(p, 2)), "the bracket went with what it opens");
    }

    @Test
    void aDashPairIsNotSplitAcrossRows() {
        // Each half is its own square and could be separated like any other
        // two. What holds them together is the closing rule: the second half
        // drags the first down with it.
        GridPlan p = verse("一二三——甲", 4);

        for (Row r : p.rows()) {
            String s = said(r);
            assertFalse(s.startsWith("—") && !s.startsWith("——"),
                    "half a 破折号 opened a row: " + s);
        }
    }

    @Test
    void noRowBeginsWithAClosingMarkOrEndsWithAnOpeningOne() {
        // The sweep. Every width, because which one collides depends on the
        // sentence.
        String body = "他说「这是（一个）测试」，然后就走了——真的走了。";
        for (int columns = 3; columns <= 24; columns++) {
            GridPlan p = plan(body, columns);
            List<Row> rows = p.rows();
            for (int i = 0; i < rows.size(); i++) {
                Row here = rows.get(i);
                List<Square> sq = here.squares();
                if (sq.isEmpty()) continue;
                int width = columns;

                if (sq.get(0) instanceof Square.Punct first) {
                    assertTrue(Mark.of(first.mark()).map(Mark::mayBeginLine).orElse(true),
                            () -> "at " + width + "/row a mark began a row: " + said(here));
                }
                // The author's own line ending is not a wrap.
                if (i + 1 >= rows.size()) continue;
                if (sq.get(sq.size() - 1) instanceof Square.Punct last) {
                    assertTrue(Mark.of(last.mark()).map(Mark::mayEndLine).orElse(true),
                            () -> "at " + width + "/row a mark ended a row");
                }
            }
        }
    }

    @Test
    void noRowIsEverWiderThanThePage() {
        String body = "警察出生点在‹Tunnel›附近，属于‹Premier›之外的图（很长的一段话）。";
        for (int columns = 2; columns <= 24; columns++) {
            GridPlan p = plan(body, columns);
            int width = columns;
            for (Row r : p.rows()) {
                assertTrue(r.used() <= width,
                        () -> "at " + width + "/row: " + said(r) + " uses " + r.used());
            }
        }
    }

    @Test
    void aPageOneSquareWideStillTerminates() {
        // There is no arrangement that satisfies 禁则 on a page this narrow: a
        // closing mark has nothing to bring down with it, because the row it
        // would come from holds one square and that is the mark's own. Looking
        // for an arrangement anyway would not stop, so it gives up and takes
        // the row. A quote rather than verse, to keep 首行缩进两格 — which is
        // itself two squares — off a one-square page.
        GridPlan p = plan("> 甲。乙。", 1);

        assertTrue(p.height() >= 4, "it terminated, which is the whole assertion");
        assertTrue(p.rows().stream().allMatch(r -> r.used() <= 1));
    }

    // ── Blocks ─────────────────────────────────────────────────────────

    @Test
    void verseKeepsItsLinesAndProseWraps() {
        GridPlan p = plan("```verse\n床前明月光，\n疑是地上霜。\n```", 20);

        assertEquals(2, p.height(), "a poem's rows are the lines its author wrote");
        assertEquals("..床前明月光，", said(row(p, 0)));
        assertEquals("..疑是地上霜。", said(row(p, 1)),
                "every line of a poem is indented, not only its first");
        assertEquals(0, row(p, 0).line());
        assertEquals(1, row(p, 1).line());
    }

    @Test
    void aStanzaBreakIsNotIndented() {
        // A poem is set in from the margin, but an empty line's whole meaning
        // is that it has nothing in it — and two blanks are something.
        GridPlan p = plan("```verse\n甲。\n\n乙。\n```", 20);

        assertEquals("..甲。", said(row(p, 0)));
        assertEquals("", said(row(p, 1)));
        assertEquals(0, row(p, 1).used());
        assertEquals("..乙。", said(row(p, 2)));
    }

    @Test
    void aWrappedVerseLineIsIndentedOnceLikeAnyOtherWrap() {
        // The indent belongs to the line the author wrote. What the page did
        // to it afterwards is the page's.
        GridPlan p = verse("一二三四五六。", 6);

        assertEquals("..一二三四", said(row(p, 0)));
        assertEquals("五六。", said(row(p, 1)), "a wrap is not a new line of the poem");
    }

    @Test
    void aWrappedParagraphIndentsOnceAndIsOneBlock() {
        GridPlan p = plan("一二三四五六七八九十一二三四五六七八九十。", 8);

        assertTrue(p.height() > 1);
        assertTrue(p.rows().stream().allMatch(r -> r.block() == 0), "one paragraph, several rows");
        assertInstanceOf(Square.Indent.class, row(p, 0).squares().get(0));
        assertFalse(row(p, 1).squares().get(0) instanceof Square.Indent,
                "only the first row is indented");
    }

    @Test
    void aListItemOpensWithItsMarker() {
        GridPlan p = plan("- 市场\n\n3. 第三条", 20);

        assertEquals("li", row(p, 0).kind());
        assertEquals("•市场", said(row(p, 0)));
        assertEquals("oli", row(p, 1).kind());
        assertEquals("3.第三条", said(row(p, 1)), "the author's own number");
    }

    @Test
    void twoParagraphsAreTwoBlocksRatherThanOneLongOne() {
        GridPlan p = plan("一。\n\n二。", 20);

        assertEquals(2, p.height());
        assertEquals(0, row(p, 0).block());
        assertEquals(1, row(p, 1).block());
    }
}
