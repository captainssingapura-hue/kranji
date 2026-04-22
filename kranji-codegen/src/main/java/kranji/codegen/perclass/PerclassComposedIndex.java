package kranji.codegen.perclass;

import com.fasterxml.jackson.databind.ObjectMapper;
import kranji.json.dto.ComposedZiJson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Reverse-lookup mapping a composed-character glyph (e.g. {@code "明"},
 * {@code "你"}, {@code "魔"}) to the Java reference of its generated
 * per-class typed record.
 *
 * <p>Unlike {@link PerclassSingularIndex}, which reflects on existing
 * classes, this index is built from the on-disk JSON catalog
 * <em>before</em> any source is emitted. Each JSON file's path
 * determines the package; its filename stem determines the class name;
 * the glyph is read from the file body.</p>
 *
 * <p>The emitted value for any GlyphRef is {@code ClassName.INSTANCE}
 * and the type parameter is {@code ClassName}. Both forms share a
 * single import ({@code import <pkg>.<ClassName>;}).</p>
 */
public final class PerclassComposedIndex {

    /** Java reference for a generated typed per-class record. */
    public record Ref(String packageFqn, String className, String glyph) {

        /** FQN of the generated record. */
        public String typeImport() {
            return packageFqn + "." + className;
        }

        /** {@code ClassName} — type expression for a slot parameter. */
        public String typeExpression() {
            return className;
        }

        /** {@code ClassName.INSTANCE} — value expression for a slot accessor. */
        public String valueExpression() {
            return className + ".INSTANCE";
        }
    }

    /** One catalog entry, parsed and routed. Produced by {@link #loadAll}. */
    public record Entry(PerclassRouting.Key key, ComposedZiJson data, Path relPath) {}

    /** Glyph → Ref map; entries in iteration order = catalog scan order. */
    private final Map<String, Ref> byGlyph;
    private final List<Entry> entries;

    private PerclassComposedIndex(Map<String, Ref> byGlyph, List<Entry> entries) {
        this.byGlyph = byGlyph;
        this.entries = entries;
    }

    public Ref find(String glyph) { return byGlyph.get(glyph); }

    public Ref require(String glyph) {
        Ref r = byGlyph.get(glyph);
        if (r == null) {
            throw new IllegalStateException(
                    "No composed-Zi reference for glyph \"" + glyph + "\". "
                            + "Either it needs a JSON catalog entry, or the "
                            + "composition tree references a glyph that is "
                            + "neither a singular nor a registered ComposedZi.");
        }
        return r;
    }

    public int size() { return byGlyph.size(); }

    /** All loaded entries in deterministic order (by FQN). */
    public List<Entry> entries() { return entries; }

    /**
     * Walk the catalog root and load every {@code <depth>/<init>/<FT>/<NAME>.json}.
     *
     * <p>Skips {@code meta.json} files (catalog metadata, not Zi records).
     * Entries are sorted by FQN so the scan order is deterministic.</p>
     */
    public static PerclassComposedIndex loadAll(Path catalogRoot) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        List<Entry> entries = new java.util.ArrayList<>();
        try (var walk = Files.walk(catalogRoot)) {
            for (Path p : (Iterable<Path>) walk::iterator) {
                if (!Files.isRegularFile(p)) continue;
                String name = p.getFileName().toString();
                if (!name.endsWith(".json")) continue;
                if (name.equals("meta.json")) continue;

                Path rel = catalogRoot.relativize(p);
                PerclassRouting.Key key = PerclassRouting.keyFor(rel);
                ComposedZiJson data = mapper.readValue(p.toFile(), ComposedZiJson.class);
                entries.add(new Entry(key, data, rel));
            }
        }
        entries.sort(Comparator.comparing(e -> e.key().fqn()));

        Map<String, Ref> map = new LinkedHashMap<>();
        for (Entry e : entries) {
            String glyph = e.data().glyph();
            if (glyph == null || glyph.isEmpty()) {
                throw new IllegalStateException(
                        "Missing glyph in " + e.relPath());
            }
            Ref ref = new Ref(e.key().packageName(), e.key().className(), glyph);
            Ref prev = map.putIfAbsent(glyph, ref);
            if (prev != null && !prev.equals(ref)) {
                throw new IllegalStateException(
                        "Duplicate glyph " + glyph + " at " + e.relPath()
                                + " (already mapped to " + prev.typeImport() + ")");
            }
        }

        return new PerclassComposedIndex(map, List.copyOf(entries));
    }
}
