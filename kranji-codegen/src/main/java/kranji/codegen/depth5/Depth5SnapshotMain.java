package kranji.codegen.depth5;

import com.fasterxml.jackson.databind.ObjectMapper;
import kranji.common.depth1.Depth1;
import kranji.codegen.routing.PinyinRouting;
import kranji.common.depth2.Depth2;
import kranji.common.depth3.Depth3;
import kranji.common.depth4.Depth4;
import kranji.common.depth5.Depth5;
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
 * Step 2 of the depth-5 migration: snapshot every typed {@link ComposedZi}
 * in {@link Depth5#ALL} into a per-record JSON file under
 * {@code kranji-codegen/src/main/resources/catalog/depth5/}.
 */
public final class Depth5SnapshotMain {

    private Depth5SnapshotMain() {}

    static final String DEFAULT_MODULE_ROOT        = "kranji-codegen";
    static final String DEFAULT_DEPTH5_MODULE_ROOT = "kranji-common-depth5";
    static final String CATALOG_REL_PATH           = "src/main/resources/catalog/depth5";
    static final String DEPTH5_PACKAGE             = "kranji.common.depth5";

    public static void main(String[] args) throws IOException {
        Path moduleRoot        = Paths.get(args.length > 0 ? args[0] : DEFAULT_MODULE_ROOT);
        Path depth5ModuleRoot  = Paths.get(args.length > 1 ? args[1] : DEFAULT_DEPTH5_MODULE_ROOT);
        Result r = run(moduleRoot, depth5ModuleRoot);
        System.out.println(String.format(
                "Wrote %d JSON records + meta.json (covering %d initials) under %s",
                r.recordsWritten(), r.initialCount(),
                moduleRoot.resolve(CATALOG_REL_PATH).toAbsolutePath()));
    }

    public record Result(int recordsWritten, int initialCount) {}

    public static Result run(Path moduleRoot, Path depth5ModuleRoot) throws IOException {
        Path catalogDir = moduleRoot.resolve(CATALOG_REL_PATH);
        if (Files.isDirectory(catalogDir)) {
            try (var walk = Files.walk(catalogDir)) {
                walk.sorted(Comparator.reverseOrder()).forEach(p -> {
                    try { Files.deleteIfExists(p); } catch (IOException ignored) {}
                });
            }
        }
        Files.createDirectories(catalogDir);

        Map<ComposedZi, String> idToName = buildIdentityIndex(depth5ModuleRoot);

        // Reference context: every depth-1..4 record's composition instance
        // maps to its glyph.
        IdentityHashMap<ComposedBlock, String> refMap = new IdentityHashMap<>();
        for (ComposedZi z : Depth1.ALL) refMap.put(z, z.character());
        for (ComposedZi z : Depth2.ALL) refMap.put(z, z.character());
        for (ComposedZi z : Depth3.ALL) refMap.put(z, z.character());
        for (ComposedZi z : Depth4.ALL) refMap.put(z, z.character());
        RefContext ctx = new RefContext(refMap);

        ObjectMapper mapper = ZiCatalogLoader.mapper();
        List<String> memberIds = new ArrayList<>();
        java.util.Set<String> initials = new java.util.LinkedHashSet<>();
        java.util.Set<String> seenIds = new java.util.HashSet<>();

        for (ComposedZi zi : Depth5.ALL) {
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
                throw new IllegalStateException("Duplicate catalog id: " + memberId);
            }

            ComposedZiJson json = TypedToUntyped.composedZi(zi, ctx);
            Path target = catalogDir.resolve(subPkg).resolve(className).resolve(fieldName + ".json");
            Files.createDirectories(target.getParent());
            try (var writer = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
                mapper.writeValue(writer, json);
            }
            memberIds.add(memberId);
        }

        memberIds.sort(Comparator.naturalOrder());
        Depth5MetaJson meta = new Depth5MetaJson(5, memberIds);
        Path metaFile = catalogDir.resolve("meta.json");
        try (var writer = Files.newBufferedWriter(metaFile, StandardCharsets.UTF_8)) {
            mapper.writeValue(writer, meta);
        }

        return new Result(memberIds.size(), initials.size());
    }

    private static Map<ComposedZi, String> buildIdentityIndex(Path depth5ModuleRoot) throws IOException {
        Path javaRoot = depth5ModuleRoot.resolve("src/main/java");
        Path depth5Root = javaRoot.resolve(DEPTH5_PACKAGE.replace('.', '/'));
        if (!Files.isDirectory(depth5Root)) {
            throw new IllegalStateException(
                    "Depth-5 source root does not exist: " + depth5Root);
        }

        List<Class<?>> sourceClasses = new ArrayList<>();
        try (var walk = Files.walk(depth5Root)) {
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
                            + " — is kranji-common-depth5 on the classpath?", e);
                }
            }
        }
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
                                            + prior + " and " + f.getName());
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
