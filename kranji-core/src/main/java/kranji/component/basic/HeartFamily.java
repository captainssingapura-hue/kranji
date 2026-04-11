package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.classification.CharacterComposition.LeftRight;
import kranji.component.BasicComponent;
import kranji.layout.Politeness;

/** 心部 — Heart-related components. */
public final class HeartFamily {
    private HeartFamily() {}

    /** 忄 — 竖心旁 (heart). Derives from 心 xīn. */
    public record ShuXinPang() implements BasicComponent {
        @Override public String glyph()      { return "忄"; }
        @Override public String name()       { return "竖心旁"; }
        @Override public String standalone() { return "心"; }
        @Override public String meaning()    { return "heart"; }
        @Override public String pinyin()     { return "xīn"; }
        @Override public int strokes()       { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }
    }

    public static final ShuXinPang SHU_XIN_PANG = new ShuXinPang();
}
