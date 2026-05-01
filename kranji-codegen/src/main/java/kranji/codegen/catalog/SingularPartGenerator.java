package kranji.codegen.catalog;

import java.util.TreeSet;

/**
 * Emits an atomic {@code SingularPart} record for catalog rows tagged
 * with layout code {@code "SP"}.
 *
 * <p>{@code SingularPart} (偏旁) is the kranji-zi interface for radical
 * variants and component parts that <em>are not standalone Zi</em> —
 * e.g. 氵 (water radical, derived from 水) or 㔾 (kneeling-form
 * component, used in 氾 / 卩-family glyphs but never written alone).</p>
 *
 * <p>Use {@code SP} when the catalog needs a singular for a glyph that
 * exists structurally but is not a freestanding character. Use {@code S}
 * (which routes to {@link SingularGenerator} → {@code SingularZi}) when
 * the glyph is a standalone Zi.</p>
 *
 * <p>{@code Etym=P!} marks the part as definite (sure); plain {@code P}
 * marks it as a placeholder pending review (mirrors {@code S} convention).
 * The default {@code derivedFrom()} returns {@code null}; promotion-time
 * review can edit the generated file to point at the standalone source.</p>
 */
public final class SingularPartGenerator implements StructureGenerator {

    @Override public String layoutCode() { return "SP"; }
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
        imports.add("kranji.zi.SingularPart");

        StringBuilder sb = new StringBuilder();
        sb.append(AUTO_BANNER).append('\n');
        sb.append("package ").append(ctx.targetPackageFqn()).append(";\n\n");
        for (String imp : imports) sb.append("import ").append(imp).append(";\n");
        sb.append('\n');

        String klass = ctx.targetClassName();
        sb.append("public record ").append(klass).append("() implements SingularPart {\n\n");
        sb.append("    public static final ").append(klass)
                .append(" INSTANCE = new ").append(klass).append("();\n\n");

        sb.append("    @Override public String glyph() { return \"")
                .append(ctx.glyphHexEscape()).append("\"; }\n\n");

        sb.append("    public PinyinSyllable pinyin() {\n");
        sb.append("        return new PinyinSyllable(Initial.").append(p.initial())
                .append(", new Final(Head.").append(p.head())
                .append(", Body.").append(p.body())
                .append(", Tail.").append(p.tail())
                .append("), Tone.").append(p.tone()).append(");\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }
}
