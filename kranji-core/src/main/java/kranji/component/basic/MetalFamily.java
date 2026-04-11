package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.classification.CharacterComposition.LeftRight;
import kranji.component.BasicComponent;
import kranji.layout.Politeness;

/** 金部 — Metal-related components. */
public final class MetalFamily {
    private MetalFamily() {}

    /** 钅 — 金字旁 (metal/gold). Derives from 金 jīn. */
    public record JinZiPang() implements BasicComponent {
        @Override public String glyph()      { return "钅"; }
        @Override public String name()       { return "金字旁"; }
        @Override public String standalone() { return "金"; }
        @Override public String meaning()    { return "metal"; }
        @Override public String pinyin()     { return "jīn"; }
        @Override public int strokes()       { return 5; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }
    }

    public static final JinZiPang JIN_ZI_PANG = new JinZiPang();
}
