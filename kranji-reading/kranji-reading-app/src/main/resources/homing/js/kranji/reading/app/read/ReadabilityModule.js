// =============================================================================
// ReadabilityModule — how much of an article this reader can already read.
//
//   readability = known Han tokens / total Han tokens
//
// Simple arithmetic over two things already in hand: the article's census,
// which the server sent once, and the known set, which never left the device.
// No request, and none needed when the set changes.
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
 * Owns the census as well as the arithmetic. The census arrives once for the
 * whole library and is cached by the browser, so keeping the figure current as
 * readings are marked costs nothing - but the fetch, the "not yet" state and
 * the sentence were three more things in a reader that had enough of them.
 *
 * Length always; the fit only once it can be told truthfully. A percentage
 * shown before the census has landed would be a number the reader has no
 * reason to distrust and every reason to.
 *
 * opts = {
 *   known : fn -> the claimed keys
 *   onText: fn(string)   // where the sentence goes
 * }
 * Returns { showing(mod), refresh() }.
 */
function createArticleFit(opts) {
    var readability = createReadability();
    var census = null;
    var current = null;

    function say() {
        if (!current || !current.id) return;
        var says = current.length + ' characters.';
        var fit = census && readability.of(census[current.id], opts.known());
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

    import('/article-census')
        .then(function (m) { census = m.articles; say(); })
        .catch(function () { census = null; });

    return {
        showing: function (mod) { current = mod; say(); },
        refresh: say
    };
}
