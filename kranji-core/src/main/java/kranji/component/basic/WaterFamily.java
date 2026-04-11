package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.classification.CharacterComposition.LeftRight;
import kranji.component.BasicComponent;
import kranji.layout.Politeness;

/** 水部 — Water and ice-related components. */
public final class WaterFamily {
    private WaterFamily() {}

    /** 氵 — 三点水 (water). Derives from 水 shuǐ. */
    public record SanDianShui() implements BasicComponent {
        @Override public String glyph()      { return "氵"; }
        @Override public String name()       { return "三点水"; }
        @Override public String standalone() { return "水"; }
        @Override public String meaning()    { return "water"; }
        @Override public String pinyin()     { return "shuǐ"; }
        @Override public int strokes()       { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }
    }

    /** 冫 — 两点水 (ice). Derives from 冰 bīng. */
    public record LiangDianShui() implements BasicComponent {
        @Override public String glyph()      { return "冫"; }
        @Override public String name()       { return "两点水"; }
        @Override public String standalone() { return "冰"; }
        @Override public String meaning()    { return "ice"; }
        @Override public String pinyin()     { return "bīng"; }
        @Override public int strokes()       { return 2; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }
    }

    public static final SanDianShui SAN_DIAN_SHUI = new SanDianShui();
    public static final LiangDianShui LIANG_DIAN_SHUI = new LiangDianShui();
}
