package kranji.reading.workbench;

import hue.captains.singapura.js.homing.studio.base.Bootstrap;
import hue.captains.singapura.js.homing.studio.base.DefaultRuntimeParams;
import hue.captains.singapura.js.homing.studio.base.Umbrella;
import kranji.reading.library.ArticleLibrary;
import kranji.reading.library.Libraries;

/**
 * The bench, standalone.
 *
 * <p>Says which libraries it found before it listens, the way the reader's
 * launch script does. Run from this module alone that is the demonstration
 * set and nothing else, and it says so; run from a library's own workbench
 * module it names that library too, and which one it mounted. A bench that
 * quietly opened on the wrong articles would be a tool for checking the wrong
 * thing.</p>
 *
 * <p>8104: clear of the reader (8102), the studio (8101) and the prep
 * server. Override with {@code -Dworkbench.port}.</p>
 */
public final class LibraryWorkbenchServer {

    public static final int DEFAULT_PORT = 8104;

    private LibraryWorkbenchServer() {}

    public static void main(String[] args) {
        int port = Integer.getInteger("workbench.port", DEFAULT_PORT);
        announce();
        var umbrella = new Umbrella.Solo<>(LibraryWorkbenchStudio.INSTANCE);
        new Bootstrap<>(new LibraryWorkbenchFixtures(umbrella), new DefaultRuntimeParams(port))
                .start();
    }

    /** One line per library on the classpath, and which of them mounted. */
    static void announce() {
        ArticleLibrary mounted = Libraries.mounted();
        for (ArticleLibrary library : Libraries.discovered()) {
            int articles = library.tree().collections().stream()
                    .mapToInt(c -> c.articles().size()).sum();
            System.out.println("  library: " + library.name()
                    + " (" + articles + " articles, precedence " + library.precedence() + ")"
                    + (library == mounted ? "  <- mounted" : ""));
        }
        if (Libraries.discovered().size() == 1) {
            System.out.println("  NOTE: only the demonstration set is on the classpath. "
                    + "Run the bench from a library's own workbench module to see that library.");
        }
    }
}
