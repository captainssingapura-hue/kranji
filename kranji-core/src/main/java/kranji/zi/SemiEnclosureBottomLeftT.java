package kranji.zi;

/**
 * 左下包围 — Typed semi-enclosure with wrapper on lower left.
 *
 * @param <W> type of the wrapper block
 * @param <C> type of the content block
 */
public non-sealed interface SemiEnclosureBottomLeftT<
        W extends BlockStructure,
        C extends BlockStructure> extends CompositionLayoutT {

    W wrapper();
    C content();

    @Override
    default CompositionLayout legacyLayout() {
        return new CompositionLayout.SemiEnclosureBottomLeft(wrapper(), content());
    }
}
