package kranji.reading.workbench.relation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * One relation on the wire: its rows, and the JSON a grid reads them from.
 *
 * <p>Moved here from the studio's gloss workbench so a second family of
 * relations could use the same shape without reaching into the studio. The
 * shape did not change: a row carries its own composite key, its parent's,
 * and a label, and a module carries the relation's name, its upstream, its
 * columns and its rows. Data only — no behaviour crosses the wire, which is
 * what lets it be fetched, cached and read by a person.</p>
 */
public final class Relation {

    private Relation() {}

    /**
     * One row, ready to be selected against.
     *
     * <p>Two keys, and the second is what makes a workbench out of a few
     * tables. {@link #pk} is this row's own identity — the composite key
     * written out, never a position. {@link #up} is its <b>parent's</b> pk in
     * the relation upstream of it, which is the entire mechanism behind the
     * cascade: a downstream widget filters on {@code up ∈ selection} and needs
     * to know nothing else about what happened above it.</p>
     *
     * <p>A root relation has no parent and carries {@code ""}. {@link #refs}
     * are keys in another relation this row points at sideways; most rows
     * point nowhere.</p>
     */
    public record Row(String pk, String up, String label, List<Object> values,
                      List<String> refs) {

        public Row {
            refs = List.copyOf(refs);
        }

        public Row(String pk, String up, String label, List<Object> values) {
            this(pk, up, label, values, List.of());
        }
    }

    // ── Scoping ────────────────────────────────────────────────────────

    /** Rows whose parent is one of these keys. An empty selection shows nothing. */
    public static List<Row> under(List<Row> rows, List<String> parentPks) {
        var out = new ArrayList<Row>();
        for (Row row : rows) if (parentPks.contains(row.up())) out.add(row);
        return List.copyOf(out);
    }

    /**
     * Rows whose own pk is one of these, <b>in the order asked for</b>.
     *
     * <p>Used when arriving by ref rather than by parent, and the order is the
     * point: a citing row states its refs in the sequence its author chose, and
     * iterating the rows instead of the keys would silently reorder the answer
     * to match a list the caller never mentioned.</p>
     */
    public static List<Row> withPks(List<Row> rows, List<String> pks) {
        var byPk = new LinkedHashMap<String, Row>();
        for (Row row : rows) byPk.put(row.pk(), row);
        var out = new ArrayList<Row>();
        for (String pk : pks) {
            Row row = byPk.get(pk);
            if (row != null) out.add(row);
        }
        return List.copyOf(out);
    }

    /**
     * The module text for a relation, optionally narrowed to a parent selection.
     *
     * <p>{@code parents} is one key per repeated {@code parent} parameter. An
     * empty list means no {@code parent} was given at all — "everything",
     * which is what a root relation wants. A list holding only empty strings
     * is a bare {@code parent=}: "nothing is selected upstream", which yields
     * no rows. The two must not collapse — a downstream grid showing the whole
     * relation because its parent selection was cleared is a grid lying about
     * what it is scoped to.</p>
     *
     * <p>{@code from}, when given, names a relation whose <b>refs</b> the keys
     * are, rather than the relation directly above — the sideways edge.</p>
     */
    public static String moduleFor(RelationSet set, String name, List<String> parents,
                                   String from) {
        List<String> columns = set.columnsOf(name == null ? "" : name);
        if (columns.isEmpty()) return problem(name);

        List<Row> rows = set.rowsOf(name);
        if (parents != null && !parents.isEmpty()) {
            List<String> keys = set.scopeKeysFor(name,
                    parents.stream().filter(k -> !k.isEmpty()).toList());
            rows = (from == null || from.isEmpty())
                    ? under(rows, keys)
                    : withPks(rows, set.refsFrom(from, keys));
        }
        return module(set, name, columns, rows);
    }

    // ── The wire ───────────────────────────────────────────────────────

    /**
     * Writes one relation.
     *
     * <p>Each row carries {@code pk} and {@code up}; the module carries the
     * relation's upstream, the relation whose refs land on it, and a note
     * when the set has one to say above the rows.</p>
     */
    public static String module(RelationSet set, String name, List<String> columns,
                                List<Row> rows) {
        var js = new StringBuilder();
        js.append("{\n");
        js.append("  \"relation\": ").append(quote(name)).append(",\n");
        String up = set.upstreamOf(name);
        String refSource = set.refSourceOf(name);
        String note = set.noteOf(name);
        js.append("  \"upstream\": ").append(up == null ? "null" : quote(up)).append(",\n");
        js.append("  \"refSource\": ")
          .append(refSource == null ? "null" : quote(refSource)).append(",\n");
        js.append("  \"note\": ").append(note == null ? "null" : quote(note)).append(",\n");
        js.append("  \"columns\": [");
        for (int i = 0; i < columns.size(); i++) {
            js.append(i > 0 ? ", " : "").append(quote(columns.get(i)));
        }
        js.append("],\n");
        js.append("  \"rows\": [\n");
        for (int r = 0; r < rows.size(); r++) {
            Row row = rows.get(r);
            js.append("    { \"pk\": ").append(quote(row.pk()))
              .append(", \"up\": ").append(quote(row.up()))
              .append(", \"label\": ").append(quote(row.label()));
            for (int c = 0; c < columns.size(); c++) {
                js.append(", ").append(quote(columns.get(c))).append(": ")
                  .append(value(row.values().get(c)));
            }
            js.append(" }").append(r + 1 < rows.size() ? "," : "").append("\n");
        }
        js.append("  ]\n}\n");
        return js.toString();
    }

    public static String problem(String name) {
        return "{\n"
             + "  \"relation\": " + quote(String.valueOf(name)) + ",\n"
             + "  \"columns\": [],\n"
             + "  \"rows\": [],\n"
             + "  \"problem\": " + quote("no relation named '" + name + "'") + "\n"
             + "}\n";
    }

    private static String value(Object v) {
        return v instanceof Integer || v instanceof Long ? String.valueOf(v) : quote(String.valueOf(v));
    }

    /** JSON string escaping, enough for the values this data can hold. */
    public static String quote(String s) {
        var out = new StringBuilder("\"");
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"'  -> out.append("\\\"");
                case '\\' -> out.append("\\\\");
                case '\n' -> out.append("\\n");
                case '\r' -> out.append("\\r");
                case '\t' -> out.append("\\t");
                default   -> {
                    if (c < 0x20) out.append(String.format("\\u%04x", (int) c));
                    else out.append(c);
                }
            }
        }
        return out.append('"').toString();
    }
}
