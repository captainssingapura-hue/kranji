package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.zi.CompositionLayout.LeftRight;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import kranji.layout.Politeness;

import java.util.List;

/** 木部 — Wood/tree-related components. */
public final class WoodFamily {
    private WoodFamily() {}

    /** 木 — wood/tree. Used as a standalone radical and as a left component. */
    public record Mu() implements LibraryMember<BasicSet>, kranji.zi.SingularPart, kranji.zi.SingularZi {
        @Override public String glyph()   { return "木"; }
        @Override public String meaning() { return "tree, wood"; }
        @Override public String pinyinText()  { return "mù"; }
        @Override public int strokes()    { return 4; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final Mu MU = new Mu();

    public static final List<LibraryMember<BasicSet>> ALL = List.of(MU);
}
