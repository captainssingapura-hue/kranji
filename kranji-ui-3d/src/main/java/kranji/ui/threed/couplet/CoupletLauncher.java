package kranji.ui.threed.couplet;

/**
 * Standalone entry point for the Couplet Playground.
 *
 * <p>Run with:</p>
 * <pre>mvn -pl kranji-ui-3d javafx:run -Djavafx.mainClass=kranji.ui.threed.couplet.CoupletLauncher</pre>
 */
public final class CoupletLauncher {
    public static void main(String[] args) {
        javafx.application.Application.launch(CoupletApp.class, args);
    }
    private CoupletLauncher() {}
}
