// PARTIALLY GENERATED — see overrides.
package kranji.singular.body.k;

import kranji.classification.BlockRole;
import kranji.layout.LayoutHint;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import kranji.library.LibraryMember;
import kranji.zi.CompositionLayout.FullEnclosure;
import kranji.zi.CompositionLayout.LeftRight;
import kranji.zi.CompositionLayout.TopBottom;
import kranji.zi.CompositionLayout.TopMiddleBottom;
import kranji.zi.SingularZi;

/** \u53E3 — mouth. Yielding in side/bottom/middle positions, inner-scaled as bottom/enclosure. */
public record Kou() implements LibraryMember<BasicSet>, SingularZi {
    public static final Kou INSTANCE = new Kou();

    @Override public String glyph() { return "\u53E3"; }
    @Override public String meaning() { return "mouth"; }
    @Override public String pinyinText() { return "k\u01D2u"; }
    @Override public int strokes() { return 3; }
    @Override public BasicSet library() { return BasicSet.INSTANCE; }

    @Override
    public Politeness politeness(BlockRole role) {
        if (role instanceof LeftRight.Left)         return Politeness.YIELDING;
        if (role instanceof LeftRight.Right)        return Politeness.YIELDING;
        if (role instanceof TopBottom.Bottom)       return Politeness.YIELDING;
        if (role instanceof TopMiddleBottom.Top)    return Politeness.YIELDING;
        if (role instanceof TopMiddleBottom.Middle) return Politeness.YIELDING;
        return Politeness.NEUTRAL;
    }

    @Override
    public LayoutHint hintFor(BlockRole role) {
        if (role instanceof TopBottom.Bottom)    return LayoutHint.innerScale(0.80, 0.80);
        if (role instanceof FullEnclosure.Inner) return LayoutHint.innerScale(0.55, 0.55);
        return null;
    }
}
