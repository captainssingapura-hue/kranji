package kranji.zi;

/**
 * Anonymous top-middle-bottom composite — value-object wrapper for
 * inline three-row composed slots.
 *
 * @param <T> type of the top block
 * @param <M> type of the middle block
 * @param <B> type of the bottom block
 */
public record TopMiddleBottomPartT<
        T extends BlockStructure,
        M extends BlockStructure,
        B extends BlockStructure>(T top, M middle, B bottom)
        implements ComposedBlock, TopMiddleBottomT<T, M, B> {

    @Override
    public CompositionLayout composition() {
        return legacyLayout();
    }
}
