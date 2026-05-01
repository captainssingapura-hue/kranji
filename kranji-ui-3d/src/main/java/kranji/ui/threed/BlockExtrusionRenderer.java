package kranji.ui.threed;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import kranji.layout.Block;
import kranji.layout.BlockLayoutEngine;
import kranji.layout.GlyphBounds;
import kranji.zi.Zi;

import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * Converts a {@link Zi}'s composition into a 3D {@link Group}.
 *
 * <p>Each {@link Block} produced by {@link BlockLayoutEngine} becomes a
 * thin slab ({@link Box}) with the block's glyph rendered as a
 * {@link Text} centered on the front face. The block's nesting depth
 * controls its Z position so deeper levels float closer to the camera.</p>
 *
 * <p>Sizing / centering of each glyph mirrors {@code BlockSvgRenderer}
 * exactly — visual bounds via {@link GlyphBounds}, fill factor 0.88,
 * stretch resistance 0.10 — so glyphs occupy the same proportion of
 * their block as in the 2D SVG and the layout looks identical when the
 * camera is head-on.</p>
 */
public final class BlockExtrusionRenderer {

    /** World-space size of the [0,1]² character bounding square. */
    private static final double WORLD_SIZE = 400;
    /** Default Z distance per nesting depth. Override via {@link #render(Zi, double)}. */
    public static final double DEFAULT_DEPTH_SPACING = 30;

    /** Reference font size matching {@link GlyphBounds} (which measures at 100). */
    private static final double BASE_FONT = 100;
    /** Glyph fills 88 % of the smaller block dimension — same as SVG renderer. */
    private static final double FILL_FACTOR = 0.88;
    /** Soften extreme aspect-ratio scaling — same as SVG renderer. */
    private static final double STRETCH_RESISTANCE = 0.10;

    /** Default glyph color — overridable per render call. */
    public static final Color DEFAULT_GLYPH_COLOR = Color.rgb(20, 20, 20);

    /**
     * Number of stacked {@link Text} layers per glyph. JavaFX Text is
     * inherently 2D, so faking thickness by stacking N copies along Z
     * gives the glyph visible depth when rotated. 12 layers feels
     * solid without measurable cost (~60 nodes for a typical Zi).
     */
    private static final int GLYPH_LAYERS = 12;
    /** Z spacing between layers — total thickness = (LAYERS-1) × this. */
    private static final double GLYPH_LAYER_STEP = 1.0;

    private BlockExtrusionRenderer() {}

    /** Convenience overload — defaults for everything. */
    public static Group render(Zi zi) {
        return render(zi, DEFAULT_DEPTH_SPACING, DEFAULT_GLYPH_COLOR, false);
    }

    /**
     * Build a {@link Group} of 3D nodes representing {@code zi}'s block
     * decomposition, centered on the world origin.
     *
     * @param depthSpacing Z distance (in world units) added per
     *                     nesting depth. 0 collapses everything onto
     *                     the same plane; ~30 gives a comfortable
     *                     default; ~100+ exaggerates the layering.
     * @param glyphColor   fill applied to all stacked-Text layers of
     *                     each glyph.
     * @param exploded     if true, also render the root glyph at Z = 0
     *                     (the assembled character) and a thin stem
     *                     from the root centre to each leaf centre —
     *                     spatial-decomposition exploded view (Flavor A).
     */
    public static Group render(Zi zi, double depthSpacing, Color glyphColor,
                               boolean exploded) {
        Group root = new Group();
        if (zi == null) return root;

        List<Block> blocks = BlockLayoutEngine.layout(zi);

        if (exploded) {
            // Root glyph at Z = 0 — the whole character, full square.
            Block rootBlock = new Block(zi.character(), 0, 0, 1, 1, 0, null, null, "");
            Group rootNode = buildGlyph(rootBlock, 0, 0, 0, glyphColor);
            if (rootNode != null) root.getChildren().add(rootNode);
        }

        for (Block b : blocks) {
            if (b.glyph() == null || b.glyph().isEmpty()) continue;
            // Block center in world space. JavaFX 3D uses +Y-down (same
            // as the SVG canvas), so no Y flip — positions match the
            // 2D layout exactly when viewed head-on.
            double cx = (b.x() + b.w() / 2.0 - 0.5) * WORLD_SIZE;
            double cy = (b.y() + b.h() / 2.0 - 0.5) * WORLD_SIZE;
            double cz = -b.depth() * depthSpacing;

            Group glyphNode = buildGlyph(b, cx, cy, cz, glyphColor);
            if (glyphNode != null) root.getChildren().add(glyphNode);

            if (exploded) {
                // Stem from world origin (root centre at Z=0) to this
                // leaf's centre. Thin translucent cylinder.
                Cylinder stem = makeStem(
                        new Point3D(0, 0, 0),
                        new Point3D(cx, cy, cz),
                        STEM_RADIUS,
                        glyphColor.deriveColor(0, 1, 1, STEM_OPACITY));
                if (stem != null) root.getChildren().add(stem);
            }
        }
        return root;
    }

    /** Stem cylinder radius (world units). */
    private static final double STEM_RADIUS = 1.5;
    /** Stem opacity factor (relative to glyph color alpha). */
    private static final double STEM_OPACITY = 0.55;

    /**
     * Build a thin cylinder stretching from {@code from} to {@code to}.
     * JavaFX {@link Cylinder} is by default oriented along its local Y
     * axis with its center at the origin; we rotate to align Y with the
     * direction (to − from) and translate to the midpoint.
     */
    private static Cylinder makeStem(Point3D from, Point3D to,
                                     double radius, Color color) {
        Point3D diff = to.subtract(from);
        double length = diff.magnitude();
        if (length < 0.5) return null;     // skip degenerate stems

        Cylinder c = new Cylinder(radius, length);
        var mat = new PhongMaterial(color);
        c.setMaterial(mat);

        Point3D mid = from.midpoint(to);
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D dirNormalized = diff.normalize();
        Point3D rotAxis = yAxis.crossProduct(dirNormalized);
        if (rotAxis.magnitude() > 1e-6) {
            double angleDeg = Math.toDegrees(Math.acos(
                    Math.max(-1.0, Math.min(1.0, yAxis.dotProduct(dirNormalized)))));
            c.getTransforms().add(new Rotate(angleDeg, rotAxis));
        } else if (dirNormalized.getY() < 0) {
            // Anti-parallel to Y — flip 180° around X.
            c.getTransforms().add(new Rotate(180, new Point3D(1, 0, 0)));
        }
        c.setTranslateX(mid.getX());
        c.setTranslateY(mid.getY());
        c.setTranslateZ(mid.getZ());
        return c;
    }

    private static Group buildGlyph(Block b, double cx, double cy, double cz,
                                    Color glyphColor) {
        Rectangle2D vb = GlyphBounds.visualBounds(b.glyph());
        double vw = vb.getWidth();
        double vh = vb.getHeight();
        if (vw < 1 || vh < 1) return null;       // degenerate

        double pw = b.w() * WORLD_SIZE;
        double ph = b.h() * WORLD_SIZE;

        // Independent x/y scale so the inked area fills FILL_FACTOR of
        // the block; geometric mean keeps area but flattens stretch.
        double rawSx = (FILL_FACTOR * pw) / vw;
        double rawSy = (FILL_FACTOR * ph) / vh;
        double sUniform = Math.sqrt(rawSx * rawSy);

        double aspect = Math.max(vw / vh, vh / vw);
        double resistance = aspect > 3.0
                ? Math.min(1.0, STRETCH_RESISTANCE + (aspect - 3.0) * 0.20)
                : STRETCH_RESISTANCE;
        double sx = rawSx * (1 - resistance) + sUniform * resistance;
        double sy = rawSy * (1 - resistance) + sUniform * resistance;
        // Cap so visual bounds never exceed the block.
        sx = Math.min(sx, pw / vw);
        sy = Math.min(sy, ph / vh);
        if (sx < 0.04 || sy < 0.04) return null;

        // Position the visual center of the glyph at the block center.
        // After Scale(sx,sy) around local origin, point (vb.x+vw/2,
        // vb.y+vh/2) ends up at ((vb.x+vw/2)*sx, (vb.y+vh/2)*sy).
        // Place the glyph node so that point sits at (cx, cy).
        double tx = cx - (vb.getX() + vw / 2.0) * sx;
        double ty = cy - (vb.getY() + vh / 2.0) * sy;

        // Stack N copies of the Text along Z to fake an extruded
        // glyph. JavaFX Text is a 2D shape; without stacking it reads
        // as paper from any non-head-on angle. With 12 layers @ 1 px
        // step, the glyph has ~12 px visible thickness.
        Group glyphGroup = new Group();
        for (int i = 0; i < GLYPH_LAYERS; i++) {
            Text text = new Text(b.glyph());
            text.setFill(glyphColor);
            text.setFont(Font.font("Microsoft YaHei", BASE_FONT));
            // Scale around local origin (0,0) — the glyph's draw origin
            // matches the FontRenderContext space GlyphBounds measures in.
            text.getTransforms().add(new Scale(sx, sy));
            text.setTranslateZ(-i * GLYPH_LAYER_STEP);
            glyphGroup.getChildren().add(text);
        }
        glyphGroup.setTranslateX(tx);
        glyphGroup.setTranslateY(ty);
        glyphGroup.setTranslateZ(cz);
        return glyphGroup;
    }
}
