package kranji.reading.app.read;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A shelf's name, said out loud.
 *
 * <p>The library tree is the one place a reader meets a Chinese name with no
 * article around it to work the meaning out from, so the reading is all the
 * help there is. What can go wrong is quiet: a bracket around the wrong run, a
 * separator swallowed, or a plausible-looking reading standing in for one the
 * corpus does not have. None of those throw, and all of them reach a child as
 * fact.</p>
 */
class PinyinLabelTest {

    // ── The ordinary case ──────────────────────────────────────────────

    @Test
    void aNameIsFollowedByItsReading() {
        // The shape the tree draws: the name a reader must get past, then the
        // sound of it.
        assertEquals("唐诗(táng shī)", PinyinLabel.sounded("唐诗"));
    }

    @Test
    void syllablesAreSpacedRatherThanRunTogether() {
        // Which characters make a word is not known here. Running them
        // together claims a boundary this cannot have established, and a
        // reader matching sound to character loses the correspondence.
        assertEquals("静夜思(jìng yè sī)", PinyinLabel.sounded("静夜思"));
    }

    @Test
    void tonesAreMarkedRatherThanNumbered() {
        // The corpus keys on si1; what a person reads is sī. Serving the key
        // would put the store's spelling in front of a child.
        String sounded = PinyinLabel.sounded("李白");

        assertEquals("李白(lǐ bái)", sounded);
        assertTrue(sounded.indexOf('3') < 0, "no tone numbers reach the tree");
    }

    // ── Runs, and what is between them ─────────────────────────────────

    @Test
    void eachRunOfHanIsBracketedOnItsOwn() {
        // A shelf named across a separator keeps its shape. One bracket round
        // the lot would read as a single name and put the separator inside it.
        assertEquals("李白(lǐ bái) · 五言(wǔ yán)", PinyinLabel.sounded("李白 · 五言"));
    }

    @Test
    void whatIsNotHanPassesThroughUntouched() {
        // Punctuation, spaces and Latin are part of the name as written.
        assertEquals("(", PinyinLabel.sounded("("));
        assertEquals("A · B", PinyinLabel.sounded("A · B"));
    }

    @Test
    void aLabelWithNoHanIsUnchanged() {
        // This is applied to every label in the tree rather than to the ones
        // someone has decided are Chinese, so a Latin label must survive it.
        assertEquals("475 to read", PinyinLabel.sounded("475 to read"));
    }

    @Test
    void nothingIsSoundable() {
        // A collection with no title, and the null a record could carry.
        assertEquals("", PinyinLabel.sounded(""));
        assertEquals("", PinyinLabel.sounded(null));
    }

    // ── When the corpus cannot say ─────────────────────────────────────

    /**
     * U+20000, the first character of CJK Extension B. Han by script and
     * outside the 8,100 the corpus holds, so it is the case where there is no
     * reading to give. Written as an escape because it is a surrogate pair,
     * which also puts the supplementary plane through the run scanner.
     */
    private static final String UNREADABLE = "\uD840\uDC00";

    @Test
    void aRunWithAnUnreadableCharacterIsLeftBare() {
        // A partial gloss reads as a fact about the characters rather than as
        // a gap in the corpus, and a reader has no way to tell which it is.
        String label = "唐" + UNREADABLE;

        assertEquals(label, PinyinLabel.sounded(label),
                "silence rather than a hole in the middle of a reading");
    }

    @Test
    void anUnreadableCharacterDoesNotSilenceTheRestOfTheLabel() {
        // Only the run that cannot be read goes bare. A separator ends a run,
        // so one unreadable name does not cost the shelf its other half.
        assertEquals(UNREADABLE + " · 五言(wǔ yán)",
                PinyinLabel.sounded(UNREADABLE + " · 五言"));
    }
}
