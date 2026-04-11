package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.classification.CharacterComposition.LeftRight;
import kranji.component.BasicComponent;
import kranji.layout.Politeness;

/** 食部 — Food-related components. */
public final class FoodFamily {
    private FoodFamily() {}

    /** 饣 — 食字旁 (food). Derives from 食 shí. */
    public record ShiZiPang() implements BasicComponent {
        @Override public String glyph()      { return "饣"; }
        @Override public String name()       { return "食字旁"; }
        @Override public String standalone() { return "食"; }
        @Override public String meaning()    { return "food"; }
        @Override public String pinyin()     { return "shí"; }
        @Override public int strokes()       { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }
    }

    public static final ShiZiPang SHI_ZI_PANG = new ShiZiPang();
}
