package kranji.demos;

import kranji.classification.EtymologicalCategory.*;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.animals.Animals;
import kranji.singular.nature.NatureElements;
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.structures.Structures;
import kranji.pinyin.*;
import kranji.zi.*;
import kranji.zi.ComposedBlock.*;

import java.util.List;

import static kranji.component.basic.BasicComponents.*;

/**
 * Depth-5 composed characters: deeply nested compositions.
 *
 * <p>Depth is defined recursively as {@code max(depth of components) + 1},
 * where singular blocks have depth 0.</p>
 *
 * <pre>
 * 𰻝 (biáng) — depth breakdown:
 *   SemiEnclosureBottomLeft                          depth 5
 *   └─ TopMiddleBottom                               depth 4
 *      └─ LeftMiddleRight                            depth 3
 *         └─ TopBottom                               depth 2
 *            ├─ LeftMiddleRight(幺, 言, 幺)           depth 1
 *            └─ LeftMiddleRight(長, 馬, 長)           depth 1
 * </pre>
 */
public final class Depth5Examples {

    private Depth5Examples() {}

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    /**
     * 𰻝 (biáng) — the notoriously complex character from biángbiáng noodles (𰻝𰻝面).
     *
     * <p>58 strokes. A folk-coined character encoding the sound of noodle dough
     * slapped against a board. Structurally: 辶 wrapping a TMB of 穴 over
     * a complex center column over 心.</p>
     */
    public static final ComposedZi BIANG = new ComposedZi(
            "𰻝",
            List.of(py(Initial.B, Head.I, Body.A, Tail.NG, Tone.SECOND)),
            58, 162, "",
            new SemiEnclosureBottomLeft(
                    ZOU_ZHI_DI,                                     // 辶 — walk radical
                    new TopMiddleBottom(
                            Structures.XUE,                            // 穴 — cave / roof
                            new LeftMiddleRight(
                                NatureElements.YUE,                       // 月
                                new TopBottom(
                                        new LeftMiddleRight(SingularBlock.plain("幺"), ActionsAndStates.YAN_SPEECH, SingularBlock.plain("幺")),
                                        new LeftMiddleRight(SpaceAndDirection.CHANG, Animals.MA, SpaceAndDirection.CHANG)
                                ),
                                LI_DAO_PANG                              // 刂
                            ),
                            SingularBlock.plain("心")                  // 心 — heart
                    )
            ),
            new CompoundIndicative(
                    "folk-coined character for the sound biáng — "
                    + "the slapping noise of noodle dough pulled and struck against a board"
            )
    );

    /** All depth-5 examples. */
    public static final List<ComposedZi> ALL = List.of(BIANG);
}
