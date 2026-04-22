package kranji.zi;

/**
 * 左三包围 — Typed three-side left enclosure.
 *
 * @param <W> type of the wrapper block
 * @param <C> type of the content block
 */
public non-sealed interface SemiEnclosureLeftThreeT<
        W extends BlockStructure,
        C extends BlockStructure> extends CompositionLayoutT {

    W wrapper();
    C content();

    @Override
    default CompositionLayout legacyLayout() {
        return new CompositionLayout.SemiEnclosureLeftThree(wrapper(), content());
    }
}
