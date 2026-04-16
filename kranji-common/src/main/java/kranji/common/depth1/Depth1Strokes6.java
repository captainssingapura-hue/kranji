package kranji.common.depth1;

import kranji.classification.EtymologicalCategory.*;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.body.BodyParts;
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.materials.Materials;
import kranji.singular.nature.NatureElements;
import kranji.singular.numbers.NumbersAndMeasure;
import kranji.singular.people.PeopleAndRoles;
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.structures.Structures;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.tools.ToolsAndVessels;
import kranji.singular.animals.Animals;
import kranji.singular.plants.PlantsAndAgriculture;
import kranji.component.basic.WoodFamily;
import kranji.pinyin.*;
import kranji.zi.*;
import kranji.zi.ComposedBlock.*;

import java.util.List;

import static kranji.component.basic.BasicComponents.*;

public final class Depth1Strokes6 {

    private Depth1Strokes6() {}

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    private static ZiChar lit(String s) { return new ZiChar.Literal(s); }
    private static ZiChar uni(String s) { return new ZiChar.Unicode(s); }

    // ── 6 strokes ──────────────────────────────────────────────────────

    /** 休 (xiū) — rest. LeftRight: 亻 + 木. */
    public static final ComposedZi XIU = new ComposedZi(
            lit("休"),
            List.of(py(Initial.X, Head.I, Body.O, Tail.VOWEL_U, Tone.FIRST)),
            6, 9, "",
            new LeftRight(DAN_REN_PANG, WoodFamily.MU),
            new CompoundIndicative("亻(person) + 木(tree) → person leaning on tree → rest")
    );

    /** 字 (zì) — character/word. TopBottom: 宀(semantic) + 子(phonetic). */
    public static final ComposedZi ZI = new ComposedZi(
            lit("字"),
            List.of(py(Initial.Z, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH)),
            6, 39, "",
            new TopBottom(BAO_GAI_TOU, PeopleAndRoles.ZI),
            new PhonoSemantic(BAO_GAI_TOU, PeopleAndRoles.ZI)
    );

    /** 刑 (xíng) — punishment. LeftRight: 开(phonetic) + 刂(semantic). */
    public static final ComposedZi XING_PUNISH = new ComposedZi(
            lit("刑"),
            List.of(py(Initial.X, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            6, 18, "",
            new LeftRight(ActionsAndStates.KAI, LI_DAO_PANG),
            new PhonoSemantic(LI_DAO_PANG, ActionsAndStates.KAI)
    );

    /** 动 (dòng) — move. LeftRight: 云(phonetic) + 力(semantic). */
    public static final ComposedZi DONG = new ComposedZi(
            lit("动"),
            List.of(py(Initial.D, Head.OPEN, Body.O, Tail.NG, Tone.FOURTH)),
            6, 19, "",
            new LeftRight(NatureElements.YUN, ToolsAndVessels.LI_PLOW),
            new PhonoSemantic(ToolsAndVessels.LI_PLOW, NatureElements.YUN)
    );

    /** 扛 (káng) — carry on shoulder. LeftRight: 扌(semantic) + 工(phonetic). */
    public static final ComposedZi KANG = new ComposedZi(
            lit("扛"),
            List.of(py(Initial.K, Head.OPEN, Body.A, Tail.NG, Tone.SECOND)),
            6, 64, "",
            new LeftRight(TI_SHOU_PANG, ActionsAndStates.GONG_WORK),
            new PhonoSemantic(TI_SHOU_PANG, ActionsAndStates.GONG_WORK)
    );

    /** 寺 (sì) — temple. TopBottom: 土(phonetic) + 寸(semantic). */
    public static final ComposedZi SI_TEMPLE = new ComposedZi(
            lit("寺"),
            List.of(py(Initial.S, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH)),
            6, 41, "",
            new TopBottom(NatureElements.TU, Structures.CUN_INCH),
            new PhonoSemantic(Structures.CUN_INCH, NatureElements.TU)
    );

    /** 吉 (jí) — auspicious. TopBottom: 士 + 口. */
    public static final ComposedZi JI_LUCKY = new ComposedZi(
            lit("吉"),
            List.of(py(Initial.J, Head.OPEN, Body.I, Tail.NONE, Tone.SECOND)),
            6, 30, "",
            new TopBottom(PeopleAndRoles.SHI, BodyParts.KOU),
            new CompoundIndicative("士(scholar/jade tablet) + 口(mouth) → auspicious proclamation")
    );

    public static final List<ComposedZi> ALL = List.of(
            XIU, ZI, XING_PUNISH, DONG, KANG, SI_TEMPLE, JI_LUCKY
    );
}
