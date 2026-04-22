package kranji.zi;

import kranji.classification.EtymologicalCategory;
import kranji.pinyin.Body;
import kranji.pinyin.Final;
import kranji.pinyin.Head;
import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;
import kranji.pinyin.Tail;
import kranji.pinyin.Tone;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit coverage of {@link BlockStructures#depthOf(BlockStructure)} and
 * the {@link CompositionLayout#components()} / {@link ComposedBlock#components()}
 * walkers that back it.
 *
 * <p>Uses a tiny in-file {@link SingularBlock} stub so the test has no
 * dependency on {@code kranji-singulars}.</p>
 */
final class BlockStructuresDepthTest {

    /** Minimal stub leaf — glyph only, everything else defaults. */
    private record Atom(String glyph) implements SingularBlock {}

    private static final Atom A = new Atom("A");
    private static final Atom B = new Atom("B");
    private static final Atom C = new Atom("C");

    @Test
    void singular_is_depth_zero() {
        assertEquals(0, BlockStructures.depthOf(A));
    }

    @Test
    void simple_composed_part_is_depth_one() {
        // LeftRight(A, B) wrapped as ComposedPart
        ComposedPart lr = new ComposedPart(new CompositionLayout.LeftRight(A, B));
        assertEquals(1, BlockStructures.depthOf(lr));
    }

    @Test
    void nested_composed_part_is_depth_two() {
        // TopBottom(A, ComposedPart(LeftRight(B, C))) → 1 + max(0, 1) = 2
        ComposedPart inner = new ComposedPart(new CompositionLayout.LeftRight(B, C));
        ComposedPart outer = new ComposedPart(new CompositionLayout.TopBottom(A, inner));
        assertEquals(2, BlockStructures.depthOf(outer));
    }

    @Test
    void components_of_layout_returns_level_one_slots_in_order() {
        CompositionLayout.LeftMiddleRight lmr =
                new CompositionLayout.LeftMiddleRight(A, B, C);
        assertEquals(List.<BlockStructure>of(A, B, C), lmr.components());
    }

    @Test
    void components_of_composed_block_delegates_to_layout() {
        ComposedPart cp = new ComposedPart(new CompositionLayout.TopMiddleBottom(A, B, C));
        assertEquals(List.<BlockStructure>of(A, B, C), cp.components());
    }

    @Test
    void forEachComposedZiChild_skips_singulars_and_recurses_through_parts() {
        // Build a tiny ComposedZi to use as a "registered" identity target.
        ComposedZi fakeZi = new ComposedZi(
                new ZiChar.Unicode("\u4E00"),
                new PinyinSyllable(Initial.ZERO, new Final(Head.OPEN, Body.I, Tail.NONE), Tone.FIRST),
                1, 1, "",
                new CompositionLayout.LeftRight(A, B),
                new EtymologicalCategory.Pictograph());

        // Top: TopBottom(fakeZi, ComposedPart(LeftRight(A, fakeZi)))
        //   - level-1 component 1: ComposedZi fakeZi → consumer once
        //   - level-1 component 2: ComposedPart → recurse; inside:
        //       - A (singular) → skip
        //       - fakeZi (ComposedZi) → consumer once
        ComposedPart inner = new ComposedPart(new CompositionLayout.LeftRight(A, fakeZi));
        ComposedPart top = new ComposedPart(new CompositionLayout.TopBottom(fakeZi, inner));

        List<ComposedZi> seen = new ArrayList<>();
        BlockStructures.forEachComposedZiChild(top, seen::add);

        assertEquals(2, seen.size(), "expected two ComposedZi visits");
        assertEquals(fakeZi, seen.get(0));
        assertEquals(fakeZi, seen.get(1));
    }

    @Test
    void forEachComposedZiChild_does_not_recurse_into_a_named_zi() {
        // If a slot is itself a ComposedZi, its OWN composition must NOT
        // be traversed — the walker treats named Zis as terminals so
        // callers auditing their own module's records don't accidentally
        // inspect the internals of a depth-below record.
        Atom inside = new Atom("inside");
        ComposedZi child = new ComposedZi(
                new ZiChar.Unicode("\u4E01"),
                new PinyinSyllable(Initial.D, new Final(Head.OPEN, Body.I, Tail.NG), Tone.FIRST),
                2, 1, "",
                new CompositionLayout.LeftRight(inside, inside),
                new EtymologicalCategory.Pictograph());

        ComposedPart outer = new ComposedPart(new CompositionLayout.TopBottom(A, child));

        List<ComposedZi> seen = new ArrayList<>();
        BlockStructures.forEachComposedZiChild(outer, seen::add);

        // Only `child` is reported — its internal `inside`/`inside` slots
        // are not exposed to the consumer.
        assertEquals(List.of(child), seen);
    }

    @Test
    void deeply_nested_tree_matches_worked_example() {
        // Mirrors the biáng shape (depth 5):
        //   SemiEnclosureBottomLeft(
        //     atom,                                              // 0
        //     ComposedPart(TopMiddleBottom(                       // 4
        //       atom,                                             // 0
        //       ComposedPart(LeftMiddleRight(                     // 3
        //         atom,                                           // 0
        //         ComposedPart(TopBottom(                         // 2
        //           ComposedPart(LeftMiddleRight(A, B, C)),       // 1
        //           ComposedPart(LeftMiddleRight(A, B, C)))),     // 1
        //         atom)),                                         // 0
        //       atom)))                                           // 0
        ComposedPart lmr1 = new ComposedPart(new CompositionLayout.LeftMiddleRight(A, B, C));
        ComposedPart lmr2 = new ComposedPart(new CompositionLayout.LeftMiddleRight(A, B, C));
        ComposedPart tb   = new ComposedPart(new CompositionLayout.TopBottom(lmr1, lmr2));
        ComposedPart lmr3 = new ComposedPart(new CompositionLayout.LeftMiddleRight(A, tb, B));
        ComposedPart tmb  = new ComposedPart(new CompositionLayout.TopMiddleBottom(A, lmr3, B));
        ComposedPart root = new ComposedPart(new CompositionLayout.SemiEnclosureBottomLeft(A, tmb));

        assertEquals(1, BlockStructures.depthOf(lmr1));
        assertEquals(1, BlockStructures.depthOf(lmr2));
        assertEquals(2, BlockStructures.depthOf(tb));
        assertEquals(3, BlockStructures.depthOf(lmr3));
        assertEquals(4, BlockStructures.depthOf(tmb));
        assertEquals(5, BlockStructures.depthOf(root));
    }
}
