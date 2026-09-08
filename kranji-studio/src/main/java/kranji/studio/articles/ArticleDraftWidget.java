package kranji.studio.articles;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;

import java.util.List;

/**
 * The article workbench: a folder of drafts, and what the subset makes of one.
 *
 * <h2>It gave up its list, as it said it would</h2>
 *
 * <p>This pane used to hold the folder on its left, and the note here used to
 * say that splitting them would cost a party, a secretary and a message type
 * before the bench could show anything at all. That turned out to be the right
 * price rather than too high a one: the folder is
 * {@link ArticleNavigatorWidget} now, and what bought the split is that two
 * navigators can follow two different roots while this pane shows whichever
 * draft was picked last.</p>
 *
 * <p>So it keeps its right-hand side and knows nothing about folders. It is
 * told a root and a draft on the shelf bus — both, because a draft id is
 * relative to its root — and reads that one file.</p>
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
 * The document lives on a {@code doc} branch and is replaced by dissolving the
 * old one — which is the only way this pane can know that what left the screen
 * also left the tree. It is worth naming what this replaced: the first version
 * assigned {@code innerHTML} and built the rest with
 * {@code document.createElement}, so nothing it put on screen was owned by
 * anything.</p>
 *
 * <h2>Nothing is cached</h2>
 *
 * <p>Refresh re-reads the open draft. The loop this exists for is save, look,
 * fix, and a bench that showed the file you saved a minute ago would be worse
 * than no bench.</p>
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
                        new ArticleWorkbenchCss.aw_sq_pack(),
                        new ArticleWorkbenchCss.aw_sq_half(),
                        new ArticleWorkbenchCss.aw_sq_hang(),
                        new ArticleWorkbenchCss.aw_sq_bold(),
                        new ArticleWorkbenchCss.aw_sq_marker(),
                        new ArticleWorkbenchCss.aw_sq_run(),
                        new ArticleWorkbenchCss.aw_sq_run_text(),
                        new ArticleWorkbenchCss.aw_sq_tag(),
                        new ArticleWorkbenchCss.aw_sq_cont(),
                        new ArticleWorkbenchCss.aw_sq_indent(),
                        new ArticleWorkbenchCss.aw_sq_pad(),
                        new ArticleWorkbenchCss.aw_tree(),
                        new ArticleWorkbenchCss.aw_node(),
                        new ArticleWorkbenchCss.aw_d0(),
                        new ArticleWorkbenchCss.aw_d1(),
                        new ArticleWorkbenchCss.aw_d2(),
                        new ArticleWorkbenchCss.aw_d3(),
                        new ArticleWorkbenchCss.aw_path(),
                        new ArticleWorkbenchCss.aw_name(),
                        new ArticleWorkbenchCss.aw_part(),
                        new ArticleWorkbenchCss.aw_id(),
                        new ArticleWorkbenchCss.aw_bar(),
                        new ArticleWorkbenchCss.aw_fill(),
                        new ArticleWorkbenchCss.aw_w0(),
                        new ArticleWorkbenchCss.aw_w1(),
                        new ArticleWorkbenchCss.aw_w2(),
                        new ArticleWorkbenchCss.aw_w3(),
                        new ArticleWorkbenchCss.aw_w4(),
                        new ArticleWorkbenchCss.aw_chars(),
                        new ArticleWorkbenchCss.aw_over(),
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
                        MdSquaresModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new MdSegmentsModule.createMdSegments()),
                        MdSegmentsModule.INSTANCE));
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
                "    // Three views on one draft. The document says what the file says;",
                "    // the squares say what a reader will do with a page of it; the tree",
                "    // says how many pages there are. All three come from the same",
                "    // response, so switching costs nothing.",
                "    var VIEWS = ['document', 'squares', 'segments'];",
                "    var view = 0;",
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
                "        viewBtn.textContent = 'Show ' + VIEWS[(view + 1) % VIEWS.length];",
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
                "            ann: aw_sq_ann, punct: aw_sq_punct,",
                "            pack: aw_sq_pack, half: aw_sq_half, hang: aw_sq_hang,",
                "            bold: aw_sq_bold, marker: aw_sq_marker,",
                "            run: aw_sq_run,",
                "            runText: aw_sq_run_text, tag: aw_sq_tag, cont: aw_sq_cont,",
                "            indent: aw_sq_indent, pad: aw_sq_pad",
                "        }",
                "    });",
                "",
                "    // The tree the document should become. Not a table of contents -",
                "    // it shows what the cut produced, which is the author's question.",
                "    var tree = createMdSegments({",
                "        css: css,",
                "        budget: " + Segments.BUDGET + ",",
                "        classes: {",
                "            tree: aw_tree, node: aw_node,",
                "            depth: [aw_d0, aw_d1, aw_d2, aw_d3],",
                "            path: aw_path, name: aw_name, part: aw_part, id: aw_id,",
                "            unpinned: aw_unpinned,",
                "            bar: aw_bar, fill: aw_fill,",
                "            step: [aw_w0, aw_w1, aw_w2, aw_w3, aw_w4],",
                "            size: aw_chars, over: aw_over",
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
                "        } else if (VIEWS[view] === 'squares') {",
                "            sheet.draw(b, page, d.plan);",
                "        } else if (VIEWS[view] === 'segments') {",
                "            tree.draw(b, page, d.tree);",
                "        } else {",
                "            doc.draw(b, page, d.blocks);",
                "        }",
                "        drawFindings(b, d.findings, d.ok);",
                "    }",
                "",
                "    function note(text) { doc.say(fresh('doc'), page, text); }",
                "",
                "    viewBtn.addEventListener('click', function () {",
                "        view = (view + 1) % VIEWS.length;",
                "        labels();",
                "        if (last) show(last);",
                "    });",
                "",
                "    widthBtn.addEventListener('click', function () {",
                "        width = (width + 1) % WIDTHS.length;",
                "        labels();",
                "        if (chosen) load();",
                "    });",
                "",
                "    // Never from the browser's cache. This bench exists to be looked",
                "    // at again after a save, and a response served from cache is a",
                "    // Refresh button that does nothing - which is worse than none.",
                "    var FRESH = { cache: 'no-store' };",
                "",
                "    // Which draft, and under which root. Both, because a draft id is",
                "    // relative to its root: the same id in another folder is either",
                "    // nothing or - far worse - a different file at the same path.",
                "    var chosenRoot = '';",
                "",
                "    function open(rootId, id, label) {",
                "        chosen = id;",
                "        chosenRoot = rootId || '';",
                "        if (!id) { where.textContent = ''; note('Nothing chosen.'); return; }",
                "        where.textContent = label || '';",
                "        note('Reading\\u2026');",
                "        fetch('/article-draft?root=' + encodeURIComponent(chosenRoot)",
                "            + '&id=' + encodeURIComponent(id)",
                "            + '&columns=' + WIDTHS[width], FRESH)",
                "            .then(function (r) { return r.json(); })",
                "            .then(show)",
                "            .catch(function (e) { note('Could not read it: ' + e); });",
                "    }",
                "",
                "    /** Re-read what is open. This is the whole of Refresh now. */",
                "    function load() {",
                "        if (!chosen) { note('Choose a draft in the navigator.'); return; }",
                "        open(chosenRoot, chosen, where.textContent);",
                "    }",
                "",
                "    refresh.addEventListener('click', function () { load(); });",
                "",
                "    // ── The bus ────────────────────────────────────────────────",
                "",
                "    // The tree that used to sit on the left is its own pane now, which",
                "    // is what lets two of them follow two different roots. What this",
                "    // gave up is knowing anything about the folder; what it gained is",
                "    // that it no longer has to.",
                "    var __party = (workspaceCtx && workspaceCtx.articleShelf)",
                "                ? workspaceCtx.articleShelf : null;",
                "    var __actorId = null;",
                "",
                "    if (__party) {",
                "        __actorId = 'article-draft-'",
                "                  + Math.random().toString(36).slice(2, 8);",
                "        __party.joinActor({",
                "            id: __actorId,",
                "            parentSecretary: 'articleShelf',",
                "            reactors: {",
                "                ShelfChanged: function (msg) {",
                "                    // A root changing clears the draft, so this arrives as",
                "                    // an empty id and the pane says so rather than keeping",
                "                    // a document from a folder nobody is looking at.",
                "                    open(msg.root, msg.draft, msg.name);",
                "                }",
                "            }",
                "        });",
                "        // Ask, rather than wait for the next change. A pane opened after",
                "        // a draft was already chosen would otherwise sit empty until",
                "        // somebody clicked something they had already clicked.",
                "        __party.tellFrom(__actorId, { kind: 'WhatIsChosen' });",
                "    }",
                "",
                "    if (!chosen) note('Choose a draft in the navigator.');",
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
