package kranji.studio.gloss;

import kranji.reading.workbench.relation.Relation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The flattening, against the real data rather than a fixture.
 *
 * <p>A fixture would prove the code runs. What matters is that the relations
 * agree with each other and with the aggregates they came from — a grid built
 * on rows that quietly lost a sense is worse than no grid, because it looks
 * authoritative.</p>
 */
class GlossRelationsTest {

    private static final List<kranji.simple.gloss.ZiGloss> GLOSSES =
            GlossWorkbench.glosses().all();
    private static final List<kranji.simple.gloss.ExampleEntry> PHRASES =
            GlossWorkbench.examples().all();

    // ── Nothing is lost on the way out of the aggregate ────────────────

    @Test
    void everyCharacterBecomesExactlyOneZiRow() {
        assertEquals(GlossWorkbench.glosses().size(), GlossRelations.zi(GLOSSES).size());
    }

    @Test
    void theCountsOnAZiRowAgreeWithTheRowsBelowIt() {
        // The columns a person reads first, so they are the ones worth pinning:
        // a summary that disagrees with its own detail is the bug that makes
        // somebody stop trusting the whole tool.
        int sounds = GlossRelations.sounds(GLOSSES).size();
        int senses = GlossRelations.senses(GLOSSES).size();

        assertEquals(sounds, GlossRelations.zi(GLOSSES).stream()
                .mapToInt(GlossRelations.ZiRow::readings).sum());
        assertEquals(senses, GlossRelations.zi(GLOSSES).stream()
                .mapToInt(GlossRelations.ZiRow::senses).sum());
    }

    @Test
    void everySoundRowCarriesThePairKeyTheModelIsGrainedOn() {
        for (GlossRelations.SoundRow row : GlossRelations.sounds(GLOSSES)) {
            assertEquals(row.codePoint() + ":" + row.reading(), row.pairKey(),
                    "the pair key must be the codepoint and the canonical reading");
        }
    }

    @Test
    void readingsAreCanonicalNotDisplayForm() {
        // The whole system holds di4, never dì. A grid is a display, but its
        // ROWS are data - conversion belongs at the boundary, not here.
        for (GlossRelations.SoundRow row : GlossRelations.sounds(GLOSSES)) {
            assertTrue(row.reading().matches("[a-zü]+[0-4]"),
                    () -> row.glyph() + " reads '" + row.reading() + "', which is not canonical");
        }
    }

    // ── The relations agree with each other ────────────────────────────

    @Test
    void everySenseRowBelongsToASoundRow() {
        var pairs = GlossRelations.sounds(GLOSSES).stream()
                .map(GlossRelations.SoundRow::pairKey).toList();
        for (GlossRelations.SenseRow row : GlossRelations.senses(GLOSSES)) {
            assertTrue(pairs.contains(row.codePoint() + ":" + row.reading()),
                    () -> "orphaned sense: " + row.glyph() + " " + row.reading());
        }
    }
    @Test
    void everyReferencedPhraseSenseExists() {
        // The foreign key that spans the two aggregates, and the one place a
        // dangling reference could hide - a sense citing a phrase nobody
        // defined shows an empty cell rather than an error.
        var defined = GlossRelations.phraseSenses(PHRASES).stream()
                .map(r -> r.phrase() + "#" + r.senseIndex()).toList();
        for (GlossRelations.SenseRow sense : GlossRelations.senses(GLOSSES)) {
            for (String ref : sense.refs()) {
                assertTrue(defined.contains(ref),
                        () -> sense.glyph() + " cites " + ref + ", which is not defined");
            }
        }
    }

    @Test
    void whatASenseShowsAndWhatItReferencesAreTheSamePhrases() {
        // Two renderings of one list: text for a person, keys for a grid. They
        // are built from the same ordered examples, and this is what says so -
        // a cell naming phrases the cascade will not open is worse than either
        // one being absent.
        for (GlossRelations.SenseRow sense : GlossRelations.senses(GLOSSES)) {
            var inlined = sense.examples().isEmpty()
                    ? List.<String>of() : List.of(sense.examples().split(java.util.regex.Pattern.quote("|")));

            assertEquals(sense.refs().size(), inlined.size(),
                    () -> "the cell lists " + inlined.size() + " where there are "
                        + sense.refs().size() + ": " + sense.examples());

            for (int i = 0; i < inlined.size(); i++) {
                String ref = sense.refs().get(i);
                String shown = inlined.get(i);
                assertTrue(ref.equals(shown) || ref.equals(shown + "#0"),
                        () -> sense.meaning() + " shows '" + shown
                            + "' where it references '" + ref + "'");
            }
        }
    }


    @Test
    void aPhrasesSenseIndexIsShownOnlyWhenItIsNotTheFirst() {
        // 东's "east" is shown by 东西 sense 1 - "east and west", not "a thing".
        // Dropping the index would make the cell name a phrase that means
        // something else; printing #0 on every other row would bury it.
        var east = GlossRelations.senses(GLOSSES).stream()
                .filter(s -> s.glyph().equals("东")).findFirst().orElseThrow();

        assertTrue(east.examples().contains("东西#1"), east.examples());
        assertFalse(east.examples().contains("#0"),
                "the default index is silent: " + east.examples());
    }

    // ── The sparse column ──────────────────────────────────────────────

    @Test
    void pinnedReadingsShowOnlyWhereOneWasStated() {
        var withPins = GlossRelations.phraseSenses(PHRASES).stream()
                .filter(r -> !r.sounds().isEmpty()).toList();

        assertFalse(withPins.isEmpty(), "the data does pin some readings");
        for (var row : withPins) {
            assertTrue(row.sounds().matches("(\\S+=[a-zü]+[0-4])(  \\S+=[a-zü]+[0-4])*"),
                    () -> row.phrase() + " pins '" + row.sounds() + "'");
        }
        assertTrue(withPins.size() < GlossRelations.phraseSenses(PHRASES).size(),
                "a pin is the exception - if every sense had one the column says nothing");
    }

    // ── The worklist ───────────────────────────────────────────────────

    @Test
    void demandShowsEveryPairTheLibraryUsesWrittenOrNot() {
        // The relation that answers "why is the workbench showing 28 rows when
        // the set is 447". It is not: 447 is what the articles ask for, and 28
        // is what has been written. Both belong on screen or the gap is
        // invisible where the work happens.
        var rows = GlossRelations.rowsOf("demand", GLOSSES, PHRASES);
        var glossedPairs = GlossRelations.sounds(GLOSSES).stream()
                .map(GlossRelations.SoundRow::pairKey).toList();

        // The UNION: everything demanded, plus every pair glossed that the
        // library never reads. Demand replaced the character and reading
        // relations, so a pair it omitted would be unreachable in the tool -
        // and the extras are all polyphone completions, which is exactly the
        // kind that would vanish unnoticed.
        long extras = rows.stream().filter(r -> r.values().get(3).equals("extra")).count();
        assertEquals(GlossDemand.pairs().size() + extras, rows.size(),
                "every demanded pair, plus every glossed one the library never reads");
        assertTrue(extras > 0, "the polyphone rule forces some, and they must stay reachable");

        long todo = rows.stream().filter(r -> r.values().get(3).equals("todo")).count();
        long done = rows.size() - todo;
        assertEquals(done, rows.stream()
                        .filter(r -> glossedPairs.contains(r.pk())).count(),
                "a row is done exactly when a collection can explain that pair");

        // This used to require todo > 0, which was true while the set was being
        // written and became a test that failed on finishing it. What the tool
        // owes is an honest count either way, so the states are what is checked:
        // every row says done, todo or extra, and nothing else.
        for (Relation.Row row : rows) {
            String status = String.valueOf(row.values().get(3));
            assertTrue(status.isEmpty() || status.equals("todo") || status.equals("extra"),
                    () -> row.pk() + " carries status '" + status + "'");
        }
    }

    @Test
    void anOutstandingRowCarriesNoMeaningAndSaysSo() {
        // Marked, not merely blank. An empty cell reads as "nothing to say";
        // a word reads as "not yet said", and only the second can be filtered.
        var rows = GlossRelations.rowsOf("demand", GLOSSES, PHRASES);
        for (Relation.Row row : rows) {
            boolean todo = row.values().get(3).equals("todo");
            assertEquals(todo, row.values().get(2).equals(""),
                    () -> row.pk() + " is marked " + row.values().get(3)
                        + " but its meaning is '" + row.values().get(2) + "'");
        }
    }

    // ── The sound picker's own columns ─────────────────────────────────

    @Test
    void everyReadingIsDecomposedAndCountsItsOwnPairs() {
        // The picker filters on initial and final, so those columns are the
        // filter. A blank one would silently drop the reading out of every
        // filtered view while leaving it in the unfiltered one - visible only
        // to somebody who went looking.
        var sounds = GlossSounds.rows(GLOSSES);
        assertFalse(sounds.isEmpty());

        int pairs = 0;
        for (GlossSounds.Row row : sounds) {
            assertFalse(row.initial().isEmpty(),
                    () -> row.reading() + " has no initial - the zero one is written "
                        + GlossSounds.ZERO_INITIAL);
            assertFalse(row.rhyme().isEmpty(), () -> row.reading() + " has no final");
            assertTrue(row.tone() >= 0 && row.tone() <= 4,
                    () -> row.reading() + " is toned " + row.tone());
            assertTrue(row.characters() > 0, () -> row.reading() + " counts no characters");
            assertTrue(row.todo() <= row.characters(),
                    () -> row.reading() + " owes more than it has");
            pairs += row.characters();
        }
        assertEquals(GlossRelations.rowsOf("demand", GLOSSES, PHRASES).size(), pairs,
                "every demanded pair is counted under exactly one reading");
    }

    @Test
    void theTwoSilentSpellingsAreWrittenRatherThanLeftBlank() {
        // Two places where correct pinyin is the empty string and a filter
        // needs a token. The zero initial (爱 ai4 has no onset) and the empty
        // rime (si1's "-i" is acoustic, not a vowel). Left blank, both are
        // indistinguishable from "any" in a dropdown - the option would be
        // there, and choosing it would look like choosing nothing.
        var sounds = GlossSounds.rows(GLOSSES);

        var zeroOnset = sounds.stream()
                .filter(r -> r.initial().equals(GlossSounds.ZERO_INITIAL)).toList();
        assertFalse(zeroOnset.isEmpty(), "the set does read vowel-initial syllables");
        assertTrue(zeroOnset.stream().allMatch(r -> "aoeiuwy".indexOf(r.reading().charAt(0)) >= 0),
                zeroOnset.stream().map(GlossSounds.Row::reading).toList().toString());

        var emptyRime = sounds.stream()
                .filter(r -> r.rhyme().equals(GlossSounds.EMPTY_RHYME)).toList();
        assertFalse(emptyRime.isEmpty(), "...and syllables like si1 and zhi1");
        assertTrue(emptyRime.stream().allMatch(r -> r.reading().matches("(z|c|s|zh|ch|sh|r)i[0-4]")),
                emptyRime.stream().map(GlossSounds.Row::reading).toList().toString());
    }

    @Test
    void aReadingAppearsOnceHoweverManyCharactersUseIt() {
        var readings = GlossSounds.rows(GLOSSES).stream()
                .map(GlossSounds.Row::reading).toList();

        assertEquals(readings.size(), Set.copyOf(readings).size(), "a reading is one row");
        assertTrue(GlossSounds.rows(GLOSSES).stream().anyMatch(r -> r.characters() > 1),
                "and readings ARE shared - otherwise the relation says nothing new");
    }

    @Test
    void everyPhraseBecomesExactlyOnePhraseRow() {
        assertEquals(GlossWorkbench.examples().size(), GlossRelations.phrases(PHRASES).size());
        assertEquals(GlossRelations.phraseSenses(PHRASES).size(),
                GlossRelations.phrases(PHRASES).stream()
                        .mapToInt(GlossRelations.PhraseRow::senses).sum());
    }
}
