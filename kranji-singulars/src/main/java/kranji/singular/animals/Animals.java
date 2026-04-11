package kranji.singular.animals;

import kranji.component.SingularZi;

public final class Animals {
    private Animals() {}

    public record Ma() implements SingularZi {
        @Override public String glyph()   { return "马"; }
        @Override public String meaning() { return "horse"; }
        @Override public String pinyin()  { return "mǎ"; }
        @Override public int strokes()    { return 3; }
    }
    public static final Ma MA = new Ma();

    public record Niu() implements SingularZi {
        @Override public String glyph()   { return "牛"; }
        @Override public String meaning() { return "ox, cow"; }
        @Override public String pinyin()  { return "niú"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Niu NIU = new Niu();

    public record Yang() implements SingularZi {
        @Override public String glyph()   { return "羊"; }
        @Override public String meaning() { return "sheep"; }
        @Override public String pinyin()  { return "yáng"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Yang YANG = new Yang();

    public record Niao() implements SingularZi {
        @Override public String glyph()   { return "鸟"; }
        @Override public String meaning() { return "bird"; }
        @Override public String pinyin()  { return "niǎo"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Niao NIAO = new Niao();

    public record Yu() implements SingularZi {
        @Override public String glyph()   { return "鱼"; }
        @Override public String meaning() { return "fish"; }
        @Override public String pinyin()  { return "yú"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Yu YU = new Yu();

    public record Chong() implements SingularZi {
        @Override public String glyph()   { return "虫"; }
        @Override public String meaning() { return "insect"; }
        @Override public String pinyin()  { return "chóng"; }
        @Override public int strokes()    { return 6; }
    }
    public static final Chong CHONG = new Chong();

    public record Quan() implements SingularZi {
        @Override public String glyph()   { return "犬"; }
        @Override public String meaning() { return "dog"; }
        @Override public String pinyin()  { return "quǎn"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Quan QUAN = new Quan();

    public record Shi_Pig() implements SingularZi {
        @Override public String glyph()   { return "豕"; }
        @Override public String meaning() { return "pig"; }
        @Override public String pinyin()  { return "shǐ"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Shi_Pig SHI_PIG = new Shi_Pig();

    public record Lu() implements SingularZi {
        @Override public String glyph()   { return "鹿"; }
        @Override public String meaning() { return "deer"; }
        @Override public String pinyin()  { return "lù"; }
        @Override public int strokes()    { return 11; }
    }
    public static final Lu LU = new Lu();

    public record Zhui() implements SingularZi {
        @Override public String glyph()   { return "隹"; }
        @Override public String meaning() { return "short-tailed bird"; }
        @Override public String pinyin()  { return "zhuī"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Zhui ZHUI = new Zhui();

    public record Long() implements SingularZi {
        @Override public String glyph()   { return "龙"; }
        @Override public String meaning() { return "dragon"; }
        @Override public String pinyin()  { return "lóng"; }
        @Override public int strokes()    { return 5; }
    }
    public static final Long LONG = new Long();

    public record She() implements SingularZi {
        @Override public String glyph()   { return "蛇"; }
        @Override public String meaning() { return "snake"; }
        @Override public String pinyin()  { return "shé"; }
        @Override public int strokes()    { return 11; }
    }
    public static final She SHE = new She();

    public record Hu() implements SingularZi {
        @Override public String glyph()   { return "虎"; }
        @Override public String meaning() { return "tiger"; }
        @Override public String pinyin()  { return "hǔ"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Hu HU = new Hu();

    public record Shu() implements SingularZi {
        @Override public String glyph()   { return "鼠"; }
        @Override public String meaning() { return "mouse, rat"; }
        @Override public String pinyin()  { return "shǔ"; }
        @Override public int strokes()    { return 13; }
    }
    public static final Shu SHU = new Shu();

    public record Tu() implements SingularZi {
        @Override public String glyph()   { return "兔"; }
        @Override public String meaning() { return "rabbit"; }
        @Override public String pinyin()  { return "tù"; }
        @Override public int strokes()    { return 8; }
    }
    public static final Tu TU = new Tu();

    public record Gui() implements SingularZi {
        @Override public String glyph()   { return "龟"; }
        @Override public String meaning() { return "tortoise"; }
        @Override public String pinyin()  { return "guī"; }
        @Override public int strokes()    { return 7; }
    }
    public static final Gui GUI = new Gui();

    public record Yan() implements SingularZi {
        @Override public String glyph()   { return "燕"; }
        @Override public String meaning() { return "swallow (bird)"; }
        @Override public String pinyin()  { return "yàn"; }
        @Override public int strokes()    { return 16; }
    }
    public static final Yan YAN = new Yan();

    public record Feng() implements SingularZi {
        @Override public String glyph()   { return "凤"; }
        @Override public String meaning() { return "phoenix"; }
        @Override public String pinyin()  { return "fèng"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Feng FENG = new Feng();

    public record Bei() implements SingularZi {
        @Override public String glyph()   { return "贝"; }
        @Override public String meaning() { return "shell, cowrie"; }
        @Override public String pinyin()  { return "bèi"; }
        @Override public int strokes()    { return 4; }
    }
    public static final Bei BEI = new Bei();
}
