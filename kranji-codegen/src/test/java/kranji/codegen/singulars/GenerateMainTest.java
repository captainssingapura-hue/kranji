package kranji.codegen.singulars;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerateMainTest {

    /**
     * Source catalog used by every test. Relative to the kranji-codegen
     * module directory, which is the working dir when surefire runs.
     *
     * <p>The catalog lives in the sibling {@code kranji-singulars-perclass}
     * module; it is the committed source of truth since the retirement of
     * the {@code SnapshotMain} bootstrap. Tests copy it into a
     * {@code @TempDir} before calling {@link GenerateMain}.</p>
     */
    private static final Path SOURCE_CATALOG = Path.of(
            "..", "kranji-singulars-perclass", "src", "main", "resources", "catalog");

    /**
     * Copy the committed catalog into {@code moduleRoot/src/main/resources/catalog}
     * and return the number of per-record JSON files copied (i.e. excluding
     * {@code meta.json}). Mirrors the return contract of the old
     * {@code SnapshotMain.run}.
     */
    private static int seedCatalog(Path moduleRoot) throws IOException {
        Path destCatalog = moduleRoot.resolve("src/main/resources/catalog");
        Files.createDirectories(destCatalog);
        AtomicInteger records = new AtomicInteger();
        try (Stream<Path> walk = Files.walk(SOURCE_CATALOG)) {
            for (Path src : (Iterable<Path>) walk::iterator) {
                Path rel = SOURCE_CATALOG.relativize(src);
                Path dst = destCatalog.resolve(rel.toString());
                if (Files.isDirectory(src)) {
                    Files.createDirectories(dst);
                } else {
                    Files.createDirectories(dst.getParent());
                    Files.copy(src, dst);
                    String name = src.getFileName().toString();
                    if (name.endsWith(".json") && !"meta.json".equals(name)) {
                        records.incrementAndGet();
                    }
                }
            }
        }
        return records.get();
    }

    /**
     * End-to-end: seed the committed catalog into the temp moduleRoot,
     * regenerate, and assert the Java file count and registrar existence.
     */
    @Test
    void catalog_then_generate_roundtrip(@TempDir Path moduleRoot) throws IOException {
        int total = seedCatalog(moduleRoot);
        GenerateMain.Result r = GenerateMain.run(moduleRoot);

        assertEquals(total, r.written(),
                "every catalog record should be emitted as a .java file");
        assertEquals(0, r.skipped(),
                "no PARTIALLY GENERATED files present on a clean temp dir");
        assertEquals(13, r.aggregators(),
                "one aggregator per family");

        // Registrar must exist.
        Path registrar = moduleRoot.resolve(
                "src/main/java/kranji/singular/SingularFamiliesPerclass.java");
        assertTrue(Files.exists(registrar), "registrar file must be emitted");

        // Spot-check one known record's layout.
        Path yiClothes = moduleRoot.resolve(
                "src/main/java/kranji/singular/materials/zero/Yi_Clothes.java");
        assertTrue(Files.exists(yiClothes),
                "Yi_Clothes should land in the 'zero' initial sub-package");
        String src = Files.readString(yiClothes, StandardCharsets.UTF_8);
        assertTrue(src.contains("public record Yi_Clothes()"));
        assertTrue(src.contains("package kranji.singular.materials.zero;"));
        assertTrue(src.contains("\"\\u8863\""),   // 衣 escaped
                "glyph should be Unicode-escaped");

        // Materials aggregator
        Path materials = moduleRoot.resolve(
                "src/main/java/kranji/singular/materials/MaterialsSingulars.java");
        assertTrue(Files.exists(materials));
        String mat = Files.readString(materials, StandardCharsets.UTF_8);
        assertTrue(mat.contains("public final class MaterialsSingulars"));
        assertTrue(mat.contains("Yi_Clothes.INSTANCE"));
    }

    /** The partial-banner guard must prevent re-emission. */
    @Test
    void skips_files_marked_partially_generated(@TempDir Path moduleRoot) throws IOException {
        seedCatalog(moduleRoot);
        GenerateMain.run(moduleRoot);

        // Poison a known file: swap its banner.
        Path target = moduleRoot.resolve(
                "src/main/java/kranji/singular/materials/zero/Yi_Clothes.java");
        String original = Files.readString(target, StandardCharsets.UTF_8);
        String poisoned = original.replaceFirst("^// AUTO-GENERATED.*",
                JavaEmitter.PARTIAL_BANNER + " bye");
        Files.writeString(target, poisoned);

        GenerateMain.Result r = GenerateMain.run(moduleRoot);
        assertTrue(r.skipped() >= 1, "at least one file should be skipped");

        String afterSecondRun = Files.readString(target, StandardCharsets.UTF_8);
        assertEquals(poisoned, afterSecondRun,
                "PARTIALLY GENERATED file must survive untouched");
    }

    @Test
    void aggregatorSimpleName_capitalises_and_suffixes_with_Singulars() {
        // The "Singulars" suffix avoids FQN collision with legacy classes
        // like kranji.singular.structures.Structures (which has a different
        // shape and would shadow the per-class aggregator on the classpath).
        assertEquals("MaterialsSingulars",  GenerateMain.aggregatorSimpleName("materials"));
        assertEquals("RadicalsSingulars",   GenerateMain.aggregatorSimpleName("radicals"));
        assertEquals("BodySingulars",       GenerateMain.aggregatorSimpleName("body"));
        assertEquals("StructuresSingulars", GenerateMain.aggregatorSimpleName("structures"));
    }

    /** The registrar must import every aggregator and call addAll. */
    @Test
    void registrar_wires_all_families(@TempDir Path moduleRoot) throws IOException {
        seedCatalog(moduleRoot);
        GenerateMain.run(moduleRoot);

        String src = Files.readString(moduleRoot.resolve(
                "src/main/java/kranji/singular/SingularFamiliesPerclass.java"),
                StandardCharsets.UTF_8);

        List<String> mustContain = List.of(
                "import kranji.singular.actions.ActionsSingulars;",
                "import kranji.singular.materials.MaterialsSingulars;",
                "import kranji.singular.radicals.RadicalsSingulars;",
                "basicSet.addAll(ActionsSingulars.ALL);",
                "basicSet.addAll(RadicalsSingulars.ALL);"
        );
        for (String s : mustContain) {
            assertTrue(src.contains(s), () -> "registrar missing: " + s);
        }
        assertFalse(src.contains("ALL.ALL"),
                "no typo duplication");
    }
}
