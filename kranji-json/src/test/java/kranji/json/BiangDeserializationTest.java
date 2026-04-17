package kranji.json;

import kranji.json.catalog.LoadResult;
import kranji.json.catalog.ZiCatalog;
import kranji.json.catalog.ZiCatalogLoader;
import kranji.json.dto.ComposedZiJson;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Reads {@code biang.json} from the test classpath and confirms the Biáng
 * character (𰻞, U+30EDE) round-trips through {@link ZiCatalogLoader} into a
 * {@link ComposedZiJson} DTO. Pure deserialize-and-look-up smoke test.
 */
final class BiangDeserializationTest {

    /** Biáng — the 58-stroke noodle character. Surrogate pair in Java. */
    private static final String BIANG = new String(Character.toChars(0x30EDE));

    /** Simple utility: load the bundled biang.json into a typed catalog. */
    private static LoadResult loadBiang() throws Exception {
        try (InputStream in = BiangDeserializationTest.class
                .getResourceAsStream("/biang.json")) {
            assertNotNull(in, "biang.json not found on test classpath");
            return ZiCatalogLoader.load(in);
        }
    }

    @Test
    void biangJsonDeserializesCleanly() throws Exception {
        LoadResult result = loadBiang();

        assertTrue(result.isClean(),
                () -> "unexpected warnings: " + result.warnings());

        ZiCatalog cat = result.catalog();
        ComposedZiJson biang = cat.composedZi(BIANG).orElseThrow();

        assertEquals(58, biang.strokes());
        assertEquals(162, biang.radicalNo());
        assertEquals("biáng (a Shaanxi noodle)", biang.meaning());

        // Extended-BMP sanity: the glyph really is a surrogate pair in Java.
        assertEquals(2, BIANG.length(), "𰻞 should be a surrogate pair in Java");
        assertEquals(0x30EDE, BIANG.codePointAt(0));

        // Component pools came through.
        assertEquals(7, cat.singularZiCount());
        assertEquals(2, cat.singularPartCount());
        assertEquals(1, cat.composedZiCount());
    }
}
