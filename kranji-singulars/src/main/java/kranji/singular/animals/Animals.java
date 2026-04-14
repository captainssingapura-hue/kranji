package kranji.singular.animals;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

public final class Animals {
    private Animals() {}

    public record Ma() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "马"; }
        @Override public String meaning() { return "horse"; }
        @Override public String pinyinText()  { return "mǎ"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ma MA = new Ma();

    public record Niu() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "牛"; }
        @Override public String meaning() { return "ox, cow"; }
        @Override public String pinyinText()  { return "niú"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Niu NIU = new Niu();

    public record Yang() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "羊"; }
        @Override public String meaning() { return "sheep"; }
        @Override public String pinyinText()  { return "yáng"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yang YANG = new Yang();

    public record Niao() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "鸟"; }
        @Override public String meaning() { return "bird"; }
        @Override public String pinyinText()  { return "niǎo"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Niao NIAO = new Niao();

    public record Yu() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "鱼"; }
        @Override public String meaning() { return "fish"; }
        @Override public String pinyinText()  { return "yú"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yu YU = new Yu();

    public record Chong() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "虫"; }
        @Override public String meaning() { return "insect"; }
        @Override public String pinyinText()  { return "chóng"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chong CHONG = new Chong();

    public record Quan() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "犬"; }
        @Override public String meaning() { return "dog"; }
        @Override public String pinyinText()  { return "quǎn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Quan QUAN = new Quan();

    public record Shi_Pig() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "豕"; }
        @Override public String meaning() { return "pig"; }
        @Override public String pinyinText()  { return "shǐ"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Pig SHI_PIG = new Shi_Pig();

    public record Zhui() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "隹"; }
        @Override public String meaning() { return "short-tailed bird"; }
        @Override public String pinyinText()  { return "zhuī"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhui ZHUI = new Zhui();

    public record Long() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "龙"; }
        @Override public String meaning() { return "dragon"; }
        @Override public String pinyinText()  { return "lóng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Long LONG = new Long();

    public record Shu() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "鼠"; }
        @Override public String meaning() { return "mouse, rat"; }
        @Override public String pinyinText()  { return "shǔ"; }
        @Override public int strokes()    { return 13; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shu SHU = new Shu();

    public record Bei() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "贝"; }
        @Override public String meaning() { return "shell, cowrie"; }
        @Override public String pinyinText()  { return "bèi"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bei BEI = new Bei();

    public record Wu_Crow() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "乌"; }
        @Override public String meaning() { return "crow/black"; }
        @Override public String pinyinText()  { return "wū"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wu_Crow WU_CROW = new Wu_Crow();

    public record Xiang() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "象"; }
        @Override public String meaning() { return "elephant/image"; }
        @Override public String pinyinText()  { return "xiàng"; }
        @Override public int strokes()    { return 11; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xiang XIANG = new Xiang();

    public record Zhi_Beast() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "豸"; }
        @Override public String meaning() { return "legless insect/beast"; }
        @Override public String pinyinText()  { return "zhì"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhi_Beast ZHI_BEAST = new Zhi_Beast();

    public record Gui_Ghost() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "鬼"; }
        @Override public String meaning() { return "ghost/spirit"; }
        @Override public String pinyinText()  { return "guǐ"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Gui_Ghost GUI_GHOST = new Gui_Ghost();

    public record Qiang() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "羌"; }
        @Override public String meaning() { return "Qiang people/shepherd"; }
        @Override public String pinyinText()  { return "qiāng"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qiang QIANG = new Qiang();

    public record He_Crane() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "隺"; }
        @Override public String meaning() { return "crane (bird)"; }
        @Override public String pinyinText()  { return "hè"; }
        @Override public int strokes()    { return 10; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final He_Crane HE_CRANE = new He_Crane();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            MA, NIU, YANG, NIAO, YU, CHONG, QUAN, SHI_PIG,
            ZHUI, LONG, SHU, BEI, WU_CROW, XIANG, ZHI_BEAST,
            GUI_GHOST, QIANG, HE_CRANE);
}
