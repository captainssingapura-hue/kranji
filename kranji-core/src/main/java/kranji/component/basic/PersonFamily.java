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

    /** 彳 — 双人旁 (step). */
    public record ShuangRenPang() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()      { return "彳"; }
        @Override public String name()       { return "双人旁"; }
        @Override public String standalone() { return "彳"; }
        @Override public String meaning()    { return "step"; }
        @Override public String pinyinText()     { return "chì"; }
        @Override public int strokes()       { return 3; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.DEFERENTIAL;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final DanRenPang DAN_REN_PANG = new DanRenPang();
    public static final ShuangRenPang SHUANG_REN_PANG = new ShuangRenPang();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(DAN_REN_PANG, SHUANG_REN_PANG);
}
