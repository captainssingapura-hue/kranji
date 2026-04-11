package kranji.singular.numbers;

import kranji.component.SingularZi;

public final class NumbersAndMeasure {
    private NumbersAndMeasure() {}

    public record Yi() implements SingularZi {
        @Override public String glyph()   { return "一"; }
        @Override public String meaning() { return "one"; }
        @Override public String pinyin()  { return "yī"; }
        @Override public int strokes()    { return 1; }
    }
    public static final Yi YI = new Yi();

    public record Er() implements SingularZi {
        @Override public String glyph()   { return "二"; }
        @Override public String meaning() { return "two"; }
        @Override public String pinyin()  { return "èr"; }
        @Override public int strokes()    { return 2; }
    }
    public static final Er ER = new Er();

    public record San() implements SingularZi {
        @Override public String glyph()   { return "三"; }
        @Override public String meaning() { return "three"; }
        @Override public String pinyin()  { return "sān"; }
        @Override public int strokes()    { return 3; }
    }
    public static final San SAN = new San();

    public record Si() implements SingularZi {
        @Override public String glyph()   { return "四"; }
        @Override public String meaning() { return "four"; }
        @Override public String pinyin()  { return "sì"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Si SI = new Si();

    public record Wu() implements SingularZi {
        @Override public String glyph()   { return "五"; }
        @Override public String meaning() { return "five"; }
        @Override public String pinyin()  { return "wǔ"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Wu WU = new Wu();

    public record Liu() implements SingularZi {
        @Override public String glyph()   { return "六"; }
        @Override public String meaning() { return "six"; }
        @Override public String pinyin()  { return "liù"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Liu LIU = new Liu();

    public record Qi() implements SingularZi {
        @Override public String glyph()   { return "七"; }
        @Override public String meaning() { return "seven"; }
        @Override public String pinyin()  { return "qī"; }
        @Override public int strokes()    { return 2; }
    }
    public static final Qi QI = new Qi();

    public record Ba() implements SingularZi {
        @Override public String glyph()   { return "八"; }
        @Override public String meaning() { return "eight"; }
        @Override public String pinyin()  { return "bā"; }
        @Override public int strokes()    { return 2; }
    }
    public static final Ba BA = new Ba();

    public record Jiu() implements SingularZi {
        @Override public String glyph()   { return "九"; }
        @Override public String meaning() { return "nine"; }
        @Override public String pinyin()  { return "jiǔ"; }
        @Override public int strokes()    { return 2; }
    }
    public static final Jiu JIU = new Jiu();

    public record Shi() implements SingularZi {
        @Override public String glyph()   { return "十"; }
        @Override public String meaning() { return "ten"; }
        @Override public String pinyin()  { return "shí"; }
        @Override public int strokes()    { return 2; }
    }
    public static final Shi SHI = new Shi();

    public record Bai() implements SingularZi {
        @Override public String glyph()   { return "百"; }
        @Override public String meaning() { return "hundred"; }
        @Override public String pinyin()  { return "bǎi"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Bai BAI = new Bai();

    public record Qian() implements SingularZi {
        @Override public String glyph()   { return "千"; }
        @Override public String meaning() { return "thousand"; }
        @Override public String pinyin()  { return "qiān"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Qian QIAN = new Qian();

    public record Wan() implements SingularZi {
        @Override public String glyph()   { return "万"; }
        @Override public String meaning() { return "ten thousand"; }
        @Override public String pinyin()  { return "wàn"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Wan WAN = new Wan();

    public record Ban() implements SingularZi {
        @Override public String glyph()   { return "半"; }
        @Override public String meaning() { return "half"; }
        @Override public String pinyin()  { return "bàn"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Ban BAN = new Ban();

    public record Shuang() implements SingularZi {
        @Override public String glyph()   { return "双"; }
        @Override public String meaning() { return "pair, double"; }
        @Override public String pinyin()  { return "shuāng"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Shuang SHUANG = new Shuang();
}
