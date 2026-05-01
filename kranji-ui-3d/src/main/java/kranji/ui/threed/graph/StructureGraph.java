package kranji.ui.threed.graph;

import kranji.zi.BlockStructure;
import kranji.zi.ComposedBlock;
import kranji.zi.Zi;

import java.util.ArrayList;
import java.util.List;

/**
 * Pure-data view of a single character's <em>internal</em> composition
 * tree as a graph: nodes are visible {@link BlockStructure} components
 * (root + named intermediates + leaves), edges are parent-child links.
 *
 * <p>Anonymous synthetic intermediates (empty glyph — typically the
 * auto-generated {@code _Inner1} records) are <em>routed around</em>:
 * their would-be edges shift up to the nearest named ancestor. Result
 * is a clean tree of actually-visible characters.</p>
 *
 * <p>Pure: no fields, no mutation outside the local accumulators.</p>
 */
public final class StructureGraph {

    /** Immutable snapshot of one character's structure graph. */
    public record Graph(
            BlockStructure root,
            List<BlockStructure> nodes,
            List<Edge<BlockStructure>> edges
    ) {
        public boolean isEmpty() { return nodes.isEmpty(); }
    }

    private StructureGraph() {}

    /**
     * Walk {@code zi.structure()} top-down and emit a graph of every
     * named block (non-empty {@code glyph()}) connected to its nearest
     * named ancestor.
     */
    public static Graph buildFor(Zi zi) {
        if (zi == null) return new Graph(null, List.of(), List.of());
        var nodes = new ArrayList<BlockStructure>();
        var edges = new ArrayList<Edge<BlockStructure>>();
        walk(zi.structure(), null, nodes, edges);
        return new Graph(zi.structure(), List.copyOf(nodes), List.copyOf(edges));
    }

    /**
     * Recursive walker. {@code visibleAncestor} = the nearest already-
     * recorded named ancestor; null at the top.
     */
    private static void walk(BlockStructure node, BlockStructure visibleAncestor,
                             List<BlockStructure> nodes,
                             List<Edge<BlockStructure>> edges) {
        BlockStructure newAncestor = visibleAncestor;
        if (isVisible(node)) {
            nodes.add(node);
            if (visibleAncestor != null) {
                edges.add(new Edge<>(visibleAncestor, node));
            }
            newAncestor = node;
        }
        if (node instanceof ComposedBlock cb) {
            for (BlockStructure child : cb.composition().components()) {
                walk(child, newAncestor, nodes, edges);
            }
        }
    }

    private static boolean isVisible(BlockStructure b) {
        String g = b.glyph();
        return g != null && !g.isEmpty();
    }
}
