package kranji.gloss.tsv;


import kranji.gloss.handcrafted.HandCrafted;
import kranji.simple.gloss.ExampleEntry;
import kranji.simple.gloss.Meaning;
import kranji.simple.gloss.ZiGloss;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The check that replaces the compiler.
 *
 * <p>Authoring in Java meant a mistyped reading could not be built. Authoring
 * in a file means it can, so something has to fail the build instead — and
 * "something" has to be stronger than "the parser did not throw". The parser
 * not throwing is compatible with it having silently dropped half the rows.</p>
 *
 * <p>So the standard here is <b>round-trip equality against the data we
 * already have</b>: write the hand-crafted set out, read it back, and require
 * the result to equal what went in. Every field, every order, every pin. If the
 * format cannot express something, this fails rather than quietly losing it.</p>
 */
class GlossTsvTest {

    private static final List<ZiGloss> GLOSSES = HandCrafted.INSTANCE.characters().all();
    private static final List<ExampleEntry> PHRASES = HandCrafted.phraseList();

    // ── The format loses nothing ───────────────────────────────────────

    @Test
    void sensesSurviveTheRoundTripExactly() {
        String tsv = GlossTsv.writeSenses(GLOSSES);
        var read = GlossTsv.readSenses("senses.tsv", tsv);

        assertNoProblems(read.problems());
        assertEquals(GLOSSES, read.entries(),
                "a character, its readings, its meanings, their order and their examples");
    }

    @Test
    void phrasesSurviveTheRoundTripExactly() {
        String tsv = GlossTsv.writePhrases(PHRASES);
        var read = GlossTsv.readPhrases("phrases.tsv", tsv);

        assertNoProblems(read.problems());
        assertEquals(PHRASES, read.entries(),
                "a phrase, its senses, their English and their pinned readings");
    }

    @Test
    void thePinnedReadingsComeBackOnTheSamePositions() {
        // The same character pinned at different positions in different
        // phrases: 地 leads 地上 and trails 土地. A format that recorded the
        // glyph instead of the position would round-trip both of these and
        // still be wrong the first time a phrase repeated a character - 慢慢地走
        // already does, and only escapes because neither 慢 needs pinning.
        var read = GlossTsv.readPhrases("phrases.tsv", GlossTsv.writePhrases(PHRASES));

        var ground = phrase(read.entries(), "地上");
        assertEquals("di4", ground.primary().soundAt(0).orElseThrow().numbered());
        assertTrue(ground.primary().soundAt(1).isEmpty(), "上 was never pinned");

        var land = phrase(read.entries(), "土地");
        assertTrue(land.primary().soundAt(0).isEmpty(), "土 was never pinned");
        assertEquals("di4", land.primary().soundAt(1).orElseThrow().numbered());
    }

    @Test
    void aPhraseWithNothingWorthPinningCarriesNoPins() {
        // The sparse half of the same rule. 慢慢地走 reads 地 as its principal
        // de0, so stating it would be noise - and a file full of noise is one
        // nobody reads.
        var read = GlossTsv.readPhrases("phrases.tsv", GlossTsv.writePhrases(PHRASES));
        var walk = phrase(read.entries(), "慢慢地走");

        assertTrue(walk.primary().sounds().isEmpty(), walk.primary().sounds().toString());
    }

    private static ExampleEntry phrase(List<ExampleEntry> entries, String phrase) {
        return entries.stream().filter(e -> e.key().phrase().equals(phrase))
                .findFirst().orElseThrow();
    }

    @Test
    void theFileIsTabSeparatedAndNeedsNoQuoting() {
        // The reason for tabs rather than commas. If either ever becomes false
        // the format needs escaping, and escaping is where hand-edited files
        // go wrong.
        String all = GlossTsv.writeSenses(GLOSSES) + GlossTsv.writePhrases(PHRASES);

        assertTrue(all.contains(","), "commas are in the data - that is the point");
        assertFalse(all.contains("\""), "but no value carries a quote");
        for (String line : all.split("\n")) {
            if (line.startsWith("#") || line.isEmpty()) continue;
            assertFalse(line.contains("\t\t\t\t\t\t\t\t"),
                    () -> "a stray tab inside a value would shift every field: " + line);
        }
    }

    // ── What it says when a file is wrong ──────────────────────────────

    @Test
    void everyBadRowIsReportedWithItsLineNotJustTheFirst() {
        // The compiler named one error at a time and you fixed them one at a
        // time. A file can do better: report all of them, so one run tells you
        // everything to fix.
        String tsv = String.join("\n",
                GlossTsv.SENSES_HEADER,
                "19996\t东\tdōng\tP\teast\t东方\t",     // reading not canonical
                "19996\t丢\tdong1\tP\teast\t东方\t",     // glyph disagrees with codepoint
                "19996\t东\tdong1\tX\teast\t东方\t",     // X is not a band
                "19996\t东\tdong1\tP\t\t东方\t");        // no meaning at all

        var read = GlossTsv.readSenses("senses.tsv", tsv);

        assertEquals(4, read.problems().size(),
                () -> "expected all four bad rows:\n  " + join(read.problems()));
        assertEquals(List.of(2, 3, 4, 5),
                read.problems().stream().map(GlossTsv.Problem::line).toList(),
                "and each one pointing at its own line");
    }

    // ── One row per sense ──────────────────────────────────────────────

    @Test
    void aRowIsASenseAndASemicolonDoesNotDivideOne() {
        // The distinction that cost three attempts to get right. "sky" and "a
        // day" are two senses, so two rows. "a hill; a mountain" is one sense
        // said twice, so one row - and the semicolon can no longer be mistaken
        // for a divider, because dividing is what a row boundary does.
        var read = GlossTsv.readSenses("senses.tsv", String.join("\n",
                GlossTsv.SENSES_HEADER,
                "22825\t天\ttian1\tP\tsky\t天上\t",
                "22825\t天\ttian1\tS\ta day\t今天\t",
                "23665\t山\tshan1\tP\ta hill; a mountain\t高山\t"));

        assertNoProblems(read.problems());

        var sky = read.entries().get(0).sounds().get(0);
        assertEquals(2, sky.senses().size(), "two rows, two senses");
        assertEquals(List.of("sky", "a day"),
                sky.orderedMeanings().stream().map(Meaning::text).toList());

        var hill = read.entries().get(1).sounds().get(0);
        assertEquals(1, hill.senses().size(), "a semicolon joins, it does not divide");
        assertEquals("a hill; a mountain", hill.primary().text());
    }

    @Test
    void aSensesExamplesCannotDriftOntoAnother() {
        // The failure the old shape allowed: senses in one cell and their
        // example groups in a parallel cell, aligned by position, so
        // "busy|to hurry" beside "急忙|很忙" parsed cleanly and was backwards.
        // A row carries its own examples, so there is nothing left to align.
        var read = GlossTsv.readSenses("senses.tsv", String.join("\n",
                GlossTsv.SENSES_HEADER,
                "24537\t忙\tmang2\tP\tbusy\t很忙|帮忙\t",
                "24537\t忙\tmang2\tS\tto hurry\t急忙\t"));

        var sound = read.entries().get(0).sounds().get(0);
        assertEquals(2, sound.senseOf(Meaning.of("busy")).examples().size());
        assertEquals(1, sound.senseOf(Meaning.of("to hurry")).examples().size());
        assertEquals("急忙",
                sound.senseOf(Meaning.of("to hurry")).bestExample().phrase().phrase());
    }

    @Test
    void aBarListsPhrasesAndIsTheOnlyThingItDoes() {
        // One rule across both files: a bar lists, a semicolon or comma joins
        // within one item. It used to mean "sense boundary" in one column and
        // nothing in another, which is how it came to be doing two jobs.
        var read = GlossTsv.readSenses("senses.tsv", String.join("\n",
                GlossTsv.SENSES_HEADER,
                "27700\t水\tshui3\tP\twater\t水滴|水果\t"));

        var sense = read.entries().get(0).sounds().get(0).primary();
        var examples = read.entries().get(0).sounds().get(0).senseOf(sense).orderedExamples();

        assertEquals(List.of("水滴", "水果"),
                examples.stream().map(r -> r.phrase().phrase()).toList());
    }

    // ── The band, and the sequence that breaks its ties ────────────────

    @Test
    void equalBandsFallBackToTheOrderTheyWereWrittenIn() {
        // The reason a number could go. Two senses of equal standing no longer
        // force an author to invent a ranking between them: they are both P,
        // and the file's sequence decides. Reversing the two rows must reverse
        // the reading - if it did not, something other than the file would be
        // choosing, and that something would be a hash order.
        var written = GlossTsv.readSenses("senses.tsv", String.join("\n",
                GlossTsv.SENSES_HEADER,
                "22825\t天\ttian1\tP\tsky\t天上\t",
                "22825\t天\ttian1\tP\ta day\t今天\t"));
        var reversed = GlossTsv.readSenses("senses.tsv", String.join("\n",
                GlossTsv.SENSES_HEADER,
                "22825\t天\ttian1\tP\ta day\t今天\t",
                "22825\t天\ttian1\tP\tsky\t天上\t"));

        assertNoProblems(written.problems());
        assertEquals(List.of("sky", "a day"), meanings(written));
        assertEquals(List.of("a day", "sky"), meanings(reversed));
    }

    @Test
    void aLaterBandNeverOutranksAnEarlierOneWhateverTheSequence() {
        // And the half the sequence must NOT decide. An auxiliary sense written
        // first is still auxiliary - otherwise the band is decoration and the
        // file is back to ranking by position.
        var read = GlossTsv.readSenses("senses.tsv", String.join("\n",
                GlossTsv.SENSES_HEADER,
                "22825\t天\ttian1\tA\ta corner sense\t天罡\t",
                "22825\t天\ttian1\tS\ta day\t今天\t",
                "22825\t天\ttian1\tP\tsky\t天上\t"));

        assertNoProblems(read.problems());
        assertEquals(List.of("sky", "a day", "a corner sense"), meanings(read));
        assertEquals("sky", read.entries().get(0).sounds().get(0).primary().text(),
                "and the primary is the band, not the first row");
    }

    @Test
    void aBandNobodyDefinedIsRefused() {
        // What replaced the mis-numbering check. A gap in a sequence cannot be
        // written any more - the loader stamps it - so the mistake a file can
        // still make is claiming a band that does not exist, and guessing which
        // one was meant would be worse than refusing.
        var read = GlossTsv.readSenses("senses.tsv", String.join("\n",
                GlossTsv.SENSES_HEADER,
                "22825\t天\ttian1\t1\tsky\t天上\t"));

        assertFalse(read.ok());
        assertTrue(read.problems().get(0).message().contains("expected P, S or A"),
                read.problems().get(0).message());
    }

    @Test
    void oneMeaningTwiceUnderOneReadingIsRefused() {
        // The number used to catch this by accident: two rows claiming one
        // meaning collapsed to a single map entry and left a hole in the
        // sequence, which SoundGloss rejected. Nothing catches it by accident
        // now, so the reader names it.
        var read = GlossTsv.readSenses("senses.tsv", String.join("\n",
                GlossTsv.SENSES_HEADER,
                "22825\t天\ttian1\tP\tsky\t天上\t",
                "22825\t天\ttian1\tS\tsky\t今天\t"));

        assertFalse(read.ok());
        assertTrue(read.problems().get(0).message().contains("twice"),
                read.problems().get(0).message());
    }

    private static List<String> meanings(GlossTsv.Read<ZiGloss> read) {
        return read.entries().get(0).sounds().get(0).orderedMeanings()
                .stream().map(Meaning::text).toList();
    }


    @Test
    void aReadingTheCorpusSpellsDifferentlyIsRefused() {
        // dōng is the display form. Inside the system a reading is di4, never
        // dì - the file is a source, so it holds the canonical form.
        var read = GlossTsv.readSenses("senses.tsv", String.join("\n",
                GlossTsv.SENSES_HEADER,
                "19996\t东\tdōng\tP\teast\t东方\t"));

        assertFalse(read.ok());
        assertTrue(read.problems().get(0).message().contains("canonical"),
                read.problems().get(0).message());
    }

    @Test
    void aPinOutsideThePhraseIsRefused() {
        var read = GlossTsv.readPhrases("phrases.tsv", String.join("\n",
                GlossTsv.PHRASES_HEADER,
                "东方\t0\tthe east\t9=dong1"));

        assertFalse(read.ok());
        assertTrue(read.problems().get(0).message().contains("outside a phrase"),
                read.problems().get(0).message());
    }

    @Test
    void aHalfWrittenPhraseIsRefused() {
        // The check that already exists in EgKey, reached through the file.
        var read = GlossTsv.readPhrases("phrases.tsv", String.join("\n",
                GlossTsv.PHRASES_HEADER,
                "east方\t0\tthe east\t"));

        assertFalse(read.ok());
        assertTrue(read.problems().get(0).message().contains("not all Han"),
                read.problems().get(0).message());
    }

    @Test
    void blankLinesAndCommentsAreNotData() {
        var read = GlossTsv.readSenses("senses.tsv", String.join("\n",
                "# a note somebody left",
                "",
                GlossTsv.SENSES_HEADER,
                "19996\t东\tdong1\tP\teast\t东方\t",
                "",
                "# and another"));

        assertNoProblems(read.problems());
        assertEquals(1, read.entries().size());
    }

    @Test
    void aFileEditedOnWindowsStillParses() {
        // A trailing \r would otherwise ride along on the last field of every
        // row - invisible, and enough to make a reading match nothing.
        var read = GlossTsv.readSenses("senses.tsv",
                GlossTsv.SENSES_HEADER + "\r\n19996\t东\tdong1\tP\teast\t东方\t\r\n");

        assertNoProblems(read.problems());
        assertEquals("east", read.entries().get(0).sounds().get(0).primary().text());
    }

    private static void assertNoProblems(List<GlossTsv.Problem> problems) {
        assertTrue(problems.isEmpty(), () -> problems.size() + " problems:\n  " + join(problems));
    }

    private static String join(List<GlossTsv.Problem> problems) {
        return String.join("\n  ", problems.stream().map(Object::toString).toList());
    }
}
