package kranji.stroke;

/**
 * An immutable 2D vector / point.
 *
 * <p>Used for Bézier control points, positions, tangents, and normals
 * throughout the stroke rendering pipeline.</p>
 */
public record Vec2(double x, double y) {

    public static final Vec2 ZERO = new Vec2(0, 0);

    public Vec2 add(Vec2 v) { return new Vec2(x + v.x, y + v.y); }
    public Vec2 sub(Vec2 v) { return new Vec2(x - v.x, y - v.y); }
    public Vec2 scale(double s) { return new Vec2(x * s, y * s); }
    public Vec2 lerp(Vec2 v, double t) { return scale(1 - t).add(v.scale(t)); }

    /** 90° counter-clockwise rotation: (x, y) → (−y, x). */
    public Vec2 perp() { return new Vec2(-y, x); }

    public double length() { return Math.sqrt(x * x + y * y); }
    public double lengthSq() { return x * x + y * y; }

    public Vec2 normalized() {
        double len = length();
        return len < 1e-12 ? ZERO : new Vec2(x / len, y / len);
    }
}
