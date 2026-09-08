// =============================================================================
// MdSquaresModule — a plan, drawn as the page of squares it describes.
//
// The document view answers "does this say what its author meant". This one
// answers the next question: what the reader will do with it. Same draft, same
// response, two renderings - which is the whole reason the parser hands over
// structure rather than markup.
//
// It is a grid of divs rather than a RelationGrid, on purpose. Stage two is for
// settling the arrangement, and doing that against a plain grid keeps the two
// questions apart: whether the SIZES are right, and whether the grid substrate
// can draw them. Stage three swaps the substrate and the plan does not move.
//
// The thing worth looking at here is a run - any non-Chinese sequence, whether
// or not its author wrapped it. It claims several squares and gets
// them as one head plus placeholders, because RelationGrid cannot merge cells
// yet - so the placeholders are drawn as marked blanks. What they will become
// is one wide cell; what they must never look like is empty page.
//
// No CJK literal appears in this file. Every character it draws came from a
// draft on disk.
// =============================================================================

/**
 * opts = {
 *   css,
 *   classes: {  // sheet, row, sq, zi, ann, punct, lead, bold,
 *               // marker, run, loose, runText, cont, indent, pad, tag
 *   }
 * }
 * Returns { draw(branch, host, plan) }.
 */
function createMdSquares(opts) {

    var C = opts.classes;
    var minted = 0;

    function el(branch, tag, cls, text) {
        var e = branch.createElement('s' + (++minted), tag);
        if (cls) opts.css.setClass.apply(opts.css, [e].concat(cls));
        if (text) e.textContent = text;
        return e;
    }

    // Every square is the same box whatever is in it. That is the point of a
    // grid: the column is the unit, and nothing inside a cell may set its width.
    function box(branch, row, classes) {
        var b = el(branch, 'div', [C.sq].concat(classes || []), null);
        row.appendChild(b);
        return b;
    }

    // ── The four things a square can be ─────────────────────────────────

    function zi(branch, row, s) {
        var b = box(branch, row, s.b ? [C.bold] : []);
        // The reading sits above the character INSIDE the box, not as ruby -
        // ruby widens what it sits on, and a square that grew to fit its pinyin
        // would stop being square.
        b.appendChild(el(branch, 'div', C.ann, s.r || ''));
        b.appendChild(el(branch, 'div', C.zi, s.t));
        // Punctuation rides in a corner and claims no width, which is 禁则
        // handled by the cell rather than by a line-breaking rule.
        if (s.lp) b.appendChild(el(branch, 'div', C.lead, s.lp));
        if (s.p) b.appendChild(el(branch, 'div', C.punct, s.p));
    }

    function marker(branch, row, s) {
        box(branch, row, [C.marker]).appendChild(el(branch, 'div', C.zi, s.t));
    }

    /**
     * A run's head: the text, and how many squares it asked for.
     *
     * <p>The text overflows its own box on purpose. It is supposed to occupy
     * the placeholders beside it, and letting it spill across them is the
     * closest a grid without merged cells can get to showing that.</p>
     *
     * <p>Tinted only when the author marked it. A run this had to infer is left
     * plain and wears a dotted underline, because that is a place the author has
     * not yet said whether these letters are one thing.</p>
     */
    function run(branch, row, s) {
        var b = box(branch, row, [s.m ? C.run : C.loose]);
        var text = el(branch, 'div', C.runText, null);
        for (var i = 0; i < (s.parts || []).length; i++) {
            var p = s.parts[i];
            var tag = p.e === 'strong' ? 'strong' : p.e === 'em' ? 'em' : 'span';
            text.appendChild(el(branch, tag, null, p.t));
        }
        b.appendChild(text);
        // What it claimed, and whether the page was too narrow to give it.
        b.appendChild(el(branch, 'div', C.tag, s.cut ? s.w + '✂' : String(s.w)));
    }

    function square(branch, row, s) {
        switch (s.k) {
            // A character and a sign are the same box with the same corners.
            // The only difference is that one has a reading and one never will.
            case 'z': zi(branch, row, s); return;
            case 's': zi(branch, row, s); return;
            case 't': marker(branch, row, s); return;
            case 'r': run(branch, row, s); return;
            // Claimed by the run before it. Marked, never blank: an empty box
            // here would read as page the author left alone.
            case 'c': box(branch, row, [C.cont]); return;
            // 首行缩进两格 - empty, and empty is what it means.
            default:  box(branch, row, [C.indent]); return;
        }
    }

    return {

        /**
         * Draws the whole plan. Appends, never clears — see MdPreviewModule.
         */
        draw: function (branch, host, plan) {
            var sheet = el(branch, 'div', C.sheet, null);
            var rows = (plan && plan.rows) || [];
            var columns = (plan && plan.columns) || 0;

            for (var r = 0; r < rows.length; r++) {
                var line = el(branch, 'div', C.row, null);
                var squares = rows[r].squares || [];
                for (var i = 0; i < squares.length; i++) square(branch, line, squares[i]);
                // Squares past the end of a short line are padding rather than
                // cells - a poem's shape is the absence of content, and the
                // model says so by leaving the row short.
                for (var pad = squares.length; pad < columns; pad++) {
                    box(branch, line, [C.pad]);
                }
                sheet.appendChild(line);
            }
            host.appendChild(sheet);
        }
    };
}
