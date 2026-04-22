package kranji.util.existence;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Command-line entry point for the existence checker.
 *
 * <p>Usage:</p>
 * <pre>
 *   java -cp ... kranji.util.existence.ExistenceCheckMain &lt;json-file&gt;
 * </pre>
 *
 * <p>Reads the given JSON feed, runs it through {@link ExistenceChecker},
 * and prints {@link ExistenceReport#format()} to standard output. Exit
 * code is {@code 0} if every entry has a strong-typed implementation,
 * {@code 1} if any entries are missing, {@code 2} on I/O errors or bad
 * arguments.</p>
 */
public final class ExistenceCheckMain {

    private ExistenceCheckMain() {}

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -cp ... " +
                    ExistenceCheckMain.class.getName() + " <json-file>");
            System.exit(2);
            return;
        }

        Path path = Path.of(args[0]);
        ExistenceReport report;
        try {
            report = ExistenceChecker.check(path);
        } catch (IOException e) {
            System.err.println("Failed to read " + path + ": " + e.getMessage());
            System.exit(2);
            return;
        }

        System.out.println("Source: " + path);
        System.out.println(report.format());
        System.exit(report.isComplete() ? 0 : 1);
    }
}
