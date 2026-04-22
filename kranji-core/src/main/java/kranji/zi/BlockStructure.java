package kranji.zi;

/**
 * The structural grammar of Chinese character composition.
 *
 * <p>A {@code BlockStructure} describes how visual blocks are spatially
 * arranged — it is purely about layout, not semantics. It is either
 * atomic ({@link SingularBlock}) or composed ({@link ComposedBlock}).</p>
 *
 * <p>This is the "grammar" axis of the double hierarchy. The "semantics"
 * axis is {@link Zi}, which holds meaning, pinyin, and other identity
 * information.</p>
 *
 * <p>The seal below is the type-level guarantee that every slot in a
 * composition is either an atomic {@link SingularBlock} or a named/wrapped
 * {@link ComposedBlock} (a {@link ComposedZi} or {@link ComposedPart}) —
 * raw {@link CompositionLayout} records never appear as slot values
 * directly. They can only occur as the inner {@code composition()} field
 * of a {@link ComposedBlock}.</p>
 */
public sealed interface BlockStructure
        permits SingularBlock, ComposedBlock {

    /** The visual glyph of this structural block. */
    String glyph();
}
