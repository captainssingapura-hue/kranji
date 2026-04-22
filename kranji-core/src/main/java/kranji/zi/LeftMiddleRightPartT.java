package kranji.zi;

/**
 * Anonymous left-middle-right composite — value-object wrapper for
 * inline three-column composed slots.
 *
 * @param <L> type of the left block
 * @param <M> type of the middle block
 * @param <R> type of the right block
 */
public record LeftMiddleRightPartT<
        L extends BlockStructure,
        M extends BlockStructure,
        R extends BlockStructure>(L left, M middle, R right)
        implements ComposedBlock, LeftMiddleRightT<L, M, R> {

    @Override
    public CompositionLayout composition() {
        return legacyLayout();
    }
}
