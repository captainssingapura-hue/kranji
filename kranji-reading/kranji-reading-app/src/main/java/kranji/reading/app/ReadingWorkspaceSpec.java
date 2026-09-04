package kranji.reading.app;

import hue.captains.singapura.js.homing.studio.workspace.NavigatorSecretaryModule;
import hue.captains.singapura.js.homing.workspace.WidgetEntry;
import hue.captains.singapura.js.homing.workspace.WidgetGroup;
import hue.captains.singapura.js.homing.workspace.WidgetIcon;
import hue.captains.singapura.js.homing.workspace.WidgetLabel;
import hue.captains.singapura.js.homing.workspace.shell.PartyDecl;
import hue.captains.singapura.js.homing.workspace.shell.WorkspaceSpec;

import kranji.reading.app.phonic.PhonicSourceWidget;
import kranji.reading.app.read.ArticleCatalogueWidget;
import kranji.reading.app.read.ArticleReaderWidget;
import kranji.reading.app.known.KnownSecretaryModule;
import kranji.reading.app.known.KnownTransferWidget;
import kranji.reading.app.known.KnownZiWidget;
import kranji.reading.app.read.ArticleSelectionSecretaryModule;
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
        WidgetGroup known = WidgetGroup.of("Known");
        WidgetGroup source = WidgetGroup.of("Source");
        return List.of(
                WidgetEntry.of(ReadingHomeWidget.class, WidgetLabel.of("Reading Home"))
                        .withIcon(new WidgetIcon.Emoji("\uD83D\uDCD6"))   // 📖
                        .withGroup(read),
                WidgetEntry.of(ArticleCatalogueWidget.class, WidgetLabel.of("Library"))
                        .withIcon(new WidgetIcon.Emoji("\uD83D\uDCC7"))
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
                // Mark Known was here. It is now the Character pane above:
                // claiming a reading and looking at one were the same list with
                // one control's difference between them.
                WidgetEntry.of(KnownZiWidget.class, WidgetLabel.of("Known"))
                        .withIcon(new WidgetIcon.Emoji("\uD83C\uDFC5"))   // medal
                        .withGroup(known),
                // Last of the three, because it is the one nobody opens daily:
                // marking is the child's, reviewing is anyone's, and moving the
                // whole record in or out is a parent's occasional maintenance.
                WidgetEntry.of(KnownTransferWidget.class, WidgetLabel.of("Import / Export"))
                        .withIcon(new WidgetIcon.Emoji("\uD83D\uDCBE"))   // floppy disk
                        .withGroup(known),
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
                         .build(),
                // Which article to read. Separate from navigation because an
                // article is an identity, not a tree position, and separate
                // from ziSelection so the library can gain other producers -
                // a search result, a "continue reading" tile - without the
                // reader learning about any of them.
                PartyDecl.of("articleSelection", ArticleSelectionSecretaryModule.INSTANCE,
                             "ArticleSelectionSecretary")
                         .exposedAs("articleParty")
                         .build(),
                // What the reader already knows. Unlike the other three this
                // bus holds state rather than relaying a selection, and every
                // broadcast carries the whole set - a pane that mounted late
                // is correct on the next change rather than needing a resync.
                PartyDecl.of("knownSet", KnownSecretaryModule.INSTANCE, "KnownSecretary")
                         .exposedAs("knownParty")
                         .build()
        );
    }
}
