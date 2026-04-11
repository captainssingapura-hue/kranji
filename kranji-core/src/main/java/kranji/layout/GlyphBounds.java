package kranji.layout;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Measures the actual visual bounding box of CJK glyphs using Java2D.
 *
 * <p>The visual bounds reflect the real ink extent of the glyph, not the
 * em-square. This allows the renderer to scale and center glyphs based on
 * their true shape rather than the full character cell.</p>
 */
public final class GlyphBounds {

    private static final FontRenderContext FRC =
            new FontRenderContext(null, true, true);

    /** Must match the font-family list in BlockSvgRenderer's CSS. */
    private static final Font FONT =
            new Font("Microsoft YaHei", Font.PLAIN, 100);

    private static final Map<String, Rectangle2D> CACHE = new ConcurrentHashMap<>();

    private GlyphBounds() {}

    /**
     * Returns the visual bounding box of the given glyph string rendered
     * at font-size 100, with origin at (0, 0) and default baseline alignment.
     *
     * <p>The returned rectangle describes where actual ink appears relative
     * to the text draw origin. For a typical CJK character the x will be
     * near 0, y will be negative (above baseline), and width/height will
     * be close to 100.</p>
     */
    public static Rectangle2D visualBounds(String glyph) {
        return CACHE.computeIfAbsent(glyph, g -> {
            GlyphVector gv = FONT.createGlyphVector(FRC, g);
            return gv.getVisualBounds();
        });
    }

    /**
     * Returns the visual bounding box for a glyph, using the custom SVG
     * shape's viewBox if present, otherwise falling back to font metrics.
     *
     * <p>For SVG shapes, the bounds use the same Y-negative convention as
     * font glyphs so that the centering math in {@link BlockSvgRenderer}
     * works identically for both cases.</p>
     *
     * @param glyph the glyph string (used as fallback key)
     * @param svg   custom SVG shape, or {@code null} for font-based measurement
     */
    public static Rectangle2D visualBounds(String glyph, LayoutHint.SvgShape svg) {
        if (svg != null) {
            // SVG paths use Y-down from (0,0) at top-left — return bounds
            // in the same coordinate space so the centering formula works.
            return new Rectangle2D.Double(0, 0, svg.viewBoxW(), svg.viewBoxH());
        }
        return visualBounds(glyph);
    }
}
