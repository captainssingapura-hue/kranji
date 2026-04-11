package kranji.singular.materials;

import kranji.component.SingularZi;

public final class Materials {
    private Materials() {}

    public record Yi_Clothes() implements SingularZi {
        @Override public String glyph()   { return "衣"; }
        @Override public String meaning() { return "clothes"; }
        @Override public String pinyin()  { return "yī"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Yi_Clothes YI_CLOTHES = new Yi_Clothes();

    public record Jin_Towel() implements SingularZi {
        @Override public String glyph()   { return "巾"; }
        @Override public String meaning() { return "towel, cloth"; }
        @Override public String pinyin()  { return "jīn"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Jin_Towel JIN_TOWEL = new Jin_Towel();

    public record Ge() implements SingularZi {
        @Override public String glyph()   { return "革"; }
        @Override public String meaning() { return "leather"; }
        @Override public String pinyin()  { return "gé"; }
        @Override public int strokes()    { return 9; }
    }
    public static final Ge GE = new Ge();

    public record Yu_Jade() implements SingularZi {
        @Override public String glyph()   { return "玉"; }
        @Override public String meaning() { return "jade"; }
        @Override public String pinyin()  { return "yù"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Yu_Jade YU_JADE = new Yu_Jade();

    public record Zhu_Pearl() implements SingularZi {
        @Override public String glyph()   { return "珠"; }
        @Override public String meaning() { return "pearl"; }
        @Override public String pinyin()  { return "zhū"; }
        @Override public int strokes()    { return 10; }
    }
    public static final Zhu_Pearl ZHU_PEARL = new Zhu_Pearl();

    public record Se() implements SingularZi {
        @Override public String glyph()   { return "色"; }
        @Override public String meaning() { return "color"; }
        @Override public String pinyin()  { return "sè"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Se SE = new Se();

    public record Bai_White() implements SingularZi {
        @Override public String glyph()   { return "白"; }
        @Override public String meaning() { return "white"; }
        @Override public String pinyin()  { return "bái"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Bai_White BAI_WHITE = new Bai_White();

    public record Hei() implements SingularZi {
        @Override public String glyph()   { return "黑"; }
        @Override public String meaning() { return "black"; }
        @Override public String pinyin()  { return "hēi"; }
        @Override public int strokes()    { return 12; }
    }
    public static final Hei HEI = new Hei();

    public record Hong() implements SingularZi {
        @Override public String glyph()   { return "红"; }
        @Override public String meaning() { return "red"; }
        @Override public String pinyin()  { return "hóng"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Hong HONG = new Hong();

    public record Huang() implements SingularZi {
        @Override public String glyph()   { return "黄"; }
        @Override public String meaning() { return "yellow"; }
        @Override public String pinyin()  { return "huáng"; }
        @Override public int strokes()    { return 11; }
    }
    public static final Huang HUANG = new Huang();

    public record Qing() implements SingularZi {
        @Override public String glyph()   { return "青"; }
        @Override public String meaning() { return "blue-green"; }
        @Override public String pinyin()  { return "qīng"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Qing QING = new Qing();

    public record Dan() implements SingularZi {
        @Override public String glyph()   { return "丹"; }
        @Override public String meaning() { return "vermillion, cinnabar"; }
        @Override public String pinyin()  { return "dān"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Dan DAN = new Dan();

    public record Tie() implements SingularZi {
        @Override public String glyph()   { return "铁"; }
        @Override public String meaning() { return "iron"; }
        @Override public String pinyin()  { return "tiě"; }
        @Override public int strokes()    { return 10; }
    }
    public static final Tie TIE = new Tie();

    public record Tong() implements SingularZi {
        @Override public String glyph()   { return "铜"; }
        @Override public String meaning() { return "copper"; }
        @Override public String pinyin()  { return "tóng"; }
        @Override public int strokes()    { return 11; }
    }
    public static final Tong TONG = new Tong();

    public record Zhi_Paper() implements SingularZi {
        @Override public String glyph()   { return "纸"; }
        @Override public String meaning() { return "paper"; }
        @Override public String pinyin()  { return "zhǐ"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Zhi_Paper ZHI_PAPER = new Zhi_Paper();
}
