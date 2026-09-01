// =============================================================================
// ReaderCellStyleModule — where a punctuation mark sits in its square.
//
// Punctuation is drawn by the square's own class rather than by an element, so
// nothing can widen the square. How far the mark is pushed depends on how much
// of its box the character in front of it fills - 光 reaches the edge, 月 stops
// short - so the offset is chosen from measured ink and quantised into four
// buckets. Quantised because a class per character would be unbounded, and four
// steps is below what the eye resolves at this size.
//
// Dressing a square is ArticleBoardModule's job now; what is left here is the
// measurement, which needs a laid-out element and so cannot happen at paint.
// =============================================================================

/**
 * opts = { metrics }        // createGlyphMetrics()
 *
 * Returns { offsetClass(ratio), fontSpecOf(el) }.
 */
function createReaderCellStyle(opts) {

    return {
        /** Which of the four offsets suits a glyph filling this much of its box. */
        offsetClass: function (ratio) {
            if (ratio >= 0.18) return kr_read_punct_x0;
            if (ratio >= 0.10) return kr_read_punct_x1;
            if (ratio >= 0.05) return kr_read_punct_x2;
            return kr_read_punct_x3;
        },

        /** The font a square actually rendered in, for measuring against. */
        fontSpecOf: function (el) {
            var s = window.getComputedStyle(el);
            return s.fontSize + ' ' + s.fontFamily;
        }
    };
}
