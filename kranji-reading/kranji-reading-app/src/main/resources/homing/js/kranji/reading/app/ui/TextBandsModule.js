// =============================================================================
// TextBandsModule — how many squares fit on a line, and where it breaks.
//
// Chinese breaks between any two characters, so wrapping prose is only a
// matter of choosing a count. The count is quantised into bands rather than
// fitted exactly: 作文纸 has a fixed number of columns, and a banded count
// changes only when the pane crosses a boundary - so an ordinary resize does
// not reflow the text under a child's eyes.
// =============================================================================

/** Column counts a line may have. Familiar from squared paper. */
var BANDS = [6, 8, 10, 12, 16, 20, 24];

function createBanding(opts) {
    var bands = (opts && opts.bands) || BANDS;

    return {
        bands: bands,

        /**
         * The widest band that fits, or the narrowest band if none does —
         * a very narrow pane still has to render something.
         */
        cellsPerRow: function (available, cellWidth) {
            if (!(cellWidth > 0)) return bands[0];
            var fits = Math.floor(available / cellWidth);
            var chosen = bands[0];
            for (var i = 0; i < bands.length; i++) {
                if (bands[i] <= fits) chosen = bands[i];
            }
            return chosen;
        },

        /** Splits a line's cells into rows of at most n. */
        chunk: function (cells, n) {
            if (!(n > 0)) return [cells];
            var rows = [];
            for (var i = 0; i < cells.length; i += n) {
                rows.push(cells.slice(i, i + n));
            }
            return rows.length ? rows : [[]];
        }
    };
}
