package kranji.studio.articles;

import kranji.studio.articles.MdDocument.Span;

import java.util.List;

/**
 * One square of the page.
 *
 * <h2>Why this is not {@code kranji.reading.model.Cell}</h2>
 *
 * <p>That one is sealed over a character and a plain stretch, and neither
 * carries a width — every cell it can describe is exactly one square wide. A
 * {@code ‹…›} run is not: {@code Apartments} is one typographic unit that needs
 * several squares, and saying so is the whole point of the extension.</p>
 *
 * <p>Widening the reading model would mean a third {@code Token} case and a new
 * arm in every switch the reader has, on behalf of a format the reader does not
 * yet serve. The design note argues for that eventually. It is not this
 * change.</p>
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
     * A character, and everything glued to it.
     *
     * @param zi      the character
     * @param reading the reading its author pinned, or empty
     * @param lead    an opening mark that must not end a line — 「（《
     * @param tail    a closing mark that must not begin one — 。，、！？
     * @param bold    inside {@code **…**}. There is no italic here: the parser
     *                drops it over Chinese, for the reason E5 gives
     */
    record Zi(String zi, String reading, String lead, String tail, boolean bold)
            implements Square {}

    /**
     * The planner's own mark: a bullet, a list number.
     *
     * <p>The only square that holds text and is not from the document. Always
     * one square — a marker that needed two would be a marker nobody would
     * write.</p>
     */
    record Marker(String text) implements Square {}

    /**
     * A character that stands in a square of its own.
     *
     * <p>Chinese punctuation that is not in the 禁则 tables and so has nowhere
     * to ride: 破折号 —, 波浪号 ～, 间隔号 ·. Each is full-width and each takes
     * one square, which means 破折号 written properly as {@code ——} is two
     * squares and needs no special handling at all.</p>
     *
     * <p>They are the exception to <i>everything non-Chinese belongs inside
     * {@code ‹…›}</i>. Requiring an author to write {@code ‹——›} would be
     * asking them to mark Chinese punctuation as foreign. The list is
     * {@link GridPlanner#SIGNS} and it is short on purpose: a character earns
     * a place on it by being Chinese punctuation that occupies exactly one
     * square, not by being convenient.</p>
     *
     * @param lead an opening mark riding in its corner, as on a {@link Zi}
     * @param tail a closing mark riding in the other one
     */
    record Sign(String text, String lead, String tail) implements Square {}

    /**
     * The head of a non-Chinese sequence.
     *
     * <p>Both kinds. {@code ‹Tunnel›} is one, and so is a bare {@code markdown}
     * an author did not wrap — because the square it would otherwise get is one
     * square, and eight letters do not fit in one square. That was the bug this
     * case was widened to fix: only marked runs claimed width, so unmarked
     * Latin overflowed silently into the characters beside it.</p>
     *
     * @param parts  the sequence's own spans, so the emphasis inside it
     *               survives — {@code ‹Tunnel bla *bla*›} is three pieces
     * @param marked whether the author wrote {@code ‹…›} around it. It changes
     *               nothing about the arrangement and everything about what a
     *               workbench should point at: an unmarked run is a place the
     *               author has not yet said what they meant
     * @param width  squares claimed, from {@link SquareWidth}
     * @param id     ties this head to its {@link Cont} squares. Unique in a plan
     * @param broken cut from a longer run that could not fit a line at all, and
     *               therefore carrying a hyphen the author did not write
     */
    record Run(List<Span> parts, boolean marked, int width, int id, boolean broken)
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
