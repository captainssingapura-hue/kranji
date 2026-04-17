package kranji.json;

import kranji.json.catalog.LoadResult;
import kranji.json.catalog.ZiCatalog;
import kranji.json.catalog.ZiCatalogLoader;
import kranji.json.dto.BlockRefJson;
import kranji.json.dto.CatalogJson;
import kranji.json.dto.ComposedBlockJson;
import kranji.json.dto.ComposedZiJson;
import kranji.json.dto.EntryJson;
import kranji.json.dto.EtymologyJson;
import kranji.json.dto.PinyinJson;
import kranji.json.dto.SingularPartJson;
import kranji.json.dto.SingularZiJson;
import kranji.json.dto.ZiCharForm;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CatalogLookupTest {

    @Test
    void sameGlyphAcrossPools_allAndAny_returnsAll() {
        var shuiZi = new SingularZiJson(
                "水", "U+6C34", "shui", 4, 85, "shuǐ",
                List.of(new PinyinJson("sh", "uei", 3)),
                "water", new EtymologyJson.Pictograph());
        var shuiPart = new SingularPartJson("水", "水部首", 4, "shuǐ", "water (radical form)", null);

        var cat = ZiCatalog.from(new CatalogJson("1",
                List.of(shuiZi), List.of(shuiPart), List.of()));

        assertTrue(cat.singularZi("水").isPresent());
        assertTrue(cat.singularPart("水").isPresent());
        assertTrue(cat.composedZi("水").isEmpty());

        var all = cat.all("水");
        assertEquals(2, all.size());
        // Priority: Composed > SingularZi > SingularPart.
        assertTrue(all.get(0) instanceof EntryJson.OfSingularZi);
        assertTrue(all.get(1) instanceof EntryJson.OfSingularPart);

        var any = cat.any("水").orElseThrow();
        assertTrue(any instanceof EntryJson.OfSingularZi);
    }

    @Test
    void samePoolDuplicate_surfacesWarning_lastWins() throws Exception {
        var v1 = new ComposedZiJson("清", ZiCharForm.LITERAL, 11, 85, List.of(), "clear (v1)",
                new ComposedBlockJson("left_right", Map.of(
                        "left",  new BlockRefJson.GlyphRef("氵"),
                        "right", new BlockRefJson.GlyphRef("青"))),
                new EtymologyJson.Pictograph());
        var v2 = new ComposedZiJson("清", ZiCharForm.LITERAL, 11, 85, List.of(), "clear (v2)",
                new ComposedBlockJson("left_right", Map.of(
                        "left",  new BlockRefJson.GlyphRef("氵"),
                        "right", new BlockRefJson.GlyphRef("青"))),
                new EtymologyJson.Pictograph());

        var catalog = new CatalogJson("1", List.of(), List.of(), List.of(v1, v2));

        LoadResult result = ZiCatalogLoader.load(ZiCatalogLoader.toJson(catalog));
        assertFalse(result.isClean());
        assertTrue(result.warnings().stream()
                        .anyMatch(w -> w.contains("清") && w.contains("composedZi")),
                () -> "expected dup warning for 清 in composedZi; got " + result.warnings());
        // last-wins
        assertEquals("clear (v2)", result.catalog().composedZi("清").orElseThrow().meaning());
    }

    @Test
    void slotKeysMismatch_emitsWarning() throws Exception {
        var bogus = new ComposedZiJson("X", ZiCharForm.LITERAL, 0, 0, List.of(), "",
                new ComposedBlockJson("left_right", Map.of(
                        "top", new BlockRefJson.GlyphRef("Y"))),  // wrong key for left_right
                new EtymologyJson.Pictograph());

        var catalog = new CatalogJson("1", List.of(), List.of(), List.of(bogus));
        LoadResult result = ZiCatalogLoader.load(ZiCatalogLoader.toJson(catalog));

        assertFalse(result.isClean());
        assertTrue(result.warnings().stream().anyMatch(w -> w.contains("slot keys")),
                () -> "expected slot-keys warning; got " + result.warnings());
    }

    @Test
    void unknownStyle_emitsWarning() throws Exception {
        var bogus = new ComposedZiJson("X", ZiCharForm.LITERAL, 0, 0, List.of(), "",
                new ComposedBlockJson("wraparound_spiral", Map.of(
                        "a", new BlockRefJson.GlyphRef("Y"))),
                new EtymologyJson.Pictograph());

        var catalog = new CatalogJson("1", List.of(), List.of(), List.of(bogus));
        LoadResult result = ZiCatalogLoader.load(ZiCatalogLoader.toJson(catalog));

        assertFalse(result.isClean());
        assertTrue(result.warnings().stream().anyMatch(w -> w.contains("unknown composition style")),
                () -> "expected unknown-style warning; got " + result.warnings());
    }

    @Test
    void byStrokeCount_and_byRadical() {
        var eightRadical72 = new ComposedZiJson("明", ZiCharForm.LITERAL, 8, 72, List.of(), "bright",
                new ComposedBlockJson("left_right", Map.of(
                        "left",  new BlockRefJson.GlyphRef("日"),
                        "right", new BlockRefJson.GlyphRef("月"))),
                new EtymologyJson.CompoundIndicative("sun + moon"));
        var elevenRadical85 = new ComposedZiJson("清", ZiCharForm.LITERAL, 11, 85, List.of(), "clear",
                new ComposedBlockJson("left_right", Map.of(
                        "left",  new BlockRefJson.GlyphRef("氵"),
                        "right", new BlockRefJson.GlyphRef("青"))),
                new EtymologyJson.Pictograph());

        var cat = ZiCatalog.from(new CatalogJson("1",
                List.of(), List.of(), List.of(eightRadical72, elevenRadical85)));

        assertEquals(1, cat.byStrokeCount(8).size());
        assertEquals(1, cat.byStrokeCount(11).size());
        assertEquals(0, cat.byStrokeCount(99).size());
        assertEquals(1, cat.byRadical(72).size());
        assertEquals(1, cat.byRadical(85).size());
        assertEquals(2, cat.composedZiCount());
    }
}
