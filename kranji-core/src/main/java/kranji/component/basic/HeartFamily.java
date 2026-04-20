package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.zi.CompositionLayout.LeftRight;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import kranji.layout.Politeness;

import java.util.List;

/** 心部 — Heart-related components. */
public final class HeartFamily {
    private HeartFamily() {}

    /** 忄 — 竖心旁 (heart). Derives from 心 xīn. */
    public record ShuXinPang() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()      { return "忄"; }
        @Override public String name()       { return "竖心旁"; }
        @Override public String standalone() { return "心"; }
        @Override public String meaning()    { return "heart"; }
        @Override public String pinyinText()     { return "xīn"; }
        @Override public int strokes()       { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final ShuXinPang SHU_XIN_PANG = new ShuXinPang();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(SHU_XIN_PANG);
}
