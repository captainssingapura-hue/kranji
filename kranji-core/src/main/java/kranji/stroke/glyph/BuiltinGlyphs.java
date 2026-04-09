package kranji.stroke.glyph;

import kranji.stroke.*;

import java.util.List;

/**
 * Hand-coded stroke definitions for a starter set of leaf components.
 *
 * <p>All coordinates are in normalized [0,1]² space: (0,0) = top-left,
 * (1,1) = bottom-right. Typical base width: 0.06 (≈6% of glyph cell).</p>
 */
public final class BuiltinGlyphs {

    private static final double W = 0.06; // standard stroke width

    private BuiltinGlyphs() {}

    static void registerAll(GlyphRegistry r) {
        r.register(kou());
        r.register(ri());
        r.register(yue());
        r.register(mu());
        r.register(shan());
        r.register(sanDianShui());
        r.register(baoGaiTou());
        r.register(xin());
        r.register(xue());
        r.register(yan());
        r.register(yao());
        r.register(chang());
        r.register(liDao());
    }

    // ── 口 kǒu — mouth (3 strokes) ──────────────────────────────

    private static GlyphDef kou() {
        double L = 0.20, R = 0.80, T = 0.20, B = 0.80;
        return new GlyphDef("口", List.of(
                // 1. 竖 left vertical (top-left → bottom-left)
                stroke(StrokeType.SHU, L, T, L, B),
                // 2. 横折 top-right corner (top-left → top-right → bottom-right)
                compound(StrokeType.HENG_ZHE, W,
                        line(L, T, R, T),
                        line(R, T, R, B)),
                // 3. 横 bottom horizontal (bottom-left → bottom-right)
                stroke(StrokeType.HENG, L, B, R, B)
        ));
    }

    // ── 日 rì — sun (5 strokes) ──────────────────────────────────

    private static GlyphDef ri() {
        double L = 0.20, R = 0.80, T = 0.15, B = 0.85, M = 0.50;
        return new GlyphDef("日", List.of(
                stroke(StrokeType.SHU, L, T, L, B),
                compound(StrokeType.HENG_ZHE, W,
                        line(L, T, R, T),
                        line(R, T, R, B)),
                stroke(StrokeType.HENG, L, M, R, M),
                stroke(StrokeType.HENG, L, B, R, B)
        ));
    }

    // ── 月 yuè — moon (4 strokes) ───────────────────────────────

    private static GlyphDef yue() {
        double L = 0.22, R = 0.78, T = 0.12, B = 0.88;
        double M1 = 0.42, M2 = 0.62;
        return new GlyphDef("月", List.of(
                // 1. 撇 left sweep
                bezier(StrokeType.PIE, W,
                        p(L, T), p(L, 0.50), p(0.15, 0.75), p(0.10, B)),
                // 2. 横折钩 top → right side → hook
                compound(StrokeType.HENG_ZHE_GOU, W,
                        line(L, T, R, T),
                        line(R, T, R, B),
                        bezier(p(R, B), p(R - 0.05, B + 0.03), p(R - 0.12, B), p(R - 0.15, B - 0.05))),
                // 3. 横 first inner bar
                stroke(StrokeType.HENG, L + 0.05, M1, R - 0.02, M1),
                // 4. 横 second inner bar
                stroke(StrokeType.HENG, L + 0.05, M2, R - 0.02, M2)
        ));
    }

    // ── 木 mù — tree (4 strokes) ────────────────────────────────

    private static GlyphDef mu() {
        double CX = 0.50, TOP = 0.10, BOT = 0.90, MID = 0.45;
        return new GlyphDef("木", List.of(
                // 1. 横 horizontal bar
                stroke(StrokeType.HENG, 0.12, MID, 0.88, MID),
                // 2. 竖 vertical trunk
                stroke(StrokeType.SHU, CX, TOP, CX, BOT),
                // 3. 撇 left-falling branch
                bezier(StrokeType.PIE, W,
                        p(CX, MID), p(0.38, 0.55), p(0.25, 0.70), p(0.12, BOT)),
                // 4. 捺 right-falling branch
                bezier(StrokeType.NA, W,
                        p(CX, MID), p(0.62, 0.55), p(0.75, 0.70), p(0.88, BOT))
        ));
    }

    // ── 山 shān — mountain (3 strokes) ──────────────────────────

    private static GlyphDef shan() {
        double BOT = 0.85;
        return new GlyphDef("山", List.of(
                // 1. 竖 center peak (tallest)
                stroke(StrokeType.SHU, 0.50, 0.10, 0.50, BOT),
                // 2. 竖折 left peak + base
                compound(StrokeType.SHU_ZHE, W,
                        line(0.20, 0.35, 0.20, BOT),
                        line(0.20, BOT, 0.50, BOT)),
                // 3. 竖折 right peak + base  (mirrored)
                compound(StrokeType.SHU_ZHE, W,
                        line(0.80, 0.35, 0.80, BOT),
                        line(0.50, BOT, 0.80, BOT))
        ));
    }

    // ── 氵 sāndiǎnshuǐ — three-dot water radical (3 strokes) ───

    private static GlyphDef sanDianShui() {
        return new GlyphDef("氵", List.of(
                // 1. 点 top dot
                bezier(StrokeType.DIAN, 0.05,
                        p(0.45, 0.18), p(0.50, 0.20), p(0.55, 0.25), p(0.50, 0.30)),
                // 2. 点 middle dot
                bezier(StrokeType.DIAN, 0.05,
                        p(0.40, 0.40), p(0.45, 0.42), p(0.50, 0.47), p(0.45, 0.52)),
                // 3. 提 rising stroke
                bezier(StrokeType.TI, W,
                        p(0.35, 0.75), p(0.38, 0.70), p(0.50, 0.62), p(0.65, 0.55))
        ));
    }

    // ── 宀 bǎogàitóu — roof radical (3 strokes) ────────────────

    private static GlyphDef baoGaiTou() {
        return new GlyphDef("宀", List.of(
                // 1. 点 center dot
                bezier(StrokeType.DIAN, 0.05,
                        p(0.48, 0.10), p(0.50, 0.14), p(0.53, 0.20), p(0.50, 0.25)),
                // 2. 横折 left roof slope + left vertical
                compound(StrokeType.HENG_ZHE, W,
                        bezier(p(0.50, 0.35), p(0.35, 0.33), p(0.18, 0.35), p(0.12, 0.38)),
                        line(0.12, 0.38, 0.12, 0.55)),
                // 3. 横 wide horizontal beam (extends beyond verticals)
                stroke(StrokeType.HENG, 0.08, 0.55, 0.92, 0.55)
        ));
    }

    // ── 心 xīn — heart (3 strokes) ──────────────────────────────

    private static GlyphDef xin() {
        return new GlyphDef("心", List.of(
                // 1. 点 left dot
                bezier(StrokeType.DIAN, 0.05,
                        p(0.18, 0.35), p(0.20, 0.40), p(0.22, 0.50), p(0.20, 0.55)),
                // 2. 弯钩 center hook (the main curve)
                bezier(StrokeType.WAN_GOU, W,
                        p(0.45, 0.15), p(0.30, 0.55), p(0.40, 0.80), p(0.55, 0.85)),
                // 3. 点 right dot
                bezier(StrokeType.DIAN, 0.05,
                        p(0.65, 0.30), p(0.68, 0.35), p(0.72, 0.42), p(0.70, 0.48))
        ));
    }

    // ── 穴 xué — cave/hole (5 strokes) ──────────────────────────

    private static GlyphDef xue() {
        return new GlyphDef("穴", List.of(
                // 1. 点 top dot (宀 part)
                bezier(StrokeType.DIAN, 0.05,
                        p(0.48, 0.05), p(0.50, 0.09), p(0.53, 0.14), p(0.50, 0.18)),
                // 2. 横折 roof left slope + drop
                compound(StrokeType.HENG_ZHE, W,
                        bezier(p(0.50, 0.25), p(0.35, 0.23), p(0.18, 0.25), p(0.12, 0.28)),
                        line(0.12, 0.28, 0.15, 0.45)),
                // 3. 横 roof beam
                stroke(StrokeType.HENG, 0.08, 0.45, 0.92, 0.45),
                // 4. 撇 left-falling from under roof
                bezier(StrokeType.PIE, W,
                        p(0.40, 0.55), p(0.35, 0.65), p(0.28, 0.78), p(0.18, 0.90)),
                // 5. 捺 right-falling from under roof
                bezier(StrokeType.NA, W,
                        p(0.60, 0.55), p(0.65, 0.65), p(0.72, 0.78), p(0.82, 0.90))
        ));
    }

    // ── 言 yán — speech (7 strokes) ─────────────────────────────

    private static GlyphDef yan() {
        return new GlyphDef("言", List.of(
                // 1. 点 top dot
                bezier(StrokeType.DIAN, 0.05,
                        p(0.48, 0.05), p(0.50, 0.08), p(0.53, 0.12), p(0.50, 0.16)),
                // 2. 横 first horizontal
                stroke(StrokeType.HENG, 0.15, 0.22, 0.85, 0.22),
                // 3. 横 second horizontal
                stroke(StrokeType.HENG, 0.20, 0.35, 0.80, 0.35),
                // 4. 横 third horizontal
                stroke(StrokeType.HENG, 0.25, 0.48, 0.75, 0.48),
                // 5. 竖 left vertical of 口
                stroke(StrokeType.SHU, 0.25, 0.58, 0.25, 0.88),
                // 6. 横折 top-right of 口
                compound(StrokeType.HENG_ZHE, W,
                        line(0.25, 0.58, 0.75, 0.58),
                        line(0.75, 0.58, 0.75, 0.88)),
                // 7. 横 bottom of 口
                stroke(StrokeType.HENG, 0.25, 0.88, 0.75, 0.88)
        ));
    }

    // ── 幺 yāo — tiny/thread (3 strokes) ────────────────────────

    private static GlyphDef yao() {
        return new GlyphDef("幺", List.of(
                // 1. 撇折 upper loop
                compound(StrokeType.PIE_ZHE, W,
                        bezier(p(0.55, 0.10), p(0.45, 0.20), p(0.30, 0.30), p(0.25, 0.40)),
                        bezier(p(0.25, 0.40), p(0.35, 0.42), p(0.55, 0.38), p(0.65, 0.45))),
                // 2. 撇折 lower loop
                compound(StrokeType.PIE_ZHE, W,
                        bezier(p(0.60, 0.45), p(0.50, 0.55), p(0.35, 0.62), p(0.30, 0.70)),
                        bezier(p(0.30, 0.70), p(0.40, 0.72), p(0.58, 0.68), p(0.68, 0.75))),
                // 3. 点 bottom dot
                bezier(StrokeType.DIAN, 0.05,
                        p(0.45, 0.78), p(0.48, 0.82), p(0.50, 0.88), p(0.48, 0.92))
        ));
    }

    // ── 長 cháng — long (8 strokes, simplified view) ────────────

    private static GlyphDef chang() {
        return new GlyphDef("長", List.of(
                // 1. 横 top horizontal
                stroke(StrokeType.HENG, 0.15, 0.12, 0.85, 0.12),
                // 2. 竖 center vertical (backbone)
                stroke(StrokeType.SHU, 0.50, 0.12, 0.50, 0.90),
                // 3. 横 second bar
                stroke(StrokeType.HENG, 0.25, 0.30, 0.75, 0.30),
                // 4. 横 third bar
                stroke(StrokeType.HENG, 0.25, 0.48, 0.75, 0.48),
                // 5. 横 fourth bar
                stroke(StrokeType.HENG, 0.25, 0.64, 0.75, 0.64),
                // 6. 撇 left-falling
                bezier(StrokeType.PIE, W,
                        p(0.50, 0.64), p(0.42, 0.72), p(0.30, 0.82), p(0.15, 0.92)),
                // 7. 捺 right-falling
                bezier(StrokeType.NA, W,
                        p(0.50, 0.64), p(0.58, 0.72), p(0.70, 0.82), p(0.85, 0.92))
        ));
    }

    // ── 刂 lìdāopáng — standing knife radical (2 strokes) ──────

    private static GlyphDef liDao() {
        return new GlyphDef("刂", List.of(
                // 1. 竖 short left vertical
                stroke(StrokeType.SHU, 0.35, 0.25, 0.35, 0.70),
                // 2. 竖钩 long right vertical with hook
                compound(StrokeType.SHU_GOU, W,
                        line(0.65, 0.10, 0.65, 0.85),
                        bezier(p(0.65, 0.85), p(0.63, 0.88), p(0.58, 0.90), p(0.52, 0.87)))
        ));
    }

    // ── Helper methods ──────────────────────────────────────────

    private static Vec2 p(double x, double y) {
        return new Vec2(x, y);
    }

    private static CubicBezier line(double x1, double y1, double x2, double y2) {
        return CubicBezier.line(p(x1, y1), p(x2, y2));
    }

    private static CubicBezier bezier(Vec2 p0, Vec2 p1, Vec2 p2, Vec2 p3) {
        return new CubicBezier(p0, p1, p2, p3);
    }

    /** Simple straight-line stroke. */
    private static StrokeInstance stroke(StrokeType type, double x1, double y1, double x2, double y2) {
        return new StrokeInstance(type, line(x1, y1, x2, y2), W);
    }

    /** Single Bézier curve stroke. */
    private static StrokeInstance bezier(StrokeType type, double width, Vec2 p0, Vec2 p1, Vec2 p2, Vec2 p3) {
        return new StrokeInstance(type, new CubicBezier(p0, p1, p2, p3), width);
    }

    /** Multi-segment compound stroke. */
    private static StrokeInstance compound(StrokeType type, double width, CubicBezier... segments) {
        return new StrokeInstance(type, List.of(segments), width, null);
    }
}
