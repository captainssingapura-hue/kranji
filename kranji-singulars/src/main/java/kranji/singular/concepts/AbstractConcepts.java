package kranji.singular.concepts;

import kranji.component.SingularZi;

public final class AbstractConcepts {
    private AbstractConcepts() {}

    public record Bu() implements SingularZi {
        @Override public String glyph()   { return "\u4E0D"; }
        @Override public String meaning() { return "not"; }
        @Override public String pinyin()  { return "b\u00F9"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Bu BU = new Bu();

    public record Zheng() implements SingularZi {
        @Override public String glyph()   { return "\u6B63"; }
        @Override public String meaning() { return "correct, straight"; }
        @Override public String pinyin()  { return "zh\u00E8ng"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Zheng ZHENG = new Zheng();

    public record Zhi_Straight() implements SingularZi {
        @Override public String glyph()   { return "\u76F4"; }
        @Override public String meaning() { return "straight"; }
        @Override public String pinyin()  { return "zh\u00ED"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Zhi_Straight ZHI_STRAIGHT = new Zhi_Straight();

    public record Ping() implements SingularZi {
        @Override public String glyph()   { return "\u5E73"; }
        @Override public String meaning() { return "flat, level"; }
        @Override public String pinyin()  { return "p\u00EDng"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Ping PING = new Ping();

    public record An() implements SingularZi {
        @Override public String glyph()   { return "\u5B89"; }
        @Override public String meaning() { return "peace, safe"; }
        @Override public String pinyin()  { return "\u0101n"; }
        @Override public int strokes()    { return 6; }
    }
    public static final An AN = new An();

    public record Shan_Good() implements SingularZi {
        @Override public String glyph()   { return "\u5584"; }
        @Override public String meaning() { return "good"; }
        @Override public String pinyin()  { return "sh\u00E0n"; }
        @Override public int strokes()    { return 12; }
    }
    public static final Shan_Good SHAN_GOOD = new Shan_Good();

    public record Mei() implements SingularZi {
        @Override public String glyph()   { return "\u7F8E"; }
        @Override public String meaning() { return "beautiful"; }
        @Override public String pinyin()  { return "m\u011Bi"; }
        @Override public int strokes()    { return 9; }
    }
    public static final Mei MEI = new Mei();

    public record Zhen_True() implements SingularZi {
        @Override public String glyph()   { return "\u771F"; }
        @Override public String meaning() { return "true"; }
        @Override public String pinyin()  { return "zh\u0113n"; }
        @Override public int strokes()    { return 10; }
    }
    public static final Zhen_True ZHEN_TRUE = new Zhen_True();

    public record Yi_Meaning() implements SingularZi {
        @Override public String glyph()   { return "\u4E49"; }
        @Override public String meaning() { return "justice, meaning"; }
        @Override public String pinyin()  { return "y\u00EC"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Yi_Meaning YI_MEANING = new Yi_Meaning();

    public record Li_Ritual() implements SingularZi {
        @Override public String glyph()   { return "\u793C"; }
        @Override public String meaning() { return "ritual, courtesy"; }
        @Override public String pinyin()  { return "l\u01D0"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Li_Ritual LI_RITUAL = new Li_Ritual();

    public record De() implements SingularZi {
        @Override public String glyph()   { return "\u5FB7"; }
        @Override public String meaning() { return "virtue"; }
        @Override public String pinyin()  { return "d\u00E9"; }
        @Override public int strokes()    { return 15; }
    }
    public static final De DE = new De();

    public record Dao_Way() implements SingularZi {
        @Override public String glyph()   { return "\u9053"; }
        @Override public String meaning() { return "way, path"; }
        @Override public String pinyin()  { return "d\u00E0o"; }
        @Override public int strokes()    { return 12; }
    }
    public static final Dao_Way DAO_WAY = new Dao_Way();

    public record Xin_Faith() implements SingularZi {
        @Override public String glyph()   { return "\u4FE1"; }
        @Override public String meaning() { return "trust, faith"; }
        @Override public String pinyin()  { return "x\u00ECn"; }
        @Override public int strokes()    { return 9; }
    }
    public static final Xin_Faith XIN_FAITH = new Xin_Faith();

    public record Shi_Thing() implements SingularZi {
        @Override public String glyph()   { return "\u4E8B"; }
        @Override public String meaning() { return "matter, affair"; }
        @Override public String pinyin()  { return "sh\u00EC"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Shi_Thing SHI_THING = new Shi_Thing();

    public record Qing_Feeling() implements SingularZi {
        @Override public String glyph()   { return "\u60C5"; }
        @Override public String meaning() { return "feeling, emotion"; }
        @Override public String pinyin()  { return "q\u00EDng"; }
        @Override public int strokes()    { return 11; }
    }
    public static final Qing_Feeling QING_FEELING = new Qing_Feeling();

    public record Yi_Art() implements SingularZi {
        @Override public String glyph()   { return "\u827A"; }
        @Override public String meaning() { return "art, skill"; }
        @Override public String pinyin()  { return "y\u00EC"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Yi_Art YI_ART = new Yi_Art();

    public record Xue_Study() implements SingularZi {
        @Override public String glyph()   { return "\u5B66"; }
        @Override public String meaning() { return "study, learn"; }
        @Override public String pinyin()  { return "xu\u00E9"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Xue_Study XUE_STUDY = new Xue_Study();

    public record Jing_Classic() implements SingularZi {
        @Override public String glyph()   { return "\u7ECF"; }
        @Override public String meaning() { return "classic, scripture"; }
        @Override public String pinyin()  { return "j\u012Bng"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Jing_Classic JING_CLASSIC = new Jing_Classic();

    public record Li_Reason() implements SingularZi {
        @Override public String glyph()   { return "\u7406"; }
        @Override public String meaning() { return "reason, logic"; }
        @Override public String pinyin()  { return "l\u01D0"; }
        @Override public int strokes()    { return 11; }
    }
    public static final Li_Reason LI_REASON = new Li_Reason();

    public record Fa() implements SingularZi {
        @Override public String glyph()   { return "\u6CD5"; }
        @Override public String meaning() { return "law, method"; }
        @Override public String pinyin()  { return "f\u01CE"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Fa FA = new Fa();

    public record Ye_Also() implements SingularZi {
        @Override public String glyph()   { return "\u4E5F"; }
        @Override public String meaning() { return "also"; }
        @Override public String pinyin()  { return "y\u011B"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Ye_Also YE_ALSO = new Ye_Also();

    public record Nai() implements SingularZi {
        @Override public String glyph()   { return "\u4E43"; }
        @Override public String meaning() { return "therefore"; }
        @Override public String pinyin()  { return "n\u01CEi"; }
        @Override public int strokes()    { return 2; }
    }
    public static final Nai NAI = new Nai();

    public record Qi_Its() implements SingularZi {
        @Override public String glyph()   { return "\u5176"; }
        @Override public String meaning() { return "its, that"; }
        @Override public String pinyin()  { return "q\u00ED"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Qi_Its QI_ITS = new Qi_Its();
}
