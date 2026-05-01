package kranji.codegen.catalog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Renders a {@code docs/catalog/issues.md} report from the issues
 * surfaced during one iterator run.
 *
 * <p>The report is overwritten on every iterator invocation. Sections
 * are stable (one per {@link CatalogIssue.Kind}), and within each
 * section rows are sorted by source-file then line number for
 * deterministic diffs.</p>
 */
public final class CatalogIssueWriter {

    private CatalogIssueWriter() {}

    public static void write(List<CatalogIssue> issues, Path outFile) throws IOException {
        EnumMap<CatalogIssue.Kind, java.util.List<CatalogIssue>> bucketed =
                new EnumMap<>(CatalogIssue.Kind.class);
        for (var k : CatalogIssue.Kind.values()) {
            bucketed.put(k, new java.util.ArrayList<>());
        }
        for (CatalogIssue is : issues) bucketed.get(is.kind()).add(is);

        Comparator<CatalogIssue> ordering = Comparator
                .comparing((CatalogIssue x) -> x.row().sourceFile())
                .thenComparingInt(x -> x.row().sourceLine());
        for (var k : CatalogIssue.Kind.values()) bucketed.get(k).sort(ordering);

        StringBuilder sb = new StringBuilder();
        sb.append("# Catalog issues — auto-generated\n\n");
        sb.append("Rows from `docs/catalog/batch-*.md` that the catalog iterator\n");
        sb.append("could not process for reasons indicating the **row itself** is\n");
        sb.append("malformed or under-specified. (Rows merely blocked on missing\n");
        sb.append("components live in the histogram of the iterator's run report,\n");
        sb.append("not here — they typically resolve in later rounds.)\n\n");
        sb.append("This file is overwritten by every `CatalogIteratorMain` run.\n");
        sb.append("Edit the source `batch-*.md` rows; the next run will refresh\n");
        sb.append("this report.\n\n");
        sb.append("**Total issues:** ").append(issues.size()).append("\n\n");

        for (Map.Entry<CatalogIssue.Kind, List<CatalogIssue>> e : bucketed.entrySet()) {
            List<CatalogIssue> rows = e.getValue();
            sb.append("## ").append(e.getKey().sectionTitle)
                    .append(" (").append(rows.size()).append(")\n\n");
            if (rows.isEmpty()) {
                sb.append("_None._\n\n");
                continue;
            }
            sb.append("| Source | Line | Glyph | Pkg | Layout | Components | Etym | Suffix | Detail |\n");
            sb.append("|--------|------|-------|-----|--------|------------|------|--------|--------|\n");
            for (CatalogIssue is : rows) {
                CatalogRow r = is.row();
                sb.append("| ").append(r.sourceFile())
                        .append(" | ").append(r.sourceLine())
                        .append(" | ").append(r.glyph())
                        .append(" | ").append(r.pkg())
                        .append(" | ").append(r.layout())
                        .append(" | ").append(String.join("+", r.components()))
                        .append(" | ").append(r.etym())
                        .append(" | ").append(r.suffix())
                        .append(" | ").append(is.detail())
                        .append(" |\n");
            }
            sb.append('\n');
        }

        Files.createDirectories(outFile.getParent());
        Files.writeString(outFile, sb.toString(), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
