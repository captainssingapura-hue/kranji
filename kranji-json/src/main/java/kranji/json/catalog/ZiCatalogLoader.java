package kranji.json.catalog;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import kranji.json.dto.BlockRefJson;
import kranji.json.dto.CatalogJson;
import kranji.json.dto.ComposedBlockJson;
import kranji.json.dto.ComposedZiJson;
import kranji.json.dto.SingularPartJson;
import kranji.json.dto.SingularZiJson;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Jackson-based catalog IO. Stateless: the single {@link ObjectMapper} is
 * {@code static final}, configured at class load, and never reassigned.
 *
 * <p>Loads produce a {@link LoadResult} carrying both the typed catalog and
 * any non-fatal warnings observed during parsing:</p>
 * <ul>
 *   <li>duplicate glyphs within a pool (last-wins),</li>
 *   <li>unknown composition styles,</li>
 *   <li>slot-key sets that don't match the declared style.</li>
 * </ul>
 *
 * <p>Cross-pool glyph overlap is <em>allowed</em> and not warned — a glyph may
 * validly appear as both a standalone Zi and a radical-form Part.</p>
 */
public final class ZiCatalogLoader {

    private static final ObjectMapper MAPPER = JsonMapper.builder()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();

    private ZiCatalogLoader() {}

    /** Returns the shared, pre-configured mapper. Treat as read-only. */
    public static ObjectMapper mapper() {
        return MAPPER;
    }

    // ── Load ──────────────────────────────────────────────────────────

    public static LoadResult load(Reader reader) throws IOException {
        return buildResult(MAPPER.readValue(reader, CatalogJson.class));
    }

    public static LoadResult load(InputStream in) throws IOException {
        return buildResult(MAPPER.readValue(in, CatalogJson.class));
    }

    public static LoadResult load(Path path) throws IOException {
        try (var in = Files.newInputStream(path)) {
            return load(in);
        }
    }

    public static LoadResult load(String json) throws IOException {
        return buildResult(MAPPER.readValue(json, CatalogJson.class));
    }

    // ── Save ──────────────────────────────────────────────────────────

    public static void save(CatalogJson json, Writer writer) throws IOException {
        MAPPER.writeValue(writer, json);
    }

    public static void save(CatalogJson json, Path path) throws IOException {
        try (var out = Files.newBufferedWriter(path)) {
            save(json, out);
        }
    }

    public static String toJson(CatalogJson json) throws IOException {
        return MAPPER.writeValueAsString(json);
    }

    // ── Internals ─────────────────────────────────────────────────────

    private static LoadResult buildResult(CatalogJson json) {
        var warnings = new ArrayList<String>();
        collectPoolDupWarnings("singularZi",    json.singularZi(),    SingularZiJson::glyph,   warnings);
        collectPoolDupWarnings("singularParts", json.singularParts(), SingularPartJson::glyph, warnings);
        collectPoolDupWarnings("composedZi",    json.composedZi(),    ComposedZiJson::glyph,   warnings);

        for (var cz : json.composedZi()) {
            if (cz.composition() != null) {
                validateSlots(cz.glyph(), cz.composition(), warnings);
            }
        }

        return new LoadResult(ZiCatalog.from(json), List.copyOf(warnings));
    }

    private static <T> void collectPoolDupWarnings(
            String poolName,
            Collection<T> items,
            Function<T, String> glyphFn,
            List<String> sink) {
        var seen = new HashSet<String>();
        var dups = new LinkedHashSet<String>();
        for (var it : items) {
            String g = glyphFn.apply(it);
            if (g != null && !seen.add(g)) dups.add(g);
        }
        for (var g : dups) {
            sink.add("duplicate glyph '" + g + "' in pool '" + poolName + "' (last-wins)");
        }
    }

    /** Required slot-key sets per composition style. */
    private static final Map<String, Set<String>> REQUIRED_SLOTS = Map.ofEntries(
            Map.entry("left_right",                   Set.of("left", "right")),
            Map.entry("top_bottom",                   Set.of("top", "bottom")),
            Map.entry("left_middle_right",            Set.of("left", "middle", "right")),
            Map.entry("top_middle_bottom",            Set.of("top", "middle", "bottom")),
            Map.entry("full_enclosure",               Set.of("outer", "inner")),
            Map.entry("semi_enclosure_upper_left",    Set.of("wrapper", "content")),
            Map.entry("semi_enclosure_upper_right",   Set.of("wrapper", "content")),
            Map.entry("semi_enclosure_bottom_left",   Set.of("wrapper", "content")),
            Map.entry("semi_enclosure_top_three",     Set.of("wrapper", "content")),
            Map.entry("semi_enclosure_bottom_three",  Set.of("wrapper", "content")),
            Map.entry("semi_enclosure_left_three",    Set.of("wrapper", "content"))
    );

    private static void validateSlots(String owner, ComposedBlockJson block, List<String> sink) {
        var required = REQUIRED_SLOTS.get(block.style());
        if (required == null) {
            sink.add("unknown composition style '" + block.style() + "' in '" + owner + "'");
            return;
        }
        if (!block.slots().keySet().equals(required)) {
            sink.add("slot keys for '" + owner + "' do not match style '" + block.style()
                    + "'; expected " + required + ", got " + block.slots().keySet());
        }
        for (var slot : block.slots().values()) {
            if (slot instanceof BlockRefJson.InlineComposed ic) {
                validateSlots(owner, ic.block(), sink);
            }
        }
    }
}
