package kranji.zi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The slice rule, and the two properties everything downstream leans on.
 *
 * <p>A partition is not a number somebody chose — it is a promise that the
 * browser and the workbench put the same character in the same place. These
 * are the parts of that promise a change could break silently.</p>
 */
class ZiPartitionTest {

    @Test
    void aCharacterAlwaysLandsInTheSameSlice() {
        // The whole point. A reviewer marks partition 7 done; the reader later
        // asks for partition 7 and must get what was reviewed.
        int di = "地".codePointAt(0);
        assertEquals(ZiPartition.of(di), ZiPartition.of(di));
        assertEquals(ZiPartition.of(di), ZiPartition.of(new ZiCharUTF8(di)));
    }

    @Test
    void everySliceIsOneThatExists() {
        // Across the whole Han range, not a sample: an index outside 0..COUNT-1
        // is a request for a partition nothing serves.
        for (int cp = 0x4E00; cp <= 0x9FFF; cp++) {
            final int at = cp, p = ZiPartition.of(cp);
            assertTrue(ZiPartition.exists(p),
                    () -> "U+" + Integer.toHexString(at) + " -> " + p);
        }
    }

    @Test
    void aNegativeCodepointDoesNotYieldANegativeSlice() {
        // Java's % keeps the sign of its left operand, so plain remainder would
        // answer -1 here - an index that reads as a partition and is not one.
        // Codepoints are never negative until something parses a "-1".
        assertTrue(ZiPartition.exists(ZiPartition.of(-1)));
        assertTrue(ZiPartition.exists(ZiPartition.of(Integer.MIN_VALUE)));
    }

    @Test
    void theCountIsPrimeSoTheSplitDoesNotInheritUnicodeSStrides() {
        // Han characters sit in dense radical-ordered blocks, so a composite
        // modulus - a power of two worst of all - keeps that structure and
        // clumps. This is the property the evenness rests on, so it is stated
        // rather than assumed from the literal.
        int n = ZiPartition.COUNT;
        assertTrue(n > 1);
        for (int d = 2; (long) d * d <= n; d++) {
            final int by = d;
            assertFalse(n % by == 0, () -> n + " is divisible by " + by);
        }
    }

    @Test
    void theStandardCorpusSplitsEvenlyEnoughToBeAWorkQueue() {
        // 8,100 characters over 101 slices. An even split is why a partition is
        // a predictable sitting rather than a lottery; a clumped one would put
        // hundreds in a few and single figures in others.
        int[] count = new int[ZiPartition.COUNT];
        int total = 0;
        for (int cp = 0x4E00; cp <= 0x9FFF; cp++) {
            count[ZiPartition.of(cp)]++;
            total++;
        }
        int min = Integer.MAX_VALUE, max = 0;
        for (int c : count) { min = Math.min(min, c); max = Math.max(max, c); }

        assertEquals(total, java.util.Arrays.stream(count).sum(),
                "every character lands in exactly one slice");
        final int lo = min, hi = max;
        assertTrue(hi - lo <= 1,
                () -> "a contiguous range should divide to within one: " + lo + ".." + hi);
    }
}
