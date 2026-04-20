package kranji.codegen.depth1;

import com.fasterxml.jackson.databind.ObjectMapper;
import kranji.codegen.routing.PinyinRouting;
import kranji.json.catalog.ZiCatalogLoader;
import kranji.json.dto.ComposedZiJson;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Step 3 of the Batch-3 depth1 migration: read the JSON catalog under
 * {@code kranji-codegen/src/main/resources/catalog/depth1/} and emit the
 * fully-populated {@code kranji.common.depth1.*} source tree.
 *
 * <p>Strict invariant: <strong>zero hand-edits</strong>. Output is a pure
 * function of the JSON catalog plus the singular-glyph reverse-index built
 * by {@link SingularZiIndex}. Re-running this main on unchanged inputs
 * must produce byte-identical output.</p>
 *
 * <h2>What it writes</h2>
 * <ul>
 *   <li>Per-(initial, final, tone) Zi-bearing classes at
 *       {@code kranji-common-depth1/src/main/java/kranji/common/depth1/<initial>/<Final><Tone>.java}.
 *       Each contains one {@code public static final ComposedZi <FIELD_NAME>}
 *       per JSON record routed there, plus a deterministic {@code ALL} list.</li>
 *   <li>Per-initial aggregators
 *       {@code kranji.common.depth1.Depth1<InitialPascal>} concatenating
 *       their sub-package's class {@code ALL} lists.</li>
 *   <li>The top-level {@code kranji.common.depth1.Depth1} aggregator
 *       concatenating every {@code Depth1<InitialPascal>.ALL}.</li>
 * </ul>
 *
 * <h2>Usage</h2>
 * <pre>
 * mvn -pl kranji-codegen exec:java \
 *   -Dexec.mainClass=kranji.codegen.depth1.Depth1GenerateMain
 * </pre>
 * Optional argument: target {@code kranji-common-depth1} module root.
 * Default: {@code kranji-common-depth1} relative to the project root.
 */
public final class Depth1GenerateMain {

    private Depth1GenerateMain() {}

    static final String DEFAULT_TARGET_MODULE = "kranji-common-depth1";
    static final String DEFAULT_CATALOG_MODULE = "kranji-codegen";
    static final String CATALOG_REL_PATH = "src/main/resources/catalog/depth1";
    static final String DEPTH1_PACKAGE = "kranji.common.depth1";

    public static void main(String[] args) throws IOException {
        Path targetModule = Paths.get(args.length > 0 ? args[0] : DEFAULT_TARGET_MODULE);
        Path catalogModule = Paths.get(args.length > 1 ? args[1] : DEFAULT_CATALOG_MODULE);
        Result r = run(targetModule, catalogModule);
        System.out.println(String.format(
                "Wrote %d per-(final, tone) classes + %d initial aggregators + Depth1.java "
                        + "(covering %d records) under %s",
                r.classesWritten(), r.aggregatorsWritten(), r.recordsLoaded(),
                targetModule.resolve("src/main/java").toAbsolutePath()));
    }

    public record Result(int classesWritten, int aggregatorsWritten, int recordsLoaded) {}

    public static Result run(Path targetModule, Path catalogModule) throws IOException {
        Path javaRoot = targetModule.resolve("src/main/java");
        Path depth1Root = javaRoot.resolve(DEPTH1_PACKAGE.replace('.', '/'));
        Path catalogDir = catalogModule.resolve(CATALOG_REL_PATH);

        // ① Build the singular reverse-index once.
        SingularZiIndex index = SingularZiIndex.build();

        // ② Load meta.json + every per-record JSON, grouped by (subPkg, className).
        ObjectMapper mapper = ZiCatalogLoader.mapper();
        Path metaFile = catalogDir.resolve("meta.json");
        Depth1MetaJson meta;
        try (var reader = Files.newBufferedReader(metaFile, StandardCharsets.UTF_8)) {
            meta = mapper.readValue(reader, Depth1MetaJson.class);
        }

        // Sub-package -> class name -> list of members
        Map<String, Map<String, List<Depth1JavaEmitter.Member>>> grouped = new TreeMap<>();
        // Sub-package -> initial pascal (for the per-initial aggregator name)
        Map<String, String> initialPascal = new LinkedHashMap<>();
        Set<String> seenIds = new HashSet<>();

        int recordCount = 0;
        for (String memberId : meta.members()) {
            if (!seenIds.add(memberId)) {
                throw new IllegalStateException("Duplicate member id in meta.json: " + memberId);
            }
            String[] parts = memberId.split("/");
            if (parts.length != 3) {
                throw new IllegalStateException(
                        "Malformed member id (expected <subPkg>/<Class>/<Field>): " + memberId);
            }
            String subPkg    = parts[0];
            String className = parts[1];
            String fieldName = parts[2];

            Path jsonFile = catalogDir.resolve(subPkg).resolve(className).resolve(fieldName + ".json");
            if (!Files.exists(jsonFile)) {
                throw new IllegalStateException(
                        "meta.json lists " + memberId + " but " + jsonFile
                                + " does not exist");
            }
            ComposedZiJson dto;
            try (var reader = Files.newBufferedReader(jsonFile, StandardCharsets.UTF_8)) {
                dto = mapper.readValue(reader, ComposedZiJson.class);
            }

            // Trust the catalog: meta.json's <subPkg>/<Class>/<Field> tuple
            // is the authoritative routing record (it was produced by
            // PinyinRouting.keyFor at snapshot time). The orchestrator
            // never re-parses the JSON pinyin to avoid coupling to the
            // PinyinSyllable.parse round-trip behaviour for empty finals
            // (the syllabic-consonant case).

            grouped.computeIfAbsent(subPkg, k -> new TreeMap<>())
                    .computeIfAbsent(className, k -> new ArrayList<>())
                    .add(new Depth1JavaEmitter.Member(fieldName, dto));
            initialPascal.putIfAbsent(subPkg, PinyinRouting.pascalFromSubPackage(subPkg));
            recordCount++;
        }

        // ③ Wipe the depth1 source tree so removed entries don't leave
        //    stale files behind. We delete the per-initial sub-directories
        //    and the top-level Depth1*.java files; everything is rewritten.
        wipeDepth1Tree(depth1Root);
        Files.createDirectories(depth1Root);

        // ④ Emit per-(initial, final, tone) classes.
        int classesWritten = 0;
        for (var initialEntry : grouped.entrySet()) {
            String subPkg = initialEntry.getKey();
            String pkgName = DEPTH1_PACKAGE + "." + subPkg;
            Path subPkgDir = depth1Root.resolve(subPkg);
            Files.createDirectories(subPkgDir);

            for (var classEntry : initialEntry.getValue().entrySet()) {
                String className = classEntry.getKey();
                String source = Depth1JavaEmitter.emitClass(
                        pkgName, className, classEntry.getValue(), index);
                Path target = subPkgDir.resolve(className + ".java");
                Files.writeString(target, source, StandardCharsets.UTF_8);
                classesWritten++;
            }
        }

        // ⑤ Emit per-initial aggregators.
        int aggregatorsWritten = 0;
        List<String> initialAggregatorNames = new ArrayList<>();
        for (var entry : grouped.entrySet()) {
            String subPkg = entry.getKey();
            String pascal = initialPascal.get(subPkg);
            String aggName = "Depth1" + pascal;
            List<String> classNames = new ArrayList<>(new TreeSet<>(entry.getValue().keySet()));
            String source = Depth1JavaEmitter.emitInitialAggregator(
                    DEPTH1_PACKAGE, aggName, subPkg, classNames);
            Path target = depth1Root.resolve(aggName + ".java");
            Files.writeString(target, source, StandardCharsets.UTF_8);
            initialAggregatorNames.add(aggName);
            aggregatorsWritten++;
        }

        // ⑥ Emit the top-level Depth1.java.
        initialAggregatorNames.sort(Comparator.naturalOrder());
        String depth1Source = Depth1JavaEmitter.emitTopLevelAggregator(
                DEPTH1_PACKAGE, initialAggregatorNames);
        Path depth1File = depth1Root.resolve("Depth1.java");
        Files.writeString(depth1File, depth1Source, StandardCharsets.UTF_8);

        return new Result(classesWritten, aggregatorsWritten, recordCount);
    }

    /**
     * Delete every regenerated artefact under {@code depth1Root}: the
     * per-initial sub-directories, every {@code Depth1<Initial>.java}
     * aggregator, and {@code Depth1.java} itself. Leaves the legacy
     * {@code Depth1Strokes*.java} files alone — those are removed by the
     * step-4 manual cleanup once the new tree is verified.
     */
    private static void wipeDepth1Tree(Path depth1Root) throws IOException {
        if (!Files.isDirectory(depth1Root)) return;
        try (var entries = Files.list(depth1Root)) {
            for (Path p : entries.toList()) {
                String name = p.getFileName().toString();
                if (Files.isDirectory(p)) {
                    // Per-initial sub-package — wipe entirely.
                    deleteRecursively(p);
                } else if (name.equals("Depth1.java")
                        || (name.startsWith("Depth1") && name.endsWith(".java")
                            && !name.startsWith("Depth1Strokes"))) {
                    Files.deleteIfExists(p);
                }
            }
        }
    }

    private static void deleteRecursively(Path root) throws IOException {
        try (var walk = Files.walk(root)) {
            walk.sorted(Comparator.reverseOrder()).forEach(p -> {
                try { Files.deleteIfExists(p); } catch (IOException ignored) {}
            });
        }
    }
}
