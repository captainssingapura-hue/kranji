// =============================================================================
// ReaderCellStyleModule — how a square is dressed.
//
// A cell's classes are assembled rather than enumerated: a base, a size, a
// typeface, and whatever else that cell happens to need. setClass replaces the
// whole list, which is why rebuilding it every time loses nothing when one
// control changes.
//
// Punctuation is drawn by the cell's own class rather than by an element, so
// nothing can widen the square. How far the mark is pushed depends on how much
// of its box the character in front of it fills - 光 reaches the edge, 月 stops
// short - so the offset is chosen from measured ink and quantised into four
// buckets. Quantised because a class per character would be unbounded, and
// four steps is below what the eye resolves at this size.
// =============================================================================

/**
 * opts = {
 *   metrics,                             // createGlyphMetrics()
 *   size:  fn -> { zi, ann },            // current size classes
 *   grid:  fn -> boolean,
 *   mode:  fn -> 'all' | 'none',
 *   typefaceClass: fn -> class,
 *   classes: { zi, ann, grid, hidden, punct, punctLead, x0, x1, x2, x3 }
 * }
 *
 * Returns { cell(td, fontSpec), ann(td), offsetClass(ratio), fontSpecOf(td) }.
 */
function createReaderCellStyle(opts) {

    function offsetClass(ratio) {
        if (ratio >= 0.18) return kr_read_punct_x0;
        if (ratio >= 0.10) return kr_read_punct_x1;
        if (ratio >= 0.05) return kr_read_punct_x2;
        return kr_read_punct_x3;
    }

    return {
        offsetClass: offsetClass,

        /** The font the cells are actually rendered in, for measuring against. */
        fontSpecOf: function (td) {
            var s = window.getComputedStyle(td);
            return s.fontSize + ' ' + s.fontFamily;
        },

        cell: function (td, fontSpec) {
            var classes = [td, kr_read_zi, opts.size().zi, opts.typefaceClass()];
            if (opts.grid()) classes.push(kr_read_grid);
            if (td.getAttribute('data-lead')) classes.push(kr_read_punct_lead);
            if (td.getAttribute('data-punct')) {
                classes.push(kr_read_punct);
                if (fontSpec) {
                    classes.push(offsetClass(
                        opts.metrics.gapRatio(td.textContent, fontSpec)));
                }
            }
            css.setClass.apply(css, classes);
        },

        ann: function (td) {
            var classes = [td, kr_read_ann, opts.size().ann];
            // Hidden with visibility, never display - the row keeps its height,
            // so showing or hiding pinyin cannot reflow the page.
            if (opts.mode() === 'none') classes.push(kr_read_hidden);
            css.setClass.apply(css, classes);
        }
    };
}

/**
 * Draws one line as a two-row table: annotations above, characters below.
 *
 * opts = { style, readings }        // style from createReaderCellStyle
 *
 * Returns { table(db, key, cells) -> { el, annCells, ziCells } }.
 *
 * A table is what buys the even rhythm of 方块字: every column is exactly one
 * character wide, so the page cannot re-space as a child learns which
 * characters they know.
 */
function createReaderLine(opts) {
    var style = opts.style, readings = opts.readings;

    return {
        table: function (db, key, cells) {
            var table = db.createElement('t' + key, 'table');
            css.setClass(table, kr_read_line);
            var annRow = db.createElement('ar' + key, 'tr');
            var ziRow = db.createElement('zr' + key, 'tr');
            table.appendChild(annRow);
            table.appendChild(ziRow);

            var anns = [], zis = [];
            for (var i = 0; i < cells.length; i++) {
                var cell = cells[i];

                var ann = db.createElement('a' + key + '_' + i, 'td');
                style.ann(ann);
                ann.textContent = cell.r || '';
                anns.push(ann);
                annRow.appendChild(ann);

                var zi = db.createElement('z' + key + '_' + i, 'td');
                zis.push(zi);

                // The character is the cell's only content. Punctuation is
                // drawn by the cell's own class - ::before and ::after read it
                // back out of these attributes - so no element carries it and
                // nothing can widen the square.
                zi.textContent = cell.z || cell.t || '';
                if (cell.lp) zi.setAttribute('data-lead', cell.lp);
                if (cell.p) zi.setAttribute('data-punct', cell.p);
                if (cell.z) {
                    // Derived, not sent. The wire used to carry a codepoint
                    // beside every glyph, which is the glyph restated.
                    zi.setAttribute('data-cp', readings.cpLabel(cell.z));
                    zi.setAttribute('aria-label',
                        (cell.lp || '') + cell.z + (cell.p || '')
                        + ' ' + (cell.r || ''));
                }
                style.cell(zi, null);
                ziRow.appendChild(zi);
            }
            return { el: table, annCells: anns, ziCells: zis };
        }
    };
}
