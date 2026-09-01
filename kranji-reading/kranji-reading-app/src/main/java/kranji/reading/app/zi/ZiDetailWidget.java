package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;

import java.util.List;

/**
 * One character, in full.
 *
 * <p>Joins the character-selection Party rather than the navigator: it reacts
 * to {@code ShowZi}, which carries a codepoint, and never to {@code NavigateTo},
 * which carries a tree path. Clicking a card in the Characters pane is what
 * produces the selection; this pane never names its producer.</p>
 *
 * <p>Shows every reading the character has, each decomposed into the five
 * parts of a syllable, with the corpus evidence beside it. The point of the
 * pane is that a polyphonic character stops being a footnote on a card and
 * becomes the subject.</p>
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
                        new ReadingCss.kr_badge(),
                        new ReadingCss.kr_zi_hero(),
                        new ReadingCss.kr_zi_hero_glyph(),
                        new ReadingCss.kr_zi_hero_meta(),
                        new ReadingCss.kr_reading_row(),
                        new ReadingCss.kr_reading_name(),
                        new ReadingCss.kr_reading_parts(),
                        new ReadingCss.kr_part(),
                        new ReadingCss.kr_font_kai()),
                        ReadingCss.INSTANCE));
    }

    @Override
    protected List<String> constructBodyJs() {
        return List.of(
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, kr_widget_root);",
                "",
                "    var status = branch.createElement('status', 'div');",
                "    css.setClass(status, kr_status);",
                "    status.textContent = 'Click a character to see it here.';",
                "    root.appendChild(status);",
                "",
                "    var body = branch.createElement('body', 'div');",
                "    root.appendChild(body);",
                "",
                "    var owner = Object.freeze({ toString: function () { return 'ziDetail'; } });",
                "    var __seq = 0;",
                "",
                "    function clearBody() {",
                "        if (branch.getBranch('detail')) branch.dissolveBranch('detail');",
                "    }",
                "",
                "    function readingRow(db, idx, r) {",
                "        var row = db.createElement('r' + idx, 'div');",
                "        css.setClass(row, kr_reading_row);",
                "",
                "        var name = db.createElement('n' + idx, 'div');",
                "        css.setClass(name, kr_reading_name);",
                "        name.textContent = r.reading + (r.principal ? '' : '  (also)');",
                "        row.appendChild(name);",
                "",
                "        var parts = db.createElement('p' + idx, 'div');",
                "        css.setClass(parts, kr_reading_parts);",
                "        row.appendChild(parts);",
                "",
                "        // The five parts a syllable is made of, each as its own chip.",
                "        var spec = [['initial', r.initial], ['medial', r.medial],",
                "                    ['nucleus', r.nucleus], ['coda', r.coda],",
                "                    ['tone', String(r.tone)]];",
                "        for (var i = 0; i < spec.length; i++) {",
                "            var chip = db.createElement('c' + idx + '_' + i, 'span');",
                "            css.setClass(chip, kr_part);",
                "            chip.textContent = spec[i][0] + ' ' + spec[i][1];",
                "            parts.appendChild(chip);",
                "        }",
                "",
                "        var note = db.createElement('t' + idx, 'div');",
                "        css.setClass(note, kr_status);",
                "        var bits = [];",
                "        if (r.homophones > 0) bits.push(r.homophones + ' other characters read this way');",
                "        if (r.observed > 0) bits.push('observed ' + r.observed + ' times');",
                "        note.textContent = bits.join(' \\u00b7 ');",
                "        row.appendChild(note);",
                "        return row;",
                "    }",
                "",
                "    function render(mod, seq) {",
                "        if (seq !== __seq) return;",
                "        clearBody();",
                "        if (!mod.glyph) {",
                "            status.textContent = mod.problem ? 'Nothing here: ' + mod.problem",
                "                                            : 'No such character.';",
                "            return;",
                "        }",
                "        var db = branch.createBranch('detail');",
                "        db.activate(owner);",
                "",
                "        var hero = db.createElement('hero', 'div');",
                "        css.setClass(hero, kr_zi_hero);",
                "",
                "        var g = db.createElement('glyph', 'div');",
                "        css.setClass(g, kr_zi_hero_glyph, kr_font_kai);",
                "        g.textContent = mod.glyph;",
                "        hero.appendChild(g);",
                "",
                "        var meta = db.createElement('meta', 'div');",
                "        css.setClass(meta, kr_zi_hero_meta);",
                "        meta.textContent = mod.codePoint",
                "            + (mod.supplementary ? '  (outside the BMP)' : '')",
                "            + '  \\u00b7  principally ' + mod.principal;",
                "        hero.appendChild(meta);",
                "        body.appendChild(hero);",
                "",
                "        status.textContent = mod.polyphonic",
                "            ? 'Read ' + mod.readings.length + ' ways.'",
                "            : 'One reading.';",
                "",
                "        for (var i = 0; i < mod.readings.length; i++) {",
                "            body.appendChild(readingRow(db, i, mod.readings[i]));",
                "        }",
                "",
                "        if (mod.unmodelled) {",
                "            var u = db.createElement('unmodelled', 'div');",
                "            css.setClass(u, kr_status);",
                "            u.textContent = 'Not modelled here: ' + mod.unmodelled;",
                "            body.appendChild(u);",
                "        }",
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
                "                clearBody();",
                "                status.textContent = 'Could not load ' + codePoint + ': '",
                "                    + (err && err.message ? err.message : String(err));",
                "            });",
                "    }",
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
                "    return {",
                "        root: root,",
                "        setActive: function (active) {},",
                "        partyDeregister: function () {",
                "            if (__actorId && __ziParty) {",
                "                try { __ziParty.leave(__actorId); } catch (e) {}",
                "            }",
                "        }",
                "    };");
    }
}
