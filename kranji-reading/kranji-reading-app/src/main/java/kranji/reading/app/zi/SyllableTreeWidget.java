package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.core.js.TreeRendererModule;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;

import java.util.List;

/**
 * Browses the characters by sound — initial, then final, then tone.
 *
 * <p>Uses the framework's generic tree renderer, so there is no per-tree
 * JavaScript here: the server emits canonical tree JSON and the renderer draws
 * it. Selecting a node publishes on the navigation party, which the syllable
 * pane listens to — the same navigator-plus-detail arrangement the desktop
 * explorer uses.</p>
 *
 * <p>No CJK literal appears in this file. The characters arrive from the corpus
 * over the wire, which {@code NoInlineGlyphRule} enforces.</p>
 */
public final class SyllableTreeWidget extends WorkspaceWidget<WorkspaceWidget._None, SyllableTreeWidget> {

    public static final SyllableTreeWidget INSTANCE = new SyllableTreeWidget();

    private SyllableTreeWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, SyllableTreeWidget> {}

    @Override protected _Construct<_None, SyllableTreeWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Sounds"; }
    @Override public LifecycleHint lifecycleHint() { return LifecycleHint.MULTI; }

    @Override
    protected List<ModuleImports<? extends Importable>> bodyImports() {
        return List.of(
                new ModuleImports<>(List.of(new TreeRendererModule.TreeRenderer()),
                        TreeRendererModule.INSTANCE),
                new ModuleImports<>(List.of(
                        new ReadingCss.kr_widget_root(),
                        new ReadingCss.kr_status()),
                        ReadingCss.INSTANCE));
    }

    @Override
    protected List<String> constructBodyJs() {
        return List.of(
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, kr_widget_root);",
                "",
                "    var container = branch.createElement('treeContainer', 'div');",
                "    root.appendChild(container);",
                "",
                "    var status = branch.createElement('status', 'div');",
                "    css.setClass(status, kr_status);",
                "    status.textContent = 'Loading sounds\\u2026';",
                "    container.appendChild(status);",
                "",
                "    var __actorId  = null;",
                "    var __navParty = (workspaceCtx && workspaceCtx.navParty)",
                "                   ? workspaceCtx.navParty : null;",
                "    if (__navParty) {",
                "        __actorId = 'zi/tree-' + Math.random().toString(36).slice(2, 8);",
                "        __navParty.joinActor({",
                "            id: __actorId,",
                "            parentSecretary: 'navigation',",
                "            reactors: {}",
                "        });",
                "    }",
                "",
                "    var __renderer = null;",
                "    var __keyHandler = function (ev) {",
                "        if (__renderer && __renderer.handleKeydown(ev)) ev.preventDefault();",
                "    };",
                "",
                "    fetch('/zi-tree')",
                "        .then(function (r) {",
                "            if (!r.ok) throw new Error('HTTP ' + r.status);",
                "            return r.json();",
                "        })",
                "        .then(function (treeJson) {",
                "            container.removeChild(status);",
                "            __renderer = new TreeRenderer({",
                "                branch:      branch,",
                "                container:   container,",
                "                data:        treeJson,",
                "                expandDepth: 1,",
                "                onSelect: function (sel) {",
                "                    if (__navParty && __actorId) {",
                "                        __navParty.tellFrom(__actorId,",
                "                            { kind: 'NodeSelected', node: sel });",
                "                    }",
                "                },",
                "                onActivate: function (sel) {",
                "                    if (__navParty && __actorId) {",
                "                        // Enter or double-click. The secretary knows only",
                "                        // NodeSelected, so the activation rides on the node -",
                "                        // it is the node the secretary rebroadcasts.",
                "                        var opened = Object.assign({}, sel, { activated: true });",
                "                        __navParty.tellFrom(__actorId,",
                "                            { kind: 'NodeSelected', node: opened });",
                "                    }",
                "                }",
                "            });",
                "        })",
                "        .catch(function (err) {",
                "            status.textContent = 'Could not load the sound tree: '",
                "                + (err && err.message ? err.message : String(err));",
                "        });",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) {",
                "            if (active) document.addEventListener('keydown', __keyHandler);",
                "            else        document.removeEventListener('keydown', __keyHandler);",
                "        },",
                "        partyDeregister: function () {",
                "            document.removeEventListener('keydown', __keyHandler);",
                "            if (__actorId && __navParty) {",
                "                try { __navParty.leave(__actorId); } catch (e) {}",
                "            }",
                "        }",
                "    };"
        );
    }
}
