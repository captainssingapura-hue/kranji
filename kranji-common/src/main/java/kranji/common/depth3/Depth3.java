package kranji.common.depth3;

import kranji.classification.EtymologicalCategory.*;
import kranji.common.depth1.Depth1;
import kranji.singular.animals.Animals;
import kranji.singular.structures.Structures;
import kranji.pinyin.*;
import kranji.zi.*;
import kranji.zi.ComposedBlock.*;

import java.util.List;

/**
 * Depth-3 composed characters: at least one direct component is itself
 * a depth-2 composition.
 *
 * <p>Depth is defined recursively as {@code max(depth of components) + 1},
 * where singular blocks have depth 0.</p>
 *
 * <p>Characters are organized by stroke count within this depth level.</p>
 */
public final class Depth3 {

    private Depth3() {}

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    // ── 20 strokes ─────────────────────────────────────────────────────

    /**
     * 魔 (mó) — demon/magic. SemiEnclosureUpperLeft: 广(wrapper) +
     * TopBottom(林, 鬼) as content.
     *
     * <pre>
     * 魔 — depth 3
     * └─ SemiEnclosureUpperLeft
     *    ├─ 广                           depth 0
     *    └─ TopBottom                    depth 2
     *       ├─ 林 LeftRight(木, 木)       depth 1
     *       └─ 鬼                        depth 0
     * </pre>
     */
    public static final ComposedZi MO_DEMON = new ComposedZi(
            "魔",
            List.of(py(Initial.M, Head.OPEN, Body.O, Tail.NONE, Tone.SECOND)),
            20, 194, "",
            new SemiEnclosureUpperLeft(Structures.GUANG,
                    new TopBottom(
                            Depth1.LIN.structure(),
                            Animals.GUI_GHOST)),
            new PhonoSemantic(Animals.GUI_GHOST,
                    new SemiEnclosureUpperLeft(Structures.GUANG, Depth1.LIN.structure()))
    );

    // ════════════════════════════════════════════════════════════════════

    /** All depth-3 composed characters, ordered by stroke count. */
    public static final List<ComposedZi> ALL = List.of(
            // 20 strokes
            MO_DEMON
    );
}
