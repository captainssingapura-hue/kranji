package kranji.studio.gloss;

import hue.captains.singapura.js.homing.core.DomModule;
import hue.captains.singapura.js.homing.core.Exportable;
import hue.captains.singapura.js.homing.core.ExportsOf;
import hue.captains.singapura.js.homing.core.ImportsFor;

import java.util.List;

/**
 * The Secretary for the workbench's selection bus.
 *
 * <p>One bus for every entity type rather than one per type. A message names
 * the entity it concerns — {@code PksSelected{entity, pks}} in,
 * {@code SelectionChanged{entity, pks, all}} out — and a widget reacts only to
 * the entity directly above it in the chain.</p>
 *
 * <p>Every broadcast carries the whole selection state, not just what changed.
 * A widget that mounted after a selection was made is then correct on the next
 * change rather than needing a resync handshake, which is the same reason the
 * reading app's known-set bus carries its whole set.</p>
 */
public record GlossSelectionSecretaryModule()
        implements DomModule<GlossSelectionSecretaryModule> {

    /** The pure (state, envelope) → step function. */
    public record GlossSelectionSecretary()
            implements Exportable._Constant<GlossSelectionSecretaryModule> {}

    public static final GlossSelectionSecretaryModule INSTANCE =
            new GlossSelectionSecretaryModule();

    @Override
    public ImportsFor<GlossSelectionSecretaryModule> imports() {
        return ImportsFor.<GlossSelectionSecretaryModule>builder().build();
    }

    @Override
    public ExportsOf<GlossSelectionSecretaryModule> exports() {
        return new ExportsOf<>(INSTANCE, List.of(new GlossSelectionSecretary()));
    }
}
