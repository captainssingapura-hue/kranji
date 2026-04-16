package kranji.common.depth2;

import kranji.zi.ComposedZi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Depth-2 composed characters: at least one direct component is itself
 * a depth-1 composition (i.e. a {@link kranji.zi.ComposedBlock} of singular blocks).
 *
 * <p>Characters are split into per-stroke-count files for maintainability.
 * This class aggregates all stroke files into a single {@link #ALL} list.</p>
 *
 * <p>Stroke files:</p>
 * <ul>
 *   <li>{@link Depth2Strokes6} — 6 strokes</li>
 *   <li>{@link Depth2Strokes7} — 7 strokes</li>
 *   <li>{@link Depth2StrokesHigh} — 9–21 strokes</li>
 * </ul>
 */
public final class Depth2 {

    private Depth2() {}

    /** All depth-2 composed characters, ordered by stroke count. */
    public static final List<ComposedZi> ALL;

    static {
        var list = new ArrayList<ComposedZi>();
        list.addAll(Depth2Strokes6.ALL);
        list.addAll(Depth2Strokes7.ALL);
        list.addAll(Depth2StrokesHigh.ALL);
        ALL = Collections.unmodifiableList(list);
    }
}
