package kranji.codegen.catalog;

import java.util.List;
import java.util.TreeSet;

/**
 * Common boilerplate emitter for all {@link StructureGenerator}s.
 *
 * <p>Concrete subclasses specify only the per-layout differences:</p>
 * <ul>
 *   <li>{@link #layoutCode()} and {@link #arity()} (from the interface).</li>
 *   <li>{@link #layoutInterface()} — simple name of the typed-layout
 *       interface from {@code kranji.zi}, e.g. {@code "LeftRightT"},
 *       {@code "SemiEnclosureUpperLeftT"}.</li>
 *   <li>{@link #slotAccessorNames()} — the slot accessor method names in
 *       slot order, e.g. {@code ["left","right"]}, {@code ["wrapper","content"]},
 *       {@code ["top","middle","bottom"]}, {@code ["outer","inner"]}.</li>
 * </ul>
 *
 * <p>The base handles: AUTO-GENERATED banner, package, alphabetised imports,
 * record header with parameterised typed-layout interface, INSTANCE singleton,
 * slot accessor implementations, layout()/glyph()/pinyin()/strokes()/
 * radicalNo()/meaning()/etymology() — all deterministic and byte-identical
 * for identical inputs.</p>
 */
public abstract class BaseStructureGenerator implements StructureGenerator {

    /** Simple name of the typed-layout interface (e.g. {@code "LeftRightT"}). */
    protected abstract String layoutInterface();

    /** Slot accessor names in slot order; size must equal {@link #arity()}. */
    protected abstract String[] slotAccessorNames();

    /** Default: assume the interface lives in {@code kranji.zi.<simple>}. */
    protected String layoutInterfaceImport() {
        return "kranji.zi." + layoutInterface();
    }

    @Override
    public final String emit(EmitContext ctx) {
        List<JavaRef> components = ctx.components();
        String[] slots = slotAccessorNames();
        if (components.size() != slots.length) {
            throw new IllegalStateException("arity mismatch in "
                    + getClass().getSimpleName() + ": expected " + slots.length
                    + " components, got " + components.size());
        }

        var p = ctx.pinyinParts();

        // Per-slot type and value expressions, with self-name collision avoidance.
        // If a slot's simple type name matches the declaring class name, we cannot
        // import it (would shadow the declaration) — use the full FQN inline instead.
        // Same for the value import.
        String selfName = ctx.targetClassName();
        java.util.List<String> slotTypeExprs = new java.util.ArrayList<>(components.size());
        java.util.List<String> slotValueExprs = new java.util.ArrayList<>(components.size());

        TreeSet<String> imports = new TreeSet<>();
        imports.add("kranji.classification.EtymologicalCategory");
        imports.add("kranji.pinyin.Body");
        imports.add("kranji.pinyin.Final");
        imports.add("kranji.pinyin.Head");
        imports.add("kranji.pinyin.Initial");
        imports.add("kranji.pinyin.PinyinSyllable");
        imports.add("kranji.pinyin.Tail");
        imports.add("kranji.pinyin.Tone");
        for (JavaRef ref : components) {
            // Type side: only import the outer if its simple name doesn't collide.
            String typeOuterImport = ref.typeOuterImport();
            String typeOuterSimple = simpleNameOf(typeOuterImport);
            String typeExpr = ref.qualifiedTypeExpression();
            if (typeOuterSimple.equals(selfName)) {
                // Use FQN inline; do not import.
                typeExpr = typeOuterImport;
                if (ref instanceof JavaRef.SingularRef sr) {
                    typeExpr = typeOuterImport + "." + sr.typeNestedSimple();
                }
            } else {
                imports.add(typeOuterImport);
            }
            slotTypeExprs.add(typeExpr);

            // Value side: same check on valueImport.
            String valueImport = ref.valueImport();
            String valueSimple = simpleNameOf(valueImport);
            String valueExpr = ref.valueExpression();
            if (valueSimple.equals(selfName)) {
                // Use FQN value; do not import. The value expression already contains
                // the simple name + ".CONST" or ".INSTANCE"; replace the simple name
                // with the full FQN.
                valueExpr = valueImport
                        + valueExpr.substring(valueExpr.indexOf('.'));
            } else {
                imports.add(valueImport);
            }
            slotValueExprs.add(valueExpr);
        }
        imports.add("kranji.zi.ComposedZiT");
        imports.add("kranji.zi.CompositionLayoutT");
        imports.add(layoutInterfaceImport());

        StringBuilder sb = new StringBuilder();
        sb.append(AUTO_BANNER).append('\n');
        sb.append("package ").append(ctx.targetPackageFqn()).append(";\n\n");
        for (String imp : imports) sb.append("import ").append(imp).append(";\n");
        sb.append('\n');

        String klass = ctx.targetClassName();
        sb.append("public record ").append(klass)
                .append("() implements ComposedZiT, ").append(layoutInterface()).append("<");
        for (int i = 0; i < components.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(slotTypeExprs.get(i));
        }
        sb.append("> {\n\n");

        sb.append("    public static final ").append(klass)
                .append(" INSTANCE = new ").append(klass).append("();\n\n");

        for (int i = 0; i < slots.length; i++) {
            sb.append("    @Override public ").append(slotTypeExprs.get(i))
                    .append(' ').append(slots[i]).append("() { return ")
                    .append(slotValueExprs.get(i)).append("; }\n");
        }
        sb.append('\n');

        sb.append("    @Override public CompositionLayoutT layout() { return this; }\n\n");

        sb.append("    @Override public String glyph() { return \"")
                .append(ctx.glyphHexEscape()).append("\"; }\n\n");

        sb.append("    @Override public PinyinSyllable pinyin() {\n");
        sb.append("        return new PinyinSyllable(Initial.").append(p.initial())
                .append(", new Final(Head.").append(p.head())
                .append(", Body.").append(p.body())
                .append(", Tail.").append(p.tail())
                .append("), Tone.").append(p.tone()).append(");\n");
        sb.append("    }\n\n");

        sb.append("    @Override public int strokes() { return 0; }\n");
        sb.append("    @Override public int radicalNo() { return 0; }\n");
        sb.append("    @Override public String meaning() { return \"\"; }\n\n");

        sb.append("    @Override public EtymologicalCategory etymology() {\n");
        sb.append("        return ").append(EtymologyEmitter.emit(
                ctx.row().etym(), ctx.row(), ctx.components(), selfName)).append(";\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }

    /** Last dot-separated segment of an FQN. */
    private static String simpleNameOf(String fqn) {
        int dot = fqn.lastIndexOf('.');
        return dot < 0 ? fqn : fqn.substring(dot + 1);
    }
}
