package kranji.singular.numbers;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

public final class NumbersAndMeasure {
    private NumbersAndMeasure() {}

    public record Yi() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "一"; }
        @Override public String meaning() { return "one"; }
        @Override public String pinyin()  { return "yī"; }
        @Override public int strokes()    { return 1; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi YI = new Yi();

    public record Er() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "二"; }
        @Override public String meaning() { return "two"; }
        @Override public String pinyin()  { return "èr"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Er ER = new Er();

    public record San() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "三"; }
        @Override public String meaning() { return "three"; }
        @Override public String pinyin()  { return "sān"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final San SAN = new San();

    public record Si() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "四"; }
        @Override public String meaning() { return "four"; }
        @Override public String pinyin()  { return "sì"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Si SI = new Si();

    public record Wu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "五"; }
        @Override public String meaning() { return "five"; }
        @Override public String pinyin()  { return "wǔ"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wu WU = new Wu();

    public record Liu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "六"; }
        @Override public String meaning() { return "six"; }
        @Override public String pinyin()  { return "liù"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Liu LIU = new Liu();

    public record Qi() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "七"; }
        @Override public String meaning() { return "seven"; }
        @Override public String pinyin()  { return "qī"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qi QI = new Qi();

    public record Ba() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "八"; }
        @Override public String meaning() { return "eight"; }
        @Override public String pinyin()  { return "bā"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ba BA = new Ba();

    public record Jiu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "九"; }
        @Override public String meaning() { return "nine"; }
        @Override public String pinyin()  { return "jiǔ"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jiu JIU = new Jiu();

    public record Shi() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "十"; }
        @Override public String meaning() { return "ten"; }
        @Override public String pinyin()  { return "shí"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi SHI = new Shi();

    public record Bai() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "百"; }
        @Override public String meaning() { return "hundred"; }
        @Override public String pinyin()  { return "bǎi"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bai BAI = new Bai();

    public record Qian() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "千"; }
        @Override public String meaning() { return "thousand"; }
        @Override public String pinyin()  { return "qiān"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qian QIAN = new Qian();

    public record Wan() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "万"; }
        @Override public String meaning() { return "ten thousand"; }
        @Override public String pinyin()  { return "wàn"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wan WAN = new Wan();

    public record Ban() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "半"; }
        @Override public String meaning() { return "half"; }
        @Override public String pinyin()  { return "bàn"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ban BAN = new Ban();

    public record Shuang() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "双"; }
        @Override public String meaning() { return "pair, double"; }
        @Override public String pinyin()  { return "shuāng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shuang SHUANG = new Shuang();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            YI, ER, SAN, SI, WU, LIU, QI, BA, JIU, SHI,
            BAI, QIAN, WAN, BAN, SHUANG);
}
