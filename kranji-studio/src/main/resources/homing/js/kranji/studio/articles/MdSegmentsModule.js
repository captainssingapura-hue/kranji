// =============================================================================
// MdSegmentsModule — a document as the tree of segments it should become.
//
// The third view on one fetch. The document says what the file says; the
// squares say what a reader will do with a page of it; this says how many
// pages there are, and how big each one is.
//
// It is not a table of contents, though it is what one will be built from. A
// TOC shows a reader where to go. This shows an AUTHOR what the cut produced -
// which sections are too long, which parts the budget had to invent, and which
// segments still have no address a bookmark could survive.
//
// Three things are worth looking at and each has a mark of its own: a segment
// over budget, a part the budget cut, and a heading with no pinned id.
//
// No CJK literal appears in this file. Every character it draws came from a
// draft on disk.
// =============================================================================

/**
 * opts = {
 *   css,
 *   budget,     // characters; a segment over it is marked
 *   classes: {  // tree, node, rule, name, part, path, id, unpinned,
 *               // size, over, bar, fill
 *   }
 * }
 * Returns { draw(branch, host, tree) }.
 */
function createMdSegments(opts) {

    var C = opts.classes;
    var minted = 0;

    function el(branch, tag, cls, text) {
        var e = branch.createElement('g' + (++minted), tag);
        if (cls) opts.css.setClass.apply(opts.css, [e].concat(cls));
        if (text) e.textContent = text;
        return e;
    }

    // The widest subtree there is, so every bar is drawn against the same
    // scale. A bar measured against its own parent says nothing.
    function widest(node, so) {
        var most = Math.max(so, node.chars || 0);
        for (var i = 0; i < (node.children || []).length; i++) {
            most = widest(node.children[i], most);
        }
        return most;
    }

    /**
     * How much prose a segment holds, drawn.
     *
     * <p>Width is quantised into a fixed set of classes, because the exact
     * figure is beside it in numbers and a computed width would have to be an
     * inline style.</p>
     */
    function bar(branch, into, chars, most) {
        var track = el(branch, 'div', C.bar, null);
        var share = most > 0 ? chars / most : 0;
        var fill = el(branch, 'div', [C.fill, C.step[Math.min(C.step.length - 1,
                Math.round(share * (C.step.length - 1)))]], null);
        track.appendChild(fill);
        into.appendChild(track);
    }

    function node(branch, host, seg, most) {
        var row = el(branch, 'div', [C.node, C.depth[Math.min(C.depth.length - 1,
                (seg.path || '').split('.').length - (seg.path ? 0 : 1))]], null);

        // A part has no heading of its own; it is named by where it sits.
        row.appendChild(el(branch, 'span', C.path, seg.path || '/'));
        row.appendChild(el(branch, 'span', seg.level === 0 ? C.part : C.name,
                seg.level === 0 ? 'part' : seg.title));

        // The address, and the absence of one. A segment nobody pinned cannot
        // be bookmarked, and that is the author's to fix rather than ours.
        if (seg.id) row.appendChild(el(branch, 'span', C.id, '#' + seg.id));
        else if (seg.level > 1) row.appendChild(el(branch, 'span', C.unpinned, 'no id'));

        if (seg.chars > 0) {
            bar(branch, row, seg.chars, most);
            row.appendChild(el(branch, 'span',
                    seg.chars > opts.budget ? [C.size, C.over] : C.size,
                    String(seg.chars)));
        }
        host.appendChild(row);

        for (var i = 0; i < (seg.children || []).length; i++) {
            node(branch, host, seg.children[i], most);
        }
    }

    return {

        /** Draws the whole tree. Appends, never clears — see MdPreviewModule. */
        draw: function (branch, host, tree) {
            if (!tree) return;
            var box = el(branch, 'div', C.tree, null);
            node(branch, box, tree, widest(tree, 1));
            host.appendChild(box);
        }
    };
}
