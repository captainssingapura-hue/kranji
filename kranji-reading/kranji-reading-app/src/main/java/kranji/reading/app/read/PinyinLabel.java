package kranji.reading.app.read;

import kranji.phonic.SourceReadings;
import kranji.phonic.SyllableIndex;
import kranji.zi.ZiCharUTF8;

import java.util.Optional;

/**
 * A label with its sound written after it — {@code 唐诗} becomes
 * {@code 唐诗(táng shī)}.
 *
 * <h2>Why this and not a shelf's description</h2>
 *
 * <p>A shelf used to carry a sentence of English about what was on it. It was
 * a curator's note rather than a reader's aid: it said what the class of
 * literature was, which a reader who could not read the shelf's own name could
 * not use, and it wanted a column of its own that the tree did not have. The
 * name is the thing a reader has to get past, so the name is what gets
 * help.</p>
 *
 * <h2>A run at a time, not a character at a time</h2>
 *
 * <p>Annotating each character separately — {@code 唐(táng)诗(shī)} — breaks the
 * word up exactly where a reader is trying to hold it together. A run of Han is
 * annotated as a unit, and everything that is not Han passes through untouched,
 * so a shelf named across a separator keeps its shape:
 * {@code 李白(lǐ bái) · 五言(wǔ yán)}.</p>
 *
 * <p>Syllables within a run are spaced rather than run together. Which
 * characters make a word is not known here, and {@code jìngyèsī} claims a
 * word boundary this has no way to have established; spacing them says only
 * what is true, which is that these are the characters' readings in order.</p>
 *
 * <h2>The principal reading, or none</h2>
 *
 * <p>Each character contributes its principal reading. A title is not an
 * article — there is no authored reading to defer to and no context to choose
 * an alternate from, so the corpus's principal is the only answer available.
 * It is right for a title far more often than not, and it is the same answer
 * the character pane leads with.</p>
 *
 * <p>If any character of a run has no reading at all, the whole run goes
 * unannotated. A partial gloss — {@code jìng ? shān} — reads as a fact about
 * the characters rather than as a gap in the corpus, and a reader has no way to
 * tell which it is. Silence is the honest failure.</p>
 */
public final class PinyinLabel {

    private PinyinLabel() {}

    /**
     * The label, with each run of Han followed by its reading in brackets.
     *
     * <p>A label with no Han in it comes back unchanged, which is what makes
     * this safe to apply to every label in a tree rather than to the ones
     * someone has decided are Chinese.</p>
     */
    public static String sounded(String label) {
        if (label == null || label.isEmpty()) return "";

        var out = new StringBuilder(label.length() * 3);
        var run = new StringBuilder();
        int i = 0;
        while (i < label.length()) {
            int cp = label.codePointAt(i);
            i += Character.charCount(cp);
            if (ZiCharUTF8.isHan(cp)) {
                run.appendCodePoint(cp);
            } else {
                flush(out, run);
                out.appendCodePoint(cp);
            }
        }
        flush(out, run);
        return out.toString();
    }

    /** Writes the pending run and its reading, and empties the run. */
    private static void flush(StringBuilder out, StringBuilder run) {
        if (run.isEmpty()) return;
        String han = run.toString();
        run.setLength(0);

        out.append(han);
        String sound = soundOf(han);
        if (!sound.isEmpty()) out.append('(').append(sound).append(')');
    }

    /** The principal readings, spaced — or empty if the corpus is missing one. */
    private static String soundOf(String han) {
        var sb = new StringBuilder(han.length() * 4);
        int i = 0;
        while (i < han.length()) {
            int cp = han.codePointAt(i);
            i += Character.charCount(cp);
            Optional<SourceReadings> found =
                    SyllableIndex.instance().readingsOf(new ZiCharUTF8(cp));
            if (found.isEmpty()) return "";
            if (!sb.isEmpty()) sb.append(' ');
            sb.append(found.get().principal().toDiacritic());
        }
        return sb.toString();
    }
}
