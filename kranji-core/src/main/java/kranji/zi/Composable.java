package kranji.zi;

/**
 * A sum type bridging {@link Zi} (semantics) and {@link BlockStructure} (grammar)
 * at composition slots.
 *
 * <p>Every slot in a {@link ComposedBlock} accepts a {@code Composable}, which
 * is either a reference to a full {@link Zi} (carrying meaning, pinyin, etc.)
 * or a raw {@link BlockStructure} (a structural fragment with no semantic
 * identity, e.g. a reusable double-口 part).</p>
 *
 * <p>The layout engine resolves each {@code Composable} to a {@code BlockStructure}
 * before rendering:</p>
 * <ul>
 *   <li>{@link OfZi} — unwraps via {@code zi.structure()}</li>
 *   <li>{@link OfBlock} — uses the block directly</li>
 * </ul>
 */
public sealed interface Composable permits Composable.OfZi, Composable.OfBlock {

    /** The display glyph of the wrapped element. */
    String glyph();

    /** A composition slot referencing a full Zi (semantic identity). */
    record OfZi(Zi zi) implements Composable {
        @Override public String glyph() { return zi.character(); }
    }

    /** A composition slot referencing a raw BlockStructure (structural fragment). */
    record OfBlock(BlockStructure block) implements Composable {
        @Override public String glyph() { return block.glyph(); }
    }

    // ── Factory methods ───────────────────────────────────────────────

    /** Wrap a Zi as a Composable. */
    static Composable ofZi(Zi zi) { return new OfZi(zi); }

    /** Wrap a BlockStructure as a Composable. */
    static Composable ofBlock(BlockStructure block) { return new OfBlock(block); }
}
