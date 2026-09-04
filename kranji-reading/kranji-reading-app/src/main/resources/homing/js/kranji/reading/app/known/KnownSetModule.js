// =============================================================================
// KnownSetModule — the set a child can read without pinyin.
//
// The key is (character, reading), not the character. 行 read xíng may be
// secure while 行 in 银行 is not, and the whole job of this set is deciding
// whether to show a reading - so the reading is what has to be known. A
// character-keyed set would drop the pinyin from 银行 the moment 行走 was
// learnt, which is exactly the failure the adaptive mechanic exists to avoid.
//
// Pure: no DOM, no storage, no clock. Storage puts it somewhere; the party
// shares it; this decides what it means.
// =============================================================================

/**
 * Returns { keyOf, has, add, remove, readingsOf, characters, size }.
 *
 * A set is a plain array of keys, so it serialises as-is into a Secretary's
 * state and into an export file. Order is insertion order, which makes a
 * diff between two exports readable.
 */
function createKnownSet() {

    /**
     * codePoint and reading, joined.
     *
     * The codepoint rather than the glyph so the key is fixed-width ASCII and
     * cannot be broken by a surrogate pair; the reading in its diacritic form
     * because that is what the corpus, the reader and the child all use.
     */
    function keyOf(zi, reading) {
        if (!zi || !reading) return null;
        var cp = typeof zi === 'number' ? zi : zi.codePointAt(0);
        return cp + ':' + reading;
    }

    function has(known, zi, reading) {
        var k = keyOf(zi, reading);
        return !!k && known.indexOf(k) >= 0;
    }

    return {
        keyOf: keyOf,

        has: has,

        /**
         * Should this character carry its reading?
         *
         * The whole mechanic, in one place, so that the cell that draws it does
         * not have to know what a known set is and the rule can be tested
         * without a browser.
         *
         *   all       -> annotate
         *   none      -> bare
         *   adaptive  -> annotate unless (character, reading) is in the set
         *
         * The test is on the PAIR. The cell already knows which reading it is
         * showing - that is what the annotation says - so two occurrences of 行
         * in one article get different answers when only xíng has been marked.
         *
         * A cell with no reading to show is bare whatever the mode: there is
         * nothing to hide, and nothing to claim it by. That covers punctuation,
         * the squares past the end of a line, and any character the corpus does
         * not model - which is how an out-of-corpus character degrades rather
         * than breaking the page.
         *
         * An unrecognised mode annotates. Failing towards MORE support is the
         * safe direction: a child shown pinyin they did not need has lost
         * nothing, one denied pinyin they did need is stuck.
         */
        annotates: function (mode, known, zi, reading) {
            if (!zi || !reading) return false;
            if (mode === 'none') return false;
            if (mode === 'adaptive') return !has(known || [], zi, reading);
            return true;
        },

        /** Idempotent: claiming something twice is not an error and not a duplicate. */
        add: function (known, zi, reading) {
            var k = keyOf(zi, reading);
            if (!k || known.indexOf(k) >= 0) return known;
            return known.concat([k]);
        },

        remove: function (known, zi, reading) {
            var k = keyOf(zi, reading);
            if (!k) return known;
            var i = known.indexOf(k);
            if (i < 0) return known;
            return known.slice(0, i).concat(known.slice(i + 1));
        },

        /** Every reading of one character that is in the set. */
        readingsOf: function (known, zi) {
            var cp = typeof zi === 'number' ? zi : (zi ? zi.codePointAt(0) : null);
            if (cp === null) return [];
            var prefix = cp + ':';
            var out = [];
            for (var i = 0; i < known.length; i++) {
                if (known[i].indexOf(prefix) === 0) {
                    out.push(known[i].slice(prefix.length));
                }
            }
            return out;
        },

        /**
         * The distinct characters represented, as codepoints.
         *
         * A character counts once however many of its readings are known -
         * which is why this is not simply the set's length, and why a count of
         * "characters known" and a count of "readings known" are different
         * numbers that should never be shown as if they were the same.
         */
        characters: function (known) {
            var seen = {}, out = [];
            for (var i = 0; i < known.length; i++) {
                var cp = known[i].slice(0, known[i].indexOf(':'));
                if (!seen[cp]) { seen[cp] = true; out.push(Number(cp)); }
            }
            return out;
        },

        size: function (known) { return known.length; }
    };
}
