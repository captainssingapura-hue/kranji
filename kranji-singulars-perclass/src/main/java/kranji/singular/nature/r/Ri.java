// PARTIALLY GENERATED — see overrides.
package kranji.singular.nature.r;

import kranji.classification.BlockRole;
import kranji.layout.LayoutHint;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import kranji.library.LibraryMember;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.zi.ComposedBlock.TopBottom;
import kranji.zi.SingularZi;

/** \u65E5 — sun/day. Yielding as left/bottom, inner-scaled as bottom. */
public record Ri() implements LibraryMember<BasicSet>, SingularZi {
    public static final Ri INSTANCE = new Ri();

    @Override public String glyph() { return "\u65E5"; }
    @Override public String meaning() { return "sun, day"; }
    @Override public String pinyinText() { return "r\u00EC"; }
    @Override public int strokes() { return 4; }
    @Override public BasicSet library() { return BasicSet.INSTANCE; }

    @Override
    public Politeness politeness(BlockRole role) {
        if (role instanceof LeftRight.Left)   return Politeness.YIELDING;
        if (role instanceof TopBottom.Bottom) return Politeness.YIELDING;
        return Politeness.NEUTRAL;
    }

    @Override
    public LayoutHint hintFor(BlockRole role) {
        if (role instanceof TopBottom.Bottom) return LayoutHint.innerScale(0.85, 0.80);
        return null;
    }
}
