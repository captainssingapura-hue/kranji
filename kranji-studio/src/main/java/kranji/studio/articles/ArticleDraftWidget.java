package kranji.studio.articles;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;

import java.util.List;

/**
 * The article workbench: a folder of drafts, and what the subset makes of one.
 *
 * <h2>One widget, not two</h2>
 *
 * <p>The list and the preview are the same pane deliberately. A draft list is
 * not something anybody reads on its own — it exists to choose from — and
 * splitting them would mean a party, a secretary and a message type before the
 * bench could show anything at all. When the library view arrives from the
 * reader this pane gives up its list and keeps its right-hand side.</p>
 *
 * <h2>What it shows, and what it does not</h2>
 *
 * <p>Stage one. The preview is the <em>document</em> — headings, lists, verse,
 * the runs and the overrides — drawn as a document rather than as the reader's
 * grid. That is the right thing to look at first: the question at this stage is
 * whether the file says what its author meant and whether the subset accepted
 * it, not what a child will eventually see.</p>
 *
 * <p>Findings sit underneath and are the point of the bench. An error means
 * nothing rendered, and the pane says so rather than showing an empty document.
 * A warning means it rendered with something dropped, which is the case an
 * author has to look at and agree with.</p>
 *
 * <h2>Two branches, and nothing cleared by hand</h2>
 *
 * <p>The frame is built on the widget's own branch and outlives every draft.
 * The list lives on a {@code drafts} branch and the document on a {@code doc}
 * branch, and each is replaced by dissolving the old one — which is the only
 * way this pane can know that what left the screen also left the tree. It is
 * worth naming what this replaced: the first version assigned {@code innerHTML}
 * and built the rest with {@code document.createElement}, so nothing it put on
 * screen was owned by anything.</p>
 *
 * <h2>Nothing is cached</h2>
 *
 * <p>Refresh re-reads the folder, and choosing a draft re-fetches it. The loop
 * this exists for is save, look, fix, and a bench that showed the file you
 * saved a minute ago would be worse than no bench.</p>
 *
 * <p>No CJK literal appears in this file. Every character on screen came from a
 * draft on disk.</p>
 */
public final class ArticleDraftWidget
        extends WorkspaceWidget<WorkspaceWidget._None, ArticleDraftWidget> {

    public static final ArticleDraftWidget INSTANCE = new ArticleDraftWidget();

    private ArticleDraftWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, ArticleDraftWidget> {}

    @Override protected _Construct<_None, ArticleDraftWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Drafts"; }
    @Override public LifecycleHint lifecycleHint() { return LifecycleHint.MULTI; }

    @Override
    protected List<ModuleImports<? extends Importable>> bodyImports() {
        return List.of(
                new ModuleImports<>(List.of(
                        new ArticleWorkbenchCss.aw_root(),
                        new ArticleWorkbenchCss.aw_head(),
                        new ArticleWorkbenchCss.aw_btn(),
                        new ArticleWorkbenchCss.aw_split(),
                        new ArticleWorkbenchCss.aw_list(),
                        new ArticleWorkbenchCss.aw_item(),
                        new ArticleWorkbenchCss.aw_item_on(),
                        new ArticleWorkbenchCss.aw_size(),
                        new ArticleWorkbenchCss.aw_main(),
                        new ArticleWorkbenchCss.aw_preview(),
                        new ArticleWorkbenchCss.aw_page(),
                        new ArticleWorkbenchCss.aw_title(),
                        new ArticleWorkbenchCss.aw_h2(),
                        new ArticleWorkbenchCss.aw_h3(),
                        new ArticleWorkbenchCss.aw_pin(),
                        new ArticleWorkbenchCss.aw_unpinned(),
                        new ArticleWorkbenchCss.aw_p(),
                        new ArticleWorkbenchCss.aw_li(),
                        new ArticleWorkbenchCss.aw_marker(),
                        new ArticleWorkbenchCss.aw_quote(),
                        new ArticleWorkbenchCss.aw_verse(),
                        new ArticleWorkbenchCss.aw_vline(),
                        new ArticleWorkbenchCss.aw_run(),
                        new ArticleWorkbenchCss.aw_ruby(),
                        new ArticleWorkbenchCss.aw_rt(),
                        new ArticleWorkbenchCss.aw_msg(),
                        new ArticleWorkbenchCss.aw_sheet(),
                        new ArticleWorkbenchCss.aw_row(),
                        new ArticleWorkbenchCss.aw_sq(),
                        new ArticleWorkbenchCss.aw_sq_zi(),
                        new ArticleWorkbenchCss.aw_sq_ann(),
                        new ArticleWorkbenchCss.aw_sq_punct(),
                        new ArticleWorkbenchCss.aw_sq_lead(),
                        new ArticleWorkbenchCss.aw_sq_bold(),
                        new ArticleWorkbenchCss.aw_sq_marker(),
                        new ArticleWorkbenchCss.aw_sq_loose(),
                        new ArticleWorkbenchCss.aw_sq_run(),
                        new ArticleWorkbenchCss.aw_sq_run_text(),
                        new ArticleWorkbenchCss.aw_sq_tag(),
                        new ArticleWorkbenchCss.aw_sq_cont(),
                        new ArticleWorkbenchCss.aw_sq_indent(),
                        new ArticleWorkbenchCss.aw_sq_pad(),
                        new ArticleWorkbenchCss.aw_findings(),
                        new ArticleWorkbenchCss.aw_finding(),
                        new ArticleWorkbenchCss.aw_error(),
                        new ArticleWorkbenchCss.aw_warn(),
                        new ArticleWorkbenchCss.aw_status()),
                        ArticleWorkbenchCss.INSTANCE),
                new ModuleImports<>(
                        List.of(new MdPreviewModule.createMdPreview()),
                        MdPreviewModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new MdSquaresModule.createMdSquares()),
                        MdSquaresModule.INSTANCE));
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
                "    // Two views on one draft. The document says what the file says;",
                "    // the squares say what a reader will do with it. Both come from",
                "    // the same response, so switching costs nothing.",
                "    var squaresView = false;",
                "    var viewBtn = branch.createElement('view', 'button');",
                "    viewBtn.type = 'button';",
                "    css.setClass(viewBtn, aw_btn);",
                "    head.appendChild(viewBtn);",
                "",
                "    // Twenty per row first, because that is the ruled paper a child",
                "    // writes on. The narrow ones are here to make runs collide with the",
                "    // end of a row, which is the case worth looking at.",
                "    var WIDTHS = [20, 16, 12, 10, 8, 6];",
                "    var width = 0;",
                "    var widthBtn = branch.createElement('width', 'button');",
                "    widthBtn.type = 'button';",
                "    css.setClass(widthBtn, aw_btn);",
                "    head.appendChild(widthBtn);",
                "",
                "    function labels() {",
                "        viewBtn.textContent = squaresView ? 'Show document' : 'Show squares';",
                "        widthBtn.textContent = WIDTHS[width] + '/row';",
                "    }",
                "    labels();",
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
                "    var preview = branch.createElement('preview', 'div');",
                "    css.setClass(preview, aw_preview);",
                "    main.appendChild(preview);",
                "",
                "    // The measure the document is read at, inside what scrolls it.",
                "    // Two elements because the findings must stay on screen: the",
                "    // document scrolls within its own half rather than pushing them",
                "    // off the bottom of the pane.",
                "    var page = branch.createElement('page', 'div');",
                "    css.setClass(page, aw_page);",
                "    preview.appendChild(page);",
                "",
                "    var findings = branch.createElement('findings', 'div');",
                "    css.setClass(findings, aw_findings);",
                "    main.appendChild(findings);",
                "",
                "    var owner = Object.freeze({ toString: function () { return 'drafts'; } });",
                "",
                "    // How a block becomes elements is not this pane's subject, and",
                "    // stage two will answer it a second way over the same blocks.",
                "    var doc = createMdPreview({",
                "        css: css,",
                "        classes: {",
                "            title: aw_title, h2: aw_h2, h3: aw_h3,",
                "            pin: aw_pin, unpinned: aw_unpinned,",
                "            p: aw_p, li: aw_li, marker: aw_marker,",
                "            quote: aw_quote, verse: aw_verse, vline: aw_vline,",
                "            run: aw_run, ruby: aw_ruby, rt: aw_rt, msg: aw_msg",
                "        }",
                "    });",
                "",
                "    var sheet = createMdSquares({",
                "        css: css,",
                "        classes: {",
                "            sheet: aw_sheet, row: aw_row, sq: aw_sq, zi: aw_sq_zi,",
                "            ann: aw_sq_ann, punct: aw_sq_punct, lead: aw_sq_lead,",
                "            bold: aw_sq_bold, marker: aw_sq_marker,",
                "            run: aw_sq_run, loose: aw_sq_loose,",
                "            runText: aw_sq_run_text, tag: aw_sq_tag, cont: aw_sq_cont,",
                "            indent: aw_sq_indent, pad: aw_sq_pad",
                "        }",
                "    });",
                "",
                "    // Nothing is cleared by hand. A branch owns what it made, so",
                "    // dissolving it is how the last draft leaves the screen - and it is",
                "    // the only way this pane can know nothing was left behind.",
                "    function fresh(name) {",
                "        if (branch.getBranch(name)) branch.dissolveBranch(name);",
                "        var b = branch.createBranch(name);",
                "        b.activate(owner);",
                "        return b;",
                "    }",
                "",
                "    // ── One draft ──────────────────────────────────────────────",
                "",
                "    var chosen = null;",
                "",
                "    function drawFindings(b, items, ok) {",
                "        if (!items || !items.length) {",
                "            if (ok) doc.say(b, findings, 'Nothing to report.');",
                "            return;",
                "        }",
                "        for (var i = 0; i < items.length; i++) {",
                "            var f = items[i];",
                "            var row = b.createElement('f' + i, 'div');",
                "            css.setClass(row, aw_finding);",
                "            var tag = b.createElement('ft' + i, 'span');",
                "            css.setClass(tag, f.severity === 'ERROR' ? aw_error : aw_warn);",
                "            tag.textContent = (f.severity === 'ERROR' ? 'error' : 'warning')",
                "                            + ' ' + f.line;",
                "            var msg = b.createElement('fm' + i, 'span');",
                "            msg.textContent = f.message;",
                "            row.appendChild(tag);",
                "            row.appendChild(msg);",
                "            findings.appendChild(row);",
                "        }",
                "    }",
                "",
                "    // The last response, so switching view is a redraw rather than a",
                "    // re-read. Changing the width is not: the arrangement is computed",
                "    // on the server, and asking the browser to guess it again here is",
                "    // exactly the duplication the plan exists to avoid.",
                "    var last = null;",
                "",
                "    function show(d) {",
                "        last = d;",
                "        var b = fresh('doc');",
                "        if (!d.ok) {",
                "            // An error means nothing rendered. Saying so beats an empty",
                "            // pane the author has to interpret.",
                "            doc.say(b, page, 'This draft did not render. See below.');",
                "        } else if (squaresView) {",
                "            sheet.draw(b, page, d.plan);",
                "        } else {",
                "            doc.draw(b, page, d.blocks);",
                "        }",
                "        drawFindings(b, d.findings, d.ok);",
                "    }",
                "",
                "    function note(text) { doc.say(fresh('doc'), page, text); }",
                "",
                "    viewBtn.addEventListener('click', function () {",
                "        squaresView = !squaresView;",
                "        labels();",
                "        if (last) show(last);",
                "    });",
                "",
                "    widthBtn.addEventListener('click', function () {",
                "        width = (width + 1) % WIDTHS.length;",
                "        labels();",
                "        if (chosen) open(chosen);",
                "    });",
                "",
                "    // Never from the browser's cache. This bench exists to be looked",
                "    // at again after a save, and a response served from cache is a",
                "    // Refresh button that does nothing - which is worse than none.",
                "    var FRESH = { cache: 'no-store' };",
                "",
                "    function open(id) {",
                "        chosen = id;",
                "        paintList();",
                "        note('Reading\\u2026');",
                "        fetch('/article-draft?id=' + encodeURIComponent(id)",
                "            + '&columns=' + WIDTHS[width], FRESH)",
                "            .then(function (r) { return r.json(); })",
                "            .then(show)",
                "            .catch(function (e) { note('Could not read it: ' + e); });",
                "    }",
                "",
                "    // ── The folder ─────────────────────────────────────────────",
                "",
                "    var drafts = [];",
                "",
                "    function paintList() {",
                "        var b = fresh('drafts');",
                "        if (!drafts.length) {",
                "            var none = b.createElement('none', 'div');",
                "            css.setClass(none, aw_status);",
                "            none.textContent = 'No .md files here yet.';",
                "            list.appendChild(none);",
                "            return;",
                "        }",
                "        for (var i = 0; i < drafts.length; i++) {",
                "            (function (d, n) {",
                "                var item = b.createElement('d' + n, 'div');",
                "                css.setClass(item, d.id === chosen ? aw_item_on : aw_item);",
                "                item.textContent = d.name;",
                "                var size = b.createElement('s' + n, 'span');",
                "                css.setClass(size, aw_size);",
                "                // Characters. Bytes would read 2.6x on Chinese.",
                "                size.textContent = d.chars + ' characters';",
                "                item.appendChild(size);",
                "                item.addEventListener('click', function () { open(d.id); });",
                "                list.appendChild(item);",
                "            })(drafts[i], i);",
                "        }",
                "    }",
                "",
                "    function load() {",
                "        fetch('/article-draft', FRESH)",
                "            .then(function (r) { return r.json(); })",
                "            .then(function (d) {",
                "                where.textContent = d.dir;",
                "                drafts = d.drafts || [];",
                "                paintList();",
                "                if (!drafts.length) { chosen = null; note('Nothing to show.'); return; }",
                "                // Re-read whatever is open. Refreshing the folder and",
                "                // leaving the document as it was is the one thing this",
                "                // must not do: the loop it serves is save, look, fix.",
                "                var here = drafts.some(function (x) { return x.id === chosen; });",
                "                open(here ? chosen : drafts[0].id);",
                "            })",
                "            .catch(function (e) { where.textContent = 'Could not list: ' + e; });",
                "    }",
                "",
                "    refresh.addEventListener('click', function () { load(); });",
                "    load();",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) { if (active) load(); }",
                "    };"
        );
    }
}
