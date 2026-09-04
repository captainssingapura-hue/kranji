package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;
import hue.captains.singapura.js.homing.core.ModuleImports;

import java.util.List;

/**
 * Following the known set without changing it.
 *
 * <p>Everything a pane needs to <em>answer against</em> the set, and nothing it
 * would need to alter one. The reader hides pinyin for readings already
 * claimed; the character panel will flag which components are familiar. Neither
 * marks anything, and neither should be able to.</p>
 *
 * <p>One call joins the party, asks for the current set, seeds from the device
 * and keeps the answer up to date. Written longhand in each pane that was
 * thirty lines of party plumbing whose only interesting part — <em>and then
 * repaint</em> — was buried in it.</p>
 *
 * <h2>Read-only is enforced by what is absent</h2>
 *
 * <p>No {@code MarkKnown}, no {@code UnmarkKnown}, no save. {@code changed()} is
 * never called on the persistence, so a watching pane cannot write to the
 * device even by accident. Marking is a deliberate act made in front of a
 * character's other readings — not a side effect of reading past one.</p>
 */
public record KnownWatchModule() implements DomModule<KnownWatchModule> {

    /** Yields {@code annotates}, {@code set} and {@code leave}. */
    public record createKnownWatch() implements Exportable._Constant<KnownWatchModule> {}

    public static final KnownWatchModule INSTANCE = new KnownWatchModule();

    /**
     * The three pieces a watcher needs, imported here so a consuming pane
     * declares one dependency instead of four.
     */
    @Override
    public ImportsFor<KnownWatchModule> imports() {
        return ImportsFor.<KnownWatchModule>builder()
                .add(new ModuleImports<>(
                        List.of(new KnownSetModule.createKnownSet()),
                        KnownSetModule.INSTANCE))
                .add(new ModuleImports<>(
                        List.of(new KnownStoreModule.createKnownStore()),
                        KnownStoreModule.INSTANCE))
                .add(new ModuleImports<>(
                        List.of(new KnownPersistenceModule.createKnownPersistence()),
                        KnownPersistenceModule.INSTANCE))
                .build();
    }

    @Override
    public ExportsOf<KnownWatchModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createKnownWatch()));
    }
}
