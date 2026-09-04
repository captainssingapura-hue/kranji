package kranji.reading.app;

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
 * <p>Two populations, and missing either one breaks the app in a way a page
 * request does not reveal.</p>
 *
 * <ul>
 *   <li><b>Ours</b> — the widgets and CSS group declared in {@link ReadingCrate}.</li>
 *   <li><b>The framework's</b> — the harness the shell itself is built from:
 *       the catalogue host, the doc reader, the workspace, its codecs, its
 *       persistence. The fixtures mount these; they are as much a served module
 *       as anything we wrote.</li>
 * </ul>
 *
 * <p>The set is the <b>transitive closure</b> over both, not one level of
 * {@code requires()}. A crate's dependencies have dependencies, and a partial
 * walk produces a page that loads and then fails to boot — the HTML arrives,
 * the modules 404, and nothing renders.</p>
 */
public final class ReadingServableModules {

    private ReadingServableModules() {}

    /**
     * Framework crate roots the shell needs at runtime.
     *
     * <p>Mirrors the set the framework's own conformance studio declares, minus
     * the crates specific to it.</p>
     */
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
        roots.add(ReadingCrate.INSTANCE);
        roots.addAll(FRAMEWORK);

        var classes = new HashSet<String>();
        for (Crate c : CrateClosure.of(roots)) {
            for (CrateEntry e : c.entries()) classes.add(e.moduleClass());
        }
        return Set.copyOf(classes);
    }
}
