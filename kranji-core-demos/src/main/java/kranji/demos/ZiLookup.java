package kranji.demos;

import kranji.common.depth1.Depth1;
import kranji.common.depth2.Depth2;
import kranji.common.depth3.Depth3;
import kranji.common.depth4.Depth4;
import kranji.common.depth5.Depth5;
import kranji.library.BasicSet;
import kranji.singular.SingularFamiliesPerclass;
import kranji.zi.ComposedZi;
import kranji.zi.SingularBlock;
import kranji.zi.SingularPart;
import kranji.zi.SingularZi;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Checks which characters from a given set already exist in the system.
 *
 * <p>File-based usage (recommended — avoids Windows encoding issues):</p>
 * <pre>
 * mvn -pl kranji-core-demos exec:java \
 *     -Dexec.mainClass=kranji.demos.ZiLookup \
 *     -Dexec.args="input.txt output.txt"
 * </pre>
 *
 * <p>The input file is a UTF-8 text file containing comma-separated characters.
 * The result is written to the output file (UTF-8).  If the output path is
 * omitted, results go to {@code <input-stem>-result.txt} in the same
 * directory.</p>
 *
 * <p>Stdin/stdout fallback (works on UTF-8 terminals):</p>
 * <pre>
 * printf '一,二,三' | mvn -pl kranji-core-demos exec:java \
 *     -Dexec.mainClass=kranji.demos.ZiLookup
 * </pre>
 */
public final class ZiLookup {

    private ZiLookup() {}

    public static void main(String[] args) throws IOException {
        // Bootstrap the library
        BasicSet.INSTANCE.register();
        SingularFamiliesPerclass.registerInto(BasicSet.INSTANCE);

        // Build glyph index
        Map<String, String> index = buildIndex();

        // Resolve input source and output destination
        String input;
        PrintStream out;
        Path outputPath = null;

        if (args.length >= 1 && !args[0].isBlank() && looksLikeFilePath(args[0])) {
            // File mode
            Path inputPath = Path.of(args[0]);
            input = Files.readString(inputPath, StandardCharsets.UTF_8).trim();

            if (args.length >= 2 && !args[1].isBlank()) {
                outputPath = Path.of(args[1]);
            } else {
                // Derive output name: input-stem + "-result.txt"
                String stem = stripExtension(inputPath.getFileName().toString());
                outputPath = inputPath.resolveSibling(stem + "-result.txt");
            }
            out = new PrintStream(
                    Files.newOutputStream(outputPath, StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING),
                    true, StandardCharsets.UTF_8);
        } else if (args.length >= 1 && !args[0].isBlank()) {
            // Inline argument mode (legacy)
            input = args[0];
            out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        } else {
            // Stdin mode
            input = new String(System.in.readAllBytes(), StandardCharsets.UTF_8).trim();
            out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        }

        if (input.isBlank()) {
            System.err.println("Usage: ZiLookup <input.txt> [output.txt]");
            System.err.println("       echo \"一,二,三\" | ZiLookup");
            System.exit(1);
        }

        // Parse and look up
        String[] requested = input.split(",");
        List<String> foundList = new ArrayList<>();
        List<String> missingList = new ArrayList<>();

        for (String ch : requested) {
            String c = ch.trim();
            if (c.isEmpty()) continue;
            if (index.containsKey(c)) {
                foundList.add(c);
            } else {
                missingList.add(c);
            }
        }

        // Report
        out.println("=== Zi Lookup ===");
        out.println("Requested: " + requested.length);
        out.println("Found:     " + foundList.size());
        out.println("Missing:   " + missingList.size());

        if (!foundList.isEmpty()) {
            out.println();
            out.println("--- Found (" + foundList.size() + ") ---");
            for (String c : foundList) {
                out.printf("  %s  %s%n", c, index.get(c));
            }
        }

        if (!missingList.isEmpty()) {
            out.println();
            out.println("--- Missing (" + missingList.size() + ") ---");
            out.println(String.join(",", missingList));
        }

        out.flush();

        if (outputPath != null) {
            out.close();
            // Echo a short summary to the console so the caller knows it worked
            System.out.println("Wrote " + outputPath.toAbsolutePath());
            System.out.println("  Found: " + foundList.size()
                    + "  Missing: " + missingList.size());
        }
    }

    // ── helpers ──────────────────────────────────────────────────────────

    private static Map<String, String> buildIndex() {
        Map<String, String> found = new LinkedHashMap<>();

        // Singular catalog — SingularZi + SingularPart + component parts.
        for (var member : BasicSet.INSTANCE.components()) {
            if (member instanceof SingularBlock sb) {
                String glyph = sb.glyph();
                String type = (sb instanceof SingularZi) ? "SingularZi"
                            : (sb instanceof SingularPart) ? "SingularPart"
                            : "Part";
                found.put(glyph, type + " (" + member.getClass().getSimpleName() + ")");
            }
        }

        // Composed catalog — one contribution per depth. First writer wins so
        // a singular entry is preferred over a composed one if they collide,
        // which is what callers want to see in the report.
        indexComposed(found, Depth1.ALL, 1);
        indexComposed(found, Depth2.ALL, 2);
        indexComposed(found, Depth3.ALL, 3);
        indexComposed(found, Depth4.ALL, 4);
        indexComposed(found, Depth5.ALL, 5);

        return found;
    }

    private static void indexComposed(Map<String, String> found,
                                      List<ComposedZi> pool, int depth) {
        for (ComposedZi z : pool) {
            found.putIfAbsent(z.character(),
                    "ComposedZi (depth " + depth + ")");
        }
    }

    /** True if the string looks like a file path rather than inline Chinese characters. */
    private static boolean looksLikeFilePath(String s) {
        return s.endsWith(".txt") || s.endsWith(".csv")
                || s.contains("/") || s.contains("\\")
                || s.contains(".");
    }

    private static String stripExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        return (dot > 0) ? filename.substring(0, dot) : filename;
    }
}
