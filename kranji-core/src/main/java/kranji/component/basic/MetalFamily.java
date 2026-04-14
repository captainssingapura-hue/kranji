package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import kranji.layout.Politeness;

import java.util.List;

/** 金部 — Metal-related components. */
public final class MetalFamily {
    private MetalFamily() {}

    /** 钅 — 金字旁 (metal/gold). Derives from 金 jīn. */
    public record JinZiPang() implements LibraryMember<BasicSet> {
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

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final JinZiPang JIN_ZI_PANG = new JinZiPang();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(JIN_ZI_PANG);
}
