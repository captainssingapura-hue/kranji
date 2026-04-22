package kranji.zi;

/**
 * 全包围 — Typed full-enclosure composition (outer surrounds inner).
 *
 * @param <O> type of the outer (frame) block
 * @param <I> type of the inner (content) block
 */
public non-sealed interface FullEnclosureT<
        O extends BlockStructure,
        I extends BlockStructure> extends CompositionLayoutT {

    O outer();
    I inner();

    @Override
    default CompositionLayout legacyLayout() {
        return new CompositionLayout.FullEnclosure(outer(), inner());
    }
}
