package kranji.json;

import kranji.json.catalog.LoadResult;
import kranji.json.catalog.ZiCatalogLoader;
import kranji.json.dto.BlockRefJson;
import kranji.json.dto.CatalogJson;
import kranji.json.dto.ComposedBlockJson;
import kranji.json.dto.ComposedZiJson;
import kranji.json.dto.EtymologyJson;
import kranji.json.dto.PinyinJson;
import kranji.json.dto.SingularPartJson;
import kranji.json.dto.SingularZiJson;
import kranji.json.dto.ZiCharForm;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RoundTripTest {

    @Test
    void composedZi_glyphRefSlots() throws Exception {
        var qing = new ComposedZiJson(
                "清", ZiCharForm.LITERAL, 11, 85,
                new PinyinJson("q", "ing", 1),
                "clear",
                new ComposedBlockJson("left_right", Map.of(
                        "left",  new BlockRefJson.GlyphRef("氵"),
                        "right", new BlockRefJson.GlyphRef("青"))),
                new EtymologyJson.PhonoSemantic(
                        new BlockRefJson.GlyphRef("氵"),
                        new BlockRefJson.GlyphRef("青")));

        var ming = new ComposedZiJson(
                "明", ZiCharForm.LITERAL, 8, 72,
                new PinyinJson("m", "ing", 2),
                "bright",
                new ComposedBlockJson("left_right", Map.of(
                        "left",  new BlockRefJson.GlyphRef("日"),
                        "right", new BlockRefJson.GlyphRef("月"))),
                new EtymologyJson.CompoundIndicative("sun + moon = bright"));

        var shuiPart = new SingularPartJson("氵", "三点水", 3, "shuǐ", "water radical", "水");

        var catalog = new CatalogJson("1",
                List.of(),
                List.of(shuiPart),
                List.of(qing, ming));

        String json = ZiCatalogLoader.toJson(catalog);
        LoadResult result = ZiCatalogLoader.load(json);

        assertTrue(result.isClean(), () -> "unexpected warnings: " + result.warnings());
        assertEquals(qing,     result.catalog().composedZi("清").orElseThrow());
        assertEquals(ming,     result.catalog().composedZi("明").orElseThrow());
        assertEquals(shuiPart, result.catalog().singularPart("氵").orElseThrow());
    }

    @Test
    void composedZi_inlineComposedSlot() throws Exception {
        // 松 written with an inline composition for the right side (no registry lookup needed).
        var rightInline = new ComposedBlockJson("top_bottom", Map.of(
                "top",    new BlockRefJson.GlyphRef("八"),
                "bottom", new BlockRefJson.GlyphRef("厶")));

        var song = new ComposedZiJson(
                "松", ZiCharForm.LITERAL, 8, 75, null,
                "pine",
                new ComposedBlockJson("left_right", Map.of(
                        "left",  new BlockRefJson.GlyphRef("木"),
                        "right", new BlockRefJson.InlineComposed(rightInline))),
                new EtymologyJson.Pictograph());

        var catalog = new CatalogJson("1", List.of(), List.of(), List.of(song));

        String json = ZiCatalogLoader.toJson(catalog);
        LoadResult result = ZiCatalogLoader.load(json);

        assertTrue(result.isClean(), () -> "unexpected warnings: " + result.warnings());
        assertEquals(song, result.catalog().composedZi("松").orElseThrow());
    }

    @Test
    void composedZi_inlineSingularSlot() throws Exception {
        // A slot expressed as a full SingularPartJson (no registry reference).
        var inlineSan = new SingularPartJson("氵", "三点水", 3, "shuǐ", "water radical", "水");

        var qing = new ComposedZiJson(
                "清", ZiCharForm.LITERAL, 11, 85,
                new PinyinJson("q", "ing", 1),
                "clear",
                new ComposedBlockJson("left_right", Map.of(
                        "left",  new BlockRefJson.InlineSingular(inlineSan),
                        "right", new BlockRefJson.GlyphRef("青"))),
                new EtymologyJson.PhonoSemantic(
                        new BlockRefJson.InlineSingular(inlineSan),
                        new BlockRefJson.GlyphRef("青")));

        var catalog = new CatalogJson("1", List.of(), List.of(), List.of(qing));

        String json = ZiCatalogLoader.toJson(catalog);
        LoadResult result = ZiCatalogLoader.load(json);

        assertTrue(result.isClean(), () -> "unexpected warnings: " + result.warnings());
        assertEquals(qing, result.catalog().composedZi("清").orElseThrow());
    }

    @Test
    void singularZi_roundTripEtymology() throws Exception {
        var shui = new SingularZiJson(
                "水", "U+6C34", "shui", 4, 85, "shuǐ",
                new PinyinJson("sh", "uei", 3),
                "water",
                new EtymologyJson.Pictograph());

        var catalog = new CatalogJson("1", List.of(shui), List.of(), List.of());
        var result  = ZiCatalogLoader.load(ZiCatalogLoader.toJson(catalog));

        assertTrue(result.isClean());
        assertEquals(shui, result.catalog().singularZi("水").orElseThrow());
    }
}
