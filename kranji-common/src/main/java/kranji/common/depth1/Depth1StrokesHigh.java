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

public final class Depth1StrokesHigh {

    private Depth1StrokesHigh() {}

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    private static ZiChar lit(String s) { return new ZiChar.Literal(s); }
    private static ZiChar uni(String s) { return new ZiChar.Unicode(s); }

    // ── 8 strokes ──────────────────────────────────────────────────────

    /** 明 (míng) — bright. LeftRight: 日 + 月. */
    public static final ComposedZi MING = new ComposedZi(
            lit("明"),
            py(Initial.M, Head.OPEN, Body.I, Tail.NG, Tone.SECOND),
            8, 72, "",
            new LeftRight(NatureElements.RI, NatureElements.YUE),
            new CompoundIndicative("日(sun) + 月(moon) → bright")
    );

    /** 林 (lín) — forest. LeftRight: 木 + 木. */
    public static final ComposedZi LIN = new ComposedZi(
            lit("林"),
            py(Initial.L, Head.OPEN, Body.I, Tail.N, Tone.SECOND),
            8, 75, "",
            new LeftRight(WoodFamily.MU, WoodFamily.MU),
            new CompoundIndicative("木(tree) + 木(tree) → forest")
    );

    /** 国 (guó) — country. FullEnclosure: 囗(semantic) + 玉(phonetic). */
    public static final ComposedZi GUO = new ComposedZi(
            lit("国"),
            py(Initial.G, Head.U, Body.O, Tail.NONE, Tone.SECOND),
            8, 31, "",
            new FullEnclosure(GUO_ZI_KUANG, Materials.YU_JADE),
            new PhonoSemantic(GUO_ZI_KUANG, Materials.YU_JADE)
    );

    // ── 8 strokes – hand radical 扌 ────────────────────────────────────

    /** 抹 (mǒ) — wipe. LeftRight: 扌(semantic) + 末(phonetic). */
    public static final ComposedZi MO_WIPE = new ComposedZi(
            lit("\u62B9"), py(Initial.M, Head.OPEN, Body.O, Tail.NONE, Tone.THIRD),
            8, 64, "", new LeftRight(TI_SHOU_PANG, PlantsAndAgriculture.MO),
            new PhonoSemantic(TI_SHOU_PANG, PlantsAndAgriculture.MO));

    /** 拢 (lǒng) — gather. LeftRight: 扌(semantic) + 龙(phonetic). */
    public static final ComposedZi LONG_GATHER = new ComposedZi(
            lit("\u62E2"), py(Initial.L, Head.OPEN, Body.O, Tail.NG, Tone.THIRD),
            8, 64, "", new LeftRight(TI_SHOU_PANG, Animals.LONG),
            new PhonoSemantic(TI_SHOU_PANG, Animals.LONG));

    /** 押 (yā) — press/detain. LeftRight: 扌(semantic) + 甲(phonetic). */
    public static final ComposedZi YA_PRESS = new ComposedZi(
            lit("\u62BC"), py(Initial.ZERO, Head.I, Body.A, Tail.NONE, Tone.FIRST),
            8, 64, "", new LeftRight(TI_SHOU_PANG, SpaceAndDirection.JIA_FIRST),
            new PhonoSemantic(TI_SHOU_PANG, SpaceAndDirection.JIA_FIRST));

    /** 抽 (chōu) — draw/pull. LeftRight: 扌(semantic) + 由(phonetic). */
    public static final ComposedZi CHOU_DRAW = new ComposedZi(
            lit("\u62BD"), py(Initial.CH, Head.OPEN, Body.O, Tail.VOWEL_U, Tone.FIRST),
            8, 64, "", new LeftRight(TI_SHOU_PANG, SpaceAndDirection.YOU_FROM),
            new PhonoSemantic(TI_SHOU_PANG, SpaceAndDirection.YOU_FROM));

    /** 拍 (pāi) — pat/clap. LeftRight: 扌(semantic) + 白(phonetic). */
    public static final ComposedZi PAI = new ComposedZi(
            lit("\u62CD"), py(Initial.P, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.FIRST),
            8, 64, "", new LeftRight(TI_SHOU_PANG, Materials.BAI_WHITE),
            new PhonoSemantic(TI_SHOU_PANG, Materials.BAI_WHITE));

    /** 拌 (bàn) — mix/stir. LeftRight: 扌(semantic) + 半(phonetic). */
    public static final ComposedZi BAN_MIX = new ComposedZi(
            lit("\u62CC"), py(Initial.B, Head.OPEN, Body.A, Tail.N, Tone.FOURTH),
            8, 64, "", new LeftRight(TI_SHOU_PANG, NumbersAndMeasure.BAN),
            new PhonoSemantic(TI_SHOU_PANG, NumbersAndMeasure.BAN));

    /** 披 (pī) — drape/wear. LeftRight: 扌(semantic) + 皮(phonetic). */
    public static final ComposedZi PI_DRAPE = new ComposedZi(
            lit("\u62AB"), py(Initial.P, Head.OPEN, Body.I, Tail.NONE, Tone.FIRST),
            8, 64, "", new LeftRight(TI_SHOU_PANG, BodyParts.PI),
            new PhonoSemantic(TI_SHOU_PANG, BodyParts.PI));

    /** 拨 (bō) — move/stir. LeftRight: 扌(semantic) + 发(phonetic). */
    public static final ComposedZi BO_STIR = new ComposedZi(
            lit("\u62E8"), py(Initial.B, Head.OPEN, Body.O, Tail.NONE, Tone.FIRST),
            8, 64, "", new LeftRight(TI_SHOU_PANG, ActionsAndStates.FA_SEND),
            new PhonoSemantic(TI_SHOU_PANG, ActionsAndStates.FA_SEND));

    /** 抬 (tái) — lift/raise. LeftRight: 扌(semantic) + 台(phonetic). */
    public static final ComposedZi TAI_LIFT = new ComposedZi(
            lit("\u62AC"), py(Initial.T, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.SECOND),
            8, 64, "", new LeftRight(TI_SHOU_PANG, Structures.TAI),
            new PhonoSemantic(TI_SHOU_PANG, Structures.TAI));

    /** 拆 (chāi) — dismantle. LeftRight: 扌(semantic) + 斥(phonetic). */
    public static final ComposedZi CHAI = new ComposedZi(
            lit("\u62C6"), py(Initial.CH, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.FIRST),
            8, 64, "", new LeftRight(TI_SHOU_PANG, ActionsAndStates.CHI_REPEL),
            new PhonoSemantic(TI_SHOU_PANG, ActionsAndStates.CHI_REPEL));

    // ── 8 strokes – water radical 氵 ───────────────────────────────────

    /** 沫 (mò) — foam. LeftRight: 氵(semantic) + 末(phonetic). */
    public static final ComposedZi MO_FOAM = new ComposedZi(
            lit("\u6CAB"), py(Initial.M, Head.OPEN, Body.O, Tail.NONE, Tone.FOURTH),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, PlantsAndAgriculture.MO),
            new PhonoSemantic(SAN_DIAN_SHUI, PlantsAndAgriculture.MO));

    /** 注 (zhù) — pour/annotate. LeftRight: 氵(semantic) + 主(phonetic). */
    public static final ComposedZi ZHU_POUR = new ComposedZi(
            lit("\u6CE8"), py(Initial.ZH, Head.U, Body.U, Tail.NONE, Tone.FOURTH),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, AbstractConcepts.ZHU_LORD),
            new PhonoSemantic(SAN_DIAN_SHUI, AbstractConcepts.ZHU_LORD));

    /** 沸 (fèi) — boil. LeftRight: 氵(semantic) + 弗(phonetic). */
    public static final ComposedZi FEI_BOIL = new ComposedZi(
            lit("\u6CB8"), py(Initial.F, Head.OPEN, Body.E, Tail.VOWEL_I, Tone.FOURTH),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, ToolsAndVessels.FU_NOT),
            new PhonoSemantic(SAN_DIAN_SHUI, ToolsAndVessels.FU_NOT));

    /** 泳 (yǒng) — swim. LeftRight: 氵(semantic) + 永(phonetic). */
    public static final ComposedZi YONG_SWIM = new ComposedZi(
            lit("\u6CF3"), py(Initial.ZERO, Head.I, Body.O, Tail.NG, Tone.THIRD),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, NatureElements.YONG_ETERNAL),
            new PhonoSemantic(SAN_DIAN_SHUI, NatureElements.YONG_ETERNAL));

    /** 油 (yóu) — oil. LeftRight: 氵(semantic) + 由(phonetic). */
    public static final ComposedZi YOU_OIL = new ComposedZi(
            lit("\u6CB9"), py(Initial.ZERO, Head.I, Body.O, Tail.VOWEL_U, Tone.SECOND),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, SpaceAndDirection.YOU_FROM),
            new PhonoSemantic(SAN_DIAN_SHUI, SpaceAndDirection.YOU_FROM));

    /** 泊 (bó) — moor/anchor. LeftRight: 氵(semantic) + 白(phonetic). */
    public static final ComposedZi BO_MOOR = new ComposedZi(
            lit("\u6CCA"), py(Initial.B, Head.OPEN, Body.O, Tail.NONE, Tone.SECOND),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, Materials.BAI_WHITE),
            new PhonoSemantic(SAN_DIAN_SHUI, Materials.BAI_WHITE));

    /** 河 (hé) — river. LeftRight: 氵(semantic) + 可(phonetic). */
    public static final ComposedZi HE_RIVER = new ComposedZi(
            lit("\u6CB3"), py(Initial.H, Head.OPEN, Body.E, Tail.NONE, Tone.SECOND),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, ActionsAndStates.KE),
            new PhonoSemantic(SAN_DIAN_SHUI, ActionsAndStates.KE));

    /** 泪 (lèi) — tear. LeftRight: 氵(semantic) + 目(phonetic). */
    public static final ComposedZi LEI_TEAR = new ComposedZi(
            lit("\u6CEA"), py(Initial.L, Head.OPEN, Body.E, Tail.VOWEL_I, Tone.FOURTH),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, BodyParts.MU),
            new PhonoSemantic(SAN_DIAN_SHUI, BodyParts.MU));

    /** 波 (bō) — wave. LeftRight: 氵(semantic) + 皮(phonetic). */
    public static final ComposedZi BO_WAVE = new ComposedZi(
            lit("\u6CE2"), py(Initial.B, Head.OPEN, Body.O, Tail.NONE, Tone.FIRST),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, BodyParts.PI),
            new PhonoSemantic(SAN_DIAN_SHUI, BodyParts.PI));

    /** 泄 (xiè) — leak. LeftRight: 氵(semantic) + 世(phonetic). */
    public static final ComposedZi XIE_LEAK = new ComposedZi(
            lit("\u6CC4"), py(Initial.X, Head.I, Body.E, Tail.NONE, Tone.FOURTH),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, AbstractConcepts.SHI_WORLD),
            new PhonoSemantic(SAN_DIAN_SHUI, AbstractConcepts.SHI_WORLD));

    /** 泼 (pō) — splash. LeftRight: 氵(semantic) + 发(phonetic). */
    public static final ComposedZi PO_SPLASH = new ComposedZi(
            lit("\u6CFC"), py(Initial.P, Head.OPEN, Body.O, Tail.NONE, Tone.FIRST),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, ActionsAndStates.FA_SEND),
            new PhonoSemantic(SAN_DIAN_SHUI, ActionsAndStates.FA_SEND));

    /** 治 (zhì) — govern/cure. LeftRight: 氵(semantic) + 台(phonetic). */
    public static final ComposedZi ZHI_GOVERN = new ComposedZi(
            lit("\u6CBB"), py(Initial.ZH, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, Structures.TAI),
            new PhonoSemantic(SAN_DIAN_SHUI, Structures.TAI));

    /** 法 (fǎ) — law/method. LeftRight: 氵(semantic) + 去(phonetic). */
    public static final ComposedZi FA_LAW = new ComposedZi(
            lit("\u6CD5"), py(Initial.F, Head.OPEN, Body.A, Tail.NONE, Tone.THIRD),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, ActionsAndStates.QU),
            new PhonoSemantic(SAN_DIAN_SHUI, ActionsAndStates.QU));

    // ── 8 strokes – woman radical 女 ───────────────────────────────────

    /** 姐 (jiě) — elder sister. LeftRight: 女(semantic) + 且(phonetic). */
    public static final ComposedZi JIE_SISTER = new ComposedZi(
            lit("\u59D0"), py(Initial.J, Head.I, Body.E, Tail.NONE, Tone.THIRD),
            8, 38, "", new LeftRight(PeopleAndRoles.NV, AbstractConcepts.QIE),
            new PhonoSemantic(PeopleAndRoles.NV, AbstractConcepts.QIE));

    /** 始 (shǐ) — begin. LeftRight: 女(semantic) + 台(phonetic). */
    public static final ComposedZi SHI_BEGIN = new ComposedZi(
            lit("\u59CB"), py(Initial.SH, Head.OPEN, Body.I, Tail.NONE, Tone.THIRD),
            8, 38, "", new LeftRight(PeopleAndRoles.NV, Structures.TAI),
            new PhonoSemantic(PeopleAndRoles.NV, Structures.TAI));

    /** 妹 (mèi) — younger sister. LeftRight: 女(semantic) + 未(phonetic). */
    public static final ComposedZi MEI_SISTER = new ComposedZi(
            lit("\u59B9"), py(Initial.M, Head.OPEN, Body.E, Tail.VOWEL_I, Tone.FOURTH),
            8, 38, "", new LeftRight(PeopleAndRoles.NV, PlantsAndAgriculture.WEI_NOT),
            new PhonoSemantic(PeopleAndRoles.NV, PlantsAndAgriculture.WEI_NOT));

    /** 姓 (xìng) — surname. LeftRight: 女(semantic) + 生(phonetic). */
    public static final ComposedZi XING_SURNAME = new ComposedZi(
            lit("\u59D3"), py(Initial.X, Head.OPEN, Body.I, Tail.NG, Tone.FOURTH),
            8, 38, "", new LeftRight(PeopleAndRoles.NV, ActionsAndStates.SHENG),
            new PhonoSemantic(PeopleAndRoles.NV, ActionsAndStates.SHENG));

    // ── 8 strokes – heart radical 忄 ───────────────────────────────────

    /** 性 (xìng) — nature/sex. LeftRight: 忄(semantic) + 生(phonetic). */
    public static final ComposedZi XING_NATURE = new ComposedZi(
            lit("\u6027"), py(Initial.X, Head.OPEN, Body.I, Tail.NG, Tone.FOURTH),
            8, 61, "", new LeftRight(SHU_XIN_PANG, ActionsAndStates.SHENG),
            new PhonoSemantic(SHU_XIN_PANG, ActionsAndStates.SHENG));

    /** 怕 (pà) — fear. LeftRight: 忄(semantic) + 白(phonetic). */
    public static final ComposedZi PA_FEAR = new ComposedZi(
            lit("\u6015"), py(Initial.P, Head.OPEN, Body.A, Tail.NONE, Tone.FOURTH),
            8, 61, "", new LeftRight(SHU_XIN_PANG, Materials.BAI_WHITE),
            new PhonoSemantic(SHU_XIN_PANG, Materials.BAI_WHITE));

    /** 怜 (lián) — pity. LeftRight: 忄(semantic) + 令(phonetic). */
    public static final ComposedZi LIAN_PITY = new ComposedZi(
            lit("\u601C"), py(Initial.L, Head.I, Body.A, Tail.N, Tone.SECOND),
            8, 61, "", new LeftRight(SHU_XIN_PANG, ActionsAndStates.LING),
            new PhonoSemantic(SHU_XIN_PANG, ActionsAndStates.LING));

    // ── 8 strokes – double person radical 彳 ──────────────────────────

    /** 彼 (bǐ) — that/those. LeftRight: 彳(semantic) + 皮(phonetic). */
    public static final ComposedZi BI_THAT = new ComposedZi(
            lit("\u5F7C"), py(Initial.B, Head.OPEN, Body.I, Tail.NONE, Tone.THIRD),
            8, 60, "", new LeftRight(ToolsAndVessels.CHI_STEP, BodyParts.PI),
            new PhonoSemantic(ToolsAndVessels.CHI_STEP, BodyParts.PI));

    /** 往 (wǎng) — go/toward. LeftRight: 彳(semantic) + 主(phonetic). */
    public static final ComposedZi WANG_GO = new ComposedZi(
            lit("\u5F80"), py(Initial.ZERO, Head.U, Body.A, Tail.NG, Tone.THIRD),
            8, 60, "", new LeftRight(ToolsAndVessels.CHI_STEP, AbstractConcepts.ZHU_LORD),
            new PhonoSemantic(ToolsAndVessels.CHI_STEP, AbstractConcepts.ZHU_LORD));

    // ── 8 strokes – silk radical 纟 ────────────────────────────────────

    /** 终 (zhōng) — end/final. LeftRight: 纟(semantic) + 冬(phonetic). */
    public static final ComposedZi ZHONG_END = new ComposedZi(
            lit("\u7EC8"), py(Initial.ZH, Head.OPEN, Body.O, Tail.NG, Tone.FIRST),
            8, 120, "", new LeftRight(JIAO_SI_PANG, NatureElements.DONG_WINTER),
            new PhonoSemantic(JIAO_SI_PANG, NatureElements.DONG_WINTER));

    /** 细 (xì) — thin/fine. LeftRight: 纟(semantic) + 田(phonetic). */
    public static final ComposedZi XI_THIN = new ComposedZi(
            lit("\u7EC6"), py(Initial.X, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH),
            8, 120, "", new LeftRight(JIAO_SI_PANG, NatureElements.TIAN),
            new PhonoSemantic(JIAO_SI_PANG, NatureElements.TIAN));

    /** 组 (zǔ) — group/organize. LeftRight: 纟(semantic) + 且(phonetic). */
    public static final ComposedZi ZU_GROUP = new ComposedZi(
            lit("\u7EC4"), py(Initial.Z, Head.U, Body.U, Tail.NONE, Tone.THIRD),
            8, 120, "", new LeftRight(JIAO_SI_PANG, AbstractConcepts.QIE),
            new PhonoSemantic(JIAO_SI_PANG, AbstractConcepts.QIE));

    // ── 8 strokes – moon/flesh radical 月 ──────────────────────────────

    /** 朋 (péng) — friend. LeftRight: 月 + 月. */
    public static final ComposedZi PENG = new ComposedZi(
            lit("\u670B"), py(Initial.P, Head.OPEN, Body.E, Tail.NG, Tone.SECOND),
            8, 74, "", new LeftRight(NatureElements.YUE, NatureElements.YUE),
            new CompoundIndicative("月(moon) + 月(moon) \u2192 friends"));

    /** 肿 (zhǒng) — swollen. LeftRight: 月(semantic) + 中(phonetic). */
    public static final ComposedZi ZHONG_SWOLLEN = new ComposedZi(
            lit("\u80BF"), py(Initial.ZH, Head.OPEN, Body.O, Tail.NG, Tone.THIRD),
            8, 130, "", new LeftRight(NatureElements.YUE, SpaceAndDirection.ZHONG),
            new PhonoSemantic(NatureElements.YUE, SpaceAndDirection.ZHONG));

    /** 肢 (zhī) — limb. LeftRight: 月(semantic) + 支(phonetic). */
    public static final ComposedZi ZHI_LIMB = new ComposedZi(
            lit("\u80A2"), py(Initial.ZH, Head.OPEN, Body.I, Tail.NONE, Tone.FIRST),
            8, 130, "", new LeftRight(NatureElements.YUE, ActionsAndStates.ZHI_BRANCH),
            new PhonoSemantic(NatureElements.YUE, ActionsAndStates.ZHI_BRANCH));

    /** 肥 (féi) — fat. LeftRight: 月(semantic) + 巴(phonetic). */
    public static final ComposedZi FEI_FAT = new ComposedZi(
            lit("\u80A5"), py(Initial.F, Head.OPEN, Body.E, Tail.VOWEL_I, Tone.SECOND),
            8, 130, "", new LeftRight(NatureElements.YUE, Structures.BA_CLING),
            new PhonoSemantic(NatureElements.YUE, Structures.BA_CLING));

    // ── 8 strokes – other LeftRight compositions ───────────────────────

    /** 到 (dào) — arrive. LeftRight: 至(semantic) + 刂(phonetic). */
    public static final ComposedZi DAO_ARRIVE = new ComposedZi(
            lit("\u5230"), py(Initial.D, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.FOURTH),
            8, 18, "", new LeftRight(RadicalComponents.ZHI_ARRIVE, LI_DAO_PANG),
            new PhonoSemantic(RadicalComponents.ZHI_ARRIVE, LI_DAO_PANG));

    /** 放 (fàng) — release. LeftRight: 方(semantic) + 攵(phonetic). */
    public static final ComposedZi FANG_RELEASE = new ComposedZi(
            lit("\u653E"), py(Initial.F, Head.OPEN, Body.A, Tail.NG, Tone.FOURTH),
            8, 66, "", new LeftRight(SpaceAndDirection.FANG, FAN_WEN_PANG),
            new CompoundIndicative("\u65B9(direction) + \u6535(strike) \u2192 release"));

    /** 取 (qǔ) — take. LeftRight: 耳(semantic) + 又(phonetic). */
    public static final ComposedZi QU_TAKE = new ComposedZi(
            lit("\u53D6"), py(Initial.Q, Head.V, Body.V, Tail.NONE, Tone.THIRD),
            8, 29, "", new LeftRight(BodyParts.ER, ActionsAndStates.YOU_AGAIN),
            new CompoundIndicative("\u8033(ear) + \u53C8(hand) \u2192 take"));

    /** 孤 (gū) — lonely. LeftRight: 子(semantic) + 瓜(phonetic). */
    public static final ComposedZi GU_LONELY = new ComposedZi(
            lit("\u5B64"), py(Initial.G, Head.U, Body.U, Tail.NONE, Tone.FIRST),
            8, 39, "", new LeftRight(PeopleAndRoles.ZI, PlantsAndAgriculture.GUA),
            new PhonoSemantic(PeopleAndRoles.ZI, PlantsAndAgriculture.GUA));

    /** 欧 (ōu) — Europe. LeftRight: 区(phonetic) + 欠(semantic). */
    public static final ComposedZi OU = new ComposedZi(
            lit("\u6B27"), py(Initial.ZERO, Head.OPEN, Body.O, Tail.VOWEL_U, Tone.FIRST),
            8, 76, "", new LeftRight(SpaceAndDirection.QU_AREA, RadicalComponents.QIAN_OWE),
            new PhonoSemantic(RadicalComponents.QIAN_OWE, SpaceAndDirection.QU_AREA));

    /** 斩 (zhǎn) — chop. LeftRight: 车(phonetic) + 斤(semantic). */
    public static final ComposedZi ZHAN_CHOP = new ComposedZi(
            lit("\u65A9"), py(Initial.ZH, Head.OPEN, Body.A, Tail.N, Tone.THIRD),
            8, 69, "", new LeftRight(ToolsAndVessels.CHE, ToolsAndVessels.JIN_AXE),
            new CompoundIndicative("\u8F66(vehicle) + \u65A4(axe) \u2192 chop"));

    /** 软 (ruǎn) — soft. LeftRight: 车(semantic) + 欠(phonetic). */
    public static final ComposedZi RUAN = new ComposedZi(
            lit("\u8F6F"), py(Initial.R, Head.U, Body.A, Tail.N, Tone.THIRD),
            8, 159, "", new LeftRight(ToolsAndVessels.CHE, RadicalComponents.QIAN_OWE),
            new PhonoSemantic(ToolsAndVessels.CHE, RadicalComponents.QIAN_OWE));

    /** 侄 (zhí) — nephew. LeftRight: 亻(semantic) + 至(phonetic). */
    public static final ComposedZi ZHI_NEPHEW = new ComposedZi(
            lit("\u4F84"), py(Initial.ZH, Head.OPEN, Body.I, Tail.NONE, Tone.SECOND),
            8, 9, "", new LeftRight(DAN_REN_PANG, RadicalComponents.ZHI_ARRIVE),
            new PhonoSemantic(DAN_REN_PANG, RadicalComponents.ZHI_ARRIVE));

    /** 供 (gōng) — supply. LeftRight: 亻(semantic) + 共(phonetic). */
    public static final ComposedZi GONG_SUPPLY = new ComposedZi(
            lit("\u4F9B"), py(Initial.G, Head.OPEN, Body.O, Tail.NG, Tone.FIRST),
            8, 9, "", new LeftRight(DAN_REN_PANG, ActionsAndStates.GONG_TOGETHER),
            new PhonoSemantic(DAN_REN_PANG, ActionsAndStates.GONG_TOGETHER));

    /** 物 (wù) — thing. LeftRight: 牛(semantic) + 勿(phonetic). */
    public static final ComposedZi WU_THING = new ComposedZi(
            lit("\u7269"), py(Initial.ZERO, Head.U, Body.U, Tail.NONE, Tone.FOURTH),
            8, 93, "", new LeftRight(Animals.NIU, ActionsAndStates.WU_DONT),
            new PhonoSemantic(Animals.NIU, ActionsAndStates.WU_DONT));

    /** 牧 (mù) — herd/shepherd. LeftRight: 牛(semantic) + 攵(phonetic). */
    public static final ComposedZi MU_HERD = new ComposedZi(
            lit("\u7267"), py(Initial.M, Head.U, Body.U, Tail.NONE, Tone.FOURTH),
            8, 93, "", new LeftRight(Animals.NIU, FAN_WEN_PANG),
            new CompoundIndicative("\u725B(cattle) + \u6535(strike) \u2192 herd"));

    /** 矿 (kuàng) — ore/mine. LeftRight: 石(semantic) + 广(phonetic). */
    public static final ComposedZi KUANG_ORE = new ComposedZi(
            lit("\u77FF"), py(Initial.K, Head.U, Body.A, Tail.NG, Tone.FOURTH),
            8, 112, "", new LeftRight(NatureElements.SHI, Structures.GUANG),
            new PhonoSemantic(NatureElements.SHI, Structures.GUANG));

    /** 码 (mǎ) — code/yard. LeftRight: 石(semantic) + 马(phonetic). */
    public static final ComposedZi MA_CODE = new ComposedZi(
            lit("\u7801"), py(Initial.M, Head.OPEN, Body.A, Tail.NONE, Tone.THIRD),
            8, 112, "", new LeftRight(NatureElements.SHI, Animals.MA),
            new PhonoSemantic(NatureElements.SHI, Animals.MA));

    /** 知 (zhī) — know. LeftRight: 矢(semantic) + 口(phonetic). */
    public static final ComposedZi ZHI_KNOW = new ComposedZi(
            lit("\u77E5"), py(Initial.ZH, Head.OPEN, Body.I, Tail.NONE, Tone.FIRST),
            8, 111, "", new LeftRight(ToolsAndVessels.SHI_ARROW, BodyParts.KOU),
            new CompoundIndicative("\u77E2(arrow) + \u53E3(mouth) \u2192 know"));

    /** 败 (bài) — defeat. LeftRight: 贝(semantic) + 攵(phonetic). */
    public static final ComposedZi BAI_DEFEAT = new ComposedZi(
            lit("\u8D25"), py(Initial.B, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.FOURTH),
            8, 154, "", new LeftRight(Animals.BEI, FAN_WEN_PANG),
            new PhonoSemantic(Animals.BEI, FAN_WEN_PANG));

    /** 狐 (hú) — fox. LeftRight: 犭(semantic) + 瓜(phonetic). */
    public static final ComposedZi HU_FOX = new ComposedZi(
            lit("\u72D0"), py(Initial.H, Head.U, Body.U, Tail.NONE, Tone.SECOND),
            8, 94, "", new LeftRight(FAN_QUAN_PANG, PlantsAndAgriculture.GUA),
            new PhonoSemantic(FAN_QUAN_PANG, PlantsAndAgriculture.GUA));

    /** 所 (suǒ) — place. LeftRight: 户(semantic) + 斤(phonetic). */
    public static final ComposedZi SUO_PLACE = new ComposedZi(
            lit("\u6240"), py(Initial.S, Head.U, Body.O, Tail.NONE, Tone.THIRD),
            8, 63, "", new LeftRight(Structures.HU, ToolsAndVessels.JIN_AXE),
            new CompoundIndicative("\u6237(door) + \u65A4(axe) \u2192 place"));

    /** 坡 (pō) — slope. LeftRight: 土(semantic) + 皮(phonetic). */
    public static final ComposedZi PO_SLOPE = new ComposedZi(
            lit("\u5761"), py(Initial.P, Head.OPEN, Body.O, Tail.NONE, Tone.FIRST),
            8, 32, "", new LeftRight(NatureElements.TU, BodyParts.PI),
            new PhonoSemantic(NatureElements.TU, BodyParts.PI));

    /** 味 (wèi) — taste. LeftRight: 口(semantic) + 未(phonetic). */
    public static final ComposedZi WEI_TASTE = new ComposedZi(
            lit("\u5473"), py(Initial.ZERO, Head.U, Body.E, Tail.VOWEL_I, Tone.FOURTH),
            8, 30, "", new LeftRight(BodyParts.KOU, PlantsAndAgriculture.WEI_NOT),
            new PhonoSemantic(BodyParts.KOU, PlantsAndAgriculture.WEI_NOT));

    /** 咏 (yǒng) — chant. LeftRight: 口(semantic) + 永(phonetic). */
    public static final ComposedZi YONG_CHANT = new ComposedZi(
            lit("\u548F"), py(Initial.ZERO, Head.I, Body.O, Tail.NG, Tone.THIRD),
            8, 30, "", new LeftRight(BodyParts.KOU, NatureElements.YONG_ETERNAL),
            new PhonoSemantic(BodyParts.KOU, NatureElements.YONG_ETERNAL));

    /** 鸣 (míng) — cry (of bird). LeftRight: 口(semantic) + 鸟(phonetic). */
    public static final ComposedZi MING_CRY = new ComposedZi(
            lit("\u9E23"), py(Initial.M, Head.OPEN, Body.I, Tail.NG, Tone.SECOND),
            8, 196, "", new LeftRight(BodyParts.KOU, Animals.NIAO),
            new CompoundIndicative("\u53E3(mouth) + \u9E1F(bird) \u2192 bird cry"));

    /** 驻 (zhù) — station/reside. LeftRight: 马(semantic) + 主(phonetic). */
    public static final ComposedZi ZHU_STATION = new ComposedZi(
            lit("\u9A7B"), py(Initial.ZH, Head.U, Body.U, Tail.NONE, Tone.FOURTH),
            8, 187, "", new LeftRight(Animals.MA, AbstractConcepts.ZHU_LORD),
            new PhonoSemantic(Animals.MA, AbstractConcepts.ZHU_LORD));

    /** 视 (shì) — look/regard. LeftRight: 礻(semantic) + 见(phonetic). */
    public static final ComposedZi SHI_LOOK = new ComposedZi(
            lit("\u89C6"), py(Initial.SH, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH),
            8, 147, "", new LeftRight(SHI_ZI_PANG_SPIRIT, ActionsAndStates.JIAN_SEE),
            new PhonoSemantic(SHI_ZI_PANG_SPIRIT, ActionsAndStates.JIAN_SEE));

    /** 钓 (diào) — fish (with rod). LeftRight: 钅(semantic) + 勺(phonetic). */
    public static final ComposedZi DIAO_FISH = new ComposedZi(
            lit("\u9493"), py(Initial.D, Head.I, Body.A, Tail.VOWEL_U, Tone.FOURTH),
            8, 167, "", new LeftRight(JIN_ZI_PANG, ToolsAndVessels.SHAO),
            new PhonoSemantic(JIN_ZI_PANG, ToolsAndVessels.SHAO));

    /** 旺 (wàng) — flourishing. LeftRight: 日(semantic) + 王(phonetic). */
    public static final ComposedZi WANG_FLOURISH = new ComposedZi(
            lit("\u65FA"), py(Initial.ZERO, Head.U, Body.A, Tail.NG, Tone.FOURTH),
            8, 72, "", new LeftRight(NatureElements.RI, PeopleAndRoles.WANG),
            new PhonoSemantic(NatureElements.RI, PeopleAndRoles.WANG));

    /** 衫 (shān) — shirt. LeftRight: 衤(semantic) + 彡(phonetic). */
    public static final ComposedZi SHAN_SHIRT = new ComposedZi(
            lit("\u886B"), py(Initial.SH, Head.OPEN, Body.A, Tail.N, Tone.FIRST),
            8, 145, "", new LeftRight(YI_ZI_PANG, RadicalComponents.SHAN_HAIR),
            new PhonoSemantic(YI_ZI_PANG, RadicalComponents.SHAN_HAIR));

    /** 陕 (shǎn) — Shaanxi. LeftRight: 阝(semantic) + 夹(phonetic). */
    public static final ComposedZi SHAN_SHAANXI = new ComposedZi(
            lit("\u9655"), py(Initial.SH, Head.OPEN, Body.A, Tail.N, Tone.THIRD),
            8, 170, "", new LeftRight(ZUO_ER_PANG, ActionsAndStates.JIA_CLAMP),
            new PhonoSemantic(ZUO_ER_PANG, ActionsAndStates.JIA_CLAMP));

    /** 限 (xiàn) — limit. LeftRight: 阝(semantic) + 艮(phonetic). */
    public static final ComposedZi XIAN_LIMIT = new ComposedZi(
            lit("\u9650"), py(Initial.X, Head.I, Body.A, Tail.N, Tone.FOURTH),
            8, 170, "", new LeftRight(ZUO_ER_PANG, BodyParts.GEN_TOUGH),
            new PhonoSemantic(ZUO_ER_PANG, BodyParts.GEN_TOUGH));

    /** 郊 (jiāo) — suburb. LeftRight: 交(phonetic) + 阝(semantic). */
    public static final ComposedZi JIAO_SUBURB = new ComposedZi(
            lit("\u90CA"), py(Initial.J, Head.I, Body.A, Tail.VOWEL_U, Tone.FIRST),
            8, 163, "", new LeftRight(ActionsAndStates.JIAO, YOU_ER_PANG),
            new PhonoSemantic(YOU_ER_PANG, ActionsAndStates.JIAO));

    /** 轮 (lún) — wheel. LeftRight: 车(semantic) + 仑(phonetic). */
    public static final ComposedZi LUN_WHEEL = new ComposedZi(
            lit("\u8F6E"), py(Initial.L, Head.U, Body.E, Tail.N, Tone.SECOND),
            8, 159, "", new LeftRight(ToolsAndVessels.CHE, AbstractConcepts.LUN),
            new PhonoSemantic(ToolsAndVessels.CHE, AbstractConcepts.LUN));

    // ── 8 strokes – TopBottom compositions ─────────────────────────────

    /** 奇 (qí) — strange. TopBottom: 大(phonetic) + 可(semantic). */
    public static final ComposedZi QI_STRANGE = new ComposedZi(
            lit("\u5947"), py(Initial.Q, Head.OPEN, Body.I, Tail.NONE, Tone.SECOND),
            8, 37, "", new TopBottom(PeopleAndRoles.DA, ActionsAndStates.KE),
            new PhonoSemantic(PeopleAndRoles.DA, ActionsAndStates.KE));

    /** 奋 (fèn) — exert. TopBottom: 大(top) + 田(bottom). */
    public static final ComposedZi FEN_EXERT = new ComposedZi(
            lit("\u594B"), py(Initial.F, Head.OPEN, Body.E, Tail.N, Tone.FOURTH),
            8, 37, "", new TopBottom(PeopleAndRoles.DA, NatureElements.TIAN),
            new CompoundIndicative("\u5927(big) + \u7530(field) \u2192 exert effort"));

    /** 季 (jì) — season. TopBottom: 禾(semantic) + 子(phonetic). */
    public static final ComposedZi JI_SEASON = new ComposedZi(
            lit("\u5B63"), py(Initial.J, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH),
            8, 39, "", new TopBottom(PlantsAndAgriculture.HE, PeopleAndRoles.ZI),
            new PhonoSemantic(PlantsAndAgriculture.HE, PeopleAndRoles.ZI));

    /** 委 (wěi) — entrust. TopBottom: 禾(semantic) + 女(phonetic). */
    public static final ComposedZi WEI_ENTRUST = new ComposedZi(
            lit("\u59D4"), py(Initial.ZERO, Head.U, Body.E, Tail.VOWEL_I, Tone.THIRD),
            8, 38, "", new TopBottom(PlantsAndAgriculture.HE, PeopleAndRoles.NV),
            new PhonoSemantic(PlantsAndAgriculture.HE, PeopleAndRoles.NV));

    /** 宗 (zōng) — clan/sect. TopBottom: 宀(semantic) + 示(phonetic). */
    public static final ComposedZi ZONG_CLAN = new ComposedZi(
            lit("\u5B97"), py(Initial.Z, Head.OPEN, Body.O, Tail.NG, Tone.FIRST),
            8, 40, "", new TopBottom(BAO_GAI_TOU, RadicalComponents.SHI_SHOW),
            new PhonoSemantic(BAO_GAI_TOU, RadicalComponents.SHI_SHOW));

    /** 宙 (zhòu) — cosmos. TopBottom: 宀(semantic) + 由(phonetic). */
    public static final ComposedZi ZHOU_COSMOS = new ComposedZi(
            lit("\u5B99"), py(Initial.ZH, Head.OPEN, Body.O, Tail.VOWEL_U, Tone.FOURTH),
            8, 40, "", new TopBottom(BAO_GAI_TOU, SpaceAndDirection.YOU_FROM),
            new PhonoSemantic(BAO_GAI_TOU, SpaceAndDirection.YOU_FROM));

    /** 宜 (yí) — fitting. TopBottom: 宀(semantic) + 且(phonetic). */
    public static final ComposedZi YI_FITTING = new ComposedZi(
            lit("\u5B9C"), py(Initial.ZERO, Head.OPEN, Body.I, Tail.NONE, Tone.SECOND),
            8, 40, "", new TopBottom(BAO_GAI_TOU, AbstractConcepts.QIE),
            new CompoundIndicative("\u5B80(roof) + \u4E14(moreover) \u2192 fitting"));

    /** 宝 (bǎo) — treasure. TopBottom: 宀(semantic) + 玉(phonetic). */
    public static final ComposedZi BAO_TREASURE = new ComposedZi(
            lit("\u5B9D"), py(Initial.B, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.THIRD),
            8, 40, "", new TopBottom(BAO_GAI_TOU, Materials.YU_JADE),
            new PhonoSemantic(BAO_GAI_TOU, Materials.YU_JADE));

    /** 审 (shěn) — examine. TopBottom: 宀(semantic) + 申(phonetic). */
    public static final ComposedZi SHEN_EXAMINE = new ComposedZi(
            lit("\u5BA1"), py(Initial.SH, Head.OPEN, Body.E, Tail.N, Tone.THIRD),
            8, 40, "", new TopBottom(BAO_GAI_TOU, SpaceAndDirection.SHEN_EXTEND),
            new PhonoSemantic(BAO_GAI_TOU, SpaceAndDirection.SHEN_EXTEND));

    /** 空 (kōng) — empty/sky. TopBottom: 穴(semantic) + 工(phonetic). */
    public static final ComposedZi KONG = new ComposedZi(
            lit("\u7A7A"), py(Initial.K, Head.OPEN, Body.O, Tail.NG, Tone.FIRST),
            8, 116, "", new TopBottom(Structures.XUE, ActionsAndStates.GONG_WORK),
            new PhonoSemantic(Structures.XUE, ActionsAndStates.GONG_WORK));

    /** 岩 (yán) — rock. TopBottom: 山(semantic) + 石(phonetic). */
    public static final ComposedZi YAN_ROCK = new ComposedZi(
            lit("\u5CA9"), py(Initial.ZERO, Head.I, Body.A, Tail.N, Tone.SECOND),
            8, 46, "", new TopBottom(NatureElements.SHAN, NatureElements.SHI),
            new CompoundIndicative("\u5C71(mountain) + \u77F3(stone) \u2192 rock"));

    /** 忠 (zhōng) — loyal. TopBottom: 中(phonetic) + 心(semantic). */
    public static final ComposedZi ZHONG_LOYAL = new ComposedZi(
            lit("\u5FE0"), py(Initial.ZH, Head.OPEN, Body.O, Tail.NG, Tone.FIRST),
            8, 61, "", new TopBottom(SpaceAndDirection.ZHONG, BodyParts.XIN),
            new PhonoSemantic(BodyParts.XIN, SpaceAndDirection.ZHONG));

    /** 忽 (hū) — suddenly. TopBottom: 勿(phonetic) + 心(semantic). */
    public static final ComposedZi HU_SUDDENLY = new ComposedZi(
            lit("\u603D"), py(Initial.H, Head.U, Body.U, Tail.NONE, Tone.FIRST),
            8, 61, "", new TopBottom(ActionsAndStates.WU_DONT, BodyParts.XIN),
            new PhonoSemantic(BodyParts.XIN, ActionsAndStates.WU_DONT));

    /** 盲 (máng) — blind. TopBottom: 亡(semantic) + 目(semantic). */
    public static final ComposedZi MANG_BLIND = new ComposedZi(
            lit("\u76F2"), py(Initial.M, Head.OPEN, Body.A, Tail.NG, Tone.SECOND),
            8, 109, "", new TopBottom(ActionsAndStates.WANG, BodyParts.MU),
            new CompoundIndicative("\u4EA1(perish) + \u76EE(eye) \u2192 blind"));

    /** 斧 (fǔ) — axe. TopBottom: 父(phonetic) + 斤(semantic). */
    public static final ComposedZi FU_AXE = new ComposedZi(
            lit("\u65A7"), py(Initial.F, Head.U, Body.U, Tail.NONE, Tone.THIRD),
            8, 69, "", new TopBottom(PeopleAndRoles.FU_FATHER, ToolsAndVessels.JIN_AXE),
            new PhonoSemantic(ToolsAndVessels.JIN_AXE, PeopleAndRoles.FU_FATHER));

    /** 炎 (yán) — flame. TopBottom: 火 + 火. */
    public static final ComposedZi YAN_FLAME = new ComposedZi(
            lit("\u708E"), py(Initial.ZERO, Head.I, Body.A, Tail.N, Tone.SECOND),
            8, 86, "", new TopBottom(NatureElements.HUO, NatureElements.HUO),
            new CompoundIndicative("\u706B(fire) + \u706B(fire) \u2192 flame"));

    /** 昆 (kūn) — elder brother/together. TopBottom: 日(semantic) + 比(phonetic). */
    public static final ComposedZi KUN = new ComposedZi(
            lit("\u6606"), py(Initial.K, Head.U, Body.E, Tail.N, Tone.FIRST),
            8, 72, "", new TopBottom(NatureElements.RI, ActionsAndStates.BI_COMPARE),
            new CompoundIndicative("\u65E5(sun) + \u6BD4(together) \u2192 all together"));

    /** 肩 (jiān) — shoulder. TopBottom: 户(top) + 月(bottom). */
    public static final ComposedZi JIAN_SHOULDER = new ComposedZi(
            lit("\u80A9"), py(Initial.J, Head.I, Body.A, Tail.N, Tone.FIRST),
            8, 130, "", new TopBottom(Structures.HU, NatureElements.YUE),
            new CompoundIndicative("\u6237(door) + \u6708(flesh) \u2192 shoulder"));

    /** 苹 (píng) — apple (modern). TopBottom: 艹(semantic) + 平(phonetic). */
    public static final ComposedZi PING_APPLE = new ComposedZi(
            lit("\u82F9"), py(Initial.P, Head.OPEN, Body.I, Tail.NG, Tone.SECOND),
            8, 140, "", new TopBottom(CAO_ZI_TOU, AbstractConcepts.PING),
            new PhonoSemantic(CAO_ZI_TOU, AbstractConcepts.PING));

    /** 苗 (miáo) — seedling. TopBottom: 艹(semantic) + 田(phonetic). */
    public static final ComposedZi MIAO_SEEDLING = new ComposedZi(
            lit("\u82D7"), py(Initial.M, Head.I, Body.A, Tail.VOWEL_U, Tone.SECOND),
            8, 140, "", new TopBottom(CAO_ZI_TOU, NatureElements.TIAN),
            new PhonoSemantic(CAO_ZI_TOU, NatureElements.TIAN));

    /** 英 (yīng) — hero/flower. TopBottom: 艹(semantic) + 央(phonetic). */
    public static final ComposedZi YING_HERO = new ComposedZi(
            lit("\u82F1"), py(Initial.ZERO, Head.OPEN, Body.I, Tail.NG, Tone.FIRST),
            8, 140, "", new TopBottom(CAO_ZI_TOU, SpaceAndDirection.YANG_CENTER),
            new PhonoSemantic(CAO_ZI_TOU, SpaceAndDirection.YANG_CENTER));

    /** 若 (ruò) — if/like. TopBottom: 艹(semantic) + 右(phonetic). */
    public static final ComposedZi RUO = new ComposedZi(
            lit("\u82E5"), py(Initial.R, Head.U, Body.O, Tail.NONE, Tone.FOURTH),
            8, 140, "", new TopBottom(CAO_ZI_TOU, SpaceAndDirection.YOU_RIGHT),
            new PhonoSemantic(CAO_ZI_TOU, SpaceAndDirection.YOU_RIGHT));

    /** 茅 (máo) — thatch. TopBottom: 艹(semantic) + 矛(phonetic). */
    public static final ComposedZi MAO_THATCH = new ComposedZi(
            lit("\u8305"), py(Initial.M, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.SECOND),
            8, 140, "", new TopBottom(CAO_ZI_TOU, ToolsAndVessels.MAO),
            new PhonoSemantic(CAO_ZI_TOU, ToolsAndVessels.MAO));

    /** 念 (niàn) — think/miss. TopBottom: 今(phonetic) + 心(semantic). */
    public static final ComposedZi NIAN_THINK = new ComposedZi(
            lit("\u5FF5"), py(Initial.N, Head.I, Body.A, Tail.N, Tone.FOURTH),
            8, 61, "", new TopBottom(AbstractConcepts.JIN, BodyParts.XIN),
            new PhonoSemantic(BodyParts.XIN, AbstractConcepts.JIN));

    /** 贪 (tān) — greedy. TopBottom: 今(phonetic) + 贝(semantic). */
    public static final ComposedZi TAN_GREEDY = new ComposedZi(
            lit("\u8D2A"), py(Initial.T, Head.OPEN, Body.A, Tail.N, Tone.FIRST),
            8, 154, "", new TopBottom(AbstractConcepts.JIN, Animals.BEI),
            new PhonoSemantic(Animals.BEI, AbstractConcepts.JIN));

    /** 货 (huò) — goods. TopBottom: 化(phonetic) + 贝(semantic). */
    public static final ComposedZi HUO_GOODS = new ComposedZi(
            lit("\u8D27"), py(Initial.H, Head.U, Body.O, Tail.NONE, Tone.FOURTH),
            8, 154, "", new TopBottom(ActionsAndStates.HUA_CHANGE, Animals.BEI),
            new PhonoSemantic(Animals.BEI, ActionsAndStates.HUA_CHANGE));

    // ── 8 strokes – SemiEnclosure compositions ─────────────────────────

    /** 迫 (pò) — press/urgent. SemiEnclosureBottomLeft: 辶(semantic) + 白(phonetic). */
    public static final ComposedZi PO_PRESS = new ComposedZi(
            lit("\u8FEB"), py(Initial.P, Head.OPEN, Body.O, Tail.NONE, Tone.FOURTH),
            8, 162, "", new SemiEnclosureBottomLeft(ZOU_ZHI_DI, Materials.BAI_WHITE),
            new PhonoSemantic(ZOU_ZHI_DI, Materials.BAI_WHITE));

    /** 房 (fáng) — room/house. SemiEnclosureUpperLeft: 户(semantic) + 方(phonetic). */
    public static final ComposedZi FANG_ROOM = new ComposedZi(
            lit("\u623F"), py(Initial.F, Head.OPEN, Body.A, Tail.NG, Tone.SECOND),
            8, 63, "", new SemiEnclosureUpperLeft(Structures.HU, SpaceAndDirection.FANG),
            new PhonoSemantic(Structures.HU, SpaceAndDirection.FANG));

    /** 届 (jiè) — session/届. SemiEnclosureUpperLeft: 尸(semantic) + 由(phonetic). */
    public static final ComposedZi JIE_SESSION = new ComposedZi(
            lit("\u5C4A"), py(Initial.J, Head.I, Body.E, Tail.NONE, Tone.FOURTH),
            8, 44, "", new SemiEnclosureUpperLeft(Structures.SHI_CORPSE, SpaceAndDirection.YOU_FROM),
            new PhonoSemantic(Structures.SHI_CORPSE, SpaceAndDirection.YOU_FROM));

    /** 屈 (qū) — bend/yield. SemiEnclosureUpperLeft: 尸(semantic) + 出(phonetic). */
    public static final ComposedZi QU_BEND = new ComposedZi(
            lit("\u5C48"), py(Initial.Q, Head.V, Body.V, Tail.NONE, Tone.FIRST),
            8, 44, "", new SemiEnclosureUpperLeft(Structures.SHI_CORPSE, ActionsAndStates.CHU),
            new PhonoSemantic(Structures.SHI_CORPSE, ActionsAndStates.CHU));

    // ── 8 strokes – additional wood radical 木 ──────────────────────────

    /** 枝 (zhī) — branch. LeftRight: 木(semantic) + 支(phonetic). */
    public static final ComposedZi ZHI_TWIG = new ComposedZi(
            lit("\u679D"), py(Initial.ZH, Head.OPEN, Body.I, Tail.NONE, Tone.FIRST),
            8, 75, "", new LeftRight(WoodFamily.MU, ActionsAndStates.ZHI_BRANCH),
            new PhonoSemantic(WoodFamily.MU, ActionsAndStates.ZHI_BRANCH));

    /** 杯 (bēi) — cup. LeftRight: 木(semantic) + 不(phonetic). */
    public static final ComposedZi BEI_CUP = new ComposedZi(
            lit("\u676F"), py(Initial.B, Head.OPEN, Body.E, Tail.VOWEL_I, Tone.FIRST),
            8, 75, "", new LeftRight(WoodFamily.MU, AbstractConcepts.BU),
            new PhonoSemantic(WoodFamily.MU, AbstractConcepts.BU));

    /** 析 (xī) — analyze. LeftRight: 木(semantic) + 斤(semantic). */
    public static final ComposedZi XI_ANALYZE = new ComposedZi(
            lit("\u6790"), py(Initial.X, Head.OPEN, Body.I, Tail.NONE, Tone.FIRST),
            8, 75, "", new LeftRight(WoodFamily.MU, ToolsAndVessels.JIN_AXE),
            new CompoundIndicative("\u6728(wood) + \u65A4(axe) \u2192 split/analyze"));

    /** 枪 (qiāng) — gun/spear. LeftRight: 木(semantic) + 仓(phonetic). */
    public static final ComposedZi QIANG_GUN = new ComposedZi(
            lit("\u67AA"), py(Initial.Q, Head.I, Body.A, Tail.NG, Tone.FIRST),
            8, 75, "", new LeftRight(WoodFamily.MU, Structures.CANG),
            new PhonoSemantic(WoodFamily.MU, Structures.CANG));

    /** 柜 (guì) — cabinet. LeftRight: 木(semantic) + 巨(phonetic). */
    public static final ComposedZi GUI_CABINET = new ComposedZi(
            lit("\u67DC"), py(Initial.G, Head.U, Body.E, Tail.VOWEL_I, Tone.FOURTH),
            8, 75, "", new LeftRight(WoodFamily.MU, Structures.JU),
            new PhonoSemantic(WoodFamily.MU, Structures.JU));

    // ── 8 strokes – grain radical 禾 ──────────────────────────────────────

    /** 秆 (gǎn) — straw/stalk. LeftRight: 禾(semantic) + 干(phonetic). */
    public static final ComposedZi GAN_STRAW = new ComposedZi(
            lit("\u79C6"), py(Initial.G, Head.OPEN, Body.A, Tail.N, Tone.THIRD),
            8, 115, "", new LeftRight(PlantsAndAgriculture.HE, SpaceAndDirection.GAN_DRY),
            new PhonoSemantic(PlantsAndAgriculture.HE, SpaceAndDirection.GAN_DRY));

    /** 和 (hé) — harmony. LeftRight: 禾(semantic) + 口(semantic). */
    public static final ComposedZi HE_HARMONY = new ComposedZi(
            lit("\u548C"), py(Initial.H, Head.OPEN, Body.E, Tail.NONE, Tone.SECOND),
            8, 115, "", new LeftRight(PlantsAndAgriculture.HE, BodyParts.KOU),
            new CompoundIndicative("\u79BE(grain) + \u53E3(mouth) \u2192 harmony"));

    // ── 8 strokes – grass radical 艹 (continued) ─────────────────────────

    /** 苦 (kǔ) — bitter. TopBottom: 艹(semantic) + 古(phonetic). */
    public static final ComposedZi KU_BITTER = new ComposedZi(
            lit("\u82E6"), py(Initial.K, Head.U, Body.U, Tail.NONE, Tone.THIRD),
            8, 140, "", new TopBottom(CAO_ZI_TOU, AbstractConcepts.GU),
            new PhonoSemantic(CAO_ZI_TOU, AbstractConcepts.GU));

    // ── 8 strokes – enclosure 囗/门 ──────────────────────────────────────

    /** 固 (gù) — solid. FullEnclosure: 囗(semantic) + 古(phonetic). */
    public static final ComposedZi GU_SOLID = new ComposedZi(
            lit("\u56FA"), py(Initial.G, Head.U, Body.U, Tail.NONE, Tone.FOURTH),
            8, 31, "", new FullEnclosure(GUO_ZI_KUANG, AbstractConcepts.GU),
            new PhonoSemantic(GUO_ZI_KUANG, AbstractConcepts.GU));

    /** 图 (tú) — picture/map. FullEnclosure: 囗(semantic) + 冬(phonetic). */
    public static final ComposedZi TU_MAP = new ComposedZi(
            lit("\u56FE"), py(Initial.T, Head.U, Body.U, Tail.NONE, Tone.SECOND),
            8, 31, "", new FullEnclosure(GUO_ZI_KUANG, NatureElements.DONG_WINTER),
            new PhonoSemantic(GUO_ZI_KUANG, NatureElements.DONG_WINTER));

    /** 闸 (zhá) — sluice gate. FullEnclosure: 门(semantic) + 甲(phonetic). */
    public static final ComposedZi ZHA_SLUICE = new ComposedZi(
            lit("\u95F8"), py(Initial.ZH, Head.OPEN, Body.A, Tail.NONE, Tone.SECOND),
            8, 169, "", new FullEnclosure(ToolsAndVessels.MEN, SpaceAndDirection.JIA_FIRST),
            new PhonoSemantic(ToolsAndVessels.MEN, SpaceAndDirection.JIA_FIRST));

    /** 闹 (nào) — noisy. FullEnclosure: 门(semantic) + 市(phonetic). */
    public static final ComposedZi NAO_NOISY = new ComposedZi(
            lit("\u95F9"), py(Initial.N, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.FOURTH),
            8, 169, "", new FullEnclosure(ToolsAndVessels.MEN, Structures.SHI_MARKET),
            new PhonoSemantic(ToolsAndVessels.MEN, Structures.SHI_MARKET));

    // ── 8 strokes – additional TopBottom ──────────────────────────────────

    /** 昌 (chāng) — prosper. TopBottom: 日 + 日. */
    public static final ComposedZi CHANG_PROSPER = new ComposedZi(
            lit("\u660C"), py(Initial.CH, Head.OPEN, Body.A, Tail.NG, Tone.FIRST),
            8, 72, "", new TopBottom(NatureElements.RI, NatureElements.RI),
            new CompoundIndicative("\u65E5(sun) + \u65E5(sun) \u2192 prosperous"));

    /** 态 (tài) — attitude. TopBottom: 太(phonetic) + 心(semantic). */
    public static final ComposedZi TAI_ATTITUDE = new ComposedZi(
            lit("\u6001"), py(Initial.T, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.FOURTH),
            8, 61, "", new TopBottom(PeopleAndRoles.TAI_GREAT, BodyParts.XIN),
            new PhonoSemantic(BodyParts.XIN, PeopleAndRoles.TAI_GREAT));

    /** 昏 (hūn) — dusk. TopBottom: 氏(phonetic) + 日(semantic). */
    public static final ComposedZi HUN_DUSK = new ComposedZi(
            lit("\u660F"), py(Initial.H, Head.U, Body.E, Tail.N, Tone.FIRST),
            8, 72, "", new TopBottom(PeopleAndRoles.SHI_CLAN, NatureElements.RI),
            new CompoundIndicative("\u6C0F(clan/setting) + \u65E5(sun) \u2192 dusk"));

    /** 爸 (bà) — dad. TopBottom: 父(semantic) + 巴(phonetic). */
    public static final ComposedZi BA_DAD = new ComposedZi(
            lit("\u7238"), py(Initial.B, Head.OPEN, Body.A, Tail.NONE, Tone.FOURTH),
            8, 88, "", new TopBottom(PeopleAndRoles.FU_FATHER, Structures.BA_CLING),
            new PhonoSemantic(PeopleAndRoles.FU_FATHER, Structures.BA_CLING));

    /** 垄 (lǒng) — ridge. TopBottom: 龙(phonetic) + 土(semantic). */
    public static final ComposedZi LONG_RIDGE = new ComposedZi(
            lit("\u5784"), py(Initial.L, Head.OPEN, Body.O, Tail.NG, Tone.THIRD),
            8, 32, "", new TopBottom(Animals.LONG, NatureElements.TU),
            new PhonoSemantic(NatureElements.TU, Animals.LONG));

    /** 帘 (lián) — curtain. TopBottom: 穴(semantic) + 巾(phonetic). */
    public static final ComposedZi LIAN_CURTAIN = new ComposedZi(
            lit("\u5E18"), py(Initial.L, Head.I, Body.A, Tail.N, Tone.SECOND),
            8, 50, "", new TopBottom(Structures.XUE, Materials.JIN_TOWEL),
            new PhonoSemantic(Structures.XUE, Materials.JIN_TOWEL));

    // ── 8 strokes – speech radical 讠 (continued) ────────────────────────

    /** 话 (huà) — speech. LeftRight: 讠(semantic) + 舌(phonetic). */
    public static final ComposedZi HUA_SPEECH = new ComposedZi(
            lit("\u8BDD"), py(Initial.H, Head.U, Body.A, Tail.NONE, Tone.FOURTH),
            8, 149, "", new LeftRight(YAN_ZI_PANG, BodyParts.SHE),
            new PhonoSemantic(YAN_ZI_PANG, BodyParts.SHE));

    /** 该 (gāi) — should. LeftRight: 讠(semantic) + 亥(phonetic). */
    public static final ComposedZi GAI_SHOULD = new ComposedZi(
            lit("\u8BE5"), py(Initial.G, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.FIRST),
            8, 149, "", new LeftRight(YAN_ZI_PANG, AbstractConcepts.HAI),
            new PhonoSemantic(YAN_ZI_PANG, AbstractConcepts.HAI));

    /** 详 (xiáng) — detailed. LeftRight: 讠(semantic) + 羊(phonetic). */
    public static final ComposedZi XIANG_DETAIL = new ComposedZi(
            lit("\u8BE6"), py(Initial.X, Head.I, Body.A, Tail.NG, Tone.SECOND),
            8, 149, "", new LeftRight(YAN_ZI_PANG, Animals.YANG),
            new PhonoSemantic(YAN_ZI_PANG, Animals.YANG));

    // ── 8 strokes – person radical 亻 (continued) ────────────────────────

    /** 使 (shǐ) — use/make. LeftRight: 亻(semantic) + 吏(phonetic). */
    public static final ComposedZi SHI_USE = new ComposedZi(
            lit("\u4F7F"), py(Initial.SH, Head.OPEN, Body.I, Tail.NONE, Tone.THIRD),
            8, 9, "", new LeftRight(DAN_REN_PANG, PeopleAndRoles.LI_OFFICIAL),
            new PhonoSemantic(DAN_REN_PANG, PeopleAndRoles.LI_OFFICIAL));

    /** 依 (yī) — rely. LeftRight: 亻(semantic) + 衣(phonetic). */
    public static final ComposedZi YI_RELY = new ComposedZi(
            lit("\u4F9D"), py(Initial.ZERO, Head.OPEN, Body.I, Tail.NONE, Tone.FIRST),
            8, 9, "", new LeftRight(DAN_REN_PANG, Materials.YI_CLOTHES),
            new PhonoSemantic(DAN_REN_PANG, Materials.YI_CLOTHES));

    // ── 8 strokes – fire radical 火 ──────────────────────────────────────

    /** 炒 (chǎo) — stir-fry. LeftRight: 火(semantic) + 少(phonetic). */
    public static final ComposedZi CHAO_STIRFRY = new ComposedZi(
            lit("\u7092"), py(Initial.CH, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.THIRD),
            8, 86, "", new LeftRight(NatureElements.HUO, PeopleAndRoles.SHAO),
            new PhonoSemantic(NatureElements.HUO, PeopleAndRoles.SHAO));

    /** 炊 (chuī) — cook. LeftRight: 火(semantic) + 欠(phonetic). */
    public static final ComposedZi CHUI_COOK = new ComposedZi(
            lit("\u7094"), py(Initial.CH, Head.U, Body.E, Tail.VOWEL_I, Tone.FIRST),
            8, 86, "", new LeftRight(NatureElements.HUO, RadicalComponents.QIAN_OWE),
            new PhonoSemantic(NatureElements.HUO, RadicalComponents.QIAN_OWE));

    /** 炉 (lú) — stove. LeftRight: 火(semantic) + 户(phonetic). */
    public static final ComposedZi LU_STOVE = new ComposedZi(
            lit("\u7089"), py(Initial.L, Head.U, Body.U, Tail.NONE, Tone.SECOND),
            8, 86, "", new LeftRight(NatureElements.HUO, Structures.HU),
            new PhonoSemantic(NatureElements.HUO, Structures.HU));

    // ── 8 strokes – additional LeftRight (batch 2) ───────────────────────

    /** 衬 (chèn) — lining. LeftRight: 衤(semantic) + 寸(phonetic). */
    public static final ComposedZi CHEN_LINING = new ComposedZi(
            lit("\u886C"), py(Initial.CH, Head.OPEN, Body.E, Tail.N, Tone.FOURTH),
            8, 145, "", new LeftRight(YI_ZI_PANG, Structures.CUN_INCH),
            new PhonoSemantic(YI_ZI_PANG, Structures.CUN_INCH));

    /** 呼 (hū) — call/shout. LeftRight: 口(semantic) + 乎(phonetic). */
    public static final ComposedZi HU_CALL = new ComposedZi(
            lit("\u547C"), py(Initial.H, Head.U, Body.U, Tail.NONE, Tone.FIRST),
            8, 30, "", new LeftRight(BodyParts.KOU, AbstractConcepts.HU_QUESTION),
            new PhonoSemantic(BodyParts.KOU, AbstractConcepts.HU_QUESTION));

    /** 刮 (guā) — scrape. LeftRight: 舌(phonetic) + 刂(semantic). */
    public static final ComposedZi GUA_SCRAPE = new ComposedZi(
            lit("\u522E"), py(Initial.G, Head.U, Body.A, Tail.NONE, Tone.FIRST),
            8, 18, "", new LeftRight(BodyParts.SHE, LI_DAO_PANG),
            new PhonoSemantic(LI_DAO_PANG, BodyParts.SHE));

    /** 欣 (xīn) — joy. LeftRight: 斤(phonetic) + 欠(semantic). */
    public static final ComposedZi XIN_JOY = new ComposedZi(
            lit("\u6B23"), py(Initial.X, Head.OPEN, Body.I, Tail.N, Tone.FIRST),
            8, 76, "", new LeftRight(ToolsAndVessels.JIN_AXE, RadicalComponents.QIAN_OWE),
            new PhonoSemantic(RadicalComponents.QIAN_OWE, ToolsAndVessels.JIN_AXE));

    /** 拉 (lā) — pull. LeftRight: 扌(semantic) + 立(phonetic). */
    public static final ComposedZi LA_PULL = new ComposedZi(
            lit("\u62C9"), py(Initial.L, Head.OPEN, Body.A, Tail.NONE, Tone.FIRST),
            8, 64, "", new LeftRight(TI_SHOU_PANG, ActionsAndStates.LI_STAND),
            new PhonoSemantic(TI_SHOU_PANG, ActionsAndStates.LI_STAND));

    /** 拦 (lán) — block/bar. LeftRight: 扌(semantic) + 兰(phonetic). */
    public static final ComposedZi LAN_BLOCK = new ComposedZi(
            lit("\u62E6"), py(Initial.L, Head.OPEN, Body.A, Tail.N, Tone.SECOND),
            8, 64, "", new LeftRight(TI_SHOU_PANG, PlantsAndAgriculture.LAN),
            new PhonoSemantic(TI_SHOU_PANG, PlantsAndAgriculture.LAN));

    /** 拥 (yōng) — embrace. LeftRight: 扌(semantic) + 用(phonetic). */
    public static final ComposedZi YONG_EMBRACE = new ComposedZi(
            lit("\u62E5"), py(Initial.ZERO, Head.I, Body.O, Tail.NG, Tone.FIRST),
            8, 64, "", new LeftRight(TI_SHOU_PANG, ActionsAndStates.YONG),
            new PhonoSemantic(TI_SHOU_PANG, ActionsAndStates.YONG));

    /** 姑 (gū) — aunt. LeftRight: 女(semantic) + 古(phonetic). */
    public static final ComposedZi GU_AUNT = new ComposedZi(
            lit("\u59D1"), py(Initial.G, Head.U, Body.U, Tail.NONE, Tone.FIRST),
            8, 38, "", new LeftRight(PeopleAndRoles.NV, AbstractConcepts.GU),
            new PhonoSemantic(PeopleAndRoles.NV, AbstractConcepts.GU));

    /** 驶 (shǐ) — drive. LeftRight: 马(semantic) + 史(phonetic). */
    public static final ComposedZi SHI_DRIVE = new ComposedZi(
            lit("\u9A76"), py(Initial.SH, Head.OPEN, Body.I, Tail.NONE, Tone.THIRD),
            8, 187, "", new LeftRight(Animals.MA, PeopleAndRoles.SHI_HISTORY),
            new PhonoSemantic(Animals.MA, PeopleAndRoles.SHI_HISTORY));

    /** 转 (zhuǎn) — turn. LeftRight: 车(semantic) + 专(phonetic). */
    public static final ComposedZi ZHUAN_TURN = new ComposedZi(
            lit("\u8F6C"), py(Initial.ZH, Head.U, Body.A, Tail.N, Tone.THIRD),
            8, 159, "", new LeftRight(ToolsAndVessels.CHE, ActionsAndStates.ZHUAN),
            new PhonoSemantic(ToolsAndVessels.CHE, ActionsAndStates.ZHUAN));

    /** 顶 (dǐng) — top/peak. LeftRight: 丁(phonetic) + 页(semantic). */
    public static final ComposedZi DING_TOP = new ComposedZi(
            lit("\u9876"), py(Initial.D, Head.OPEN, Body.I, Tail.NG, Tone.THIRD),
            8, 181, "", new LeftRight(ToolsAndVessels.DING_NAIL, SpaceAndDirection.YE_PAGE),
            new PhonoSemantic(SpaceAndDirection.YE_PAGE, ToolsAndVessels.DING_NAIL));

    /** 征 (zhēng) — expedition. LeftRight: 彳(semantic) + 正(phonetic). */
    public static final ComposedZi ZHENG_EXPEDITION = new ComposedZi(
            lit("\u5F81"), py(Initial.ZH, Head.OPEN, Body.E, Tail.NG, Tone.FIRST),
            8, 60, "", new LeftRight(ToolsAndVessels.CHI_STEP, AbstractConcepts.ZHENG),
            new PhonoSemantic(ToolsAndVessels.CHI_STEP, AbstractConcepts.ZHENG));

    /** 肤 (fū) — skin. LeftRight: 月(semantic) + 夫(phonetic). */
    public static final ComposedZi FU_SKIN = new ComposedZi(
            lit("\u80A4"), py(Initial.F, Head.U, Body.U, Tail.NONE, Tone.FIRST),
            8, 130, "", new LeftRight(NatureElements.YUE, PeopleAndRoles.FU_HUSBAND),
            new PhonoSemantic(NatureElements.YUE, PeopleAndRoles.FU_HUSBAND));

    /** 垃 (lā) — garbage. LeftRight: 土(semantic) + 立(phonetic). */
    public static final ComposedZi LA_GARBAGE = new ComposedZi(
            lit("\u5783"), py(Initial.L, Head.OPEN, Body.A, Tail.NONE, Tone.FIRST),
            8, 32, "", new LeftRight(NatureElements.TU, ActionsAndStates.LI_STAND),
            new PhonoSemantic(NatureElements.TU, ActionsAndStates.LI_STAND));

    /** 规 (guī) — rule. LeftRight: 夫(phonetic) + 见(semantic). */
    public static final ComposedZi GUI_RULE = new ComposedZi(
            lit("\u89C4"), py(Initial.G, Head.U, Body.E, Tail.VOWEL_I, Tone.FIRST),
            8, 147, "", new LeftRight(PeopleAndRoles.FU_HUSBAND, ActionsAndStates.JIAN_SEE),
            new PhonoSemantic(ActionsAndStates.JIAN_SEE, PeopleAndRoles.FU_HUSBAND));

    /** 现 (xiàn) — appear. LeftRight: 王(semantic) + 见(phonetic). */
    public static final ComposedZi XIAN_APPEAR = new ComposedZi(
            lit("\u73B0"), py(Initial.X, Head.I, Body.A, Tail.N, Tone.FOURTH),
            8, 96, "", new LeftRight(PeopleAndRoles.WANG, ActionsAndStates.JIAN_SEE),
            new PhonoSemantic(PeopleAndRoles.WANG, ActionsAndStates.JIAN_SEE));

    /** 环 (huán) — ring. LeftRight: 王(semantic) + 不(phonetic). */
    public static final ComposedZi HUAN_RING = new ComposedZi(
            lit("\u73AF"), py(Initial.H, Head.U, Body.A, Tail.N, Tone.SECOND),
            8, 96, "", new LeftRight(PeopleAndRoles.WANG, AbstractConcepts.BU),
            new PhonoSemantic(PeopleAndRoles.WANG, AbstractConcepts.BU));

    /** 拔 (bá) — pull out. LeftRight: 扌(semantic) + 犮(phonetic). */
    public static final ComposedZi BA_PULL = new ComposedZi(
            lit("\u62D4"), py(Initial.B, Head.OPEN, Body.A, Tail.NONE, Tone.SECOND),
            8, 64, "", new LeftRight(TI_SHOU_PANG, Animals.BA_DOG),
            new PhonoSemantic(TI_SHOU_PANG, Animals.BA_DOG));

    /** 净 (jìng) — clean. LeftRight: 冫(semantic) + 争(phonetic). */
    public static final ComposedZi JING_CLEAN = new ComposedZi(
            lit("\u51C0"), py(Initial.J, Head.OPEN, Body.I, Tail.NG, Tone.FOURTH),
            8, 15, "", new LeftRight(LIANG_DIAN_SHUI, ActionsAndStates.ZHENG_CONTEST),
            new PhonoSemantic(LIANG_DIAN_SHUI, ActionsAndStates.ZHENG_CONTEST));

    /** 爬 (pá) — crawl. LeftRight: 爪(semantic) + 巴(phonetic). */
    public static final ComposedZi PA_CRAWL = new ComposedZi(
            lit("\u722C"), py(Initial.P, Head.OPEN, Body.A, Tail.NONE, Tone.SECOND),
            8, 87, "", new LeftRight(BodyParts.ZHUA, Structures.BA_CLING),
            new PhonoSemantic(BodyParts.ZHUA, Structures.BA_CLING));

    /** 的 (dì) — target, possessive. LeftRight: 白(semantic) + 勺(phonetic). */
    public static final ComposedZi DI_TARGET = new ComposedZi(
            lit("\u7684"), py(Initial.D, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH),
            8, 106, "", new LeftRight(Materials.BAI_WHITE, ToolsAndVessels.SHAO),
            new PhonoSemantic(Materials.BAI_WHITE, ToolsAndVessels.SHAO));

    // ── 8 strokes – shelter radical 广 ───────────────────────────────────

    /** 庙 (miào) — temple. SemiEnclosureUpperLeft: 广(semantic) + 由(phonetic). */
    public static final ComposedZi MIAO_TEMPLE = new ComposedZi(
            lit("\u5E99"), py(Initial.M, Head.I, Body.A, Tail.VOWEL_U, Tone.FOURTH),
            8, 53, "", new SemiEnclosureUpperLeft(Structures.GUANG, SpaceAndDirection.YOU_FROM),
            new PhonoSemantic(Structures.GUANG, SpaceAndDirection.YOU_FROM));

    /** 底 (dǐ) — bottom. SemiEnclosureUpperLeft: 广(semantic) + 氐(phonetic). */
    public static final ComposedZi DI_BOTTOM = new ComposedZi(
            lit("\u5E95"), py(Initial.D, Head.OPEN, Body.I, Tail.NONE, Tone.THIRD),
            8, 53, "", new SemiEnclosureUpperLeft(Structures.GUANG, NatureElements.DI_FOUNDATION),
            new PhonoSemantic(Structures.GUANG, NatureElements.DI_FOUNDATION));

    /** 废 (fèi) — waste. SemiEnclosureUpperLeft: 广(semantic) + 发(phonetic). */
    public static final ComposedZi FEI_WASTE = new ComposedZi(
            lit("\u5E9F"), py(Initial.F, Head.OPEN, Body.E, Tail.VOWEL_I, Tone.FOURTH),
            8, 53, "", new SemiEnclosureUpperLeft(Structures.GUANG, ActionsAndStates.FA_SEND),
            new PhonoSemantic(Structures.GUANG, ActionsAndStates.FA_SEND));

    /** 居 (jū) — dwell. SemiEnclosureUpperLeft: 尸(semantic) + 古(phonetic). */
    public static final ComposedZi JU_DWELL = new ComposedZi(
            lit("\u5C45"), py(Initial.J, Head.V, Body.V, Tail.NONE, Tone.FIRST),
            8, 44, "", new SemiEnclosureUpperLeft(Structures.SHI_CORPSE, AbstractConcepts.GU),
            new PhonoSemantic(Structures.SHI_CORPSE, AbstractConcepts.GU));

    // ── 9 strokes ──────────────────────────────────────────────────────

    /** 俗 (sú) — custom/vulgar. LeftRight: 亻(semantic) + 谷(phonetic). */
    public static final ComposedZi SU = new ComposedZi(
            lit("俗"),
            py(Initial.S, Head.U, Body.U, Tail.NONE, Tone.SECOND),
            9, 9, "",
            new LeftRight(DAN_REN_PANG, NatureElements.GU),
            new PhonoSemantic(DAN_REN_PANG, NatureElements.GU)
    );

    // ── 10 strokes ─────────────────────────────────────────────────────

    /** 请 (qǐng) — invite/please. LeftRight: 讠(semantic) + 青(phonetic). */
    public static final ComposedZi QING_INVITE = new ComposedZi(
            lit("请"),
            py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.THIRD),
            10, 149, "",
            new LeftRight(YAN_ZI_PANG, Materials.QING),
            new PhonoSemantic(YAN_ZI_PANG, Materials.QING)
    );

    // ── 11 strokes ─────────────────────────────────────────────────────

    /** 清 (qīng) — clear. LeftRight: 氵(semantic) + 青(phonetic). */
    public static final ComposedZi QING_CLEAR = new ComposedZi(
            lit("清"),
            py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.FIRST),
            11, 85, "",
            new LeftRight(SAN_DIAN_SHUI, Materials.QING),
            new PhonoSemantic(SAN_DIAN_SHUI, Materials.QING)
    );

    /** 情 (qíng) — emotion/feeling. LeftRight: 忄(semantic) + 青(phonetic). */
    public static final ComposedZi QING_EMOTION = new ComposedZi(
            lit("情"),
            py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.SECOND),
            11, 61, "",
            new LeftRight(SHU_XIN_PANG, Materials.QING),
            new PhonoSemantic(SHU_XIN_PANG, Materials.QING)
    );

    // ── 12 strokes ─────────────────────────────────────────────────────

    /** 晴 (qíng) — sunny/clear weather. LeftRight: 日(semantic) + 青(phonetic). */
    public static final ComposedZi QING_SUNNY = new ComposedZi(
            lit("晴"),
            py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.SECOND),
            12, 72, "",
            new LeftRight(NatureElements.RI, Materials.QING),
            new PhonoSemantic(NatureElements.RI, Materials.QING)
    );

    // ── 13 strokes ─────────────────────────────────────────────────────

    /** 遨 (áo) — roam/travel. SemiEnclosureBottomLeft: 辶(semantic) + 敖(phonetic). */
    public static final ComposedZi AO = new ComposedZi(
            lit("遨"),
            py(Initial.ZERO, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.SECOND),
            13, 162, "",
            new SemiEnclosureBottomLeft(ZOU_ZHI_DI, ActionsAndStates.AO_ROAM),
            new PhonoSemantic(ZOU_ZHI_DI, ActionsAndStates.AO_ROAM)
    );

    public static final List<ComposedZi> ALL = List.of(
            // 8 strokes
            MING, LIN, GUO,
            // 8 – hand radical 扌
            MO_WIPE, LONG_GATHER, YA_PRESS, CHOU_DRAW, PAI, BAN_MIX,
            PI_DRAPE, BO_STIR, TAI_LIFT, CHAI,
            // 8 – water radical 氵
            MO_FOAM, ZHU_POUR, FEI_BOIL, YONG_SWIM, YOU_OIL, BO_MOOR,
            HE_RIVER, LEI_TEAR, BO_WAVE, XIE_LEAK, PO_SPLASH,
            ZHI_GOVERN, FA_LAW,
            // 8 – woman radical 女
            JIE_SISTER, SHI_BEGIN, MEI_SISTER, XING_SURNAME,
            // 8 – heart radical 忄
            XING_NATURE, PA_FEAR, LIAN_PITY,
            // 8 – double person 彳
            BI_THAT, WANG_GO,
            // 8 – silk radical 纟
            ZHONG_END, XI_THIN, ZU_GROUP,
            // 8 – moon/flesh 月
            PENG, ZHONG_SWOLLEN, ZHI_LIMB, FEI_FAT,
            // 8 – other LeftRight
            DAO_ARRIVE, FANG_RELEASE, QU_TAKE, GU_LONELY, OU,
            ZHAN_CHOP, RUAN, ZHI_NEPHEW, GONG_SUPPLY, WU_THING,
            MU_HERD, KUANG_ORE, MA_CODE, ZHI_KNOW, BAI_DEFEAT,
            HU_FOX, SUO_PLACE, PO_SLOPE, WEI_TASTE, YONG_CHANT,
            MING_CRY, ZHU_STATION, SHI_LOOK, DIAO_FISH, WANG_FLOURISH,
            SHAN_SHIRT, SHAN_SHAANXI, XIAN_LIMIT, JIAO_SUBURB, LUN_WHEEL,
            // 8 – TopBottom
            QI_STRANGE, FEN_EXERT, JI_SEASON, WEI_ENTRUST, ZONG_CLAN,
            ZHOU_COSMOS, YI_FITTING, BAO_TREASURE, SHEN_EXAMINE, KONG,
            YAN_ROCK, ZHONG_LOYAL, HU_SUDDENLY, MANG_BLIND, FU_AXE,
            YAN_FLAME, KUN, JIAN_SHOULDER, PING_APPLE, MIAO_SEEDLING,
            YING_HERO, RUO, MAO_THATCH, NIAN_THINK, TAN_GREEDY,
            HUO_GOODS,
            // 8 – SemiEnclosure
            PO_PRESS, FANG_ROOM, JIE_SESSION, QU_BEND,
            // 8 – wood (batch 2)
            ZHI_TWIG, BEI_CUP, XI_ANALYZE, QIANG_GUN, GUI_CABINET,
            // 8 – grain
            GAN_STRAW, HE_HARMONY,
            // 8 – grass (batch 2)
            KU_BITTER,
            // 8 – enclosure 囗/门
            GU_SOLID, TU_MAP, ZHA_SLUICE, NAO_NOISY,
            // 8 – TopBottom (batch 2)
            CHANG_PROSPER, TAI_ATTITUDE, HUN_DUSK, BA_DAD, LONG_RIDGE, LIAN_CURTAIN,
            // 8 – speech (batch 2)
            HUA_SPEECH, GAI_SHOULD, XIANG_DETAIL,
            // 8 – person (batch 2)
            SHI_USE, YI_RELY,
            // 8 – fire
            CHAO_STIRFRY, CHUI_COOK, LU_STOVE,
            // 8 – additional LR (batch 2)
            CHEN_LINING, HU_CALL, GUA_SCRAPE, XIN_JOY,
            LA_PULL, LAN_BLOCK, YONG_EMBRACE, GU_AUNT,
            SHI_DRIVE, ZHUAN_TURN, DING_TOP, ZHENG_EXPEDITION,
            FU_SKIN, LA_GARBAGE, GUI_RULE, XIAN_APPEAR, HUAN_RING,
            BA_PULL, JING_CLEAN,
            PA_CRAWL, DI_TARGET,
            // 8 – shelter 广 / corpse 尸
            MIAO_TEMPLE, DI_BOTTOM, FEI_WASTE, JU_DWELL,
            // 9 strokes
            SU,
            // 10 strokes
            QING_INVITE,
            // 11 strokes
            QING_CLEAR, QING_EMOTION,
            // 12 strokes
            QING_SUNNY,
            // 13 strokes
            AO
    );
}
