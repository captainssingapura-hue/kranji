package kranji.singular;

import kranji.classification.BlockRole;
import kranji.layout.LayoutHint;
import kranji.layout.Politeness;
import kranji.singular.body.k.Kou;
import kranji.singular.body.m.Mu;
import kranji.singular.body.zero.Er;
import kranji.singular.nature.h.Huo;
import kranji.singular.nature.r.Ri;
import kranji.singular.nature.sh.Shi;
import kranji.singular.nature.t.Tian;
import kranji.singular.nature.t.Tu;
import kranji.singular.nature.zero.Yue;
import kranji.zi.ComposedBlock.FullEnclosure;
import kranji.zi.ComposedBlock.LeftRight;
import kranji.zi.ComposedBlock.TopBottom;
import kranji.zi.ComposedBlock.TopMiddleBottom;
import kranji.zi.SingularZi;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Locks in the role-aware overrides hand-ported from the original
 * {@code kranji-singulars} family classes into the per-class module.
 *
 * <p>Eight generated files carry the {@code // PARTIALLY GENERATED}
 * banner and define non-default {@link SingularZi#politeness} and/or
 * {@link SingularZi#hintFor} methods. This test catches any typo in those
 * method bodies and any accidental loss of the overrides on re-run of
 * {@code GenerateMain}.</p>
 */
class OverrideBehaviorTest {

    // Reusable role instances — records are zero-arg so these are safe singletons.
    private static final BlockRole LR_LEFT       = new LeftRight.Left();
    private static final BlockRole LR_RIGHT      = new LeftRight.Right();
    private static final BlockRole TB_TOP        = new TopBottom.Top();
    private static final BlockRole TB_BOTTOM     = new TopBottom.Bottom();
    private static final BlockRole TMB_TOP       = new TopMiddleBottom.Top();
    private static final BlockRole TMB_MIDDLE    = new TopMiddleBottom.Middle();
    private static final BlockRole TMB_BOTTOM    = new TopMiddleBottom.Bottom();
    private static final BlockRole FE_INNER      = new FullEnclosure.Inner();
    private static final BlockRole FE_OUTER      = new FullEnclosure.OuterFrame();

    // ── body/ ─────────────────────────────────────────────────────────

    @Test
    void kou_yields_on_side_bottom_and_middle_positions() {
        Kou k = Kou.INSTANCE;

        // Politeness: LR.Left/Right, TB.Bottom, TMB.Top/Middle → YIELDING.
        assertEquals(Politeness.YIELDING, k.politeness(LR_LEFT));
        assertEquals(Politeness.YIELDING, k.politeness(LR_RIGHT));
        assertEquals(Politeness.YIELDING, k.politeness(TB_BOTTOM));
        assertEquals(Politeness.YIELDING, k.politeness(TMB_TOP));
        assertEquals(Politeness.YIELDING, k.politeness(TMB_MIDDLE));
        // Everything else → NEUTRAL (default).
        assertEquals(Politeness.NEUTRAL, k.politeness(TB_TOP));
        assertEquals(Politeness.NEUTRAL, k.politeness(TMB_BOTTOM));
        assertEquals(Politeness.NEUTRAL, k.politeness(FE_OUTER));

        // hintFor: TB.Bottom → innerScale(0.80, 0.80); FE.Inner → innerScale(0.55, 0.55).
        assertEquals(LayoutHint.innerScale(0.80, 0.80), k.hintFor(TB_BOTTOM));
        assertEquals(LayoutHint.innerScale(0.55, 0.55), k.hintFor(FE_INNER));
        assertNull(k.hintFor(LR_LEFT), "LR.Left has no hint override for Kou");
        assertNull(k.hintFor(TMB_TOP));
    }

    @Test
    void mu_yields_on_left_and_bottom() {
        Mu m = Mu.INSTANCE;

        assertEquals(Politeness.YIELDING, m.politeness(LR_LEFT));
        assertEquals(Politeness.YIELDING, m.politeness(TB_BOTTOM));
        assertEquals(Politeness.NEUTRAL,  m.politeness(LR_RIGHT));
        assertEquals(Politeness.NEUTRAL,  m.politeness(TB_TOP));

        assertEquals(LayoutHint.innerScale(0.85, 0.80), m.hintFor(TB_BOTTOM));
        assertNull(m.hintFor(LR_LEFT));
    }

    // ── nature/ ───────────────────────────────────────────────────────

    @Test
    void ri_yields_on_left_and_bottom() {
        Ri r = Ri.INSTANCE;

        assertEquals(Politeness.YIELDING, r.politeness(LR_LEFT));
        assertEquals(Politeness.YIELDING, r.politeness(TB_BOTTOM));
        assertEquals(Politeness.NEUTRAL,  r.politeness(TB_TOP));

        assertEquals(LayoutHint.innerScale(0.85, 0.80), r.hintFor(TB_BOTTOM));
        assertNull(r.hintFor(LR_LEFT));
    }

    @Test
    void yue_yields_only_on_left() {
        Yue y = Yue.INSTANCE;

        assertEquals(Politeness.YIELDING, y.politeness(LR_LEFT));
        assertEquals(Politeness.NEUTRAL,  y.politeness(LR_RIGHT));
        assertEquals(Politeness.NEUTRAL,  y.politeness(TB_BOTTOM));

        // Yue has no hintFor override — default null for every role.
        assertNull(y.hintFor(LR_LEFT));
        assertNull(y.hintFor(TB_BOTTOM));
    }

    @Test
    void tian_yields_on_left_and_bottom() {
        Tian t = Tian.INSTANCE;

        assertEquals(Politeness.YIELDING, t.politeness(LR_LEFT));
        assertEquals(Politeness.YIELDING, t.politeness(TB_BOTTOM));
        assertEquals(Politeness.NEUTRAL,  t.politeness(TB_TOP));

        assertEquals(LayoutHint.innerScale(0.85, 0.80), t.hintFor(TB_BOTTOM));
        assertNull(t.hintFor(LR_LEFT));
    }

    @Test
    void huo_yields_only_on_left() {
        Huo h = Huo.INSTANCE;

        assertEquals(Politeness.YIELDING, h.politeness(LR_LEFT));
        assertEquals(Politeness.NEUTRAL,  h.politeness(LR_RIGHT));
        assertEquals(Politeness.NEUTRAL,  h.politeness(TB_BOTTOM));

        assertNull(h.hintFor(LR_LEFT));
    }

    @Test
    void tu_yields_on_left_with_ti_tu_pang_svg_hint() {
        Tu t = Tu.INSTANCE;

        assertEquals(Politeness.YIELDING, t.politeness(LR_LEFT));
        assertEquals(Politeness.NEUTRAL,  t.politeness(LR_RIGHT));
        assertEquals(Politeness.NEUTRAL,  t.politeness(TB_BOTTOM));

        // hintFor on LR.Left must return a politeSvgGlyph with YIELDING politeness
        // and the 土 glyph carrying an SVG shape.
        LayoutHint hint = t.hintFor(LR_LEFT);
        assertNotNull(hint, "Tu must emit a layout hint when acting as LR.Left");
        assertNotNull(hint.bh(),    "hint must carry a BlockHint (politeness)");
        assertSame(Politeness.YIELDING, hint.bh().politeness());
        assertNotNull(hint.glyph(), "hint must carry a Glyph");
        assertEquals("\u571F", hint.glyph().value());
        assertNotNull(hint.glyph().svg(), "Glyph must carry an SvgShape (TI_TU_PANG)");
        assertEquals(100.0, hint.glyph().svg().viewBoxW());
        assertEquals(100.0, hint.glyph().svg().viewBoxH());
        assertNull(t.hintFor(LR_RIGHT), "only LR.Left triggers the SVG hint");
        assertNull(t.hintFor(TB_BOTTOM));
    }

    @Test
    void shi_yields_only_on_left() {
        Shi s = Shi.INSTANCE;

        assertEquals(Politeness.YIELDING, s.politeness(LR_LEFT));
        assertEquals(Politeness.NEUTRAL,  s.politeness(LR_RIGHT));
        assertEquals(Politeness.NEUTRAL,  s.politeness(TB_BOTTOM));

        assertNull(s.hintFor(LR_LEFT));
    }

    // ── Regression: purely-generated records still use defaults ───────

    @Test
    void auto_generated_records_use_default_neutral_and_null() {
        // Er (耳 — ear) is AUTO-GENERATED with no overrides; it must return
        // the interface defaults. This guards against accidental blanket
        // overrides leaking into the emitter.
        Er er = Er.INSTANCE;
        assertEquals(Politeness.NEUTRAL, er.politeness(LR_LEFT));
        assertEquals(Politeness.NEUTRAL, er.politeness(TB_BOTTOM));
        assertEquals(Politeness.NEUTRAL, er.politeness(FE_INNER));
        assertNull(er.hintFor(LR_LEFT));
        assertNull(er.hintFor(TB_BOTTOM));
    }
}
