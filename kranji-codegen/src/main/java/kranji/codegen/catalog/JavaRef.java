package kranji.codegen.catalog;

/**
 * Java reference to one available glyph component, of either flavor:
 * a singular ({@code BasicComponents.MU} → type {@code WoodFamily.Mu})
 * or a composed ComposedZi record ({@code ZhanOccupy.INSTANCE} → type
 * {@code ZhanOccupy}).
 *
 * <p>Always-qualified type expressions: emitted source uses the form
 * {@code WoodFamily.Mu}, never the bare {@code Mu}. This avoids the
 * single-type-import collision that occurs when two slots reference
 * different glyphs whose nested-type simple names clash (e.g.
 * {@code WoodFamily.Mu} 木 and {@code BodyParts.Mu} 目).</p>
 *
 * <p>Each ref provides:</p>
 * <ul>
 *   <li>{@link #typeOuterImport()} — FQN to add to the import block so
 *       the qualified type expression resolves.</li>
 *   <li>{@link #valueImport()} — FQN of the value container (often
 *       different from the type's outer for re-export hubs like
 *       {@code BasicComponents}).</li>
 *   <li>{@link #qualifiedTypeExpression()} — type expression to use in
 *       generic-parameter and accessor-return positions
 *       (e.g. {@code "WoodFamily.Mu"}, {@code "BaCease"}).</li>
 *   <li>{@link #valueExpression()} — the slot-accessor return body
 *       (e.g. {@code "BasicComponents.MU"}, {@code "BaCease.INSTANCE"}).</li>
 * </ul>
 */
public sealed interface JavaRef {

    /** FQN of the outer container that must be imported for the qualified type expression. */
    String typeOuterImport();

    /** FQN of the class that exposes the value field. */
    String valueImport();

    /**
     * Always-qualified type expression for slot positions
     * ({@code "WoodFamily.Mu"} for nested singular records,
     * {@code "BaCease"} for top-level composed records).
     */
    String qualifiedTypeExpression();

    /** Right-hand side of the slot-accessor return statement (e.g. {@code "BasicComponents.MU"}). */
    String valueExpression();

    /** The glyph this ref resolves. */
    String glyph();

    /**
     * Singular flavor: a value already declared in {@code kranji-singulars}.
     *
     * @param glyph                glyph this ref resolves
     * @param typeOuterImport      FQN of the class declaring the nested type
     *                             (e.g. {@code "kranji.component.basic.WoodFamily"})
     * @param typeOuterSimple      simple name of the same
     *                             (e.g. {@code "WoodFamily"})
     * @param typeNestedSimple     simple name of the nested type
     *                             (e.g. {@code "Mu"})
     * @param valueImport          FQN of the class exposing the value constant
     *                             (e.g. {@code "kranji.component.basic.BasicComponents"})
     * @param valueExpression      full value expression
     *                             (e.g. {@code "BasicComponents.MU"})
     */
    record SingularRef(String glyph,
                       String typeOuterImport,
                       String typeOuterSimple,
                       String typeNestedSimple,
                       String valueImport,
                       String valueExpression) implements JavaRef {

        @Override public String qualifiedTypeExpression() {
            return typeOuterSimple + "." + typeNestedSimple;
        }
    }

    /** Composed flavor: a {@code ComposedZi} record already in perclass (hand-authored, promoted, or staged). */
    record ComposedRef(String glyph,
                       String classFqn,
                       String simpleClassName) implements JavaRef {

        @Override public String typeOuterImport() { return classFqn; }
        @Override public String valueImport() { return classFqn; }
        @Override public String qualifiedTypeExpression() { return simpleClassName; }
        @Override public String valueExpression() { return simpleClassName + ".INSTANCE"; }
    }

    /**
     * Inline-composed flavor: an anonymous {@code ComposedZi} record
     * generated on the fly from a parens group like {@code "(革+月)"} in
     * a parent row's components. The iterator emits both the synthetic
     * inner file and the outer in the same round.
     *
     * @param classFqn         FQN where the synthetic record will be
     *                         emitted (e.g.
     *                         {@code "kranji.common.perclass.staging.b.a4.BaTyrant_Inner1"})
     * @param simpleClassName  simple name (e.g. {@code "BaTyrant_Inner1"})
     * @param layoutCode       layout for the inner ({@code "LR"} for the
     *                         default parens form; future: explicit
     *                         {@code "TB(...)"} prefix would set this)
     * @param children         resolved refs of the inner's components,
     *                         in slot order
     */
    record InlineRef(String classFqn,
                     String simpleClassName,
                     String layoutCode,
                     java.util.List<JavaRef> children) implements JavaRef {

        @Override public String glyph() { return ""; }   // anonymous
        @Override public String typeOuterImport() { return classFqn; }
        @Override public String valueImport() { return classFqn; }
        @Override public String qualifiedTypeExpression() { return simpleClassName; }
        @Override public String valueExpression() { return simpleClassName + ".INSTANCE"; }
    }
}
