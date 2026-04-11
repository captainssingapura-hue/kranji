package kranji.singular.body;

import kranji.component.SingularZi;

public final class BodyParts {
    private BodyParts() {}

    // 口 (Kou) and 目 (Mu) are defined in HintedZi (kranji-core) — the single source of truth.

    public record Er() implements SingularZi {
        @Override public String glyph()   { return "\u8033"; }
        @Override public String meaning() { return "ear"; }
        @Override public String pinyin()  { return "\u011Br"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Er ER = new Er();

    public record Shou() implements SingularZi {
        @Override public String glyph()   { return "\u624B"; }
        @Override public String meaning() { return "hand"; }
        @Override public String pinyin()  { return "sh\u01D2u"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Shou SHOU = new Shou();

    public record Zu() implements SingularZi {
        @Override public String glyph()   { return "\u8DB3"; }
        @Override public String meaning() { return "foot"; }
        @Override public String pinyin()  { return "z\u00FA"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Zu ZU = new Zu();

    public record Xin() implements SingularZi {
        @Override public String glyph()   { return "\u5FC3"; }
        @Override public String meaning() { return "heart"; }
        @Override public String pinyin()  { return "x\u012Bn"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Xin XIN = new Xin();

    public record Tou() implements SingularZi {
        @Override public String glyph()   { return "\u5934"; }
        @Override public String meaning() { return "head"; }
        @Override public String pinyin()  { return "t\u00F3u"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Tou TOU = new Tou();

    public record Mian() implements SingularZi {
        @Override public String glyph()   { return "\u9762"; }
        @Override public String meaning() { return "face"; }
        @Override public String pinyin()  { return "mi\u00E0n"; }
        @Override public int strokes()    { return 9; }
    }
    public static final Mian MIAN = new Mian();

    public record Shen() implements SingularZi {
        @Override public String glyph()   { return "\u8EAB"; }
        @Override public String meaning() { return "body"; }
        @Override public String pinyin()  { return "sh\u0113n"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Shen SHEN = new Shen();

    public record Shou_Head() implements SingularZi {
        @Override public String glyph()   { return "\u9996"; }
        @Override public String meaning() { return "head (formal)"; }
        @Override public String pinyin()  { return "sh\u01D2u"; }
        @Override public int strokes()    { return 9; }
    }
    public static final Shou_Head SHOU_HEAD = new Shou_Head();

    public record Ya() implements SingularZi {
        @Override public String glyph()   { return "\u7259"; }
        @Override public String meaning() { return "tooth"; }
        @Override public String pinyin()  { return "y\u00E1"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Ya YA = new Ya();

    public record She() implements SingularZi {
        @Override public String glyph()   { return "\u820C"; }
        @Override public String meaning() { return "tongue"; }
        @Override public String pinyin()  { return "sh\u00E9"; }
        @Override public int strokes()    { return 6; }
    }
    public static final She SHE = new She();

    public record Pi() implements SingularZi {
        @Override public String glyph()   { return "\u76AE"; }
        @Override public String meaning() { return "skin"; }
        @Override public String pinyin()  { return "p\u00ED"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Pi PI = new Pi();

    public record Mao() implements SingularZi {
        @Override public String glyph()   { return "\u6BDB"; }
        @Override public String meaning() { return "hair, fur"; }
        @Override public String pinyin()  { return "m\u00E1o"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Mao MAO = new Mao();

    public record Gu() implements SingularZi {
        @Override public String glyph()   { return "\u9AA8"; }
        @Override public String meaning() { return "bone"; }
        @Override public String pinyin()  { return "g\u01D4"; }
        @Override public int strokes()    { return 9; }
    }
    public static final Gu GU = new Gu();

    public record Rou() implements SingularZi {
        @Override public String glyph()   { return "\u8089"; }
        @Override public String meaning() { return "meat, flesh"; }
        @Override public String pinyin()  { return "r\u00F2u"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Rou ROU = new Rou();

    public record Xue() implements SingularZi {
        @Override public String glyph()   { return "\u8840"; }
        @Override public String meaning() { return "blood"; }
        @Override public String pinyin()  { return "xu\u00E8"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Xue XUE = new Xue();

    public record Bi() implements SingularZi {
        @Override public String glyph()   { return "\u9F3B"; }
        @Override public String meaning() { return "nose"; }
        @Override public String pinyin()  { return "b\u00ED"; }
        @Override public int strokes()    { return 14; }
    }
    public static final Bi BI = new Bi();

    public record Wei() implements SingularZi {
        @Override public String glyph()   { return "\u80C3"; }
        @Override public String meaning() { return "stomach"; }
        @Override public String pinyin()  { return "w\u00E8i"; }
        @Override public int strokes()    { return 9; }
    }
    public static final Wei WEI = new Wei();
}
