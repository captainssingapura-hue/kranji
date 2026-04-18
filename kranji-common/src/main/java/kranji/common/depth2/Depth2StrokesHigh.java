package kranji.common.depth2;

import kranji.classification.EtymologicalCategory.*;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.animals.Animals;
import kranji.singular.body.BodyParts;
import kranji.singular.concepts.AbstractConcepts;
import kranji.singular.nature.NatureElements;
import kranji.singular.radicals.RadicalComponents;
import kranji.singular.people.PeopleAndRoles;
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.structures.Structures;
import kranji.singular.tools.ToolsAndVessels;
import kranji.common.CommonBlocks;
import kranji.common.depth1.Depth1Strokes4;
import kranji.common.depth1.Depth1Strokes5;
import kranji.common.depth1.Depth1Strokes6;
import kranji.common.depth1.Depth1Strokes7;
import kranji.common.depth1.Depth1StrokesHigh;
import kranji.component.basic.WoodFamily;
import kranji.singular.materials.Materials;
import kranji.pinyin.*;
import kranji.zi.*;
import kranji.zi.ComposedBlock.*;

import java.util.List;

import static kranji.component.basic.BasicComponents.*;

/**
 * Depth-2 composed characters with 9 or more strokes.
 *
 * <p>Will be split into per-stroke files as those stroke-count batches
 * are processed.</p>
 */
public final class Depth2StrokesHigh {

    private Depth2StrokesHigh() {}

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    private static ZiChar lit(String s) { return new ZiChar.Literal(s); }
    private static ZiChar uni(String s) { return new ZiChar.Unicode(s); }

    // ── 8 strokes (depth-2) ──────────────────────────────────────────────

    // – using 反 FAN_OPPOSE (depth-1, Depth1Strokes4) ──────────────────

    /** 板 (bǎn) — board. LeftRight: 木(semantic) + 反(phonetic, d1). */
    public static final ComposedZi BAN_BOARD = new ComposedZi(
            lit("\u677F"), py(Initial.B, Head.OPEN, Body.A, Tail.N, Tone.THIRD),
            8, 75, "", new LeftRight(WoodFamily.MU, Depth1Strokes4.FAN_OPPOSE.structure()),
            new PhonoSemantic(WoodFamily.MU, Depth1Strokes4.FAN_OPPOSE.structure()));

    /** 贩 (fàn) — peddle. LeftRight: 贝(semantic) + 反(phonetic, d1). */
    public static final ComposedZi FAN_PEDDLE = new ComposedZi(
            lit("\u8D29"), py(Initial.F, Head.OPEN, Body.A, Tail.N, Tone.FOURTH),
            8, 154, "", new LeftRight(Animals.BEI, Depth1Strokes4.FAN_OPPOSE.structure()),
            new PhonoSemantic(Animals.BEI, Depth1Strokes4.FAN_OPPOSE.structure()));

    /** 版 (bǎn) — edition. LeftRight: 片(semantic) + 反(phonetic, d1). */
    public static final ComposedZi BAN_EDITION = new ComposedZi(
            lit("\u7248"), py(Initial.B, Head.OPEN, Body.A, Tail.N, Tone.THIRD),
            8, 91, "", new LeftRight(ToolsAndVessels.PIAN, Depth1Strokes4.FAN_OPPOSE.structure()),
            new PhonoSemantic(ToolsAndVessels.PIAN, Depth1Strokes4.FAN_OPPOSE.structure()));

    // – using 元 YUAN (depth-1, Depth1Strokes4) ────────────────────────

    /** 玩 (wán) — play. LeftRight: 王(semantic) + 元(phonetic, d1). */
    public static final ComposedZi WAN_PLAY = new ComposedZi(
            lit("\u73A9"), py(Initial.ZERO, Head.U, Body.A, Tail.N, Tone.SECOND),
            8, 96, "", new LeftRight(PeopleAndRoles.WANG, Depth1Strokes4.YUAN.structure()),
            new PhonoSemantic(PeopleAndRoles.WANG, Depth1Strokes4.YUAN.structure()));

    // – using 公 GONG_PUBLIC (depth-1, Depth1Strokes4) ─────────────────

    /** 松 (sōng) — pine. LeftRight: 木(semantic) + 公(phonetic, d1). */
    public static final ComposedZi SONG_PINE = new ComposedZi(
            lit("\u677E"), py(Initial.S, Head.OPEN, Body.O, Tail.NG, Tone.FIRST),
            8, 75, "", new LeftRight(WoodFamily.MU, Depth1Strokes4.GONG_PUBLIC.structure()),
            new PhonoSemantic(WoodFamily.MU, Depth1Strokes4.GONG_PUBLIC.structure()));

    // – using 包 BAO_WRAP (depth-1, Depth1Strokes5) ────────────────────

    /** 抱 (bào) — hug. LeftRight: 扌(semantic) + 包(phonetic, d1). */
    public static final ComposedZi BAO_HUG = new ComposedZi(
            lit("\u62B1"), py(Initial.B, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.FOURTH),
            8, 64, "", new LeftRight(TI_SHOU_PANG, Depth1Strokes5.BAO_WRAP.structure()),
            new PhonoSemantic(TI_SHOU_PANG, Depth1Strokes5.BAO_WRAP.structure()));

    /** 泡 (pào) — bubble/soak. LeftRight: 氵(semantic) + 包(phonetic, d1). */
    public static final ComposedZi PAO_BUBBLE = new ComposedZi(
            lit("\u6CE1"), py(Initial.P, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.FOURTH),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, Depth1Strokes5.BAO_WRAP.structure()),
            new PhonoSemantic(SAN_DIAN_SHUI, Depth1Strokes5.BAO_WRAP.structure()));

    /** 饱 (bǎo) — full/satisfied. LeftRight: 饣(semantic) + 包(phonetic, d1). */
    public static final ComposedZi BAO_FULL = new ComposedZi(
            lit("\u9971"), py(Initial.B, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.THIRD),
            8, 184, "", new LeftRight(SHI_ZI_PANG, Depth1Strokes5.BAO_WRAP.structure()),
            new PhonoSemantic(SHI_ZI_PANG, Depth1Strokes5.BAO_WRAP.structure()));

    // – using 旦 DAN_DAWN (depth-1, Depth1Strokes5) ────────────────────

    /** 担 (dān) — carry. LeftRight: 扌(semantic) + 旦(phonetic, d1). */
    public static final ComposedZi DAN_CARRY = new ComposedZi(
            lit("\u62C5"), py(Initial.D, Head.OPEN, Body.A, Tail.N, Tone.FIRST),
            8, 64, "", new LeftRight(TI_SHOU_PANG, Depth1Strokes5.DAN_DAWN.structure()),
            new PhonoSemantic(TI_SHOU_PANG, Depth1Strokes5.DAN_DAWN.structure()));

    /** 坦 (tǎn) — flat. LeftRight: 土(semantic) + 旦(phonetic, d1). */
    public static final ComposedZi TAN_FLAT = new ComposedZi(
            lit("\u5766"), py(Initial.T, Head.OPEN, Body.A, Tail.N, Tone.THIRD),
            8, 32, "", new LeftRight(NatureElements.TU, Depth1Strokes5.DAN_DAWN.structure()),
            new PhonoSemantic(NatureElements.TU, Depth1Strokes5.DAN_DAWN.structure()));

    // – using 召 ZHAO_SUMMON (depth-1, Depth1Strokes5) ─────────────────

    /** 招 (zhāo) — beckon. LeftRight: 扌(semantic) + 召(phonetic, d1). */
    public static final ComposedZi ZHAO_BECKON = new ComposedZi(
            lit("\u62DB"), py(Initial.ZH, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.FIRST),
            8, 64, "", new LeftRight(TI_SHOU_PANG, Depth1Strokes5.ZHAO_SUMMON.structure()),
            new PhonoSemantic(TI_SHOU_PANG, Depth1Strokes5.ZHAO_SUMMON.structure()));

    // – using 句 JU_SENTENCE (depth-1, Depth1Strokes5) ─────────────────

    /** 拘 (jū) — arrest. LeftRight: 扌(semantic) + 句(phonetic, d1). */
    public static final ComposedZi JU_ARREST = new ComposedZi(
            lit("\u62D8"), py(Initial.J, Head.V, Body.V, Tail.NONE, Tone.FIRST),
            8, 64, "", new LeftRight(TI_SHOU_PANG, Depth1Strokes5.JU_SENTENCE.structure()),
            new PhonoSemantic(TI_SHOU_PANG, Depth1Strokes5.JU_SENTENCE.structure()));

    /** 狗 (gǒu) — dog. LeftRight: 犭(semantic) + 句(phonetic, d1). */
    public static final ComposedZi GOU_DOG = new ComposedZi(
            lit("\u72D7"), py(Initial.G, Head.OPEN, Body.O, Tail.VOWEL_U, Tone.THIRD),
            8, 94, "", new LeftRight(FAN_QUAN_PANG, Depth1Strokes5.JU_SENTENCE.structure()),
            new PhonoSemantic(FAN_QUAN_PANG, Depth1Strokes5.JU_SENTENCE.structure()));

    // – using 占 ZHAN_OCCUPY (depth-1, Depth1Strokes5) ─────────────────

    /** 店 (diàn) — shop. SemiEnclosureUpperLeft: 广(semantic) + 占(phonetic, d1). */
    public static final ComposedZi DIAN_SHOP = new ComposedZi(
            lit("\u5E97"), py(Initial.D, Head.I, Body.A, Tail.N, Tone.FOURTH),
            8, 53, "", new SemiEnclosureUpperLeft(Structures.GUANG, Depth1Strokes5.ZHAN_OCCUPY.structure()),
            new PhonoSemantic(Structures.GUANG, Depth1Strokes5.ZHAN_OCCUPY.structure()));

    /** 沾 (zhān) — moisten. LeftRight: 氵(semantic) + 占(phonetic, d1). */
    public static final ComposedZi ZHAN_MOISTEN = new ComposedZi(
            lit("\u6CBE"), py(Initial.ZH, Head.OPEN, Body.A, Tail.N, Tone.FIRST),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, Depth1Strokes5.ZHAN_OCCUPY.structure()),
            new PhonoSemantic(SAN_DIAN_SHUI, Depth1Strokes5.ZHAN_OCCUPY.structure()));

    /** 帖 (tiē) — invitation card. LeftRight: 巾(semantic) + 占(phonetic, d1). */
    public static final ComposedZi TIE_CARD = new ComposedZi(
            lit("\u5E16"), py(Initial.T, Head.I, Body.E, Tail.NONE, Tone.FIRST),
            8, 50, "", new LeftRight(Materials.JIN_TOWEL, Depth1Strokes5.ZHAN_OCCUPY.structure()),
            new PhonoSemantic(Materials.JIN_TOWEL, Depth1Strokes5.ZHAN_OCCUPY.structure()));

    // – using 付 FU_PAY (depth-1, Depth1Strokes5) ──────────────────────

    /** 咐 (fù) — instruct. LeftRight: 口(semantic) + 付(phonetic, d1). */
    public static final ComposedZi FU_INSTRUCT = new ComposedZi(
            lit("\u5490"), py(Initial.F, Head.U, Body.U, Tail.NONE, Tone.FOURTH),
            8, 30, "", new LeftRight(BodyParts.KOU, Depth1Strokes5.FU_PAY.structure()),
            new PhonoSemantic(BodyParts.KOU, Depth1Strokes5.FU_PAY.structure()));

    /** 府 (fǔ) — mansion. SemiEnclosureUpperLeft: 广(semantic) + 付(phonetic, d1). */
    public static final ComposedZi FU_MANSION = new ComposedZi(
            lit("\u5E9C"), py(Initial.F, Head.U, Body.U, Tail.NONE, Tone.THIRD),
            8, 53, "", new SemiEnclosureUpperLeft(Structures.GUANG, Depth1Strokes5.FU_PAY.structure()),
            new PhonoSemantic(Structures.GUANG, Depth1Strokes5.FU_PAY.structure()));

    // – using 它 TA_IT (depth-1, Depth1Strokes5) ───────────────────────

    /** 驼 (tuó) — camel. LeftRight: 马(semantic) + 它(phonetic, d1). */
    public static final ComposedZi TUO_CAMEL = new ComposedZi(
            lit("\u9A7C"), py(Initial.T, Head.U, Body.O, Tail.NONE, Tone.SECOND),
            8, 187, "", new LeftRight(Animals.MA, Depth1Strokes5.TA_IT.structure()),
            new PhonoSemantic(Animals.MA, Depth1Strokes5.TA_IT.structure()));

    // – using 尼 NI_NUN (depth-1, Depth1Strokes5) ──────────────────────

    /** 呢 (ne) — question particle. LeftRight: 口(semantic) + 尼(phonetic, d1). */
    public static final ComposedZi NE_PARTICLE = new ComposedZi(
            lit("\u5462"), py(Initial.N, Head.OPEN, Body.E, Tail.NONE, Tone.FOURTH),
            8, 30, "", new LeftRight(BodyParts.KOU, Depth1Strokes5.NI_NUN.structure()),
            new PhonoSemantic(BodyParts.KOU, Depth1Strokes5.NI_NUN.structure()));

    /** 泥 (ní) — mud. LeftRight: 氵(semantic) + 尼(phonetic, d1). */
    public static final ComposedZi NI_MUD = new ComposedZi(
            lit("\u6CE5"), py(Initial.N, Head.OPEN, Body.I, Tail.NONE, Tone.SECOND),
            8, 85, "", new LeftRight(SAN_DIAN_SHUI, Depth1Strokes5.NI_NUN.structure()),
            new PhonoSemantic(SAN_DIAN_SHUI, Depth1Strokes5.NI_NUN.structure()));

    // – using 寺 SI_TEMPLE (depth-1, Depth1Strokes6) ───────────────────

    /** 侍 (shì) — serve. LeftRight: 亻(semantic) + 寺(phonetic, d1). */
    public static final ComposedZi SHI_SERVE = new ComposedZi(
            lit("\u4F8D"), py(Initial.SH, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH),
            8, 9, "", new LeftRight(DAN_REN_PANG, Depth1Strokes6.SI_TEMPLE.structure()),
            new PhonoSemantic(DAN_REN_PANG, Depth1Strokes6.SI_TEMPLE.structure()));

    /** 诗 (shī) — poetry. LeftRight: 讠(semantic) + 寺(phonetic, d1). */
    public static final ComposedZi SHI_POETRY = new ComposedZi(
            lit("\u8BD7"), py(Initial.SH, Head.OPEN, Body.I, Tail.NONE, Tone.FIRST),
            8, 149, "", new LeftRight(YAN_ZI_PANG, Depth1Strokes6.SI_TEMPLE.structure()),
            new PhonoSemantic(YAN_ZI_PANG, Depth1Strokes6.SI_TEMPLE.structure()));

    // – using 只 ZHI_ONLY (depth-1, Depth1Strokes5) ────────────────────

    /** 织 (zhī) — weave. LeftRight: 纟(semantic) + 只(phonetic, d1). */
    public static final ComposedZi ZHI_WEAVE = new ComposedZi(
            lit("\u7EC7"), py(Initial.ZH, Head.OPEN, Body.I, Tail.NONE, Tone.FIRST),
            8, 120, "", new LeftRight(JIAO_SI_PANG, Depth1Strokes5.ZHI_ONLY.structure()),
            new PhonoSemantic(JIAO_SI_PANG, Depth1Strokes5.ZHI_ONLY.structure()));

    // – using 分 FEN (depth-1, Depth1Strokes4) ─────────────────────────

    /** 贫 (pín) — poor. TopBottom: 分(phonetic, d1) + 贝(semantic). */
    public static final ComposedZi PIN_POOR = new ComposedZi(
            lit("\u8D2B"), py(Initial.P, Head.OPEN, Body.I, Tail.N, Tone.SECOND),
            8, 154, "", new TopBottom(Depth1Strokes4.FEN.structure(), Animals.BEI),
            new PhonoSemantic(Animals.BEI, Depth1Strokes4.FEN.structure()));

    // ── 9 strokes ──────────────────────────────────────────────────────

    /** 品 (pǐn) — product/taste. TopBottom: 口(top) + 口口(bottom). */
    public static final ComposedZi PIN = new ComposedZi(
            lit("品"),
            py(Initial.P, Head.OPEN, Body.I, Tail.N, Tone.THIRD),
            9, 30, "",
            new TopBottom(BodyParts.KOU, CommonBlocks.DOUBLE_KOU),
            new CompoundIndicative("口(mouth) \u00d7 3 \u2192 many mouths \u2192 tasting, judging quality")
    );

    // ── 11 strokes ─────────────────────────────────────────────────────

    /** 萌 (méng) — sprout. TopBottom: 艹(semantic) + 明(phonetic, depth-1). */
    public static final ComposedZi MENG = new ComposedZi(
            lit("萌"),
            py(Initial.M, Head.OPEN, Body.E, Tail.NG, Tone.SECOND),
            11, 140, "",
            new TopBottom(CAO_ZI_TOU, Depth1StrokesHigh.MING.structure()),
            new PhonoSemantic(CAO_ZI_TOU, Depth1StrokesHigh.MING.structure())
    );

    // ── 12 strokes ─────────────────────────────────────────────────────

    /** 雅 (yǎ) — elegant/refined. LeftRight: 牙(phonetic) + 佳(semantic, decomposed as 亻+圭). */
    public static final ComposedZi YA = new ComposedZi(
            lit("雅"),
            py(Initial.ZERO, Head.I, Body.A, Tail.NONE, Tone.THIRD),
            12, 172, "",
            new LeftRight(BodyParts.YA, AbstractConcepts.JIA_FINE),
            new PhonoSemantic(AbstractConcepts.JIA_FINE, BodyParts.YA)
    );

    /** 森 (sēn) — dense forest. TopBottom: 木(top) + 林(bottom, depth-1). */
    public static final ComposedZi SEN = new ComposedZi(
            lit("森"),
            py(Initial.S, Head.OPEN, Body.E, Tail.N, Tone.FIRST),
            12, 75, "",
            new TopBottom(WoodFamily.MU, Depth1StrokesHigh.LIN.structure()),
            new CompoundIndicative("木(tree) \u00d7 3 \u2192 dense forest")
    );

    // ── 13 strokes ─────────────────────────────────────────────────────

    /** 脚 (jiǎo) — foot/leg. LeftRight: 月(semantic) + 却(phonetic, LeftRight: 去+卩). */
    public static final ComposedZi JIAO_FOOT = new ComposedZi(
            lit("脚"),
            py(Initial.J, Head.I, Body.A, Tail.VOWEL_U, Tone.THIRD),
            13, 130, "",
            new LeftRight(NatureElements.YUE, Depth1Strokes7.QUE.structure()),
            new PhonoSemantic(NatureElements.YUE, Depth1Strokes7.QUE.structure())
    );

    // ── 16 strokes ─────────────────────────────────────────────────────

    /** 器 (qì) — vessel/utensil. TMB: 口口(top) + 犬(middle) + 口口(bottom). */
    public static final ComposedZi QI_VESSEL = new ComposedZi(
            lit("器"),
            py(Initial.Q, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH),
            16, 30, "",
            new TopMiddleBottom(CommonBlocks.DOUBLE_KOU, Animals.QUAN, CommonBlocks.DOUBLE_KOU),
            new CompoundIndicative("四口(four mouths/openings) + 犬(dog as guard) \u2192 vessel, instrument")
    );

    // ── 21 strokes ─────────────────────────────────────────────────────

    /** 蠢 (chǔn) — stupid/wriggling. TopBottom: 春(top) + 虫虫(bottom). */
    public static final ComposedZi CHUN_STUPID = new ComposedZi(
            lit("蠢"),
            py(Initial.CH, Head.U, Body.E, Tail.N, Tone.THIRD),
            21, 142, "",
            new TopBottom(NatureElements.CHUN_SPRING, CommonBlocks.DOUBLE_CHONG),
            new CompoundIndicative("春(spring, insects stir) + 虫虫(insects) \u2192 wriggling \u2192 stupid")
    );

    // ════════════════════════════════════════════════════════════════════

    public static final List<ComposedZi> ALL = List.of(
            // 8 strokes (depth-2)
            BAN_BOARD, FAN_PEDDLE, BAN_EDITION, WAN_PLAY, SONG_PINE,
            BAO_HUG, PAO_BUBBLE, BAO_FULL,
            DAN_CARRY, TAN_FLAT, ZHAO_BECKON,
            JU_ARREST, GOU_DOG,
            DIAN_SHOP, ZHAN_MOISTEN, TIE_CARD,
            FU_INSTRUCT, FU_MANSION,
            TUO_CAMEL,
            NE_PARTICLE, NI_MUD,
            SHI_SERVE, SHI_POETRY,
            ZHI_WEAVE, PIN_POOR,
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
