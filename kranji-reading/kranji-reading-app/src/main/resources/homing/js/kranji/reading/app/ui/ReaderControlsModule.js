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

// The three modes are different activities, not degrees of one setting: all is
// how 注音 material already reads, adaptive is the mechanic the app exists for,
// and none is a fluency check or reading aloud to an adult.
//
// 'adaptive' is the default. It used to be 'all', on the reasoning that a fresh
// reader has an empty set where the two are identical, so adaptive would appear
// to do nothing on first run.
//
// That was true and still is, and it no longer decides the question. A default
// is not only what a stranger meets once; it is what everybody who never opens
// the settings lives with forever. Adaptive is the mechanic the app exists for,
// and leaving it behind a menu meant the one thing that matters was opt-in. The
// first run costs a reader nothing - it looks exactly like 'all', because with
// nothing marked it IS 'all' - and every run after it is the app working.
var READER_MODES = [
    { id: 'all',      label: 'Show all pinyin' },
    { id: 'adaptive', label: 'Hide pinyin I know' },
    { id: 'none',     label: 'Hide pinyin' }
];

var SIZE_KEY = 'kranji.reading.glyphSize';
var MODE_KEY = 'kranji.reading.pinyinMode';

/**
 * opts = {
 *   branch, css, selectClass, btnClass,
 *   headClass, panelClass, hiddenClass,  // the strip and its folded panel
 *   sizes: [ { id, label, zi, ann } ],   // CSS class handles, from the caller
 *   extra: [ element ],                  // e.g. the typeface picker's select
 *   onChange: fn,                        // any styling control moved
 *   onToggle: fn(open)                   // the panel opened or closed
 * }
 * Returns { element, mode(), size(), grid() }.
 *
 * `element` is the HEADER STRIP, not the controls: a Settings button, and the
 * panel that folds out beneath it. The caller puts its own status line in the
 * strip alongside the button.
 *
 * The controls fold away because of who they belong to. They are a parent's
 * dials - set once, rarely touched - and five of them sitting permanently
 * between a child and the page is five things to fiddle with instead of read.
 *
 * The panel is an OVERLAY. The reader rebuilds the whole article whenever the
 * available width crosses a band boundary, so a panel that pushed the board
 * down would relayout the page every time somebody glanced at the settings.
 *
 * Which article to read is not a styling control and is not chosen here - the
 * Library says, over the articleSelection party.
 */
function createReaderControls(opts) {
    var branch = opts.branch, css = opts.css;

    var head = branch.createElement('controlHead', 'div');
    css.setClass(head, opts.headClass);

    var toggle = branch.createElement('settingsBtn', 'button');
    css.setClass(toggle, opts.btnClass);
    toggle.type = 'button';
    head.appendChild(toggle);

    // The panel is the bar: the controls go straight into it rather than into
    // a wrapper inside it, so there is one box to lay out and one to position.
    var bar = branch.createElement('controlBar', 'div');
    head.appendChild(bar);

    var open = false;
    function paint() {
        // One class or the other, never both: two classes that each set
        // display are resolved by stylesheet order, not by call order.
        css.setClass(bar, open ? opts.panelClass : opts.hiddenClass);
        toggle.textContent = open ? 'Done' : 'Settings';
    }
    toggle.addEventListener('click', function () {
        open = !open;
        paint();
        if (opts.onToggle) opts.onToggle(open);
    });

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

    // Remembered like the size, and for a stronger reason: a child who chose
    // to read without the pinyin they know should not have to choose again
    // every time the page opens. Coming back to a fully annotated page reads
    // as the app having forgotten what they had learnt.
    var mode = 'adaptive';
    try { mode = localStorage.getItem(MODE_KEY) || mode; } catch (e) {}
    select('modeSelect', READER_MODES, mode, function (v) {
        mode = v;
        try { localStorage.setItem(MODE_KEY, mode); } catch (e) {}
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

    paint();

    return {
        element: head,
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
