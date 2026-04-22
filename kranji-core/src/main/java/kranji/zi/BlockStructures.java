package kranji.zi;

import java.util.function.Consumer;

/**
 * Static utilities for {@link BlockStructure} trees.
 *
 * <p>Home for pure recursive walkers over the grammar axis — currently
 * {@link #depthOf(BlockStructure)} and
 * {@link #forEachComposedZiChild(ComposedBlock, Consumer)}. Add more
 * walkers here as they arise (leaf enumeration, node counts, role
 * histograms, etc.) rather than sprinkling static helpers across
 * unrelated classes.</p>
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
     * number of named references. The UI demo uses this to drive its
     * "Depth N" cascade filter over the flat
     * {@code kranji-common-perclass} registry.</p>
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

    /**
     * Visit every {@link ComposedZi} reachable from {@code root}'s
     * composition, treating reached ComposedZis as terminals.
     *
     * <p>Traversal rules:</p>
     * <ul>
     *   <li>{@link SingularBlock} slots — skipped (not a Zi).</li>
     *   <li>{@link ComposedPart} slots — recursed into (anonymous wrapper
     *       that may hide a named Zi inside its layout).</li>
     *   <li>{@link ComposedZi} slots — {@code consumer} is invoked and
     *       recursion stops there (the referenced Zi has its own identity
     *       and its sub-tree is its own concern).</li>
     * </ul>
     *
     * <p>Use case: identity audits — a depth-N composition's referenced
     * lower-depth Zis must be the canonical registry instances (compared
     * by {@code ==}), never freshly-constructed duplicates with equal
     * field content.</p>
     *
     * @param root     the owning composed block whose composition is walked
     * @param consumer called once per referenced {@link ComposedZi}
     */
    public static void forEachComposedZiChild(ComposedBlock root, Consumer<ComposedZi> consumer) {
        for (BlockStructure c : root.components()) {
            switch (c) {
                case ComposedZi zi                            -> consumer.accept(zi);
                case ComposedPart cp                          -> forEachComposedZiChild(cp, consumer);
                case ComposedZiT zt                           -> { /* typed named composite — terminal; legacy consumer can't accept it */ }
                case LeftRightPartT<?, ?> p                   -> forEachComposedZiChild(p, consumer);
                case TopBottomPartT<?, ?> p                   -> forEachComposedZiChild(p, consumer);
                case LeftMiddleRightPartT<?, ?, ?> p          -> forEachComposedZiChild(p, consumer);
                case TopMiddleBottomPartT<?, ?, ?> p          -> forEachComposedZiChild(p, consumer);
                case FullEnclosurePartT<?, ?> p               -> forEachComposedZiChild(p, consumer);
                case SemiEnclosureUpperLeftPartT<?, ?> p      -> forEachComposedZiChild(p, consumer);
                case SemiEnclosureUpperRightPartT<?, ?> p     -> forEachComposedZiChild(p, consumer);
                case SemiEnclosureBottomLeftPartT<?, ?> p     -> forEachComposedZiChild(p, consumer);
                case SemiEnclosureTopThreePartT<?, ?> p       -> forEachComposedZiChild(p, consumer);
                case SemiEnclosureBottomThreePartT<?, ?> p    -> forEachComposedZiChild(p, consumer);
                case SemiEnclosureLeftThreePartT<?, ?> p      -> forEachComposedZiChild(p, consumer);
                case SingularBlock sb                         -> { /* terminal — not a Zi */ }
            }
        }
    }
}
