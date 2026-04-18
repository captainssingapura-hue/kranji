package kranji.codegen.singulars;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerateMainTest {

    /**
     * End-to-end: snapshot + regenerate into the same temp module root;
     * assert the Java file count and registrar existence.
     */
    @Test
    void snapshot_then_generate_roundtrip(@TempDir Path moduleRoot) throws IOException {
        Path catalog = moduleRoot.resolve("src/main/resources/catalog");
        Files.createDirectories(catalog);

        int total = SnapshotMain.run(catalog);
        GenerateMain.Result r = GenerateMain.run(moduleRoot);

        assertEquals(total, r.written(),
                "every snapshotted record should be emitted as a .java file");
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
                "src/main/java/kranji/singular/materials/Materials.java");
        assertTrue(Files.exists(materials));
        String mat = Files.readString(materials, StandardCharsets.UTF_8);
        assertTrue(mat.contains("public final class Materials"));
        assertTrue(mat.contains("Yi_Clothes.INSTANCE"));
    }

    /** The partial-banner guard must prevent re-emission. */
    @Test
    void skips_files_marked_partially_generated(@TempDir Path moduleRoot) throws IOException {
        Path catalog = moduleRoot.resolve("src/main/resources/catalog");
        Files.createDirectories(catalog);
        SnapshotMain.run(catalog);
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
    void aggregatorSimpleName_capitalises_family_name() {
        assertEquals("Materials", GenerateMain.aggregatorSimpleName("materials"));
        assertEquals("Radicals",  GenerateMain.aggregatorSimpleName("radicals"));
        assertEquals("Body",      GenerateMain.aggregatorSimpleName("body"));
    }

    /** The registrar must import every aggregator and call addAll. */
    @Test
    void registrar_wires_all_families(@TempDir Path moduleRoot) throws IOException {
        Path catalog = moduleRoot.resolve("src/main/resources/catalog");
        Files.createDirectories(catalog);
        SnapshotMain.run(catalog);
        GenerateMain.run(moduleRoot);

        String src = Files.readString(moduleRoot.resolve(
                "src/main/java/kranji/singular/SingularFamiliesPerclass.java"),
                StandardCharsets.UTF_8);

        List<String> mustContain = List.of(
                "import kranji.singular.actions.Actions;",
                "import kranji.singular.materials.Materials;",
                "import kranji.singular.radicals.Radicals;",
                "basicSet.addAll(Actions.ALL);",
                "basicSet.addAll(Radicals.ALL);"
        );
        for (String s : mustContain) {
            assertTrue(src.contains(s), () -> "registrar missing: " + s);
        }
        assertFalse(src.contains("ALL.ALL"),
                "no typo duplication");
    }
}
