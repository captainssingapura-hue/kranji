package kranji.simple;

import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tone;
import kranji.simple.common2000.H;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleZiTest {

    private static final SimpleZiDsl H_DSL = SimpleZiDsl.forInitial(Initial.H);

    // ── The partition invariant ────────────────────────────────────────

    @Test
    void everySyllableIsDeclaredInThePartitionOfItsInitial() {
        // The invariant survives decoupling in a better form: it no longer
        // needs a default, because a SYLLABLE has exactly one initial whether
        // or not the characters under it are principally read there.
        for (SyllableDecl decl : H.DECLARATIONS) {
            assertEquals(Initial.H, decl.syllable().initial(),
                    () -> decl.syllable().toDiacritic() + " is declared in H");
        }
    }

    @Test
    void registryGroupsByDefaultInitial() {
        for (Initial i : SimpleZiRegistry.populatedInitials()) {
            for (SimpleZi z : SimpleZiRegistry.byInitial(i)) {
                assertEquals(i, z.partitionInitial());
            }
        }
    }

    @Test
    void registryFindsBySingleGlyph() {
        SimpleZi hao = SimpleZiRegistry.find("好").orElseThrow();
        assertEquals("hǎo", hao.defaultReading());

        assertTrue(SimpleZiRegistry.find("X").isEmpty(), "not every character is covered yet");
    }

    // ── Polyphony ──────────────────────────────────────────────────────

    @Test
    void polyphonyIsHavingMoreThanOneReading() {
        SimpleZi hang = H_DSL.zi("航", Finals.ANG, Tone.SECOND);
        assertFalse(hang.isPolyphonic());

        SimpleZi hao = SimpleZiRegistry.find("好").orElseThrow();
        assertTrue(hao.isPolyphonic(), "hǎo / hào");
        assertEquals(List.of("hǎo", "hào"),
                hao.allPhonics().stream().map(PinyinSyllable::toDiacritic).toList(),
                "the default reading comes first");
    }

    @Test
    void polyphonicSetDrivesThePreparationReviewList() {
        // Article preparation derives its review list from exactly this, so the
        // set must be reachable and must contain the multi-reading characters.
        List<String> glyphs = SimpleZiRegistry.polyphonic().stream()
                .map(z -> z.glyph().value()).toList();
        assertTrue(glyphs.contains("好"));
        assertTrue(glyphs.contains("和"), "hé / hè / huó");
        assertFalse(glyphs.contains("航"), "single-reading characters need no review");
    }

    @Test
    void alsoAppendsWithoutMutating() {
        SimpleZi one = H_DSL.zi("行", Finals.ANG, Tone.SECOND);
        SimpleZi two = one.also(Initial.X, Finals.ING, Tone.SECOND);

        assertFalse(one.isPolyphonic(), "the original is untouched");
        assertTrue(two.isPolyphonic());
        assertEquals("háng", two.defaultReading(), "appending does not change the default");
        assertEquals(List.of("háng", "xíng"),
                two.allPhonics().stream().map(PinyinSyllable::toDiacritic).toList());
    }

    // ── Validation ─────────────────────────────────────────────────────

    @Test
    void rejectsAMultiCodepointGlyph() {
        assertThrows(IllegalArgumentException.class,
                () -> H_DSL.zi("好好", Finals.AO, Tone.THIRD));
    }

    @Test
    void rejectsRepeatingTheDefaultAmongAdditionalPhonics() {
        SimpleZi hang = H_DSL.zi("航", Finals.ANG, Tone.SECOND);
        assertThrows(IllegalArgumentException.class,
                () -> hang.also(Initial.H, Finals.ANG, Tone.SECOND));
    }

    @Test
    void rejectsDuplicateAdditionalPhonics() {
        SimpleZi hao = H_DSL.zi("好", Finals.AO, Tone.THIRD)
                .also(Initial.H, Finals.AO, Tone.FOURTH);
        assertThrows(IllegalArgumentException.class,
                () -> hao.also(Initial.H, Finals.AO, Tone.FOURTH));
    }

    @Test
    void syllabicHelperProducesTheWrittenPlaceholder() {
        SimpleZi shi = SimpleZiDsl.forInitial(Initial.SH).syllabic("是", Tone.FOURTH);
        assertEquals("shì", shi.defaultReading());
    }
}
