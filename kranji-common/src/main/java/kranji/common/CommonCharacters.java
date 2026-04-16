package kranji.common;

import kranji.common.depth1.Depth1;
import kranji.common.depth2.Depth2;
import kranji.common.depth3.Depth3;
import kranji.common.depth4.Depth4;
import kranji.common.depth5.Depth5;
import kranji.zi.ComposedZi;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregates all common {@link ComposedZi} instances across depth levels.
 *
 * <p>Composed characters are grouped by structural depth — the maximum
 * nesting level of their composition tree. Singular characters (depth 0)
 * live in the {@code kranji-singulars} module and are not duplicated here.</p>
 *
 * @see Depth1
 * @see Depth2
 * @see Depth3
 * @see Depth4
 * @see Depth5
 */
public final class CommonCharacters {

    private CommonCharacters() {}

    /** All common composed characters, across all depth levels. */
    public static final List<ComposedZi> ALL;

    static {
        var all = new ArrayList<ComposedZi>();
        all.addAll(Depth1.ALL);
        all.addAll(Depth2.ALL);
        all.addAll(Depth3.ALL);
        all.addAll(Depth4.ALL);
        all.addAll(Depth5.ALL);
        ALL = List.copyOf(all);
    }
}
