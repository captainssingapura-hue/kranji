package kranji.codegen.catalog;

import kranji.codegen.perclass.PerclassSingularIndex;
import kranji.common.perclass.AllPerclassRecords;
import kranji.common.perclass.promoted.AllPerclassRecordsPromoted;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

/**
 * One-shot tool: walk the catalog, find every single-glyph missing component
 * that's blocking ≥1 row, and append a synthesised singular catalog row
 * for it (with pinyin auto-looked-up from Unihan_Readings.txt).
 *
 * <p>Output format: a markdown table compatible with {@link CatalogParser},
 * written to {@code docs/catalog/batch-99-singulars-auto.md}. Each row uses
 * {@code Layout=S}, {@code Components=Ø}, {@code Etym=P}, and a synthesised
 * {@code Suffix} of the form {@code <PascalPinyin>Auto} (e.g. {@code MoAuto}).
 * Auto suffixes guarantee no collision with the manually-curated
 * {@code batch-99-singulars.md}.</p>
 *
 * <p>Glyphs that:</p>
 * <ul>
 *   <li>Aren't found in Unihan → skipped (logged).</li>
 *   <li>Have a pinyin that doesn't parse via {@link PinyinPkgFormatter} →
 *       skipped (logged).</li>
 *   <li>Are already in the GlyphIndex (already covered) → skipped silently.</li>
 *   <li>Are surrogate-pair glyphs (CJK Extension B+) → skipped (logged) —
 *       the staging file emitter doesn't yet handle 2-codepoint glyphs in
 *       Unicode-escape form. (Trivial future enhancement.)</li>
 * </ul>
 *
 * <p>Args:</p>
 * <ul>
 *   <li>{@code --catalog PATH} — catalog dir (default {@code docs/catalog})</li>
 *   <li>{@code --unihan PATH} — Unihan file (default {@code input/_2500/Unihan_Readings.txt})</li>
 *   <li>{@code --out PATH} — output file (default {@code docs/catalog/batch-99-singulars-auto.md})</li>
 * </ul>
 */
public final class SingularCatalogBuilder {

    private SingularCatalogBuilder() {}

    public static void main(String[] args) throws IOException {
        Path catalogDir = Path.of("docs", "catalog");
        Path unihan = Path.of("input", "_2500", "Unihan_Readings.txt");
        Path out = Path.of("docs", "catalog", "batch-99-singulars-auto.md");
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--catalog" -> catalogDir = Path.of(args[++i]);
                case "--unihan" -> unihan = Path.of(args[++i]);
                case "--out" -> out = Path.of(args[++i]);
                default -> System.err.println("Unknown arg: " + args[i]);
            }
        }

        // 1. Build GlyphIndex (so we can skip glyphs already covered).
        PerclassSingularIndex singulars = PerclassSingularIndex.build();
        List<kranji.zi.ComposedZiT> composed = new ArrayList<>();
        composed.addAll(AllPerclassRecords.ALL);
        composed.addAll(AllPerclassRecordsPromoted.ALL);
        GlyphIndex index = GlyphIndex.build(singulars, composed,
                AllPerclassRecordsPromoted.SINGULARS);
        System.out.println("Glyph index size: " + index.size());

        // 2. Walk catalog, collect blocked glyphs (single-glyph tokens only).
        List<CatalogRow> rows = CatalogParser.parseDirectory(catalogDir);
        Map<String, Integer> blockedHistogram = new TreeMap<>();
        ComponentResolver resolver = new ComponentResolver(index);
        for (CatalogRow row : rows) {
            if ("P".equals(row.layout()) || "S".equals(row.layout())) continue;
            if (index.contains(row.glyph())) continue;
            var res = resolver.resolve(row);
            if (res.unsupported()) continue;
            for (String m : res.missing()) {
                if (m.codePointCount(0, m.length()) == 1) {
                    blockedHistogram.merge(m, 1, Integer::sum);
                }
            }
        }
        System.out.println("Unique missing single-glyph components: " + blockedHistogram.size());

        // 3. Load Unihan + lookup each.
        UnihanReader uh = UnihanReader.load(unihan);
        System.out.println("Unihan kMandarin entries loaded: " + uh.size());

        // 4. Synthesise rows.
        Set<String> seenSuffixes = new HashSet<>();   // dedupe by pkg+suffix
        Map<String, List<String>> rowsByPkg = new TreeMap<>();
        int skippedNoUnihan = 0, skippedSurrogate = 0, skippedBadPinyin = 0, skippedAlreadyCovered = 0;
        for (var e : blockedHistogram.entrySet()) {
            String glyph = e.getKey();
            int blockedCount = e.getValue();

            if (index.contains(glyph)) { skippedAlreadyCovered++; continue; }

            // Skip surrogate-pair glyphs — staging emitter writes \\uXXXX which
            // works for BMP but emits two separate escape sequences for surrogate
            // pairs; that's fine for the string literal but we haven't
            // verified the rest of the toolchain. Skip for v1.
            if (glyph.codePointAt(0) > 0xFFFF) { skippedSurrogate++; continue; }

            Optional<String> py = uh.pinyinOf(glyph);
            if (py.isEmpty()) { skippedNoUnihan++; continue; }

            Optional<String> pkg = PinyinPkgFormatter.toPkg(py.get());
            if (pkg.isEmpty()) { skippedBadPinyin++; continue; }

            // Suffix: capitalise pinyin, strip diacritics, add "Auto" for clarity.
            String suffix = stripDiacritics(py.get());
            if (suffix.isEmpty()) suffix = "X";
            suffix = Character.toUpperCase(suffix.charAt(0))
                    + suffix.substring(1).toLowerCase(Locale.ROOT) + "Auto";
            String key = pkg.get() + "/" + suffix;
            if (!seenSuffixes.add(key)) continue;

            String mdRow = String.format("| %s | %s | %s | S | Ø | P | %s |  // blocks %d",
                    glyph, py.get(), pkg.get(), suffix, blockedCount);
            rowsByPkg.computeIfAbsent(pkg.get(), k -> new ArrayList<>()).add(mdRow);
        }

        System.out.println("Synthesised rows: "
                + rowsByPkg.values().stream().mapToInt(List::size).sum());
        System.out.println("Skipped: noUnihan=" + skippedNoUnihan
                + " surrogate=" + skippedSurrogate
                + " badPinyin=" + skippedBadPinyin
                + " alreadyCovered=" + skippedAlreadyCovered);

        // 5. Write the markdown file.
        StringBuilder sb = new StringBuilder();
        sb.append("# Auto-generated singular catalog rows\n\n");
        sb.append("Single-glyph components blocking composed-iterator rows, with\n");
        sb.append("authoritative pinyin from `input/_2500/Unihan_Readings.txt`.\n");
        sb.append("Generated by `SingularCatalogBuilder` — overwrite-safe.\n\n");
        sb.append("To use: review then concatenate (or symlink) into the catalog.\n\n");
        sb.append("**Total rows:** ")
                .append(rowsByPkg.values().stream().mapToInt(List::size).sum())
                .append("\n\n");
        sb.append("| Glyph | Pinyin | Pkg | Layout | Components | Etym | Suffix |\n");
        sb.append("|-------|--------|-----|--------|------------|------|--------|\n");
        for (var entry : rowsByPkg.entrySet()) {
            for (String row : entry.getValue()) sb.append(row).append('\n');
        }
        Files.createDirectories(out.getParent());
        Files.writeString(out, sb.toString(), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Wrote " + out);
    }

    /** Drop tone diacritics, keep ü. */
    private static String stripDiacritics(String pinyin) {
        StringBuilder out = new StringBuilder(pinyin.length());
        for (int i = 0; i < pinyin.length(); i++) {
            char c = pinyin.charAt(i);
            char ascii = switch (c) {
                case 'ā', 'á', 'ǎ', 'à' -> 'a';
                case 'ē', 'é', 'ě', 'è' -> 'e';
                case 'ī', 'í', 'ǐ', 'ì' -> 'i';
                case 'ō', 'ó', 'ǒ', 'ò' -> 'o';
                case 'ū', 'ú', 'ǔ', 'ù' -> 'u';
                case 'ǖ', 'ǘ', 'ǚ', 'ǜ' -> 'v';
                case 'ü' -> 'v';
                default -> c;
            };
            out.append(ascii);
        }
        return out.toString();
    }
}
