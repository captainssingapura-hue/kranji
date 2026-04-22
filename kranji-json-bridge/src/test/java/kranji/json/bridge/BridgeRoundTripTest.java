package kranji.json.bridge;

import kranji.classification.EtymologicalCategory;
import kranji.json.catalog.LoadResult;
import kranji.json.catalog.ZiCatalog;
import kranji.json.catalog.ZiCatalogLoader;
import kranji.json.dto.ComposedBlockJson;
import kranji.json.dto.ComposedZiJson;
import kranji.zi.ComposedBlock;
import kranji.zi.ComposedPart;
import kranji.zi.CompositionLayout;
import kranji.zi.ComposedZi;
import kranji.zi.SingularBlock;
import kranji.zi.SingularPart;
import kranji.zi.SingularZi;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-end bridge coverage. Loads the shared JSON fixtures from
 * {@code kranji-test-fixtures}, resolves them through {@link TypedZiCatalog}
 * into typed {@link SingularZi} / {@link SingularPart} / {@link ComposedZi},
 * asserts structural correctness, and confirms a round-trip back to JSON
 * preserves the key fields.
 */
final class BridgeRoundTripTest {

    private static TypedZiCatalog load(String resource) throws Exception {
        try (InputStream in = BridgeRoundTripTest.class.getResourceAsStream(resource)) {
            assertNotNull(in, resource + " not found on test classpath");
            LoadResult result = ZiCatalogLoader.load(in);
            assertTrue(result.isClean(), () -> "unexpected warnings: " + result.warnings());
            return TypedZiCatalog.of(result.catalog());
        }
    }

    // ── Biáng — deep composition, extended-BMP glyph ──────────────────

    @Test
    void biangResolvesAsTypedComposedZi() throws Exception {
        TypedZiCatalog typed = load("/biang.json");
        String biangGlyph = new String(Character.toChars(0x30EDE));

        ComposedZi biang = typed.composedZi(biangGlyph).orElseThrow();

        assertEquals(58, biang.strokes());
        assertEquals(162, biang.radicalNo());
        assertEquals(biangGlyph, biang.character());

        // Root composition: semi-enclosure bottom-left wrapping a top_middle_bottom.
        assertInstanceOf(CompositionLayout.SemiEnclosureBottomLeft.class, biang.composition());
        CompositionLayout.SemiEnclosureBottomLeft outer =
                (CompositionLayout.SemiEnclosureBottomLeft) biang.composition();

        // Wrapper is 辶 — a SingularPart resolved from the catalog's part pool.
        assertInstanceOf(SingularPart.class, outer.wrapper());
        assertEquals("辶", outer.wrapper().glyph());

        // Content is a nested TopMiddleBottom wrapped in a ComposedPart.
        assertInstanceOf(ComposedPart.class, outer.content());
        CompositionLayout contentLayout = ((ComposedPart) outer.content()).composition();
        assertInstanceOf(CompositionLayout.TopMiddleBottom.class, contentLayout);

        // Spot-check a deep leaf: the 心 under content.bottom.bottom.
        CompositionLayout.TopMiddleBottom content =
                (CompositionLayout.TopMiddleBottom) contentLayout;
        CompositionLayout.TopMiddleBottom contentBottom =
                (CompositionLayout.TopMiddleBottom)
                        ((ComposedPart) content.bottom()).composition();
        assertInstanceOf(SingularZi.class, contentBottom.bottom());
        assertEquals("心", contentBottom.bottom().glyph());
    }

    @Test
    void biangRoundTripsThroughBridge() throws Exception {
        TypedZiCatalog typed = load("/biang.json");
        String biangGlyph = new String(Character.toChars(0x30EDE));

        ComposedZi biang = typed.composedZi(biangGlyph).orElseThrow();
        ComposedZiJson rebuilt = TypedToUntyped.composedZi(biang);

        assertEquals(biangGlyph, rebuilt.glyph());
        assertEquals(58, rebuilt.strokes());
        assertEquals(162, rebuilt.radicalNo());
        assertEquals("semi_enclosure_bottom_left", rebuilt.composition().style());
        assertInstanceOf(EtymologicalCategory.CompoundIndicative.class,
                UntypedToTyped.etymology(rebuilt.etymology(), typed));
    }

    // ── Reduplicated set — every composition style variant ────────────

    @Test
    void reduplicatedLeavesResolveAsTypedSingularZi() throws Exception {
        TypedZiCatalog typed = load("/reduplicated.json");

        // Each leaf resolves via the typed Zi pool and carries its field data.
        SingularZi kou = typed.singularZi("口").orElseThrow();
        assertEquals(3, kou.strokes());
        assertEquals(30, kou.radicalNo());
        assertEquals("mouth", kou.meaning());

        SingularZi ren = typed.singularZi("人").orElseThrow();
        assertEquals(2, ren.strokes());
        assertEquals(9, ren.radicalNo());
    }

    @Test
    void reduplicatedTwoFoldResolvesWithBothSlotsTyped() throws Exception {
        TypedZiCatalog typed = load("/reduplicated.json");

        // 吅 = 口 left_right 口 — both slots must resolve to the same SingularZi.
        ComposedZi xuan = typed.composedZi("吅").orElseThrow();
        assertInstanceOf(CompositionLayout.LeftRight.class, xuan.composition());
        CompositionLayout.LeftRight lr = (CompositionLayout.LeftRight) xuan.composition();

        assertInstanceOf(SingularZi.class, lr.left());
        assertInstanceOf(SingularZi.class, lr.right());
        assertEquals("口", lr.left().glyph());
        assertEquals("口", lr.right().glyph());

        // Memoisation: the same typed instance is reused for each ref.
        assertEquals(lr.left(), lr.right());
    }

    @Test
    void reduplicatedThreeFoldHasNestedTypedComposition() throws Exception {
        TypedZiCatalog typed = load("/reduplicated.json");

        // 品 = top_bottom { 口, left_right{ 口, 口 } } — the nested slot must
        // also be a typed CompositionLayout.LeftRight.
        ComposedZi pin = typed.composedZi("品").orElseThrow();
        assertInstanceOf(CompositionLayout.TopBottom.class, pin.composition());
        CompositionLayout.TopBottom outer = (CompositionLayout.TopBottom) pin.composition();

        assertEquals("口", outer.top().glyph());
        assertInstanceOf(ComposedPart.class, outer.bottom());
        CompositionLayout.LeftRight bottom =
                (CompositionLayout.LeftRight) ((ComposedPart) outer.bottom()).composition();
        assertEquals("口", bottom.left().glyph());
        assertEquals("口", bottom.right().glyph());
    }

    @Test
    void reduplicatedRoundTripsStyleAndStrokes() throws Exception {
        TypedZiCatalog typed = load("/reduplicated.json");

        String[] glyphs = { "吅", "炎", "朋", "从", "品", "晶", "焱", "垚" };
        ZiCatalog original = typed.source();

        for (String g : glyphs) {
            ComposedZi typedForm = typed.composedZi(g).orElseThrow();
            ComposedZiJson rebuilt = TypedToUntyped.composedZi(typedForm);
            ComposedZiJson originalDto = original.composedZi(g).orElseThrow();

            assertEquals(originalDto.glyph(),   rebuilt.glyph(),   g + " glyph");
            assertEquals(originalDto.strokes(), rebuilt.strokes(), g + " strokes");
            assertEquals(originalDto.composition().style(),
                    rebuilt.composition().style(),
                    g + " style");
        }
    }

    // ── Sample catalog — exercises SingularPart with derivedFrom ──────

    @Test
    void samplePartDerivedFromResolvesTyped() throws Exception {
        TypedZiCatalog typed = load("/sample-catalog.json");

        // 氵 derives from 水; the typed part should carry a resolved SingularZi.
        SingularPart san = typed.singularPart("氵").orElseThrow();
        assertEquals("水", san.standalone());
        assertNotNull(san.derivedFrom(),
                "derivedFrom should be resolved when 水 is in the Zi pool");
        assertEquals("水", san.derivedFrom().character());
    }

    // ── BlockResolver contract ────────────────────────────────────────

    @Test
    void resolverFallsBackFromZiToPartPool() throws Exception {
        TypedZiCatalog typed = load("/sample-catalog.json");

        // 日 is in the Zi pool.
        SingularBlock zi = typed.resolve("日");
        assertInstanceOf(SingularZi.class, zi);

        // 氵 is only in the Part pool — resolver must still find it.
        SingularBlock part = typed.resolve("氵");
        assertInstanceOf(SingularPart.class, part);

        // Unknown glyph → null.
        assertEquals(null, typed.resolve("☃"));
    }
}
