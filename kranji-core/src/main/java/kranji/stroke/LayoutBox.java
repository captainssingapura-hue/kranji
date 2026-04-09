package kranji.stroke;

/**
 * Computed bounding box for a node in the layout tree.
 *
 * <p>Produced by the bottom-up measure pass of {@link LayoutEngine}.
 * Units are abstract layout units (the base size for a leaf is typically 60).</p>
 *
 * @param width  horizontal extent
 * @param height vertical extent
 */
public record LayoutBox(double width, double height) {

    public static final double BASE_SIZE = 60;

    /** A single leaf component's default box. */
    public static final LayoutBox LEAF = new LayoutBox(BASE_SIZE, BASE_SIZE);
}
