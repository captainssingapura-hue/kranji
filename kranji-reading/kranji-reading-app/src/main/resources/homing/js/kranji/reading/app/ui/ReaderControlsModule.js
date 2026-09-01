// =============================================================================
// ReaderControlsModule — the reader's control bar.
//
// Which article, how much pinyin, how big, and whether the practice grid is
// drawn. Owns the controls and what they remember; the reader asks what is
// currently chosen.
//
// The typeface picker arrives already built, so the two stay independent -
// the Characters pane wants that picker without any of this.
// =============================================================================

var READER_MODES = [
    { id: 'all',  label: 'Show all pinyin' },
    { id: 'none', label: 'Hide pinyin' }
];

var SIZE_KEY = 'kranji.reading.glyphSize';

/**
 * opts = {
 *   branch, css, selectClass, btnClass,
 *   sizes: [ { id, label, zi, ann } ],   // CSS class handles, from the caller
 *   extra: [ element ],                  // e.g. the typeface picker's select
 *   onChange: fn                         // any styling control moved
 * }
 * Returns { element, mode(), size(), grid() }.
 *
 * Which article to read is not a styling control and is not chosen here - the
 * Library says, over the articleSelection party.
 */
function createReaderControls(opts) {
    var branch = opts.branch, css = opts.css;
    var bar = branch.createElement('controlBar', 'div');

    function select(name, entries, initial, onPick) {
        var el = branch.createElement(name, 'select');
        css.setClass(el, opts.selectClass);
        for (var i = 0; i < entries.length; i++) {
            var option = branch.createElement(name + 'Opt' + i, 'option');
            option.value = entries[i].id;
            option.textContent = entries[i].label;
            if (entries[i].id === initial) option.selected = true;
            el.appendChild(option);
        }
        el.addEventListener('change', function () { onPick(el.value); });
        bar.appendChild(el);
        return el;
    }

    var mode = 'all';
    select('modeSelect', READER_MODES, mode, function (v) {
        mode = v;
        if (opts.onChange) opts.onChange();
    });

    var sizeId = 'm';
    try { sizeId = localStorage.getItem(SIZE_KEY) || sizeId; } catch (e) {}
    select('sizeSelect', opts.sizes, sizeId, function (v) {
        sizeId = v;
        try { localStorage.setItem(SIZE_KEY, sizeId); } catch (e) {}
        if (opts.onChange) opts.onChange();
    });

    for (var e = 0; e < (opts.extra || []).length; e++) bar.appendChild(opts.extra[e]);

    var grid = true;
    var gridBtn = branch.createElement('gridBtn', 'button');
    css.setClass(gridBtn, opts.btnClass);
    gridBtn.textContent = 'Grid on';
    gridBtn.addEventListener('click', function () {
        grid = !grid;
        gridBtn.textContent = grid ? 'Grid on' : 'Grid off';
        if (opts.onChange) opts.onChange();
    });
    bar.appendChild(gridBtn);

    return {
        element: bar,
        mode: function () { return mode; },
        grid: function () { return grid; },
        size: function () {
            for (var i = 0; i < opts.sizes.length; i++) {
                if (opts.sizes[i].id === sizeId) return opts.sizes[i];
            }
            return opts.sizes[0];
        }
    };
}
