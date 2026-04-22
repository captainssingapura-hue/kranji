// PARTIALLY GENERATED — see overrides.
package kranji.singular.nature.t;

import kranji.classification.BlockRole;
import kranji.layout.LayoutHint;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import kranji.library.LibraryMember;
import kranji.zi.CompositionLayout.LeftRight;
import kranji.zi.CompositionLayout.TopBottom;
import kranji.zi.SingularZi;

/** \u7530 — field. Yielding as left/bottom, inner-scaled as bottom. */
public record Tian() implements LibraryMember<BasicSet>, SingularZi {
    public static final Tian INSTANCE = new Tian();

    @Override public String glyph() { return "\u7530"; }
    @Override public String meaning() { return "field"; }
    @Override public String pinyinText() { return "ti\u00E1n"; }
    @Override public int strokes() { return 5; }
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
