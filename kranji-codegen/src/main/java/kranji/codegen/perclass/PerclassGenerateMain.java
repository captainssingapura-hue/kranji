package kranji.codegen.perclass;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Orchestrator for the per-class typed-record code generator.
 *
 * <p>Reads the JSON catalog under {@code kranji-codegen/src/main/resources/catalog/}
 * (depth1–depth5) and emits one typed Java record per ComposedZi into
 * {@code kranji-common-perclass/src/main/java/kranji/common/perclass/}.
 * Also emits a flat {@code AllPerclassRecords.ALL} registry.</p>
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
        List<PerclassRouting.Key> keys = new ArrayList<>();
        for (PerclassComposedIndex.Entry e : composed.entries()) {
            PerclassRouting.Key key = e.key();
            String source = PerclassJavaEmitter.emitRecord(key, e.data(), singulars, composed);
            Path targetDir = outputRoot
                    .resolve(key.initial())
                    .resolve(key.finalTone());
            Files.createDirectories(targetDir);
            Path target = targetDir.resolve(key.className() + ".java");
            Files.writeString(target, source, StandardCharsets.UTF_8);
            keys.add(key);
        }
        System.out.println("Emitted " + keys.size() + " record files.");

        // 4. Emit AllPerclassRecords registry at the root package.
        String registry = PerclassJavaEmitter.emitRegistry(
                PerclassRouting.ROOT_PACKAGE, keys);
        Path registryPath = outputRoot.resolve("AllPerclassRecords.java");
        Files.writeString(registryPath, registry, StandardCharsets.UTF_8);
        System.out.println("Registry : " + registryPath);
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
