package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.classification.CharacterComposition.LeftRight;
import kranji.component.BasicComponent;
import kranji.layout.Politeness;

/** 手部 — Hand-related components. */
public final class HandFamily {
    private HandFamily() {}

    /** 扌 — 提手旁 (hand). Derives from 手 shǒu. */
    public record TiShouPang() implements BasicComponent {
        @Override public String glyph()      { return "扌"; }
        @Override public String name()       { return "提手旁"; }
        @Override public String standalone() { return "手"; }
        @Override public String meaning()    { return "hand"; }
        @Override public String pinyin()     { return "shǒu"; }
        @Override public int strokes()       { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }
    }

    public static final TiShouPang TI_SHOU_PANG = new TiShouPang();
}
