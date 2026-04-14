package kranji.demos;

import kranji.classification.EtymologicalCategory;
import kranji.classification.EtymologicalCategory.*;
import kranji.component.basic.WoodFamily;
import kranji.singular.body.BodyParts;
import kranji.singular.nature.NatureElements;
import kranji.pinyin.*;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.animals.Animals;
import kranji.singular.materials.Materials;
import kranji.singular.people.PeopleAndRoles;
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.structures.Structures;
import kranji.zi.*;
import kranji.zi.ComposedBlock.*;

import java.util.List;

import static kranji.component.basic.BasicComponents.*;

/**
 * A curated collection of example {@link Zi} instances that demonstrate
 * coverage across different structural compositions and etymological categories.
 */
public final class ExampleCharacters {

    private ExampleCharacters() {}

    // ── Helper: build a PinyinSyllable concisely ────────────────────────

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    /**
     * Minimal SingularZi stub for characters not yet catalogued
     * in the singulars library. Carries full Zi metadata.
     */
    private record SimpleZi(
            String glyph,
            List<PinyinSyllable> pinyin,
            int strokes,
            int radicalNo,
            String meaning,
            EtymologicalCategory etymology
    ) implements SingularZi {}

    // ── Singular / Pictograph ───────────────────────────────────────────

    /** 人 (rén) — person. Singular pictograph. Kangxi radical 9. */
    public static final Zi REN = PeopleAndRoles.REN;

    /** 山 (shān) — mountain. Singular pictograph. Kangxi radical 46. */
    public static final Zi SHAN = NatureElements.SHAN;

    /** 日 (rì) — sun/day. Singular pictograph. Kangxi radical 72. */
    public static final Zi RI = NatureElements.RI;

    /** 月 (yuè) — moon/month. Singular pictograph. Kangxi radical 74. */
    public static final Zi YUE = NatureElements.YUE;

    /** 水 (shuǐ) — water. Singular pictograph. Kangxi radical 85. */
    public static final Zi SHUI = NatureElements.SHUI;

    /** 木 (mù) — tree/wood. Singular pictograph. Kangxi radical 75. */
    public static final Zi MU = WoodFamily.MU;

    /** 口 (kǒu) — mouth. Singular pictograph. Kangxi radical 30. */
    public static final Zi KOU = BodyParts.KOU;

    /** 火 (huǒ) — fire. Singular pictograph. Kangxi radical 86. */
    public static final Zi HUO = NatureElements.HUO;

    // ── Singular / Simple Indicative ────────────────────────────────────

    /** 上 (shàng) — above. Singular simple indicative. Kangxi radical 1. */
    public static final Zi SHANG = SpaceAndDirection.SHANG;

    /** 下 (xià) — below. Singular simple indicative. Kangxi radical 1. */
    public static final Zi XIA = SpaceAndDirection.XIA;

    // ── Singular / Pictograph (additional) ───────────────────────────────

    /** 已 (yǐ) — already/stop. Singular. Kangxi radical 49. */
    public static final Zi YI_ALREADY = new SimpleZi(
            "已",
            List.of(py(Initial.ZERO, Head.OPEN, Body.I, Tail.NONE, Tone.THIRD)),
            3, 49, "",
            new Pictograph()
    );

    /** 己 (jǐ) — self. Singular. Kangxi radical 49. */
    public static final Zi JI_SELF = new SimpleZi(
            "己",
            List.of(py(Initial.J, Head.OPEN, Body.I, Tail.NONE, Tone.THIRD)),
            3, 49, "",
            new Pictograph()
    );

    // ── LeftRight / Compound Indicative ─────────────────────────────────

    /** 明 (míng) — bright. LeftRight: 日(left) + 月(right). Kangxi radical 72. */
    public static final Zi MING = new ComposedZi(
            "明",
            List.of(py(Initial.M, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            8, 72, "",
            new LeftRight(NatureElements.RI, NatureElements.YUE),
            new CompoundIndicative("日(sun) + 月(moon) → bright")
    );

    /** 休 (xiū) — rest. LeftRight: 亻(left) + 木(right). Kangxi radical 9. */
    public static final Zi XIU = new ComposedZi(
            "休",
            List.of(py(Initial.X, Head.I, Body.O, Tail.VOWEL_U, Tone.FIRST)),
            6, 9, "",
            new LeftRight(DAN_REN_PANG, WoodFamily.MU),
            new CompoundIndicative("亻(person) + 木(tree) → person leaning on tree → rest")
    );

    /** 孔 (kǒng) — hole/Confucius. LeftRight: 子(left) + 乚(right). Kangxi radical 39. */
    public static final Zi KONG = new ComposedZi(
            "孔",
            List.of(py(Initial.K, Head.OPEN, Body.O, Tail.NG, Tone.THIRD)),
            4, 39, "",
            new LeftRight(PeopleAndRoles.ZI, SingularBlock.plain("乚")),
            new CompoundIndicative("子(child) + 乚(opening) → hole")
    );

    /** 林 (lín) — forest. LeftRight: 木(left) + 木(right). Kangxi radical 75. */
    public static final Zi LIN = new ComposedZi(
            "林",
            List.of(py(Initial.L, Head.OPEN, Body.I, Tail.N, Tone.SECOND)),
            8, 75, "",
            new LeftRight(WoodFamily.MU, WoodFamily.MU),
            new CompoundIndicative("木(tree) + 木(tree) → forest")
    );

    // ── LeftRight / Phono-Semantic ──────────────────────────────────────

    /** 俗 (sú) — custom/vulgar. LeftRight: 亻(left, semantic) + 谷(right, phonetic). Kangxi radical 9. */
    public static final Zi SU = new ComposedZi(
            "俗",
            List.of(py(Initial.S, Head.U, Body.U, Tail.NONE, Tone.SECOND)),
            9, 9, "",
            new LeftRight(DAN_REN_PANG, SingularBlock.plain("谷")),
            new PhonoSemantic(DAN_REN_PANG, SingularBlock.plain("谷"))
    );

    /** 清 (qīng) — clear. LeftRight: 氵(left, semantic) + 青(right, phonetic). Kangxi radical 85. */
    public static final Zi QING_CLEAR = new ComposedZi(
            "清",
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.FIRST)),
            11, 85, "",
            new LeftRight(SAN_DIAN_SHUI, Materials.QING),
            new PhonoSemantic(SAN_DIAN_SHUI, Materials.QING)
    );

    /** 请 (qǐng) — invite/please. LeftRight: 讠(left, semantic) + 青(right, phonetic). Kangxi radical 149. */
    public static final Zi QING_INVITE = new ComposedZi(
            "请",
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.THIRD)),
            10, 149, "",
            new LeftRight(YAN_ZI_PANG, Materials.QING),
            new PhonoSemantic(YAN_ZI_PANG, Materials.QING)
    );

    /** 情 (qíng) — emotion/feeling. LeftRight: 忄(left, semantic) + 青(right, phonetic). Kangxi radical 61. */
    public static final Zi QING_EMOTION = new ComposedZi(
            "情",
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            11, 61, "",
            new LeftRight(SHU_XIN_PANG, Materials.QING),
            new PhonoSemantic(SHU_XIN_PANG, Materials.QING)
    );

    /** 晴 (qíng) — sunny/clear weather. LeftRight: 日(left, semantic) + 青(right, phonetic). Kangxi radical 72. */
    public static final Zi QING_SUNNY = new ComposedZi(
            "晴",
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            12, 72, "",
            new LeftRight(NatureElements.RI, Materials.QING),
            new PhonoSemantic(NatureElements.RI, Materials.QING)
    );

    /** 雅 (yǎ) — elegant/refined. LeftRight: 牙(left, phonetic) + 佳(right, semantic). Kangxi radical 172. */
    public static final Zi YA = new ComposedZi(
            "雅",
            List.of(py(Initial.ZERO, Head.I, Body.A, Tail.NONE, Tone.THIRD)),
            12, 172, "",
            new LeftRight(SingularBlock.plain("牙"),
                    new LeftRight(DAN_REN_PANG, SingularBlock.plain("圭"))),
            new PhonoSemantic(SingularBlock.plain("佳"), SingularBlock.plain("牙"))
    );

    // ── TopBottom / Phono-Semantic ──────────────────────────────────────

    /** 字 (zì) — character/word. TopBottom: 宀(top, semantic) + 子(bottom, phonetic). Kangxi radical 39. */
    public static final Zi ZI = new ComposedZi(
            "字",
            List.of(py(Initial.Z, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH)),
            6, 39, "",
            new TopBottom(BAO_GAI_TOU, PeopleAndRoles.ZI),
            new PhonoSemantic(BAO_GAI_TOU, PeopleAndRoles.ZI)
    );

    /** 花 (huā) — flower. TopBottom: 艹(top, semantic) + 化(bottom, phonetic). Kangxi radical 140. */
    public static final Zi HUA = new ComposedZi(
            "花",
            List.of(py(Initial.H, Head.U, Body.A, Tail.NONE, Tone.FIRST)),
            7, 140, "",
            new TopBottom(CAO_ZI_TOU, SingularBlock.plain("化")),
            new PhonoSemantic(CAO_ZI_TOU, SingularBlock.plain("化"))
    );

    // ── TopMiddleBottom / Compound Indicative ────────────────────────────

    /** 器 (qì) — vessel/utensil. TMB: double-口(top) + 犬(middle) + double-口(bottom). Kangxi radical 30. */
    public static final Zi QI_VESSEL = new ComposedZi(
            "器",
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH)),
            16, 30, "",
            new TopMiddleBottom(
                    new LeftRight(SingularBlock.plain("口"), SingularBlock.plain("口")),
                    SingularBlock.plain("犬"),
                    new LeftRight(SingularBlock.plain("口"), SingularBlock.plain("口"))
            ),
            new CompoundIndicative("四口(four mouths/openings) + 犬(dog as guard) → vessel, instrument")
    );

    // ── FullEnclosure / Phono-Semantic ──────────────────────────────────

    /** 国 (guó) — country. FullEnclosure: 囗(outer, semantic) + 玉(inner, phonetic). Kangxi radical 31. */
    public static final Zi GUO = new ComposedZi(
            "国",
            List.of(py(Initial.G, Head.U, Body.O, Tail.NONE, Tone.SECOND)),
            8, 31, "",
            new FullEnclosure(GUO_ZI_KUANG, Materials.YU_JADE),
            new PhonoSemantic(GUO_ZI_KUANG, Materials.YU_JADE)
    );

    // ── TopBottom (was Mosaic) / Compound Indicative ────────────────────

    /** 品 (pǐn) — product/taste. TopBottom: 口 + double-口. Kangxi radical 30. */
    public static final Zi PIN = new ComposedZi(
            "品",
            List.of(py(Initial.P, Head.OPEN, Body.I, Tail.N, Tone.THIRD)),
            9, 30, "",
            new TopBottom(BodyParts.KOU, new LeftRight(SingularBlock.plain("口"), SingularBlock.plain("口"))),
            new CompoundIndicative("口(mouth) × 3 → many mouths → tasting, judging quality")
    );

    /** 森 (sēn) — dense forest. TopBottom: 木 + double-木. Kangxi radical 75. */
    public static final Zi SEN = new ComposedZi(
            "森",
            List.of(py(Initial.S, Head.OPEN, Body.E, Tail.N, Tone.FIRST)),
            12, 75, "",
            new TopBottom(WoodFamily.MU, new LeftRight(SingularBlock.plain("木"), SingularBlock.plain("木"))),
            new CompoundIndicative("木(tree) × 3 → dense forest")
    );

    // ── SemiEnclosureBottomLeft / Phono-Semantic ─────────────────────

    /** 遨 (áo) — roam/travel. SemiEnclosureBottomLeft: 辶(wrapper, semantic) + 敖(content, phonetic). Kangxi radical 162. */
    public static final Zi AO = new ComposedZi(
            "遨",
            List.of(py(Initial.ZERO, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.SECOND)),
            13, 162, "",
            new SemiEnclosureBottomLeft(ZOU_ZHI_DI, SingularBlock.plain("敖")),
            new PhonoSemantic(ZOU_ZHI_DI, SingularBlock.plain("敖"))
    );

    // ── SemiEnclosureBottomLeft / Compound Indicative (recursive) ─────

    /**
     * 𰻝 (biáng) — the notoriously complex character from biángbiáng noodles (𰻝𰻝面).
     */
    public static final Zi BIANG = new ComposedZi(
            "𰻝",
            List.of(py(Initial.B, Head.I, Body.A, Tail.NG, Tone.SECOND)),
            58, 162, "",
            new SemiEnclosureBottomLeft(
                    ZOU_ZHI_DI,                                 // 辶 — walk radical
                    new TopMiddleBottom(                            // ── Level 1 ──
                            Structures.XUE,                        // cave / roof
                            new LeftMiddleRight(
                                NatureElements.YUE,                     // ── Level 2 ──
                                new TopBottom(                          // center column
                                        new LeftMiddleRight(SingularBlock.plain("幺"), ActionsAndStates.YAN_SPEECH, SingularBlock.plain("幺")),
                                        new LeftMiddleRight(SpaceAndDirection.CHANG, Animals.MA, SpaceAndDirection.CHANG)
                                ),
                                LI_DAO_PANG
                            ),
                            SingularBlock.plain("心")                 // heart
                    )
            ),
            new CompoundIndicative(
                    "folk-coined character for the sound biáng — "
                    + "the slapping noise of noodle dough pulled and struck against a board"
            )
    );

    // ── All examples ────────────────────────────────────────────────────

    /** All example characters in a single list for easy iteration. */
    public static final List<Zi> ALL = List.of(
            // Singular / Pictograph
            REN, SHAN, RI, YUE, SHUI, MU, KOU, HUO,
            // Singular (additional)
            YI_ALREADY, JI_SELF,
            // Singular / Simple Indicative
            SHANG, XIA,
            // LeftRight / Compound Indicative
            MING, XIU, KONG, LIN,
            // LeftRight / Phono-Semantic
            SU, YA, QING_CLEAR, QING_INVITE, QING_EMOTION, QING_SUNNY,
            // TopBottom / Phono-Semantic
            ZI, HUA,
            // TopMiddleBottom / Compound Indicative
            QI_VESSEL,
            // FullEnclosure / Phono-Semantic
            GUO,
            // TopBottom (was Mosaic) / Compound Indicative
            PIN, SEN,
            // SemiEnclosureBottomLeft / Phono-Semantic
            AO,
            // SemiEnclosureBottomLeft / Compound Indicative (recursive)
            BIANG
    );
}
