package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.classification.CharacterComposition.LeftRight;
import kranji.component.BasicComponent;
import kranji.layout.Politeness;

/** 人部 — Person-related components. */
public final class PersonFamily {
    private PersonFamily() {}

    /** 亻 — 单人旁 (person). Derives from 人 rén. */
    public record DanRenPang() implements BasicComponent {
        @Override public String glyph()      { return "亻"; }
        @Override public String name()       { return "单人旁"; }
        @Override public String standalone() { return "人"; }
        @Override public String meaning()    { return "person"; }
        @Override public String pinyin()     { return "rén"; }
        @Override public int strokes()       { return 2; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }
    }

    /** 彳 — 双人旁 (step). */
    public record ShuangRenPang() implements BasicComponent {
        @Override public String glyph()      { return "彳"; }
        @Override public String name()       { return "双人旁"; }
        @Override public String standalone() { return "彳"; }
        @Override public String meaning()    { return "step"; }
        @Override public String pinyin()     { return "chì"; }
        @Override public int strokes()       { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }
    }

    public static final DanRenPang DAN_REN_PANG = new DanRenPang();
    public static final ShuangRenPang SHUANG_REN_PANG = new ShuangRenPang();
}
