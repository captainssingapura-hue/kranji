package kranji.graph;

import java.util.Optional;

/**
 * A directed, labeled edge in the character navigation graph.
 *
 * @param source   the source vertex ID
 * @param target   the target vertex ID
 * @param label    the edge label
 * @param position optional positional metadata (only meaningful for composition edges)
 */
public record Edge(
        VertexId source,
        VertexId target,
        EdgeLabel label,
        Optional<Position> position
) {

    /** Convenience constructor for edges without position metadata. */
    public Edge(VertexId source, VertexId target, EdgeLabel label) {
        this(source, target, label, Optional.empty());
    }

    /** Convenience constructor for composition edges with position. */
    public static Edge composition(VertexId source, VertexId target, Position pos) {
        return new Edge(source, target, EdgeLabel.COMPOSED_OF, Optional.of(pos));
    }
}
