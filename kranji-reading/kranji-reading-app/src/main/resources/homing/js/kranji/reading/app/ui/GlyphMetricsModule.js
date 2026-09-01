// =============================================================================
// GlyphMetricsModule — how much of its own box a glyph actually fills.
//
// Han characters all advance one em; their ink does not. 光 reaches its box
// edge, 月 stops well short. Anything set beside a character has to know
// which, or it crowds one and floats away from the other.
//
// canvas.measureText reports the ink extent, so this is measured rather than
// guessed. Cached per (font, character), because the same character recurs
// constantly and the measurement is not free.
// =============================================================================

/**
 * opts = { branch, name }   name defaults to 'glyphMetrics'
 * Returns { gapRatio(ch, fontSpec) }.
 */
function createGlyphMetrics(opts) {
    var context = null;
    var cache = {};

    function ctx() {
        if (!context) {
            // Through the branch: document.createElement is a raw DOM factory
            // and the party owns element creation.
            var canvas = opts.branch.createElement(opts.name || 'glyphMetrics', 'canvas');
            context = canvas.getContext('2d');
        }
        return context;
    }

    return {
        /**
         * The share of its own box a character leaves empty on the right,
         * as a fraction of the advance width. 0 means the ink runs to the
         * edge; 0.2 means a fifth of the box is empty.
         *
         * Falls back to a middling value when the browser does not report
         * ink bounds - a slightly wrong offset beats a broken render.
         */
        gapRatio: function (ch, fontSpec) {
            var key = fontSpec + '|' + ch;
            if (cache[key] !== undefined) return cache[key];

            var ratio = 0.07;
            try {
                var c = ctx();
                c.font = fontSpec;
                var m = c.measureText(ch);
                if (m.actualBoundingBoxRight !== undefined && m.width > 0) {
                    ratio = (m.width - m.actualBoundingBoxRight) / m.width;
                }
            } catch (e) {}

            cache[key] = ratio;
            return ratio;
        }
    };
}
