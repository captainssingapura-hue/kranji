package kranji.codegen.singulars;

import kranji.pinyin.Initial;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Objects;

/**
 * Maps a Mandarin syllable's onset to a one-or-two-letter sub-package name.
 *
 * <p>Two entry points cover the two information shapes the codegen sees:</p>
 * <ul>
 *   <li>{@link #forEnum(Initial)} — used when a structured
 *       {@link Initial} is available (e.g. from a parsed
 *       {@code SingularZi.pinyin()} list).</li>
 *   <li>{@link #fromPinyinText(String)} — used when only the display-form
 *       pinyin (with tone marks) is available, which is the common case
 *       in today's records (most {@code SingularZi} records override only
 *       {@code pinyinText()} and leave {@code pinyin()} as the empty
 *       default list).</li>
 * </ul>
 *
 * <p>Mapping rules:</p>
 * <ul>
 *   <li>{@link Initial#ZERO} → {@code "zero"}.</li>
 *   <li>Every other {@link Initial} value → the lowercase pinyin
 *       spelling ({@code "b"}, {@code "p"}, …, {@code "zh"}, {@code "ch"},
 *       {@code "sh"}).</li>
 *   <li>For {@code fromPinyinText}, syllables that begin with the glide
 *       letters {@code y}/{@code w} are treated as zero-initial — the
 *       Mandarin convention is that {@code yi}, {@code yu}, {@code wu},
 *       {@code wan}, … carry no consonant onset, so they all land in the
 *       {@code zero} bucket.</li>
 * </ul>
 */
public final class InitialPackage {

    private InitialPackage() {}

    /** Sub-package name for the zero initial — lowercased English word. */
    public static final String ZERO_PACKAGE = "zero";

    /** Returns the sub-package name for a typed {@link Initial}. */
    public static String forEnum(Initial initial) {
        Objects.requireNonNull(initial, "initial");
        return initial == Initial.ZERO ? ZERO_PACKAGE : initial.pinyin();
    }

    /**
     * Returns the sub-package name derived from a tone-marked pinyin string
     * such as {@code "wáng"}, {@code "yī"}, or {@code "zhōng"}.
     *
     * <p>Empty input maps to {@code "zero"}. Inputs whose stripped form
     * does not start with a recognised consonant onset (including the
     * glide-only syllables that begin with {@code y} or {@code w}) also
     * map to {@code "zero"}.</p>
     *
     * @throws NullPointerException if {@code pinyinText} is null
     */
    public static String fromPinyinText(String pinyinText) {
        Objects.requireNonNull(pinyinText, "pinyinText");
        String ascii = Normalizer.normalize(pinyinText, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase(Locale.ROOT)
                .trim();
        if (ascii.isEmpty()) return ZERO_PACKAGE;

        // Two-letter retroflex onsets must be tested before single letters.
        if (ascii.startsWith("zh")) return "zh";
        if (ascii.startsWith("ch")) return "ch";
        if (ascii.startsWith("sh")) return "sh";

        char head = ascii.charAt(0);
        // The 18 single-consonant initials of Mandarin.
        switch (head) {
            case 'b': case 'p': case 'm': case 'f':
            case 'd': case 't': case 'n': case 'l':
            case 'g': case 'k': case 'h':
            case 'j': case 'q': case 'x':
            case 'r':
            case 'z': case 'c': case 's':
                return String.valueOf(head);
            default:
                // 'y', 'w', vowels, ü — all zero-initial in Mandarin.
                return ZERO_PACKAGE;
        }
    }
}
