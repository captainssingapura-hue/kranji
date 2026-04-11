package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.classification.CharacterComposition.LeftRight;
import kranji.component.BasicComponent;
import kranji.component.SingularZi;
import kranji.layout.Politeness;

/** 木部 — Wood/tree-related components. */
public final class WoodFamily {
    private WoodFamily() {}

    /** 木 — wood/tree. Used as a standalone radical and as a left component.
     *  Implements SingularZi so that {@code SingularZi.of("木")} and
     *  {@code WoodFamily.MU} resolve to the same instance. */
    public record Mu() implements SingularZi {
        @Override public String glyph()   { return "木"; }
        @Override public String meaning() { return "tree, wood"; }
        @Override public String pinyin()  { return "mù"; }
        @Override public int strokes()    { return 4; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }
    }

    public static final Mu MU = new Mu();

    static { SingularZi.register(MU); }
}
