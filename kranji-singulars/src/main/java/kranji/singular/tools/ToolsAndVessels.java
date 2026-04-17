package kranji.singular.tools;

import kranji.classification.BlockRole;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.library.LibraryMember;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import java.util.List;

public final class ToolsAndVessels {
    private ToolsAndVessels() {}

    public record Dao() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "刀"; }
        @Override public String meaning() { return "knife"; }
        @Override public String pinyinText()  { return "dāo"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Dao DAO = new Dao();

    public record Ren_Blade() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "刃"; }
        @Override public String meaning() { return "blade"; }
        @Override public String pinyinText()  { return "rèn"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ren_Blade REN_BLADE = new Ren_Blade();

    /** 弓 — bow. Yielding as left. */
    public record Gong() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "弓"; }
        @Override public String meaning() { return "bow"; }
        @Override public String pinyinText()  { return "gōng"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }
    }
    public static final Gong GONG = new Gong();

    public record Mao() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "矛"; }
        @Override public String meaning() { return "spear"; }
        @Override public String pinyinText()  { return "máo"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mao MAO = new Mao();

    public record Shi_Arrow() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "矢"; }
        @Override public String meaning() { return "arrow"; }
        @Override public String pinyinText()  { return "shǐ"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Arrow SHI_ARROW = new Shi_Arrow();

    public record Ge() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "戈"; }
        @Override public String meaning() { return "halberd"; }
        @Override public String pinyinText()  { return "gē"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ge GE = new Ge();

    public record Jin_Axe() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "斤"; }
        @Override public String meaning() { return "axe"; }
        @Override public String pinyinText()  { return "jīn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jin_Axe JIN_AXE = new Jin_Axe();

    public record Dou() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "斗"; }
        @Override public String meaning() { return "dipper, measure"; }
        @Override public String pinyinText()  { return "dǒu"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Dou DOU = new Dou();

    public record Fou() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "缶"; }
        @Override public String meaning() { return "jar, crock"; }
        @Override public String pinyinText()  { return "fǒu"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fou FOU = new Fou();

    public record Wa() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "瓦"; }
        @Override public String meaning() { return "tile"; }
        @Override public String pinyinText()  { return "wǎ"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wa WA = new Wa();

    public record Min() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "皿"; }
        @Override public String meaning() { return "dish, vessel"; }
        @Override public String pinyinText()  { return "mǐn"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Min MIN = new Min();

    public record Che() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "车"; }
        @Override public String meaning() { return "vehicle"; }
        @Override public String pinyinText()  { return "chē"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Che CHE = new Che();

    public record Zhou() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "舟"; }
        @Override public String meaning() { return "boat"; }
        @Override public String pinyinText()  { return "zhōu"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhou ZHOU = new Zhou();

    public record Men() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "门"; }
        @Override public String meaning() { return "gate, door"; }
        @Override public String pinyinText()  { return "mén"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Men MEN = new Men();

    public record Bi_Spoon() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "匕"; }
        @Override public String meaning() { return "spoon"; }
        @Override public String pinyinText()  { return "bǐ"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bi_Spoon BI_SPOON = new Bi_Spoon();

    public record Pian() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "片"; }
        @Override public String meaning() { return "slice, piece"; }
        @Override public String pinyinText()  { return "piàn"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Pian PIAN = new Pian();

    public record Li_Plow() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "力"; }
        @Override public String meaning() { return "strength, plow"; }
        @Override public String pinyinText()  { return "lì"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Li_Plow LI_PLOW = new Li_Plow();

    public record Shao() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "勺"; }
        @Override public String meaning() { return "spoon, ladle"; }
        @Override public String pinyinText()  { return "sháo"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shao SHAO = new Shao();

    public record Jin_Metal() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "金"; }
        @Override public String meaning() { return "gold, metal"; }
        @Override public String pinyinText()  { return "jīn"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jin_Metal JIN_METAL = new Jin_Metal();

    public record Ding_Nail() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "丁"; }
        @Override public String meaning() { return "nail/4th Heavenly Stem"; }
        @Override public String pinyinText()  { return "dīng"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ding_Nail DING_NAIL = new Ding_Nail();

    public record Wan_Pill() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "丸"; }
        @Override public String meaning() { return "pill/pellet"; }
        @Override public String pinyinText()  { return "wán"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wan_Pill WAN_PILL = new Wan_Pill();

    public record Ce() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "册"; }
        @Override public String meaning() { return "book/volume"; }
        @Override public String pinyinText()  { return "cè"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ce CE = new Ce();

    public record Ji_Table() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "几"; }
        @Override public String meaning() { return "small table/how many"; }
        @Override public String pinyinText()  { return "jī"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ji_Table JI_TABLE = new Ji_Table();

    public record Fan() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "凡"; }
        @Override public String meaning() { return "ordinary/all"; }
        @Override public String pinyinText()  { return "fán"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fan FAN = new Fan();

    public record Bu_Divination() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "卜"; }
        @Override public String meaning() { return "divination"; }
        @Override public String pinyinText()  { return "bǔ"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Bu_Divination BU_DIVINATION = new Bu_Divination();

    public record Ka() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "卡"; }
        @Override public String meaning() { return "card/clip"; }
        @Override public String pinyinText()  { return "kǎ"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ka KA = new Ka();

    public record Wei_Guard() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "卫"; }
        @Override public String meaning() { return "guard/defend"; }
        @Override public String pinyinText()  { return "wèi"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wei_Guard WEI_GUARD = new Wei_Guard();

    public record Cha() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "叉"; }
        @Override public String meaning() { return "fork/cross"; }
        @Override public String pinyinText()  { return "chā"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Cha CHA = new Cha();

    public record Diao() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "刁"; }
        @Override public String meaning() { return "cunning/tricky"; }
        @Override public String pinyinText()  { return "diāo"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Diao DIAO = new Diao();

    public record You_Wine() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "酉"; }
        @Override public String meaning() { return "wine vessel/10th Earthly Branch"; }
        @Override public String pinyinText()  { return "yǒu"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final You_Wine YOU_WINE = new You_Wine();

    public record Cai_Talent() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "才"; }
        @Override public String meaning() { return "talent/just now"; }
        @Override public String pinyinText()  { return "cái"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Cai_Talent CAI_TALENT = new Cai_Talent();

    public record Yi_Shoot() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "弋"; }
        @Override public String meaning() { return "shoot/stake"; }
        @Override public String pinyinText()  { return "yì"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yi_Shoot YI_SHOOT = new Yi_Shoot();

    public record Fu_Not() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "弗"; }
        @Override public String meaning() { return "not (archaic)"; }
        @Override public String pinyinText()  { return "fú"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Fu_Not FU_NOT = new Fu_Not();

    public record Chi_Step() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "彳"; }
        @Override public String meaning() { return "step (left foot)"; }
        @Override public String pinyinText()  { return "chì"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chi_Step CHI_STEP = new Chi_Step();

    public record Wu_Fifth() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "戊"; }
        @Override public String meaning() { return "fifth Heavenly Stem"; }
        @Override public String pinyinText()  { return "wù"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wu_Fifth WU_FIFTH = new Wu_Fifth();

    public record Jian_Small() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "戋"; }
        @Override public String meaning() { return "small/narrow"; }
        @Override public String pinyinText()  { return "jiān"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Jian_Small JIAN_SMALL = new Jian_Small();

    public record Ya_Fork() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "丫"; }
        @Override public String meaning() { return "fork/girl"; }
        @Override public String pinyinText()  { return "yā"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ya_Fork YA_FORK = new Ya_Fork();

    /** 印 — seal, stamp. */
    public record Yin_Seal() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5370"; }
        @Override public String meaning() { return "seal, stamp"; }
        @Override public String pinyinText()  { return "y\u00ECn"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yin_Seal YIN_SEAL = new Yin_Seal();

    /** \u5177 — tool, utensil, possess. */
    public record Ju_Tool() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5177"; }
        @Override public String meaning() { return "tool, utensil"; }
        @Override public String pinyinText()  { return "j\u00F9"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ju_Tool JU_TOOL = new Ju_Tool();

    /** \u5377 — scroll, roll. */
    public record Juan() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5377"; }
        @Override public String meaning() { return "scroll, roll"; }
        @Override public String pinyinText()  { return "ju\u01CEn"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Juan JUAN = new Juan();

    /** \u5238 — ticket, voucher. */
    public record Quan_Ticket() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5238"; }
        @Override public String meaning() { return "ticket, voucher"; }
        @Override public String pinyinText()  { return "qu\u00E0n"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Quan_Ticket QUAN_TICKET = new Quan_Ticket();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            DAO, REN_BLADE, GONG, MAO, SHI_ARROW, GE, JIN_AXE, DOU,
            FOU, WA, MIN, CHE, ZHOU, MEN, BI_SPOON, PIAN, LI_PLOW,
            SHAO, JIN_METAL, DING_NAIL, WAN_PILL, CE, JI_TABLE, FAN,
            BU_DIVINATION, KA, WEI_GUARD, CHA, DIAO, YOU_WINE,
            CAI_TALENT, YI_SHOOT, FU_NOT, CHI_STEP, WU_FIFTH,
            JIAN_SMALL, YA_FORK, YIN_SEAL,
            JU_TOOL, JUAN, QUAN_TICKET);
}
