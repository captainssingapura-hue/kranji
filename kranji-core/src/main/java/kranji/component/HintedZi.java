package kranji.component;

import kranji.classification.BlockRole;
import kranji.classification.CharacterComposition.FullEnclosure;
import kranji.classification.CharacterComposition.LeftRight;
import kranji.classification.CharacterComposition.TopBottom;
import kranji.layout.LayoutHint;
import kranji.layout.Politeness;

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
    public record Ri() implements SingularZi {
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

    /** 口 — mouth. Yielding in side/bottom positions, inner-scaled as right/bottom. */
    public record Kou() implements SingularZi {
        @Override public String glyph() { return "口"; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left)   return Politeness.YIELDING;
            if (role instanceof LeftRight.Right)  return Politeness.YIELDING;
            if (role instanceof TopBottom.Bottom) return Politeness.YIELDING;
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
    public record Mu() implements SingularZi {
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
    public record Tian() implements SingularZi {
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
    public record Yue() implements SingularZi {
        @Override public String glyph() { return "月"; }

        @Override
        public Politeness politeness(BlockRole role) {
            if (role instanceof LeftRight.Left) return Politeness.YIELDING;
            return Politeness.NEUTRAL;
        }
    }

    // ── Singleton instances ────────────────────────────────────

    public static final Ri   RI   = new Ri();
    public static final Kou  KOU  = new Kou();
    public static final Mu   MU   = new Mu();
    public static final Tian TIAN = new Tian();
    public static final Yue  YUE  = new Yue();

    /** Register all hinted instances so {@link SingularZi#of} returns them. */
    static {
        SingularZi.register(RI);
        SingularZi.register(KOU);
        SingularZi.register(MU);
        SingularZi.register(TIAN);
        SingularZi.register(YUE);
    }
}
