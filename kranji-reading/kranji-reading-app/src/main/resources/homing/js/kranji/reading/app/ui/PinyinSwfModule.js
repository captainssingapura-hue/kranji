// =============================================================================
// PinyinSwfModule — the display boundary for readings.
//
// Everything below the display layer carries the canonical form: standard
// written spelling plus a tone digit, "dong1" / "nü3" / "de0". That form is
// ASCII apart from ü, so it has no combining marks, no two byte sequences that
// look alike, and nothing to normalise - which is what makes it safe as a key
// in IndexedDB, in a JS module, and in a Java registry at the same time.
//
// A child reads dōng, not dong1. This is the only place that conversion
// happens, and past it the diacritic form must not be compared, stored or
// keyed on.
//
// Pure: no DOM, no fetch. The rule is fixed and small, so it is tested against
// a written spec rather than against the Java renderer - two implementations
// agreeing would prove only that they agree.
// =============================================================================

/**
 * Returns { toSWF, fromSWF, canonical, isCanonical }.
 */
function createPinyinSwf() {

    // Tone marks by tone number, for each vowel that can carry one.
    var MARKS = {
        a: ['a', 'ā', 'á', 'ǎ', 'à'],
        e: ['e', 'ē', 'é', 'ě', 'è'],
        i: ['i', 'ī', 'í', 'ǐ', 'ì'],
        o: ['o', 'ō', 'ó', 'ǒ', 'ò'],
        u: ['u', 'ū', 'ú', 'ǔ', 'ù'],
        'ü': ['ü', 'ǖ', 'ǘ', 'ǚ', 'ǜ']
    };

    // The same table read backwards: a marked letter to the plain letter it
    // stands for and the tone it carries. Derived rather than written out, so
    // the two directions cannot drift apart.
    var UNMARK = {};
    for (var base in MARKS) {
        var row = MARKS[base];
        for (var t = 1; t <= 4; t++) UNMARK[row[t]] = { base: base, tone: t };
    }

    /**
     * Which vowel carries the mark.
     *
     * The standard rule, in the order it is stated: a takes it if present;
     * otherwise o or e, which never co-occur; otherwise the LAST vowel, which
     * is what makes iu → iù and ui → uì rather than the other way round.
     */
    function markedIndex(letters) {
        var a = letters.indexOf('a');
        if (a >= 0) return a;
        var o = letters.indexOf('o');
        if (o >= 0) return o;
        var e = letters.indexOf('e');
        if (e >= 0) return e;
        for (var i = letters.length - 1; i >= 0; i--) {
            if (MARKS[letters[i]]) return i;
        }
        return -1;
    }

    return {

        /**
         * "dong1" -> "dōng". A neutral syllable ("de0") loses its digit and
         * takes no mark.
         *
         * Anything that is not a canonical form is returned unchanged rather
         * than mangled: a caller handing this a diacritic string has a bug
         * somewhere above, and turning it into nonsense would hide that.
         */
        toSWF: function (numbered) {
            if (typeof numbered !== 'string' || numbered.length < 2) return numbered;
            var tone = numbered.charCodeAt(numbered.length - 1) - 48;
            if (tone < 0 || tone > 4) return numbered;

            var letters = numbered.slice(0, -1);
            if (tone === 0) return letters;

            var chars = Array.from(letters);
            var at = markedIndex(chars);
            if (at < 0) return letters;
            chars[at] = MARKS[chars[at]][tone];
            return chars.join('');
        },

        /**
         * "dōng" -> "dong1", the inverse. An unmarked syllable is neutral, so
         * "de" -> "de0".
         *
         * Returns null for anything that is not a display-form syllable, which
         * is what lets a caller tell "could not read this" from "read it as
         * neutral" - the two need different answers when a person has typed
         * the line by hand.
         *
         * Composed first, because a mark written as its own code point looks
         * identical on the page and compares unequal. That hazard is the whole
         * reason the canonical form carries a digit instead.
         */
        fromSWF: function (display) {
            if (typeof display !== 'string' || display === '') return null;
            var s = display.normalize ? display.normalize('NFC') : display;

            var chars = Array.from(s);
            var tone = 0;
            for (var i = 0; i < chars.length; i++) {
                var found = UNMARK[chars[i]];
                if (!found) continue;
                if (tone !== 0) return null;      // two marks is not a syllable
                chars[i] = found.base;
                tone = found.tone;
            }

            var letters = chars.join('');
            if (!/^[a-zü]+$/.test(letters)) return null;
            return letters + tone;
        },

        /**
         * Whatever form it arrives in, canonical out - or null if it is not a
         * syllable at all.
         *
         * The entry point for anything crossing INTO the system: a record
         * written before the canonical form existed, a file a parent typed by
         * hand. Inside, use the canonical form directly.
         */
        canonical: function (s) {
            if (this.isCanonical(s)) return s;
            return this.fromSWF(s);
        },

        /** Is this the canonical internal form - spelling plus a tone digit? */
        isCanonical: function (s) {
            if (typeof s !== 'string' || s.length < 2) return false;
            var tone = s.charCodeAt(s.length - 1) - 48;
            if (tone < 0 || tone > 4) return false;
            return /^[a-zü]+$/.test(s.slice(0, -1));
        }
    };
}
