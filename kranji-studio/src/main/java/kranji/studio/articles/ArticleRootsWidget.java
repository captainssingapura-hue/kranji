package kranji.studio.articles;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;

import java.util.List;

/**
 * The shelf: which folders the editor may read, and which one it is reading.
 *
 * <h2>A list, because roots are not a tree</h2>
 *
 * <p>The navigator next door draws a tree because a folder <i>is</i> one. A
 * root is not under anything — a checkout, an inbox and a scratch directory
 * are peers with nothing between them — so a list is the shape, and choosing
 * one is a single click rather than a walk.</p>
 *
 * <h2>What choosing does</h2>
 *
 * <p>Nothing here, which is the point. It says {@code PickRoot} on the shelf
 * bus and the navigator reloads its tree; this pane never learns what a draft
 * is. That division is why the navigator can be opened twice on two roots
 * without this pane knowing there are two.</p>
 *
 * <h2>Adding one is a POST</h2>
 *
 * <p>It writes a file. A GET that writes is one a prefetch or a back button
 * can fire, and the shelf would gain a row nobody asked for. The route answers
 * with the whole shelf rather than an acknowledgement, so a change is one round
 * trip and not two.</p>
 *
 * <p>Removing takes a folder off the list. It does not delete anything — worth
 * saying on the button, because a × next to a folder reads like it might.</p>
 */
public final class ArticleRootsWidget
        extends WorkspaceWidget<WorkspaceWidget._None, ArticleRootsWidget> {

    public static final ArticleRootsWidget INSTANCE = new ArticleRootsWidget();

    private ArticleRootsWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, ArticleRootsWidget> {}

    @Override protected _Construct<_None, ArticleRootsWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Roots"; }
    @Override public LifecycleHint lifecycleHint() { return LifecycleHint.SINGLETON; }

    @Override
    protected List<ModuleImports<? extends Importable>> bodyImports() {
        return List.of(new ModuleImports<>(List.of(
                new ArticleWorkbenchCss.aw_root(),
                new ArticleWorkbenchCss.aw_head(),
                new ArticleWorkbenchCss.aw_btn(),
                new ArticleWorkbenchCss.aw_shelf_bar(),
                new ArticleWorkbenchCss.aw_shelf_input(),
                new ArticleWorkbenchCss.aw_shelf_row(),
                new ArticleWorkbenchCss.aw_shelf_on(),
                new ArticleWorkbenchCss.aw_shelf_path(),
                new ArticleWorkbenchCss.aw_shelf_gone(),
                new ArticleWorkbenchCss.aw_shelf_x(),
                new ArticleWorkbenchCss.aw_preview(),
                new ArticleWorkbenchCss.aw_status(),
                new ArticleWorkbenchCss.aw_error()),
                ArticleWorkbenchCss.INSTANCE));
    }

    @Override
    protected List<String> constructBodyJs() {
        return List.of(
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, aw_root);",
                "",
                "    var bar = branch.createElement('bar', 'div');",
                "    css.setClass(bar, aw_shelf_bar);",
                "    root.appendChild(bar);",
                "",
                "    var box = branch.createElement('box', 'input');",
                "    box.type = 'text';",
                "    box.placeholder = 'A folder of .kmd files';",
                "    css.setClass(box, aw_shelf_input);",
                "    bar.appendChild(box);",
                "",
                "    var addBtn = branch.createElement('add', 'button');",
                "    addBtn.type = 'button';",
                "    addBtn.textContent = 'Add';",
                "    css.setClass(addBtn, aw_btn);",
                "    bar.appendChild(addBtn);",
                "",
                "    var list = branch.createElement('list', 'div');",
                "    css.setClass(list, aw_preview);",
                "    root.appendChild(list);",
                "",
                "    // Where the shelf itself is kept. A tool that reads its settings",
                "    // from a file nobody can find is a tool nobody can fix, and this",
                "    // is exactly the kind of file that ends up in a home directory",
                "    // other than the one being looked in.",
                "    var foot = branch.createElement('foot', 'div');",
                "    css.setClass(foot, aw_head);",
                "    root.appendChild(foot);",
                "",
                "    var where = branch.createElement('where', 'span');",
                "    foot.appendChild(where);",
                "",
                "    var owner = Object.freeze({ toString: function () { return 'roots'; } });",
                "",
                "    function fresh(name) {",
                "        if (branch.getBranch(name)) branch.dissolveBranch(name);",
                "        var b = branch.createBranch(name);",
                "        b.activate(owner);",
                "        return b;",
                "    }",
                "",
                "    var FRESH = { cache: 'no-store' };",
                "    var chosen = null;",
                "    var shelf = [];",
                "",
                "    // ── The bus ────────────────────────────────────────────────",
                "",
                "    var __party = (workspaceCtx && workspaceCtx.articleShelf)",
                "                ? workspaceCtx.articleShelf : null;",
                "    var __actorId = null;",
                "",
                "    function tellRoot(id) {",
                "        if (!__party || !__actorId) return;",
                "        __party.tellFrom(__actorId, { kind: 'PickRoot', root: id });",
                "    }",
                "",
                "    // ── Drawing ────────────────────────────────────────────────",
                "",
                "    function say(b, text, bad) {",
                "        var line = b.createElement('msg', 'div');",
                "        css.setClass(line, bad ? aw_error : aw_status);",
                "        line.textContent = text;",
                "        list.appendChild(line);",
                "    }",
                "",
                "    var problem = '';",
                "",
                "    function paint() {",
                "        var b = fresh('shelf');",
                "        if (problem) say(b, problem, true);",
                "        if (!shelf.length) {",
                "            say(b, 'No folders yet. Type one above.', false);",
                "            return;",
                "        }",
                "        for (var i = 0; i < shelf.length; i++) {",
                "            (function (r, n) {",
                "                var row = b.createElement('r' + n, 'div');",
                "                css.setClass(row, r.id === chosen ? aw_shelf_on : aw_shelf_row);",
                "                row.addEventListener('click', function () { choose(r.id); });",
                "",
                "                var name = b.createElement('n' + n, 'span');",
                "                name.textContent = r.name;",
                "                row.appendChild(name);",
                "",
                "                if (!r.present) {",
                "                    var gone = b.createElement('g' + n, 'span');",
                "                    css.setClass(gone, aw_shelf_gone);",
                "                    gone.textContent = 'not there';",
                "                    row.appendChild(gone);",
                "                }",
                "",
                "                var path = b.createElement('p' + n, 'span');",
                "                css.setClass(path, aw_shelf_path);",
                "                path.textContent = r.path;",
                "                row.appendChild(path);",
                "",
                "                var x = b.createElement('x' + n, 'button');",
                "                x.type = 'button';",
                "                x.textContent = '\\u00d7';",
                "                // The folder is untouched. This is a list, not a bin.",
                "                x.title = 'Take off the shelf (the folder is not deleted)';",
                "                css.setClass(x, aw_shelf_x);",
                "                x.addEventListener('click', function (ev) {",
                "                    ev.stopPropagation();",
                "                    change({ action: 'remove', id: r.id });",
                "                });",
                "                row.appendChild(x);",
                "",
                "                list.appendChild(row);",
                "            })(shelf[i], i);",
                "        }",
                "    }",
                "",
                "    function choose(id) {",
                "        chosen = id;",
                "        paint();",
                "        tellRoot(id);",
                "    }",
                "",
                "    // ── Reading and changing ───────────────────────────────────",
                "",
                "    function took(d) {",
                "        where.textContent = d.store || '';",
                "        shelf = d.roots || [];",
                "        var here = shelf.some(function (r) { return r.id === chosen; });",
                "        if (!here) {",
                "            var was = chosen;",
                "            chosen = shelf.length ? shelf[0].id : null;",
                "            // Only when a chosen root actually WENT. A root that has",
                "            // been removed leaves the navigator showing a folder that",
                "            // is no longer on the shelf, so it has to be told.",
                "            //",
                "            // Adopting the first root on arrival is a different thing:",
                "            // this pane catching up with the default the navigator",
                "            // already uses. Announcing that would clear whichever draft",
                "            // was open, and opening a pane is not a reason to close a",
                "            // document - which is exactly what it did until somebody",
                "            // watched it happen.",
                "            if (was) tellRoot(chosen);",
                "        }",
                "        paint();",
                "    }",
                "",
                "    function load() {",
                "        fetch('/article-roots', FRESH)",
                "            .then(function (r) { return r.json(); })",
                "            .then(function (d) { problem = ''; took(d); })",
                "            .catch(function (e) {",
                "                problem = 'Could not read the shelf: ' + e;",
                "                paint();",
                "            });",
                "    }",
                "",
                "    function change(body) {",
                "        fetch('/article-roots', {",
                "            method: 'POST',",
                "            cache: 'no-store',",
                "            headers: { 'Content-Type': 'application/json' },",
                "            body: JSON.stringify(body)",
                "        })",
                "            .then(function (r) { return r.json(); })",
                "            .then(function (d) {",
                "                // A refusal is an ordinary mistake - a path that is not a",
                "                // folder - so it is said and everything else stays put.",
                "                problem = d.ok ? '' : (d.message || 'That did not work.');",
                "                if (d.ok) box.value = '';",
                "                took(d.shelf || { roots: [] });",
                "            })",
                "            .catch(function (e) {",
                "                problem = 'Could not change the shelf: ' + e;",
                "                paint();",
                "            });",
                "    }",
                "",
                "    function add() {",
                "        var typed = (box.value || '').trim();",
                "        if (!typed) return;",
                "        change({ action: 'add', path: typed });",
                "    }",
                "",
                "    addBtn.addEventListener('click', add);",
                "    box.addEventListener('keydown', function (ev) {",
                "        if (ev.key === 'Enter') { ev.preventDefault(); add(); }",
                "    });",
                "",
                "    // ── Joining ────────────────────────────────────────────────",
                "",
                "    // No reactors: the shelf is the top of this conversation and there",
                "    // is nothing upstream of it. It joins to be able to speak.",
                "    if (__party) {",
                "        __actorId = 'article-roots-'",
                "                  + Math.random().toString(36).slice(2, 8);",
                "        __party.joinActor({",
                "            id: __actorId,",
                "            parentSecretary: 'articleShelf',",
                "            reactors: {}",
                "        });",
                "    }",
                "",
                "    load();",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) { if (active) load(); },",
                "        partyDeregister: function () {",
                "            if (__party && __actorId) __party.leaveActor(__actorId);",
                "        }",
                "    };"
        );
    }
}
