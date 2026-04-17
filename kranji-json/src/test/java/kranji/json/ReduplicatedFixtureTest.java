package kranji.json;

import kranji.json.catalog.LoadResult;
import kranji.json.catalog.ZiCatalog;
import kranji.json.catalog.ZiCatalogLoader;
import kranji.json.dto.ComposedZiJson;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Loads {@code reduplicated.json} from the shared {@code kranji-test-fixtures}
 * jar and verifies every reduplicated-form entry resolves with its expected
 * stroke count. Covers both 2× (simple left_right / top_bottom) and 3× (nested
 * top-over-left_right triangle) composition styles.
 */
final class ReduplicatedFixtureTest {

    private static LoadResult loadReduplicated() throws Exception {
        try (InputStream in = ReduplicatedFixtureTest.class
                .getResourceAsStream("/reduplicated.json")) {
            assertNotNull(in, "reduplicated.json not found on test classpath");
            return ZiCatalogLoader.load(in);
        }
    }

    @Test
    void reduplicatedFixtureLoadsCleanly() throws Exception {
        LoadResult result = loadReduplicated();

        assertTrue(result.isClean(),
                () -> "unexpected warnings: " + result.warnings());

        ZiCatalog cat = result.catalog();
        assertEquals(6, cat.singularZiCount(), "leaf pool");
        assertEquals(0, cat.singularPartCount(), "no radical parts in this fixture");
        assertEquals(8, cat.composedZiCount(), "2× and 3× compositions");
    }

    @Test
    void doubledPairsResolve() throws Exception {
        ZiCatalog cat = loadReduplicated().catalog();

        // glyph, expected-stroke-count — all 2× compositions
        record Case(String glyph, int strokes) {}
        List<Case> cases = List.of(
                new Case("吅", 6),   // double 口, left_right
                new Case("炎", 8),   // double 火, top_bottom
                new Case("朋", 8),   // double 月, left_right
                new Case("从", 4)    // double 人, left_right (simplified)
        );

        for (Case c : cases) {
            ComposedZiJson entry = cat.composedZi(c.glyph())
                    .orElseThrow(() -> new AssertionError("missing: " + c.glyph()));
            assertEquals(c.strokes(), entry.strokes(), c.glyph() + " strokes");
        }
    }

    @Test
    void tripledTriangleFormsResolve() throws Exception {
        ZiCatalog cat = loadReduplicated().catalog();

        // All use style = top_bottom with bottom being left_right { same, same }.
        record Case(String glyph, int strokes) {}
        List<Case> cases = List.of(
                new Case("品", 9),    // triple 口
                new Case("晶", 12),   // triple 日
                new Case("焱", 12),   // triple 火
                new Case("垚", 9)     // triple 土
        );

        for (Case c : cases) {
            ComposedZiJson entry = cat.composedZi(c.glyph())
                    .orElseThrow(() -> new AssertionError("missing: " + c.glyph()));
            assertEquals(c.strokes(), entry.strokes(), c.glyph() + " strokes");
            assertEquals("top_bottom", entry.composition().style(),
                    c.glyph() + " should be top_bottom at the root");
        }
    }
}
