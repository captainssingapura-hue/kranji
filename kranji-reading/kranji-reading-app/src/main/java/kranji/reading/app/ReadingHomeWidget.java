package kranji.reading.app;

import hue.captains.singapura.js.homing.core.Importable;
import hue.captains.singapura.js.homing.core.ModuleImports;
import hue.captains.singapura.js.homing.workspace.LifecycleHint;
import hue.captains.singapura.js.homing.workspace.WorkspaceWidget;
import kranji.reading.app.css.ReadingCss;

import java.util.List;

/**
 * Placeholder pane, standing in for the reader until rp5 lands.
 *
 * <p>Exists so the workspace has something to mount and the crate has
 * something to declare - the scaffold is provable end to end before any
 * reading logic is written.</p>
 *
 * <p>Deliberately carries no Chinese characters in its JS. Glyphs and phonics
 * reach the browser from the corpus over the wire, never inlined in a served
 * module; {@code NoInlineGlyphRule} enforces that.</p>
 */
public final class ReadingHomeWidget extends WorkspaceWidget<WorkspaceWidget._None, ReadingHomeWidget> {

    public static final ReadingHomeWidget INSTANCE = new ReadingHomeWidget();

    private ReadingHomeWidget() {}

    private record construct() implements WorkspaceWidget._Construct<_None, ReadingHomeWidget> {}

    @Override protected _Construct<_None, ReadingHomeWidget> construct() { return new construct(); }
    @Override public Class<_None> paramsType() { return _None.class; }
    @Override public String title() { return "Reading Home"; }
    @Override public LifecycleHint lifecycleHint() { return LifecycleHint.MULTI; }

    @Override
    protected List<ModuleImports<? extends Importable>> bodyImports() {
        return List.of(
                new ModuleImports<>(List.of(
                        new ReadingCss.kr_widget_root(),
                        new ReadingCss.kr_card(),
                        new ReadingCss.kr_badge(),
                        new ReadingCss.kr_title(),
                        new ReadingCss.kr_body()),
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
                "    badge.textContent = 'Scaffold';",
                "",
                "    var titleEl = branch.createElement('title', 'div');",
                "    css.setClass(titleEl, kr_title);",
                "    titleEl.textContent = 'Kranji Reading';",
                "",
                "    var body = branch.createElement('body', 'div');",
                "    css.setClass(body, kr_body);",
                "    body.textContent = 'The workspace is wired and the crate is gated. '",
                "        + 'The reader arrives in a later phase; this pane is here so the '",
                "        + 'scaffold can be proven end to end first.';",
                "",
                "    card.appendChild(badge);",
                "    card.appendChild(titleEl);",
                "    card.appendChild(body);",
                "    root.appendChild(card);",
                "",
                "    return {",
                "        root: root,",
                "        setActive: function (active) {},",
                "        partyDeregister: function () {}",
                "    };"
        );
    }
}
