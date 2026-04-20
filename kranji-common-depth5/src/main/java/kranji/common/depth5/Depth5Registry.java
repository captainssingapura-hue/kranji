package kranji.common.depth5;

import kranji.common.base.DepthRegistry;
import kranji.zi.ComposedZi;

import java.util.List;

/**
 * Depth-5 sub-registry: holds every {@link ComposedZi} whose composition
 * tree has a maximum nesting depth of five.
 */
public final class Depth5Registry implements DepthRegistry {

    public static final Depth5Registry INSTANCE = new Depth5Registry();

    private Depth5Registry() {}

    @Override public int depth() { return 5; }

    @Override public List<ComposedZi> zi() {
        return Depth5.ALL;
    }
}
