package kranji.component.basic;

import kranji.classification.BlockRole;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.library.LibraryMember;
import kranji.library.BasicSet;
import kranji.layout.Politeness;

import java.util.List;

/**
 * 耳部 — Ear-radical component 阝.
 *
 * <p>Left and right 阝 share a visual glyph but carry different derivation
 * semantics: left-阝 derives from 阜 (fù, "mound"), right-阝 from 邑 (yì,
 * "city"). Rather than split into two records (which would collide on
 * glyph in the strong-typed registry), a single {@link ErPang} record
 * varies its identity accessors by {@link BlockRole}.</p>
 */
public final class EarFamily {
    private EarFamily() {}

    /**
     * 阝 — 耳旁. Role-aware: the {@code name(role)}, {@code standalone(role)},
     * {@code meaning(role)}, {@code pinyinText(role)} overloads return the
     * left-ear (阜/fù/mound) or right-ear (邑/yì/city) variant based on the
     * structural position; the zero-arg accessors return neutral values.
     */
    public record ErPang() implements LibraryMember<BasicSet>, kranji.zi.SingularPart {
        @Override public String glyph()   { return "阝"; }
        @Override public int    strokes() { return 2; }

        @Override public String name()       { return "耳旁"; }
        @Override public String standalone() { return "阝"; }
        @Override public String meaning()    { return "ear radical"; }
        @Override public String pinyinText() { return ""; }

        @Override public String name(BlockRole role) {
            if (role instanceof LeftRight.Left)  return "左耳旁";
            if (role instanceof LeftRight.Right) return "右耳旁";
            return name();
        }
        @Override public String standalone(BlockRole role) {
            if (role instanceof LeftRight.Left)  return "阜";
            if (role instanceof LeftRight.Right) return "邑";
            return standalone();
        }
        @Override public String meaning(BlockRole role) {
            if (role instanceof LeftRight.Left)  return "mound";
            if (role instanceof LeftRight.Right) return "city";
            return meaning();
        }
        @Override public String pinyinText(BlockRole role) {
            if (role instanceof LeftRight.Left)  return "fù";
            if (role instanceof LeftRight.Right) return "yì";
            return pinyinText();
        }

        /**
         * 阝 always yields space to its partner, regardless of which side
         * it occupies — hence deferential on either Left or Right.
         */
        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left || role instanceof LeftRight.Right) {
                return Politeness.DEFERENTIAL;
            }
            return Politeness.NEUTRAL;
        }

        @Override public BasicSet library() { return BasicSet.INSTANCE; }
    }

    public static final ErPang ER_PANG = new ErPang();

    /** @deprecated Alias of {@link #ER_PANG} — kept for back-compat. Role
     *  is now carried by slot position in {@link LeftRight}. */
    @Deprecated public static final ErPang ZUO_ER_PANG = ER_PANG;
    /** @deprecated Alias of {@link #ER_PANG} — kept for back-compat. Role
     *  is now carried by slot position in {@link LeftRight}. */
    @Deprecated public static final ErPang YOU_ER_PANG = ER_PANG;

    public static final List<LibraryMember<BasicSet>> ALL = List.of(ER_PANG);
}
