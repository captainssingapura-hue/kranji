package kranji.studio;

import hue.captains.singapura.js.homing.studio.base.Bootstrap;
import hue.captains.singapura.js.homing.studio.base.DefaultRuntimeParams;
import hue.captains.singapura.js.homing.studio.base.Studio;
import hue.captains.singapura.js.homing.studio.base.Umbrella;

/**
 * Standalone server for Kranji Studio. Listens on port 8101 — clear of the
 * Homing demo (8082), Building Blocks (8083), and the fin dashboard (8100).
 *
 * <p>Run with:</p>
 * <pre>{@code
 * mvn -pl kranji-studio exec:java \
 *     -Dexec.mainClass="kranji.studio.KranjiStudioServer"
 * }</pre>
 */
public final class KranjiStudioServer {

    /** Default listen port for the studio. */
    public static final int PORT = 8101;

    private KranjiStudioServer() {}

    public static void main(String[] args) {
        Umbrella<Studio<?>> umbrella = new Umbrella.Solo<>(KranjiStudio.INSTANCE);

        new Bootstrap<>(
                new KranjiStudioFixtures<>(umbrella),
                new DefaultRuntimeParams(PORT)
        ).start();
    }
}
