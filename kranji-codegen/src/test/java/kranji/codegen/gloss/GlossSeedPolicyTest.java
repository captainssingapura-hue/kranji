package kranji.codegen.gloss;

import kranji.codegen.gloss.GlossSeedPolicy.Doubt;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The selection policy, on real {@code kDefinition} fields.
 *
 * <p>Every input below is copied from the vendored Unihan drop rather than
 * invented. A policy tested on tidy examples is a policy tested on the corpus
 * somebody wished for.</p>
 */
class GlossSeedPolicyTest {

    // ── The separator, which is the whole problem ─────────────────────

    @Test
    void aSemicolonDividesSensesAndACommaDoesNot() {
        // U+4F4D 位. Unihan's semicolon is a sense boundary; ours joins. Read
        // the other way round this yields one sense reading like a dictionary
        // entry, or four where the corpus offered near-synonyms.
        var seed = GlossSeedPolicy.of("throne; position, post; rank, status; seat");

        // All four. This used to keep three and flag the fourth, which was the
        // cap talking rather than the language: the bands are not one each, so
        // a reading may carry several auxiliary meanings.
        assertEquals(List.of("throne", "position, post", "rank, status", "seat"),
                seed.meanings());
        assertFalse(seed.doubts().contains(Doubt.TRUNCATED), "nothing was cut");
    }

    @Test
    void aFieldThatIsReallyADictionaryEntryStillTruncates() {
        // U+3498 㤘, the longest in the corpus at eight. Five is where the data
        // sits - 138 of the 141 fields offering more than three offer four or
        // five - so what still flags is a field nobody would print whole.
        var seed = GlossSeedPolicy.of(
                "obstinate; stubborn; opinionated; obstinacy; stubbornness; "
              + "intransigent, truculent; savage, ferocious; fierce");

        assertEquals(GlossSeedPolicy.MAX_SENSES, seed.meanings().size());
        assertTrue(seed.doubts().contains(Doubt.TRUNCATED));
    }

    @Test
    void commasInsideOneSenseAreLeftExactlyAsTheyAre() {
        // U+4E1E 丞. Three words for one meaning, not three meanings. Rewriting
        // them would be this tool having an opinion about wording.
        var seed = GlossSeedPolicy.of("assist, aid, rescue");

        assertEquals(List.of("assist, aid, rescue"), seed.meanings());
        assertFalse(seed.wantsReview());
    }

    // ── What is not a meaning ─────────────────────────────────────────

    @Test
    void aStrikeThatLeavesAMeaningStandingIsNotADoubt() {
        // A pointer at another character is not a meaning, and a reviewer
        // would strike it every time - which is exactly why saying so is not
        // worth their time. This used to raise FILTERED, and 豹 kept "leopard,
        // panther", lost "surname", and asked a person to confirm it.
        var seed = GlossSeedPolicy.of("same as U+7684; bright; clear");

        assertEquals(List.of("bright", "clear"), seed.meanings());
        assertFalse(seed.doubts().contains(Doubt.FILTERED),
                "the filter succeeded; there is nothing to look at");
    }

    @Test
    void aStrikeThatLeavesNothingIsStillWorthNaming() {
        // The other half, and the reason FILTERED did not simply go away.
        // "had no gloss at all" and "had one that was all apparatus" send
        // whoever writes the replacement to different places.
        //
        // Both groups here are cross-references, which are never a meaning
        // however little else there is - unlike a proper noun, which is one
        // when it is the last thing standing.
        var seed = GlossSeedPolicy.of("same as U+7684; variant of U+767D");

        assertFalse(seed.usable());
        assertTrue(seed.doubts().contains(Doubt.FILTERED));
        assertTrue(seed.doubts().contains(Doubt.EMPTY));
    }

    @Test
    void aMeaningThatMerelyContainsThePatternSurvives() {
        // The filters anchor at the start for this reason. "to treat the same
        // as" is a meaning; "same as X" is a cross-reference. An unanchored
        // match would lose the first to catch the second.
        var seed = GlossSeedPolicy.of("to treat the same as another");

        assertEquals(List.of("to treat the same as another"), seed.meanings());
        assertFalse(seed.doubts().contains(Doubt.FILTERED));
    }

    @Test
    void aFieldWithNothingButApparatusSeedsNothing() {
        // Not an error and not a blank row - a queue entry. The character has
        // to be authored, and saying so is more useful than a gloss reading
        // "KangXi radical 9".
        var seed = GlossSeedPolicy.of("KangXi radical 9");

        assertFalse(seed.usable());
        assertTrue(seed.doubts().contains(Doubt.EMPTY));
        assertEquals(List.of(), seed.meanings());
    }

    // ── Register apparatus ────────────────────────────────────────────

    @Test
    void aLeadingRegisterMarkerIsStrippedFromTheSenseItMarks() {
        // U+4EE8 佨. "(coll.) three" is the word three; the marker is
        // apparatus, and a child reading the cell does not want it.
        var seed = GlossSeedPolicy.of("(coll.) three (cannot be followed by a measure word)");

        assertEquals(1, seed.meanings().size());
        assertTrue(seed.meanings().get(0).startsWith("three"), seed.meanings().get(0));
    }

    // ── Saying when it is out of its depth ────────────────────────────

    @Test
    void aSenseTooLongForTheModelIsDroppedAndAskedFor() {
        // U+5CB7 岷 killed the first run: "Minshan mountain range in northern
        // Sichuan and southern Gansu, Min River" is 72 characters and Meaning
        // refuses anything over 60. Carrying it would only move the failure
        // into the writer, so it is dropped and the character is asked for.
        var seed = GlossSeedPolicy.of(
                "Minshan mountain range in northern Sichuan and southern Gansu, Min River");

        assertFalse(seed.usable());
        assertTrue(seed.doubts().contains(Doubt.VERBOSE));
    }

    @Test
    void theShortSensesSurviveWhenALongOneIsDropped() {
        // U+5146 兆. The units note is too long to hold; omen and million are
        // not, and losing them with it would be the seeder throwing away what
        // it came for.
        var seed = GlossSeedPolicy.of(
                "omen; million; mega; also trillion. China = million, "
              + "Japan and Taiwan = trillion, which nobody agrees about");

        assertEquals(List.of("omen", "million", "mega"), seed.meanings());
        assertFalse(seed.doubts().contains(Doubt.VERBOSE),
                "three meanings survived; the long one was the encyclopaedia");
    }

    @Test
    void aProperNounGoesWhileARealMeaningIsStanding() {
        // 邴. The city named after the character does not explain it, so with
        // "pleased" on offer the city goes - and quietly, because a reviewer
        // would have struck it too.
        var seed = GlossSeedPolicy.of("name of a river in Shandong; pleased");

        assertEquals(List.of("pleased"), seed.meanings());
        assertEquals(List.of(), seed.doubts());
    }

    @Test
    void aProperNounStaysWhenItIsTheLastThingStanding() {
        // The condition, and the bug it fixes. 侴 IS a surname; 岽 IS a place
        // in Guangxi. Striking those unconditionally deleted the one gloss 114
        // characters had and filed them as unglossed, which is the opposite of
        // tidying.
        assertEquals(List.of("surname"), GlossSeedPolicy.of("surname").meanings());
        assertEquals(List.of("name of a river in Shandong"),
                GlossSeedPolicy.of("name of a river in Shandong").meanings());
    }

    @Test
    void aPlainNounIsNeverMistakenForAProperOne() {
        // Why every pattern carries a specifier. 河 means "river"; a river in
        // Shandong is where a river happens to be. Patterns stopping at the
        // noun would strike the meanings they exist to protect.
        assertEquals(List.of("river", "stream"),
                GlossSeedPolicy.of("river; stream").meanings());
        assertEquals(List.of("city", "state"),
                GlossSeedPolicy.of("city; state").meanings());
    }

    @Test
    void aFieldThatIsOnlyLongSaysSoRatherThanSayingNothing() {
        // 岷, the first row that ever broke the generator: 72 characters where
        // Meaning takes 60. Not a proper-noun pattern, just unprintable - and
        // still worth naming, because the character needs a gloss and this
        // says the source had something to say rather than nothing.
        var seed = GlossSeedPolicy.of(
                "Minshan mountain range in northern Sichuan and southern Gansu, Min River");

        assertFalse(seed.usable());
        assertTrue(seed.doubts().contains(Doubt.VERBOSE));
        assertTrue(seed.doubts().contains(Doubt.EMPTY));
    }

    @Test
    void nothingAtAllIsAnEmptySeedRatherThanAThrow() {
        // The generator meets characters with no kDefinition - 348 of them -
        // and a throw would stop a partition on its worst row.
        assertFalse(GlossSeedPolicy.of(null).usable());
        assertFalse(GlossSeedPolicy.of("").usable());
        assertFalse(GlossSeedPolicy.of("   ").usable());
    }

    // ── The shape the writer depends on ───────────────────────────────

    @Test
    void noSeedCarriesATabOrRunsOfWhitespace() {
        // The output is a tab-separated file and the input is one too. A tab
        // riding along inside a meaning shifts every field after it.
        var seed = GlossSeedPolicy.of("first\tsense;  second   sense  ");

        for (String m : seed.meanings()) {
            assertFalse(m.contains("\t"), () -> "tab in: " + m);
            assertFalse(m.contains("  "), () -> "double space in: " + m);
        }
        assertEquals(List.of("first sense", "second sense"), seed.meanings());
    }

    @Test
    void neverMoreThanTheCeiling() {
        var seed = GlossSeedPolicy.of("one; two; three; four; five; six");

        assertEquals(GlossSeedPolicy.MAX_SENSES, seed.meanings().size());
        assertTrue(seed.doubts().contains(Doubt.TRUNCATED));
    }
}
