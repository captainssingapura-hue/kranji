// =============================================================================
// KnownProgressModule — how many characters this reader can read, and what
// that is called.
//
// ONE NUMBER. Not readings, not sounds covered, not stories unlocked. Those
// are all true and all measurable and every one of them turns the page into a
// dashboard - the thing a learner scans instead of reads. What somebody wants
// to know is how many characters they can read, so that is the only number
// here, and the bands are what make it mean something.
//
// A COUNT IS NOT A PLACE. 248 is a fact and "Reading along" is a place to be,
// and the second is what somebody remembers. So the band is the headline and
// the number sits under it.
//
// NEVER LEAD WITH THE FRACTION. The corpus is around 8,100 characters. 248 of
// them is 3%, and a page that opens with that has told a child their term's
// work rounds to nothing. The denominator never appears.
//
// Pure - no DOM, no fetch, no clock.
// =============================================================================

// TWO KINDS OF NAME, and the change is deliberate. Up to 2,500 the names are
// about moving - getting going, reading along, well on your way - because that
// is what those months are, and nobody wants a rank for still being on the
// road. From 3,500 the reader has arrived somewhere and the names say what
// they are rather than what they are doing.
//
// Where the boundaries come from. The five at the top are borrowed rather than
// invented, so passing one means something outside this app:
//
//   2,500  the frequently-used set
//   3,500  the first level of the standard list
//   5,000  midway through the second level
//   6,500  the first and second levels together
//   8,000  all but the last hundred of this corpus, which holds 8,100
//
// 3,500 upwards was one band once, which said the difference between a reader
// of 3,600 characters and one of 8,000 was nothing worth naming. It is most of
// a decade, and it is the stretch where nothing else can show movement - the
// stories all read easily by then.
//
// Below 2,500 each band is roughly double the last, so the names come quickly
// at first and slowly later. That is the right shape: the encouragement is
// wanted most in the first weeks and least once somebody is plainly reading.
//
// The marks climb with the names and never turn into medals. A trophy shelf is
// what this pane was cut back from, and rank names are quite enough of it.
var CHARACTER_BANDS = [
    { from: 8000, name: 'Guru',                    icon: '🐉' },
    { from: 6500, name: 'Pro plus',                icon: '🌄' },
    { from: 5000, name: 'Pro',                     icon: '🦅' },
    { from: 3500, name: 'Semi-pro',                icon: '🏔' },
    { from: 2500, name: 'The everyday characters', icon: '⛰' },
    { from: 1000, name: 'Well on your way',        icon: '🧭' },
    { from: 500,  name: 'Hitting your stride',     icon: '🏃' },
    { from: 200,  name: 'Reading along',           icon: '🥾' },
    { from: 50,   name: 'Getting going',           icon: '🚶' },
    { from: 1,    name: 'First characters',        icon: '👣' },
    { from: 0,    name: 'Ready when you are',      icon: '🚩' }
];

/**
 * Returns { bands, count, bandOf, next, headline, icon, line, nextLine }.
 *
 * @param knownSet createKnownSet() - so what counts as a character is decided
 *                 in one place rather than by picking keys apart here
 */
function createKnownProgress(knownSet) {

    /**
     * Characters, not readings.
     *
     * A character claimed at one of its two readings counts: the reader can
     * read it where they meet it, which is the question this page asks. The
     * per-reading picture is what the Known pane is for.
     */
    function count(known) {
        return knownSet.characters(known || []).length;
    }

    function bandOf(n) {
        for (var i = 0; i < CHARACTER_BANDS.length; i++) {
            if (n >= CHARACTER_BANDS[i].from) return CHARACTER_BANDS[i];
        }
        return CHARACTER_BANDS[CHARACTER_BANDS.length - 1];
    }

    /** The band above this one, or null at the top. */
    function next(n) {
        var here = bandOf(n);
        for (var i = CHARACTER_BANDS.length - 1; i >= 0; i--) {
            if (CHARACTER_BANDS[i].from > here.from) return CHARACTER_BANDS[i];
        }
        return null;
    }

    /** The band's name. This is the headline, not the number. */
    function headline(n) {
        return bandOf(n).name;
    }

    /** The band's mark. Kept apart from the name so a later look can place it. */
    function icon(n) {
        return bandOf(n).icon;
    }

    /** The number, plainly, with no denominator. */
    function line(n) {
        if (n === 0) {
            return 'Mark a character you can read and this starts counting.';
        }
        if (n === 1) {
            return 'One character you can read.';
        }
        return n + ' characters you can read.';
    }

    /**
     * How far to the next name. A distance somebody can act on, never a
     * percentage of the way there.
     */
    function nextLine(n) {
        var up = next(n);
        if (!up) return 'There is no name past this one. There rarely is.';
        var togo = up.from - n;
        if (n === 0) {
            return 'The first one makes it ' + up.name + '.';
        }
        if (togo === 1) {
            return 'One more and this becomes ' + up.name + '.';
        }
        return togo + ' more and this becomes ' + up.name + '.';
    }

    return {
        bands: CHARACTER_BANDS,
        count: count,
        bandOf: bandOf,
        next: next,
        headline: headline,
        icon: icon,
        line: line,
        nextLine: nextLine
    };
}
