package kranji.zi;

/**
 * 左右结构 — Typed left-right composition.
 *
 * <p>Unlike the untyped {@link CompositionLayout.LeftRight} record,
 * each slot here is an explicit generic type, so an implementation
 * such as {@code Ming implements ComposedZiT, LeftRightT<Ri, Yue>}
 * states at the type level that 明's left slot is 日 and its right
 * slot is 月.</p>
 *
 * @param <L> type of the left block
 * @param <R> type of the right block
 */
public non-sealed interface LeftRightT<L extends BlockStructure, R extends BlockStructure>
        extends CompositionLayoutT {

    /** The left block. */
    L left();

    /** The right block. */
    R right();

    @Override
    default CompositionLayout legacyLayout() {
        return new CompositionLayout.LeftRight(left(), right());
    }
}
