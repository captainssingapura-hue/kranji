package kranji.json.catalog;

import kranji.json.dto.CatalogJson;
import kranji.json.dto.ComposedZiJson;
import kranji.json.dto.EntryJson;
import kranji.json.dto.SingularPartJson;
import kranji.json.dto.SingularZiJson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Immutable, indexed view over a {@link CatalogJson}.
 *
 * <p>Built via {@link #from(CatalogJson)}. All internal maps are unmodifiable
 * after construction; the DTOs themselves are records and therefore also
 * immutable.</p>
 *
 * <p>Lookup is O(1) by glyph across three pools (Singular Zi, Singular Part,
 * Composed Zi). A single glyph may legitimately appear in more than one pool
 * — e.g. 水 as a standalone Zi and 水 used as a radical; {@link #all(String)}
 * returns every match, {@link #any(String)} returns the first by priority
 * (Composed &gt; Singular Zi &gt; Singular Part).</p>
 *
 * <p>Same-pool duplicates collapse last-wins silently at this layer. The
 * loader ({@link ZiCatalogLoader}) detects them and reports via
 * {@link LoadResult#warnings()}.</p>
 */
public final class ZiCatalog {

    private final Map<String, SingularZiJson>        szByGlyph;
    private final Map<String, SingularPartJson>      spByGlyph;
    private final Map<String, ComposedZiJson>        czByGlyph;
    private final Map<Integer, List<ComposedZiJson>> czByStrokes;
    private final Map<Integer, List<ComposedZiJson>> czByRadical;

    private ZiCatalog(
            Map<String, SingularZiJson>        szByGlyph,
            Map<String, SingularPartJson>      spByGlyph,
            Map<String, ComposedZiJson>        czByGlyph,
            Map<Integer, List<ComposedZiJson>> czByStrokes,
            Map<Integer, List<ComposedZiJson>> czByRadical) {
        this.szByGlyph   = szByGlyph;
        this.spByGlyph   = spByGlyph;
        this.czByGlyph   = czByGlyph;
        this.czByStrokes = czByStrokes;
        this.czByRadical = czByRadical;
    }

    /**
     * Builds a catalog from a parsed {@link CatalogJson}. Same-pool duplicates
     * resolve last-wins silently — callers that care use {@link ZiCatalogLoader}
     * which surfaces such cases in {@link LoadResult#warnings()}.
     */
    public static ZiCatalog from(CatalogJson json) {
        Map<String, SingularZiJson> sz = new LinkedHashMap<>();
        for (var z : json.singularZi()) {
            if (z.glyph() != null) sz.put(z.glyph(), z);
        }

        Map<String, SingularPartJson> sp = new LinkedHashMap<>();
        for (var p : json.singularParts()) {
            if (p.glyph() != null) sp.put(p.glyph(), p);
        }

        Map<String, ComposedZiJson> cz = new LinkedHashMap<>();
        for (var z : json.composedZi()) {
            if (z.glyph() != null) cz.put(z.glyph(), z);
        }

        Map<Integer, List<ComposedZiJson>> byStrokes = cz.values().stream()
                .filter(z -> z.strokes() != null)
                .collect(Collectors.groupingBy(
                        ComposedZiJson::strokes,
                        Collectors.collectingAndThen(Collectors.toList(), List::copyOf)));

        Map<Integer, List<ComposedZiJson>> byRadical = cz.values().stream()
                .filter(z -> z.radicalNo() != null)
                .collect(Collectors.groupingBy(
                        ComposedZiJson::radicalNo,
                        Collectors.collectingAndThen(Collectors.toList(), List::copyOf)));

        return new ZiCatalog(
                Collections.unmodifiableMap(sz),
                Collections.unmodifiableMap(sp),
                Collections.unmodifiableMap(cz),
                Collections.unmodifiableMap(byStrokes),
                Collections.unmodifiableMap(byRadical));
    }

    // ── Single-pool lookup ────────────────────────────────────────────

    public Optional<SingularZiJson> singularZi(String glyph) {
        return Optional.ofNullable(szByGlyph.get(glyph));
    }

    public Optional<SingularPartJson> singularPart(String glyph) {
        return Optional.ofNullable(spByGlyph.get(glyph));
    }

    public Optional<ComposedZiJson> composedZi(String glyph) {
        return Optional.ofNullable(czByGlyph.get(glyph));
    }

    // ── Cross-pool lookup ─────────────────────────────────────────────

    /**
     * Every entry across all three pools that matches the given glyph.
     *
     * <p>A glyph may legitimately appear in more than one pool (e.g. a
     * standalone character that is also used as a radical); this method
     * returns all matches in priority order: Composed Zi, Singular Zi,
     * Singular Part.</p>
     */
    public List<EntryJson> all(String glyph) {
        var out = new ArrayList<EntryJson>(3);
        var cz = czByGlyph.get(glyph);
        if (cz != null) out.add(new EntryJson.OfComposedZi(cz));
        var sz = szByGlyph.get(glyph);
        if (sz != null) out.add(new EntryJson.OfSingularZi(sz));
        var sp = spByGlyph.get(glyph);
        if (sp != null) out.add(new EntryJson.OfSingularPart(sp));
        return List.copyOf(out);
    }

    /** First match by priority, or empty. See {@link #all(String)}. */
    public Optional<EntryJson> any(String glyph) {
        var cz = czByGlyph.get(glyph);
        if (cz != null) return Optional.of(new EntryJson.OfComposedZi(cz));
        var sz = szByGlyph.get(glyph);
        if (sz != null) return Optional.of(new EntryJson.OfSingularZi(sz));
        var sp = spByGlyph.get(glyph);
        if (sp != null) return Optional.of(new EntryJson.OfSingularPart(sp));
        return Optional.empty();
    }

    // ── Composed-Zi grouping ──────────────────────────────────────────

    public List<ComposedZiJson> byStrokeCount(int strokes) {
        return czByStrokes.getOrDefault(strokes, List.of());
    }

    public List<ComposedZiJson> byRadical(int radicalNo) {
        return czByRadical.getOrDefault(radicalNo, List.of());
    }

    // ── Streams ───────────────────────────────────────────────────────

    public Stream<SingularZiJson>   streamSingularZi()    { return szByGlyph.values().stream(); }
    public Stream<SingularPartJson> streamSingularParts() { return spByGlyph.values().stream(); }
    public Stream<ComposedZiJson>   streamComposed()      { return czByGlyph.values().stream(); }

    // ── Sizes (convenience) ───────────────────────────────────────────

    public int singularZiCount()   { return szByGlyph.size(); }
    public int singularPartCount() { return spByGlyph.size(); }
    public int composedZiCount()   { return czByGlyph.size(); }
}
