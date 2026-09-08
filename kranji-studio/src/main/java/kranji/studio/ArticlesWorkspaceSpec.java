package kranji.studio;

import hue.captains.singapura.js.homing.workspace.WidgetEntry;
import hue.captains.singapura.js.homing.workspace.WidgetIcon;
import hue.captains.singapura.js.homing.workspace.WidgetLabel;
import hue.captains.singapura.js.homing.workspace.shell.PartyDecl;
import hue.captains.singapura.js.homing.workspace.shell.WorkspaceSpec;
import kranji.studio.articles.ArticleDraftWidget;
import kranji.studio.articles.SectionReaderWidget;

import java.util.List;

/**
 * The article workbench: drafts on disk, read through the subset.
 *
 * <p>One widget so far, and it is {@code MULTI} — which is the arrangement this
 * exists for. An author comparing two drafts, or one draft against the guide
 * they are following, opens the pane twice; the useful workspace is the second
 * copy, not the first.</p>
 *
 * <p>No parties. Nothing here has a second widget to tell anything to yet, and
 * a selection bus with one participant is a bus with nobody on it. The library
 * view arrives from the reader in stage two and brings the first real
 * conversation with it.</p>
 */
public final class ArticlesWorkspaceSpec implements WorkspaceSpec {

    public static final String KIND = "articles";

    public static final ArticlesWorkspaceSpec INSTANCE = new ArticlesWorkspaceSpec();

    private ArticlesWorkspaceSpec() {}

    @Override public String kind()  { return KIND; }
    @Override public String title() { return "Article Workbench"; }

    @Override
    public List<WidgetEntry> widgetEntries() {
        return List.of(
                WidgetEntry.of(ArticleDraftWidget.class, WidgetLabel.of("Drafts"))
                        .withIcon(new WidgetIcon.Emoji("📝")),
                WidgetEntry.of(SectionReaderWidget.class, WidgetLabel.of("Reading"))
                        .withIcon(new WidgetIcon.Emoji("📖")));
    }

    @Override
    public List<PartyDecl> parties() {
        return List.of();
    }
}
