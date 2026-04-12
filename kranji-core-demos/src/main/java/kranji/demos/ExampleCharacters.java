package kranji.demos;

import kranji.classification.EtymologicalCategory.*;
import kranji.component.HintedZi;
import kranji.component.basic.WoodFamily;
import kranji.entry.ChineseCharacterEntry;
import kranji.pinyin.*;
import kranji.singular.actions.ActionsAndStates;
import kranji.singular.animals.Animals;
import kranji.singular.materials.Materials;
import kranji.singular.people.PeopleAndRoles;
import kranji.singular.space.SpaceAndDirection;
import kranji.singular.structures.Structures;
import kranji.zi.ComposedZi.*;
import kranji.zi.SingularZi;

import java.util.List;

import static kranji.component.basic.BasicComponents.*;
import static kranji.entry.ChineseCharacterEntry.toCodepoint;

/**
 * A curated collection of example {@link ChineseCharacterEntry} instances that
 * demonstrate coverage across different structural compositions and etymological
 * categories.
 */
public final class ExampleCharacters {

    private ExampleCharacters() {}

    // ── Helper: build a PinyinSyllable concisely ────────────────────────

    private static PinyinSyllable py(Initial ini, Head h, Body b, Tail t, Tone tone) {
        return new PinyinSyllable(ini, new Final(h, b, t), tone);
    }

    // ── Singular / Pictograph ───────────────────────────────────────────

    /** 人 (rén) — person. Singular pictograph. Kangxi radical 9. */
    public static final ChineseCharacterEntry REN = new ChineseCharacterEntry(
            "人", toCodepoint("人"),
            List.of(py(Initial.R, Head.OPEN, Body.E, Tail.N, Tone.SECOND)),
            2, 9,
            SingularZi.plain("人"),
            new Pictograph()
    );

    /** 山 (shān) — mountain. Singular pictograph. Kangxi radical 46. */
    public static final ChineseCharacterEntry SHAN = new ChineseCharacterEntry(
            "山", toCodepoint("山"),
            List.of(py(Initial.SH, Head.OPEN, Body.A, Tail.N, Tone.FIRST)),
            3, 46,
            SingularZi.plain("山"),
            new Pictograph()
    );

    /** 日 (rì) — sun/day. Singular pictograph. Kangxi radical 72. */
    public static final ChineseCharacterEntry RI = new ChineseCharacterEntry(
            "日", toCodepoint("日"),
            List.of(py(Initial.R, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH)),
            4, 72,
            HintedZi.RI,
            new Pictograph()
    );

    /** 月 (yuè) — moon/month. Singular pictograph. Kangxi radical 74. */
    public static final ChineseCharacterEntry YUE = new ChineseCharacterEntry(
            "月", toCodepoint("月"),
            List.of(py(Initial.ZERO, Head.V, Body.E_CARON, Tail.NONE, Tone.FOURTH)),
            4, 74,
            HintedZi.YUE,
            new Pictograph()
    );

    /** 水 (shuǐ) — water. Singular pictograph. Kangxi radical 85. */
    public static final ChineseCharacterEntry SHUI = new ChineseCharacterEntry(
            "水", toCodepoint("水"),
            List.of(py(Initial.SH, Head.U, Body.E, Tail.VOWEL_I, Tone.THIRD)),
            4, 85,
            SingularZi.plain("水"),
            new Pictograph()
    );

    /** 木 (mù) — tree/wood. Singular pictograph. Kangxi radical 75. */
    public static final ChineseCharacterEntry MU = new ChineseCharacterEntry(
            "木", toCodepoint("木"),
            List.of(py(Initial.M, Head.U, Body.U, Tail.NONE, Tone.FOURTH)),
            4, 75,
            WoodFamily.MU,
            new Pictograph()
    );

    /** 口 (kǒu) — mouth. Singular pictograph. Kangxi radical 30. */
    public static final ChineseCharacterEntry KOU = new ChineseCharacterEntry(
            "口", toCodepoint("口"),
            List.of(py(Initial.K, Head.OPEN, Body.O, Tail.VOWEL_U, Tone.THIRD)),
            3, 30,
            HintedZi.KOU,
            new Pictograph()
    );

    /** 火 (huǒ) — fire. Singular pictograph. Kangxi radical 86. */
    public static final ChineseCharacterEntry HUO = new ChineseCharacterEntry(
            "火", toCodepoint("火"),
            List.of(py(Initial.H, Head.U, Body.O, Tail.NONE, Tone.THIRD)),
            4, 86,
            SingularZi.plain("火"),
            new Pictograph()
    );

    // ── Singular / Simple Indicative ────────────────────────────────────

    /** 上 (shàng) — above. Singular simple indicative. Kangxi radical 1. */
    public static final ChineseCharacterEntry SHANG = new ChineseCharacterEntry(
            "上", toCodepoint("上"),
            List.of(py(Initial.SH, Head.OPEN, Body.A, Tail.NG, Tone.FOURTH)),
            3, 1,
            SingularZi.plain("上"),
            new SimpleIndicative("stroke above the baseline marks 'above'")
    );

    /** 下 (xià) — below. Singular simple indicative. Kangxi radical 1. */
    public static final ChineseCharacterEntry XIA = new ChineseCharacterEntry(
            "下", toCodepoint("下"),
            List.of(py(Initial.X, Head.I, Body.A, Tail.NONE, Tone.FOURTH)),
            3, 1,
            SingularZi.plain("下"),
            new SimpleIndicative("stroke below the baseline marks 'below'")
    );

    // ── Singular / Pictograph (additional) ───────────────────────────────

    /** 已 (yǐ) — already/stop. Singular. Kangxi radical 49. */
    public static final ChineseCharacterEntry YI_ALREADY = new ChineseCharacterEntry(
            "已", toCodepoint("已"),
            List.of(py(Initial.ZERO, Head.OPEN, Body.I, Tail.NONE, Tone.THIRD)),
            3, 49,
            SingularZi.plain("已"),
            new Pictograph()
    );

    /** 己 (jǐ) — self. Singular. Kangxi radical 49. */
    public static final ChineseCharacterEntry JI_SELF = new ChineseCharacterEntry(
            "己", toCodepoint("己"),
            List.of(py(Initial.J, Head.OPEN, Body.I, Tail.NONE, Tone.THIRD)),
            3, 49,
            SingularZi.plain("己"),
            new Pictograph()
    );

    // ── LeftRight / Compound Indicative ─────────────────────────────────

    /** 明 (míng) — bright. LeftRight: 日(left) + 月(right). Kangxi radical 72. */
    public static final ChineseCharacterEntry MING = new ChineseCharacterEntry(
            "明", toCodepoint("明"),
            List.of(py(Initial.M, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            8, 72,
            new LeftRight(HintedZi.RI, HintedZi.YUE),
            new CompoundIndicative("日(sun) + 月(moon) → bright")
    );

    /** 休 (xiū) — rest. LeftRight: 亻(left) + 木(right). Kangxi radical 9. */
    public static final ChineseCharacterEntry XIU = new ChineseCharacterEntry(
            "休", toCodepoint("休"),
            List.of(py(Initial.X, Head.I, Body.O, Tail.VOWEL_U, Tone.FIRST)),
            6, 9,
            new LeftRight(DAN_REN_PANG, WoodFamily.MU),
            new CompoundIndicative("亻(person) + 木(tree) → person leaning on tree → rest")
    );

    /** 孔 (kǒng) — hole/Confucius. LeftRight: 子(left) + 乚(right). Kangxi radical 39. */
    public static final ChineseCharacterEntry KONG = new ChineseCharacterEntry(
            "孔", toCodepoint("孔"),
            List.of(py(Initial.K, Head.OPEN, Body.O, Tail.NG, Tone.THIRD)),
            4, 39,
            new LeftRight(PeopleAndRoles.ZI, SingularZi.plain("乚")),
            new CompoundIndicative("子(child) + 乚(opening) → hole")
    );

    /** 林 (lín) — forest. LeftRight: 木(left) + 木(right). Kangxi radical 75. */
    public static final ChineseCharacterEntry LIN = new ChineseCharacterEntry(
            "林", toCodepoint("林"),
            List.of(py(Initial.L, Head.OPEN, Body.I, Tail.N, Tone.SECOND)),
            8, 75,
            new LeftRight(WoodFamily.MU, WoodFamily.MU),
            new CompoundIndicative("木(tree) + 木(tree) → forest")
    );

    // ── LeftRight / Phono-Semantic ──────────────────────────────────────

    /** 俗 (sú) — custom/vulgar. LeftRight: 亻(left, semantic) + 谷(right, phonetic). Kangxi radical 9. */
    public static final ChineseCharacterEntry SU = new ChineseCharacterEntry(
            "俗", toCodepoint("俗"),
            List.of(py(Initial.S, Head.U, Body.U, Tail.NONE, Tone.SECOND)),
            9, 9,
            new LeftRight(DAN_REN_PANG, SingularZi.plain("谷")),
            new PhonoSemantic(DAN_REN_PANG, SingularZi.plain("谷"))
    );

    /** 清 (qīng) — clear. LeftRight: 氵(left, semantic) + 青(right, phonetic). Kangxi radical 85. */
    public static final ChineseCharacterEntry QING_CLEAR = new ChineseCharacterEntry(
            "清", toCodepoint("清"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.FIRST)),
            11, 85,
            new LeftRight(SAN_DIAN_SHUI, Materials.QING),
            new PhonoSemantic(SAN_DIAN_SHUI, Materials.QING)
    );

    /** 请 (qǐng) — invite/please. LeftRight: 讠(left, semantic) + 青(right, phonetic). Kangxi radical 149. */
    public static final ChineseCharacterEntry QING_INVITE = new ChineseCharacterEntry(
            "请", toCodepoint("请"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.THIRD)),
            10, 149,
            new LeftRight(YAN_ZI_PANG, Materials.QING),
            new PhonoSemantic(YAN_ZI_PANG, Materials.QING)
    );

    /** 情 (qíng) — emotion/feeling. LeftRight: 忄(left, semantic) + 青(right, phonetic). Kangxi radical 61. */
    public static final ChineseCharacterEntry QING_EMOTION = new ChineseCharacterEntry(
            "情", toCodepoint("情"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            11, 61,
            new LeftRight(SHU_XIN_PANG, Materials.QING),
            new PhonoSemantic(SHU_XIN_PANG, Materials.QING)
    );

    /** 晴 (qíng) — sunny/clear weather. LeftRight: 日(left, semantic) + 青(right, phonetic). Kangxi radical 72. */
    public static final ChineseCharacterEntry QING_SUNNY = new ChineseCharacterEntry(
            "晴", toCodepoint("晴"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            12, 72,
            new LeftRight(HintedZi.RI, Materials.QING),
            new PhonoSemantic(HintedZi.RI, Materials.QING)
    );

    /** 雅 (yǎ) — elegant/refined. LeftRight: 牙(left, phonetic) + 佳(right, semantic). Kangxi radical 172. */
    public static final ChineseCharacterEntry YA = new ChineseCharacterEntry(
            "雅", toCodepoint("雅"),
            List.of(py(Initial.ZERO, Head.I, Body.A, Tail.NONE, Tone.THIRD)),
            12, 172,
            new LeftRight(SingularZi.plain("牙"), new LeftRight(DAN_REN_PANG, SingularZi.plain("圭"))),
            new PhonoSemantic(SingularZi.plain("佳"), SingularZi.plain("牙"))
    );

    // ── TopBottom / Phono-Semantic ──────────────────────────────────────

    /** 字 (zì) — character/word. TopBottom: 宀(top, semantic) + 子(bottom, phonetic). Kangxi radical 39. */
    public static final ChineseCharacterEntry ZI = new ChineseCharacterEntry(
            "字", toCodepoint("字"),
            List.of(py(Initial.Z, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH)),
            6, 39,
            new TopBottom(BAO_GAI_TOU, PeopleAndRoles.ZI),
            new PhonoSemantic(BAO_GAI_TOU, PeopleAndRoles.ZI)
    );

    /** 花 (huā) — flower. TopBottom: 艹(top, semantic) + 化(bottom, phonetic). Kangxi radical 140. */
    public static final ChineseCharacterEntry HUA = new ChineseCharacterEntry(
            "花", toCodepoint("花"),
            List.of(py(Initial.H, Head.U, Body.A, Tail.NONE, Tone.FIRST)),
            7, 140,
            new TopBottom(CAO_ZI_TOU, SingularZi.plain("化")),
            new PhonoSemantic(CAO_ZI_TOU, SingularZi.plain("化"))
    );

    // ── FullEnclosure / Phono-Semantic ──────────────────────────────────

    /** 国 (guó) — country. FullEnclosure: 囗(outer, semantic) + 玉(inner, phonetic). Kangxi radical 31. */
    public static final ChineseCharacterEntry GUO = new ChineseCharacterEntry(
            "国", toCodepoint("国"),
            List.of(py(Initial.G, Head.U, Body.O, Tail.NONE, Tone.SECOND)),
            8, 31,
            new FullEnclosure(GUO_ZI_KUANG, Materials.YU_JADE),
            new PhonoSemantic(GUO_ZI_KUANG, Materials.YU_JADE)
    );

    // ── Mosaic / Compound Indicative ────────────────────────────────────

    /** 品 (pǐn) — product/taste. Mosaic: 口 × 3. Kangxi radical 30. */
    public static final ChineseCharacterEntry PIN = new ChineseCharacterEntry(
            "品", toCodepoint("品"),
            List.of(py(Initial.P, Head.OPEN, Body.I, Tail.N, Tone.THIRD)),
            9, 30,
            new Mosaic(HintedZi.KOU),
            new CompoundIndicative("口(mouth) × 3 → many mouths → tasting, judging quality")
    );

    /** 森 (sēn) — dense forest. Mosaic: 木 × 3. Kangxi radical 75. */
    public static final ChineseCharacterEntry SEN = new ChineseCharacterEntry(
            "森", toCodepoint("森"),
            List.of(py(Initial.S, Head.OPEN, Body.E, Tail.N, Tone.FIRST)),
            12, 75,
            new Mosaic(WoodFamily.MU),
            new CompoundIndicative("木(tree) × 3 → dense forest")
    );

    // ── SemiEnclosureBottomLeft / Phono-Semantic ─────────────────────

    /** 遨 (áo) — roam/travel. SemiEnclosureBottomLeft: 辶(wrapper, semantic) + 敖(content, phonetic). Kangxi radical 162. */
    public static final ChineseCharacterEntry AO = new ChineseCharacterEntry(
            "遨", toCodepoint("遨"),
            List.of(py(Initial.ZERO, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.SECOND)),
            13, 162,
            new SemiEnclosureBottomLeft(ZOU_ZHI_DI, SingularZi.plain("敖")),
            new PhonoSemantic(ZOU_ZHI_DI, SingularZi.plain("敖"))
    );

    // ── SemiEnclosureBottomLeft / Compound Indicative (recursive) ─────

    /**
     * 𰻝 (biáng) — the notoriously complex character from biángbiáng noodles (𰻝𰻝面).
     */
    public static final ChineseCharacterEntry BIANG = new ChineseCharacterEntry(
            "𰻝", toCodepoint("𰻝"),
            List.of(py(Initial.B, Head.I, Body.A, Tail.NG, Tone.SECOND)),
            58, 162,
            new SemiEnclosureBottomLeft(
                    ZOU_ZHI_DI,                                 // 辶 — walk radical
                    new TopMiddleBottom(                         // ── Level 1 ──
                            Structures.XUE,                        // cave / roof
                            new LeftMiddleRight(
                                HintedZi.YUE,                     // ── Level 2 ──
                                new TopBottom(                     // center column
                                        new LeftMiddleRight(SingularZi.plain("幺"), ActionsAndStates.YAN_SPEECH, SingularZi.plain("幺")),
                                        new LeftMiddleRight(SpaceAndDirection.CHANG, Animals.MA, SpaceAndDirection.CHANG)
                                ),
                                LI_DAO_PANG
                            ),
                            SingularZi.plain("心")                 // heart
                    )
            ),
            new CompoundIndicative(
                    "folk-coined character for the sound biáng — "
                    + "the slapping noise of noodle dough pulled and struck against a board"
            )
    );

    // ── All examples ────────────────────────────────────────────────────

    /** All example characters in a single list for easy iteration. */
    public static final List<ChineseCharacterEntry> ALL = List.of(
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
            // FullEnclosure / Phono-Semantic
            GUO,
            // Mosaic / Compound Indicative
            PIN, SEN,
            // SemiEnclosureBottomLeft / Phono-Semantic
            AO,
            // SemiEnclosureBottomLeft / Compound Indicative (recursive)
            BIANG
    );
}
