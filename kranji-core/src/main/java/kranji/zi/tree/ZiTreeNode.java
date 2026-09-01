package kranji.zi.tree;

import java.util.List;

/**
 * A node in a projection of the character collection.
 *
 * <p>Plain records with no framework types, per CD-001: a projection is a pure
 * function over the hub, so its result must be expressible — and testable —
 * without a browser or a server. An adapter in a UI module turns this into
 * whatever that framework wants.</p>
 *
 * <p>Every node carries two names, and the distinction is the point.
 * {@link #segment()} is the address: ASCII, stable, derived. {@link #label()}
 * is what a reader sees: the character itself, its reading, whatever is
 * clearest. Conflating them forces one to compromise the other.</p>
 */
public sealed interface ZiTreeNode permits ZiBranch, ZiTerminal {

    /** ASCII address segment, unique among siblings. */
    String segment();

    /** Display name; no charset restriction. */
    String label();

    /** Number of characters at or beneath this node. */
    int characterCount();

    default List<ZiTreeNode> children() { return List.of(); }
}
