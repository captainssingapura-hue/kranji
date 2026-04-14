package kranji.demos;

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

import static kranji.zi.Composable.ofBlock;

import java.util.List;

import static kranji.component.basic.BasicComponents.*;
import static kranji.zi.Composable.ofBlock;
import static kranji.zi.ZiEntry.toCodepoint;

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

    /** Wrap a BlockStructure as Composable for composition slots. */
    private static Composable c(BlockStructure bs) {
        return ofBlock(bs);
    }

    // ── Singular / Pictograph ───────────────────────────────────────────

    /** 人 (rén) — person. Singular pictograph. Kangxi radical 9. */
    public static final Zi REN = new ZiEntry(
            "人", toCodepoint("人"),
            List.of(py(Initial.R, Head.OPEN, Body.E, Tail.N, Tone.SECOND)),
            2, 9, "",
            SingularBlock.plain("人"),
            new Pictograph()
    );

    /** 山 (shān) — mountain. Singular pictograph. Kangxi radical 46. */
    public static final Zi SHAN = new ZiEntry(
            "山", toCodepoint("山"),
            List.of(py(Initial.SH, Head.OPEN, Body.A, Tail.N, Tone.FIRST)),
            3, 46, "",
            SingularBlock.plain("山"),
            new Pictograph()
    );

    /** 日 (rì) — sun/day. Singular pictograph. Kangxi radical 72. */
    public static final Zi RI = new ZiEntry(
            "日", toCodepoint("日"),
            List.of(py(Initial.R, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH)),
            4, 72, "",
            NatureElements.RI,
            new Pictograph()
    );

    /** 月 (yuè) — moon/month. Singular pictograph. Kangxi radical 74. */
    public static final Zi YUE = new ZiEntry(
            "月", toCodepoint("月"),
            List.of(py(Initial.ZERO, Head.V, Body.E_CARON, Tail.NONE, Tone.FOURTH)),
            4, 74, "",
            NatureElements.YUE,
            new Pictograph()
    );

    /** 水 (shuǐ) — water. Singular pictograph. Kangxi radical 85. */
    public static final Zi SHUI = new ZiEntry(
            "水", toCodepoint("水"),
            List.of(py(Initial.SH, Head.U, Body.E, Tail.VOWEL_I, Tone.THIRD)),
            4, 85, "",
            SingularBlock.plain("水"),
            new Pictograph()
    );

    /** 木 (mù) — tree/wood. Singular pictograph. Kangxi radical 75. */
    public static final Zi MU = new ZiEntry(
            "木", toCodepoint("木"),
            List.of(py(Initial.M, Head.U, Body.U, Tail.NONE, Tone.FOURTH)),
            4, 75, "",
            WoodFamily.MU,
            new Pictograph()
    );

    /** 口 (kǒu) — mouth. Singular pictograph. Kangxi radical 30. */
    public static final Zi KOU = new ZiEntry(
            "口", toCodepoint("口"),
            List.of(py(Initial.K, Head.OPEN, Body.O, Tail.VOWEL_U, Tone.THIRD)),
            3, 30, "",
            BodyParts.KOU,
            new Pictograph()
    );

    /** 火 (huǒ) — fire. Singular pictograph. Kangxi radical 86. */
    public static final Zi HUO = new ZiEntry(
            "火", toCodepoint("火"),
            List.of(py(Initial.H, Head.U, Body.O, Tail.NONE, Tone.THIRD)),
            4, 86, "",
            SingularBlock.plain("火"),
            new Pictograph()
    );

    // ── Singular / Simple Indicative ────────────────────────────────────

    /** 上 (shàng) — above. Singular simple indicative. Kangxi radical 1. */
    public static final Zi SHANG = new ZiEntry(
            "上", toCodepoint("上"),
            List.of(py(Initial.SH, Head.OPEN, Body.A, Tail.NG, Tone.FOURTH)),
            3, 1, "",
            SingularBlock.plain("上"),
            new SimpleIndicative("stroke above the baseline marks 'above'")
    );

    /** 下 (xià) — below. Singular simple indicative. Kangxi radical 1. */
    public static final Zi XIA = new ZiEntry(
            "下", toCodepoint("下"),
            List.of(py(Initial.X, Head.I, Body.A, Tail.NONE, Tone.FOURTH)),
            3, 1, "",
            SingularBlock.plain("下"),
            new SimpleIndicative("stroke below the baseline marks 'below'")
    );

    // ── Singular / Pictograph (additional) ───────────────────────────────

    /** 已 (yǐ) — already/stop. Singular. Kangxi radical 49. */
    public static final Zi YI_ALREADY = new ZiEntry(
            "已", toCodepoint("已"),
            List.of(py(Initial.ZERO, Head.OPEN, Body.I, Tail.NONE, Tone.THIRD)),
            3, 49, "",
            SingularBlock.plain("已"),
            new Pictograph()
    );

    /** 己 (jǐ) — self. Singular. Kangxi radical 49. */
    public static final Zi JI_SELF = new ZiEntry(
            "己", toCodepoint("己"),
            List.of(py(Initial.J, Head.OPEN, Body.I, Tail.NONE, Tone.THIRD)),
            3, 49, "",
            SingularBlock.plain("己"),
            new Pictograph()
    );

    // ── LeftRight / Compound Indicative ─────────────────────────────────

    /** 明 (míng) — bright. LeftRight: 日(left) + 月(right). Kangxi radical 72. */
    public static final Zi MING = new ZiEntry(
            "明", toCodepoint("明"),
            List.of(py(Initial.M, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            8, 72, "",
            new LeftRight(c(NatureElements.RI), c(NatureElements.YUE)),
            new CompoundIndicative("日(sun) + 月(moon) → bright")
    );

    /** 休 (xiū) — rest. LeftRight: 亻(left) + 木(right). Kangxi radical 9. */
    public static final Zi XIU = new ZiEntry(
            "休", toCodepoint("休"),
            List.of(py(Initial.X, Head.I, Body.O, Tail.VOWEL_U, Tone.FIRST)),
            6, 9, "",
            new LeftRight(c(DAN_REN_PANG), c(WoodFamily.MU)),
            new CompoundIndicative("亻(person) + 木(tree) → person leaning on tree → rest")
    );

    /** 孔 (kǒng) — hole/Confucius. LeftRight: 子(left) + 乚(right). Kangxi radical 39. */
    public static final Zi KONG = new ZiEntry(
            "孔", toCodepoint("孔"),
            List.of(py(Initial.K, Head.OPEN, Body.O, Tail.NG, Tone.THIRD)),
            4, 39, "",
            new LeftRight(c(PeopleAndRoles.ZI), c(SingularBlock.plain("乚"))),
            new CompoundIndicative("子(child) + 乚(opening) → hole")
    );

    /** 林 (lín) — forest. LeftRight: 木(left) + 木(right). Kangxi radical 75. */
    public static final Zi LIN = new ZiEntry(
            "林", toCodepoint("林"),
            List.of(py(Initial.L, Head.OPEN, Body.I, Tail.N, Tone.SECOND)),
            8, 75, "",
            new LeftRight(c(WoodFamily.MU), c(WoodFamily.MU)),
            new CompoundIndicative("木(tree) + 木(tree) → forest")
    );

    // ── LeftRight / Phono-Semantic ──────────────────────────────────────

    /** 俗 (sú) — custom/vulgar. LeftRight: 亻(left, semantic) + 谷(right, phonetic). Kangxi radical 9. */
    public static final Zi SU = new ZiEntry(
            "俗", toCodepoint("俗"),
            List.of(py(Initial.S, Head.U, Body.U, Tail.NONE, Tone.SECOND)),
            9, 9, "",
            new LeftRight(c(DAN_REN_PANG), c(SingularBlock.plain("谷"))),
            new PhonoSemantic(ofBlock(DAN_REN_PANG), ofBlock(SingularBlock.plain("谷")))
    );

    /** 清 (qīng) — clear. LeftRight: 氵(left, semantic) + 青(right, phonetic). Kangxi radical 85. */
    public static final Zi QING_CLEAR = new ZiEntry(
            "清", toCodepoint("清"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.FIRST)),
            11, 85, "",
            new LeftRight(c(SAN_DIAN_SHUI), c(Materials.QING)),
            new PhonoSemantic(ofBlock(SAN_DIAN_SHUI), ofBlock(Materials.QING))
    );

    /** 请 (qǐng) — invite/please. LeftRight: 讠(left, semantic) + 青(right, phonetic). Kangxi radical 149. */
    public static final Zi QING_INVITE = new ZiEntry(
            "请", toCodepoint("请"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.THIRD)),
            10, 149, "",
            new LeftRight(c(YAN_ZI_PANG), c(Materials.QING)),
            new PhonoSemantic(ofBlock(YAN_ZI_PANG), ofBlock(Materials.QING))
    );

    /** 情 (qíng) — emotion/feeling. LeftRight: 忄(left, semantic) + 青(right, phonetic). Kangxi radical 61. */
    public static final Zi QING_EMOTION = new ZiEntry(
            "情", toCodepoint("情"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            11, 61, "",
            new LeftRight(c(SHU_XIN_PANG), c(Materials.QING)),
            new PhonoSemantic(ofBlock(SHU_XIN_PANG), ofBlock(Materials.QING))
    );

    /** 晴 (qíng) — sunny/clear weather. LeftRight: 日(left, semantic) + 青(right, phonetic). Kangxi radical 72. */
    public static final Zi QING_SUNNY = new ZiEntry(
            "晴", toCodepoint("晴"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            12, 72, "",
            new LeftRight(c(NatureElements.RI), c(Materials.QING)),
            new PhonoSemantic(ofBlock(NatureElements.RI), ofBlock(Materials.QING))
    );

    /** 雅 (yǎ) — elegant/refined. LeftRight: 牙(left, phonetic) + 佳(right, semantic). Kangxi radical 172. */
    public static final Zi YA = new ZiEntry(
            "雅", toCodepoint("雅"),
            List.of(py(Initial.ZERO, Head.I, Body.A, Tail.NONE, Tone.THIRD)),
            12, 172, "",
            new LeftRight(c(SingularBlock.plain("牙")),
                    ofBlock(new LeftRight(c(DAN_REN_PANG), c(SingularBlock.plain("圭"))))),
            new PhonoSemantic(ofBlock(SingularBlock.plain("佳")), ofBlock(SingularBlock.plain("牙")))
    );

    // ── TopBottom / Phono-Semantic ──────────────────────────────────────

    /** 字 (zì) — character/word. TopBottom: 宀(top, semantic) + 子(bottom, phonetic). Kangxi radical 39. */
    public static final Zi ZI = new ZiEntry(
            "字", toCodepoint("字"),
            List.of(py(Initial.Z, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH)),
            6, 39, "",
            new TopBottom(c(BAO_GAI_TOU), c(PeopleAndRoles.ZI)),
            new PhonoSemantic(ofBlock(BAO_GAI_TOU), ofBlock(PeopleAndRoles.ZI))
    );

    /** 花 (huā) — flower. TopBottom: 艹(top, semantic) + 化(bottom, phonetic). Kangxi radical 140. */
    public static final Zi HUA = new ZiEntry(
            "花", toCodepoint("花"),
            List.of(py(Initial.H, Head.U, Body.A, Tail.NONE, Tone.FIRST)),
            7, 140, "",
            new TopBottom(c(CAO_ZI_TOU), c(SingularBlock.plain("化"))),
            new PhonoSemantic(ofBlock(CAO_ZI_TOU), ofBlock(SingularBlock.plain("化")))
    );

    // ── TopMiddleBottom / Compound Indicative ────────────────────────────

    /** 器 (qì) — vessel/utensil. TMB: double-口(top) + 犬(middle) + double-口(bottom). Kangxi radical 30. */
    public static final Zi QI_VESSEL = new ZiEntry(
            "器", toCodepoint("器"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NONE, Tone.FOURTH)),
            16, 30, "",
            new TopMiddleBottom(
                    ofBlock(new LeftRight(c(SingularBlock.plain("口")), c(SingularBlock.plain("口")))),
                    c(SingularBlock.plain("犬")),
                    ofBlock(new LeftRight(c(SingularBlock.plain("口")), c(SingularBlock.plain("口"))))
            ),
            new CompoundIndicative("四口(four mouths/openings) + 犬(dog as guard) → vessel, instrument")
    );

    // ── FullEnclosure / Phono-Semantic ──────────────────────────────────

    /** 国 (guó) — country. FullEnclosure: 囗(outer, semantic) + 玉(inner, phonetic). Kangxi radical 31. */
    public static final Zi GUO = new ZiEntry(
            "国", toCodepoint("国"),
            List.of(py(Initial.G, Head.U, Body.O, Tail.NONE, Tone.SECOND)),
            8, 31, "",
            new FullEnclosure(c(GUO_ZI_KUANG), c(Materials.YU_JADE)),
            new PhonoSemantic(ofBlock(GUO_ZI_KUANG), ofBlock(Materials.YU_JADE))
    );

    // ── TopBottom (was Mosaic) / Compound Indicative ────────────────────

    /** 品 (pǐn) — product/taste. TopBottom: 口 + double-口. Kangxi radical 30. */
    public static final Zi PIN = new ZiEntry(
            "品", toCodepoint("品"),
            List.of(py(Initial.P, Head.OPEN, Body.I, Tail.N, Tone.THIRD)),
            9, 30, "",
            new TopBottom(c(BodyParts.KOU), ofBlock(new LeftRight(c(SingularBlock.plain("口")), c(SingularBlock.plain("口"))))),
            new CompoundIndicative("口(mouth) × 3 → many mouths → tasting, judging quality")
    );

    /** 森 (sēn) — dense forest. TopBottom: 木 + double-木. Kangxi radical 75. */
    public static final Zi SEN = new ZiEntry(
            "森", toCodepoint("森"),
            List.of(py(Initial.S, Head.OPEN, Body.E, Tail.N, Tone.FIRST)),
            12, 75, "",
            new TopBottom(c(WoodFamily.MU), ofBlock(new LeftRight(c(SingularBlock.plain("木")), c(SingularBlock.plain("木"))))),
            new CompoundIndicative("木(tree) × 3 → dense forest")
    );

    // ── SemiEnclosureBottomLeft / Phono-Semantic ─────────────────────

    /** 遨 (áo) — roam/travel. SemiEnclosureBottomLeft: 辶(wrapper, semantic) + 敖(content, phonetic). Kangxi radical 162. */
    public static final Zi AO = new ZiEntry(
            "遨", toCodepoint("遨"),
            List.of(py(Initial.ZERO, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.SECOND)),
            13, 162, "",
            new SemiEnclosureBottomLeft(c(ZOU_ZHI_DI), c(SingularBlock.plain("敖"))),
            new PhonoSemantic(ofBlock(ZOU_ZHI_DI), ofBlock(SingularBlock.plain("敖")))
    );

    // ── SemiEnclosureBottomLeft / Compound Indicative (recursive) ─────

    /**
     * 𰻝 (biáng) — the notoriously complex character from biángbiáng noodles (𰻝𰻝面).
     */
    public static final Zi BIANG = new ZiEntry(
            "𰻝", toCodepoint("𰻝"),
            List.of(py(Initial.B, Head.I, Body.A, Tail.NG, Tone.SECOND)),
            58, 162, "",
            new SemiEnclosureBottomLeft(
                    c(ZOU_ZHI_DI),                                 // 辶 — walk radical
                    ofBlock(new TopMiddleBottom(                    // ── Level 1 ──
                            c(Structures.XUE),                        // cave / roof
                            ofBlock(new LeftMiddleRight(
                                c(NatureElements.YUE),                     // ── Level 2 ──
                                ofBlock(new TopBottom(                     // center column
                                        ofBlock(new LeftMiddleRight(c(SingularBlock.plain("幺")), c(ActionsAndStates.YAN_SPEECH), c(SingularBlock.plain("幺")))),
                                        ofBlock(new LeftMiddleRight(c(SpaceAndDirection.CHANG), c(Animals.MA), c(SpaceAndDirection.CHANG)))
                                )),
                                c(LI_DAO_PANG)
                            )),
                            c(SingularBlock.plain("心"))                 // heart
                    ))
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
