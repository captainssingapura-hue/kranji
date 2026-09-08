package kranji.studio;

import hue.captains.singapura.js.homing.workspace.WidgetEntry;
import hue.captains.singapura.js.homing.workspace.WidgetIcon;
import hue.captains.singapura.js.homing.workspace.WidgetLabel;
import hue.captains.singapura.js.homing.workspace.shell.PartyDecl;
import hue.captains.singapura.js.homing.workspace.shell.WorkspaceSpec;
import kranji.studio.articles.ArticleDraftWidget;
import kranji.studio.articles.ArticleNavigatorWidget;
import kranji.studio.articles.ArticleRootsWidget;
import kranji.studio.articles.ArticleShelfSecretaryModule;
import kranji.studio.articles.SectionReaderWidget;

import java.util.List;

/**
 * The article workbench: drafts on disk, read through the subset.
 *
 * <h2>Three panes and one chain</h2>
 *
 * <p><b>Roots</b> is which folders may be read; <b>Navigator</b> is one of
 * those folders as the tree it already is; <b>Drafts</b> is one file in it,
 * through the subset. Each pane answers one question and passes the answer
 * along, which is why none of them has to know what the others are showing.</p>
 *
 * <p>Roots is {@code SINGLETON} — a shelf is a fact about the studio and two
 * copies of it would be two lists of the same thing. The other two are
 * {@code MULTI}, which is the arrangement they exist for: two navigators on two
 * roots, or one draft beside the guide it is being written against.</p>
 *
 * <h2>The party this note said was coming</h2>
 *
 * <p>It used to say there were no parties, because a bus with one participant
 * is a bus with nobody on it. There are three now, so there is one.</p>
 */
public final class ArticlesWorkspaceSpec implements WorkspaceSpec {

    public static final String KIND = "articles";

    public static final ArticlesWorkspaceSpec INSTANCE = new ArticlesWorkspaceSpec();

    private ArticlesWorkspaceSpec() {}

    @Override public String kind()  { return KIND; }
    @Override public String title() { return "Article Workbench"; }

    @Override
    public List<WidgetEntry> widgetEntries() {
        // Listed in the order the chain runs, because that is the order
        // somebody opens them in the first time.
        return List.of(
                WidgetEntry.of(ArticleRootsWidget.class, WidgetLabel.of("Roots"))
                        .withIcon(new WidgetIcon.Emoji("🗂")),
                WidgetEntry.of(ArticleNavigatorWidget.class, WidgetLabel.of("Navigator"))
                        .withIcon(new WidgetIcon.Emoji("🌲")),
                WidgetEntry.of(ArticleDraftWidget.class, WidgetLabel.of("Drafts"))
                        .withIcon(new WidgetIcon.Emoji("📝")),
                WidgetEntry.of(SectionReaderWidget.class, WidgetLabel.of("Reading"))
                        .withIcon(new WidgetIcon.Emoji("📖")));
    }

    /**
     * The shelf bus.
     *
     * <p>One party for the whole chain rather than one per hop. Two would mean
     * two declarations and two joins per widget, and a widget that forgot one
     * would go quiet with nothing to show for it — the same argument the gloss
     * workbench makes next door.</p>
     */
    @Override
    public List<PartyDecl> parties() {
        return List.of(
                PartyDecl.of("articleShelf", ArticleShelfSecretaryModule.INSTANCE,
                             "ArticleShelfSecretary")
                         .exposedAs("articleShelf")
                         .build());
    }
}
