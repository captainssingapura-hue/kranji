package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.grid.RelationGridModule;
import hue.captains.singapura.js.homing.grid.StockCellsModule;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;
import kranji.reading.app.known.KnownPersistenceModule;
import kranji.reading.app.known.KnownSetModule;
import kranji.reading.app.known.KnownStoreModule;
import kranji.reading.app.ui.PinyinSwfModule;

import java.util.List;

/**
 * One character, in full — and where its readings are claimed.
 *
 * <h2>Two panes that were the same pane</h2>
 *
 * <p>This absorbed Mark Known, which had grown into a near-copy of it: the same
 * hero, the same list of readings, the same meaning under each. The two
 * differed by one control. Keeping them apart meant a child looked at a
 * character in one pane and claimed it in another, and every field shown in
 * both had to be kept in step by hand.</p>
 *
 * <p>The merge is not just a paste. The reading list became a
 * {@link ZiReadingsGridModule} grid whose rows are (character, reading) pairs —
 * the key the whole system is grained on — with the claim as a cell. So the row
 * a person reads and the row they act on are the same row, which is what the
 * two panes could never quite manage between them.</p>
 *
 * <h2>Everything about a reading is in its row</h2>
 *
 * <p>The claim, the sound, the meaning, the five parts of the syllable, and how
 * many other characters share it. The parts were chips under the grid that
 * followed the cursor; as columns they can be compared, and two readings of one
 * character that differ only in the tone now say so at a glance — which is the
 * thing a strip showing one at a time could never do.</p>
 *
 * <p>There is no count of how often the corpus uses a reading. It decided
 * nothing a reader does, and it cost the meaning the width it needed.</p>
 *
 * <h2>The claim is a picker, not a button</h2>
 *
 * <p>Two values in one cell, so the same control both claims a reading and
 * gives it back. A button could only have gone one way: in a grid the cursor
 * lands on rows by arrow key and by stray click, and a press that destroyed a
 * deliberate claim would be one keystroke from wherever it happened to be.
 * Choosing from a picker costs two acts and only the second fires, which is
 * what lets the destructive direction live in a cell at all.</p>
 *
 * <p>Every column except the meaning is fixed width. Their content is bounded
 * and the meaning's is not, so the meaning takes what is left and the control a
 * person aims at stops moving between characters.</p>
 *
 * <p>It joins two buses. Character selection says what to show; the known set
 * says what is already claimed and receives the claims made here.</p>
 *
 * <p>No CJK appears in this file. Characters arrive from the corpus.</p>
 */
public final class ZiDetailWidget extends WorkspaceWidget<WorkspaceWidget._None, ZiDetailWidget> {

    public static final ZiDetailWidget INSTANCE = new ZiDetailWidget();

    private ZiDetailWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, ZiDetailWidget> {}

    @Override protected _Construct<_None, ZiDetailWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Character"; }
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
                        new ReadingCss.kr_zi_hero(),
                        new ReadingCss.kr_zi_hero_glyph(),
                        new ReadingCss.kr_zi_hero_meta(),
                        new ReadingCss.kr_kn_pick(),
                        new ReadingCss.kr_kn_tally(),
                        new ReadingCss.kr_grid_host(),
                        new ReadingCss.kr_font_kai()),
                        ReadingCss.INSTANCE),
                new ModuleImports<>(
                        List.of(new ZiReadingsGridModule.createZiReadingsGrid()),
                        ZiReadingsGridModule.INSTANCE),
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
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, kr_widget_root);",
                "",
                "    var status = branch.createElement('status', 'div');",
                "    css.setClass(status, kr_status);",
                "    status.textContent = 'Click a character to see it here.';",
                "    root.appendChild(status);",
                "",
                "    // The hero is rebuilt per character rather than cleared. Emptying an",
                "    // element is a wholesale wipe; dissolving the branch that made it is",
                "    // how this codebase takes DOM away.",
                "    var heroHost = branch.createElement('heroHost', 'div');",
                "    root.appendChild(heroHost);",
                "",
                "    var host = branch.createElement('host', 'div');",
                "    css.setClass(host, kr_grid_host);",
                "    root.appendChild(host);",
                "",
                "    var tally = branch.createElement('tally', 'div');",
                "    css.setClass(tally, kr_kn_tally);",
                "    root.appendChild(tally);",
                "",
                "    var owner = Object.freeze({ toString: function () { return 'ziDetail'; } });",
                "    var __seq = 0;",
                "    var __known = [];",
                "    var __lastImport = [];",
                "    var __storeProblem = false;",
                "",
                "    // ── The record ────────────────────────────────────────────",
                "",
                "    var __knownParty = (workspaceCtx && workspaceCtx.knownParty)",
                "                     ? workspaceCtx.knownParty : null;",
                "    var __knownActorId = null;",
                "",
                "    function tellKnown(msg) {",
                "        if (__knownParty && __knownActorId) {",
                "            __knownParty.tellFrom(__knownActorId, msg);",
                "        }",
                "    }",
                "",
                "    function paintTally() {",
                "        var readings = __known.length;",
                "        var chars = knownSet.characters(__known).length;",
                "        var count = readings === 0 ? 'Nothing marked yet'",
                "            : chars + (chars === 1 ? ' character' : ' characters')",
                "              + ', ' + readings + (readings === 1 ? ' reading' : ' readings');",
                "        // A failure to save is the one thing a person must be told about",
                "        // unprompted: marking looks identical either way, and what is",
                "        // lost is only discovered on the next visit.",
                "        tally.textContent = __storeProblem",
                "            ? count + ' \\u00b7 NOT SAVED on this device' : count;",
                "    }",
                "",
                "    // ── The grid ──────────────────────────────────────────────",
                "",
                "    var readings = createZiReadingsGrid({",
                "        branch: branch, css: css, host: host, owner: owner,",
                "        RelationGrid: RelationGrid, TextCell: TextCell,",
                "        pickClass: kr_kn_pick,",
                "        swf: swf,",
                "        isKnown: function (key) { return __known.indexOf(key) >= 0; },",
                "        onMark:   function (key) { tellKnown({ kind: 'MarkKnown',   key: key }); },",
                "        onUnmark: function (key) { tellKnown({ kind: 'UnmarkKnown', key: key }); },",
                "    });",
                "",
                "    // ── The character ─────────────────────────────────────────",
                "",
                "    function render(mod, seq) {",
                "        if (seq !== __seq) return;",
                "        if (branch.getBranch('hero')) branch.dissolveBranch('hero');",
                "        if (!mod.glyph) {",
                "            readings.show(0, []);",
                "            status.textContent = mod.problem ? 'Nothing here: ' + mod.problem",
                "                                            : 'No such character.';",
                "            return;",
                "        }",
                "",
                "        var hb = branch.createBranch('hero');",
                "        hb.activate(owner);",
                "        var hero = hb.createElement('hero', 'div');",
                "        css.setClass(hero, kr_zi_hero);",
                "        heroHost.appendChild(hero);",
                "",
                "        var glyph = hb.createElement('glyph', 'div');",
                "        css.setClass(glyph, kr_zi_hero_glyph, kr_font_kai);",
                "        glyph.textContent = mod.glyph;",
                "        hero.appendChild(glyph);",
                "",
                "        var meta = hb.createElement('meta', 'div');",
                "        css.setClass(meta, kr_zi_hero_meta);",
                "        meta.textContent = mod.codePoint",
                "            + (mod.supplementary ? '  (outside the BMP)' : '')",
                "            + '  \\u00b7  principally ' + mod.principal",
                "            + (mod.unmodelled ? '  \\u00b7  not modelled: ' + mod.unmodelled : '');",
                "        hero.appendChild(meta);",
                "",
                "        status.textContent = mod.polyphonic",
                "            ? 'Read ' + mod.readings.length + ' ways \\u2014 mark each on its own.'",
                "            : 'One reading.';",
                "        readings.show(mod.glyph.codePointAt(0), mod.readings);",
                "    }",
                "",
                "    function load(codePoint) {",
                "        if (!codePoint) return;",
                "        var seq = ++__seq;",
                "        status.textContent = 'Loading ' + codePoint + '\\u2026';",
                "        import('/zi-detail?codepoint=' + encodeURIComponent(codePoint))",
                "            .then(function (mod) { render(mod, seq); })",
                "            .catch(function (err) {",
                "                if (seq !== __seq) return;",
                "                status.textContent = 'Could not load ' + codePoint + ': '",
                "                    + (err && err.message ? err.message : String(err));",
                "            });",
                "    }",
                "",
                "    // ── Joining ───────────────────────────────────────────────",
                "",
                "    var __actorId = null;",
                "    var __ziParty = (workspaceCtx && workspaceCtx.ziParty)",
                "                  ? workspaceCtx.ziParty : null;",
                "    if (__ziParty) {",
                "        __actorId = 'zi/detail-' + Math.random().toString(36).slice(2, 8);",
                "        __ziParty.joinActor({",
                "            id: __actorId,",
                "            parentSecretary: 'ziSelection',",
                "            reactors: {",
                "                // The secretary rebroadcasts ZiSelected as ShowZi; reacting",
                "                // to ZiSelected would never fire.",
                "                ShowZi: function (msg) { load(msg.zi && msg.zi.codePoint); }",
                "            }",
                "        });",
                "    }",
                "",
                "    if (__knownParty) {",
                "        __knownActorId = 'known/zi-' + Math.random().toString(36).slice(2, 8);",
                "        __knownParty.joinActor({",
                "            id: __knownActorId,",
                "            parentSecretary: 'knownSet',",
                "            reactors: {",
                "                KnownChanged: function (msg) {",
                "                    __known = (msg && msg.known) ? msg.known : [];",
                "                    __lastImport = (msg && msg.lastImport) ? msg.lastImport : [];",
                "                    store.changed(__known, __lastImport);",
                "                    // The cells read isKnown() as they paint, so the grid is",
                "                    // redrawn rather than told which row moved. Four rows at",
                "                    // most - the bookkeeping would cost more than the redraw.",
                "                    readings.refresh();",
                "                    paintTally();",
                "                }",
                "            }",
                "        });",
                "    }",
                "",
                "    // This pane writes, so it persists. The order rule - never write",
                "    // before the device has answered - lives in the persistence module.",
                "    var store = createKnownPersistence({",
                "        store: createKnownStore(),",
                "        tell: function (msg) { tellKnown(msg); },",
                "        onProblem: function (broken) { __storeProblem = broken; paintTally(); }",
                "    });",
                "    if (__knownParty) {",
                "        tellKnown({ kind: 'WhatIsKnown' });",
                "        store.start();",
                "    }",
                "",
                "    paintTally();",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) {},",
                "        partyDeregister: function () {",
                "            if (__actorId && __ziParty) {",
                "                try { __ziParty.leave(__actorId); } catch (e) {}",
                "            }",
                "            if (__knownActorId && __knownParty) {",
                "                try { __knownParty.leave(__knownActorId); } catch (e) {}",
                "            }",
                "        },",
                "        dispose: function () { __seq++; readings.destroy(); }",
                "    };");
    }
}
