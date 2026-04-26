package kranji.codegen.perclass;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Orchestrator for the per-class typed-record code generator.
 *
 * <p>Reads the JSON catalog under {@code kranji-codegen/src/main/resources/catalog/}
 * (depth1–depth5) and emits one typed Java record per ComposedZi into
 * {@code kranji-codegen/target/perclass-out/kranji/common/perclass/}
 * (see {@link PerclassRouting#OUTPUT_ROOT}). The output is diagnostic
 * — it is <strong>not</strong> on the build's compile classpath.
 * Hand-authored records under
 * {@code kranji-common-perclass/src/main/java/kranji/common/perclass/}
 * are the canonical Java; this generator can never touch them.
 * To promote a regenerated record onto the classpath, copy it from
 * {@code target/perclass-out/} into the corresponding src/main path.</p>
 *
 * <p>Also emits a flat {@code AllPerclassRecords.ALL} registry into
 * the same {@code target/perclass-out/} root.</p>
 *
 * <p>Strict invariant: output is a pure function of the JSON catalog
 * plus the singular-glyph reverse-index built from
 * {@code kranji-singulars} + {@code kranji-core}. Running the generator
 * twice produces byte-identical output.</p>
 *
 * <p>Run with:</p>
 * <pre>{@code
 * mvn -pl kranji-codegen exec:java \
 *     -Dexec.mainClass=kranji.codegen.perclass.PerclassGenerateMain
 * }</pre>
 */
public final class PerclassGenerateMain {

    private PerclassGenerateMain() {}

    public static void main(String[] args) throws IOException {
        // 1. Locate the reactor root so the tool works regardless of the
        //    cwd Maven picks (it usually runs from the module, but IDE
        //    invocations may set the repo root).
        Path cwd = Path.of("").toAbsolutePath();
        Path reactorRoot = locateReactorRoot(cwd);
        Path catalogRoot = reactorRoot.resolve(PerclassRouting.CATALOG_ROOT);
        Path outputRoot  = reactorRoot.resolve(PerclassRouting.OUTPUT_ROOT);

        System.out.println("Reactor root : " + reactorRoot);
        System.out.println("Catalog root : " + catalogRoot);
        System.out.println("Output root  : " + outputRoot);

        // 2. Build indices.
        PerclassSingularIndex singulars = PerclassSingularIndex.build();
        System.out.println("Singular index entries: " + singulars.size());

        PerclassComposedIndex composed = PerclassComposedIndex.loadAll(catalogRoot);
        System.out.println("Composed index entries: " + composed.size());

        // 3. Emit each per-class record.
        //    Emission is idempotent: files whose on-disk content already
        //    matches the freshly generated source are left untouched
        //    (preserving mtime — keeps Maven incremental compile happy
        //    and avoids IDE re-indexing storms on no-op runs).
        int written = 0;
        int unchanged = 0;
        List<PerclassRouting.Key> keys = new ArrayList<>();
        Set<Path> emittedPaths = new HashSet<>();
        for (PerclassComposedIndex.Entry e : composed.entries()) {
            PerclassRouting.Key key = e.key();
            String source = PerclassJavaEmitter.emitRecord(key, e.data(), singulars, composed);
            Path targetDir = outputRoot
                    .resolve(key.initial())
                    .resolve(key.finalTone());
            Files.createDirectories(targetDir);
            Path target = targetDir.resolve(key.className() + ".java");
            if (writeIfChanged(target, source)) {
                written++;
            } else {
                unchanged++;
            }
            emittedPaths.add(target.toAbsolutePath().normalize());
            keys.add(key);
        }

        // 4. Emit AllPerclassRecords registry at the root package.
        String registry = PerclassJavaEmitter.emitRegistry(
                PerclassRouting.ROOT_PACKAGE, keys);
        Path registryPath = outputRoot.resolve("AllPerclassRecords.java");
        if (writeIfChanged(registryPath, registry)) {
            written++;
        } else {
            unchanged++;
        }
        emittedPaths.add(registryPath.toAbsolutePath().normalize());

        // 5. Stale-file sweep. Remove any .java under a per-initial
        //    sub-package that is not in the emitted set. Root-level
        //    files (AllPerclassRecords.java, or hand-written aggregators
        //    that might live at the root) are never swept.
        int deleted = sweepStale(outputRoot, emittedPaths);

        System.out.println("Emitted: " + written + " written, "
                + unchanged + " unchanged, " + deleted + " deleted"
                + " (total intended: " + (written + unchanged) + ")");
        System.out.println("Registry : " + registryPath);
    }

    /**
     * Write {@code source} to {@code target} only if the existing content
     * differs. Returns {@code true} if a write actually happened,
     * {@code false} if the file was already up-to-date.
     */
    private static boolean writeIfChanged(Path target, String source) throws IOException {
        if (Files.exists(target)) {
            String existing = Files.readString(target, StandardCharsets.UTF_8);
            if (existing.equals(source)) {
                return false;
            }
        }
        Files.writeString(target, source, StandardCharsets.UTF_8);
        return true;
    }

    /**
     * Delete stale generated files. Walks {@code outputRoot} recursively
     * and removes any {@code .java} file under a per-initial sub-package
     * (depth ≥ 2 below {@code outputRoot}) that was not emitted this run.
     * Files directly at {@code outputRoot} (e.g. {@code AllPerclassRecords.java})
     * are never swept — they are either regenerated (and present in
     * {@code emittedPaths}) or hand-written.
     */
    private static int sweepStale(Path outputRoot, Set<Path> emittedPaths) throws IOException {
        if (!Files.isDirectory(outputRoot)) {
            return 0;
        }
        Path root = outputRoot.toAbsolutePath().normalize();
        List<Path> toDelete = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(root)) {
            stream.filter(Files::isRegularFile)
                  .filter(p -> p.getFileName().toString().endsWith(".java"))
                  .filter(p -> root.relativize(p).getNameCount() >= 2)
                  .map(p -> p.toAbsolutePath().normalize())
                  .filter(p -> !emittedPaths.contains(p))
                  .forEach(toDelete::add);
        }
        for (Path p : toDelete) {
            Files.delete(p);
        }
        // Sweep empty per-initial / per-finalTone dirs left behind.
        if (!toDelete.isEmpty()) {
            try (Stream<Path> stream = Files.walk(root)) {
                stream.sorted((a, b) -> b.getNameCount() - a.getNameCount())
                      .filter(Files::isDirectory)
                      .filter(p -> !p.equals(root))
                      .forEach(p -> {
                          try (Stream<Path> children = Files.list(p)) {
                              if (children.findAny().isEmpty()) {
                                  Files.delete(p);
                              }
                          } catch (IOException ignored) {
                              // best-effort; leave dir in place if it can't be cleaned
                          }
                      });
            }
        }
        return toDelete.size();
    }

    /** Walk up from {@code cwd} until we find {@code kranji-codegen} directory. */
    private static Path locateReactorRoot(Path cwd) {
        Path p = cwd.toAbsolutePath();
        while (p != null) {
            if (Files.isDirectory(p.resolve("kranji-codegen"))
                    && Files.isDirectory(p.resolve("kranji-common-perclass"))) {
                return p;
            }
            p = p.getParent();
        }
        throw new IllegalStateException(
                "Could not locate reactor root (looking for kranji-codegen + "
                        + "kranji-common-perclass as siblings) from " + cwd);
    }
}
