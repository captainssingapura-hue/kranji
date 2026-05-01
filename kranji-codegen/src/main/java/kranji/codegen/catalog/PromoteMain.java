package kranji.codegen.catalog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mechanically promotes records from
 * {@code kranji-common-perclass-staging} to
 * {@code kranji-common-perclass-promoted}.
 *
 * <p>For each {@code .java} file under the staging package tree (excluding
 * the {@code AllPerclassRecordsStaging} and {@code AllZiRecords} aggregator
 * files), the promoter:</p>
 *
 * <ol>
 *   <li>Reads the file content as UTF-8.</li>
 *   <li>Rewrites every occurrence of {@code kranji.common.perclass.staging.}
 *       → {@code kranji.common.perclass.promoted.} (so both the package
 *       declaration and any inter-staged imports are corrected in one pass).</li>
 *   <li>Writes to the equivalent path under the promoted module.</li>
 *   <li>Deletes the source file.</li>
 * </ol>
 *
 * <p>After all files are moved, both registry aggregators are rewritten:</p>
 * <ul>
 *   <li>{@code AllPerclassRecordsPromoted.ALL} — scan the promoted tree,
 *       list every record FQN alphabetically.</li>
 *   <li>{@code AllPerclassRecordsStaging.ALL} — scan the (now smaller)
 *       staging tree, regenerate (often empty after a full promotion).</li>
 * </ul>
 *
 * <p>The promoter is order-blind: when promoting all-at-once, file order
 * doesn't matter because the package rewrite is uniform. If callers
 * choose selective promotion (one file at a time), they must promote
 * dependency-leaves first — otherwise a still-staged file would have an
 * import to a now-promoted neighbour and fail to compile in staging.</p>
 *
 * <p>Args:</p>
 * <ul>
 *   <li>{@code --dry-run} — report what would move without touching files.</li>
 *   <li>{@code --pattern REGEX} — only promote files whose relative path
 *       (under the staging package root) matches the regex
 *       (e.g. {@code "b/.*"} for all initial-{@code b} records). Default: all.</li>
 *   <li>{@code --staging PATH} — override staging source root.</li>
 *   <li>{@code --promoted PATH} — override promoted source root.</li>
 * </ul>
 */
public final class PromoteMain {

    private static final String STAGING_PKG_ROOT = "kranji.common.perclass.staging";
    private static final String PROMOTED_PKG_ROOT = "kranji.common.perclass.promoted";

    private PromoteMain() {}

    public static void main(String[] args) throws IOException {
        Args a = Args.parse(args);
        Path stagingPkgDir = a.stagingRoot.resolve(STAGING_PKG_ROOT.replace('.', '/'));
        Path promotedPkgDir = a.promotedRoot.resolve(PROMOTED_PKG_ROOT.replace('.', '/'));

        if (!Files.isDirectory(stagingPkgDir)) {
            System.err.println("Staging package dir does not exist: " + stagingPkgDir);
            System.exit(1);
        }
        Files.createDirectories(promotedPkgDir);

        // 1. Collect candidate files
        List<Path> candidates = new ArrayList<>();
        try (var walk = Files.walk(stagingPkgDir)) {
            for (Path p : (Iterable<Path>) walk::iterator) {
                if (!Files.isRegularFile(p)) continue;
                String name = p.getFileName().toString();
                if (!name.endsWith(".java")) continue;
                if (name.equals("AllPerclassRecordsStaging.java")) continue;
                if (name.equals("AllZiRecords.java")) continue;
                Path rel = stagingPkgDir.relativize(p);
                String relStr = rel.toString().replace('\\', '/');
                if (a.pattern != null && !relStr.matches(a.pattern)) continue;
                candidates.add(p);
            }
        }
        Collections.sort(candidates);
        System.out.println("Promotion candidates: " + candidates.size());

        if (a.dryRun) {
            for (Path p : candidates) {
                System.out.println("  would promote: " + stagingPkgDir.relativize(p));
            }
            System.out.println();
            System.out.println("Dry-run mode: no files moved.");
            return;
        }

        // 2. Move each file with package rewrite
        for (Path src : candidates) {
            Path rel = stagingPkgDir.relativize(src);
            Path dst = promotedPkgDir.resolve(rel);
            Files.createDirectories(dst.getParent());
            String body = Files.readString(src, java.nio.charset.StandardCharsets.UTF_8);
            String rewritten = body.replace(
                    STAGING_PKG_ROOT + ".",
                    PROMOTED_PKG_ROOT + ".");
            Files.writeString(dst, rewritten, java.nio.charset.StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.delete(src);
        }
        System.out.println("Moved " + candidates.size() + " files to promoted module.");

        // 3. Clean up empty staging directories left behind
        cleanupEmptyDirs(stagingPkgDir);

        // 4. Rewrite both registry aggregators from the current on-disk state
        rewriteRegistry(stagingPkgDir, STAGING_PKG_ROOT,
                "AllPerclassRecordsStaging",
                "Flat registry of every staged (auto-generated) typed per-class\n"
                        + " * record. Rewritten in full by the catalog iterator on every\n"
                        + " * generation round. Do not edit by hand.");
        rewriteRegistry(promotedPkgDir, PROMOTED_PKG_ROOT,
                "AllPerclassRecordsPromoted",
                "Flat registry of every promoted (auto-generated, reviewed-and-stabilised)\n"
                        + " * record. Rewritten in full by PromoteMain after each\n"
                        + " * promotion round. Do not edit by hand.");
        System.out.println("Rewrote AllPerclassRecordsStaging and AllPerclassRecordsPromoted.");
    }

    // ── Helpers ─────────────────────────────────────────────────────────

    private static void cleanupEmptyDirs(Path root) throws IOException {
        if (!Files.isDirectory(root)) return;
        // Walk depth-first; delete any directory that's empty after children are processed.
        try (var walk = Files.walk(root)) {
            walk.sorted(java.util.Comparator.reverseOrder())
                    .filter(p -> !p.equals(root))
                    .filter(Files::isDirectory)
                    .forEach(p -> {
                        try (var children = Files.list(p)) {
                            if (children.findAny().isEmpty()) Files.delete(p);
                        } catch (IOException ignored) {}
                    });
        }
    }

    private static void rewriteRegistry(Path pkgDir, String pkgFqn,
                                        String className, String docBlock) throws IOException {
        Files.createDirectories(pkgDir);
        CatalogIteratorMain.ScanResult scan =
                CatalogIteratorMain.scanRecordFiles(pkgDir, pkgFqn, className);
        String body = CatalogIteratorMain.renderRegistry(pkgFqn, className, docBlock,
                scan, "kranji.codegen.catalog.PromoteMain");
        Path target = pkgDir.resolve(className + ".java");
        Files.writeString(target, body, java.nio.charset.StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    // ── Args ────────────────────────────────────────────────────────────

    private record Args(boolean dryRun, String pattern, Path stagingRoot, Path promotedRoot) {
        static Args parse(String[] args) {
            boolean dry = false;
            String pat = null;
            Path stage = Path.of("kranji-common-perclass-staging", "src", "main", "java");
            Path promo = Path.of("kranji-common-perclass-promoted", "src", "main", "java");
            for (int i = 0; i < args.length; i++) {
                String x = args[i];
                switch (x) {
                    case "--dry-run" -> dry = true;
                    case "--pattern" -> pat = args[++i];
                    case "--staging" -> stage = Path.of(args[++i]);
                    case "--promoted" -> promo = Path.of(args[++i]);
                    default -> System.err.println("Unknown arg: " + x);
                }
            }
            return new Args(dry, pat, stage, promo);
        }
    }
}
