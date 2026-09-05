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
import kranji.reading.app.known.KnownProgressWidget;
import kranji.reading.app.known.KnownCommandSecretaryModule;
import kranji.reading.app.known.KnownEventSecretaryModule;
import kranji.reading.app.known.KnownServiceWidget;
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
                //
                // First of the three, because it is the one that gets opened
                // during ordinary reading: a child who has just claimed a
                // reading wants to see where that puts them, not a list of
                // everything they have ever claimed. The same class as in the
                // Known workspace - a claim made while reading shows up there
                // and here, because both are reading one device.
                WidgetEntry.of(KnownProgressWidget.class, WidgetLabel.of("Progress"))
                        .withIcon(new WidgetIcon.Emoji("\uD83C\uDF31"))   // seedling
                        .withGroup(known),
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
                        .withGroup(source),
                // The record service. Ordinary picker entry for now - see the
                // note below on why it is not pinned.
                WidgetEntry.of(KnownServiceWidget.class, WidgetLabel.of("Record"))
                        .withIcon(new WidgetIcon.Emoji("\uD83D\uDDC4"))   // file cabinet
                        .withGroup(known)
        );
    }

    /**
     * The record service, started at boot and impossible to close.
     *
     * <p>It owns the set, the device and the failure, so it cannot be something
     * a reader opts into. A workspace where marking silently fails to save
     * because nobody opened the right pane is the exact fault this arrangement
     * removes, and leaving the owner in the picker would have preserved it in a
     * politer form.</p>
     *
     * <p>Pinning is the shell's way of saying <em>always there, not in the
     * picker, no close button</em>. It costs one tab, which the pane earns back
     * by being where a failed save is announced.</p>
     */
    // pinnedSpawns() is deliberately NOT overridden.
    //
    // It is the right mechanism and it does not work in this shell: the
    // chrome seeds pinned widgets only from its onEmpty hook, and opening a
    // workspace records SessionStarted before replay runs, so the log is
    // never empty and the hook never fires. Naming the service there removed
    // it from the picker as well, leaving it spawned by nothing and openable
    // by nobody - an owner that does not exist is worse than one a reader has
    // to open. Until the shell can start it, it is an ordinary SINGLETON entry.

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
                // What the reader already knows. A relay like the other three:
                // KnownService owns the set, the device and the failure, and
                // this only carries the words between the panes and it.
                PartyDecl.of("knownCommands", KnownCommandSecretaryModule.INSTANCE,
                             "KnownCommandSecretary")
                         .exposedAs("knownCommandParty")
                         .build(),
                PartyDecl.of("knownEvents", KnownEventSecretaryModule.INSTANCE,
                             "KnownEventSecretary")
                         .exposedAs("knownEventParty")
                         .build()
        );
    }
}
