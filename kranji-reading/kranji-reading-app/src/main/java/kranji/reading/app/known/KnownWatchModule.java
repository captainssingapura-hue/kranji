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
 * <p>One call joins the party, asks the service for a snapshot, and keeps a
 * mirror of it current from the news that follows. It writes nothing: no
 * store, no save, no device. It used to construct a persistence engine of its
 * own, with an empty failure handler, in every pane that watched.</p>
 *
 * <h2>Read-only is enforced by what is absent</h2>
 *
 * <p>No {@code toggle} unless a caller asks for one, and no device at all. A
 * watching pane cannot write, because there is nothing in it that writes.
 * Marking is a deliberate act made in front of a character's other readings —
 * not a side effect of reading past one.</p>
 */
public record KnownWatchModule() implements DomModule<KnownWatchModule> {

    /** Yields {@code annotates}, {@code isKnown}, {@code record} and {@code leave}. */
    public record createKnownWatch() implements Exportable._Constant<KnownWatchModule> {}

    public static final KnownWatchModule INSTANCE = new KnownWatchModule();

    /**
     * The two pieces a watcher needs, imported here so a consuming pane
     * declares one dependency instead of three.
     */
    @Override
    public ImportsFor<KnownWatchModule> imports() {
        return ImportsFor.<KnownWatchModule>builder()
                .add(new ModuleImports<>(
                        List.of(new KnownSetModule.createKnownSet()),
                        KnownSetModule.INSTANCE))
                .add(new ModuleImports<>(
                        List.of(new KnownRecordModule.createKnownRecord()),
                        KnownRecordModule.INSTANCE))
                .build();
    }

    @Override
    public ExportsOf<KnownWatchModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new createKnownWatch()));
    }
}
