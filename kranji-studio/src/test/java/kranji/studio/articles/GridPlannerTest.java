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
 * <p>What is worth asserting here is what a person cannot check by looking:
 * that a run claims the squares it needs, that the claim is made up by
 * placeholders so the row's arithmetic is right, and that nothing ever ends up
 * wider than the page.</p>
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

    /** What a row says, placeholders shown as an underscore each. */
    private static String said(Row r) {
        var sb = new StringBuilder();
        for (Square s : r.squares()) {
            if (s instanceof Square.Zi z)           sb.append(z.zi());
            else if (s instanceof Square.Marker m)  sb.append(m.text());
            else if (s instanceof Square.Punct p)   sb.append(p.hanging() ? "|" : "").append(p.marks());
            else if (s instanceof Square.Run run)  sb.append('[').append(run.text()).append(']');
            else if (s instanceof Square.Cont)     sb.append('_');
            else                                   sb.append('.');
        }
        return sb.toString();
    }

    /** Verse, to get a line with no 首行缩进 in the way. */
    private static GridPlan verse(String line, int columns) {
        return plan("```verse\n" + line + "\n```", columns);
    }

    // ── Squares ────────────────────────────────────────────────────────

    @Test
    void aCharacterIsASquareAndSoIsAMark() {
        GridPlan p = plan("一二三。", 20);

        Row r = row(p, 0);
        assertEquals("p", r.kind());
        // 首行缩进两格 - the indent is two empty squares, not a margin, which
        // is what it looks like on squared paper.
        assertInstanceOf(Square.Indent.class, r.squares().get(0));
        assertInstanceOf(Square.Indent.class, r.squares().get(1));
        assertEquals("..一二三。", said(r));
        assertEquals(6, r.used(), "两个空格，三个字，一个标点 - 每个标点占一格");
    }

    @Test
    void nothingRidesInACharactersCornerAnyMore() {
        // It used to. A mark's square depended on what happened to be beside
        // it, which made 。 after a character invisible in the model and 。
        // after a run a square. Every mark has a box now.
        Row r = row(plan("一，二。", 20), 0);

        assertEquals("..一，二。", said(r));
        assertEquals(6, r.used());
        assertInstanceOf(Square.Punct.class, r.squares().get(3));
        assertEquals("，", ((Square.Punct) r.squares().get(3)).marks());
    }

    @Test
    void aMarkAfterBoldIsNotItselfBold() {
        // The bold ends with the characters it was written around. The comma
        // is a square of its own and belongs to neither side.
        Row r = row(plan("这是**很重要**，对吗。", 20), 0);

        var zi = (Square.Zi) r.squares().stream()
                .filter(s -> s instanceof Square.Zi z && z.zi().equals("要")).findFirst().orElseThrow();
        assertTrue(zi.bold());
        assertEquals("..这是很重要，对吗。", said(r));
    }

    @Test
    void anOverrideKeepsItsReadingInItsSquare() {
        Row r = row(plan("读地{dì}方。", 20), 0);

        var zi = (Square.Zi) r.squares().stream()
                .filter(s -> s instanceof Square.Zi z && z.zi().equals("地")).findFirst().orElseThrow();
        assertEquals("dì", zi.reading());
    }

    // ── Marks sharing a square ─────────────────────────────────────────

    @Test
    void twoClosingMarksShareASquare() {
        // ”。 in one box, which is what a hand does: two boxes there leaves a
        // hole in the line.
        Row r = row(plan("他说：“好。”", 20), 0);

        var last = (Square.Punct) r.squares().get(r.squares().size() - 1);
        assertEquals("。”", last.marks());
        assertTrue(last.packed());
    }

    @Test
    void openingMarksShareToo_butNotWithClosingOnes() {
        // Same class only. 「“‘」 open together and 「’”」 close together, but a
        // 「“」 starting the next quotation gets its own square, because it
        // belongs to what follows rather than to what came before.
        Row r = row(plan("“‘甲’”，“乙”", 20), 0);

        List<String> marks = r.squares().stream()
                .filter(s -> s instanceof Square.Punct)
                .map(s -> ((Square.Punct) s).marks()).toList();
        assertEquals(List.of("“‘", "’”，", "“", "”"), marks);
    }

    @Test
    void aDashNeverSharesASquare() {
        // 破折号 is written double and each half fills a square. Packing them
        // would turn a dash into a hyphen.
        Row r = row(plan("一——二。", 20), 0);

        assertEquals("..一——二。", said(r));
        assertEquals(7, r.used(), "two blanks, 一, two dashes, 二, and 。 in a box of its own");
        assertInstanceOf(Square.Punct.class, r.squares().get(3));
        assertFalse(((Square.Punct) r.squares().get(3)).packed());
        assertTrue(r.squares().stream().noneMatch(s -> s instanceof Square.Run),
                () -> "a dash is a mark, not a run: " + said(r));
    }

    @Test
    void aDashMeasuresAFullSquare() {
        // U+2014 sits in General Punctuation, which is mostly not full-width -
        // ‹ and › are in the same block. It is named rather than ranged, and
        // getting it wrong made 破折号 claim one square for two.
        assertEquals(1.0, SquareWidth.ems("—"), 0.001);
        assertEquals(2, SquareWidth.squares("——"));
    }

    // ── 禁则 ───────────────────────────────────────────────────────────

    @Test
    void aMarkWithNoRoomHangsPastTheEdgeRatherThanBeginningARow() {
        // The paper trick: when a full stop lands at the margin you carry on
        // past the ruling. Nothing is pushed down and nothing is squeezed into
        // the character's box.
        GridPlan p = verse("一二三四。", 4);

        Row r = row(p, 0);
        assertEquals("一二三四|。", said(r));
        assertEquals(4, r.used(), "what hangs past the edge is not a position");
        assertEquals(1, r.hanging().size());
        assertEquals(1, p.height(), "and nothing was pushed onto a second row");
    }

    @Test
    void aDashPairCannotBeSplitAcrossRows() {
        // Free, and worth an assertion because it is the reason hanging beats
        // pushing: the second dash hangs beside the first instead of opening
        // the next row.
        GridPlan p = verse("一二三——四", 4);

        assertEquals("一二三—|—", said(row(p, 0)));
        assertEquals("四", said(row(p, 1)));
    }

    @Test
    void anOpeningMarkMovesDownRatherThanEndingARow() {
        // Hanging cannot help here: the mark belongs to what follows it, and
        // what follows is on the next row.
        GridPlan p = verse("一二三（四）", 4);

        assertEquals("一二三", said(row(p, 0)));
        assertEquals("（四）", said(row(p, 1)));
    }

    @Test
    void noRowBeginsWithAClosingMarkOrEndsWithAnOpeningOne() {
        // The rule check, over something long enough to hit every width.
        String text = "他说：“一二三四五六七八九十。”（甲）乙、丙——丁，戊己庚辛。";
        for (int columns = 4; columns <= 20; columns++) {
            GridPlan p = plan(text, columns);
            for (Row r : p.rows()) {
                List<Square> in = r.squares().stream().filter(s -> !s.hanging()).toList();
                if (in.isEmpty()) continue;
                if (in.get(0) instanceof Square.Punct first) {
                    assertFalse(kranji.reading.model.Cells.CLOSING.contains(first.first()),
                            () -> "a closing mark began a row at " + said(r));
                }
                if (in.get(in.size() - 1) instanceof Square.Punct last) {
                    assertFalse(kranji.reading.model.Cells.OPENING.contains(last.first()),
                            () -> "an opening mark ended a row at " + said(r));
                }
            }
        }
    }

    // ── What needs no ‹…› ──────────────────────────────────────────────

    @Test
    void fullWidthMarksNeverEndUpInsideARun() {
        // The whole reason this is simpler than it was. Brackets used to have
        // to join their run and separators used to have to end one, because
        // marks had no squares of their own. Now none of them are in a run at
        // all, and the two rules that arranged that are gone.
        Row r = row(plan("它不在（Premier）的池里。", 20), 0);

        var run = (Square.Run) r.squares().stream()
                .filter(s -> s instanceof Square.Run).findFirst().orElseThrow();
        assertEquals("Premier", run.text());
        assertEquals("..它不在（[Premier]__）的池里。", said(r));
    }

    @Test
    void aListOfLatinNamesIsNotOneEnormousRun() {
        // Ancient、Anubis、… was one run of fifty characters when the 、 had
        // nowhere else to go: nineteen of twenty squares, hyphenated through
        // map names on any narrower page.
        Row r = row(plan("Ancient、Anubis、Cache", 20), 0);

        assertEquals("..[Ancient]__、[Anubis]__、[Cache]_", said(r));
        assertEquals(3, r.squares().stream().filter(s -> s instanceof Square.Run).count());
    }

    // ── Runs ───────────────────────────────────────────────────────────

    @Test
    void aRunClaimsSeveralSquaresAndPlaceholdersMakeUpTheClaim() {
        Row r = row(plan("在‹Tunnel›口。", 20), 0);

        var run = (Square.Run) r.squares().stream()
                .filter(s -> s instanceof Square.Run).findFirst().orElseThrow();
        assertEquals("Tunnel", run.text());
        assertTrue(run.width() > 1, "six letters do not fit in one square");

        long conts = r.squares().stream()
                .filter(s -> s instanceof Square.Cont c && c.id() == run.id()).count();
        assertEquals(run.width() - 1, conts, "the head takes one square, placeholders the rest");
        assertEquals(2 + 1 + run.width() + 1 + 1, r.used(), "indent, 在, the run, 口, 。");
    }

    @Test
    void aRunKeepsTheEmphasisInsideIt() {
        Row r = row(plan("在‹Tunnel *bla*›口。", 20), 0);

        var run = (Square.Run) r.squares().stream()
                .filter(s -> s instanceof Square.Run).findFirst().orElseThrow();
        assertEquals("Tunnel bla", run.text());
        assertTrue(run.parts().stream().anyMatch(s -> s.emphasis().equals("em")),
                "a run is pieces, not a string - the italic inside it survives");
    }

    @Test
    void bareLatinSpansItsSquaresToo() {
        // The bug this case exists for. Only marked runs used to claim width,
        // so a word nobody wrapped got one square and overflowed into the
        // characters beside it. How wide it is was never a matter of opinion.
        Row r = row(plan("会像 markdown 那样。", 20), 0);

        var run = (Square.Run) r.squares().stream()
                .filter(s -> s instanceof Square.Run).findFirst().orElseThrow();
        assertEquals("markdown", run.text(), "and the spaces around it are not squares");
        assertTrue(run.width() > 1);
        assertFalse(run.marked(), "nobody wrapped it, and the workbench should say so");
    }

    @Test
    void aMarkedRunIsTheSameShapeAndSaysWhoAskedForIt() {
        Row bare = row(plan("在 Tunnel 口。", 20), 0);
        Row wrapped = row(plan("在‹Tunnel›口。", 20), 0);

        var a = (Square.Run) bare.squares().stream()
                .filter(s -> s instanceof Square.Run).findFirst().orElseThrow();
        var b = (Square.Run) wrapped.squares().stream()
                .filter(s -> s instanceof Square.Run).findFirst().orElseThrow();

        assertEquals(a.width(), b.width(), "the arrangement does not depend on the marking");
        assertFalse(a.marked());
        assertTrue(b.marked());
    }

    @Test
    void aRunThatDoesNotFitTheRestOfARowMovesToTheNextOne() {
        // What Chinese typesetting does with a Latin word, and it needs no
        // hyphen: the row simply ends early.
        GridPlan p = plan("一二三四五六七‹Apartments›八九。", 10);

        Row first = row(p, 0);
        assertTrue(first.squares().stream().noneMatch(s -> s instanceof Square.Run),
                () -> "the run should not have started on row 0: " + said(first));
        assertTrue(row(p, 1).squares().get(0) instanceof Square.Run,
                () -> "and should open row 1: " + said(row(p, 1)));
    }

    @Test
    void noRowIsEverWiderThanThePage() {
        GridPlan p = plan("一二三‹Counter-Strike Global Offensive›四五六七八九十。", 8);

        for (Row r : p.rows()) {
            assertTrue(r.used() <= p.columns(),
                    () -> "row over the page: " + said(r) + " (" + r.used() + ")");
            assertTrue(r.hanging().size() <= 2,
                    () -> "too much hanging past the edge: " + said(r));
        }
    }

    // ── The hyphen ─────────────────────────────────────────────────────

    @Test
    void aRunWiderThanThePageIsCutAndTheCutIsMarked() {
        // The only place a hyphen is invented. There is nowhere to move a run
        // that is wider than the whole row, so it is broken instead.
        GridPlan p = plan("‹Counter-Strike Global Offensive›", 6);

        List<Square.Run> pieces = p.rows().stream()
                .flatMap(r -> r.squares().stream())
                .filter(s -> s instanceof Square.Run)
                .map(s -> (Square.Run) s).toList();

        assertTrue(pieces.size() > 1, "it had to be cut");
        assertTrue(pieces.get(0).text().endsWith("-"), pieces.get(0).text());
        assertTrue(pieces.get(pieces.size() - 1).broken(), "every piece knows it was cut");
        assertFalse(pieces.get(pieces.size() - 1).text().endsWith("-"),
                "except that the last one closes nothing");

        // Nothing is lost and nothing is duplicated: the pieces spell the run,
        // once the hyphens this added are taken back out. Every piece but the
        // last ends in one we added - which is why they are counted off rather
        // than searched for. The run has an authored hyphen of its own, and a
        // rule that stripped any trailing '-' would eat that too.
        var rejoined = new StringBuilder();
        for (int i = 0; i < pieces.size(); i++) {
            String t = pieces.get(i).text();
            rejoined.append(i < pieces.size() - 1 ? t.substring(0, t.length() - 1) : t);
        }
        assertEquals("Counter-Strike Global Offensive", rejoined.toString());
    }

    @Test
    void everyPieceOfACutRunKeepsTheRunsId() {
        // So that a renderer with merged cells can still see these were one
        // run before the page turned out to be too narrow for it.
        GridPlan p = plan("‹Counter-Strike Global Offensive›", 6);

        List<Integer> ids = p.rows().stream().flatMap(r -> r.squares().stream())
                .filter(s -> s instanceof Square.Run).map(s -> ((Square.Run) s).id()).distinct().toList();

        assertEquals(1, ids.size(), "one run, however many pieces");
    }

    @Test
    void aRunThatFitsIsNeverHyphenated() {
        GridPlan p = plan("‹Apartments›", 20);

        // After the paragraph indent.
        var run = (Square.Run) row(p, 0).squares().get(2);
        assertEquals("Apartments", run.text());
        assertFalse(run.broken());
    }

    // ── Blocks ─────────────────────────────────────────────────────────

    @Test
    void verseKeepsItsLinesAndProseWraps() {
        GridPlan p = plan("```verse\n床前明月光，\n疑是地上霜。\n```", 20);

        assertEquals(2, p.height(), "a poem's rows are the lines its author wrote");
        assertEquals("床前明月光，", said(row(p, 0)));
        assertEquals(0, row(p, 0).line());
        assertEquals(1, row(p, 1).line());
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
