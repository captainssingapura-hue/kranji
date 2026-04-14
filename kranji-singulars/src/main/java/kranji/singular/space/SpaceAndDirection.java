package kranji.singular.space;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

public final class SpaceAndDirection {
    private SpaceAndDirection() {}

    public record Shang() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "上"; }
        @Override public String meaning() { return "above, up"; }
        @Override public String pinyin()  { return "shàng"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shang SHANG = new Shang();

    public record Xia() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "下"; }
        @Override public String meaning() { return "below, down"; }
        @Override public String pinyin()  { return "xià"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xia XIA = new Xia();

    public record Zhong() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "中"; }
        @Override public String meaning() { return "middle, center"; }
        @Override public String pinyin()  { return "zhōng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhong ZHONG = new Zhong();

    public record Li_Inside() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "里"; }
        @Override public String meaning() { return "inside, mile"; }
        @Override public String pinyin()  { return "lǐ"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Li_Inside LI_INSIDE = new Li_Inside();

    public record Fang() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "方"; }
        @Override public String meaning() { return "direction, square"; }
        @Override public String pinyin()  { return "fāng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fang FANG = new Fang();

    public record Dong() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "东"; }
        @Override public String meaning() { return "east"; }
        @Override public String pinyin()  { return "dōng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Dong DONG = new Dong();

    public record Xi() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "西"; }
        @Override public String meaning() { return "west"; }
        @Override public String pinyin()  { return "xī"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xi XI = new Xi();

    public record Nan() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "南"; }
        @Override public String meaning() { return "south"; }
        @Override public String pinyin()  { return "nán"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Nan NAN = new Nan();

    public record Bei_North() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "北"; }
        @Override public String meaning() { return "north"; }
        @Override public String pinyin()  { return "běi"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bei_North BEI_NORTH = new Bei_North();

    public record Qian_Front() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "前"; }
        @Override public String meaning() { return "front, before"; }
        @Override public String pinyin()  { return "qián"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qian_Front QIAN_FRONT = new Qian_Front();

    public record Nei() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "内"; }
        @Override public String meaning() { return "inside"; }
        @Override public String pinyin()  { return "nèi"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Nei NEI = new Nei();

    public record Chang() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "长"; }
        @Override public String meaning() { return "long"; }
        @Override public String pinyin()  { return "cháng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chang CHANG = new Chang();

    public record Yu_At() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "于"; }
        @Override public String meaning() { return "at/in"; }
        @Override public String pinyin()  { return "yú"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu_At YU_AT = new Yu_At();

    public record Yang_Center() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "央"; }
        @Override public String meaning() { return "center/middle"; }
        @Override public String pinyin()  { return "yāng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yang_Center YANG_CENTER = new Yang_Center();

    public record Xiang_Village() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "乡"; }
        @Override public String meaning() { return "village/countryside"; }
        @Override public String pinyin()  { return "xiāng"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xiang_Village XIANG_VILLAGE = new Xiang_Village();

    public record Jia_First() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "甲"; }
        @Override public String meaning() { return "first/armor"; }
        @Override public String pinyin()  { return "jiǎ"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jia_First JIA_FIRST = new Jia_First();

    public record Shen_Extend() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "申"; }
        @Override public String meaning() { return "extend/state"; }
        @Override public String pinyin()  { return "shēn"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shen_Extend SHEN_EXTEND = new Shen_Extend();

    public record You_From() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "由"; }
        @Override public String meaning() { return "from/cause"; }
        @Override public String pinyin()  { return "yóu"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final You_From YOU_FROM = new You_From();

    public record Fu_Begin() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "甫"; }
        @Override public String meaning() { return "just/begin"; }
        @Override public String pinyin()  { return "fǔ"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fu_Begin FU_BEGIN = new Fu_Begin();

    public record Pan() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "爿"; }
        @Override public String meaning() { return "split wood radical"; }
        @Override public String pinyin()  { return "pán"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Pan PAN = new Pan();

    public record Ye_Page() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "页"; }
        @Override public String meaning() { return "page/head"; }
        @Override public String pinyin()  { return "yè"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ye_Page YE_PAGE = new Ye_Page();

    public record Jiu_Long() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "久"; }
        @Override public String meaning() { return "long time"; }
        @Override public String pinyin()  { return "jiǔ"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jiu_Long JIU_LONG = new Jiu_Long();

    public record Wu_Noon() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "午"; }
        @Override public String meaning() { return "noon/7th Earthly Branch"; }
        @Override public String pinyin()  { return "wǔ"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wu_Noon WU_NOON = new Wu_Noon();

    public record Nian_Year() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "年"; }
        @Override public String meaning() { return "year"; }
        @Override public String pinyin()  { return "nián"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Nian_Year NIAN_YEAR = new Nian_Year();

    public record Gan_Dry() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "干"; }
        @Override public String meaning() { return "dry/trunk/stem"; }
        @Override public String pinyin()  { return "gān"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gan_Dry GAN_DRY = new Gan_Dry();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            SHANG, XIA, ZHONG, LI_INSIDE, FANG, DONG, XI, NAN, BEI_NORTH,
            QIAN_FRONT, NEI, CHANG, YU_AT, YANG_CENTER, XIANG_VILLAGE,
            JIA_FIRST, SHEN_EXTEND, YOU_FROM, FU_BEGIN, PAN, YE_PAGE,
            JIU_LONG, WU_NOON, NIAN_YEAR, GAN_DRY);
}
