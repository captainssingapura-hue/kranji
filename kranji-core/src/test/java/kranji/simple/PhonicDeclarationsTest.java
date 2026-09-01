package kranji.simple;

import kranji.pinyin.Initial;
import kranji.pinyin.Tone;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the emphasis invariant.
 *
 * <p>Decoupling sound from shape removed every special case from authoring, at
 * the cost of spreading one fact — which reading is principal — across separate
 * declarations. These assert that the spread fact is still checked.</p>
 */
class PhonicDeclarationsTest {

    private static final SimpleZiDsl H = SimpleZiDsl.forInitial(Initial.H);
    private static final SimpleZiDsl X = SimpleZiDsl.forInitial(Initial.X);

    // ── The real corpus ────────────────────────────────────────────────

    @Test
    void theSeededCorpusIsValid() {
        assertEquals(List.of(), PhonicDeclarations.check(SimpleZiRegistry.DECLARATIONS),
                "every seeded character has exactly one principal reading");
    }

    // ── A polyphonic character needs no special handling ───────────────

    @Test
    void aCharacterMayBeDeclaredUnderSeveralSyllables() {
        var decls = List.of(
                H.syl(Finals.AO, Tone.THIRD,  "好"),
                H.syl(Finals.AO, Tone.FOURTH, ZiDecl.alt("好")));

        assertEquals(List.of(), PhonicDeclarations.check(decls));
    }

    @Test
    void readingsMaySpanDifferentInitials() {
        // 行 is xíng and háng - two partitions, one character, no ceremony.
        var decls = List.of(
                X.syl(Finals.ING, Tone.SECOND, "行"),
                H.syl(Finals.ANG, Tone.SECOND, ZiDecl.alt("行")));

        assertEquals(List.of(), PhonicDeclarations.check(decls));
    }

    // ── What emphasis must guarantee ───────────────────────────────────

    @Test
    void aCharacterEmphasisedTwiceIsAnError() {
        var decls = List.of(
                H.syl(Finals.AO, Tone.THIRD,  "好"),
                H.syl(Finals.AO, Tone.FOURTH, "好"));   // both principal

        var findings = PhonicDeclarations.check(decls);
        assertEquals(1, findings.size());
        assertEquals(PhonicDeclarations.Severity.ERROR, findings.get(0).severity());
        assertTrue(findings.get(0).message().contains("more than one"));
        assertTrue(findings.get(0).message().contains("hǎo"));
        assertTrue(findings.get(0).message().contains("hào"));
    }

    @Test
    void aCharacterWithNoEmphasisIsAnError() {
        var decls = List.of(
                H.syl(Finals.AO, Tone.FOURTH, ZiDecl.alt("好")));   // alternate only

        var findings = PhonicDeclarations.check(decls);
        assertEquals(1, findings.size());
        assertEquals(PhonicDeclarations.Severity.ERROR, findings.get(0).severity());
        assertTrue(findings.get(0).message().contains("no principal reading"),
                "nothing could decide what reading to show for it");
    }

    @Test
    void requireValidThrowsAndNamesTheOffender() {
        var decls = List.of(H.syl(Finals.AO, Tone.FOURTH, ZiDecl.alt("好")));

        var e = assertThrows(IllegalStateException.class,
                () -> PhonicDeclarations.requireValid(decls));
        assertTrue(e.getMessage().contains("好"));
        assertTrue(e.getMessage().contains("U+597D"));
    }

    // ── Declaration shape ──────────────────────────────────────────────

    @Test
    void theSameCharacterTwiceInOneSyllableIsRejectedAtConstruction() {
        assertThrows(IllegalArgumentException.class,
                () -> H.syl(Finals.AO, Tone.THIRD, "好", "好"));
    }

    @Test
    void anEmptySyllableIsRejectedAtConstruction() {
        // Through the record rather than the DSL: with zero characters the two
        // syl overloads are ambiguous, which is tolerable only because an empty
        // syllable is invalid regardless of which one you meant.
        assertThrows(IllegalArgumentException.class,
                () -> new SyllableDecl(
                        new kranji.pinyin.PinyinSyllable(Initial.H, Finals.AO, Tone.THIRD),
                        List.of()));
    }

    @Test
    void theTerseFormEmphasisesEveryCharacter() {
        SyllableDecl decl = H.syl(Finals.E, Tone.SECOND, "河", "何", "合");
        assertTrue(decl.characters().stream().allMatch(ZiDecl::principal),
                "the common case is that a character is principally read where it is listed");
        assertEquals(3, decl.characters().size());
    }

    @Test
    void altProducesANonPrincipalAppearance() {
        assertTrue(ZiDecl.of("好") instanceof ZiDecl.Principal);
        assertTrue(ZiDecl.alt("好") instanceof ZiDecl.Alternate);
        assertEquals(ZiDecl.of("好").zi(), ZiDecl.alt("好").zi(),
                "same character, different emphasis");
    }
}
