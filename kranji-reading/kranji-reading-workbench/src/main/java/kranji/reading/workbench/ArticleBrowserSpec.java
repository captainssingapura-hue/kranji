package kranji.reading.workbench;

import hue.captains.singapura.js.homing.workspace.WidgetEntry;
import hue.captains.singapura.js.homing.workspace.WidgetGroup;
import hue.captains.singapura.js.homing.workspace.WidgetIcon;
import hue.captains.singapura.js.homing.workspace.WidgetLabel;
import hue.captains.singapura.js.homing.workspace.shell.PartyDecl;
import hue.captains.singapura.js.homing.workspace.shell.WorkspaceSpec;
import kranji.reading.app.known.KnownSecretaryModule;
import kranji.reading.app.read.ArticleCatalogueWidget;
import kranji.reading.app.read.ArticleReaderWidget;
import kranji.reading.app.read.ArticleSelectionSecretaryModule;
import kranji.reading.app.zi.ZiSelectionSecretaryModule;

import java.util.List;

/**
 * The library, browsed the way a reader browses it.
 *
 * <h2>The same widgets, on purpose</h2>
 *
 * <p>Not a copy of the reader's browser and not a simplified one: the
 * {@link ArticleCatalogueWidget} and {@link ArticleReaderWidget} classes
 * themselves, on the same routes. An author checking how an article sits on
 * the page is asking what a child will see, and the only honest answer is the
 * thing the child sees. A workbench that rendered squares its own way would
 * be a second opinion about a fact.</p>
 *
 * <h2>Two widgets, four parties</h2>
 *
 * <p>The article-selection bus is what joins the two. The other three are
 * declared though nothing here speaks on them yet, because the reader speaks
 * on them - it announces the square under the cursor and it reads and marks
 * the known set - and a widget that finds its bus missing behaves differently
 * from one that finds it empty. Declaring them keeps the reader's behaviour
 * here identical to its behaviour at home, and makes the Character pane a
 * one-line addition when it is wanted.</p>
 */
public final class ArticleBrowserSpec implements WorkspaceSpec {

    public static final String KIND = "article-browser";

    public static final ArticleBrowserSpec INSTANCE = new ArticleBrowserSpec();

    private ArticleBrowserSpec() {}

    @Override public String kind()  { return KIND; }
    @Override public String title() { return "Article Browser"; }

    @Override
    public List<WidgetEntry> widgetEntries() {
        WidgetGroup read = WidgetGroup.of("Read");
        return List.of(
                WidgetEntry.of(ArticleCatalogueWidget.class, WidgetLabel.of("Library"))
                        .withIcon(new WidgetIcon.Emoji("📇"))
                        .withGroup(read),
                WidgetEntry.of(ArticleReaderWidget.class, WidgetLabel.of("Reader"))
                        .withIcon(new WidgetIcon.Emoji("📚"))
                        .withGroup(read));
    }

    @Override
    public List<PartyDecl> parties() {
        return List.of(
                PartyDecl.of("articleSelection", ArticleSelectionSecretaryModule.INSTANCE,
                             "ArticleSelectionSecretary")
                         .exposedAs("articleParty")
                         .build(),
                PartyDecl.of("ziSelection", ZiSelectionSecretaryModule.INSTANCE,
                             "ZiSelectionSecretary")
                         .exposedAs("ziParty")
                         .build(),
                PartyDecl.of("knownSet", KnownSecretaryModule.INSTANCE, "KnownSecretary")
                         .exposedAs("knownParty")
                         .build());
    }
}
