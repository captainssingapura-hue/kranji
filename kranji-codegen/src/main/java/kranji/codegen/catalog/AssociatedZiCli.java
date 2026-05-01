package kranji.codegen.catalog;

import kranji.common.perclass.AllPerclassRecords;
import kranji.common.perclass.promoted.AllPerclassRecordsPromoted;
import kranji.zi.BlockStructure;
import kranji.zi.ComposedBlock;
import kranji.zi.ComposedZiT;
import kranji.zi.Zi;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * CLI to validate the "associated Zi" reverse index — i.e. for a given
 * glyph, which Zi use it as an <em>immediate</em> component slot.
 *
 * <p>Built before integrating into the UI to make sure the matching
 * strategy is correct without any JavaFX in the loop. The UI tried
 * instance-equality on slot values and got 0 hits because the same
 * conceptual radical is represented by different classes in different
 * places (e.g. typed slot singletons vs the BasicSet's view).</p>
 *
 * <p>Strategy here: <b>glyph-string key</b>. For each composed Zi's
 * slot, register {@code slot.glyph() → parentZi}. Lookup by glyph
 * string. Side-steps multi-class-per-glyph entirely.</p>
 *
 * <h3>Usage</h3>
 * <pre>
 *   mvn -pl kranji-codegen exec:java \
 *     -Dexec.mainClass=kranji.codegen.catalog.AssociatedZiCli \
 *     -Dexec.args="钅 贝 ⺌"     # query specific glyphs
 *
 *   mvn -pl kranji-codegen exec:java \
 *     -Dexec.mainClass=kranji.codegen.catalog.AssociatedZiCli
 *     # no args → prints overall stats + top-20 most-reused slots
 * </pre>
 */
public final class AssociatedZiCli {

    public static void main(String[] args) throws IOException {
        // Union of hand-authored + promoted composed Zi. (Staging is
        // volatile and not on the codegen classpath; the index doesn't
        // need it for validation.)
        // Filter out synthetic inner records (empty character()) — they
        // are auto-generated structural helpers, not user-facing Zi,
        // and their empty glyph crashes the codepoint-based comparator.
        List<ComposedZiT> corpus = new ArrayList<>(
                AllPerclassRecords.ALL.size() + AllPerclassRecordsPromoted.ALL.size());
        for (ComposedZiT z : AllPerclassRecords.ALL) {
            if (z.character() != null && !z.character().isEmpty()) corpus.add(z);
        }
        for (ComposedZiT z : AllPerclassRecordsPromoted.ALL) {
            if (z.character() != null && !z.character().isEmpty()) corpus.add(z);
        }

        Map<String, List<ComposedZiT>> index = buildIndex(corpus);

        // Write the report to a UTF-8 file so CJK glyphs render
        // correctly (Windows console mangles them to "?").
        Path outFile = Path.of("kranji-codegen", "target", "associated-zi-report.txt");
        Files.createDirectories(outFile.getParent());
        try (PrintStream rpt = new PrintStream(Files.newOutputStream(outFile), true,
                StandardCharsets.UTF_8)) {
            writeReport(rpt, corpus, index, args);
        }
        System.out.println("Wrote report: " + outFile);
        System.out.println("(Console may show '?' for CJK chars — open the file in a UTF-8 viewer.)");
    }

    private static void writeReport(PrintStream out, List<ComposedZiT> corpus,
                                    Map<String, List<ComposedZiT>> index, String[] args) {
        out.println("=== Immediate-users index ===");
        out.println("Composed Zi indexed: " + corpus.size());
        out.println("Unique slot-glyph keys: " + index.size());
        long totalRefs = index.values().stream().mapToLong(List::size).sum();
        out.println("Total slot references: " + totalRefs);
        out.println();

        // Top-20 most-reused slots — high-level sanity check.
        out.println("=== Top 20 most-used slots ===");
        index.entrySet().stream()
                .sorted(Comparator.<Map.Entry<String, List<ComposedZiT>>>comparingInt(
                        e -> e.getValue().size()).reversed())
                .limit(20)
                .forEach(e -> out.printf(
                        "  %s  (%d uses)  e.g. %s%n",
                        e.getKey(), e.getValue().size(),
                        sample(e.getValue(), 8)));
        out.println();

        // Built-in sanity queries — covers a representative mix of
        // glyphs (radical-form, full-Zi, surrogate-pair, etc.) so the
        // logic stays validated without needing CJK-clean shell args
        // (Windows mangles them to '?' before they reach main()).
        String[] sanity = {
                "钅", "贝", "木", "扌", "氵", "口", "亻",
                "路", "马", "聚", "心", "宀", "辶",
                "⺌", "⺳", "𠆢"
        };
        out.println("=== Sanity queries ===");
        for (String q : sanity) {
            queryAndPrint(out, index, q);
        }

        // Optional: also process any args (works on platforms with
        // proper UTF-8 stdin → exec.args wiring).
        if (args.length > 0) {
            out.println("=== Args queries ===");
            for (String q : args) {
                queryAndPrint(out, index, q);
            }
        }
    }

    /**
     * Build the reverse index. Key = the {@code glyph()} string of an
     * immediate slot. Value = parent Zi instances that use that slot.
     * Glyph-based keys avoid the instance-equality / multi-class
     * pitfalls — every visual radical is a stable lookup key.
     *
     * <p><b>Skip empty-glyph slots</b>: anonymous synthetic inners
     * ({@code ComposedPart} or auto-generated {@code _Inner1} records)
     * have no meaningful name to look them up by. Indexing them under
     * "" would conflate every synthetic, which is useless. We still
     * index real glyph slots, including the synthetic's own children
     * recursively if the user wants them — but here we only do the
     * <em>immediate</em> level per the original spec.</p>
     */
    static Map<String, List<ComposedZiT>> buildIndex(List<ComposedZiT> corpus) {
        // TreeMap → deterministic ordering for diagnostics.
        Map<String, List<ComposedZiT>> idx = new TreeMap<>();
        for (ComposedZiT z : corpus) {
            if (!(z.structure() instanceof ComposedBlock cb)) continue;
            for (BlockStructure slot : cb.composition().components()) {
                String g = slot.glyph();
                if (g == null || g.isEmpty()) continue;   // skip anonymous synthetics
                idx.computeIfAbsent(g, k -> new ArrayList<>()).add(z);
            }
        }
        return idx;
    }

    private static void queryAndPrint(PrintStream out, Map<String, List<ComposedZiT>> index, String glyph) {
        List<ComposedZiT> users = index.getOrDefault(glyph, List.of());
        out.println("--- " + glyph + " ---");
        if (users.isEmpty()) {
            out.println("  (no immediate users found)");
            out.println();
            return;
        }
        out.println("  " + users.size() + " immediate users:");
        var sorted = new ArrayList<>(users);
        sorted.sort(Comparator.comparingInt((Zi z) -> z.strokes())
                .thenComparing(z -> z.character().codePointAt(0)));
        StringBuilder line = new StringBuilder("    ");
        int col = 0;
        for (Zi u : sorted) {
            String s = u.character() + " ";
            line.append(s);
            col += s.length();
            if (col > 60) {
                out.println(line);
                line.setLength(0);
                line.append("    ");
                col = 0;
            }
        }
        if (col > 0) out.println(line);
        out.println();
    }

    private static String sample(List<ComposedZiT> users, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(n, users.size()); i++) {
            if (i > 0) sb.append(' ');
            sb.append(users.get(i).character());
        }
        if (users.size() > n) sb.append(" …");
        return sb.toString();
    }

    private AssociatedZiCli() {}
}
