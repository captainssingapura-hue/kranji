package kranji.json.bridge;

import kranji.json.catalog.ZiCatalog;
import kranji.json.dto.ComposedZiJson;
import kranji.json.dto.SingularPartJson;
import kranji.json.dto.SingularZiJson;
import kranji.zi.ComposedZi;
import kranji.zi.SingularBlock;
import kranji.zi.SingularPart;
import kranji.zi.SingularZi;
import kranji.zi.Zi;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A typed view over an untyped {@link ZiCatalog}.
 *
 * <p>Resolves DTO entries to {@link Zi} / {@link SingularZi} /
 * {@link SingularPart} / {@link ComposedZi} on demand and memoises the
 * results so repeated lookups are O(1). The catalog is external state;
 * this view is a thin, functional projection over it.</p>
 *
 * <p>The typed view also serves as a {@link BlockResolver}: glyph strings
 * inside composition trees are resolved against the combined singular
 * pool (Zi first, Part fallback).</p>
 *
 * <h2>Immutability</h2>
 * The underlying {@link ZiCatalog} is immutable. The two memoisation caches
 * are populated lazily but each key maps to a single value; once computed,
 * the typed instance is reused. Effectively immutable to observers.
 */
public final class TypedZiCatalog implements BlockResolver {

    private final ZiCatalog source;

    // Memoisation — keyed by glyph, populated on first lookup.
    private final Map<String, SingularZi>   zCache = new ConcurrentHashMap<>();
    private final Map<String, SingularPart> pCache = new ConcurrentHashMap<>();
    private final Map<String, ComposedZi>   cCache = new ConcurrentHashMap<>();

    private TypedZiCatalog(ZiCatalog source) {
        this.source = source;
    }

    /** Wrap a {@link ZiCatalog} for typed access. */
    public static TypedZiCatalog of(ZiCatalog source) {
        return new TypedZiCatalog(Objects.requireNonNull(source, "source"));
    }

    /** The underlying untyped catalog. */
    public ZiCatalog source() {
        return source;
    }

    // ── Typed accessors ───────────────────────────────────────────────

    /** Typed {@link SingularZi} for a glyph, if present in the standalone pool. */
    public Optional<SingularZi> singularZi(String glyph) {
        SingularZi cached = zCache.get(glyph);
        if (cached != null) return Optional.of(cached);
        return source.singularZi(glyph).map(this::buildSingularZi);
    }

    /** Typed {@link SingularPart} for a glyph, if present in the radicals pool. */
    public Optional<SingularPart> singularPart(String glyph) {
        SingularPart cached = pCache.get(glyph);
        if (cached != null) return Optional.of(cached);
        return source.singularPart(glyph).map(this::buildSingularPart);
    }

    /** Typed {@link ComposedZi} for a glyph, if present in the composed pool. */
    public Optional<ComposedZi> composedZi(String glyph) {
        ComposedZi cached = cCache.get(glyph);
        if (cached != null) return Optional.of(cached);
        return source.composedZi(glyph).map(this::buildComposedZi);
    }

    /**
     * Typed {@link Zi} for a glyph — Composed wins over Singular if the
     * glyph appears in both pools, mirroring {@link ZiCatalog#any(String)}.
     */
    public Optional<Zi> zi(String glyph) {
        Optional<ComposedZi> composed = composedZi(glyph);
        if (composed.isPresent()) return composed.map(cz -> cz);
        return singularZi(glyph).map(sz -> sz);
    }

    // ── BlockResolver ─────────────────────────────────────────────────

    /**
     * Resolve a glyph to a typed {@link SingularBlock}. Checks the Zi pool
     * first, then the Part pool. Returns {@code null} if neither contains
     * the glyph.
     */
    @Override
    public SingularBlock resolve(String glyph) {
        return singularZi(glyph)
                .<SingularBlock>map(sz -> sz)
                .or(() -> singularPart(glyph).map(sp -> sp))
                .orElse(null);
    }

    // ── Builders (memoise + construct) ────────────────────────────────

    private SingularZi buildSingularZi(SingularZiJson json) {
        return zCache.computeIfAbsent(json.glyph(), g -> UntypedToTyped.singularZi(json));
    }

    private SingularPart buildSingularPart(SingularPartJson json) {
        return pCache.computeIfAbsent(json.glyph(), g -> {
            // Eagerly resolve derivedFrom against the Zi pool when possible.
            SingularZi derived = null;
            if (json.derivedFrom() != null) {
                derived = singularZi(json.derivedFrom()).orElse(null);
            }
            return UntypedToTyped.singularPart(json, derived);
        });
    }

    private ComposedZi buildComposedZi(ComposedZiJson json) {
        return cCache.computeIfAbsent(json.glyph(), g -> UntypedToTyped.composedZi(json, this));
    }

    // ── Diagnostics ───────────────────────────────────────────────────

    /** Snapshot of the current memoisation size (for tests / observability). */
    public int cachedZiCount()   { return zCache.size(); }
    public int cachedPartCount() { return pCache.size(); }
    public int cachedComposedCount() { return cCache.size(); }
}
