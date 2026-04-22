package kranji.zi;

/**
 * Anonymous top-bottom composite — a value-object wrapper for
 * inline composed slots that have no name/pinyin/etymology of
 * their own.
 *
 * <p>See {@link LeftRightPartT} for the same pattern applied to
 * left-right composites.</p>
 *
 * @param <T> type of the top block
 * @param <B> type of the bottom block
 */
public record TopBottomPartT<T extends BlockStructure, B extends BlockStructure>(T top, B bottom)
        implements ComposedBlock, TopBottomT<T, B> {

    @Override
    public CompositionLayout composition() {
        return legacyLayout();
    }
}
