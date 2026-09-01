package kranji.studio;

import hue.captains.singapura.js.homing.core.AppModule;
import hue.captains.singapura.js.homing.studio.base.DefaultFixtures;
import hue.captains.singapura.js.homing.studio.base.Fixtures;
import hue.captains.singapura.js.homing.studio.base.Studio;
import hue.captains.singapura.js.homing.studio.base.Umbrella;
import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.List;
import java.util.Objects;

/**
 * Plain delegation to {@link DefaultFixtures}. Kranji Studio is a pure
 * doc-and-plan studio: no custom harness apps, no workspace spec, no data
 * endpoints of its own. The framework defaults supply everything —
 * catalogue host, doc reader, and the shared plan host.
 */
public record KranjiStudioFixtures<S extends Studio<?>>(Umbrella<S> umbrella)
        implements Fixtures<S>, ValueObject {

    public KranjiStudioFixtures {
        Objects.requireNonNull(umbrella);
    }

    @Override public List<AppModule<?, ?>> harnessApps() {
        return new DefaultFixtures<>(umbrella).harnessApps();
    }

    @Override public NodeChrome chromeFor(Umbrella<S> node) {
        return new DefaultFixtures<>(umbrella).chromeFor(node);
    }
}
