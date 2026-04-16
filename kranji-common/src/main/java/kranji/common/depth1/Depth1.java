package kranji.common.depth1;

import kranji.zi.ComposedZi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Depth-1 composed characters: every leaf component is a singular block (depth 0).
 *
 * <p>Characters are split into per-stroke-count files for maintainability.
 * This class aggregates all stroke files into a single {@link #ALL} list.</p>
 *
 * <p>Stroke files:</p>
 * <ul>
 *   <li>{@link Depth1Strokes3} — 3 strokes</li>
 *   <li>{@link Depth1Strokes4} — 4 strokes</li>
 *   <li>{@link Depth1Strokes5} — 5 strokes</li>
 *   <li>{@link Depth1Strokes6} — 6 strokes</li>
 *   <li>{@link Depth1Strokes7} — 7 strokes</li>
 *   <li>{@link Depth1StrokesHigh} — 8–13 strokes</li>
 * </ul>
 */
public final class Depth1 {

    private Depth1() {}

    /** All depth-1 composed characters, ordered by stroke count. */
    public static final List<ComposedZi> ALL;

    static {
        var list = new ArrayList<ComposedZi>();
        list.addAll(Depth1Strokes3.ALL);
        list.addAll(Depth1Strokes4.ALL);
        list.addAll(Depth1Strokes5.ALL);
        list.addAll(Depth1Strokes6.ALL);
        list.addAll(Depth1Strokes7.ALL);
        list.addAll(Depth1StrokesHigh.ALL);
        ALL = Collections.unmodifiableList(list);
    }
}
