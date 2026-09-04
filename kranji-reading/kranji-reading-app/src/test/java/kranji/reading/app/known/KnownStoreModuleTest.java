package kranji.reading.app.known;

import hue.captains.singapura.js.homing.ssjs.test.JsModuleTestBase;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The boundary between the set and the disk.
 *
 * <p>GraalVM has no IndexedDB, so what is exercised here is the key guard —
 * which is where the logic is. The rest of the store is a transaction wrapper;
 * a malformed key is the failure that would actually cost a reader something,
 * because a key matching nothing does not throw, it silently withholds the
 * support a child had earned.</p>
 */
class KnownStoreModuleTest extends JsModuleTestBase {

    private static final String MODULE =
            "/homing/js/kranji/reading/app/known/KnownStoreModule.js";

    private Value store;

    @BeforeEach
    void load() {
        loadModule(MODULE);
        store = global("KnownStore");
    }

    private boolean isKey(Object k) {
        return store.getMember("isKey").execute(k).asBoolean();
    }

    @Test
    void acceptsWhatTheSetActuallyProduces() {
        assertTrue(isKey("34892:xíng"));
        assertTrue(isKey("22909:hǎo"));
        assertTrue(isKey("183955:x"), "a supplementary codepoint is six digits, not four");
        assertTrue(isKey("22320:de"), "a toneless reading is still a reading");
    }

    @Test
    void rejectsAKeyWithNoCodepoint() {
        assertFalse(isKey(":xíng"));
        assertFalse(isKey("xíng"));
    }

    @Test
    void rejectsAKeyWithNoReading() {
        // The half that decides whether pinyin is shown. Without it the key
        // matches nothing and quietly does nothing.
        assertFalse(isKey("34892:"));
        assertFalse(isKey("34892"));
    }

    @Test
    void rejectsAGlyphWhereTheCodepointBelongs() {
        // The mistake worth guarding: the glyph is what a person would type
        // into a hand-edited export, and it would round-trip as a key that
        // never matches.
        assertFalse(isKey("行:xíng"));
        assertFalse(isKey("U+884C:xíng"));
    }

    @Test
    void rejectsWhatIsNotAString() {
        assertFalse(isKey(null));
        assertFalse(isKey(34892));
        assertFalse(isKey(""));
    }

    @Test
    void theReadingMayContainAnything() {
        // Only the codepoint half is constrained. Readings are the corpus's
        // business, and a store that second-guessed them would reject a
        // spelling the corpus later adds.
        assertTrue(isKey("34892:x:y"), "the first colon separates; later ones are reading");
        assertTrue(isKey("34892:ê̄"));
    }
}
