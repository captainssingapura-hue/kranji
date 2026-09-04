package kranji.phonic;

import kranji.pinyin.Initial;
import kranji.zi.ZiCharUTF8Codec;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pins the extracted source data.
 *
 * <p>These numbers are the ones the plan's decisions were argued from, so a
 * Unihan revision that changes them should fail here rather than quietly
 * shift the corpus underneath everything downstream.</p>
 */
class PhonicPartitionsTest {

    private static final List<SourceReadings> ALL = PhonicPartitions.loadAll();

    // ── Coverage ───────────────────────────────────────────────────────

    @Test
    void everyInitialHasAPartition() {
        for (Initial initial : Initial.values()) {
            assertFalse(PhonicPartitions.load(initial).isEmpty(),
                    () -> "no characters filed under " + initial);
        }
    }

    @Test
    void theStandardSetIsCoveredExceptWhatCannotBeParsed() {
        assertEquals(8100, ALL.size(),
                "8,105 in the standard set, less the 5 whose only readings are unmodellable");
    }

    @Test
    void aCharacterIsFiledExactlyOnce() {
        assertEquals(ALL.size(), ALL.stream().map(SourceReadings::zi).distinct().count(),
                "partitions describe characters, not appearances - unlike the DSL tiers");
    }

    @Test
    void appearanceAndSyllableCountsMatchWhatThePlanClaims() {
        long appearances = ALL.stream().mapToLong(SourceReadings::readingCount).sum();
        long syllables = ALL.stream().flatMap(r -> r.all().stream()).distinct().count();
        long polyphonic = ALL.stream().filter(SourceReadings::isPolyphonic).count();

        assertEquals(8759, appearances);
        assertEquals(1284, syllables, "the tree is bounded by phonology, and this is the bound");
        assertEquals(592, polyphonic);
    }

    // ── The readings themselves ────────────────────────────────────────

    @Test
    void aPolyphonicCharacterKeepsEveryReadingWithOnePrincipal() {
        SourceReadings hao = find("好");

        assertEquals("hǎo", hao.principal().toDiacritic());
        assertEquals(List.of("hào"), hao.alternates().stream()
                .map(s -> s.toDiacritic()).toList());
        assertTrue(hao.isPolyphonic());
        assertTrue(hao.mandarinAgrees());
    }

    @Test
    void readingsMaySpanInitialsWithoutSplittingTheCharacter() {
        // 行 is xíng, háng and héng. It is filed once, under its principal.
        SourceReadings xing = find("行");

        assertEquals("xíng", xing.principal().toDiacritic());
        assertEquals(3, xing.readingCount());
        assertTrue(PhonicPartitions.load(Initial.X).contains(xing));
        assertFalse(PhonicPartitions.load(Initial.H).contains(xing),
                "a character has one home, even when its readings do not");
    }

    @Test
    void unparseableReadingsAreKeptRatherThanDropped() {
        SourceReadings heng = find("哼");

        assertEquals(List.of("hng"), heng.unparseable());
        assertEquals("hēng", heng.principal().toDiacritic());
        assertFalse(heng.isPolyphonic(), "the modellable part is a single reading");
    }

    // ── Evidence and disagreement ──────────────────────────────────────

    @Test
    void frequencyEvidenceIsParsedWhereItExists() {
        ReadingFrequency f = find("好").frequency();

        assertEquals(6060, f.countOf("hǎo"));
        assertEquals(115, f.countOf("hào"));
        assertEquals(List.of("hǎo", "hāo", "hào"), f.byDescendingCount());
    }

    @Test
    void mostCharactersHaveNoFrequencyEvidence() {
        long withEvidence = ALL.stream().filter(r -> !r.frequency().isEmpty()).count();
        assertEquals(2827, withEvidence,
                "kHanyuPinlu reaches 2,829 of the 8,105 - two of those are among the 5 dropped");
    }

    @Test
    void theReviewQueueIsSmallEnoughToWorkThrough() {
        var findings = SourceFindings.check(ALL);

        assertEquals(26, SourceFindings.of(findings,
                SourceFindings.Kind.MANDARIN_NOT_IN_STANDARD).size());
        assertEquals(9, SourceFindings.of(findings,
                SourceFindings.Kind.FREQUENCY_DISAGREES).size());
        // Only 哼 (hng) and 欸 (ê̄ ế ê̌ ề) keep an unmodellable reading beside a
        // usable one. Five more had nothing else and never reached a partition,
        // so seven characters in the standard set are touched in all.
        assertEquals(2, SourceFindings.of(findings,
                SourceFindings.Kind.UNPARSEABLE_READING).size());
    }

    @Test
    void theEvidenceRanksTheSameWayOnEveryRun() {
        // An immutable map's iteration order varies per JVM, so a tie at the
        // top would otherwise resolve differently run to run - and generate a
        // different corpus. 哦 is ò 98 against ó 98.
        ReadingFrequency o = find("哦").frequency();

        assertTrue(o.isTiedAtTop(), "the top two are observed equally often");
        assertEquals("ò", o.mostFrequent().orElseThrow());
        assertEquals(o.byDescendingCount(), o.byDescendingCount());
    }

    @Test
    void aFrequencyDisagreementNamesBothSides() {
        // 得 is principally dé by the dictionary, but de - the particle -
        // is what a corpus of running text is full of.
        SourceReadings de = find("得");

        assertEquals("dé", de.principal().toDiacritic());
        assertEquals("de", de.frequencyDissent().orElseThrow());
    }

    private static SourceReadings find(String glyph) {
        var zi = ZiCharUTF8Codec.INSTANCE.from(glyph);
        return ALL.stream().filter(r -> r.zi().equals(zi)).findFirst()
                .orElseThrow(() -> new AssertionError("not in the source data: " + glyph));
    }
}
