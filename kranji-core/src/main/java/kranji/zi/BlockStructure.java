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
 * information. A {@code Zi} <em>contains</em> a {@code BlockStructure}
 * but does not extend it.</p>
 */
public interface BlockStructure {

    /** The visual glyph of this structural block. */
    String glyph();
}
