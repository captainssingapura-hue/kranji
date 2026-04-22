package kranji.codegen.singulars;

import com.fasterxml.jackson.databind.ObjectMapper;
import kranji.json.catalog.ZiCatalogLoader;
import kranji.json.dto.SingularPartJson;
import kranji.json.dto.SingularZiJson;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Reads the JSON catalog at
 * {@code kranji-singulars-perclass/src/main/resources/catalog/} and emits
 * one Java record class per entry into
 * {@code kranji-singulars-perclass/src/main/java/kranji/singular/...},
 * plus a per-family aggregator class and the top-level registrar
 * {@code SingularFamiliesPerclass}.
 *
 * <p>Skips files whose first line starts with {@link JavaEmitter#PARTIAL_BANNER}
 * so hand-ported overrides survive re-runs.</p>
 *
 * <h2>Usage</h2>
 * <pre>
 * mvn -pl kranji-codegen exec:java \
 *   -Dexec.mainClass=kranji.codegen.singulars.GenerateMain \
 *   -Dexec.args="kranji-singulars-perclass"
 * </pre>
 * The argument is the {@code kranji-singulars-perclass} module root; the
 * tool derives {@code src/main/resources/catalog} (input) and
 * {@code src/main/java} (output) from it. Default is the module's
 * conventional path relative to the project root.
 */
public final class GenerateMain {

    private GenerateMain() {}

    static final String DEFAULT_MODULE_ROOT = "kranji-singulars-perclass";
    static final String REGISTRAR_PACKAGE   = "kranji.singular";
    static final String SINGULAR_PREFIX     = "kranji.singular";

    public static void main(String[] args) throws IOException {
        Path moduleRoot = Paths.get(args.length > 0 ? args[0] : DEFAULT_MODULE_ROOT);
        Result r = run(moduleRoot);
        System.out.println(String.format(
                "Wrote %d member files (%d skipped as PARTIALLY GENERATED), %d aggregators, 1 registrar, under %s",
                r.written(), r.skipped(), r.aggregators(),
                moduleRoot.resolve("src/main/java").toAbsolutePath()));
    }

    public record Result(int written, int skipped, int aggregators) {}

    public static Result run(Path moduleRoot) throws IOException {
        Path catalogDir = moduleRoot.resolve("src/main/resources/catalog");
        Path javaRoot   = moduleRoot.resolve("src/main/java");
        if (!Files.isDirectory(catalogDir)) {
            throw new IOException("catalog directory not found: " + catalogDir.toAbsolutePath());
        }

        ObjectMapper mapper = ZiCatalogLoader.mapper();
        List<String> aggregatorFQNs = new ArrayList<>();
        int written = 0, skipped = 0;

        // Iterate families in alphabetical order so output is deterministic.
        try (var dirs = Files.list(catalogDir)) {
            List<Path> families = new ArrayList<>();
            dirs.filter(Files::isDirectory).forEach(families::add);
            families.sort(java.util.Comparator.comparing(p -> p.getFileName().toString()));

            for (Path familyDir : families) {
                FamilyMetaJson meta = mapper.readValue(
                        familyDir.resolve("meta.json").toFile(), FamilyMetaJson.class);

                EmitCounts c = emitFamily(mapper, familyDir, javaRoot, meta);
                written += c.written;
                skipped += c.skipped;
                aggregatorFQNs.add(SINGULAR_PREFIX + "." + meta.family() + "."
                        + aggregatorSimpleName(meta.family()));
            }
        }

        // Top-level registrar
        String registrarSrc = JavaEmitter.emitRegistrar(REGISTRAR_PACKAGE, aggregatorFQNs);
        Path registrarPath = javaRoot.resolve("kranji/singular/SingularFamiliesPerclass.java");
        writeIfAllowed(registrarPath, registrarSrc);

        return new Result(written, skipped, aggregatorFQNs.size());
    }

    // ── Per-family emit ───────────────────────────────────────────────

    private record EmitCounts(int written, int skipped) {}

    private static EmitCounts emitFamily(ObjectMapper mapper,
                                         Path familyDir,
                                         Path javaRoot,
                                         FamilyMetaJson meta) throws IOException {
        int written = 0, skipped = 0;
        List<String> memberFQNs = new ArrayList<>();

        for (String className : meta.members()) {
            Path jsonFile = familyDir.resolve(className + ".json");
            if (!Files.exists(jsonFile)) {
                throw new IOException("member " + className + " listed in meta.json for "
                        + meta.family() + " but file missing: " + jsonFile);
            }

            // Derive the initial sub-package from pinyinText (or default
            // to "zero" when absent).
            String subPkg = pickSubPackage(mapper, jsonFile, meta.kind());
            String pkg    = SINGULAR_PREFIX + "." + meta.family() + "." + subPkg;
            memberFQNs.add(pkg + "." + className);

            Path outFile = classPath(javaRoot, pkg, className);

            String source;
            if (FamilyMetaJson.KIND_PART.equals(meta.kind())) {
                SingularPartJson data = mapper.readValue(jsonFile.toFile(), SingularPartJson.class);
                source = JavaEmitter.emitSingularPart(pkg, className, data);
            } else {
                SingularZiJson data = mapper.readValue(jsonFile.toFile(), SingularZiJson.class);
                source = JavaEmitter.emitSingularZi(pkg, className, data);
            }

            if (writeIfAllowed(outFile, source)) written++;
            else                                 skipped++;
        }

        // Per-family aggregator (<Family>.java)
        String aggPkg       = SINGULAR_PREFIX + "." + meta.family();
        String aggClassName = aggregatorSimpleName(meta.family());
        String aggSource    = JavaEmitter.emitFamilyAggregator(aggPkg, aggClassName, memberFQNs);
        Path   aggPath      = classPath(javaRoot, aggPkg, aggClassName);
        writeIfAllowed(aggPath, aggSource);

        return new EmitCounts(written, skipped);
    }

    // ── Helpers ───────────────────────────────────────────────────────

    private static String pickSubPackage(ObjectMapper mapper,
                                         Path jsonFile,
                                         String kind) throws IOException {
        String pinyinText;
        if (FamilyMetaJson.KIND_PART.equals(kind)) {
            SingularPartJson pt = mapper.readValue(jsonFile.toFile(), SingularPartJson.class);
            pinyinText = pt.pinyinText();
        } else {
            SingularZiJson zi = mapper.readValue(jsonFile.toFile(), SingularZiJson.class);
            pinyinText = zi.pinyinText();
        }
        return pinyinText == null || pinyinText.isEmpty()
                ? InitialPackage.ZERO_PACKAGE
                : InitialPackage.fromPinyinText(pinyinText);
    }

    /**
     * {@code materials} → {@code MaterialsSingulars},
     * {@code radicals} → {@code RadicalsSingulars}.
     *
     * <p>The {@code Singulars} suffix is deliberate: it prevents FQN
     * collisions with legacy {@code kranji-singulars} classes that happen
     * to already own the capitalised name (e.g. the legacy
     * {@code kranji.singular.structures.Structures} is a grouping class
     * with 100+ constants, while this module's aggregator for the same
     * directory holds only {@code ALL}). Calling the per-class aggregator
     * {@code Structures} would shadow that legacy class on the classpath
     * and break every caller compiled against its fields.</p>
     *
     * <p>The family directory names from {@link SnapshotMain} are already
     * single lowercase words.</p>
     */
    static String aggregatorSimpleName(String family) {
        if (family.isEmpty()) return family;
        return Character.toUpperCase(family.charAt(0)) + family.substring(1) + "Singulars";
    }

    /** Resolve {@code javaRoot / (pkg as path) / (className).java}. */
    private static Path classPath(Path javaRoot, String pkg, String className) {
        return javaRoot.resolve(pkg.replace('.', '/')).resolve(className + ".java");
    }

    /**
     * Write {@code source} to {@code target} unless the existing file is
     * marked {@link JavaEmitter#PARTIAL_BANNER}. Returns {@code true}
     * when the file was (re)written.
     */
    static boolean writeIfAllowed(Path target, String source) throws IOException {
        if (Files.exists(target) && isPartiallyGenerated(target)) return false;
        Files.createDirectories(target.getParent());
        // Normalise newlines to LF so diffs look consistent across platforms.
        Files.writeString(target, source, StandardCharsets.UTF_8);
        return true;
    }

    static boolean isPartiallyGenerated(Path target) throws IOException {
        // Cheap: read the first line only.
        try (var reader = Files.newBufferedReader(target, StandardCharsets.UTF_8)) {
            String first = reader.readLine();
            return first != null && first.startsWith("// PARTIALLY GENERATED");
        }
    }

    /** Expose dedup helper for tests. */
    static List<String> dedupSorted(List<String> xs) {
        return new ArrayList<>(new TreeSet<>(xs));
    }
}
