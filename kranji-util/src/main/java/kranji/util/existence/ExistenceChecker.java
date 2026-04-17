package kranji.util.existence;

import kranji.json.catalog.LoadResult;
import kranji.json.catalog.ZiCatalog;
import kranji.json.catalog.ZiCatalogLoader;
import kranji.json.dto.CatalogJson;
import kranji.json.dto.ComposedZiJson;
import kranji.json.dto.SingularPartJson;
import kranji.json.dto.SingularZiJson;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Takes a JSON feed describing Parts and/or Zi and reports which glyphs
 * already have a strong-typed record implementation and which do not.
 *
 * <p>This is the foundation tool for adding new characters: load a JSON
 * list of target glyphs, run the check, and the missing entries are the
 * work list. Each missing entry retains the pool classification from the
 * JSON (Singular Zi / Singular Part / Composed Zi) so the downstream
 * author knows where the new record belongs.</p>
 *
 * <p>All methods are pure and thread-safe. The underlying
 * {@link StrongTypedRegistry} is a memoised singleton; repeated checks
 * share its state.</p>
 */
public final class ExistenceChecker {

    private ExistenceChecker() {}

    // ── JSON entry points ───────────────────────────────────────────

    /** Check a raw {@link CatalogJson} DTO against the strong-typed registry. */
    public static ExistenceReport check(CatalogJson feed) {
        return check(feed, StrongTypedRegistry.instance());
    }

    /** Check an already-loaded {@link ZiCatalog} (warnings are not re-reported). */
    public static ExistenceReport check(ZiCatalog catalog) {
        return check(
                new CatalogJson(
                        "1",
                        catalog.streamSingularZi().toList(),
                        catalog.streamSingularParts().toList(),
                        catalog.streamComposed().toList()),
                StrongTypedRegistry.instance());
    }

    /** Check the catalog portion of a {@link LoadResult}. */
    public static ExistenceReport check(LoadResult result) {
        return check(result.catalog());
    }

    /** Load JSON from a filesystem path, then check. */
    public static ExistenceReport check(Path jsonFile) throws IOException {
        return check(ZiCatalogLoader.load(jsonFile));
    }

    /** Load JSON from an {@link InputStream}, then check. */
    public static ExistenceReport check(InputStream in) throws IOException {
        return check(ZiCatalogLoader.load(in));
    }

    /** Load JSON from a {@link Reader}, then check. */
    public static ExistenceReport check(Reader reader) throws IOException {
        return check(ZiCatalogLoader.load(reader));
    }

    // ── Core ─────────────────────────────────────────────────────────

    /**
     * Direct form: check a {@link CatalogJson} against any registry instance.
     * Useful for tests that want a fixture registry rather than the real one.
     */
    public static ExistenceReport check(CatalogJson feed, StrongTypedRegistry registry) {
        Objects.requireNonNull(feed,     "feed");
        Objects.requireNonNull(registry, "registry");

        List<EntryStatus> zi    = new ArrayList<>(feed.singularZi().size());
        for (SingularZiJson e : feed.singularZi()) {
            zi.add(statusFor(e.glyph(), EntryStatus.Pool.SINGULAR_ZI,
                    registry.singularZi(e.glyph()).map(Object::getClass).map(Class::getName).orElse(null)));
        }

        List<EntryStatus> parts = new ArrayList<>(feed.singularParts().size());
        for (SingularPartJson e : feed.singularParts()) {
            parts.add(statusFor(e.glyph(), EntryStatus.Pool.SINGULAR_PART,
                    registry.singularPart(e.glyph()).map(Object::getClass).map(Class::getName).orElse(null)));
        }

        List<EntryStatus> composed = new ArrayList<>(feed.composedZi().size());
        for (ComposedZiJson e : feed.composedZi()) {
            composed.add(statusFor(e.glyph(), EntryStatus.Pool.COMPOSED_ZI,
                    registry.composedZi(e.glyph()).map(Object::getClass).map(Class::getName).orElse(null)));
        }

        return new ExistenceReport(zi, parts, composed);
    }

    // ── Helpers ─────────────────────────────────────────────────────

    private static EntryStatus statusFor(String glyph, EntryStatus.Pool pool, String typedClassName) {
        boolean exists = typedClassName != null;
        return new EntryStatus(glyph, pool, exists, typedClassName);
    }
}
