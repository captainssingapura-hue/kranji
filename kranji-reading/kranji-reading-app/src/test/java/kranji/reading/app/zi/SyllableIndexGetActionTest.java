package kranji.reading.app.zi;

import kranji.pinyin.PinyinSyllable;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The index is the join key between two halves that never meet on a server.
 *
 * <p>The corpus half is here; the known set is on a device. They are matched on
 * the canonical reading and nothing checks that at run time — an index whose
 * readings were subtly wrong would intersect to nothing and report a reader who
 * knows a few hundred characters as knowing none, with no error anywhere.</p>
 */
class SyllableIndexGetActionTest {

    private static final List<SyllableIndexGetAction.Syllable> INDEX =
            SyllableIndexGetAction.syllables();

    @Test
    void everyReadingIsCanonicalAndRoundTrips() {
        for (var s : INDEX) {
            PinyinSyllable parsed = PinyinSyllable.parseCanonical(s.reading());
            assertEquals(s.reading(), parsed.numbered(),
                    () -> s.reading() + " at " + s.path() + " is not what the model writes");
        }
    }

    @Test
    void aZeroInitialIsSpelledAsNothing() {
        // The tree files 安 under "zero"; the model writes "an1".
        var zero = INDEX.stream().filter(s -> "zero".equals(s.initial())).toList();
        assertFalse(zero.isEmpty(), "the corpus has characters with no initial");
        for (var s : zero) {
            assertFalse(s.reading().startsWith("zero"),
                    () -> s.reading() + " carries the projection's name for no initial");
            assertTrue(s.path().startsWith("zero."),
                    () -> s.path() + " must stay the address /zi-data answers to");
        }
    }

    @Test
    void theOrthographyIsTheModelsAndNotTheAddresss() {
        // The fifteen places where joining the segments gives a key that
        // matches nothing. The projection spells ü as v and keeps the
        // underlying finals; pinyin writes yun, iu, ui, un. A key that is
        // subtly wrong intersects to nothing and reports a reader who knows
        // the sound as not knowing it.
        var byPath = INDEX.stream().collect(
                java.util.stream.Collectors.toMap(
                        SyllableIndexGetAction.Syllable::path,
                        SyllableIndexGetAction.Syllable::reading));
        assertEquals("yun4", byPath.get("y.ven.4"));
        assertEquals("yu2",  byPath.get("y.v.2"));
        assertEquals("yuan3", byPath.get("y.van.3"));
        assertEquals("yue4", byPath.get("y.ve.4"));

        // And the finals the projection keeps in their underlying form.
        for (var s : INDEX) {
            assertFalse(s.reading().contains("v"),
                    () -> s.reading() + " is the address spelling, not pinyin");
            assertFalse(s.reading().contains("iou") || s.reading().contains("uei")
                            || s.reading().contains("uen"),
                    () -> s.reading() + " keeps an underlying final pinyin rewrites");
        }
    }

    @Test
    void everySyllableIsThereOnceAndCarriesCharacters() {
        var seen = new HashSet<String>();
        for (var s : INDEX) {
            assertTrue(seen.add(s.reading()), () -> s.reading() + " is indexed twice");
            assertTrue(s.characters() > 0,
                    () -> s.reading() + " is a sound nothing is read as");
        }
    }

    @Test
    void thePathIsThreeSegments() {
        // initial.final.tone, which is what /zi-data takes. A fourth segment or
        // a missing one would load nothing, and the pane would look empty
        // rather than broken.
        for (var s : INDEX) {
            assertEquals(3, s.path().split("\\.").length, s.path());
        }
    }

    @Test
    void theTotalIsTheCorpusTotal() {
        // 8,763 (character, reading) pairs - the same figure the phonic
        // partitions report. A syllable counted twice or dropped shows up here
        // and nowhere else.
        int characters = INDEX.stream().mapToInt(SyllableIndexGetAction.Syllable::characters).sum();
        assertEquals(8763, characters, "the index must account for every pair in the corpus");
        assertEquals(1288, INDEX.size(), "and for every distinct syllable");
    }

    @Test
    void theModuleIsDataOnly() {
        String js = SyllableIndexGetAction.indexJs();
        assertTrue(js.startsWith("// Generated from the Kranji corpus"), js.substring(0, 60));
        assertTrue(js.contains("export const syllables = ["));
        assertTrue(js.contains("export const count = 1288;"));
        assertTrue(js.contains("export const characters = 8763;"));
        for (String behaviour : new String[] { "function", "=>", "if (", "for (" }) {
            assertFalse(js.contains(behaviour), () -> "the module carries " + behaviour);
        }
    }
}
