package kranji.codegen.depth2;

import kranji.zi.ComposedZi;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Reverse-lookup table mapping a depth-N composed glyph (e.g. {@code "元"},
 * {@code "分"}) to the Java reference {@link Ref} that should appear in
 * generated higher-depth code
 * (e.g. {@code kranji.common.depth1.zero.Van2.YUAN}).
 *
 * <p>Built by walking a depth module's source tree — reads every
 * {@code public static final ComposedZi <NAME>} declaration and records
 * its glyph. First-seen wins on ties, so stable scan order matters.</p>
 *
 * <p>The {@link Ref#expression()} returned for a slot position must always
 * be suffixed with {@code .structure()} by the caller, since
 * {@link ComposedZi} instances satisfy the {@link kranji.zi.Zi} interface
 * but composition slots require {@link kranji.zi.BlockStructure}.</p>
 */
public final class ComposedZiIndex {

    /**
     * A Java reference for a named ComposedZi field.
     *
     * <p>Always emitted in fully-qualified form (e.g.
     * {@code kranji.common.depth1.f.En1.FEN.structure()}) — never as a
     * simple-name + import — because the per-pinyin class names
     * (e.g. {@code An3}, {@code I4}) collide between depth buckets:
     * {@code kranji.common.depth1.f.An3} and the generated
     * {@code kranji.common.depth2.b.An3} would otherwise both want the
     * simple name {@code An3} in the same compilation unit, which is a
     * compile error. Using FQNs sidesteps that conflict and matches the
     * style of the legacy hand-written depth-2 source.</p>
     */
    public record Ref(String importFqn, String simpleClass, String constant) {
        /** Fully-qualified expression: {@code <pkg>.<SimpleClass>.<CONSTANT>}. */
        public String fqnExpression() {
            return importFqn + "." + constant;
        }

        /** {@code <pkg>.<SimpleClass>.<CONSTANT>.structure()} — for slot positions. */
        public String structureExpression() {
            return fqnExpression() + ".structure()";
        }
    }

    private final Map<String, Ref> byGlyph;

    private ComposedZiIndex(Map<String, Ref> byGlyph) {
        this.byGlyph = byGlyph;
    }

    /**
     * Build an index by walking {@code <moduleRoot>/src/main/java/<package…>/}
     * recursively and reflecting on every {@code public static final ComposedZi}
     * field discovered.
     *
     * @param moduleRoot e.g. {@code Path.of("kranji-common-depth1")}
     * @param rootPackage e.g. {@code "kranji.common.depth1"}
     */
    public static ComposedZiIndex buildFromModule(Path moduleRoot, String rootPackage) throws IOException {
        return buildFromModules(List.of(new ModuleSpec(moduleRoot, rootPackage)));
    }

    /**
     * One depth module to include in the index.
     *
     * @param moduleRoot e.g. {@code Path.of("kranji-common-depth1")}
     * @param rootPackage e.g. {@code "kranji.common.depth1"}
     */
    public record ModuleSpec(Path moduleRoot, String rootPackage) {}

    /**
     * Build a single merged reverse-index covering multiple depth modules.
     *
     * <p>Used by depth-3+ codegen, where a composed slot may reference
     * either a depth-1 or a depth-2 record. Module scan order is the
     * order in the list; first-seen wins on duplicate glyphs (which
     * should not happen across distinct depth buckets, but the rule is
     * deterministic).</p>
     */
    public static ComposedZiIndex buildFromModules(List<ModuleSpec> modules) throws IOException {
        Map<String, Ref> map = new LinkedHashMap<>();
        for (ModuleSpec spec : modules) {
            Path javaRoot = spec.moduleRoot().resolve("src/main/java");
            Path pkgRoot  = javaRoot.resolve(spec.rootPackage().replace('.', '/'));
            if (!Files.isDirectory(pkgRoot)) {
                throw new IllegalStateException(
                        "Source package root does not exist: " + pkgRoot);
            }

            List<Class<?>> sourceClasses = new ArrayList<>();
            try (var walk = Files.walk(pkgRoot)) {
                for (Path javaFile : (Iterable<Path>) walk::iterator) {
                    if (!Files.isRegularFile(javaFile)) continue;
                    String name = javaFile.getFileName().toString();
                    if (!name.endsWith(".java")) continue;
                    Path rel = javaRoot.relativize(javaFile);
                    String relStr = rel.toString().replace('\\', '/');
                    String fqn = relStr.substring(0, relStr.length() - 5).replace('/', '.');
                    try {
                        sourceClasses.add(Class.forName(fqn));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("Cannot load " + fqn
                                + " — is " + spec.moduleRoot() + " on the classpath?", e);
                    }
                }
            }
            sourceClasses.sort(Comparator.comparing(Class::getName));

            for (Class<?> c : sourceClasses) {
                for (Field f : c.getDeclaredFields()) {
                    int mods = f.getModifiers();
                    if (!Modifier.isStatic(mods) || !Modifier.isFinal(mods)
                            || !Modifier.isPublic(mods)) continue;
                    if (!ComposedZi.class.isAssignableFrom(f.getType())) continue;
                    try {
                        Object value = f.get(null);
                        if (value instanceof ComposedZi cz) {
                            // First-seen wins; subsequent matches silently ignored.
                            map.putIfAbsent(cz.character(), new Ref(
                                    c.getName(),
                                    c.getSimpleName(),
                                    f.getName()));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Reflection failure on "
                                + c.getSimpleName() + "." + f.getName(), e);
                    }
                }
            }
        }
        return new ComposedZiIndex(map);
    }

    /** Look up the {@link Ref} for a glyph; returns {@code null} if absent. */
    public Ref find(String glyph) { return byGlyph.get(glyph); }

    /** Total number of distinct glyphs in the index. */
    public int size() { return byGlyph.size(); }
}
