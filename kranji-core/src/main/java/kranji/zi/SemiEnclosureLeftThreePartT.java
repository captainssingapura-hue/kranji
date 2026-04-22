package kranji.zi;

/**
 * Anonymous left-three semi-enclosure composite — value-object wrapper
 * for inline wrapper/content composed slots.
 *
 * @param <W> type of the wrapper block
 * @param <C> type of the content block
 */
public record SemiEnclosureLeftThreePartT<
        W extends BlockStructure,
        C extends BlockStructure>(W wrapper, C content)
        implements ComposedBlock, SemiEnclosureLeftThreeT<W, C> {

    @Override
    public CompositionLayout composition() {
        return legacyLayout();
    }
}
