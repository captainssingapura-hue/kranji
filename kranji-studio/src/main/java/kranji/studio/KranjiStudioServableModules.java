package kranji.studio;

import hue.captains.singapura.js.homing.conformance.rules.CrateClosure;
import hue.captains.singapura.js.homing.core.Crate;
import hue.captains.singapura.js.homing.core.CrateEntry;
import hue.captains.singapura.js.homing.core.js.CoreJsCrate;
import hue.captains.singapura.js.homing.grid.RelationGridCrate;
import hue.captains.singapura.js.homing.server.ServerCrate;
import hue.captains.singapura.js.homing.studio.base.StudioBaseCrate;
import hue.captains.singapura.js.homing.studio.workspace.StudioWorkspaceCrate;
import hue.captains.singapura.js.homing.workspace.WorkspaceCrate;
import hue.captains.singapura.js.homing.workspace.codecs.WorkspaceCodecsCrate;
import hue.captains.singapura.js.homing.workspace.persistence.WorkspacePersistenceCrate;
import hue.captains.singapura.js.homing.workspace.shell.WorkspaceShellCrate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The allow-list of classes {@code /module?class=…} will serve.
 *
 * <p>Two populations, and missing either one breaks the studio in a way a page
 * request does not reveal: our own widgets, and the framework harness the shell
 * is built from. The set is the <b>transitive closure</b> over both — a partial
 * walk gives a page that loads and then fails to boot, because the HTML arrives
 * and the modules 404.</p>
 */
public final class KranjiStudioServableModules {

    private KranjiStudioServableModules() {}

    private static final List<Crate> FRAMEWORK = List.of(
            CoreJsCrate.INSTANCE,
            ServerCrate.INSTANCE,
            StudioBaseCrate.INSTANCE,
            WorkspaceCrate.INSTANCE,
            WorkspaceCodecsCrate.INSTANCE,
            WorkspacePersistenceCrate.INSTANCE,
            WorkspaceShellCrate.INSTANCE,
            StudioWorkspaceCrate.INSTANCE,
            RelationGridCrate.INSTANCE);

    /** Every class servable through the crate gate. */
    public static Set<String> all() {
        var roots = new ArrayList<Crate>();
        roots.add(KranjiStudioCrate.INSTANCE);
        roots.addAll(FRAMEWORK);

        var classes = new HashSet<String>();
        for (Crate c : CrateClosure.of(roots)) {
            for (CrateEntry e : c.entries()) classes.add(e.moduleClass());
        }
        return Set.copyOf(classes);
    }
}
