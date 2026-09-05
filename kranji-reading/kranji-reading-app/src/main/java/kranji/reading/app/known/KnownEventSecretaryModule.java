package kranji.reading.app.known;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ImportsFor;
import hue.captains.singapura.js.homing.core.ExportsOf;

import java.util.List;

/**
 * The read channel for the known set.
 *
 * <p>It carries what has already happened, plus the one request that changes
 * nothing:</p>
 *
 * <pre>{@code
 * WhatIsKnown                       // a pane that has just mounted, asking
 * KnownSnapshot { keys: [...] }     // the whole set, for whoever just asked
 * KnownAdded    { key }             // this pair is now claimed
 * KnownRemoved  { key }             // this pair is not
 * }</pre>
 *
 * <h2>Named facts, not a boolean</h2>
 *
 * <p>{@code KnownAdded} rather than {@code KnownChanged} with
 * {@code claimed: true}. The name of a fact should be the fact: a pane reading
 * a boolean has to work out what {@code false} would have meant, and the two
 * cases update different things.</p>
 *
 * <h2>Badges change here, and only here</h2>
 *
 * <p>Every message on this channel is published by {@code KnownService} and by
 * nothing else. A pane never updates itself when it sends a command, because a
 * request is not an outcome — the set lives on the device, the device can
 * refuse, and a badge that turned on at the request would be showing a child
 * something that had not happened.</p>
 *
 * <h2>Why the query lives here</h2>
 *
 * <p>{@code WhatIsKnown} changes nothing, so it belongs with the answers rather
 * than with the mutations. That way a read-only pane needs this party and no
 * other, which is what makes "it cannot write" a fact about its wiring instead
 * of a promise in its documentation.</p>
 */
public record KnownEventSecretaryModule()
        implements DomModule<KnownEventSecretaryModule> {

    /** The Secretary object itself. */
    public record KnownEventSecretary()
            implements Exportable._Constant<KnownEventSecretaryModule> {}

    public static final KnownEventSecretaryModule INSTANCE =
            new KnownEventSecretaryModule();

    @Override
    public ImportsFor<KnownEventSecretaryModule> imports() {
        return ImportsFor.<KnownEventSecretaryModule>builder().build();
    }

    @Override
    public ExportsOf<KnownEventSecretaryModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new KnownEventSecretary()));
    }
}
