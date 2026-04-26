package kranji.codegen.catalog;

import java.util.List;

/**
 * Builds the {@code etymology()} method body for a generated record from
 * the catalog's terse Etym code.
 *
 * <p>Supported codes:</p>
 * <ul>
 *   <li>{@code PS(sem,phon)} — PhonoSemantic; sem and phon are component
 *       glyphs that should appear in the row's Components list. Emits
 *       {@code new EtymologicalCategory.PhonoSemantic(<semRef>, <phonRef>)}.</li>
 *   <li>{@code CI} — CompoundIndicative; emits with empty description string.</li>
 *   <li>{@code DC} — DerivativeCognate; emits with empty description string.</li>
 *   <li>{@code SI} — SimpleIndicative; emits with empty description string.</li>
 *   <li>{@code PL} — PhoneticLoan; emits with empty description string.</li>
 *   <li>{@code P}  — Pictograph; emits {@code new Pictograph()}.
 *       (Should not appear in catalog rows that survive routing — those
 *       are flagged as singulars upstream — but supported for completeness.)</li>
 * </ul>
 *
 * <p>The semantic and phonetic glyphs in {@code PS(sem,phon)} are matched
 * against the row's resolved components to recover their {@link JavaRef}.
 * If either glyph isn't in the resolved components, the emitter falls back
 * to {@code CompoundIndicative} with an empty description — safer than
 * compiling a syntactically broken file.</p>
 */
public final class EtymologyEmitter {

    private EtymologyEmitter() {}

    /**
     * Emit the body of {@code public EtymologicalCategory etymology() }.
     *
     * @param etym       raw etym code from the catalog
     * @param row        catalog row (for diagnostics)
     * @param components resolved JavaRefs in slot order, used to look up
     *                   semantic/phonetic refs by glyph
     * @return single-statement Java source ending without trailing semicolon
     *         or newline (the caller wraps with {@code return ...;})
     */
    public static String emit(String etym, CatalogRow row, List<JavaRef> components) {
        return emit(etym, row, components, null);
    }

    /**
     * @param selfName declaring record's simple class name; used to rewrite any
     *                 {@code SimpleName.INSTANCE} value expression that would
     *                 collide with the declaring class to its full FQN form.
     *                 Pass {@code null} to disable rewriting.
     */
    public static String emit(String etym, CatalogRow row, List<JavaRef> components, String selfName) {
        String code = baseCode(etym);
        return switch (code) {
            case "PS" -> emitPhonoSemantic(etym, row, components, selfName);
            case "CI" -> "new EtymologicalCategory.CompoundIndicative(\"\")";
            case "DC" -> "new EtymologicalCategory.DerivativeCognate(\"\")";
            case "SI" -> "new EtymologicalCategory.SimpleIndicative(\"\")";
            case "PL" -> "new EtymologicalCategory.PhoneticLoan(\"\")";
            case "P"  -> "new EtymologicalCategory.Pictograph()";
            default   -> "new EtymologicalCategory.CompoundIndicative(\"\")";
        };
    }

    private static String baseCode(String etym) {
        int paren = etym.indexOf('(');
        return paren < 0 ? etym : etym.substring(0, paren);
    }

    private static String emitPhonoSemantic(String etym, CatalogRow row,
                                             List<JavaRef> components, String selfName) {
        // Parse "PS(sem,phon)"
        int open = etym.indexOf('('), close = etym.lastIndexOf(')');
        if (open < 0 || close < 0 || close <= open) {
            return "new EtymologicalCategory.CompoundIndicative(\"\")";
        }
        String[] args = etym.substring(open + 1, close).split(",", 2);
        if (args.length != 2) {
            return "new EtymologicalCategory.CompoundIndicative(\"\")";
        }
        String sem = args[0].trim();
        String phon = args[1].trim();
        JavaRef semRef  = findByGlyph(components, sem);
        JavaRef phonRef = findByGlyph(components, phon);
        if (semRef == null || phonRef == null) {
            return "new EtymologicalCategory.CompoundIndicative(\"\")";
        }
        return "new EtymologicalCategory.PhonoSemantic("
                + valueExprWithSelfRewrite(semRef, selfName) + ", "
                + valueExprWithSelfRewrite(phonRef, selfName) + ")";
    }

    /**
     * For ComposedRef whose simpleClassName collides with the declaring class,
     * use the full FQN; otherwise the bare valueExpression.
     */
    private static String valueExprWithSelfRewrite(JavaRef ref, String selfName) {
        String expr = ref.valueExpression();
        if (selfName == null) return expr;
        if (ref instanceof JavaRef.ComposedRef cr && cr.simpleClassName().equals(selfName)) {
            return cr.classFqn() + ".INSTANCE";
        }
        return expr;
    }

    private static JavaRef findByGlyph(List<JavaRef> components, String glyph) {
        for (JavaRef r : components) {
            if (glyph.equals(r.glyph())) return r;
        }
        return null;
    }
}
