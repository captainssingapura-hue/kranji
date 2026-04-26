package kranji.codegen.catalog;

import java.util.List;

/**
 * One parsed row from {@code docs/catalog/batch-NN-*.md}.
 *
 * <p>The catalog is the human-readable source of truth for unprocessed
 * (missing) Zi. Each row tells the iterator everything it needs to emit a
 * staged ComposedZi record:</p>
 *
 * <ul>
 *   <li>{@link #pkg()} — target package (e.g. {@code "b.a1"} →
 *       {@code kranji.common.perclass.staging.b.a1})</li>
 *   <li>{@link #layout()} — composition-structure code; routes to a
 *       {@link StructureGenerator}</li>
 *   <li>{@link #components()} — child glyphs in slot order</li>
 *   <li>{@link #etym()} — etymology classification + optional argument hint</li>
 * </ul>
 *
 * @param glyph       the Chinese character itself (e.g. {@code "核"})
 * @param pinyinRaw   pinyin with diacritic tone marks (e.g. {@code "hé"});
 *                    informational — pkg already encodes initial+final+tone
 * @param pkg         dot-separated package suffix under
 *                    {@code kranji.common.perclass.staging.}
 *                    (e.g. {@code "b.a1"}, {@code "zh.ong3"}, {@code "zero.iang2"})
 * @param layout      structure code (e.g. {@code "LR"}, {@code "TB"},
 *                    {@code "SemiUL"}, {@code "FullEnc"}, {@code "P"})
 * @param components  child glyphs in slot order, top-level only —
 *                    grouping parens like {@code "(王+白)"} flattened to one entry
 *                    representing a composed Zi (must be looked up as such)
 * @param etym        etymology code, e.g. {@code "PS(木,亥)"}, {@code "CI"},
 *                    {@code "DC"}, {@code "P"}; semantic-first PhonoSemantic args
 *                    are parsed out by the iterator
 * @param suffix      English meaning hint that becomes the second half of the
 *                    Java identifier (e.g. {@code "Core"} → {@code HeCore})
 * @param sourceFile  catalog file this row came from, for diagnostics
 * @param sourceLine  1-based line number in the source file, for diagnostics
 */
public record CatalogRow(
        String glyph,
        String pinyinRaw,
        String pkg,
        String layout,
        List<String> components,
        String etym,
        String suffix,
        String sourceFile,
        int sourceLine) {

    /** Initial portion of {@link #pkg()} (e.g. {@code "b"}, {@code "zh"}, {@code "zero"}). */
    public String initial() {
        int dot = pkg.indexOf('.');
        return dot < 0 ? pkg : pkg.substring(0, dot);
    }

    /** Final+tone portion of {@link #pkg()} (e.g. {@code "a1"}, {@code "ong3"}). */
    public String finalTone() {
        int dot = pkg.indexOf('.');
        return dot < 0 ? "" : pkg.substring(dot + 1);
    }

    /**
     * The Java class name to emit, derived from initial-pinyin + suffix.
     * Examples:
     * <ul>
     *   <li>(b, "Scar") → {@code "BaScar"}</li>
     *   <li>(zh, "Glue") → {@code "ZhanGlue"} — uses the full pinyin syllable</li>
     * </ul>
     *
     * <p>The first half is computed from {@link #pinyinSyllableForName()}; the
     * second half is {@link #suffix()} verbatim.</p>
     */
    public String className() {
        return pinyinSyllableForName() + suffix;
    }

    /**
     * Pinyin syllable with first letter capitalised, no tone marks
     * (e.g. {@code "hé"} → {@code "He"}, {@code "zhāng"} → {@code "Zhang"}).
     */
    public String pinyinSyllableForName() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pinyinRaw.length(); i++) {
            char c = pinyinRaw.charAt(i);
            char ascii = stripTone(c);
            if (sb.isEmpty()) sb.append(Character.toUpperCase(ascii));
            else sb.append(Character.toLowerCase(ascii));
        }
        return sb.toString();
    }

    private static char stripTone(char c) {
        return switch (c) {
            case 'ā', 'á', 'ǎ', 'à' -> 'a';
            case 'ē', 'é', 'ě', 'è' -> 'e';
            case 'ī', 'í', 'ǐ', 'ì' -> 'i';
            case 'ō', 'ó', 'ǒ', 'ò' -> 'o';
            case 'ū', 'ú', 'ǔ', 'ù' -> 'u';
            case 'ǖ', 'ǘ', 'ǚ', 'ǜ', 'ü' -> 'v';
            default -> c;
        };
    }
}
