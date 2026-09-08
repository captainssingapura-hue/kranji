package kranji.studio.articles;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.core.js.TreeRendererModule;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;

import java.util.List;

/**
 * The navigator: one root's folder, as the tree it already is.
 *
 * <h2>The folder is the shape</h2>
 *
 * <p>No manifest, no ordering file, nothing that can fall out of step with the
 * directory it describes. Sub-folders nest, {@code .kmd} files are the leaves,
 * and where a draft sits says nothing about the address it publishes under —
 * so moving one between folders is free.</p>
 *
 * <h2>Its own widget, and why that is worth a bus</h2>
 *
 * <p>It was the left-hand side of the workbench and is now a pane of its own,
 * which is what lets two of them sit side by side on two different roots. The
 * cost is that choosing a draft has to reach the bench somehow, and that is
 * the shelf party: {@code PickDraft} out, {@code ShelfChanged} in.</p>
 *
 * <p>{@code MULTI} for the same reason: comparing two folders is the
 * arrangement this exists for, and each copy follows whichever root it was
 * last told about.</p>
 *
 * <h2>Arrow keys come from the renderer</h2>
 *
 * <p>{@code TreeRenderer} owns the key semantics for any conforming tree, so a
 * second tree with a second idea of what ArrowDown means never gets written.
 * This pane owns only <em>when</em> the keys flow, and gates that on focus.</p>
 */
public final class ArticleNavigatorWidget
        extends WorkspaceWidget<WorkspaceWidget._None, ArticleNavigatorWidget> {

    public static final ArticleNavigatorWidget INSTANCE = new ArticleNavigatorWidget();

    private ArticleNavigatorWidget() {}

    private record construct()
            implements WorkspaceWidget._Construct<_None, ArticleNavigatorWidget> {}

    @Override protected _Construct<_None, ArticleNavigatorWidget> construct() {
        return new construct();
    }

    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Navigator"; }
    @Override public LifecycleHint lifecycleHint() { return LifecycleHint.MULTI; }

    @Override
    protected List<ModuleImports<? extends Importable>> bodyImports() {
        return List.of(
                new ModuleImports<>(List.of(
                        new ArticleWorkbenchCss.aw_root(),
                        new ArticleWorkbenchCss.aw_head(),
                        new ArticleWorkbenchCss.aw_btn(),
                        new ArticleWorkbenchCss.aw_preview(),
                        new ArticleWorkbenchCss.aw_status(),
                        new ArticleWorkbenchCss.aw_error()),
                        ArticleWorkbenchCss.INSTANCE),
                new ModuleImports<>(List.of(new TreeRendererModule.TreeRenderer()),
                        TreeRendererModule.INSTANCE));
    }

    @Override
    protected List<String> constructBodyJs() {
        return List.of(
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, aw_root);",
                "",
                "    var head = branch.createElement('head', 'div');",
                "    css.setClass(head, aw_head);",
                "    root.appendChild(head);",
                "",
                "    var refresh = branch.createElement('refresh', 'button');",
                "    refresh.type = 'button';",
                "    refresh.textContent = 'Refresh';",
                "    css.setClass(refresh, aw_btn);",
                "    head.appendChild(refresh);",
                "",
                "    var where = branch.createElement('where', 'span');",
                "    head.appendChild(where);",
                "",
                "    var list = branch.createElement('list', 'div');",
                "    css.setClass(list, aw_preview);",
                "    root.appendChild(list);",
                "",
                "    var owner = Object.freeze({ toString: function () { return 'navigator'; } });",
                "",
                "    function fresh(name) {",
                "        if (branch.getBranch(name)) branch.dissolveBranch(name);",
                "        var b = branch.createBranch(name);",
                "        b.activate(owner);",
                "        return b;",
                "    }",
                "",
                "    // Never from the browser's cache. The loop this serves is save,",
                "    // look, fix, and a cached listing is a Refresh that does nothing.",
                "    var FRESH = { cache: 'no-store' };",
                "",
                "    // '' means 'whichever the server calls first', which is what a pane",
                "    // shows before anybody has chosen a root.",
                "    var rootId = '';",
                "    var drafts = [];",
                "    var __renderer = null;",
                "",
                "    // ── The bus ────────────────────────────────────────────────",
                "",
                "    var __party = (workspaceCtx && workspaceCtx.articleShelf)",
                "                ? workspaceCtx.articleShelf : null;",
                "    var __actorId = null;",
                "",
                "    // ── Keys ───────────────────────────────────────────────────",
                "",
                "    // Arrow keys are the renderer's semantics; deciding when they flow",
                "    // is this pane's. Gated on FOCUS rather than on the shell's notion",
                "    // of an active pane: focus is the thing a person can see, it",
                "    // already decides where their typing goes, and it settles two of",
                "    // these open side by side without either asking about the other.",
                "    // Focus sits inside this root when something here was clicked, and",
                "    // on an ANCESTOR when the pane was merely entered.",
                "    function ours() {",
                "        var here = document.activeElement;",
                "        return !!here && (root.contains(here) || here.contains(root));",
                "    }",
                "",
                "    var __keys = function (ev) {",
                "        if (!ours()) return;",
                "        if (__renderer && __renderer.handleKeydown(ev)) ev.preventDefault();",
                "    };",
                "    // Capture, so nothing between the focused tab content and the",
                "    // document swallows an arrow before the tree sees it.",
                "    document.addEventListener('keydown', __keys, true);",
                "",
                "    // ── Drawing ────────────────────────────────────────────────",
                "",
                "    function say(text, bad) {",
                "        var b = fresh('tree');",
                "        if (__renderer && __renderer.destroy) {",
                "            try { __renderer.destroy(); } catch (e) {}",
                "        }",
                "        __renderer = null;",
                "        var line = b.createElement('msg', 'div');",
                "        css.setClass(line, bad ? aw_error : aw_status);",
                "        line.textContent = text;",
                "        list.appendChild(line);",
                "    }",
                "",
                "    // A draft's segment IS its id; a folder's is empty, which is how a",
                "    // folder is known not to be something to open.",
                "    function idOf(node) {",
                "        var parts = String((node && node.namePath) || '').split('/');",
                "        return parts[parts.length - 1] || '';",
                "    }",
                "",
                "    function nameOf(id) {",
                "        for (var i = 0; i < drafts.length; i++) {",
                "            if (drafts[i].id === id) return drafts[i].path;",
                "        }",
                "        return '';",
                "    }",
                "",
                "    function pick(id) {",
                "        if (!id) return;",
                "        if (!__party || !__actorId) return;",
                "        __party.tellFrom(__actorId, {",
                "            kind: 'PickDraft', root: rootId, draft: id, name: nameOf(id)",
                "        });",
                "    }",
                "",
                "    function paint(tree) {",
                "        var b = fresh('tree');",
                "        if (__renderer && __renderer.destroy) {",
                "            try { __renderer.destroy(); } catch (e) {}",
                "        }",
                "        __renderer = null;",
                "        __renderer = new TreeRenderer({",
                "            branch: b, container: list, data: tree,",
                "            expandDepth: 3,",
                "            // The count belongs here. A navigator is where somebody",
                "            // asks how much there is left to check.",
                "            showBadge: true, showNote: false,",
                "            onSelect: function (sel) { pick(idOf(sel)); },",
                "            onActivate: function (sel) { pick(idOf(sel)); }",
                "        });",
                "    }",
                "",
                "    function load() {",
                "        var url = '/article-draft'",
                "                + (rootId ? '?root=' + encodeURIComponent(rootId) : '');",
                "        fetch(url, FRESH)",
                "            .then(function (r) { return r.json(); })",
                "            .then(function (d) {",
                "                // The server answers with the root it actually read, so a",
                "                // pane that asked for nothing now knows what it got and",
                "                // can name it when a draft is picked.",
                "                rootId = d.root || '';",
                "                drafts = d.drafts || [];",
                "                where.textContent = d.dir || '';",
                "                if (!d.tree || !drafts.length) {",
                "                    say(d.message || 'No .kmd files here yet.', false);",
                "                    return;",
                "                }",
                "                paint(d.tree);",
                "            })",
                "            .catch(function (e) { say('Could not list: ' + e, true); });",
                "    }",
                "",
                "    refresh.addEventListener('click', function () { load(); });",
                "",
                "    // ── Joining ────────────────────────────────────────────────",
                "",
                "    if (__party) {",
                "        __actorId = 'article-nav-'",
                "                  + Math.random().toString(36).slice(2, 8);",
                "        __party.joinActor({",
                "            id: __actorId,",
                "            parentSecretary: 'articleShelf',",
                "            reactors: {",
                "                ShelfChanged: function (msg) {",
                "                    // Only the root half. A draft being picked is this",
                "                    // pane's own doing on the way out, and reacting to it",
                "                    // would reload the tree under the click that caused it.",
                "                    if (msg.what !== 'root' && msg.what !== 'resync') return;",
                "                    if ((msg.root || '') === rootId) return;",
                "                    rootId = msg.root || '';",
                "                    load();",
                "                }",
                "            }",
                "        });",
                "    }",
                "",
                "    load();",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) { if (active) load(); },",
                "        partyDeregister: function () {",
                "            document.removeEventListener('keydown', __keys, true);",
                "            if (__party && __actorId) __party.leaveActor(__actorId);",
                "        }",
                "    };"
        );
    }
}
