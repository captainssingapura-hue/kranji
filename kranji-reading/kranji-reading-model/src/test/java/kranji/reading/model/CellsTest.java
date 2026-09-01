package kranji.reading.model;

import kranji.pinyin.PinyinSyllable;
import kranji.zi.ZiCharUTF8Codec;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The rule that makes 禁则 structural: punctuation never gets a square of its
 * own, so a line can never begin with 。 or end with 「.
 */
class CellsTest {

    private static Token.Zi zi(String glyph, String reading) {
        return new Token.Zi(ZiCharUTF8Codec.INSTANCE.from(glyph),
                PinyinSyllable.parse(reading), false);
    }

    private static Token plain(String text) { return new Token.Plain(text); }

    @Test
    void aBareCharacterIsAPlainSquare() {
        List<Cell> cells = Cells.of(List.of(zi("月", "yuè")));

        assertEquals(1, cells.size());
        assertInstanceOf(Cell.Char.class, cells.get(0));
    }

    @Test
    void aClosingMarkJoinsTheCharacterBeforeIt() {
        List<Cell> cells = Cells.of(List.of(zi("光", "guāng"), plain("，")));

        assertEquals(1, cells.size(), "punctuation does not take a square of its own");
        var cell = assertInstanceOf(Cell.CharWithPunctuation.class, cells.get(0));
        assertEquals("，", cell.trailing());
        assertEquals("", cell.leading());
        assertEquals("光", cell.character().text());
    }

    @Test
    void anOpeningMarkJoinsTheCharacterAfterIt() {
        List<Cell> cells = Cells.of(List.of(plain("「"), zi("月", "yuè")));

        assertEquals(1, cells.size());
        var cell = assertInstanceOf(Cell.CharWithPunctuation.class, cells.get(0));
        assertEquals("「", cell.leading());
        assertTrue(cell.hasLeading());
    }

    @Test
    void oneCharacterCanCarryBothEnds() {
        List<Cell> cells = Cells.of(List.of(plain("「"), zi("月", "yuè"), plain("」")));

        assertEquals(1, cells.size());
        var cell = assertInstanceOf(Cell.CharWithPunctuation.class, cells.get(0));
        assertEquals("「", cell.leading());
        assertEquals("」", cell.trailing());
    }

    @Test
    void severalClosingMarksRideTogether() {
        List<Cell> cells = Cells.of(List.of(zi("好", "hǎo"), plain("！」")));

        assertEquals(1, cells.size());
        assertEquals("！」",
                ((Cell.CharWithPunctuation) cells.get(0)).trailing());
    }

    @Test
    void aWholeLineBecomesOneSquarePerCharacter() {
        // 床前明月光，
        List<Cell> cells = Cells.of(List.of(
                zi("床", "chuáng"), zi("前", "qián"), zi("明", "míng"),
                zi("月", "yuè"), zi("光", "guāng"), plain("，")));

        assertEquals(5, cells.size(), "five characters, five squares");
        assertInstanceOf(Cell.CharWithPunctuation.class, cells.get(4));
    }

    // ── The awkward cases ──────────────────────────────────────────────

    @Test
    void aClosingMarkWithNothingBeforeItKeepsItsOwnCell() {
        // Nowhere to attach - dropping it would lose text, so it stands alone.
        List<Cell> cells = Cells.of(List.of(plain("。"), zi("月", "yuè")));

        assertEquals(2, cells.size());
        assertInstanceOf(Cell.Plain.class, cells.get(0));
    }

    @Test
    void latinAndDigitsGetTheirOwnCell() {
        List<Cell> cells = Cells.of(List.of(zi("第", "dì"), plain("3"), zi("课", "kè")));

        assertEquals(3, cells.size());
        assertInstanceOf(Cell.Plain.class, cells.get(1));
        assertEquals("3", ((Cell.Plain) cells.get(1)).text());
    }

    @Test
    void aTrailingOpenMarkDoesNotVanish() {
        List<Cell> cells = Cells.of(List.of(zi("说", "shuō"), plain("「")));

        assertEquals(2, cells.size(), "nothing follows it, so it keeps a cell");
        assertEquals("「", ((Cell.Plain) cells.get(1)).text());
    }

    @Test
    void aCellWithNoPunctuationCannotBeTheePunctuationKind() {
        var character = zi("月", "yuè");
        assertTrue(Cells.of(List.of(character)).get(0) instanceof Cell.Char,
                "the type distinguishes the two cases, so neither can pose as the other");
    }
}
