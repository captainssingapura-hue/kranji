package kranji.util.existence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Per-pool existence-check results for a loaded JSON feed.
 *
 * @param singularZi    status for each JSON {@code singularZi} entry
 * @param singularParts status for each JSON {@code singularParts} entry
 * @param composedZi    status for each JSON {@code composedZi} entry
 */
public record ExistenceReport(
        List<EntryStatus> singularZi,
        List<EntryStatus> singularParts,
        List<EntryStatus> composedZi
) {

    public ExistenceReport {
        singularZi    = List.copyOf(singularZi);
        singularParts = List.copyOf(singularParts);
        composedZi    = List.copyOf(composedZi);
    }

    // ── Aggregates ───────────────────────────────────────────────────

    /** Total number of entries across all three pools. */
    public int total() {
        return singularZi.size() + singularParts.size() + composedZi.size();
    }

    /** Number of entries whose glyph was found as a strong-typed record. */
    public int existing() {
        return (int) allEntries().filter(EntryStatus::exists).count();
    }

    /** Number of entries with no strong-typed record yet. */
    public int missing() {
        return total() - existing();
    }

    /** True iff every entry has a strong-typed implementation. */
    public boolean isComplete() {
        return missing() == 0;
    }

    /** Flat list of entries that need to be added to the strong-typed code. */
    public List<EntryStatus> missingEntries() {
        return allEntries().filter(s -> !s.exists()).toList();
    }

    /** Stream over every entry across pools, in pool-then-input order. */
    public Stream<EntryStatus> allEntries() {
        List<EntryStatus> merged = new ArrayList<>(total());
        merged.addAll(singularZi);
        merged.addAll(singularParts);
        merged.addAll(composedZi);
        return merged.stream();
    }

    // ── Human-readable formatting ───────────────────────────────────

    /**
     * Render the report as a plain-text block suitable for console output
     * or inclusion in a task description.
     */
    public String format() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Strong-Typed Existence Check ===\n");
        formatPool(sb, "Singular Zi",    singularZi);
        formatPool(sb, "Singular Parts", singularParts);
        formatPool(sb, "Composed Zi",    composedZi);
        sb.append("\nTotals: ")
          .append(total()).append(" entries, ")
          .append(existing()).append(" existing, ")
          .append(missing()).append(" missing")
          .append(isComplete() ? "  (complete)" : "  (incomplete)")
          .append('\n');
        return sb.toString();
    }

    private static void formatPool(StringBuilder sb, String label, List<EntryStatus> entries) {
        if (entries.isEmpty()) return;
        sb.append('\n').append(label).append(" (").append(entries.size()).append("):\n");
        for (EntryStatus e : entries) {
            sb.append("  ").append(e.exists() ? "[+]" : "[-]")
              .append(' ').append(e.glyph());
            if (e.exists()) {
                sb.append("  ").append(e.typedClassName());
            } else {
                sb.append("  MISSING");
            }
            sb.append('\n');
        }
    }
}
