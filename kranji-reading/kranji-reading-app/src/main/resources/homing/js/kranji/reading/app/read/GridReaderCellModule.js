// =============================================================================
// GridReaderCellModule — one square, as a RelationGrid cell widget.
//
// The spike that asks whether the reader's square survives being a grid cell
// rather than a td we built ourselves. Minesweeper is the precedent: a cell
// factory returns a stateful widget, and the grid hands it the host element to
// dress however it likes.
//
// The annotation moves INSIDE the cell, stacked over the character, because a
// Relation row is one row of cells and there is no row above to put it in. The
// property that mattered survives: a column's width is set by the grid, not by
// the content, so an annotation cannot spread the characters apart the way ruby
// does.
// =============================================================================

/**
 * opts = {
 *   branch,                       // for creating the cell's own elements
 *   grid:    fn -> boolean,       // whether to draw the practice rule
 * }
 *
 * Returns a factory suitable for RelationGrid's cellFactory.
 *
 * A cell's value is the scanner's cell object - { z, r, p, lp, t } - or null
 * for a square past the end of a line.
 */
function createGridReaderCell(opts) {
    var seq = 0;

    return function () {
        var host = null, annEl = null, ziEl = null, value = null, id = seq++;

        function paint(v) {
            value = v;
            if (!ziEl) return;
            annEl.textContent = (v && v.r) || '';
            ziEl.textContent = (v && (v.z || v.t)) || '';

            // Punctuation is drawn by the character box's own class, reading
            // it back out of these attributes - so nothing widens the square.
            if (v && v.p) ziEl.setAttribute('data-punct', v.p);
            else ziEl.removeAttribute('data-punct');
            if (v && v.lp) ziEl.setAttribute('data-lead', v.lp);
            else ziEl.removeAttribute('data-lead');

            // The annotation row above the character, dressed from the live
            // controls so a size or mode change is a repaint, not a rebuild.
            var ann = [annEl, kr_gr_ann];
            if (opts.size) ann.push(opts.size().ann);
            // Hidden with visibility, never display - the box keeps its height,
            // so hiding pinyin cannot reflow the page.
            if (opts.mode && opts.mode() === 'none') ann.push(kr_read_hidden);
            css.setClass.apply(css, ann);

            var classes = [ziEl, kr_gr_zi];
            if (opts.size) classes.push(opts.size().zi);
            if (opts.typefaceClass) classes.push(opts.typefaceClass());
            if (opts.grid && opts.grid() && v) classes.push(kr_gr_grid);
            if (v && v.lp) classes.push(kr_read_punct_lead);
            if (v && v.p) {
                classes.push(kr_read_punct);
                // How far the mark is pushed depends on how much of its box the
                // character fills, so it can only be chosen once the square has
                // a resolved font. The first paint has none; the second does.
                var spec = opts.fontSpec && opts.fontSpec();
                if (spec && opts.offsetOf) {
                    var off = opts.offsetOf(ziEl.textContent, spec);
                    if (off) classes.push(off);
                }
            }
            css.setClass.apply(css, classes);
            // The widget needs one painted square to measure the font from, and
            // may not go looking for it in the DOM.
            if (opts.onPainted) opts.onPainted(ziEl, v);
        }

        return {
            render: function (hostEl, v) {
                host = hostEl;
                css.addClass(host, kr_gr_cell);
                annEl = opts.branch.createElement('gann' + id, 'div');
                ziEl = opts.branch.createElement('gzi' + id, 'div');
                host.appendChild(annEl);
                host.appendChild(ziEl);
                paint(v);
            },

            // The direct update path: the domain pushes a new value for one
            // square and only that square repaints.
            update: function (v) { paint(v); },

            // Part of the cell shape; the grid reports selection through its
            // own onCursorMoved instead, so this stays a no-op.
            onSelect: function () {},

            getValue: function () { return value; },

            getValueToCopy: function () {
                if (!value) return '';
                return (value.lp || '') + (value.z || value.t || '') + (value.p || '');
            },

            dispose: function () { host = null; annEl = null; ziEl = null; }
        };
    };
}

/**
 * A Relation over one paragraph: rows are display lines, columns are square
 * positions.
 *
 * opts = { cells, columns }
 *
 * Degenerate in both directions - no sorting, no filtering, no edits. The
 * lookup is set once, which is the case RFC 0050 says costs nothing when
 * unused.
 */
function createParagraphRelation(opts) {
    var cells = opts.cells;
    var width = opts.columns;
    var rows = Math.max(1, Math.ceil(cells.length / width));

    var pks = [];
    for (var r = 0; r < rows; r++) pks.push('L' + r);
    var columns = [];
    for (var i = 0; i < width; i++) columns.push('c' + i);

    function at(pk, column) {
        var r = parseInt(String(pk).slice(1), 10);
        var i = parseInt(String(column).slice(1), 10);
        var index = r * width + i;
        return index < cells.length ? cells[index] : null;
    }

    return {
        pks:         function () { return pks; },
        columns:     function () { return columns; },
        get:         function (pk, column) { return at(pk, column); },
        subscribe:   function () {},
        unsubscribe: function () {},
        update:      function () {},
        deleteRows:  function () {}
    };
}

/**
 * A Relation over one verse: rows are the authored lines, columns are square
 * positions. Short lines leave empty squares at their end, which is what an
 * exercise book looks like and what the poem's shape requires.
 *
 * opts = { lines }   // an array of cell arrays, one per authored line
 */
function createVerseRelation(opts) {
    var lines = opts.lines;
    var width = 1;
    for (var i = 0; i < lines.length; i++) {
        if (lines[i].length > width) width = lines[i].length;
    }

    var pks = [];
    for (var r = 0; r < lines.length; r++) pks.push('L' + r);
    var columns = [];
    for (var j = 0; j < width; j++) columns.push('c' + j);

    function at(pk, column) {
        var r = parseInt(String(pk).slice(1), 10);
        var i = parseInt(String(column).slice(1), 10);
        var line = lines[r];
        return (line && i < line.length) ? line[i] : null;
    }

    return {
        width:       function () { return width; },
        pks:         function () { return pks; },
        columns:     function () { return columns; },
        get:         function (pk, column) { return at(pk, column); },
        subscribe:   function () {},
        unsubscribe: function () {},
        update:      function () {},
        deleteRows:  function () {}
    };
}

/**
 * Repaints every square of a grid without rebuilding it.
 *
 * A control change - size, typeface, grid on, pinyin mode - alters how a
 * square is dressed but not what it holds, so the value is pushed back
 * unchanged and the cell re-reads the live settings. updateCell is queued and
 * coalesced per frame, so a whole article is one batch: the direct-update path
 * at the burst case it was built for.
 */
function repaintGrid(grid, relation) {
    var pks = relation.pks(), columns = relation.columns();
    for (var r = 0; r < pks.length; r++) {
        for (var c = 0; c < columns.length; c++) {
            grid.updateCell(pks[r], columns[c], relation.get(pks[r], columns[c]));
        }
    }
}

/**
 * Builds one grid per block and appends it to the body.
 *
 * A block is the natural rectangle: a verse's rows are the lines the author
 * wrote, a paragraph's are the wrap. Blocks do not share a shape, so they do
 * not share a grid.
 *
 * opts = {
 *   db, owner, body, blocks,
 *   cellFactory, cellsOf, indented,
 *   columns:  fn -> squares per line for prose,
 *   onSelect: fn(cell)
 * }
 *
 * Returns [{ grid, relation }] - the caller repaints through these.
 */
function buildArticleBoards(opts) {
    var out = [];

    for (var b = 0; b < opts.blocks.length; b++) {
        var block = opts.blocks[b];
        var relation = null;

        if (block.kind === 'verse') {
            var lines = [];
            for (var l = 0; l < block.lines.length; l++) {
                lines.push(opts.cellsOf(block.lines[l]));
            }
            relation = createVerseRelation({ lines: lines });
        } else if (block.kind === 'p') {
            // Prose wraps; a verse does not. A poem's lines are the author's,
            // and re-breaking them would destroy the form.
            relation = createParagraphRelation({
                cells: opts.indented(opts.cellsOf(block.text)),
                columns: opts.columns()
            });
        } else {
            continue;
        }

        var host = opts.db.createElement('gh' + b, 'div');
        opts.body.appendChild(host);

        // Each board gets its own branch: a grid names its cells cell0, cell1,
        // and two boards on one branch collide on the second cell.
        var bb = opts.db.createBranch('bd' + b);
        bb.activate(opts.owner);

        out.push({
            host: host,
            grid: new RelationGrid({
                container:   host,
                branch:      bb,
                adapter:     relation,
                header:      { show: false },
                label:       'Squares',
                cellFactory: opts.cellFactory,
                onCursorMoved: (function (rel) {
                    return function (pk, column) {
                        if (opts.onSelect) opts.onSelect(rel.get(pk, column));
                    };
                })(relation)
            }),
            relation: relation
        });
    }
    return out;
}
