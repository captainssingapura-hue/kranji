package kranji.ui.demo;

import kranji.common.perclass.staging.AllZiRecords;
import kranji.library.BasicSet;
import kranji.singular.SingularFamiliesPerclass;
import kranji.zi.BlockStructure;
import kranji.zi.ComposedBlock;
import kranji.zi.SingularPart;
import kranji.zi.SingularZi;
import kranji.zi.Zi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Mirrors the UI's exact corpus + index build path
 * ({@link KranjiDemoApp#buildLists()} and
 * {@link KranjiDemoApp#buildImmediateUsersIndex()}) but without
 * JavaFX, so we can prove the data layer works regardless of any UI
 * interaction.
 *
 * <p>If this test passes, the "Associated tab is always 0" report is
 * either user-perception (clicked only on chars with no parents) or a
 * UI-rendering issue — not a data-path issue.</p>
 */
final class AssociatedZiUiPathTest {

    private static List<Zi> allList;
    private static Map<String, List<Zi>> index;

    @BeforeAll
    static void buildCorpusAndIndex() {
        // Same as KranjiDemoApp.start() prelude.
        SingularFamiliesPerclass.registerInto(BasicSet.INSTANCE);
        kranji.library.ZiLibrary.load(BasicSet.INSTANCE);

        // Same as buildLists().
        var singulars = new ArrayList<Zi>();
        var parts = new ArrayList<Zi>();
        for (var member : BasicSet.INSTANCE.components()) {
            if (member instanceof SingularZi sz) singulars.add(sz);
            else if (member instanceof SingularPart sp) parts.add(new PartAsZiAdapter(sp));
        }
        var composed = new ArrayList<Zi>();
        for (Zi z : AllZiRecords.ALL) {
            String g = z.character();
            if (g != null && !g.isEmpty()) composed.add(z);
        }
        var all = new ArrayList<Zi>();
        all.addAll(singulars);
        all.addAll(parts);
        all.addAll(composed);
        allList = List.copyOf(all);

        // Same as buildImmediateUsersIndex().
        index = new HashMap<>();
        for (Zi z : allList) {
            if (!(z.structure() instanceof ComposedBlock cb)) continue;
            for (BlockStructure slot : cb.composition().components()) {
                String g = slot.glyph();
                if (g == null || g.isEmpty()) continue;
                index.computeIfAbsent(g, k -> new ArrayList<>()).add(z);
            }
        }
    }

    @Test
    void corpus_hasExpectedSize() {
        assertTrue(allList.size() > 2000,
                "expected >2000 Zi in allList, got " + allList.size());
    }

    @Test
    void index_hasExpectedSize() {
        assertTrue(index.size() > 500,
                "expected >500 unique slot keys, got " + index.size());
    }

    @Test
    void index_findsCommonRadicals() {
        // Each of these should map to many parents — if any are 0, the
        // UI's data path is broken for that glyph.
        assertHasManyUsers("木", 50);   // wood
        assertHasManyUsers("口", 50);   // mouth
        assertHasManyUsers("钅", 30);   // metal radical
        assertHasManyUsers("贝", 20);   // shell
        assertHasManyUsers("氵", 100);  // water radical
        assertHasManyUsers("亻", 50);   // person radical
        assertHasManyUsers("扌", 100);  // hand radical
    }

    @Test
    void index_specificCharactersPresent() {
        // 钅 should include 锁 (lock) — known immediate use.
        var metal = index.getOrDefault("钅", List.of());
        assertTrue(metal.stream().anyMatch(z -> "锁".equals(z.character())),
                "expected 锁 to be among 钅 users");

        // 木 should include 林 (forest = 木+木).
        var wood = index.getOrDefault("木", List.of());
        assertTrue(wood.stream().anyMatch(z -> "林".equals(z.character())),
                "expected 林 to be among 木 users");
    }

    private static void assertHasManyUsers(String glyph, int min) {
        var users = index.getOrDefault(glyph, List.of());
        assertTrue(users.size() >= min,
                "expected " + glyph + " to have >=" + min
                        + " users, got " + users.size());
    }

    /** Mirrors KranjiDemoApp.PartAsZi (private record there). */
    private record PartAsZiAdapter(SingularPart part) implements Zi {
        @Override public String character()  { return part.glyph(); }
        @Override public kranji.pinyin.PinyinSyllable pinyin() { return null; }
        @Override public int strokes()       { return part.strokes(); }
        @Override public int radicalNo()     { return 0; }
        @Override public String meaning()    { return part.meaning(); }
        @Override public BlockStructure structure() { return part; }
        @Override public kranji.classification.EtymologicalCategory etymology() {
            return new kranji.classification.EtymologicalCategory.Pictograph();
        }
    }
}
