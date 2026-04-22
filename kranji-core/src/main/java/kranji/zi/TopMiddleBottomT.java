package kranji.zi;

/**
 * 上中下结构 — Typed top-middle-bottom composition.
 *
 * @param <T> type of the top block
 * @param <M> type of the middle block
 * @param <B> type of the bottom block
 */
public non-sealed interface TopMiddleBottomT<
        T extends BlockStructure,
        M extends BlockStructure,
        B extends BlockStructure> extends CompositionLayoutT {

    T top();
    M middle();
    B bottom();

    @Override
    default CompositionLayout legacyLayout() {
        return new CompositionLayout.TopMiddleBottom(top(), middle(), bottom());
    }
}
