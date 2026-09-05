// =============================================================================
// KnownSoundsModule — the known set, bucketed by sound.
//
// THE JOIN IS FREE. A known-set entry is codePoint:reading and a syllable is
// keyed on that same canonical reading, so bucketing the set by sound is
// reading the back half of each key. No lookup, no request, nothing the server
// has to have seen.
//
// LEFT, NOT DONE. Rows are ordered by how much of a sound is still unclaimed.
// A sound with forty characters and two claimed is a larger piece of work than
// one with three and none, and somebody deciding where to spend an afternoon
// wants the first at the top.
//
// Pure - no DOM, no fetch, no clock.
// =============================================================================

/**
 * Returns { byReading, rowsOf, summarise }.
 *
 * The sound column carries both forms - the label the projection wrote (yì)
 * and the canonical key (yi4) - because the first is what a reader recognises
 * and the second is what they can type into the filter. Running the label
 * through the SWF module printed it twice: it is already the diacritic form.
 */
function createKnownSounds() {

    /** Known readings, counted. The key carries the reading already. */
    function byReading(known) {
        var out = {};
        for (var i = 0; i < (known || []).length; i++) {
            var at = known[i].indexOf(':');
            if (at < 0) continue;
            var reading = known[i].slice(at + 1);
            out[reading] = (out[reading] || 0) + 1;
        }
        return out;
    }

    // A claim can name a (character, reading) the corpus does not file under
    // this sound - an import from another corpus version, a reading since
    // corrected. Clamped rather than dropped: the row is still true about the
    // sound, and an unclamped count would print 41 of 40.
    function claimed(counts, syllable) {
        var k = counts[syllable.r] || 0;
        return k > syllable.c ? syllable.c : k;
    }

    /**
     * One row per syllable, filtered and ordered.
     *
     * @param syllables from /syllable-index
     * @param known     the known set
     * @param opts      { find: '', only: ''|'started'|'untouched'|'done' }
     */
    function rowsOf(syllables, known, opts) {
        var counts = byReading(known);
        var find = (opts && opts.find) ? opts.find : '';
        var only = (opts && opts.only) ? opts.only : '';
        var out = [];
        for (var i = 0; i < (syllables || []).length; i++) {
            var s = syllables[i];
            var got = claimed(counts, s);
            var left = s.c - got;
            if (only === 'started' && (got === 0 || left === 0)) continue;
            if (only === 'untouched' && got !== 0) continue;
            if (only === 'done' && left !== 0) continue;
            if (find && s.r.indexOf(find) < 0
                     && s.l.indexOf(find) < 0
                     && s.i.indexOf(find) < 0
                     && s.il.indexOf(find) < 0) continue;
            out.push({
                pk: s.p, path: s.p, label: s.l, reading: s.r,
                'initial': s.il,
                'sound': s.l + '  ' + s.r,
                'known': got,
                'characters': s.c,
                'left': left
            });
        }
        // Most still to learn first, then the biggest sound, then by reading so
        // the order is total and rows do not shuffle when something is claimed
        // elsewhere.
        out.sort(function (a, b) {
            if (a.left !== b.left) return b.left - a.left;
            if (a.characters !== b.characters) return b.characters - a.characters;
            return a.reading < b.reading ? -1 : (a.reading > b.reading ? 1 : 0);
        });
        return out;
    }

    /** Where the reader is, over the whole index. */
    function summarise(syllables, known) {
        var counts = byReading(known);
        var out = { sounds: 0, started: 0, complete: 0, known: 0, characters: 0 };
        for (var i = 0; i < (syllables || []).length; i++) {
            var s = syllables[i];
            var got = claimed(counts, s);
            out.sounds++;
            out.known += got;
            out.characters += s.c;
            if (got > 0) out.started++;
            if (got === s.c) out.complete++;
        }
        return out;
    }

    return {
        byReading: byReading,
        rowsOf: rowsOf,
        summarise: summarise
    };
}
