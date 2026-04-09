package kranji.ui.demo;

import javafx.application.Application;

/**
 * Non-Application launcher class — avoids the "JavaFX runtime components
 * are missing" error when running from a non-modular classpath JAR.
 */
public final class KranjiDemoLauncher {

    public static void main(String[] args) {
        Application.launch(KranjiDemoApp.class, args);
    }
}
