package kranji.dist;

import kranji.reading.app.ReadingServer;
import kranji.studio.KranjiStudioServer;

/**
 * The one entry point in the runnable jar.
 *
 * <pre>
 *   java -jar kranji.jar                 the reader, on 8102
 *   java -jar kranji.jar reader          the same
 *   java -jar kranji.jar reader 9102     the reader, somewhere else
 *   java -jar kranji.jar studio 9101     the studio, somewhere else
 * </pre>
 *
 * <h2>Why the port is an argument</h2>
 *
 * <p>Because the whole point of this jar is running <em>beside</em> a working
 * tree, not instead of it. A frozen build that could only listen on 8102 would
 * be in a fight with every {@code mvn exec:java} for the same port, and the one
 * that lost would be whichever was started second — which is a coin toss, not a
 * deployment.</p>
 *
 * <p>Both servers already read a system property. This only spells the argument
 * form, so nobody has to remember which property a given app named.</p>
 *
 * <h2>Two apps, one jar</h2>
 *
 * <p>They share almost all of their weight — Homing, Vert.x, the corpus, the
 * gloss collections — so two jars would be two copies of the same forty
 * megabytes. Which one runs is a word on the command line.</p>
 */
public final class KranjiApp {

    /** What the reader's own main reads. */
    static final String READER_PORT_PROPERTY = "reading.port";

    /** And the studio's. */
    static final String STUDIO_PORT_PROPERTY = "studio.port";

    private KranjiApp() {}

    public static void main(String[] args) {
        String app = args.length > 0 ? args[0].trim().toLowerCase() : "reader";

        if (app.equals("-h") || app.equals("--help") || app.equals("help")) {
            System.out.println(usage());
            return;
        }

        // A port given here wins over one already in the environment: an
        // argument is the more deliberate of the two, and the person typing it
        // can see what they typed.
        String port = args.length > 1 ? args[1].trim() : null;
        if (port != null && !port.isEmpty()) {
            if (!port.matches("\\d{1,5}")) {
                System.err.println("Not a port: '" + port + "'");
                System.err.println();
                System.err.println(usage());
                System.exit(2);
                return;
            }
            System.setProperty(propertyFor(app), port);
        }

        switch (app) {
            case "reader" -> ReadingServer.main(new String[0]);
            case "studio" -> KranjiStudioServer.main(new String[0]);
            default -> {
                System.err.println("No app called '" + app + "'.");
                System.err.println();
                System.err.println(usage());
                System.exit(2);
            }
        }
    }

    private static String propertyFor(String app) {
        return "studio".equals(app) ? STUDIO_PORT_PROPERTY : READER_PORT_PROPERTY;
    }

    private static String usage() {
        return """
               Usage: java -jar kranji.jar [reader|studio] [port]

                 reader   read Chinese with pinyin that appears only where it
                          is needed                            (default, 8102)
                 studio   the internal workbench: gloss relations, corpus
                          reports, design docs                          (8101)

               The port may also be given as -Dreading.port or -Dstudio.port.
               """;
    }
}
