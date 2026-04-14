package kranji.classification;

/**
 * The structural position a component occupies within its parent composition.
 *
 * <p>This is an open ADT — each composition variant defines
 * its own set of role records (e.g. {@code LeftRight.Left}, {@code TopBottom.Top}).
 * All role types implement this common interface so the layout engine and
 * renderers can handle them polymorphically.</p>
 *
 * <p>Stateless records: equality is by type (no fields), so two
 * {@code LeftRight.Left()} instances are always equal.</p>
 */
public interface BlockRole {

    /** Human-readable label for debugging / display. */
    String label();

    /**
     * True for overlay roles whose blocks render behind content
     * (e.g. enclosure wrappers and outer frames).
     */
    default boolean isOverlay() { return false; }
}
