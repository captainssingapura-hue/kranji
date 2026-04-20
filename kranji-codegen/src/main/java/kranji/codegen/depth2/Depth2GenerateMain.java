package kranji.codegen.depth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import kranji.codegen.depth1.SingularZiIndex;
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
 * Step 3 of the Batch-4 depth2 migration: read the JSON catalog under
 * {@code kranji-codegen/src/main/resources/catalog/depth2/} and emit
 * the fully-populated {@code kranji.common.depth2.*} source tree.
 *
 * <p>Strict invariant: <strong>zero hand-edits</strong>. Output is a
 * pure function of:</p>
 * <ul>
 *   <li>the JSON catalog,</li>
 *   <li>the {@link SingularZiIndex} (singular glyph → Java reference),</li>
 *   <li>the depth-1 {@link ComposedZiIndex} (depth-1 glyph → Java
 *       reference, expanded to {@code .structure()} at slot positions).</li>
 * </ul>
 * Re-running on unchanged inputs must produce byte-identical output.
 *
 * <h2>What it writes</h2>
 * <ul>
 *   <li>Per-(initial, final, tone) Zi-bearing classes at
 *       {@code kranji-common-depth2/src/main/java/kranji/common/depth2/<initial>/<Final><Tone>.java}.</li>
 *   <li>Per-initial aggregators
 *       {@code kranji.common.depth2.Depth2<InitialPascal>}.</li>
 *   <li>The top-level {@code kranji.common.depth2.Depth2} aggregator.</li>
 * </ul>
 *
 * <p>Leaves the legacy {@code Depth2Strokes6.java}, {@code Depth2Strokes7.java},
 * {@code Depth2StrokesHigh.java} intact — they're removed by the manual
 * cleanup once the new tree is verified.</p>
 *
 * <h2>Usage</h2>
 * <pre>
 * mvn -pl kranji-codegen exec:java \
 *   -Dexec.mainClass=kranji.codegen.depth2.Depth2GenerateMain
 * </pre>
 * Optional arguments: {@code <kranji-common-depth2 root> <kranji-codegen root> <kranji-common-depth1 root>}.
 */
public final class Depth2GenerateMain {

    private Depth2GenerateMain() {}

    static final String DEFAULT_TARGET_MODULE  = "kranji-common-depth2";
    static final String DEFAULT_CATALOG_MODULE = "kranji-codegen";
    static final String DEFAULT_DEPTH1_MODULE  = "kranji-common-depth1";
    static final String CATALOG_REL_PATH       = "src/main/resources/catalog/depth2";
    static final String DEPTH2_PACKAGE         = "kranji.common.depth2";
    static final String DEPTH1_PACKAGE         = "kranji.common.depth1";

    public static void main(String[] args) throws IOException {
        Path targetModule  = Paths.get(args.length > 0 ? args[0] : DEFAULT_TARGET_MODULE);
        Path catalogModule = Paths.get(args.length > 1 ? args[1] : DEFAULT_CATALOG_MODULE);
        Path depth1Module  = Paths.get(args.length > 2 ? args[2] : DEFAULT_DEPTH1_MODULE);
        Result r = run(targetModule, catalogModule, depth1Module);
        System.out.println(String.format(
                "Wrote %d per-(final, tone) classes + %d initial aggregators + Depth2.java "
                        + "(covering %d records) under %s",
                r.classesWritten(), r.aggregatorsWritten(), r.recordsLoaded(),
                targetModule.resolve("src/main/java").toAbsolutePath()));
    }

    public record Result(int classesWritten, int aggregatorsWritten, int recordsLoaded) {}

    public static Result run(Path targetModule, Path catalogModule, Path depth1Module) throws IOException {
        Path javaRoot   = targetModule.resolve("src/main/java");
        Path depth2Root = javaRoot.resolve(DEPTH2_PACKAGE.replace('.', '/'));
        Path catalogDir = catalogModule.resolve(CATALOG_REL_PATH);

        // ① Indexes.
        SingularZiIndex singulars = SingularZiIndex.build();
        ComposedZiIndex composed  = ComposedZiIndex.buildFromModule(depth1Module, DEPTH1_PACKAGE);

        // ② Load meta.json + every per-record JSON, grouped by (subPkg, className).
        ObjectMapper mapper = ZiCatalogLoader.mapper();
        Path metaFile = catalogDir.resolve("meta.json");
        Depth2MetaJson meta;
        try (var reader = Files.newBufferedReader(metaFile, StandardCharsets.UTF_8)) {
            meta = mapper.readValue(reader, Depth2MetaJson.class);
        }

        Map<String, Map<String, List<Depth2JavaEmitter.Member>>> grouped = new TreeMap<>();
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

            grouped.computeIfAbsent(subPkg, k -> new TreeMap<>())
                    .computeIfAbsent(className, k -> new ArrayList<>())
                    .add(new Depth2JavaEmitter.Member(fieldName, dto));
            initialPascal.putIfAbsent(subPkg, PinyinRouting.pascalFromSubPackage(subPkg));
            recordCount++;
        }

        // ③ Wipe existing per-initial dirs + Depth2*.java aggregators (keep
        //    legacy Depth2Strokes*.java for step-5 manual deletion).
        wipeDepth2Tree(depth2Root);
        Files.createDirectories(depth2Root);

        // ④ Emit per-(initial, final, tone) classes.
        int classesWritten = 0;
        for (var initialEntry : grouped.entrySet()) {
            String subPkg = initialEntry.getKey();
            String pkgName = DEPTH2_PACKAGE + "." + subPkg;
            Path subPkgDir = depth2Root.resolve(subPkg);
            Files.createDirectories(subPkgDir);

            for (var classEntry : initialEntry.getValue().entrySet()) {
                String className = classEntry.getKey();
                String source = Depth2JavaEmitter.emitClass(
                        pkgName, className, classEntry.getValue(), singulars, composed);
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
            String aggName = "Depth2" + pascal;
            List<String> classNames = new ArrayList<>(new TreeSet<>(entry.getValue().keySet()));
            String source = Depth2JavaEmitter.emitInitialAggregator(
                    DEPTH2_PACKAGE, aggName, subPkg, classNames);
            Path target = depth2Root.resolve(aggName + ".java");
            Files.writeString(target, source, StandardCharsets.UTF_8);
            initialAggregatorNames.add(aggName);
            aggregatorsWritten++;
        }

        // ⑥ Emit the top-level Depth2.java.
        initialAggregatorNames.sort(Comparator.naturalOrder());
        String depth2Source = Depth2JavaEmitter.emitTopLevelAggregator(
                DEPTH2_PACKAGE, initialAggregatorNames);
        Path depth2File = depth2Root.resolve("Depth2.java");
        Files.writeString(depth2File, depth2Source, StandardCharsets.UTF_8);

        return new Result(classesWritten, aggregatorsWritten, recordCount);
    }

    /**
     * Delete every regenerated artefact under {@code depth2Root}: the
     * per-initial sub-directories, every {@code Depth2<Initial>.java}
     * aggregator, and {@code Depth2.java} itself. Leaves the legacy
     * {@code Depth2Strokes*.java} files alone — those go in step 5.
     * Also leaves {@code Depth2Registry.java} alone since it's a
     * hand-written runtime class.
     */
    private static void wipeDepth2Tree(Path depth2Root) throws IOException {
        if (!Files.isDirectory(depth2Root)) return;
        try (var entries = Files.list(depth2Root)) {
            for (Path p : entries.toList()) {
                String name = p.getFileName().toString();
                if (Files.isDirectory(p)) {
                    deleteRecursively(p);
                } else if (name.equals("Depth2.java")
                        || (name.startsWith("Depth2") && name.endsWith(".java")
                            && !name.startsWith("Depth2Strokes")
                            && !name.equals("Depth2Registry.java"))) {
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
