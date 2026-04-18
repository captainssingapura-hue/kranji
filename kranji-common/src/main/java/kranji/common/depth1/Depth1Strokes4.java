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

public final class Depth1Strokes4 {

    private Depth1Strokes4() {}

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    private static ZiChar lit(String s) { return new ZiChar.Literal(s); }
    private static ZiChar uni(String s) { return new ZiChar.Unicode(s); }

    // ── 4 strokes ──────────────────────────────────────────────────────

    /** 孔 (kǒng) — hole/Confucius. LeftRight: 子 + 乚. */
    public static final ComposedZi KONG = new ComposedZi(
            lit("孔"),
            py(Initial.K, Head.OPEN, Body.O, Tail.NG, Tone.THIRD),
            4, 39, "",
            new LeftRight(PeopleAndRoles.ZI, RadicalComponents.YI_HOOK),
            new CompoundIndicative("子(child) + 乚(opening) → hole")
    );

    /** 元 (yuán) — origin, yuan. TopBottom: 二 + 儿. */
    public static final ComposedZi YUAN = new ComposedZi(
            uni("\u5143"),
            py(Initial.ZERO, Head.V, Body.A, Tail.N, Tone.SECOND),
            4, 10, "",
            new TopBottom(NumbersAndMeasure.ER, PeopleAndRoles.ER_CHILD),
            new CompoundIndicative("\u4E8C(two) + \u513F(person) \u2192 origin, first")
    );

    /** 公 (gōng) — public. TopBottom: 八 + 厶. */
    public static final ComposedZi GONG_PUBLIC = new ComposedZi(
            uni("\u516C"),
            py(Initial.G, Head.OPEN, Body.O, Tail.NG, Tone.FIRST),
            4, 12, "",
            new TopBottom(NumbersAndMeasure.BA, RadicalComponents.SI_PRIVATE),
            new CompoundIndicative("\u516B(divide) + \u53B6(private) \u2192 public")
    );

    /** 凶 (xiōng) — fierce, ominous. BottomThree: 凵 wraps 㐅. */
    public static final ComposedZi XIONG_FIERCE = new ComposedZi(
            uni("\u51F6"),
            py(Initial.X, Head.I, Body.O, Tail.NG, Tone.FIRST),
            4, 17, "",
            new SemiEnclosureBottomThree(XIONG_ZI_KUANG, RadicalComponents.WU_CROSS),
            new CompoundIndicative("\u51F5(pit) + \u3405(cross/wound) \u2192 fierce, ominous")
    );

    /** 冈 (gāng) — ridge, hill. TopThree: 冂 wraps 㐅. */
    public static final ComposedZi GANG_RIDGE = new ComposedZi(
            uni("\u5188"),
            py(Initial.G, Head.OPEN, Body.A, Tail.NG, Tone.FIRST),
            4, 13, "",
            new SemiEnclosureTopThree(TONG_ZI_KUANG, RadicalComponents.WU_CROSS),
            new CompoundIndicative("\u5182(border) + \u3405(cross marks) \u2192 ridge")
    );

    /** 仁 (rén) — benevolence. LeftRight: 亻+ 二. */
    public static final ComposedZi REN_BENEVOLENCE = new ComposedZi(
            uni("\u4EC1"),
            py(Initial.R, Head.OPEN, Body.E, Tail.N, Tone.SECOND),
            4, 9, "",
            new LeftRight(DAN_REN_PANG, NumbersAndMeasure.ER),
            new CompoundIndicative("\u4EBB(person) + \u4E8C(two) \u2192 benevolence between people")
    );

    /** 什 (shí) — ten, assorted. LeftRight: 亻+ 十. */
    public static final ComposedZi SHI_TEN = new ComposedZi(
            uni("\u4EC0"),
            py(Initial.SH, Head.OPEN, Body.NULL, Tail.NONE, Tone.SECOND),
            4, 9, "",
            new LeftRight(DAN_REN_PANG, NumbersAndMeasure.SHI),
            new PhonoSemantic(DAN_REN_PANG, NumbersAndMeasure.SHI)
    );

    /** 仆 (pú) — servant. LeftRight: 亻+ 卜. */
    public static final ComposedZi PU_SERVANT = new ComposedZi(
            uni("\u4EC6"),
            py(Initial.P, Head.U, Body.U, Tail.NONE, Tone.SECOND),
            4, 9, "",
            new LeftRight(DAN_REN_PANG, ToolsAndVessels.BU_DIVINATION),
            new PhonoSemantic(DAN_REN_PANG, ToolsAndVessels.BU_DIVINATION)
    );

    /** 仇 (chóu) — hatred, enemy. LeftRight: 亻+ 九. */
    public static final ComposedZi CHOU_HATRED = new ComposedZi(
            uni("\u4EC7"),
            py(Initial.CH, Head.OPEN, Body.O, Tail.VOWEL_U, Tone.SECOND),
            4, 9, "",
            new LeftRight(DAN_REN_PANG, NumbersAndMeasure.JIU),
            new PhonoSemantic(DAN_REN_PANG, NumbersAndMeasure.JIU)
    );

    /** 仍 (réng) — still, yet. LeftRight: 亻+ 乃. */
    public static final ComposedZi RENG = new ComposedZi(
            uni("\u4ECD"),
            py(Initial.R, Head.OPEN, Body.E, Tail.NG, Tone.SECOND),
            4, 9, "",
            new LeftRight(DAN_REN_PANG, AbstractConcepts.NAI),
            new PhonoSemantic(DAN_REN_PANG, AbstractConcepts.NAI)
    );

    /** 仅 (jǐn) — only, merely. LeftRight: 亻+ 又. */
    public static final ComposedZi JIN_ONLY = new ComposedZi(
            uni("\u4EC5"),
            py(Initial.J, Head.OPEN, Body.I, Tail.N, Tone.THIRD),
            4, 9, "",
            new LeftRight(DAN_REN_PANG, ActionsAndStates.YOU_AGAIN),
            new PhonoSemantic(DAN_REN_PANG, ActionsAndStates.YOU_AGAIN)
    );

    /** 从 (cóng) — from, follow. LeftRight: 人 + 人. */
    public static final ComposedZi CONG_FROM = new ComposedZi(
            uni("\u4ECE"),
            py(Initial.C, Head.OPEN, Body.O, Tail.NG, Tone.SECOND),
            4, 9, "",
            new LeftRight(PeopleAndRoles.REN, PeopleAndRoles.REN),
            new CompoundIndicative("\u4EBA(person) + \u4EBA(person) \u2192 follow, from")
    );

    /** 切 (qiē) — cut. LeftRight: 七(phonetic) + 刀(semantic). */
    public static final ComposedZi QIE_CUT = new ComposedZi(
            uni("\u5207"),
            py(Initial.Q, Head.I, Body.E_CARON, Tail.NONE, Tone.FIRST),
            4, 18, "",
            new LeftRight(NumbersAndMeasure.QI, ToolsAndVessels.DAO),
            new PhonoSemantic(ToolsAndVessels.DAO, NumbersAndMeasure.QI)
    );

    /** 分 (fēn) — divide, separate. TopBottom: 八 + 刀. */
    public static final ComposedZi FEN = new ComposedZi(
            uni("\u5206"),
            py(Initial.F, Head.OPEN, Body.E, Tail.N, Tone.FIRST),
            4, 18, "",
            new TopBottom(NumbersAndMeasure.BA, ToolsAndVessels.DAO),
            new CompoundIndicative("\u516B(divide) + \u5200(knife) \u2192 divide, separate")
    );

    /** 双 (shuāng) — pair, double. LeftRight: 又 + 又. */
    public static final ComposedZi SHUANG = new ComposedZi(
            uni("\u53CC"),
            py(Initial.SH, Head.U, Body.A, Tail.NG, Tone.FIRST),
            4, 29, "",
            new LeftRight(ActionsAndStates.YOU_AGAIN, ActionsAndStates.YOU_AGAIN),
            new CompoundIndicative("\u53C8(hand) + \u53C8(hand) \u2192 pair, double")
    );

    /** 忆 (yì) — recall, memory. LeftRight: 忄(semantic) + 乙(phonetic). */
    public static final ComposedZi YI_RECALL = new ComposedZi(
            uni("\u5FC6"),
            py(Initial.ZERO, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH),
            4, 61, "",
            new LeftRight(SHU_XIN_PANG, AbstractConcepts.YI_SECOND),
            new PhonoSemantic(SHU_XIN_PANG, AbstractConcepts.YI_SECOND)
    );

    /** 订 (dìng) — subscribe, book. LeftRight: 讠(semantic) + 丁(phonetic). */
    public static final ComposedZi DING_SUBSCRIBE = new ComposedZi(
            uni("\u8BA2"),
            py(Initial.D, Head.OPEN, Body.I, Tail.NG, Tone.FOURTH),
            4, 149, "",
            new LeftRight(YAN_ZI_PANG, ToolsAndVessels.DING_NAIL),
            new PhonoSemantic(YAN_ZI_PANG, ToolsAndVessels.DING_NAIL)
    );

    /** 计 (jì) — plan, count. LeftRight: 讠(semantic) + 十(phonetic). */
    public static final ComposedZi JI_PLAN = new ComposedZi(
            uni("\u8BA1"),
            py(Initial.J, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH),
            4, 149, "",
            new LeftRight(YAN_ZI_PANG, NumbersAndMeasure.SHI),
            new PhonoSemantic(YAN_ZI_PANG, NumbersAndMeasure.SHI)
    );

    /** 认 (rèn) — recognize. LeftRight: 讠(semantic) + 人(phonetic). */
    public static final ComposedZi REN_RECOGNIZE = new ComposedZi(
            uni("\u8BA4"),
            py(Initial.R, Head.OPEN, Body.E, Tail.N, Tone.FOURTH),
            4, 149, "",
            new LeftRight(YAN_ZI_PANG, PeopleAndRoles.REN),
            new PhonoSemantic(YAN_ZI_PANG, PeopleAndRoles.REN)
    );

    /** 引 (yǐn) — pull, guide. LeftRight: 弓 + 丨. */
    public static final ComposedZi YIN_PULL = new ComposedZi(
            uni("\u5F15"),
            py(Initial.ZERO, Head.OPEN, Body.I, Tail.N, Tone.THIRD),
            4, 57, "",
            new LeftRight(ToolsAndVessels.GONG, RadicalComponents.GUN),
            new CompoundIndicative("\u5F13(bow) + \u4E28(string) \u2192 draw, pull")
    );

    /** 扎 (zhā) — tie, prick. LeftRight: 扌(semantic) + 乚(phonetic). */
    public static final ComposedZi ZHA_TIE = new ComposedZi(
            uni("\u624E"),
            py(Initial.ZH, Head.OPEN, Body.A, Tail.NONE, Tone.FIRST),
            4, 64, "",
            new LeftRight(TI_SHOU_PANG, RadicalComponents.YI_HOOK),
            new PhonoSemantic(TI_SHOU_PANG, RadicalComponents.YI_HOOK)
    );

    /** 艺 (yì) — art, skill. TopBottom: 艹(semantic) + 乙(phonetic). */
    public static final ComposedZi YI_ART = new ComposedZi(
            uni("\u827A"),
            py(Initial.ZERO, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH),
            4, 140, "",
            new TopBottom(CAO_ZI_TOU, AbstractConcepts.YI_SECOND),
            new PhonoSemantic(CAO_ZI_TOU, AbstractConcepts.YI_SECOND)
    );

    /** 厅 (tīng) — hall. SemiEnclosureUpperLeft: 厂 + 丁. */
    public static final ComposedZi TING_HALL = new ComposedZi(
            uni("\u5385"),
            py(Initial.T, Head.OPEN, Body.I, Tail.NG, Tone.FIRST),
            4, 27, "",
            new SemiEnclosureUpperLeft(Structures.CHANG_FACTORY, ToolsAndVessels.DING_NAIL),
            new PhonoSemantic(Structures.CHANG_FACTORY, ToolsAndVessels.DING_NAIL)
    );

    /** 历 (lì) — history, experience. SemiEnclosureUpperLeft: 厂 + 力. */
    public static final ComposedZi LI_HISTORY = new ComposedZi(
            uni("\u5386"),
            py(Initial.L, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH),
            4, 27, "",
            new SemiEnclosureUpperLeft(Structures.CHANG_FACTORY, ToolsAndVessels.LI_PLOW),
            new PhonoSemantic(Structures.CHANG_FACTORY, ToolsAndVessels.LI_PLOW)
    );

    /** 反 (fǎn) — oppose, reverse. SemiEnclosureUpperLeft: 厂 + 又. */
    public static final ComposedZi FAN_OPPOSE = new ComposedZi(
            uni("\u53CD"),
            py(Initial.F, Head.OPEN, Body.A, Tail.N, Tone.THIRD),
            4, 29, "",
            new SemiEnclosureUpperLeft(Structures.CHANG_FACTORY, ActionsAndStates.YOU_AGAIN),
            new CompoundIndicative("\u5382(cliff) + \u53C8(hand) \u2192 turn over, oppose")
    );

    /** 劝 (quàn) — advise, urge. LeftRight: 又 + 力. */
    public static final ComposedZi QUAN_ADVISE = new ComposedZi(
            uni("\u529D"),
            py(Initial.Q, Head.V, Body.A, Tail.N, Tone.FOURTH),
            4, 19, "",
            new LeftRight(ActionsAndStates.YOU_AGAIN, ToolsAndVessels.LI_PLOW),
            new CompoundIndicative("\u53C8(hand) + \u529B(strength) \u2192 urge, advise")
    );

    /** 队 (duì) — team, group. LeftRight: 阝(semantic) + 人(phonetic). */
    public static final ComposedZi DUI_TEAM = new ComposedZi(
            uni("\u961F"),
            py(Initial.D, Head.U, Body.E, Tail.VOWEL_I, Tone.FOURTH),
            4, 170, "",
            new LeftRight(ZUO_ER_PANG, PeopleAndRoles.REN),
            new PhonoSemantic(ZUO_ER_PANG, PeopleAndRoles.REN)
    );

    public static final List<ComposedZi> ALL = List.of(
            KONG, YUAN, GONG_PUBLIC, XIONG_FIERCE, GANG_RIDGE,
            REN_BENEVOLENCE, SHI_TEN, PU_SERVANT, CHOU_HATRED, RENG, JIN_ONLY,
            CONG_FROM, QIE_CUT, FEN, SHUANG,
            YI_RECALL, DING_SUBSCRIBE, JI_PLAN, REN_RECOGNIZE,
            YIN_PULL, ZHA_TIE, YI_ART,
            TING_HALL, LI_HISTORY, FAN_OPPOSE, QUAN_ADVISE, DUI_TEAM
    );
}
