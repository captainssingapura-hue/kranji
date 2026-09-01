package kranji.reading.app.zi;

import hue.captains.singapura.js.homing.tree.NodeIdentity;

import java.util.Objects;

/**
 * Identity of a node in a Zi projection.
 *
 * <p>Carries the <b>projection</b> as well as the key, at every level and not
 * only at the leaf. Two projections that each group by some key {@code h} would
 * otherwise mint the same identity for that node, and one identity cannot
 * occupy two positions — so the second projection would fail at boot rather
 * than at render.</p>
 *
 * <p>Being our own record type, this cannot collide with a typed catalogue's
 * identity by accident: records of different classes are never equal.</p>
 *
 * @param projection the projection name, e.g. {@code phonic}
 * @param path       slash-joined segments from the projection root, e.g. {@code h/ao/3}
 */
public record ZiNodeIdentity(String projection, String path) implements NodeIdentity {

    public ZiNodeIdentity {
        Objects.requireNonNull(projection, "projection");
        Objects.requireNonNull(path, "path");
    }

    /** The projection's own root. */
    public static ZiNodeIdentity root(String projection) {
        return new ZiNodeIdentity(projection, "");
    }

    /** A child of this node, addressed by one further segment. */
    public ZiNodeIdentity child(String segment) {
        return new ZiNodeIdentity(projection, path.isEmpty() ? segment : path + "/" + segment);
    }
}
