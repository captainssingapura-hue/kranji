package kranji.codegen.catalog;

import kranji.codegen.perclass.PerclassSingularIndex;
import kranji.common.perclass.AllPerclassRecords;
import kranji.common.perclass.promoted.AllPerclassRecordsPromoted;
import kranji.zi.ComposedZiT;
import kranji.zi.SingularZi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Catalog-driven iterator: walks {@code docs/catalog/batch-*.md}, resolves
 * each row's components against the current {@link GlyphIndex}, and either
 * reports (dry-run) or emits .java files into the staging module.
 *
 * <p>Single-round execution by default. The full fixpoint loop is the
 * caller's responsibility — typical workflow:</p>
 *
 * <ol>
 *   <li>{@code mvn -pl kranji-codegen exec:java -Dexec.mainClass=kranji.codegen.catalog.CatalogIteratorMain -Dexec.args="--dry-run"}</li>
 *   <li>If happy with predicted resolve count, drop {@code --dry-run}.</li>
 *   <li>{@code mvn -pl kranji-common-perclass-staging install} — build the round.</li>
 *   <li>Repeat (1) — newly emitted records are now in the GlyphIndex.</li>
 * </ol>
 *
 * <p>Args:</p>
 * <ul>
 *   <li>{@code --dry-run} — don't write any files; just print the resolution report.</li>
 *   <li>{@code --limit N} — process only first N rows of the catalog (pilot mode).</li>
 *   <li>{@code --catalog PATH} — override catalog directory (default
 *       {@code docs/catalog}).</li>
 *   <li>{@code --staging PATH} — override staging source root (default
 *       {@code kranji-common-perclass-staging/src/main/java}).</li>
 * </ul>
 */
public final class CatalogIteratorMain {

    private static final String STAGING_PKG_ROOT = "kranji.common.perclass.staging";

    private CatalogIteratorMain() {}

    public static void main(String[] args) throws IOException {
        Args a = Args.parse(args);

        // 1. Load catalog rows (optionally truncated for pilot)
        List<CatalogRow> rows = CatalogParser.parseDirectory(a.catalogDir);
        if (a.limit > 0 && rows.size() > a.limit) {
            rows = rows.subList(0, a.limit);
        }
        System.out.println("Catalog rows loaded: " + rows.size()
                + (a.limit > 0 ? " (limit=" + a.limit + ")" : ""));

        // 2. Build current GlyphIndex covering:
        //      - singulars (kranji-singulars)
        //      - hand-authored composed records (kranji-common-perclass)
        //      - promoted composed records (kranji-common-perclass-promoted)
        //
        //    Staged records (kranji-common-perclass-staging) are NOT included
        //    because kranji-codegen does not depend on the staging module
        //    (would create a cycle: staging depends on promoted depends on
        //    codegen via mvn ordering, etc.). Iterator behaviour: each round
        //    sees promoted as available; promote what's stable, then re-run.
        PerclassSingularIndex singulars = PerclassSingularIndex.build();
        List<ComposedZiT> composed = new ArrayList<>(
                AllPerclassRecords.ALL.size() + AllPerclassRecordsPromoted.ALL.size());
        composed.addAll(AllPerclassRecords.ALL);
        composed.addAll(AllPerclassRecordsPromoted.ALL);
        List<SingularZi> generatedSingulars = new ArrayList<>(
                AllPerclassRecordsPromoted.SINGULARS);
        GlyphIndex index = GlyphIndex.build(singulars, composed, generatedSingulars);
        System.out.println("Glyph index size: " + index.size()
                + " (" + singulars.size() + " hand-authored singulars + "
                + AllPerclassRecords.ALL.size() + " hand-authored composed + "
                + AllPerclassRecordsPromoted.ALL.size() + " promoted composed + "
                + AllPerclassRecordsPromoted.SINGULARS.size() + " promoted singulars)");

        // 3. Resolve + bucketise
        ComponentResolver resolver = new ComponentResolver(index);
        GeneratorRegistry registry = GeneratorRegistry.defaultRegistry();

        List<EmittedRow> readyToEmit = new ArrayList<>();
        Map<String, Integer> unsupportedByLayout = new TreeMap<>();
        Map<String, Integer> blockedByMissingGlyph = new TreeMap<>();
        List<CatalogIssue> issues = new ArrayList<>();
        int alreadyDone = 0;
        int pictographSkipped = 0;

        // What-if analytics: rows that WOULD resolve if every single-glyph missing
        // component were available. Multi-glyph parens-grouped tokens like "(王+白)"
        // need catalog re-decomposition (or their inner row to be added) — those
        // are excluded from the count.
        int wouldResolveIfMissingAdded = 0;
        Set<String> wouldNeedAsSingulars = new TreeSet<>();

        for (CatalogRow row : rows) {
            // Skip pictographs entirely — they belong in singulars, not perclass.
            if ("P".equals(row.layout())) { pictographSkipped++; continue; }

            // Skip rows whose glyph is already in the index (already added).
            if (index.contains(row.glyph())) { alreadyDone++; continue; }

            var gen = registry.find(row.layout());
            if (gen.isEmpty()) {
                unsupportedByLayout.merge(row.layout(), 1, Integer::sum);
                issues.add(new CatalogIssue(row, CatalogIssue.Kind.UNSUPPORTED_LAYOUT,
                        "no generator registered for layout code '" + row.layout() + "'"));
                continue;
            }

            var res = resolver.resolve(row);
            if (res.unsupported()) {
                issues.add(new CatalogIssue(row, CatalogIssue.Kind.BAD_COMPONENT_TOKEN,
                        "components contain a token that's neither a single glyph nor a "
                                + "single-glyph parens group: " + res.missing()));
                // Fall through into missing-glyph histogram too so the row still counts as blocked.
            }
            if (!res.ok()) {
                for (String m : res.missing()) {
                    blockedByMissingGlyph.merge(m, 1, Integer::sum);
                }
                // What-if: would this row resolve if every missing component were a singular?
                // Only if NO missing token is a multi-glyph parens-group (those require
                // catalog re-decomposition, not just a singular addition).
                boolean recoverableByAddingSingulars = !res.unsupported()
                        && res.missing().stream()
                                .allMatch(m -> m.codePointCount(0, m.length()) == 1);
                if (recoverableByAddingSingulars) {
                    wouldResolveIfMissingAdded++;
                    wouldNeedAsSingulars.addAll(res.missing());
                }
                continue;
            }
            if (res.resolved().size() != gen.get().arity()) {
                unsupportedByLayout.merge(row.layout() + "(arity_mismatch)", 1, Integer::sum);
                issues.add(new CatalogIssue(row, CatalogIssue.Kind.ARITY_MISMATCH,
                        "layout '" + row.layout() + "' expects " + gen.get().arity()
                                + " components, row provides " + res.resolved().size()));
                continue;
            }

            // All good — build the EmitContext.
            PinyinSyllableParser.Parts parts;
            try {
                parts = PinyinSyllableParser.parse(row.initial(), row.finalTone());
            } catch (Exception e) {
                unsupportedByLayout.merge("pinyin_parse_fail(" + row.pkg() + ")", 1, Integer::sum);
                issues.add(new CatalogIssue(row, CatalogIssue.Kind.PINYIN_PARSE,
                        "could not parse pinyin from pkg '" + row.pkg() + "': " + e.getMessage()));
                continue;
            }
            String targetPkg = STAGING_PKG_ROOT + "." + row.pkg();
            String targetClass = row.className();
            EmitContext ctx = new EmitContext(
                    row, res.resolved(), parts, targetPkg, targetClass,
                    EmitContext.unicodeEscape(row.glyph()));
            readyToEmit.add(new EmittedRow(row, gen.get(), ctx));
        }

        // 4. Report
        System.out.println();
        System.out.println("─── Resolution report ──────────────────────────");
        System.out.println("  ready to emit:        " + readyToEmit.size());
        System.out.println("  already in index:     " + alreadyDone);
        System.out.println("  pictograph skipped:   " + pictographSkipped);
        int unsupportedTotal = unsupportedByLayout.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("  unsupported layout:   " + unsupportedTotal);
        unsupportedByLayout.forEach((k, v) ->
                System.out.println("      " + pad(k, 20) + " " + v));
        int blockedRowsApprox = rows.size() - readyToEmit.size() - alreadyDone
                - pictographSkipped - unsupportedTotal;
        System.out.println("  blocked on missing:   " + blockedRowsApprox + " rows");
        System.out.println("  top 20 missing components:");
        blockedByMissingGlyph.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(20)
                .forEach(e -> System.out.println(
                        "      " + e.getKey() + "  (" + e.getValue() + " rows)"));

        // Write a UTF-8 report file too (Windows console mojibakes CJK).
        Path reportFile = Path.of("kranji-codegen", "target", "catalog-iterator-report.txt");
        Files.createDirectories(reportFile.getParent());
        StringBuilder report = new StringBuilder();
        report.append("Catalog rows: ").append(rows.size()).append('\n');
        report.append("Glyph index: ").append(index.size()).append(" (")
                .append(singulars.size()).append(" singulars + ")
                .append(composed.size()).append(" composed)\n\n");
        report.append("Ready to emit: ").append(readyToEmit.size()).append('\n');
        report.append("Already in index: ").append(alreadyDone).append('\n');
        report.append("Pictograph skipped: ").append(pictographSkipped).append('\n');
        report.append("Unsupported layout buckets:\n");
        unsupportedByLayout.forEach((k, v) ->
                report.append("  ").append(k).append(" : ").append(v).append('\n'));
        report.append("Blocked-on-missing glyph histogram (sorted desc):\n");
        blockedByMissingGlyph.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(e -> report.append("  ").append(e.getKey())
                        .append(" : ").append(e.getValue()).append('\n'));

        report.append("\n─── What-if: add every missing single-glyph component ──────\n");
        report.append("Would unblock: ").append(wouldResolveIfMissingAdded).append(" more rows\n");
        report.append("Unique components needed: ").append(wouldNeedAsSingulars.size()).append('\n');
        int totalReachable = alreadyDone + wouldResolveIfMissingAdded;
        report.append("Total reachable then: ").append(totalReachable)
                .append(" / ").append(rows.size())
                .append(" (").append(100 * totalReachable / rows.size()).append("%)\n");
        report.append("Remaining unresolvable (arity/parens-group/typo): ")
                .append(rows.size() - totalReachable - pictographSkipped).append(" rows\n");
        report.append("\nReady-to-emit rows (glyph, pkg, suffix):\n");
        for (var er : readyToEmit) {
            report.append("  ").append(er.row.glyph()).append("  ")
                    .append(er.row.pkg()).append("  ")
                    .append(er.ctx.targetClassName()).append('\n');
        }
        Files.writeString(reportFile, report.toString(), java.nio.charset.StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println();
        System.out.println("Detailed UTF-8 report: " + reportFile);

        // Catalog quality issues — separate registry for human triage.
        Path issuesFile = Path.of("docs", "catalog", "issues.md");
        CatalogIssueWriter.write(issues, issuesFile);
        System.out.println("Catalog issues report: " + issuesFile + " (" + issues.size() + " issues)");

        if (a.dryRun) {
            System.out.println();
            System.out.println("Dry-run mode: no files written.");
            return;
        }

        // 5. Emit files (overwrite if present — deterministic output means
        //    no spurious diffs for unchanged rows). For each row, emit any
        //    synthetic inline-composition records first so the outer can
        //    reference them.
        int written = 0;
        int synthWritten = 0;
        Set<Path> emittedPaths = new TreeSet<>();
        for (EmittedRow er : readyToEmit) {
            for (JavaRef ref : er.ctx.components()) {
                if (ref instanceof JavaRef.InlineRef ir) {
                    String body = emitInlineSynthetic(ir, er.ctx);
                    Path target = a.stagingRoot
                            .resolve(STAGING_PKG_ROOT.replace('.', '/'))
                            .resolve(er.row.initial())
                            .resolve(er.row.finalTone())
                            .resolve(ir.simpleClassName() + ".java");
                    Files.createDirectories(target.getParent());
                    Files.writeString(target, body, StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING);
                    emittedPaths.add(target);
                    synthWritten++;
                }
            }

            String body = er.generator.emit(er.ctx);
            Path target = a.stagingRoot
                    .resolve(STAGING_PKG_ROOT.replace('.', '/'))
                    .resolve(er.row.initial())
                    .resolve(er.row.finalTone())
                    .resolve(er.row.className() + ".java");
            Files.createDirectories(target.getParent());
            Files.writeString(target, body, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            emittedPaths.add(target);
            written++;
        }
        System.out.println();
        System.out.println("Wrote " + written + " staged files under "
                + a.stagingRoot.resolve(STAGING_PKG_ROOT.replace('.', '/')));
        if (synthWritten > 0) {
            System.out.println("Plus " + synthWritten + " synthetic inline-composition records.");
        }

        // 6. Rewrite AllPerclassRecordsStaging.java with the union of:
        //    (a) existing staged files from previous rounds (we don't read them
        //        back — instead we list every emitted file across all rounds.
        //        For round-1 simplicity: list only what we just emitted plus
        //        whatever survives from prior rounds at that path.)
        //    (b) what we just emitted.
        //
        // Implementation: scan the staging tree on disk for any .java file whose
        // class implements ComposedZiT (we can't load classes here, but file
        // naming + a fixed pattern is enough — every staged file declares
        // INSTANCE), and aggregate all FQNs alphabetically.
        rewriteStagingRegistry(a.stagingRoot);
        System.out.println("Rewrote AllPerclassRecordsStaging.ALL");
    }

    // ── Helpers ─────────────────────────────────────────────────────────

    private record EmittedRow(CatalogRow row, StructureGenerator generator, EmitContext ctx) {}

    /**
     * Emit a synthetic inline-composition record. The inner inherits the
     * outer's pinyin and package, has an empty glyph (anonymous), and uses
     * the inline's declared layout (currently always {@code "LR"}).
     */
    private static String emitInlineSynthetic(JavaRef.InlineRef ir, EmitContext outerCtx) {
        CatalogRow innerRow = new CatalogRow(
                "",
                outerCtx.row().pinyinRaw(),
                outerCtx.row().pkg(),
                ir.layoutCode(),
                java.util.List.of(),
                "CI",
                "Inner",
                outerCtx.row().sourceFile(),
                outerCtx.row().sourceLine());
        EmitContext innerCtx = new EmitContext(
                innerRow,
                ir.children(),
                outerCtx.pinyinParts(),
                outerCtx.targetPackageFqn(),
                ir.simpleClassName(),
                "");
        StructureGenerator gen = GeneratorRegistry.defaultRegistry()
                .find(ir.layoutCode())
                .orElseThrow(() -> new IllegalStateException(
                        "no generator for inline layout " + ir.layoutCode()));
        return gen.emit(innerCtx);
    }

    private static String pad(String s, int w) {
        if (s.length() >= w) return s;
        return s + " ".repeat(w - s.length());
    }

    private static void rewriteStagingRegistry(Path stagingRoot) throws IOException {
        Path stagingPkgDir = stagingRoot.resolve(STAGING_PKG_ROOT.replace('.', '/'));
        ScanResult scan = scanRecordFiles(stagingPkgDir, STAGING_PKG_ROOT,
                "AllPerclassRecordsStaging");
        String body = renderRegistry(STAGING_PKG_ROOT, "AllPerclassRecordsStaging",
                "Flat registry of every staged (auto-generated) typed per-class\n"
                        + " * record. Rewritten in full by the catalog iterator on every\n"
                        + " * generation round. Do not edit by hand.",
                scan, "kranji.codegen.catalog.CatalogIteratorMain");
        Path target = stagingPkgDir.resolve("AllPerclassRecordsStaging.java");
        Files.writeString(target, body, java.nio.charset.StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /** Holds the partition of staged/promoted record files into composed vs singular. */
    record ScanResult(List<String> composedFqns, List<String> singularFqns) {}

    /**
     * Walk a staging/promoted package tree, classify each .java file as a
     * {@code ComposedZiT} or {@code SingularZi} record by sniffing its
     * source for {@code implements SingularZi} vs {@code implements ComposedZiT},
     * and return the two FQN lists alphabetically sorted.
     */
    static ScanResult scanRecordFiles(Path pkgDir, String pkgFqn, String registryClassName) throws IOException {
        List<String> composed = new ArrayList<>();
        List<String> singular = new ArrayList<>();
        if (!Files.isDirectory(pkgDir)) return new ScanResult(List.of(), List.of());
        try (var walk = Files.walk(pkgDir)) {
            for (Path p : (Iterable<Path>) walk::iterator) {
                if (!Files.isRegularFile(p)) continue;
                String name = p.getFileName().toString();
                if (!name.endsWith(".java")) continue;
                if (name.equals(registryClassName + ".java")) continue;
                if (name.equals("AllZiRecords.java")) continue;
                Path rel = pkgDir.relativize(p);
                String relStr = rel.toString().replace('\\', '/');
                String fqn = pkgFqn + "." +
                        relStr.substring(0, relStr.length() - ".java".length())
                                .replace('/', '.');
                String body = Files.readString(p, java.nio.charset.StandardCharsets.UTF_8);
                if (body.contains("implements SingularZi")) singular.add(fqn);
                else composed.add(fqn);
            }
        }
        Collections.sort(composed);
        Collections.sort(singular);
        return new ScanResult(composed, singular);
    }

    /** Render the registry .java source with both {@code ALL} (composed) and {@code SINGULARS} fields. */
    static String renderRegistry(String pkgFqn, String className, String docBlock,
                                 ScanResult scan, String generatorMarker) {
        StringBuilder sb = new StringBuilder();
        sb.append("// AUTO-GENERATED by ").append(generatorMarker).append(" - do not edit by hand.\n");
        sb.append("package ").append(pkgFqn).append(";\n\n");
        sb.append("import kranji.zi.ComposedZiT;\n");
        sb.append("import kranji.zi.SingularZi;\n\n");
        sb.append("import java.util.List;\n\n");
        sb.append("/**\n * ").append(docBlock).append("\n */\n");
        sb.append("public final class ").append(className).append(" {\n\n");
        sb.append("    private ").append(className).append("() {}\n\n");
        sb.append("    /** All staged/promoted ComposedZi records, alphabetical by FQN. */\n");
        appendListField(sb, "ComposedZiT", "ALL", scan.composedFqns());
        sb.append("\n    /** All staged/promoted SingularZi records, alphabetical by FQN. */\n");
        appendListField(sb, "SingularZi", "SINGULARS", scan.singularFqns());
        sb.append("}\n");
        return sb.toString();
    }

    private static void appendListField(StringBuilder sb, String elemType,
                                        String fieldName, List<String> fqns) {
        sb.append("    public static final List<").append(elemType).append("> ")
                .append(fieldName).append(" = List.of(");
        if (fqns.isEmpty()) {
            sb.append(");\n");
        } else {
            sb.append("\n");
            for (int i = 0; i < fqns.size(); i++) {
                sb.append("            ").append(fqns.get(i)).append(".INSTANCE");
                sb.append(i == fqns.size() - 1 ? ");\n" : ",\n");
            }
        }
    }

    // ── Args ────────────────────────────────────────────────────────────

    private record Args(boolean dryRun, int limit, Path catalogDir, Path stagingRoot) {
        static Args parse(String[] args) {
            boolean dry = false;
            int lim = -1;
            Path cat = Path.of("docs", "catalog");
            Path stage = Path.of("kranji-common-perclass-staging", "src", "main", "java");
            for (int i = 0; i < args.length; i++) {
                String x = args[i];
                switch (x) {
                    case "--dry-run" -> dry = true;
                    case "--limit"   -> lim = Integer.parseInt(args[++i]);
                    case "--catalog" -> cat = Path.of(args[++i]);
                    case "--staging" -> stage = Path.of(args[++i]);
                    default -> System.err.println("Unknown arg: " + x);
                }
            }
            return new Args(dry, lim, cat, stage);
        }
    }
}
