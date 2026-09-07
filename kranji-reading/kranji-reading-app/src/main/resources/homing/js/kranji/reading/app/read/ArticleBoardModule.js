// =============================================================================
// ArticleBoardModule — an article drawn as boards of squares.
//
// One board per block: a verse's rows are the lines the author wrote, a
// paragraph's are the wrap. Each square is a RelationGrid cell widget, which is
// what brings keyboard navigation and selection to a reader that used to
// hand-roll a table. Minesweeper is the precedent - a cell factory returns a
// stateful widget and the grid hands it the host element to dress.
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
 *   marks:   fn -> boolean,       // whether to tick a reading already claimed
 *   mastered: fn(v) -> boolean,   // whether this one is claimed
 * }
 *
 * Returns a factory suitable for RelationGrid's cellFactory.
 *
 * A cell's value is the scanner's cell object - { z, r, p, lp, t } - or null
 * for a square past the end of a line.
 */
function createArticleCell(opts) {
    var seq = 0;
    // One converter for the whole article. A cell keeps the canonical reading
    // in its value - that is what the known set is keyed on, what a click
    // reports, and what the adaptive rule compares - and converts only on the
    // way to the glass.
    var swf = createPinyinSwf();

    return function () {
        var host = null, annEl = null, ziEl = null, knEl = null;
        var value = null, id = seq++;

        function paint(v) {
            value = v;
            if (!ziEl) return;
            // The display boundary. v.r is "dong1"; a child reads dōng.
            annEl.textContent = (v && v.r) ? swf.toSWF(v.r) : '';
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
            // WHETHER to show it is asked, not decided here. The cell draws; it
            // does not know what a mode is or what a known set is, which is
            // what lets that rule be tested without a browser.
            //
            // Hidden with visibility, never display - the box keeps its height,
            // so hiding pinyin cannot reflow the page. That matters most for
            // the adaptive mode: marking a reading changes every occurrence of
            // it in the article, lines apart, and a child's place on the page
            // must not move as a reward for knowing something.
            if (opts.annotates && !opts.annotates(v)) ann.push(kr_read_hidden);
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

            // The mastery mark. Out of flow, so it can appear and disappear
            // without moving a square - the same rule the annotation above
            // follows, and for the same reason: earning a mark must not shift
            // the page under the child who earned it.
            //
            // kr_gr_known is applied in BOTH states and kr_read_hidden layered
            // over it, exactly as the annotation does. Swapping one class for
            // the other looked equivalent and was not: kr_read_hidden sets
            // visibility and nothing else, so an unmarked badge fell back to
            // static and took a line of its own at the foot of the cell. Marked
            // squares were then a line shorter than unmarked ones, and a row
            // containing both had its characters at two different heights.
            //
            // Two questions, not one. Whether this reading is claimed is asked
            // of the known set; whether ticks are drawn at all is asked of the
            // controls. A reader who has turned them off sees no tick on a
            // claimed reading - and the tick is still the same element, so
            // turning them back on is a repaint rather than a rebuild.
            //
            // Only on a real character. A punctuation square or a gap has
            // nothing to master, and a mark there would read as decoration.
            if (knEl) {
                var mark = [knEl, kr_gr_known];
                var show = opts.marks ? opts.marks() : true;
                if (!(show && v && v.z && opts.mastered && opts.mastered(v))) {
                    mark.push(kr_read_hidden);
                }
                css.setClass.apply(css, mark);
            }

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
                // A SIBLING of the character box, never a child of it.
                //
                // paint() sets ziEl.textContent on every repaint, and assigning
                // textContent replaces every child - so a badge inside the box
                // is created once and silently destroyed by the first repaint.
                // The cell is positioned instead, and the mark hangs off its
                // bottom-right, which is the box's bottom-right too.
                //
                // Built whether or not ticks are on. Whether one is drawn is a
                // class, so the setting costs a repaint and never a rebuild -
                // and a reader turning ticks on mid-article does not lose their
                // place to a board being torn down.
                knEl = opts.branch.createElement('gkn' + id, 'div');
                // Written once: the mark never changes shape, only whether it
                // is shown. A tick is not a Han character, so it is the one
                // glyph this file may carry - and it is drawn in the badge's
                // own sans face, never the reader's chosen CJK typeface.
                knEl.textContent = '✓';
                host.appendChild(annEl);
                host.appendChild(ziEl);
                host.appendChild(knEl);
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

            dispose: function () { host = null; annEl = null; ziEl = null; knEl = null; }
        };
    };
}

/**
 * A Relation over the whole article: one row per display line, columns as wide
 * as the longest of them.
 *
 * Short rows leave empty squares at their end, which is what an exercise book
 * looks like and what a poem's shape requires.
 *
 * opts = { lines }   // an array of cell arrays, one per display line
 */
function createLineRelation(opts) {
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
/**
 * The article as display lines, in reading order.
 *
 * A verse contributes the lines its author wrote; a paragraph contributes its
 * wrap. Prose wraps and verse does not - a poem's lines are the author's, and
 * re-breaking them would destroy the form.
 *
 * opts = { blocks, cellsOf, indented, chunk, columns }
 */
function articleLines(opts) {
    var lines = [];
    for (var b = 0; b < opts.blocks.length; b++) {
        var block = opts.blocks[b];
        if (block.kind === 'verse') {
            for (var l = 0; l < block.lines.length; l++) {
                lines.push(opts.cellsOf(block.lines[l]));
            }
        } else if (block.kind === 'p') {
            var rows = opts.chunk(opts.indented(opts.cellsOf(block.text)),
                                  opts.columns());
            for (var r = 0; r < rows.length; r++) lines.push(rows[r]);
        }
    }
    return lines;
}

/**
 * Builds the article as ONE board and appends it to the body.
 *
 * One grid, not one per block: an article is a single page of squares, so it
 * is a single Relation. The cursor therefore runs from the first square to the
 * last without leaving the grid, and a paragraph boundary is not a hole in the
 * keyboard model.
 *
 * Blocks run together for now - the indent is what marks a new paragraph, as
 * it does on paper. Separating them properly wants row gaps, which the grid
 * does not yet have.
 *
 * opts = {
 *   db, owner, body, blocks,
 *   cellsOf, indented, chunk, columns,
 *   cellFactory, onSelect
 * }
 *
 * Returns { host, grid, relation }.
 */
function buildArticleBoard(opts) {
    var relation = createLineRelation({ lines: articleLines(opts) });

    var host = opts.db.createElement('board', 'div');
    opts.body.appendChild(host);

    var bb = opts.db.createBranch('board');
    bb.activate(opts.owner);

    var grid = new RelationGrid({
            container:   host,
            branch:      bb,
            adapter:     relation,
            header:      { show: false },
            // The article is not a spreadsheet. editable:false turns off the
            // grid's deep edit, its Delete-clears and its bulk session - none of
            // which a reader wants - and it is also what frees Enter: the edit
            // controller only claims that key on an editable grid, so with it
            // off the press reaches the reader and can mean "I know this one".
            editable:    false,
            label:       'The article, as squares',
            cellFactory: opts.cellFactory,
        onCursorMoved: function (pk, column) {
            if (opts.onSelect) opts.onSelect(relation.get(pk, column));
        }
    });

    // The square under the cursor, handed back for whatever a claim means.
    //
    // Read from the grid's cursor rather than from a remembered cell: the
    // keyboard moves it and so does a click, and a second copy would be wrong
    // exactly when the two disagree.
    function claimAtCursor() {
        var at = null;
        try { at = JSON.parse(grid.cursor()); } catch (e) { return; }
        if (!at) return;
        var cell = relation.get(at.pk, at.column);
        // A punctuation square, a gap past the end of a line, or a character
        // the corpus has no reading for. Nothing to claim.
        if (!cell || !cell.z || !cell.r) return;
        opts.onClaim(cell);
    }

    // Two deliberate gestures, listened for on the BOARD rather than on a
    // square: the grid owns its cells and remakes them constantly, so a
    // listener per square would be hundreds to keep in step.
    //
    // A single click is NOT one of them. That is how a character is selected
    // for the other panes, and a reader moving through a poem would claim half
    // of it by accident.
    if (opts.onClaim) {
        host.addEventListener('keydown', function (e) {
            if (e.key !== 'Enter') return;
            e.preventDefault();
            claimAtCursor();
        });
        host.addEventListener('dblclick', function (e) {
            e.preventDefault();
            claimAtCursor();
        });
    }

    return { host: host, relation: relation, grid: grid };
}
