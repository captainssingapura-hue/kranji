package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;

import java.util.List;

/**
 * How many characters this reader can read, and what that is called.
 *
 * <p>{@link KnownProgressModule} holds the bands, their marks and every word of
 * the wording. This draws them, in one of two ways.</p>
 *
 * <h2>Two designs, not one design rotated</h2>
 *
 * <p>A wide pane gets a <b>bar</b>: eleven segments across the top, then the
 * mark, the name, the count and the distance to the next name. A narrow pane
 * gets a <b>list</b>: every band on a line of its own with its mark and name,
 * the passed ones ruled off, the current one lit, and the two sentences tucked
 * under it.</p>
 *
 * <p>They share no markup. The first attempt did — one row of segments with the
 * flex direction swapped by a class — and it was wrong twice over. A vertical
 * bar is just a horizontal bar on its side, which wastes the one thing a narrow
 * column has plenty of; and making the shape depend on a class applied at the
 * right moment produced a pane that quietly stayed horizontal when it should
 * not have. Crossing the threshold now throws the markup away and builds the
 * other one, which is a thing that either happened or did not.</p>
 *
 * <p>The measurement is the pane's width, not the window's: this widget lives
 * in a tile a reader can drag to any size, so a wide screen says nothing about
 * the room actually available.</p>
 *
 * <h2>Bands, not a filled proportion</h2>
 *
 * <p>A continuous bar would have to be drawn against the corpus, and 248
 * characters of 8,100 is a sliver that says a term's work came to nothing — the
 * exact number the bands exist to keep off the page.</p>
 *
 * <p>No CJK appears in this file.</p>
 */
public final class KnownProgressWidget
        extends WorkspaceWidget<WorkspaceWidget._None, KnownProgressWidget> {

    public static final KnownProgressWidget INSTANCE = new KnownProgressWidget();

    private KnownProgressWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, KnownProgressWidget> {}

    @Override protected _Construct<_None, KnownProgressWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Progress"; }
    @Override public LifecycleHint lifecycleHint() { return LifecycleHint.MULTI; }

    @Override
    protected List<ModuleImports<? extends Importable>> bodyImports() {
        return List.of(
                new ModuleImports<>(List.of(
                        new ReadingCss.kr_widget_root(),
                        new ReadingCss.kr_status(),
                        new ReadingCss.kr_card(),
                        new ReadingCss.kr_title(),
                        new ReadingCss.kr_body(),
                        new ReadingCss.kr_pb(),
                        new ReadingCss.kr_pb_seg(),
                        new ReadingCss.kr_pb_done(),
                        new ReadingCss.kr_pb_now(),
                        new ReadingCss.kr_pb_list(),
                        new ReadingCss.kr_pb_row(),
                        new ReadingCss.kr_pb_row_done(),
                        new ReadingCss.kr_pb_row_now(),
                        new ReadingCss.kr_pb_mark(),
                        new ReadingCss.kr_pb_name(),
                        new ReadingCss.kr_pb_detail()),
                        ReadingCss.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownSetModule.createKnownSet()),
                        KnownSetModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownProgressModule.createKnownProgress()),
                        KnownProgressModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownStoreModule.createKnownStore()),
                        KnownStoreModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownPersistenceModule.createKnownPersistence()),
                        KnownPersistenceModule.INSTANCE));
    }

    @Override
    protected List<String> constructBodyJs() {
        return List.of(
                "    var progress = createKnownProgress(createKnownSet());",
                "",
                "    // Low band first. Both designs read in the order a reader travels.",
                "    var BANDS = progress.bands.slice().reverse();",
                "",
                "    // Under this a bar of eleven segments is eleven slivers, and the",
                "    // column has room for the names instead.",
                "    var NARROW = 420;",
                "",
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, kr_widget_root);",
                "",
                "    var card = branch.createElement('card', 'div');",
                "    css.setClass(card, kr_card);",
                "    root.appendChild(card);",
                "",
                "    // Attached only while it has something to say.",
                "    var status = branch.createElement('status', 'div');",
                "    css.setClass(status, kr_status);",
                "",
                "    function say(text) {",
                "        if (text) {",
                "            status.textContent = text;",
                "            if (!status.parentNode) root.appendChild(status);",
                "        } else if (status.parentNode) {",
                "            root.removeChild(status);",
                "        }",
                "    }",
                "",
                "    var owner = Object.freeze({ toString: function () { return 'knownProgress'; } });",
                "    var __known = [];",
                "    var __design = null;   // 'bar' | 'list'",
                "    var __paint = null;",
                "",
                "    // A fresh branch for whichever design is being built: a branch",
                "    // registers names, and reusing one would collide.",
                "    function freshView() {",
                "        if (branch.getBranch('view')) branch.dissolveBranch('view');",
                "        var b = branch.createBranch('view');",
                "        b.activate(owner);",
                "        return b;",
                "    }",
                "",
                "    function el(b, name, tag, klass, parent) {",
                "        var node = b.createElement(name, tag);",
                "        if (klass) css.setClass(node, klass);",
                "        parent.appendChild(node);",
                "        return node;",
                "    }",
                "",
                "    // ── The wide design: a strip, then the words ──────────────────",
                "",
                "    function buildBar() {",
                "        var b = freshView();",
                "        var strip = el(b, 'strip', 'div', kr_pb, card);",
                "        var segs = [];",
                "        for (var i = 0; i < BANDS.length; i++) {",
                "            var seg = el(b, 'seg' + i, 'div', kr_pb_seg, strip);",
                "            seg.title = BANDS[i].name;",
                "            segs.push(seg);",
                "        }",
                "        var mark = el(b, 'mark', 'div', kr_title, card);",
                "        var name = el(b, 'name', 'div', kr_title, card);",
                "        var count = el(b, 'count', 'div', kr_body, card);",
                "        var next = el(b, 'next', 'div', kr_body, card);",
                "",
                "        return function paintBar(n) {",
                "            var here = progress.bandOf(n);",
                "            for (var i = 0; i < segs.length; i++) {",
                "                if (BANDS[i].from === here.from)     css.setClass(segs[i], kr_pb_seg, kr_pb_now);",
                "                else if (BANDS[i].from < here.from)  css.setClass(segs[i], kr_pb_seg, kr_pb_done);",
                "                else                                css.setClass(segs[i], kr_pb_seg);",
                "            }",
                "            mark.textContent = progress.icon(n);",
                "            name.textContent = progress.headline(n);",
                "            count.textContent = progress.line(n);",
                "            next.textContent = progress.nextLine(n);",
                "        };",
                "    }",
                "",
                "    // ── The narrow design: every band on its own line ─────────────",
                "",
                "    function buildList() {",
                "        var b = freshView();",
                "        var list = el(b, 'list', 'div', kr_pb_list, card);",
                "        var rows = [];",
                "        for (var i = 0; i < BANDS.length; i++) {",
                "            var row = el(b, 'row' + i, 'div', kr_pb_row, list);",
                "            el(b, 'mark' + i, 'span', kr_pb_mark, row).textContent = BANDS[i].icon;",
                "            el(b, 'name' + i, 'span', kr_pb_name, row).textContent = BANDS[i].name;",
                "            // The two sentences live under the band they are about, so",
                "            // the eye does not have to carry a number down the list.",
                "            var detail = el(b, 'detail' + i, 'div', kr_pb_detail, list);",
                "            rows.push({ row: row, detail: detail, from: BANDS[i].from });",
                "        }",
                "",
                "        return function paintList(n) {",
                "            var here = progress.bandOf(n);",
                "            for (var i = 0; i < rows.length; i++) {",
                "                var at = rows[i];",
                "                if (at.from === here.from)     css.setClass(at.row, kr_pb_row, kr_pb_row_now);",
                "                else if (at.from < here.from)  css.setClass(at.row, kr_pb_row, kr_pb_row_done);",
                "                else                           css.setClass(at.row, kr_pb_row);",
                "                at.detail.textContent = at.from === here.from",
                "                    ? progress.line(n) + ' ' + progress.nextLine(n)",
                "                    : '';",
                "            }",
                "        };",
                "    }",
                "",
                "    // ── Which one, and when it changes ────────────────────────────",
                "",
                "    function wanted() {",
                "        var width = root.clientWidth;",
                "        if (!width) return __design || 'bar';",
                "        return width < NARROW ? 'list' : 'bar';",
                "    }",
                "",
                "    function render() {",
                "        var design = wanted();",
                "        if (design !== __design) {",
                "            __design = design;",
                "            __paint = design === 'list' ? buildList() : buildBar();",
                "        }",
                "        __paint(progress.count(__known));",
                "    }",
                "",
                "    // Rebuilt when the tile is dragged across the threshold, not on",
                "    // every frame: render() only throws markup away when the answer",
                "    // to wanted() actually changed.",
                "    if (typeof ResizeObserver !== 'undefined') {",
                "        new ResizeObserver(function () { render(); }).observe(root);",
                "    }",
                "",
                "    var __knownParty = (workspaceCtx && workspaceCtx.knownParty)",
                "                     ? workspaceCtx.knownParty : null;",
                "    var __knownActorId = null;",
                "    if (__knownParty) {",
                "        __knownActorId = 'known/prog-' + Math.random().toString(36).slice(2, 8);",
                "        __knownParty.joinActor({",
                "            id: __knownActorId,",
                "            parentSecretary: 'knownSet',",
                "            reactors: {",
                "                KnownChanged: function (msg) {",
                "                    __known = (msg && msg.known) ? msg.known : [];",
                "                    render();",
                "                }",
                "            }",
                "        });",
                "        __knownParty.tellFrom(__knownActorId, { kind: 'WhatIsKnown' });",
                "        // Seeded from the device, never written to it. Seeding matters",
                "        // here: opened on its own, this would otherwise say nobody has",
                "        // read anything on a device read on for months.",
                "        createKnownPersistence({",
                "            store: createKnownStore(),",
                "            tell: function (msg) {",
                "                __knownParty.tellFrom(__knownActorId, msg);",
                "            },",
                "            onProblem: function (broken) {",
                "                if (broken) {",
                "                    say('Could not read what is saved on this device.');",
                "                }",
                "            }",
                "        }).start();",
                "    }",
                "",
                "    render();",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) { render(); },",
                "        partyDeregister: function () {",
                "            if (__knownActorId && __knownParty) {",
                "                try { __knownParty.leave(__knownActorId); } catch (e) {}",
                "            }",
                "        }",
                "    };"
        );
    }
}
