package kranji.codegen.singulars;

import kranji.json.bridge.TypedToUntyped;
import kranji.json.catalog.ZiCatalogLoader;
import kranji.json.dto.SingularPartJson;
import kranji.json.dto.SingularZiJson;
import kranji.library.BasicSet;
import kranji.library.LibraryMember;
import kranji.singular.SingularFamilies;
import kranji.zi.SingularPart;
import kranji.zi.SingularZi;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads every {@code kranji-singulars} family record from {@link BasicSet}
 * and writes one JSON file per record under a target catalog directory,
 * plus a {@code meta.json} manifest per family.
 *
 * <p>Run once to seed
 * {@code kranji-singulars-perclass/src/main/resources/catalog/}. After
 * that the JSON is the source of truth and {@link GenerateMain} is the
 * only tool that touches {@code kranji-singulars-perclass}'s Java sources.
 * </p>
 *
 * <h2>Usage</h2>
 * <pre>
 * mvn -pl kranji-codegen exec:java \
 *   -Dexec.mainClass=kranji.codegen.singulars.SnapshotMain \
 *   -Dexec.args="kranji-singulars-perclass/src/main/resources/catalog"
 * </pre>
 * If no argument is given, the default target is
 * {@code ./kranji-singulars-perclass/src/main/resources/catalog}
 * relative to the current working directory — correct when the command
 * is run from the project root.
 */
public final class SnapshotMain {

    private SnapshotMain() {}

    static final String DEFAULT_OUT =
            "kranji-singulars-perclass/src/main/resources/catalog";

    /** Prefix filter so only kranji-singulars members are snapshotted. */
    static final String SINGULAR_PKG_PREFIX = "kranji.singular.";

    public static void main(String[] args) throws IOException {
        Path outDir = Paths.get(args.length > 0 ? args[0] : DEFAULT_OUT);
        int written = run(outDir);
        System.out.println("Wrote " + written + " record JSON files + meta.json manifests under " + outDir.toAbsolutePath());
    }

    /**
     * Bootstrap all singular families into {@code BasicSet.INSTANCE},
     * partition the members by package, and write one JSON per record
     * plus {@code meta.json} per family.
     *
     * @return the number of record JSON files written (excluding meta.json)
     */
    public static int run(Path outDir) throws IOException {
        // 1. Populate BasicSet.INSTANCE — safe to call multiple times; the
        //    helpers are idempotent thanks to their `loaded` flags.
        BasicSet.INSTANCE.register();
        SingularFamilies.registerInto(BasicSet.INSTANCE);

        // 2. Group members by their family package ("materials", "actions",
        //    …). Skip anything not under `kranji.singular.*` — those come
        //    from kranji-core and are out of scope for this snapshot.
        Map<String, List<LibraryMember<BasicSet>>> byFamily =
                partitionByFamily(BasicSet.INSTANCE.components());

        // 3. Write per-record JSON + meta.json for each family.
        Files.createDirectories(outDir);
        ObjectMapper mapper = ZiCatalogLoader.mapper();
        int totalRecords = 0;
        for (Map.Entry<String, List<LibraryMember<BasicSet>>> e : byFamily.entrySet()) {
            totalRecords += writeFamily(outDir, mapper, e.getKey(), e.getValue());
        }
        return totalRecords;
    }

    // ── Internals ─────────────────────────────────────────────────────

    static Map<String, List<LibraryMember<BasicSet>>> partitionByFamily(
            List<? extends LibraryMember<BasicSet>> members) {
        // TreeMap gives alphabetical family iteration order, which in turn
        // makes the on-disk layout deterministic.
        Map<String, List<LibraryMember<BasicSet>>> out = new java.util.TreeMap<>();
        for (LibraryMember<BasicSet> m : members) {
            String pkg = m.getClass().getPackageName();
            if (!pkg.startsWith(SINGULAR_PKG_PREFIX)) continue;
            String family = pkg.substring(SINGULAR_PKG_PREFIX.length());
            // Strip any further sub-package — `kranji.singular.materials` and
            // `kranji.singular.materials.foo` both roll up to "materials".
            int dot = family.indexOf('.');
            if (dot >= 0) family = family.substring(0, dot);
            out.computeIfAbsent(family, k -> new ArrayList<>()).add(m);
        }
        return out;
    }

    private static int writeFamily(Path outDir,
                                   ObjectMapper mapper,
                                   String family,
                                   List<LibraryMember<BasicSet>> members) throws IOException {
        Path familyDir = outDir.resolve(family);
        Files.createDirectories(familyDir);

        // Sort by class simple name so both the on-disk layout and the
        // meta.json members list are deterministic.
        List<LibraryMember<BasicSet>> sorted = new ArrayList<>(members);
        sorted.sort(Comparator.comparing(m -> m.getClass().getSimpleName()));

        String kind = detectKind(sorted);
        List<String> names = new ArrayList<>(sorted.size());

        for (LibraryMember<BasicSet> m : sorted) {
            String className = m.getClass().getSimpleName();
            Path file = familyDir.resolve(className + ".json");
            Object dto = toDto(m);
            mapper.writeValue(file.toFile(), dto);
            names.add(className);
        }

        // Meta last — so a partial snapshot (interrupted mid-family) never
        // advertises members that weren't written.
        FamilyMetaJson meta = new FamilyMetaJson(family, kind, names);
        mapper.writeValue(familyDir.resolve("meta.json").toFile(), meta);
        return names.size();
    }

    /**
     * A family is "part" if and only if every member is a
     * {@link SingularPart}; otherwise it is "zi". Today the only
     * all-{@code Part} family is {@code radicals/}.
     */
    static String detectKind(List<LibraryMember<BasicSet>> members) {
        boolean anyZi   = false;
        boolean anyPart = false;
        for (LibraryMember<BasicSet> m : members) {
            if (m instanceof SingularZi)        anyZi = true;
            else if (m instanceof SingularPart) anyPart = true;
        }
        if (anyPart && !anyZi) return FamilyMetaJson.KIND_PART;
        return FamilyMetaJson.KIND_ZI;
    }

    static Object toDto(LibraryMember<BasicSet> m) {
        if (m instanceof SingularZi sz)   return TypedToUntyped.singularZi(sz);
        if (m instanceof SingularPart sp) return TypedToUntyped.singularPart(sp);
        throw new IllegalStateException(
                "Unexpected member type: " + m.getClass().getName()
                        + " — SnapshotMain only handles SingularZi / SingularPart");
    }

    /** Exposes the linked-map assembly for tests. */
    static Map<String, Class<?>> firstMemberPerFamily(
            Map<String, List<LibraryMember<BasicSet>>> byFamily) {
        Map<String, Class<?>> out = new LinkedHashMap<>();
        for (var e : byFamily.entrySet()) {
            if (!e.getValue().isEmpty()) {
                out.put(e.getKey(), e.getValue().get(0).getClass());
            }
        }
        return out;
    }
}
