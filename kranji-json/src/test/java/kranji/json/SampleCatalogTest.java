package kranji.json;

import kranji.json.catalog.LoadResult;
import kranji.json.catalog.ZiCatalogLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SampleCatalogTest {

    @Test
    void sampleCatalogLoadsCleanly() throws Exception {
        try (var in = getClass().getResourceAsStream("/sample-catalog.json")) {
            assertNotNull(in, "sample-catalog.json not found on test classpath");

            LoadResult result = ZiCatalogLoader.load(in);
            assertTrue(result.isClean(),
                    () -> "unexpected warnings: " + result.warnings());

            var cat = result.catalog();
            assertEquals(5, cat.composedZiCount());
            assertEquals(5, cat.singularZiCount());
            assertEquals(1, cat.singularPartCount());

            // Cross-pool smoke: 日 present as SingularZi and referenced by 明.
            assertTrue(cat.singularZi("日").isPresent());
            assertTrue(cat.composedZi("明").isPresent());
            assertTrue(cat.composedZi("森").isPresent());     // inline-composed slot
            assertTrue(cat.singularPart("氵").isPresent());
        }
    }
}
