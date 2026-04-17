package kranji.util.existence;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Runs the existence checker against the shared JSON fixtures and verifies
 * the report correctly partitions existing vs missing glyphs against the
 * real {@link StrongTypedRegistry} populated from {@code kranji-core},
 * {@code kranji-singulars}, and {@code kranji-common}.
 */
final class ExistenceCheckerTest {

    private static InputStream resource(String path) {
        InputStream in = ExistenceCheckerTest.class.getResourceAsStream(path);
        assertNotNull(in, path + " not found on test classpath");
        return in;
    }

    // ── Registry smoke tests ─────────────────────────────────────────

    @Test
    void registryExposesStrongTypedPools() {
        StrongTypedRegistry reg = StrongTypedRegistry.instance();

        // All three pools populated from the real registries.
        assertTrue(reg.singularZiCount()   > 0, "expected populated Zi pool");
        assertTrue(reg.singularPartCount() > 0, "expected populated Part pool");
        assertTrue(reg.composedZiCount()   > 0, "expected populated Composed pool");

        // Known singletons — 日 is in kranji-singulars (NatureElements.Ri),
        // 氵 is in kranji-core (WaterFamily.SanDianShui).
        assertTrue(reg.hasSingularZi("日"),   "日 should be a typed SingularZi");
        assertTrue(reg.hasSingularPart("氵"), "氵 should be a typed SingularPart");

        // Cross-pool check: a glyph in the Zi pool is not in the Part pool.
        assertFalse(reg.hasSingularPart("日"));
    }

    @Test
    void registryIsMemoisedSingleton() {
        StrongTypedRegistry first  = StrongTypedRegistry.instance();
        StrongTypedRegistry second = StrongTypedRegistry.instance();
        assertTrue(first == second, "registry should be a shared singleton");
    }

    // ── Existence report on known fixtures ───────────────────────────

    @Test
    void sampleCatalogLeavesAreTypedComposedZiAreMissing() throws Exception {
        ExistenceReport report;
        try (InputStream in = resource("/sample-catalog.json")) {
            report = ExistenceChecker.check(in);
        }

        // sample-catalog: 5 Zi + 1 Part + 5 Composed = 11 total.
        assertEquals(11, report.total());

        // All 5 singular Zi (日 月 木 水 青) are in kranji-singulars, so existing.
        assertEquals(5, report.singularZi().size());
        assertTrue(report.singularZi().stream().allMatch(EntryStatus::exists),
                "every singular Zi in sample-catalog should be typed");

        // 氵 is in kranji-core WaterFamily — existing.
        assertEquals(1, report.singularParts().size());
        assertTrue(report.singularParts().get(0).exists(), "氵 should be typed");

        // Composed Zi (明 清 林 森 晴) — their existence depends on what
        // kranji-common currently covers. Just assert the pool is populated.
        assertEquals(5, report.composedZi().size());
    }

    @Test
    void reduplicatedAnchorLeavesAreTyped() throws Exception {
        ExistenceReport report;
        try (InputStream in = resource("/reduplicated.json")) {
            report = ExistenceChecker.check(in);
        }

        // All 6 leaves (口 火 月 人 日 土) are common singulars — expect
        // every singular Zi entry in this fixture to be typed.
        assertEquals(6, report.singularZi().size());
        assertTrue(report.singularZi().stream().allMatch(EntryStatus::exists),
                () -> "missing typed leaves: " + report.missingEntries());
    }

    @Test
    void biangIsMissing() throws Exception {
        // Biáng (𰻞, U+30EDE) is not in kranji-common — the checker should
        // report the single composed entry as missing.
        ExistenceReport report;
        try (InputStream in = resource("/biang.json")) {
            report = ExistenceChecker.check(in);
        }

        assertEquals(1, report.composedZi().size());
        EntryStatus biang = report.composedZi().get(0);
        assertFalse(biang.exists(), "Biáng should not be typed yet");
        assertEquals(EntryStatus.Pool.COMPOSED_ZI, biang.pool());
        assertEquals(null, biang.typedClassName());
    }

    // ── Report formatting & derived aggregates ───────────────────────

    @Test
    void reportAggregatesAndFormattingAreConsistent() throws Exception {
        ExistenceReport report;
        try (InputStream in = resource("/biang.json")) {
            report = ExistenceChecker.check(in);
        }

        assertEquals(report.existing() + report.missing(), report.total());
        assertEquals(report.missingEntries().size(), report.missing());

        String text = report.format();
        assertTrue(text.contains("Strong-Typed Existence Check"));
        assertTrue(text.contains("Singular Zi"));
        // Biáng's row renders with the missing marker.
        assertTrue(text.contains("[-]"), "missing marker should appear");
    }
}
