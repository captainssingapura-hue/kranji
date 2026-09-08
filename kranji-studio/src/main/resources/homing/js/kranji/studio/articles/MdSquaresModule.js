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
// Every mark has a square, the way it does on 稿纸 - and where two would leave
// a hole in the line they share one. A mark that finds no room hangs past the
// right edge rather than opening the next row.
//
// The thing worth looking at here is a run - a ‹…› sequence the author wrapped.
// It claims several squares and gets them as one head plus placeholders,
// because RelationGrid cannot merge cells yet - so the placeholders are drawn
// as marked blanks. What they will become is one wide cell; what they must
// never look like is empty page.
//
// No CJK literal appears in this file. Every character it draws came from a
// draft on disk.
// =============================================================================

/**
 * opts = {
 *   css,
 *   classes: {  // sheet, row, sq, zi, ann, bold,
 *               // punct, pack, half, hang, marker, run,
 *               // runText, cont, indent, pad, tag
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

    // ── What a square can hold ──────────────────────────────────────────

    function zi(branch, row, s) {
        var b = box(branch, row, s.b ? [C.bold] : []);
        // The reading sits above the character INSIDE the box, not as ruby -
        // ruby widens what it sits on, and a square that grew to fit its pinyin
        // would stop being square.
        b.appendChild(el(branch, 'div', C.ann, s.r || ''));
        b.appendChild(el(branch, 'div', C.zi, s.t));
    }

    /**
     * A square of punctuation.
     *
     * <p>One mark fills it. Two or three share it, each squeezed to its share
     * of the width - which is what a person does on paper, and why the packing
     * happened at all.</p>
     *
     * <p>A hanging square sits past the right edge: narrower, no ruling of its
     * own, so the row's last cell simply reads as a little wider.</p>
     */
    function punct(branch, row, s) {
        var classes = [C.punct];
        if (s.hang) classes.push(C.hang);
        var b = box(branch, row, classes);
        if (!s.n) { b.appendChild(el(branch, 'div', C.zi, s.t)); return; }

        var pack = el(branch, 'div', C.pack, null);
        var marks = Array.from(s.t);
        for (var i = 0; i < marks.length; i++) {
            pack.appendChild(el(branch, 'span', C.half, marks[i]));
        }
        b.appendChild(pack);
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
     */
    function run(branch, row, s) {
        var b = box(branch, row, [C.run]);
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
            case 'z': zi(branch, row, s); return;
            case 's': punct(branch, row, s); return;
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
                var filled = 0;
                for (var i = 0; i < squares.length; i++) {
                    square(branch, line, squares[i]);
                    if (!squares[i].hang) filled++;
                }
                // Squares past the end of a short line are padding rather than
                // cells - a poem's shape is the absence of content, and the
                // model says so by leaving the row short. What hangs past the
                // right edge was never a position, so it is not counted here.
                for (var pad = filled; pad < columns; pad++) box(branch, line, [C.pad]);
                sheet.appendChild(line);
            }
            host.appendChild(sheet);
        }
    };
}
