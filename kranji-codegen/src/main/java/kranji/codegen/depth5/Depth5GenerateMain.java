package kranji.codegen.depth5;

import com.fasterxml.jackson.databind.ObjectMapper;
import kranji.codegen.depth1.SingularZiIndex;
import kranji.codegen.depth2.ComposedZiIndex;
import kranji.codegen.depth2.Depth2JavaEmitter;
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
 * Step 3 of the depth-5 migration: read the JSON catalog under
 * {@code kranji-codegen/src/main/resources/catalog/depth5/} and emit
 * the fully-populated {@code kranji.common.depth5.*} source tree.
 *
 * <p>The {@link ComposedZiIndex} is built from all four lower depth
 * modules (depth-1, depth-2, depth-3, depth-4), so any composition slot
 * that references a known lower-depth glyph emits as a fully-qualified
 * {@code .structure()} expression rather than being inlined.</p>
 */
public final class Depth5GenerateMain {

    private Depth5GenerateMain() {}

    static final String DEFAULT_TARGET_MODULE  = "kranji-common-depth5";
    static final String DEFAULT_CATALOG_MODULE = "kranji-codegen";
    static final String DEFAULT_DEPTH1_MODULE  = "kranji-common-depth1";
    static final String DEFAULT_DEPTH2_MODULE  = "kranji-common-depth2";
    static final String DEFAULT_DEPTH3_MODULE  = "kranji-common-depth3";
    static final String DEFAULT_DEPTH4_MODULE  = "kranji-common-depth4";
    static final String CATALOG_REL_PATH       = "src/main/resources/catalog/depth5";
    static final String DEPTH5_PACKAGE         = "kranji.common.depth5";
    static final String DEPTH1_PACKAGE         = "kranji.common.depth1";
    static final String DEPTH2_PACKAGE         = "kranji.common.depth2";
    static final String DEPTH3_PACKAGE         = "kranji.common.depth3";
    static final String DEPTH4_PACKAGE         = "kranji.common.depth4";

    public static void main(String[] args) throws IOException {
        Path targetModule  = Paths.get(args.length > 0 ? args[0] : DEFAULT_TARGET_MODULE);
        Path catalogModule = Paths.get(args.length > 1 ? args[1] : DEFAULT_CATALOG_MODULE);
        Path depth1Module  = Paths.get(args.length > 2 ? args[2] : DEFAULT_DEPTH1_MODULE);
        Path depth2Module  = Paths.get(args.length > 3 ? args[3] : DEFAULT_DEPTH2_MODULE);
        Path depth3Module  = Paths.get(args.length > 4 ? args[4] : DEFAULT_DEPTH3_MODULE);
        Path depth4Module  = Paths.get(args.length > 5 ? args[5] : DEFAULT_DEPTH4_MODULE);
        Result r = run(targetModule, catalogModule, depth1Module, depth2Module, depth3Module, depth4Module);
        System.out.println(String.format(
                "Wrote %d per-(final, tone) classes + %d initial aggregators + Depth5.java "
                        + "(covering %d records) under %s",
                r.classesWritten(), r.aggregatorsWritten(), r.recordsLoaded(),
                targetModule.resolve("src/main/java").toAbsolutePath()));
    }

    public record Result(int classesWritten, int aggregatorsWritten, int recordsLoaded) {}

    public static Result run(Path targetModule,
                             Path catalogModule,
                             Path depth1Module,
                             Path depth2Module,
                             Path depth3Module,
                             Path depth4Module) throws IOException {
        Path javaRoot   = targetModule.resolve("src/main/java");
        Path depth5Root = javaRoot.resolve(DEPTH5_PACKAGE.replace('.', '/'));
        Path catalogDir = catalogModule.resolve(CATALOG_REL_PATH);

        SingularZiIndex singulars = SingularZiIndex.build();
        ComposedZiIndex composed = ComposedZiIndex.buildFromModules(List.of(
                new ComposedZiIndex.ModuleSpec(depth1Module, DEPTH1_PACKAGE),
                new ComposedZiIndex.ModuleSpec(depth2Module, DEPTH2_PACKAGE),
                new ComposedZiIndex.ModuleSpec(depth3Module, DEPTH3_PACKAGE),
                new ComposedZiIndex.ModuleSpec(depth4Module, DEPTH4_PACKAGE)
        ));

        ObjectMapper mapper = ZiCatalogLoader.mapper();
        Path metaFile = catalogDir.resolve("meta.json");
        Depth5MetaJson meta;
        try (var reader = Files.newBufferedReader(metaFile, StandardCharsets.UTF_8)) {
            meta = mapper.readValue(reader, Depth5MetaJson.class);
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
                        "meta.json lists " + memberId + " but " + jsonFile + " does not exist");
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

        wipeDepth5Tree(depth5Root);
        Files.createDirectories(depth5Root);

        int classesWritten = 0;
        for (var initialEntry : grouped.entrySet()) {
            String subPkg = initialEntry.getKey();
            String pkgName = DEPTH5_PACKAGE + "." + subPkg;
            Path subPkgDir = depth5Root.resolve(subPkg);
            Files.createDirectories(subPkgDir);

            for (var classEntry : initialEntry.getValue().entrySet()) {
                String className = classEntry.getKey();
                String source = Depth2JavaEmitter.emitClass(
                        5, pkgName, className, classEntry.getValue(), singulars, composed);
                Path target = subPkgDir.resolve(className + ".java");
                Files.writeString(target, source, StandardCharsets.UTF_8);
                classesWritten++;
            }
        }

        int aggregatorsWritten = 0;
        List<String> initialAggregatorNames = new ArrayList<>();
        for (var entry : grouped.entrySet()) {
            String subPkg = entry.getKey();
            String pascal = initialPascal.get(subPkg);
            String aggName = "Depth5" + pascal;
            List<String> classNames = new ArrayList<>(new TreeSet<>(entry.getValue().keySet()));
            String source = Depth2JavaEmitter.emitInitialAggregator(
                    5, DEPTH5_PACKAGE, aggName, subPkg, classNames);
            Path target = depth5Root.resolve(aggName + ".java");
            Files.writeString(target, source, StandardCharsets.UTF_8);
            initialAggregatorNames.add(aggName);
            aggregatorsWritten++;
        }

        initialAggregatorNames.sort(Comparator.naturalOrder());
        String depth5Source = Depth2JavaEmitter.emitTopLevelAggregator(
                5, DEPTH5_PACKAGE, initialAggregatorNames);
        Path depth5File = depth5Root.resolve("Depth5.java");
        Files.writeString(depth5File, depth5Source, StandardCharsets.UTF_8);

        return new Result(classesWritten, aggregatorsWritten, recordCount);
    }

    /**
     * Delete every regenerated artefact under {@code depth5Root}. Leaves
     * {@code Depth5Registry.java} alone (hand-written runtime class).
     */
    private static void wipeDepth5Tree(Path depth5Root) throws IOException {
        if (!Files.isDirectory(depth5Root)) return;
        try (var entries = Files.list(depth5Root)) {
            for (Path p : entries.toList()) {
                String name = p.getFileName().toString();
                if (Files.isDirectory(p)) {
                    deleteRecursively(p);
                } else if (name.equals("Depth5.java")
                        || (name.startsWith("Depth5") && name.endsWith(".java")
                            && !name.equals("Depth5Registry.java"))) {
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
