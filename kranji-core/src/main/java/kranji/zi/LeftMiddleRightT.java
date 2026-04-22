package kranji.zi;

/**
 * 左中右结构 — Typed left-middle-right composition.
 *
 * @param <L> type of the left block
 * @param <M> type of the middle block
 * @param <R> type of the right block
 */
public non-sealed interface LeftMiddleRightT<
        L extends BlockStructure,
        M extends BlockStructure,
        R extends BlockStructure> extends CompositionLayoutT {

    L left();
    M middle();
    R right();

    @Override
    default CompositionLayout legacyLayout() {
        return new CompositionLayout.LeftMiddleRight(left(), middle(), right());
    }
}
