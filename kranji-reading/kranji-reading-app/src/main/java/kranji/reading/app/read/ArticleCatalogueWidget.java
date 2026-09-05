package kranji.reading.app.read;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.core.js.TreeRendererModule;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;

import java.util.List;

/**
 * Browses the articles by class — 古诗, 寓言, 说明文, and the rest.
 *
 * <p>The framework's generic tree renderer draws canonical tree JSON from
 * {@code /article-tree}, so there is no per-tree JavaScript here.</p>
 *
 * <h2>What travels when a title is chosen</h2>
 *
 * <p>The article's id, and only that. A title's tree segment <em>is</em> its
 * id, so activating a leaf needs no lookup: the widget reads the last segment
 * of the node's {@code namePath} and says {@code ArticleSelected}. It never
 * names the reader, and the reader never names the catalogue — which is what
 * lets a search result or a "continue reading" tile become a second producer
 * later without either side changing.</p>
 *
 * <p>A shelf is not openable; only a title is. That is why the leaf carries
 * {@code kind: "article"} — activating a shelf should expand it, not try to
 * read a class of literature.</p>
 *
 * <p>No CJK literal appears in this file. Titles arrive over the wire, which
 * {@code NoInlineGlyphRule} enforces.</p>
 */
public final class ArticleCatalogueWidget
        extends WorkspaceWidget<WorkspaceWidget._None, ArticleCatalogueWidget> {

    public static final ArticleCatalogueWidget INSTANCE = new ArticleCatalogueWidget();

    private ArticleCatalogueWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, ArticleCatalogueWidget> {}

    @Override protected _Construct<_None, ArticleCatalogueWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Library"; }
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
                "    status.textContent = 'Loading the library\\u2026';",
                "    container.appendChild(status);",
                "",
                "    var __actorId = null;",
                "    var __party = (workspaceCtx && workspaceCtx.articleParty)",
                "                ? workspaceCtx.articleParty : null;",
                "    if (__party) {",
                "        __actorId = 'read/library-' + Math.random().toString(36).slice(2, 8);",
                "        __party.joinActor({",
                "            id: __actorId,",
                "            parentSecretary: 'articleSelection',",
                "            reactors: {}",
                "        });",
                "    }",
                "",
                "    var owner = Object.freeze({ toString: function () { return 'library'; } });",
                "",
                "    // No known set here, and no readability. The figures were worth",
                "    // having and cost more than they were worth: keeping them true meant",
                "    // measuring all 475 articles and rebuilding the tree on every claim -",
                "    // 19.6ms to end up with the same 3,124 elements and 34 changed",
                "    // numbers. The reader still gets the measure, in the pane that is",
                "    // showing the article it describes.",
                "",
                "    var __renderer = null;",
                "    var __keyHandler = function (ev) {",
                "        if (__renderer && __renderer.handleKeydown(ev)) ev.preventDefault();",
                "    };",
                "",
                "    // A title's segment is its id, so the last path element is",
                "    // the whole message. No lookup, no second request.",
                "    var idOf = function (node) {",
                "        var path = String((node && node.namePath) || '');",
                "        var parts = path.split('/');",
                "        return parts[parts.length - 1] || '';",
                "    };",
                "",
                "    var open = function (node) {",
                "        if (!node || node.kind !== 'article') return;",
                "        var id = idOf(node);",
                "        if (!id) return;",
                "        if (__party && __actorId) {",
                "            __party.tellFrom(__actorId,",
                "                { kind: 'ArticleSelected', article: id });",
                "        }",
                "    };",
                "",
                "    // The tree says what there is, and that is all this pane says. What",
                "    // a particular reader brings to a particular article is the Reader's",
                "    // question, asked once when an article is opened.",
                "    var __tree = null;",
                "",
                "    // Built once. It used to be rebuilt on every claim, which the",
                "    // comment here called cheap because the library was a couple of",
                "    // dozen articles; it is 475 now and the justification did not move",
                "    // with it. Nothing in this tree depends on the reader any more, so",
                "    // there is no second call.",
                "    function draw() {",
                "        if (!__tree) return;",
                "        if (status.parentNode) container.removeChild(status);",
                "        if (__renderer && __renderer.destroy) { try { __renderer.destroy(); } catch (e) {} }",
                "        if (branch.getBranch('tree')) branch.dissolveBranch('tree');",
                "        var tb = branch.createBranch('tree');",
                "        tb.activate(owner);",
                "        __renderer = new TreeRenderer({",
                "            branch:      tb,",
                "            container:   container,",
                "            data:        __tree,",
                "            expandDepth: 3,",
                "            // The note survives the badge: a shelf's own description is",
                "            // written by whoever curated it and says nothing about any",
                "            // reader, so it was never the expensive half.",
                "            showBadge:   false,",
                "            showNote:    true,",
                "            onSelect: function (sel) {",
                "                if (sel && sel.activated) open(sel);",
                "            },",
                "            onActivate: function (sel) { open(sel); }",
                "        });",
                "    }",
                "",
                "    // One request now. The census used to be fetched here too - 274KB",
                "    // to put a percentage on every shelf - and both had to land before",
                "    // anything drew, so the list waited on a figure it no longer shows.",
                "    fetch('/article-tree').then(function (r) {",
                "        if (!r.ok) throw new Error('HTTP ' + r.status);",
                "        return r.json();",
                "    }).then(function (tree) {",
                "        __tree = tree;",
                "        draw();",
                "    }).catch(function (err) {",
                "        status.textContent = 'Could not load the library: '",
                "            + (err && err.message ? err.message : String(err));",
                "    });",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) {",
                "            if (active) document.addEventListener('keydown', __keyHandler);",
                "            else        document.removeEventListener('keydown', __keyHandler);",
                "        },",
                "        partyDeregister: function () {",
                "            document.removeEventListener('keydown', __keyHandler);",
                "            if (__actorId && __party) {",
                "                try { __party.leave(__actorId); } catch (e) {}",
                "            }",
                "        }",
                "    };"
        );
    }
}
