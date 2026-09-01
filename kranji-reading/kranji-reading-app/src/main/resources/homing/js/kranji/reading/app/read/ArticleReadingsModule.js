// =============================================================================
// ArticleReadingsModule — fills in the readings an article did not state.
//
// An article carries its text and only the readings its author chose against
// the corpus principal. Everything else is looked up here, from the codepoint
// map, so correcting a reading in the corpus corrects every article rather
// than none of them.
//
// The map arrives in hash partitions; only the ones a given text needs are
// pulled, and each is kept once. How they are pulled is injected rather than
// hardcoded, which is what lets this run under GraalVM in an ordinary JUnit
// test with no browser and no network.
// =============================================================================

/**
 * opts = {
 *   load: fn(partition) -> Promise of { characters: { codePoint: [reading] } },
 *   partitions: 101          // must match SyllableMapGetAction.PARTITIONS
 * }
 *
 * Returns { partitionsFor(texts), ensure(texts), readingsOf(ch), fill(cells),
 *           cpLabel(ch), loadedCount() }.
 */
function createArticleReadings(opts) {
    var load = opts.load;
    var partitions = opts.partitions || 101;

    var readings = {};   // codePoint -> [reading]
    var have = {};       // partition -> true

    function partitionOf(cp) {
        return ((cp % partitions) + partitions) % partitions;
    }

    return {

        /** The partitions these texts need that are not already held. */
        partitionsFor: function (texts) {
            var want = {};
            for (var t = 0; t < texts.length; t++) {
                // Array.from iterates code points, so a character outside the
                // BMP is one lookup rather than two surrogate halves.
                var chars = Array.from(texts[t] || '');
                for (var i = 0; i < chars.length; i++) {
                    var p = partitionOf(chars[i].codePointAt(0));
                    if (!have[p]) want[p] = true;
                }
            }
            return Object.keys(want).map(Number);
        },

        /**
         * Pulls what is missing. Resolves when every reading these texts need
         * is in hand - the reader waits on this before drawing, because an
         * annotation appearing late would reflow the page.
         */
        ensure: function (texts) {
            var self = this;
            var pending = self.partitionsFor(texts).map(function (p) {
                return load(p).then(function (mod) {
                    have[p] = true;
                    var chars = (mod && mod.characters) || {};
                    for (var cp in chars) readings[cp] = chars[cp];
                });
            });
            return Promise.all(pending);
        },

        /** Every reading the corpus has for a character, principal first. */
        readingsOf: function (ch) {
            if (!ch) return [];
            return readings[ch.codePointAt(0)] || [];
        },

        /**
         * Fills the reading on any cell that does not already carry one. An
         * authored reading always wins - it is the one thing the corpus cannot
         * know, because the article is choosing against the principal.
         */
        fill: function (cells) {
            for (var i = 0; i < cells.length; i++) {
                var cell = cells[i];
                if (!cell.z || cell.r) continue;
                var found = this.readingsOf(cell.z);
                cell.r = found.length ? found[0] : '';
            }
            return cells;
        },

        /** The codepoint label, derived rather than sent beside every glyph. */
        cpLabel: function (ch) {
            return 'U+' + ch.codePointAt(0).toString(16).toUpperCase();
        },

        /** How many partitions are held. For tests and diagnostics. */
        loadedCount: function () {
            return Object.keys(have).length;
        }
    };
}
