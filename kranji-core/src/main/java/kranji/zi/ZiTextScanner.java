package kranji.zi;

import hue.captains.singapura.tao.ontology.StatelessFunctionalObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Finds the characters in a passage of text.
 *
 * <p>Separate from {@link ZiCharUTF8Codec}, which converts a single character.
 * This one reads text, which is a different job with a different failure mode:
 * the naive version walks {@code char} by {@code char} and splits every
 * supplementary character into two broken halves. Iterating by codepoint is
 * the whole point of having this in one place.</p>
 *
 * <h2>What counts</h2>
 *
 * <p>Anything in the Han script, per {@link ZiCharUTF8#isHan}. Ideographic
 * punctuation (。，) and the ideographic description operators (⿰⿱) are
 * {@code COMMON} rather than {@code HAN} and are correctly left out; Kangxi
 * radicals and the CJK Radicals Supplement are {@code HAN} and are kept.</p>
 *
 * <p>Note what this cannot tell you: <b>Han script is not the same as Chinese
 * language</b>. Unicode unifies Han across Chinese, Japanese, Korean and
 * Vietnamese, so Japanese-coined characters such as 峠 and 辻 — never used in
 * Chinese — are {@code HAN} and will be returned. Whether a character belongs
 * to Chinese is a question about language, not script, and no Unicode property
 * answers it. That question is settled by corpus membership instead.</p>
 */
public record ZiTextScanner() implements StatelessFunctionalObject {

    public static final ZiTextScanner INSTANCE = new ZiTextScanner();

    /**
     * Every Han character in {@code text}, in order, repeats included.
     *
     * <p>Repeats matter: readability is a proportion of character
     * <em>occurrences</em>, so a character appearing forty times counts forty
     * times.</p>
     *
     * <p>Everything else — punctuation, spacing, Latin, digits — is dropped.
     * This is not a tokenizer; a renderer still needs the material this
     * discards.</p>
     */
    public List<ZiCharUTF8> scan(String text) {
        if (text == null || text.isEmpty()) return List.of();
        var out = new ArrayList<ZiCharUTF8>();
        text.codePoints()
            .filter(ZiCharUTF8::isHan)
            .forEach(cp -> out.add(new ZiCharUTF8(cp)));
        return List.copyOf(out);
    }

    /**
     * The distinct Han characters in {@code text}, in order of first
     * appearance.
     *
     * <p>The counterpart to {@link #scan}: an article at 94% readability with
     * eight distinct unfamiliar characters is a different proposition from one
     * with a single unfamiliar character repeated forty times, and only this
     * view tells them apart.</p>
     */
    public List<ZiCharUTF8> distinct(String text) {
        if (text == null || text.isEmpty()) return List.of();
        var seen = new LinkedHashSet<ZiCharUTF8>();
        text.codePoints()
            .filter(ZiCharUTF8::isHan)
            .forEach(cp -> seen.add(new ZiCharUTF8(cp)));
        return List.copyOf(seen);
    }
}
