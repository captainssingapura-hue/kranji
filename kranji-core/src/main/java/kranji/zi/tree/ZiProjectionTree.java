package kranji.zi.tree;

import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.Objects;

/**
 * A projection of the character collection, named.
 *
 * <p>The name is not decoration and not merely a path segment: it must appear
 * in the <b>identity of every node</b>, at every level, not only in the leaf.
 * Two projections that both group by some key {@code h} would otherwise mint
 * the same identity for that partition, and one identity cannot occupy two
 * positions — the downstream registry rejects it at boot, correctly.</p>
 *
 * <p>So an adapter minting identities should key on the pair, e.g.
 * {@code (projection, path)}, never on the path alone. Pairing the name with
 * the root here makes that unambiguous rather than leaving it to be inferred
 * from the root's segment.</p>
 *
 * <p>Cheap to carry now and awkward to retrofit: once addresses exist, changing
 * how identities are minted moves every URL.</p>
 *
 * @param projection stable name — {@code phonic}, {@code radical}, {@code strokes}
 * @param root       the tree, whose own segment is conventionally the projection name
 */
public record ZiProjectionTree(String projection, ZiBranch root) implements ValueObject {

    public ZiProjectionTree {
        Objects.requireNonNull(projection, "projection");
        Objects.requireNonNull(root, "root");
        if (!projection.matches("[a-z0-9-]+")) {
            throw new IllegalArgumentException(
                    "projection name must be a lowercase address-safe token: '" + projection + "'");
        }
    }

    /** Characters in this projection. Every projection views the whole collection. */
    public int characterCount() {
        return root.characterCount();
    }
}
