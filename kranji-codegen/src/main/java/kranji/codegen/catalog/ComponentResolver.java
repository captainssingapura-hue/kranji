package kranji.codegen.catalog;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Given a {@link CatalogRow}, decide whether <em>all</em> its components
 * are currently available in the {@link GlyphIndex} — and if so, return
 * the resolved {@link JavaRef}s in slot order so the generator can emit.
 *
 * <p>Component string handling:</p>
 * <ul>
 *   <li>Single-glyph token (e.g. {@code "木"}) → look up directly.</li>
 *   <li>Parenthesised group (e.g. {@code "(革+月)"}) → <strong>inline LR
 *       composition</strong>. The resolver constructs a synthetic
 *       {@link JavaRef.InlineRef} carrying the resolved children.</li>
 *   <li>Explicit-layout group (e.g. {@code "TB(亠+凶)"}, {@code "TB3(山+一+几)"}) →
 *       inline composition with that layout. Arity must match
 *       (LR/TB = 2, LR3/TB3 = 3).</li>
 *   <li><strong>Nested inline</strong> (e.g. {@code "TB((亻+隹)+鸟)"}) →
 *       supported recursively. Each parens group at any depth becomes its
 *       own {@link JavaRef.InlineRef} synthetic record. The iterator emits
 *       all nested synthetic records depth-first before the outer.</li>
 * </ul>
 *
 * <p>Multi-character literal tokens that aren't parenthesised (rare —
 * typically a typo in the catalog) are flagged as unresolved.</p>
 */
public final class ComponentResolver {

    private final GlyphIndex index;

    public ComponentResolver(GlyphIndex index) {
        this.index = index;
    }

    /**
     * Resolution outcome for a row.
     *
     * @param resolved   refs in slot order, populated only when {@link #ok()} is true
     * @param missing    glyphs that couldn't be found (in encounter order, deduped)
     * @param unsupported true if the row contains a token shape this resolver
     *                    can't even attempt
     */
    public record Result(List<JavaRef> resolved,
                         List<String> missing,
                         boolean unsupported) {
        public boolean ok() { return missing.isEmpty() && !unsupported; }
    }

    /** Resolve a row's components, generating inline synthetic refs for parens groups. */
    public Result resolve(CatalogRow row) {
        return resolve(row, row.pkg(), row.className());
    }

    /**
     * @param targetPkg       package the synthetic inner records will be emitted into
     * @param outerClassName  simple class name of the outer; used to derive
     *                        deterministic synthetic inner names like
     *                        {@code BaTyrant_Inner1}, {@code YingEagle_Inner2}
     */
    public Result resolve(CatalogRow row, String targetPkg, String outerClassName) {
        Ctx ctx = new Ctx(targetPkg, outerClassName);
        List<JavaRef> resolved = new ArrayList<>();
        for (String token : row.components()) {
            Optional<JavaRef> ref = resolveToken(token, ctx);
            ref.ifPresent(resolved::add);
        }
        return new Result(resolved, List.copyOf(ctx.missing), ctx.unsupported);
    }

    /**
     * Resolve a single token, possibly recursively if it's a parens-grouped
     * inline composition. Returns {@code Optional.empty()} on failure (and
     * accumulates the failure into {@code ctx.missing} or sets
     * {@code ctx.unsupported}).
     */
    private Optional<JavaRef> resolveToken(String token, Ctx ctx) {
        // Inline composition: either default-LR parens "(X+Y)" or explicit
        // "<Layout>(X+Y)" e.g. "TB(亠+凶)", "TB3(山+一+几)".
        String inlineLayout = null;
        String inlineInner = null;
        if (token.startsWith("(") && token.endsWith(")")) {
            inlineLayout = "LR";
            inlineInner = token.substring(1, token.length() - 1);
        } else {
            int parenStart = token.indexOf('(');
            if (parenStart > 0 && token.endsWith(")")) {
                String prefix = token.substring(0, parenStart);
                if (prefix.matches("[A-Za-z][A-Za-z0-9]*")) {
                    inlineLayout = prefix;
                    inlineInner = token.substring(parenStart + 1, token.length() - 1);
                }
            }
        }

        if (inlineLayout != null) {
            List<String> innerTokens = CatalogParser.parseComponents(inlineInner);
            int expectedArity = switch (inlineLayout) {
                case "LR", "TB" -> 2;
                case "LR3", "TB3" -> 3;
                default -> -1;
            };
            if (expectedArity < 0 || innerTokens.size() != expectedArity) {
                ctx.unsupported = true;
                ctx.missing.add(token);
                return Optional.empty();
            }
            ctx.inlineIdx++;
            String innerClassName = ctx.outerClassName + "_Inner" + ctx.inlineIdx;
            List<JavaRef> innerChildren = new ArrayList<>(innerTokens.size());
            boolean innerOk = true;
            for (String t : innerTokens) {
                // RECURSE — inner tokens can themselves be inline groups.
                Optional<JavaRef> child = resolveToken(t, ctx);
                if (child.isEmpty()) innerOk = false;
                else innerChildren.add(child.get());
            }
            if (!innerOk) return Optional.empty();
            String innerFqn = "kranji.common.perclass.staging." + ctx.targetPkg
                    + "." + innerClassName;
            return Optional.of(new JavaRef.InlineRef(
                    innerFqn, innerClassName, inlineLayout, innerChildren));
        }

        // Try direct index lookup first — supports both single-glyph tokens
        // and registered multi-codepoint compound keys (e.g. "寒-冫" subtraction
        // SingularParts).
        Optional<JavaRef> ref = index.find(token);
        if (ref.isPresent()) {
            return ref;
        }
        // Not in index. If it's not even a single codepoint, the token shape
        // is something the resolver can't attempt (multi-codepoint literal).
        if (token.codePointCount(0, token.length()) != 1) {
            ctx.unsupported = true;
            ctx.missing.add(token);
            return Optional.empty();
        }
        // Single-codepoint token that's just missing from the index.
        ctx.missing.add(token);
        return Optional.empty();
    }

    /** Mutable resolution context — accumulates missing glyphs and the inline-id counter. */
    private static final class Ctx {
        final String targetPkg;
        final String outerClassName;
        final Set<String> missing = new LinkedHashSet<>();
        boolean unsupported = false;
        int inlineIdx = 0;
        Ctx(String targetPkg, String outerClassName) {
            this.targetPkg = targetPkg;
            this.outerClassName = outerClassName;
        }
    }
}
