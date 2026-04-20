package kranji.codegen.depth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import kranji.common.depth1.Depth1;
import kranji.codegen.routing.PinyinRouting;
import kranji.common.depth2.Depth2;
import kranji.json.bridge.TypedToUntyped;
import kranji.json.bridge.TypedToUntyped.RefContext;
import kranji.json.catalog.ZiCatalogLoader;
import kranji.json.dto.ComposedZiJson;
import kranji.zi.ComposedBlock;
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
 * Step 2 of the Batch-4 depth2 migration: snapshot every typed
 * {@link ComposedZi} in {@link Depth2#ALL} into a per-record JSON file
 * under {@code kranji-codegen/src/main/resources/catalog/depth2/}.
 *
 * <p>Two ingredients distinguish this from {@code Depth1SnapshotMain}:</p>
 * <ol>
 *   <li><b>Reference context.</b> Depth-2 records embed depth-1
 *       compositions as their child slots (e.g. {@code 远} = 辶 +
 *       {@code Van2.YUAN.structure()}). Inlining those sub-trees would
 *       discard the by-reference intent. We pre-build a
 *       {@link RefContext} mapping every {@code Depth1.ALL} record's
 *       {@link ComposedZi#composition()} instance to its glyph, then
 *       hand it to {@link TypedToUntyped#composedZi(ComposedZi, RefContext)}
 *       so each such child serializes as a {@code GlyphRef}.</li>
 *   <li><b>Recursive source scan.</b> The depth-2 source tree is
 *       currently in its legacy flat layout
 *       ({@code Depth2Strokes6}, {@code Depth2Strokes7},
 *       {@code Depth2StrokesHigh}); after the Batch-4 generation step
 *       it will be {@code <initial>/<Class>.java}. We walk the tree
 *       recursively so the same snapshot tool works for both the
 *       initial bootstrap and round-trip verification.</li>
 * </ol>
 *
 * <h2>Output layout</h2>
 * <pre>
 * catalog/depth2/
 *     meta.json                         { depth: 2, members: ["zero/An2/YUAN_FAR", ...] }
 *     b/An4/BAN_DRESS.json
 *     ...
 *     zero/An2/YUAN_GARDEN.json
 * </pre>
 *
 * <h2>Usage</h2>
 * <pre>
 * mvn -pl kranji-codegen exec:java \
 *   -Dexec.mainClass=kranji.codegen.depth2.Depth2SnapshotMain
 * </pre>
 * Optional argument: target {@code kranji-codegen} module root. Default
 * is the conventional path relative to the project root.
 */
public final class Depth2SnapshotMain {

    private Depth2SnapshotMain() {}

    static final String DEFAULT_MODULE_ROOT       = "kranji-codegen";
    static final String DEFAULT_DEPTH2_MODULE_ROOT = "kranji-common-depth2";
    static final String CATALOG_REL_PATH          = "src/main/resources/catalog/depth2";
    static final String DEPTH2_PACKAGE            = "kranji.common.depth2";

    public static void main(String[] args) throws IOException {
        Path moduleRoot       = Paths.get(args.length > 0 ? args[0] : DEFAULT_MODULE_ROOT);
        Path depth2ModuleRoot = Paths.get(args.length > 1 ? args[1] : DEFAULT_DEPTH2_MODULE_ROOT);
        Result r = run(moduleRoot, depth2ModuleRoot);
        System.out.println(String.format(
                "Wrote %d JSON records + meta.json (covering %d initials) under %s",
                r.recordsWritten(), r.initialCount(),
                moduleRoot.resolve(CATALOG_REL_PATH).toAbsolutePath()));
    }

    public record Result(int recordsWritten, int initialCount) {}

    public static Result run(Path moduleRoot, Path depth2ModuleRoot) throws IOException {
        Path catalogDir = moduleRoot.resolve(CATALOG_REL_PATH);
        // Wipe stale output so re-runs cleanly reflect deletions/renames.
        if (Files.isDirectory(catalogDir)) {
            try (var walk = Files.walk(catalogDir)) {
                walk.sorted(Comparator.reverseOrder()).forEach(p -> {
                    try { Files.deleteIfExists(p); } catch (IOException ignored) {}
                });
            }
        }
        Files.createDirectories(catalogDir);

        // ① Build identity map: ComposedZi instance → original field name.
        Map<ComposedZi, String> idToName = buildIdentityIndex(depth2ModuleRoot);

        // ② Build the depth-1 reference context: every Depth1 record's
        // composition instance maps to the record's glyph. When a depth-2
        // record cites that exact ComposedBlock as a slot (via
        // .structure() on the depth-1 ComposedZi), the bridge emits a
        // GlyphRef instead of inlining the sub-tree.
        IdentityHashMap<ComposedBlock, String> refMap = new IdentityHashMap<>();
        for (ComposedZi d1 : Depth1.ALL) {
            refMap.put(d1, d1.character());
        }
        RefContext ctx = new RefContext(refMap);

        // ③ Snapshot every record.
        ObjectMapper mapper = ZiCatalogLoader.mapper();
        List<String> memberIds = new ArrayList<>();
        java.util.Set<String> initials = new java.util.LinkedHashSet<>();
        java.util.Set<String> seenIds = new java.util.HashSet<>();

        for (ComposedZi zi : Depth2.ALL) {
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

            ComposedZiJson json = TypedToUntyped.composedZi(zi, ctx);
            Path target = catalogDir.resolve(subPkg).resolve(className).resolve(fieldName + ".json");
            Files.createDirectories(target.getParent());
            try (var writer = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
                mapper.writeValue(writer, json);
            }
            memberIds.add(memberId);
        }

        // ④ Sort and write meta.json.
        memberIds.sort(Comparator.naturalOrder());
        Depth2MetaJson meta = new Depth2MetaJson(2, memberIds);
        Path metaFile = catalogDir.resolve("meta.json");
        try (var writer = Files.newBufferedWriter(metaFile, StandardCharsets.UTF_8)) {
            mapper.writeValue(writer, meta);
        }

        return new Result(memberIds.size(), initials.size());
    }

    // ── Reflection helper ─────────────────────────────────────────────

    /**
     * Walk every {@code .java} file under
     * {@code kranji-common-depth2/src/main/java/kranji/common/depth2/}
     * (recursively, so this works for both the legacy {@code Depth2Strokes*}
     * flat layout and the post-Batch-4 {@code <initial>/<Class>.java}
     * layout), load each class, and record every
     * {@code public static final ComposedZi <NAME>} field by reference
     * identity.
     *
     * <p>Aggregator classes ({@code Depth2}, {@code Depth2<Initial>})
     * declare {@code List<ComposedZi>} fields, not bare
     * {@link ComposedZi}, so they're naturally skipped by the field-type
     * filter — no special-case is needed.</p>
     */
    private static Map<ComposedZi, String> buildIdentityIndex(Path depth2ModuleRoot) throws IOException {
        Path javaRoot = depth2ModuleRoot.resolve("src/main/java");
        Path depth2Root = javaRoot.resolve(DEPTH2_PACKAGE.replace('.', '/'));
        if (!Files.isDirectory(depth2Root)) {
            throw new IllegalStateException(
                    "Depth-2 source root does not exist: " + depth2Root
                            + ". Pass the kranji-common-depth2 module root as "
                            + "the second argument.");
        }

        List<Class<?>> sourceClasses = new ArrayList<>();
        try (var walk = Files.walk(depth2Root)) {
            for (Path javaFile : (Iterable<Path>) walk::iterator) {
                if (!Files.isRegularFile(javaFile)) continue;
                String name = javaFile.getFileName().toString();
                if (!name.endsWith(".java")) continue;
                Path rel = javaRoot.relativize(javaFile);
                String relStr = rel.toString().replace('\\', '/');
                String fqn = relStr.substring(0, relStr.length() - 5).replace('/', '.');
                try {
                    sourceClasses.add(Class.forName(fqn));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Cannot load " + fqn
                            + " — is kranji-common-depth2 on the classpath?", e);
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
