package kranji.codegen.catalog;

import kranji.codegen.perclass.PerclassSingularIndex;
import kranji.zi.ComposedZiT;
import kranji.zi.SingularZi;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Combined glyph → {@link JavaRef} resolver covering all available
 * components for the catalog iterator:
 *
 * <ol>
 *   <li>Every singular registered under {@code kranji-singulars}
 *       (via {@link PerclassSingularIndex}).</li>
 *   <li>Every hand-authored {@code ComposedZi} record in
 *       {@code kranji-common-perclass}.</li>
 *   <li>Every staged record currently in
 *       {@code kranji-common-perclass-staging} (built up across
 *       iteration rounds).</li>
 * </ol>
 *
 * <p>Built once per round (snapshots whatever's compiled and on the
 * classpath at that moment). The iterator rebuilds the index between
 * rounds so newly-emitted records become available components.</p>
 *
 * <p>Singulars take precedence on glyph collisions (matches the
 * established generator priority).</p>
 */
public final class GlyphIndex {

    private final Map<String, JavaRef> byGlyph;

    private GlyphIndex(Map<String, JavaRef> byGlyph) {
        this.byGlyph = byGlyph;
    }

    public Optional<JavaRef> find(String glyph) {
        return Optional.ofNullable(byGlyph.get(glyph));
    }

    public boolean contains(String glyph) {
        return byGlyph.containsKey(glyph);
    }

    public int size() { return byGlyph.size(); }

    /**
     * Build an index from:
     * <ol>
     *   <li>The reflective singular index.</li>
     *   <li>An iterable of already-added composed-Zi records (typically
     *       {@code AllPerclassRecords.ALL} ∪ {@code AllPerclassRecordsStaging.ALL}).</li>
     * </ol>
     */
    public static GlyphIndex build(PerclassSingularIndex singulars,
                                   Iterable<? extends ComposedZiT> composedRecords) {
        return build(singulars, composedRecords, Collections.emptyList());
    }

    /**
     * Build an index from singulars + composed records + auto-generated
     * top-level singulars (those emitted into staging/promoted by the
     * SingularGenerator). Auto-singulars are top-level classes with an
     * INSTANCE constant; they're indexed using {@link JavaRef.ComposedRef}
     * (same shape as a composed record from the indexing perspective).
     */
    public static GlyphIndex build(PerclassSingularIndex singulars,
                                   Iterable<? extends ComposedZiT> composedRecords,
                                   Iterable<? extends SingularZi> generatedSingulars) {
        Map<String, JavaRef> map = new LinkedHashMap<>();

        // Singulars first — radical-form parts win over syllabic aliases.
        for (var glyph : extractSingularGlyphs(singulars)) {
            PerclassSingularIndex.Ref r = singulars.find(glyph);
            // PerclassSingularIndex.Ref carries:
            //   typeContainerFqn  → outer-container FQN to import
            //   typeContainerSimple → outer-container simple name
            //   nestedTypeSimple  → nested record simple name
            //   valueContainerFqn → value container FQN
            //   <synthesised>     → "ContainerSimple.CONST" value expression
            map.putIfAbsent(glyph, new JavaRef.SingularRef(
                    glyph,
                    r.typeContainerFqn(),
                    r.typeContainerSimple(),
                    r.nestedTypeSimple(),
                    r.valueContainerFqn(),
                    r.valueExpression()));
        }

        // Then composed — only added if the glyph isn't already a singular.
        for (ComposedZiT z : composedRecords) {
            String glyph = z.glyph();
            if (glyph == null || glyph.isEmpty()) continue;
            Class<?> clazz = z.getClass();
            map.putIfAbsent(glyph, new JavaRef.ComposedRef(
                    glyph, clazz.getName(), clazz.getSimpleName()));
        }

        // Auto-generated singulars: top-level classes, same shape as composed.
        // putIfAbsent so hand-authored singulars (added in step 1) take precedence.
        for (SingularZi s : generatedSingulars) {
            String glyph = s.glyph();
            if (glyph == null || glyph.isEmpty()) continue;
            Class<?> clazz = s.getClass();
            map.putIfAbsent(glyph, new JavaRef.ComposedRef(
                    glyph, clazz.getName(), clazz.getSimpleName()));
        }

        return new GlyphIndex(map);
    }

    /**
     * Fish out the set of singular glyphs from {@link PerclassSingularIndex}.
     * The index doesn't expose its keyset directly, so we re-scan by
     * iterating over a tight set of well-known glyphs from the catalog —
     * but in practice we just rebuild from the index's own internals via a
     * lightweight reflection adapter. For now, we call find() on every
     * candidate that appears in any catalog row; alternatively, the index
     * could expose a glyphs() accessor.
     *
     * <p>Implementation note: since {@code PerclassSingularIndex} doesn't
     * publish its keyset, this helper assumes the caller will combine the
     * full singular set externally. For first integration we dump the
     * index lazily by introspecting via reflection on the class.</p>
     */
    private static Iterable<String> extractSingularGlyphs(PerclassSingularIndex singulars) {
        // Reflective access to the private byGlyph map's keyset.
        try {
            var f = PerclassSingularIndex.class.getDeclaredField("byGlyph");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, ?> internal = (Map<String, ?>) f.get(singulars);
            return internal.keySet();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(
                    "Cannot extract glyph keyset from PerclassSingularIndex", e);
        }
    }
}
