package kranji.ui.threed.graph;

/**
 * Generic directed edge between two nodes of the same type.
 *
 * <p>Shared by {@link ComponentGraph} (corpus-mode, {@code N = Zi}) and
 * {@link StructureGraph} (intra-character mode, {@code N = BlockStructure}).
 * Renderers that don't care about direction can ignore the from/to
 * distinction; force-layout uses both ends symmetrically.</p>
 */
public record Edge<N>(N from, N to) {}
