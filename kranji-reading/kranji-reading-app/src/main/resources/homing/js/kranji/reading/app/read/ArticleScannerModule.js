// =============================================================================
// ArticleScannerModule — turns a line of an article into the squares it is
// written in.
//
// The whole of the rule: a closing mark joins the character before it, an
// opening mark joins the character after it, and anything left over is its own
// plain cell. 禁则 follows from that without a line-breaking rule, because a
// cell is atomic and a row cannot break inside one.
//
// This is the same rule as kranji.reading.model.Cells, moved to where the text
// arrives. The wire now carries the article as its source lines, so a reading
// the corpus already knows is not restated once per character per article, and
// correcting the corpus does not leave every article that used the character
// carrying the old reading.
//
// Pure - no DOM, no fetch. It is read by GraalVM under ordinary JUnit,
// including a parity test against the Java it was ported from.
// =============================================================================

/** Closing marks - these must never begin a line. Kept equal to Cells.CLOSING. */
var ARTICLE_CLOSING = '。，、！？：；）】》」』’”…';

/** Opening marks - these must never end a line. Kept equal to Cells.OPENING. */
var ARTICLE_OPENING = '（【《「『‘“';

var ARTICLE_HAN = /\p{Script=Han}/u;

/**
 * opts = { closing, opening }  - both optional, defaulted to the tables above.
 *
 * Returns { closing, opening, scan(line) }.
 *
 * scan yields cells, in the shape the reader renders:
 *   { z }                      a character, reading resolved from the map
 *   { z, r, o:true }           a character whose reading the article authored
 *   { z, p }                   trailing punctuation, drawn inside the square
 *   { z, lp }                  leading punctuation
 *   { t }                      anything that is not a character
 */
function createArticleScanner(opts) {
    var closing = (opts && opts.closing) || ARTICLE_CLOSING;
    var opening = (opts && opts.opening) || ARTICLE_OPENING;

    /** Adds a closing run to the last cell, when that cell can carry one. */
    function attachTrailing(cells, mark) {
        if (cells.length === 0) return false;
        var last = cells[cells.length - 1];
        if (!last.z) return false;
        last.p = (last.p || '') + mark;
        return true;
    }

    return {
        closing: closing,
        opening: opening,

        scan: function (line) {
            // Array.from iterates code points, so a character outside the BMP
            // is one cell rather than two halves of a surrogate pair.
            var chars = Array.from(line || '');
            var cells = [];
            var pendingOpen = '';
            var plain = '';

            function flushPlain() {
                if (plain.trim() !== '') cells.push({ t: plain });
                plain = '';
            }

            for (var i = 0; i < chars.length; i++) {
                var ch = chars[i];

                if (ARTICLE_HAN.test(ch)) {
                    flushPlain();
                    var cell = { z: ch };

                    // An authored reading follows the character it corrects.
                    if (chars[i + 1] === '{') {
                        var j = i + 2;
                        var reading = '';
                        while (j < chars.length && chars[j] !== '}') {
                            reading += chars[j];
                            j++;
                        }
                        if (j < chars.length) {
                            cell.r = reading;
                            cell.o = true;
                            i = j;
                        }
                    }

                    if (pendingOpen !== '') {
                        cell.lp = pendingOpen;
                        pendingOpen = '';
                    }
                    cells.push(cell);
                    continue;
                }

                if (closing.indexOf(ch) >= 0) {
                    // Only reaches back past nothing. With plain text already
                    // accumulating, the mark belongs to that run; with no cell
                    // to carry it - a line opening with a full stop - it
                    // becomes a cell of its own rather than being dropped.
                    if (plain === '' && attachTrailing(cells, ch)) continue;
                    plain += ch;
                    continue;
                }

                if (opening.indexOf(ch) >= 0) {
                    flushPlain();
                    pendingOpen += ch;
                    continue;
                }

                plain += ch;
            }

            flushPlain();
            if (pendingOpen !== '') cells.push({ t: pendingOpen });
            return cells;
        }
    };
}
