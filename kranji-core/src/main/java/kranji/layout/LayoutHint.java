package kranji.layout;

import kranji.zi.SingularZi;

/**
 * A per-position layout hint intrinsic to a component via its
 * {@code hintFor(BlockRole)} method.
 *
 * <p>Composed of three independent, nullable concerns consumed by
 * different stages of the rendering pipeline:</p>
 * <ul>
 *   <li>{@link BlockHint} — block-level sizing, consumed by Stage 1
 *       ({@link BlockLayoutEngine}) for spatial partitioning</li>
 *   <li>{@link InnerPositionHint} — inner positioning, consumed by Stage 2
 *       ({@link BlockSvgRenderer}) per block in isolation</li>
 *   <li>{@link Glyph} — glyph identity/substitution, resolved during layout</li>
 * </ul>
 *
 * @param bh    block-level hint ({@code null} = use component's default politeness)
 * @param iph   inner position hint ({@code null} = fill block fully)
 * @param glyph alternate glyph ({@code null} = use component's own glyph)
 */
public record LayoutHint(BlockHint bh, InnerPositionHint iph, Glyph glyph) {

    // ── Sub-records ────────────────────────────────────────────

    /**
     * Block-level sizing hint — consumed by Stage 1 (layout engine).
     *
     * <p>Politeness determines split ratios between siblings. Optional
     * width/height overrides allow fine-tuning for edge cases where
     * politeness levels aren't granular enough.</p>
     *
     * @param politeness     relative deference level ({@code null} = use component default)
     * @param widthOverride  explicit width weight override ({@code null} = derive from politeness)
     * @param heightOverride explicit height weight override ({@code null} = derive from politeness)
     */
    public record BlockHint(Politeness politeness, Double widthOverride, Double heightOverride) {
        /** Convenience: politeness only, no overrides. */
        public BlockHint(Politeness p) { this(p, null, null); }
    }

    /**
     * Inner positioning hint — consumed by Stage 2 (renderer), per block in isolation.
     *
     * <p>Controls how the glyph sits within its allocated block without
     * affecting neighboring blocks.</p>
     *
     * @param scale  constrains how much of the block the glyph fills ({@code null} = fill fully)
     * @param offset nudges glyph center within the block ({@code null} = centered)
     */
    public record InnerPositionHint(InnerScale scale, Offset offset) {
        /** Convenience: scale only. */
        public InnerPositionHint(InnerScale scale) { this(scale, null); }
        /** Convenience: offset only. */
        public InnerPositionHint(Offset offset) { this(null, offset); }
    }

    /**
     * Constrains glyph dimensions within its block, as fractions of block size.
     *
     * @param width  fraction of block width the glyph fills (e.g. 0.80)
     * @param height fraction of block height the glyph fills (e.g. 0.65)
     */
    public record InnerScale(double width, double height) {}

    /**
     * Glyph offset within its block, as a fraction of block dimensions.
     *
     * <p>Values in [-0.5, 0.5] where (0,0) is the block center.
     * Positive dx shifts right, positive dy shifts down.</p>
     *
     * @param dx horizontal offset as fraction of block width
     * @param dy vertical offset as fraction of block height
     */
    public record Offset(double dx, double dy) {}

    /**
     * Custom SVG shape for a glyph, used when the standard font rendering
     * is insufficient (e.g. 提土旁 — 土 as a left radical).
     *
     * <p>The path data is authored in a local coordinate space defined by
     * {@code viewBoxW × viewBoxH}. The renderer scales this to fit the
     * allocated block, using the same centering logic as font glyphs.</p>
     *
     * @param pathData  SVG {@code d} attribute content (e.g. "M 10 80 L 50 20 ...")
     * @param viewBoxW  width of the path's coordinate space
     * @param viewBoxH  height of the path's coordinate space
     */
    public record SvgShape(String pathData, double viewBoxW, double viewBoxH) {}

    /**
     * Strongly-typed glyph with optional back-reference to source component
     * and optional custom SVG shape.
     *
     * @param value  the glyph string (e.g. "氵") — always present for identification/fallback
     * @param source the component this glyph derives from ({@code null} for raw glyphs)
     * @param svg    custom SVG path to render instead of font text ({@code null} = use font)
     */
    public record Glyph(String value, SingularZi source, SvgShape svg) {
        /** Convenience: raw glyph, no component, no SVG. */
        public Glyph(String value) { this(value, null, null); }
        /** Convenience: glyph with component, no SVG. */
        public Glyph(String value, SingularZi source) { this(value, source, null); }
        /** Whether this glyph has a custom SVG shape. */
        public boolean hasSvg() { return svg != null; }
    }

    // ── Factory methods ────────────────────────────────────────

    /** Politeness-only block hint. */
    public static LayoutHint politeness(Politeness p) {
        return new LayoutHint(new BlockHint(p), null, null);
    }

    /** Inner scale only — constrains glyph within block. */
    public static LayoutHint innerScale(double width, double height) {
        return new LayoutHint(null, new InnerPositionHint(new InnerScale(width, height)), null);
    }

    /** Politeness + inner scale. */
    public static LayoutHint politeScale(Politeness p, double scaleW, double scaleH) {
        return new LayoutHint(
                new BlockHint(p),
                new InnerPositionHint(new InnerScale(scaleW, scaleH)),
                null);
    }

    /** Glyph offset only. */
    public static LayoutHint offset(double dx, double dy) {
        return new LayoutHint(null, new InnerPositionHint(new Offset(dx, dy)), null);
    }

    /** Glyph substitution only. */
    public static LayoutHint glyph(String value) {
        return new LayoutHint(null, null, new Glyph(value));
    }

    /** Glyph substitution with source component. */
    public static LayoutHint glyph(String value, SingularZi source) {
        return new LayoutHint(null, null, new Glyph(value, source));
    }

    /** Politeness + glyph substitution. */
    public static LayoutHint politeGlyph(Politeness p, String glyphValue) {
        return new LayoutHint(new BlockHint(p), null, new Glyph(glyphValue));
    }

    /** Custom SVG glyph only. */
    public static LayoutHint svgGlyph(String value, SvgShape svg) {
        return new LayoutHint(null, null, new Glyph(value, null, svg));
    }

    /** Politeness + custom SVG glyph. */
    public static LayoutHint politeSvgGlyph(Politeness p, String value, SvgShape svg) {
        return new LayoutHint(new BlockHint(p), null, new Glyph(value, null, svg));
    }

    /** All three concerns. */
    public static LayoutHint of(BlockHint bh, InnerPositionHint iph, Glyph glyph) {
        return new LayoutHint(bh, iph, glyph);
    }
}
