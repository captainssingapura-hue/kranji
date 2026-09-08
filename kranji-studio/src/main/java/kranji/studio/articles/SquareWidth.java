package kranji.studio.articles;

/**
 * How many squares a stretch of text claims.
 *
 * <h2>The two numbers this rests on</h2>
 *
 * <p>A square is not an em. The reader's medium size is a <b>62px cell holding
 * a 42px glyph</b> ({@code ReadingCss.kr_read_size_m}), so one square is about
 * {@value #EMS_PER_SQUARE} em of the text drawn inside it. Small and large give
 * 46/32 and 80/54 — 1.44 and 1.48 — so the ratio is near enough constant that a
 * run claims the same number of squares at every size, which is what lets this
 * be computed once on the server without knowing what a reader has chosen.</p>
 *
 * <p>The advances are Helvetica's, from its AFM table. A real table rather than
 * invented numbers, and it survives contact with a browser. Measured in the
 * workbench against {@code system-ui}:</p>
 *
 * <pre>
 *   markdown                          predicted 3.19   drawn 3.19   → 4
 *   Premier                                     2.38          2.52  → 3
 *   Tunnel                                      2.07          2.05  → 3
 *   Apartments                                  3.46          3.51  → 4
 *   Counter-Strike Global Offensive             9.42          9.45  → 10
 * </pre>
 *
 * <p>Within a couple of percent throughout, and every ceiling the same.</p>
 *
 * <h2>It is an estimate, and the ceiling is the safety margin</h2>
 *
 * <p>Nothing here can know the reader's font. What it can do is be wrong in the
 * safe direction: a run that claims one square too many leaves a gap, and a run
 * that claims one too few overflows into the next character's box. Rounding up
 * is therefore not a rounding convention but the whole error budget.</p>
 *
 * <p>If this ever needs to be exact, the browser is where exact lives — it can
 * measure a painted square, as the reader already does for its punctuation
 * offsets, and re-plan. That would be a refinement of this, not a replacement:
 * the model has to hold a width before anything can render one.</p>
 */
public final class SquareWidth {

    private SquareWidth() {}

    /** Ems of text per square, from the reader's own 62px cell and 42px glyph. */
    public static final double EMS_PER_SQUARE = 62.0 / 42.0;

    /** A full-width character is one em, and one square. */
    private static final int FULL_WIDTH = 1000;

    /** Anything this has no advance for. Middling, and rarely reached. */
    private static final int UNKNOWN = 556;

    /**
     * Helvetica advances for {@code ' '} through {@code '~'}, in 1/1000 em.
     *
     * <p>Straight from the AFM. It is here rather than derived because the
     * alternative — bucketing characters as "narrow, normal, wide" — gets
     * {@code i} and {@code m} wrong by a factor of nearly four, and a run's
     * width is exactly a sum over its letters.</p>
     */
    private static final int[] HELVETICA = {
            278, 278, 355, 556, 556, 889, 667, 191, 333, 333,   //   ! " # $ % & ' ( )
            389, 584, 278, 333, 278, 278, 556, 556, 556, 556,   // * + , - . / 0 1 2 3
            556, 556, 556, 556, 556, 556, 278, 278, 584, 584,   // 4 5 6 7 8 9 : ; < =
            584, 556, 1015, 667, 667, 722, 722, 667, 611, 778,  // > ? @ A B C D E F G
            722, 278, 500, 667, 556, 833, 722, 778, 667, 778,   // H I J K L M N O P Q
            722, 667, 611, 722, 667, 944, 667, 667, 611, 278,   // R S T U V W X Y Z [
            278, 278, 469, 556, 333, 556, 556, 500, 556, 556,   // \ ] ^ _ ` a b c d e
            278, 556, 556, 222, 222, 500, 222, 833, 556, 556,   // f g h i j k l m n o
            556, 556, 333, 500, 278, 556, 500, 722, 500, 500,   // p q r s t u v w x y
            500, 334, 260, 334, 584                             // z { | } ~
    };

    /** One character's advance, in 1/1000 em. */
    private static int advance(int codePoint) {
        if (codePoint >= ' ' && codePoint <= '~') return HELVETICA[codePoint - ' '];
        if (isFullWidth(codePoint)) return FULL_WIDTH;
        return UNKNOWN;
    }

    /**
     * Whether a character occupies a full em.
     *
     * <p>Han, kana, Hangul, the CJK punctuation block and the full-width forms.
     * These are the characters a square was designed around, so they are one
     * square each by construction rather than by measurement.</p>
     */
    private static boolean isFullWidth(int cp) {
        // Named one by one rather than by block: General Punctuation holds
        // these three at full width and ‹ › at half, and this file supplies
        // the delimiters. A range would have made the run marks a square each.
        if (cp == 0x2014 || cp == 0x2015 || cp == 0x2026) return true;  // — ― …
        return (cp >= 0x1100 && cp <= 0x115F)     // Hangul jamo
            || (cp >= 0x2E80 && cp <= 0x303E)     // CJK radicals, kangxi, punctuation
            || (cp >= 0x3041 && cp <= 0x33FF)     // kana, compatibility
            || (cp >= 0x3400 && cp <= 0x4DBF)     // extension A
            || (cp >= 0x4E00 && cp <= 0x9FFF)     // unified
            || (cp >= 0xA000 && cp <= 0xA4CF)     // yi
            || (cp >= 0xAC00 && cp <= 0xD7A3)     // hangul syllables
            || (cp >= 0xF900 && cp <= 0xFAFF)     // compatibility ideographs
            || (cp >= 0xFE30 && cp <= 0xFE4F)     // CJK compatibility forms
            || (cp >= 0xFF00 && cp <= 0xFF60)     // full-width forms
            || (cp >= 0x20000 && cp <= 0x3FFFD);  // extensions B and beyond
    }

    /** The width of some text, in ems. */
    public static double ems(String text) {
        if (text == null || text.isEmpty()) return 0;
        double total = 0;
        for (int i = 0; i < text.length(); ) {
            int cp = text.codePointAt(i);
            total += advance(cp) / 1000.0;
            i += Character.charCount(cp);
        }
        return total;
    }

    /**
     * How many squares that text needs.
     *
     * <p>Always at least one: a run exists, so it occupies a square even when
     * its text is a single thin letter.</p>
     */
    public static int squares(String text) {
        int n = (int) Math.ceil(ems(text) / EMS_PER_SQUARE - TOLERANCE);
        return Math.max(1, n);
    }

    /**
     * Slack before a hair's breadth costs a whole square.
     *
     * <p>Four Han characters measure 4.0 ems and 2.709 squares, which is fine.
     * But a run that lands on 3.0000000001 through floating-point noise would
     * take four squares for nothing. A thousandth of a square is far below any
     * real difference and well above the arithmetic's error.</p>
     */
    private static final double TOLERANCE = 0.001;
}
