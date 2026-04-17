package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import kranji.layout.Politeness;

import java.util.List;

/** 人部 — Person-related components. */
public final class PersonFamily {
    private PersonFamily() {}

    /** 亻 — 单人旁 (person). Derives from 人 rén. */
    public record DanRenPang() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()      { return "亻"; }
        @Override public String name()       { return "单人旁"; }
        @Override public String standalone() { return "人"; }
        @Override public String meaning()    { return "person"; }
        @Override public String pinyinText()     { return "rén"; }
        @Override public int strokes()       { return 2; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    // 彳 (chì) — "step". Previously modeled as SingularPart ShuangRenPang
    // here; 彳 is a semantically valid standalone Zi, so the canonical
    // record is the SingularZi kranji.singular.tools.ToolsAndVessels.CHI_STEP.
    // Callers should reference CHI_STEP directly.

    public static final DanRenPang DAN_REN_PANG = new DanRenPang();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(DAN_REN_PANG);
}
