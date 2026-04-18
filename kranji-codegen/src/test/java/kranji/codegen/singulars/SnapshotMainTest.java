package kranji.codegen.singulars;

import com.fasterxml.jackson.databind.ObjectMapper;
import kranji.json.catalog.ZiCatalogLoader;
import kranji.json.dto.SingularPartJson;
import kranji.json.dto.SingularZiJson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SnapshotMainTest {

    /**
     * End-to-end: run the bootstrap snapshot against a temp directory and
     * validate that every expected family directory exists, contains a
     * {@code meta.json} with alphabetised members, and the first-entry
     * JSON round-trips cleanly back to a DTO.
     */
    @Test
    void snapshots_all_13_singular_families(@TempDir Path tmp) throws IOException {
        int total = SnapshotMain.run(tmp);

        // 13 singular-family sub-directories — hard-wired in
        // kranji.singular.SingularFamilies#registerInto.
        List<String> expectedFamilies = List.of(
                "actions", "animals", "body", "concepts", "materials",
                "nature",  "numbers", "people", "plants",   "radicals",
                "space",   "structures", "tools"
        );

        int meta = 0;
        ObjectMapper mapper = ZiCatalogLoader.mapper();
        for (String fam : expectedFamilies) {
            Path dir = tmp.resolve(fam);
            assertTrue(Files.isDirectory(dir), () -> "missing family dir: " + fam);

            Path metaFile = dir.resolve("meta.json");
            assertTrue(Files.exists(metaFile), () -> "missing meta.json in " + fam);
            meta++;

            FamilyMetaJson metaJson = mapper.readValue(metaFile.toFile(), FamilyMetaJson.class);
            assertEquals(fam, metaJson.family());
            assertFalse(metaJson.members().isEmpty(), () -> "empty members list for " + fam);

            // Members must be alphabetically sorted.
            List<String> sorted = metaJson.members().stream().sorted().toList();
            assertEquals(sorted, metaJson.members(),
                    () -> "members for " + fam + " are not alphabetically sorted");

            // Every listed member must have its own JSON file on disk.
            for (String name : metaJson.members()) {
                Path file = dir.resolve(name + ".json");
                assertTrue(Files.exists(file), () -> "missing per-record JSON: " + file);
            }

            // Round-trip the first record through Jackson to confirm schema.
            String first = metaJson.members().get(0);
            Path firstFile = dir.resolve(first + ".json");
            if (FamilyMetaJson.KIND_ZI.equals(metaJson.kind())) {
                SingularZiJson zi = mapper.readValue(firstFile.toFile(), SingularZiJson.class);
                assertNotNull(zi.glyph(), "glyph required");
                assertFalse(zi.glyph().isEmpty());
            } else {
                SingularPartJson pt = mapper.readValue(firstFile.toFile(), SingularPartJson.class);
                assertNotNull(pt.glyph(), "glyph required");
                assertFalse(pt.glyph().isEmpty());
            }
        }

        assertEquals(13, meta, "expected 13 meta.json files, one per family");
        assertTrue(total > 100,
                () -> "expected a few hundred records; got " + total);
    }

    @Test
    void radicals_family_is_classified_as_part(@TempDir Path tmp) throws IOException {
        SnapshotMain.run(tmp);
        ObjectMapper mapper = ZiCatalogLoader.mapper();
        FamilyMetaJson meta = mapper.readValue(
                tmp.resolve("radicals").resolve("meta.json").toFile(),
                FamilyMetaJson.class);
        assertEquals(FamilyMetaJson.KIND_PART, meta.kind());
    }

    @Test
    void materials_family_is_classified_as_zi(@TempDir Path tmp) throws IOException {
        SnapshotMain.run(tmp);
        ObjectMapper mapper = ZiCatalogLoader.mapper();
        FamilyMetaJson meta = mapper.readValue(
                tmp.resolve("materials").resolve("meta.json").toFile(),
                FamilyMetaJson.class);
        assertEquals(FamilyMetaJson.KIND_ZI, meta.kind());
        assertTrue(meta.members().contains("Yi_Clothes"),
                "materials should include the Yi_Clothes record");
    }
}
