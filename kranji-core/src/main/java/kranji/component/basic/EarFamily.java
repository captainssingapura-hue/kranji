package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.classification.CharacterComposition.LeftRight;
import kranji.component.BasicComponent;
import kranji.layout.Politeness;

/** 阜部 / 邑部 — Ear-radical components (left and right 阝). */
public final class EarFamily {
    private EarFamily() {}

    /** 阝(left) — 左耳旁 (mound/hill). Derives from 阜 fù. */
    public record ZuoErPang() implements BasicComponent {
        @Override public String glyph()      { return "阝"; }
        @Override public String name()       { return "左耳旁"; }
        @Override public String standalone() { return "阜"; }
        @Override public String meaning()    { return "mound"; }
        @Override public String pinyin()     { return "fù"; }
        @Override public int strokes()       { return 2; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }
    }

    /** 阝(right) — 右耳旁 (city). Derives from 邑 yì. */
    public record YouErPang() implements BasicComponent {
        @Override public String glyph()      { return "阝"; }
        @Override public String name()       { return "右耳旁"; }
        @Override public String standalone() { return "邑"; }
        @Override public String meaning()    { return "city"; }
        @Override public String pinyin()     { return "yì"; }
        @Override public int strokes()       { return 2; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Right) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }
    }

    public static final ZuoErPang ZUO_ER_PANG = new ZuoErPang();
    public static final YouErPang YOU_ER_PANG = new YouErPang();
}
