package kranji.zi;

/**
 * Anonymous top-three semi-enclosure composite — value-object wrapper
 * for inline wrapper/content composed slots.
 *
 * @param <W> type of the wrapper block
 * @param <C> type of the content block
 */
public record SemiEnclosureTopThreePartT<
        W extends BlockStructure,
        C extends BlockStructure>(W wrapper, C content)
        implements ComposedBlock, SemiEnclosureTopThreeT<W, C> {

    @Override
    public CompositionLayout composition() {
        return legacyLayout();
    }
}
