// PARTIALLY GENERATED — see overrides.
package kranji.singular.nature.zero;

import kranji.classification.BlockRole;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import kranji.library.LibraryMember;
import kranji.zi.CompositionLayout.LeftRight;
import kranji.zi.SingularZi;

/** \u6708 — moon/month. Yielding as left. */
public record Yue() implements LibraryMember<BasicSet>, SingularZi {
    public static final Yue INSTANCE = new Yue();

    @Override public String glyph() { return "\u6708"; }
    @Override public String meaning() { return "moon, month"; }
    @Override public String pinyinText() { return "yu\u00E8"; }
    @Override public int strokes() { return 4; }
    @Override public BasicSet library() { return BasicSet.INSTANCE; }

    @Override
    public Politeness politeness(BlockRole role) {
        if (role instanceof LeftRight.Left) return Politeness.YIELDING;
        return Politeness.NEUTRAL;
    }
}
