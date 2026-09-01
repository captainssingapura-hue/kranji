package kranji.reading.app;

import hue.captains.singapura.js.homing.studio.base.app.Entry;
import hue.captains.singapura.js.homing.studio.base.app.L0_Catalogue;
import hue.captains.singapura.js.homing.studio.base.app.Navigable;
import hue.captains.singapura.js.homing.workspace.shell.GenericWorkspace;

import java.util.List;

/** Landing for the reading studio — one door, into the reading workspace. */
public record ReadingLandingCatalogue() implements L0_Catalogue<ReadingLandingCatalogue> {

    public static final ReadingLandingCatalogue INSTANCE = new ReadingLandingCatalogue();

    @Override public String name()    { return "Kranji · Reading"; }
    @Override public String badge()   { return "STUDIO"; }
    @Override public String icon()    { return "\uD83D\uDCD6"; }   // 📖
    @Override public String summary() {
        return "Read Chinese with pinyin that appears only where you need it.";
    }

    @Override
    public List<Entry<ReadingLandingCatalogue>> leaves() {
        Navigable<GenericWorkspace.Params, GenericWorkspace> workspace =
                new Navigable<>(GenericWorkspace.INSTANCE,
                        new GenericWorkspace.Params(ReadingWorkspaceSpec.KIND),
                        "Reading Workspace",
                        "The reader, with the character panel and the known-set manager alongside.");
        return List.of(Entry.of(this, workspace));
    }
}
