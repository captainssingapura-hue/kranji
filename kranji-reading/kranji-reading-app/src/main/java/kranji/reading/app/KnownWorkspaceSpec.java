package kranji.reading.app;

import hue.captains.singapura.js.homing.studio.workspace.NavigatorSecretaryModule;
import hue.captains.singapura.js.homing.workspace.WidgetEntry;
import hue.captains.singapura.js.homing.workspace.WidgetGroup;
import hue.captains.singapura.js.homing.workspace.WidgetIcon;
import hue.captains.singapura.js.homing.workspace.WidgetLabel;
import hue.captains.singapura.js.homing.workspace.shell.PartyDecl;
import hue.captains.singapura.js.homing.workspace.shell.WorkspaceSpec;
import kranji.reading.app.known.KnownSecretaryModule;
import kranji.reading.app.known.KnownProgressWidget;
import kranji.reading.app.known.KnownSoundsWidget;
import kranji.reading.app.known.KnownTransferWidget;
import kranji.reading.app.known.KnownZiWidget;
import kranji.reading.app.zi.SyllableDetailWidget;
import kranji.reading.app.zi.SyllableTreeWidget;
import kranji.reading.app.zi.ZiCharactersWidget;
import kranji.reading.app.zi.ZiDetailWidget;
import kranji.reading.app.zi.ZiSelectionSecretaryModule;

import java.util.List;

/**
 * A workspace for the record itself — what is known, and what is next.
 *
 * <h2>Why it is not a corner of the reading workspace</h2>
 *
 * <p>Every pane here already exists there, and that is the problem it is
 * separating from. The reading workspace is arranged around a text: the reader
 * is the middle of it, and the known set appears at the edges as something that
 * happens <em>while</em> reading — a reading claimed because it came up, a
 * tally glanced at. That is the right arrangement for reading and the wrong one
 * for the half-hour somebody spends deciding what to work on next, when there
 * is no text on screen at all and the record is the subject rather than the
 * margin.</p>
 *
 * <p>Nothing is duplicated to achieve it. A widget is a class and a workspace
 * is an arrangement of classes, so the Character pane here is the same pane —
 * a claim made in one workspace is on the device and in every other.</p>
 *
 * <h2>The sounds index is the spine</h2>
 *
 * <p>Two ways in, and they answer different questions. <b>Sounds</b> is the
 * corpus tree, for when you know which sound you want. <b>Sounds known</b> is
 * the same 1,288 syllables flat and weighed against the record, for when the
 * question is where to go at all — it is the only pane in the app that can show
 * the <em>shape</em> of what is left, because a list of claims cannot describe
 * a gap.</p>
 *
 * <p>Both publish a sound on the navigation party, so Characters and Character
 * follow either of them and neither knows the other exists. Then Character is
 * where a claim is made, which is where it is made everywhere else — the pane
 * beside the character's other readings, so what a claim excludes is visible
 * while making it.</p>
 *
 * <p>Import / Export is here rather than in reading because moving the whole
 * record is maintenance, and this is the workspace maintenance belongs in.</p>
 *
 * <h2>No article party</h2>
 *
 * <p>Three buses, not four. Nothing here reads an article, so declaring the
 * article bus would advertise a producer that never speaks and a consumer that
 * never arrives.</p>
 */
public final class KnownWorkspaceSpec implements WorkspaceSpec {

    /** Workspace kind — also the {@code ws_kind} query parameter. */
    public static final String KIND = "known";

    public static final KnownWorkspaceSpec INSTANCE = new KnownWorkspaceSpec();

    private KnownWorkspaceSpec() {}

    @Override public String kind()  { return KIND; }
    @Override public String title() { return "Known Characters"; }

    @Override
    public List<WidgetEntry> widgetEntries() {
        WidgetGroup index = WidgetGroup.of("Index");
        WidgetGroup character = WidgetGroup.of("Character");
        WidgetGroup record = WidgetGroup.of("Record");
        return List.of(
                // First, because it is the pane this workspace exists for.
                WidgetEntry.of(KnownSoundsWidget.class, WidgetLabel.of("Sounds known"))
                        .withIcon(new WidgetIcon.Emoji("📈"))   // chart
                        .withGroup(index),
                WidgetEntry.of(SyllableTreeWidget.class, WidgetLabel.of("Sounds"))
                        .withIcon(new WidgetIcon.Emoji("🎵"))
                        .withGroup(index),
                WidgetEntry.of(SyllableDetailWidget.class, WidgetLabel.of("Syllable"))
                        .withIcon(new WidgetIcon.Emoji("🔍"))
                        .withGroup(index),
                WidgetEntry.of(ZiCharactersWidget.class, WidgetLabel.of("Characters"))
                        .withIcon(new WidgetIcon.Emoji("字"))
                        .withGroup(character),
                WidgetEntry.of(ZiDetailWidget.class, WidgetLabel.of("Character"))
                        .withIcon(new WidgetIcon.Emoji("🔎"))
                        .withGroup(character),
                // First in the group, and the pane somebody opens for its own
                // sake. The rest of the record answers "what is on it"; this
                // one answers "how am I doing", which is the question that
                // brings anybody back.
                WidgetEntry.of(KnownProgressWidget.class, WidgetLabel.of("Progress"))
                        .withIcon(new WidgetIcon.Emoji("🌱"))
                        .withGroup(record),
                WidgetEntry.of(KnownZiWidget.class, WidgetLabel.of("Known"))
                        .withIcon(new WidgetIcon.Emoji("🏅"))
                        .withGroup(record),
                WidgetEntry.of(KnownTransferWidget.class, WidgetLabel.of("Import / Export"))
                        .withIcon(new WidgetIcon.Emoji("💾"))
                        .withGroup(record));
    }

    /**
     * The same three buses the reading workspace declares, under the same
     * exposed names.
     *
     * <p>Load-bearing rather than tidy: every pane here reads {@code navParty},
     * {@code ziParty} or {@code knownParty} off the workspace context by name,
     * so a workspace that called them anything else would mount widgets that
     * join nothing and silently never scope.</p>
     */
    @Override
    public List<PartyDecl> parties() {
        return List.of(
                PartyDecl.of("navigation", NavigatorSecretaryModule.INSTANCE, "NavigatorSecretary")
                         .exposedAs("navParty")
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
