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
 * <p><b>Cells.</b> A character is a square; a closing mark rides in the corner
 * of the character before it and an opening mark in the corner of the one
 * after. That is 禁则 handled structurally — a row cannot break inside a
 * square, so 。 can never begin a line without anybody writing a line-breaking
 * rule. The tables are {@link Cells#CLOSING} and {@link Cells#OPENING}, taken
 * from the reading model rather than copied, because two lists that must agree
 * should be one list.</p>
 *
 * <p><b>Runs.</b> A {@code ‹…›} run is one typographic unit that needs several
 * squares. It becomes a {@link Square.Run} carrying its width and as many
 * {@link Square.Cont} placeholders as it claimed — see {@link Square.Cont} for
 * why placeholders rather than one wide cell.</p>
 *
 * <p><b>Wrapping.</b> Prose wraps at the column count; verse does not, because
 * a poem's lines are the author's. A run is never split across a row boundary
 * unless it could not fit on a row at all.</p>
 *
 * <h2>Where a hyphen comes from</h2>
 *
 * <p>Only from the last case. A run that does not fit in what is left of a row
 * moves to the next row whole — which is what Chinese typesetting does with a
 * Latin word, and it needs no hyphen. A run wider than the entire row has
 * nowhere to move to, so it is cut and the pieces carry a hyphen.</p>
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

    /** A fragment shorter than this is not worth leaving at the end of a row. */
    private static final int MIN_FRAGMENT = 2;

    private static final String BULLET = "•";

    /**
     * Characters that need no {@code ‹…›} and take one square each.
     *
     * <p>The exception to <i>everything non-Chinese belongs inside a run</i>,
     * and the reason there has to be one: these are Chinese punctuation. The
     * 禁则 tables cover the marks that ride in a character's corner; these have
     * nowhere to ride and are full-width, so they stand in a square.</p>
     *
     * <pre>
     *   —  U+2014  破折号 — written ——, and therefore two squares
     *   ―  U+2015  the same mark, the other codepoint
     *   ～  U+FF5E  波浪号 — 三～五
     *   ·  U+00B7  间隔号 — 麦克·乔丹
     *   ／  U+FF0F  full-width solidus
     * </pre>
     *
     * <p>Short on purpose. A character earns a place by being Chinese
     * punctuation that occupies exactly one square — not by being common.
     * Digits are the case that tests the rule and are deliberately absent:
     * {@code 1999} is four characters and one word, and on 稿纸 numerals are
     * written two to a square, which is a convention this does not yet
     * implement. They stay runs until it does.</p>
     */
    public static final String SIGNS = "—―～·／";

    /**
     * The half of {@link Cells#CLOSING} that separates rather than closes.
     *
     * <p>Both halves must not begin a line, which is why they share a table.
     * They differ in what they belong to: 「）」closes the thing before it and
     * is part of it, so {@code （Premier）} is one run with both brackets in it.
     * 「、」 separates two things and is part of neither, so it ends a run
     * instead of joining it.</p>
     *
     * <p>Without the split, {@code Ancient、Anubis、Cache、Dust II、…} was a
     * single run fifty characters wide — nineteen of the twenty squares on a
     * row, and hyphenated through the middle of map names at any narrower
     * width.</p>
     */
    private static final String SEPARATORS = "。，、！？：；…";

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

        private int used() { return cur.size(); }
        private int left() { return columns - used(); }

        void open(List<Square> opener) { cur.addAll(opener); }

        void add(Square sq) {
            if (sq instanceof Square.Run run) { run(run); return; }
            if (left() < 1) newRow();
            cur.add(sq);
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

    private static final String HYPHEN = "-";

    // ── Squares from spans ─────────────────────────────────────────────

    /**
     * One line's spans, as squares.
     *
     * <p>Punctuation reaches across span boundaries on purpose: {@code
     * **很重要**，} puts the comma in 要's square, and the comma is in a
     * different span from the character it belongs to.</p>
     */
    private static List<Square> squaresOf(List<Span> line, int[] ids) {
        var out = new ArrayList<Square>();
        var plain = new StringBuilder();
        var lead = new StringBuilder();

        for (int i = 0; i < line.size(); ) {
            Span s = line.get(i);

            if (s.inRun()) {
                int j = i;
                var parts = new ArrayList<Span>();
                while (j < line.size() && line.get(j).inRun()) parts.add(line.get(j++));
                flush(out, plain, lead, ids);
                out.add(run(parts, true, ids));
                i = j;
                continue;
            }

            boolean bold = s.emphasis().equals("strong");
            if (s.kind().equals("ruby")) {
                flush(out, plain, lead, ids);
                out.add(new Square.Zi(s.text(), s.reading(), take(lead), "", bold));
                i++;
                continue;
            }

            scan(s.text(), bold, out, plain, lead, ids);
            i++;
        }

        flush(out, plain, lead, ids);
        // An opening mark with nothing after it at all. Rare, and its own
        // square rather than dropped - the author wrote it.
        if (lead.length() > 0) out.add(new Square.Sign(take(lead), "", ""));
        return out;
    }

    private static Square.Run run(List<Span> parts, boolean marked, int[] ids) {
        String text = parts.stream().map(Span::text).reduce("", String::concat);
        return new Square.Run(parts, marked, SquareWidth.squares(text), ids[0]++, false);
    }

    /** The 禁则 walk, character by character. */
    private static void scan(String text, boolean bold, List<Square> out,
                             StringBuilder plain, StringBuilder lead, int[] ids) {
        for (int i = 0; i < text.length(); ) {
            int cp = text.codePointAt(i);
            i += Character.charCount(cp);
            String ch = new String(Character.toChars(cp));

            if (Character.UnicodeScript.of(cp) == Character.UnicodeScript.HAN) {
                flush(out, plain, lead, ids);
                out.add(new Square.Zi(ch, "", take(lead), "", bold));
                continue;
            }
            if (Cells.CLOSING.contains(ch)) {
                // A bracket closes the run it opened and belongs inside it.
                // A separator separates two things and belongs to neither, so
                // it ends the run rather than being swallowed by it - which is
                // what kept Ancient、Anubis、Cache、… as one run fifty
                // characters long.
                if (SEPARATORS.contains(ch)) flush(out, plain, lead, ids);
                if (plain.isEmpty()) {
                    if (attach(out, ch)) continue;
                    // Nothing to ride on: a line opening with 。, or a mark
                    // after a run. Its own square rather than dropped.
                    out.add(new Square.Sign(ch, take(lead), ""));
                    continue;
                }
                plain.append(ch);
                continue;
            }
            if (Cells.OPENING.contains(ch)) {
                flush(out, plain, lead, ids);
                lead.append(ch);
                continue;
            }
            // Chinese punctuation with nowhere to ride. Its own square, and
            // no ‹…› asked of the author - see SIGNS.
            if (SIGNS.contains(ch)) {
                flush(out, plain, lead, ids);
                out.add(new Square.Sign(ch, take(lead), ""));
                continue;
            }
            plain.append(ch);
        }
    }

    /**
     * Adds a closing mark to the last square, when that square can carry one.
     *
     * <p>A {@link Square.Sign} can, as well as a {@link Square.Zi}: 破折号 is a
     * full square with two free corners like any other, and 。 after {@code ——}
     * has the same reason to ride there as 。 after a character — it must not
     * be what begins the next line.</p>
     */
    private static boolean attach(List<Square> out, String mark) {
        if (out.isEmpty()) return false;
        int last = out.size() - 1;
        if (out.get(last) instanceof Square.Zi zi) {
            out.set(last, new Square.Zi(zi.zi(), zi.reading(), zi.lead(),
                                        zi.tail() + mark, zi.bold()));
            return true;
        }
        if (out.get(last) instanceof Square.Sign sign) {
            out.set(last, new Square.Sign(sign.text(), sign.lead(), sign.tail() + mark));
            return true;
        }
        return false;
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
     * is not a square — it is the gap that already exists between two squares.
     * Spaces inside the run are its own and stay.</p>
     *
     * <h2>A pending opening mark joins the run it opened</h2>
     *
     * <p>{@code （Premier）} is one bracketed thing, and both brackets belong to
     * it. Without this, the {@code （} waited for a character to ride on and
     * found the one <em>after</em> the run — so 的现役地图池 in
     * {@code （Premier）的现役地图池} was drawn as 「（的」and the bracket ended up
     * on the wrong side of the run it opened.</p>
     *
     * <p>It waits only when there is nothing to join. A blank {@code plain}
     * leaves the mark pending, so {@code （你好）} still puts the bracket in
     * 你's corner where it belongs.</p>
     */
    private static void flush(List<Square> out, StringBuilder plain,
                              StringBuilder lead, int[] ids) {
        String text = take(plain).strip();
        if (text.isEmpty()) return;
        out.add(run(List.of(new Span("text", take(lead) + text, "", "", false)), false, ids));
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
        if (run.length() > 0) out.add(new Span("text", take(run), "", weight == null ? "" : weight, true));
        return out;
    }
}
