// PARTIALLY GENERATED — see overrides.
package kranji.singular.nature.t;

import kranji.classification.BlockRole;
import kranji.layout.LayoutHint;
import kranji.layout.LayoutHint.SvgShape;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import kranji.library.LibraryMember;
import kranji.zi.CompositionLayout.LeftRight;
import kranji.zi.SingularZi;

/** \u571F — earth, soil. Yielding as left; uses \u63D0\u571F\u65C1 SVG when left. */
public record Tu() implements LibraryMember<BasicSet>, SingularZi {
    public static final Tu INSTANCE = new Tu();

    /**
     * \u63D0\u571F\u65C1 — the left-radical form of \u571F.
     * Bottom stroke changes from horizontal (\u6A2A) to rising (\u63D0).
     * Authored in a 100x100 viewBox, Y-down from top-left.
     */
    private static final SvgShape TI_TU_PANG = new SvgShape(
            // Vertical stroke (丨) — center, shortened to reduce intersection
            "M 44 5 L 56 5 L 56 78 L 44 78 Z " +
            // Horizontal stroke (一) — top
            "M 15 30 L 85 30 L 85 38 L 15 38 Z " +
            // Rising stroke (提) — shorter and slimmer, painted last
            "M 15 98 L 23 104 L 75 72 L 68 66 Z",
            100, 100);

    @Override public String glyph() { return "\u571F"; }
    @Override public String meaning() { return "earth, soil"; }
    @Override public String pinyinText() { return "t\u01D4"; }
    @Override public int strokes() { return 3; }
    @Override public BasicSet library() { return BasicSet.INSTANCE; }

    @Override
    public Politeness politeness(BlockRole role) {
        if (role instanceof LeftRight.Left) return Politeness.YIELDING;
        return Politeness.NEUTRAL;
    }

    @Override
    public LayoutHint hintFor(BlockRole role) {
        if (role instanceof LeftRight.Left) {
            return LayoutHint.politeSvgGlyph(Politeness.YIELDING, "\u571F", TI_TU_PANG);
        }
        return null;
    }
}
