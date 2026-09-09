// =============================================================================
// KmdBoardModule — a GridPlan on the real grid.
//
// MdSquaresModule draws a plan as plain divs and always said that was stage
// two: settle the sizes against something dumb, then swap the substrate without
// the plan changing. This is the swap. The plan did not change.
//
// What the grid brings that divs did not is a CURSOR. RelationGrid owns
// arrow-key movement and reports where it lands through onCursorMoved, so a
// reader walks the page from the keyboard and something above the sheet can
// follow without either knowing about the other.
//
// That is what makes a one-square run readable. The square shows a placeholder
// because a word does not fit in a box; the cursor says which placeholder is
// under the reader; the strip above says what it stands for. None of the three
// has to be clever, because the grid already tracks the thing that joins them.
//
// No CJK literal appears in this file. Every character it draws came from a
// draft on disk.
// =============================================================================

/**
 * opts = {
 *   css, container,
 *   classes: { cell, zi, ann, bold, punct, run, marker, indent },
 *   onCursor: fn(square)   // the square under the cursor, or null
 * }
 * Returns { draw(branch, plan), destroy() }.
 */
function createKmdBoard(opts) {

    var C = opts.classes;
    var grid = null;
    var relation = null;
    var minted = 0;

    // The branch the current drawing owns. A fresh one arrives with every draw
    // and the old one is dissolved by the caller, which is how this pane knows
    // that what left the screen also left the tree - the grid makes and unmakes
    // cells as it scrolls, and elements minted against a branch that outlived
    // them would accumulate for the life of the pane.
    var live = null;

    // ── The relation ────────────────────────────────────────────────────

    // Read-only, and as thin as the interface allows: a plan is already rows of
    // squares, which is what the grid wants. Width comes from the PLAN rather
    // than from the longest row - a page is twenty squares wide even when its
    // last line holds four, and deriving it from the content would make a poem
    // narrow the paper.
    function planRelation(plan) {
        var lines = [];
        var rows = (plan && plan.rows) || [];
        for (var r = 0; r < rows.length; r++) lines.push(rows[r].squares || []);

        var width = Math.max(1, (plan && plan.columns) || 1);
        var pks = [];
        for (var i = 0; i < lines.length; i++) pks.push('L' + i);
        var columns = [];
        for (var j = 0; j < width; j++) columns.push('c' + j);

        function at(pk, column) {
            var r = parseInt(String(pk).slice(1), 10);
            var i = parseInt(String(column).slice(1), 10);
            var line = lines[r];
            // Past the end of a short line. Null rather than a blank square:
            // the difference between "the author stopped here" and "the author
            // left a box empty" is one the cell has to be able to draw.
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

    // ── The cell ────────────────────────────────────────────────────────

    // One square, as a cell widget. The grid owns the host element and remakes
    // cells as it scrolls, so everything here is built on mount and repainted
    // in place - never rebuilt per value.
    function cellFactory(column, value) {
        var host = null;
        var annEl = null;
        var textEl = null;
        var branch = live;

        function classesFor(v) {
            if (!v) return [C.cell, C.indent];
            if (v.k === 'r') return v.b ? [C.cell, C.run, C.bold] : [C.cell, C.run];
            if (v.k === 's') return [C.cell, C.punct];
            if (v.k === 't') return [C.cell, C.marker];
            if (v.k === 'i') return [C.cell, C.indent];
            return v.b ? [C.cell, C.bold] : [C.cell];
        }

        // A run shows its first character and an ellipsis, because a word does
        // not fit a box and three blank boxes in a line tell you nothing. The
        // whole of it goes on the title, so a pointer can read it out - and on
        // the strip above, which is how it is read without one.
        function shown(v) {
            if (!v) return '';
            if (v.k !== 'r') return v.t || '';
            var chars = Array.from(String(v.t || ''));
            return chars.length > 1 ? chars[0] + '…' : (v.t || '');
        }

        function paint(v) {
            if (!host) return;
            opts.css.setClass.apply(opts.css, [host].concat(classesFor(v)));
            annEl.textContent = (v && v.k !== 'r' && v.r) || '';
            textEl.textContent = shown(v);
            // Only a run hides anything, so only a run has something to say.
            if (v && v.k === 'r') host.title = String(v.t || '');
            else host.removeAttribute('title');
        }

        return {
            render: function (el, v) {
                host = el;
                annEl = branch.createElement('a' + (++minted), 'div');
                opts.css.setClass(annEl, C.ann);
                textEl = branch.createElement('t' + (++minted), 'div');
                opts.css.setClass(textEl, C.zi);
                host.appendChild(annEl);
                host.appendChild(textEl);
                paint(v);
            },
            update: function (v) { paint(v); },
            onSelect: function () {},
            getValue: function () { return value; },
            getValueToCopy: function () {
                // The WHOLE run, not the placeholder. Copying a page and
                // getting `T…` would be the display leaking into the content.
                if (!value) return '';
                return value.t || '';
            },
            dispose: function () { host = null; annEl = null; textEl = null; }
        };
    }

    // ── The board ───────────────────────────────────────────────────────

    return {

        /** Replaces whatever was drawn. The caller owns the branch's lifetime. */
        draw: function (branch, plan) {
            if (grid && grid.destroy) { try { grid.destroy(); } catch (e) {} }
            live = branch;
            relation = planRelation(plan);
            grid = new RelationGrid({
                container: opts.container,
                branch:    branch,
                adapter:   relation,
                header:    { show: false },
                // An article is not a spreadsheet. editable:false turns off the
                // deep edit, the Delete-clears and the bulk session, none of
                // which a reader wants - and it is what leaves Enter free.
                editable:  false,
                label:     'The section, as squares',
                cellFactory: cellFactory,
                columnOps: false,
                onCursorMoved: function (pk, column) {
                    if (opts.onCursor) opts.onCursor(relation.get(pk, column));
                }
            });
            return grid;
        },

        destroy: function () {
            if (grid && grid.destroy) { try { grid.destroy(); } catch (e) {} }
            grid = null;
            relation = null;
        }
    };
}
