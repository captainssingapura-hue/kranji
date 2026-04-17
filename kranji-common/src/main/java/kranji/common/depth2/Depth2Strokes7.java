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
import kranji.singular.people.PeopleAndRoles;
import kranji.common.depth1.Depth1Strokes4;
import kranji.common.depth1.Depth1Strokes5;
import kranji.common.depth1.Depth1StrokesHigh;
import kranji.component.basic.WoodFamily;
import kranji.singular.materials.Materials;
import kranji.pinyin.*;
import kranji.zi.*;
import kranji.zi.ComposedBlock.*;

import java.util.List;

import static kranji.component.basic.BasicComponents.*;

public final class Depth2Strokes7 {

    private Depth2Strokes7() {}

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    private static ZiChar lit(String s) { return new ZiChar.Literal(s); }
    private static ZiChar uni(String s) { return new ZiChar.Unicode(s); }

    // ── 7 strokes ──────────────────────────────────────────────────────

    /** 远 (yuǎn) — far. SemiEnclosureBottomLeft: 辶 + 元(depth-1). */
    public static final ComposedZi YUAN_FAR = new ComposedZi(
            uni("\u8FDC"), List.of(py(Initial.ZERO, Head.U, Body.A, Tail.N, Tone.THIRD)),
            7, 162, "", new SemiEnclosureBottomLeft(ZOU_ZHI_DI, Depth1Strokes4.YUAN.structure()),
            new PhonoSemantic(ZOU_ZHI_DI, Depth1Strokes4.YUAN.structure()));

    /** 扮 (bàn) — dress up, play a role. LeftRight: 扌 + 分(depth-1). */
    public static final ComposedZi BAN_DRESS = new ComposedZi(
            uni("\u626E"), List.of(py(Initial.B, Head.OPEN, Body.A, Tail.N, Tone.FOURTH)),
            7, 64, "", new LeftRight(TI_SHOU_PANG, Depth1Strokes4.FEN.structure()),
            new PhonoSemantic(TI_SHOU_PANG, Depth1Strokes4.FEN.structure()));

    /** 芬 (fēn) — fragrance. TopBottom: 艹(semantic) + 分(phonetic, depth-1). */
    public static final ComposedZi FEN_FRAGRANCE = new ComposedZi(
            uni("\u82AC"), List.of(py(Initial.F, Head.OPEN, Body.E, Tail.N, Tone.FIRST)),
            7, 140, "", new TopBottom(CAO_ZI_TOU, Depth1Strokes4.FEN.structure()),
            new PhonoSemantic(CAO_ZI_TOU, Depth1Strokes4.FEN.structure()));

    /** 完 (wán) — complete, finish. TopBottom: 宀(semantic) + 元(phonetic, depth-1). */
    public static final ComposedZi WAN_COMPLETE = new ComposedZi(
            uni("\u5B8C"), List.of(py(Initial.ZERO, Head.U, Body.A, Tail.N, Tone.SECOND)),
            7, 40, "", new TopBottom(BAO_GAI_TOU, Depth1Strokes4.YUAN.structure()),
            new PhonoSemantic(BAO_GAI_TOU, Depth1Strokes4.YUAN.structure()));

    /** 园 (yuán) — garden. FullEnclosure: 囗 + 元(depth-1). */
    public static final ComposedZi YUAN_GARDEN = new ComposedZi(
            uni("\u56ED"), List.of(py(Initial.ZERO, Head.U, Body.A, Tail.N, Tone.SECOND)),
            7, 31, "", new FullEnclosure(GUO_ZI_KUANG, Depth1Strokes4.YUAN.structure()),
            new PhonoSemantic(GUO_ZI_KUANG, Depth1Strokes4.YUAN.structure()));

    /** 吩 (fēn) — instruct, tell. LeftRight: 口(semantic) + 分(phonetic, depth-1). */
    public static final ComposedZi FEN_INSTRUCT = new ComposedZi(
            uni("\u5429"), List.of(py(Initial.F, Head.OPEN, Body.E, Tail.N, Tone.FIRST)),
            7, 30, "", new LeftRight(BodyParts.KOU, Depth1Strokes4.FEN.structure()),
            new PhonoSemantic(BodyParts.KOU, Depth1Strokes4.FEN.structure()));

    /** 返 (fǎn) — return. SemiEnclosureBottomLeft: 辶 + 反(depth-1). */
    public static final ComposedZi FAN_RETURN = new ComposedZi(
            uni("\u8FD4"), List.of(py(Initial.F, Head.OPEN, Body.A, Tail.N, Tone.THIRD)),
            7, 162, "", new SemiEnclosureBottomLeft(ZOU_ZHI_DI, Depth1Strokes4.FAN_OPPOSE.structure()),
            new PhonoSemantic(ZOU_ZHI_DI, Depth1Strokes4.FAN_OPPOSE.structure()));

    /** 别 (bié) — other. LeftRight: 阝(left, semantic) + 付(phonetic, depth-1). */
    public static final ComposedZi FU_ATTACH = new ComposedZi(
            uni("\u9644"), List.of(py(Initial.F, Head.U, Body.NULL, Tail.NONE, Tone.FOURTH)),
            7, 170, "", new LeftRight(ZUO_ER_PANG, Depth1Strokes5.FU_PAY.structure()),
            new PhonoSemantic(ZUO_ER_PANG, Depth1Strokes5.FU_PAY.structure()));

    /** 纲 (gāng) — guiding principle. LeftRight: 纟(semantic) + 冈(phonetic, depth-1). */
    public static final ComposedZi GANG_PRINCIPLE = new ComposedZi(
            uni("\u7EB2"), List.of(py(Initial.G, Head.OPEN, Body.A, Tail.NG, Tone.FIRST)),
            7, 120, "", new LeftRight(JIAO_SI_PANG, Depth1Strokes4.GANG_RIDGE.structure()),
            new PhonoSemantic(JIAO_SI_PANG, Depth1Strokes4.GANG_RIDGE.structure()));

    /** 纵 (zòng) — vertical, indulge. LeftRight: 纟(semantic) + 从(phonetic, depth-1). */
    public static final ComposedZi ZONG_VERTICAL = new ComposedZi(
            uni("\u7EB5"), List.of(py(Initial.Z, Head.OPEN, Body.O, Tail.NG, Tone.FOURTH)),
            7, 120, "", new LeftRight(JIAO_SI_PANG, Depth1Strokes4.CONG_FROM.structure()),
            new PhonoSemantic(JIAO_SI_PANG, Depth1Strokes4.CONG_FROM.structure()));

    /** 纷 (fēn) — numerous, confused. LeftRight: 纟(semantic) + 分(phonetic, depth-1). */
    public static final ComposedZi FEN_NUMEROUS = new ComposedZi(
            uni("\u7EB7"), List.of(py(Initial.F, Head.OPEN, Body.E, Tail.N, Tone.FIRST)),
            7, 120, "", new LeftRight(JIAO_SI_PANG, Depth1Strokes4.FEN.structure()),
            new PhonoSemantic(JIAO_SI_PANG, Depth1Strokes4.FEN.structure()));

    /** 估 (gū) — estimate. LeftRight: 亻(semantic) + 古(phonetic, depth-1). */
    public static final ComposedZi GU_ESTIMATE = new ComposedZi(
            uni("\u4F30"), List.of(py(Initial.G, Head.U, Body.NULL, Tail.NONE, Tone.FIRST)),
            7, 9, "", new LeftRight(DAN_REN_PANG, AbstractConcepts.GU),
            new PhonoSemantic(DAN_REN_PANG, AbstractConcepts.GU));

    /** 但 (dàn) — but, however. LeftRight: 亻(semantic) + 旦(phonetic, depth-1). */
    public static final ComposedZi DAN_BUT = new ComposedZi(
            uni("\u4F46"), List.of(py(Initial.D, Head.OPEN, Body.A, Tail.N, Tone.FOURTH)),
            7, 9, "", new LeftRight(DAN_REN_PANG, Depth1Strokes5.DAN_DAWN.structure()),
            new PhonoSemantic(DAN_REN_PANG, Depth1Strokes5.DAN_DAWN.structure()));

    /** 识 (shí) — know, recognize. LeftRight: 讠(semantic) + 只(phonetic, depth-1). */
    public static final ComposedZi SHI_KNOW = new ComposedZi(
            uni("\u8BC6"), List.of(py(Initial.SH, Head.OPEN, Body.NULL, Tail.NONE, Tone.SECOND)),
            7, 149, "", new LeftRight(YAN_ZI_PANG, Depth1Strokes5.ZHI_ONLY.structure()),
            new PhonoSemantic(YAN_ZI_PANG, Depth1Strokes5.ZHI_ONLY.structure()));

    /** 努 (nǔ) — strive, exert. TopBottom: 奴(depth-1) + 力(semantic). */
    public static final ComposedZi NU_STRIVE = new ComposedZi(
            uni("\u52AA"), List.of(py(Initial.N, Head.U, Body.NULL, Tail.NONE, Tone.THIRD)),
            7, 19, "", new TopBottom(Depth1Strokes5.NU_SLAVE.structure(), ToolsAndVessels.LI_PLOW),
            new PhonoSemantic(ToolsAndVessels.LI_PLOW, Depth1Strokes5.NU_SLAVE.structure()));

    /** 岗 (gǎng) — hillock, sentry. TopBottom: 山(semantic) + 冈(phonetic, depth-1). */
    public static final ComposedZi GANG_SENTRY = new ComposedZi(
            uni("\u5C97"), List.of(py(Initial.G, Head.OPEN, Body.A, Tail.NG, Tone.THIRD)),
            7, 46, "", new TopBottom(NatureElements.SHAN, Depth1Strokes4.GANG_RIDGE.structure()),
            new PhonoSemantic(NatureElements.SHAN, Depth1Strokes4.GANG_RIDGE.structure()));

    /** 岔 (chà) — fork, branch off. TopBottom: 分(depth-1, top) + 山(bottom). */
    public static final ComposedZi CHA_FORK = new ComposedZi(
            uni("\u5C94"), List.of(py(Initial.CH, Head.OPEN, Body.A, Tail.NONE, Tone.FOURTH)),
            7, 46, "", new TopBottom(Depth1Strokes4.FEN.structure(), NatureElements.SHAN),
            new CompoundIndicative("\u5206(divide) + \u5C71(mountain) \u2192 forking path"));

    /** 彻 (chè) — thorough. LeftRight: 彳(semantic) + 切(phonetic, depth-1). */
    public static final ComposedZi CHE_THOROUGH = new ComposedZi(
            uni("\u5F7B"), List.of(py(Initial.CH, Head.OPEN, Body.E, Tail.NONE, Tone.FOURTH)),
            7, 60, "", new LeftRight(ToolsAndVessels.CHI_STEP, Depth1Strokes4.QIE_CUT.structure()),
            new PhonoSemantic(ToolsAndVessels.CHI_STEP, Depth1Strokes4.QIE_CUT.structure()));

    /** 饭 (fàn) — meal, rice. LeftRight: 饣(semantic) + 反(phonetic, depth-1). */
    public static final ComposedZi FAN_MEAL = new ComposedZi(
            uni("\u996D"), List.of(py(Initial.F, Head.OPEN, Body.A, Tail.N, Tone.FOURTH)),
            7, 184, "", new LeftRight(SHI_ZI_PANG, Depth1Strokes4.FAN_OPPOSE.structure()),
            new PhonoSemantic(SHI_ZI_PANG, Depth1Strokes4.FAN_OPPOSE.structure()));

    /** 励 (lì) — encourage. LeftRight: 厉(depth-1) + 力. */
    public static final ComposedZi LI_ENCOURAGE = new ComposedZi(
            uni("\u52B1"), List.of(py(Initial.L, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH)),
            7, 19, "", new LeftRight(Depth1Strokes5.LI_STRICT.structure(), ToolsAndVessels.LI_PLOW),
            new PhonoSemantic(Depth1Strokes5.LI_STRICT.structure(), ToolsAndVessels.LI_PLOW));

    public static final List<ComposedZi> ALL = List.of(
            YUAN_FAR, BAN_DRESS, FEN_FRAGRANCE, WAN_COMPLETE, YUAN_GARDEN,
            FEN_INSTRUCT, FAN_RETURN, FU_ATTACH, GANG_PRINCIPLE, ZONG_VERTICAL,
            FEN_NUMEROUS, GU_ESTIMATE, DAN_BUT, SHI_KNOW, NU_STRIVE,
            GANG_SENTRY, CHA_FORK, CHE_THOROUGH, FAN_MEAL, LI_ENCOURAGE
    );
}
