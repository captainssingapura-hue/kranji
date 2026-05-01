package kranji.ui.threed.graph;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Cylinder;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import kranji.layout.GlyphBounds;

import java.awt.geom.Rectangle2D;

/**
 * Small static factories for the 3D building blocks shared across the
 * graph renderers: extruded glyphs (stacked Text) and spring cylinders.
 *
 * <p>Same scaling math as
 * {@link kranji.ui.threed.BlockExtrusionRenderer} so glyphs read
 * consistently across modes.</p>
 */
public final class GlyphNode3d {

    private static final double BASE_FONT = 100;
    private static final double FILL_FACTOR = 0.88;
    private static final int    DEFAULT_LAYERS = 12;
    private static final double DEFAULT_LAYER_STEP = 1.0;
    /** Default CJK-capable font on Windows. */
    public static final String  DEFAULT_FONT_FAMILY = "Microsoft YaHei";

    private GlyphNode3d() {}

    /** Stacked-Text extrusion of {@code glyph}, scaled to fill {@code size}. */
    public static Group extrudedGlyph(String glyph, Color color, double size) {
        return extrudedGlyph(glyph, (Paint) color, size,
                DEFAULT_LAYERS, DEFAULT_LAYER_STEP, DEFAULT_FONT_FAMILY);
    }

    public static Group extrudedGlyph(String glyph, Paint paint, double size,
                                      int layers, double layerStep) {
        return extrudedGlyph(glyph, paint, size, layers, layerStep, DEFAULT_FONT_FAMILY);
    }

    public static Group extrudedGlyph(String glyph, Paint paint, double size,
                                      int layers, double layerStep, String fontFamily) {
        if (glyph == null || glyph.isEmpty()) return null;
        Rectangle2D vb = GlyphBounds.visualBounds(glyph);
        double vw = vb.getWidth(), vh = vb.getHeight();
        if (vw < 1 || vh < 1) return null;
        double s = (FILL_FACTOR * size) / Math.max(vw, vh);
        double tx = -(vb.getX() + vw / 2.0) * s;
        double ty = -(vb.getY() + vh / 2.0) * s;
        var stack = new Group();
        for (int i = 0; i < layers; i++) {
            Text t = new Text(glyph);
            t.setFill(paint);
            t.setFont(Font.font(
                    fontFamily == null ? DEFAULT_FONT_FAMILY : fontFamily, BASE_FONT));
            t.getTransforms().add(new Scale(s, s));
            t.setTranslateX(tx);
            t.setTranslateY(ty);
            t.setTranslateZ(-i * layerStep);
            stack.getChildren().add(t);
        }
        return stack;
    }

    /**
     * Update an existing {@link Cylinder} so it stretches from
     * {@code (x1, y1, z1)} to {@code (x2, y2, z2)} in world space.
     * Reuses the cylinder (no allocation) — designed for per-frame
     * use in continuous simulations.
     */
    public static void positionSpring(Cylinder c,
                                      double x1, double y1, double z1,
                                      double x2, double y2, double z2) {
        double dx = x2 - x1, dy = y2 - y1, dz = z2 - z1;
        double length = Math.max(0.5, Math.sqrt(dx*dx + dy*dy + dz*dz));
        c.setHeight(length);
        c.getTransforms().clear();
        Point3D dir = new Point3D(dx, dy, dz).multiply(1.0 / length);
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D rotAxis = yAxis.crossProduct(dir);
        if (rotAxis.magnitude() > 1e-6) {
            double angle = Math.toDegrees(Math.acos(
                    Math.max(-1.0, Math.min(1.0, yAxis.dotProduct(dir)))));
            c.getTransforms().add(new Rotate(angle, rotAxis));
        } else if (dir.getY() < 0) {
            c.getTransforms().add(new Rotate(180, new Point3D(1, 0, 0)));
        }
        c.setTranslateX((x1 + x2) / 2);
        c.setTranslateY((y1 + y2) / 2);
        c.setTranslateZ((z1 + z2) / 2);
    }
}
