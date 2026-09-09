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
// A 字 is a square and so is a mark. Everything else - a word, a number, a
// symbol - is a RUN, and a run is one square whatever its length.
//
// There used to be a great deal more: marks shared a box the way ”。 do on
// 稿纸, a mark with no room hung past the right margin, and a run claimed
// several squares from a width table with placeholders behind it. All gone,
// and with it the reason an author had to wrap anything in ‹…›.
//
// No CJK literal appears in this file. Every character it draws came from a
// draft on disk.
// =============================================================================

/**
 * opts = {
 *   css,
 *   classes: { sheet, row, sq, zi, ann, bold, punct, run, marker,
 *              indent, pad }
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

    // A run of anything that is not Chinese, in ONE square whatever its length.
    // A foreign word is one thing to read rather than ten characters to
    // practise, and a box each would say it was a 字.
    //
    // So the square is a placeholder: the first character and an ellipsis, with
    // the whole word on the cell's title so a hover says what it is. Three
    // blank boxes in a sentence would be unreadable - which is the whole reason
    // the first character is shown rather than nothing.
    //
    // Temporary, and known to be. The arrangement this is standing in for is a
    // run spanning as many merged cells as it has characters; RelationGrid
    // cannot merge yet, and when it can this is where that choice is made.
    function run(branch, row, s) {
        var b = box(branch, row, s.b ? [C.run, C.bold] : [C.run]);
        var text = String(s.t || '');
        b.title = text;
        var chars = Array.from(text);
        b.appendChild(el(branch, 'div', C.zi,
                chars.length > 1 ? chars[0] + '…' : text));
    }

    function punct(branch, row, s) {
        box(branch, row, [C.punct]).appendChild(el(branch, 'div', C.zi, s.t));
    }

    function marker(branch, row, s) {
        box(branch, row, [C.marker]).appendChild(el(branch, 'div', C.zi, s.t));
    }

    function square(branch, row, s) {
        switch (s.k) {
            case 'z': zi(branch, row, s); return;
            case 'r': run(branch, row, s); return;
            case 's': punct(branch, row, s); return;
            case 't': marker(branch, row, s); return;
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
                for (var pad = squares.length; pad < columns; pad++) box(branch, line, [C.pad]);
                sheet.appendChild(line);
            }
            host.appendChild(sheet);
        }
    };
}
