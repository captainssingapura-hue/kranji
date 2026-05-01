package kranji.ui.threed.graph;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import kranji.zi.Zi;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Pure builder: turns a laid-out {@link ComponentGraph.Graph} into a
 * JavaFX {@link Group} of node spheres, glyph labels, and edge cylinders.
 *
 * <p>{@link #render} is a function of its arguments — no fields, no
 * mutation, no external state. Click handling is delegated through a
 * {@link Consumer}, so the caller decides what re-focusing means.</p>
 */
public final class ComponentGraphRenderer {

    /** Visual mapping from role to node accent color. */
    private static final Map<ComponentGraph.Role, Color> ROLE_COLORS = Map.of(
            ComponentGraph.Role.FOCAL,     Color.rgb(220, 38, 38),    // red
            ComponentGraph.Role.COMPONENT, Color.rgb(37, 99, 235),    // blue
            ComponentGraph.Role.USER,      Color.rgb(34, 139, 34)     // green
    );

    private static final double NODE_SPHERE_R = 8;
    private static final double NODE_LABEL_FONT = 28;
    private static final double EDGE_RADIUS = 1.2;
    private static final Color EDGE_COLOR = Color.rgb(170, 170, 170, 0.7);

    private ComponentGraphRenderer() {}

    /**
     * @param graph     the focal-neighborhood graph to render
     * @param positions per-node 3D positions (e.g. from {@link ForceLayout3d})
     * @param onClick   click callback for any node; nullable
     */
    public static Group render(ComponentGraph.Graph graph,
                               Map<Zi, Point3D> positions,
                               Consumer<Zi> onClick) {
        var root = new Group();
        graph.edges().forEach(e -> addEdge(root, e, positions));
        graph.nodes().forEach(n -> addNode(root, n, graph.roles(), positions, onClick));
        return root;
    }

    // ── Nodes ──────────────────────────────────────────────────────

    private static void addNode(Group parent, Zi z,
                                Map<Zi, ComponentGraph.Role> roles,
                                Map<Zi, Point3D> positions,
                                Consumer<Zi> onClick) {
        Point3D p = positions.get(z);
        if (p == null) return;
        Color color = ROLE_COLORS.getOrDefault(roles.get(z), Color.GRAY);
        var node = makeNode(z, color);
        node.setTranslateX(p.getX());
        node.setTranslateY(p.getY());
        node.setTranslateZ(p.getZ());
        if (onClick != null) {
            node.setOnMouseClicked(ev -> onClick.accept(z));
        }
        parent.getChildren().add(node);
    }

    /** Sphere accent + centred glyph label. */
    private static Group makeNode(Zi z, Color color) {
        var sphere = new Sphere(NODE_SPHERE_R);
        sphere.setMaterial(new PhongMaterial(color));
        var text = makeCenteredLabel(z.character());
        return new Group(sphere, text);
    }

    private static Text makeCenteredLabel(String glyph) {
        var text = new Text(glyph);
        text.setFont(Font.font("Microsoft YaHei", NODE_LABEL_FONT));
        text.setFill(Color.BLACK);
        var bounds = text.getLayoutBounds();
        text.setTranslateX(-bounds.getMinX() - bounds.getWidth() / 2);
        text.setTranslateY(-bounds.getMinY() - bounds.getHeight() / 2);
        text.setTranslateZ(-NODE_SPHERE_R - 1);   // sit just in front of the sphere
        return text;
    }

    // ── Edges ──────────────────────────────────────────────────────

    private static void addEdge(Group parent, Edge<Zi> e,
                                Map<Zi, Point3D> positions) {
        Point3D from = positions.get(e.from());
        Point3D to = positions.get(e.to());
        if (from == null || to == null) return;
        Cylinder c = makeEdgeCylinder(from, to);
        if (c != null) parent.getChildren().add(c);
    }

    /** Thin cylinder oriented from {@code from} to {@code to}. */
    private static Cylinder makeEdgeCylinder(Point3D from, Point3D to) {
        Point3D diff = to.subtract(from);
        double length = diff.magnitude();
        if (length < 1) return null;

        var c = new Cylinder(EDGE_RADIUS, length);
        c.setMaterial(new PhongMaterial(EDGE_COLOR));
        orientCylinder(c, diff, length);
        var mid = from.midpoint(to);
        c.setTranslateX(mid.getX());
        c.setTranslateY(mid.getY());
        c.setTranslateZ(mid.getZ());
        return c;
    }

    /** Rotate so the cylinder's local Y axis aligns with {@code diff}. */
    private static void orientCylinder(Cylinder c, Point3D diff, double length) {
        Point3D dir = diff.multiply(1.0 / length);
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D rotAxis = yAxis.crossProduct(dir);
        if (rotAxis.magnitude() > 1e-6) {
            double angle = Math.toDegrees(Math.acos(
                    Math.max(-1.0, Math.min(1.0, yAxis.dotProduct(dir)))));
            c.getTransforms().add(new Rotate(angle, rotAxis));
        } else if (dir.getY() < 0) {
            c.getTransforms().add(new Rotate(180, new Point3D(1, 0, 0)));
        }
    }
}
