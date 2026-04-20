package kranji.zi;

/**
 * Static utilities for {@link BlockStructure} trees.
 *
 * <p>Home for pure recursive walkers over the grammar axis — currently
 * just {@link #depthOf(BlockStructure)}. Add more walkers here as they
 * arise (leaf enumeration, node counts, role histograms, etc.) rather
 * than sprinkling static helpers across unrelated classes.</p>
 */
public final class BlockStructures {

    private BlockStructures() {}

    /**
     * Structural composition depth of the given block.
     *
     * <p>Defined recursively:</p>
     * <ul>
     *   <li>A {@link SingularBlock} has depth {@code 0} (atomic leaf).</li>
     *   <li>A {@link ComposedBlock} ({@link ComposedZi} or
     *       {@link ComposedPart}) has depth
     *       {@code 1 + max(depthOf(c) for c in composition().components())}.</li>
     * </ul>
     *
     * <p>Both named composites ({@link ComposedZi}) and anonymous
     * wrappers ({@link ComposedPart}) contribute {@code +1} — the count
     * measures structural nesting of the composition tree, not just the
     * number of named references. This matches how the
     * {@code kranji-common-depth<N>} modules are partitioned: every
     * member of {@code Depth<N>.ALL} is expected to have depth {@code N}.</p>
     *
     * @param block any {@link BlockStructure} (must be non-null)
     * @return the composition depth (≥ 0)
     */
    public static int depthOf(BlockStructure block) {
        return switch (block) {
            case SingularBlock sb -> 0;
            case ComposedBlock cb -> 1 + cb.components().stream()
                    .mapToInt(BlockStructures::depthOf)
                    .max()
                    .orElse(-1);
        };
    }
}
