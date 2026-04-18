// PARTIALLY GENERATED — see overrides.
package kranji.singular.nature.sh;

import kranji.classification.BlockRole;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import kranji.library.LibraryMember;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.zi.SingularZi;

/** \u77F3 — stone. Yielding as left. */
public record Shi() implements LibraryMember<BasicSet>, SingularZi {
    public static final Shi INSTANCE = new Shi();

    @Override public String glyph() { return "\u77F3"; }
    @Override public String meaning() { return "stone"; }
    @Override public String pinyinText() { return "sh\u00ED"; }
    @Override public int strokes() { return 5; }
    @Override public BasicSet library() { return BasicSet.INSTANCE; }

    @Override
    public Politeness politeness(BlockRole role) {
        if (role instanceof LeftRight.Left) return Politeness.YIELDING;
        return Politeness.NEUTRAL;
    }
}
