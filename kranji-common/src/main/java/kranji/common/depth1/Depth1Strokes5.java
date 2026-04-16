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

public final class Depth1Strokes5 {

    private Depth1Strokes5() {}

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    private static ZiChar lit(String s) { return new ZiChar.Literal(s); }
    private static ZiChar uni(String s) { return new ZiChar.Unicode(s); }

    // ── 5 strokes ──────────────────────────────────────────────────────

    /** 刊 (kān) — publish. LeftRight: 干(phonetic) + 刂(semantic). */
    public static final ComposedZi KAN = new ComposedZi(
            uni("\u520A"), List.of(py(Initial.K, Head.OPEN, Body.A, Tail.N, Tone.FIRST)),
            5, 18, "", new LeftRight(SpaceAndDirection.GAN_DRY, LI_DAO_PANG),
            new PhonoSemantic(LI_DAO_PANG, SpaceAndDirection.GAN_DRY));

    /** 打 (dǎ) — hit, strike. LeftRight: 扌(semantic) + 丁(phonetic). */
    public static final ComposedZi DA_HIT = new ComposedZi(
            uni("\u6253"), List.of(py(Initial.D, Head.OPEN, Body.A, Tail.NONE, Tone.THIRD)),
            5, 64, "", new LeftRight(TI_SHOU_PANG, ToolsAndVessels.DING_NAIL),
            new PhonoSemantic(TI_SHOU_PANG, ToolsAndVessels.DING_NAIL));

    /** 扑 (pū) — pounce, pat. LeftRight: 扌(semantic) + 卜(phonetic). */
    public static final ComposedZi PU_POUNCE = new ComposedZi(
            uni("\u6251"), List.of(py(Initial.P, Head.U, Body.U, Tail.NONE, Tone.FIRST)),
            5, 64, "", new LeftRight(TI_SHOU_PANG, ToolsAndVessels.BU_DIVINATION),
            new PhonoSemantic(TI_SHOU_PANG, ToolsAndVessels.BU_DIVINATION));

    /** 扒 (bā) — rake, cling. LeftRight: 扌(semantic) + 八(phonetic). */
    public static final ComposedZi BA_RAKE = new ComposedZi(
            uni("\u6252"), List.of(py(Initial.B, Head.OPEN, Body.A, Tail.NONE, Tone.FIRST)),
            5, 64, "", new LeftRight(TI_SHOU_PANG, NumbersAndMeasure.BA),
            new PhonoSemantic(TI_SHOU_PANG, NumbersAndMeasure.BA));

    /** 功 (gōng) — achievement, merit. LeftRight: 工(phonetic) + 力(semantic). */
    public static final ComposedZi GONG_MERIT = new ComposedZi(
            uni("\u529F"), List.of(py(Initial.G, Head.OPEN, Body.O, Tail.NG, Tone.FIRST)),
            5, 19, "", new LeftRight(ActionsAndStates.GONG_WORK, ToolsAndVessels.LI_PLOW),
            new PhonoSemantic(ToolsAndVessels.LI_PLOW, ActionsAndStates.GONG_WORK));

    /** 扔 (rēng) — throw. LeftRight: 扌(semantic) + 乃(phonetic). */
    public static final ComposedZi RENG_THROW = new ComposedZi(
            uni("\u6254"), List.of(py(Initial.R, Head.OPEN, Body.E, Tail.NG, Tone.FIRST)),
            5, 64, "", new LeftRight(TI_SHOU_PANG, AbstractConcepts.NAI),
            new PhonoSemantic(TI_SHOU_PANG, AbstractConcepts.NAI));

    /** 古 (gǔ) — ancient. TopBottom: 十 + 口. */
    public static final ComposedZi GU_ANCIENT = new ComposedZi(
            uni("\u53E4"), List.of(py(Initial.G, Head.U, Body.U, Tail.NONE, Tone.THIRD)),
            5, 30, "", new TopBottom(NumbersAndMeasure.SHI, BodyParts.KOU),
            new CompoundIndicative("\u5341(ten) + \u53E3(mouth/generations) \u2192 ancient"));

    /** 节 (jié) — festival, joint. TopBottom: 艹(semantic) + 卩(phonetic). */
    public static final ComposedZi JIE_FESTIVAL = new ComposedZi(
            uni("\u8282"), List.of(py(Initial.J, Head.I, Body.E_CARON, Tail.NONE, Tone.SECOND)),
            5, 140, "", new TopBottom(CAO_ZI_TOU, RadicalComponents.JIE_SEAL),
            new PhonoSemantic(CAO_ZI_TOU, RadicalComponents.JIE_SEAL));

    /** 厉 (lì) — strict, fierce. SemiEnclosureUpperLeft: 厂 + 万. */
    public static final ComposedZi LI_STRICT = new ComposedZi(
            uni("\u5389"), List.of(py(Initial.L, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH)),
            5, 27, "", new SemiEnclosureUpperLeft(Structures.CHANG_FACTORY, NumbersAndMeasure.WAN),
            new PhonoSemantic(Structures.CHANG_FACTORY, NumbersAndMeasure.WAN));

    /** 灭 (miè) — extinguish. TopBottom: 一(cover) + 火(fire). */
    public static final ComposedZi MIE_EXTINGUISH = new ComposedZi(
            uni("\u706D"), List.of(py(Initial.M, Head.I, Body.E_CARON, Tail.NONE, Tone.FOURTH)),
            5, 86, "", new TopBottom(NumbersAndMeasure.YI, NatureElements.HUO),
            new CompoundIndicative("\u4E00(cover) + \u706B(fire) \u2192 extinguish"));

    /** 轧 (yà) — crush, roll. LeftRight: 车(semantic) + 乚(phonetic). */
    public static final ComposedZi YA_CRUSH = new ComposedZi(
            uni("\u8F67"), List.of(py(Initial.ZERO, Head.I, Body.A, Tail.NONE, Tone.FOURTH)),
            5, 159, "", new LeftRight(ToolsAndVessels.CHE, RadicalComponents.YI_HOOK),
            new PhonoSemantic(ToolsAndVessels.CHE, RadicalComponents.YI_HOOK));

    /** 占 (zhàn) — occupy, divine. TopBottom: 卜 + 口. */
    public static final ComposedZi ZHAN_OCCUPY = new ComposedZi(
            uni("\u5360"), List.of(py(Initial.ZH, Head.OPEN, Body.A, Tail.N, Tone.FOURTH)),
            5, 25, "", new TopBottom(ToolsAndVessels.BU_DIVINATION, BodyParts.KOU),
            new CompoundIndicative("\u535C(divine) + \u53E3(mouth) \u2192 occupy, divine"));

    /** 旧 (jiù) — old, former. LeftRight: 丨 + 日. */
    public static final ComposedZi JIU_OLD = new ComposedZi(
            uni("\u65E7"), List.of(py(Initial.J, Head.I, Body.O, Tail.VOWEL_U, Tone.FOURTH)),
            5, 72, "", new LeftRight(RadicalComponents.GUN, NatureElements.RI),
            new CompoundIndicative("\u4E28(vertical) + \u65E5(sun/day) \u2192 old, former"));

    /** 旦 (dàn) — dawn. TopBottom: 日 + 一. */
    public static final ComposedZi DAN_DAWN = new ComposedZi(
            uni("\u65E6"), List.of(py(Initial.D, Head.OPEN, Body.A, Tail.N, Tone.FOURTH)),
            5, 72, "", new TopBottom(NatureElements.RI, NumbersAndMeasure.YI),
            new CompoundIndicative("\u65E5(sun) + \u4E00(horizon) \u2192 dawn"));

    /** 叶 (yè) — leaf. LeftRight: 口(semantic) + 十(phonetic). */
    public static final ComposedZi YE_LEAF = new ComposedZi(
            uni("\u53F6"), List.of(py(Initial.ZERO, Head.I, Body.E_CARON, Tail.NONE, Tone.FOURTH)),
            5, 30, "", new LeftRight(BodyParts.KOU, NumbersAndMeasure.SHI),
            new PhonoSemantic(BodyParts.KOU, NumbersAndMeasure.SHI));

    /** 叮 (dīng) — sting, bite. LeftRight: 口(semantic) + 丁(phonetic). */
    public static final ComposedZi DING_STING = new ComposedZi(
            uni("\u53EE"), List.of(py(Initial.D, Head.OPEN, Body.I, Tail.NG, Tone.FIRST)),
            5, 30, "", new LeftRight(BodyParts.KOU, ToolsAndVessels.DING_NAIL),
            new PhonoSemantic(BodyParts.KOU, ToolsAndVessels.DING_NAIL));

    /** 只 (zhǐ) — only. TopBottom: 口 + 八. */
    public static final ComposedZi ZHI_ONLY = new ComposedZi(
            uni("\u53EA"), List.of(py(Initial.ZH, Head.OPEN, Body.NULL, Tail.NONE, Tone.THIRD)),
            5, 30, "", new TopBottom(BodyParts.KOU, NumbersAndMeasure.BA),
            new CompoundIndicative("\u53E3(mouth) + \u516B(split) \u2192 only"));

    /** 叼 (diāo) — hold in mouth. LeftRight: 口(semantic) + 刁(phonetic). */
    public static final ComposedZi DIAO_HOLD = new ComposedZi(
            uni("\u53FC"), List.of(py(Initial.D, Head.I, Body.A, Tail.VOWEL_U, Tone.FIRST)),
            5, 30, "", new LeftRight(BodyParts.KOU, ToolsAndVessels.DIAO),
            new PhonoSemantic(BodyParts.KOU, ToolsAndVessels.DIAO));

    /** 另 (lìng) — other, separate. TopBottom: 口 + 力. */
    public static final ComposedZi LING_OTHER = new ComposedZi(
            uni("\u53E6"), List.of(py(Initial.L, Head.OPEN, Body.I, Tail.NG, Tone.FOURTH)),
            5, 30, "", new TopBottom(BodyParts.KOU, ToolsAndVessels.LI_PLOW),
            new CompoundIndicative("\u53E3(mouth) + \u529B(power) \u2192 separate, other"));

    /** 叨 (dāo) — chatter. LeftRight: 口(semantic) + 刀(phonetic). */
    public static final ComposedZi DAO_CHATTER = new ComposedZi(
            uni("\u53E8"), List.of(py(Initial.D, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.FIRST)),
            5, 30, "", new LeftRight(BodyParts.KOU, ToolsAndVessels.DAO),
            new PhonoSemantic(BodyParts.KOU, ToolsAndVessels.DAO));

    /** 叹 (tàn) — sigh. LeftRight: 口(semantic) + 又(phonetic). */
    public static final ComposedZi TAN_SIGH = new ComposedZi(
            uni("\u53F9"), List.of(py(Initial.T, Head.OPEN, Body.A, Tail.N, Tone.FOURTH)),
            5, 30, "", new LeftRight(BodyParts.KOU, ActionsAndStates.YOU_AGAIN),
            new PhonoSemantic(BodyParts.KOU, ActionsAndStates.YOU_AGAIN));

    /** 付 (fù) — pay. LeftRight: 亻(semantic) + 寸(phonetic). */
    public static final ComposedZi FU_PAY = new ComposedZi(
            uni("\u4ED8"), List.of(py(Initial.F, Head.U, Body.U, Tail.NONE, Tone.FOURTH)),
            5, 9, "", new LeftRight(DAN_REN_PANG, Structures.CUN_INCH),
            new PhonoSemantic(DAN_REN_PANG, Structures.CUN_INCH));

    /** 仗 (zhàng) — battle, rely on. LeftRight: 亻(semantic) + 丈(phonetic). */
    public static final ComposedZi ZHANG_BATTLE = new ComposedZi(
            uni("\u4ED7"), List.of(py(Initial.ZH, Head.OPEN, Body.A, Tail.NG, Tone.FOURTH)),
            5, 9, "", new LeftRight(DAN_REN_PANG, NumbersAndMeasure.ZHANG),
            new PhonoSemantic(DAN_REN_PANG, NumbersAndMeasure.ZHANG));

    /** 代 (dài) — generation, era. LeftRight: 亻(semantic) + 弋(phonetic). */
    public static final ComposedZi DAI_GENERATION = new ComposedZi(
            uni("\u4EE3"), List.of(py(Initial.D, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.FOURTH)),
            5, 9, "", new LeftRight(DAN_REN_PANG, ToolsAndVessels.YI_SHOOT),
            new PhonoSemantic(DAN_REN_PANG, ToolsAndVessels.YI_SHOOT));

    /** 仙 (xiān) — immortal. LeftRight: 亻(person) + 山(mountain). */
    public static final ComposedZi XIAN_IMMORTAL = new ComposedZi(
            uni("\u4ED9"), List.of(py(Initial.X, Head.I, Body.A, Tail.N, Tone.FIRST)),
            5, 9, "", new LeftRight(DAN_REN_PANG, NatureElements.SHAN),
            new CompoundIndicative("\u4EBB(person) + \u5C71(mountain) \u2192 immortal"));

    /** 们 (men) — plural suffix. LeftRight: 亻(semantic) + 门(phonetic). */
    public static final ComposedZi MEN_PLURAL = new ComposedZi(
            uni("\u4EEC"), List.of(py(Initial.M, Head.OPEN, Body.E, Tail.N, Tone.NEUTRAL)),
            5, 9, "", new LeftRight(DAN_REN_PANG, ToolsAndVessels.MEN),
            new PhonoSemantic(DAN_REN_PANG, ToolsAndVessels.MEN));

    /** 仪 (yí) — ceremony, appearance. LeftRight: 亻(semantic) + 义(phonetic). */
    public static final ComposedZi YI_CEREMONY = new ComposedZi(
            uni("\u4EEA"), List.of(py(Initial.ZERO, Head.OPEN, Body.I, Tail.NONE, Tone.SECOND)),
            5, 9, "", new LeftRight(DAN_REN_PANG, AbstractConcepts.YI_MEANING),
            new PhonoSemantic(DAN_REN_PANG, AbstractConcepts.YI_MEANING));

    /** 仔 (zǎi) — careful, young. LeftRight: 亻(semantic) + 子(phonetic). */
    public static final ComposedZi ZAI_CAREFUL = new ComposedZi(
            uni("\u4ED4"), List.of(py(Initial.Z, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.THIRD)),
            5, 9, "", new LeftRight(DAN_REN_PANG, PeopleAndRoles.ZI),
            new PhonoSemantic(DAN_REN_PANG, PeopleAndRoles.ZI));

    /** 他 (tā) — he, other. LeftRight: 亻(semantic) + 也(phonetic). */
    public static final ComposedZi TA_HE = new ComposedZi(
            uni("\u4ED6"), List.of(py(Initial.T, Head.OPEN, Body.A, Tail.NONE, Tone.FIRST)),
            5, 9, "", new LeftRight(DAN_REN_PANG, AbstractConcepts.YE_ALSO),
            new PhonoSemantic(DAN_REN_PANG, AbstractConcepts.YE_ALSO));

    /** 外 (wài) — outside. LeftRight: 夕(semantic) + 卜(phonetic). */
    public static final ComposedZi WAI_OUTSIDE = new ComposedZi(
            uni("\u5916"), List.of(py(Initial.ZERO, Head.U, Body.A, Tail.VOWEL_I, Tone.FOURTH)),
            5, 36, "", new LeftRight(NatureElements.XI_EVENING, ToolsAndVessels.BU_DIVINATION),
            new PhonoSemantic(NatureElements.XI_EVENING, ToolsAndVessels.BU_DIVINATION));

    /** 句 (jù) — sentence. SemiEnclosureUpperRight: 勹 wraps 口. */
    public static final ComposedZi JU_SENTENCE = new ComposedZi(
            uni("\u53E5"), List.of(py(Initial.J, Head.V, Body.V, Tail.NONE, Tone.FOURTH)),
            5, 20, "", new SemiEnclosureUpperRight(BAO_ZI_TOU, BodyParts.KOU),
            new CompoundIndicative("\u52F9(wrap) + \u53E3(mouth) \u2192 sentence"));

    /** 包 (bāo) — wrap, package. SemiEnclosureUpperRight: 勹 wraps 巳. */
    public static final ComposedZi BAO_WRAP = new ComposedZi(
            uni("\u5305"), List.of(py(Initial.B, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.FIRST)),
            5, 20, "", new SemiEnclosureUpperRight(BAO_ZI_TOU, AbstractConcepts.SI_SIXTH),
            new CompoundIndicative("\u52F9(wrap) + \u5DF3(contents) \u2192 package, wrap"));

    /** 饥 (jī) — hunger. LeftRight: 饣(semantic) + 几(phonetic). */
    public static final ComposedZi JI_HUNGER = new ComposedZi(
            uni("\u9965"), List.of(py(Initial.J, Head.OPEN, Body.I, Tail.NONE, Tone.FIRST)),
            5, 184, "", new LeftRight(SHI_ZI_PANG, ToolsAndVessels.JI_TABLE),
            new PhonoSemantic(SHI_ZI_PANG, ToolsAndVessels.JI_TABLE));

    /** 闪 (shǎn) — flash, dodge. SemiEnclosureTopThree: 门 wraps 人. */
    public static final ComposedZi SHAN_FLASH = new ComposedZi(
            uni("\u95EA"), List.of(py(Initial.SH, Head.OPEN, Body.A, Tail.N, Tone.THIRD)),
            5, 169, "", new SemiEnclosureTopThree(ToolsAndVessels.MEN, PeopleAndRoles.REN),
            new CompoundIndicative("\u95E8(door) + \u4EBA(person) \u2192 flash, dodge"));

    /** 汁 (zhī) — juice. LeftRight: 氵(semantic) + 十(phonetic). */
    public static final ComposedZi ZHI_JUICE = new ComposedZi(
            uni("\u6C41"), List.of(py(Initial.ZH, Head.OPEN, Body.NULL, Tail.NONE, Tone.FIRST)),
            5, 85, "", new LeftRight(SAN_DIAN_SHUI, NumbersAndMeasure.SHI),
            new PhonoSemantic(SAN_DIAN_SHUI, NumbersAndMeasure.SHI));

    /** 汉 (hàn) — Chinese, Han. LeftRight: 氵(semantic) + 又(phonetic). */
    public static final ComposedZi HAN = new ComposedZi(
            uni("\u6C49"), List.of(py(Initial.H, Head.OPEN, Body.A, Tail.N, Tone.FOURTH)),
            5, 85, "", new LeftRight(SAN_DIAN_SHUI, ActionsAndStates.YOU_AGAIN),
            new PhonoSemantic(SAN_DIAN_SHUI, ActionsAndStates.YOU_AGAIN));

    /** 宁 (níng) — peaceful. TopBottom: 宀(semantic) + 丁(phonetic). */
    public static final ComposedZi NING = new ComposedZi(
            uni("\u5B81"), List.of(py(Initial.N, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            5, 40, "", new TopBottom(BAO_GAI_TOU, ToolsAndVessels.DING_NAIL),
            new PhonoSemantic(BAO_GAI_TOU, ToolsAndVessels.DING_NAIL));

    /** 它 (tā) — it. TopBottom: 宀 + 匕. */
    public static final ComposedZi TA_IT = new ComposedZi(
            uni("\u5B83"), List.of(py(Initial.T, Head.OPEN, Body.A, Tail.NONE, Tone.FIRST)),
            5, 40, "", new TopBottom(BAO_GAI_TOU, ToolsAndVessels.BI_SPOON),
            new CompoundIndicative("\u5B80(roof) + \u5315(spoon) \u2192 it"));

    /** 讨 (tǎo) — discuss, demand. LeftRight: 讠(semantic) + 寸(phonetic). */
    public static final ComposedZi TAO_DISCUSS = new ComposedZi(
            uni("\u8BA8"), List.of(py(Initial.T, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.THIRD)),
            5, 149, "", new LeftRight(YAN_ZI_PANG, Structures.CUN_INCH),
            new PhonoSemantic(YAN_ZI_PANG, Structures.CUN_INCH));

    /** 写 (xiě) — write. TopBottom: 冖 + 与. */
    public static final ComposedZi XIE_WRITE = new ComposedZi(
            uni("\u5199"), List.of(py(Initial.X, Head.I, Body.E_CARON, Tail.NONE, Tone.THIRD)),
            5, 14, "", new TopBottom(TU_BAO_GAI, ActionsAndStates.YU_GIVE),
            new CompoundIndicative("\u5196(cover) + \u4E0E(give) \u2192 write"));

    /** 让 (ràng) — let, yield. LeftRight: 讠(semantic) + 上(phonetic). */
    public static final ComposedZi RANG_LET = new ComposedZi(
            uni("\u8BA9"), List.of(py(Initial.R, Head.OPEN, Body.A, Tail.NG, Tone.FOURTH)),
            5, 149, "", new LeftRight(YAN_ZI_PANG, SpaceAndDirection.SHANG),
            new PhonoSemantic(YAN_ZI_PANG, SpaceAndDirection.SHANG));

    /** 礼 (lǐ) — ceremony, gift. LeftRight: 礻(semantic) + 乚(phonetic). */
    public static final ComposedZi LI_CEREMONY = new ComposedZi(
            uni("\u793C"), List.of(py(Initial.L, Head.OPEN, Body.I, Tail.NONE, Tone.THIRD)),
            5, 113, "", new LeftRight(SHI_ZI_PANG_SPIRIT, RadicalComponents.YI_HOOK),
            new PhonoSemantic(SHI_ZI_PANG_SPIRIT, RadicalComponents.YI_HOOK));

    /** 训 (xùn) — instruct. LeftRight: 讠(semantic) + 川(phonetic). */
    public static final ComposedZi XUN_INSTRUCT = new ComposedZi(
            uni("\u8BAD"), List.of(py(Initial.X, Head.V, Body.E, Tail.N, Tone.FOURTH)),
            5, 149, "", new LeftRight(YAN_ZI_PANG, NatureElements.CHUAN),
            new PhonoSemantic(YAN_ZI_PANG, NatureElements.CHUAN));

    /** 议 (yì) — discuss, opinion. LeftRight: 讠(semantic) + 义(phonetic). */
    public static final ComposedZi YI_DISCUSS = new ComposedZi(
            uni("\u8BAE"), List.of(py(Initial.ZERO, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH)),
            5, 149, "", new LeftRight(YAN_ZI_PANG, AbstractConcepts.YI_MEANING),
            new PhonoSemantic(YAN_ZI_PANG, AbstractConcepts.YI_MEANING));

    /** 记 (jì) — record, remember. LeftRight: 讠(semantic) + 己(phonetic). */
    public static final ComposedZi JI_RECORD = new ComposedZi(
            uni("\u8BB0"), List.of(py(Initial.J, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH)),
            5, 149, "", new LeftRight(YAN_ZI_PANG, AbstractConcepts.JI_SELF),
            new PhonoSemantic(YAN_ZI_PANG, AbstractConcepts.JI_SELF));

    /** 尼 (ní) — nun, near. SemiEnclosureUpperLeft: 尸 + 匕. */
    public static final ComposedZi NI_NUN = new ComposedZi(
            uni("\u5C3C"), List.of(py(Initial.N, Head.OPEN, Body.I, Tail.NONE, Tone.SECOND)),
            5, 44, "", new SemiEnclosureUpperLeft(Structures.SHI_CORPSE, ToolsAndVessels.BI_SPOON),
            new PhonoSemantic(Structures.SHI_CORPSE, ToolsAndVessels.BI_SPOON));

    /** 辽 (liáo) — distant. SemiEnclosureBottomLeft: 辶 + 了. */
    public static final ComposedZi LIAO = new ComposedZi(
            uni("\u8FBD"), List.of(py(Initial.L, Head.I, Body.A, Tail.VOWEL_U, Tone.SECOND)),
            5, 162, "", new SemiEnclosureBottomLeft(ZOU_ZHI_DI, ActionsAndStates.LE),
            new PhonoSemantic(ZOU_ZHI_DI, ActionsAndStates.LE));

    /** 奶 (nǎi) — milk, grandmother. LeftRight: 女(semantic) + 乃(phonetic). */
    public static final ComposedZi NAI_MILK = new ComposedZi(
            uni("\u5976"), List.of(py(Initial.N, Head.OPEN, Body.A, Tail.VOWEL_I, Tone.THIRD)),
            5, 38, "", new LeftRight(PeopleAndRoles.NV, AbstractConcepts.NAI),
            new PhonoSemantic(PeopleAndRoles.NV, AbstractConcepts.NAI));

    /** 奴 (nú) — slave. LeftRight: 女(semantic) + 又(phonetic). */
    public static final ComposedZi NU_SLAVE = new ComposedZi(
            uni("\u5974"), List.of(py(Initial.N, Head.U, Body.U, Tail.NONE, Tone.SECOND)),
            5, 38, "", new LeftRight(PeopleAndRoles.NV, ActionsAndStates.YOU_AGAIN),
            new PhonoSemantic(PeopleAndRoles.NV, ActionsAndStates.YOU_AGAIN));

    /** 加 (jiā) — add, increase. LeftRight: 力 + 口. */
    public static final ComposedZi JIA_ADD = new ComposedZi(
            uni("\u52A0"), List.of(py(Initial.J, Head.I, Body.A, Tail.NONE, Tone.FIRST)),
            5, 19, "", new LeftRight(ToolsAndVessels.LI_PLOW, BodyParts.KOU),
            new CompoundIndicative("\u529B(strength) + \u53E3(mouth) \u2192 add, increase"));

    /** 召 (zhào) — summon. TopBottom: 刀 + 口. */
    public static final ComposedZi ZHAO_SUMMON = new ComposedZi(
            uni("\u53EC"), List.of(py(Initial.ZH, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.FOURTH)),
            5, 30, "", new TopBottom(ToolsAndVessels.DAO, BodyParts.KOU),
            new PhonoSemantic(BodyParts.KOU, ToolsAndVessels.DAO));

    /** 边 (biān) — side, edge. SemiEnclosureBottomLeft: 辶 + 力. */
    public static final ComposedZi BIAN_SIDE = new ComposedZi(
            uni("\u8FB9"), List.of(py(Initial.B, Head.I, Body.A, Tail.N, Tone.FIRST)),
            5, 162, "", new SemiEnclosureBottomLeft(ZOU_ZHI_DI, ToolsAndVessels.LI_PLOW),
            new PhonoSemantic(ZOU_ZHI_DI, ToolsAndVessels.LI_PLOW));

    /** 圣 (shèng) — saint, holy. TopBottom: 又 + 土. */
    public static final ComposedZi SHENG_SAINT = new ComposedZi(
            uni("\u5723"), List.of(py(Initial.SH, Head.OPEN, Body.E, Tail.NG, Tone.FOURTH)),
            5, 32, "", new TopBottom(ActionsAndStates.YOU_AGAIN, NatureElements.TU),
            new CompoundIndicative("\u53C8(hand) + \u571F(earth) \u2192 sage, saint"));

    /** 对 (duì) — correct, pair. LeftRight: 又 + 寸. */
    public static final ComposedZi DUI_CORRECT = new ComposedZi(
            uni("\u5BF9"), List.of(py(Initial.D, Head.U, Body.E, Tail.VOWEL_I, Tone.FOURTH)),
            5, 41, "", new LeftRight(ActionsAndStates.YOU_AGAIN, Structures.CUN_INCH),
            new CompoundIndicative("\u53C8(hand) + \u5BF8(inch/measure) \u2192 correct, pair"));

    /** 幼 (yòu) — young, immature. LeftRight: 幺 + 力. */
    public static final ComposedZi YOU_YOUNG = new ComposedZi(
            uni("\u5E7C"), List.of(py(Initial.ZERO, Head.I, Body.O, Tail.VOWEL_U, Tone.FOURTH)),
            5, 52, "", new LeftRight(NumbersAndMeasure.YAO_TINY, ToolsAndVessels.LI_PLOW),
            new CompoundIndicative("\u5E7A(tiny) + \u529B(strength) \u2192 young, immature"));

    /** 厉 (lì) — severe, strict. SemiEnclosureUpperLeft: 厂 + 万. */
    public static final ComposedZi LI_SEVERE = new ComposedZi(
            uni("\u5389"), List.of(py(Initial.L, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH)),
            5, 27, "", new SemiEnclosureUpperLeft(Structures.CHANG_FACTORY, NumbersAndMeasure.WAN),
            new PhonoSemantic(Structures.CHANG_FACTORY, NumbersAndMeasure.WAN));

    public static final List<ComposedZi> ALL = List.of(
            KAN, DA_HIT, PU_POUNCE, BA_RAKE, GONG_MERIT, RENG_THROW,
            GU_ANCIENT, JIE_FESTIVAL, LI_STRICT, MIE_EXTINGUISH, YA_CRUSH,
            ZHAN_OCCUPY, JIU_OLD, DAN_DAWN, YE_LEAF, DING_STING,
            ZHI_ONLY, DIAO_HOLD, LING_OTHER, DAO_CHATTER, TAN_SIGH,
            FU_PAY, ZHANG_BATTLE, DAI_GENERATION, XIAN_IMMORTAL, MEN_PLURAL,
            YI_CEREMONY, ZAI_CAREFUL, TA_HE, WAI_OUTSIDE,
            JU_SENTENCE, BAO_WRAP, JI_HUNGER, SHAN_FLASH,
            ZHI_JUICE, HAN, NING, TA_IT,
            TAO_DISCUSS, XIE_WRITE, RANG_LET, LI_CEREMONY, XUN_INSTRUCT,
            YI_DISCUSS, JI_RECORD, NI_NUN, LIAO, NAI_MILK, NU_SLAVE,
            JIA_ADD, ZHAO_SUMMON, BIAN_SIDE, SHENG_SAINT, DUI_CORRECT, YOU_YOUNG,
            LI_SEVERE
    );
}
