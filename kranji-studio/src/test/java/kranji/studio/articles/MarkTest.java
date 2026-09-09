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
    void onlyAnOpeningMarkIsBarredFromEndingALine() {
        // Everything else may. A mark that could neither begin nor end a line
        // would have nowhere to go at a row boundary at all.
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
    void whatIsAMarkAndWhatIsJustACharacter() {
        // This used to be the whitelist: what needed no ‹…› and what was a Mark
        // were the same question. Nothing needs wrapping now, so all it decides
        // is which square a character lands in - a mark's or a letter's.
        assertTrue(Mark.of("。").isPresent());
        assertTrue(Mark.of("—").isPresent());
        assertTrue(Mark.of("·").isPresent());

        assertTrue(Mark.of("a").isEmpty(), "a letter is a letter");
        assertTrue(Mark.of("1").isEmpty(), "so is a digit");
        assertTrue(Mark.of("/").isEmpty(),
                "the half-width solidus is not the full-width one");
        assertTrue(Mark.of("中").isEmpty(), "a character is not a mark");
    }
}
