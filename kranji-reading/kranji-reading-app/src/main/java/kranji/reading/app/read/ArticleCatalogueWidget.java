package kranji.reading.app.read;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.core.js.TreeRendererModule;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;
import kranji.reading.app.known.KnownWatchModule;

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
                new ModuleImports<>(
                        List.of(new KnownWatchModule.createKnownWatch()),
                        KnownWatchModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new ReadabilityModule.createReadability()),
                        ReadabilityModule.INSTANCE),
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
                "    // Redrawn when a reading is marked, because that is exactly when the",
                "    // figures stop being true. An article that was Stretch in January and",
                "    // is Just right in June is the clearest evidence of progress the app",
                "    // can show, and it is worth nothing if it is stale.",
                "    var known = createKnownWatch({",
                "        events: (workspaceCtx && workspaceCtx.knownEventParty)",
                "                    ? workspaceCtx.knownEventParty : null,",
                "        id: 'library',",
                "        onChanged: function () { draw(); }",
                "    });",
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
                "    // The tree says what there is; the census says what each one asks;",
                "    // the known set says what this reader brings. Only the first two come",
                "    // from the server - the third never leaves the device, so the fit is",
                "    // worked out here and the server never learns what a child can read.",
                "    var readability = createReadability();",
                "    var __tree = null;",
                "    var __census = null;",
                "",
                "    // Written onto the node the renderer already draws, rather than into",
                "    // a column of its own: badge for the figure, note for what it means.",
                "    // A reader choosing a story wants both at once - 94% with eight new",
                "    // readings is a different offer from 94% with one repeated forty",
                "    // times, and the second is the better lesson.",
                "    function decorate(node) {",
                "        var d = node.display;",
                "        if (d && d.kind === 'article' && __census) {",
                "            var fit = readability.of(__census[node.segment], known.record());",
                "            if (fit && fit.total > 0) {",
                "                d.badge = Math.round(fit.ratio * 100) + '%';",
                "                var says = fit.band.label;",
                "                if (fit.unknown > 0) says += ' \\u00b7 ' + fit.unknown + ' to learn';",
                "                d.note = d.note ? says + ' \\u00b7 ' + d.note : says;",
                "            }",
                "        }",
                "        var kids = node.children || [];",
                "        for (var i = 0; i < kids.length; i++) decorate(kids[i]);",
                "        return node;",
                "    }",
                "",
                "    function draw() {",
                "        if (!__tree) return;",
                "        if (status.parentNode) container.removeChild(status);",
                "        // Rebuilt rather than patched: the tree is a couple of dozen",
                "        // articles and the renderer owns its DOM. Cheap enough that",
                "        // keeping the figures honest costs nothing worth saving.",
                "        if (__renderer && __renderer.destroy) { try { __renderer.destroy(); } catch (e) {} }",
                "        if (branch.getBranch('tree')) branch.dissolveBranch('tree');",
                "        var tb = branch.createBranch('tree');",
                "        tb.activate(owner);",
                "        __renderer = new TreeRenderer({",
                "            branch:      tb,",
                "            container:   container,",
                "            data:        decorate(JSON.parse(JSON.stringify(__tree))),",
                "            expandDepth: 3,",
                "            // Both opt-in, and both wanted: the figure is useless without",
                "            // what it means beside it. A percentage alone makes a reader",
                "            // chase the highest number, which is the easiest story.",
                "            showBadge:   true,",
                "            showNote:    true,",
                "            onSelect: function (sel) {",
                "                if (sel && sel.activated) open(sel);",
                "            },",
                "            onActivate: function (sel) { open(sel); }",
                "        });",
                "    }",
                "",
                "    // The census is static and cached by the browser; the library is not",
                "    // drawn until both are in, so a figure never appears late and moves",
                "    // the row somebody was about to click.",
                "    Promise.all([",
                "        fetch('/article-tree').then(function (r) {",
                "            if (!r.ok) throw new Error('HTTP ' + r.status);",
                "            return r.json();",
                "        }),",
                "        import('/article-census').then(function (m) { return m.articles; },",
                "                                       function () { return null; })",
                "    ]).then(function (both) {",
                "        __tree = both[0];",
                "        __census = both[1];",
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
                "            known.leave();",
                "            if (__actorId && __party) {",
                "                try { __party.leave(__actorId); } catch (e) {}",
                "            }",
                "        }",
                "    };"
        );
    }
}
