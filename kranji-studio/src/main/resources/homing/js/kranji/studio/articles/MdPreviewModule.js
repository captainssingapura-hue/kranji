// =============================================================================
// MdPreviewModule — a parsed draft, drawn.
//
// The parser hands over blocks and spans; this turns them into elements. It
// used to be neither: the server built a string of HTML and the pane assigned
// it to innerHTML, which is how a widget quietly stops owning its own DOM.
// Everything here is minted through the caller's branch, so the whole document
// goes when that branch is dissolved and nothing is left behind.
//
// It draws a DOCUMENT, not the reader's grid. Stage one asks whether the file
// says what its author meant; what a child eventually sees is stage two, and a
// different rendering of these same blocks.
//
// Three things are shown that a reader would never see, because they are what
// an author has come here to check: a ‹…› run is tinted, an override wears its
// pinned reading, and a heading with no id says so.
//
// No CJK literal appears in this file. Every character it draws came from a
// draft on disk.
// =============================================================================

/**
 * opts = {
 *   css,        // the class setter
 *   classes: {  // every class this sets, by name
 *     title, h2, h3, pin, unpinned, p, li, marker,
 *     quote, verse, vline, run, ruby, rt, msg
 *   }
 * }
 * Returns { draw(branch, host, blocks), say(branch, host, text) }.
 */
function createMdPreview(opts) {

    var C = opts.classes;
    var BULLET = '•';
    var minted = 0;

    // Every element the preview owns comes from here, so there is exactly one
    // place that could ever fail to go through the branch.
    function el(branch, tag, cls, text) {
        var e = branch.createElement('m' + (++minted), tag);
        if (cls) opts.css.setClass(e, cls);
        if (text) e.textContent = text;
        return e;
    }

    // ── A line ─────────────────────────────────────────────────────────

    function ruby(branch, host, span) {
        var r = el(branch, 'ruby', C.ruby, span.t);
        r.appendChild(el(branch, 'rt', C.rt, span.r));
        host.appendChild(r);
    }

    function piece(branch, host, span) {
        if (span.k === 'ruby') { ruby(branch, host, span); return; }
        // strong and em are the elements themselves - they mean the same thing
        // the tags mean, and inventing classes for them would say less.
        var tag = span.k === 'strong' ? 'strong'
                : span.k === 'em'     ? 'em'
                : 'span';
        host.appendChild(el(branch, tag, null, span.t));
    }

    /**
     * One line's spans, in order.
     *
     * <p>Consecutive spans marked `run` are one run. They travel flat so that
     * nesting never has to cross the wire, and are grouped back into a single
     * tinted element here — which is also the only place that knows a run can
     * hold emphasis.</p>
     */
    function line(branch, host, spans) {
        var open = null;
        for (var i = 0; i < (spans || []).length; i++) {
            var s = spans[i];
            if (!s.run) { open = null; piece(branch, host, s); continue; }
            if (!open) {
                open = el(branch, 'span', C.run, null);
                host.appendChild(open);
            }
            piece(branch, open, s);
        }
    }

    // ── The blocks ─────────────────────────────────────────────────────

    function simple(branch, host, b, tag, cls) {
        var e = el(branch, tag, cls, null);
        line(branch, e, b.lines && b.lines[0]);
        host.appendChild(e);
    }

    function heading(branch, host, b) {
        var h = el(branch, 'div', b.level === 3 ? C.h3 : C.h2, null);
        line(branch, h, b.lines && b.lines[0]);
        // The address a reader keeps. Shown either way: a heading that has one
        // is worth being able to copy, and a heading that has none is worth
        // noticing before somebody links to it.
        h.appendChild(b.id ? el(branch, 'span', C.pin, '#' + b.id)
                           : el(branch, 'span', C.unpinned, 'no id'));
        host.appendChild(h);
    }

    function item(branch, host, b, marker) {
        var it = el(branch, 'div', C.li, null);
        it.appendChild(el(branch, 'span', C.marker, marker));
        var body = el(branch, 'span', null, null);
        line(branch, body, b.lines && b.lines[0]);
        it.appendChild(body);
        host.appendChild(it);
    }

    function verse(branch, host, b) {
        var v = el(branch, 'div', C.verse, null);
        for (var i = 0; i < (b.lines || []).length; i++) {
            var ln = el(branch, 'div', C.vline, null);
            line(branch, ln, b.lines[i]);
            v.appendChild(ln);
        }
        host.appendChild(v);
    }

    function block(branch, host, b) {
        switch (b.kind) {
            case 'title':   simple(branch, host, b, 'div', C.title); return;
            case 'heading': heading(branch, host, b); return;
            case 'p':       simple(branch, host, b, 'p', C.p); return;
            case 'quote':   simple(branch, host, b, 'div', C.quote); return;
            case 'verse':   verse(branch, host, b); return;
            case 'li':      item(branch, host, b, BULLET); return;
            // The author's own number, not a count kept here. A list that
            // starts at 3 starts at 3.
            case 'oli':     item(branch, host, b, b.level + '.'); return;
            // A kind this does not know is the parser having grown one. Draw it
            // as text and let it look wrong: a preview that silently omits a
            // block is worse than no preview.
            default:        simple(branch, host, b, 'div', C.msg); return;
        }
    }

    return {

        /**
         * Draws the whole document into `host`.
         *
         * <p>`branch` is the caller's, and is expected to be a fresh one — this
         * appends and never clears, because clearing is the branch's job and
         * doing it here would mean reaching for the wipes the framework's
         * rules exist to keep out of a view.</p>
         */
        draw: function (branch, host, blocks) {
            for (var i = 0; i < (blocks || []).length; i++) {
                block(branch, host, blocks[i]);
            }
        },

        /** What the pane says when there is no document to draw. */
        say: function (branch, host, text) {
            host.appendChild(el(branch, 'div', C.msg, text));
        }
    };
}
