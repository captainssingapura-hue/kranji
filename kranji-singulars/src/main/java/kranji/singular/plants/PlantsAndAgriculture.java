package kranji.singular.plants;

import kranji.classification.BlockRole;
import kranji.zi.ComposedZi.LeftRight;
import kranji.library.LibraryMember;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import java.util.List;

/**
 * Stateless record implementations of {@link SingularZi} for Chinese
 * characters related to plants and agriculture.
 */
public final class PlantsAndAgriculture {

    private PlantsAndAgriculture() {}

    // 木 is defined in WoodFamily (kranji-core) — the single source of truth.

    /** 禾 — grain, cereal. Yielding as left. */
    public record He() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "禾"; }
        @Override public String meaning() { return "grain, cereal"; }
        @Override public String pinyin()  { return "hé"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }
    }

    /** 米 — rice. */
    public record Mi() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "米"; }
        @Override public String meaning() { return "rice"; }
        @Override public String pinyin()  { return "mǐ"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 竹 — bamboo. */
    public record Zhu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "竹"; }
        @Override public String meaning() { return "bamboo"; }
        @Override public String pinyin()  { return "zhú"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 草 — grass. */
    public record Cao() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "草"; }
        @Override public String meaning() { return "grass"; }
        @Override public String pinyin()  { return "cǎo"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 花 — flower. */
    public record Hua() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "花"; }
        @Override public String meaning() { return "flower"; }
        @Override public String pinyin()  { return "huā"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 果 — fruit. */
    public record Guo() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "果"; }
        @Override public String meaning() { return "fruit"; }
        @Override public String pinyin()  { return "guǒ"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 叶 — leaf. */
    public record Ye() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "叶"; }
        @Override public String meaning() { return "leaf"; }
        @Override public String pinyin()  { return "yè"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 根 — root. */
    public record Gen() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "根"; }
        @Override public String meaning() { return "root"; }
        @Override public String pinyin()  { return "gēn"; }
        @Override public int strokes()    { return 10; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 林 — forest. */
    public record Lin() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "林"; }
        @Override public String meaning() { return "forest"; }
        @Override public String pinyin()  { return "lín"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 农 — farming. */
    public record Nong() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "农"; }
        @Override public String meaning() { return "farming"; }
        @Override public String pinyin()  { return "nóng"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 种 — seed, to plant. */
    public record Zhong() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "种"; }
        @Override public String meaning() { return "seed, to plant"; }
        @Override public String pinyin()  { return "zhǒng"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 麦 — wheat. */
    public record Mai() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "麦"; }
        @Override public String meaning() { return "wheat"; }
        @Override public String pinyin()  { return "mài"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 豆 — bean. */
    public record Dou() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "豆"; }
        @Override public String meaning() { return "bean"; }
        @Override public String pinyin()  { return "dòu"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 瓜 — melon. */
    public record Gua() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "瓜"; }
        @Override public String meaning() { return "melon"; }
        @Override public String pinyin()  { return "guā"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 束 — bundle, to bind. */
    public record Shu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "束"; }
        @Override public String meaning() { return "bundle, to bind"; }
        @Override public String pinyin()  { return "shù"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 朱 — vermillion (trunk of tree). */
    public record Zhu_Trunk() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "朱"; }
        @Override public String meaning() { return "vermillion (trunk of tree)"; }
        @Override public String pinyin()  { return "zhū"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 未 — not yet. */
    public record Wei_Not() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "未"; }
        @Override public String meaning() { return "not yet"; }
        @Override public String pinyin()  { return "wèi"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 末 — end, tip. */
    public record Mo() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "末"; }
        @Override public String meaning() { return "end, tip"; }
        @Override public String pinyin()  { return "mò"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 本 — root, origin. */
    public record Ben() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "本"; }
        @Override public String meaning() { return "root, origin"; }
        @Override public String pinyin()  { return "běn"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    // ── Singleton instances ───────────────────────────────────

    public static final He        HE        = new He();
    public static final Mi        MI        = new Mi();
    public static final Zhu       ZHU       = new Zhu();
    public static final Cao       CAO       = new Cao();
    public static final Hua       HUA       = new Hua();
    public static final Guo       GUO       = new Guo();
    public static final Ye        YE        = new Ye();
    public static final Gen       GEN       = new Gen();
    public static final Lin       LIN       = new Lin();
    public static final Nong      NONG      = new Nong();
    public static final Zhong     ZHONG     = new Zhong();
    public static final Mai       MAI       = new Mai();
    public static final Dou       DOU       = new Dou();
    public static final Gua       GUA       = new Gua();
    public static final Shu       SHU       = new Shu();
    public static final Zhu_Trunk ZHU_TRUNK = new Zhu_Trunk();
    public static final Wei_Not   WEI_NOT   = new Wei_Not();
    public static final Mo        MO        = new Mo();
    public static final Ben       BEN       = new Ben();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            HE, MI, ZHU, CAO, HUA, GUO, YE, GEN, LIN, NONG,
            ZHONG, MAI, DOU, GUA, SHU, ZHU_TRUNK, WEI_NOT, MO, BEN);
}
