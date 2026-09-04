package kranji.studio;

import hue.captains.singapura.js.homing.workspace.WidgetEntry;
import hue.captains.singapura.js.homing.workspace.WidgetGroup;
import hue.captains.singapura.js.homing.workspace.WidgetIcon;
import hue.captains.singapura.js.homing.workspace.WidgetLabel;
import hue.captains.singapura.js.homing.workspace.shell.PartyDecl;
import hue.captains.singapura.js.homing.workspace.shell.WorkspaceSpec;
import kranji.studio.gloss.CuratedEntityWidget;
import kranji.studio.gloss.GlossSelectionSecretaryModule;
import kranji.studio.gloss.IssueEntityWidget;
import kranji.studio.gloss.PartitionEntityWidget;

import java.util.List;

/**
 * A bench for writing the curated set, one partition at a time.
 *
 * <p>Separate from the Gloss Workbench because the question is different.
 * That one walks the model — a sound, then a pair, then its senses — and
 * answers things about a character. This one starts from a <b>file</b>: pick
 * p041 and see what the seeder could not settle in it beside what somebody has
 * written, because the curated set is now cut into the same 101 partitions the
 * seed is, and a sitting is one of them.</p>
 *
 * <h2>Three widgets and a short chain</h2>
 *
 * <p>Partitions is the root; Issues and Curated both hang off it and neither
 * hangs off the other. They are two views of one partition rather than two
 * steps of a walk — the left says what is missing, the right says what is
 * there, and reading them together is the work.</p>
 *
 * <p>Curated is read-only in this first stage. Seeing the guess and the
 * writing side by side is worth having before editing either, and an editor
 * that arrives before the view has nothing to prove it is editing the right
 * row.</p>
 */
public final class CuratedWorkspaceSpec implements WorkspaceSpec {

    public static final String KIND = "curated";

    public static final CuratedWorkspaceSpec INSTANCE = new CuratedWorkspaceSpec();

    private CuratedWorkspaceSpec() {}

    @Override public String kind()  { return KIND; }
    @Override public String title() { return "Curated Workbench"; }

    @Override
    public List<WidgetEntry> widgetEntries() {
        WidgetGroup partition = WidgetGroup.of("By partition");
        return List.of(
                WidgetEntry.of(PartitionEntityWidget.class, WidgetLabel.of("Partitions"))
                        .withIcon(new WidgetIcon.Emoji("🗂"))
                        .withGroup(partition),
                WidgetEntry.of(IssueEntityWidget.class, WidgetLabel.of("Issues"))
                        .withIcon(new WidgetIcon.Emoji("🔎"))
                        .withGroup(partition),
                WidgetEntry.of(CuratedEntityWidget.class, WidgetLabel.of("Curated"))
                        .withIcon(new WidgetIcon.Emoji("✍"))
                        .withGroup(partition));
    }

    /**
     * The same bus, the same secretary, the same exposed name.
     *
     * <p>{@code GlossEntityWidget} reads {@code glossParty} from the workspace
     * context, so a second workbench that named its party anything else would
     * get widgets that join nothing and silently never scope. The selection
     * secretary is about relations, not about which bench is open.</p>
     */
    @Override
    public List<PartyDecl> parties() {
        return List.of(
                PartyDecl.of("glossSelection", GlossSelectionSecretaryModule.INSTANCE,
                             "GlossSelectionSecretary")
                         .exposedAs("glossParty")
                         .build());
    }
}
