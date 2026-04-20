package kranji.codegen.depth1;

import kranji.common.depth1.Depth1;
import kranji.codegen.routing.PinyinRouting;
import kranji.zi.ComposedZi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Step 1 of the Batch-3 depth1 migration: emit one empty
 * {@code <Final><Tone>.java} skeleton per (initial, final, tone) triple
 * found in {@link Depth1#ALL}, plus one {@code Depth1<InitialPascal>}
 * aggregator per initial.
 *
 * <p>Skeletons sit in {@code kranji.common.depth1.<initial>} sub-packages
 * (e.g. {@code kranji.common.depth1.b.Ang2}); aggregators sit at the top
 * of {@code kranji.common.depth1} (e.g. {@code Depth1B}). Both expose an
 * empty {@code public static final List<ComposedZi> ALL = List.of();}
 * so the new types compile alongside the existing
 * {@link kranji.common.depth1.Depth1Strokes3 Depth1Strokes*} files
 * without affecting {@code Depth1.ALL} (which still aggregates the old
 * stroke files).</p>
 *
 * <h2>Usage</h2>
 * <pre>
 * mvn -pl kranji-codegen exec:java \
 *   -Dexec.mainClass=kranji.codegen.depth1.Depth1SkeletonMain
 * </pre>
 * The optional argument is the {@code kranji-common-depth1} module root;
 * default is the conventional path relative to the project root.
 */
public final class Depth1SkeletonMain {

    private Depth1SkeletonMain() {}

    static final String DEFAULT_MODULE_ROOT = "kranji-common-depth1";
    static final String DEPTH1_PACKAGE      = "kranji.common.depth1";
    static final String AUTO_BANNER =
            "// AUTO-GENERATED skeleton by Depth1SkeletonMain - do not edit by hand.";

    public static void main(String[] args) throws IOException {
        Path moduleRoot = Paths.get(args.length > 0 ? args[0] : DEFAULT_MODULE_ROOT);
        Result r = run(moduleRoot);
        System.out.println(String.format(
                "Wrote %d (final, tone) skeletons + %d initial aggregators under %s",
                r.skeletons(), r.aggregators(),
                moduleRoot.resolve("src/main/java").toAbsolutePath()));
    }

    public record Result(int skeletons, int aggregators) {}

    public static Result run(Path moduleRoot) throws IOException {
        Path javaRoot = moduleRoot.resolve("src/main/java");

        // Sub-package -> set of class names (alphabetical).
        Map<String, TreeSet<String>> initialToClasses = new TreeMap<>();
        // Sub-package -> Pascal-cased initial (for the aggregator's name).
        Map<String, String> initialToPascal = new LinkedHashMap<>();
        // Distinct (subPkg, className) pairs -> their routing key.
        Map<String, PinyinRouting.Key> triples = new LinkedHashMap<>();

        for (ComposedZi zi : Depth1.ALL) {
            PinyinRouting.Key key = PinyinRouting.keyFor(zi.pinyin());
            String tripleId = key.subPackage() + "/" + key.className();
            triples.putIfAbsent(tripleId, key);
            initialToClasses
                    .computeIfAbsent(key.subPackage(), k -> new TreeSet<>())
                    .add(key.className());
            initialToPascal.putIfAbsent(key.subPackage(), key.initialPascal());
        }

        // Skeletons (one per triple).
        int skeletons = 0;
        for (PinyinRouting.Key key : triples.values()) {
            writeClassSkeleton(javaRoot, key);
            skeletons++;
        }

        // Per-initial aggregators (one per distinct sub-package).
        int aggregators = 0;
        for (Map.Entry<String, TreeSet<String>> e : initialToClasses.entrySet()) {
            writeInitialAggregator(javaRoot, e.getKey(),
                    initialToPascal.get(e.getKey()), e.getValue());
            aggregators++;
        }

        return new Result(skeletons, aggregators);
    }

    // ── Emitters ──────────────────────────────────────────────────────

    static void writeClassSkeleton(Path javaRoot, PinyinRouting.Key key) throws IOException {
        String pkg = DEPTH1_PACKAGE + "." + key.subPackage();
        StringBuilder sb = new StringBuilder();
        sb.append(AUTO_BANNER).append('\n');
        sb.append("package ").append(pkg).append(";\n\n");
        sb.append("import kranji.zi.ComposedZi;\n\n");
        sb.append("import java.util.List;\n\n");
        sb.append("public final class ").append(key.className()).append(" {\n");
        sb.append("    private ").append(key.className()).append("() {}\n\n");
        sb.append("    public static final List<ComposedZi> ALL = List.of();\n");
        sb.append("}\n");
        Path target = javaRoot.resolve(pkg.replace('.', '/'))
                .resolve(key.className() + ".java");
        Files.createDirectories(target.getParent());
        Files.writeString(target, sb.toString(), StandardCharsets.UTF_8);
    }

    static void writeInitialAggregator(Path javaRoot,
                                       String subPkg,
                                       String initialPascal,
                                       TreeSet<String> classNames) throws IOException {
        String simple = "Depth1" + initialPascal;
        StringBuilder sb = new StringBuilder();
        sb.append(AUTO_BANNER).append('\n');
        sb.append("package ").append(DEPTH1_PACKAGE).append(";\n\n");
        sb.append("import kranji.zi.ComposedZi;\n\n");
        sb.append("import java.util.List;\n\n");
        sb.append("public final class ").append(simple).append(" {\n");
        sb.append("    private ").append(simple).append("() {}\n\n");
        // Skeleton: empty list. Step 3 (Depth1GenerateMain) will rewrite
        // this to concatenate the per-(final, tone) classes' ALL lists.
        // Reference the sub-package as a comment for human reviewers.
        sb.append("    // Members (sub-package ").append(subPkg).append("): ");
        sb.append(String.join(", ", classNames)).append("\n");
        sb.append("    public static final List<ComposedZi> ALL = List.of();\n");
        sb.append("}\n");
        Path target = javaRoot.resolve(DEPTH1_PACKAGE.replace('.', '/'))
                .resolve(simple + ".java");
        Files.createDirectories(target.getParent());
        Files.writeString(target, sb.toString(), StandardCharsets.UTF_8);
    }

    /** Convenience for tests: returns the list of unique triple ids. */
    static List<String> uniqueTripleIds() {
        List<String> ids = new ArrayList<>();
        for (ComposedZi zi : Depth1.ALL) {
            PinyinRouting.Key k = PinyinRouting.keyFor(zi.pinyin());
            ids.add(k.subPackage() + "/" + k.className());
        }
        return new ArrayList<>(new TreeSet<>(ids));
    }
}
