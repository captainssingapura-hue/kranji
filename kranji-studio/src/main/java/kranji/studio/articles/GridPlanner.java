package kranji.studio.articles;

import kranji.reading.model.Cells;
import kranji.studio.articles.GridPlan.Row;
import kranji.studio.articles.MdDocument.Block;
import kranji.studio.articles.MdDocument.Span;

import java.util.ArrayList;
import java.util.List;

/**
 * A parsed document, arranged into squares.
 *
 * <h2>Three jobs</h2>
 *
 * <p><b>Squares.</b> A character is a square and so is a mark. Adjacent marks
 * of the same class share one, the way {@code ”，} shares a box on 稿纸.</p>
 *
 * <p><b>Runs.</b> A non-Chinese sequence is one typographic unit needing
 * several squares — {@code ‹Tunnel›} and a bare {@code markdown} alike. It
 * becomes a {@link Square.Run} carrying its width, plus as many
 * {@link Square.Cont} placeholders as it claimed.</p>
 *
 * <p><b>Wrapping.</b> Prose wraps at the column count; verse does not, because
 * a poem's lines are the author's.</p>
 *
 * <h2>禁则, and why there is barely any code for it</h2>
 *
 * <p>A mark must not begin a line. Rather than push the character before it
 * down, or squeeze the mark into that character's box, a mark that finds no
 * room <b>hangs past the right edge</b> and the row's last cell runs wider to
 * take it — which is what a person does when a full stop lands at the margin
 * of a composition.</p>
 *
 * <p>Two rules come free with it. {@code ——} and {@code ……} cannot be split
 * across rows, because the second half hangs beside the first rather than
 * opening the next row. And nothing has to move, so a row's contents never
 * depend on what comes after them.</p>
 *
 * <p>The other half of 禁则 needs one line: an opening mark must not <em>end</em>
 * a row, so one that would land last moves down instead. Hanging cannot help
 * there — the mark belongs to what follows it, and what follows is on the next
 * row.</p>
 *
 * <h2>Where a hyphen comes from</h2>
 *
 * <p>One place only. A run that does not fit in what is left of a row moves to
 * the next row whole — which is what Chinese typesetting does with a Latin
 * word, and it needs no hyphen. A run wider than the entire row has nowhere to
 * move to, so it is cut and the pieces carry one.</p>
 *
 * <p>The cut is at whatever character fits, which is not where a dictionary
 * would hyphenate. That is deliberate: real hyphenation needs a language and a
 * pattern table, and guessing badly at one is worse for a child learning to
 * read than an obviously mechanical break. It happens only to a run wider than
 * the whole page, where the alternative is not rendering it.</p>
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

    /**
     * How many marks will share a square.
     *
     * <p>Two is the ordinary case — {@code ”，} — and three is the squeeze a
     * crowded line will take. Past that a person starts a new box, and so does
     * this.</p>
     */
    public static final int MAX_MARKS = 3;

    /**
     * Marks that always have a square to themselves.
     *
     * <p>破折号 and 省略号 are written double, {@code ——} and {@code ……}, and
     * each half fills a square: packing them would turn a dash into a hyphen.
     * 波浪号, 间隔号 and the full-width solidus are single full-width marks with
     * nothing to pack against.</p>
     */
    public static final String STANDING = "—―…～·／";

    /** Every full-width mark that gets a square. */
    private static final String MARKS = Cells.CLOSING + Cells.OPENING + STANDING;

    /** A fragment shorter than this is not worth leaving at the end of a row. */
    private static final int MIN_FRAGMENT = 2;

    private static final String BULLET = "•";
    private static final String HYPHEN = "-";

    public static GridPlan plan(List<Block> blocks, int columns) {
        int width = Math.max(1, columns);
        var rows = new ArrayList<Row>();
        var ids = new int[1];

        for (int b = 0; b < blocks.size(); b++) {
            Block block = blocks.get(b);
            switch (block.kind()) {
                // A poem's rows are the lines its author wrote. Only a line
                // too long for the page is wrapped, and then only because the
                // alternative is losing the end of it.
                case "verse" -> lay(rows, "verse", b, width, List.of(), lines(block, ids));
                case "p" -> lay(rows, "p", b, width,
                        List.of(new Square.Indent(), new Square.Indent()), lines(block, ids));
                case "li" -> lay(rows, "li", b, width,
                        List.of(new Square.Marker(BULLET)), lines(block, ids));
                case "oli" -> lay(rows, "oli", b, width,
                        List.of(new Square.Marker(block.level() + ".")), lines(block, ids));
                default -> lay(rows, block.kind(), b, width, List.of(), lines(block, ids));
            }
        }
        return new GridPlan(width, rows);
    }

    public static GridPlan plan(List<Block> blocks) {
        return plan(blocks, DEFAULT_COLUMNS);
    }

    private static List<List<Square>> lines(Block block, int[] ids) {
        return block.lines().stream().map(line -> squaresOf(line, ids)).toList();
    }

    // ── Wrapping ───────────────────────────────────────────────────────

    /**
     * Lays one block into rows, opening the first with {@code opener}.
     *
     * <p>Every authored line becomes at least one row, which is what keeps a
     * blank line inside a {@code ```verse} fence as the stanza break its author
     * wrote rather than as nothing.</p>
     */
    private static void lay(List<Row> rows, String kind, int block, int columns,
                            List<Square> opener, List<List<Square>> lines) {
        var wrap = new Wrap(rows, kind, block, columns);
        for (int i = 0; i < lines.size(); i++) {
            wrap.open(i == 0 ? opener : List.of());
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

        /** Positions used. What hangs past the edge is not one of them. */
        private int used() {
            int n = 0;
            for (Square s : cur) if (!s.hanging()) n++;
            return n;
        }

        private int left() { return columns - used(); }

        void open(List<Square> opener) { cur.addAll(opener); }

        void add(Square sq) {
            if (sq instanceof Square.Punct p) { punct(p); return; }
            if (sq instanceof Square.Run run) { run(run); return; }
            if (left() < 1) newRow();
            cur.add(sq);
        }

        /**
         * A mark. It never begins a row.
         *
         * <p>With no room left it hangs off the end of this one instead of
         * starting the next, which is the whole of 禁则's first half. An
         * opening mark is the exception in the other direction: it belongs to
         * what follows, so one that would sit in the last box moves down to
         * meet it.</p>
         */
        private void punct(Square.Punct p) {
            if (opening(p) && used() > 0 && left() == 1) newRow();
            cur.add(left() < 1 ? new Square.Punct(p.marks(), true) : p);
        }

        /** A run, moved rather than split — unless it can never fit a row. */
        private void run(Square.Run run) {
            if (run.width() > columns) { cut(run); return; }
            if (run.width() > left()) newRow();
            place(run);
        }

        /** The head, then the squares it claimed. */
        private void place(Square.Run run) {
            cur.add(run);
            for (int i = 1; i < run.width(); i++) cur.add(new Square.Cont(run.id()));
        }

        /**
         * A run wider than the page, cut into pieces that fit.
         *
         * <p>Each piece but the last gains a hyphen, and every piece keeps the
         * run's id — so when merged cells arrive, a renderer can still see that
         * these were one run before the page was too narrow for it.</p>
         */
        private void cut(Square.Run run) {
            List<Ch> chars = flatten(run.parts());
            int at = 0;
            boolean broken = false;

            while (at < chars.size()) {
                if (left() < MIN_FRAGMENT) newRow();

                // The tail may fit as it is, and then no hyphen is invented.
                String rest = textOf(chars, at, chars.size());
                int restWidth = SquareWidth.squares(rest);
                if (restWidth <= left()) {
                    place(new Square.Run(spansOf(chars, at, chars.size(), ""),
                                         run.marked(), restWidth, run.id(), broken));
                    return;
                }

                int take = fits(chars, at, left());
                // Guaranteed on a fresh row: one character and a hyphen is
                // under a square for every character there is.
                if (take == 0) { newRow(); take = Math.max(1, fits(chars, at, left())); }

                String piece = textOf(chars, at, at + take) + HYPHEN;
                place(new Square.Run(spansOf(chars, at, at + take, HYPHEN),
                                     run.marked(), SquareWidth.squares(piece), run.id(), true));
                at += take;
                broken = true;
                newRow();
            }
        }

        /** How many characters from `at` fit in `budget` squares with a hyphen. */
        private int fits(List<Ch> chars, int at, int budget) {
            int take = 0;
            for (int n = 1; at + n <= chars.size(); n++) {
                String piece = textOf(chars, at, at + n) + HYPHEN;
                if (SquareWidth.squares(piece) > budget) break;
                take = n;
            }
            return take;
        }

        /** A wrap: the row so far is full and there is more to place. */
        private void newRow() {
            if (!cur.isEmpty()) rows.add(new Row(kind, block, line++, cur));
            cur = new ArrayList<>();
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

    private static boolean opening(Square.Punct p) {
        return Cells.OPENING.contains(p.first());
    }

    // ── Squares from spans ─────────────────────────────────────────────

    /** One line's spans, as squares. */
    private static List<Square> squaresOf(List<Span> line, int[] ids) {
        var out = new ArrayList<Square>();
        var plain = new StringBuilder();

        for (int i = 0; i < line.size(); ) {
            Span s = line.get(i);

            if (s.inRun()) {
                int j = i;
                var parts = new ArrayList<Span>();
                while (j < line.size() && line.get(j).inRun()) parts.add(line.get(j++));
                flush(out, plain, ids);
                out.add(run(parts, true, ids));
                i = j;
                continue;
            }

            boolean bold = s.emphasis().equals("strong");
            if (s.kind().equals("ruby")) {
                flush(out, plain, ids);
                out.add(new Square.Zi(s.text(), s.reading(), bold));
                i++;
                continue;
            }

            scan(s.text(), bold, out, plain, ids);
            i++;
        }

        flush(out, plain, ids);
        return out;
    }

    private static Square.Run run(List<Span> parts, boolean marked, int[] ids) {
        String text = parts.stream().map(Span::text).reduce("", String::concat);
        return new Square.Run(parts, marked, SquareWidth.squares(text), ids[0]++, false);
    }

    /** Character by character: a 字, a mark, or something to collect. */
    private static void scan(String text, boolean bold, List<Square> out,
                             StringBuilder plain, int[] ids) {
        for (int i = 0; i < text.length(); ) {
            int cp = text.codePointAt(i);
            i += Character.charCount(cp);
            String ch = new String(Character.toChars(cp));

            if (Character.UnicodeScript.of(cp) == Character.UnicodeScript.HAN) {
                flush(out, plain, ids);
                out.add(new Square.Zi(ch, "", bold));
                continue;
            }
            if (MARKS.contains(ch)) {
                flush(out, plain, ids);
                mark(out, ch);
                continue;
            }
            // Latin, digits, and the punctuation that belongs to them: a full
            // stop in "Dust II." is part of the Latin, not a Chinese mark.
            plain.append(ch);
        }
    }

    /**
     * A mark, sharing the previous square when the two belong in one.
     *
     * <p>Same class only. {@code ”，} is two closing marks and shares; a
     * {@code “} opening the next quotation starts its own square, because it
     * belongs to what comes after it rather than to what came before.</p>
     */
    private static void mark(List<Square> out, String ch) {
        if (!out.isEmpty()
                && !STANDING.contains(ch)
                && out.get(out.size() - 1) instanceof Square.Punct prev
                && !STANDING.contains(prev.first())
                && prev.marks().length() < MAX_MARKS
                && sameClass(prev.first(), ch)) {
            out.set(out.size() - 1, new Square.Punct(prev.marks() + ch, false));
            return;
        }
        out.add(new Square.Punct(ch, false));
    }

    private static boolean sameClass(String a, String b) {
        return (Cells.CLOSING.contains(a) && Cells.CLOSING.contains(b))
            || (Cells.OPENING.contains(a) && Cells.OPENING.contains(b));
    }

    /**
     * The non-Chinese text collected so far, as a run.
     *
     * <p>A run whether or not the author marked one, because the width is not
     * a matter of opinion: {@code markdown} is eight letters and needs the
     * squares eight letters need. What the marking changes is what a workbench
     * can say about it.</p>
     *
     * <p>Stripped at the ends. The space between a character and a Latin word
     * is not a square — it is the gap that already exists between two
     * squares.</p>
     */
    private static void flush(List<Square> out, StringBuilder plain, int[] ids) {
        String text = take(plain).strip();
        if (text.isEmpty()) return;
        out.add(run(List.of(new Span("text", text, "", "", false)), false, ids));
    }

    private static String take(StringBuilder sb) {
        String s = sb.toString();
        sb.setLength(0);
        return s;
    }

    // ── Cutting a run ──────────────────────────────────────────────────

    /** One character of a run, with the emphasis it was written under. */
    private record Ch(String text, String emphasis) {}

    private static List<Ch> flatten(List<Span> parts) {
        var out = new ArrayList<Ch>();
        for (Span s : parts) {
            String t = s.text();
            for (int i = 0; i < t.length(); ) {
                int cp = t.codePointAt(i);
                i += Character.charCount(cp);
                out.add(new Ch(new String(Character.toChars(cp)), s.emphasis()));
            }
        }
        return out;
    }

    private static String textOf(List<Ch> chars, int from, int to) {
        var sb = new StringBuilder();
        for (int i = from; i < to; i++) sb.append(chars.get(i).text());
        return sb.toString();
    }

    /** Rebuilds spans from a slice, merging characters that share an emphasis. */
    private static List<Span> spansOf(List<Ch> chars, int from, int to, String extra) {
        var out = new ArrayList<Span>();
        var run = new StringBuilder();
        String weight = null;
        for (int i = from; i < to; i++) {
            Ch c = chars.get(i);
            if (weight == null) weight = c.emphasis();
            if (!c.emphasis().equals(weight)) {
                out.add(new Span("text", take(run), "", weight, true));
                weight = c.emphasis();
            }
            run.append(c.text());
        }
        // The hyphen joins the last piece, so it is emphasised the way the
        // characters beside it are rather than standing out on its own.
        if (!extra.isEmpty()) run.append(extra);
        if (run.length() > 0) {
            out.add(new Span("text", take(run), "", weight == null ? "" : weight, true));
        }
        return out;
    }
}
