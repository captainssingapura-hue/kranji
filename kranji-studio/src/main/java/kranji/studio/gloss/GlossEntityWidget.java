package kranji.studio.gloss;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.grid.RelationGridModule;
import hue.captains.singapura.js.homing.grid.StockCellsModule;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;

import java.util.List;

/**
 * One entity type of the gloss model, on a grid, wired into the cascade.
 *
 * <h2>The cascade</h2>
 *
 * <p>Each widget listens for a selection on the entity <b>directly above</b> it
 * and knows nothing else about what happened there. On hearing one it reloads
 * itself scoped to those keys, auto-selects the first row, and announces that
 * — which is what the entity below it is listening for. Select 地 and the
 * sounds narrow to {@code de0} and {@code di4}, the first is taken, and the
 * senses narrow to that pair, all without any widget knowing the shape of the
 * chain it sits in.</p>
 *
 * <p>The chain itself is declared once, in {@link GlossRelations#upstreamOf},
 * so adding a relation does not mean teaching every widget about it.</p>
 *
 * <h2>Why keys and not objects</h2>
 *
 * <p>Only primary keys travel. A message says "sound {@code 22320:di4} is
 * selected" and never carries the row, so a widget cannot come to depend on the
 * shape of another's data, and a stale row cannot be broadcast as though it
 * were current. The receiving widget asks the server for what it needs, which
 * is also what makes the filtering testable in Java rather than trapped in the
 * browser.</p>
 *
 * <p>Scoping is done by the repository projection, not by the grid: the request
 * carries {@code parent=} and the response holds only rows under it. A grid
 * that held everything and hid most of it would report the wrong count and copy
 * the wrong rows.</p>
 */
public abstract class GlossEntityWidget<SELF extends GlossEntityWidget<SELF>>
        extends WorkspaceWidget<WorkspaceWidget._None, SELF> {

    /** The relation this widget shows — one of {@link GlossRelations#relations()}. */
    protected abstract String entity();

    @Override public Class<_None> paramsType() { return _None.class; }

    /**
     * MULTI so the same relation can be opened twice — two sense grids scoped
     * to different sounds is a legitimate way to compare them.
     */
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
                        new GlossCss.gl_root(),
                        new GlossCss.gl_bar(),
                        new GlossCss.gl_status(),
                        new GlossCss.gl_find(),
                        new GlossCss.gl_grid_host()),
                        GlossCss.INSTANCE));
    }

    @Override
    protected List<String> constructBodyJs() {
        String entity = entity();
        String upstream = GlossRelations.upstreamOf(entity);
        String up = upstream == null ? "null" : "'" + upstream + "'";
        String refSource = GlossRelations.refSourceOf(entity);
        String ref = refSource == null ? "null" : "'" + refSource + "'";

        return List.of(
                "    var ENTITY = '" + entity + "';",
                "    var UPSTREAM = " + up + ";",
                "    // The relation whose rows point AT this one. A phrase sense is",
                "    // reached two ways: down from its phrase, or across from a",
                "    // citation that names it. Whichever was touched last wins.",
                "    var REF_SOURCE = " + ref + ";",
                "",
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, gl_root);",
                "",
                "    var bar = branch.createElement('bar', 'div');",
                "    css.setClass(bar, gl_bar);",
                "    root.appendChild(bar);",
                "",
                "    var find = branch.createElement('find', 'input');",
                "    css.setClass(find, gl_find);",
                "    find.type = 'search';",
                "    find.placeholder = 'filter ' + ENTITY;",
                "    bar.appendChild(find);",
                "",
                "    // Re-asks the server for the rows it is already showing. The",
                "    // curated relation reads the working files off disk, so this is",
                "    // the whole edit loop: change p041.tsv, press this, see it.",
                "    var again = branch.createElement('again', 'button');",
                "    again.type = 'button';",
                "    again.textContent = 'Refresh';",
                "    again.title = 'Re-read from source';",
                "    bar.appendChild(again);",
                "",
                "    var status = branch.createElement('status', 'div');",
                "    css.setClass(status, gl_status);",
                "    bar.appendChild(status);",
                "",
                "    var host = branch.createElement('host', 'div');",
                "    css.setClass(host, gl_grid_host);",
                "    root.appendChild(host);",
                "",
                "    var owner = Object.freeze({ toString: function () { return 'gloss/' + ENTITY; } });",
                "    var grid = null;",
                "    var seq = 0;",
                "    // The pane can become active before the relation has arrived, so an",
                "    // activation that finds no grid is remembered rather than dropped.",
                "    var __focusWhenReady = false;",
                "    var __rows = [];",
                "    var __columns = [];",
                "    var __find = '';",
                "    var __selected = [];",
                "    var __note = '';      // the relation's own sentence, when it has one",
                "",
                "    var __party = (workspaceCtx && workspaceCtx.glossParty)",
                "                ? workspaceCtx.glossParty : null;",
                "    var __actorId = null;",
                "",
                "    // ── The bus ───────────────────────────────────────────────",
                "",
                "    function announce(pks) {",
                "        if (__party && __actorId) {",
                "            __party.tellFrom(__actorId,",
                "                    { kind: 'PksSelected', entity: ENTITY, pks: pks });",
                "        }",
                "    }",
                "",
                "    // Selection is set here and announced here, never inferred from",
                "    // the grid firing back at us - which is what keeps a programmatic",
                "    // auto-select from looping through the bus and returning as a",
                "    // change we would then re-announce.",
                "    function select(pk) {",
                "        if (pk === null) {",
                "            if (__selected.length === 0) return;",
                "            __selected = [];",
                "            announce([]);",
                "            return;",
                "        }",
                "        if (__selected.length === 1 && __selected[0] === pk) return;",
                "        __selected = [pk];",
                "        announce([pk]);",
                "    }",
                "",
                "    // ── Rows ──────────────────────────────────────────────────",
                "",
                "    // '1 rows' is the kind of thing nobody fixes and everybody notices.",
                "    function countText(shown, total) {",
                "        var noun = total === 1 ? ' row' : ' rows';",
                "        return shown === total ? total + noun",
                "                               : shown + ' of ' + total + noun;",
                "    }",
                "",
                "    function matching() {",
                "        if (!__find) return __rows;",
                "        var out = [];",
                "        for (var i = 0; i < __rows.length; i++) {",
                "            var row = __rows[i], hit = false;",
                "            for (var c = 0; c < __columns.length; c++) {",
                "                var v = row[__columns[c]];",
                "                if (v !== undefined && v !== null",
                "                        && String(v).indexOf(__find) >= 0) { hit = true; break; }",
                "            }",
                "            if (hit) out.push(row);",
                "        }",
                "        return out;",
                "    }",
                "",
                "    function freshCellsBranch() {",
                "        if (branch.getBranch('cells')) branch.dissolveBranch('cells');",
                "        var b = branch.createBranch('cells');",
                "        b.activate(owner);",
                "        return b;",
                "    }",
                "",
                "    function relationOver(rows, columns) {",
                "        var byPk = {};",
                "        var order = [];",
                "        rows.forEach(function (r) { byPk[r.pk] = r; order.push(r.pk); });",
                "        var subs = [];",
                "        return {",
                "            pks:     function () { return order.slice(); },",
                "            columns: function () { return columns.slice(); },",
                "            get:     function (pk, col) {",
                "                         return byPk[pk] ? byPk[pk][col] : undefined; },",
                "            subscribe:   function (fn) { subs.push(fn); },",
                "            unsubscribe: function (fn) {",
                "                         var i = subs.indexOf(fn);",
                "                         if (i >= 0) subs.splice(i, 1); },",
                "            // Read-only until editing goes through the repository.",
                "            update:     function () {},",
                "            deleteRows: function () {}",
                "        };",
                "    }",
                "",
                "    function draw() {",
                "        var rows = matching();",
                "        if (grid) { grid.destroy(); grid = null; }",
                "        grid = new RelationGrid({",
                "            container: host,",
                "            branch:    freshCellsBranch(),",
                "            adapter:   relationOver(rows, __columns),",
                "            label:     'Gloss ' + ENTITY,",
                "            cellFactory: function () { return new TextCell(); },",
                "            // A person clicking a row is the other way a selection",
                "            // starts. It goes through the same door as the automatic",
                "            // one, so downstream cannot tell them apart.",
                "            onCursorMoved: function (pk) { if (pk) select(pk); }",
                "        });",
                "        // The relation's own sentence first, when it has one. The",
                "        // coverage root carries the whole-library figure this way, and",
                "        // it is what a person opening that grid came to read.",
                "        status.textContent = (__note ? __note + '  \\u00b7  ' : '')",
                "                           + countText(rows.length, __rows.length);",
                "        if (__focusWhenReady) { __focusWhenReady = false; grid.focus(); }",
                "    }",
                "",
                "    // The first row, taken automatically. It is a guess, and the",
                "    // right one: a relation ordered by the authoring sequence puts",
                "    // the principal reading and the primary sense first, so the",
                "    // cascade lands on what a person most likely meant.",
                "    function takeFirst() {",
                "        var rows = matching();",
                "        if (!rows.length) { __selected = []; announce([]); return; }",
                "        var pk = rows[0].pk;",
                "        __selected = [pk];",
                "        if (grid && __columns.length) grid.selectCell(pk, __columns[0]);",
                "        announce([pk]);",
                "    }",
                "",
                "    /**",
                "     * parentPks null loads the whole relation; [] loads nothing.",
                "     * `from` names the relation those keys belong to when they are",
                "     * refs pointing at us rather than our own parent's keys.",
                "     */",
                "    var __lastPks = null, __lastFrom = null;",
                "",
                "    function load(parentPks, from) {",
                "        var mine = ++seq;",
                "        __lastPks = parentPks; __lastFrom = from;",
                "        var url = '/gloss-relation?name=' + encodeURIComponent(ENTITY);",
                "        if (parentPks !== null) {",
                "            // One parameter per key, never a joined string: a sense key",
                "            // ends in its meaning, and a meaning is free English that can",
                "            // hold any separator you might pick.",
                "            if (parentPks.length === 0) {",
                "                url += '&parent=';   // selected nothing, which shows nothing",
                "            } else {",
                "                for (var i = 0; i < parentPks.length; i++) {",
                "                    url += '&parent=' + encodeURIComponent(parentPks[i]);",
                "                }",
                "            }",
                "            if (from) url += '&from=' + encodeURIComponent(from);",
                "        }",
                "        // fetch, not import(). A module is memoised on its URL and",
                "        // never re-evaluated, so re-asking for a relation returned the",
                "        // copy already held - Refresh needed a cache-busting parameter",
                "        // and left a dead module behind on every press. no-store says",
                "        // the same thing to the HTTP cache, which has no headers here",
                "        // to say it for us.",
                "        status.textContent = 'Loading...';",
                "        fetch(url, { cache: 'no-store' })",
                "            .then(function (r) { return r.json(); })",
                "            .then(function (mod) {",
                "                if (mine !== seq) return;   // a later selection already won",
                "                if (mod.problem) {",
                "                    __rows = []; __columns = [];",
                "                    status.textContent = mod.problem;",
                "                    return;",
                "                }",
                "                __rows = mod.rows || [];",
                "                __columns = mod.columns || [];",
                "                __note = mod.note || '';",
                "                draw();",
                "                takeFirst();",
                "            })",
                "            .catch(function (e) {",
                "                if (mine !== seq) return;",
                "                status.textContent = 'Could not load ' + ENTITY + ': ' + e;",
                "            });",
                "    }",
                "",
                "    find.addEventListener('input', function () {",
                "        __find = find.value;",
                "        if (__columns.length) draw();",
                "    });",
                "",
                "    // ── Joining ───────────────────────────────────────────────",
                "",
                "    if (__party) {",
                "        __actorId = 'gloss-' + ENTITY + '-'",
                "                  + Math.random().toString(36).slice(2, 8);",
                "        __party.joinActor({",
                "            id: __actorId,",
                "            parentSecretary: 'glossSelection',",
                "            reactors: {",
                "                SelectionChanged: function (msg) {",
                "                    // Arriving across a ref, from whatever points at us.",
                "                    if (REF_SOURCE && msg.entity === REF_SOURCE) {",
                "                        load(msg.pks || [], REF_SOURCE);",
                "                        return;",
                "                    }",
                "                    if (!UPSTREAM) return;   // a root scopes nothing",
                "                    var pks;",
                "                    if (msg.entity === UPSTREAM) pks = msg.pks;",
                "                    else if (msg.entity === '*' && msg.all) pks = msg.all[UPSTREAM];",
                "                    else return;",
                "                    // undefined is 'never selected' and shows everything;",
                "                    // [] is 'selected nothing' and shows nothing. Only the",
                "                    // second is a scope.",
                "                    load(pks === undefined ? null : pks, null);",
                "                }",
                "            }",
                "        });",
                "    }",
                "",
                "    again.addEventListener('click', function () { load(__lastPks, __lastFrom); });",
                "",
                "    if (UPSTREAM === null) {",
                "        load(null, null);",
                "    } else if (__party && __actorId) {",
                "        // Ask what is already selected, rather than sitting empty until",
                "        // something upstream happens to change again. The answer decides",
                "        // between showing everything and showing this scope.",
                "        __party.tellFrom(__actorId, { kind: 'WhatIsSelected' });",
                "    } else {",
                "        load(null, null);   // no bus: an unscoped browser is still useful",
                "    }",
                "",
                "    return {",
                "        root: root,",
                "        // Hand activation to the grid, which is what the pane is for.",
                "        //",
                "        // This does NOT fix the first-click problem, and it would be easy",
                "        // to think it does. A pane's first pointer event lands on the",
                "        // shell's own overlay and never reaches the grid, so the first",
                "        // click on a row selects nothing - see UD-003. Focusing on",
                "        // activation is right on its own account; it is not that fix.",
                "        setActive: function (active) {",
                "            if (!active) return;",
                "            if (grid) grid.focus(); else __focusWhenReady = true;",
                "        },",
                "        partyDeregister: function () {",
                "            if (__party && __actorId) __party.leaveActor(__actorId);",
                "        },",
                "        dispose: function () {",
                "            seq++;",
                "            if (grid) { grid.destroy(); grid = null; }",
                "        }",
                "    };");
    }
}
