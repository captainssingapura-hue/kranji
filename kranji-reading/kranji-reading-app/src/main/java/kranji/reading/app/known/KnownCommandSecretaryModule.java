package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The write channel for the known set.
 *
 * <p>It carries requests, and only requests. Whether one was accepted, and what
 * the set looks like afterwards, arrives on {@link KnownEventSecretaryModule} —
 * a different bus, with different members.</p>
 *
 * <pre>{@code
 * MarkKnown   { key: "34892:xing2" }
 * UnmarkKnown { key: "34892:xing2" }
 * }</pre>
 *
 * <h2>Why the direction is a channel rather than a convention</h2>
 *
 * <p>A pane that never joins this party cannot change the set. Not by
 * agreement, not by being handed an object with no {@code toggle} on it — by
 * having no channel to say it on. The old module claimed that guarantee by
 * leaving a method out of a returned object, which held exactly as long as
 * nobody added the method back.</p>
 *
 * <p>It also removes a trap the single bus set. With requests and facts on one
 * party, a pane hears its own request come back and cannot distinguish it from
 * a confirmation — so "did this work?" and "did I ask?" look identical.
 * Here, nothing a pane sends returns to it.</p>
 *
 * <h2>One consumer, ever</h2>
 *
 * <p>{@code KnownService}. If a second thing starts listening here the set has
 * two owners again, and the whole exercise has been undone.</p>
 */
public record KnownCommandSecretaryModule()
        implements DomModule<KnownCommandSecretaryModule> {

    /** The Secretary object itself. */
    public record KnownCommandSecretary()
            implements Exportable._Constant<KnownCommandSecretaryModule> {}

    public static final KnownCommandSecretaryModule INSTANCE =
            new KnownCommandSecretaryModule();

    @Override
    public ImportsFor<KnownCommandSecretaryModule> imports() {
        return ImportsFor.<KnownCommandSecretaryModule>builder().build();
    }

    @Override
    public ExportsOf<KnownCommandSecretaryModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new KnownCommandSecretary()));
    }
}
