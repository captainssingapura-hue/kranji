package kranji.singular.materials;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

public final class Materials {
    private Materials() {}

    public record Yi_Clothes() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "衣"; }
        @Override public String meaning() { return "clothes"; }
        @Override public String pinyin()  { return "yī"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Clothes YI_CLOTHES = new Yi_Clothes();

    public record Jin_Towel() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "巾"; }
        @Override public String meaning() { return "towel, cloth"; }
        @Override public String pinyin()  { return "jīn"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jin_Towel JIN_TOWEL = new Jin_Towel();

    public record Ge() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "革"; }
        @Override public String meaning() { return "leather"; }
        @Override public String pinyin()  { return "gé"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ge GE = new Ge();

    public record Yu_Jade() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "玉"; }
        @Override public String meaning() { return "jade"; }
        @Override public String pinyin()  { return "yù"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu_Jade YU_JADE = new Yu_Jade();

    public record Zhu_Pearl() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "珠"; }
        @Override public String meaning() { return "pearl"; }
        @Override public String pinyin()  { return "zhū"; }
        @Override public int strokes()    { return 10; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhu_Pearl ZHU_PEARL = new Zhu_Pearl();

    public record Se() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "色"; }
        @Override public String meaning() { return "color"; }
        @Override public String pinyin()  { return "sè"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Se SE = new Se();

    public record Bai_White() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "白"; }
        @Override public String meaning() { return "white"; }
        @Override public String pinyin()  { return "bái"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bai_White BAI_WHITE = new Bai_White();

    public record Hei() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "黑"; }
        @Override public String meaning() { return "black"; }
        @Override public String pinyin()  { return "hēi"; }
        @Override public int strokes()    { return 12; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Hei HEI = new Hei();

    public record Hong() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "红"; }
        @Override public String meaning() { return "red"; }
        @Override public String pinyin()  { return "hóng"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Hong HONG = new Hong();

    public record Huang() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "黄"; }
        @Override public String meaning() { return "yellow"; }
        @Override public String pinyin()  { return "huáng"; }
        @Override public int strokes()    { return 11; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Huang HUANG = new Huang();

    public record Qing() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "青"; }
        @Override public String meaning() { return "blue-green"; }
        @Override public String pinyin()  { return "qīng"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qing QING = new Qing();

    public record Dan() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "丹"; }
        @Override public String meaning() { return "vermillion, cinnabar"; }
        @Override public String pinyin()  { return "dān"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Dan DAN = new Dan();

    public record Tie() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "铁"; }
        @Override public String meaning() { return "iron"; }
        @Override public String pinyin()  { return "tiě"; }
        @Override public int strokes()    { return 10; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Tie TIE = new Tie();

    public record Tong() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "铜"; }
        @Override public String meaning() { return "copper"; }
        @Override public String pinyin()  { return "tóng"; }
        @Override public int strokes()    { return 11; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Tong TONG = new Tong();

    public record Zhi_Paper() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "纸"; }
        @Override public String meaning() { return "paper"; }
        @Override public String pinyin()  { return "zhǐ"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhi_Paper ZHI_PAPER = new Zhi_Paper();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            YI_CLOTHES, JIN_TOWEL, GE, YU_JADE, ZHU_PEARL, SE,
            BAI_WHITE, HEI, HONG, HUANG, QING, DAN, TIE, TONG, ZHI_PAPER);
}
