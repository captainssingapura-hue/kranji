package kranji.reading.app.read;

import kranji.reading.library.ArticleAddress;
import kranji.reading.library.Libraries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The reader opens on something that is there.
 *
 * <p>The address the reader loads first used to be a literal, and a literal
 * address is a claim about a catalogue that no build checks. It named a demo
 * article; the moment a published root displaced the demo, the reader would
 * have opened on "no article" with every test still green.</p>
 */
class ArticleReaderWidgetTest {

    @Test
    void opensOnAnArticleTheMountedLibraryHas() {
        ArticleAddress opening = ArticleReaderWidget.opensAt();
        assertTrue(Libraries.mounted().tree().find(opening).isPresent(),
                () -> "the reader opens on " + opening
                    + ", which is not in the mounted library '"
                    + Libraries.mounted().name() + "'");
    }

    @Test
    void thatArticleParses() {
        // Resolving is half of it. An address can name a real entry whose text
        // is missing or malformed, and the reader would then open on an error
        // module - which is the same blank screen from the reader's side.
        ArticleAddress opening = ArticleReaderWidget.opensAt();
        String module = ArticleGetAction.moduleFor(opening.toString());
        assertTrue(module.contains("export const blocks = [\n"),
                () -> "opening article served no blocks: " + module);
        assertTrue(!module.contains("export const problem"),
                () -> "opening article served an error module: " + module);
    }
}
