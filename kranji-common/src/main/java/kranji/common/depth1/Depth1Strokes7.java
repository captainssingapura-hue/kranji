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

import kranji.common.CommonBlocks;

import java.util.List;

import static kranji.component.basic.BasicComponents.*;

public final class Depth1Strokes7 {

    private Depth1Strokes7() {}

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    private static ZiChar lit(String s) { return new ZiChar.Literal(s); }
    private static ZiChar uni(String s) { return new ZiChar.Unicode(s); }

    // ── 7 strokes ──────────────────────────────────────────────────────

    /** 花 (huā) — flower. TopBottom: 艹(semantic) + 化(phonetic). */
    public static final ComposedZi HUA = new ComposedZi(
            lit("花"),
            py(Initial.H, Head.U, Body.A, Tail.NONE, Tone.FIRST),
            7, 140, "",
            new TopBottom(CAO_ZI_TOU, ActionsAndStates.HUA_CHANGE),
            new PhonoSemantic(CAO_ZI_TOU, ActionsAndStates.HUA_CHANGE)
    );

    /** 弄 (nòng) — play with, handle. TopBottom: 王(semantic) + 廾(semantic). */
    public static final ComposedZi NONG = new ComposedZi(
            uni("\u5F04"), py(Initial.N, Head.OPEN, Body.O, Tail.NG, Tone.FOURTH),
            7, 55, "", new TopBottom(PeopleAndRoles.WANG, RadicalComponents.GONG_CLASP),
            new CompoundIndicative("\u738B(jade) + \u5EFE(two hands) \u2192 handle jade \u2192 play with"));

    /** 形 (xíng) — shape, form. LeftRight: 开(semantic) + 彡(phonetic). */
    public static final ComposedZi XING_SHAPE = new ComposedZi(
            uni("\u5F62"), py(Initial.X, Head.OPEN, Body.I, Tail.NG, Tone.SECOND),
            7, 59, "", new LeftRight(ActionsAndStates.KAI, RadicalComponents.SHAN_HAIR),
            new PhonoSemantic(ActionsAndStates.KAI, RadicalComponents.SHAN_HAIR));

    /** 进 (jìn) — advance, enter. SemiEnclosureBottomLeft: 辶 + 井. */
    public static final ComposedZi JIN_ADVANCE = new ComposedZi(
            uni("\u8FDB"), py(Initial.J, Head.I, Body.NULL, Tail.N, Tone.FOURTH),
            7, 162, "", new SemiEnclosureBottomLeft(ZOU_ZHI_DI, NatureElements.JING),
            new PhonoSemantic(ZOU_ZHI_DI, NatureElements.JING));

    /** 戒 (jiè) — warn, abstain. TopBottom: 戈 + 廾. */
    public static final ComposedZi JIE_WARN = new ComposedZi(
            uni("\u6212"), py(Initial.J, Head.I, Body.E, Tail.NONE, Tone.FOURTH),
            7, 62, "", new TopBottom(ToolsAndVessels.GE, RadicalComponents.GONG_CLASP),
            new CompoundIndicative("\u6208(weapon) + \u5EFE(two hands) \u2192 guard/warn"));

    /** 吞 (tūn) — swallow. TopBottom: 天 + 口. */
    public static final ComposedZi TUN_SWALLOW = new ComposedZi(
            uni("\u541E"), py(Initial.T, Head.U, Body.NULL, Tail.N, Tone.FIRST),
            7, 30, "", new TopBottom(NatureElements.TIAN_SKY, BodyParts.KOU),
            new CompoundIndicative("\u5929(heaven) + \u53E3(mouth) \u2192 swallow"));

    /** 违 (wéi) — violate, go against. SemiEnclosureBottomLeft: 辶 + 韦. */
    public static final ComposedZi WEI_VIOLATE = new ComposedZi(
            uni("\u8FDD"), py(Initial.ZERO, Head.U, Body.E, Tail.VOWEL_I, Tone.SECOND),
            7, 162, "", new SemiEnclosureBottomLeft(ZOU_ZHI_DI, Materials.WEI_LEATHER),
            new PhonoSemantic(ZOU_ZHI_DI, Materials.WEI_LEATHER));

    /** 运 (yùn) — transport, luck. SemiEnclosureBottomLeft: 辶 + 云. */
    public static final ComposedZi YUN_TRANSPORT = new ComposedZi(
            uni("\u8FD0"), py(Initial.ZERO, Head.U, Body.NULL, Tail.N, Tone.FOURTH),
            7, 162, "", new SemiEnclosureBottomLeft(ZOU_ZHI_DI, NatureElements.YUN),
            new PhonoSemantic(ZOU_ZHI_DI, NatureElements.YUN));

    /** 扶 (fú) — support. LeftRight: 扌 + 夫. */
    public static final ComposedZi FU_SUPPORT = new ComposedZi(
            uni("\u6276"), py(Initial.F, Head.U, Body.NULL, Tail.NONE, Tone.SECOND),
            7, 64, "", new LeftRight(TI_SHOU_PANG, PeopleAndRoles.FU_HUSBAND),
            new PhonoSemantic(TI_SHOU_PANG, PeopleAndRoles.FU_HUSBAND));

    /** 抚 (fǔ) — stroke, comfort. LeftRight: 扌 + 无. */
    public static final ComposedZi FU_STROKE = new ComposedZi(
            uni("\u629A"), py(Initial.F, Head.U, Body.NULL, Tail.NONE, Tone.THIRD),
            7, 64, "", new LeftRight(TI_SHOU_PANG, AbstractConcepts.WU_NONE),
            new PhonoSemantic(TI_SHOU_PANG, AbstractConcepts.WU_NONE));

    /** 坛 (tán) — altar, arena. LeftRight: 土 + 云. */
    public static final ComposedZi TAN_ALTAR = new ComposedZi(
            uni("\u575B"), py(Initial.T, Head.OPEN, Body.A, Tail.N, Tone.SECOND),
            7, 32, "", new LeftRight(NatureElements.TU, NatureElements.YUN),
            new PhonoSemantic(NatureElements.TU, NatureElements.YUN));

    /** 技 (jì) — skill, technique. LeftRight: 扌 + 支. */
    public static final ComposedZi JI_SKILL = new ComposedZi(
            uni("\u6280"), py(Initial.J, Head.I, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 64, "", new LeftRight(TI_SHOU_PANG, ActionsAndStates.ZHI_BRANCH),
            new PhonoSemantic(TI_SHOU_PANG, ActionsAndStates.ZHI_BRANCH));

    /** 坏 (huài) — bad, broken. LeftRight: 土 + 不. */
    public static final ComposedZi HUAI = new ComposedZi(
            uni("\u574F"), py(Initial.H, Head.U, Body.A, Tail.VOWEL_I, Tone.FOURTH),
            7, 32, "", new LeftRight(NatureElements.TU, AbstractConcepts.BU),
            new PhonoSemantic(NatureElements.TU, AbstractConcepts.BU));

    /** 扰 (rǎo) — disturb. LeftRight: 扌 + 尤. */
    public static final ComposedZi RAO = new ComposedZi(
            uni("\u6270"), py(Initial.R, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.THIRD),
            7, 64, "", new LeftRight(TI_SHOU_PANG, PeopleAndRoles.YOU_ESPECIALLY),
            new PhonoSemantic(TI_SHOU_PANG, PeopleAndRoles.YOU_ESPECIALLY));

    /** 拒 (jù) — refuse, resist. LeftRight: 扌 + 巨. */
    public static final ComposedZi JU_REFUSE = new ComposedZi(
            uni("\u62D2"), py(Initial.J, Head.U, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 64, "", new LeftRight(TI_SHOU_PANG, Structures.JU),
            new PhonoSemantic(TI_SHOU_PANG, Structures.JU));

    /** 找 (zhǎo) — look for. LeftRight: 扌 + 戈. */
    public static final ComposedZi ZHAO_FIND = new ComposedZi(
            uni("\u627E"), py(Initial.ZH, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.THIRD),
            7, 64, "", new LeftRight(TI_SHOU_PANG, ToolsAndVessels.GE),
            new PhonoSemantic(TI_SHOU_PANG, ToolsAndVessels.GE));

    /** 批 (pī) — criticize, batch. LeftRight: 扌 + 比. */
    public static final ComposedZi PI_BATCH = new ComposedZi(
            uni("\u6279"), py(Initial.P, Head.I, Body.NULL, Tail.NONE, Tone.FIRST),
            7, 64, "", new LeftRight(TI_SHOU_PANG, ActionsAndStates.BI_COMPARE),
            new PhonoSemantic(TI_SHOU_PANG, ActionsAndStates.BI_COMPARE));

    /** 扯 (chě) — pull, tear. LeftRight: 扌 + 止. */
    public static final ComposedZi CHE_PULL = new ComposedZi(
            uni("\u626F"), py(Initial.CH, Head.OPEN, Body.E, Tail.NONE, Tone.THIRD),
            7, 64, "", new LeftRight(TI_SHOU_PANG, ActionsAndStates.ZHI_STOP),
            new PhonoSemantic(TI_SHOU_PANG, ActionsAndStates.ZHI_STOP));

    /** 址 (zhǐ) — site, address. LeftRight: 土 + 止. */
    public static final ComposedZi ZHI_SITE = new ComposedZi(
            uni("\u5740"), py(Initial.ZH, Head.OPEN, Body.NULL, Tail.NONE, Tone.THIRD),
            7, 32, "", new LeftRight(NatureElements.TU, ActionsAndStates.ZHI_STOP),
            new PhonoSemantic(NatureElements.TU, ActionsAndStates.ZHI_STOP));

    /** 抄 (chāo) — copy. LeftRight: 扌 + 少. */
    public static final ComposedZi CHAO_COPY = new ComposedZi(
            uni("\u6284"), py(Initial.CH, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.FIRST),
            7, 64, "", new LeftRight(TI_SHOU_PANG, PeopleAndRoles.SHAO),
            new PhonoSemantic(TI_SHOU_PANG, PeopleAndRoles.SHAO));

    /** 坝 (bà) — dam. LeftRight: 土 + 贝. */
    public static final ComposedZi BA_DAM = new ComposedZi(
            uni("\u575D"), py(Initial.B, Head.OPEN, Body.A, Tail.NONE, Tone.FOURTH),
            7, 32, "", new LeftRight(NatureElements.TU, Animals.BEI),
            new PhonoSemantic(NatureElements.TU, Animals.BEI));

    /** 贡 (gòng) — tribute, contribute. TopBottom: 工 + 贝. */
    public static final ComposedZi GONG_TRIBUTE = new ComposedZi(
            uni("\u8D21"), py(Initial.G, Head.OPEN, Body.O, Tail.NG, Tone.FOURTH),
            7, 154, "", new TopBottom(ActionsAndStates.GONG_WORK, Animals.BEI),
            new PhonoSemantic(Animals.BEI, ActionsAndStates.GONG_WORK));

    /** 攻 (gōng) — attack. LeftRight: 工 + 攵. */
    public static final ComposedZi GONG_ATTACK = new ComposedZi(
            uni("\u653B"), py(Initial.G, Head.OPEN, Body.O, Tail.NG, Tone.FIRST),
            7, 66, "", new LeftRight(ActionsAndStates.GONG_WORK, FAN_WEN_PANG),
            new PhonoSemantic(FAN_WEN_PANG, ActionsAndStates.GONG_WORK));

    /** 折 (zhé) — break, fold. LeftRight: 扌 + 斤. */
    public static final ComposedZi ZHE_FOLD = new ComposedZi(
            uni("\u6298"), py(Initial.ZH, Head.OPEN, Body.E, Tail.NONE, Tone.SECOND),
            7, 64, "", new LeftRight(TI_SHOU_PANG, ToolsAndVessels.JIN_AXE),
            new PhonoSemantic(TI_SHOU_PANG, ToolsAndVessels.JIN_AXE));

    /** 抓 (zhuā) — grab. LeftRight: 扌 + 爪. */
    public static final ComposedZi ZHUA_GRAB = new ComposedZi(
            uni("\u6293"), py(Initial.ZH, Head.U, Body.A, Tail.NONE, Tone.FIRST),
            7, 64, "", new LeftRight(TI_SHOU_PANG, BodyParts.ZHUA),
            new PhonoSemantic(TI_SHOU_PANG, BodyParts.ZHUA));

    /** 抢 (qiǎng) — grab, rob. LeftRight: 扌 + 仓. */
    public static final ComposedZi QIANG_ROB = new ComposedZi(
            uni("\u62A2"), py(Initial.Q, Head.I, Body.A, Tail.NG, Tone.THIRD),
            7, 64, "", new LeftRight(TI_SHOU_PANG, Structures.CANG),
            new PhonoSemantic(TI_SHOU_PANG, Structures.CANG));

    /** 孝 (xiào) — filial piety. TopBottom: 耂 + 子. */
    public static final ComposedZi XIAO_FILIAL = new ComposedZi(
            uni("\u5B5D"), py(Initial.X, Head.I, Body.A, Tail.VOWEL_U, Tone.FOURTH),
            7, 39, "", new TopBottom(RadicalComponents.LAO_TOP, PeopleAndRoles.ZI),
            new CompoundIndicative("\u8002(old) + \u5B50(child) \u2192 filial piety"));

    /** 均 (jūn) — equal, even. LeftRight: 土 + 匀. */
    public static final ComposedZi JUN_EQUAL = new ComposedZi(
            uni("\u5747"), py(Initial.J, Head.U, Body.NULL, Tail.N, Tone.FIRST),
            7, 32, "", new LeftRight(NatureElements.TU, AbstractConcepts.YUN_EVEN),
            new PhonoSemantic(NatureElements.TU, AbstractConcepts.YUN_EVEN));

    /** 抛 (pāo) — throw. LeftRight: 扌 + ⺈力. */
    public static final ComposedZi PAO_THROW = new ComposedZi(
            uni("\u629B"), py(Initial.P, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.FIRST),
            7, 64, "", new LeftRight(TI_SHOU_PANG, CommonBlocks.DAO_LI),
            new PhonoSemantic(TI_SHOU_PANG, ToolsAndVessels.LI_PLOW));

    /** 投 (tóu) — throw, cast. LeftRight: 扌 + 殳. */
    public static final ComposedZi TOU_THROW = new ComposedZi(
            uni("\u6295"), py(Initial.T, Head.OPEN, Body.O, Tail.VOWEL_U, Tone.SECOND),
            7, 64, "", new LeftRight(TI_SHOU_PANG, RadicalComponents.SHU_LANCE),
            new PhonoSemantic(TI_SHOU_PANG, RadicalComponents.SHU_LANCE));

    /** 坟 (fén) — grave, tomb. LeftRight: 土 + 文. */
    public static final ComposedZi FEN_GRAVE = new ComposedZi(
            uni("\u575F"), py(Initial.F, Head.OPEN, Body.E, Tail.N, Tone.SECOND),
            7, 32, "", new LeftRight(NatureElements.TU, PeopleAndRoles.WEN),
            new PhonoSemantic(NatureElements.TU, PeopleAndRoles.WEN));

    /** 抗 (kàng) — resist. LeftRight: 扌 + 亢. */
    public static final ComposedZi KANG_RESIST = new ComposedZi(
            uni("\u6297"), py(Initial.K, Head.OPEN, Body.A, Tail.NG, Tone.FOURTH),
            7, 64, "", new LeftRight(TI_SHOU_PANG, RadicalComponents.KANG),
            new PhonoSemantic(TI_SHOU_PANG, RadicalComponents.KANG));

    /** 坑 (kēng) — pit, hole. LeftRight: 土 + 亢. */
    public static final ComposedZi KENG = new ComposedZi(
            uni("\u5751"), py(Initial.K, Head.OPEN, Body.E, Tail.NG, Tone.FIRST),
            7, 32, "", new LeftRight(NatureElements.TU, RadicalComponents.KANG),
            new PhonoSemantic(NatureElements.TU, RadicalComponents.KANG));

    /** 坊 (fāng) — lane, workshop. LeftRight: 土 + 方. */
    public static final ComposedZi FANG_LANE = new ComposedZi(
            uni("\u574A"), py(Initial.F, Head.OPEN, Body.A, Tail.NG, Tone.FIRST),
            7, 32, "", new LeftRight(NatureElements.TU, SpaceAndDirection.FANG),
            new PhonoSemantic(NatureElements.TU, SpaceAndDirection.FANG));

    /** 抖 (dǒu) — tremble. LeftRight: 扌 + 斗. */
    public static final ComposedZi DOU_TREMBLE = new ComposedZi(
            uni("\u6296"), py(Initial.D, Head.OPEN, Body.O, Tail.VOWEL_U, Tone.THIRD),
            7, 64, "", new LeftRight(TI_SHOU_PANG, ToolsAndVessels.DOU),
            new PhonoSemantic(TI_SHOU_PANG, ToolsAndVessels.DOU));

    /** 护 (hù) — protect. LeftRight: 扌 + 户. */
    public static final ComposedZi HU_PROTECT = new ComposedZi(
            uni("\u62A4"), py(Initial.H, Head.U, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 64, "", new LeftRight(TI_SHOU_PANG, Structures.HU),
            new PhonoSemantic(TI_SHOU_PANG, Structures.HU));

    /** 志 (zhì) — ambition, will. TopBottom: 士 + 心. */
    public static final ComposedZi ZHI_WILL = new ComposedZi(
            uni("\u5FD7"), py(Initial.ZH, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 61, "", new TopBottom(PeopleAndRoles.SHI, BodyParts.XIN),
            new PhonoSemantic(BodyParts.XIN, PeopleAndRoles.SHI));

    /** 扭 (niǔ) — twist. LeftRight: 扌 + 丑. */
    public static final ComposedZi NIU_TWIST = new ComposedZi(
            uni("\u626D"), py(Initial.N, Head.I, Body.O, Tail.VOWEL_U, Tone.THIRD),
            7, 64, "", new LeftRight(TI_SHOU_PANG, AbstractConcepts.CHOU),
            new PhonoSemantic(TI_SHOU_PANG, AbstractConcepts.CHOU));

    /** 块 (kuài) — block, piece. LeftRight: 土 + 夬. */
    public static final ComposedZi KUAI_BLOCK = new ComposedZi(
            uni("\u5757"), py(Initial.K, Head.U, Body.A, Tail.VOWEL_I, Tone.FOURTH),
            7, 32, "", new LeftRight(NatureElements.TU, AbstractConcepts.GUAI),
            new PhonoSemantic(NatureElements.TU, AbstractConcepts.GUAI));

    /** 把 (bǎ) — hold, handle. LeftRight: 扌 + 巴. */
    public static final ComposedZi BA_HOLD = new ComposedZi(
            uni("\u628A"), py(Initial.B, Head.OPEN, Body.A, Tail.NONE, Tone.THIRD),
            7, 64, "", new LeftRight(TI_SHOU_PANG, Structures.BA_CLING),
            new PhonoSemantic(TI_SHOU_PANG, Structures.BA_CLING));

    /** 报 (bào) — report, newspaper. LeftRight: 扌 + ⺈又. */
    public static final ComposedZi BAO_REPORT = new ComposedZi(
            uni("\u62A5"), py(Initial.B, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.FOURTH),
            7, 64, "", new LeftRight(TI_SHOU_PANG, CommonBlocks.DAO_YOU),
            new PhonoSemantic(TI_SHOU_PANG, ActionsAndStates.YOU_AGAIN));

    /** 却 (què) — but, retreat. LeftRight: 去 + 卩. */
    public static final ComposedZi QUE = new ComposedZi(
            uni("\u5374"), py(Initial.Q, Head.U, Body.E, Tail.NONE, Tone.FOURTH),
            7, 26, "", new LeftRight(ActionsAndStates.QU, RadicalComponents.JIE_SEAL),
            new PhonoSemantic(RadicalComponents.JIE_SEAL, ActionsAndStates.QU));

    /** 劫 (jié) — rob, calamity. LeftRight: 去 + 力. */
    public static final ComposedZi JIE_ROB = new ComposedZi(
            uni("\u52AB"), py(Initial.J, Head.I, Body.E, Tail.NONE, Tone.SECOND),
            7, 19, "", new LeftRight(ActionsAndStates.QU, ToolsAndVessels.LI_PLOW),
            new PhonoSemantic(ToolsAndVessels.LI_PLOW, ActionsAndStates.QU));

    /** 芽 (yá) — bud, sprout. TopBottom: 艹 + 牙. */
    public static final ComposedZi YA_SPROUT = new ComposedZi(
            uni("\u82BD"), py(Initial.ZERO, Head.OPEN, Body.A, Tail.NONE, Tone.SECOND),
            7, 140, "", new TopBottom(CAO_ZI_TOU, BodyParts.YA),
            new PhonoSemantic(CAO_ZI_TOU, BodyParts.YA));

    /** 芹 (qín) — celery. TopBottom: 艹 + 斤. */
    public static final ComposedZi QIN_CELERY = new ComposedZi(
            uni("\u82B9"), py(Initial.Q, Head.I, Body.NULL, Tail.N, Tone.SECOND),
            7, 140, "", new TopBottom(CAO_ZI_TOU, ToolsAndVessels.JIN_AXE),
            new PhonoSemantic(CAO_ZI_TOU, ToolsAndVessels.JIN_AXE));

    /** 苍 (cāng) — pale, vast. TopBottom: 艹 + 仓. */
    public static final ComposedZi CANG_PALE = new ComposedZi(
            uni("\u82CD"), py(Initial.C, Head.OPEN, Body.A, Tail.NG, Tone.FIRST),
            7, 140, "", new TopBottom(CAO_ZI_TOU, Structures.CANG),
            new PhonoSemantic(CAO_ZI_TOU, Structures.CANG));

    /** 芳 (fāng) — fragrant. TopBottom: 艹 + 方. */
    public static final ComposedZi FANG_FRAGRANT = new ComposedZi(
            uni("\u82B3"), py(Initial.F, Head.OPEN, Body.A, Tail.NG, Tone.FIRST),
            7, 140, "", new TopBottom(CAO_ZI_TOU, SpaceAndDirection.FANG),
            new PhonoSemantic(CAO_ZI_TOU, SpaceAndDirection.FANG));

    /** 芦 (lú) — reed. TopBottom: 艹 + 户. */
    public static final ComposedZi LU_REED = new ComposedZi(
            uni("\u82A6"), py(Initial.L, Head.U, Body.NULL, Tail.NONE, Tone.SECOND),
            7, 140, "", new TopBottom(CAO_ZI_TOU, Structures.HU),
            new PhonoSemantic(CAO_ZI_TOU, Structures.HU));

    /** 劳 (láo) — labor. TopMiddleBottom: 艹 + 冖 + 力. */
    public static final ComposedZi LAO = new ComposedZi(
            uni("\u52B3"), py(Initial.L, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.SECOND),
            7, 19, "", new TopMiddleBottom(CAO_ZI_TOU, BAO_ZI_TOU, ToolsAndVessels.LI_PLOW),
            new CompoundIndicative("\u8279(plants) + \u5196(cover) + \u529B(strength) \u2192 toil"));

    /** 苏 (sū) — revive. TopBottom: 艹 + 办. */
    public static final ComposedZi SU_REVIVE = new ComposedZi(
            uni("\u82CF"), py(Initial.S, Head.U, Body.NULL, Tail.NONE, Tone.FIRST),
            7, 140, "", new TopBottom(CAO_ZI_TOU, ActionsAndStates.BAN_HANDLE),
            new PhonoSemantic(CAO_ZI_TOU, ActionsAndStates.BAN_HANDLE));

    /** 杆 (gān) — pole, rod. LeftRight: 木 + 干. */
    public static final ComposedZi GAN_POLE = new ComposedZi(
            uni("\u6746"), py(Initial.G, Head.OPEN, Body.A, Tail.N, Tone.FIRST),
            7, 75, "", new LeftRight(MU, SpaceAndDirection.GAN_DRY),
            new PhonoSemantic(MU, SpaceAndDirection.GAN_DRY));

    /** 杠 (gàng) — bar, lever. LeftRight: 木 + 工. */
    public static final ComposedZi GANG_BAR = new ComposedZi(
            uni("\u6760"), py(Initial.G, Head.OPEN, Body.A, Tail.NG, Tone.FOURTH),
            7, 75, "", new LeftRight(MU, ActionsAndStates.GONG_WORK),
            new PhonoSemantic(MU, ActionsAndStates.GONG_WORK));

    /** 杜 (dù) — birch, prevent. LeftRight: 木 + 土. */
    public static final ComposedZi DU_BIRCH = new ComposedZi(
            uni("\u675C"), py(Initial.D, Head.U, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 75, "", new LeftRight(MU, NatureElements.TU),
            new PhonoSemantic(MU, NatureElements.TU));

    /** 材 (cái) — material, timber. LeftRight: 木 + 才. */
    public static final ComposedZi CAI_MATERIAL = new ComposedZi(
            uni("\u6750"), py(Initial.C, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.SECOND),
            7, 75, "", new LeftRight(MU, ToolsAndVessels.CAI_TALENT),
            new PhonoSemantic(MU, ToolsAndVessels.CAI_TALENT));

    /** 村 (cūn) — village. LeftRight: 木 + 寸. */
    public static final ComposedZi CUN_VILLAGE = new ComposedZi(
            uni("\u6751"), py(Initial.C, Head.U, Body.NULL, Tail.N, Tone.FIRST),
            7, 75, "", new LeftRight(MU, Structures.CUN_INCH),
            new PhonoSemantic(MU, Structures.CUN_INCH));

    /** 杏 (xìng) — apricot. TopBottom: 木 + 口. */
    public static final ComposedZi XING_APRICOT = new ComposedZi(
            uni("\u674F"), py(Initial.X, Head.OPEN, Body.I, Tail.NG, Tone.FOURTH),
            7, 75, "", new TopBottom(WoodFamily.MU, BodyParts.KOU),
            new PhonoSemantic(WoodFamily.MU, BodyParts.KOU));

    /** 极 (jí) — extreme, pole. LeftRight: 木 + 及. */
    public static final ComposedZi JI_EXTREME = new ComposedZi(
            uni("\u6781"), py(Initial.J, Head.I, Body.NULL, Tail.NONE, Tone.SECOND),
            7, 75, "", new LeftRight(MU, ActionsAndStates.JI_REACH),
            new PhonoSemantic(MU, ActionsAndStates.JI_REACH));

    /** 李 (lǐ) — plum, surname. TopBottom: 木 + 子. */
    public static final ComposedZi LI_PLUM = new ComposedZi(
            uni("\u674E"), py(Initial.L, Head.I, Body.NULL, Tail.NONE, Tone.THIRD),
            7, 75, "", new TopBottom(WoodFamily.MU, PeopleAndRoles.ZI),
            new CompoundIndicative("\u6728(tree) + \u5B50(seed) \u2192 plum tree"));

    /** 否 (fǒu) — no, negate. TopBottom: 不 + 口. */
    public static final ComposedZi FOU = new ComposedZi(
            uni("\u5426"), py(Initial.F, Head.OPEN, Body.O, Tail.VOWEL_U, Tone.THIRD),
            7, 30, "", new TopBottom(AbstractConcepts.BU, BodyParts.KOU),
            new CompoundIndicative("\u4E0D(not) + \u53E3(mouth) \u2192 deny"));

    /** 还 (hái/huán) — still, return. SemiEnclosureBottomLeft: 辶 + 不. */
    public static final ComposedZi HAI_STILL = new ComposedZi(
            uni("\u8FD8"), py(Initial.H, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.SECOND),
            7, 162, "", new SemiEnclosureBottomLeft(ZOU_ZHI_DI, AbstractConcepts.BU),
            new PhonoSemantic(ZOU_ZHI_DI, AbstractConcepts.BU));

    /** 歼 (jiān) — annihilate. LeftRight: 歹 + 千. */
    public static final ComposedZi JIAN_ANNIHILATE = new ComposedZi(
            uni("\u6B7C"), py(Initial.J, Head.I, Body.A, Tail.N, Tone.FIRST),
            7, 78, "", new LeftRight(PlantsAndAgriculture.DAI, NumbersAndMeasure.QIAN),
            new PhonoSemantic(PlantsAndAgriculture.DAI, NumbersAndMeasure.QIAN));

    /** 连 (lián) — connect. SemiEnclosureBottomLeft: 辶 + 车. */
    public static final ComposedZi LIAN = new ComposedZi(
            uni("\u8FDE"), py(Initial.L, Head.I, Body.A, Tail.N, Tone.SECOND),
            7, 162, "", new SemiEnclosureBottomLeft(ZOU_ZHI_DI, ToolsAndVessels.CHE),
            new PhonoSemantic(ZOU_ZHI_DI, ToolsAndVessels.CHE));

    /** 旱 (hàn) — drought. TopBottom: 日 + 干. */
    public static final ComposedZi HAN_DROUGHT = new ComposedZi(
            uni("\u65F1"), py(Initial.H, Head.OPEN, Body.A, Tail.N, Tone.FOURTH),
            7, 72, "", new TopBottom(NatureElements.RI, SpaceAndDirection.GAN_DRY),
            new PhonoSemantic(NatureElements.RI, SpaceAndDirection.GAN_DRY));

    /** 盯 (dīng) — stare. LeftRight: 目 + 丁. */
    public static final ComposedZi DING_STARE = new ComposedZi(
            uni("\u76EF"), py(Initial.D, Head.OPEN, Body.I, Tail.NG, Tone.FIRST),
            7, 109, "", new LeftRight(BodyParts.MU, ToolsAndVessels.DING_NAIL),
            new PhonoSemantic(BodyParts.MU, ToolsAndVessels.DING_NAIL));

    /** 呈 (chéng) — present, display. TopBottom: 口 + 壬. */
    public static final ComposedZi CHENG_PRESENT = new ComposedZi(
            uni("\u5448"), py(Initial.CH, Head.OPEN, Body.E, Tail.NG, Tone.SECOND),
            7, 30, "", new TopBottom(BodyParts.KOU, PeopleAndRoles.REN_NINTH),
            new PhonoSemantic(BodyParts.KOU, PeopleAndRoles.REN_NINTH));

    /** 时 (shí) — time. LeftRight: 日 + 寸. */
    public static final ComposedZi SHI_TIME = new ComposedZi(
            uni("\u65F6"), py(Initial.SH, Head.OPEN, Body.NULL, Tail.NONE, Tone.SECOND),
            7, 72, "", new LeftRight(NatureElements.RI, Structures.CUN_INCH),
            new PhonoSemantic(NatureElements.RI, Structures.CUN_INCH));

    /** 吴 (wú) — surname Wu. TopBottom: 口 + 天. */
    public static final ComposedZi WU_SURNAME = new ComposedZi(
            uni("\u5434"), py(Initial.ZERO, Head.U, Body.NULL, Tail.NONE, Tone.SECOND),
            7, 30, "", new TopBottom(BodyParts.KOU, NatureElements.TIAN_SKY),
            new CompoundIndicative("\u53E3(mouth) + \u5929(heaven) \u2192 cry out loud"));

    /** 助 (zhù) — help. LeftRight: 且 + 力. */
    public static final ComposedZi ZHU_HELP = new ComposedZi(
            uni("\u52A9"), py(Initial.ZH, Head.U, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 19, "", new LeftRight(AbstractConcepts.QIE, ToolsAndVessels.LI_PLOW),
            new PhonoSemantic(ToolsAndVessels.LI_PLOW, AbstractConcepts.QIE));

    /** 县 (xiàn) — county. TopBottom: 目 + 厶. */
    public static final ComposedZi XIAN_COUNTY = new ComposedZi(
            uni("\u53BF"), py(Initial.X, Head.I, Body.A, Tail.N, Tone.FOURTH),
            7, 109, "", new TopBottom(BodyParts.MU, RadicalComponents.SI_PRIVATE),
            new PhonoSemantic(BodyParts.MU, RadicalComponents.SI_PRIVATE));

    /** 呆 (dāi) — dull, silly. TopBottom: 口 + 木. */
    public static final ComposedZi DAI_DULL = new ComposedZi(
            uni("\u5446"), py(Initial.D, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.FIRST),
            7, 30, "", new TopBottom(BodyParts.KOU, WoodFamily.MU),
            new CompoundIndicative("\u53E3(mouth) + \u6728(wood) \u2192 dull"));

    /** 旷 (kuàng) — vast, open. LeftRight: 日 + 广. */
    public static final ComposedZi KUANG_VAST = new ComposedZi(
            uni("\u65F7"), py(Initial.K, Head.U, Body.A, Tail.NG, Tone.FOURTH),
            7, 72, "", new LeftRight(NatureElements.RI, Structures.GUANG),
            new PhonoSemantic(NatureElements.RI, Structures.GUANG));

    /** 围 (wéi) — surround. FullEnclosure: 囗 + 韦. */
    public static final ComposedZi WEI_SURROUND = new ComposedZi(
            uni("\u56F4"), py(Initial.ZERO, Head.U, Body.E, Tail.VOWEL_I, Tone.SECOND),
            7, 31, "", new FullEnclosure(GUO_ZI_KUANG, Materials.WEI_LEATHER),
            new PhonoSemantic(GUO_ZI_KUANG, Materials.WEI_LEATHER));

    /** 呀 (ya) — ah. LeftRight: 口 + 牙. */
    public static final ComposedZi YA_AH = new ComposedZi(
            uni("\u5440"), py(Initial.ZERO, Head.OPEN, Body.A, Tail.NONE, Tone.NEUTRAL),
            7, 30, "", new LeftRight(BodyParts.KOU, BodyParts.YA),
            new PhonoSemantic(BodyParts.KOU, BodyParts.YA));

    /** 吨 (dūn) — ton. LeftRight: 口 + 屯. */
    public static final ComposedZi DUN_TON = new ComposedZi(
            uni("\u5428"), py(Initial.D, Head.U, Body.NULL, Tail.N, Tone.FIRST),
            7, 30, "", new LeftRight(BodyParts.KOU, Structures.TUN),
            new PhonoSemantic(BodyParts.KOU, Structures.TUN));

    /** 邮 (yóu) — postal, mail. LeftRight: 由 + 阝. */
    public static final ComposedZi YOU_MAIL = new ComposedZi(
            uni("\u90AE"), py(Initial.ZERO, Head.I, Body.O, Tail.VOWEL_U, Tone.SECOND),
            7, 163, "", new LeftRight(SpaceAndDirection.YOU_FROM, YOU_ER_PANG),
            new PhonoSemantic(YOU_ER_PANG, SpaceAndDirection.YOU_FROM));

    /** 男 (nán) — male. TopBottom: 田 + 力. */
    public static final ComposedZi NAN_MALE = new ComposedZi(
            uni("\u7537"), py(Initial.N, Head.OPEN, Body.A, Tail.N, Tone.SECOND),
            7, 102, "", new TopBottom(NatureElements.TIAN, ToolsAndVessels.LI_PLOW),
            new CompoundIndicative("\u7530(field) + \u529B(strength) \u2192 man"));

    /** 困 (kùn) — sleepy, stuck. FullEnclosure: 囗 + 木. */
    public static final ComposedZi KUN = new ComposedZi(
            uni("\u56F0"), py(Initial.K, Head.U, Body.NULL, Tail.N, Tone.FOURTH),
            7, 31, "", new FullEnclosure(GUO_ZI_KUANG, WoodFamily.MU),
            new CompoundIndicative("\u56D7(enclosure) + \u6728(tree) \u2192 tree confined \u2192 stuck"));

    /** 吵 (chǎo) — quarrel, noisy. LeftRight: 口 + 少. */
    public static final ComposedZi CHAO_NOISY = new ComposedZi(
            uni("\u5435"), py(Initial.CH, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.THIRD),
            7, 30, "", new LeftRight(BodyParts.KOU, PeopleAndRoles.SHAO),
            new PhonoSemantic(BodyParts.KOU, PeopleAndRoles.SHAO));

    /** 员 (yuán) — member. TopBottom: 口 + 贝. */
    public static final ComposedZi YUAN_MEMBER = new ComposedZi(
            uni("\u5458"), py(Initial.ZERO, Head.U, Body.A, Tail.N, Tone.SECOND),
            7, 30, "", new TopBottom(BodyParts.KOU, Animals.BEI),
            new PhonoSemantic(BodyParts.KOU, Animals.BEI));

    /** 听 (tīng) — listen. LeftRight: 口 + 斤. */
    public static final ComposedZi TING_LISTEN = new ComposedZi(
            uni("\u542C"), py(Initial.T, Head.OPEN, Body.I, Tail.NG, Tone.FIRST),
            7, 30, "", new LeftRight(BodyParts.KOU, ToolsAndVessels.JIN_AXE),
            new PhonoSemantic(BodyParts.KOU, ToolsAndVessels.JIN_AXE));

    /** 吹 (chuī) — blow. LeftRight: 口 + 欠. */
    public static final ComposedZi CHUI_BLOW = new ComposedZi(
            uni("\u5439"), py(Initial.CH, Head.U, Body.NULL, Tail.VOWEL_I, Tone.FIRST),
            7, 30, "", new LeftRight(BodyParts.KOU, RadicalComponents.QIAN_OWE),
            new PhonoSemantic(BodyParts.KOU, RadicalComponents.QIAN_OWE));

    /** 呜 (wū) — wail. LeftRight: 口 + 乌. */
    public static final ComposedZi WU_WAIL = new ComposedZi(
            uni("\u545C"), py(Initial.ZERO, Head.U, Body.NULL, Tail.NONE, Tone.FIRST),
            7, 30, "", new LeftRight(BodyParts.KOU, Animals.WU_CROW),
            new PhonoSemantic(BodyParts.KOU, Animals.WU_CROW));

    /** 吧 (ba) — particle. LeftRight: 口 + 巴. */
    public static final ComposedZi BA_PARTICLE = new ComposedZi(
            uni("\u5427"), py(Initial.B, Head.OPEN, Body.A, Tail.NONE, Tone.NEUTRAL),
            7, 30, "", new LeftRight(BodyParts.KOU, Structures.BA_CLING),
            new PhonoSemantic(BodyParts.KOU, Structures.BA_CLING));

    /** 吼 (hǒu) — roar. LeftRight: 口 + 犬. */
    public static final ComposedZi HOU_ROAR = new ComposedZi(
            uni("\u543C"), py(Initial.H, Head.OPEN, Body.O, Tail.VOWEL_U, Tone.THIRD),
            7, 30, "", new LeftRight(BodyParts.KOU, Animals.QUAN),
            new PhonoSemantic(BodyParts.KOU, Animals.QUAN));

    /** 帐 (zhàng) — tent, account. LeftRight: 巾 + 长. */
    public static final ComposedZi ZHANG_TENT = new ComposedZi(
            uni("\u5E10"), py(Initial.ZH, Head.OPEN, Body.A, Tail.NG, Tone.FOURTH),
            7, 50, "", new LeftRight(Materials.JIN_TOWEL, SpaceAndDirection.CHANG),
            new PhonoSemantic(Materials.JIN_TOWEL, SpaceAndDirection.CHANG));

    /** 财 (cái) — wealth. LeftRight: 贝 + 才. */
    public static final ComposedZi CAI_WEALTH = new ComposedZi(
            uni("\u8D22"), py(Initial.C, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.SECOND),
            7, 154, "", new LeftRight(Animals.BEI, ToolsAndVessels.CAI_TALENT),
            new PhonoSemantic(Animals.BEI, ToolsAndVessels.CAI_TALENT));

    /** 针 (zhēn) — needle. LeftRight: 钅 + 十. */
    public static final ComposedZi ZHEN_NEEDLE = new ComposedZi(
            uni("\u9488"), py(Initial.ZH, Head.OPEN, Body.E, Tail.N, Tone.FIRST),
            7, 167, "", new LeftRight(JIN_ZI_PANG, NumbersAndMeasure.SHI),
            new PhonoSemantic(JIN_ZI_PANG, NumbersAndMeasure.SHI));

    /** 钉 (dīng) — nail. LeftRight: 钅 + 丁. */
    public static final ComposedZi DING_NAIL_METAL = new ComposedZi(
            uni("\u9489"), py(Initial.D, Head.OPEN, Body.I, Tail.NG, Tone.FIRST),
            7, 167, "", new LeftRight(JIN_ZI_PANG, ToolsAndVessels.DING_NAIL),
            new PhonoSemantic(JIN_ZI_PANG, ToolsAndVessels.DING_NAIL));

    /** 告 (gào) — tell, announce. TopBottom: ⺧ + 口. */
    public static final ComposedZi GAO_TELL = new ComposedZi(
            uni("\u544A"), py(Initial.G, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.FOURTH),
            7, 30, "", new TopBottom(RadicalComponents.NIU_TOP, BodyParts.KOU),
            new CompoundIndicative("\u2EA7(ox-top) + \u53E3(mouth) \u2192 announce"));

    /** 利 (lì) — sharp, profit. LeftRight: 禾 + 刂. */
    public static final ComposedZi LI_PROFIT = new ComposedZi(
            uni("\u5229"), py(Initial.L, Head.I, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 18, "", new LeftRight(PlantsAndAgriculture.HE, LI_DAO_PANG),
            new CompoundIndicative("\u79BE(grain) + \u5202(knife) \u2192 harvest \u2192 profit"));

    /** 秃 (tū) — bald. TopBottom: 禾 + 儿. */
    public static final ComposedZi TU_BALD = new ComposedZi(
            uni("\u79C3"), py(Initial.T, Head.U, Body.NULL, Tail.NONE, Tone.FIRST),
            7, 115, "", new TopBottom(PlantsAndAgriculture.HE, PeopleAndRoles.ER_CHILD),
            new PhonoSemantic(PlantsAndAgriculture.HE, PeopleAndRoles.ER_CHILD));

    /** 秀 (xiù) — elegant, outstanding. TopBottom: 禾 + 乃. */
    public static final ComposedZi XIU_ELEGANT = new ComposedZi(
            uni("\u79C0"), py(Initial.X, Head.I, Body.O, Tail.VOWEL_U, Tone.FOURTH),
            7, 115, "", new TopBottom(PlantsAndAgriculture.HE, AbstractConcepts.NAI),
            new PhonoSemantic(PlantsAndAgriculture.HE, AbstractConcepts.NAI));

    /** 私 (sī) — private. LeftRight: 禾 + 厶. */
    public static final ComposedZi SI_PRIVATE = new ComposedZi(
            uni("\u79C1"), py(Initial.S, Head.OPEN, Body.NULL, Tail.NONE, Tone.FIRST),
            7, 115, "", new LeftRight(PlantsAndAgriculture.HE, RadicalComponents.SI_PRIVATE),
            new PhonoSemantic(PlantsAndAgriculture.HE, RadicalComponents.SI_PRIVATE));

    /** 体 (tǐ) — body. LeftRight: 亻 + 本. */
    public static final ComposedZi TI_BODY = new ComposedZi(
            uni("\u4F53"), py(Initial.T, Head.I, Body.NULL, Tail.NONE, Tone.THIRD),
            7, 9, "", new LeftRight(DAN_REN_PANG, PlantsAndAgriculture.BEN),
            new PhonoSemantic(DAN_REN_PANG, PlantsAndAgriculture.BEN));

    /** 何 (hé) — what, how. LeftRight: 亻 + 可. */
    public static final ComposedZi HE_WHAT = new ComposedZi(
            uni("\u4F55"), py(Initial.H, Head.OPEN, Body.E, Tail.NONE, Tone.SECOND),
            7, 9, "", new LeftRight(DAN_REN_PANG, ActionsAndStates.KE),
            new PhonoSemantic(DAN_REN_PANG, ActionsAndStates.KE));

    /** 伸 (shēn) — stretch. LeftRight: 亻 + 申. */
    public static final ComposedZi SHEN_STRETCH = new ComposedZi(
            uni("\u4F38"), py(Initial.SH, Head.OPEN, Body.E, Tail.N, Tone.FIRST),
            7, 9, "", new LeftRight(DAN_REN_PANG, SpaceAndDirection.SHEN_EXTEND),
            new PhonoSemantic(DAN_REN_PANG, SpaceAndDirection.SHEN_EXTEND));

    /** 作 (zuò) — do, make. LeftRight: 亻 + 乍. */
    public static final ComposedZi ZUO_MAKE = new ComposedZi(
            uni("\u4F5C"), py(Initial.Z, Head.U, Body.O, Tail.NONE, Tone.FOURTH),
            7, 9, "", new LeftRight(DAN_REN_PANG, ActionsAndStates.ZHA),
            new PhonoSemantic(DAN_REN_PANG, ActionsAndStates.ZHA));

    /** 伯 (bó) — elder uncle. LeftRight: 亻 + 白. */
    public static final ComposedZi BO_UNCLE = new ComposedZi(
            uni("\u4F2F"), py(Initial.B, Head.OPEN, Body.O, Tail.NONE, Tone.SECOND),
            7, 9, "", new LeftRight(DAN_REN_PANG, Materials.BAI_WHITE),
            new PhonoSemantic(DAN_REN_PANG, Materials.BAI_WHITE));

    /** 伶 (líng) — clever, actor. LeftRight: 亻 + 令. */
    public static final ComposedZi LING_CLEVER = new ComposedZi(
            uni("\u4F36"), py(Initial.L, Head.I, Body.NULL, Tail.NG, Tone.SECOND),
            7, 9, "", new LeftRight(DAN_REN_PANG, ActionsAndStates.LING),
            new PhonoSemantic(DAN_REN_PANG, ActionsAndStates.LING));

    /** 佣 (yōng) — servant. LeftRight: 亻 + 用. */
    public static final ComposedZi YONG_SERVANT = new ComposedZi(
            uni("\u4F63"), py(Initial.ZERO, Head.I, Body.O, Tail.NG, Tone.FIRST),
            7, 9, "", new LeftRight(DAN_REN_PANG, ActionsAndStates.YONG),
            new PhonoSemantic(DAN_REN_PANG, ActionsAndStates.YONG));

    /** 低 (dī) — low. LeftRight: 亻 + 氐. */
    public static final ComposedZi DI_LOW = new ComposedZi(
            uni("\u4F4E"), py(Initial.D, Head.I, Body.NULL, Tail.NONE, Tone.FIRST),
            7, 9, "", new LeftRight(DAN_REN_PANG, NatureElements.DI_FOUNDATION),
            new PhonoSemantic(DAN_REN_PANG, NatureElements.DI_FOUNDATION));

    /** 你 (nǐ) — you. LeftRight: 亻 + ⺈小. */
    public static final ComposedZi NI_YOU = new ComposedZi(
            uni("\u4F60"), py(Initial.N, Head.I, Body.NULL, Tail.NONE, Tone.THIRD),
            7, 9, "", new LeftRight(DAN_REN_PANG, CommonBlocks.DAO_XIAO),
            new PhonoSemantic(DAN_REN_PANG, PeopleAndRoles.XIAO));

    /** 住 (zhù) — live, reside. LeftRight: 亻 + 主. */
    public static final ComposedZi ZHU_LIVE = new ComposedZi(
            uni("\u4F4F"), py(Initial.ZH, Head.U, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 9, "", new LeftRight(DAN_REN_PANG, AbstractConcepts.ZHU_LORD),
            new PhonoSemantic(DAN_REN_PANG, AbstractConcepts.ZHU_LORD));

    /** 位 (wèi) — position. LeftRight: 亻 + 立. */
    public static final ComposedZi WEI_POSITION = new ComposedZi(
            uni("\u4F4D"), py(Initial.ZERO, Head.U, Body.E, Tail.VOWEL_I, Tone.FOURTH),
            7, 9, "", new LeftRight(DAN_REN_PANG, ActionsAndStates.LI_STAND),
            new PhonoSemantic(DAN_REN_PANG, ActionsAndStates.LI_STAND));

    /** 伴 (bàn) — companion. LeftRight: 亻 + 半. */
    public static final ComposedZi BAN_COMPANION = new ComposedZi(
            uni("\u4F34"), py(Initial.B, Head.OPEN, Body.A, Tail.N, Tone.FOURTH),
            7, 9, "", new LeftRight(DAN_REN_PANG, NumbersAndMeasure.BAN),
            new PhonoSemantic(DAN_REN_PANG, NumbersAndMeasure.BAN));

    /** 皂 (zào) — soap. TopBottom: 白 + 七. */
    public static final ComposedZi ZAO_SOAP = new ComposedZi(
            uni("\u7682"), py(Initial.Z, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.FOURTH),
            7, 106, "", new TopBottom(Materials.BAI_WHITE, NumbersAndMeasure.QI),
            new CompoundIndicative("\u767D(white) + \u4E03(seven) \u2192 soap"));

    /** 佛 (fó) — Buddha. LeftRight: 亻 + 弗. */
    public static final ComposedZi FO_BUDDHA = new ComposedZi(
            uni("\u4F5B"), py(Initial.F, Head.OPEN, Body.O, Tail.NONE, Tone.SECOND),
            7, 9, "", new LeftRight(DAN_REN_PANG, ToolsAndVessels.FU_NOT),
            new PhonoSemantic(DAN_REN_PANG, ToolsAndVessels.FU_NOT));

    /** 近 (jìn) — near. SemiEnclosureBottomLeft: 辶 + 斤. */
    public static final ComposedZi JIN_NEAR = new ComposedZi(
            uni("\u8FD1"), py(Initial.J, Head.I, Body.NULL, Tail.N, Tone.FOURTH),
            7, 162, "", new SemiEnclosureBottomLeft(ZOU_ZHI_DI, ToolsAndVessels.JIN_AXE),
            new PhonoSemantic(ZOU_ZHI_DI, ToolsAndVessels.JIN_AXE));

    /** 役 (yì) — service, battle. LeftRight: 彳 + 殳. */
    public static final ComposedZi YI_SERVICE = new ComposedZi(
            uni("\u5F79"), py(Initial.ZERO, Head.I, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 60, "", new LeftRight(JIAN_ZHI_PANG, RadicalComponents.SHU_LANCE),
            new PhonoSemantic(JIAN_ZHI_PANG, RadicalComponents.SHU_LANCE));

    /** 妥 (tuǒ) — suitable. TopBottom: 爫 + 女. */
    public static final ComposedZi TUO = new ComposedZi(
            uni("\u59A5"), py(Initial.T, Head.U, Body.O, Tail.NONE, Tone.THIRD),
            7, 38, "", new TopBottom(RadicalComponents.ZHAO_CLAW, PeopleAndRoles.NV),
            new CompoundIndicative("\u722B(hand/claw) + \u5973(woman) \u2192 suitable, proper"));

    /** 含 (hán) — contain. TopBottom: 今 + 口. */
    public static final ComposedZi HAN_CONTAIN = new ComposedZi(
            uni("\u542B"), py(Initial.H, Head.OPEN, Body.A, Tail.N, Tone.SECOND),
            7, 30, "", new TopBottom(AbstractConcepts.JIN, BodyParts.KOU),
            new PhonoSemantic(BodyParts.KOU, AbstractConcepts.JIN));

    /** 邻 (lín) — neighbor. LeftRight: 令 + 阝. */
    public static final ComposedZi LIN_NEIGHBOR = new ComposedZi(
            uni("\u90BB"), py(Initial.L, Head.I, Body.NULL, Tail.N, Tone.SECOND),
            7, 163, "", new LeftRight(ActionsAndStates.LING, YOU_ER_PANG),
            new PhonoSemantic(YOU_ER_PANG, ActionsAndStates.LING));

    /** 肝 (gān) — liver. LeftRight: 月 + 干. */
    public static final ComposedZi GAN_LIVER = new ComposedZi(
            uni("\u809D"), py(Initial.G, Head.OPEN, Body.A, Tail.N, Tone.FIRST),
            7, 130, "", new LeftRight(NatureElements.YUE, SpaceAndDirection.GAN_DRY),
            new PhonoSemantic(NatureElements.YUE, SpaceAndDirection.GAN_DRY));

    /** 肚 (dù) — belly. LeftRight: 月 + 土. */
    public static final ComposedZi DU_BELLY = new ComposedZi(
            uni("\u809A"), py(Initial.D, Head.U, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 130, "", new LeftRight(NatureElements.YUE, NatureElements.TU),
            new PhonoSemantic(NatureElements.YUE, NatureElements.TU));

    /** 狂 (kuáng) — mad, wild. LeftRight: 犭 + 王. */
    public static final ComposedZi KUANG_MAD = new ComposedZi(
            uni("\u72C2"), py(Initial.K, Head.U, Body.A, Tail.NG, Tone.SECOND),
            7, 94, "", new LeftRight(FAN_QUAN_PANG, PeopleAndRoles.WANG),
            new PhonoSemantic(FAN_QUAN_PANG, PeopleAndRoles.WANG));

    /** 犹 (yóu) — still, as if. LeftRight: 犭 + 尤. */
    public static final ComposedZi YOU_STILL = new ComposedZi(
            uni("\u72B9"), py(Initial.ZERO, Head.I, Body.O, Tail.VOWEL_U, Tone.SECOND),
            7, 94, "", new LeftRight(FAN_QUAN_PANG, PeopleAndRoles.YOU_ESPECIALLY),
            new PhonoSemantic(FAN_QUAN_PANG, PeopleAndRoles.YOU_ESPECIALLY));

    /** 删 (shān) — delete. LeftRight: 册 + 刂. */
    public static final ComposedZi SHAN_DELETE = new ComposedZi(
            uni("\u5220"), py(Initial.SH, Head.OPEN, Body.A, Tail.N, Tone.FIRST),
            7, 18, "", new LeftRight(ToolsAndVessels.CE, LI_DAO_PANG),
            new PhonoSemantic(LI_DAO_PANG, ToolsAndVessels.CE));

    /** 条 (tiáo) — strip, item. TopBottom: 夂 + 木. */
    public static final ComposedZi TIAO = new ComposedZi(
            uni("\u6761"), py(Initial.T, Head.I, Body.A, Tail.VOWEL_U, Tone.SECOND),
            7, 75, "", new TopBottom(RadicalComponents.ZHI_SLOW, WoodFamily.MU),
            new PhonoSemantic(WoodFamily.MU, RadicalComponents.ZHI_SLOW));

    /** 迎 (yíng) — welcome. SemiEnclosureBottomLeft: 辶 + 卬. */
    public static final ComposedZi YING_WELCOME = new ComposedZi(
            uni("\u8FCE"), py(Initial.ZERO, Head.I, Body.NULL, Tail.NG, Tone.SECOND),
            7, 162, "", new SemiEnclosureBottomLeft(ZOU_ZHI_DI, RadicalComponents.ANG),
            new PhonoSemantic(ZOU_ZHI_DI, RadicalComponents.ANG));

    /** 饮 (yǐn) — drink. LeftRight: 饣 + 欠. */
    public static final ComposedZi YIN_DRINK = new ComposedZi(
            uni("\u996E"), py(Initial.ZERO, Head.I, Body.NULL, Tail.N, Tone.THIRD),
            7, 184, "", new LeftRight(SHI_ZI_PANG, RadicalComponents.QIAN_OWE),
            new PhonoSemantic(SHI_ZI_PANG, RadicalComponents.QIAN_OWE));

    /** 冻 (dòng) — freeze. LeftRight: 冫 + 东. */
    public static final ComposedZi DONG_FREEZE = new ComposedZi(
            uni("\u51BB"), py(Initial.D, Head.OPEN, Body.O, Tail.NG, Tone.FOURTH),
            7, 15, "", new LeftRight(LIANG_DIAN_SHUI, SpaceAndDirection.DONG),
            new PhonoSemantic(LIANG_DIAN_SHUI, SpaceAndDirection.DONG));

    /** 状 (zhuàng) — shape, state. LeftRight: 丬 + 犬. */
    public static final ComposedZi ZHUANG_STATE = new ComposedZi(
            uni("\u72B6"), py(Initial.ZH, Head.U, Body.A, Tail.NG, Tone.FOURTH),
            7, 94, "", new LeftRight(SpaceAndDirection.PAN, Animals.QUAN),
            new PhonoSemantic(SpaceAndDirection.PAN, Animals.QUAN));

    /** 亩 (mǔ) — acre. TopBottom: 亠 + 田. */
    public static final ComposedZi MU_ACRE = new ComposedZi(
            uni("\u4EA9"), py(Initial.M, Head.U, Body.NULL, Tail.NONE, Tone.THIRD),
            7, 102, "", new TopBottom(WEN_ZI_TOU, NatureElements.TIAN),
            new PhonoSemantic(NatureElements.TIAN, WEN_ZI_TOU));

    /** 况 (kuàng) — situation. LeftRight: 冫 + 兄. */
    public static final ComposedZi KUANG_SITUATION = new ComposedZi(
            uni("\u51B5"), py(Initial.K, Head.U, Body.A, Tail.NG, Tone.FOURTH),
            7, 15, "", new LeftRight(LIANG_DIAN_SHUI, PeopleAndRoles.XIONG),
            new PhonoSemantic(LIANG_DIAN_SHUI, PeopleAndRoles.XIONG));

    /** 床 (chuáng) — bed. SemiEnclosureUpperLeft: 广 + 木. */
    public static final ComposedZi CHUANG_BED = new ComposedZi(
            uni("\u5E8A"), py(Initial.CH, Head.U, Body.A, Tail.NG, Tone.SECOND),
            7, 53, "", new SemiEnclosureUpperLeft(Structures.GUANG, WoodFamily.MU),
            new PhonoSemantic(Structures.GUANG, WoodFamily.MU));

    /** 库 (kù) — warehouse. SemiEnclosureUpperLeft: 广 + 车. */
    public static final ComposedZi KU_WAREHOUSE = new ComposedZi(
            uni("\u5E93"), py(Initial.K, Head.U, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 53, "", new SemiEnclosureUpperLeft(Structures.GUANG, ToolsAndVessels.CHE),
            new PhonoSemantic(Structures.GUANG, ToolsAndVessels.CHE));

    /** 疗 (liáo) — cure, treat. SemiEnclosureUpperLeft: 疒 + 了. */
    public static final ComposedZi LIAO_CURE = new ComposedZi(
            uni("\u7597"), py(Initial.L, Head.I, Body.A, Tail.VOWEL_U, Tone.SECOND),
            7, 104, "", new SemiEnclosureUpperLeft(BING_ZI_PANG, ActionsAndStates.LE),
            new PhonoSemantic(BING_ZI_PANG, ActionsAndStates.LE));

    /** 冷 (lěng) — cold. LeftRight: 冫 + 令. */
    public static final ComposedZi LENG = new ComposedZi(
            uni("\u51B7"), py(Initial.L, Head.OPEN, Body.E, Tail.NG, Tone.THIRD),
            7, 15, "", new LeftRight(LIANG_DIAN_SHUI, ActionsAndStates.LING),
            new PhonoSemantic(LIANG_DIAN_SHUI, ActionsAndStates.LING));

    /** 这 (zhè) — this. SemiEnclosureBottomLeft: 辶 + 文. */
    public static final ComposedZi ZHE_THIS = new ComposedZi(
            uni("\u8FD9"), py(Initial.ZH, Head.OPEN, Body.E, Tail.NONE, Tone.FOURTH),
            7, 162, "", new SemiEnclosureBottomLeft(ZOU_ZHI_DI, PeopleAndRoles.WEN),
            new PhonoSemantic(ZOU_ZHI_DI, PeopleAndRoles.WEN));

    /** 序 (xù) — order, preface. SemiEnclosureUpperLeft: 广 + 予. */
    public static final ComposedZi XU_ORDER = new ComposedZi(
            uni("\u5E8F"), py(Initial.X, Head.U, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 53, "", new SemiEnclosureUpperLeft(Structures.GUANG, ActionsAndStates.YU_BESTOW),
            new PhonoSemantic(Structures.GUANG, ActionsAndStates.YU_BESTOW));

    /** 冶 (yě) — smelt. LeftRight: 冫 + 台. */
    public static final ComposedZi YE_SMELT = new ComposedZi(
            uni("\u51B6"), py(Initial.ZERO, Head.I, Body.E, Tail.NONE, Tone.THIRD),
            7, 15, "", new LeftRight(LIANG_DIAN_SHUI, Structures.TAI),
            new PhonoSemantic(LIANG_DIAN_SHUI, Structures.TAI));

    /** 忘 (wàng) — forget. TopBottom: 亡 + 心. */
    public static final ComposedZi WANG_FORGET = new ComposedZi(
            uni("\u5FD8"), py(Initial.ZERO, Head.U, Body.A, Tail.NG, Tone.FOURTH),
            7, 61, "", new TopBottom(ActionsAndStates.WANG, BodyParts.XIN),
            new PhonoSemantic(BodyParts.XIN, ActionsAndStates.WANG));

    /** 闲 (xián) — idle. SemiEnclosureTopThree: 门 + 木. */
    public static final ComposedZi XIAN_IDLE = new ComposedZi(
            uni("\u95F2"), py(Initial.X, Head.I, Body.A, Tail.N, Tone.SECOND),
            7, 169, "", new SemiEnclosureTopThree(ToolsAndVessels.MEN, WoodFamily.MU),
            new PhonoSemantic(ToolsAndVessels.MEN, WoodFamily.MU));

    /** 间 (jiān) — between. SemiEnclosureTopThree: 门 + 日. */
    public static final ComposedZi JIAN_BETWEEN = new ComposedZi(
            uni("\u95F4"), py(Initial.J, Head.I, Body.A, Tail.N, Tone.FIRST),
            7, 169, "", new SemiEnclosureTopThree(ToolsAndVessels.MEN, NatureElements.RI),
            new PhonoSemantic(ToolsAndVessels.MEN, NatureElements.RI));

    /** 闷 (mèn) — stuffy, bored. SemiEnclosureTopThree: 门 + 心. */
    public static final ComposedZi MEN_STUFFY = new ComposedZi(
            uni("\u95F7"), py(Initial.M, Head.OPEN, Body.E, Tail.N, Tone.FOURTH),
            7, 169, "", new SemiEnclosureTopThree(ToolsAndVessels.MEN, BodyParts.XIN),
            new PhonoSemantic(ToolsAndVessels.MEN, BodyParts.XIN));

    /** 判 (pàn) — judge. LeftRight: 半 + 刂. */
    public static final ComposedZi PAN_JUDGE = new ComposedZi(
            uni("\u5224"), py(Initial.P, Head.OPEN, Body.A, Tail.N, Tone.FOURTH),
            7, 18, "", new LeftRight(NumbersAndMeasure.BAN, LI_DAO_PANG),
            new PhonoSemantic(LI_DAO_PANG, NumbersAndMeasure.BAN));

    /** 灶 (zào) — stove. LeftRight: 火 + 土. */
    public static final ComposedZi ZAO_STOVE = new ComposedZi(
            uni("\u7076"), py(Initial.Z, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.FOURTH),
            7, 86, "", new LeftRight(NatureElements.HUO, NatureElements.TU),
            new CompoundIndicative("\u706B(fire) + \u571F(earth) \u2192 stove"));

    /** 灿 (càn) — brilliant. LeftRight: 火 + 山. */
    public static final ComposedZi CAN_BRILLIANT = new ComposedZi(
            uni("\u707F"), py(Initial.C, Head.OPEN, Body.A, Tail.N, Tone.FOURTH),
            7, 86, "", new LeftRight(NatureElements.HUO, NatureElements.SHAN),
            new PhonoSemantic(NatureElements.HUO, NatureElements.SHAN));

    /** 汪 (wāng) — vast (water). LeftRight: 氵 + 王. */
    public static final ComposedZi WANG_VAST = new ComposedZi(
            uni("\u6C6A"), py(Initial.ZERO, Head.U, Body.A, Tail.NG, Tone.FIRST),
            7, 85, "", new LeftRight(SAN_DIAN_SHUI, PeopleAndRoles.WANG),
            new PhonoSemantic(SAN_DIAN_SHUI, PeopleAndRoles.WANG));

    /** 沙 (shā) — sand. LeftRight: 氵 + 少. */
    public static final ComposedZi SHA_SAND = new ComposedZi(
            uni("\u6C99"), py(Initial.SH, Head.OPEN, Body.A, Tail.NONE, Tone.FIRST),
            7, 85, "", new LeftRight(SAN_DIAN_SHUI, PeopleAndRoles.SHAO),
            new PhonoSemantic(SAN_DIAN_SHUI, PeopleAndRoles.SHAO));

    /** 汽 (qì) — steam, vapor. LeftRight: 氵 + 气. */
    public static final ComposedZi QI_STEAM = new ComposedZi(
            uni("\u6C7D"), py(Initial.Q, Head.I, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 85, "", new LeftRight(SAN_DIAN_SHUI, NatureElements.QI),
            new PhonoSemantic(SAN_DIAN_SHUI, NatureElements.QI));

    /** 沃 (wò) — fertile, irrigate. LeftRight: 氵 + 夭. */
    public static final ComposedZi WO_FERTILE = new ComposedZi(
            uni("\u6C83"), py(Initial.ZERO, Head.U, Body.O, Tail.NONE, Tone.FOURTH),
            7, 85, "", new LeftRight(SAN_DIAN_SHUI, PeopleAndRoles.YAO),
            new PhonoSemantic(SAN_DIAN_SHUI, PeopleAndRoles.YAO));

    /** 泛 (fàn) — flood, general. LeftRight: 氵 + 凡. */
    public static final ComposedZi FAN_FLOOD = new ComposedZi(
            uni("\u6CDB"), py(Initial.F, Head.OPEN, Body.A, Tail.N, Tone.FOURTH),
            7, 85, "", new LeftRight(SAN_DIAN_SHUI, ToolsAndVessels.FAN),
            new PhonoSemantic(SAN_DIAN_SHUI, ToolsAndVessels.FAN));

    /** 沟 (gōu) — ditch. LeftRight: 氵 + 勾. */
    public static final ComposedZi GOU_DITCH = new ComposedZi(
            uni("\u6C9F"), py(Initial.G, Head.OPEN, Body.O, Tail.VOWEL_U, Tone.FIRST),
            7, 85, "", new LeftRight(SAN_DIAN_SHUI, ActionsAndStates.GOU),
            new PhonoSemantic(SAN_DIAN_SHUI, ActionsAndStates.GOU));

    /** 没 (méi/mò) — not have, sink. LeftRight: 氵 + 殳. */
    public static final ComposedZi MEI_NOT = new ComposedZi(
            uni("\u6CA1"), py(Initial.M, Head.OPEN, Body.E, Tail.VOWEL_I, Tone.SECOND),
            7, 85, "", new LeftRight(SAN_DIAN_SHUI, RadicalComponents.SHU_LANCE),
            new PhonoSemantic(SAN_DIAN_SHUI, RadicalComponents.SHU_LANCE));

    /** 沈 (shěn) — sink, surname. LeftRight: 氵 + 冘. */
    public static final ComposedZi SHEN_SINK = new ComposedZi(
            uni("\u6C88"), py(Initial.SH, Head.OPEN, Body.E, Tail.N, Tone.THIRD),
            7, 85, "", new LeftRight(SAN_DIAN_SHUI, RadicalComponents.YIN_WAVER),
            new PhonoSemantic(SAN_DIAN_SHUI, RadicalComponents.YIN_WAVER));

    /** 沉 (chén) — sink, heavy. LeftRight: 氵 + 冘variant. */
    public static final ComposedZi CHEN_SINK = new ComposedZi(
            uni("\u6C89"), py(Initial.CH, Head.OPEN, Body.E, Tail.N, Tone.SECOND),
            7, 85, "", new LeftRight(SAN_DIAN_SHUI, RadicalComponents.YIN_WAVER),
            new PhonoSemantic(SAN_DIAN_SHUI, RadicalComponents.YIN_WAVER));

    /** 怀 (huái) — bosom, cherish. LeftRight: 忄 + 不. */
    public static final ComposedZi HUAI_CHERISH = new ComposedZi(
            uni("\u6000"), py(Initial.H, Head.U, Body.A, Tail.VOWEL_I, Tone.SECOND),
            7, 61, "", new LeftRight(SHU_XIN_PANG, AbstractConcepts.BU),
            new PhonoSemantic(SHU_XIN_PANG, AbstractConcepts.BU));

    /** 忧 (yōu) — worry. LeftRight: 忄 + 尤. */
    public static final ComposedZi YOU_WORRY = new ComposedZi(
            uni("\u5FE7"), py(Initial.ZERO, Head.I, Body.O, Tail.VOWEL_U, Tone.FIRST),
            7, 61, "", new LeftRight(SHU_XIN_PANG, PeopleAndRoles.YOU_ESPECIALLY),
            new PhonoSemantic(SHU_XIN_PANG, PeopleAndRoles.YOU_ESPECIALLY));

    /** 快 (kuài) — fast. LeftRight: 忄 + 夬. */
    public static final ComposedZi KUAI_FAST = new ComposedZi(
            uni("\u5FEB"), py(Initial.K, Head.U, Body.A, Tail.VOWEL_I, Tone.FOURTH),
            7, 61, "", new LeftRight(SHU_XIN_PANG, AbstractConcepts.GUAI),
            new PhonoSemantic(SHU_XIN_PANG, AbstractConcepts.GUAI));

    /** 宋 (sòng) — Song dynasty, surname. TopBottom: 宀 + 木. */
    public static final ComposedZi SONG = new ComposedZi(
            uni("\u5B8B"), py(Initial.S, Head.OPEN, Body.O, Tail.NG, Tone.FOURTH),
            7, 40, "", new TopBottom(BAO_GAI_TOU, WoodFamily.MU),
            new PhonoSemantic(BAO_GAI_TOU, WoodFamily.MU));

    /** 牢 (láo) — prison, firm. TopBottom: 宀 + 牛. */
    public static final ComposedZi LAO_PRISON = new ComposedZi(
            uni("\u7262"), py(Initial.L, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.SECOND),
            7, 40, "", new TopBottom(BAO_GAI_TOU, Animals.NIU),
            new CompoundIndicative("\u5B80(roof) + \u725B(ox) \u2192 pen/prison"));

    /** 究 (jiū) — investigate. TopBottom: 穴 + 九. */
    public static final ComposedZi JIU_INVESTIGATE = new ComposedZi(
            uni("\u7A76"), py(Initial.J, Head.I, Body.O, Tail.VOWEL_U, Tone.FIRST),
            7, 116, "", new TopBottom(Structures.XUE, NumbersAndMeasure.JIU),
            new PhonoSemantic(Structures.XUE, NumbersAndMeasure.JIU));

    /** 穷 (qióng) — poor. TopBottom: 穴 + 力. */
    public static final ComposedZi QIONG = new ComposedZi(
            uni("\u7A77"), py(Initial.Q, Head.I, Body.O, Tail.NG, Tone.SECOND),
            7, 116, "", new TopBottom(Structures.XUE, ToolsAndVessels.LI_PLOW),
            new PhonoSemantic(Structures.XUE, ToolsAndVessels.LI_PLOW));

    /** 灾 (zāi) — disaster. TopBottom: 宀 + 火. */
    public static final ComposedZi ZAI_DISASTER = new ComposedZi(
            uni("\u707E"), py(Initial.Z, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.FIRST),
            7, 86, "", new TopBottom(BAO_GAI_TOU, NatureElements.HUO),
            new CompoundIndicative("\u5B80(roof) + \u706B(fire) \u2192 disaster"));

    /** 证 (zhèng) — prove, certificate. LeftRight: 讠 + 正. */
    public static final ComposedZi ZHENG_PROVE = new ComposedZi(
            uni("\u8BC1"), py(Initial.ZH, Head.OPEN, Body.E, Tail.NG, Tone.FOURTH),
            7, 149, "", new LeftRight(YAN_ZI_PANG, AbstractConcepts.ZHENG),
            new PhonoSemantic(YAN_ZI_PANG, AbstractConcepts.ZHENG));

    /** 启 (qǐ) — open, start. SemiEnclosureUpperLeft: 户 + 口. */
    public static final ComposedZi QI_START = new ComposedZi(
            uni("\u542F"), py(Initial.Q, Head.I, Body.NULL, Tail.NONE, Tone.THIRD),
            7, 30, "", new SemiEnclosureUpperLeft(Structures.HU, BodyParts.KOU),
            new CompoundIndicative("\u6237(door) + \u53E3(mouth) \u2192 open/start"));

    /** 评 (píng) — comment, evaluate. LeftRight: 讠 + 平. */
    public static final ComposedZi PING_EVALUATE = new ComposedZi(
            uni("\u8BC4"), py(Initial.P, Head.I, Body.NULL, Tail.NG, Tone.SECOND),
            7, 149, "", new LeftRight(YAN_ZI_PANG, AbstractConcepts.PING),
            new PhonoSemantic(YAN_ZI_PANG, AbstractConcepts.PING));

    /** 补 (bǔ) — mend, supplement. LeftRight: 衤 + 卜. */
    public static final ComposedZi BU_MEND = new ComposedZi(
            uni("\u8865"), py(Initial.B, Head.U, Body.NULL, Tail.NONE, Tone.THIRD),
            7, 145, "", new LeftRight(YI_ZI_PANG, ToolsAndVessels.BU_DIVINATION),
            new PhonoSemantic(YI_ZI_PANG, ToolsAndVessels.BU_DIVINATION));

    /** 初 (chū) — initial, first. LeftRight: 衤 + 刀. */
    public static final ComposedZi CHU_INITIAL = new ComposedZi(
            uni("\u521D"), py(Initial.CH, Head.U, Body.NULL, Tail.NONE, Tone.FIRST),
            7, 18, "", new LeftRight(YI_ZI_PANG, ToolsAndVessels.DAO),
            new CompoundIndicative("\u8864(clothes) + \u5200(knife) \u2192 cut cloth \u2192 begin"));

    /** 社 (shè) — society. LeftRight: 礻 + 土. */
    public static final ComposedZi SHE_SOCIETY = new ComposedZi(
            uni("\u793E"), py(Initial.SH, Head.OPEN, Body.E, Tail.NONE, Tone.FOURTH),
            7, 113, "", new LeftRight(SHI_ZI_PANG_SPIRIT, NatureElements.TU),
            new PhonoSemantic(SHI_ZI_PANG_SPIRIT, NatureElements.TU));

    /** 诉 (sù) — tell, complain. LeftRight: 讠 + 斥. */
    public static final ComposedZi SU_TELL = new ComposedZi(
            uni("\u8BC9"), py(Initial.S, Head.U, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 149, "", new LeftRight(YAN_ZI_PANG, ActionsAndStates.CHI_REPEL),
            new PhonoSemantic(YAN_ZI_PANG, ActionsAndStates.CHI_REPEL));

    /** 词 (cí) — word, lyrics. LeftRight: 讠 + 司. */
    public static final ComposedZi CI_WORD = new ComposedZi(
            uni("\u8BCD"), py(Initial.C, Head.OPEN, Body.NULL, Tail.NONE, Tone.SECOND),
            7, 149, "", new LeftRight(YAN_ZI_PANG, PeopleAndRoles.SI_MANAGE),
            new PhonoSemantic(YAN_ZI_PANG, PeopleAndRoles.SI_MANAGE));

    /** 灵 (líng) — spirit. TopBottom: 彐 + 火. */
    public static final ComposedZi LING_SPIRIT = new ComposedZi(
            uni("\u7075"), py(Initial.L, Head.I, Body.NULL, Tail.NG, Tone.SECOND),
            7, 86, "", new TopBottom(RadicalComponents.JI_SNOUT, NatureElements.HUO),
            new PhonoSemantic(NatureElements.HUO, RadicalComponents.JI_SNOUT));

    /** 层 (céng) — layer. SemiEnclosureUpperLeft: 尸 + 云. */
    public static final ComposedZi CENG = new ComposedZi(
            uni("\u5C42"), py(Initial.C, Head.OPEN, Body.E, Tail.NG, Tone.SECOND),
            7, 44, "", new SemiEnclosureUpperLeft(Structures.SHI_CORPSE, NatureElements.YUN),
            new PhonoSemantic(Structures.SHI_CORPSE, NatureElements.YUN));

    /** 尿 (niào) — urine. SemiEnclosureUpperLeft: 尸 + 水. */
    public static final ComposedZi NIAO_URINE = new ComposedZi(
            uni("\u5C3F"), py(Initial.N, Head.I, Body.A, Tail.VOWEL_U, Tone.FOURTH),
            7, 44, "", new SemiEnclosureUpperLeft(Structures.SHI_CORPSE, NatureElements.SHUI),
            new PhonoSemantic(Structures.SHI_CORPSE, NatureElements.SHUI));

    /** 尾 (wěi) — tail. SemiEnclosureUpperLeft: 尸 + 毛. */
    public static final ComposedZi WEI_TAIL = new ComposedZi(
            uni("\u5C3E"), py(Initial.ZERO, Head.U, Body.E, Tail.VOWEL_I, Tone.THIRD),
            7, 44, "", new SemiEnclosureUpperLeft(Structures.SHI_CORPSE, BodyParts.MAO),
            new PhonoSemantic(Structures.SHI_CORPSE, BodyParts.MAO));

    /** 迟 (chí) — late. SemiEnclosureBottomLeft: 辶 + 尺. */
    public static final ComposedZi CHI_LATE = new ComposedZi(
            uni("\u8FDF"), py(Initial.CH, Head.OPEN, Body.NULL, Tail.NONE, Tone.SECOND),
            7, 162, "", new SemiEnclosureBottomLeft(ZOU_ZHI_DI, Structures.CHI_RULER),
            new PhonoSemantic(ZOU_ZHI_DI, Structures.CHI_RULER));

    /** 改 (gǎi) — change. LeftRight: 己 + 攵. */
    public static final ComposedZi GAI_CHANGE = new ComposedZi(
            uni("\u6539"), py(Initial.G, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.THIRD),
            7, 66, "", new LeftRight(AbstractConcepts.JI_SELF, FAN_WEN_PANG),
            new PhonoSemantic(FAN_WEN_PANG, AbstractConcepts.JI_SELF));

    /** 张 (zhāng) — stretch, sheet. LeftRight: 弓 + 长. */
    public static final ComposedZi ZHANG_STRETCH = new ComposedZi(
            uni("\u5F20"), py(Initial.ZH, Head.OPEN, Body.A, Tail.NG, Tone.FIRST),
            7, 57, "", new LeftRight(ToolsAndVessels.GONG, SpaceAndDirection.CHANG),
            new PhonoSemantic(ToolsAndVessels.GONG, SpaceAndDirection.CHANG));

    /** 忌 (jì) — taboo, jealous. TopBottom: 己 + 心. */
    public static final ComposedZi JI_TABOO = new ComposedZi(
            uni("\u5FCC"), py(Initial.J, Head.I, Body.NULL, Tail.NONE, Tone.FOURTH),
            7, 61, "", new TopBottom(AbstractConcepts.JI_SELF, BodyParts.XIN),
            new PhonoSemantic(BodyParts.XIN, AbstractConcepts.JI_SELF));

    /** 阿 (ā) — prefix, hill. LeftRight: 阝 + 可. */
    public static final ComposedZi A_PREFIX = new ComposedZi(
            uni("\u963F"), py(Initial.ZERO, Head.OPEN, Body.A, Tail.NONE, Tone.FIRST),
            7, 170, "", new LeftRight(ZUO_ER_PANG, ActionsAndStates.KE),
            new PhonoSemantic(ZUO_ER_PANG, ActionsAndStates.KE));

    /** 陈 (chén) — display, surname. LeftRight: 阝 + 东. */
    public static final ComposedZi CHEN_DISPLAY = new ComposedZi(
            uni("\u9648"), py(Initial.CH, Head.OPEN, Body.E, Tail.N, Tone.SECOND),
            7, 170, "", new LeftRight(ZUO_ER_PANG, SpaceAndDirection.DONG),
            new PhonoSemantic(ZUO_ER_PANG, SpaceAndDirection.DONG));

    /** 阻 (zǔ) — block, hinder. LeftRight: 阝 + 且. */
    public static final ComposedZi ZU_BLOCK = new ComposedZi(
            uni("\u963B"), py(Initial.Z, Head.U, Body.NULL, Tail.NONE, Tone.THIRD),
            7, 170, "", new LeftRight(ZUO_ER_PANG, AbstractConcepts.QIE),
            new PhonoSemantic(ZUO_ER_PANG, AbstractConcepts.QIE));

    /** 妙 (miào) — wonderful. LeftRight: 女 + 少. */
    public static final ComposedZi MIAO = new ComposedZi(
            uni("\u5999"), py(Initial.M, Head.I, Body.A, Tail.VOWEL_U, Tone.FOURTH),
            7, 38, "", new LeftRight(PeopleAndRoles.NV, PeopleAndRoles.SHAO),
            new PhonoSemantic(PeopleAndRoles.NV, PeopleAndRoles.SHAO));

    /** 妖 (yāo) — monster, bewitching. LeftRight: 女 + 夭. */
    public static final ComposedZi YAO_MONSTER = new ComposedZi(
            uni("\u5996"), py(Initial.ZERO, Head.I, Body.A, Tail.VOWEL_U, Tone.FIRST),
            7, 38, "", new LeftRight(PeopleAndRoles.NV, PeopleAndRoles.YAO),
            new PhonoSemantic(PeopleAndRoles.NV, PeopleAndRoles.YAO));

    /** 妨 (fáng) — hinder. LeftRight: 女 + 方. */
    public static final ComposedZi FANG_HINDER = new ComposedZi(
            uni("\u59A8"), py(Initial.F, Head.OPEN, Body.A, Tail.NG, Tone.SECOND),
            7, 38, "", new LeftRight(PeopleAndRoles.NV, SpaceAndDirection.FANG),
            new PhonoSemantic(PeopleAndRoles.NV, SpaceAndDirection.FANG));

    /** 忍 (rěn) — endure. TopBottom: 刃 + 心. */
    public static final ComposedZi REN_ENDURE = new ComposedZi(
            uni("\u5FCD"), py(Initial.R, Head.OPEN, Body.E, Tail.N, Tone.THIRD),
            7, 61, "", new TopBottom(ToolsAndVessels.REN_BLADE, BodyParts.XIN),
            new PhonoSemantic(BodyParts.XIN, ToolsAndVessels.REN_BLADE));

    /** 鸡 (jī) — chicken. LeftRight: 又 + 鸟. */
    public static final ComposedZi JI_CHICKEN = new ComposedZi(
            uni("\u9E21"), py(Initial.J, Head.I, Body.NULL, Tail.NONE, Tone.FIRST),
            7, 196, "", new LeftRight(ActionsAndStates.YOU_AGAIN, Animals.NIAO),
            new PhonoSemantic(Animals.NIAO, ActionsAndStates.YOU_AGAIN));

    /** 驱 (qū) — drive. LeftRight: 马 + 区. */
    public static final ComposedZi QU_DRIVE = new ComposedZi(
            uni("\u9A71"), py(Initial.Q, Head.U, Body.NULL, Tail.NONE, Tone.FIRST),
            7, 187, "", new LeftRight(Animals.MA, SpaceAndDirection.QU_AREA),
            new PhonoSemantic(Animals.MA, SpaceAndDirection.QU_AREA));

    /** 纯 (chún) — pure. LeftRight: 纟 + 屯. */
    public static final ComposedZi CHUN_PURE = new ComposedZi(
            uni("\u7EAF"), py(Initial.CH, Head.U, Body.NULL, Tail.N, Tone.SECOND),
            7, 120, "", new LeftRight(JIAO_SI_PANG, Structures.TUN),
            new PhonoSemantic(JIAO_SI_PANG, Structures.TUN));

    /** 纱 (shā) — yarn, gauze. LeftRight: 纟 + 少. */
    public static final ComposedZi SHA_YARN = new ComposedZi(
            uni("\u7EB1"), py(Initial.SH, Head.OPEN, Body.A, Tail.NONE, Tone.FIRST),
            7, 120, "", new LeftRight(JIAO_SI_PANG, PeopleAndRoles.SHAO),
            new PhonoSemantic(JIAO_SI_PANG, PeopleAndRoles.SHAO));

    /** 纳 (nà) — accept. LeftRight: 纟 + 内. */
    public static final ComposedZi NA_ACCEPT = new ComposedZi(
            uni("\u7EB3"), py(Initial.N, Head.OPEN, Body.A, Tail.NONE, Tone.FOURTH),
            7, 120, "", new LeftRight(JIAO_SI_PANG, SpaceAndDirection.NEI),
            new PhonoSemantic(JIAO_SI_PANG, SpaceAndDirection.NEI));

    /** 纸 (zhǐ) — paper. LeftRight: 纟 + 氏. */
    public static final ComposedZi ZHI_PAPER = new ComposedZi(
            uni("\u7EB8"), py(Initial.ZH, Head.OPEN, Body.NULL, Tail.NONE, Tone.THIRD),
            7, 120, "", new LeftRight(JIAO_SI_PANG, PeopleAndRoles.SHI_CLAN),
            new PhonoSemantic(JIAO_SI_PANG, PeopleAndRoles.SHI_CLAN));

    /** 纹 (wén) — pattern, lines. LeftRight: 纟 + 文. */
    public static final ComposedZi WEN_PATTERN = new ComposedZi(
            uni("\u7EB9"), py(Initial.ZERO, Head.U, Body.E, Tail.N, Tone.SECOND),
            7, 120, "", new LeftRight(JIAO_SI_PANG, PeopleAndRoles.WEN),
            new PhonoSemantic(JIAO_SI_PANG, PeopleAndRoles.WEN));

    /** 纺 (fǎng) — spin (thread). LeftRight: 纟 + 方. */
    public static final ComposedZi FANG_SPIN = new ComposedZi(
            uni("\u7EBA"), py(Initial.F, Head.OPEN, Body.A, Tail.NG, Tone.THIRD),
            7, 120, "", new LeftRight(JIAO_SI_PANG, SpaceAndDirection.FANG),
            new PhonoSemantic(JIAO_SI_PANG, SpaceAndDirection.FANG));

    /** 驴 (lǘ) — donkey. LeftRight: 马 + 户. */
    public static final ComposedZi LV_DONKEY = new ComposedZi(
            uni("\u9A74"), py(Initial.L, Head.V, Body.NULL, Tail.NONE, Tone.SECOND),
            7, 187, "", new LeftRight(Animals.MA, Structures.HU),
            new PhonoSemantic(Animals.MA, Structures.HU));

    /** 纽 (niǔ) — button, knot. LeftRight: 纟 + 丑. */
    public static final ComposedZi NIU_BUTTON = new ComposedZi(
            uni("\u7EBD"), py(Initial.N, Head.I, Body.O, Tail.VOWEL_U, Tone.THIRD),
            7, 120, "", new LeftRight(JIAO_SI_PANG, AbstractConcepts.CHOU),
            new PhonoSemantic(JIAO_SI_PANG, AbstractConcepts.CHOU));

    /** 坚 (jiān) — firm, solid. TopBottom: LeftRight(又+刂) + 土. */
    public static final ComposedZi JIAN_FIRM = new ComposedZi(
            uni("\u575A"), py(Initial.J, Head.I, Body.A, Tail.N, Tone.FIRST),
            7, 32, "", new TopBottom(CommonBlocks.YOU_DAO, NatureElements.TU),
            new CompoundIndicative("\u53C8(again) + \u5202(knife) + \u571F(earth) \u2192 firm ground"));

    /** 驳 (bó) — refute, mottled. LeftRight: 马(semantic) + 爻(phonetic). */
    public static final ComposedZi BO_REFUTE = new ComposedZi(
            uni("\u9A73"), py(Initial.B, Head.OPEN, Body.O, Tail.NONE, Tone.SECOND),
            7, 187, "", new LeftRight(Animals.MA, RadicalComponents.YAO_LINES),
            new PhonoSemantic(Animals.MA, RadicalComponents.YAO_LINES));

    /** 别 (bié) — separate, don't. LeftRight: TopBottom(口+力) + 刂. */
    public static final ComposedZi BIE = new ComposedZi(
            uni("\u522B"), py(Initial.B, Head.I, Body.E, Tail.NONE, Tone.SECOND),
            7, 18, "", new LeftRight(new TopBottom(BodyParts.KOU, ToolsAndVessels.LI_PLOW), LI_DAO_PANG),
            new CompoundIndicative("\u53E3(mouth) + \u529B(force) + \u5202(knife) \u2192 separate"));

    /** 诊 (zhěn) — diagnose. LeftRight: 讠(semantic) + TopBottom(人+彡)(phonetic). */
    public static final ComposedZi ZHEN_DIAGNOSE = new ComposedZi(
            uni("\u8BCA"), py(Initial.ZH, Head.OPEN, Body.E, Tail.N, Tone.THIRD),
            7, 149, "", new LeftRight(YAN_ZI_PANG, CommonBlocks.REN_SHAN),
            new PhonoSemantic(YAN_ZI_PANG, CommonBlocks.REN_SHAN));

    /** 医 (yī) — medicine, doctor. SemiEnclosureLeftThree: 匚 + 矢. */
    public static final ComposedZi YI_MEDICINE = new ComposedZi(
            uni("\u533B"), py(Initial.ZERO, Head.I, Body.NULL, Tail.NONE, Tone.FIRST),
            7, 22, "", new SemiEnclosureLeftThree(SAN_KUANG, ToolsAndVessels.SHI_ARROW),
            new CompoundIndicative("\u531A(box) + \u77E2(arrow) \u2192 remove arrow \u2192 medicine"));

    public static final List<ComposedZi> ALL = List.of(
            HUA, NONG, XING_SHAPE, JIN_ADVANCE, JIE_WARN, TUN_SWALLOW,
            WEI_VIOLATE, YUN_TRANSPORT, FU_SUPPORT, FU_STROKE, TAN_ALTAR,
            JI_SKILL, HUAI, RAO, JU_REFUSE, ZHAO_FIND, PI_BATCH,
            CHE_PULL, ZHI_SITE, CHAO_COPY, BA_DAM, GONG_TRIBUTE,
            GONG_ATTACK, ZHE_FOLD, ZHUA_GRAB, QIANG_ROB, XIAO_FILIAL,
            JUN_EQUAL, PAO_THROW, TOU_THROW, FEN_GRAVE, KANG_RESIST,
            KENG, FANG_LANE, DOU_TREMBLE, HU_PROTECT, ZHI_WILL,
            NIU_TWIST, KUAI_BLOCK, BA_HOLD, BAO_REPORT, QUE, JIE_ROB,
            YA_SPROUT, QIN_CELERY, CANG_PALE, FANG_FRAGRANT, LU_REED,
            LAO, SU_REVIVE, GAN_POLE, GANG_BAR, DU_BIRCH, CAI_MATERIAL,
            CUN_VILLAGE, XING_APRICOT, JI_EXTREME, LI_PLUM, FOU,
            HAI_STILL, JIAN_ANNIHILATE, LIAN, HAN_DROUGHT, DING_STARE,
            CHENG_PRESENT, SHI_TIME, WU_SURNAME, ZHU_HELP, XIAN_COUNTY,
            DAI_DULL, KUANG_VAST, WEI_SURROUND, YA_AH, DUN_TON,
            YOU_MAIL, NAN_MALE, KUN, CHAO_NOISY, YUAN_MEMBER, TING_LISTEN,
            CHUI_BLOW, WU_WAIL, BA_PARTICLE, HOU_ROAR, ZHANG_TENT,
            CAI_WEALTH, ZHEN_NEEDLE, DING_NAIL_METAL, GAO_TELL, LI_PROFIT,
            TU_BALD, XIU_ELEGANT, SI_PRIVATE, TI_BODY, HE_WHAT,
            SHEN_STRETCH, ZUO_MAKE, BO_UNCLE, LING_CLEVER, YONG_SERVANT,
            DI_LOW, NI_YOU, ZHU_LIVE, WEI_POSITION, BAN_COMPANION,
            ZAO_SOAP, FO_BUDDHA, JIN_NEAR, YI_SERVICE, TUO,
            HAN_CONTAIN, LIN_NEIGHBOR, GAN_LIVER, DU_BELLY, KUANG_MAD,
            YOU_STILL, SHAN_DELETE, TIAO, YING_WELCOME, YIN_DRINK,
            DONG_FREEZE, ZHUANG_STATE, MU_ACRE, KUANG_SITUATION, CHUANG_BED,
            KU_WAREHOUSE, LIAO_CURE, LENG, ZHE_THIS, XU_ORDER,
            YE_SMELT, WANG_FORGET, XIAN_IDLE, JIAN_BETWEEN, MEN_STUFFY,
            PAN_JUDGE, ZAO_STOVE, CAN_BRILLIANT, WANG_VAST, SHA_SAND,
            QI_STEAM, WO_FERTILE, FAN_FLOOD, GOU_DITCH, MEI_NOT,
            SHEN_SINK, CHEN_SINK, HUAI_CHERISH, YOU_WORRY, KUAI_FAST,
            SONG, LAO_PRISON, JIU_INVESTIGATE, QIONG, ZAI_DISASTER,
            ZHENG_PROVE, QI_START, PING_EVALUATE, BU_MEND, CHU_INITIAL,
            SHE_SOCIETY, SU_TELL, CI_WORD, LING_SPIRIT, CENG,
            NIAO_URINE, WEI_TAIL, CHI_LATE, GAI_CHANGE, ZHANG_STRETCH,
            JI_TABOO, A_PREFIX, CHEN_DISPLAY, ZU_BLOCK, MIAO,
            YAO_MONSTER, FANG_HINDER, REN_ENDURE, JI_CHICKEN, QU_DRIVE,
            CHUN_PURE, SHA_YARN, NA_ACCEPT, ZHI_PAPER, WEN_PATTERN,
            FANG_SPIN, LV_DONKEY, NIU_BUTTON,
            JIAN_FIRM, BO_REFUTE, BIE, ZHEN_DIAGNOSE, YI_MEDICINE
    );
}
