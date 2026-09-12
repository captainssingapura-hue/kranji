package kranji.reading.content;

import kranji.pinyin.PinyinSyllable;
import kranji.reading.library.LibraryTree;
import kranji.simple.gloss.GlossSource;
import kranji.simple.gloss.Glosses;
import kranji.simple.gloss.Meaning;
import kranji.simple.gloss.RankingInfo;
import kranji.simple.gloss.Sense;
import kranji.simple.gloss.SoundGloss;
import kranji.simple.gloss.ZiGloss;
import kranji.zi.ZiCharUTF8;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The arithmetic, pinned against registries whose contents are known.
 *
 * <p>The real registries are large and someone else's; a test that measured
 * the demo set against them would be asserting a number that changes whenever
 * a gloss is written. So the registry here is empty, or holds exactly one pair
 * — and what coverage must then read is not in doubt.</p>
 */
class GlossCoverageTest {

    private static final LibraryTree DEMO = DemoLibrary.INSTANCE.tree();

    @Test
    void nothingGlossedIsZeroAtEveryGrain() {
        GlossCoverage.Report r = GlossCoverage.of("demo", DEMO, Glosses.none());

        assertTrue(r.reads() > 1000, "precondition: the demo set has text in it");
        assertEquals(0, r.readsCovered());
        assertEquals(0, r.pairsCovered());
        assertEquals(0, r.charactersKnown());
        assertEquals(0.0, r.readRatio());
        assertEquals(0, r.articlesComplete());
        assertEquals(r.pairs(), r.missing().size(),
                "every demanded pair is on the worklist when nothing is written");
        for (GlossCoverage.Missing m : r.missing()) {
            assertFalse(m.characterKnown(), "an empty registry knows no character");
        }
    }

    @Test
    void theWorklistIsMostReadFirstAndSumsToTheReads() {
        GlossCoverage.Report r = GlossCoverage.of("demo", DEMO, Glosses.none());

        int sum = 0;
        int previous = Integer.MAX_VALUE;
        for (GlossCoverage.Missing m : r.missing()) {
            assertTrue(m.reads() <= previous, "the worklist is ordered by weight");
            previous = m.reads();
            sum += m.reads();
        }
        assertEquals(r.reads(), sum, "every read is accounted for exactly once");
    }

    @Test
    void oneGlossedPairIsCountedAtAllThreeGrains() {
        // The most-read pair in the demo set, glossed and nothing else. Reads
        // covered must equal its weight; pairs covered must be one; and its
        // character must be known - while a second reading of the same
        // character, if the set has one, stays missing but 'known'.
        GlossCoverage.Report before = GlossCoverage.of("demo", DEMO, Glosses.none());
        GlossCoverage.Missing top = before.missing().get(0);

        Glosses one = Glosses.of(single(top.codePoint(), top.reading()));
        GlossCoverage.Report r = GlossCoverage.of("demo", DEMO, one);

        assertEquals(top.reads(), r.readsCovered());
        assertEquals(1, r.pairsCovered());
        assertEquals(1, r.charactersKnown());
        assertEquals(before.pairs() - 1, r.missing().size());
        assertTrue(r.missing().stream().noneMatch(m -> m.key().equals(top.key())),
                "the glossed pair left the worklist");
        for (GlossCoverage.Missing m : r.missing()) {
            assertEquals(m.codePoint() == top.codePoint(), m.characterKnown(),
                    "only the glossed character is known; its other readings are gaps in an entry");
        }
    }

    @Test
    void anArticleIsCompleteOnlyWhenEveryPairIsCovered() {
        GlossCoverage.Report r = GlossCoverage.of("demo", DEMO, Glosses.none());
        for (GlossCoverage.OfArticle a : r.articles()) {
            assertFalse(a.complete());
            assertEquals(a.pairs(), a.missing().size());
            assertEquals(0, a.readsCovered());
        }
        // Worst first, and the demo set is uniformly worst, so the tie-break
        // is the address - which is what makes the file stable between builds.
        for (int i = 1; i < r.articles().size(); i++) {
            assertTrue(r.articles().get(i - 1).address()
                        .compareTo(r.articles().get(i).address()) < 0,
                    "ties in coverage are broken by address");
        }
    }

    @Test
    void theRenderedReportNamesTheNumbersItIsMadeOf() {
        GlossCoverage.Report r = GlossCoverage.of("demo", DEMO, Glosses.none());
        String text = r.render();
        assertTrue(text.startsWith("Gloss coverage - demo"));
        assertTrue(text.contains(String.format("%7d of %7d", 0, r.reads())),
                "the reads line carries the total");
        assertTrue(text.contains("By article, worst first"));
        assertTrue(text.contains("the worklist"));
        assertTrue(text.contains(r.missing().get(0).glyph()),
                "the most-read missing character is in the worklist");
    }

    // ── A registry of one ──────────────────────────────────────────────

    private static GlossSource single(int codePoint, String reading) {
        var zi = new ZiCharUTF8(codePoint);
        var sense = new Sense(RankingInfo.at(0), Map.of());
        var sound = new SoundGloss(zi, PinyinSyllable.parseCanonical(reading),
                Map.of(new Meaning("a test meaning"), sense));
        var gloss = new ZiGloss(zi, List.of(sound));
        return new GlossSource() {
            @Override public String name() { return "one pair"; }
            @Override public String licence() { return "test"; }
            @Override public List<ZiGloss> entries() { return List.of(gloss); }
        };
    }
}
