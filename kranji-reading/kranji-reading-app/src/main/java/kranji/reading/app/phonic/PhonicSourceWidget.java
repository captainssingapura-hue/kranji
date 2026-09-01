package kranji.reading.app.phonic;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.grid.RelationGridModule;
import hue.captains.singapura.js.homing.grid.StockCellsModule;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;

import java.util.List;

/**
 * The phonic source data, one partition at a time, on a RelationGrid.
 *
 * <p>This is what extraction looks like when you can see it. The generator
 * that will emit the DSL reads exactly these rows, so anything wrong here is
 * wrong in the corpus before a line of it is generated — which is the whole
 * argument for making the step visible rather than trusting a test to have
 * asked the right question.</p>
 *
 * <h2>Partitioned, because 8,100 rows is not a view</h2>
 *
 * <p>A partition is a few hundred characters, which a grid renders without
 * ceremony and a person can actually read. Switching partitions re-imports;
 * nothing is held across the change.</p>
 *
 * <p>The {@code review} column is the review queue made visible: it is empty
 * for the several thousand characters that need no decision and carries the
 * conflict for the thirty-five that do.</p>
 *
 * <p>No CJK appears in this file. Characters arrive from the corpus.</p>
 */
public final class PhonicSourceWidget
        extends WorkspaceWidget<WorkspaceWidget._None, PhonicSourceWidget> {

    public static final PhonicSourceWidget INSTANCE = new PhonicSourceWidget();

    private PhonicSourceWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, PhonicSourceWidget> {}

    @Override protected _Construct<_None, PhonicSourceWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Phonic Source"; }
    @Override public LifecycleHint lifecycleHint() { return LifecycleHint.MULTI; }

    @Override
    protected List<ModuleImports<? extends Importable>> bodyImports() {
        return List.of(
                new ModuleImports<>(
                        List.of(new RelationGridModule.RelationGrid()),
                        RelationGridModule.INSTANCE),
                new ModuleImports<>(
                        List.of(new StockCellsModule.TextCell()),
                        StockCellsModule.INSTANCE),
                new ModuleImports<>(List.of(
                        new ReadingCss.kr_widget_root(),
                        new ReadingCss.kr_status(),
                        new ReadingCss.kr_bar(),
                        new ReadingCss.kr_btn(),
                        new ReadingCss.kr_grid_host()),
                        ReadingCss.INSTANCE));
    }

    @Override
    protected List<String> constructBodyJs() {
        return List.of(
                "    var root = branch.createElement('root', 'div');",
                "    css.setClass(root, kr_widget_root);",
                "",
                "    var bar = branch.createElement('bar', 'div');",
                "    css.setClass(bar, kr_bar);",
                "    root.appendChild(bar);",
                "",
                "    var status = branch.createElement('status', 'div');",
                "    css.setClass(status, kr_status);",
                "    root.appendChild(status);",
                "",
                "    var host = branch.createElement('host', 'div');",
                "    css.setClass(host, kr_grid_host);",
                "    root.appendChild(host);",
                "",
                "    var INITIALS = ['zero','b','p','m','f','d','t','n','l','g','k','h',",
                "                    'j','q','x','zh','ch','sh','r','z','c','s'];",
                "",
                "    var owner = Object.freeze({ toString: function(){ return 'phonicSource'; } });",
                "    var grid = null;",
                "    var seq = 0;",
                "",
                "    // The grid owns its cell elements, so its branch is dissolved and",
                "    // remade on every load - a branch registers names, and reusing one",
                "    // across loads would collide on the second partition.",
                "    function freshCellsBranch() {",
                "        if (branch.getBranch('cells')) branch.dissolveBranch('cells');",
                "        var b = branch.createBranch('cells');",
                "        b.activate(owner);",
                "        return b;",
                "    }",
                "",
                "    function relationOver(rows, columns) {",
                "        var byPk = {};",
                "        var order = [];",
                "        rows.forEach(function (r) { byPk[r.pk] = r; order.push(r.pk); });",
                "        var subs = [];",
                "        return {",
                "            pks:     function () { return order.slice(); },",
                "            columns: function () { return columns.slice(); },",
                "            get:     function (pk, col) {",
                "                         return byPk[pk] ? byPk[pk][col] : undefined; },",
                "            subscribe:   function (fn) { subs.push(fn); },",
                "            unsubscribe: function (fn) {",
                "                         var i = subs.indexOf(fn);",
                "                         if (i >= 0) subs.splice(i, 1); },",
                "            // Source data is read-only here. Editing it would mean editing",
                "            // Unihan; corrections belong in the override file instead.",
                "            update:     function () {},",
                "            deleteRows: function () {}",
                "        };",
                "    }",
                "",
                "    function load(segment) {",
                "        var mine = ++seq;",
                "        status.textContent = 'Loading ' + segment + '...';",
                "        import('/phonic-source?initial=' + encodeURIComponent(segment))",
                "            .then(function (mod) {",
                "                if (mine !== seq) return;   // a later click already won",
                "                render(segment, mod);",
                "            })",
                "            .catch(function (e) {",
                "                if (mine !== seq) return;",
                "                status.textContent = 'Could not load ' + segment + ': ' + e;",
                "            });",
                "    }",
                "",
                "    function render(segment, mod) {",
                "        if (grid) { grid.destroy(); grid = null; }",
                "        var rows = mod.rows || [];",
                "        if (rows.length === 0) {",
                "            status.textContent = mod.problem",
                "                ? 'Nothing here: ' + mod.problem",
                "                : 'No characters are filed under ' + segment + '.';",
                "            return;",
                "        }",
                "        var flagged = 0;",
                "        rows.forEach(function (r) { if (r.review) flagged++; });",
                "        status.textContent = mod.label + ' \\u00b7 ' + rows.length",
                "                           + ' characters \\u00b7 ' + flagged + ' to review';",
                "",
                "        grid = new RelationGrid({",
                "            container: host,",
                "            branch: freshCellsBranch(),",
                "            adapter: relationOver(rows, mod.columns),",
                "            label: 'Phonic source data for ' + mod.label,",
                "            cellFactory: function () { return new TextCell(); }",
                "        });",
                "    }",
                "",
                "    INITIALS.forEach(function (segment, i) {",
                "        var b = branch.createElement('i' + i, 'button');",
                "        css.setClass(b, kr_btn);",
                "        b.textContent = segment;",
                "        b.addEventListener('click', function () { load(segment); });",
                "        bar.appendChild(b);",
                "    });",
                "",
                "    load('h');   // the partition the seed corpus was hand-authored from",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) {},",
                "        // The grid holds DOM and listeners of its own, so it is destroyed",
                "        // explicitly rather than left to the branch teardown.",
                "        dispose: function () {",
                "            seq++;",
                "            if (grid) { grid.destroy(); grid = null; }",
                "        }",
                "    };");
    }
}
