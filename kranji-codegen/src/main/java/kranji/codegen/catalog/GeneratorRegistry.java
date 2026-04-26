package kranji.codegen.catalog;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Holds the set of {@link StructureGenerator}s currently supported by the
 * iterator. Lookup is by {@link CatalogRow#layout()} code.
 *
 * <p>Adding support for a new layout = drop in a new {@code StructureGenerator}
 * implementation and add it to {@link #defaultRegistry()} (one line).</p>
 */
public final class GeneratorRegistry {

    private final Map<String, StructureGenerator> byCode;

    private GeneratorRegistry(Map<String, StructureGenerator> byCode) {
        this.byCode = byCode;
    }

    public Optional<StructureGenerator> find(String layoutCode) {
        return Optional.ofNullable(byCode.get(layoutCode));
    }

    public java.util.Set<String> supportedCodes() {
        return java.util.Collections.unmodifiableSet(byCode.keySet());
    }

    /**
     * Default registry: every typed-layout interface currently in
     * {@code kranji.zi.*T}. To extend: add a new generator class under
     * this package and register it here.
     */
    public static GeneratorRegistry defaultRegistry() {
        Map<String, StructureGenerator> m = new HashMap<>();
        register(m, new LeftRightGenerator());
        register(m, new TopBottomGenerator());
        register(m, new LeftMiddleRightGenerator());
        register(m, new TopMiddleBottomGenerator());
        register(m, new SemiEnclosureUpperLeftGenerator());
        register(m, new SemiEnclosureUpperRightGenerator());
        register(m, new SemiEnclosureBottomLeftGenerator());
        register(m, new SemiEnclosureTopThreeGenerator());
        register(m, new SemiEnclosureBottomThreeGenerator());
        register(m, new SemiEnclosureLeftThreeGenerator());
        register(m, new FullEnclosureGenerator());
        register(m, new SingularGenerator());
        return new GeneratorRegistry(m);
    }

    private static void register(Map<String, StructureGenerator> m, StructureGenerator g) {
        if (m.put(g.layoutCode(), g) != null) {
            throw new IllegalStateException("Duplicate layout code: " + g.layoutCode());
        }
    }
}
