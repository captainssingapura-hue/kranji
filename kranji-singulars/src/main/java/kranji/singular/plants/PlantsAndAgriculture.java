package kranji.singular.plants;

import kranji.classification.BlockRole;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.library.LibraryMember;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import java.util.List;

/**
 * Stateless record implementations of {@link SingularBlock} for Chinese
 * characters related to plants and agriculture.
 */
public final class PlantsAndAgriculture {

    private PlantsAndAgriculture() {}

    /** 禾 — grain, cereal. Yielding as left. */
    public record He() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "禾"; }
        @Override public String meaning() { return "grain, cereal"; }
        @Override public String pinyinText()  { return "hé"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }
    }

    /** 米 — rice. */
    public record Mi() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "米"; }
        @Override public String meaning() { return "rice"; }
        @Override public String pinyinText()  { return "mǐ"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 果 — fruit. */
    public record Guo() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "果"; }
        @Override public String meaning() { return "fruit"; }
        @Override public String pinyinText()  { return "guǒ"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 农 — farming. */
    public record Nong() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "农"; }
        @Override public String meaning() { return "farming"; }
        @Override public String pinyinText()  { return "nóng"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 瓜 — melon. */
    public record Gua() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "瓜"; }
        @Override public String meaning() { return "melon"; }
        @Override public String pinyinText()  { return "guā"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 束 — bundle, to bind. */
    public record Shu() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "束"; }
        @Override public String meaning() { return "bundle, to bind"; }
        @Override public String pinyinText()  { return "shù"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 朱 — vermillion (trunk of tree). */
    public record Zhu_Trunk() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "朱"; }
        @Override public String meaning() { return "vermillion (trunk of tree)"; }
        @Override public String pinyinText()  { return "zhū"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 未 — not yet. */
    public record Wei_Not() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "未"; }
        @Override public String meaning() { return "not yet"; }
        @Override public String pinyinText()  { return "wèi"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 末 — end, tip. */
    public record Mo() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "末"; }
        @Override public String meaning() { return "end, tip"; }
        @Override public String pinyinText()  { return "mò"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 本 — root, origin. */
    public record Ben() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "本"; }
        @Override public String meaning() { return "root, origin"; }
        @Override public String pinyinText()  { return "běn"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 木 — tree, wood. */
    public record Mu_Tree() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "木"; }
        @Override public String meaning() { return "tree, wood"; }
        @Override public String pinyinText()  { return "mù"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 术 — art, technique. */
    public record Shu_Art() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "术"; }
        @Override public String meaning() { return "art, technique"; }
        @Override public String pinyinText()  { return "shù"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 柬 — select, letter. */
    public record Jian_Select() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "柬"; }
        @Override public String meaning() { return "select, letter"; }
        @Override public String pinyinText()  { return "jiǎn"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 耒 — plow handle. */
    public record Lei_Plow() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "耒"; }
        @Override public String meaning() { return "plow handle"; }
        @Override public String pinyinText()  { return "lěi"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 聿 — writing brush. */
    public record Yu_Brush() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "聿"; }
        @Override public String meaning() { return "writing brush"; }
        @Override public String pinyinText()  { return "yù"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 芈 — bleat, surname. */
    public record Mi_Bleat() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "芈"; }
        @Override public String meaning() { return "bleat, surname"; }
        @Override public String pinyinText()  { return "mǐ"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 歹 — bad, death remains. */
    public record Dai() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "歹"; }
        @Override public String meaning() { return "bad, death remains"; }
        @Override public String pinyinText()  { return "dǎi"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 冉 — slowly, gradually. */
    public record Ran() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "冉"; }
        @Override public String meaning() { return "slowly, gradually"; }
        @Override public String pinyinText()  { return "rǎn"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    // ── Singleton instances ───────────────────────────────────

    public static final He          HE          = new He();
    public static final Mi          MI          = new Mi();
    public static final Guo         GUO         = new Guo();

    public static final Nong        NONG        = new Nong();
    public static final Gua         GUA         = new Gua();
    public static final Shu         SHU         = new Shu();
    public static final Zhu_Trunk   ZHU_TRUNK   = new Zhu_Trunk();
    public static final Wei_Not     WEI_NOT     = new Wei_Not();
    public static final Mo          MO          = new Mo();
    public static final Ben         BEN         = new Ben();
    public static final Mu_Tree     MU_TREE     = new Mu_Tree();
    public static final Shu_Art     SHU_ART     = new Shu_Art();
    public static final Jian_Select JIAN_SELECT = new Jian_Select();
    public static final Lei_Plow    LEI_PLOW    = new Lei_Plow();
    public static final Yu_Brush    YU_BRUSH    = new Yu_Brush();
    public static final Mi_Bleat    MI_BLEAT    = new Mi_Bleat();
    public static final Dai         DAI         = new Dai();
    public static final Ran         RAN         = new Ran();

    /** 丛 — cluster, thicket. */
    public record Cong_Cluster() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4E1B"; }
        @Override public String meaning() { return "cluster, thicket"; }
        @Override public String pinyinText()  { return "c\u00F3ng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Cong_Cluster CONG_CLUSTER = new Cong_Cluster();

    /** 兰 — orchid. */
    public record Lan() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5170"; }
        @Override public String meaning() { return "orchid"; }
        @Override public String pinyinText()  { return "l\u00E1n"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Lan LAN = new Lan();

    /** \u9EA6 — wheat. */
    public record Mai() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u9EA6"; }
        @Override public String meaning() { return "wheat"; }
        @Override public String pinyinText()  { return "m\u00E0i"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Mai MAI = new Mai();

    /** \u8C46 — bean. */
    public record Dou_Bean() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u8C46"; }
        @Override public String meaning() { return "bean"; }
        @Override public String pinyinText()  { return "d\u00F2u"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Dou_Bean DOU_BEAN = new Dou_Bean();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            HE, MI, GUO, NONG, GUA, SHU, ZHU_TRUNK, WEI_NOT, MO, BEN,
            MU_TREE, SHU_ART, JIAN_SELECT, LEI_PLOW, YU_BRUSH, MI_BLEAT, DAI, RAN,
            CONG_CLUSTER, LAN,
            MAI, DOU_BEAN);
}
