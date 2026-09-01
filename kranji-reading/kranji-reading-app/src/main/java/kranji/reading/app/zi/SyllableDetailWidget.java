package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;

import java.util.List;

/**
 * Explains the selected <em>sound</em> — not its characters.
 *
 * <p>The division of labour matters: this pane takes the syllable apart into
 * initial, final and tone, while {@link ZiCharactersWidget} shows the
 * characters read that way. Two panes rendering the same characters
 * differently is the kind of duplication that drifts.</p>
 *
 * <p>Reads only fields the tree renderer actually supplies —
 * {@code path, namePath, level, kind, label, summary, hasChildren}. An earlier
 * version read {@code badge} and {@code note}, which the renderer does not
 * send, so it silently displayed nothing.</p>
 */
public final class SyllableDetailWidget extends WorkspaceWidget<WorkspaceWidget._None, SyllableDetailWidget> {

    public static final SyllableDetailWidget INSTANCE = new SyllableDetailWidget();

    private SyllableDetailWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, SyllableDetailWidget> {}

    @Override protected _Construct<_None, SyllableDetailWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Syllable"; }
    @Override public LifecycleHint lifecycleHint() { return LifecycleHint.MULTI; }

    @Override
    protected List<ModuleImports<? extends Importable>> bodyImports() {
        return List.of(
                new ModuleImports<>(List.of(
                        new ReadingCss.kr_widget_root(),
                        new ReadingCss.kr_card(),
                        new ReadingCss.kr_badge(),
                        new ReadingCss.kr_syllable(),
                        new ReadingCss.kr_body(),
                        new ReadingCss.kr_part_row(),
                        new ReadingCss.kr_part()),
                        ReadingCss.INSTANCE));
    }

    @Override
    protected List<String> constructBodyJs() {
        return List.of(
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, kr_widget_root);",
                "",
                "    var card = branch.createElement('card', 'div');",
                "    css.setClass(card, kr_card);",
                "",
                "    var badge = branch.createElement('badge', 'div');",
                "    css.setClass(badge, kr_badge);",
                "",
                "    var syllable = branch.createElement('syllable', 'div');",
                "    css.setClass(syllable, kr_syllable);",
                "",
                "    var parts = branch.createElement('parts', 'div');",
                "    css.setClass(parts, kr_part_row);",
                "",
                "    var note = branch.createElement('note', 'div');",
                "    css.setClass(note, kr_body);",
                "",
                "    card.appendChild(badge);",
                "    card.appendChild(syllable);",
                "    card.appendChild(parts);",
                "    card.appendChild(note);",
                "    root.appendChild(card);",
                "",
                "    var TONE_NAMES = ['neutral', 'high level', 'rising', 'low dipping',",
                "                      'falling', 'neutral'];",
                "",
                "    // A branch must be activated by an owner before anything may be",
                "    // created in it - creating the branch only reserves the name.",
                "    var owner = Object.freeze({ toString: function () { return 'syllableDetail'; } });",
                "",
                "    // Same reason as the cards: a branch owns element names, so",
                "    // parts are rebuilt by dissolving and reopening a sub-branch.",
                "    function clearParts() {",
                "        if (branch.getBranch('parts')) branch.dissolveBranch('parts');",
                "    }",
                "",
                "    function addPart(pb, id, caption, value) {",
                "        var box = pb.createElement(id, 'div');",
                "        css.setClass(box, kr_part);",
                "        box.textContent = caption + ': ' + value;",
                "        parts.appendChild(box);",
                "    }",
                "",
                "    function show(kind, label, namePath, summary) {",
                "        clearParts();",
                "        syllable.textContent = label;",
                "        if (kind !== 'syllable') {",
                "            badge.textContent = 'Grouping';",
                "            note.textContent = summary",
                "                + ' below this point. Open it to reach complete syllables.';",
                "            return;",
                "        }",
                "        badge.textContent = 'Syllable';",
                "        var seg = String(namePath || '').split('/');",
                "        if (seg.length === 3) {",
                "            var pb = branch.createBranch('parts');",
                "            pb.activate(owner);",
                "            addPart(pb, 'p0', 'initial', seg[0] === 'zero' ? 'none' : seg[0]);",
                "            addPart(pb, 'p1', 'final', seg[1]);",
                "            var t = parseInt(seg[2], 10);",
                "            addPart(pb, 'p2', 'tone', seg[2] + ' \\u00b7 ' + (TONE_NAMES[t] || t));",
                "        }",
                "        note.textContent = 'The characters read this way are in the '",
                "            + 'Characters pane.';",
                "    }",
                "",
                "    function empty() {",
                "        clearParts();",
                "        badge.textContent = 'Sounds';",
                "        syllable.textContent = 'Nothing selected';",
                "        note.textContent = 'Pick a sound on the left. Initials and finals '",
                "            + 'group; a tone completes a syllable.';",
                "    }",
                "",
                "    function render(node) {",
                "        if (!node) { empty(); return; }",
                "        show(node.kind || '', node.label || '',",
                "             node.namePath || '', node.summary || '');",
                "    }",
                "",
                "    empty();",
                "",
                "    var __actorId  = null;",
                "    var __navParty = (workspaceCtx && workspaceCtx.navParty)",
                "                   ? workspaceCtx.navParty : null;",
                "    if (__navParty) {",
                "        __actorId = 'zi/detail-' + Math.random().toString(36).slice(2, 8);",
                "        __navParty.joinActor({",
                "            id: __actorId,",
                "            parentSecretary: 'navigation',",
                "            reactors: {",
                "                // The secretary rebroadcasts NodeSelected as NavigateTo;",
                "                // reacting to NodeSelected would never fire.",
                "                NavigateTo: function (msg) { render(msg.node); }",
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
                "        }",
                "    };"
        );
    }
}
