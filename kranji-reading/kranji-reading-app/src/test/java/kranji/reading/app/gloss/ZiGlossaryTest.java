package kranji.reading.app.gloss;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The seam between the reader and whatever glosses are deployed.
 *
 * <p>These run against the collections actually on the classpath, which is the
 * point: the app names none of them, so what these assert is that the SPI wiring
 * holds end to end rather than that one particular file parses.</p>
 */
class ZiGlossaryTest {

    // ── The wiring itself ──────────────────────────────────────────────

    @Test
    void theCollectionsAreFoundWithoutBeingNamed() {
        // Nothing in the reading app mentions a collection class. If the
        // runtime dependency were dropped this is the test that would say so,
        // and it would say so here rather than as a blank column somebody
        // notices weeks later.
        assertFalse(ZiGlossary.isEmpty(),
                "no ZiCollection on the classpath - is kranji-gloss still a dependency?");
        assertTrue(ZiGlossary.characterCount() > 0);
    }

    // ── The grain ──────────────────────────────────────────────────────

    @Test
    void meaningIsKeyedOnTheReadingAndNotOnTheCharacter() {
        // 地 is the case the pair key exists for. Answering for the character
        // alone would have to pick one of these and be wrong half the time -
        // and it would be wrong in a pane whose entire job is telling a reader
        // which of two readings is in front of them.
        int di = "地".codePointAt(0);

        String earth = ZiGlossary.primaryOf(di, "di4");
        String particle = ZiGlossary.primaryOf(di, "de0");

        assertFalse(earth.isBlank());
        assertFalse(particle.isBlank());
        assertFalse(earth.equals(particle), "two readings, two meanings");
    }

    @Test
    void everySenseComesBackInThePriorityOrderTheCollectionAuthored() {
        // 忙 mang2 is written as two senses, primary then secondary. A pane
        // showing one must get "busy"; a pane showing all must get them this
        // way round. Both follow from the order, so the order is what is
        // checked rather than either caller's use of it.
        List<String> meanings = ZiGlossary.meaningsOf("忙".codePointAt(0), "mang2");

        assertEquals(2, meanings.size(), meanings.toString());
        assertEquals("busy", meanings.get(0));
        assertEquals(ZiGlossary.primaryOf("忙".codePointAt(0), "mang2"), meanings.get(0),
                "the primary is the first, not a separate opinion");
    }

    // ── Absence, in each of its forms ──────────────────────────────────

    @Test
    void aReadingNoCollectionCoversAnswersEmptyRatherThanThrowing() {
        // Three ways to ask for nothing, all of which a browser can produce,
        // and none of which is a fault: a character outside the set, a reading
        // the character does not have, and a display form left over from an
        // older stored key.
        assertEquals("", ZiGlossary.primaryOf("鬱".codePointAt(0), "yu4"));
        assertEquals("", ZiGlossary.primaryOf("地".codePointAt(0), "zzz9"));
        assertEquals("", ZiGlossary.primaryOf("地".codePointAt(0), "dì"),
                "a diacritic key is a key nothing is filed under, not an error");
        assertEquals(List.of(), ZiGlossary.meaningsOf("地".codePointAt(0), ""));
    }

    @Test
    void aCodepointThatIsNotAHanCharacterIsJustAMiss() {
        // Arrives from a URL, so it is an answer to give rather than a fault
        // to raise - the same stance ZiDetailGetAction takes on its query.
        assertEquals("", ZiGlossary.primaryOf('A', "a1"));
        assertEquals("", ZiGlossary.primaryOf(-1, "a1"));
    }

    // ── The bulk feed ──────────────────────────────────────────────────

    @Test
    void everyPairIsKeyedTheWayTheKnownSetKeysIt() {
        // The claim that makes the Known grid a lookup rather than a join. If
        // these two ever spelled a key differently the column would come back
        // empty and nothing would say why.
        var primaries = ZiGlossary.primaries();

        assertFalse(primaries.isEmpty());
        for (var pair : primaries.entrySet()) {
            assertTrue(pair.getKey().matches("[0-9]+:[a-zü]+[0-4]"),
                    () -> "not a pair key: " + pair.getKey());
            assertFalse(pair.getValue().isBlank(),
                    () -> pair.getKey() + " is listed with no meaning");
        }
        assertEquals(ZiGlossary.primaryOf("地".codePointAt(0), "di4"),
                primaries.get("地".codePointAt(0) + ":di4"),
                "the bulk answer and the single answer are the same answer");
    }
}
