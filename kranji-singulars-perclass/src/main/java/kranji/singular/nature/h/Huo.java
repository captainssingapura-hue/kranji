// PARTIALLY GENERATED — see overrides.
package kranji.singular.nature.h;

import kranji.classification.BlockRole;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import kranji.library.LibraryMember;
import kranji.zi.CompositionLayout.LeftRight;
import kranji.zi.SingularZi;

/** \u706B — fire. Yielding as left. */
public record Huo() implements LibraryMember<BasicSet>, SingularZi {
    public static final Huo INSTANCE = new Huo();

    @Override public String glyph() { return "\u706B"; }
    @Override public String meaning() { return "fire"; }
    @Override public String pinyinText() { return "hu\u01D2"; }
    @Override public int strokes() { return 4; }
    @Override public BasicSet library() { return BasicSet.INSTANCE; }

    @Override
    public Politeness politeness(BlockRole role) {
        if (role instanceof LeftRight.Left) return Politeness.YIELDING;
        return Politeness.NEUTRAL;
    }
}
