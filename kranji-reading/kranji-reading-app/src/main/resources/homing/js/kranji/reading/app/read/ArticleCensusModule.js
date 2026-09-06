// =============================================================================
// ArticleCensusModule — what an article asks of a reader, counted.
//
// Everything needed to work out how much of an article somebody can read, and
// NOTHING about any particular reader. That split is the whole point: a census
// is a property of the article alone, so the same article always produces the
// same census, and the answer for a given child is that census met with a
// known set somewhere else.
//
// Which is why this is not part of readability. Readability is a comparison
// between two things; this is one of the two.
//
//   total  every Han token, repeats included - a character met forty times is
//          forty moments of support, and that is the reading experience
//   pairs  distinct (character, reading) keys, and how often each occurs
//
// COUNTED BY READING, NOT BY CHARACTER, because that is how the known set is
// keyed and because it is the truthful count. An article using 行 as háng asks
// nothing of a reader who has learnt xíng, and treating the two as one would
// report a readability the child cannot actually achieve.
//
// The server computes the same thing, from the same rule, in ArticleCensus.of.
// It used to send all 475 of them - 274KB - so that one pane could describe
// one article. That was the right shape while the library ranked every article
// and the wrong shape the moment it stopped: the reader already scanned the
// article it is showing, and every cell already carries the reading it
// resolved to. The census was in the browser all along, waiting to be counted.
//
// Pure - no DOM, no fetch, no clock.
// =============================================================================

/**
 * Returns { ofCells, ofLines }.
 */
function createArticleCensus() {

    var set = createKnownSet();

    /**
     * Count cells that have already been scanned and filled.
     *
     * A cell with no reading is not counted at all. That covers punctuation,
     * the padding past the end of a line, and any character the corpus does
     * not model - and it agrees with the server, whose parser drops a
     * character it cannot read rather than guessing one.
     *
     * The key comes from KnownSetModule rather than being spelled out again
     * here. Readability is a set intersection against the known set, so a key
     * that differed by one character would make every article read 0% and
     * nothing would throw. One spelling, one place.
     */
    function ofCells(cells) {
        var total = 0;
        var pairs = {};
        var list = cells || [];
        for (var i = 0; i < list.length; i++) {
            var c = list[i];
            if (!c || !c.z || !c.r) continue;
            var key = set.keyOf(c.z, c.r);
            if (!key) continue;
            pairs[key] = (pairs[key] || 0) + 1;
            total++;
        }
        return { total: total, pairs: pairs };
    }

    return {

        ofCells: ofCells,

        /**
         * The whole article: its lines, and how a line becomes cells.
         *
         * The caller supplies the scan rather than this module importing one,
         * because the pane that draws the article already has it - the same
         * scan, the same readings, the same cells. Counting a second time
         * through a second scanner would be a second chance to disagree.
         */
        ofLines: function (lines, cellsOf) {
            var all = [];
            var list = lines || [];
            for (var i = 0; i < list.length; i++) {
                all = all.concat(cellsOf(list[i]));
            }
            return ofCells(all);
        }
    };
}
