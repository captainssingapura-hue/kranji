package kranji.reading.workbench;

import hue.captains.singapura.js.homing.workspace.WidgetEntry;
import hue.captains.singapura.js.homing.workspace.WidgetGroup;
import hue.captains.singapura.js.homing.workspace.WidgetIcon;
import hue.captains.singapura.js.homing.workspace.WidgetLabel;
import hue.captains.singapura.js.homing.workspace.shell.PartyDecl;
import hue.captains.singapura.js.homing.workspace.shell.WorkspaceSpec;
import kranji.reading.workbench.coverage.CoverageArticleWidget;
import kranji.reading.workbench.coverage.CoverageMissingWidget;
import kranji.reading.workbench.coverage.CoverageShelfWidget;
import kranji.reading.workbench.coverage.CoverageWidget;
import kranji.reading.workbench.relation.RelationSelectionSecretaryModule;

import java.util.List;

/**
 * How much of the library the meanings explain — shelf, article, gap.
 *
 * <p>Three grids on one bus. Open all three side by side and the chain does
 * the rest: the worst shelf is taken, its worst article is taken, and its
 * missing pairs are on screen before anything has been clicked. That is the
 * worklist, found rather than compiled.</p>
 */
public final class CoverageWorkbenchSpec implements WorkspaceSpec {

    public static final String KIND = "coverage";

    public static final CoverageWorkbenchSpec INSTANCE = new CoverageWorkbenchSpec();

    private CoverageWorkbenchSpec() {}

    @Override public String kind()  { return KIND; }
    @Override public String title() { return "Coverage"; }

    @Override
    public List<WidgetEntry> widgetEntries() {
        WidgetGroup coverage = WidgetGroup.of("Coverage");
        return List.of(
                WidgetEntry.of(CoverageShelfWidget.class, WidgetLabel.of("Shelves"))
                        .withIcon(new WidgetIcon.Emoji("📊"))   // 📊
                        .withGroup(coverage),
                WidgetEntry.of(CoverageArticleWidget.class, WidgetLabel.of("Articles"))
                        .withIcon(new WidgetIcon.Emoji("📰"))   // 📰
                        .withGroup(coverage),
                WidgetEntry.of(CoverageMissingWidget.class, WidgetLabel.of("Missing here"))
                        .withIcon(new WidgetIcon.Emoji("🕳️"))   // 🕳️
                        .withGroup(coverage));
    }

    @Override
    public List<PartyDecl> parties() {
        return List.of(
                PartyDecl.of(CoverageWidget.PARTY, RelationSelectionSecretaryModule.INSTANCE,
                             "RelationSelectionSecretary")
                         .exposedAs(CoverageWidget.PARTY_EXPOSED)
                         .build());
    }
}
