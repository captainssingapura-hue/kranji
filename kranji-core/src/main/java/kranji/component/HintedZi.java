package kranji.component;

import kranji.classification.BlockRole;
import kranji.layout.LayoutHint;
import kranji.layout.Politeness;
import kranji.library.BasicSet;
import kranji.library.LibraryMember;
import kranji.zi.ComposedZi.FullEnclosure;
import kranji.zi.ComposedZi.LeftRight;
import kranji.zi.ComposedZi.TopBottom;
import kranji.zi.ComposedZi.TopMiddleBottom;

import java.util.List;

/**
 * SingularZi records with intrinsic position-specific layout hints.
 *
 * <p>These characters behave differently depending on where they sit
 * in a composition — narrower as a left part, shorter as a bottom part,
 * etc. Their {@link #hintFor} encodes calligraphic knowledge as
 * {@link LayoutHint.InnerPositionHint} (glyph fitting within a block),
 * while {@link #politeness} controls how much space the block itself
 * claims relative to siblings.</p>
 */
public final class HintedZi {

    private HintedZi() {}

    /** 日 — sun/day. Yielding as left/bottom, inner-scaled as bottom. */
    public record Ri() implements LibraryMember<BasicSet> {
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
        @Override public String glyph() { return "日"; }

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

    /** 口 — mouth. Yielding in side/bottom/middle positions, inner-scaled as right/bottom. */
    public record Kou() implements LibraryMember<BasicSet> {
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
        @Override public String glyph() { return "口"; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left)          return Politeness.YIELDING;
            if (role instanceof LeftRight.Right)         return Politeness.YIELDING;
            if (role instanceof TopBottom.Bottom)        return Politeness.YIELDING;
            if (role instanceof TopMiddleBottom.Top)     return Politeness.YIELDING;
            if (role instanceof TopMiddleBottom.Middle)  return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }

        @Override
        public LayoutHint hintFor(BlockRole role) {
            if (role instanceof LeftRight.Right)       return LayoutHint.innerScale(1.0, 0.65);
            if (role instanceof TopBottom.Bottom)      return LayoutHint.innerScale(0.80, 0.80);
            if (role instanceof FullEnclosure.Inner)   return LayoutHint.innerScale(0.55, 0.55);
            return null;
        }
    }

    /** 目 — eye. Yielding as left/bottom, inner-scaled as bottom. */
    public record Mu() implements LibraryMember<BasicSet> {
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
        @Override public String glyph() { return "目"; }

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

    /** 田 — field. Yielding as left/bottom, inner-scaled as bottom. */
    public record Tian() implements LibraryMember<BasicSet> {
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
        @Override public String glyph() { return "田"; }

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

    /** 月 — moon/month. Yielding as left. */
    public record Yue() implements LibraryMember<BasicSet> {
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
        @Override public String glyph() { return "月"; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }
    }

    /** 亡 — perish/flee. Yielding as top/middle in vertical stacks. */
    public record Wang() implements LibraryMember<BasicSet> {
        @Override public BasicSet library() { return BasicSet.INSTANCE; }
        @Override public String glyph() { return "亡"; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof TopMiddleBottom.Top)    return Politeness.YIELDING;
            if (role instanceof TopMiddleBottom.Middle) return Politeness.YIELDING;
            if (role instanceof TopBottom.Top)          return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }
    }

    // ── Singleton instances ────────────────────────────────────

    public static final Ri   RI   = new Ri();
    public static final Kou  KOU  = new Kou();
    public static final Mu   MU   = new Mu();
    public static final Tian TIAN = new Tian();
    public static final Yue  YUE  = new Yue();
    public static final Wang WANG = new Wang();

    /** All hinted components in this family. */
    public static final List<LibraryMember<BasicSet>> ALL = List.of(RI, KOU, MU, TIAN, YUE, WANG);
}
