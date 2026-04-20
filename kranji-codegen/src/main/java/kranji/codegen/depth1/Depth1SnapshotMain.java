package kranji.codegen.depth1;

import com.fasterxml.jackson.databind.ObjectMapper;
import kranji.common.depth1.Depth1;
import kranji.codegen.routing.PinyinRouting;
import kranji.json.bridge.TypedToUntyped;
import kranji.json.catalog.ZiCatalogLoader;
import kranji.json.dto.ComposedZiJson;
import kranji.zi.ComposedZi;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Step 2 of the Batch-3 depth1 migration: snapshot every typed
 * {@link ComposedZi} in {@link Depth1#ALL} into a per-record JSON file
 * under {@code kranji-codegen/src/main/resources/catalog/depth1/}.
 *
 * <p>The original Java field identifier (e.g. {@code MING}, {@code WO})
 * is recovered by walking
 * {@code kranji-common-depth1/src/main/java/kranji/common/depth1/<initial>/}
 * to discover every per-(final, tone) class, loading each via
 * {@link Class#forName}, and reflecting on its
 * {@code public static final ComposedZi} fields. Records are then matched
 * by reference identity against {@link Depth1#ALL}. The identifier is used
 * both as the JSON file name and — by {@link Depth1GenerateMain} in step 3
 * — as the {@code public static final ComposedZi <NAME>} identifier in the
 * regenerated source.</p>
 *
 * <p>The legacy single-flat-namespace {@code Depth1Strokes3..7} /
 * {@code Depth1StrokesHigh} files were retired by the Batch-3 migration;
 * if you ever resurrect them, also re-add them as source classes here.</p>
 *
 * <h2>Output layout</h2>
 * <pre>
 * catalog/depth1/
 *     meta.json                         { depth: 1, members: ["b/A1/BA", ...] }
 *     b/A1/BA.json                      (ComposedZiJson)
 *     ...
 *     k/Ong1/KONG.json                  (空 kōng)
 *     k/Ong3/KONG.json                  (孔 kǒng — same field name, different class)
 *     ...
 *     zero/Uo3/WO.json
 * </pre>
 * The {@code <Class>} segment matches the {@code <Final><Tone>} class
 * name from {@link PinyinRouting}, so records sharing a Java field name
 * (e.g. {@code KONG}, {@code KUN}) land in distinct directories.
 *
 * <h2>Usage</h2>
 * <pre>
 * mvn -pl kranji-codegen exec:java \
 *   -Dexec.mainClass=kranji.codegen.depth1.Depth1SnapshotMain
 * </pre>
 * Optional argument: target {@code kranji-codegen} module root. Default
 * is the conventional path relative to the project root.
 */
public final class Depth1SnapshotMain {

    private Depth1SnapshotMain() {}

    static final String DEFAULT_MODULE_ROOT       = "kranji-codegen";
    static final String DEFAULT_DEPTH1_MODULE_ROOT = "kranji-common-depth1";
    static final String CATALOG_REL_PATH          = "src/main/resources/catalog/depth1";
    static final String DEPTH1_PACKAGE            = "kranji.common.depth1";

    public static void main(String[] args) throws IOException {
        Path moduleRoot      = Paths.get(args.length > 0 ? args[0] : DEFAULT_MODULE_ROOT);
        Path depth1ModuleRoot = Paths.get(args.length > 1 ? args[1] : DEFAULT_DEPTH1_MODULE_ROOT);
        Result r = run(moduleRoot, depth1ModuleRoot);
        System.out.println(String.format(
                "Wrote %d JSON records + meta.json (covering %d initials) under %s",
                r.recordsWritten(), r.initialCount(),
                moduleRoot.resolve(CATALOG_REL_PATH).toAbsolutePath()));
    }

    public record Result(int recordsWritten, int initialCount) {}

    public static Result run(Path moduleRoot, Path depth1ModuleRoot) throws IOException {
        Path catalogDir = moduleRoot.resolve(CATALOG_REL_PATH);
        // Wipe the directory so removed entries don't leave stale files
        // behind on re-runs. Recreate after.
        if (Files.isDirectory(catalogDir)) {
            try (var walk = Files.walk(catalogDir)) {
                walk.sorted(Comparator.reverseOrder()).forEach(p -> {
                    try { Files.deleteIfExists(p); } catch (IOException ignored) {}
                });
            }
        }
        Files.createDirectories(catalogDir);

        // ① Build identity map: ComposedZi instance → original field name.
        Map<ComposedZi, String> idToName = buildIdentityIndex(depth1ModuleRoot);

        // ② Snapshot every record.
        ObjectMapper mapper = ZiCatalogLoader.mapper();
        List<String> memberIds = new ArrayList<>();
        java.util.Set<String> initials = new java.util.LinkedHashSet<>();
        java.util.Set<String> seenIds = new java.util.HashSet<>();

        for (ComposedZi zi : Depth1.ALL) {
            String fieldName = idToName.get(zi);
            if (fieldName == null) {
                throw new IllegalStateException(
                        "ComposedZi " + zi.character() + " (pinyin "
                                + zi.pinyin().numberedTone()
                                + ") has no source field identifier");
            }
            PinyinRouting.Key key = PinyinRouting.keyFor(zi.pinyin());
            String subPkg = key.subPackage();
            String className = key.className();
            initials.add(subPkg);

            String memberId = subPkg + "/" + className + "/" + fieldName;
            if (!seenIds.add(memberId)) {
                throw new IllegalStateException(
                        "Duplicate catalog id: " + memberId + " — two records share "
                                + "the same (initial, final, tone, fieldName) tuple. "
                                + "Disambiguate by renaming the source field.");
            }

            ComposedZiJson json = TypedToUntyped.composedZi(zi);
            Path target = catalogDir.resolve(subPkg).resolve(className).resolve(fieldName + ".json");
            Files.createDirectories(target.getParent());
            try (var writer = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
                mapper.writeValue(writer, json);
            }
            memberIds.add(memberId);
        }

        // ③ Sort and write meta.json.
        memberIds.sort(Comparator.naturalOrder());
        Depth1MetaJson meta = new Depth1MetaJson(1, memberIds);
        Path metaFile = catalogDir.resolve("meta.json");
        try (var writer = Files.newBufferedWriter(metaFile, StandardCharsets.UTF_8)) {
            mapper.writeValue(writer, meta);
        }

        return new Result(memberIds.size(), initials.size());
    }

    // ── Reflection helper ─────────────────────────────────────────────

    /**
     * Discover every per-(initial, final, tone) class under
     * {@code kranji-common-depth1/src/main/java/kranji/common/depth1/<initial>/}
     * by walking the source tree, then load each via {@link Class#forName}
     * and record its {@code public static final ComposedZi <NAME>} field
     * identities.
     *
     * <p>The aggregator classes ({@link kranji.common.depth1.Depth1} and
     * {@code Depth1<InitialPascal>}) sit at the top of the {@code depth1}
     * package and don't declare {@code ComposedZi} fields, so they're
     * skipped naturally — the directory walk only descends into the
     * per-initial sub-packages.</p>
     */
    private static Map<ComposedZi, String> buildIdentityIndex(Path depth1ModuleRoot) throws IOException {
        Path javaRoot = depth1ModuleRoot.resolve("src/main/java");
        Path depth1Root = javaRoot.resolve(DEPTH1_PACKAGE.replace('.', '/'));
        if (!Files.isDirectory(depth1Root)) {
            throw new IllegalStateException(
                    "Depth-1 source root does not exist: " + depth1Root
                            + ". Pass the kranji-common-depth1 module root as "
                            + "the second argument.");
        }

        List<Class<?>> sourceClasses = new ArrayList<>();
        try (var initials = Files.list(depth1Root)) {
            for (Path initialDir : (Iterable<Path>) initials::iterator) {
                if (!Files.isDirectory(initialDir)) continue;
                String subPkg = initialDir.getFileName().toString();
                try (var files = Files.list(initialDir)) {
                    for (Path javaFile : (Iterable<Path>) files::iterator) {
                        String name = javaFile.getFileName().toString();
                        if (!name.endsWith(".java")) continue;
                        String simple = name.substring(0, name.length() - 5);
                        String fqn = DEPTH1_PACKAGE + "." + subPkg + "." + simple;
                        try {
                            sourceClasses.add(Class.forName(fqn));
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException("Cannot load " + fqn
                                    + " — is kranji-common-depth1 on the classpath?", e);
                        }
                    }
                }
            }
        }
        // Stable scan order for deterministic error messages.
        sourceClasses.sort(Comparator.comparing(Class::getName));

        Map<ComposedZi, String> map = new IdentityHashMap<>();
        for (Class<?> c : sourceClasses) {
            for (Field f : c.getDeclaredFields()) {
                int mods = f.getModifiers();
                if (!Modifier.isStatic(mods) || !Modifier.isFinal(mods)
                        || !Modifier.isPublic(mods)) continue;
                if (!ComposedZi.class.isAssignableFrom(f.getType())) continue;
                try {
                    Object value = f.get(null);
                    if (value instanceof ComposedZi cz) {
                        String prior = map.put(cz, f.getName());
                        if (prior != null && !prior.equals(f.getName())) {
                            throw new IllegalStateException(
                                    "Duplicate identity for ComposedZi: "
                                            + prior + " and " + f.getName()
                                            + " refer to the same instance");
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Reflection failure on "
                            + c.getSimpleName() + "." + f.getName(), e);
                }
            }
        }
        return map;
    }
}
