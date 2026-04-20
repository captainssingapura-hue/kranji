package kranji.common.depth4;

import kranji.common.base.DepthRegistry;
import kranji.zi.ComposedZi;

import java.util.List;

/**
 * Depth-4 sub-registry: holds every {@link ComposedZi} whose composition
 * tree has a maximum nesting depth of four.
 */
public final class Depth4Registry implements DepthRegistry {

    public static final Depth4Registry INSTANCE = new Depth4Registry();

    private Depth4Registry() {}

    @Override public int depth() { return 4; }

    @Override public List<ComposedZi> zi() {
        return Depth4.ALL;
    }
}
