package kranji.ui.threed.graph;

import kranji.zi.BlockStructure;
import kranji.zi.ComposedBlock;
import kranji.zi.Zi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * Pure-data view of a focal-neighborhood component graph.
 *
 * <p>Nodes = focal Zi + its immediate components (forward) + its
 * immediate users (reverse). Edges = directed component → user.
 * Building a graph is a pure function over {@code (focal, indexes)}
 * and returns immutable collections — no mutable state, no side
 * effects.</p>
 */
public final class ComponentGraph {

    public enum Role { FOCAL, COMPONENT, USER }

    /** Immutable snapshot of one focal-neighborhood graph. */
    public record Graph(
            Zi focal,
            List<Zi> nodes,                  // unique, includes focal
            List<Edge<Zi>> edges,            // directed: component → user
            Map<Zi, Role> roles
    ) {
        public boolean isEmpty() { return nodes.isEmpty(); }
    }

    private ComponentGraph() {}

    /**
     * Build the 1-hop neighborhood graph of {@code focal}.
     *
     * @param immediateUsersByGlyph inverse index: glyph string → list of
     *                              Zi that use it as an immediate slot
     *                              value
     * @param byGlyph               glyph string → preferred Zi adapter
     *                              (e.g. {@code SingularZi}, {@code ComposedZi},
     *                              or a part adapter)
     */
    public static Graph buildFocal(Zi focal,
                                   Map<String, List<Zi>> immediateUsersByGlyph,
                                   Map<String, Zi> byGlyph) {
        if (focal == null) {
            return new Graph(null, List.of(), List.of(), Map.of());
        }
        var nodes = new LinkedHashSet<Zi>();
        var edges = new ArrayList<Edge<Zi>>();
        var roles = new LinkedHashMap<Zi, Role>();

        nodes.add(focal);
        roles.put(focal, Role.FOCAL);

        addComponents(focal, byGlyph, nodes, edges, roles);
        addUsers(focal, immediateUsersByGlyph, nodes, edges, roles);

        return new Graph(focal,
                List.copyOf(nodes),
                List.copyOf(edges),
                Map.copyOf(roles));
    }

    /** Forward edges: focal's immediate slot values that map to a Zi. */
    private static void addComponents(Zi focal, Map<String, Zi> byGlyph,
                                      LinkedHashSet<Zi> nodes,
                                      List<Edge<Zi>> edges,
                                      LinkedHashMap<Zi, Role> roles) {
        if (!(focal.structure() instanceof ComposedBlock cb)) return;
        for (BlockStructure slot : cb.composition().components()) {
            String g = slot.glyph();
            if (g == null || g.isEmpty()) continue;
            Zi compZi = byGlyph.get(g);
            if (compZi == null || compZi == focal) continue;
            if (nodes.add(compZi)) roles.putIfAbsent(compZi, Role.COMPONENT);
            edges.add(new Edge<>(compZi, focal));
        }
    }

    /** Reverse edges: every Zi that uses focal as an immediate component. */
    private static void addUsers(Zi focal,
                                 Map<String, List<Zi>> usersByGlyph,
                                 LinkedHashSet<Zi> nodes,
                                 List<Edge<Zi>> edges,
                                 LinkedHashMap<Zi, Role> roles) {
        var users = usersByGlyph.getOrDefault(focal.character(), List.of());
        for (Zi user : users) {
            if (user == focal) continue;
            if (nodes.add(user)) roles.putIfAbsent(user, Role.USER);
            edges.add(new Edge<>(focal, user));
        }
    }
}
