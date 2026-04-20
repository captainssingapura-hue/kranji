package kranji.demos;

import kranji.pinyin.PinyinParser;
import kranji.pinyin.PinyinSyllable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Groups a list of CJK glyphs by pinyin using the Unicode Unihan
 * {@code kMandarin} reading and {@link PinyinParser}.
 *
 * <p>Usage:</p>
 * <pre>
 * mvn -pl kranji-core-demos exec:java \
 *     -Dexec.mainClass=kranji.demos.PinyinGrouper \
 *     -Dexec.args="glyphs.txt Unihan_Readings.txt outdir"
 * </pre>
 *
 * <ul>
 *   <li><b>glyphs.txt</b> — UTF-8, comma- or newline-separated CJK glyphs.</li>
 *   <li><b>Unihan_Readings.txt</b> — the
 *       {@code Unihan_Readings.txt} extract from
 *       <a href="https://www.unicode.org/Public/UCD/latest/ucd/Unihan.zip">Unihan.zip</a>.</li>
 *   <li><b>outdir</b> — output directory; one file per pinyin initial
 *       ({@code b.txt}, {@code ch.txt}, ..., {@code zero.txt}) plus a
 *       summary {@code _index.txt} and an {@code _unresolved.txt} list
 *       for any glyph missing from Unihan.</li>
 * </ul>
 *
 * <p>Each per-initial file groups glyphs by {@code <Final><Tone>} class
 * (the same PascalCase name used as the Java classname under
 * {@code kranji-common-depth<N>/<initial>/}), sorted alphabetically
 * within the initial and by class, then by glyph.</p>
 */
public final class PinyinGrouper {

    private PinyinGrouper() {}

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("usage: PinyinGrouper <glyphs.txt> <Unihan_Readings.txt> <outdir>");
            System.exit(2);
        }
        Path glyphsFile  = Path.of(args[0]);
        Path unihanFile  = Path.of(args[1]);
        Path outDir      = Path.of(args[2]);
        Files.createDirectories(outDir);

        List<String> glyphs    = readGlyphs(glyphsFile);
        Map<Integer, String> mandarin = readKMandarin(unihanFile);

        // initial -> class ("Ing2") -> list of "glyph\tpinyin"
        Map<String, Map<String, List<String>>> buckets = new TreeMap<>(initialOrder());
        List<String> unresolved = new ArrayList<>();

        for (String g : glyphs) {
            int cp = g.codePointAt(0);
            String kMand = mandarin.get(cp);
            if (kMand == null || kMand.isBlank()) {
                unresolved.add(g);
                continue;
            }
            // kMandarin may have multiple space-separated readings; pick the first
            String primary = kMand.trim().split("\\s+")[0];
            PinyinSyllable syl;
            try {
                syl = PinyinParser.parse(primary).toSyllable();
            } catch (Exception e) {
                unresolved.add(g + "\t" + primary + "\t(parse failed: " + e.getMessage() + ")");
                continue;
            }

            String initialKey = initialKey(syl);
            String className  = classNameOf(syl);
            buckets.computeIfAbsent(initialKey, k -> new TreeMap<>())
                   .computeIfAbsent(className, k -> new ArrayList<>())
                   .add(g + "\t" + primary);
        }

        // Write per-initial files
        StringBuilder index = new StringBuilder();
        index.append("=== Pinyin grouping index ===\n");
        index.append("Input:     ").append(glyphs.size()).append("\n");
        index.append("Unresolved: ").append(unresolved.size()).append("\n\n");
        index.append("initial  classes  glyphs\n");
        index.append("-------  -------  ------\n");

        int totalResolved = 0;
        for (var e : buckets.entrySet()) {
            String initial = e.getKey();
            var classMap   = e.getValue();
            int glyphCount = classMap.values().stream().mapToInt(List::size).sum();
            totalResolved += glyphCount;
            index.append(String.format("%-7s  %7d  %6d%n", initial, classMap.size(), glyphCount));

            StringBuilder out = new StringBuilder();
            out.append("# initial=").append(initial)
               .append("  classes=").append(classMap.size())
               .append("  glyphs=").append(glyphCount).append("\n");
            for (var cls : classMap.entrySet()) {
                out.append("\n## ").append(cls.getKey())
                   .append("  (").append(cls.getValue().size()).append(")\n");
                cls.getValue().stream()
                   .sorted()
                   .forEach(line -> out.append(line).append("\n"));
            }
            Files.writeString(outDir.resolve(initial + ".txt"), out.toString(),
                    StandardCharsets.UTF_8);
        }
        index.append("-------  -------  ------\n");
        index.append(String.format("%-7s  %7s  %6d%n", "TOTAL", "", totalResolved));

        Files.writeString(outDir.resolve("_index.txt"), index.toString(),
                StandardCharsets.UTF_8);

        if (!unresolved.isEmpty()) {
            Files.writeString(outDir.resolve("_unresolved.txt"),
                    String.join("\n", unresolved) + "\n",
                    StandardCharsets.UTF_8);
        }

        System.out.println("Resolved: " + totalResolved);
        System.out.println("Unresolved: " + unresolved.size());
        System.out.println("Wrote " + outDir.toAbsolutePath());
    }

    // ── Input parsing ────────────────────────────────────────────────────

    private static List<String> readGlyphs(Path file) throws IOException {
        String raw = Files.readString(file, StandardCharsets.UTF_8);
        List<String> out = new ArrayList<>();
        for (String token : raw.split("[,\\s]+")) {
            if (token.isEmpty()) continue;
            // Each token should be a single code point (or surrogate pair)
            int cp = token.codePointAt(0);
            int charCount = Character.charCount(cp);
            if (token.length() == charCount) {
                out.add(token);
            } else {
                // If multiple glyphs got concatenated (no separator), split them.
                for (int i = 0; i < token.length(); ) {
                    int c = token.codePointAt(i);
                    out.add(new String(Character.toChars(c)));
                    i += Character.charCount(c);
                }
            }
        }
        return out;
    }

    /** Parse Unihan_Readings.txt, keeping only kMandarin entries. */
    private static Map<Integer, String> readKMandarin(Path file) throws IOException {
        Map<Integer, String> map = new HashMap<>();
        try (var lines = Files.lines(file, StandardCharsets.UTF_8)) {
            lines.forEach(line -> {
                if (line.isEmpty() || line.startsWith("#")) return;
                String[] parts = line.split("\t");
                if (parts.length < 3) return;
                if (!"kMandarin".equals(parts[1])) return;
                if (!parts[0].startsWith("U+")) return;
                try {
                    int cp = Integer.parseInt(parts[0].substring(2), 16);
                    map.put(cp, parts[2]);
                } catch (NumberFormatException ignored) {
                }
            });
        }
        return map;
    }

    // ── Pinyin → filename keys ───────────────────────────────────────────

    private static String initialKey(PinyinSyllable syl) {
        String s = syl.initial().pinyin();
        return s.isEmpty() ? "zero" : s.toLowerCase();
    }

    /** PascalCase(&lt;final&gt;)&lt;tone&gt;. "ü" → "V". */
    private static String classNameOf(PinyinSyllable syl) {
        String spelling = syl.fin().spelling();
        if (spelling == null || spelling.isEmpty()) {
            spelling = "i"; // syllabic consonant (zi, ci, si, zhi, chi, shi, ri)
        }
        String normalized = spelling.replace('ü', 'v').toLowerCase();
        String pascal = Character.toUpperCase(normalized.charAt(0))
                + normalized.substring(1);
        return pascal + syl.tone().number();
    }

    private static Comparator<String> initialOrder() {
        // b, c, ch, d, ..., z, zh, zero — plain alphabetical with "zero" last
        return (a, b) -> {
            if (a.equals("zero") && !b.equals("zero")) return 1;
            if (b.equals("zero") && !a.equals("zero")) return -1;
            return a.compareTo(b);
        };
    }
}
