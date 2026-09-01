package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;
import kranji.reading.app.ui.TypefacePickerModule;

import java.util.List;

/**
 * The characters read as the selected syllable.
 *
 * <p>Data reaches this widget as <b>a module, not JSON</b>. On activation it
 * performs a dynamic {@code import()} of the syllable's data module, so what
 * arrives is already a structure — no fetch, no parse, no reconstructing typed
 * values from a display string. The module name is derived from the tree's
 * {@code namePath}, the chain of segments the renderer rebuilt while drawing
 * the row.</p>
 *
 * <h2>Loading is on activation, not selection</h2>
 *
 * <p>The tree renderer fires {@code onSelect} on every arrow-key move, so
 * loading on selection would fire an import for each row a reader passes
 * through on the way to the one they wanted. Selection therefore only offers;
 * <b>Enter or double-click</b> loads.</p>
 *
 * <p>The navigation secretary understands one message kind, so an activation
 * travels as a {@code NodeSelected} whose node carries an {@code activated}
 * marker — the flag rides on the node because the secretary rebroadcasts the
 * node rather than the message.</p>
 *
 * <p>Shows every character read that way, including those for which this is
 * <em>not</em> the default reading, marked accordingly.</p>
 *
 * <p>No CJK appears in this file. Characters arrive from the corpus.</p>
 */
public final class ZiCharactersWidget extends WorkspaceWidget<WorkspaceWidget._None, ZiCharactersWidget> {

    public static final ZiCharactersWidget INSTANCE = new ZiCharactersWidget();

    private ZiCharactersWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, ZiCharactersWidget> {}

    @Override protected _Construct<_None, ZiCharactersWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Characters"; }
    @Override public LifecycleHint lifecycleHint() { return LifecycleHint.MULTI; }

    @Override
    protected List<ModuleImports<? extends Importable>> bodyImports() {
        return List.of(
                new ModuleImports<>(List.of(
                        new ReadingCss.kr_widget_root(),
                        new ReadingCss.kr_status(),
                        new ReadingCss.kr_glyph_row(),
                        new ReadingCss.kr_zi_card(),
                        new ReadingCss.kr_zi_card_hover(),
                        new ReadingCss.kr_zi_glyph(),
                        new ReadingCss.kr_zi_readings(),
                        new ReadingCss.kr_zi_mark(),
                        new ReadingCss.kr_zi_mark_alt(),
                        new ReadingCss.kr_control(),
                        new ReadingCss.kr_select(),
                        new ReadingCss.kr_font_system(),
                        new ReadingCss.kr_font_hei(),
                        new ReadingCss.kr_font_song(),
                        new ReadingCss.kr_font_kai(),
                        new ReadingCss.kr_font_fangsong()),
                        ReadingCss.INSTANCE),
                new ModuleImports<>(
                        List.of(new TypefacePickerModule.createTypefacePicker()),
                        TypefacePickerModule.INSTANCE));
    }

    @Override
    protected List<String> constructBodyJs() {
        return List.of(
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, kr_widget_root);",
                "",
                "    var __glyphs = [];",
                "",
                "    // The character-selection bus, joined as a producer only: this pane",
                "    // announces a click and never listens for one.",
                "    var __ziParty = (workspaceCtx && workspaceCtx.ziParty)",
                "                  ? workspaceCtx.ziParty : null;",
                "    var __ziActorId = null;",
                "    if (__ziParty) {",
                "        __ziActorId = 'zi/chars-sel-' + Math.random().toString(36).slice(2, 8);",
                "        __ziParty.joinActor({",
                "            id: __ziActorId,",
                "            parentSecretary: 'ziSelection',",
                "            reactors: {}",
                "        });",
                "    }",
                "",
                "    var control = branch.createElement('control', 'div');",
                "    css.setClass(control, kr_control);",
                "    root.appendChild(control);",
                "",
                "    var fontLabel = branch.createElement('fontLabel', 'label');",
                "    fontLabel.textContent = 'Typeface';",
                "    control.appendChild(fontLabel);",
                "",
                "    // The same control the reader uses. The list, the labels and the",
                "    // remembered choice live in one place; only the class handles are",
                "    // local, because a widget's imports bind those into its own scope.",
                "    var typeface = createTypefacePicker({",
                "        branch: branch, css: css, prefix: 'chars', selectClass: kr_select,",
                "        classes: { system: kr_font_system, kai: kr_font_kai, hei: kr_font_hei,",
                "                   song: kr_font_song, fangsong: kr_font_fangsong },",
                "        onChange: function () { applyFont(); }",
                "    });",
                "    control.appendChild(typeface.element);",
                "",
                "    // Restyling in place rather than re-rendering: the cards do not change,",
                "    // only how their glyphs are drawn, and rebuilding would dissolve the",
                "    // branch and lose nothing but cost everything.",
                "    function applyFont() {",
                "        var cls = typeface.currentClass();",
                "        for (var i = 0; i < __glyphs.length; i++) {",
                "            css.setClass(__glyphs[i], kr_zi_glyph, cls);",
                "        }",
                "    }",
                "",
                "    var status = branch.createElement('status', 'div');",
                "    css.setClass(status, kr_status);",
                "    status.textContent = 'Select a syllable, then press Enter to load it.';",
                "    root.appendChild(status);",
                "",
                "    var grid = branch.createElement('grid', 'div');",
                "    css.setClass(grid, kr_glyph_row);",
                "    root.appendChild(grid);",
                "",
                "    var __seq = 0;",
                "    var __loaded = null;",
                "",
                "    // A branch must be activated by an owner before anything may be",
                "    // created in it - creating the branch only reserves the name.",
                "    var owner = Object.freeze({ toString: function () { return 'ziCharacters'; } });",
                "",
                "    // Cards are rebuilt on every load, so they live in a sub-branch: a",
                "    // branch owns its element NAMES, and detaching a node from the DOM",
                "    // does not release the name. Dissolving does both.",
                "    function clearGrid() {",
                "        __glyphs = [];",
                "        if (branch.getBranch('cards')) branch.dissolveBranch('cards');",
                "    }",
                "",
                "    function card(cards, idx, entry, syllable) {",
                "        var box = cards.createElement('c' + idx, 'div');",
                "        css.setClass(box, kr_zi_card, kr_zi_card_hover);",
                "",
                "        var g = cards.createElement('g' + idx, 'div');",
                "        css.setClass(g, kr_zi_glyph, typeface.currentClass());",
                "        g.textContent = entry.glyph;",
                "        __glyphs.push(g);",
                "        box.appendChild(g);",
                "",
                "        // The reading shown is the one being viewed, not the character's",
                "        // whole set - every card on the page carries the same syllable, so",
                "        // the row stays even and the page reads as one thing.",
                "        var r = cards.createElement('r' + idx, 'div');",
                "        css.setClass(r, kr_zi_readings);",
                "        r.textContent = syllable;",
                "        box.appendChild(r);",
                "",
                "        // Extra readings become a corner mark rather than another line:",
                "        // out of flow, so a polyphonic character cannot make its card",
                "        // taller than the ones beside it.",
                "        var others = (entry.readings || []).length - 1;",
                "        if (others > 0) {",
                "            var m = cards.createElement('m' + idx, 'div');",
                "            css.setClass(m, entry.readHereByDefault ? kr_zi_mark : kr_zi_mark_alt);",
                "            m.textContent = '+' + others;",
                "            m.title = entry.readHereByDefault",
                "                ? 'Also read ' + entry.readings.slice(1).join(', ')",
                "                : 'Usually read ' + entry.readings[0];",
                "            box.appendChild(m);",
                "        }",
                "        // A card is now a way in to the character itself. The pane does",
                "        // not name who listens - it says what happened and stops.",
                "        box.addEventListener('click', function () {",
                "            if (__ziParty && __ziActorId) {",
                "                __ziParty.tellFrom(__ziActorId, { kind: 'ZiSelected', zi: {",
                "                    codePoint: entry.codePoint, glyph: entry.glyph } });",
                "            }",
                "        });",
                "        return box;",
                "    }",
                "",
                "    function render(mod, seq) {",
                "        if (seq !== __seq) return;   // a later activation already won",
                "        clearGrid();",
                "        var chars = mod.characters || [];",
                "        if (chars.length === 0) {",
                "            status.textContent = mod.problem",
                "                ? 'Nothing here: ' + mod.problem",
                "                : 'No characters are read this way.';",
                "            return;",
                "        }",
                "        status.textContent = chars.length === 1",
                "            ? 'One character is read ' + mod.syllable + '.'",
                "            : chars.length + ' characters are read ' + mod.syllable + '.';",
                "        var cards = branch.createBranch('cards');",
                "        cards.activate(owner);",
                "        for (var i = 0; i < chars.length; i++) {",
                "            grid.appendChild(card(cards, i, chars[i], mod.syllable));",
                "        }",
                "    }",
                "",
                "    function load(namePath) {",
                "        var name = String(namePath || '').split('/').join('.');",
                "        if (!name) return;",
                "        var seq = ++__seq;",
                "        __loaded = name;",
                "        status.textContent = 'Loading ' + name + '\\u2026';",
                "        import('/zi-data?syllable=' + encodeURIComponent(name))",
                "            .then(function (mod) { render(mod, seq); })",
                "            .catch(function (err) {",
                "                if (seq !== __seq) return;",
                "                clearGrid();",
                "                status.textContent = 'Could not load ' + name + ': '",
                "                    + (err && err.message ? err.message : String(err));",
                "            });",
                "    }",
                "",
                "    function onNode(node) {",
                "        if (!node) return;",
                "        if (node.kind !== 'syllable') {",
                "            // A grouping was selected. Whatever is loaded stays put -",
                "            // clearing it on every arrow-key move would be noise.",
                "            return;",
                "        }",
                "        if (node.activated) { load(node.namePath); return; }",
                "        var name = String(node.namePath || '').split('/').join('.');",
                "        if (name && name !== __loaded) {",
                "            status.textContent = 'Press Enter to load '",
                "                + (node.label || name) + '.';",
                "        }",
                "    }",
                "",
                "    var __actorId  = null;",
                "    var __navParty = (workspaceCtx && workspaceCtx.navParty)",
                "                   ? workspaceCtx.navParty : null;",
                "    if (__navParty) {",
                "        __actorId = 'zi/chars-' + Math.random().toString(36).slice(2, 8);",
                "        __navParty.joinActor({",
                "            id: __actorId,",
                "            parentSecretary: 'navigation',",
                "            reactors: {",
                "                // The secretary rebroadcasts NodeSelected as NavigateTo;",
                "                // reacting to NodeSelected would never fire.",
                "                NavigateTo: function (msg) { onNode(msg.node); }",
                "            }",
                "        });",
                "    }",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) {},",
                "        partyDeregister: function () {",
                "            if (__actorId && __navParty) {",
                "                try { __navParty.leave(__actorId); } catch (e) {}",
                "            }",
                "            if (__ziActorId && __ziParty) {",
                "                try { __ziParty.leave(__ziActorId); } catch (e) {}",
                "            }",
                "        }",
                "    };"
        );
    }
}
