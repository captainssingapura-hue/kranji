package kranji.common.depth2;

import kranji.common.base.DepthRegistry;
import kranji.zi.ComposedZi;

import java.util.List;

/**
 * Depth-2 sub-registry: holds every {@link ComposedZi} whose composition
 * tree has a maximum nesting depth of two (at least one child is itself
 * a depth-1 composition, all other descendants are singular).
 *
 * <p>Records live in {@link Depth2}; this registry exposes them through
 * the {@link DepthRegistry} contract so the per-depth modules form a
 * uniform pool for downstream callers.</p>
 */
public final class Depth2Registry implements DepthRegistry {

    public static final Depth2Registry INSTANCE = new Depth2Registry();

    private Depth2Registry() {}

    @Override public int depth() { return 2; }

    @Override public List<ComposedZi> zi() {
        return Depth2.ALL;
    }
}
