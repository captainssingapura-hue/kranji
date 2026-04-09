package kranji.demos;

import kranji.classification.CharacterComposition.*;
import kranji.classification.EtymologicalCategory.*;
import kranji.component.Component;
import kranji.component.Component.Zi;
import kranji.component.Parts;
import kranji.entry.ChineseCharacterEntry;
import kranji.pinyin.*;

import java.util.List;

import static kranji.component.Parts.*;
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
            new Singular(),
            new Pictograph()
    );

    /** 山 (shān) — mountain. Singular pictograph. Kangxi radical 46. */
    public static final ChineseCharacterEntry SHAN = new ChineseCharacterEntry(
            "山", toCodepoint("山"),
            List.of(py(Initial.SH, Head.OPEN, Body.A, Tail.N, Tone.FIRST)),
            3, 46,
            new Singular(),
            new Pictograph()
    );

    /** 日 (rì) — sun/day. Singular pictograph. Kangxi radical 72. */
    public static final ChineseCharacterEntry RI = new ChineseCharacterEntry(
            "日", toCodepoint("日"),
            List.of(py(Initial.R, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH)),
            4, 72,
            new Singular(),
            new Pictograph()
    );

    /** 月 (yuè) — moon/month. Singular pictograph. Kangxi radical 74. */
    public static final ChineseCharacterEntry YUE = new ChineseCharacterEntry(
            "月", toCodepoint("月"),
            List.of(py(Initial.ZERO, Head.V, Body.E_CARON, Tail.NONE, Tone.FOURTH)),
            4, 74,
            new Singular(),
            new Pictograph()
    );

    /** 水 (shuǐ) — water. Singular pictograph. Kangxi radical 85. */
    public static final ChineseCharacterEntry SHUI = new ChineseCharacterEntry(
            "水", toCodepoint("水"),
            List.of(py(Initial.SH, Head.U, Body.E, Tail.VOWEL_I, Tone.THIRD)),
            4, 85,
            new Singular(),
            new Pictograph()
    );

    /** 木 (mù) — tree/wood. Singular pictograph. Kangxi radical 75. */
    public static final ChineseCharacterEntry MU = new ChineseCharacterEntry(
            "木", toCodepoint("木"),
            List.of(py(Initial.M, Head.U, Body.U, Tail.NONE, Tone.FOURTH)),
            4, 75,
            new Singular(),
            new Pictograph()
    );

    /** 口 (kǒu) — mouth. Singular pictograph. Kangxi radical 30. */
    public static final ChineseCharacterEntry KOU = new ChineseCharacterEntry(
            "口", toCodepoint("口"),
            List.of(py(Initial.K, Head.OPEN, Body.O, Tail.VOWEL_U, Tone.THIRD)),
            3, 30,
            new Singular(),
            new Pictograph()
    );

    /** 火 (huǒ) — fire. Singular pictograph. Kangxi radical 86. */
    public static final ChineseCharacterEntry HUO = new ChineseCharacterEntry(
            "火", toCodepoint("火"),
            List.of(py(Initial.H, Head.U, Body.O, Tail.NONE, Tone.THIRD)),
            4, 86,
            new Singular(),
            new Pictograph()
    );

    // ── Singular / Simple Indicative ────────────────────────────────────

    /** 上 (shàng) — above. Singular simple indicative. Kangxi radical 1. */
    public static final ChineseCharacterEntry SHANG = new ChineseCharacterEntry(
            "上", toCodepoint("上"),
            List.of(py(Initial.SH, Head.OPEN, Body.A, Tail.NG, Tone.FOURTH)),
            3, 1,
            new Singular(),
            new SimpleIndicative("stroke above the baseline marks 'above'")
    );

    /** 下 (xià) — below. Singular simple indicative. Kangxi radical 1. */
    public static final ChineseCharacterEntry XIA = new ChineseCharacterEntry(
            "下", toCodepoint("下"),
            List.of(py(Initial.X, Head.I, Body.A, Tail.NONE, Tone.FOURTH)),
            3, 1,
            new Singular(),
            new SimpleIndicative("stroke below the baseline marks 'below'")
    );

    // ── Singular / Pictograph (additional) ───────────────────────────────

    /** 已 (yǐ) — already/stop. Singular. Kangxi radical 49. */
    public static final ChineseCharacterEntry YI_ALREADY = new ChineseCharacterEntry(
            "已", toCodepoint("已"),
            List.of(py(Initial.ZERO, Head.OPEN, Body.I, Tail.NONE, Tone.THIRD)),
            3, 49,
            new Singular(),
            new Pictograph()
    );

    /** 己 (jǐ) — self. Singular. Kangxi radical 49. */
    public static final ChineseCharacterEntry JI_SELF = new ChineseCharacterEntry(
            "己", toCodepoint("己"),
            List.of(py(Initial.J, Head.OPEN, Body.I, Tail.NONE, Tone.THIRD)),
            3, 49,
            new Singular(),
            new Pictograph()
    );

    // ── LeftRight / Compound Indicative ─────────────────────────────────

    /** 明 (míng) — bright. LeftRight: 日(left) + 月(right). Kangxi radical 72. */
    public static final ChineseCharacterEntry MING = new ChineseCharacterEntry(
            "明", toCodepoint("明"),
            List.of(py(Initial.M, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            8, 72,
            new LeftRight(new Zi("日"), new Zi("月")),
            new CompoundIndicative("日(sun) + 月(moon) → bright")
    );

    /** 休 (xiū) — rest. LeftRight: 亻(left) + 木(right). Kangxi radical 9. */
    public static final ChineseCharacterEntry XIU = new ChineseCharacterEntry(
            "休", toCodepoint("休"),
            List.of(py(Initial.X, Head.I, Body.O, Tail.VOWEL_U, Tone.FIRST)),
            6, 9,
            new LeftRight(DAN_REN_PANG, new Zi("木")),
            new CompoundIndicative("亻(person) + 木(tree) → person leaning on tree → rest")
    );

    /** 孔 (kǒng) — hole/Confucius. LeftRight: 子(left) + 乚(right). Kangxi radical 39. */
    public static final ChineseCharacterEntry KONG = new ChineseCharacterEntry(
            "孔", toCodepoint("孔"),
            List.of(py(Initial.K, Head.OPEN, Body.O, Tail.NG, Tone.THIRD)),
            4, 39,
            new LeftRight(new Zi("子"), new Zi("乚")),
            new CompoundIndicative("子(child) + 乚(opening) → hole")
    );

    /** 林 (lín) — forest. LeftRight: 木(left) + 木(right). Kangxi radical 75. */
    public static final ChineseCharacterEntry LIN = new ChineseCharacterEntry(
            "林", toCodepoint("林"),
            List.of(py(Initial.L, Head.OPEN, Body.I, Tail.N, Tone.SECOND)),
            8, 75,
            new LeftRight(new Zi("木"), new Zi("木")),
            new CompoundIndicative("木(tree) + 木(tree) → forest")
    );

    // ── LeftRight / Phono-Semantic ──────────────────────────────────────

    /** 清 (qīng) — clear. LeftRight: 氵(left, semantic) + 青(right, phonetic). Kangxi radical 85. */
    public static final ChineseCharacterEntry QING_CLEAR = new ChineseCharacterEntry(
            "清", toCodepoint("清"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.FIRST)),
            11, 85,
            new LeftRight(SAN_DIAN_SHUI, new Zi("青")),
            new PhonoSemantic(SAN_DIAN_SHUI, new Zi("青"))
    );

    /** 请 (qǐng) — invite/please. LeftRight: 讠(left, semantic) + 青(right, phonetic). Kangxi radical 149. */
    public static final ChineseCharacterEntry QING_INVITE = new ChineseCharacterEntry(
            "请", toCodepoint("请"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.THIRD)),
            10, 149,
            new LeftRight(YAN_ZI_PANG, new Zi("青")),
            new PhonoSemantic(YAN_ZI_PANG, new Zi("青"))
    );

    /** 情 (qíng) — emotion/feeling. LeftRight: 忄(left, semantic) + 青(right, phonetic). Kangxi radical 61. */
    public static final ChineseCharacterEntry QING_EMOTION = new ChineseCharacterEntry(
            "情", toCodepoint("情"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            11, 61,
            new LeftRight(SHU_XIN_PANG, new Zi("青")),
            new PhonoSemantic(SHU_XIN_PANG, new Zi("青"))
    );

    /** 晴 (qíng) — sunny/clear weather. LeftRight: 日(left, semantic) + 青(right, phonetic). Kangxi radical 72. */
    public static final ChineseCharacterEntry QING_SUNNY = new ChineseCharacterEntry(
            "晴", toCodepoint("晴"),
            List.of(py(Initial.Q, Head.OPEN, Body.I, Tail.NG, Tone.SECOND)),
            12, 72,
            new LeftRight(new Zi("日"), new Zi("青")),
            new PhonoSemantic(new Zi("日"), new Zi("青"))
    );

    // ── TopBottom / Phono-Semantic ──────────────────────────────────────

    /** 字 (zì) — character/word. TopBottom: 宀(top, semantic) + 子(bottom, phonetic). Kangxi radical 39. */
    public static final ChineseCharacterEntry ZI = new ChineseCharacterEntry(
            "字", toCodepoint("字"),
            List.of(py(Initial.Z, Head.OPEN, Body.NULL, Tail.NONE, Tone.FOURTH)),
            6, 39,
            new TopBottom(BAO_GAI_TOU, new Zi("子")),
            new PhonoSemantic(BAO_GAI_TOU, new Zi("子"))
    );

    /** 花 (huā) — flower. TopBottom: 艹(top, semantic) + 化(bottom, phonetic). Kangxi radical 140. */
    public static final ChineseCharacterEntry HUA = new ChineseCharacterEntry(
            "花", toCodepoint("花"),
            List.of(py(Initial.H, Head.U, Body.A, Tail.NONE, Tone.FIRST)),
            7, 140,
            new TopBottom(CAO_ZI_TOU, new Zi("化")),
            new PhonoSemantic(CAO_ZI_TOU, new Zi("化"))
    );

    // ── FullEnclosure / Phono-Semantic ──────────────────────────────────

    /** 国 (guó) — country. FullEnclosure: 囗(outer, semantic) + 玉(inner, phonetic). Kangxi radical 31. */
    public static final ChineseCharacterEntry GUO = new ChineseCharacterEntry(
            "国", toCodepoint("国"),
            List.of(py(Initial.G, Head.U, Body.O, Tail.NONE, Tone.SECOND)),
            8, 31,
            new FullEnclosure(GUO_ZI_KUANG, new Zi("玉")),
            new PhonoSemantic(GUO_ZI_KUANG, new Zi("玉"))
    );

    // ── Mosaic / Compound Indicative ────────────────────────────────────

    /** 品 (pǐn) — product/taste. Mosaic: 口 × 3. Kangxi radical 30. */
    public static final ChineseCharacterEntry PIN = new ChineseCharacterEntry(
            "品", toCodepoint("品"),
            List.of(py(Initial.P, Head.OPEN, Body.I, Tail.N, Tone.THIRD)),
            9, 30,
            new Mosaic(new Zi("口")),
            new CompoundIndicative("口(mouth) × 3 → many mouths → tasting, judging quality")
    );

    /** 森 (sēn) — dense forest. Mosaic: 木 × 3. Kangxi radical 75. */
    public static final ChineseCharacterEntry SEN = new ChineseCharacterEntry(
            "森", toCodepoint("森"),
            List.of(py(Initial.S, Head.OPEN, Body.E, Tail.N, Tone.FIRST)),
            12, 75,
            new Mosaic(new Zi("木")),
            new CompoundIndicative("木(tree) × 3 → dense forest")
    );

    // ── SemiEnclosureBottomLeft / Phono-Semantic ─────────────────────

    /** 遨 (áo) — roam/travel. SemiEnclosureBottomLeft: 辶(wrapper, semantic) + 敖(content, phonetic). Kangxi radical 162. */
    public static final ChineseCharacterEntry AO = new ChineseCharacterEntry(
            "遨", toCodepoint("遨"),
            List.of(py(Initial.ZERO, Head.OPEN, Body.A, Tail.VOWEL_U, Tone.SECOND)),
            13, 162,
            new SemiEnclosureBottomLeft(ZOU_ZHI_DI, new Zi("敖")),
            new PhonoSemantic(ZOU_ZHI_DI, new Zi("敖"))
    );

    // ── SemiEnclosureBottomLeft / Compound Indicative (recursive) ─────

    /**
     * 𰻝 (biáng) — the notoriously complex character from biángbiáng noodles (𰻝𰻝面).
     * Added to Unicode in CJK Unified Ideographs Extension G.
     *
     * <p>Recursive decomposition (4 levels deep):</p>
     * <pre>
     *   Level 0  SemiEnclosureBottomLeft — 辶 wraps everything
     *   Level 1  TopMiddleBottom — 穴 / middle-band / 心
     *   Level 2  LeftMiddleRight — (月+長) / (言+幺幺) / (刂+長)
     *   Level 3  TopBottom / LeftRight within each column
     * </pre>
     *
     * <p>Traditional mnemonic (民谣):
     * 一点飞上天，黄河两边弯，八字大张口，言字往里走，
     * 左一扭右一扭，东长西长，心字底，月字旁，
     * 挂麻糖，推了车车走咸阳。</p>
     *
     * <p>Kangxi radical 162 (辶/辵). 58 strokes.</p>
     */
    public static final ChineseCharacterEntry BIANG = new ChineseCharacterEntry(
            "𰻝", toCodepoint("𰻝"),
            List.of(py(Initial.B, Head.I, Body.A, Tail.NG, Tone.SECOND)),
            58, 162,
            new SemiEnclosureBottomLeft(
                    ZOU_ZHI_DI,                                 // 辶 — walk radical
                    new TopMiddleBottom(                         // ── Level 1 ──
                            new Zi("穴"),                        // cave / roof
                            new LeftMiddleRight(
                                new Zi("月"),// ── Level 2 ──
                                new TopBottom(               // left column
                                        new LeftMiddleRight(new Zi("幺"), new Zi("言"), new Zi("幺")),        // moon / flesh
                                        new LeftMiddleRight(new Zi("长"), new Zi("马"), new Zi("长"))         // long
                                ),
                                LI_DAO_PANG
                            ),
                            new Zi("心")                         // heart
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
            // LeftRight / Phono-Semantic (青 family)
            QING_CLEAR, QING_INVITE, QING_EMOTION, QING_SUNNY,
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
