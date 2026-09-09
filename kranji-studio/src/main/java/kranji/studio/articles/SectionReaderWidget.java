package kranji.studio.articles;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.core.js.TreeRendererModule;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;

import java.util.List;

/**
 * A generated document, read: its sections on the left, one of them as squares.
 *
 * <h2>What this is for</h2>
 *
 * <p>The workbench answers an author's questions — did the subset accept this,
 * what did the cut produce, where are the ids missing. This answers the only
 * question that matters afterwards: <b>does it read.</b></p>
 *
 * <p>So there is one thing on the right and it is the grid. No document view,
 * no findings, no plan sizes: those are the bench's, and a page a child is
 * looking at should have nothing on it but the page.</p>
 *
 * <h2>Nothing here has seen markdown</h2>
 *
 * <p>The sections come from resources {@link ArticleGenerator} wrote. The route
 * reads structure and arranges it; the pane draws the arrangement. That is the
 * whole path, and the absence of a parser anywhere along it is the point of the
 * generator existing.</p>
 *
 * <p>The document is fixed for now. Choosing between documents is the library's
 * job and the library does not carry a generated one yet.</p>
 *
 * <p>No CJK literal appears in this file. Every character on screen came from a
 * generated resource.</p>
 */
public final class SectionReaderWidget
        extends WorkspaceWidget<WorkspaceWidget._None, SectionReaderWidget> {

    public static final SectionReaderWidget INSTANCE = new SectionReaderWidget();

    private SectionReaderWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, SectionReaderWidget> {}

    @Override protected _Construct<_None, SectionReaderWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Reading"; }
    @Override public LifecycleHint lifecycleHint() { return LifecycleHint.MULTI; }

    @Override
    protected List<ModuleImports<? extends Importable>> bodyImports() {
        return List.of(
                // The library's own tree. Arrow keys come with it, and so does
                // the guarantee that they mean here what they mean there.
                new ModuleImports<>(List.of(new TreeRendererModule.TreeRenderer()),
                        TreeRendererModule.INSTANCE),
                new ModuleImports<>(List.of(
                        new ArticleWorkbenchCss.aw_root(),
                        new ArticleWorkbenchCss.aw_head(),
                        new ArticleWorkbenchCss.aw_btn(),
                        new ArticleWorkbenchCss.aw_split(),
                        new ArticleWorkbenchCss.aw_list(),
                        new ArticleWorkbenchCss.aw_main(),
                        new ArticleWorkbenchCss.aw_preview(),
                        new ArticleWorkbenchCss.aw_msg(),
                        new ArticleWorkbenchCss.aw_status(),
                        new ArticleWorkbenchCss.aw_strip(),
                        new ArticleWorkbenchCss.aw_strip_text(),
                        new ArticleWorkbenchCss.aw_cell(),
                        new ArticleWorkbenchCss.aw_board_host(),
                        new ArticleWorkbenchCss.aw_sq_zi(),
                        new ArticleWorkbenchCss.aw_sq_ann(),
                        new ArticleWorkbenchCss.aw_sq_punct(),
                        new ArticleWorkbenchCss.aw_sq_run(),
                        new ArticleWorkbenchCss.aw_sq_bold(),
                        new ArticleWorkbenchCss.aw_sq_marker(),
                        new ArticleWorkbenchCss.aw_sq_indent()),
                        ArticleWorkbenchCss.INSTANCE),
                new ModuleImports<>(
                        List.of(new KmdBoardModule.createKmdBoard()),
                        KmdBoardModule.INSTANCE));
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
                "    // Twenty per row is the ruled paper a child writes on. The",
                "    // others are here because a smaller page is a different reading.",
                "    var WIDTHS = [20, 16, 12, 10];",
                "    var width = 0;",
                "    var widthBtn = branch.createElement('width', 'button');",
                "    widthBtn.type = 'button';",
                "    css.setClass(widthBtn, aw_btn);",
                "    head.appendChild(widthBtn);",
                "",
                "    var where = branch.createElement('where', 'span');",
                "    head.appendChild(where);",
                "",
                "    var split = branch.createElement('split', 'div');",
                "    css.setClass(split, aw_split);",
                "    root.appendChild(split);",
                "",
                "    var list = branch.createElement('list', 'div');",
                "    css.setClass(list, aw_list);",
                "    split.appendChild(list);",
                "",
                "    var main = branch.createElement('main', 'div');",
                "    css.setClass(main, aw_main);",
                "    split.appendChild(main);",
                "",
                "    // What the square under the cursor is not showing.",
                "    //",
                "    // A run is one square holding a placeholder, because a word does",
                "    // not fit in a box. This is where the word goes. It sits above",
                "    // the page rather than beside the square for the reason a",
                "    // tooltip is not enough: a tooltip needs a pointer, and the",
                "    // grid is walked with arrow keys.",
                "    var strip = branch.createElement('strip', 'div');",
                "    css.setClass(strip, aw_strip);",
                "    main.appendChild(strip);",
                "",
                "    var stripText = branch.createElement('stripText', 'span');",
                "    css.setClass(stripText, aw_strip_text);",
                "    strip.appendChild(stripText);",
                "",
                "    var page = branch.createElement('page', 'div');",
                "    css.setClass(page, aw_preview);",
                "    main.appendChild(page);",
                "",
                "    // The grid mounts in here rather than straight into the page.",
                "    // A grid table is width:100%, so it takes whatever it is given -",
                "    // and a page of squares given a wide pane stops being square.",
                "    var boardHost = branch.createElement('boardHost', 'div');",
                "    css.setClass(boardHost, aw_board_host);",
                "    page.appendChild(boardHost);",
                "",
                "    var owner = Object.freeze({ toString: function () { return 'reading'; } });",
                "",
                "    // Empty is a state, not an absence: the strip keeps its height so",
                "    // that moving onto a 字 does not shift the whole page up.",
                "    function follow(square) {",
                "        stripText.textContent =",
                "                (square && square.k === 'r') ? String(square.t || '') : '';",
                "    }",
                "",
                "    var sheet = createKmdBoard({",
                "        css: css,",
                "        branch: branch,",
                "        container: boardHost,",
                "        classes: {",
                "            cell: aw_cell, zi: aw_sq_zi, ann: aw_sq_ann,",
                "            punct: aw_sq_punct, run: aw_sq_run,",
                "            bold: aw_sq_bold, marker: aw_sq_marker,",
                "            indent: aw_sq_indent",
                "        },",
                "        onCursor: follow",
                "    });",
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
                "",
                "    function say(text) {",
                "        sheet.destroy();",
                "        var b = fresh('page');",
                "        var line = b.createElement('msg', 'div');",
                "        css.setClass(line, aw_msg);",
                "        line.textContent = text;",
                "        page.appendChild(line);",
                "    }",
                "",
                "    // Arrow keys are the renderer's semantics; deciding when they",
                "    // flow is this pane's. That division is TreeRendererModule's own,",
                "    // and it is why this shelf behaves like the library's without",
                "    // sharing a line of key handling with it.",
                "    //",
                "    // The condition here is FOCUS rather than the shell's notion of",
                "    // an active pane. Focus is the thing a person can see, it is",
                "    // already what decides where their typing goes, and it settles",
                "    // two of these open side by side without either asking anything",
                "    // about the other.",
                "    var __renderer = null;",
                "",
                "    // Focus sits on either side of this root: inside it when",
                "    // something here was clicked, and on the shell's tab content -",
                "    // an ANCESTOR - when the pane was merely entered. Both mean the",
                "    // keys are ours. Neither is true of the pane next door, whose",
                "    // tab content contains its own root and not this one.",
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
                "    // document can swallow an arrow before the shelf sees it. The",
                "    // focus guard is what keeps capturing from being greedy: first",
                "    // refusal, but only over this pane's own keys.",
                "    document.addEventListener('keydown', __keys, true);",
                "",
                "    // A section's segment IS its id, so the last element of the",
                "    // name path is the whole address. No lookup, no second request.",
                "    function idOf(node) {",
                "        var parts = String((node && node.namePath) || '').split('/');",
                "        return parts[parts.length - 1] || '';",
                "    }",
                "",
                "    function open(id) {",
                "        if (!id) return;",
                "        chosen = id;",
                "        say('\\u2026');",
                "        fetch('/mvp-section?id=' + encodeURIComponent(id)",
                "            + '&columns=' + WIDTHS[width], FRESH)",
                "            .then(function (r) { return r.json(); })",
                "            .then(function (d) {",
                "                if (d.error) { say(d.error); return; }",
                "                sheet.draw(fresh('page'), d.plan);",
                "            })",
                "            .catch(function (e) { say('Could not read it: ' + e); });",
                "    }",
                "",
                "    function load() {",
                "        fetch('/mvp-section', FRESH)",
                "            .then(function (r) { return r.json(); })",
                "            .then(function (tree) {",
                "                where.textContent = (tree.display && tree.display.label) || '';",
                "                var tb = fresh('tree');",
                "                if (__renderer && __renderer.destroy) {",
                "                    try { __renderer.destroy(); } catch (e) {}",
                "                }",
                "                __renderer = new TreeRenderer({",
                "                    branch: tb, container: list, data: tree,",
                "                    expandDepth: 3,",
                "                    // A count of characters is a fact about the",
                "                    // arrangement, not about the thing being chosen.",
                "                    showBadge: false, showNote: false,",
                "                    onSelect: function (sel) { open(idOf(sel)); },",
                "                    onActivate: function (sel) { open(idOf(sel)); }",
                "                });",
                "                open(chosen || idOf({ namePath: firstOf(tree) }));",
                "            })",
                "            .catch(function (e) { where.textContent = 'Could not list: ' + e; });",
                "    }",
                "",
                "    // The first thing there is to read: the document's own opening,",
                "    // which is what a reader meets before any section.",
                "    function firstOf(tree) {",
                "        var kids = (tree && tree.children) || [];",
                "        return kids.length ? kids[0].segment : '';",
                "    }",
                "",
                "    function labels() { widthBtn.textContent = WIDTHS[width] + '/row'; }",
                "    labels();",
                "",
                "    widthBtn.addEventListener('click', function () {",
                "        width = (width + 1) % WIDTHS.length;",
                "        labels();",
                "        if (chosen) open(chosen);",
                "    });",
                "",
                "    load();",
                "",
                "    return {",
                "        root: root,",
                "        // Required by the mounter, and nothing to do: the keys are",
                "        // gated on focus rather than on being the active pane, for",
                "        // the reason given where they are attached.",
                "        setActive: function () {},",
                "        partyDeregister: function () {",
                "            document.removeEventListener('keydown', __keys, true);",
                "        }",
                "    };"
        );
    }
}
