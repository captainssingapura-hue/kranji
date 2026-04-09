package kranji.stroke;

/**
 * An axis-aligned 2D affine transform: scale then translate.
 *
 * <p>Maps a point p to (sx·p.x + tx, sy·p.y + ty). This is sufficient for
 * all composition layout partitions, which are axis-aligned rectangles.</p>
 *
 * <p>Composition: {@code outer.compose(inner)} applies inner first, then outer.</p>
 */
public record AffineTransform2D(double sx, double sy, double tx, double ty) {

    public static final AffineTransform2D IDENTITY = new AffineTransform2D(1, 1, 0, 0);

    public Vec2 apply(Vec2 p) {
        return new Vec2(sx * p.x() + tx, sy * p.y() + ty);
    }

    /**
     * Compose two transforms: result(p) = this(inner(p)).
     *
     * <pre>
     *   this(inner(p)) = (sx·(inner.sx·p.x + inner.tx) + tx,
     *                     sy·(inner.sy·p.y + inner.ty) + ty)
     *                  = (sx·inner.sx·p.x + sx·inner.tx + tx,
     *                     sy·inner.sy·p.y + sy·inner.ty + ty)
     * </pre>
     */
    public AffineTransform2D compose(AffineTransform2D inner) {
        return new AffineTransform2D(
                sx * inner.sx, sy * inner.sy,
                sx * inner.tx + tx, sy * inner.ty + ty
        );
    }

    /**
     * Scale factor for stroke widths: geometric mean of |sx| and |sy|.
     *
     * <p>This preserves visual stroke weight regardless of whether the
     * sub-frame is wider or taller than it is high.</p>
     */
    public double widthScale() {
        return Math.sqrt(Math.abs(sx * sy));
    }

    /** Create a transform that maps [0,1]² into the rectangle (x, y, w, h). */
    public static AffineTransform2D rect(double x, double y, double w, double h) {
        return new AffineTransform2D(w, h, x, y);
    }
}
