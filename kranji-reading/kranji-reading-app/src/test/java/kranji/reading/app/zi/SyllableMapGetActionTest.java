package kranji.reading.app.zi;

import kranji.phonic.PhonicPartitions;
import kranji.phonic.SourceReadings;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the codepoint → readings map.
 *
 * <p>The properties that matter are coverage and disjointness: every character
 * must appear in exactly one partition, or a client will look in the partition
 * the hash names and find nothing.</p>
 */
class SyllableMapGetActionTest {

    private static final Pattern ENTRY = Pattern.compile("(?m)^  (\\d+):\\[");

    private static Set<Integer> codePointsIn(String module) {
        Set<Integer> out = new HashSet<>();
        Matcher m = ENTRY.matcher(module);
        while (m.find()) out.add(Integer.parseInt(m.group(1)));
        return out;
    }

    // ── Data only ──────────────────────────────────────────────────────

    @Test
    void emitsDataOnlyAndNeverBehaviour() {
        String js = SyllableMapGetAction.moduleFor("0");

        for (String construct : new String[]{
                "function", "=>", "class ", "if(", "if (", "for(", "for (",
                "while", "return", "eval", "import", "require", "Function"}) {
            assertFalse(js.contains(construct),
                    () -> "a data module must contain no behaviour, found: " + construct);
        }
    }

    // ── Partitioning ───────────────────────────────────────────────────

    @Test
    void everyCharacterLandsInExactlyOnePartition() {
        Set<Integer> seen = new HashSet<>();
        int total = 0;
        for (int p = 0; p < SyllableMapGetAction.PARTITIONS; p++) {
            Set<Integer> here = codePointsIn(SyllableMapGetAction.moduleFor(String.valueOf(p)));
            for (int cp : here) {
                assertTrue(seen.add(cp),
                        () -> "codepoint appears in two partitions: U+"
                            + Integer.toHexString(cp).toUpperCase());
            }
            total += here.size();
        }
        assertEquals(PhonicPartitions.loadAll().size(), total,
                "every character in the corpus is reachable");
        assertEquals(8100, total);
    }

    @Test
    void aCharacterIsWhereItsHashSaysItIs() {
        // The client computes the partition itself, so this is the contract.
        for (SourceReadings row : PhonicPartitions.loadAll().subList(0, 200)) {
            int cp = row.zi().codePoint();
            int expected = Math.floorMod(cp, SyllableMapGetAction.PARTITIONS);
            assertTrue(codePointsIn(SyllableMapGetAction.moduleFor(String.valueOf(expected)))
                            .contains(cp),
                    () -> row.zi().value() + " is not in partition " + expected);
        }
    }

    @Test
    void partitionsAreEvenlySized() {
        int min = Integer.MAX_VALUE, max = 0;
        for (int p = 0; p < SyllableMapGetAction.PARTITIONS; p++) {
            int n = codePointsIn(SyllableMapGetAction.moduleFor(String.valueOf(p))).size();
            min = Math.min(min, n);
            max = Math.max(max, n);
        }
        // Hashing is what buys this - by codepoint RANGE the spread was 1..493.
        assertTrue(max - min < 100,
                "hash partitions should be within a hundred of each other, got "
              + min + ".." + max);
    }

    // ── Readings are written out, not interned ─────────────────────────

    @Test
    void carriesNoSyllableTableToResolveAgainst() {
        String js = SyllableMapGetAction.moduleFor("0");

        assertFalse(js.contains("export const syllables"),
                "interning bought 1.1x inside a partition - the ids cost more than the strings");
        assertTrue(js.contains("\"y"),
                "readings are written out, so the module carries them literally");
    }

    @Test
    void aModuleStaysSmall() {
        // The interned shape averaged 10.5 KB a module, nearly all of it a
        // table duplicated 101 times. Writing readings out gives about 1.5 KB.
        for (int p = 0; p < SyllableMapGetAction.PARTITIONS; p++) {
            int bytes = SyllableMapGetAction.moduleFor(String.valueOf(p))
                                            .getBytes(StandardCharsets.UTF_8).length;
            int at = p;
            assertTrue(bytes < 4096,
                    () -> "partition " + at + " is " + bytes + " bytes, expected under 4 KB");
        }
    }

    @Test
    void aCharacterListsItsPrincipalReadingFirst() {
        // 好 is hǎo then hào. Whichever partition holds it, the order is fixed.
        int cp = 0x597D;
        String js = SyllableMapGetAction.moduleFor(
                String.valueOf(Math.floorMod(cp, SyllableMapGetAction.PARTITIONS)));

        Matcher m = Pattern.compile("(?m)^  " + cp + ":\\[([^\\]]*)\\]").matcher(js);
        assertTrue(m.find(), "好 should be in the partition its hash names");
        assertEquals("\"hao3\",\"hao4\"", m.group(1),
                "the canonical readings, principal first, with no indirection");
    }

    // ── Unknown input ──────────────────────────────────────────────────

    @Test
    void anOutOfRangePartitionStillYieldsAValidModule() {
        String js = SyllableMapGetAction.moduleFor("999");
        assertTrue(js.contains("export const characters = {};"));
        assertTrue(js.contains("export const problem ="));
    }

    @Test
    void aMalformedPartitionYieldsAValidModule() {
        String js = SyllableMapGetAction.moduleFor("banana");
        assertTrue(js.contains("export const characters = {};"));
        assertTrue(js.contains("export const problem ="));
    }
}
