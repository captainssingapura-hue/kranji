package kranji.zi;

/**
 * 上三包围 — Typed three-side top enclosure.
 *
 * @param <W> type of the wrapper block
 * @param <C> type of the content block
 */
public non-sealed interface SemiEnclosureTopThreeT<
        W extends BlockStructure,
        C extends BlockStructure> extends CompositionLayoutT {

    W wrapper();
    C content();

    @Override
    default CompositionLayout legacyLayout() {
        return new CompositionLayout.SemiEnclosureTopThree(wrapper(), content());
    }
}
