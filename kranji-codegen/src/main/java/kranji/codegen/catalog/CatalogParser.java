package kranji.codegen.catalog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Parses {@code docs/catalog/batch-NN-*.md} into {@link CatalogRow}s.
 *
 * <p>The catalog format is a markdown table whose header row is exactly:</p>
 * <pre>{@code
 * | Glyph | Pinyin | Pkg | Layout | Components | Etym | Suffix |
 * }</pre>
 *
 * <p>The parser is deliberately liberal:</p>
 * <ul>
 *   <li>Lines outside table rows (prose, blanks) are skipped silently.</li>
 *   <li>Header and separator rows ({@code |---|---|...}) are skipped.</li>
 *   <li>Cells are trimmed; empty Suffix is accepted (will be flagged by
 *       the iterator).</li>
 *   <li>The Components cell is split on top-level {@code +} (parens-aware),
 *       so {@code "(革+月)+石"} becomes {@code ["(革+月)", "石"]} — the inner
 *       group remains as a literal string token for the resolver to handle.</li>
 * </ul>
 */
public final class CatalogParser {

    private CatalogParser() {}

    /** Parse all {@code batch-*.md} files in a directory; returns rows in file+line order. */
    public static List<CatalogRow> parseDirectory(Path catalogDir) throws IOException {
        List<CatalogRow> all = new ArrayList<>();
        try (var stream = Files.list(catalogDir)) {
            List<Path> files = stream
                    .filter(p -> p.getFileName().toString().startsWith("batch-"))
                    .filter(p -> p.getFileName().toString().endsWith(".md"))
                    .sorted()
                    .toList();
            for (Path f : files) all.addAll(parseFile(f));
        }
        return all;
    }

    /** Parse one .md catalog file. */
    public static List<CatalogRow> parseFile(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file);
        List<CatalogRow> rows = new ArrayList<>();
        String fname = file.getFileName().toString();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isDataRow(line)) continue;
            CatalogRow row = parseRow(line, fname, i + 1);
            if (row != null) rows.add(row);
        }
        return rows;
    }

    // ── Internal ─────────────────────────────────────────────────────────

    private static boolean isDataRow(String line) {
        String t = line.trim();
        if (!t.startsWith("|")) return false;
        if (t.startsWith("|-")) return false;             // separator row
        if (t.startsWith("| Glyph") || t.startsWith("|Glyph")) return false; // header
        return true;
    }

    private static CatalogRow parseRow(String line, String fname, int lineNo) {
        // Split by '|' but skip the empty first/last cells from the leading/trailing pipes.
        String[] raw = line.split("\\|", -1);
        // Expect 9 segments: "" | Glyph | Pinyin | Pkg | Layout | Components | Etym | Suffix | ""
        if (raw.length < 8) return null;
        String glyph    = raw[1].trim();
        String pinyin   = raw[2].trim();
        String pkg      = raw[3].trim();
        String layout   = raw[4].trim();
        String compsStr = raw[5].trim();
        String etym     = raw[6].trim();
        String suffix   = raw.length >= 8 ? raw[7].trim() : "";

        if (glyph.isEmpty() || pkg.isEmpty()) return null;

        List<String> components = parseComponents(compsStr);

        return new CatalogRow(glyph, pinyin, pkg, layout, components,
                etym, suffix, fname, lineNo);
    }

    /**
     * Split components on top-level {@code +} only — parens groups stay intact.
     * Examples:
     * <pre>
     *   "木+亥"           → ["木", "亥"]
     *   "(王+白)+石"      → ["(王+白)", "石"]
     *   "辛+刂+辛"        → ["辛", "刂", "辛"]
     *   "Ø"               → []   (pictograph rows have no components)
     * </pre>
     */
    static List<String> parseComponents(String s) {
        if (s.isEmpty() || s.equals("Ø")) return Collections.emptyList();
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        int depth = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') { depth++; cur.append(c); }
            else if (c == ')') { depth--; cur.append(c); }
            else if (c == '+' && depth == 0) {
                String tok = cur.toString().trim();
                if (!tok.isEmpty()) out.add(tok);
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        String tail = cur.toString().trim();
        if (!tail.isEmpty()) out.add(tail);
        return out;
    }
}
