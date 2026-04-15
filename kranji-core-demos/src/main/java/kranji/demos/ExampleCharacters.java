package kranji.demos;

import kranji.zi.ComposedZi;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregates all example {@link ComposedZi} instances across depth levels.
 *
 * <p>Composed characters are grouped by structural depth — the maximum
 * nesting level of their composition tree. Singular characters (depth 0)
 * live in the {@code kranji-singulars} module and are not duplicated here.</p>
 *
 * @see Depth1Examples
 * @see Depth2Examples
 * @see Depth5Examples
 */
public final class ExampleCharacters {

    private ExampleCharacters() {}

    /** All example composed characters, across all depth levels. */
    public static final List<ComposedZi> ALL;

    static {
        var all = new ArrayList<ComposedZi>();
        all.addAll(Depth1Examples.ALL);
        all.addAll(Depth2Examples.ALL);
        all.addAll(Depth5Examples.ALL);
        ALL = List.copyOf(all);
    }
}
