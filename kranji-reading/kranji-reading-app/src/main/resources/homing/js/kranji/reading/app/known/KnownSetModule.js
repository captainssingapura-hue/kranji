// =============================================================================
// KnownSetModule — what a claim means, and when a reading is shown.
//
// The key is (character, reading), not the character. 行 read xíng may be
// secure while 行 in 银行 is not, and the whole job of this set is deciding
// whether to show a reading - so the reading is what has to be known. A
// character-keyed set would drop the pinyin from 银行 the moment 行走 was
// learnt, which is exactly the failure the adaptive mechanic exists to avoid.
//
// What used to be here as well: has, add, remove, readingsOf, characters,
// size - all of them operating on a plain array, all of them linear. They are
// gone. Membership belongs to KnownRecord, which is asked rather than
// scanned, and mutation belongs to KnownService, which is the only thing
// allowed to do it. What is left is the two questions that are genuinely
// about meaning rather than storage: what key names this pair, and should
// this cell carry its reading.
//
// Pure: no DOM, no storage, no clock.
// =============================================================================

/**
 * Returns { keyOf, annotates }.
 */
function createKnownSet() {

    /**
     * codePoint and reading, joined.
     *
     * The codepoint rather than the glyph so the key is fixed-width ASCII and
     * cannot be broken by a surrogate pair. The reading in the corpus's own
     * numbered form - di4, not the diacritic - so that this key and
     * ArticleCensus.keyOf produce the same string for the same pair. They must
     * agree exactly; a difference of one character makes every article read as
     * 0% and nothing throws.
     */
    function keyOf(zi, reading) {
        if (!zi || !reading) return null;
        var cp = typeof zi === 'number' ? zi : zi.codePointAt(0);
        return cp + ':' + reading;
    }

    return {
        keyOf: keyOf,

        /**
         * Should this character carry its reading?
         *
         * The whole mechanic, in one place, so that the cell that draws it does
         * not have to know what a known set is and the rule can be tested
         * without a browser.
         *
         *   all       -> annotate
         *   none      -> bare
         *   adaptive  -> annotate unless (character, reading) is claimed
         *
         * `record` is anything answering has(key) - the pane's mirror of the
         * service's record. A missing record reads as an empty one, so a pane
         * that has not been told anything yet shows every reading rather than
         * hiding readings it has no evidence the child knows.
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
        annotates: function (mode, record, zi, reading) {
            if (!zi || !reading) return false;
            if (mode === 'none') return false;
            if (mode === 'adaptive') {
                var key = keyOf(zi, reading);
                return !(record && key && record.has(key));
            }
            return true;
        }
    };
}
