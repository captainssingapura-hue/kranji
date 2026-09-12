package kranji.studio.gloss;

import kranji.simple.gloss.ExampleEntry;
import kranji.simple.gloss.ZiGloss;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The cascade, as arithmetic rather than as clicking.
 *
 * <p>The widgets do one thing each — take a set of parent keys and ask for the
 * rows under them. Everything that decides what a person sees is therefore in
 * {@code rowsOf} and {@code under}, and it can be walked here: select 地, take
 * the first sound, take its senses, and check that what arrives is what the
 * aggregate actually holds.</p>
 */
class GlossCascadeTest {

    private static final List<ZiGloss> GLOSSES = GlossWorkbench.glosses().all();
    private static final List<ExampleEntry> PHRASES = GlossWorkbench.examples().all();

    private static List<GlossRelations.Row> rows(String relation) {
        return GlossRelations.rowsOf(relation, GLOSSES, PHRASES);
    }

    private static List<GlossRelations.Row> under(String relation, String... parents) {
        return GlossRelations.under(rows(relation), List.of(parents));
    }

    // ── The chain is declared, not hardcoded per widget ────────────────

    @Test
    void everyRelationDeclaresItsUpstreamOrIsARoot() {
        for (String relation : GlossRelations.relations()) {
            String up = GlossRelations.upstreamOf(relation);
            if (up == null) continue;
            assertTrue(GlossRelations.relations().contains(up),
                    () -> relation + " names upstream '" + up + "', which is not a relation");
        }
    }

    @Test
    void theRootsAreTheSoundsAndThePhrases() {
        // Demand used to be a root. It stopped being one when the sound
        // relation arrived above it - not because demand gained a parent in the
        // model, but because a filter over the readings is exactly the shape
        // the cascade already had, and giving it a bespoke mechanism would have
        // meant two ways of scoping a grid.
        //
        // "problem" is demand's second child, so demand is where the chain
        // branches rather than ends. It scopes on the character of the
        // selected pair - see aProblemScopesOnTheCharacterNotThePair.
        //
        // coverageShelf is the fourth root and the one whose subject is not
        // the gloss data at all but the LIBRARY: how much of it the glosses
        // explain, shelf by shelf. It cannot hang off a sound or a partition
        // because neither is how a person asks "where are the gaps", and
        // grafting it under one would make the first click a filter nobody
        // wanted.
        var roots = GlossRelations.relations().stream()
                .filter(r -> GlossRelations.upstreamOf(r) == null).toList();
        assertEquals(List.of("sound", "phrase", "partition", "coverageShelf"), roots);
    }

    // ── The coverage chain ────────────────────────────────────────────

    @Test
    void everyArticleHangsUnderAShelfThatExists() {
        // The containment edge, checked the way the other chains are: a row
        // whose parent key names no row upstream is unreachable once the
        // upstream grid is in the chain. Article -> shelf is keyed on the
        // collection id, which is the one thing about a shelf that cannot be
        // shared with another under a different heading.
        var shelves = rows("coverageShelf").stream().map(GlossRelations.Row::pk).toList();
        assertFalse(shelves.isEmpty(), "the demo library has shelves");
        for (GlossRelations.Row article : rows("coverage")) {
            assertTrue(shelves.contains(article.up()),
                    () -> article.pk() + " hangs under shelf '" + article.up() + "', which is not a row");
        }
    }

    @Test
    void everyGapHangsUnderAnArticleThatExists() {
        var articles = rows("coverage").stream().map(GlossRelations.Row::pk).toList();
        for (GlossRelations.Row gap : rows("coverageMissing")) {
            assertTrue(articles.contains(gap.up()),
                    () -> gap.pk() + " hangs under article '" + gap.up() + "', which is not a row");
        }
    }

    @Test
    void theCoverageRootCarriesTheWholeLibraryFigureAndNothingElseDoes() {
        // The status line above the shelf grid says what fraction of the whole
        // library is covered; no shelf row could, and it is the first number a
        // person opening the workbench wants. Every other relation says
        // nothing there and keeps its row count alone.
        String note = GlossRelations.noteOf("coverageShelf");
        assertTrue(note != null && note.contains("% of reads"), () -> "note: " + note);
        for (String relation : GlossRelations.relations()) {
            if (relation.equals("coverageShelf")) continue;
            assertEquals(null, GlossRelations.noteOf(relation), relation + " should carry no note");
        }
    }

    // ── The sound filter ──────────────────────────────────────────────

    @Test
    void everySoundOffersAtLeastOnePairAndEveryPairIsOfferedASound() {
        // The two-way agreement that makes the filter honest. A reading in the
        // picker that selects nothing is a dead option; a demand row no reading
        // reaches is a row you cannot get to once the picker is in the chain.
        // Deriving the sounds from the glosses rather than from demand would
        // produce the second for every todo pair.
        var sounds = rows("sound").stream().map(GlossRelations.Row::pk).toList();
        var demanded = rows("demand");

        for (String sound : sounds) {
            assertFalse(GlossRelations.under(demanded, List.of(sound)).isEmpty(),
                    () -> "no pair reads " + sound);
        }
        for (GlossRelations.Row pair : demanded) {
            assertTrue(sounds.contains(pair.up()),
                    () -> pair.pk() + " hangs off '" + pair.up() + "', which no sound row offers");
        }
    }

    @Test
    void pickingSeveralSoundsUnionsTheirCharacters() {
        // The point of the widget: several readings at once, not one.
        var de = under("demand", "de0");
        var di = under("demand", "di4");
        var both = under("demand", "de0", "di4");

        assertFalse(de.isEmpty());
        assertFalse(di.isEmpty());
        assertEquals(de.size() + di.size(), both.size());
    }

    @Test
    void aSoundIsKeyedOnTheReadingAloneNotThePair() {
        // 地 reads de0 and di4, and those are two different sounds shared with
        // every other character that reads them. Keying the picker on the pair
        // would make it a second demand grid.
        var de = under("demand", "de0").stream().map(GlossRelations.Row::label).toList();

        assertTrue(de.contains("地"), de.toString());
        assertTrue(de.size() > 1, "de0 is not 地's alone: " + de);
    }

    // ── The walk a person actually makes ──────────────────────────────

    @Test
    void bothReadingsOfAPolyphoneAreTheirOwnRow() {
        // 地 is the useful case: two readings that mean different things, and
        // the grain is the pair, so they are two rows and never one.
        //
        // Order is the library's, not the author's - demand is built by reading
        // the articles, so a pair appears where a reader first meets it. 地 di4
        // leads because 疑是地上霜 comes before any 地 de0.
        var both = rows("demand").stream()
                .filter(r -> r.label().equals("地")).map(GlossRelations.Row::pk).toList();

        assertEquals(List.of("22320:di4", "22320:de0"), both);
    }

    @Test
    void takingAPairNarrowsToThatReadingsSenses() {
        var senses = under("sense", "22320:de0");

        assertEquals(1, senses.size());
        assertTrue(senses.get(0).label().contains("describe how something is done"),
                senses.get(0).label());
    }


    @Test
    void andOnDownToWhatShowsIt() {
        // The sense names its examples directly now - there is no join row in
        // between to step through.
        var sense = under("sense", "22320:de0").get(0);
        var reached = GlossRelations.withPks(rows("phraseSense"),
                refs("sense", sense.pk()));

        assertEquals(1, reached.size());
        assertEquals("慢慢地走#0", reached.get(0).pk());
    }


    @Test
    void theOtherReadingLeadsSomewhereElseEntirely() {
        // The point of keying on the pair. Same character, different reading,
        // and nothing in common downstream.
        var deSenses = under("sense", "22320:de0");
        var diSenses = under("sense", "22320:di4");

        assertFalse(deSenses.isEmpty());
        assertFalse(diSenses.isEmpty());
        for (var a : deSenses) {
            for (var b : diSenses) {
                assertFalse(a.pk().equals(b.pk()), "readings must not share a sense row");
            }
        }
    }

    // ── The one relation that scopes on less than it is given ─────────

    @Test
    void aProblemScopesOnTheCharacterNotThePair() {
        // A problem belongs to a CHARACTER. 欸 is queued because its five
        // readings need splitting by hand, and arriving at it through any one
        // of them must show the same single job - scoping on the pair would
        // hide the row unless you happened to pick the reading it is filed
        // under, which is the reading nobody has decided yet.
        var viaAi3 = GlossRelations.under(rows("problem"),
                GlossRelations.scopeKeysFor("problem", List.of("27448:ai3")));
        var viaEi1 = GlossRelations.under(rows("problem"),
                GlossRelations.scopeKeysFor("problem", List.of("27448:ei1")));

        assertEquals(1, viaAi3.size());
        assertEquals("27448:ai3", viaAi3.get(0).pk());
        assertEquals(viaAi3, viaEi1, "the reading you came by must not change the answer");
    }

    @Test
    void narrowingToACharacterMergesTheReadingsOfOne() {
        // Two readings of one character are one selection here. Leaving the
        // duplicate in would double-count a "how many selected" reading of it.
        assertEquals(List.of("22320"),
                GlossRelations.scopeKeysFor("problem", List.of("22320:de0", "22320:di4")));

        // And every other relation is handed its keys untouched - this is one
        // relation's exception, not a new rule for the cascade.
        var pairs = List.of("22320:de0", "22320:di4");
        assertEquals(pairs, GlossRelations.scopeKeysFor("sense", pairs));
    }

    @Test
    void thePhraseChainWalksTheSameWay() {
        var senses = under("phraseSense", "东西");
        assertEquals(2, senses.size(), "东西 is authored with two senses");
        assertEquals("东西#0", senses.get(0).pk());
    }

    // ── The distinction that keeps a scoped grid honest ───────────────

    @Test
    void anEmptySelectionShowsNothingRatherThanEverything() {
        // Not the same as "no selection". A downstream grid falling back to the
        // whole relation when its parent was cleared is a grid lying about what
        // it is scoped to - and it looks entirely plausible.
        assertEquals(List.of(), GlossRelations.under(rows("sense"), List.of()));
        assertFalse(rows("sense").isEmpty(), "...while the unscoped relation has rows");
    }

    // ── Across the ref, from a sense to the phrases that show it ──────

    private static List<String> refs(String from, String... pks) {
        return GlossRelations.refsFrom(from, List.of(pks), GLOSSES, PHRASES);
    }

    @Test
    void senseIsTheOnlyRelationThatPointsSideways() {
        // Containment is one edge; reference is another. If anything else grew
        // a ref it would mean a second join, and the chain would stop being a
        // chain without anyone deciding that.
        for (String relation : GlossRelations.relations()) {
            boolean hasRefs = rows(relation).stream().anyMatch(r -> !r.refs().isEmpty());
            assertEquals("sense".equals(relation), hasRefs,
                    () -> relation + " " + (hasRefs ? "has" : "has no") + " refs");
        }
        assertEquals("phraseSense", GlossRelations.refTargetOf("sense"));
        assertEquals("sense", GlossRelations.refSourceOf("phraseSense"));
    }

    @Test
    void everyRefNamesAPhraseSenseThatExists() {
        // The dangling-reference check for the sideways edge. Its downstream
        // counterpart is everyPhraseReferencedIsDefined in the validity suite;
        // this one guards the tool rather than the data.
        var defined = rows("phraseSense").stream().map(GlossRelations.Row::pk).toList();
        for (GlossRelations.Row row : rows("sense")) {
            for (String ref : row.refs()) {
                assertTrue(defined.contains(ref),
                        () -> row.pk() + " refs '" + ref + "', which no phraseSense has");
            }
        }
    }

    @Test
    void followingASenseReachesTheExamplesThemselves() {
        // The walk the workbench could not make when this started: a sense said
        // it had one example, and getting from that to what the example says
        // took two more grids. Now it is one hop.
        String sensePk = "24471:de0/(joins a verb to how it is done)";
        var reached = GlossRelations.withPks(rows("phraseSense"), refs("sense", sensePk));

        assertEquals(1, reached.size());
        assertEquals("跑得快#0", reached.get(0).pk());
        assertEquals("runs fast", reached.get(0).label());
        assertTrue(reached.get(0).values().contains("得=de0"),
                "and it pins the very reading the sense is filed under");
    }

    @Test
    void theExpansionReadsInTheOrderTheSenseStatesThem() {
        // The sense cell says 东方|东边|山东|东西#1, and the grid that explains
        // those phrases must list them the same way round. It did not: matching
        // by walking the phrase relation returned them in registry order, so the
        // expansion of a list silently disagreed with the list.
        var sense = GlossRelations.senses(GLOSSES).stream()
                .filter(s -> s.glyph().equals("东")).findFirst().orElseThrow();

        var reached = GlossRelations.withPks(rows("phraseSense"),
                refs("sense", sensePkOf(sense)));

        assertEquals(sense.refs(), reached.stream().map(GlossRelations.Row::pk).toList());
        assertEquals(List.of("东方#0", "东边#0", "山东#0", "东西#1"),
                reached.stream().map(GlossRelations.Row::pk).toList());
        assertEquals("east and west", reached.get(3).label(),
                "the one carrying a sequence mark is the one that needed explaining");
    }

    private static String sensePkOf(GlossRelations.SenseRow s) {
        return s.codePoint() + ":" + s.reading() + "/" + s.meaning();
    }

    @Test
    void aSenseWithSeveralExamplesReachesAllOfThem() {
        // Refs are plural, which is the whole reason the join relation could
        // go. 地上, 土地 and 地方 all show the one sense of 地 di4, and each
        // phrase belongs to itself.
        var reached = GlossRelations.withPks(rows("phraseSense"),
                refs("sense", "22320:di4/earth; ground; land"));

        // All three are sense 0 of their own phrase. 土地 pins a reading at
        // character POSITION 1, which is a different index entirely - the two
        // are easy to conflate and this is the row that would catch it.
        assertEquals(List.of("地上#0", "土地#0", "地方#0"),
                reached.stream().map(GlossRelations.Row::pk).toList());
    }


    @Test
    void selectingSeveralParentsUnionsTheirChildren() {
        var both = under("sense", "22320:de0", "22320:di4");
        assertEquals(under("sense", "22320:de0").size() + under("sense", "22320:di4").size(),
                both.size());
    }
}
