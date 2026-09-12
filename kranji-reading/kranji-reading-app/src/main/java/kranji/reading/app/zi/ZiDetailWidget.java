package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;
import kranji.reading.app.ui.PinyinSwfModule;

import java.util.List;

/**
 * One character, in full.
 *
 * <h2>Two panes that were the same pane</h2>
 *
 * <p>This absorbed Mark Known, which had grown into a near-copy of it: the same
 * hero, the same list of readings, the same meaning under each. The two
 * differed by one control. Keeping them apart meant a child looked at a
 * character in one pane and claimed it in another, and every field shown in
 * both had to be kept in step by hand.</p>
 *
 * <p>The merge is not just a paste. The reading list became
 * {@link ZiReadingCardsModule} cards, one per sense of one reading.</p>
 *
 * <h2>Everything about a reading is on its card</h2>
 *
 * <p>The sound, the meaning, its examples, the five parts of the syllable, and
 * how many other characters share it. The parts sit along the bottom, still
 * side by side, so two readings of one character differing only in the tone say
 * so at a glance — which was the one thing the columns were genuinely good
 * for.</p>
 *
 * <p>There is no count of how often the corpus uses a reading. It decided
 * nothing a reader does, and it cost the meaning the width it needed.</p>
 *
 * <h2>It does not claim any more</h2>
 *
 * <p>There was a claim column here - a picker per row, saying whether the
 * reading was known and changing it. It went when marking settled in one place.
 * A reader meets a character in a sentence and says so there; a second control
 * in a reference pane was a second way to do the same thing, kept in step over
 * a bus, for the sake of being able to do it while looking something up.</p>
 *
 * <h2>It reacts to its own width</h2>
 *
 * <p>Wide, the character sits beside its readings; narrow, above them. A glyph
 * is square and a reading is a line of prose, so one arrangement always wastes
 * a dimension. Measured rather than a media query: a widget is a pane inside a
 * workspace and can be any width at any window size.</p>
 *
 * <p>One bus, and one only: character selection, which says what to show. It
 * kept a known-set membership for a while after the claim column went, purely
 * so that a disk got written - it was one of two panes anywhere that saved.
 * That was the fault rather than the fix, and it went when the reader took over
 * keeping what the reader claims.</p>
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
                new ModuleImports<>(List.of(
                        new ReadingCss.kr_widget_root(),
                        new ReadingCss.kr_status(),
                        new ReadingCss.kr_zi_hero(),
                        new ReadingCss.kr_zi_hero_glyph(),
                        new ReadingCss.kr_zi_hero_meta(),
                        new ReadingCss.kr_zd_split(),
                        new ReadingCss.kr_zd_split_narrow(),
                        new ReadingCss.kr_zd_aside(),
                        new ReadingCss.kr_zd_main(),
                        new ReadingCss.kr_zd_card(),
                        new ReadingCss.kr_zd_card_head(),
                        new ReadingCss.kr_zd_card_reading(),
                        new ReadingCss.kr_zd_card_meaning(),
                        new ReadingCss.kr_zd_card_examples(),
                        new ReadingCss.kr_zd_card_missing(),
                        new ReadingCss.kr_zd_card_parts(),
                        new ReadingCss.kr_zd_card_part(),
                        new ReadingCss.kr_zd_card_part_key(),
                        new ReadingCss.kr_seg(),
                        new ReadingCss.kr_seg_opt(),
                        new ReadingCss.kr_seg_on(),
                        new ReadingCss.kr_kn_hidden(),
                        new ReadingCss.kr_font_kai()),
                        ReadingCss.INSTANCE),
                new ModuleImports<>(
                        List.of(new ZiReadingCardsModule.createZiReadingCards()),
                        ZiReadingCardsModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new PinyinSwfModule.createPinyinSwf()),
                        PinyinSwfModule.INSTANCE));
    }

    @Override
    protected List<String> constructBodyJs() {
        return List.of(
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
                "    // The character beside its readings, or above them.",
                "    //",
                "    // A glyph is square and a reading is a line of prose, so stacking",
                "    // them always wastes one dimension: at any usable width the hero",
                "    // left a band of empty pane either side of it and the readings got",
                "    // what was left of the height.",
                "    var split = branch.createElement('split', 'div');",
                "    css.setClass(split, kr_zd_split);",
                "    root.appendChild(split);",
                "",
                "    var aside = branch.createElement('aside', 'div');",
                "    css.setClass(aside, kr_zd_aside);",
                "    split.appendChild(aside);",
                "",
                "    var main = branch.createElement('main', 'div');",
                "    css.setClass(main, kr_zd_main);",
                "    split.appendChild(main);",
                "",
                "    // The hero is rebuilt per character rather than cleared. Emptying an",
                "    // element is a wholesale wipe; dissolving the branch that made it is",
                "    // how this codebase takes DOM away.",
                "    var heroHost = branch.createElement('heroHost', 'div');",
                "    aside.appendChild(heroHost);",
                "",
                "    // Under the character, not over the cards. It is a fixed control of",
                "    // two words; as a flex child of the readings column it was stretched",
                "    // the full width of the pane. It also reads better where it belongs:",
                "    // it does not change the character, it changes how the character is",
                "    // read, so it sits with the character rather than with the cards.",
                "    //",
                "    // Both states are named and the one that is the case is lit. A single",
                "    // button whose label said what pressing it would do made a reader work",
                "    // out where they were from the name of where they were not.",
                "    var seg = branch.createElement('seg', 'div');",
                "    css.setClass(seg, kr_seg, kr_kn_hidden);",
                "    aside.appendChild(seg);",
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
                "    // Which way round is decided by measuring, not by a media query. A",
                "    // widget is a pane inside a workspace and can be any width at any",
                "    // window size, so the viewport does not know.",
                "    //",
                "    // One class swap, not two builds. The parts that would have differed",
                "    // are a flex direction and a gap; nothing is transposed and nothing",
                "    // is rebuilt, so there is no shape here for a stale class to hide in.",
                "    var NARROW = 520;",
                "    var __narrow = null;",
                "",
                "    function fit() {",
                "        var narrow = root.clientWidth > 0 && root.clientWidth < NARROW;",
                "        if (narrow === __narrow) return;",
                "        __narrow = narrow;",
                "        css.setClass(split, narrow ? kr_zd_split_narrow : kr_zd_split);",
                "    }",
                "",
                "    // Measured at three moments, not one. A ResizeObserver is the",
                "    // right instrument for a pane being dragged, and it is not always",
                "    // the one that fires first - a widget can be mounted at its final",
                "    // size, in which case there is no resize to observe. So the two",
                "    // points where the pane knows it is visible and sized ask as well.",
                "    if (typeof ResizeObserver !== 'undefined') {",
                "        new ResizeObserver(function () { fit(); }).observe(root);",
                "    }",
                "",
                "    var owner = Object.freeze({ toString: function () { return 'ziDetail'; } });",
                "    var __seq = 0;",
                "    var __asked = '';       // the reading the selection carried",
                "    var __showAll = false;   // has somebody asked for the rest?",
                "    var __last = null;       // the loaded module, for a re-render",
                "",
                "    // ── The record ────────────────────────────────────────────",
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
                "    // The save warning used to be appended here, because this pane was",
                "    // one of the two that wrote. It belongs with whoever writes, and that",
                "    // is the reader now.",
                "    function say(about) {",
                "        if (about != null) __saying = about;",
                "        status.textContent = __saying;",
                "    }",
                "",
                "    // ── The grid ──────────────────────────────────────────────",
                "",
                "    // One grid, showing the sound that was actually selected. The rest",
                "    // of the character's readings are true and, at the moment somebody",
                "    // tapped one square in one sentence, they are noise - so the toggle",
                "    // swaps the rows rather than stacking a second grid underneath.",
                "    var readings = createZiReadingCards({",
                "        branch: branch, css: css, host: main, owner: owner,",
                "        swf: swf,",
                "        cardClass: kr_zd_card, headClass: kr_zd_card_head,",
                "        readingClass: kr_zd_card_reading, meaningClass: kr_zd_card_meaning,",
                "        missingClass: kr_zd_card_missing,",
                "        examplesClass: kr_zd_card_examples, partsClass: kr_zd_card_parts,",
                "        partClass: kr_zd_card_part, partKeyClass: kr_zd_card_part_key",
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
                "        // Said on the character's own line as well as on each card,",
                "        // because this is the one place a reader looks at the character",
                "        // rather than at a reading of it - and 'not in the library' is",
                "        // a fact about the character. A build with no library at all",
                "        // says so once, here, rather than once per card.",
                "        if (mod.glossary === false) {",
                "            meta.textContent += '  \\u00b7  no meanings library in this build';",
                "        } else if (mod.glossed === false) {",
                "            meta.textContent += '  \\u00b7  not in the meanings library yet';",
                "        }",
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
                "        readings.show(cp, (narrowed && !__showAll) ? focused : all,",
                "                      { library: mod.glossary !== false, character: mod.glossed !== false });",
                "        fit();",
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
                "    // This pane no longer touches the known set at all.",
                "    //",
                "    // It kept a subscription after the claim column went, purely so that",
                "    // a disk got written - it was one of only two panes anywhere that",
                "    // saved. That was the fault rather than the fix, and it is gone now",
                "    // that the reader keeps what the reader claims. A pane that shows a",
                "    // character has no business holding somebody's record.",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) { if (active) fit(); },",
                "        partyDeregister: function () {",
                "            if (__actorId && __ziParty) {",
                "                try { __ziParty.leave(__actorId); } catch (e) {}",
                "            }",
                "        },",
                "        dispose: function () { __seq++; readings.destroy(); }",
                "    };");
    }
}
