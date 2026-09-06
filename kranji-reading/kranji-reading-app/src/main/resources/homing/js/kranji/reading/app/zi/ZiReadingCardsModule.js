// =============================================================================
// ZiReadingCardsModule — the readings of one character, as cards.
//
// This was a RelationGrid, and a grid was the wrong instrument once the claim
// column came out of it. A grid earns its keep when rows are compared down
// columns, when there are enough of them to scroll, and when one of them is
// aimed at. None of that is true here: a character has one reading, or two,
// rarely more, and nothing in the pane acts on a row any more. What was left
// was ten fixed-width columns of one-letter values, most of them empty, and a
// meaning squeezed into whatever remained.
//
// A card can use the width it is given. The reading leads, the meaning reads
// as a sentence rather than a cell, examples sit under it, and the five parts
// of the syllable go along the bottom as small labelled pairs - still side by
// side, so two readings that differ only in the tone still say so at a glance,
// which was the one thing the columns were genuinely good for.
//
// The parts keep their short headings - ini, med, nuc, coda, tone, same - for
// the same reason they had them as columns: spelled out they cost more room
// than the words are worth, and a reader working on sounds learns the six in a
// sitting.
//
// A row per SENSE, not per reading. A reading that means three things is three
// things a reader could be looking at, and pooling them into one cell
// separated by bars made the commonest case - one sense - pay for the rarest,
// while giving no sense a place to put its own examples.
// =============================================================================

/**
 * opts = {
 *   branch, css, host, owner,   // where the cards mount
 *   swf,                        // createPinyinSwf() - the display boundary
 *   cardClass, headClass, readingClass, meaningClass,
 *   examplesClass, partsClass, partClass, partKeyClass
 * }
 * Returns { show(codePoint, readings), refresh(), destroy() }.
 */
function createZiReadingCards(opts) {

    var rows = [];
    var minted = 0;

    // Identity is the column name still: these are what a person reads, and
    // the parts are a group that reads as one once you know what they are.
    var PART_LABELS = [
        ['initial', 'ini'], ['medial', 'med'], ['nucleus', 'nuc'],
        ['coda', 'coda'], ['tone', 'tone'], ['homophones', 'same']
    ];

    function freshBranch() {
        if (opts.branch.getBranch('readingCards')) {
            opts.branch.dissolveBranch('readingCards');
        }
        var b = opts.branch.createBranch('readingCards');
        b.activate(opts.owner);
        return b;
    }

    function el(b, tag, cls, text) {
        var e = b.createElement('c' + (++minted), tag);
        if (cls) opts.css.setClass(e, cls);
        if (text !== undefined && text !== null && text !== '') e.textContent = text;
        return e;
    }

    function draw() {
        var b = freshBranch();
        for (var i = 0; i < rows.length; i++) {
            var r = rows[i];
            var card = el(b, 'div', opts.cardClass);

            var head = el(b, 'div', opts.headClass);
            head.appendChild(el(b, 'span', opts.readingClass, r.reading));
            card.appendChild(head);

            // A reading nothing has glossed still gets its card: it is a
            // reading of this character, and the pane is showing the character
            // rather than the dictionary.
            if (r.meaning) {
                card.appendChild(el(b, 'div', opts.meaningClass, r.meaning));
            }
            if (r.examples) {
                card.appendChild(el(b, 'div', opts.examplesClass, r.examples));
            }

            var parts = el(b, 'div', opts.partsClass);
            for (var p = 0; p < PART_LABELS.length; p++) {
                var name = PART_LABELS[p][0];
                var value = r[name];
                // An absent part already reads as "-" from the server rather
                // than as a blank that could be missing data.
                var one = el(b, 'div', opts.partClass);
                one.appendChild(el(b, 'span', opts.partKeyClass, PART_LABELS[p][1]));
                one.appendChild(el(b, 'span', null, String(value === undefined ? '-' : value)));
                parts.appendChild(one);
            }
            card.appendChild(parts);

            opts.host.appendChild(card);
        }
    }

    return {

        /** Replace the cards. `readings` is what /zi-detail served. */
        show: function (codePoint, readings) {
            rows = [];
            for (var i = 0; i < readings.length; i++) {
                var r = readings[i];
                var senses = (r.senses && r.senses.length) ? r.senses
                           : [{ meaning: '', examples: [] }];
                for (var j = 0; j < senses.length; j++) {
                    rows.push({
                        // The display boundary. Nothing downstream keys on this.
                        reading: opts.swf.toSWF(r.reading)
                               + (r.principal ? '' : '  (also)'),
                        meaning: senses[j].meaning,
                        examples: (senses[j].examples || []).join('   '),
                        initial: r.initial,
                        medial: r.medial,
                        nucleus: r.nucleus,
                        coda: r.coda,
                        tone: r.tone,
                        homophones: r.homophones
                    });
                }
            }
            draw();
        },

        /** Redraw in place - the cards have not moved. */
        refresh: function () { draw(); },

        destroy: function () {
            if (opts.branch.getBranch('readingCards')) {
                opts.branch.dissolveBranch('readingCards');
            }
        }
    };
}
