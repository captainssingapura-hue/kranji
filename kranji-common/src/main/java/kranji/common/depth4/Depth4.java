package kranji.common.depth4;

import kranji.zi.ComposedZi;

import java.util.List;

/**
 * Depth-4 composed characters: at least one direct component is itself
 * a depth-3 composition.
 *
 * <p>Depth is defined recursively as {@code max(depth of components) + 1},
 * where singular blocks have depth 0.</p>
 */
public final class Depth4 {

    private Depth4() {}

    /** All depth-4 composed characters. */
    public static final List<ComposedZi> ALL = List.of();
}
