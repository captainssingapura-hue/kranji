package kranji.common.base;

import kranji.zi.ComposedZi;

import java.util.List;
import java.util.Optional;

/**
 * Depth-indexed sub-registry of {@link ComposedZi}.
 *
 * <p>Composed characters are partitioned by the maximum nesting depth of
 * their composition tree. Each depth module
 * (e.g. {@code kranji-common-depth1}) holds exactly the records at its
 * depth and exposes them through a singleton {@code DepthNRegistry}
 * implementing this interface.</p>
 *
 * <p>Keeping the sub-registries per-depth lets callers that already know
 * the target depth query a single focused pool, and lets the module graph
 * enforce the "a depth-N record may only reference blocks at depth &lt; N"
 * invariant at compile time.</p>
 */
public interface DepthRegistry {

    /**
     * The composition-tree depth served by this registry. Constant per
     * implementation; values 1–5 correspond to the five depth modules.
     */
    int depth();

    /**
     * All composed Zi at this depth, in registration order. Implementations
     * should return an immutable, never-null list.
     */
    List<ComposedZi> zi();

    /** Look up a Zi at this depth by its glyph. */
    default Optional<ComposedZi> find(String glyph) {
        for (ComposedZi z : zi()) {
            if (z.character().equals(glyph)) {
                return Optional.of(z);
            }
        }
        return Optional.empty();
    }
}
