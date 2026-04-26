package kranji.singular.unsure;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

/**
 * Provisional singular registrations for phonetic / semantic components
 * that are needed <em>now</em> as building blocks for higher-depth
 * composed-Zi records, but whose own internal structure has not yet
 * been authored.
 *
 * <p>Every record here overrides {@link kranji.zi.SingularZi#unsure()}
 * to return {@code true}. The marker is the <strong>only</strong> thing
 * that distinguishes them from "real" singulars — they participate fully
 * in the singular index, can be referenced by composed records, and
 * appear in {@link kranji.library.BasicSet}.</p>
 *
 * <p>Eventually each entry should either be:</p>
 * <ul>
 *   <li>Promoted to a proper themed family (BodyParts, NatureElements,
 *       …) once analysis confirms it is genuinely atomic, with the
 *       {@code unsure()} override removed; or</li>
 *   <li>Removed from this class and re-introduced as a composed-Zi at
 *       the appropriate depth, with its referencing characters bumped
 *       up one depth level accordingly.</li>
 * </ul>
 *
 * <p>Field naming follows {@code Pinyin_Disambig} where useful, mirroring
 * the convention in other family classes for non-unique pinyin.</p>
 */
public final class UnsureSingulars {
    private UnsureSingulars() {}

    // ── Originally deferred phonetic components ─────────────────────

    public record Yuan_Grasp() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u7230"; }       // 爰
        @Override public String meaning() { return "to grasp; phonetic component"; }
        @Override public String pinyinText()  { return "yu\u00E1n"; }
        @Override public int strokes()    { return 9; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yuan_Grasp YUAN_GRASP = new Yuan_Grasp();

    public record Yao_Lofty() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5C27"; }       // 尧
        @Override public String meaning() { return "lofty; mythical sage-king Yao"; }
        @Override public String pinyinText()  { return "y\u00E1o"; }
        @Override public int strokes()    { return 6; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yao_Lofty YAO_LOFTY = new Yao_Lofty();

    public record Yi_Doubt() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u7591"; }       // 疑
        @Override public String meaning() { return "doubt, suspect"; }
        @Override public String pinyinText()  { return "y\u00ED"; }
        @Override public int strokes()    { return 14; }
        @Override public int radicalNo()  { return 103; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Doubt YI_DOUBT = new Yi_Doubt();

    public record Ni_Inverted() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5C70"; }       // 屰
        @Override public String meaning() { return "inverted figure; phonetic component"; }
        @Override public String pinyinText()  { return "n\u00EC"; }
        @Override public int strokes()    { return 6; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ni_Inverted NI_INVERTED = new Ni_Inverted();

    public record Ji_FoodBowl() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u7680"; }       // 皀
        @Override public String meaning() { return "fragrant grain; pictograph of a food vessel"; }
        @Override public String pinyinText()  { return "j\u00ED"; }
        @Override public int strokes()    { return 7; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ji_FoodBowl JI_FOODBOWL = new Ji_FoodBowl();

    public record Gong_Forearm() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u53B7"; }       // 厷
        @Override public String meaning() { return "forearm; phonetic component"; }
        @Override public String pinyinText()  { return "g\u014Dng"; }
        @Override public int strokes()    { return 5; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gong_Forearm GONG_FOREARM = new Gong_Forearm();

    public record Yi_HuntingNet() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u776A"; }       // 睪
        @Override public String meaning() { return "hunting net; phonetic component"; }
        @Override public String pinyinText()  { return "y\u00EC"; }
        @Override public int strokes()    { return 13; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_HuntingNet YI_HUNTINGNET = new Yi_HuntingNet();

    public record Ji_Sacrifice() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u796D"; }       // 祭
        @Override public String meaning() { return "sacrifice, ritual offering"; }
        @Override public String pinyinText()  { return "j\u00EC"; }
        @Override public int strokes()    { return 11; }
        @Override public int radicalNo()  { return 113; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ji_Sacrifice JI_SACRIFICE = new Ji_Sacrifice();

    public record Lu_Mound() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5774"; }       // 坴
        @Override public String meaning() { return "earthen mound; phonetic component"; }
        @Override public String pinyinText()  { return "l\u00F9"; }
        @Override public int strokes()    { return 8; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Lu_Mound LU_MOUND = new Lu_Mound();

    public record Qi_Even() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u9F50"; }       // 齐
        @Override public String meaning() { return "even, neat, uniform"; }
        @Override public String pinyinText()  { return "q\u00ED"; }
        @Override public int strokes()    { return 6; }
        @Override public int radicalNo()  { return 210; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qi_Even QI_EVEN = new Qi_Even();

    public record Qiao_Tall() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E54"; }       // 乔
        @Override public String meaning() { return "tall; surname Qiao"; }
        @Override public String pinyinText()  { return "qi\u00E1o"; }
        @Override public int strokes()    { return 6; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qiao_Tall QIAO_TALL = new Qiao_Tall();

    public record Qin_Bird() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u79BD"; }       // 禽
        @Override public String meaning() { return "fowl, bird"; }
        @Override public String pinyinText()  { return "q\u00EDn"; }
        @Override public int strokes()    { return 12; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qin_Bird QIN_BIRD = new Qin_Bird();

    // ── High-utility unblockers (pictographs / phonetic stems) ──────

    public record Zhao_Omen() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5146"; }       // 兆
        @Override public String meaning() { return "omen, sign; trillion"; }
        @Override public String pinyinText()  { return "zh\u00E0o"; }
        @Override public int strokes()    { return 6; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhao_Omen ZHAO_OMEN = new Zhao_Omen();

    public record Di_Emperor() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5E1D"; }       // 帝
        @Override public String meaning() { return "emperor, sovereign"; }
        @Override public String pinyinText()  { return "d\u00EC"; }
        @Override public int strokes()    { return 9; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Di_Emperor DI_EMPEROR = new Di_Emperor();

    public record Huang_Sovereign() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u7687"; }       // 皇
        @Override public String meaning() { return "imperial, august, sovereign"; }
        @Override public String pinyinText()  { return "hu\u00E1ng"; }
        @Override public int strokes()    { return 9; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Huang_Sovereign HUANG_SOVEREIGN = new Huang_Sovereign();

    public record Liao_Torch() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5C1E"; }       // 尞
        @Override public String meaning() { return "burning torch; phonetic component"; }
        @Override public String pinyinText()  { return "li\u00E1o"; }
        @Override public int strokes()    { return 12; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Liao_Torch LIAO_TORCH = new Liao_Torch();

    public record Xi_Former() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u6614"; }       // 昔
        @Override public String meaning() { return "former times, ancient"; }
        @Override public String pinyinText()  { return "x\u012B"; }
        @Override public int strokes()    { return 8; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xi_Former XI_FORMER = new Xi_Former();

    public record Xiao_Resemble() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u8096"; }       // 肖
        @Override public String meaning() { return "resemble; small flesh"; }
        @Override public String pinyinText()  { return "xi\u0101o"; }
        @Override public int strokes()    { return 7; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xiao_Resemble XIAO_RESEMBLE = new Xiao_Resemble();

    public record Jiao_Scorched() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u7126"; }       // 焦
        @Override public String meaning() { return "burnt, scorched, anxious"; }
        @Override public String pinyinText()  { return "ji\u0101o"; }
        @Override public int strokes()    { return 12; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jiao_Scorched JIAO_SCORCHED = new Jiao_Scorched();

    public record Kua_Boast() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5938"; }       // 夸
        @Override public String meaning() { return "boast, exaggerate"; }
        @Override public String pinyinText()  { return "ku\u0101"; }
        @Override public int strokes()    { return 6; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Kua_Boast KUA_BOAST = new Kua_Boast();

    public record Pang_Side() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u65C1"; }       // 旁
        @Override public String meaning() { return "side, beside"; }
        @Override public String pinyinText()  { return "p\u00E1ng"; }
        @Override public int strokes()    { return 10; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Pang_Side PANG_SIDE = new Pang_Side();

    public record Fu_FullVessel() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u7550"; }       // 畐
        @Override public String meaning() { return "full vessel; phonetic component"; }
        @Override public String pinyinText()  { return "f\u00FA"; }
        @Override public int strokes()    { return 9; }
        @Override public boolean unsure() { return true; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fu_FullVessel FU_FULLVESSEL = new Fu_FullVessel();

    // ── Public list (for SingularFamilies registration) ─────────────

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            YUAN_GRASP, YAO_LOFTY, YI_DOUBT, NI_INVERTED, JI_FOODBOWL,
            GONG_FOREARM, YI_HUNTINGNET, JI_SACRIFICE, LU_MOUND, QI_EVEN,
            QIAO_TALL, QIN_BIRD, ZHAO_OMEN, DI_EMPEROR, HUANG_SOVEREIGN,
            LIAO_TORCH, XI_FORMER, XIAO_RESEMBLE, JIAO_SCORCHED, KUA_BOAST,
            PANG_SIDE, FU_FULLVESSEL);
}
