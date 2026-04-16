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

    /** 圭 — jade tablet. */
    public record Gui_Tablet() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u572D"; }
        @Override public String meaning() { return "jade tablet"; }
        @Override public String pinyinText()  { return "gu\u012B"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gui_Tablet GUI_TABLET = new Gui_Tablet();

    /** 币 — currency, money. */
    public record Bi_Currency() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5E01"; }
        @Override public String meaning() { return "currency, money"; }
        @Override public String pinyinText()  { return "b\u00EC"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bi_Currency BI_CURRENCY = new Bi_Currency();

    /** 布 — cloth, spread. */
    public record Bu_Cloth() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5E03"; }
        @Override public String meaning() { return "cloth, spread"; }
        @Override public String pinyinText()  { return "b\u00F9"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bu_Cloth BU_CLOTH = new Bu_Cloth();

    /** 丝 — silk, thread. */
    public record Si_Silk() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E1D"; }
        @Override public String meaning() { return "silk, thread"; }
        @Override public String pinyinText()  { return "s\u012B"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Si_Silk SI_SILK = new Si_Silk();

    /** \u8D64 — red/bare. */
    public record Chi_Red() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u8D64"; }
        @Override public String meaning() { return "red, bare"; }
        @Override public String pinyinText()  { return "ch\u00EC"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chi_Red CHI_RED = new Chi_Red();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            YI_CLOTHES, JIN_TOWEL, GE, YU_JADE, SE,
            BAI_WHITE, HUANG, QING, DAN,
            GAN_SWEET, LU_BRINE, WEI_LEATHER,
            GUI_TABLET, BI_CURRENCY,
            BU_CLOTH, SI_SILK,
            CHI_RED);
}
