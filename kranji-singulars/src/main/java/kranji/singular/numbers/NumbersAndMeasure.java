package kranji.singular.numbers;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

public final class NumbersAndMeasure {
    private NumbersAndMeasure() {}

    public record Yi() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "一"; }
        @Override public String meaning() { return "one"; }
        @Override public String pinyinText()  { return "yī"; }
        @Override public int strokes()    { return 1; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi YI = new Yi();

    public record Er() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "二"; }
        @Override public String meaning() { return "two"; }
        @Override public String pinyinText()  { return "èr"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Er ER = new Er();

    public record San() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "三"; }
        @Override public String meaning() { return "three"; }
        @Override public String pinyinText()  { return "sān"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final San SAN = new San();

    public record Si() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "四"; }
        @Override public String meaning() { return "four"; }
        @Override public String pinyinText()  { return "sì"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Si SI = new Si();

    public record Wu() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "五"; }
        @Override public String meaning() { return "five"; }
        @Override public String pinyinText()  { return "wǔ"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wu WU = new Wu();

    public record Liu() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "六"; }
        @Override public String meaning() { return "six"; }
        @Override public String pinyinText()  { return "liù"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Liu LIU = new Liu();

    public record Qi() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "七"; }
        @Override public String meaning() { return "seven"; }
        @Override public String pinyinText()  { return "qī"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qi QI = new Qi();

    public record Ba() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "八"; }
        @Override public String meaning() { return "eight"; }
        @Override public String pinyinText()  { return "bā"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ba BA = new Ba();

    public record Jiu() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "九"; }
        @Override public String meaning() { return "nine"; }
        @Override public String pinyinText()  { return "jiǔ"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jiu JIU = new Jiu();

    public record Shi() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "十"; }
        @Override public String meaning() { return "ten"; }
        @Override public String pinyinText()  { return "shí"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi SHI = new Shi();

    public record Bai() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "百"; }
        @Override public String meaning() { return "hundred"; }
        @Override public String pinyinText()  { return "bǎi"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bai BAI = new Bai();

    public record Qian() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "千"; }
        @Override public String meaning() { return "thousand"; }
        @Override public String pinyinText()  { return "qiān"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qian QIAN = new Qian();

    public record Wan() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "万"; }
        @Override public String meaning() { return "ten thousand"; }
        @Override public String pinyinText()  { return "wàn"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wan WAN = new Wan();

    public record Ban() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "半"; }
        @Override public String meaning() { return "half"; }
        @Override public String pinyinText()  { return "bàn"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ban BAN = new Ban();

    /** 丈 — ten feet, elder. */
    public record Zhang() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "丈"; }
        @Override public String meaning() { return "ten feet, elder"; }
        @Override public String pinyinText()  { return "zhàng"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhang ZHANG = new Zhang();

    /** 两 — two, both. */
    public record Liang() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "两"; }
        @Override public String meaning() { return "two, both"; }
        @Override public String pinyinText()  { return "liǎng"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Liang LIANG = new Liang();

    /** 丰 — abundant, lush. */
    public record Feng_Abundant() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "丰"; }
        @Override public String meaning() { return "abundant, lush"; }
        @Override public String pinyinText()  { return "fēng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Feng_Abundant FENG_ABUNDANT = new Feng_Abundant();

    /** 卅 — thirty. */
    public record Sa() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "卅"; }
        @Override public String meaning() { return "thirty"; }
        @Override public String pinyinText()  { return "sà"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Sa SA = new Sa();

    /** 廿 — twenty. */
    public record Nian_Twenty() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "廿"; }
        @Override public String meaning() { return "twenty"; }
        @Override public String pinyinText()  { return "niàn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Nian_Twenty NIAN_TWENTY = new Nian_Twenty();

    /** 幺 — tiny, youngest. */
    public record Yao_Tiny() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "幺"; }
        @Override public String meaning() { return "tiny, youngest"; }
        @Override public String pinyinText()  { return "yāo"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yao_Tiny YAO_TINY = new Yao_Tiny();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            YI, ER, SAN, SI, WU, LIU, QI, BA, JIU, SHI,
            BAI, QIAN, WAN, BAN, ZHANG, LIANG, FENG_ABUNDANT,
            SA, NIAN_TWENTY, YAO_TINY);
}
