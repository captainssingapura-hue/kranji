package kranji.studio.articles;

/**
 * One square of the page, holding exactly one character.
 *
 * <h2>One character, one square — including the marks</h2>
 *
 * <p>That is the whole rule, and three earlier ones went to make room for it.
 * Punctuation used to share a box the way {@code ”，} does on 稿纸; a mark with
 * no room used to hang past the right margin; and a {@code ‹…›} run used to be
 * one typographic unit claiming several squares at once, with placeholders
 * behind it because RelationGrid has no merged cells.</p>
 *
 * <p>Each of those was defensible on its own and together they were most of the
 * model. What replaced them costs nothing: a square holds a character, the
 * arrangement is a count, and a row is full when it has as many as it has
 * columns.</p>
 *
 * <h2>What the author no longer has to say</h2>
 *
 * <p>{@code ‹…›} used to be compulsory around anything not Chinese, and the
 * reason was layout: a square held one character, so somebody had to decide
 * that {@code markdown} was one word rather than eight things, and only the
 * author could. Now nothing needs to know. {@code markdown} is eight squares
 * because it is eight characters, and the brackets are optional markup that
 * only says where italic applies.</p>
 *
 * <h2>禁则 survives, and now has to be written down</h2>
 *
 * <p>Hanging used to make half of it free — a mark that would begin a line hung
 * off the previous one instead, so it could not. With every mark in a square of
 * its own that is gone, and {@link GridPlanner} states the rule outright: a
 * closing mark brings the character before it down rather than starting a row,
 * and an opening mark is carried down rather than ending one.</p>
 *
 * <h2>A row is only as long as what is in it</h2>
 *
 * <p>Squares past the end of a short line are the renderer's padding, not a
 * case here — a poem's shape is the absence of cells, not a run of empty
 * ones.</p>
 */
public sealed interface Square {

    /**
     * A Chinese character: the thing the grid exists to practise.
     *
     * @param bold inside {@code **…**}. There is no italic: the parser drops it
     *             over Chinese, for the reason E5 gives
     */
    record Zi(String zi, String reading, boolean bold) implements Square {}

    /**
     * A stretch that is not Chinese — a word, a number, a symbol.
     *
     * <p><b>One square, however long it is.</b> {@code Apartments} is one
     * square and so is {@code T}. A foreign word is one thing to read rather
     * than ten characters to practise, and the grid exists for the practising:
     * giving each letter a box says it is a 字, which it is not.</p>
     *
     * <p>What the square shows is therefore a placeholder — the first character
     * and an ellipsis — with the whole of {@link #text} on the cell, so a hover
     * or a selection can say what the word is. That is a <b>temporary</b>
     * answer and known to be one. The eventual arrangement is a run spanning as
     * many merged cells as it has characters, which RelationGrid cannot do
     * yet; when it can, the width becomes a rendering choice and nothing about
     * this record has to move.</p>
     *
     * <p>Detecting the run is deliberately independent of either. It is a fact
     * about the document — a maximal stretch that is neither Han nor a mark —
     * and the last model got into trouble by making it a fact about pixels
     * instead.</p>
     *
     * <p>Internal spacing is kept and the ends are stripped: {@code Dust II} is
     * one run holding its own space, because that space is inside the word's
     * own typography. The space between a run and the character beside it is
     * not — it is the gap two squares already have.</p>
     */
    record Run(String text, boolean bold) implements Square {}

    /**
     * One punctuation mark.
     *
     * <p>Singular now. Marks used to double and triple up in a box — {@code ”。}
     * together, because two boxes there leaves a hole in the line — and that is
     * a real practice on 稿纸, but it made a mark's square depend on what
     * happened to be beside it. One mark, one square, and the line has a hole in
     * it exactly where the author put one.</p>
     */
    record Punct(String mark) implements Square {}

    /**
     * The planner's own mark: a bullet, a list number.
     *
     * <p>The only square that holds text and is not from the document.</p>
     */
    record Marker(String text) implements Square {}

    /** 首行缩进两格 — a square the author left empty to open a paragraph. */
    record Indent() implements Square {}
}
