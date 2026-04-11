package kranji.singular.space;

import kranji.component.SingularZi;

public final class SpaceAndDirection {
    private SpaceAndDirection() {}

    public record Shang() implements SingularZi {
        @Override public String glyph()   { return "上"; }
        @Override public String meaning() { return "above, up"; }
        @Override public String pinyin()  { return "shàng"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Shang SHANG = new Shang();

    public record Xia() implements SingularZi {
        @Override public String glyph()   { return "下"; }
        @Override public String meaning() { return "below, down"; }
        @Override public String pinyin()  { return "xià"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Xia XIA = new Xia();

    public record Zhong() implements SingularZi {
        @Override public String glyph()   { return "中"; }
        @Override public String meaning() { return "middle, center"; }
        @Override public String pinyin()  { return "zhōng"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Zhong ZHONG = new Zhong();

    public record Li_Inside() implements SingularZi {
        @Override public String glyph()   { return "里"; }
        @Override public String meaning() { return "inside, mile"; }
        @Override public String pinyin()  { return "lǐ"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Li_Inside LI_INSIDE = new Li_Inside();

    public record Fang() implements SingularZi {
        @Override public String glyph()   { return "方"; }
        @Override public String meaning() { return "direction, square"; }
        @Override public String pinyin()  { return "fāng"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Fang FANG = new Fang();

    public record Dong() implements SingularZi {
        @Override public String glyph()   { return "东"; }
        @Override public String meaning() { return "east"; }
        @Override public String pinyin()  { return "dōng"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Dong DONG = new Dong();

    public record Xi() implements SingularZi {
        @Override public String glyph()   { return "西"; }
        @Override public String meaning() { return "west"; }
        @Override public String pinyin()  { return "xī"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Xi XI = new Xi();

    public record Nan() implements SingularZi {
        @Override public String glyph()   { return "南"; }
        @Override public String meaning() { return "south"; }
        @Override public String pinyin()  { return "nán"; }
        @Override public int strokes()    { return 9; }
    }
    public static final Nan NAN = new Nan();

    public record Bei_North() implements SingularZi {
        @Override public String glyph()   { return "北"; }
        @Override public String meaning() { return "north"; }
        @Override public String pinyin()  { return "běi"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Bei_North BEI_NORTH = new Bei_North();

    public record Zuo() implements SingularZi {
        @Override public String glyph()   { return "左"; }
        @Override public String meaning() { return "left"; }
        @Override public String pinyin()  { return "zuǒ"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Zuo ZUO = new Zuo();

    public record You_Right() implements SingularZi {
        @Override public String glyph()   { return "右"; }
        @Override public String meaning() { return "right"; }
        @Override public String pinyin()  { return "yòu"; }
        @Override public int strokes()    { return 5; }
    }
    public static final You_Right YOU_RIGHT = new You_Right();

    public record Qian_Front() implements SingularZi {
        @Override public String glyph()   { return "前"; }
        @Override public String meaning() { return "front, before"; }
        @Override public String pinyin()  { return "qián"; }
        @Override public int strokes()    { return 9; }
    }
    public static final Qian_Front QIAN_FRONT = new Qian_Front();

    public record Hou() implements SingularZi {
        @Override public String glyph()   { return "后"; }
        @Override public String meaning() { return "back, after"; }
        @Override public String pinyin()  { return "hòu"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Hou HOU = new Hou();

    public record Nei() implements SingularZi {
        @Override public String glyph()   { return "内"; }
        @Override public String meaning() { return "inside"; }
        @Override public String pinyin()  { return "nèi"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Nei NEI = new Nei();

    public record Wai() implements SingularZi {
        @Override public String glyph()   { return "外"; }
        @Override public String meaning() { return "outside"; }
        @Override public String pinyin()  { return "wài"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Wai WAI = new Wai();

    public record Yuan() implements SingularZi {
        @Override public String glyph()   { return "远"; }
        @Override public String meaning() { return "far"; }
        @Override public String pinyin()  { return "yuǎn"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Yuan YUAN = new Yuan();

    public record Jin_Near() implements SingularZi {
        @Override public String glyph()   { return "近"; }
        @Override public String meaning() { return "near"; }
        @Override public String pinyin()  { return "jìn"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Jin_Near JIN_NEAR = new Jin_Near();

    public record Gao() implements SingularZi {
        @Override public String glyph()   { return "高"; }
        @Override public String meaning() { return "tall, high"; }
        @Override public String pinyin()  { return "gāo"; }
        @Override public int strokes()    { return 10; }
    }
    public static final Gao GAO = new Gao();

    public record Di_Low() implements SingularZi {
        @Override public String glyph()   { return "低"; }
        @Override public String meaning() { return "low"; }
        @Override public String pinyin()  { return "dī"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Di_Low DI_LOW = new Di_Low();

    public record Chang() implements SingularZi {
        @Override public String glyph()   { return "长"; }
        @Override public String meaning() { return "long"; }
        @Override public String pinyin()  { return "cháng"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Chang CHANG = new Chang();

    public record Duan() implements SingularZi {
        @Override public String glyph()   { return "短"; }
        @Override public String meaning() { return "short"; }
        @Override public String pinyin()  { return "duǎn"; }
        @Override public int strokes()    { return 12; }
    }
    public static final Duan DUAN = new Duan();
}
