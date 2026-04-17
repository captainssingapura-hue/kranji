package kranji.json.catalog;

import java.util.List;

/**
 * Outcome of {@link ZiCatalogLoader#load}.
 *
 * <p>Holds both the typed catalog and any non-fatal warnings collected during
 * parsing (duplicate glyphs, slot-key mismatches, unknown styles). Warnings
 * are returned rather than logged so the loader remains free of side effects
 * and tests can assert on them directly.</p>
 *
 * @param catalog  the built catalog (last-wins applied for same-pool duplicates)
 * @param warnings non-fatal issues observed during parsing; never null
 */
public record LoadResult(ZiCatalog catalog, List<String> warnings) {

    public LoadResult {
        warnings = warnings == null ? List.of() : List.copyOf(warnings);
    }

    /** True iff no warnings were collected. */
    public boolean isClean() {
        return warnings.isEmpty();
    }
}
