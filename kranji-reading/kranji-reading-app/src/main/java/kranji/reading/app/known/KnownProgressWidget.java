package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.grid.RelationGridModule;
import hue.captains.singapura.js.homing.grid.StockCellsModule;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;

import java.util.List;

/**
 * How many characters this reader can read, and what that is called.
 *
 * <p>{@link KnownProgressModule} holds the bands, their marks and every word of
 * the wording. This draws them as a grid and lets somebody walk it.</p>
 *
 * <h2>The ladder is a relation, so it gets the grid</h2>
 *
 * <p>Eleven bands, each with a mark and a name — that is a relation, and this
 * app already has one thing that draws relations properly. The earlier bar was
 * eleven anonymous slivers a reader had to hover to identify; a grid cell has
 * room to say which band it is, and the cursor that comes with it turns the
 * ladder from a picture into something you can look through.</p>
 *
 * <h2>Two designs: the same grid, transposed</h2>
 *
 * <p>Wide, the bands are the <b>columns</b> of a single row. Narrow, they are
 * the <b>rows</b> of a single column. One adapter builds either, because a
 * transpose is all the difference amounts to once the ladder is a relation —
 * which is the argument for using the grid rather than drawing two shapes by
 * hand.</p>
 *
 * <p>Crossing the threshold rebuilds, rather than restyling. A shape that
 * depends on a class landing at the right moment can quietly not change, and
 * did.</p>
 *
 * <h2>Standing and looking are different</h2>
 *
 * <p>Where the reader stands is painted into the cell and does not move. The
 * cursor is theirs to walk wherever they like, and what it rests on is
 * described underneath — a band behind them by what it took, a band ahead by
 * what it needs, the one they are in by the count itself. Painting the standing
 * band on a span <em>inside</em> the cell keeps it clear of the grid's own
 * selection painting, so the two never fight.</p>
 *
 * <h2>One widget, one surface</h2>
 *
 * <p>No card and no borders inside. The ladder and the line under it are one
 * statement — where you are, and what the cursor is resting on — and a box
 * round either half invites the eye to read them as two things that happen to
 * share a pane.</p>
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
                new ModuleImports<>(
                        List.of(new RelationGridModule.RelationGrid()),
                        RelationGridModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new StockCellsModule.TextCell()),
                        StockCellsModule.INSTANCE),
                new ModuleImports<>(List.of(
                        new ReadingCss.kr_widget_root(),
                        new ReadingCss.kr_status(),
                        new ReadingCss.kr_title(),
                        new ReadingCss.kr_pb_grid(),
                        new ReadingCss.kr_pb_cell(),
                        new ReadingCss.kr_pb_line(),
                        new ReadingCss.kr_pb_badge(),
                        new ReadingCss.kr_pb_name(),
                        new ReadingCss.kr_pb_name_line(),
                        new ReadingCss.kr_pb_past(),
                        new ReadingCss.kr_pb_here(),
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
                "    // Under this, eleven cells across are eleven slivers; the bands",
                "    // become rows instead and the names get their room back.",
                "    var NARROW = 420;",
                "",
                "    // Borrowed off a stock cell rather than built: the type that gates",
                "    // bulk edit is not exported, and this grid never edits anything.",
                "    var TEXT_TYPE = (new TextCell()).effectiveType();",
                "",
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, kr_widget_root);",
                "",
                "    // No card. This is one widget saying one thing, and a border round",
                "    // part of it invites the eye to read the parts as separate - the",
                "    // ladder and what the cursor found on it are the same sentence.",
                "    var host = branch.createElement('host', 'div');",
                "    css.setClass(host, kr_pb_grid);",
                "    root.appendChild(host);",
                "",
                "    var mark = branch.createElement('mark', 'div');",
                "    css.setClass(mark, kr_title);",
                "    root.appendChild(mark);",
                "",
                "    var detail = branch.createElement('detail', 'div');",
                "    css.setClass(detail, kr_pb_detail);",
                "    root.appendChild(detail);",
                "",
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
                "    var __design = null;      // 'wide' | 'tall'",
                "    var __grid = null;",
                "    var __cells = [];",
                "    var __looking = null;     // the band the cursor rests on",
                "    var __spanSeq = 0;",
                "",
                "    function labelAt(key) {",
                "        for (var i = 0; i < BANDS.length; i++) {",
                "            if (progress.keyOf(BANDS[i]) === key) return progress.labelOf(BANDS[i]);",
                "        }",
                "        return '';",
                "    }",
                "",
                "    function bandFor(key) {",
                "        for (var i = 0; i < BANDS.length; i++) {",
                "            if (progress.keyOf(BANDS[i]) === key) return BANDS[i];",
                "        }",
                "        return null;",
                "    }",
                "",
                "    // A cell is handed its value, not its address, so it finds its band",
                "    // by the label it was given. The labels are distinct by construction",
                "    // - no two bands share a name - and a test holds them that way.",
                "    function bandLabelled(label) {",
                "        for (var i = 0; i < BANDS.length; i++) {",
                "            if (progress.labelOf(BANDS[i]) === label) return BANDS[i];",
                "        }",
                "        return null;",
                "    }",
                "",
                "    // ── One cell, which knows whether it is being stood on ────────",
                "",
                "    function bandCell(cellsBranch, tall) {",
                "        var self = {",
                "            box: null, badge: null, name: null, value: null,",
                "            render: function (place, value) {",
                "                var n = ++__spanSeq;",
                "                self.box   = cellsBranch.createElement('b' + n, 'span');",
                "                self.badge = cellsBranch.createElement('g' + n, 'span');",
                "                self.name  = cellsBranch.createElement('n' + n, 'span');",
                "                css.setClass(self.badge, kr_pb_badge);",
                "                css.setClass(self.name, tall ? kr_pb_name_line : kr_pb_name);",
                "                self.box.appendChild(self.badge);",
                "                self.box.appendChild(self.name);",
                "                place.appendChild(self.box);",
                "                __cells.push(self);",
                "                return self.update(value);",
                "            },",
                "            update: function (value) {",
                "                self.value = value;",
                "                self.repaint();",
                "                return self;",
                "            },",
                "            repaint: function () {",
                "                if (!self.box) return;",
                "                // The mark and the name are two elements, not one string:",
                "                // a tile stacks them and a line sets them side by side,",
                "                // and neither can do that with the two run together.",
                "                var band = bandLabelled(String(self.value));",
                "                self.badge.textContent = band ? band.icon : '';",
                "                self.name.textContent = band ? band.name",
                "                    : (self.value == null ? '' : String(self.value));",
                "                var here = progress.bandOf(progress.count(__known));",
                "                var shape = tall ? kr_pb_line : kr_pb_cell;",
                "                if (band && band.from === here.from) {",
                "                    css.setClass(self.box, shape, kr_pb_here);",
                "                } else if (band && band.from < here.from) {",
                "                    css.setClass(self.box, shape, kr_pb_past);",
                "                } else {",
                "                    css.setClass(self.box, shape);",
                "                }",
                "            },",
                "            onSelect: function () {},",
                "            preview: function () { return self; },",
                "            effectiveType: function () { return TEXT_TYPE; }",
                "        };",
                "        return self;",
                "    }",
                "",
                "    // ── The relation, and its transpose ───────────────────────────",
                "",
                "    /**",
                "     * Wide: one row, a column per band. Narrow: one column, a row per",
                "     * band. The values are the same either way, which is the whole",
                "     * reason the two designs cost one adapter rather than two.",
                "     */",
                "    function adapter(design) {",
                "        var keys = BANDS.map(function (b) { return progress.keyOf(b); });",
                "        var wide = design === 'wide';",
                "        return {",
                "            pks:     function () { return wide ? ['ladder'] : keys.slice(); },",
                "            columns: function () { return wide ? keys.slice() : ['band']; },",
                "            get:     function (pk, col) { return labelAt(wide ? col : pk); },",
                "            subscribe: function () {},",
                "            unsubscribe: function () {},",
                "            update: function () {},",
                "            deleteRows: function () {}",
                "        };",
                "    }",
                "",
                "    function freshCells() {",
                "        if (branch.getBranch('cells')) branch.dissolveBranch('cells');",
                "        var b = branch.createBranch('cells');",
                "        b.activate(owner);",
                "        return b;",
                "    }",
                "",
                "    function build(design) {",
                "        var cellsBranch = freshCells();",
                "        if (__grid) { __grid.destroy(); __grid = null; }",
                "        __cells = [];",
                "        __grid = new RelationGrid({",
                "            container: host,",
                "            branch: cellsBranch,",
                "            adapter: adapter(design),",
                "            label: 'The bands, and where you are on them',",
                "            // The names are in the cells; a header would say them twice.",
                "            header: { show: false },",
                "            cellFactory: function () { return bandCell(cellsBranch, design === 'tall'); },",
                "            onCursorMoved: function (pk, column) {",
                "                __looking = bandFor(design === 'wide' ? column : pk);",
                "                showDetail();",
                "            }",
                "        });",
                "    }",
                "",
                "    // ── What the cursor is resting on ─────────────────────────────",
                "",
                "    function showDetail() {",
                "        var n = progress.count(__known);",
                "        var band = __looking || progress.bandOf(n);",
                "        mark.textContent = band.icon + '  ' + band.name;",
                "        detail.textContent = progress.detailFor(band, n);",
                "    }",
                "",
                "    function wanted() {",
                "        var width = root.clientWidth;",
                "        if (!width) return __design || 'wide';",
                "        return width < NARROW ? 'tall' : 'wide';",
                "    }",
                "",
                "    function render() {",
                "        var design = wanted();",
                "        if (design !== __design) {",
                "            __design = design;",
                "            __looking = null;",
                "            build(design);",
                "        } else {",
                "            for (var i = 0; i < __cells.length; i++) __cells[i].repaint();",
                "        }",
                "        showDetail();",
                "    }",
                "",
                "    // Rebuilt only when the answer to wanted() changes, so dragging the",
                "    // tile costs a comparison per frame rather than a rebuild.",
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
                "        // Seeded from the device, never written to it.",
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
