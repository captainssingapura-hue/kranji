package kranji.common.depth2;

import kranji.classification.EtymologicalCategory.*;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.animals.Animals;
import kranji.singular.body.BodyParts;
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.nature.NatureElements;
import kranji.singular.numbers.NumbersAndMeasure;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.tools.ToolsAndVessels;
import kranji.common.depth1.Depth1;
import kranji.component.basic.WoodFamily;
import kranji.singular.materials.Materials;
import kranji.pinyin.*;
import kranji.zi.*;
import kranji.zi.ComposedBlock.*;

import java.util.List;

import static kranji.component.basic.BasicComponents.*;

/**
 * Depth-2 composed characters: at least one direct component is itself
 * a depth-1 composition (i.e. a {@link ComposedBlock} of singular blocks).
 *
 * <p>Depth is defined recursively as {@code max(depth of components) + 1},
 * where singular blocks have depth 0.</p>
 *
 * <p>Characters are organized by stroke count within this depth level.</p>
 */
public final class Depth2 {

    private Depth2() {}

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    // ── 6 strokes ──────────────────────────────────────────────────────

    /** 式 (shì) — style/formula. LeftRight: TopBottom(一, 工) on left + 弋 on right. */
    public static final ComposedZi SHI_STYLE = new ComposedZi(
            "式",
            List.of(py(Initial.SH, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH)),
            6, 56, "",
            new LeftRight(
                    new TopBottom(NumbersAndMeasure.YI, ActionsAndStates.GONG_WORK),
                    ToolsAndVessels.YI_SHOOT),
            new PhonoSemantic(ActionsAndStates.GONG_WORK, ToolsAndVessels.YI_SHOOT)
    );

    // ── 9 strokes ──────────────────────────────────────────────────────

    /** 品 (pǐn) — product/taste. TopBottom: 口(top) + 口口(bottom). */
    public static final ComposedZi PIN = new ComposedZi(
            "品",
            List.of(py(Initial.P, Head.OPEN, Body.I, Tail.N, Tone.THIRD)),
            9, 30, "",
            new TopBottom(BodyParts.KOU, new LeftRight(BodyParts.KOU, BodyParts.KOU)),
            new CompoundIndicative("口(mouth) \u00d7 3 \u2192 many mouths \u2192 tasting, judging quality")
    );

    // ── 11 strokes ─────────────────────────────────────────────────────

    /** 萌 (méng) — sprout. TopBottom: 艹(semantic) + 明(phonetic, depth-1). */
    public static final ComposedZi MENG = new ComposedZi(
            "萌",
            List.of(py(Initial.M, Head.OPEN, Body.E, Tail.NG, Tone.SECOND)),
            11, 140, "",
            new TopBottom(CAO_ZI_TOU, Depth1.MING.structure()),
            new PhonoSemantic(CAO_ZI_TOU, Depth1.MING.structure())
    );

    // ── 12 strokes ─────────────────────────────────────────────────────

    /** 雅 (yǎ) — elegant/refined. LeftRight: 牙(phonetic) + 佳(semantic, decomposed as 亻+圭). */
    public static final ComposedZi YA = new ComposedZi(
            "雅",
            List.of(py(Initial.ZERO, Head.I, Body.A, Tail.NONE, Tone.THIRD)),
            12, 172, "",
            new LeftRight(BodyParts.YA,
                    new LeftRight(DAN_REN_PANG, Materials.GUI_TABLET)),
            new PhonoSemantic(AbstractConcepts.JIA_FINE, BodyParts.YA)
    );

    /** 森 (sēn) — dense forest. TopBottom: 木(top) + 林(bottom, depth-1). */
    public static final ComposedZi SEN = new ComposedZi(
            "森",
            List.of(py(Initial.S, Head.OPEN, Body.E, Tail.N, Tone.FIRST)),
            12, 75, "",
            new TopBottom(WoodFamily.MU, Depth1.LIN.structure()),
            new CompoundIndicative("木(tree) \u00d7 3 \u2192 dense forest")
    );

    // ── 13 strokes ─────────────────────────────────────────────────────

    /** 脚 (jiǎo) — foot/leg. LeftRight: 月(semantic) + 却(phonetic, LeftRight: 去+卩). */
    public static final ComposedZi JIAO_FOOT = new ComposedZi(
            "脚",
            List.of(py(Initial.J, Head.I, Body.A, Tail.VOWEL_U, Tone.THIRD)),
            13, 130, "",
            new LeftRight(NatureElements.YUE,
                    new LeftRight(ActionsAndStates.QU, RadicalComponents.JIE_SEAL)),
            new PhonoSemantic(NatureElements.YUE,
                    new LeftRight(ActionsAndStates.QU, RadicalComponents.JIE_SEAL))
    );

    // ── 16 strokes ─────────────────────────────────────────────────────

    /** 器 (qì) — vessel/utensil. TMB: 口口(top) + 犬(middle) + 口口(bottom). */
    public static final ComposedZi QI_VESSEL = new ComposedZi(
            "器",
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH)),
            16, 30, "",
            new TopMiddleBottom(
                    new LeftRight(BodyParts.KOU, BodyParts.KOU),
                    Animals.QUAN,
                    new LeftRight(BodyParts.KOU, BodyParts.KOU)
            ),
            new CompoundIndicative("四口(four mouths/openings) + 犬(dog as guard) \u2192 vessel, instrument")
    );

    // ── 21 strokes ─────────────────────────────────────────────────────

    /** 蠢 (chǔn) — stupid/wriggling. TopBottom: 春(top) + 虫虫(bottom). */
    public static final ComposedZi CHUN_STUPID = new ComposedZi(
            "蠢",
            List.of(py(Initial.CH, Head.U, Body.E, Tail.N, Tone.THIRD)),
            21, 142, "",
            new TopBottom(NatureElements.CHUN_SPRING,
                    new LeftRight(Animals.CHONG, Animals.CHONG)),
            new CompoundIndicative("春(spring, insects stir) + 虫虫(insects) \u2192 wriggling \u2192 stupid")
    );

    // ════════════════════════════════════════════════════════════════════

    /** All depth-2 composed characters, ordered by stroke count. */
    public static final List<ComposedZi> ALL = List.of(
            // 6 strokes
            SHI_STYLE,
            // 9 strokes
            PIN,
            // 11 strokes
            MENG,
            // 12 strokes
            YA, SEN,
            // 13 strokes
            JIAO_FOOT,
            // 16 strokes
            QI_VESSEL,
            // 21 strokes
            CHUN_STUPID
    );
}
