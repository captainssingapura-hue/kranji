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
// arrives - and beside it the block rule of ArticleParser, because the wire now
// carries the FILE. The server resolves an address, reads the resource and
// sends it; splitting it into blocks and squares is all here.
//
// What that buys is not bytes. A reading the corpus already knows is not
// restated once per character per article, correcting the corpus does not leave
// every article that used the character carrying the old reading, and there is
// no serve-time parse to keep in step with this one - there is one parser for
// the format, and a build-time check that it agrees with the Java.
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
 * Returns { closing, opening, blocks(source), scan(line) }.
 *
 * blocks splits the file: a blank line ends a block, one line is a paragraph
 * and several are a verse.
 *
 * scan yields cells, in the shape the reader renders:
 *   { z }                      a character, reading resolved from the map
 *   { z, r, o:true }           a character whose reading the article authored
 *   { z, p }                   trailing punctuation, drawn inside the square
 *   { z, lp }                  leading punctuation
 *   { t }                      anything that is not a character
 *
 * An authored reading is CANONICALISED here - dì becomes di4 - because this is
 * where a file crosses into the system. Below the display layer everything
 * carries spelling plus a tone digit, so that a reading is ASCII apart from ü
 * and can be a key in IndexedDB, a JS module and a Java registry at once; see
 * PinyinSwfModule, which owns that rule and the only conversion out of it.
 *
 * The server used to do this on the way out: it parsed the file, rebuilt the
 * line from tokens, and the rebuild wrote the canonical form. Now the file is
 * sent as it was written, so the boundary is here instead - which is what
 * PinyinSwf.canonical calls itself the entry point for.
 */
function createArticleScanner(opts) {
    var closing = (opts && opts.closing) || ARTICLE_CLOSING;
    var opening = (opts && opts.opening) || ARTICLE_OPENING;
    var swf = createPinyinSwf();

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

        /**
         * The whole file -> the blocks it is written in.
         *
         * A blank line ends a block; one line is a paragraph and several are a
         * verse. That is the entire structure of the format - a poem needs no
         * markup and prose is one long line per paragraph - which is why it
         * can be read here rather than on the server.
         *
         *   { kind: 'p',     text:  '...' }
         *   { kind: 'verse', lines: ['...', '...'] }
         *
         * The same shape /article used to send, so nothing downstream changed.
         * What changed is that the server no longer parses an article to build
         * it: it sends the file. This is the block rule of
         * kranji.reading.content.ArticleParser, held to it by the same parity
         * test that holds scan to Cells.
         */
        blocks: function (source) {
            var out = [];
            var pending = [];

            function flush() {
                if (pending.length === 0) return;
                out.push(pending.length === 1
                        ? { kind: 'p', text: pending[0] }
                        : { kind: 'verse', lines: pending });
                pending = [];
            }

            var lines = String(source || '').replace(/\r\n/g, '\n').split('\n');
            for (var i = 0; i < lines.length; i++) {
                var line = lines[i].trim();
                if (line === '') flush();
                else pending.push(line);
            }
            flush();
            return out;
        },

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
                            // Canonical if it is a syllable at all. When it is
                            // not, the author's text is kept rather than
                            // dropped: a typo that shows on the page is one
                            // somebody can find, and an empty annotation is
                            // not.
                            cell.r = swf.canonical(reading) || reading;
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
