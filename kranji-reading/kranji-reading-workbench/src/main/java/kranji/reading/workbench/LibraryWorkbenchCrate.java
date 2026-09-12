package kranji.reading.workbench;

import hue.captains.singapura.js.homing.core.Crate;
import hue.captains.singapura.js.homing.core.CrateEntry;
import hue.captains.singapura.js.homing.grid.RelationGridCrate;
import kranji.reading.workbench.coverage.CoverageArticleWidget;
import kranji.reading.workbench.coverage.CoverageMissingWidget;
import kranji.reading.workbench.coverage.CoverageShelfWidget;
import kranji.reading.workbench.relation.RelationCss;
import kranji.reading.workbench.relation.RelationSelectionSecretaryModule;

import java.util.List;

/**
 * What the bench itself adds to the reader's crate: the relation grid
 * machinery, and the coverage widgets built on it.
 *
 * <p>The article browser adds nothing here — it is the reader's widgets, and
 * they travel in the reader's crate. This crate is the bench's own tools.</p>
 */
public final class LibraryWorkbenchCrate implements Crate {

    public static final LibraryWorkbenchCrate INSTANCE = new LibraryWorkbenchCrate();

    private LibraryWorkbenchCrate() {}

    @Override
    public String name() { return "kranji-reading-workbench"; }

    @Override
    public List<CrateEntry> entries() {
        return List.of(
                CrateEntry.of(RelationCss.INSTANCE),
                CrateEntry.of(RelationSelectionSecretaryModule.INSTANCE),
                CrateEntry.of(CoverageShelfWidget.INSTANCE),
                CrateEntry.of(CoverageArticleWidget.INSTANCE),
                CrateEntry.of(CoverageMissingWidget.INSTANCE));
    }

    @Override
    public List<Crate> requires() {
        // A relation widget imports RelationGrid and TextCell.
        return List.of(RelationGridCrate.INSTANCE);
    }
}
