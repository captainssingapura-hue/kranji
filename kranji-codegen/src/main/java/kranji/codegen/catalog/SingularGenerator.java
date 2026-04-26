package kranji.codegen.catalog;

import java.util.TreeSet;

/**
 * Emits an atomic {@code SingularZi} record for catalog rows tagged with
 * layout code {@code "S"}.
 *
 * <p>Used to mechanically populate missing-but-needed singular components
 * (the glyphs that appear in the histogram of blockers when running the
 * composed iterator). Generated singulars:</p>
 *
 * <ul>
 *   <li>Live in the same staging/promoted modules as composed records,
 *       under the same {@code <initial>.<finaltone>} package layout.</li>
 *   <li>Implement only {@link kranji.zi.SingularZi} (no
 *       {@code LibraryMember<BasicSet>}). They're treated as
 *       "provisional placeholder" pieces — see
 *       {@link kranji.zi.SingularZi#unsure()}, which generated records
 *       override to {@code true}.</li>
 *   <li>Override {@code glyph()} and {@code pinyin()}; everything else
 *       (strokes, radical, meaning, etymology) uses the
 *       {@code SingularZi} defaults (0, 0, "", Pictograph). Promotion-time
 *       review can fill in proper values.</li>
 * </ul>
 *
 * <p>Catalog row shape: Layout={@code "S"}, Components={@code "Ø"}.
 * The row's {@code Suffix} becomes the second half of the Java identifier
 * (e.g. {@code 𦍌 yáng "SheepVariant"} → class {@code YangSheepVariant}).</p>
 */
public final class SingularGenerator implements StructureGenerator {

    @Override public String layoutCode() { return "S"; }
    @Override public int arity() { return 0; }

    @Override
    public String emit(EmitContext ctx) {
        var p = ctx.pinyinParts();

        TreeSet<String> imports = new TreeSet<>();
        imports.add("kranji.pinyin.Body");
        imports.add("kranji.pinyin.Final");
        imports.add("kranji.pinyin.Head");
        imports.add("kranji.pinyin.Initial");
        imports.add("kranji.pinyin.PinyinSyllable");
        imports.add("kranji.pinyin.Tail");
        imports.add("kranji.pinyin.Tone");
        imports.add("kranji.zi.SingularZi");

        StringBuilder sb = new StringBuilder();
        sb.append(AUTO_BANNER).append('\n');
        sb.append("package ").append(ctx.targetPackageFqn()).append(";\n\n");
        for (String imp : imports) sb.append("import ").append(imp).append(";\n");
        sb.append('\n');

        String klass = ctx.targetClassName();
        sb.append("public record ").append(klass).append("() implements SingularZi {\n\n");
        sb.append("    public static final ").append(klass)
                .append(" INSTANCE = new ").append(klass).append("();\n\n");

        sb.append("    @Override public String glyph() { return \"")
                .append(ctx.glyphHexEscape()).append("\"; }\n\n");

        sb.append("    @Override public PinyinSyllable pinyin() {\n");
        sb.append("        return new PinyinSyllable(Initial.").append(p.initial())
                .append(", new Final(Head.").append(p.head())
                .append(", Body.").append(p.body())
                .append(", Tail.").append(p.tail())
                .append("), Tone.").append(p.tone()).append(");\n");
        sb.append("    }\n\n");

        // Generated singulars are placeholders until promotion-time review.
        sb.append("    @Override public boolean unsure() { return true; }\n");
        sb.append("}\n");

        return sb.toString();
    }
}
