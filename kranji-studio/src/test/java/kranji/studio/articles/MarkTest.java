package kranji.studio.articles;

import kranji.reading.model.Cells;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The typed model, held against the tables it replaced.
 *
 * <p>{@link Mark} carries the rules that used to live in {@code Cells.CLOSING}
 * and {@code Cells.OPENING} plus a {@code STANDING} string. The strings are
 * still the reader's, so the two must go on agreeing — and agreeing about the
 * rule rather than about the membership, which is what these check.</p>
 */
class MarkTest {

    @Test
    void everyClosingMarkTheReaderKnowsMayNotBeginALine() {
        // 禁则's first half, stated the reader's way. Cells.CLOSING is "must
        // not begin a line", and every one of those characters has to be a
        // Mark that says so.
        Cells.CLOSING.codePoints().forEach(cp -> {
            String ch = new String(Character.toChars(cp));
            Mark mark = Mark.of(ch).orElseThrow(
                    () -> new AssertionError(ch + " is in Cells.CLOSING and is not a Mark"));
            assertFalse(mark.mayBeginLine(), ch + " may not begin a line");
        });
    }

    @Test
    void everyOpeningMarkTheReaderKnowsMayNotEndALine() {
        Cells.OPENING.codePoints().forEach(cp -> {
            String ch = new String(Character.toChars(cp));
            Mark mark = Mark.of(ch).orElseThrow(
                    () -> new AssertionError(ch + " is in Cells.OPENING and is not a Mark"));
            assertFalse(mark.mayEndLine(), ch + " may not end a line");
        });
    }

    @Test
    void marksPackOnlyWithTheirOwnSide() {
        // ”。 shares a box; “ starting the next quotation does not, however
        // adjacent it is, because it belongs to what comes after.
        assertTrue(Mark.CLOSE_DOUBLE.packsWith(Mark.FULL_STOP));
        assertTrue(Mark.FULL_STOP.packsWith(Mark.CLOSE_DOUBLE));
        assertTrue(Mark.OPEN_DOUBLE.packsWith(Mark.OPEN_SINGLE));

        assertFalse(Mark.FULL_STOP.packsWith(Mark.OPEN_DOUBLE));
        assertFalse(Mark.OPEN_DOUBLE.packsWith(Mark.FULL_STOP));
    }

    @Test
    void aMarkWrittenDoubleSharesWithNothing() {
        // Packing 破折号 or 省略号 would turn a dash into a hyphen.
        assertFalse(Mark.DASH.packsWith(Mark.DASH));
        assertFalse(Mark.ELLIPSIS.packsWith(Mark.ELLIPSIS));
        assertFalse(Mark.DASH.packsWith(Mark.FULL_STOP));
        assertFalse(Mark.FULL_STOP.packsWith(Mark.DASH));
        assertFalse(Mark.MIDDLE_DOT.packsWith(Mark.MIDDLE_DOT));
    }

    @Test
    void onlyAnOpeningMarkIsBarredFromEndingALine() {
        // Everything else may. A mark that could neither begin nor end a line
        // would have nowhere to go at a boundary, and hanging already keeps
        // the trailing ones off the head of the next row.
        for (Mark mark : Mark.values()) {
            if (mark.role() == Mark.Role.OPEN) assertFalse(mark.mayEndLine(), mark.text());
            else assertTrue(mark.mayEndLine(), mark.text() + " should be able to end a line");
        }
    }

    @Test
    void onlyAnOpeningMarkMayBeginALine() {
        for (Mark mark : Mark.values()) {
            assertEquals(mark.role() == Mark.Role.OPEN, mark.mayBeginLine(), mark.text());
        }
    }

    @Test
    void theWhitelistIsTheMarkTable() {
        // What needs no ‹…› and what is a Mark are the same question, which is
        // the point of Mark being the lookup the parser uses.
        assertTrue(Mark.is("。"));
        assertTrue(Mark.is("—"));
        assertTrue(Mark.is("·"));

        assertFalse(Mark.is("a"), "Latin belongs in a run");
        assertFalse(Mark.is("1"), "so does a digit");
        assertFalse(Mark.is("/"), "the half-width solidus is not the full-width one");
        assertFalse(Mark.is("中"), "a character is not a mark");
    }
}
