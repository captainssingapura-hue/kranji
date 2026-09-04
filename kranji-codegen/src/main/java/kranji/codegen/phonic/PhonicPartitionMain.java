package kranji.codegen.phonic;

import kranji.pinyin.Initial;
import kranji.pinyin.PinyinSyllable;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Splits {@code Unihan_Readings.txt} into one small partition per pinyin
 * initial, keeping only the modern standard set and only the fields Kranji
 * actually reads.
 *
 * <p>The source file is 291,259 lines covering 44,348 characters across
 * seventeen fields, almost all of it irrelevant here. The standard set is
 * 8,105 characters and three fields, which partitions into twenty-two files
 * of a few hundred rows each — small enough to commit, to read at runtime
 * without a cache, and to open in an editor when something looks wrong.</p>
 *
 * <p>A character lands in the partition of its <em>principal</em> reading, so
 * every character appears exactly once across the twenty-two files. That is
 * deliberately not the same rule the DSL partitions use: those file a
 * character under every initial it is read with, because they describe
 * appearances. This describes characters.</p>
 *
 * <p>Run it when the Unihan drop changes. Output is deterministic, so an
 * unchanged input produces an unchanged diff.</p>
 */
public final class PhonicPartitionMain {

    /** Unihan fields this reads. Everything else in the source is dropped. */
    private static final String STANDARD = "kTGHZ2013";
    private static final String MANDARIN = "kMandarin";
    private static final String FREQUENCY = "kHanyuPinlu";

    private PhonicPartitionMain() {}

    public static void main(String[] args) throws IOException {
        Path source = Path.of(args.length > 0 ? args[0]
                : "input/_2500/Unihan_Readings.txt");
        Path outDir = Path.of(args.length > 1 ? args[1]
                : "kranji-core/src/main/resources/kranji/phonic");

        Map<Integer, Map<String, String>> db = read(source);
        String version = versionOf(source);

        Map<Initial, List<Row>> byInitial = new TreeMap<>();
        int skipped = 0;
        for (var entry : db.entrySet()) {
            Map<String, String> fields = entry.getValue();
            if (!fields.containsKey(STANDARD)) continue;

            Row row = rowFor(entry.getKey(), fields);
            if (row == null) { skipped++; continue; }
            byInitial.computeIfAbsent(row.initial(), k -> new ArrayList<>()).add(row);
        }

        Files.createDirectories(outDir);
        int written = 0;
        for (Initial initial : Initial.values()) {
            List<Row> rows = byInitial.getOrDefault(initial, List.of());
            rows.sort((a, b) -> Integer.compare(a.codePoint(), b.codePoint()));
            Files.writeString(outDir.resolve(segment(initial) + ".tsv"),
                    render(initial, version, rows), StandardCharsets.UTF_8);
            written += rows.size();
        }

        System.out.printf("wrote %d characters across %d partitions into %s%n",
                written, Initial.values().length, outDir);
        System.out.printf("skipped %d with no parseable principal reading%n", skipped);
    }

    // ── Rows ───────────────────────────────────────────────────────────

    /**
     * One character: its standard readings, the reading Unihan calls
     * principal, and the frequency evidence if there is any.
     */
    record Row(int codePoint, String glyph, List<String> readings,
               String mandarin, String frequency, Initial initial) {}

    /**
     * Builds a row, or {@code null} when nothing about the character can be
     * parsed. A character with some unparseable readings keeps the rest; one
     * with no parseable reading at all has no place in a phonic partition.
     */
    private static Row rowFor(int codePoint, Map<String, String> fields) {
        // Every reading is written out, parseable or not - a reading this
        // build cannot model is still data, and the reader reports it rather
        // than the partitioner hiding it. Parsing is only used here to decide
        // which partition the character belongs in.
        // Corrected first, so a reading the source spelled in a notation the
        // standard replaced is written out in the form everything downstream
        // reads. Two characters; SourceCorrections says which and why.
        List<String> readings = SourceCorrections.apply(codePoint, values(fields.get(STANDARD)));
        List<String> parseable = parseable(readings);
        if (parseable.isEmpty()) return null;

        String mandarin = firstToken(fields.get(MANDARIN));
        // Unihan's own pick decides the partition when it is one of the
        // standard readings. When it is not - 26 characters where the two
        // authorities disagree outright - the standard's first reading wins,
        // because pc1 makes kTGHZ2013 the authority on which readings exist.
        String home = parseable.contains(mandarin) ? mandarin : parseable.get(0);

        return new Row(codePoint, new String(Character.toChars(codePoint)),
                readings, mandarin == null ? "" : mandarin,
                fields.getOrDefault(FREQUENCY, ""),
                PinyinSyllable.parse(home).initial());
    }

    private static List<String> parseable(List<String> raw) {
        var out = new ArrayList<String>();
        for (String r : raw) {
            try { PinyinSyllable.parse(r); out.add(r); }
            catch (RuntimeException ignored) { /* the reader reports these; here they only fail to place */ }
        }
        return out;
    }

    // ── Source parsing ─────────────────────────────────────────────────

    private static Map<Integer, Map<String, String>> read(Path source) throws IOException {
        Map<Integer, Map<String, String>> db = new HashMap<>(50_000);
        try (BufferedReader br = Files.newBufferedReader(source, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty() || line.charAt(0) == '#') continue;
                String[] parts = line.split("\t", 3);
                if (parts.length < 3) continue;
                String field = parts[1];
                if (!field.equals(STANDARD) && !field.equals(MANDARIN)
                        && !field.equals(FREQUENCY)) continue;
                int cp = Integer.parseInt(parts[0].substring(2), 16);
                db.computeIfAbsent(cp, k -> new LinkedHashMap<>()).put(field, parts[2]);
            }
        }
        return db;
    }

    /**
     * Readings out of a Unihan value. {@code kTGHZ2013} writes
     * {@code "132.140:hǎo 133.010:hào"} - a dictionary page and entry number
     * before each reading, which is collation order and carries no meaning
     * here, so it is dropped.
     */
    private static List<String> values(String raw) {
        Set<String> out = new LinkedHashSet<>();
        if (raw == null) return new ArrayList<>(out);
        for (String token : raw.trim().split("\\s+")) {
            String s = token.contains(":")
                    ? token.substring(token.lastIndexOf(':') + 1) : token;
            for (String r : s.replace(',', ' ').split("\\s+")) {
                if (!r.isEmpty()) out.add(r);
            }
        }
        return new ArrayList<>(out);
    }

    private static String firstToken(String raw) {
        if (raw == null || raw.isBlank()) return null;
        return raw.trim().split("\\s+")[0];
    }

    private static String versionOf(Path source) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(source, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null && line.startsWith("#")) {
                if (line.contains("Unicode Version")) {
                    return line.substring(line.indexOf("Version") + 8).trim();
                }
            }
        }
        return "unknown";
    }

    // ── Output ─────────────────────────────────────────────────────────

    private static String render(Initial initial, String version, List<Row> rows) {
        var sb = new StringBuilder();
        sb.append("# Kranji phonic partition - initial: ").append(segment(initial)).append('\n');
        sb.append("# Source: Unihan_Readings.txt, Unicode ").append(version)
          .append(" (c) Unicode, Inc. - fields kTGHZ2013, kMandarin, kHanyuPinlu\n");
        sb.append("# Universe: the 8,105 characters of the Table of General Standard\n");
        sb.append("# Chinese Characters. A character is filed under its principal reading,\n");
        sb.append("# so it appears in exactly one partition.\n");
        sb.append("# Columns: codepoint\tglyph\treadings\tmandarin\tfrequency\n");
        sb.append("# Generated by PhonicPartitionMain - do not edit by hand.\n");
        sb.append("# Characters: ").append(rows.size()).append('\n');
        for (Row r : rows) {
            sb.append("U+").append(String.format("%04X", r.codePoint())).append('\t')
              .append(r.glyph()).append('\t')
              .append(String.join(",", r.readings())).append('\t')
              .append(r.mandarin()).append('\t')
              .append(r.frequency()).append('\n');
        }
        return sb.toString();
    }

    /** Matches the addressing the phonic projection already uses. */
    static String segment(Initial initial) {
        return initial == Initial.ZERO ? "zero" : initial.pinyin();
    }
}
