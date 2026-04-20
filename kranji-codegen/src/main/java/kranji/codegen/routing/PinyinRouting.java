package kranji.codegen.routing;

import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;

/**
 * Pure routing helper that maps a {@link PinyinSyllable} to its per-pinyin
 * catalog address — the {@code <initial-sub-package>/<Final><Tone>} pair
 * used throughout the codegen pipeline (snapshot + skeleton + generate,
 * depths 1 through 5).
 *
 * <p>The addressing scheme is <em>depth-invariant</em>: every depth lays
 * records out under {@code kranji.common.depth<N>.<initial>.<Final><Tone>},
 * so a single routing helper serves all depths.</p>
 *
 * <h2>Naming rules</h2>
 * <ul>
 *   <li><b>Sub-package</b> = {@link Initial#pinyin()} lowercased; the empty
 *       string ({@link Initial#ZERO}) is spelled out as literal
 *       {@code "zero"} to keep it a valid Java package segment.</li>
 *   <li><b>Class-name stem</b> = {@code Final.spelling()} with {@code ü}→{@code v};
 *       if the spelling is empty (only possible for zero-nucleus
 *       syllables like {@code zhi}, which decompose to Head.OPEN + Body.NULL
 *       + Tail.NONE → "") it falls back to {@code "i"}. The first character
 *       is then PascalCased.</li>
 *   <li><b>Class-name suffix</b> = {@link kranji.pinyin.Tone#number()} as a
 *       literal digit 1–5.</li>
 * </ul>
 *
 * <h2>Worked examples</h2>
 * <table>
 *   <tr><th>Glyph</th><th>Pinyin tuple</th><th>sub-pkg</th><th>class</th></tr>
 *   <tr><td>明 míng</td><td>(M, OPEN, I, NG, 2)</td><td>m</td><td>Ing2</td></tr>
 *   <tr><td>我 wǒ</td><td>(ZERO, U, O, NONE, 3)</td><td>zero</td><td>Uo3</td></tr>
 *   <tr><td>只 zhǐ</td><td>(ZH, OPEN, NULL, NONE, 3)</td><td>zh</td><td>I3</td></tr>
 *   <tr><td>绿 lǜ</td><td>(L, V, NULL, NONE, 4)</td><td>l</td><td>V4</td></tr>
 * </table>
 *
 * <p>All methods are pure and thread-safe.</p>
 */
public final class PinyinRouting {

    private PinyinRouting() {}

    /**
     * The filesystem/package address of a pinyin triple.
     *
     * @param subPackage lowercased initial, or {@code "zero"} for
     *                   {@link Initial#ZERO}
     * @param className  {@code <Final><Tone>} — e.g. {@code "Ing2"},
     *                   {@code "Uo3"}, {@code "V4"}, {@code "I3"}
     */
    public record Key(String subPackage, String className) {
        /**
         * Convenience accessor — the PascalCase form of {@link #subPackage()},
         * used to compose the initial-aggregator class name
         * {@code Depth<N><InitialPascal>} (e.g. {@code Depth1B},
         * {@code Depth1Ch}, {@code Depth1Zero}).
         */
        public String initialPascal() {
            return pascalFromSubPackage(subPackage);
        }
    }

    /**
     * Computes the routing key for a given syllable. Pure function; two
     * syllables with equal {@code (initial, final, tone)} always map to
     * the same key.
     */
    public static Key keyFor(PinyinSyllable syl) {
        String subPkg = subPackageOf(syl.initial());
        String cls = className(syl);
        return new Key(subPkg, cls);
    }

    /**
     * Maps a sub-package back to its PascalCase aggregator suffix —
     * e.g. {@code "b" → "B"}, {@code "ch" → "Ch"}, {@code "zh" → "Zh"},
     * {@code "zero" → "Zero"}. Used to compose the initial-aggregator
     * class name {@code Depth<N><InitialPascal>}.
     */
    public static String pascalFromSubPackage(String subPackage) {
        if (subPackage == null || subPackage.isEmpty()) {
            throw new IllegalArgumentException(
                    "sub-package must be non-empty");
        }
        return Character.toUpperCase(subPackage.charAt(0))
                + subPackage.substring(1);
    }

    /**
     * The sub-package segment for an initial. {@link Initial#ZERO} spells
     * as literal {@code "zero"} because its pinyin is the empty string,
     * which is not a valid Java package segment.
     */
    public static String subPackageOf(Initial initial) {
        if (initial == Initial.ZERO) {
            return "zero";
        }
        return initial.pinyin();
    }

    /**
     * The {@code <Final><Tone>} class-name for a syllable.
     *
     * <p>Stem rules: take {@code Final.spelling()}; if it contains
     * {@code ü} replace with {@code v}; if empty (zero-nucleus syllables
     * like {@code zhi}) fall back to {@code "i"}; then PascalCase the
     * first character.</p>
     */
    public static String className(PinyinSyllable syl) {
        String raw = syl.fin().spelling().replace("ü", "v");
        if (raw.isEmpty()) {
            raw = "i";
        }
        String stem = Character.toUpperCase(raw.charAt(0)) + raw.substring(1);
        return stem + syl.tone().number();
    }
}
