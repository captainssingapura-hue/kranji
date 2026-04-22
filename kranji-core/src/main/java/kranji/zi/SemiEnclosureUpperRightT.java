package kranji.zi;

/**
 * 右上包围 — Typed semi-enclosure with wrapper on upper right.
 *
 * @param <W> type of the wrapper block
 * @param <C> type of the content block
 */
public non-sealed interface SemiEnclosureUpperRightT<
        W extends BlockStructure,
        C extends BlockStructure> extends CompositionLayoutT {

    W wrapper();
    C content();

    @Override
    default CompositionLayout legacyLayout() {
        return new CompositionLayout.SemiEnclosureUpperRight(wrapper(), content());
    }
}
