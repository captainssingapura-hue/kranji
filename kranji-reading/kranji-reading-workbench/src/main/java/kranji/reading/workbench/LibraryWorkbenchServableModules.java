package kranji.reading.workbench;

import hue.captains.singapura.js.homing.core.Crate;
import hue.captains.singapura.js.homing.conformance.rules.CrateClosure;
import hue.captains.singapura.js.homing.core.CrateEntry;
import kranji.reading.app.ReadingServableModules;

import java.util.HashSet;
import java.util.Set;

/**
 * Every module the bench may serve: the reader's set, plus the bench's own.
 *
 * <p>The reader's set is taken whole from {@link ReadingServableModules}
 * rather than recomputed, so the article browser serves exactly what the
 * reader serves. The bench's crate closes over its own requirements on top.</p>
 */
public final class LibraryWorkbenchServableModules {

    private LibraryWorkbenchServableModules() {}

    public static Set<String> all() {
        var classes = new HashSet<>(ReadingServableModules.all());
        for (Crate c : CrateClosure.of(java.util.List.of(LibraryWorkbenchCrate.INSTANCE))) {
            for (CrateEntry e : c.entries()) classes.add(e.moduleClass());
        }
        return Set.copyOf(classes);
    }
}
