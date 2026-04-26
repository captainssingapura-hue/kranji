package kranji.codegen.catalog;

import kranji.codegen.perclass.PerclassSingularIndex;
import kranji.common.perclass.AllPerclassRecords;
import kranji.common.perclass.promoted.AllPerclassRecordsPromoted;
import kranji.zi.ComposedZiT;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Prints exact coverage of the 2500 corpus by intersecting all_glyphs.txt with the GlyphIndex. */
public final class CoverageReportMain {

    public static void main(String[] args) throws IOException {
        Path corpusFile = Path.of("input", "_2500", "all_glyphs.txt");
        Path missingFile = Path.of("input", "_2500", "missing.txt");
        Path outFile = Path.of("kranji-codegen", "target", "coverage-report.txt");

        // Build GlyphIndex from current state.
        PerclassSingularIndex singulars = PerclassSingularIndex.build();
        List<ComposedZiT> composed = new ArrayList<>();
        composed.addAll(AllPerclassRecords.ALL);
        composed.addAll(AllPerclassRecordsPromoted.ALL);
        GlyphIndex index = GlyphIndex.build(singulars, composed,
                AllPerclassRecordsPromoted.SINGULARS);

        Set<String> corpus = parseCommaList(corpusFile);
        Set<String> originallyMissing = parseCommaList(missingFile);

        Set<String> corpusCovered = new LinkedHashSet<>();
        Set<String> corpusUncovered = new LinkedHashSet<>();
        for (String g : corpus) {
            if (index.contains(g)) corpusCovered.add(g);
            else corpusUncovered.add(g);
        }
        Set<String> missingCovered = new LinkedHashSet<>();
        Set<String> missingStillUncovered = new LinkedHashSet<>();
        for (String g : originallyMissing) {
            if (index.contains(g)) missingCovered.add(g);
            else missingStillUncovered.add(g);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== 2500 corpus coverage ===\n");
        sb.append("Corpus size:                  ").append(corpus.size()).append('\n');
        sb.append("Originally missing:           ").append(originallyMissing.size()).append('\n');
        sb.append("Originally covered:           ").append(corpus.size() - originallyMissing.size()).append('\n');
        sb.append('\n');
        sb.append("Now covered (corpus ∩ index): ").append(corpusCovered.size())
                .append("  (").append(100 * corpusCovered.size() / corpus.size()).append("%)\n");
        sb.append("Still uncovered:              ").append(corpusUncovered.size())
                .append("  (").append(100 * corpusUncovered.size() / corpus.size()).append("%)\n");
        sb.append('\n');
        sb.append("=== Of originally missing 1621 ===\n");
        sb.append("Now covered:                  ").append(missingCovered.size())
                .append("  (").append(100 * missingCovered.size() / originallyMissing.size()).append("%)\n");
        sb.append("Still uncovered:              ").append(missingStillUncovered.size())
                .append("  (").append(100 * missingStillUncovered.size() / originallyMissing.size()).append("%)\n");
        sb.append('\n');
        sb.append("=== GlyphIndex composition ===\n");
        sb.append("Total unique glyphs in index: ").append(index.size()).append('\n');
        sb.append("  Hand-authored singulars:    ").append(singulars.size()).append('\n');
        sb.append("  Hand-authored composed:     ").append(AllPerclassRecords.ALL.size()).append('\n');
        sb.append("  Promoted composed:          ").append(AllPerclassRecordsPromoted.ALL.size()).append('\n');
        sb.append("  Promoted singulars:         ").append(AllPerclassRecordsPromoted.SINGULARS.size()).append('\n');
        sb.append('\n');
        sb.append("=== Still-uncovered glyphs (").append(corpusUncovered.size()).append(") ===\n");
        int col = 0;
        for (String g : corpusUncovered) {
            sb.append(g);
            col++;
            if (col % 50 == 0) sb.append('\n');
            else sb.append(' ');
        }
        sb.append('\n');

        Files.createDirectories(outFile.getParent());
        Files.writeString(outFile, sb.toString(), StandardCharsets.UTF_8);
        System.out.println("Wrote coverage report: " + outFile);
        // Also print top-level summary to console (CJK-safe portion).
        for (String line : sb.toString().split("\n")) {
            if (line.matches(".*[A-Za-z=:].*") && !line.contains("===")) {
                System.out.println(line);
            } else if (line.startsWith("===")) {
                System.out.println(line);
            }
        }
    }

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
