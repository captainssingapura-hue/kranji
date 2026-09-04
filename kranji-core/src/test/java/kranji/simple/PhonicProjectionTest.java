package kranji.simple;

import kranji.phonic.SyllableIndex;
import kranji.pinyin.Final;
import kranji.pinyin.Initial;
import kranji.zi.ZiCharUTF8;
import kranji.zi.ZiCharUTF8Codec;
import kranji.zi.tree.ZiBranch;
import kranji.zi.tree.ZiTerminal;
import kranji.zi.tree.ZiTreeNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhonicProjectionTest {

    private static final PhonicProjection P = PhonicProjection.INSTANCE;

    private static List<ZiTerminal> terminals(ZiBranch root) {
        var out = new ArrayList<ZiTerminal>();
        collect(root, out);
        return out;
    }

    private static void collect(ZiTreeNode node, List<ZiTerminal> out) {
        if (node instanceof ZiTerminal t) { out.add(t); return; }
        node.children().forEach(c -> collect(c, out));
    }

    // ── Shape ──────────────────────────────────────────────────────────

    @Test
    void isThreeLevelsDeepTerminatingAtASyllable() {
        ZiBranch root = P.tree();
        for (ZiTreeNode initial : root.children()) {
            assertInstanceOf(ZiBranch.class, initial, "L1 is an initial");
            for (ZiTreeNode fin : initial.children()) {
                assertInstanceOf(ZiBranch.class, fin, "L2 is a final");
                for (ZiTreeNode tone : fin.children()) {
                    assertInstanceOf(ZiTerminal.class, tone,
                            "L3 is a complete syllable, and it terminates");
                    assertEquals(List.of(), tone.children(),
                            "characters are content, not further nodes");
                }
            }
        }
    }

    @Test
    void oneBranchPerOnsetIncludingTheTwoGlides() {
        assertEquals(Initial.values().length + 2, P.tree().children().size(),
                "22 initials, plus y- and w- which the tree treats as initials too");
    }

    @Test
    void theGlidesAreTopLevelBranchesAndZeroIsLiteral() {
        var segments = P.tree().children().stream().map(ZiTreeNode::segment).toList();

        assertTrue(segments.contains("y"));
        assertTrue(segments.contains("w"));
        assertEquals(List.of("y", "w"), segments.subList(segments.size() - 2, segments.size()),
                "a pinyin table prints them last, after the consonants");

        // What is left under zero is only what is written with no onset letter.
        // "ei" joined the list when SourceCorrections respelt 欸's four ê
        // interjection readings as ēi/éi/ěi/èi - the final was always legal
        // pinyin, and until then no character in the corpus was written with it.
        var bare = P.branchFor(Initial.ZERO).children().stream()
                .map(ZiTreeNode::label).toList();
        assertEquals(List.of("a", "ai", "an", "ang", "ao", "e", "ei", "en", "er", "o", "ou"), bare);
    }

    @Test
    void aGlideBranchHoldsTheSyllablesWrittenWithIt() {
        var y = P.branchFor("y").children().stream().map(ZiTreeNode::label).toList();

        // yi, yin and ying belong here too, though their medial is open - the
        // grouping follows the written form, not the medial.
        assertTrue(y.containsAll(List.of("yi", "yin", "ying")));
        assertTrue(y.containsAll(List.of("ya", "yan", "yao", "you", "yu", "yuan")));
        assertEquals(14, y.size());
        assertEquals(9, P.branchFor("w").children().size());
    }

    @Test
    void everyCharacterAppearsUnderEveryReadingItHas() {
        List<ZiCharUTF8> all = terminals(P.tree()).stream()
                .flatMap(t -> t.characters().stream())
                .toList();

        // Appearances, not characters: a polyphonic character is present
        // under each of its readings, which is the point of decoupling.
        SyllableIndex index = SyllableIndex.instance();
        assertEquals(index.appearanceCount(), all.size());

        assertTrue(all.stream().distinct().count() < all.size(),
                "a polyphonic corpus must show some character more than once");
        assertEquals(index.characterCount(), all.stream().distinct().count(),
                "and every character in the source is reachable");
    }

    // ── The property that bounds the tree ──────────────────────────────

    @Test
    void nodeCountIsBoundedBySyllablesNotByCharacters() {
        ZiBranch root = P.tree();
        long nodes = countNodes(root);
        long characters = root.characterCount();

        // With the seed set most syllables hold one character, so the counts are
        // close. What matters is that nodes track *distinct syllables*: adding
        // homophones grows the content, never the tree.
        assertEquals(terminals(root).size(), distinctSyllables(),
                "one terminal per distinct syllable, no more");
        assertTrue(nodes >= characters / 14,
                "sanity: the tree is not absurdly larger than its content");
    }

    private static long distinctSyllables() {
        return SyllableIndex.instance().syllables().size();
    }

    private static long countNodes(ZiTreeNode n) {
        return 1 + n.children().stream().mapToLong(PhonicProjectionTest::countNodes).sum();
    }

    // ── Addressing ─────────────────────────────────────────────────────

    @Test
    void everySegmentIsAddressSafeAtEveryLevel() {
        assertAddressSafe(P.tree());
    }

    private static void assertAddressSafe(ZiTreeNode node) {
        assertTrue(node.segment().matches("[A-Za-z0-9._-]+"),
                () -> "segment is not address-safe: '" + node.segment() + "'");
        node.children().forEach(PhonicProjectionTest::assertAddressSafe);
    }

    @Test
    void uFinalsFoldToVSoTheyStayAscii() {
        // ü, üe, üan, üen are the four finals whose spelling is not ASCII.
        assertEquals("v", PhonicProjection.segmentFor(Finals.V));
        assertEquals("ve", PhonicProjection.segmentFor(Finals.VE));
        assertEquals("van", PhonicProjection.segmentFor(Finals.VAN));
        assertEquals("ven", PhonicProjection.segmentFor(Finals.VN));
        assertEquals("u", PhonicProjection.segmentFor(Finals.U),
                "u and ü must not collide after the fold");
    }

    @Test
    void initialSegmentsIncludeAnExplicitNameForTheZeroInitial() {
        assertEquals("h", PhonicProjection.segmentFor(Initial.H));
        assertEquals("zh", PhonicProjection.segmentFor(Initial.ZH));
        assertEquals("zero", PhonicProjection.segmentFor(Initial.ZERO));
    }

    @Test
    void aTerminalIsAddressedByToneAndLabelledWithTheSyllable() {
        ZiBranch h = P.branchFor(Initial.H);
        ZiBranch ao = (ZiBranch) h.children().stream()
                .filter(c -> c.segment().equals("ao")).findFirst().orElseThrow();
        ZiTerminal third = (ZiTerminal) ao.children().stream()
                .filter(c -> c.segment().equals("3")).findFirst().orElseThrow();

        assertEquals("hǎo", third.label());
        assertTrue(third.characters().contains(ZiCharUTF8Codec.INSTANCE.from("好")));
    }

    // ── Invariants that protect addressing ─────────────────────────────

    @Test
    void anEmptyTerminalIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new ZiTerminal("1", "hā", List.of()),
                "an empty grouping is noise and should never be emitted");
    }

    @Test
    void aTerminalRejectsARepeatedCharacter() {
        ZiCharUTF8 hao = ZiCharUTF8Codec.INSTANCE.from("好");
        assertThrows(IllegalArgumentException.class,
                () -> new ZiTerminal("3", "hǎo", List.of(hao, hao)));
    }

    @Test
    void siblingSegmentsMustBeUnique() {
        var t = new ZiTerminal("1", "hā", List.of(ZiCharUTF8Codec.INSTANCE.from("好")));
        assertThrows(IllegalArgumentException.class,
                () -> new ZiBranch("ao", "ao", List.of(t, t)),
                "two positions for one segment would break addressing");
    }
}
