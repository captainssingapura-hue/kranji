package kranji.reading.app;

import hue.captains.singapura.js.homing.studio.workspace.NavigatorSecretaryModule;
import hue.captains.singapura.js.homing.workspace.WidgetEntry;
import hue.captains.singapura.js.homing.workspace.WidgetGroup;
import hue.captains.singapura.js.homing.workspace.WidgetIcon;
import hue.captains.singapura.js.homing.workspace.WidgetLabel;
import hue.captains.singapura.js.homing.workspace.shell.PartyDecl;
import hue.captains.singapura.js.homing.workspace.shell.WorkspaceSpec;

import kranji.reading.app.phonic.PhonicSourceWidget;
import kranji.reading.app.read.ArticleReaderWidget;
import kranji.reading.app.zi.SyllableDetailWidget;
import kranji.reading.app.zi.SyllableTreeWidget;
import kranji.reading.app.zi.ZiCharactersWidget;
import kranji.reading.app.zi.ZiDetailWidget;
import kranji.reading.app.zi.ZiSelectionSecretaryModule;

import java.util.List;

/**
 * The reading workspace.
 *
 * <p>Widgets arrive over the phases: the reader first, then the character
 * panel, the known-set manager, and practice. The party bus carries the
 * selection so that marking a character known in one pane is reflected in
 * every other.</p>
 */
public final class ReadingWorkspaceSpec implements WorkspaceSpec {

    /** Workspace kind — also the {@code ws_kind} query parameter. */
    public static final String KIND = "reading";

    public static final ReadingWorkspaceSpec INSTANCE = new ReadingWorkspaceSpec();

    private ReadingWorkspaceSpec() {}

    @Override public String kind()  { return KIND; }
    @Override public String title() { return "Reading"; }

    @Override
    public List<WidgetEntry> widgetEntries() {
        WidgetGroup read = WidgetGroup.of("Read");
        WidgetGroup sounds = WidgetGroup.of("Sounds");
        WidgetGroup source = WidgetGroup.of("Source");
        return List.of(
                WidgetEntry.of(ReadingHomeWidget.class, WidgetLabel.of("Reading Home"))
                        .withIcon(new WidgetIcon.Emoji("\uD83D\uDCD6"))   // 📖
                        .withGroup(read),
                WidgetEntry.of(ArticleReaderWidget.class, WidgetLabel.of("Reader"))
                        .withIcon(new WidgetIcon.Emoji("\uD83D\uDCDA"))
                        .withGroup(read),
                WidgetEntry.of(SyllableTreeWidget.class, WidgetLabel.of("Sounds"))
                        .withIcon(new WidgetIcon.Emoji("\uD83C\uDFB5"))
                        .withGroup(sounds),
                WidgetEntry.of(SyllableDetailWidget.class, WidgetLabel.of("Syllable"))
                        .withIcon(new WidgetIcon.Emoji("\uD83D\uDD0D"))
                        .withGroup(sounds),
                WidgetEntry.of(ZiCharactersWidget.class, WidgetLabel.of("Characters"))
                        .withIcon(new WidgetIcon.Emoji("\u5B57"))
                        .withGroup(sounds),
                WidgetEntry.of(ZiDetailWidget.class, WidgetLabel.of("Character"))
                        .withIcon(new WidgetIcon.Emoji("\uD83D\uDD0E"))
                        .withGroup(sounds),
                WidgetEntry.of(PhonicSourceWidget.class, WidgetLabel.of("Phonic Source"))
                        .withIcon(new WidgetIcon.Emoji("\uD83D\uDCCA"))   // bar chart
                        .withGroup(source)
        );
    }

    @Override
    public List<PartyDecl> parties() {
        return List.of(
                PartyDecl.of("navigation", NavigatorSecretaryModule.INSTANCE, "NavigatorSecretary")
                         .exposedAs("navParty")
                         .build(),
                PartyDecl.of("ziSelection", ZiSelectionSecretaryModule.INSTANCE, "ZiSelectionSecretary")
                         .exposedAs("ziParty")
                         .build()
        );
    }
}
