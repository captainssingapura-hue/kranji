package kranji.codegen.catalog;

import java.util.List;

/**
 * Per-row inputs handed to a {@link StructureGenerator}: the catalog row,
 * its resolved component refs (in slot order), and the parsed pinyin parts.
 *
 * <p>Construction is the iterator's job; generators only read it.</p>
 *
 * @param row              the source catalog row
 * @param components       resolved JavaRefs in slot order, count ==
 *                         {@code generator.arity()}
 * @param pinyinParts      pre-parsed Initial/Head/Body/Tail/Tone enum names
 *                         for direct splicing into the emitted source
 * @param targetPackageFqn fully-qualified target package
 *                         (e.g. {@code "kranji.common.perclass.staging.b.a1"})
 * @param targetClassName  target Java class simple name (e.g. {@code "BaScar"})
 * @param glyphHexEscape   the {@code \\uXXXX} form of {@link CatalogRow#glyph()}
 *                         for splicing into a Java string literal
 */
public record EmitContext(
        CatalogRow row,
        List<JavaRef> components,
        PinyinSyllableParser.Parts pinyinParts,
        String targetPackageFqn,
        String targetClassName,
        String glyphHexEscape) {

    /**
     * Convenience: glyph as {@code \\uXXXX} or {@code \\uHHHH\\uLLLL}
     * surrogate pair if outside the BMP.
     */
    public static String unicodeEscape(String glyph) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < glyph.length(); i++) {
            sb.append(String.format("\\u%04X", (int) glyph.charAt(i)));
        }
        return sb.toString();
    }
}
