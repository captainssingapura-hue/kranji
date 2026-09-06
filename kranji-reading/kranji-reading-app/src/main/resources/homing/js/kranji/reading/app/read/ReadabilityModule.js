// =============================================================================
// ReadabilityModule — how much of an article this reader can already read.
//
//   readability = known Han tokens / total Han tokens
//
// A comparison between two things, and it owns neither. ArticleCensusModule
// says what an article asks; the known set says what the reader brings. This
// is only the arithmetic between them, which is why the counting moved out:
// a census is a property of an article and has nothing to do with a reader.
//
// TWO NUMBERS, NOT ONE. The ratio counts repeats, because that is the reading
// experience - a character met forty times is forty moments of support. The
// count of distinct unknown readings is often the more useful figure: 94% with
// eight new readings is a different proposition from 94% with one repeated
// forty times, and the second is much the better lesson.
//
// Pure - no DOM, no fetch, no clock.
// =============================================================================

// Boundaries from Article Catalogue and Readability. The 90-98 band is where
// the catalogue should push readers: enough support to keep moving, not so much
// that the eye stops reading characters.
var READABILITY_BANDS = [
    { id: 'comfortable', label: 'Comfortable', from: 0.98 },
    { id: 'just-right',  label: 'Just right',  from: 0.90 },
    { id: 'stretch',     label: 'Stretch',     from: 0.75 },
    { id: 'too-hard',    label: 'Too hard',    from: 0 }
];

/**
 * Returns { of, bandOf, compare }.
 */
function createReadability() {

    function bandOf(ratio) {
        for (var i = 0; i < READABILITY_BANDS.length; i++) {
            if (ratio >= READABILITY_BANDS[i].from) return READABILITY_BANDS[i];
        }
        return READABILITY_BANDS[READABILITY_BANDS.length - 1];
    }

    return {

        bands: READABILITY_BANDS,
        bandOf: bandOf,

        /**
         * One article against one known set.
         *
         * Returns { total, known, ratio, unknown, band }, where `unknown` is
         * the number of DISTINCT readings not yet claimed - the count of things
         * left to learn, not the count of times they appear.
         *
         * An article of no Han characters reads as fully readable rather than
         * dividing by zero. There is nothing in it to be unable to read.
         */
        of: function (census, known) {
            if (!census) return null;
            var claimed = {};
            var k = known || [];
            for (var i = 0; i < k.length; i++) claimed[k[i]] = true;

            var total = census.total || 0;
            var hit = 0;
            var unknown = 0;
            var pairs = census.pairs || {};
            for (var key in pairs) {
                if (claimed[key]) hit += pairs[key];
                else unknown++;
            }

            var ratio = total === 0 ? 1 : hit / total;
            return {
                total:   total,
                known:   hit,
                ratio:   ratio,
                unknown: unknown,
                band:    bandOf(ratio)
            };
        },

        /**
         * Best fit first: nearest the middle of the just-right band.
         *
         * NOT simply highest readability, which would rank the easiest material
         * top and bury everything worth reading. A child wants the story that
         * stretches them slightly, and 94% is a better recommendation than 100%.
         */
        compare: function (a, b) {
            var target = 0.94;
            return Math.abs(a.ratio - target) - Math.abs(b.ratio - target);
        }
    };
}

/**
 * The fit line: how much of THIS article this reader can already read.
 *
 * It counts the article in front of it. There is no fetch and nothing to wait
 * for, so there is no state where the length is known and the percentage is
 * not - which is what the "only once it can be told truthfully" rule below was
 * protecting against when the figure came from a 274KB download of every
 * article in the library.
 *
 * opts = {
 *   known : fn -> the claimed keys
 *   lines  : fn(mod)  -> [string]     // the article's lines
 *   cellsOf: fn(line) -> [{ z, r }] // a line, scanned and filled
 *   onText: fn(string)              // where the sentence goes
 * }
 * Returns { showing(mod), refresh() }.
 */
function createArticleFit(opts) {
    var readability = createReadability();
    var counter = createArticleCensus();
    var census = null;
    var current = null;

    /** The article in front of it, counted once when it arrives. */
    function countArticle(mod) {
        if (!mod || !mod.id || !opts.lines || !opts.cellsOf) return null;
        return counter.ofLines(opts.lines(mod), opts.cellsOf);
    }

    function say() {
        if (!current || !current.id) return;
        var says = current.length + ' characters.';
        var fit = census && readability.of(census, opts.known());
        if (fit) {
            says += '  You can read ' + Math.round(fit.ratio * 100) + '%'
                 + ' \u00b7 ' + fit.band.label;
            if (fit.unknown > 0) {
                says += ' \u00b7 ' + fit.unknown + (fit.unknown === 1
                        ? ' reading to learn' : ' readings to learn');
            }
        }
        opts.onText(says);
    }

    return {
        showing: function (mod) {
            current = mod;
            census = countArticle(mod);
            say();
        },
        // The census does not move when a claim does - it is a property of the
        // article - so this recounts nothing and only re-reads the set.
        refresh: say
    };
}
