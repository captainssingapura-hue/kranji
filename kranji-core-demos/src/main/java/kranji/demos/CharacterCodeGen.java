package kranji.demos;

import kranji.demos.PinyinParser.Decomposition;
import kranji.pinyin.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/**
 * Generates toned-pinyin Java source files for the {@code kranji.characters} package
 * from a TSV data file.
 *
 * <p>Input: a TSV file with columns:</p>
 * <pre>
 * char  pinyin  strokes  radical  composition  components  etymology  semantic  phonetic  meaning
 * </pre>
 *
 * <p>Output: one {@code .java} file per distinct toned pinyin syllable, using the
 * fluent DSL ({@link kranji.characters.EntryBuilder}, {@link kranji.characters.Comp}).</p>
 *
 * <p>Run: {@code mvn -pl kranji-core-demos exec:java -Dexec.mainClass=kranji.demos.CharacterCodeGen}</p>
 */
public final class CharacterCodeGen {

    private CharacterCodeGen() {}

    private static final Path TSV_PATH = Path.of("kranji-characters/src/main/resources/top1000.tsv");
    private static final Path OUTPUT_DIR = Path.of("kranji-characters/src/main/java/kranji/characters");

    // Composition types → DSL method names (on EntryBuilder)
    private static final Map<String, String> COMP_BUILDER_METHOD = Map.ofEntries(
            Map.entry("singular",                    "singular"),
            Map.entry("left_right",                  "leftRight"),
            Map.entry("top_bottom",                  "topBottom"),
            Map.entry("left_middle_right",           "leftMiddleRight"),
            Map.entry("top_middle_bottom",           "topMiddleBottom"),
            Map.entry("full_enclosure",              "fullEnclosure"),
            Map.entry("semi_enclosure_upper_left",   "semiEnclosureUL"),
            Map.entry("semi_enclosure_upper_right",  "semiEnclosureUR"),
            Map.entry("semi_enclosure_bottom_left",  "semiEnclosureBL"),
            Map.entry("semi_enclosure_top_three",    "semiEnclosureT3"),
            Map.entry("semi_enclosure_bottom_three", "semiEnclosureB3"),
            Map.entry("semi_enclosure_left_three",   "semiEnclosureL3"),
            Map.entry("mosaic",                      "mosaic")
    );

    // Etymology types → DSL terminal method call
    private static final Map<String, String> ETYM_METHOD = Map.of(
            "pictograph",         "pictograph",
            "simple_indicative",  "indicative",
            "compound_indicative","compoundIndicative",
            "phono_semantic",     "phonoSemantic",
            "derivative_cognate", "cognate",
            "phonetic_loan",      "loan"
    );

    // Known Part constants from kranji.component.Parts (glyph → constant name)
    private static final Map<String, String> KNOWN_PARTS = new LinkedHashMap<>();
    static {
        KNOWN_PARTS.put("亻", "DAN_REN_PANG");
        KNOWN_PARTS.put("氵", "SAN_DIAN_SHUI");
        KNOWN_PARTS.put("冫", "LIANG_DIAN_SHUI");
        KNOWN_PARTS.put("忄", "SHU_XIN_PANG");
        KNOWN_PARTS.put("扌", "TI_SHOU_PANG");
        KNOWN_PARTS.put("犭", "FAN_QUAN_PANG");
        KNOWN_PARTS.put("讠", "YAN_ZI_PANG");
        KNOWN_PARTS.put("钅", "JIN_ZI_PANG");
        KNOWN_PARTS.put("饣", "SHI_ZI_PANG");
        KNOWN_PARTS.put("纟", "JIAO_SI_PANG");
        KNOWN_PARTS.put("礻", "SHI_ZI_PANG_SPIRIT");
        KNOWN_PARTS.put("衤", "YI_ZI_PANG");
        KNOWN_PARTS.put("彳", "SHUANG_REN_PANG");
        // Note: 阝 is ambiguous (left vs right ear), default to left
        KNOWN_PARTS.put("刂", "LI_DAO_PANG");
        KNOWN_PARTS.put("攵", "FAN_WEN_PANG");
        KNOWN_PARTS.put("艹", "CAO_ZI_TOU");
        KNOWN_PARTS.put("宀", "BAO_GAI_TOU");
        KNOWN_PARTS.put("冖", "TU_BAO_GAI");
        KNOWN_PARTS.put("亠", "WEN_ZI_TOU");
        KNOWN_PARTS.put("⺮", "ZHU_ZI_TOU");
        KNOWN_PARTS.put("灬", "SI_DIAN_DI");
        KNOWN_PARTS.put("辶", "ZOU_ZHI_DI");
        KNOWN_PARTS.put("廴", "JIAN_ZHI_PANG");
        KNOWN_PARTS.put("囗", "GUO_ZI_KUANG");
        KNOWN_PARTS.put("疒", "BING_ZI_PANG");
        KNOWN_PARTS.put("勹", "BAO_ZI_TOU");
        KNOWN_PARTS.put("匚", "SAN_KUANG");
        KNOWN_PARTS.put("凵", "XIONG_ZI_KUANG");
        KNOWN_PARTS.put("冂", "TONG_ZI_KUANG");
    }

    // ── Data model ─────────────────────────────────────────────────────

    record CharData(
            String glyph, String pinyin, int strokes, int radical,
            String composition, String[] components,
            String etymology, String semantic, String phonetic, String meaning
    ) {
        /** Toned pinyin class name, e.g. "Qing1" — uses standard pinyin spelling (ASCII). */
        String className() {
            String base = pinyin.replaceAll("[0-9]+$", "")  // strip tone number
                    .replace("ü", "v").replace("ê", "e");   // ASCII-safe
            String tone = pinyin.replaceAll("^[^0-9]+", ""); // extract tone number
            // Capitalize first letter
            String cap = base.substring(0, 1).toUpperCase() + base.substring(1);
            return cap + tone;
        }

        /** Tone number */
        int toneNumber() {
            String tone = pinyin.replaceAll("^[^0-9]+", "");
            return tone.isEmpty() ? 5 : Integer.parseInt(tone);
        }
    }

    // ── Main ───────────────────────────────────────────────────────────

    public static void main(String[] args) throws Exception {
        // Read TSV
        List<CharData> chars = readTsv(TSV_PATH);
        System.out.printf("Read %d characters from %s%n", chars.size(), TSV_PATH);

        // Group by toned pinyin class
        Map<String, List<CharData>> groups = chars.stream()
                .collect(Collectors.groupingBy(CharData::className, TreeMap::new, Collectors.toList()));

        System.out.printf("Grouped into %d toned-pinyin classes%n", groups.size());

        // Collect class names that already have hand-written files (skip them)
        Set<String> existingClasses = findExistingClasses();
        System.out.printf("Found %d existing hand-written classes (will skip)%n", existingClasses.size());

        int generated = 0;
        int skipped = 0;
        for (var entry : groups.entrySet()) {
            String className = entry.getKey();
            if (existingClasses.contains(className)) {
                skipped++;
                continue;
            }
            writeClassFile(className, entry.getValue());
            generated++;
        }

        System.out.printf("Generated %d new class files, skipped %d existing%n", generated, skipped);

        // Generate/update Characters.java registry
        writeCharactersRegistry(groups.keySet());
        System.out.println("Updated Characters.java registry");
    }

    // ── TSV reading ────────────────────────────────────────────────────

    private static List<CharData> readTsv(Path path) throws IOException {
        List<CharData> result = new ArrayList<>();
        try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String header = reader.readLine(); // skip header
            String line;
            int lineNo = 1;
            while ((line = reader.readLine()) != null) {
                lineNo++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] cols = line.split("\t", -1);
                if (cols.length < 10) {
                    System.err.printf("WARNING: line %d has %d columns (expected 10), skipping: %s%n",
                            lineNo, cols.length, line);
                    continue;
                }
                try {
                    result.add(new CharData(
                            cols[0].trim(),                              // glyph
                            cols[1].trim(),                              // pinyin
                            Integer.parseInt(cols[2].trim()),            // strokes
                            Integer.parseInt(cols[3].trim()),            // radical
                            cols[4].trim(),                              // composition
                            cols[5].trim().isEmpty() ? new String[0]
                                    : cols[5].trim().split(","),         // components
                            cols[6].trim(),                              // etymology
                            cols[7].trim(),                              // semantic
                            cols[8].trim(),                              // phonetic
                            cols[9].trim()                               // meaning
                    ));
                } catch (Exception e) {
                    System.err.printf("WARNING: line %d parse error: %s — %s%n", lineNo, e.getMessage(), line);
                }
            }
        }
        return result;
    }

    // ── Class file generation ──────────────────────────────────────────

    private static Set<String> findExistingClasses() throws IOException {
        Set<String> names = new HashSet<>();
        try (var stream = Files.list(OUTPUT_DIR)) {
            stream.filter(p -> p.toString().endsWith(".java"))
                    .map(p -> p.getFileName().toString().replace(".java", ""))
                    .filter(n -> n.matches("[A-Z][a-z]+\\d"))  // matches toned pinyin pattern
                    .forEach(names::add);
        }
        return names;
    }

    private static void writeClassFile(String className, List<CharData> chars) throws IOException {
        var sb = new StringBuilder();
        sb.append("package kranji.characters;\n\n");
        sb.append("import kranji.entry.ChineseCharacterEntry;\n\n");
        sb.append("import java.util.List;\n\n");
        sb.append("import static kranji.characters.Comp.*;\n");
        sb.append("import static kranji.characters.EntryBuilder.entry;\n");
        sb.append("import static kranji.component.Parts.*;\n");
        sb.append("import static kranji.pinyin.Initial.*;\n");
        sb.append("import static kranji.pinyin.Head.*;\n");
        sb.append("import kranji.pinyin.Body;\n");
        sb.append("import kranji.pinyin.Tail;\n\n");

        // Extract pinyin info for Javadoc
        CharData first = chars.get(0);
        String basePinyin = first.pinyin().replaceAll("\\d+$", "");
        int tone = first.toneNumber();
        sb.append(String.format("/** Characters pronounced %s (tone %d). */%n", basePinyin, tone));
        sb.append(String.format("public final class %s {%n", className));
        sb.append(String.format("    private %s() {}%n", className));

        // Generate each entry (deduplicate constant names)
        List<String> constantNames = new ArrayList<>();
        Set<String> usedNames = new HashSet<>();
        for (CharData ch : chars) {
            String baseName = ch.glyph() + "_" + sanitizeMeaning(ch.meaning());
            String constName = baseName;
            int suffix = 2;
            while (usedNames.contains(constName)) {
                constName = baseName + "_" + suffix++;
            }
            usedNames.add(constName);
            constantNames.add(constName);

            sb.append("\n");
            sb.append(String.format("    /** %s (%s) — %s. */%n", ch.glyph(), ch.pinyin(), ch.meaning()));
            sb.append(String.format("    public static final ChineseCharacterEntry %s = entry(\"%s\")%n",
                    constName, ch.glyph()));

            // Pinyin
            Decomposition d = PinyinParser.parse(ch.pinyin());
            sb.append(String.format("            .py(%s, %s, %s, %s, T%d)",
                    initialCode(d.initial()),
                    headCode(d.head()),
                    "Body." + d.body().name(),
                    "Tail." + d.tail().name(),
                    d.tone().number()));

            // Strokes and radical
            sb.append(String.format(".strokes(%d).radical(%d)%n", ch.strokes(), ch.radical()));

            // Composition
            String compMethod = COMP_BUILDER_METHOD.get(ch.composition());
            if (compMethod == null) {
                System.err.printf("WARNING: unknown composition '%s' for %s, defaulting to singular%n",
                        ch.composition(), ch.glyph());
                compMethod = "singular";
            }
            sb.append("            .");
            sb.append(buildCompositionCall(compMethod, ch.components()));
            sb.append("\n");

            // Etymology (terminal)
            sb.append("            .");
            sb.append(buildEtymologyCall(ch));
            sb.append(";\n");
        }

        // ALL list
        sb.append("\n    public static final List<ChineseCharacterEntry> ALL = List.of(");
        sb.append(String.join(", ", constantNames));
        sb.append(");\n");

        sb.append("}\n");

        Path outFile = OUTPUT_DIR.resolve(className + ".java");
        Files.writeString(outFile, sb.toString(), StandardCharsets.UTF_8);
    }

    // ── Code generation helpers ────────────────────────────────────────

    private static String sanitizeMeaning(String meaning) {
        // Uppercase, replace non-identifier chars with underscore
        String upper = meaning.toUpperCase()
                .replaceAll("[^A-Z0-9_]", "_")
                .replaceAll("_+", "_")
                .replaceAll("^_|_$", "");
        if (upper.isEmpty()) upper = "CHAR";
        return upper;
    }

    private static String initialCode(Initial ini) {
        return ini == Initial.ZERO ? "ZERO" : ini.name();
    }

    private static String headCode(Head h) {
        return h.name(); // OPEN, I, U, V — all come from static import of Head.*
    }

    private static String componentExpr(String comp) {
        comp = comp.trim();
        String partConst = KNOWN_PARTS.get(comp);
        if (partConst != null) {
            return partConst;
        }
        // Default: zi("x")
        return "zi(\"" + comp + "\")";
    }

    private static String buildCompositionCall(String method, String[] components) {
        if (method.equals("singular")) {
            return "singular()";
        }
        if (method.equals("mosaic")) {
            String elem = components.length > 0 ? componentExpr(components[0]) : "zi(\"?\")";
            return "mosaic(" + elem + ")";
        }
        // All other compositions take component arguments
        String args = Arrays.stream(components)
                .map(CharacterCodeGen::componentExpr)
                .collect(Collectors.joining(", "));
        return method + "(" + args + ")";
    }

    private static String buildEtymologyCall(CharData ch) {
        String method = ETYM_METHOD.get(ch.etymology());
        if (method == null) {
            System.err.printf("WARNING: unknown etymology '%s' for %s, defaulting to pictograph%n",
                    ch.etymology(), ch.glyph());
            return "pictograph()";
        }
        return switch (method) {
            case "pictograph" -> "pictograph()";
            case "indicative" -> "indicative(\"" + escape(ch.meaning()) + "\")";
            case "compoundIndicative" -> "compoundIndicative(\"" + escape(ch.meaning()) + "\")";
            case "phonoSemantic" -> {
                String sem = ch.semantic().isEmpty() ? "zi(\"?\")" : componentExpr(ch.semantic());
                String phon = ch.phonetic().isEmpty() ? "zi(\"?\")" : componentExpr(ch.phonetic());
                yield "phonoSemantic(" + sem + ", " + phon + ")";
            }
            case "cognate" -> "cognate(\"" + escape(ch.meaning()) + "\")";
            case "loan" -> "loan(\"" + escape(ch.meaning()) + "\")";
            default -> "pictograph()";
        };
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // ── Characters.java registry generation ────────────────────────────

    private static void writeCharactersRegistry(Set<String> allClassNames) throws IOException {
        // Merge with existing classes
        Set<String> existing = findExistingClasses();
        Set<String> all = new TreeSet<>(existing);
        all.addAll(allClassNames);

        var sb = new StringBuilder();
        sb.append("package kranji.characters;\n\n");
        sb.append("import kranji.entry.ChineseCharacterEntry;\n\n");
        sb.append("import java.util.List;\n");
        sb.append("import java.util.stream.Stream;\n\n");
        sb.append("/**\n");
        sb.append(" * Central registry of all curated {@link ChineseCharacterEntry} instances\n");
        sb.append(" * from the toned-pinyin character library.\n");
        sb.append(" *\n");
        sb.append(" * <p>Auto-generated by {@code CharacterCodeGen}. Do not edit manually.</p>\n");
        sb.append(" */\n");
        sb.append("public final class Characters {\n\n");
        sb.append("    private Characters() {}\n\n");
        sb.append("    /** All curated character entries across all toned-pinyin classes. */\n");
        sb.append("    public static final List<ChineseCharacterEntry> ALL = Stream.of(\n");

        List<String> sorted = new ArrayList<>(all);
        for (int i = 0; i < sorted.size(); i++) {
            sb.append("            ").append(sorted.get(i)).append(".ALL");
            if (i < sorted.size() - 1) sb.append(",");
            sb.append("\n");
        }

        sb.append("    ).flatMap(List::stream).toList();\n");
        sb.append("}\n");

        Files.writeString(OUTPUT_DIR.resolve("Characters.java"), sb.toString(), StandardCharsets.UTF_8);
    }
}
