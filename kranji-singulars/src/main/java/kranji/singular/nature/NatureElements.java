package kranji.singular.nature;

import kranji.classification.BlockRole;
import kranji.zi.ComposedZi.LeftRight;
import kranji.zi.ComposedZi.TopBottom;
import kranji.library.LibraryMember;
import kranji.layout.LayoutHint;
import kranji.layout.LayoutHint.SvgShape;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import java.util.List;

/**
 * Stateless record implementations of {@link SingularZi} for Chinese
 * characters related to nature and natural elements.
 */
public final class NatureElements {

    private NatureElements() {}

    // 日, 月, 田 are defined in HintedZi (kranji-core) — the single source of truth.

    /** 山 — mountain. */
    public record Shan() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "山"; }
        @Override public String meaning() { return "mountain"; }
        @Override public String pinyin()  { return "shān"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 水 — water. */
    public record Shui() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "水"; }
        @Override public String meaning() { return "water"; }
        @Override public String pinyin()  { return "shuǐ"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 火 — fire. Yielding as left. */
    public record Huo() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "火"; }
        @Override public String meaning() { return "fire"; }
        @Override public String pinyin()  { return "huǒ"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }
    }

    /** 土 — earth, soil. Yielding as left; uses 提土旁 SVG when left. */
    public record Tu() implements LibraryMember<BasicSet> {
        /**
         * 提土旁 — the left-radical form of 土.
         * Bottom stroke changes from horizontal (横) to rising (提).
         * Authored in a 100×100 viewBox, Y-down from top-left.
         */
        private static final SvgShape TI_TU_PANG = new SvgShape(
                // Vertical stroke (丨) — center, shortened to reduce intersection
                "M 44 5 L 56 5 L 56 78 L 44 78 Z " +
                // Horizontal stroke (一) — top
                "M 15 30 L 85 30 L 85 38 L 15 38 Z " +
                // Rising stroke (提) — shorter and slimmer, painted last
                "M 15 98 L 23 104 L 75 72 L 68 66 Z",
                100, 100);

        @Override public String glyph()   { return "土"; }
        @Override public String meaning() { return "earth, soil"; }
        @Override public String pinyin()  { return "tǔ"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }

        @Override
        public LayoutHint hintFor(BlockRole role) {
            if (role instanceof LeftRight.Left) {
                return LayoutHint.politeSvgGlyph(Politeness.YIELDING, "土", TI_TU_PANG);
            }
            return null;
        }
    }

    /** 石 — stone. Yielding as left. */
    public record Shi() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "石"; }
        @Override public String meaning() { return "stone"; }
        @Override public String pinyin()  { return "shí"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }
    }

    /** 雨 — rain. */
    public record Yu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "雨"; }
        @Override public String meaning() { return "rain"; }
        @Override public String pinyin()  { return "yǔ"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 风 — wind. */
    public record Feng() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "风"; }
        @Override public String meaning() { return "wind"; }
        @Override public String pinyin()  { return "fēng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 云 — cloud. */
    public record Yun() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "云"; }
        @Override public String meaning() { return "cloud"; }
        @Override public String pinyin()  { return "yún"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 天 — sky, heaven. */
    public record Tian_Sky() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "天"; }
        @Override public String meaning() { return "sky, heaven"; }
        @Override public String pinyin()  { return "tiān"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 雪 — snow. */
    public record Xue() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "雪"; }
        @Override public String meaning() { return "snow"; }
        @Override public String pinyin()  { return "xuě"; }
        @Override public int strokes()    { return 11; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 雷 — thunder. */
    public record Lei() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "雷"; }
        @Override public String meaning() { return "thunder"; }
        @Override public String pinyin()  { return "léi"; }
        @Override public int strokes()    { return 13; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 电 — lightning, electricity. */
    public record Dian() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "电"; }
        @Override public String meaning() { return "lightning, electricity"; }
        @Override public String pinyin()  { return "diàn"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 星 — star. */
    public record Xing() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "星"; }
        @Override public String meaning() { return "star"; }
        @Override public String pinyin()  { return "xīng"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 气 — air, gas. */
    public record Qi() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "气"; }
        @Override public String meaning() { return "air, gas"; }
        @Override public String pinyin()  { return "qì"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 泉 — spring (water). */
    public record Quan() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "泉"; }
        @Override public String meaning() { return "spring (water)"; }
        @Override public String pinyin()  { return "quán"; }
        @Override public int strokes()    { return 9; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 谷 — valley. */
    public record Gu() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "谷"; }
        @Override public String meaning() { return "valley"; }
        @Override public String pinyin()  { return "gǔ"; }
        @Override public int strokes()    { return 7; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 川 — river. */
    public record Chuan() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "川"; }
        @Override public String meaning() { return "river"; }
        @Override public String pinyin()  { return "chuān"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 井 — well. */
    public record Jing() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "井"; }
        @Override public String meaning() { return "well"; }
        @Override public String pinyin()  { return "jǐng"; }
        @Override public int strokes()    { return 4; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 光 — light. */
    public record Guang() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "光"; }
        @Override public String meaning() { return "light"; }
        @Override public String pinyin()  { return "guāng"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    /** 冰 — ice. */
    public record Bing() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "冰"; }
        @Override public String meaning() { return "ice"; }
        @Override public String pinyin()  { return "bīng"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    // ── Singleton instances ───────────────────────────────────

    public static final Shan       SHAN       = new Shan();
    public static final Shui       SHUI       = new Shui();
    public static final Huo        HUO        = new Huo();
    public static final Tu         TU         = new Tu();
    public static final Shi        SHI        = new Shi();
    public static final Yu         YU         = new Yu();
    public static final Feng       FENG       = new Feng();
    public static final Yun        YUN        = new Yun();
    public static final Tian_Sky   TIAN_SKY   = new Tian_Sky();
    public static final Xue        XUE        = new Xue();
    public static final Lei        LEI        = new Lei();
    public static final Dian       DIAN       = new Dian();
    public static final Xing       XING       = new Xing();
    public static final Qi         QI         = new Qi();
    public static final Quan       QUAN       = new Quan();
    public static final Gu         GU         = new Gu();
    public static final Chuan      CHUAN      = new Chuan();
    public static final Jing       JING       = new Jing();
    public static final Guang      GUANG      = new Guang();
    public static final Bing       BING       = new Bing();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            SHAN, SHUI, HUO, TU, SHI, YU, FENG, YUN, TIAN_SKY,
            XUE, LEI, DIAN, XING, QI, QUAN, GU, CHUAN, JING, GUANG, BING);
}
