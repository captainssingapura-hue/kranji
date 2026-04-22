package kranji.zi;

/**
 * 上下结构 — Typed top-bottom composition.
 *
 * <p>Each slot is an explicit generic type, so an implementation such
 * as {@code Lv implements ComposedZiT, TopBottomT<Kou, Kou>} states at
 * the type level that 吕's top and bottom slots are both 口.</p>
 *
 * @param <T> type of the top block
 * @param <B> type of the bottom block
 */
public non-sealed interface TopBottomT<T extends BlockStructure, B extends BlockStructure>
        extends CompositionLayoutT {

    /** The top block. */
    T top();

    /** The bottom block. */
    B bottom();

    @Override
    default CompositionLayout legacyLayout() {
        return new CompositionLayout.TopBottom(top(), bottom());
    }
}
