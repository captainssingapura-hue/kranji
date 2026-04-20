package kranji.zi;

import java.util.List;

/**
 * 合体字 — A composed structural block with identity. Either a named
 * character ({@link ComposedZi}) or an anonymous value-object wrapper
 * ({@link ComposedPart}).
 *
 * <p>Narrow seal: the 11 spatial layout records that previously lived
 * directly under {@code ComposedBlock} now live under
 * {@link CompositionLayout} and are accessed via {@link #composition()}.
 * This enforces the invariant that every {@link BlockStructure} slot is
 * either an atomic {@link SingularBlock} or a named composite — never a
 * raw layout record.</p>
 */
public sealed interface ComposedBlock extends BlockStructure
        permits ComposedZi, ComposedPart {

    /** The internal spatial layout — one of 11 {@link CompositionLayout} variants. */
    CompositionLayout composition();

    /** Delegated glyph view — concatenation of the inner layout. */
    @Override default String glyph() { return composition().glyph(); }

    /**
     * The direct (level-1) components — delegates to
     * {@link CompositionLayout#components()}.
     */
    default List<BlockStructure> components() {
        return composition().components();
    }
}
