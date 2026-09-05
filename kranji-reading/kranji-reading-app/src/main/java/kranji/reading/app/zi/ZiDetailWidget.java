package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.grid.RelationGridModule;
import hue.captains.singapura.js.homing.grid.StockCellsModule;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;
import kranji.reading.app.known.KnownSetModule;
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
                        new ReadingCss.kr_grid_host(),
                        new ReadingCss.kr_seg(),
                        new ReadingCss.kr_seg_opt(),
                        new ReadingCss.kr_seg_on(),
                        new ReadingCss.kr_kn_hidden(),
                        new ReadingCss.kr_font_kai()),
                        ReadingCss.INSTANCE),
                new ModuleImports<>(
                        List.of(new ZiReadingsGridModule.createZiReadingsGrid()),
                        ZiReadingsGridModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new KnownSetModule.createKnownSet()),
                        KnownSetModule.INSTANCE),
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
                "    // Between the character and its rows, because that is what it",
                "    // divides: it does not change the character above it and it changes",
                "    // every row below it.",
                "    //",
                "    // Both states are named and the one that is the case is lit. A single",
                "    // button whose label said what pressing it would do made a reader work",
                "    // out where they were from the name of where they were not.",
                "    var seg = branch.createElement('seg', 'div');",
                "    css.setClass(seg, kr_seg, kr_kn_hidden);",
                "    root.appendChild(seg);",
                "",
                "    var segOne = branch.createElement('segOne', 'button');",
                "    segOne.type = 'button';",
                "    segOne.textContent = 'This sound';",
                "    seg.appendChild(segOne);",
                "",
                "    var segAll = branch.createElement('segAll', 'button');",
                "    segAll.type = 'button';",
                "    segAll.textContent = 'All readings';",
                "    seg.appendChild(segAll);",
                "",
                "    var host = branch.createElement('host', 'div');",
                "    css.setClass(host, kr_grid_host);",
                "    root.appendChild(host);",
                "",
                "",
                "    var owner = Object.freeze({ toString: function () { return 'ziDetail'; } });",
                "    var __seq = 0;",
                "    var __asked = '';       // the reading the selection carried",
                "    var __showAll = false;   // has somebody asked for the rest?",
                "    var __last = null;       // the loaded module, for a re-render",
                "    var __known = [];",
                "    var __lastImport = [];",
                "    var __storeProblem = false;",
                "",
                "    // ── The record ────────────────────────────────────────────",
                "",
                "    var __knownParty = (workspaceCtx && workspaceCtx.knownEventParty)",
                "                     ? workspaceCtx.knownEventParty : null;",
                "    var __knownActorId = null;",
                "",
                "    function tellKnown(msg) {",
                "        if (__knownParty && __knownActorId) {",
                "            __knownParty.tellFrom(__knownActorId, msg);",
                "        }",
                "    }",
                "",
                "    // The size of the whole record used to sit here. It is a fact about",
                "    // the reader, not about this character, and the Progress pane is",
                "    // where a reader goes for it - so a pane about one character stopped",
                "    // carrying it.",
                "    //",
                "    // A failure to save did NOT go with it. It is the one thing somebody",
                "    // must be told unprompted: marking looks identical either way, and",
                "    // what is lost is only discovered on the next visit. So it rides on",
                "    // the status line, which is the pane's one place for saying things.",
                "    var __saying = 'Click a character to see it here.';",
                "",
                "    function say(about) {",
                "        // Held rather than read back off the element: re-reading would",
                "        // append the warning to a line that already carries it, and it",
                "        // would grow a tail every time the store failed again.",
                "        if (about != null) __saying = about;",
                "        status.textContent = __storeProblem",
                "            ? __saying + '  \\u00b7  NOT SAVED on this device' : __saying;",
                "    }",
                "",
                "    // ── The grid ──────────────────────────────────────────────",
                "",
                "    // One grid, showing the sound that was actually selected. The rest",
                "    // of the character's readings are true and, at the moment somebody",
                "    // tapped one square in one sentence, they are noise - so the toggle",
                "    // swaps the rows rather than stacking a second grid underneath.",
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
                "            // The one place it does go away: there is no character, so",
                "            // there are no readings for either half to name. Keeping it",
                "            // would be a control over nothing.",
                "            css.setClass(seg, kr_seg, kr_kn_hidden);",
                "            say(mod.problem ? 'Nothing here: ' + mod.problem",
                "                            : 'No such character.');",
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
                "        var cp = mod.glyph.codePointAt(0);",
                "        var all = mod.readings || [];",
                "        // The selection's reading if the character actually has it. A",
                "        // stale one - the corpus corrected since a set was exported -",
                "        // falls back to showing everything rather than to showing nothing.",
                "        var focused = [];",
                "        for (var i = 0; i < all.length; i++) {",
                "            if (all[i].reading === __asked) focused.push(all[i]);",
                "        }",
                "        var narrowed = focused.length > 0 && focused.length < all.length;",
                "",
                "        readings.show(cp, (narrowed && !__showAll) ? focused : all);",
                "",
                "        // The control only exists when it would change something. A button",
                "        // that swaps a list for the same list is a button that teaches",
                "        // nobody to press it again.",
                "        // Always there once a character is. It used to appear only when",
                "        // it had something to do, which meant it jumped in and out as a",
                "        // reader moved between characters and took the rows with it - the",
                "        // same reason every column but the meaning has a fixed width.",
                "        //",
                "        // Lit by what the grid actually holds rather than by what was",
                "        // asked for. A character read one way is one row, and that row is",
                "        // this sound and all readings at once, so the left half lights and",
                "        // pressing the right one truthfully changes nothing.",
                "        var showingAll = !(narrowed && !__showAll);",
                "        var allLit = showingAll && all.length > 1;",
                "        css.setClass(seg, kr_seg);",
                "        css.setClass(segOne, allLit ? kr_seg_opt : kr_seg_on);",
                "        css.setClass(segAll, allLit ? kr_seg_on : kr_seg_opt);",
                "",
                "        say(!mod.polyphonic ? 'One reading.'",
                "            : (narrowed && !__showAll)",
                "                ? 'Read ' + all.length + ' ways \\u2014 showing the one selected.'",
                "                : 'Read ' + all.length + ' ways \\u2014 mark each on its own.');",
                "    }",
                "",
                "    function choose(all) {",
                "        if (all === __showAll) return;",
                "        __showAll = all;",
                "        if (__last) render(__last, __seq);",
                "    }",
                "",
                "    segOne.addEventListener('click', function () { choose(false); });",
                "    segAll.addEventListener('click', function () { choose(true); });",
                "",
                "    function load(codePoint, reading) {",
                "        if (!codePoint) return;",
                "        // A new character is a new question, so the extra readings fold",
                "        // away again rather than staying open from the last one.",
                "        __asked = reading || '';",
                "        __showAll = false;",
                "        var seq = ++__seq;",
                "        say('Loading ' + codePoint + '\\u2026');",
                "        import('/zi-detail?codepoint=' + encodeURIComponent(codePoint))",
                "            .then(function (mod) { __last = mod; render(mod, seq); })",
                "            .catch(function (err) {",
                "                if (seq !== __seq) return;",
                "                say('Could not load ' + codePoint + ': '",
                "                    + (err && err.message ? err.message : String(err)));",
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
                "                ShowZi: function (msg) {",
                "                    load(msg.zi && msg.zi.codePoint,",
                "                         msg.zi && msg.zi.reading);",
                "                }",
                "            }",
                "        });",
                "    }",
                "",
                "    if (__knownParty) {",
                "        __knownActorId = 'known/zi-' + Math.random().toString(36).slice(2, 8);",
                "        __knownParty.joinActor({",
                "            id: __knownActorId,",
                "            parentSecretary: 'knownEvents',",
                "            reactors: {",
                "                KnownChanged: function (msg) {",
                "                    __known = (msg && msg.known) ? msg.known : [];",
                "                    __lastImport = (msg && msg.lastImport) ? msg.lastImport : [];",
                "                    // The cells read isKnown() as they paint, so the grid is",
                "                    // redrawn rather than told which row moved. Four rows at",
                "                    // most - the bookkeeping would cost more than the redraw.",
                "                    readings.refresh();",
                "                }",
                "            }",
                "        });",
                "    }",
                "",
                "    if (__knownParty) {",
                "        tellKnown({ kind: 'WhatIsKnown' });",
                "    }",
                "",                "",
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
