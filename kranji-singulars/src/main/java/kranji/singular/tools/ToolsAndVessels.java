package kranji.singular.tools;

import kranji.classification.BlockRole;
import kranji.zi.ComposedZi.LeftRight;
import kranji.library.LibraryMember;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import java.util.List;

public final class ToolsAndVessels {
    private ToolsAndVessels() {}

    public record Dao() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "刀"; }
        @Override public String meaning() { return "knife"; }
        @Override public String pinyin()  { return "dāo"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Dao DAO = new Dao();

    public record Ren_Blade() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "刃"; }
        @Override public String meaning() { return "blade"; }
        @Override public String pinyin()  { return "rèn"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ren_Blade REN_BLADE = new Ren_Blade();

    /** 弓 — bow. Yielding as left. */
    public record Gong() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "弓"; }
        @Override public String meaning() { return "bow"; }
        @Override public String pinyin()  { return "gōng"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }
    }
    public static final Gong GONG = new Gong();

    public record Mao() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "矛"; }
        @Override public String meaning() { return "spear"; }
        @Override public String pinyin()  { return "máo"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mao MAO = new Mao();

    public record Shi_Arrow() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "矢"; }
        @Override public String meaning() { return "arrow"; }
        @Override public String pinyin()  { return "shǐ"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Arrow SHI_ARROW = new Shi_Arrow();

    public record Ge() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "戈"; }
        @Override public String meaning() { return "halberd"; }
        @Override public String pinyin()  { return "gē"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ge GE = new Ge();

    public record Jin_Axe() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "斤"; }
        @Override public String meaning() { return "axe"; }
        @Override public String pinyin()  { return "jīn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jin_Axe JIN_AXE = new Jin_Axe();

    public record Dou() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "斗"; }
        @Override public String meaning() { return "dipper, measure"; }
        @Override public String pinyin()  { return "dǒu"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Dou DOU = new Dou();

    public record Fou() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "缶"; }
        @Override public String meaning() { return "jar, crock"; }
        @Override public String pinyin()  { return "fǒu"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fou FOU = new Fou();

    public record Ding() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "鼎"; }
        @Override public String meaning() { return "tripod vessel"; }
        @Override public String pinyin()  { return "dǐng"; }
        @Override public int strokes()    { return 12; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ding DING = new Ding();

    public record Wa() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "瓦"; }
        @Override public String meaning() { return "tile"; }
        @Override public String pinyin()  { return "wǎ"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wa WA = new Wa();

    public record Min() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "皿"; }
        @Override public String meaning() { return "dish, vessel"; }
        @Override public String pinyin()  { return "mǐn"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Min MIN = new Min();

    public record Che() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "车"; }
        @Override public String meaning() { return "vehicle"; }
        @Override public String pinyin()  { return "chē"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Che CHE = new Che();

    public record Zhou() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "舟"; }
        @Override public String meaning() { return "boat"; }
        @Override public String pinyin()  { return "zhōu"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhou ZHOU = new Zhou();

    public record Men() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "门"; }
        @Override public String meaning() { return "gate, door"; }
        @Override public String pinyin()  { return "mén"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Men MEN = new Men();

    public record Jian() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "剑"; }
        @Override public String meaning() { return "sword"; }
        @Override public String pinyin()  { return "jiàn"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jian JIAN = new Jian();

    public record Bi_Spoon() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "匕"; }
        @Override public String meaning() { return "spoon"; }
        @Override public String pinyin()  { return "bǐ"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bi_Spoon BI_SPOON = new Bi_Spoon();

    public record Pian() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "片"; }
        @Override public String meaning() { return "slice, piece"; }
        @Override public String pinyin()  { return "piàn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Pian PIAN = new Pian();

    public record Li_Plow() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "力"; }
        @Override public String meaning() { return "strength, plow"; }
        @Override public String pinyin()  { return "lì"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Li_Plow LI_PLOW = new Li_Plow();

    public record Shao() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "勺"; }
        @Override public String meaning() { return "spoon, ladle"; }
        @Override public String pinyin()  { return "sháo"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shao SHAO = new Shao();

    public record Jin_Metal() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "金"; }
        @Override public String meaning() { return "gold, metal"; }
        @Override public String pinyin()  { return "jīn"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jin_Metal JIN_METAL = new Jin_Metal();

    public record Si() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "丝"; }
        @Override public String meaning() { return "silk"; }
        @Override public String pinyin()  { return "sī"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Si SI = new Si();

    public record Zhen() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "针"; }
        @Override public String meaning() { return "needle"; }
        @Override public String pinyin()  { return "zhēn"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhen ZHEN = new Zhen();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            DAO, REN_BLADE, GONG, MAO, SHI_ARROW, GE, JIN_AXE, DOU,
            FOU, DING, WA, MIN, CHE, ZHOU, MEN, JIAN, BI_SPOON,
            PIAN, LI_PLOW, SHAO, JIN_METAL, SI, ZHEN);
}
