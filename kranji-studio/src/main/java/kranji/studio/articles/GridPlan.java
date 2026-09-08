package kranji.studio.articles;

import java.util.List;

/**
 * A document laid out as squares.
 *
 * <p>Rows and a column count, which is what a relation is: {@code RelationGrid}
 * asks for pks, columns and a value at each crossing, and this is the same
 * shape with the arrangement already decided. Stage two draws it as a table;
 * stage three hands it to the grid once merged cells exist, and the model does
 * not move between them.</p>
 *
 * <h2>Rows are as long as their content</h2>
 *
 * <p>A short row is short. The empty squares at the end of a line are the
 * renderer's padding out to {@link #columns()}, not cells in the model — a
 * poem's shape is the absence of squares, and inventing them here would make
 * every row the same length and lose that.</p>
 *
 * <p>The exception is a {@link Square.Cont}, which <em>is</em> in the model: it
 * is a square a run has claimed, not one nothing reached.</p>
 */
public record GridPlan(int columns, List<Row> rows) {

    public GridPlan {
        rows = List.copyOf(rows);
    }

    /**
     * One line of squares.
     *
     * @param kind    the block it came from — {@code title}, {@code heading},
     *                {@code p}, {@code li}, {@code oli}, {@code quote},
     *                {@code verse}
     * @param block   that block's index, so a renderer can tell a wrapped
     *                paragraph from two paragraphs
     * @param line    which row of that block this is, counting from zero. A
     *                paragraph's first row is the one carrying the indent
     * @param squares what is written in it
     */
    public record Row(String kind, int block, int line, List<Square> squares) {

        public Row {
            squares = List.copyOf(squares);
        }

        /**
         * Grid positions used.
         *
         * <p>Exactly the number of squares, because a run's head takes one
         * position and its claim is made up by the {@link Square.Cont}s
         * following it. {@link Square#width()} is what the wrapper budgets
         * with; this is what the row actually occupies.</p>
         */
        public int used() { return squares.size(); }
    }

    /** How many rows the whole document came to. */
    public int height() { return rows.size(); }
}
