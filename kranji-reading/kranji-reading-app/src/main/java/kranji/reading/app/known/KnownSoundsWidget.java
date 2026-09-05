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
 * The sounds index, weighed by what this reader knows.
 *
 * <h2>What the Sounds tree cannot do</h2>
 *
 * <p>The tree browses the corpus: initial, final, tone, opened a level at a
 * time. It is the right shape for finding a sound and the wrong one for the
 * question this pane exists for — <em>where am I</em>. Answering that means
 * seeing all 1,288 syllables at once and ordering them by an answer that is not
 * in the tree, because the known set never leaves the device and the server
 * therefore cannot have computed it.</p>
 *
 * <p>So the two halves meet here, the same way readability does: the corpus
 * arrives as {@code /syllable-index}, the set arrives over the party, and
 * {@link KnownSoundsModule} intersects them in the browser.</p>
 *
 * <h2>Why a row is a sound and not a character</h2>
 *
 * <p>Known lists what has been claimed, a row per reading, and that is the
 * right pane for reviewing a few hundred claims. It is no use for choosing what
 * to learn next, because it can only show what is already there — the shape of
 * the gap is invisible in a list of the things that are not gaps.</p>
 *
 * <h2>Selecting a row moves the other panes</h2>
 *
 * <p>It publishes a syllable node on the navigation party, which is exactly
 * what the Sounds tree publishes. Characters and Character then follow without
 * either of them learning that this pane exists — the tree was one producer of
 * a sound, and now there are two.</p>
 *
 * <p>Moving the cursor announces the sound unactivated; opening it is a button.
 * The tree taught that: Characters loads on activation rather than on
 * selection, because arrowing down a list would otherwise fire an import per
 * row passed.</p>
 *
 * <p>Read-only, like Known. A claim is made in the reader, beside the
 * character's other readings; here a whole syllable sits under the cursor, and
 * what an accidental edit would destroy is somebody's afternoon.</p>
 *
 * <p>No CJK appears in this file. Characters arrive from the corpus.</p>
 */
public final class KnownSoundsWidget
        extends WorkspaceWidget<WorkspaceWidget._None, KnownSoundsWidget> {

    public static final KnownSoundsWidget INSTANCE = new KnownSoundsWidget();

    private KnownSoundsWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, KnownSoundsWidget> {}

    @Override protected _Construct<_None, KnownSoundsWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Sounds known"; }
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
                        List.of(new KnownSoundsModule.createKnownSounds()),
                        KnownSoundsModule.INSTANCE),
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
                "    var sounds = createKnownSounds();",
                "",
                "    // 'left' is the column the grid is ordered by, so it comes before",
                "    // the two counts it is the difference of.",
                "    var COLUMNS = ['initial', 'sound', 'known', 'characters', 'left'];",
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
                "    find.placeholder = 'find a sound, or an initial';",
                "    bar.appendChild(find);",
                "",
                "    function button(name, text) {",
                "        var b = branch.createElement(name, 'button');",
                "        css.setClass(b, kr_btn);",
                "        b.type = 'button';",
                "        b.textContent = text;",
                "        bar.appendChild(b);",
                "        return b;",
                "    }",
                "",
                "    // Three states worth separating, and none of them is the default.",
                "    // Started-but-unfinished is where the work already begun is;",
                "    // untouched is where the next decision is; complete is the reward.",
                "    var started = button('started', 'Started');",
                "    var untouched = button('untouched', 'Untouched');",
                "    var done = button('done', 'Complete');",
                "    var show = button('show', 'Show characters');",
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
                "    var owner = Object.freeze({ toString: function () { return 'knownSounds'; } });",
                "    var grid = null;",
                "    var __syllables = null;   // null until the index lands",
                "    var __known = [];",
                "",
                "    // True once the device has answered and this pane has drawn what it",
                "    // holds. After that the set may keep arriving - other panes still",
                "    // claim readings - and it is recorded but not repainted, because",
                "    // repainting here means rebuilding 1,288 rows.",
                "    var __settled = false;",
                "",
                "    function settle() {",
                "        if (__settled) return;",
                "        __settled = true;",
                "        render();",
                "    }",
                "    var __find = '';",
                "    var __only = '';",
                "    var __byPath = {};",
                "    var __cursorPk = null;",
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
                "            // Read-only for the same reason Known is: a row here is a",
                "            // whole syllable, so an accidental edit would not cost one",
                "            // claim but every claim at that sound.",
                "            update: function () {},",
                "            deleteRows: function () {}",
                "        };",
                "    }",
                "",
                "    function freshCellsBranch() {",
                "        if (branch.getBranch('cells')) branch.dissolveBranch('cells');",
                "        var b = branch.createBranch('cells');",
                "        b.activate(owner);",
                "        return b;",
                "    }",
                "",
                "    function announce(pk, activated) {",
                "        var row = __byPath[pk];",
                "        if (!row || !__navParty || !__navActorId) return;",
                "        // The same message the Sounds tree sends when a syllable is",
                "        // opened. activated is what tells 'the cursor passed over this'",
                "        // from 'open it'.",
                "        __navParty.tellFrom(__navActorId, {",
                "            kind: 'NodeSelected',",
                "            node: {",
                "                kind: 'syllable',",
                "                namePath: row.path.split('.').join('/'),",
                "                label: row.label,",
                "                activated: !!activated",
                "            }",
                "        });",
                "    }",
                "",
                "    function render() {",
                "        if (grid) { grid.destroy(); grid = null; }",
                "        if (!__syllables) {",
                "            status.textContent = 'Loading the sounds index...';",
                "            return;",
                "        }",
                "",
                "        var sum = sounds.summarise(__syllables, __known);",
                "        tally.textContent = sum.started + ' of ' + sum.sounds",
                "            + ' sounds started, ' + sum.complete + ' complete, '",
                "            + sum.known + ' of ' + sum.characters + ' readings known';",
                "",
                "        var rows = sounds.rowsOf(__syllables, __known,",
                "                                 { find: __find, only: __only });",
                "        __byPath = {};",
                "        rows.forEach(function (r) { __byPath[r.pk] = r; });",
                "",
                "        if (rows.length === 0) {",
                "            status.textContent = 'No sound matches that.';",
                "            return;",
                "        }",
                "        status.textContent = 'Press Show characters to open the sound '",
                "            + 'under the cursor. ' + rows.length + ' of ' + sum.sounds",
                "            + ' shown.';",
                "",
                "        grid = new RelationGrid({",
                "            container: host,",
                "            branch: freshCellsBranch(),",
                "            adapter: relationOver(rows),",
                "            label: 'Sounds, by how much of each is left',",
                "            cellFactory: function () { return new TextCell(); },",
                "            onCursorMoved: function (pk) { __cursorPk = pk; announce(pk, false); }",
                "        });",
                "    }",
                "",
                "    // Exclusive, and clicking the lit one clears it. Overlapping filters",
                "    // would need a fourth control to say what 'both' meant.",
                "    function toggle(which) {",
                "        __only = (__only === which) ? '' : which;",
                "        var buttons = [[started, 'started'], [untouched, 'untouched'],",
                "                       [done, 'done']];",
                "        for (var i = 0; i < buttons.length; i++) {",
                "            if (__only === buttons[i][1]) {",
                "                css.setClass(buttons[i][0], kr_btn, kr_kn_on);",
                "            } else {",
                "                css.setClass(buttons[i][0], kr_btn);",
                "            }",
                "        }",
                "        render();",
                "    }",
                "",
                "    started.addEventListener('click', function () { toggle('started'); });",
                "    untouched.addEventListener('click', function () { toggle('untouched'); });",
                "    done.addEventListener('click', function () { toggle('done'); });",
                "    show.addEventListener('click', function () { announce(__cursorPk, true); });",
                "",
                "    find.addEventListener('input', function () {",
                "        __find = find.value ? find.value.trim() : '';",
                "        render();",
                "    });",
                "",
                "    // The corpus half. Profile-free and changing only when the corpus",
                "    // does, so the browser caches it by URL - the same bargain the census",
                "    // makes, and the reason this is one module and not 1,288 requests.",
                "    import('/syllable-index')",
                "        .then(function (mod) {",
                "            __syllables = mod.syllables || [];",
                "            render();",
                "        })",
                "        .catch(function () {",
                "            __syllables = [];",
                "            status.textContent = 'Could not load the sounds index.';",
                "        });",
                "",
                "    var __navActorId = null;",
                "    var __navParty = (workspaceCtx && workspaceCtx.navParty)",
                "                   ? workspaceCtx.navParty : null;",
                "    if (__navParty) {",
                "        __navActorId = 'known/sounds-' + Math.random().toString(36).slice(2, 8);",
                "        // Joined to speak, not to listen. Reacting to a sound as well",
                "        // would make selecting in the tree move the cursor here, which is",
                "        // a second cursor nobody asked for.",
                "        __navParty.joinActor({",
                "            id: __navActorId,",
                "            parentSecretary: 'navigation',",
                "            reactors: {}",
                "        });",
                "    }",
                "",
                "    var __knownParty = (workspaceCtx && workspaceCtx.knownParty)",
                "                     ? workspaceCtx.knownParty : null;",
                "    var __knownActorId = null;",
                "    if (__knownParty) {",
                "        __knownActorId = 'known/idx-' + Math.random().toString(36).slice(2, 8);",
                "        // Answered once, on opening, and not kept live.",
                "        //",
                "        // Every claim used to land here as a full rebuild: grid.destroy()",
                "        // and a fresh RelationGrid over 1,288 syllable rows to move one",
                "        // count. The arithmetic behind those rows measures 1.2ms; the",
                "        // pane cost about thirty times that, all of it DOM, to produce",
                "        // the same 1,288 rows in the same order.",
                "        //",
                "        // This is a pane somebody opens to decide what to work on next,",
                "        // and it is correct when they open it. Keeping it live again is a",
                "        // matter of updating the cells that moved rather than rebuilding",
                "        // the grid, and the grid already has the call for that - but that",
                "        // is a repair, and this is a removal.",
                "        __knownParty.joinActor({",
                "            id: __knownActorId,",
                "            parentSecretary: 'knownSet',",
                "            reactors: {",
                "                // Kept until the device has answered, then dropped.",
                "                //",
                "                // Not simply 'take the first one': the party replies to a",
                "                // new member immediately with whatever it holds, which at",
                "                // boot is an empty set while the disk is still being read.",
                "                // Stopping there would show an empty record on a device",
                "                // that has been read on for months.",
                "                KnownChanged: function (msg) {",
                "                    __known = (msg && msg.known) ? msg.known : [];",
                "                    if (!__settled) render();",
                "                }",
                "            }",
                "        });",
                "        __knownParty.tellFrom(__knownActorId, { kind: 'WhatIsKnown' });",
                "        // Seeded from the device, never written to it. Opened on its own",
                "        // this would otherwise show an empty record on a device that has",
                "        // been read on for months.",
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
                "        }).start().then(settle, settle);",
                "    }",
                "",
                "    render();",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) {},",
                "        partyDeregister: function () {",
                "            if (__navActorId && __navParty) {",
                "                try { __navParty.leave(__navActorId); } catch (e) {}",
                "            }",
                "            if (__knownActorId && __knownParty) {",
                "                try { __knownParty.leave(__knownActorId); } catch (e) {}",
                "            }",
                "        }",
                "    };"
        );
    }
}
