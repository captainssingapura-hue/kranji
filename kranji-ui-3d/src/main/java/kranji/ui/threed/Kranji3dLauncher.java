package kranji.ui.threed;

/**
 * Thin entry point that hands off to {@link Kranji3dApp#launch}.
 *
 * <p>JavaFX needs the main class to NOT extend {@code Application} when
 * launched via the {@code javafx-maven-plugin} on the classpath, so
 * this class exists purely to call {@code Application.launch} with the
 * actual app class.</p>
 */
public final class Kranji3dLauncher {
    public static void main(String[] args) {
        javafx.application.Application.launch(Kranji3dApp.class, args);
    }
    private Kranji3dLauncher() {}
}
