// =============================================================================
// KnownProgressModule — what the record adds up to, said encouragingly.
//
// NEVER LEAD WITH THE FRACTION. The corpus is 8,763 (character, reading) pairs.
// A child who has learnt fifty readings - weeks of work - is at 0.6%, and a
// tracker that opens with that number has told them their effort rounds to
// nothing. The same fifty readings can put a dozen stories within reach, and
// that is the true thing worth saying first.
//
// So the order is: what you can read, then what is close, then the counts.
// Counts are last because they are the least motivating true statement
// available, not because they are unimportant.
//
// NOTHING SCOLDS. There is no "only", no "still", no red. An empty record is
// where everyone starts and is phrased as an invitation. A number that has not
// moved is not remarked on - a tracker that notices stalling is a tracker a
// child stops opening.
//
// Pure - no DOM, no fetch, no clock.
// =============================================================================

/**
 * Returns { summarise, nearestStories, nearlyDoneSounds, headline, counts }.
 *
 * @param readability createReadability() - the same arithmetic the catalogue
 *                    and the reader use, so a story called readable here is
 *                    the story called readable there
 */
function createKnownProgress(readability) {

    // Ready means a reader can carry it alone. That is the 'comfortable' and
    // 'just right' bands together: 90% and up, where the unknown characters
    // are few enough to be carried by context.
    var READY_FROM = 0.90;
    var NEARLY_FROM = 0.75;

    function fitsOf(census, known) {
        var out = [];
        for (var address in (census || {})) {
            var fit = readability.of(census[address], known);
            if (!fit) continue;
            out.push({
                address: address,
                title: census[address].title || address,
                ratio: fit.ratio,
                unknown: fit.unknown,
                band: fit.band.id
            });
        }
        return out;
    }

    /** The whole picture, in one pass. */
    function summarise(census, syllables, known) {
        var fits = fitsOf(census, known);
        var ready = 0, nearly = 0;
        for (var i = 0; i < fits.length; i++) {
            if (fits[i].ratio >= READY_FROM) ready++;
            else if (fits[i].ratio >= NEARLY_FROM) nearly++;
        }

        var counted = counts(syllables, known);
        return {
            readings: counted.readings,
            characters: counted.characters,
            sounds: counted.sounds,
            soundsStarted: counted.started,
            soundsComplete: counted.complete,
            stories: fits.length,
            ready: ready,
            nearly: nearly,
            fits: fits
        };
    }

    /** Readings and characters claimed, and how many sounds they touch. */
    function counts(syllables, known) {
        var characters = {};
        var byReading = {};
        var k = known || [];
        for (var i = 0; i < k.length; i++) {
            var at = k[i].indexOf(':');
            if (at < 0) continue;
            characters[k[i].slice(0, at)] = true;
            var r = k[i].slice(at + 1);
            byReading[r] = (byReading[r] || 0) + 1;
        }
        var started = 0, complete = 0, all = 0;
        for (var j = 0; j < (syllables || []).length; j++) {
            var s = syllables[j];
            var got = byReading[s.r] || 0;
            if (got > s.c) got = s.c;
            all++;
            if (got > 0) started++;
            if (got === s.c) complete++;
        }
        return {
            readings: k.length,
            characters: Object.keys(characters).length,
            sounds: all, started: started, complete: complete
        };
    }

    /**
     * The stories closest to being readable, nearest first.
     *
     * Ordered by how many NEW readings each needs, not by how readable it
     * already is. "Three readings away" is a thing somebody can go and do; 87%
     * is a thing somebody can only feel.
     */
    function nearestStories(progress, howMany) {
        var out = [];
        for (var i = 0; i < progress.fits.length; i++) {
            if (progress.fits[i].ratio < READY_FROM) out.push(progress.fits[i]);
        }
        out.sort(function (a, b) {
            if (a.unknown !== b.unknown) return a.unknown - b.unknown;
            return b.ratio - a.ratio;
        });
        return out.slice(0, howMany || 5);
    }

    /**
     * Sounds with something claimed and something left, nearest to done first.
     *
     * Only sounds already begun. Suggesting an untouched one would be advice;
     * this list is meant to be a nudge towards finishing something, which is a
     * smaller and much more likely act.
     */
    function nearlyDoneSounds(syllables, known, howMany) {
        var claimed = {};
        var k = known || [];
        for (var i = 0; i < k.length; i++) {
            var at = k[i].indexOf(':');
            if (at < 0) continue;
            var r = k[i].slice(at + 1);
            claimed[r] = (claimed[r] || 0) + 1;
        }
        var out = [];
        for (var j = 0; j < (syllables || []).length; j++) {
            var s = syllables[j];
            var got = claimed[s.r] || 0;
            if (got > s.c) got = s.c;
            if (got === 0 || got === s.c) continue;
            out.push({ reading: s.r, label: s.l, path: s.p,
                       left: s.c - got, known: got, characters: s.c });
        }
        out.sort(function (a, b) {
            if (a.left !== b.left) return a.left - b.left;
            return b.characters - a.characters;
        });
        return out.slice(0, howMany || 5);
    }

    function plural(n, one, many) {
        return n + ' ' + (n === 1 ? one : many);
    }

    /**
     * The line at the top. What a reader can do, never what they lack.
     */
    function headline(p) {
        if (!p || p.readings === 0) {
            return 'Ready when you are.';
        }
        if (p.ready === 0 && p.nearly === 0) {
            return 'A start has been made.';
        }
        if (p.ready === 0) {
            return plural(p.nearly, 'story is', 'stories are') + ' nearly within reach.';
        }
        if (p.ready === 1) {
            return 'One story you can read on your own.';
        }
        return plural(p.ready, 'story', 'stories') + ' you can read on your own.';
    }

    /** The line under it. Says what to do, or what just happened. */
    function encouragement(p) {
        if (!p || p.readings === 0) {
            return 'Mark a reading you already know and this page starts keeping count. '
                 + 'Everyone begins here.';
        }
        if (p.ready === 0 && p.nearly === 0) {
            return plural(p.readings, 'reading', 'readings') + ' marked. '
                 + 'The first stories come within reach in a handful more - '
                 + 'the common characters do most of the work.';
        }
        if (p.ready === 0) {
            return 'A few more readings and the first one opens up.';
        }
        if (p.nearly > 0) {
            return 'And ' + plural(p.nearly, 'other', 'others')
                 + ' close behind.';
        }
        return 'Room to go further whenever you want it.';
    }

    return {
        summarise: summarise,
        counts: counts,
        nearestStories: nearestStories,
        nearlyDoneSounds: nearlyDoneSounds,
        headline: headline,
        encouragement: encouragement
    };
}
