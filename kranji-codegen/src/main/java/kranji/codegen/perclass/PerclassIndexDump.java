package kranji.codegen.perclass;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Diagnostic tool: dump the singular glyph index and the composed glyph
 * index to UTF-8 files under {@code target/index-dump/}, and (optionally)
 * report whether a given list of probe glyphs is resolvable.
 *
 * <p>Run with:</p>
 * <pre>{@code
 * mvn -pl kranji-codegen exec:java \
 *     -Dexec.mainClass=kranji.codegen.perclass.PerclassIndexDump \
 *     -Dexec.args="glyph1 glyph2 glyph3 ..."
 * }</pre>
 *
 * <p>With no args: writes {@code singulars.txt} and {@code composed.txt}
 * only. With args: also writes {@code probe.txt} showing OK / MISSING
 * status for each probe glyph.</p>
 */
public final class PerclassIndexDump {

    private PerclassIndexDump() {}

    public static void main(String[] args) throws IOException {
        Path cwd = Path.of("").toAbsolutePath();
        Path reactorRoot = locateReactorRoot(cwd);
        Path catalogRoot = reactorRoot.resolve(PerclassRouting.CATALOG_ROOT);
        Path outDir = reactorRoot.resolve("kranji-codegen/target/index-dump");
        Files.createDirectories(outDir);

        PerclassSingularIndex singulars = PerclassSingularIndex.build();
        PerclassComposedIndex composed = PerclassComposedIndex.loadAll(catalogRoot);

        // Dump singulars
        List<String> singularLines = new ArrayList<>();
        for (String g : collectSingularGlyphs(singulars)) {
            PerclassSingularIndex.Ref r = singulars.find(g);
            singularLines.add(g + "\t" + r.valueExpression());
        }
        Files.write(outDir.resolve("singulars.txt"),
                singularLines, StandardCharsets.UTF_8);

        // Dump composed
        List<String> composedLines = new ArrayList<>();
        for (PerclassComposedIndex.Entry e : composed.entries()) {
            composedLines.add(e.data().glyph() + "\t" + e.key().initial()
                    + "/" + e.key().finalTone() + "/" + e.key().className());
        }
        Files.write(outDir.resolve("composed.txt"),
                composedLines, StandardCharsets.UTF_8);

        // Probe: read glyphs from UTF-8 file named by first arg
        // (Windows shell mangles Chinese args through Maven; file I/O is
        // the only way to carry arbitrary code points reliably.)
        List<String> probeLines = new ArrayList<>();
        probeLines.add("glyph\tsingular?\tcomposed?");
        if (args.length > 0) {
            Path probeIn = Path.of(args[0]);
            if (Files.exists(probeIn)) {
                List<String> probeGlyphs = Files.readAllLines(
                        probeIn, StandardCharsets.UTF_8);
                for (String raw : probeGlyphs) {
                    String g = raw.strip();
                    if (g.isEmpty() || g.startsWith("#")) continue;
                    boolean hasSingular = singulars.find(g) != null;
                    boolean hasComposed = composed.find(g) != null;
                    probeLines.add(g + "\t" + (hasSingular ? "YES" : "no")
                            + "\t" + (hasComposed ? "YES" : "no"));
                }
            } else {
                probeLines.add("# probe file not found: " + probeIn);
            }
        }
        Files.write(outDir.resolve("probe.txt"),
                probeLines, StandardCharsets.UTF_8);

        // Also print to stdout with UTF-8 guarantee
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.println("Singular index entries: " + singulars.size());
        out.println("Composed index entries: " + composed.size());
        out.println("Output directory: " + outDir);
        out.println("\nProbe results (also written to probe.txt):");
        for (String line : probeLines) out.println("  " + line);
    }

    private static List<String> collectSingularGlyphs(PerclassSingularIndex idx) {
        // PerclassSingularIndex currently only exposes size/find/require.
        // Reflect on its private byGlyph map to dump all keys.
        try {
            var f = PerclassSingularIndex.class.getDeclaredField("byGlyph");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            var map = (java.util.Map<String, ?>) f.get(idx);
            List<String> keys = new ArrayList<>(map.keySet());
            java.util.Collections.sort(keys);
            return keys;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path locateReactorRoot(Path start) {
        Path p = start;
        while (p != null) {
            if (Files.exists(p.resolve("kranji-codegen/pom.xml"))
                    && Files.exists(p.resolve("pom.xml"))) {
                return p;
            }
            p = p.getParent();
        }
        throw new IllegalStateException("Cannot locate reactor root from " + start);
    }
}
