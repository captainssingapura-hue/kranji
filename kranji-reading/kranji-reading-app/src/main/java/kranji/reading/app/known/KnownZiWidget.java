package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.grid.RelationGridModule;
import hue.captains.singapura.js.homing.grid.StockCellsModule;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;
import kranji.reading.app.ui.PinyinSwfModule;

import java.util.List;

/**
 * Everything claimed, on one grid.
 *
 * <h2>A row is a reading, not a character</h2>
 *
 * <p>Which is the whole point of the pane. A character read two ways that is
 * only half learnt occupies two rows and shows one of them — and a list keyed
 * on characters could not say that at all. The tally above the grid gives both
 * numbers for the same reason: <em>characters</em> and <em>readings</em> are
 * different counts, and quoting either as the other would flatter or undersell
 * what a child has actually done.</p>
 *
 * <h2>Read-only</h2>
 *
 * <p>Nothing here writes. A reading marked by accident still has to be as easy
 * to withdraw as it was to claim — but the place to withdraw it is the Character pane,
 * beside the character's other readings, where the claim was made and where the
 * consequence of taking it back is visible. In a dense grid a withdrawal is one
 * press away from whichever row the cursor happens to be on, and what it
 * destroys is a claim somebody made on purpose.</p>
 *
 * <p>So the adapter's {@code update} and {@code deleteRows} are both no-ops.
 * This pane answers <em>what has been claimed</em>; it does not arbitrate
 * claims.</p>
 *
 * <h2>The list, reviewed - not maintained</h2>
 *
 * <p>Import and export used to be split across this pane and Mark Known, on the
 * reasoning that export is a read and import is a write. Both are list
 * operations and both are maintenance, so they now live together in Import /
 * Export and neither is here. What is left is the one thing this pane is for:
 * looking at what has been claimed.</p>
 *
 * <p>Ordered by codepoint, then reading, so a character's readings sit
 * together and a row does not move under the cursor when something elsewhere
 * is marked.</p>
 *
 * <p>No CJK appears in this file. Characters arrive from the corpus.</p>
 */
public final class KnownZiWidget
        extends WorkspaceWidget<WorkspaceWidget._None, KnownZiWidget> {

    public static final KnownZiWidget INSTANCE = new KnownZiWidget();

    private KnownZiWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, KnownZiWidget> {}

    @Override protected _Construct<_None, KnownZiWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Known"; }
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
                        new ReadingCss.kr_bar(),
                        new ReadingCss.kr_btn(),
                        new ReadingCss.kr_select(),
                        new ReadingCss.kr_kn_on(),
                        new ReadingCss.kr_kn_tally(),
                        new ReadingCss.kr_grid_host()),
                        ReadingCss.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownSetModule.createKnownSet()),
                        KnownSetModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownStoreModule.createKnownStore()),
                        KnownStoreModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownPersistenceModule.createKnownPersistence()),
                        KnownPersistenceModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new PinyinSwfModule.createPinyinSwf()),
                        PinyinSwfModule.INSTANCE));
    }

    @Override
    protected List<String> constructBodyJs() {
        return List.of(
                "    var knownSet = createKnownSet();",
                "    var swf = createPinyinSwf();",
                "",
                "    // Meaning sits between the sound and the tally: it is the second",
                "    // thing a person wants about a row, and putting it last would file",
                "    // it behind a count.",
                "    var COLUMNS = ['character', 'reading', 'meaning', 'readings known'];",
                "",
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, kr_widget_root);",
                "",
                "    var bar = branch.createElement('bar', 'div');",
                "    css.setClass(bar, kr_bar);",
                "    root.appendChild(bar);",
                "",
                "    var find = branch.createElement('find', 'input');",
                "    css.setClass(find, kr_select);",
                "    find.type = 'search';",
                "    find.placeholder = 'find a character, a reading or a meaning';",
                "    bar.appendChild(find);",
                "",
                "    // Not a decoration: a character only partly learnt is the case the",
                "    // whole (character, reading) key exists for, and it is the one worth",
                "    // being able to see on its own.",
                "    var multi = branch.createElement('multi', 'button');",
                "    css.setClass(multi, kr_btn);",
                "    multi.type = 'button';",
                "    multi.textContent = 'More than one reading';",
                "    bar.appendChild(multi);",
                "",
                "    var tally = branch.createElement('tally', 'div');",
                "    css.setClass(tally, kr_kn_tally);",
                "    bar.appendChild(tally);",
                "",
                "    var status = branch.createElement('status', 'div');",
                "    css.setClass(status, kr_status);",
                "    root.appendChild(status);",
                "",
                "    var host = branch.createElement('host', 'div');",
                "    css.setClass(host, kr_grid_host);",
                "    root.appendChild(host);",
                "",
                "    var owner = Object.freeze({ toString: function () { return 'knownZi'; } });",
                "    var grid = null;",
                "    var __known = [];",
                "    var __onlyMulti = false;",
                "    var __find = '';",
                "    // Pair key -> primary meaning. Empty until the glossary lands, and",
                "    // empty forever if no collection was deployed - a row with no",
                "    // meaning is a row, not an error.",
                "    var __meanings = {};",
                "",
                "    /** A key becomes a row; the glyph comes back from the codepoint. */",
                "    function rowsOf(known) {",
                "        var counts = {};",
                "        var i;",
                "        for (i = 0; i < known.length; i++) {",
                "            var cp = known[i].slice(0, known[i].indexOf(':'));",
                "            counts[cp] = (counts[cp] || 0) + 1;",
                "        }",
                "        var out = [];",
                "        for (i = 0; i < known.length; i++) {",
                "            var at = known[i].indexOf(':');",
                "            var code = Number(known[i].slice(0, at));",
                "            var reading = known[i].slice(at + 1);",
                "            var glyph = String.fromCodePoint(code);",
                "            if (__onlyMulti && counts[String(code)] < 2) continue;",
                "            // The known-set key IS the gloss key - codepoint, colon,",
                "            // canonical reading - so this is a lookup and not a join.",
                "            // That is the whole return on keying both on the pair.",
                "            var meaning = __meanings[known[i]] || '';",
                "            // Matched against both forms: the canonical is what a person",
                "            // can type, the SWF is what they can paste from the reader.",
                "            // And against the meaning, because 'which ones were about",
                "            // water' is a question somebody will now have.",
                "            if (__find && glyph.indexOf(__find) < 0",
                "                       && reading.indexOf(__find) < 0",
                "                       && meaning.indexOf(__find) < 0",
                "                       && swf.toSWF(reading).indexOf(__find) < 0) continue;",
                "            out.push({",
                "                pk: known[i], code: code, r: reading,",
                "                'character': glyph,",
                "                'reading': swf.toSWF(reading),",
                "                'meaning': meaning,",
                "                'readings known': counts[String(code)]",
                "            });",
                "        }",
                "        // By codepoint, then reading, so a character's readings sit",
                "        // together and rows do not shuffle when something is marked.",
                "        out.sort(function (a, b) {",
                "            if (a.code !== b.code) return a.code - b.code;",
                "            return a.r < b.r ? -1 : (a.r > b.r ? 1 : 0);",
                "        });",
                "        return out;",
                "    }",
                "",
                "    function relationOver(rows) {",
                "        var byPk = {};",
                "        var order = [];",
                "        rows.forEach(function (r) { byPk[r.pk] = r; order.push(r.pk); });",
                "        var subs = [];",
                "        return {",
                "            pks:     function () { return order.slice(); },",
                "            columns: function () { return COLUMNS.slice(); },",
                "            get:     function (pk, col) {",
                "                         return byPk[pk] ? byPk[pk][col] : undefined; },",
                "            subscribe:   function (fn) { subs.push(fn); },",
                "            unsubscribe: function (fn) {",
                "                         var i = subs.indexOf(fn);",
                "                         if (i >= 0) subs.splice(i, 1); },",
                "            // Read-only, both of them. A row is a claim, so the only",
                "            // edit it could accept is withdrawing it - and that belongs",
                "            // where the claim was made, beside the other readings of",
                "            // the same character. Here it would be one press away from",
                "            // whichever row the cursor happened to be on.",
                "            update: function () {},",
                "            deleteRows: function () {}",
                "        };",
                "    }",
                "",
                "    // The grid owns its cell elements, so its branch is remade on every",
                "    // render - a branch registers names, and reusing one would collide.",
                "    function freshCellsBranch() {",
                "        if (branch.getBranch('cells')) branch.dissolveBranch('cells');",
                "        var b = branch.createBranch('cells');",
                "        b.activate(owner);",
                "        return b;",
                "    }",
                "",
                "    function render() {",
                "        if (grid) { grid.destroy(); grid = null; }",
                "",
                "        var readings = __known.length;",
                "        var chars = knownSet.characters(__known).length;",
                "        tally.textContent = readings === 0 ? 'Nothing marked yet'",
                "            : chars + (chars === 1 ? ' character' : ' characters')",
                "              + ', ' + readings + (readings === 1 ? ' reading' : ' readings');",
                "",
                "        var rows = rowsOf(__known);",
                "        if (rows.length === 0) {",
                "            status.textContent = readings === 0",
                "                ? 'Mark a reading known in the Character pane and it will appear here.'",
                "                : 'Nothing matches that filter.';",
                "            return;",
                "        }",
                "        status.textContent = rows.length === readings",
                "            ? 'A row is a reading. Claim or return one in the Character pane.'",
                "            : rows.length + ' of ' + readings + ' readings shown.';",
                "",
                "        grid = new RelationGrid({",
                "            container: host,",
                "            branch: freshCellsBranch(),",
                "            adapter: relationOver(rows),",
                "            label: 'Readings marked known',",
                "            cellFactory: function () { return new TextCell(); }",
                "        });",
                "    }",
                "",
                "    // The glossary, once. It is profile-free and changes only when the",
                "    // collections do, so the browser caches the module by URL and a",
                "    // second visit pays nothing - the same bargain the census makes.",
                "    //",
                "    // Fetched rather than awaited: the grid is useful without meanings",
                "    // and must not wait on them. Whatever is on screen when the module",
                "    // lands is redrawn with the column filled in.",
                "    import('/zi-gloss')",
                "        .then(function (mod) {",
                "            __meanings = mod.meanings || {};",
                "            if (__known.length) render();",
                "        })",
                "        .catch(function () {",
                "            // No glossary deployed. The column stays empty, which is what",
                "            // it says for an unglossed reading anyway - so there is",
                "            // nothing to tell anyone and nothing to recover from.",
                "        });",
                "",
                "    find.addEventListener('input', function () {",
                "        __find = find.value ? find.value.trim() : '';",
                "        render();",
                "    });",
                "",
                "    multi.addEventListener('click', function () {",
                "        __onlyMulti = !__onlyMulti;",
                "        if (__onlyMulti) css.setClass(multi, kr_btn, kr_kn_on);",
                "        else             css.setClass(multi, kr_btn);",
                "        render();",
                "    });",
                "",
                "    var __knownParty = (workspaceCtx && workspaceCtx.knownParty)",
                "                     ? workspaceCtx.knownParty : null;",
                "    var __knownActorId = null;",
                "    if (__knownParty) {",
                "        __knownActorId = 'known/view-' + Math.random().toString(36).slice(2, 8);",
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
                "        // This pane reads the device but never writes it - changed() is",
                "        // never called, so the same module that persists for Mark Known",
                "        // only seeds here. Seeding from here matters: opened on its own,",
                "        // this is the only pane that would otherwise show nothing on a",
                "        // device that has been read on for months.",
                "        createKnownPersistence({",
                "            store: createKnownStore(),",
                "            tell: function (msg) {",
                "                __knownParty.tellFrom(__knownActorId, msg);",
                "            },",
                "            onProblem: function (broken) {",
                "                if (broken) {",
                "                    status.textContent =",
                "                        'Could not read what is saved on this device.';",
                "                }",
                "            }",
                "        }).start();",
                "    }",
                "",
                "    render();",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) {},",
                "        partyDeregister: function () {",
                "            if (__knownActorId && __knownParty) {",
                "                try { __knownParty.leave(__knownActorId); } catch (e) {}",
                "            }",
                "        },",
                "        // The grid holds DOM and listeners of its own, so it is destroyed",
                "        // explicitly rather than left to the branch teardown.",
                "        dispose: function () {",
                "            if (grid) { grid.destroy(); grid = null; }",
                "        }",
                "    };");
    }
}
