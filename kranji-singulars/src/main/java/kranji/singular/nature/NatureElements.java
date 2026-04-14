package kranji.singular.nature;

import kranji.classification.BlockRole;
import kranji.zi.ComposedBlock.FullEnclosure;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.zi.ComposedBlock.TopBottom;
import kranji.zi.ComposedBlock.TopMiddleBottom;
import kranji.library.LibraryMember;
import kranji.layout.LayoutHint;
import kranji.layout.LayoutHint.SvgShape;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import java.util.List;

/**
 * Stateless record implementations of {@link SingularBlock} for Chinese
 * characters related to nature and natural elements.
 */
public final class NatureElements {

    private NatureElements() {}

    /** 日 — sun/day. Yielding as left/bottom, inner-scaled as bottom. */
    public record Ri() implements LibraryMember<BasicSet> {
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
        @Override public String glyph()   { return "日"; }
        @Override public String meaning() { return "sun, day"; }
        @Override public String pinyin()  { return "rì"; }
        @Override public int strokes()    { return 4; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left)   return Politeness.YIELDING;
            if (role instanceof TopBottom.Bottom) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }

        @Override
        public LayoutHint hintFor(BlockRole role) {
            if (role instanceof TopBottom.Bottom) return LayoutHint.innerScale(0.85, 0.80);
            return null;
        }
    }

    /** 月 — moon/month. Yielding as left. */
    public record Yue() implements LibraryMember<BasicSet> {
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
        @Override public String glyph()   { return "月"; }
        @Override public String meaning() { return "moon, month"; }
        @Override public String pinyin()  { return "yuè"; }
        @Override public int strokes()    { return 4; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }
    }

    /** 田 — field. Yielding as left/bottom, inner-scaled as bottom. */
    public record Tian() implements LibraryMember<BasicSet> {
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
        @Override public String glyph()   { return "田"; }
        @Override public String meaning() { return "field"; }
        @Override public String pinyin()  { return "tián"; }
        @Override public int strokes()    { return 5; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left)   return Politeness.YIELDING;
            if (role instanceof TopBottom.Bottom) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }

        @Override
        public LayoutHint hintFor(BlockRole role) {
            if (role instanceof TopBottom.Bottom) return LayoutHint.innerScale(0.85, 0.80);
            return null;
        }
    }

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

    /** 电 — lightning, electricity. */
    public record Dian() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "电"; }
        @Override public String meaning() { return "lightning, electricity"; }
        @Override public String pinyin()  { return "diàn"; }
        @Override public int strokes()    { return 5; }
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

    /** 丘 — hill/mound. */
    public record Qiu_Hill() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "丘"; }
        @Override public String meaning() { return "hill, mound"; }
        @Override public String pinyin()  { return "qiū"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Qiu_Hill QIU_HILL = new Qiu_Hill();

    /** 永 — eternal/forever. */
    public record Yong_Eternal() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "永"; }
        @Override public String meaning() { return "eternal, forever"; }
        @Override public String pinyin()  { return "yǒng"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Yong_Eternal YONG_ETERNAL = new Yong_Eternal();

    /** 氐 — foundation/bottom. */
    public record Di_Foundation() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "氐"; }
        @Override public String meaning() { return "foundation, bottom"; }
        @Override public String pinyin()  { return "dī"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Di_Foundation DI_FOUNDATION = new Di_Foundation();

    /** 夕 — evening/sunset. */
    public record Xi_Evening() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "夕"; }
        @Override public String meaning() { return "evening, sunset"; }
        @Override public String pinyin()  { return "xī"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Xi_Evening XI_EVENING = new Xi_Evening();

    /** 凸 — convex/protruding. */
    public record Tu_Convex() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "凸"; }
        @Override public String meaning() { return "convex, protruding"; }
        @Override public String pinyin()  { return "tū"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Tu_Convex TU_CONVEX = new Tu_Convex();

    /** 凹 — concave/hollow. */
    public record Ao_Concave() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "凹"; }
        @Override public String meaning() { return "concave, hollow"; }
        @Override public String pinyin()  { return "āo"; }
        @Override public int strokes()    { return 5; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Ao_Concave AO_CONCAVE = new Ao_Concave();

    /** 垂 — droop/hang down. */
    public record Chui() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "垂"; }
        @Override public String meaning() { return "droop, hang down"; }
        @Override public String pinyin()  { return "chuí"; }
        @Override public int strokes()    { return 8; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Chui CHUI = new Chui();

    /** 州 — state/prefecture. */
    public record Zhou_State() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "州"; }
        @Override public String meaning() { return "state, prefecture"; }
        @Override public String pinyin()  { return "zhōu"; }
        @Override public int strokes()    { return 6; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Zhou_State ZHOU_STATE = new Zhou_State();

    /** 兀 — lofty/bald. */
    public record Wu_Lofty() implements LibraryMember<BasicSet> {
        @Override public String glyph()   { return "兀"; }
        @Override public String meaning() { return "lofty, bald"; }
        @Override public String pinyin()  { return "wù"; }
        @Override public int strokes()    { return 3; }
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }
    public static final Wu_Lofty WU_LOFTY = new Wu_Lofty();

    // ── Singleton instances ───────────────────────────────────

    public static final Ri         RI         = new Ri();
    public static final Yue        YUE        = new Yue();
    public static final Tian       TIAN       = new Tian();
    public static final Shan       SHAN       = new Shan();
    public static final Shui       SHUI       = new Shui();
    public static final Huo        HUO        = new Huo();
    public static final Tu         TU         = new Tu();
    public static final Shi        SHI        = new Shi();
    public static final Yu         YU         = new Yu();
    public static final Yun        YUN        = new Yun();
    public static final Tian_Sky   TIAN_SKY   = new Tian_Sky();
    public static final Dian       DIAN       = new Dian();
    public static final Qi         QI         = new Qi();

    public static final Gu         GU         = new Gu();
    public static final Chuan      CHUAN      = new Chuan();
    public static final Jing       JING       = new Jing();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(
            RI, YUE, TIAN, SHAN, SHUI, HUO, TU, SHI, YU, YUN, TIAN_SKY,
            DIAN, QI, GU, CHUAN, JING,
            QIU_HILL, YONG_ETERNAL, DI_FOUNDATION, XI_EVENING, TU_CONVEX,
            AO_CONCAVE, CHUI, ZHOU_STATE, WU_LOFTY);
}
