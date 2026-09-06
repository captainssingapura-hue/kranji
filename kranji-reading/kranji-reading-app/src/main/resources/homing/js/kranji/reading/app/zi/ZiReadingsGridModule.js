// =============================================================================
// ZiReadingsGridModule — the readings of one character, as a grid you can act
// on.
//
// A row is a (character, reading) sense. The pair key still underlies it -
// that is what the known set, the census and the gloss tier all share - but
// nothing here writes to the record any more.
//
// It used to lead with a claim column: a picker per row, saying whether the
// reading was known and changing it. That went when marking settled in one
// place. A reader meets a character in a sentence and says so there; a second
// control in a reference pane was a second way to do the same thing, kept in
// step over a bus, for the sake of being able to do it while looking something
// up.
//
// The columns run in the order the questions are asked. How is it said - what
// does it mean - and then, for a reader working on sounds, what the syllable
// is made of.

// The sound, what it means - then the sound taken apart.
//
// The five parts used to be chips under the grid, following the cursor. They
// describe one reading, so a row is where they belong: side by side you can see
// that two readings of one character differ only in the tone, which is the
// thing a strip showing one at a time could never say.
//
// There is no "seen" column. How often the corpus happens to use a reading
// decides nothing a reader does with it.
var ZI_READING_COLUMNS = [
    "reading", "meaning", "examples",
    "initial", "medial", "nucleus", "coda", "tone", "homophones"
];

// Every column except the meaning. Their content is bounded - a syllable,
// single letters, small counts - and the meaning's is not, so it takes what is
// left.
//
// Kept tight on purpose. The meaning gets the REMAINDER, so every pixel spent
// on a part column is one the only unbounded column does not have, and this
// pane is often half a window wide. The parts hold one to three letters; the
// headers are what needed the room, and the labels below give it back.
// Examples are bounded in practice - a sense cites a few short phrases - so
// they take a fixed column and the meaning keeps the remainder. Letting both
// grow would leave the meaning nothing, which is what these widths prevent.
var ZI_READING_WIDTHS = {
    "reading": 88, "examples": 150,
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
 *   swf,                        // createPinyinSwf() - the display boundary
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
            // Nothing here edits. The grid is a reference view now.
            update:     function () {},
            deleteRows: function () {}
        };
    }

    function draw() {
        if (grid) { grid.destroy(); grid = null; }
        if (!rows.length) return;
        var cells = freshCellsBranch();

        grid = new opts.RelationGrid({
            container: opts.host,
            branch:    cells,
            adapter:   adapter(),
            label:     "Readings of this character",
            header:    { labels: ZI_READING_LABELS },
            cellFactory: function (column, value) { return new opts.TextCell(); }
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
                var key = keyOf(codePoint, r.reading);
                // A row per SENSE. A reading that means three things is three
                // things a reader could be looking at, and pooling them into
                // one cell separated by bars made the commonest case - one
                // sense - pay for the rarest, while giving no sense a place to
                // put its own examples.
                //
                // A reading nothing has glossed still gets its one row: it
                // is a reading of this character, and the grid is showing the
                // character rather than the dictionary.
                var senses = (r.senses && r.senses.length) ? r.senses
                           : [{ meaning: "", examples: [] }];
                for (var j = 0; j < senses.length; j++) {
                    rows.push({
                        // Identity is (character, reading, meaning).
                        pk: key + "/" + senses[j].meaning,
                        // The display boundary. The keys above stay canonical.
                        "reading": opts.swf.toSWF(r.reading)
                                 + (r.principal ? "" : "  (also)"),
                        "meaning": senses[j].meaning,
                        "examples": (senses[j].examples || []).join("  ")
                        ,
                        // Already decomposed by the server, and an absent part
                        // already reads as "-" rather than as a blank that could
                        // be missing data.
                        "initial": r.initial,
                        "medial":  r.medial,
                        "nucleus": r.nucleus,
                        "coda":    r.coda,
                        "tone":    r.tone,
                        "homophones": r.homophones
                    });
                }
            }
            draw();
        },

        /** Repaint in place - the rows have not moved. */
        refresh: function () { draw(); },

        destroy: function () { if (grid) { grid.destroy(); grid = null; } }
    };
}
