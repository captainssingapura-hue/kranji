package kranji.zi;

/**
 * Anonymous full-enclosure composite — value-object wrapper for
 * inline outer/inner composed slots.
 *
 * @param <O> type of the outer (frame) block
 * @param <I> type of the inner (content) block
 */
public record FullEnclosurePartT<
        O extends BlockStructure,
        I extends BlockStructure>(O outer, I inner)
        implements ComposedBlock, FullEnclosureT<O, I> {

    @Override
    public CompositionLayout composition() {
        return legacyLayout();
    }
}
