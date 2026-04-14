package kranji.singular.materials;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

public final class Materials {
    private Materials() {}

    public record Yi_Clothes() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "衣"; }
        @Override public String meaning() { return "clothes"; }
        @Override public String pinyinText()  { return "yī"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Clothes YI_CLOTHES = new Yi_Clothes();

    public record Jin_Towel() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "巾"; }
        @Override public String meaning() { return "towel, cloth"; }
        @Override public String pinyinText()  { return "jīn"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jin_Towel JIN_TOWEL = new Jin_Towel();

    public record Ge() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "革"; }
        @Override public String meaning() { return "leather"; }
        @Override public String pinyinText()  { return "gé"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ge GE = new Ge();

    public record Yu_Jade() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "玉"; }
        @Override public String meaning() { return "jade"; }
        @Override public String pinyinText()  { return "yù"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu_Jade YU_JADE = new Yu_Jade();

    public record Se() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "色"; }
        @Override public String meaning() { return "color"; }
        @Override public String pinyinText()  { return "sè"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Se SE = new Se();

    public record Bai_White() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "白"; }
        @Override public String meaning() { return "white"; }
        @Override public String pinyinText()  { return "bái"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bai_White BAI_WHITE = new Bai_White();

    public record Huang() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "黄"; }
        @Override public String meaning() { return "yellow"; }
        @Override public String pinyinText()  { return "huáng"; }
        @Override public int strokes()    { return 11; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Huang HUANG = new Huang();

    public record Qing() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "青"; }
        @Override public String meaning() { return "blue-green"; }
        @Override public String pinyinText()  { return "qīng"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qing QING = new Qing();

    public record Dan() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "丹"; }
        @Override public String meaning() { return "vermillion, cinnabar"; }
        @Override public String pinyinText()  { return "dān"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Dan DAN = new Dan();

    public record Gan_Sweet() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "甘"; }
        @Override public String meaning() { return "sweet"; }
        @Override public String pinyinText()  { return "gān"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gan_Sweet GAN_SWEET = new Gan_Sweet();

    public record Lu_Brine() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "卤"; }
        @Override public String meaning() { return "brine/halogen"; }
        @Override public String pinyinText()  { return "lǔ"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Lu_Brine LU_BRINE = new Lu_Brine();

    public record Wei_Leather() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "韦"; }
        @Override public String meaning() { return "leather/tanned hide"; }
        @Override public String pinyinText()  { return "wéi"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wei_Leather WEI_LEATHER = new Wei_Leather();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            YI_CLOTHES, JIN_TOWEL, GE, YU_JADE, SE,
            BAI_WHITE, HUANG, QING, DAN,
            GAN_SWEET, LU_BRINE, WEI_LEATHER);
}
