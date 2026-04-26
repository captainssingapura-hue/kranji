package kranji.codegen.catalog;

import kranji.codegen.perclass.PerclassSingularIndex;
import kranji.common.perclass.AllPerclassRecords;
import kranji.common.perclass.promoted.AllPerclassRecordsPromoted;
import kranji.zi.ComposedZiT;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

/**
 * One-shot diagnostic: for every glyph in {@code input/_2500/all_glyphs.txt}
 * that is NOT yet in the {@link GlyphIndex}, classify why it's stuck and
 * emit a structured markdown report at {@code docs/catalog/outliers.md}
 * for human review.
 *
 * <p>Categories (mutually exclusive — each glyph appears in exactly one):</p>
 *
 * <ol>
 *   <li><b>Arity mismatch</b> — catalog row exists but its {@code Layout}
 *       expects N components and the {@code Components} cell provides M ≠ N.
 *       Fix: re-decompose into nested 2-part form, or change layout code.</li>
 *   <li><b>Blocked on missing components</b> — catalog row's components are
 *       all single glyphs but ≥1 isn't yet in the {@link GlyphIndex}.
 *       Fix: add the listed component as a singular (or wait for it to
 *       arrive via another catalog row).</li>
 *   <li><b>Bad component token</b> — catalog has a token that's neither a
 *       single glyph nor a single-glyph parens group (typically a nested
 *       composition like {@code "(王+白)"} whose inner record isn't in the
 *       catalog yet, or a literal {@code "?"} placeholder, or mojibake).</li>
 *   <li><b>Pictograph row</b> — catalog row tagged {@code Layout=P}; would
 *       belong as a singular, not a composed record.</li>
 *   <li><b>Surrogate-pair</b> — glyph is in CJK Extension B+ (codepoint
 *       above U+FFFF). Generator currently emits BMP escapes only.</li>
 *   <li><b>Not in catalog</b> — glyph is in the 2500 corpus but no row
 *       exists in any {@code docs/catalog/batch-*.md} file. Need to add it.</li>
 * </ol>
 */
public final class OutliersReportMain {

    public enum Category {
        ARITY_MISMATCH("Arity mismatches", "Layout code expects different component count than provided. Re-decompose into nested 2-part form, or change the Layout code (e.g. TB→TB3 if a real 3-part layout)."),
        BLOCKED_ON_MISSING("Blocked on missing components", "Components are well-formed but at least one isn't yet a singular or a previously-promoted record. Add the listed components to a singulars catalog batch."),
        BAD_COMPONENT_TOKEN("Bad component token", "Catalog cell has a multi-glyph token without proper grouping, a literal '?' placeholder, mojibake, or references a nested composition that has no catalog row of its own."),
        PICTOGRAPH("Pictograph rows (singular candidates)", "Tagged Layout=P; intentionally skipped by composed iterator. Move to docs/catalog/batch-99-singulars.md (or batch-99-singulars-auto.md) with Layout=S."),
        SURROGATE_PAIR("Surrogate-pair glyphs", "CJK Extension B+ codepoint (above U+FFFF). Either extend SingularGenerator to emit surrogate-pair string literals, or hand-author."),
        NOT_IN_CATALOG("Not in any catalog batch file", "Glyph is in the 2500 corpus but no row exists. Add a row to one of the batch-NN-*.md files in the appropriate initial group.");

        public final String title;
        public final String guidance;
        Category(String t, String g) { this.title = t; this.guidance = g; }
    }

    public record OutlierEntry(String glyph, Category category, String detail,
                               String sourceFile, int sourceLine,
                               String pinyin, String pkg, String layout,
                               String components, String etym, String suffix) {}

    public static void main(String[] args) throws IOException {
        Path corpusFile = Path.of("input", "_2500", "all_glyphs.txt");
        Path catalogDir = Path.of("docs", "catalog");
        Path outFile = Path.of("docs", "catalog", "outliers.md");

        // 1. Build current GlyphIndex.
        PerclassSingularIndex singulars = PerclassSingularIndex.build();
        List<ComposedZiT> composed = new ArrayList<>();
        composed.addAll(AllPerclassRecords.ALL);
        composed.addAll(AllPerclassRecordsPromoted.ALL);
        GlyphIndex index = GlyphIndex.build(singulars, composed,
                AllPerclassRecordsPromoted.SINGULARS);

        // 2. Parse corpus.
        Set<String> corpus = parseCommaList(corpusFile);

        // 3. Identify uncovered.
        Set<String> uncovered = new LinkedHashSet<>();
        for (String g : corpus) {
            if (!index.contains(g)) uncovered.add(g);
        }

        // 4. Index every catalog row by glyph for fast lookup.
        List<CatalogRow> rows = CatalogParser.parseDirectory(catalogDir);
        Map<String, CatalogRow> rowByGlyph = new HashMap<>();
        for (CatalogRow r : rows) {
            // First-write-wins (catalog deduping is a known issue but rare).
            rowByGlyph.putIfAbsent(r.glyph(), r);
        }

        // 5. Classify each uncovered glyph.
        ComponentResolver resolver = new ComponentResolver(index);
        GeneratorRegistry registry = GeneratorRegistry.defaultRegistry();
        EnumMap<Category, List<OutlierEntry>> bucketed = new EnumMap<>(Category.class);
        for (Category c : Category.values()) bucketed.put(c, new ArrayList<>());

        // Histogram of unique missing components for the "blocked" rows
        // (so the user can see which component additions would unblock the most).
        Map<String, Integer> missingComponentImpact = new TreeMap<>();

        for (String glyph : uncovered) {
            // Surrogate-pair check first — independent of catalog presence.
            if (glyph.codePointAt(0) > 0xFFFF || glyph.length() > 1) {
                CatalogRow r = rowByGlyph.get(glyph);
                bucketed.get(Category.SURROGATE_PAIR).add(toEntry(glyph,
                        Category.SURROGATE_PAIR,
                        "codepoint above U+FFFF — generator emits BMP escapes only", r));
                continue;
            }

            CatalogRow row = rowByGlyph.get(glyph);
            if (row == null) {
                bucketed.get(Category.NOT_IN_CATALOG).add(toEntry(glyph,
                        Category.NOT_IN_CATALOG, "no catalog row found", null));
                continue;
            }

            if ("P".equals(row.layout())) {
                bucketed.get(Category.PICTOGRAPH).add(toEntry(glyph,
                        Category.PICTOGRAPH, "Layout=P (singular candidate)", row));
                continue;
            }

            var gen = registry.find(row.layout());
            if (gen.isEmpty()) {
                bucketed.get(Category.BAD_COMPONENT_TOKEN).add(toEntry(glyph,
                        Category.BAD_COMPONENT_TOKEN,
                        "no generator for layout '" + row.layout() + "'", row));
                continue;
            }

            var res = resolver.resolve(row);
            if (res.unsupported()) {
                bucketed.get(Category.BAD_COMPONENT_TOKEN).add(toEntry(glyph,
                        Category.BAD_COMPONENT_TOKEN,
                        "unparseable component token: " + res.missing(), row));
                continue;
            }
            if (res.resolved().size() != gen.get().arity() && res.missing().isEmpty()) {
                bucketed.get(Category.ARITY_MISMATCH).add(toEntry(glyph,
                        Category.ARITY_MISMATCH,
                        "layout '" + row.layout() + "' expects " + gen.get().arity()
                                + ", row provides " + row.components().size(), row));
                continue;
            }
            if (!res.missing().isEmpty()) {
                String missingList = String.join(", ", res.missing());
                for (String m : res.missing()) {
                    if (m.codePointCount(0, m.length()) == 1) {
                        missingComponentImpact.merge(m, 1, Integer::sum);
                    }
                }
                bucketed.get(Category.BLOCKED_ON_MISSING).add(toEntry(glyph,
                        Category.BLOCKED_ON_MISSING,
                        "missing: " + missingList, row));
                continue;
            }
            // If we get here, the row should have resolved — likely a stale
            // index issue. Catch-all bucket.
            bucketed.get(Category.NOT_IN_CATALOG).add(toEntry(glyph,
                    Category.NOT_IN_CATALOG, "row resolves but glyph not in index (stale?)", row));
        }

        // 6. Render the markdown report.
        StringBuilder sb = new StringBuilder();
        sb.append("# Outliers — glyphs in the 2500 corpus still uncovered\n\n");
        sb.append("Auto-generated by `OutliersReportMain`. Overwritten on every run.\n\n");
        sb.append("**Total uncovered:** ").append(uncovered.size()).append(" / 2500\n\n");
        sb.append("## Summary by category\n\n");
        sb.append("| Category | Count | Action |\n");
        sb.append("|----------|-------|--------|\n");
        for (Category c : Category.values()) {
            int n = bucketed.get(c).size();
            sb.append("| ").append(c.title).append(" | ").append(n).append(" | ");
            if (n > 0) sb.append("see section below");
            else sb.append("_none_");
            sb.append(" |\n");
        }
        sb.append('\n');

        if (!missingComponentImpact.isEmpty()) {
            sb.append("## High-impact missing components (would unblock multiple rows)\n\n");
            sb.append("Adding these as singulars would resolve the listed dependent rows.\n\n");
            sb.append("| Glyph | Rows blocked |\n");
            sb.append("|-------|---|\n");
            missingComponentImpact.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(40)
                    .forEach(e -> sb.append("| ").append(e.getKey())
                            .append(" | ").append(e.getValue()).append(" |\n"));
            sb.append('\n');
        }

        for (Category c : Category.values()) {
            List<OutlierEntry> rows2 = bucketed.get(c);
            sb.append("## ").append(c.title).append(" (").append(rows2.size()).append(")\n\n");
            sb.append("_").append(c.guidance).append("_\n\n");
            if (rows2.isEmpty()) {
                sb.append("None.\n\n");
                continue;
            }
            rows2.sort(Comparator.comparing(OutlierEntry::sourceFile, Comparator.nullsLast(Comparator.naturalOrder()))
                    .thenComparingInt(OutlierEntry::sourceLine)
                    .thenComparing(OutlierEntry::glyph));
            sb.append("| Glyph | Pinyin | Pkg | Layout | Components | Etym | Suffix | Source | Line | Detail |\n");
            sb.append("|-------|--------|-----|--------|------------|------|--------|--------|------|--------|\n");
            for (OutlierEntry e : rows2) {
                sb.append("| ").append(e.glyph)
                        .append(" | ").append(safe(e.pinyin))
                        .append(" | ").append(safe(e.pkg))
                        .append(" | ").append(safe(e.layout))
                        .append(" | ").append(safe(e.components))
                        .append(" | ").append(safe(e.etym))
                        .append(" | ").append(safe(e.suffix))
                        .append(" | ").append(safe(e.sourceFile))
                        .append(" | ").append(e.sourceLine == 0 ? "" : String.valueOf(e.sourceLine))
                        .append(" | ").append(safe(e.detail))
                        .append(" |\n");
            }
            sb.append('\n');
        }

        Files.createDirectories(outFile.getParent());
        Files.writeString(outFile, sb.toString(), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Wrote " + outFile);
        System.out.println();
        System.out.println("Total uncovered: " + uncovered.size());
        for (Category c : Category.values()) {
            System.out.println("  " + c.title + ": " + bucketed.get(c).size());
        }
    }

    private static OutlierEntry toEntry(String glyph, Category cat, String detail, CatalogRow r) {
        if (r == null) {
            return new OutlierEntry(glyph, cat, detail, "", 0, "", "", "", "", "", "");
        }
        return new OutlierEntry(glyph, cat, detail,
                r.sourceFile(), r.sourceLine(),
                r.pinyinRaw(), r.pkg(), r.layout(),
                String.join("+", r.components()),
                r.etym(), r.suffix());
    }

    private static String safe(String s) { return s == null ? "" : s; }

    private static Set<String> parseCommaList(Path file) throws IOException {
        String content = Files.readString(file, StandardCharsets.UTF_8);
        Set<String> result = new LinkedHashSet<>();
        for (String tok : content.split("[,\\s]+")) {
            String t = tok.strip();
            if (!t.isEmpty()) result.add(t);
        }
        return result;
    }
}
