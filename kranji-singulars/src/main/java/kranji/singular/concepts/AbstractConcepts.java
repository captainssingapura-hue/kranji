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

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            BU, ZHENG, ZHI_STRAIGHT, PING, YI_MEANING,
            SHI_THING, XUE_STUDY, YE_ALSO, NAI, QI_ITS,
            CHOU, QIE, SHI_WORLD, YE_BUSINESS, ZHU_LORD, YI_ALSO,
            ZHI_OF, HU_QUESTION, WU_NONE, YUE_SAY, HU_MUTUAL, YA_SECOND,
            GE_MEASURE, YAN_STRICT, LI_SUBORDINATE, YI_SECOND, BING_THIRD,
            HAI, ME, GUAI, JI_SELF, YI_ALREADY, SI_SIXTH, BI_MUST);
}
