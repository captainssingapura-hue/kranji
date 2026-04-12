package kranji.singular.animals;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

public final class Animals {
    private Animals() {}

    public record Ma() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "马"; }
        @Override public String meaning() { return "horse"; }
        @Override public String pinyin()  { return "mǎ"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ma MA = new Ma();

    public record Niu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "牛"; }
        @Override public String meaning() { return "ox, cow"; }
        @Override public String pinyin()  { return "niú"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Niu NIU = new Niu();

    public record Yang() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "羊"; }
        @Override public String meaning() { return "sheep"; }
        @Override public String pinyin()  { return "yáng"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yang YANG = new Yang();

    public record Niao() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "鸟"; }
        @Override public String meaning() { return "bird"; }
        @Override public String pinyin()  { return "niǎo"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Niao NIAO = new Niao();

    public record Yu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "鱼"; }
        @Override public String meaning() { return "fish"; }
        @Override public String pinyin()  { return "yú"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu YU = new Yu();

    public record Chong() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "虫"; }
        @Override public String meaning() { return "insect"; }
        @Override public String pinyin()  { return "chóng"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chong CHONG = new Chong();

    public record Quan() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "犬"; }
        @Override public String meaning() { return "dog"; }
        @Override public String pinyin()  { return "quǎn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Quan QUAN = new Quan();

    public record Shi_Pig() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "豕"; }
        @Override public String meaning() { return "pig"; }
        @Override public String pinyin()  { return "shǐ"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Pig SHI_PIG = new Shi_Pig();

    public record Lu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "鹿"; }
        @Override public String meaning() { return "deer"; }
        @Override public String pinyin()  { return "lù"; }
        @Override public int strokes()    { return 11; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Lu LU = new Lu();

    public record Zhui() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "隹"; }
        @Override public String meaning() { return "short-tailed bird"; }
        @Override public String pinyin()  { return "zhuī"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhui ZHUI = new Zhui();

    public record Long() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "龙"; }
        @Override public String meaning() { return "dragon"; }
        @Override public String pinyin()  { return "lóng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Long LONG = new Long();

    public record She() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "蛇"; }
        @Override public String meaning() { return "snake"; }
        @Override public String pinyin()  { return "shé"; }
        @Override public int strokes()    { return 11; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final She SHE = new She();

    public record Hu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "虎"; }
        @Override public String meaning() { return "tiger"; }
        @Override public String pinyin()  { return "hǔ"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Hu HU = new Hu();

    public record Shu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "鼠"; }
        @Override public String meaning() { return "mouse, rat"; }
        @Override public String pinyin()  { return "shǔ"; }
        @Override public int strokes()    { return 13; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shu SHU = new Shu();

    public record Tu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "兔"; }
        @Override public String meaning() { return "rabbit"; }
        @Override public String pinyin()  { return "tù"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Tu TU = new Tu();

    public record Gui() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "龟"; }
        @Override public String meaning() { return "tortoise"; }
        @Override public String pinyin()  { return "guī"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gui GUI = new Gui();

    public record Yan() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "燕"; }
        @Override public String meaning() { return "swallow (bird)"; }
        @Override public String pinyin()  { return "yàn"; }
        @Override public int strokes()    { return 16; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yan YAN = new Yan();

    public record Feng() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "凤"; }
        @Override public String meaning() { return "phoenix"; }
        @Override public String pinyin()  { return "fèng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Feng FENG = new Feng();

    public record Bei() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "贝"; }
        @Override public String meaning() { return "shell, cowrie"; }
        @Override public String pinyin()  { return "bèi"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bei BEI = new Bei();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            MA, NIU, YANG, NIAO, YU, CHONG, QUAN, SHI_PIG, LU,
            ZHUI, LONG, SHE, HU, SHU, TU, GUI, YAN, FENG, BEI);
}
