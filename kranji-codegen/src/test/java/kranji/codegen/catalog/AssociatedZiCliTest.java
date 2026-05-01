package kranji.codegen.catalog;

import kranji.common.perclass.AllPerclassRecords;
import kranji.common.perclass.promoted.AllPerclassRecordsPromoted;
import kranji.zi.ComposedZiT;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Regression test for {@link AssociatedZiCli#buildIndex(List)}.
 *
 * <p>Locks in the glyph-string-keyed reverse index so the
 * "Associated Zi" feature can't silently regress to instance-equality
 * (which produces 0 hits because the same conceptual radical lives in
 * multiple singleton classes across modules).</p>
 */
final class AssociatedZiCliTest {

    private static List<ComposedZiT> corpus() {
        List<ComposedZiT> all = new ArrayList<>(
                AllPerclassRecords.ALL.size() + AllPerclassRecordsPromoted.ALL.size());
        for (ComposedZiT z : AllPerclassRecords.ALL) {
            if (z.character() != null && !z.character().isEmpty()) all.add(z);
        }
        for (ComposedZiT z : AllPerclassRecordsPromoted.ALL) {
            if (z.character() != null && !z.character().isEmpty()) all.add(z);
        }
        return all;
    }

    @Test
    void index_isNonEmpty() {
        Map<String, List<ComposedZiT>> index = AssociatedZiCli.buildIndex(corpus());
        assertFalse(index.isEmpty(), "index should not be empty");
        assertTrue(index.size() > 500,
                "expected >500 unique slot-glyph keys, got " + index.size());
    }

    /**
     * 钅 (metal radical) appears as the left slot of dozens of common
     * characters. Sanity-check a few well-known ones are present.
     */
    @Test
    void index_findsMetalRadicalUsers() {
        Map<String, List<ComposedZiT>> index = AssociatedZiCli.buildIndex(corpus());
        List<ComposedZiT> users = index.getOrDefault("钅", List.of());
        assertFalse(users.isEmpty(), "expected 钅 to have users");
        assertTrue(users.size() >= 30,
                "expected at least 30 metal-radical chars, got " + users.size());
        assertContainsCharacter(users, "锁");
        assertContainsCharacter(users, "钢");
        assertContainsCharacter(users, "银");
    }

    /** 木 should appear in many TopBottom / LeftRight wood-related compositions. */
    @Test
    void index_findsWoodUsers() {
        Map<String, List<ComposedZiT>> index = AssociatedZiCli.buildIndex(corpus());
        List<ComposedZiT> users = index.getOrDefault("木", List.of());
        assertTrue(users.size() >= 50,
                "expected at least 50 wood-radical chars, got " + users.size());
        assertContainsCharacter(users, "林");
        assertContainsCharacter(users, "村");
        assertContainsCharacter(users, "材");
    }

    /**
     * Anonymous synthetic inner records (empty glyph) must not become
     * a bucket in the index — that would conflate every synthetic and
     * make the lookup useless for the synthetic's actual content.
     */
    @Test
    void index_skipsEmptyGlyphSlots() {
        Map<String, List<ComposedZiT>> index = AssociatedZiCli.buildIndex(corpus());
        assertFalse(index.containsKey(""), "empty-glyph key must not exist");
    }

    private static void assertContainsCharacter(List<ComposedZiT> users, String character) {
        boolean present = users.stream()
                .anyMatch(z -> character.equals(z.character()));
        assertTrue(present, "expected '" + character + "' to be in users list");
    }
}
