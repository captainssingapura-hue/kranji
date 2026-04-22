package kranji.singular.concepts;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

public final class AbstractConcepts {
    private AbstractConcepts() {}

    public record Bu() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E0D"; }
        @Override public String meaning() { return "not"; }
        @Override public String pinyinText()  { return "b\u00F9"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bu BU = new Bu();

    public record Zheng() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u6B63"; }
        @Override public String meaning() { return "correct, straight"; }
        @Override public String pinyinText()  { return "zh\u00E8ng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zheng ZHENG = new Zheng();

    public record Zhi_Straight() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u76F4"; }
        @Override public String meaning() { return "straight"; }
        @Override public String pinyinText()  { return "zh\u00ED"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhi_Straight ZHI_STRAIGHT = new Zhi_Straight();

    public record Ping() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5E73"; }
        @Override public String meaning() { return "flat, level"; }
        @Override public String pinyinText()  { return "p\u00EDng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ping PING = new Ping();

    public record Yi_Meaning() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E49"; }
        @Override public String meaning() { return "justice, meaning"; }
        @Override public String pinyinText()  { return "y\u00EC"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Meaning YI_MEANING = new Yi_Meaning();

    public record Shi_Thing() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E8B"; }
        @Override public String meaning() { return "matter, affair"; }
        @Override public String pinyinText()  { return "sh\u00EC"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Thing SHI_THING = new Shi_Thing();

    public record Xue_Study() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5B66"; }
        @Override public String meaning() { return "study, learn"; }
        @Override public String pinyinText()  { return "xu\u00E9"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xue_Study XUE_STUDY = new Xue_Study();

    public record Ye_Also() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E5F"; }
        @Override public String meaning() { return "also"; }
        @Override public String pinyinText()  { return "y\u011B"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ye_Also YE_ALSO = new Ye_Also();

    public record Nai() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E43"; }
        @Override public String meaning() { return "therefore"; }
        @Override public String pinyinText()  { return "n\u01CEi"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Nai NAI = new Nai();

    public record Qi_Its() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5176"; }
        @Override public String meaning() { return "its, that"; }
        @Override public String pinyinText()  { return "q\u00ED"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qi_Its QI_ITS = new Qi_Its();

    // --- New records ---

    public record Chou() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E11"; }
        @Override public String meaning() { return "ugly, clown"; }
        @Override public String pinyinText()  { return "ch\u01D2u"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chou CHOU = new Chou();

    public record Qie() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E14"; }
        @Override public String meaning() { return "moreover, and"; }
        @Override public String pinyinText()  { return "qi\u011B"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qie QIE = new Qie();

    public record Shi_World() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E16"; }
        @Override public String meaning() { return "world, generation"; }
        @Override public String pinyinText()  { return "sh\u00EC"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_World SHI_WORLD = new Shi_World();

    public record Ye_Business() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E1A"; }
        @Override public String meaning() { return "business, industry"; }
        @Override public String pinyinText()  { return "y\u00E8"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ye_Business YE_BUSINESS = new Ye_Business();

    public record Zhu_Lord() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E3B"; }
        @Override public String meaning() { return "lord, main"; }
        @Override public String pinyinText()  { return "zh\u01D4"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhu_Lord ZHU_LORD = new Zhu_Lord();

    public record Yi_Also() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4EA6"; }
        @Override public String meaning() { return "also, too"; }
        @Override public String pinyinText()  { return "y\u00EC"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Also YI_ALSO = new Yi_Also();

    public record Zhi_Of() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E4B"; }
        @Override public String meaning() { return "of, go to"; }
        @Override public String pinyinText()  { return "zh\u012B"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhi_Of ZHI_OF = new Zhi_Of();

    public record Hu_Question() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E4E"; }
        @Override public String meaning() { return "question particle"; }
        @Override public String pinyinText()  { return "h\u016B"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Hu_Question HU_QUESTION = new Hu_Question();

    public record Wu_None() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u65E0"; }
        @Override public String meaning() { return "none, without"; }
        @Override public String pinyinText()  { return "w\u00FA"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wu_None WU_NONE = new Wu_None();

    public record Yue_Say() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u66F0"; }
        @Override public String meaning() { return "say, speak"; }
        @Override public String pinyinText()  { return "yu\u0113"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yue_Say YUE_SAY = new Yue_Say();

    public record Hu_Mutual() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E92"; }
        @Override public String meaning() { return "mutual, each other"; }
        @Override public String pinyinText()  { return "h\u00F9"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Hu_Mutual HU_MUTUAL = new Hu_Mutual();

    public record Ya_Second() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E9A"; }
        @Override public String meaning() { return "second, Asia"; }
        @Override public String pinyinText()  { return "y\u00E0"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ya_Second YA_SECOND = new Ya_Second();

    public record Ge_Measure() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E2A"; }
        @Override public String meaning() { return "measure word"; }
        @Override public String pinyinText()  { return "g\u00E8"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ge_Measure GE_MEASURE = new Ge_Measure();

    public record Yan_Strict() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E25"; }
        @Override public String meaning() { return "strict, severe"; }
        @Override public String pinyinText()  { return "y\u00E1n"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yan_Strict YAN_STRICT = new Yan_Strict();

    public record Li_Subordinate() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u96B6"; }
        @Override public String meaning() { return "subordinate, slave"; }
        @Override public String pinyinText()  { return "l\u00EC"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Li_Subordinate LI_SUBORDINATE = new Li_Subordinate();

    public record Yi_Second() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E59"; }
        @Override public String meaning() { return "second Heavenly Stem"; }
        @Override public String pinyinText()  { return "y\u01D0"; }
        @Override public int strokes()    { return 1; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Second YI_SECOND = new Yi_Second();

    public record Bing_Third() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E19"; }
        @Override public String meaning() { return "third Heavenly Stem"; }
        @Override public String pinyinText()  { return "b\u01D0ng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bing_Third BING_THIRD = new Bing_Third();

    public record Hai() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4EA5"; }
        @Override public String meaning() { return "12th Earthly Branch"; }
        @Override public String pinyinText()  { return "h\u00E0i"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Hai HAI = new Hai();

    public record Me() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E48"; }
        @Override public String meaning() { return "interrogative particle"; }
        @Override public String pinyinText()  { return "me"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Me ME = new Me();

    public record Guai() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u592C"; }
        @Override public String meaning() { return "resolute, decisive"; }
        @Override public String pinyinText()  { return "gu\u00E0i"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Guai GUAI = new Guai();

    public record Ji_Self() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5DF1"; }
        @Override public String meaning() { return "self, oneself"; }
        @Override public String pinyinText()  { return "j\u01D0"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ji_Self JI_SELF = new Ji_Self();

    public record Yi_Already() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5DF2"; }
        @Override public String meaning() { return "already, cease"; }
        @Override public String pinyinText()  { return "y\u01D0"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Already YI_ALREADY = new Yi_Already();

    public record Si_Sixth() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5DF3"; }
        @Override public String meaning() { return "sixth Earthly Branch"; }
        @Override public String pinyinText()  { return "s\u00EC"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Si_Sixth SI_SIXTH = new Si_Sixth();

    public record Bi_Must() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5FC5"; }
        @Override public String meaning() { return "must, surely"; }
        @Override public String pinyinText()  { return "b\u00EC"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bi_Must BI_MUST = new Bi_Must();

    /** 佳 — fine, good, beautiful. */
    public record Jia_Fine() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4F73"; }
        @Override public String meaning() { return "fine, good"; }
        @Override public String pinyinText()  { return "ji\u0101"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jia_Fine JIA_FINE = new Jia_Fine();

    /** 亏 — lose, deficit. */
    public record Kui() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E8F"; }
        @Override public String meaning() { return "lose, deficit"; }
        @Override public String pinyinText()  { return "ku\u012B"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Kui KUI = new Kui();

    /** 介 — between, introduce. */
    public record Jie() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4ECB"; }
        @Override public String meaning() { return "between, introduce"; }
        @Override public String pinyinText()  { return "ji\u00E8"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jie JIE = new Jie();

    /** 今 — today, now. */
    public record Jin() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4ECA"; }
        @Override public String meaning() { return "today, now"; }
        @Override public String pinyinText()  { return "j\u012Bn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jin JIN = new Jin();

    /** 乏 — lack, deficient. */
    public record Fa() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E4F"; }
        @Override public String meaning() { return "lack, deficient"; }
        @Override public String pinyinText()  { return "f\u00E1"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fa FA = new Fa();

    /** 以 — use, with, by means of. */
    public record Yi_Use() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4EE5"; }
        @Override public String meaning() { return "use, with"; }
        @Override public String pinyinText()  { return "y\u01D0"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Use YI_USE = new Yi_Use();

    /** 允 — allow, permit. */
    public record Yun() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5141"; }
        @Override public String meaning() { return "allow, permit"; }
        @Override public String pinyinText()  { return "y\u01D4n"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yun YUN = new Yun();

    /** 幻 — illusion, fantasy. */
    public record Huan() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5E7B"; }
        @Override public String meaning() { return "illusion, fantasy"; }
        @Override public String pinyinText()  { return "hu\u00E0n"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Huan HUAN = new Huan();

    /** 匀 — even, uniform. */
    public record Yun_Even() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5300"; }
        @Override public String meaning() { return "even, uniform"; }
        @Override public String pinyinText()  { return "y\u00FAn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yun_Even YUN_EVEN = new Yun_Even();

    /** 巧 — clever, skillful. */
    public record Qiao() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5DE7"; }
        @Override public String meaning() { return "clever, skillful"; }
        @Override public String pinyinText()  { return "qi\u01CEo"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qiao QIAO = new Qiao();

    /** 号 — number, sign. */
    public record Hao() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u53F7"; }
        @Override public String meaning() { return "number, sign"; }
        @Override public String pinyinText()  { return "h\u00E0o"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Hao HAO = new Hao();

    /** \u8F9B — bitter/laborious. */
    public record Xin_Bitter() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u8F9B"; }
        @Override public String meaning() { return "bitter, laborious"; }
        @Override public String pinyinText()  { return "x\u012Bn"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xin_Bitter XIN_BITTER = new Xin_Bitter();

    /** \u4E3D — beautiful. */
    public record Li_Beautiful() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E3D"; }
        @Override public String meaning() { return "beautiful"; }
        @Override public String pinyinText()  { return "l\u00EC"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Li_Beautiful LI_BEAUTIFUL = new Li_Beautiful();

    /** \u58F0 — sound/voice. */
    public record Sheng_Sound() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u58F0"; }
        @Override public String meaning() { return "sound, voice"; }
        @Override public String pinyinText()  { return "sh\u0113ng"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Sheng_Sound SHENG_SOUND = new Sheng_Sound();

    /** \u7CFB — system/tie. */
    public record Xi_System() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u7CFB"; }
        @Override public String meaning() { return "system, tie"; }
        @Override public String pinyinText()  { return "x\u00EC"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xi_System XI_SYSTEM = new Xi_System();

    /** \u5BFF — longevity. */
    public record Shou_Long() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5BFF"; }
        @Override public String meaning() { return "longevity"; }
        @Override public String pinyinText()  { return "sh\u00F2u"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shou_Long SHOU_LONG = new Shou_Long();

    /** \u4F59 — surplus/I. */
    public record Yu_Surplus() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4F59"; }
        @Override public String meaning() { return "surplus, I"; }
        @Override public String pinyinText()  { return "y\u00FA"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu_Surplus YU_SURPLUS = new Yu_Surplus();

    /** \u5E0C — hope/rare. */
    public record Xi_Hope() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5E0C"; }
        @Override public String meaning() { return "hope, rare"; }
        @Override public String pinyinText()  { return "x\u012B"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xi_Hope XI_HOPE = new Xi_Hope();

    /** \u4ED1 — logical order. */
    public record Lun() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4ED1"; }
        @Override public String meaning() { return "logical order"; }
        @Override public String pinyinText()  { return "l\u00FAn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Lun LUN = new Lun();

    /** \u6216 — or, perhaps. */
    public record Huo_Or() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u6216"; }
        @Override public String meaning() { return "or, perhaps"; }
        @Override public String pinyinText()  { return "hu\u00F2"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Huo_Or HUO_OR = new Huo_Or();

    /** \u5C1A — esteem, still. */
    public record Shang() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5C1A"; }
        @Override public String meaning() { return "esteem, still"; }
        @Override public String pinyinText()  { return "sh\u00E0ng"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shang SHANG = new Shang();

    /** \u547D — fate, life, command. */
    public record Ming_Fate() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u547D"; }
        @Override public String meaning() { return "fate, life, command"; }
        @Override public String pinyinText()  { return "m\u00ECng"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ming_Fate MING_FATE = new Ming_Fate();

    /** \u8868 — surface, express. */
    public record Biao() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u8868"; }
        @Override public String meaning() { return "surface, express"; }
        @Override public String pinyinText()  { return "bi\u01CEo"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Biao BIAO = new Biao();

    /** \u5178 — classic, canon. */
    public record Dian() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5178"; }
        @Override public String meaning() { return "classic, canon"; }
        @Override public String pinyinText()  { return "di\u01CEn"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Dian DIAN = new Dian();

    /** \u4E56 — obedient, well-behaved. */
    public record Guai_Good() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E56"; }
        @Override public String meaning() { return "obedient, well-behaved"; }
        @Override public String pinyinText()  { return "gu\u0101i"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Guai_Good GUAI_GOOD = new Guai_Good();

    /** \u5E78 — fortunate, lucky. */
    public record Xing_Fortune() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5E78"; }
        @Override public String meaning() { return "fortunate, lucky"; }
        @Override public String pinyinText()  { return "x\u00ECng"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xing_Fortune XING_FORTUNE = new Xing_Fortune();

    /** \u4E9B — some, a few. */
    public record Xie() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E9B"; }
        @Override public String meaning() { return "some, a few"; }
        @Override public String pinyinText()  { return "xi\u0113"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xie XIE = new Xie();

    /** 古 — ancient, old. */
    public record Gu() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u53E4"; }
        @Override public String meaning() { return "ancient, old"; }
        @Override public String pinyinText()  { return "g\u01D4"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gu GU = new Gu();

    /** 实 — real, solid. */
    public record Shi_Real() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5B9E"; }
        @Override public String meaning() { return "real, solid"; }
        @Override public String pinyinText()  { return "sh\u00ED"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Real SHI_REAL = new Shi_Real();

    /** 定 — decide, fixed. */
    public record Ding_Decide() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5B9A"; }
        @Override public String meaning() { return "decide, fixed"; }
        @Override public String pinyinText()  { return "d\u00ECng"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ding_Decide DING_DECIDE = new Ding_Decide();

    /** 责 — responsibility. */
    public record Ze_Duty() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u8D23"; }
        @Override public String meaning() { return "responsibility, duty"; }
        @Override public String pinyinText()  { return "z\u00E9"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ze_Duty ZE_DUTY = new Ze_Duty();

    /** 质 — quality, nature. */
    public record Zhi_Quality() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u8D28"; }
        @Override public String meaning() { return "quality, nature"; }
        @Override public String pinyinText()  { return "zh\u00EC"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhi_Quality ZHI_QUALITY = new Zhi_Quality();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            BU, ZHENG, ZHI_STRAIGHT, PING, YI_MEANING,
            SHI_THING, XUE_STUDY, YE_ALSO, NAI, QI_ITS,
            CHOU, QIE, SHI_WORLD, YE_BUSINESS, ZHU_LORD, YI_ALSO,
            ZHI_OF, HU_QUESTION, WU_NONE, YUE_SAY, HU_MUTUAL, YA_SECOND,
            GE_MEASURE, YAN_STRICT, LI_SUBORDINATE, YI_SECOND, BING_THIRD,
            HAI, ME, GUAI, JI_SELF, YI_ALREADY, SI_SIXTH, BI_MUST,
            JIA_FINE,
            KUI, JIE, JIN, FA, YI_USE, YUN, HUAN, YUN_EVEN,
            QIAO, HAO,
            XIN_BITTER, LI_BEAUTIFUL, SHENG_SOUND, XI_SYSTEM, SHOU_LONG,
            YU_SURPLUS, XI_HOPE,
            LUN, HUO_OR, SHANG, MING_FATE, BIAO, DIAN,
            GUAI_GOOD, XING_FORTUNE, XIE,
            GU,
            SHI_REAL, DING_DECIDE, ZE_DUTY, ZHI_QUALITY);
}
