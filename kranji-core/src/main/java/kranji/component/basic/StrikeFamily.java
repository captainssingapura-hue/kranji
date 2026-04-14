package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import kranji.layout.Politeness;

import java.util.List;

/** 攴部 — Strike-related components. */
public final class StrikeFamily {
    private StrikeFamily() {}

    /** 攵 — 反文旁 (strike/tap). Derives from 攴 pū. */
    public record FanWenPang() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()      { return "攵"; }
        @Override public String name()       { return "反文旁"; }
        @Override public String standalone() { return "攴"; }
        @Override public String meaning()    { return "strike"; }
        @Override public String pinyinText()     { return "pū"; }
        @Override public int strokes()       { return 4; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Right) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final FanWenPang FAN_WEN_PANG = new FanWenPang();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(FAN_WEN_PANG);
}
