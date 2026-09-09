package kranji.studio.articles;

import kranji.studio.articles.GridPlan.Row;
import kranji.studio.articles.MdDocument.Block;
import kranji.studio.articles.MdDocument.Span;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A parsed document, arranged into squares.
 *
 * <h2>Two jobs</h2>
 *
 * <p><b>Squares.</b> One character, one square. A 字, a letter, a digit and a
 * mark are all one square wide, and nothing shares, spans or hangs.</p>
 *
 * <p><b>Wrapping.</b> Prose wraps at the column count; verse does not, because
 * a poem's lines are the author's.</p>
 *
 * <h2>What this used to do instead</h2>
 *
 * <p>A {@code ‹…›} run was one unit several squares wide, placed whole, moved
 * whole when it did not fit, and cut with a hyphen when it could never fit;
 * behind it went placeholder squares, because RelationGrid has no merged cells.
 * Marks shared boxes and hung past the margin. All of it worked and all of it
 * is gone — a rule that fits in one sentence beat a model that needed six.</p>
 *
 * <p>It also cost the author something, and that is the part worth naming.
 * Wrapping was <b>compulsory</b>: a bare {@code markdown} was an error, because
 * a square held one character and nobody but the author could say that eight
 * letters were one word. Nothing needs to know that now. {@code markdown} is
 * eight squares because it is eight characters.</p>
 *
 * <h2>禁则</h2>
 *
 * <p>Hanging used to make half of this free — a mark with no room hung off the
 * end of the row rather than starting the next one, so it could not begin a
 * line. With every mark in its own square that is gone and the rule has to be
 * stated:</p>
 *
 * <ul>
 *   <li>A <b>closing</b> mark belongs to what precedes it, so it never begins a
 *       row. Where a row has no room, the character before it comes down
 *       <i>with</i> it.</li>
 *   <li>An <b>opening</b> mark belongs to what follows it, so it never ends a
 *       row. One left at the end is carried onto the next.</li>
 * </ul>
 *
 * <p>Neither can be decided when the mark is placed: whether {@code （} ends a
 * row depends on what comes after, and whether {@code 。} begins one depends on
 * what came before. So both are decided at the row boundary, which is the only
 * place that knows.</p>
 */
public final class GridPlanner {

    private GridPlanner() {}

    /**
     * 每行二十格 — the 作文纸 a Chinese child writes on.
     *
     * <p>Twenty per row and twenty rows is 400 字, which is why an essay is
     * counted in sheets. Nothing here depends on it; it is the number to show
     * first because it is the one a reader already knows.</p>
     */
    public static final int DEFAULT_COLUMNS = 20;

    /** 首行缩进两格 — and the whole of a poem. */
    private static final List<Square> INDENT =
            List.of(new Square.Indent(), new Square.Indent());

    private static final String BULLET = "•";

    public static GridPlan plan(List<Block> blocks, int columns) {
        int width = Math.max(1, columns);
        var rows = new ArrayList<Row>();

        for (int b = 0; b < blocks.size(); b++) {
            Block block = blocks.get(b);
            switch (block.kind()) {
                // A poem's rows are the lines its author wrote; only a line too
                // long for the page is wrapped, and then only because the
                // alternative is losing the end of it.
                //
                // Indented on every line, not just the first. A paragraph is
                // indented to show where it starts; a poem is set in from the
                // margin as a whole, and a second line back at the edge would
                // read as prose that had wrapped.
                case "verse" -> lay(rows, "verse", b, width, INDENT, lines(block), true);
                case "p" -> lay(rows, "p", b, width, INDENT, lines(block), false);
                case "li" -> lay(rows, "li", b, width,
                        List.of(new Square.Marker(BULLET)), lines(block), false);
                case "oli" -> lay(rows, "oli", b, width,
                        List.of(new Square.Marker(block.level() + ".")), lines(block), false);
                default -> lay(rows, block.kind(), b, width, List.of(), lines(block), false);
            }
        }
        return new GridPlan(width, rows);
    }

    public static GridPlan plan(List<Block> blocks) {
        return plan(blocks, DEFAULT_COLUMNS);
    }

    private static List<List<Square>> lines(Block block) {
        return block.lines().stream().map(GridPlanner::squaresOf).toList();
    }

    // ── Wrapping ───────────────────────────────────────────────────────

    /**
     * Lays one block into rows.
     *
     * <p>Every authored line becomes at least one row, which is what keeps a
     * blank line inside a {@code ```verse} fence as the stanza break its author
     * wrote rather than as nothing.</p>
     *
     * @param everyLine whether each authored line opens with {@code opener} or
     *                  only the first. 首行缩进两格 is the first; a poem is the
     *                  whole block
     */
    private static void lay(List<Row> rows, String kind, int block, int columns,
                            List<Square> opener, List<List<Square>> lines, boolean everyLine) {
        var wrap = new Wrap(rows, kind, block, columns);
        for (int i = 0; i < lines.size(); i++) {
            // A stanza break opens with nothing. Indenting an empty line would
            // put squares in a row whose whole meaning is that it has none.
            boolean opens = (everyLine || i == 0) && !lines.get(i).isEmpty();
            wrap.open(opens ? opener : List.of());
            for (Square sq : lines.get(i)) wrap.add(sq);
            wrap.endLine();
        }
    }

    /** One block's worth of wrapping state. */
    private static final class Wrap {

        private final List<Row> rows;
        private final String kind;
        private final int block;
        private final int columns;

        private List<Square> cur = new ArrayList<>();
        private int line = 0;

        Wrap(List<Row> rows, String kind, int block, int columns) {
            this.rows = rows; this.kind = kind; this.block = block; this.columns = columns;
        }

        private int left() { return columns - cur.size(); }

        void open(List<Square> opener) { cur.addAll(opener); }

        void add(Square sq) {
            if (sq instanceof Square.Punct p && closing(p)) { closer(p); return; }
            if (left() < 1) newRow();
            cur.add(sq);
        }

        /**
         * A closing mark, which must not begin a row.
         *
         * <p>It belongs to the character before it, so when the row is full the
         * two go down together rather than the mark opening the next line on
         * its own. That is the move hanging used to avoid having to make.</p>
         *
         * <p>A row of one square has nothing to send down with it — on a page
         * that narrow there is no arrangement that satisfies the rule, and
         * looping to find one would not terminate. It takes the new row.</p>
         */
        private void closer(Square.Punct p) {
            if (left() >= 1) { cur.add(p); return; }

            // Everything that has to come down with it. Not just the square
            // before: that square may itself be a closing mark, and moving one
            // mark down to sit under another only relocates the problem —
            // 甲”。 at a two-square margin put ” at the head of a row by
            // solving 。 first. So the whole trailing run of closing marks
            // moves, plus the one square they all belong to.
            int at = cur.size();
            while (at > 0 && cur.get(at - 1) instanceof Square.Punct q && closing(q)) at--;
            if (at > 0) at--;

            // Nothing left above it, or what must move will not fit a row of
            // its own. On a page that narrow no arrangement satisfies the rule,
            // and searching for one would not terminate — so the mark takes the
            // new row and 禁则 goes unserved rather than the planner hanging.
            if (at <= 0 || cur.size() - at + 1 > columns) { newRow(); cur.add(p); return; }

            var move = new ArrayList<>(cur.subList(at, cur.size()));
            cur = new ArrayList<>(cur.subList(0, at));
            newRow();
            cur.addAll(move);
            cur.add(p);
        }

        /**
         * A wrap: the row so far is full and there is more to place.
         *
         * <p>An opening mark must not end a row, and this is the only place
         * that can know whether one has. When {@code （} is placed there may be
         * room for it and no way to tell yet whether what it opens will fit. So
         * any opening marks left at the end of the row are carried onto the next
         * one, where the thing they open is about to be written.</p>
         *
         * <p>A row of nothing but opening marks has nowhere to carry them to,
         * and carrying them for ever would not terminate. It keeps them.</p>
         */
        private void newRow() {
            if (cur.isEmpty()) return;

            int keep = cur.size();
            while (keep > 0 && cur.get(keep - 1) instanceof Square.Punct p && opening(p)) keep--;
            if (keep == 0) keep = cur.size();

            var carry = new ArrayList<>(cur.subList(keep, cur.size()));
            rows.add(new Row(kind, block, line++, new ArrayList<>(cur.subList(0, keep))));
            cur = carry;
        }

        /**
         * The author's line has ended.
         *
         * <p>Emits the row even when it is empty, unlike {@link #newRow()}: a
         * blank line inside a verse fence is a stanza break, and dropping it
         * would silently reshape the poem.</p>
         */
        void endLine() {
            rows.add(new Row(kind, block, line++, cur));
            cur = new ArrayList<>();
        }
    }

    /** A mark that must not end a row — it belongs to what follows it. */
    private static boolean opening(Square.Punct p) {
        return Mark.of(p.mark()).map(m -> !m.mayEndLine()).orElse(false);
    }

    /** A mark that must not begin a row — it belongs to what precedes it. */
    private static boolean closing(Square.Punct p) {
        return Mark.of(p.mark()).map(m -> !m.mayBeginLine()).orElse(false);
    }

    // ── Squares from spans ─────────────────────────────────────────────

    /** One line's spans, as squares. */
    private static List<Square> squaresOf(List<Span> line) {
        var out = new ArrayList<Square>();
        for (Span s : line) {
            boolean bold = s.emphasis().equals("strong");
            // A pinned reading is a fact about one character, and the parser
            // only ever puts one in a ruby span.
            if (s.kind().equals("ruby")) {
                out.add(new Square.Zi(s.text(), s.reading(), bold));
                continue;
            }
            scan(s.text(), bold, out);
        }
        return out;
    }

    /**
     * Character by character: a 字, a mark, or anything else.
     *
     * <p>Whitespace is not a square. The gap between a character and the word
     * beside it is the gap that already exists between two squares, and a space
     * given a box of its own would open a hole in the line that the author did
     * not write.</p>
     */
    private static void scan(String text, boolean bold, List<Square> out) {
        for (int i = 0; i < text.length(); ) {
            int cp = text.codePointAt(i);
            i += Character.charCount(cp);
            if (Character.isWhitespace(cp)) continue;

            String ch = new String(Character.toChars(cp));
            if (Character.UnicodeScript.of(cp) == Character.UnicodeScript.HAN) {
                out.add(new Square.Zi(ch, "", bold));
                continue;
            }
            Optional<Mark> mark = Mark.of(ch);
            if (mark.isPresent()) {
                out.add(new Square.Punct(mark.get().text()));
                continue;
            }
            out.add(new Square.Letter(ch, bold));
        }
    }
}
