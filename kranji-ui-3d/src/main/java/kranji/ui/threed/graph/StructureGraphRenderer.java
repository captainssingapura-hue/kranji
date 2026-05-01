package kranji.ui.threed.graph;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import kranji.zi.BlockStructure;

import java.util.Map;

/**
 * Pure builder: turns a laid-out {@link StructureGraph.Graph} into a
 * JavaFX {@link Group} of <em>extruded</em> glyphs (stacked Text)
 * connected by spring-cylinders.
 *
 * <p>Visual continuity with {@link kranji.ui.threed.BlockExtrusionRenderer}:
 * same {@link GlyphBounds} scaling math, same stacked-Text trick for
 * 3D thickness. Difference is the position is whatever the force
 * layout produced, not a fixed Z-by-depth.</p>
 */
public final class StructureGraphRenderer {

    private static final double GLYPH_SIZE = 64;
    private static final double SPRING_RADIUS = 1.2;

    private StructureGraphRenderer() {}

    public static Group render(StructureGraph.Graph graph,
                               Map<BlockStructure, Point3D> positions,
                               Color glyphColor) {
        var root = new Group();
        graph.edges().forEach(e -> addSpring(root, e, positions, glyphColor));
        graph.nodes().forEach(n -> addNode(root, n, positions, glyphColor));
        return root;
    }

    // ── Nodes ──────────────────────────────────────────────────────

    private static void addNode(Group parent, BlockStructure node,
                                Map<BlockStructure, Point3D> positions,
                                Color color) {
        Point3D p = positions.get(node);
        if (p == null) return;
        Group glyph = GlyphNode3d.extrudedGlyph(node.glyph(), color, GLYPH_SIZE);
        if (glyph == null) return;
        glyph.setTranslateX(p.getX());
        glyph.setTranslateY(p.getY());
        glyph.setTranslateZ(p.getZ());
        parent.getChildren().add(glyph);
    }

    // ── Springs ────────────────────────────────────────────────────

    private static void addSpring(Group parent, Edge<BlockStructure> e,
                                  Map<BlockStructure, Point3D> positions,
                                  Color glyphColor) {
        Point3D from = positions.get(e.from());
        Point3D to = positions.get(e.to());
        if (from == null || to == null) return;
        Cylinder c = makeSpring(from, to, glyphColor);
        if (c != null) parent.getChildren().add(c);
    }

    private static Cylinder makeSpring(Point3D from, Point3D to, Color glyphColor) {
        if (from.distance(to) < 1) return null;
        var c = new Cylinder(SPRING_RADIUS, 1);
        c.setMaterial(new PhongMaterial(glyphColor.deriveColor(0, 1, 1, 0.45)));
        GlyphNode3d.positionSpring(c,
                from.getX(), from.getY(), from.getZ(),
                to.getX(),   to.getY(),   to.getZ());
        return c;
    }
}
