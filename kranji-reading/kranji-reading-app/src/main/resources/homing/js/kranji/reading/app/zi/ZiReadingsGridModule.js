// =============================================================================
// ZiReadingsGridModule — the readings of one character, as a grid you can act
// on.
//
// A row is a (character, reading) pair, which is the key the known set, the
// census and the gloss tier all share. So the row IS the claim: its pk is the
// key that would be stored, and marking it needs no lookup and no composing.
//
// The columns run in the order the questions are asked. Do I know this one -
// how is it said - what does it mean - and then, for a reader working on
// sounds, what the syllable is made of. The claim leads because it is the only
// column that does anything, and the only one a person aims a pointer at.
//
// ── The claim goes both ways, through a picker ──────────────────────────────
//
// Two values, one control: a reading is either claimed or it is not, and the
// same cell says which and changes it. A button could only have gone one way -
// a grid cursor lands on rows by arrow key and by stray click, and a press that
// destroyed a deliberate claim would be one keystroke from wherever the cursor
// happened to be, so unmarking had to live somewhere else and did not live
// anywhere at all.
//
// A picker is safe in a way a button is not: choosing costs two deliberate
// acts, open and select, and the second is the one that fires. So the cell can
// carry both directions without making the destructive one cheap.
//
// It is reachable from the keyboard for the same reason it is safe. Enter on
// the cursor is the grid's own deep-edit hook, already routed to this cell -
// see beginEdit. Nothing had to be intercepted to get it.
// =============================================================================

/**
 * The cell that claims a reading, and gives it back.
 *
 * Its VALUE is the pair key, not the state - the cell asks isKnown(key) for
 * that. The factory is handed (column, value, meta) and meta is never passed,
 * so a cell has no other way to learn which row it is on; carrying the key as
 * the value is what makes the change act on its own row rather than on the
 * cursor's.
 */
class KnownPickerCell {

    constructor(opts) {
        this._isKnown = opts.isKnown;
        this._onMark = opts.onMark;
        this._onUnmark = opts.onUnmark;
        // The picker arrives already built, already optioned and already
        // classed. A cell cannot reach document.createElement - every element
        // in the tree belongs to a branch, and the cells branch owns this one.
        this._make = opts.make;
        this._el = null;
        this._key = null;
        this._sel = null;
    }

    render(host, value) {
        this._el = host;
        this._key = value;
        var self = this;
        var sel = this._make();
        sel.addEventListener("change", function () {
            if (!self._key) return;
            if (sel.value === KNOWN) self._onMark(self._key);
            else self._onUnmark(self._key);
        });
        this._sel = sel;
        // The host is a cell element the grid just minted, so there is
        // nothing in it to clear.
        host.appendChild(sel);
        this._paint();
        return this;
    }

    _paint() {
        if (!this._sel) return;
        this._sel.value = (this._key && this._isKnown(this._key)) ? KNOWN : NOT_YET;
    }

    update(value) { this._key = value; this._paint(); return this; }

    /** The state, not the key: a copied row should say what it means. */
    getValueToCopy() { return this._key && this._isKnown(this._key) ? "known" : ""; }

    getValue()     { return this._key; }
    getEditValue() { return this._key; }

    /**
     * Enter on the cursor arrives here - the grid's deep-edit hook, which it
     * already routed to this cell and which used to be an empty stub.
     *
     * Focus first, open second. Focus is the part that always works and is
     * most of the win: with the picker focused, Up and Down move between the
     * two values, and while the grid is in its editing state it deliberately
     * stops routing arrows to the cursor, so they reach the picker instead.
     * Escape gives the keyboard back.
     *
     * showPicker() drops the list open in one keystroke rather than two. It
     * needs transient user activation, which a real key press supplies, and it
     * is absent on older engines - hence the guard and the catch. Nothing is
     * lost where it is missing; the list is one Down away.
     */
    beginEdit(current) {
        if (!this._sel) return;
        this._sel.focus();
        if (typeof this._sel.showPicker === "function") {
            try { this._sel.showPicker(); } catch (e) { /* not allowed here */ }
        }
    }

    /**
     * Both ends of an edit repaint from the record rather than from the
     * editor. A native picker fires `change` the moment a value is chosen, so
     * the claim is already made and Escape cannot take it back - repainting
     * from isKnown() is what keeps the cell honest about that instead of
     * showing a value the record does not hold.
     */
    commitEdit() { this._paint(); return this._key; }
    cancelEdit() { this._paint(); }

    // Never a bulk edit: a null type keeps the virtual session from opening
    // over this column, so a stray keystroke cannot rewrite a whole selection
    // of claims at once.
    onSelect(mode) {}
    effectiveType() { return null; }
    preview(text) { return this; }

    dispose() { this._el = null; this._sel = null; }
}

// The two values, as the option values rather than as labels: what the
// picker SAYS is for a child to read and may be reworded any day, and a
// comparison against a label would silently stop matching when it was.
var KNOWN = "known";
var NOT_YET = "";

// The claim, the sound, what it means - then the sound taken apart.
//
// The five parts used to be chips under the grid, following the cursor. They
// describe one reading, so a row is where they belong: side by side you can see
// that two readings of one character differ only in the tone, which is the
// thing a strip showing one at a time could never say.
//
// There is no "seen" column. How often the corpus happens to use a reading
// decides nothing a reader does with it.
var ZI_READING_COLUMNS = [
    "known", "reading", "meaning",
    "initial", "medial", "nucleus", "coda", "tone", "homophones"
];

// Every column except the meaning. Their content is bounded - a picker, a
// syllable, single letters, small counts - and the meaning's is not, so it
// takes what is left. A grid that sized every column to its content would move
// the claim column between characters, and with it the control a person aims at.
//
// Kept tight on purpose. The meaning gets the REMAINDER, so every pixel spent
// on a part column is one the only unbounded column does not have, and this
// pane is often half a window wide. The parts hold one to three letters; the
// headers are what needed the room, and the labels below give it back.
var ZI_READING_WIDTHS = {
    "known": 96, "reading": 88,
    "initial": 52, "medial": 52, "nucleus": 56, "coda": 48, "tone": 44,
    "homophones": 60
};

// Identity stays the column name - these are only what a person reads. The
// parts are a group and read as one once you know what they are; spelling each
// out cost the meaning more width than the words were worth.
var ZI_READING_LABELS = {
    "initial": "ini", "medial": "med", "nucleus": "nuc",
    "coda": "coda", "tone": "tone", "homophones": "same"
};

/**
 * opts = {
 *   branch, css, host,          // where the grid mounts
 *   RelationGrid, TextCell,     // the grid's own imports, passed in
 *   pickClass,                  // CSS class handle for the picker
 *   swf,                        // createPinyinSwf() - the display boundary
 *   isKnown,                    // fn(key) -> boolean
 *   onMark,                     // fn(key) - claims it
 *   onUnmark,                   // fn(key) - gives it back
 * }
 * Returns { show(glyph, readings), refresh(), destroy() }.
 */
function createZiReadingsGrid(opts) {
    var grid = null;
    var rows = [];

    function keyOf(codePoint, reading) { return codePoint + ":" + reading; }

    function freshCellsBranch() {
        if (opts.branch.getBranch("readingCells")) {
            opts.branch.dissolveBranch("readingCells");
        }
        var b = opts.branch.createBranch("readingCells");
        b.activate(opts.owner);
        return b;
    }

    function adapter() {
        var byPk = {};
        var order = [];
        rows.forEach(function (r) { byPk[r.pk] = r; order.push(r.pk); });
        return {
            pks:     function () { return order.slice(); },
            columns: function () { return ZI_READING_COLUMNS.slice(); },
            get:     function (pk, col) {
                         return byPk[pk] ? byPk[pk][col] : undefined; },
            subscribe:   function () {},
            unsubscribe: function () {},
            // The claim is not a cell edit - it goes over the party like every
            // other write to the record.
            update:     function () {},
            deleteRows: function () {}
        };
    }

    function draw() {
        if (grid) { grid.destroy(); grid = null; }
        if (!rows.length) return;
        var cells = freshCellsBranch();
        // Named per picker rather than per row: a branch registers names, and
        // the grid mints a cell the first time it needs one rather than once
        // per row, so a row-derived name could collide on a redraw.
        var minted = 0;

        function picker() {
            var n = ++minted;
            var sel = cells.createElement("pick" + n, "select");
            opts.css.setClass(sel, opts.pickClass);
            var no = cells.createElement("pick" + n + "no", "option");
            no.value = NOT_YET;
            no.textContent = "Not yet";
            sel.appendChild(no);
            var yes = cells.createElement("pick" + n + "yes", "option");
            yes.value = KNOWN;
            yes.textContent = "Known";
            sel.appendChild(yes);
            return sel;
        }

        grid = new opts.RelationGrid({
            container: opts.host,
            branch:    cells,
            adapter:   adapter(),
            label:     "Readings of this character",
            header:    { labels: ZI_READING_LABELS },
            cellFactory: function (column, value) {
                if (column !== "known") return new opts.TextCell();
                return new KnownPickerCell({
                    isKnown: opts.isKnown,
                    onMark: opts.onMark,
                    onUnmark: opts.onUnmark,
                    make: picker
                });
            }
        });

        // After construction, because width keys on column IDENTITY and the
        // grid has to know the columns before it can be told about one. Any
        // column left unset stays automatic, which is what the meaning wants.
        for (var col in ZI_READING_WIDTHS) {
            grid.setColumnWidth(col, ZI_READING_WIDTHS[col]);
        }
    }

    return {
        /** Replace the rows. `readings` is what /zi-detail served. */
        show: function (codePoint, readings) {
            rows = [];
            for (var i = 0; i < readings.length; i++) {
                var r = readings[i];
                rows.push({
                    pk: keyOf(codePoint, r.reading),
                    // The picker's value is the key, which is how a cell knows
                    // which row it is on - see KnownPickerCell.
                    "known":   keyOf(codePoint, r.reading),
                    // The display boundary. The pk above stays canonical.
                    "reading": opts.swf.toSWF(r.reading)
                             + (r.principal ? "" : "  (also)"),
                    "meaning": (r.meanings && r.meanings.length)
                             ? r.meanings.join("  |  ") : "",
                    // Already decomposed by the server, and an absent part
                    // already reads as "-" rather than as a blank that could be
                    // missing data.
                    "initial": r.initial,
                    "medial":  r.medial,
                    "nucleus": r.nucleus,
                    "coda":    r.coda,
                    "tone":    r.tone,
                    "homophones": r.homophones
                });
            }
            draw();
        },

        /** Repaint after the known set changed under us. */
        refresh: function () { draw(); },

        destroy: function () { if (grid) { grid.destroy(); grid = null; } }
    };
}
