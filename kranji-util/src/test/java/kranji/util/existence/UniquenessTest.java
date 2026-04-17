package kranji.util.existence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Guardrail: every glyph must be defined at most once across the strong-
 * typed pools. {@link StrongTypedRegistry#build()} enforces this at
 * construction time by collecting every definer for every glyph and
 * throwing if any glyph has more than one. This test simply drives that
 * build and fails with the registry's own diagnostic message if a dupe
 * has crept in.
 *
 * <p>Catches the class of mistake where the same character ends up declared
 * in two places — for example, a {@link ComposedZi} added in
 * {@code Depth1StrokesHigh} whose glyph is already a {@code SingularZi}
 * under one of the {@code kranji-singulars} families (the {@code 武/奉/表/责}
 * near-miss during the 8-stroke batch). The {@link IllegalStateException}
 * from the registry lists every offending glyph together with all its
 * definers so the author can collapse to a single canonical declaration.</p>
 *
 * <p>Within-pool dupes (two records with the same glyph in the same pool)
 * and cross-pool dupes (same glyph as both {@code SingularZi} and
 * {@code SingularPart}, etc.) are both treated as errors — there's no
 * caller that wants ambiguity here.</p>
 */
final class UniquenessTest {

    @Test
    void strongTypedRegistryBuildsWithoutDuplicateGlyphs() {
        StrongTypedRegistry registry = assertDoesNotThrow(
                StrongTypedRegistry::instance,
                "strong-typed registry construction should succeed; see the " +
                "IllegalStateException for the list of duplicated glyphs");

        // Non-triviality: the registry actually has content, so the absence
        // of duplicates above is meaningful.
        org.junit.jupiter.api.Assertions.assertTrue(registry.singularZiCount()   > 0,
                "expected populated SingularZi pool");
        org.junit.jupiter.api.Assertions.assertTrue(registry.composedZiCount()   > 0,
                "expected populated ComposedZi pool");
    }
}
