package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.zi.ComposedZi.LeftRight;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import kranji.layout.Politeness;

import java.util.List;

/** 丝部 — Silk/thread-related components. */
public final class SilkFamily {
    private SilkFamily() {}

    /** 纟 — 绞丝旁 (silk/thread). Derives from 丝 sī. */
    public record JiaoSiPang() implements LibraryMember<BasicSet> {
        @Override public String glyph()      { return "纟"; }
        @Override public String name()       { return "绞丝旁"; }
        @Override public String standalone() { return "丝"; }
        @Override public String meaning()    { return "silk"; }
        @Override public String pinyin()     { return "sī"; }
        @Override public int strokes()       { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final JiaoSiPang JIAO_SI_PANG = new JiaoSiPang();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(JIAO_SI_PANG);
}
