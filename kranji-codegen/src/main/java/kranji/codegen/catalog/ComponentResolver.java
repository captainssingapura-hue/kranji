package kranji.codegen.catalog;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
 *       {@link JavaRef.InlineRef} carrying the resolved children. The
 *       iterator emits a synthetic inner record (using the parent row's
 *       package) before emitting the outer. Default layout for inline is
 *       {@code LR} (the most common case in Chinese composition); other
 *       layouts will need an explicit prefix like {@code "TB(…)"} once
 *       supported.</li>
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
     *                    can't even attempt (e.g. nested literal {@code "(王+白)"}
     *                    where the inner composition has no catalog row yet)
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
     *                        (typically same as outer row's pkg)
     * @param outerClassName  simple class name of the outer; used to derive
     *                        deterministic synthetic inner names like
     *                        {@code BaTyrant_Inner1}
     */
    public Result resolve(CatalogRow row, String targetPkg, String outerClassName) {
        List<JavaRef> resolved = new ArrayList<>();
        Set<String> missing = new LinkedHashSet<>();
        boolean unsupported = false;
        int inlineIdx = 0;

        for (String token : row.components()) {
            if (token.startsWith("(") && token.endsWith(")")) {
                // Inline composition: parens group with default LR layout.
                String inner = token.substring(1, token.length() - 1);
                List<String> innerTokens = CatalogParser.parseComponents(inner);
                if (innerTokens.size() != 2) {
                    // Only 2-part LR is supported inline at present.
                    unsupported = true;
                    missing.add(token);
                    continue;
                }
                // Recursively resolve the inner's children. Inner can also be inline-nested.
                inlineIdx++;
                String innerClassName = outerClassName + "_Inner" + inlineIdx;
                List<JavaRef> innerChildren = new ArrayList<>(2);
                boolean innerOk = true;
                for (String t : innerTokens) {
                    if (t.codePointCount(0, t.length()) != 1) {
                        unsupported = true;
                        missing.add(token);
                        innerOk = false;
                        break;
                    }
                    var r = index.find(t);
                    if (r.isEmpty()) {
                        missing.add(t);
                        innerOk = false;
                    } else {
                        innerChildren.add(r.get());
                    }
                }
                if (!innerOk) continue;
                String innerFqn = "kranji.common.perclass.staging." + targetPkg + "." + innerClassName;
                resolved.add(new JavaRef.InlineRef(innerFqn, innerClassName, "LR", innerChildren));
                continue;
            }

            String key = token;
            if (key.codePointCount(0, key.length()) != 1) {
                unsupported = true;
                missing.add(token);
                continue;
            }
            var ref = index.find(key);
            if (ref.isEmpty()) {
                missing.add(key);
                continue;
            }
            resolved.add(ref.get());
        }

        return new Result(resolved, List.copyOf(missing), unsupported);
    }
}
