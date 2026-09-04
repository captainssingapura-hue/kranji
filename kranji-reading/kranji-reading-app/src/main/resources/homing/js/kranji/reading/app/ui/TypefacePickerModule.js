// =============================================================================
// TypefacePickerModule — the typeface control, shared by the widgets that
// show characters.
//
// Stacks rather than families: a browser cannot be asked what is installed,
// so each entry names the members of one style across platforms and the
// device takes the first it has. Labels stay ASCII - no CJK is authored into
// a served module.
//
// The CSS class handles are passed in, because a widget's imports bind those
// into its own scope and this module has no access to them.
// =============================================================================

var TYPEFACES = [
    { id: 'system',   label: 'System default' },
    { id: 'kai',      label: 'Kaiti - regular script' },
    { id: 'hei',      label: 'Heiti - sans' },
    { id: 'song',     label: 'Songti - serif' },
    { id: 'fangsong', label: 'Fangsong' }
];

var TYPEFACE_KEY = 'kranji.reading.glyphFont';

/**
 * opts = { branch, css, prefix, classes: { id -> CssClass }, selectClass, onChange }
 * Returns { element, currentClass() }.
 */
function createTypefacePicker(opts) {
    // Kaiti by default: it keeps the entry and exit of each stroke visible,
    // which is how the character is actually written, and it is the script
    // Chinese schoolbooks use for that reason.
    var chosen = 'kai';
    try { chosen = localStorage.getItem(TYPEFACE_KEY) || chosen; } catch (e) {}
    if (!opts.classes[chosen]) chosen = 'system';

    var select = opts.branch.createElement(opts.prefix + 'Face', 'select');
    opts.css.setClass(select, opts.selectClass);

    for (var i = 0; i < TYPEFACES.length; i++) {
        var face = TYPEFACES[i];
        if (!opts.classes[face.id]) continue;
        var option = opts.branch.createElement(opts.prefix + 'Face' + i, 'option');
        option.value = face.id;
        option.textContent = face.label;
        if (face.id === chosen) option.selected = true;
        select.appendChild(option);
    }

    select.addEventListener('change', function () {
        chosen = select.value;
        // A per-reader convenience, so it lives in the browser. Storage can
        // throw or come back empty; neither is worth surfacing.
        try { localStorage.setItem(TYPEFACE_KEY, chosen); } catch (e) {}
        if (opts.onChange) opts.onChange();
    });

    return {
        element: select,
        currentClass: function () { return opts.classes[chosen]; }
    };
}
