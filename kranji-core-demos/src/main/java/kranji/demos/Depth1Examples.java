package kranji.demos;

import kranji.classification.EtymologicalCategory.*;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.body.BodyParts;
import kranji.singular.materials.Materials;
import kranji.singular.nature.NatureElements;
import kranji.singular.people.PeopleAndRoles;
import kranji.singular.structures.Structures;
import kranji.singular.tools.ToolsAndVessels;
import kranji.component.basic.WoodFamily;
import kranji.pinyin.*;
import kranji.zi.*;
import kranji.zi.ComposedBlock.*;

import java.util.List;

import static kranji.component.basic.BasicComponents.*;

/**
 * Depth-1 composed characters: every direct component is a singular block.
 *
 * <p>Depth is defined recursively as {@code max(depth of components) + 1},
 * where singular blocks have depth 0.</p>
 */
public final class Depth1Examples {

    private Depth1Examples() {}

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    // ── LeftRight / Compound Indicative ─────────────────────────────────

    /** 明 (míng) — bright. LeftRight: 日 + 月. */
    public static final ComposedZi MING = new ComposedZi(
            "明",
            List.of(py(Initial.M, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            8, 72, "",
            new LeftRight(NatureElements.RI, NatureElements.YUE),
            new CompoundIndicative("日(sun) + 月(moon) → bright")
    );

    /** 休 (xiū) — rest. LeftRight: 亻 + 木. */
    public static final ComposedZi XIU = new ComposedZi(
            "休",
            List.of(py(Initial.X, Head.I, Body.O, Tail.VOWEL_U, Tone.FIRST)),
            6, 9, "",
            new LeftRight(DAN_REN_PANG, WoodFamily.MU),
            new CompoundIndicative("亻(person) + 木(tree) → person leaning on tree → rest")
    );

    /** 孔 (kǒng) — hole/Confucius. LeftRight: 子 + 乚. */
    public static final ComposedZi KONG = new ComposedZi(
            "孔",
            List.of(py(Initial.K, Head.OPEN, Body.O, Tail.NG, Tone.THIRD)),
            4, 39, "",
            new LeftRight(PeopleAndRoles.ZI, SingularBlock.plain("乚")),
            new CompoundIndicative("子(child) + 乚(opening) → hole")
    );

    /** 林 (lín) — forest. LeftRight: 木 + 木. */
    public static final ComposedZi LIN = new ComposedZi(
            "林",
            List.of(py(Initial.L, Head.OPEN, Body.I, Tail.N, Tone.SECOND)),
            8, 75, "",
            new LeftRight(WoodFamily.MU, WoodFamily.MU),
            new CompoundIndicative("木(tree) + 木(tree) → forest")
    );

    // ── LeftRight / Phono-Semantic ──────────────────────────────────────

    /** 俗 (sú) — custom/vulgar. LeftRight: 亻(semantic) + 谷(phonetic). */
    public static final ComposedZi SU = new ComposedZi(
            "俗",
            List.of(py(Initial.S, Head.U, Body.U, Tail.NONE, Tone.SECOND)),
            9, 9, "",
            new LeftRight(DAN_REN_PANG, SingularBlock.plain("谷")),
            new PhonoSemantic(DAN_REN_PANG, SingularBlock.plain("谷"))
    );

    /** 清 (qīng) — clear. LeftRight: 氵(semantic) + 青(phonetic). */
    public static final ComposedZi QING_CLEAR = new ComposedZi(
            "清",
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.FIRST)),
            11, 85, "",
            new LeftRight(SAN_DIAN_SHUI, Materials.QING),
            new PhonoSemantic(SAN_DIAN_SHUI, Materials.QING)
    );

    /** 请 (qǐng) — invite/please. LeftRight: 讠(semantic) + 青(phonetic). */
    public static final ComposedZi QING_INVITE = new ComposedZi(
            "请",
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.THIRD)),
            10, 149, "",
            new LeftRight(YAN_ZI_PANG, Materials.QING),
            new PhonoSemantic(YAN_ZI_PANG, Materials.QING)
    );

    /** 情 (qíng) — emotion/feeling. LeftRight: 忄(semantic) + 青(phonetic). */
    public static final ComposedZi QING_EMOTION = new ComposedZi(
            "情",
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            11, 61, "",
            new LeftRight(SHU_XIN_PANG, Materials.QING),
            new PhonoSemantic(SHU_XIN_PANG, Materials.QING)
    );

    /** 晴 (qíng) — sunny/clear weather. LeftRight: 日(semantic) + 青(phonetic). */
    public static final ComposedZi QING_SUNNY = new ComposedZi(
            "晴",
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            12, 72, "",
            new LeftRight(NatureElements.RI, Materials.QING),
            new PhonoSemantic(NatureElements.RI, Materials.QING)
    );

    // ── TopBottom / Phono-Semantic ──────────────────────────────────────

    /** 字 (zì) — character/word. TopBottom: 宀(semantic) + 子(phonetic). */
    public static final ComposedZi ZI = new ComposedZi(
            "字",
            List.of(py(Initial.Z, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH)),
            6, 39, "",
            new TopBottom(BAO_GAI_TOU, PeopleAndRoles.ZI),
            new PhonoSemantic(BAO_GAI_TOU, PeopleAndRoles.ZI)
    );

    /** 花 (huā) — flower. TopBottom: 艹(semantic) + 化(phonetic). */
    public static final ComposedZi HUA = new ComposedZi(
            "花",
            List.of(py(Initial.H, Head.U, Body.A, Tail.NONE, Tone.FIRST)),
            7, 140, "",
            new TopBottom(CAO_ZI_TOU, SingularBlock.plain("化")),
            new PhonoSemantic(CAO_ZI_TOU, SingularBlock.plain("化"))
    );

    // ── FullEnclosure / Phono-Semantic ──────────────────────────────────

    /** 国 (guó) — country. FullEnclosure: 囗(semantic) + 玉(phonetic). */
    public static final ComposedZi GUO = new ComposedZi(
            "国",
            List.of(py(Initial.G, Head.U, Body.O, Tail.NONE, Tone.SECOND)),
            8, 31, "",
            new FullEnclosure(GUO_ZI_KUANG, Materials.YU_JADE),
            new PhonoSemantic(GUO_ZI_KUANG, Materials.YU_JADE)
    );

    // ── SemiEnclosureBottomLeft / Phono-Semantic ────────────────────────

    /** 遨 (áo) — roam/travel. SemiEnclosureBottomLeft: 辶(semantic) + 敖(phonetic). */
    public static final ComposedZi AO = new ComposedZi(
            "遨",
            List.of(py(Initial.ZERO, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.SECOND)),
            13, 162, "",
            new SemiEnclosureBottomLeft(ZOU_ZHI_DI, SingularBlock.plain("敖")),
            new PhonoSemantic(ZOU_ZHI_DI, SingularBlock.plain("敖"))
    );

    // ── LeftRight / Phono-Semantic (additional) ────────────────────────

    /** 刑 (xíng) — punishment. LeftRight: 开(phonetic) + 刂(semantic). */
    public static final ComposedZi XING_PUNISH = new ComposedZi(
            "刑",
            List.of(py(Initial.X, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            6, 18, "",
            new LeftRight(SingularBlock.plain("开"), LI_DAO_PANG),
            new PhonoSemantic(LI_DAO_PANG, SingularBlock.plain("开"))
    );

    /** 动 (dòng) — move. LeftRight: 云(phonetic) + 力(semantic). */
    public static final ComposedZi DONG = new ComposedZi(
            "动",
            List.of(py(Initial.D, Head.OPEN, Body.O, Tail.NG, Tone.FOURTH)),
            6, 19, "",
            new LeftRight(NatureElements.YUN, ToolsAndVessels.LI_PLOW),
            new PhonoSemantic(ToolsAndVessels.LI_PLOW, NatureElements.YUN)
    );

    /** 扛 (káng) — carry on shoulder. LeftRight: 扌(semantic) + 工(phonetic). */
    public static final ComposedZi KANG = new ComposedZi(
            "扛",
            List.of(py(Initial.K, Head.OPEN, Body.A, Tail.NG, Tone.SECOND)),
            6, 64, "",
            new LeftRight(TI_SHOU_PANG, ActionsAndStates.GONG_WORK),
            new PhonoSemantic(TI_SHOU_PANG, ActionsAndStates.GONG_WORK)
    );

    // ── TopBottom / Phono-Semantic (additional) ─────────────────────────

    /** 寺 (sì) — temple. TopBottom: 土(phonetic) + 寸(semantic). */
    public static final ComposedZi SI_TEMPLE = new ComposedZi(
            "寺",
            List.of(py(Initial.S, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH)),
            6, 41, "",
            new TopBottom(NatureElements.TU, Structures.CUN_INCH),
            new PhonoSemantic(Structures.CUN_INCH, NatureElements.TU)
    );

    // ── TopBottom / Compound Indicative (additional) ────────────────────

    /** 吉 (jí) — auspicious. TopBottom: 士 + 口. */
    public static final ComposedZi JI_LUCKY = new ComposedZi(
            "吉",
            List.of(py(Initial.J, Head.OPEN, Body.I, Tail.NONE, Tone.SECOND)),
            6, 30, "",
            new TopBottom(PeopleAndRoles.SHI, BodyParts.KOU),
            new CompoundIndicative("士(scholar/jade tablet) + 口(mouth) → auspicious proclamation")
    );

    // ── SemiEnclosureUpperRight / Phono-Semantic ────────────────────────

    /** All depth-1 examples. */
    public static final List<ComposedZi> ALL = List.of(
            MING, XIU, KONG, LIN,
            SU, QING_CLEAR, QING_INVITE, QING_EMOTION, QING_SUNNY,
            XING_PUNISH, DONG, KANG,
            ZI, HUA, SI_TEMPLE, JI_LUCKY,
            GUO,
            AO
    );
}
