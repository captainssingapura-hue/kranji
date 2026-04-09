package kranji.stroke;

/**
 * A cubic Bézier curve defined by four control points in ℝ².
 *
 * <p>B(t) = (1−t)³P₀ + 3(1−t)²tP₁ + 3(1−t)t²P₂ + t³P₃, for t ∈ [0, 1].</p>
 */
public record CubicBezier(Vec2 p0, Vec2 p1, Vec2 p2, Vec2 p3) {

    /** Evaluate the curve at parameter t. */
    public Vec2 at(double t) {
        double u = 1 - t;
        double u2 = u * u;
        double t2 = t * t;
        // B(t) = u³P₀ + 3u²tP₁ + 3ut²P₂ + t³P₃
        return p0.scale(u2 * u)
                .add(p1.scale(3 * u2 * t))
                .add(p2.scale(3 * u * t2))
                .add(p3.scale(t2 * t));
    }

    /**
     * First derivative B′(t) = 3(1−t)²(P₁−P₀) + 6(1−t)t(P₂−P₁) + 3t²(P₃−P₂).
     *
     * <p>Returns the tangent vector (not normalized).</p>
     */
    public Vec2 tangentAt(double t) {
        double u = 1 - t;
        return p1.sub(p0).scale(3 * u * u)
                .add(p2.sub(p1).scale(6 * u * t))
                .add(p3.sub(p2).scale(3 * t * t));
    }

    /**
     * Unit normal at parameter t — perpendicular to the tangent, pointing left.
     *
     * <p>Falls back to the chord direction P₃−P₀ if the tangent degenerates
     * (e.g. when P₀=P₁ for a straight-line segment).</p>
     */
    public Vec2 normalAt(double t) {
        Vec2 tan = tangentAt(t);
        if (tan.lengthSq() < 1e-20) {
            tan = p3.sub(p0); // fallback to chord
        }
        return tan.perp().normalized();
    }

    /** Apply an affine transform to all four control points. */
    public CubicBezier transform(AffineTransform2D m) {
        return new CubicBezier(m.apply(p0), m.apply(p1), m.apply(p2), m.apply(p3));
    }

    /** Convenience: a straight line from a to b as a degenerate cubic. */
    public static CubicBezier line(Vec2 a, Vec2 b) {
        return new CubicBezier(a, a.lerp(b, 1.0 / 3), a.lerp(b, 2.0 / 3), b);
    }
}
