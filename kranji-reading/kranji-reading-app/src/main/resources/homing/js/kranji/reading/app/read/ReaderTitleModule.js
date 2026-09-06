// =============================================================================
// ReaderTitleModule — the article's title, read the same way its body is.
//
// The title was the one line of Chinese in the pane with no reading over it.
// A child who needed pinyin for 篇 in the first sentence needed it for 始 in
// the title, and the title is what they met first.
//
// It is not squares. The body is a practice grid because that is how the
// characters are written out; a title is a name and is read as one phrase, so
// it flows. Ruby is what puts a reading over a character without taking any
// width - a bracketed reading after each character would be longer than the
// name and would stop being a name.
//
// Per character, not per word. Which characters make a word is not known here
// and the reading has to sit over the character it belongs to, which is the
// whole point of putting it above rather than after.
//
// ADAPTIVE, on the same terms as the body. Whether a reading shows is asked of
// the caller, never decided here, so the title and the article can never
// disagree about what this reader knows. Hiding uses visibility rather than
// display, again like the body: a reading leaving must not move the title, or
// the page shifts under a child as a reward for having learnt something.
//
// No CJK literal appears in this file. Titles arrive from the corpus.
// =============================================================================

/**
 * opts = {
 *   css,                  // the class setter
 *   swf,                  // createPinyinSwf() - the display boundary
 *   cellsOf,              // fn(text) -> cells, scanned and with readings filled
 *   annotates,            // fn(cell) -> whether this reading should show
 *   ziClass, rtClass, hiddenClass
 * }
 * Returns { show(branch, host, text), restyle() }.
 */
function createReaderTitle(opts) {

    // What restyle() works on. A reading appearing or leaving is a class
    // change on an <rt> that already exists, never a rebuild - the same
    // bargain the squares make, and for the same reason.
    var marks = [];
    var minted = 0;

    function el(branch, tag, text) {
        var e = branch.createElement('t' + (++minted), tag);
        if (text !== undefined && text !== null && text !== '') e.textContent = text;
        return e;
    }

    function dress(mark) {
        var classes = [mark.rt, opts.rtClass];
        if (!opts.annotates(mark.cell)) classes.push(opts.hiddenClass);
        opts.css.setClass.apply(opts.css, classes);
    }

    return {

        /**
         * Writes the title into `host`, one element per cell.
         *
         * <p>`branch` is the caller's document branch, so the title is
         * dissolved with the article it belongs to rather than living on into
         * the next one.</p>
         */
        show: function (branch, host, text) {
            marks = [];
            var cells = opts.cellsOf(text || '');

            for (var i = 0; i < cells.length; i++) {
                var c = cells[i];

                // A run the scanner could not read as characters - Latin, a
                // separator, a stretch of punctuation. It is part of the title
                // as written and there is nothing to sound.
                if (!c.z) {
                    if (c.t) host.appendChild(el(branch, 'span', c.t));
                    continue;
                }

                // Punctuation the scanner folded onto the character travels
                // with it, and outside the ruby: the reading belongs over the
                // character, not over the comma after it.
                if (c.lp) host.appendChild(el(branch, 'span', c.lp));

                if (c.r) {
                    var ru = el(branch, 'ruby', c.z);
                    opts.css.setClass(ru, opts.ziClass);
                    var rt = el(branch, 'rt', opts.swf.toSWF(c.r));
                    ru.appendChild(rt);
                    host.appendChild(ru);
                    marks.push({ rt: rt, cell: c });
                    dress(marks[marks.length - 1]);
                } else {
                    // A character the corpus has no reading for. It is still
                    // part of the title; it just cannot be helped with.
                    host.appendChild(el(branch, 'span', c.z));
                }

                if (c.p) host.appendChild(el(branch, 'span', c.p));
            }
        },

        /** A mode change, or a reading marked. Classes only - nothing is rebuilt. */
        restyle: function () {
            for (var i = 0; i < marks.length; i++) dress(marks[i]);
        }
    };
}
