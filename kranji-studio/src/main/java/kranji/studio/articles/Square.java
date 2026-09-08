package kranji.studio.articles;

import kranji.studio.articles.MdDocument.Span;

import java.util.List;

/**
 * One square of the page.
 *
 * <h2>Punctuation has squares of its own</h2>
 *
 * <p>It did not always. A closing mark used to ride in the corner of the
 * character before it and an opening mark in the corner of the one after, which
 * made 禁则 free: a row cannot break inside a square, so 。 could not begin a
 * line without anybody writing a line-breaking rule.</p>
 *
 * <p>That was clever and it was inconsistent. It made a mark's square depend on
 * what happened to be beside it — 。 after a character was invisible in the
 * model, 。 after a Latin run was a square, and 。 opening a line was a third
 * thing. On 稿纸 every mark occupies a square, and now so does every mark
 * here.</p>
 *
 * <p>禁则 is a real rule again as a result. {@code GridPlanner} binds a closing
 * mark to what precedes it and an opening mark to what follows, and places the
 * binding whole — so a mark still cannot begin or end a row, but because
 * something says so rather than because the model could not express it.</p>
 *
 * <h2>Why this is not {@code kranji.reading.model.Cell}</h2>
 *
 * <p>That one is sealed over a character and a plain stretch, and neither
 * carries a width — every cell it can describe is exactly one square wide. A
 * {@code ‹…›} run is not: {@code Apartments} is one typographic unit that needs
 * several squares, and saying so is the whole point of the extension.</p>
 *
 * <h2>The two blanks are different blanks</h2>
 *
 * <p>{@link Indent} is 首行缩进两格 — two squares an author left empty on
 * purpose, which is what a paragraph looks like on squared paper. {@link Cont}
 * is a square a run has claimed and cannot yet fill, because RelationGrid has
 * no merged cells. They look similar and mean opposite things: one is content,
 * the other is a placeholder waiting for a framework feature.</p>
 *
 * <p>A row is only as long as what is in it. Squares past the end of a short
 * line are the renderer's padding, not a case here — a poem's shape is the
 * absence of cells, not a run of empty ones.</p>
 */
public sealed interface Square {

    /** How many squares this claims. One, except on a {@link Run}. */
    default int width() { return 1; }

    /**
     * Whether this is hanging past the right edge of its row.
     *
     * <p>Only punctuation ever does. It is how a mark that would otherwise
     * begin a line stays on the one before it — see {@link Punct}.</p>
     */
    default boolean hanging() { return false; }

    /**
     * A character.
     *
     * <p>Nothing rides on it any more. It used to carry the punctuation glued
     * to either corner; punctuation has its own squares now, so a character is
     * a character and its reading.</p>
     *
     * @param bold inside {@code **…**}. There is no italic: the parser drops it
     *             over Chinese, for the reason E5 gives
     */
    record Zi(String zi, String reading, boolean bold) implements Square {}

    /**
     * One square of punctuation.
     *
     * <h2>More than one mark in a square</h2>
     *
     * <p>Which is what a person does on 稿纸: {@code ”，} and {@code ”。} go in
     * one box, because two boxes there leaves a hole in the line. Adjacent
     * marks of the same class share a square, up to
     * {@code GridPlanner.MAX_MARKS} — two is the ordinary case and three is the
     * squeeze.</p>
     *
     * <p>A standing mark never shares. 破折号 and 省略号 are written double,
     * {@code ——} and {@code ……}, and each half is a full square; packing them
     * would turn a dash into a hyphen.</p>
     *
     * <h2>Hanging</h2>
     *
     * <p>A mark must not begin a line. When one lands where a row has no room
     * left, it does not push a character down and it does not squeeze into the
     * character's box — it <b>hangs past the right edge</b>, and the row's last
     * cell simply runs a little wider to take it.</p>
     *
     * <p>That is not a trick invented here. It is what you do writing a
     * composition when a full stop arrives at the margin: you carry on past the
     * ruling rather than starting the next line with it. It also happens to
     * make 禁则 free again without any of the machinery pushing would need —
     * and it keeps {@code ——} together, since the second dash hangs beside the
     * first rather than opening the next row.</p>
     *
     * @param marks   one to three characters, in the order they were written
     * @param hanging placed past the right edge of its row
     */
    record Punct(String marks, boolean hanging) implements Square {

        /** The mark that decides which class this square belongs to. */
        public String first() {
            return marks.isEmpty() ? "" : marks.substring(0, 1);
        }

        /** The mark a following one would have to pack with. */
        public String last() {
            return marks.isEmpty() ? "" : marks.substring(marks.length() - 1);
        }

        /** Whether more than one mark is sharing this square. */
        public boolean packed() { return marks.length() > 1; }
    }

    /**
     * The planner's own mark: a bullet, a list number.
     *
     * <p>The only square that holds text and is not from the document.</p>
     */
    record Marker(String text) implements Square {}

    /**
     * The head of a non-Chinese sequence.
     *
     * <p>Always one the author wrote. {@code ‹Tunnel›} is a run; a bare
     * {@code markdown} is an error, because a square holds one character and
     * nobody but the author can say that eight letters are one word.</p>
     *
     * <p>Its own punctuation stays inside it. A full stop in {@code Dust II.}
     * is Latin punctuation belonging to Latin text; the marks that get squares
     * of their own are the full-width ones.</p>
     *
     * @param parts  the sequence's own spans, so the emphasis inside it
     *               survives — {@code ‹Tunnel bla *bla*›} is three pieces
     * @param width  squares claimed, from {@link SquareWidth}
     * @param id     ties this head to its {@link Cont} squares. Unique in a plan
     * @param broken cut from a longer run that could not fit a line at all, and
     *               therefore carrying a hyphen the author did not write
     */
    record Run(List<Span> parts, int width, int id, boolean broken)
            implements Square {

        public Run {
            parts = List.copyOf(parts);
        }

        /** What the run says, emphasis flattened away. */
        public String text() {
            return parts.stream().map(Span::text).reduce("", String::concat);
        }
    }

    /**
     * A square a {@link Run} claimed and cannot fill.
     *
     * <p>It exists because RelationGrid builds one element per column and has
     * no span or merge: {@code RelGridCellsModule} makes a div per column and
     * {@code RelGridLayoutModule} sizes them through {@code <col>}. Until that
     * changes, a run four squares wide is one square with the text and three
     * of these, drawn as marked blanks so the claim is visible.</p>
     *
     * <p>When merged cells arrive these collapse into the head and nothing else
     * about the model moves — which is why the width lives on the head rather
     * than being implied by counting them.</p>
     */
    record Cont(int id) implements Square {}

    /** 首行缩进两格 — a square the author left empty to open a paragraph. */
    record Indent() implements Square {}
}
