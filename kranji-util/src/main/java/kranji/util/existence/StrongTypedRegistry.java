package kranji.util.existence;

import kranji.library.BasicSet;
import kranji.library.LibraryMember;
import kranji.singular.SingularFamiliesPerclass;
import kranji.zi.ComposedZi;
import kranji.zi.SingularBlock;
import kranji.zi.SingularPart;
import kranji.zi.SingularZi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Immutable snapshot of every glyph that currently has a strong-typed
 * record implementation in the Kranji codebase.
 *
 * <p>Three pools, indexed by glyph:</p>
 * <ul>
 *   <li><b>Singular Zi</b> — {@code kranji-core} and
 *       {@code kranji-singulars-perclass} families that are both
 *       {@link LibraryMember} and {@link SingularZi}
 *       (e.g. {@code kranji.singular.nature.r.Ri}).</li>
 *   <li><b>Singular Part</b> — families that are {@link LibraryMember} and
 *       {@link SingularPart} (e.g. {@code WaterFamily.SanDianShui}).</li>
 *   <li><b>Composed Zi</b> — {@link ComposedZi} records. Currently empty:
 *       the former {@code kranji-common} provider has been orphaned
 *       pending a per-class codegen replacement, so the composed pool
 *       has no source until that work lands. The API surface
 *       ({@link #composedZi}, {@link #hasComposedZi}, {@link #composedZiCount})
 *       is preserved so callers compile and the existence checker keeps
 *       reporting composed-feed entries as "missing" rather than crashing.</li>
 * </ul>
 *
 * <p>The registry is a <em>singleton snapshot</em>: built exactly once on
 * first access by registering {@link BasicSet#register()} and
 * {@link SingularFamiliesPerclass#registerInto(BasicSet)}, then indexing
 * the resulting members. Both register calls are idempotent, so accessing
 * the registry is safe regardless of what the rest of the application
 * has already done.</p>
 */
public final class StrongTypedRegistry {

    // ── Singleton ────────────────────────────────────────────────────

    private static volatile StrongTypedRegistry instance;

    /** Shared instance; built on first call, cheap on subsequent calls. */
    public static StrongTypedRegistry instance() {
        StrongTypedRegistry local = instance;
        if (local == null) {
            synchronized (StrongTypedRegistry.class) {
                local = instance;
                if (local == null) {
                    instance = local = build();
                }
            }
        }
        return local;
    }

    // ── State ────────────────────────────────────────────────────────

    private final Map<String, SingularZi>   ziByGlyph;
    private final Map<String, SingularPart> partByGlyph;
    private final Map<String, ComposedZi>   composedByGlyph;

    private StrongTypedRegistry(
            Map<String, SingularZi>   ziByGlyph,
            Map<String, SingularPart> partByGlyph,
            Map<String, ComposedZi>   composedByGlyph) {
        this.ziByGlyph        = ziByGlyph;
        this.partByGlyph      = partByGlyph;
        this.composedByGlyph  = composedByGlyph;
    }

    // ── Builder ──────────────────────────────────────────────────────

    private static StrongTypedRegistry build() {
        BasicSet basicSet = BasicSet.INSTANCE;
        basicSet.register();                                  // idempotent
        SingularFamiliesPerclass.registerInto(basicSet);      // idempotent

        // Index every glyph → list of definers. Same glyph appearing more
        // than once (within a pool or across pools) is a bug: lookups and
        // the existence checker would give inconsistent answers depending
        // on which pool was queried first.
        Map<String, List<String>> allDefiners = new LinkedHashMap<>();

        Map<String, SingularZi>   ziByGlyph   = new LinkedHashMap<>();
        Map<String, SingularPart> partByGlyph = new LinkedHashMap<>();

        for (LibraryMember<BasicSet> member : basicSet.components()) {
            if (member instanceof SingularZi sz) {
                ziByGlyph.put(sz.glyph(), sz);
                recordDefiner(allDefiners, sz.glyph(),
                        "SingularZi(" + member.getClass().getName() + ")");
            } else if (member instanceof SingularPart sp) {
                partByGlyph.put(sp.glyph(), sp);
                recordDefiner(allDefiners, sp.glyph(),
                        "SingularPart(" + member.getClass().getName() + ")");
            }
            // Members that are LibraryMember but neither SingularZi nor
            // SingularPart are ignored — not relevant to existence checks.
        }

        // Composed pool is intentionally empty: the legacy kranji-common
        // provider is no longer on the classpath. Keep the map to preserve
        // the API surface; the codegen'd replacement will repopulate here.
        Map<String, ComposedZi> composedByGlyph = new LinkedHashMap<>();

        List<String> offenders = new ArrayList<>();
        for (Map.Entry<String, List<String>> e : allDefiners.entrySet()) {
            if (e.getValue().size() > 1) {
                offenders.add("  " + e.getKey() + " → " + e.getValue());
            }
        }
        if (!offenders.isEmpty()) {
            throw new IllegalStateException(
                    "Strong-typed registry has " + offenders.size() +
                    " glyph(s) defined more than once. Each Zi or Part may be " +
                    "defined at most once across SingularZi / SingularPart / " +
                    "ComposedZi pools:\n" + String.join("\n", offenders));
        }

        return new StrongTypedRegistry(
                Collections.unmodifiableMap(ziByGlyph),
                Collections.unmodifiableMap(partByGlyph),
                Collections.unmodifiableMap(composedByGlyph));
    }

    private static void recordDefiner(
            Map<String, List<String>> allDefiners, String glyph, String definer) {
        allDefiners.computeIfAbsent(glyph, g -> new ArrayList<>()).add(definer);
    }

    // ── Lookup ───────────────────────────────────────────────────────

    public Optional<SingularZi>   singularZi(String glyph)   { return Optional.ofNullable(ziByGlyph.get(glyph)); }
    public Optional<SingularPart> singularPart(String glyph) { return Optional.ofNullable(partByGlyph.get(glyph)); }
    public Optional<ComposedZi>   composedZi(String glyph)   { return Optional.ofNullable(composedByGlyph.get(glyph)); }

    /** Any strong-typed singular block (Zi first, Part fallback). */
    public Optional<SingularBlock> singular(String glyph) {
        SingularZi sz = ziByGlyph.get(glyph);
        if (sz != null) return Optional.of(sz);
        SingularPart sp = partByGlyph.get(glyph);
        if (sp != null) return Optional.of(sp);
        return Optional.empty();
    }

    public boolean hasSingularZi(String glyph)   { return ziByGlyph.containsKey(glyph); }
    public boolean hasSingularPart(String glyph) { return partByGlyph.containsKey(glyph); }
    public boolean hasComposedZi(String glyph)   { return composedByGlyph.containsKey(glyph); }

    /** True if the glyph exists in any of the three pools. */
    public boolean hasAny(String glyph) {
        return hasSingularZi(glyph) || hasSingularPart(glyph) || hasComposedZi(glyph);
    }

    // ── Counts & enumeration ────────────────────────────────────────

    public int singularZiCount()   { return ziByGlyph.size(); }
    public int singularPartCount() { return partByGlyph.size(); }
    public int composedZiCount()   { return composedByGlyph.size(); }

    public Set<String> singularZiGlyphs()   { return ziByGlyph.keySet(); }
    public Set<String> singularPartGlyphs() { return partByGlyph.keySet(); }
    public Set<String> composedZiGlyphs()   { return composedByGlyph.keySet(); }
}
