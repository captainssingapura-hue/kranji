package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import kranji.layout.Politeness;

import java.util.List;

/** 刀部 — Knife-related components. */
public final class KnifeFamily {
    private KnifeFamily() {}

    /** 刂 — 立刀旁 (knife). Derives from 刀 dāo. */
    public record LiDaoPang() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()      { return "刂"; }
        @Override public String name()       { return "立刀旁"; }
        @Override public String standalone() { return "刀"; }
        @Override public String meaning()    { return "knife"; }
        @Override public String pinyinText()     { return "dāo"; }
        @Override public int strokes()       { return 2; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Right) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final LiDaoPang LI_DAO_PANG = new LiDaoPang();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(LI_DAO_PANG);
}
