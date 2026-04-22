package kranji.zi;

/**
 * 左上包围 — Typed semi-enclosure with wrapper on upper left.
 *
 * @param <W> type of the wrapper block
 * @param <C> type of the content block
 */
public non-sealed interface SemiEnclosureUpperLeftT<
        W extends BlockStructure,
        C extends BlockStructure> extends CompositionLayoutT {

    W wrapper();
    C content();

    @Override
    default CompositionLayout legacyLayout() {
        return new CompositionLayout.SemiEnclosureUpperLeft(wrapper(), content());
    }
}
