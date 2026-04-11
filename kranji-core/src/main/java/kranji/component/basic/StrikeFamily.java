package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.classification.CharacterComposition.LeftRight;
import kranji.component.BasicComponent;
import kranji.layout.Politeness;

/** 攴部 — Strike-related components. */
public final class StrikeFamily {
    private StrikeFamily() {}

    /** 攵 — 反文旁 (strike/tap). Derives from 攴 pū. */
    public record FanWenPang() implements BasicComponent {
        @Override public String glyph()      { return "攵"; }
        @Override public String name()       { return "反文旁"; }
        @Override public String standalone() { return "攴"; }
        @Override public String meaning()    { return "strike"; }
        @Override public String pinyin()     { return "pū"; }
        @Override public int strokes()       { return 4; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Right) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }
    }

    public static final FanWenPang FAN_WEN_PANG = new FanWenPang();
}
