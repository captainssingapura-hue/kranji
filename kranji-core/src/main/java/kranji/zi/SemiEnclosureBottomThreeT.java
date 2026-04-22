package kranji.zi;

/**
 * 下三包围 — Typed three-side bottom enclosure.
 *
 * @param <W> type of the wrapper block
 * @param <C> type of the content block
 */
public non-sealed interface SemiEnclosureBottomThreeT<
        W extends BlockStructure,
        C extends BlockStructure> extends CompositionLayoutT {

    W wrapper();
    C content();

    @Override
    default CompositionLayout legacyLayout() {
        return new CompositionLayout.SemiEnclosureBottomThree(wrapper(), content());
    }
}
