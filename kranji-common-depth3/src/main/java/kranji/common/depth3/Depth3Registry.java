package kranji.common.depth3;

import kranji.common.base.DepthRegistry;
import kranji.zi.ComposedZi;

import java.util.List;

/**
 * Depth-3 sub-registry: holds every {@link ComposedZi} whose composition
 * tree has a maximum nesting depth of three.
 */
public final class Depth3Registry implements DepthRegistry {

    public static final Depth3Registry INSTANCE = new Depth3Registry();

    private Depth3Registry() {}

    @Override public int depth() { return 3; }

    @Override public List<ComposedZi> zi() {
        return Depth3.ALL;
    }
}
