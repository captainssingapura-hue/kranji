package kranji.singular.structures;

import kranji.component.SingularZi;

/**
 * Stateless record implementations of {@link SingularZi} for Chinese
 * characters related to buildings, shelters, and enclosures.
 */
public final class Structures {

    private Structures() {}

    // ── Record types ─────────────────────────────────────────

    /** 广 — wide, shelter. */
    public record Guang() implements SingularZi {
        @Override public String glyph()   { return "广"; }
        @Override public String meaning() { return "wide, shelter"; }
        @Override public String pinyin()  { return "guǎng"; }
        @Override public int strokes()    { return 3; }
    }

    /** 厂 — factory, cliff. */
    public record Chang_Factory() implements SingularZi {
        @Override public String glyph()   { return "厂"; }
        @Override public String meaning() { return "factory, cliff"; }
        @Override public String pinyin()  { return "chǎng"; }
        @Override public int strokes()    { return 2; }
    }

    /** 穴 — cave, hole. */
    public record Xue() implements SingularZi {
        @Override public String glyph()   { return "穴"; }
        @Override public String meaning() { return "cave, hole"; }
        @Override public String pinyin()  { return "xué"; }
        @Override public int strokes()    { return 5; }
    }

    /** 户 — door, household. */
    public record Hu() implements SingularZi {
        @Override public String glyph()   { return "户"; }
        @Override public String meaning() { return "door, household"; }
        @Override public String pinyin()  { return "hù"; }
        @Override public int strokes()    { return 4; }
    }

    /** 室 — room. */
    public record Shi_Room() implements SingularZi {
        @Override public String glyph()   { return "室"; }
        @Override public String meaning() { return "room"; }
        @Override public String pinyin()  { return "shì"; }
        @Override public int strokes()    { return 9; }
    }

    /** 家 — home, family. */
    public record Jia() implements SingularZi {
        @Override public String glyph()   { return "家"; }
        @Override public String meaning() { return "home, family"; }
        @Override public String pinyin()  { return "jiā"; }
        @Override public int strokes()    { return 10; }
    }

    /** 宫 — palace. */
    public record Gong_Palace() implements SingularZi {
        @Override public String glyph()   { return "宫"; }
        @Override public String meaning() { return "palace"; }
        @Override public String pinyin()  { return "gōng"; }
        @Override public int strokes()    { return 9; }
    }

    /** 城 — city. */
    public record Cheng() implements SingularZi {
        @Override public String glyph()   { return "城"; }
        @Override public String meaning() { return "city"; }
        @Override public String pinyin()  { return "chéng"; }
        @Override public int strokes()    { return 9; }
    }

    /** 邑 — city, settlement. */
    public record Yi_City() implements SingularZi {
        @Override public String glyph()   { return "邑"; }
        @Override public String meaning() { return "city, settlement"; }
        @Override public String pinyin()  { return "yì"; }
        @Override public int strokes()    { return 7; }
    }

    /** 尸 — body, corpse. */
    public record Shi_Corpse() implements SingularZi {
        @Override public String glyph()   { return "尸"; }
        @Override public String meaning() { return "body, corpse"; }
        @Override public String pinyin()  { return "shī"; }
        @Override public int strokes()    { return 3; }
    }

    /** 屋 — house. */
    public record Wu_House() implements SingularZi {
        @Override public String glyph()   { return "屋"; }
        @Override public String meaning() { return "house"; }
        @Override public String pinyin()  { return "wū"; }
        @Override public int strokes()    { return 9; }
    }

    /** 亭 — pavilion. */
    public record Ting() implements SingularZi {
        @Override public String glyph()   { return "亭"; }
        @Override public String meaning() { return "pavilion"; }
        @Override public String pinyin()  { return "tíng"; }
        @Override public int strokes()    { return 9; }
    }

    /** 台 — platform. */
    public record Tai() implements SingularZi {
        @Override public String glyph()   { return "台"; }
        @Override public String meaning() { return "platform"; }
        @Override public String pinyin()  { return "tái"; }
        @Override public int strokes()    { return 5; }
    }

    /** 寸 — inch. */
    public record Cun_Inch() implements SingularZi {
        @Override public String glyph()   { return "寸"; }
        @Override public String meaning() { return "inch"; }
        @Override public String pinyin()  { return "cùn"; }
        @Override public int strokes()    { return 3; }
    }

    /** 尺 — ruler, foot. */
    public record Chi_Ruler() implements SingularZi {
        @Override public String glyph()   { return "尺"; }
        @Override public String meaning() { return "ruler, foot"; }
        @Override public String pinyin()  { return "chǐ"; }
        @Override public int strokes()    { return 4; }
    }

    // ── Singleton instances ──────────────────────────────────

    public static final Guang         GUANG         = new Guang();
    public static final Chang_Factory CHANG_FACTORY = new Chang_Factory();
    public static final Xue           XUE           = new Xue();
    public static final Hu            HU            = new Hu();
    public static final Shi_Room      SHI_ROOM      = new Shi_Room();
    public static final Jia           JIA           = new Jia();
    public static final Gong_Palace   GONG_PALACE   = new Gong_Palace();
    public static final Cheng         CHENG         = new Cheng();
    public static final Yi_City       YI_CITY       = new Yi_City();
    public static final Shi_Corpse    SHI_CORPSE    = new Shi_Corpse();
    public static final Wu_House      WU_HOUSE      = new Wu_House();
    public static final Ting          TING          = new Ting();
    public static final Tai           TAI           = new Tai();
    public static final Cun_Inch      CUN_INCH      = new Cun_Inch();
    public static final Chi_Ruler     CHI_RULER     = new Chi_Ruler();
}
