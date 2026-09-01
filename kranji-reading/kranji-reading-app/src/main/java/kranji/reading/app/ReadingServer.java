package kranji.reading.app;

import hue.captains.singapura.js.homing.studio.base.Bootstrap;
import hue.captains.singapura.js.homing.studio.base.DefaultRuntimeParams;
import hue.captains.singapura.js.homing.studio.base.Umbrella;

/**
 * Standalone server for Kranji Reading. Port 8102 — clear of the Kranji
 * studio on 8101 and the Homing demos on 8082/8083/8090.
 *
 * <pre>{@code
 * mvn -o -pl kranji-reading/kranji-reading-app exec:java \
 *     -Dexec.mainClass="kranji.reading.app.ReadingServer"
 * }</pre>
 */
public final class ReadingServer {

    /** Default listen port. Override with {@code -Dreading.port=...}. */
    public static final int DEFAULT_PORT = 8102;

    private ReadingServer() {}

    public static void main(String[] args) {
        int port = Integer.getInteger("reading.port", DEFAULT_PORT);
        var umbrella = new Umbrella.Solo<>(ReadingStudio.INSTANCE);
        new Bootstrap<>(new ReadingFixtures(umbrella), new DefaultRuntimeParams(port)).start();
    }
}
