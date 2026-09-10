package kranji.reading.app.read;

import kranji.phonic.SourceReadings;
import kranji.phonic.SyllableIndex;
import kranji.pinyin.PinyinSyllable;
import kranji.zi.ZiCharUTF8;

import java.util.ArrayList;
import java.util.List;
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
 * <h2>The principal reading, unless the label says otherwise</h2>
 *
 * <p>Each character contributes its principal reading, which is right for a
 * title far more often than not and is the same answer the character pane
 * leads with. Where it is wrong the label says so, in the syntax an article
 * already uses: {@code 地{dì}图}. The braces are an instruction, not content,
 * so they are stripped — a reader sees {@code 地图(dì tú)}.</p>
 *
 * <p>This exists because a title has no context to choose an alternate from
 * and the corpus principal is chosen over running text, where 的-like uses win.
 * 地 is the case that showed it: its principal is {@code de}, so 地球, 地理,
 * 地震 and 扫地 were all annotated with the particle's reading. One override
 * per label fixes each, and the same spelling works here as in a body — the
 * override is read through {@link PinyinSyllable}, so {@code dì} and
 * {@code di4} are one instruction and a typo annotates nothing rather than
 * something wrong.</p>
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
     *
     * <p>An authored reading is written the way an article writes one, and is
     * stripped from what the reader sees: {@code 地{dì}图} is displayed as
     * {@code 地图(dì tú)}.</p>
     */
    public static String sounded(String label) {
        if (label == null || label.isEmpty()) return "";

        var out = new StringBuilder(label.length() * 3);
        var run = new ArrayList<int[]>();          // codepoint, unused
        var authored = new ArrayList<String>();    // parallel: null where none
        int i = 0;
        while (i < label.length()) {
            int cp = label.codePointAt(i);
            i += Character.charCount(cp);
            if (ZiCharUTF8.isHan(cp)) {
                run.add(new int[] { cp });
                String override = null;
                if (i < label.length() && label.charAt(i) == '{') {
                    int close = label.indexOf('}', i);
                    if (close > 0) {
                        override = label.substring(i + 1, close).trim();
                        i = close + 1;
                    }
                }
                authored.add(override);
            } else {
                flush(out, run, authored);
                out.appendCodePoint(cp);
            }
        }
        flush(out, run, authored);
        return out.toString();
    }

    /** Writes the pending run and its reading, and empties the run. */
    private static void flush(StringBuilder out, List<int[]> run, List<String> authored) {
        if (run.isEmpty()) return;
        for (int[] ch : run) out.appendCodePoint(ch[0]);
        String sound = soundOf(run, authored);
        run.clear();
        authored.clear();
        if (!sound.isEmpty()) out.append('(').append(sound).append(')');
    }

    /**
     * The readings, spaced — the authored one where the label gave it, the
     * corpus principal otherwise, or empty if the corpus is missing one.
     */
    private static String soundOf(List<int[]> run, List<String> authored) {
        var sb = new StringBuilder(run.size() * 4);
        for (int k = 0; k < run.size(); k++) {
            String override = authored.get(k);
            String reading;
            if (override != null && !override.isEmpty()) {
                // Read through PinyinSyllable so a label cannot smuggle in
                // something that is not a syllable, and so 'di4' and 'dì' are
                // the same instruction - the same two spellings an article's
                // override accepts.
                try {
                    reading = PinyinSyllable.parse(override).toDiacritic();
                } catch (RuntimeException e) {
                    return "";
                }
            } else {
                Optional<SourceReadings> found =
                        SyllableIndex.instance().readingsOf(new ZiCharUTF8(run.get(k)[0]));
                if (found.isEmpty()) return "";
                reading = found.get().principal().toDiacritic();
            }
            if (!sb.isEmpty()) sb.append(' ');
            sb.append(reading);
        }
        return sb.toString();
    }
}
