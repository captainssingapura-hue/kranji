package kranji.singular.structures;

import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import java.util.List;

/**
 * Stateless record implementations of {@link SingularBlock} for Chinese
 * characters related to buildings, shelters, and enclosures.
 */
public final class Structures {

    private Structures() {}

    // ── Record types ─────────────────────────────────────────

    /** 广 — wide, shelter. */
    public record Guang() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "广"; }
        @Override public String meaning() { return "wide, shelter"; }
        @Override public String pinyinText()  { return "guǎng"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 厂 — factory, cliff. */
    public record Chang_Factory() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "厂"; }
        @Override public String meaning() { return "factory, cliff"; }
        @Override public String pinyinText()  { return "chǎng"; }
        @Override public int strokes()    { return 2; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 穴 — cave, hole. */
    public record Xue() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "穴"; }
        @Override public String meaning() { return "cave, hole"; }
        @Override public String pinyinText()  { return "xué"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 户 — door, household. */
    public record Hu() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "户"; }
        @Override public String meaning() { return "door, household"; }
        @Override public String pinyinText()  { return "hù"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 尸 — body, corpse. */
    public record Shi_Corpse() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "尸"; }
        @Override public String meaning() { return "body, corpse"; }
        @Override public String pinyinText()  { return "shī"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 亭 — pavilion. */
    public record Ting() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "亭"; }
        @Override public String meaning() { return "pavilion"; }
        @Override public String pinyinText()  { return "tíng"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 台 — platform. */
    public record Tai() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "台"; }
        @Override public String meaning() { return "platform"; }
        @Override public String pinyinText()  { return "tái"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 寸 — inch. */
    public record Cun_Inch() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "寸"; }
        @Override public String meaning() { return "inch"; }
        @Override public String pinyinText()  { return "cùn"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 尺 — ruler, foot. */
    public record Chi_Ruler() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "尺"; }
        @Override public String meaning() { return "ruler, foot"; }
        @Override public String pinyinText()  { return "chǐ"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 囱 — chimney, window. */
    public record Cong_Chimney() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "囱"; }
        @Override public String meaning() { return "chimney, window"; }
        @Override public String pinyinText()  { return "cōng"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 巨 — huge, enormous. */
    public record Ju() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "巨"; }
        @Override public String meaning() { return "huge, enormous"; }
        @Override public String pinyinText()  { return "jù"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 屯 — village, station. */
    public record Tun() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "屯"; }
        @Override public String meaning() { return "village, station"; }
        @Override public String pinyinText()  { return "tún"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 巴 — cling to, hope. */
    public record Ba_Cling() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "巴"; }
        @Override public String meaning() { return "cling to, hope"; }
        @Override public String pinyinText()  { return "bā"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    // ── Singleton instances ──────────────────────────────────

    public static final Guang         GUANG         = new Guang();
    public static final Chang_Factory CHANG_FACTORY = new Chang_Factory();
    public static final Xue           XUE           = new Xue();
    public static final Hu            HU            = new Hu();
    public static final Shi_Corpse    SHI_CORPSE    = new Shi_Corpse();

    public static final Ting          TING          = new Ting();
    public static final Tai           TAI           = new Tai();
    public static final Cun_Inch      CUN_INCH      = new Cun_Inch();
    public static final Chi_Ruler     CHI_RULER     = new Chi_Ruler();
    public static final Cong_Chimney  CONG_CHIMNEY  = new Cong_Chimney();
    public static final Ju            JU            = new Ju();
    public static final Tun           TUN           = new Tun();
    public static final Ba_Cling      BA_CLING      = new Ba_Cling();

    /** 仓 — warehouse, granary. */
    public record Cang() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u4ED3"; }
        @Override public String meaning() { return "warehouse, granary"; }
        @Override public String pinyinText()  { return "c\u0101ng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Cang CANG = new Cang();

    /** 市 — market, city. */
    public record Shi_Market() implements LibraryMember<BasicSet>, kranji.zi.SingularZi {
        @Override public String glyph()   { return "\u5E02"; }
        @Override public String meaning() { return "market, city"; }
        @Override public String pinyinText()  { return "sh\u00EC"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Shi_Market SHI_MARKET = new Shi_Market();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            GUANG, CHANG_FACTORY, XUE, HU, SHI_CORPSE, TING, TAI,
            CUN_INCH, CHI_RULER, CONG_CHIMNEY, JU, TUN, BA_CLING,
            CANG, SHI_MARKET);
}
