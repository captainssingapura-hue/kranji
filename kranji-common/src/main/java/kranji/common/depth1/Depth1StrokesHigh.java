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
            List.of(py(Initial.M, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            8, 72, "",
            new LeftRight(NatureElements.RI, NatureElements.YUE),
            new CompoundIndicative("日(sun) + 月(moon) → bright")
    );

    /** 林 (lín) — forest. LeftRight: 木 + 木. */
    public static final ComposedZi LIN = new ComposedZi(
            lit("林"),
            List.of(py(Initial.L, Head.OPEN, Body.I, Tail.N, Tone.SECOND)),
            8, 75, "",
            new LeftRight(WoodFamily.MU, WoodFamily.MU),
            new CompoundIndicative("木(tree) + 木(tree) → forest")
    );

    /** 国 (guó) — country. FullEnclosure: 囗(semantic) + 玉(phonetic). */
    public static final ComposedZi GUO = new ComposedZi(
            lit("国"),
            List.of(py(Initial.G, Head.U, Body.O, Tail.NONE, Tone.SECOND)),
            8, 31, "",
            new FullEnclosure(GUO_ZI_KUANG, Materials.YU_JADE),
            new PhonoSemantic(GUO_ZI_KUANG, Materials.YU_JADE)
    );

    // ── 9 strokes ──────────────────────────────────────────────────────

    /** 俗 (sú) — custom/vulgar. LeftRight: 亻(semantic) + 谷(phonetic). */
    public static final ComposedZi SU = new ComposedZi(
            lit("俗"),
            List.of(py(Initial.S, Head.U, Body.U, Tail.NONE, Tone.SECOND)),
            9, 9, "",
            new LeftRight(DAN_REN_PANG, NatureElements.GU),
            new PhonoSemantic(DAN_REN_PANG, NatureElements.GU)
    );

    // ── 10 strokes ─────────────────────────────────────────────────────

    /** 请 (qǐng) — invite/please. LeftRight: 讠(semantic) + 青(phonetic). */
    public static final ComposedZi QING_INVITE = new ComposedZi(
            lit("请"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.THIRD)),
            10, 149, "",
            new LeftRight(YAN_ZI_PANG, Materials.QING),
            new PhonoSemantic(YAN_ZI_PANG, Materials.QING)
    );

    // ── 11 strokes ─────────────────────────────────────────────────────

    /** 清 (qīng) — clear. LeftRight: 氵(semantic) + 青(phonetic). */
    public static final ComposedZi QING_CLEAR = new ComposedZi(
            lit("清"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.FIRST)),
            11, 85, "",
            new LeftRight(SAN_DIAN_SHUI, Materials.QING),
            new PhonoSemantic(SAN_DIAN_SHUI, Materials.QING)
    );

    /** 情 (qíng) — emotion/feeling. LeftRight: 忄(semantic) + 青(phonetic). */
    public static final ComposedZi QING_EMOTION = new ComposedZi(
            lit("情"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            11, 61, "",
            new LeftRight(SHU_XIN_PANG, Materials.QING),
            new PhonoSemantic(SHU_XIN_PANG, Materials.QING)
    );

    // ── 12 strokes ─────────────────────────────────────────────────────

    /** 晴 (qíng) — sunny/clear weather. LeftRight: 日(semantic) + 青(phonetic). */
    public static final ComposedZi QING_SUNNY = new ComposedZi(
            lit("晴"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            12, 72, "",
            new LeftRight(NatureElements.RI, Materials.QING),
            new PhonoSemantic(NatureElements.RI, Materials.QING)
    );

    // ── 13 strokes ─────────────────────────────────────────────────────

    /** 遨 (áo) — roam/travel. SemiEnclosureBottomLeft: 辶(semantic) + 敖(phonetic). */
    public static final ComposedZi AO = new ComposedZi(
            lit("遨"),
            List.of(py(Initial.ZERO, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.SECOND)),
            13, 162, "",
            new SemiEnclosureBottomLeft(ZOU_ZHI_DI, ActionsAndStates.AO_ROAM),
            new PhonoSemantic(ZOU_ZHI_DI, ActionsAndStates.AO_ROAM)
    );

    public static final List<ComposedZi> ALL = List.of(
            // 8 strokes
            MING, LIN, GUO,
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
