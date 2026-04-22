package kranji.zi;

import kranji.classification.EtymologicalCategory;

/**
 * Typed mixin interface for named composed characters (Option A).
 *
 * <p>A concrete per-character record implements {@code ComposedZiT}
 * together with the typed layout it physically has, e.g.:</p>
 *
 * <pre>{@code
 * public record Ming() implements ComposedZiT, LeftRightT<Ri, Yue> {
 *     public static final Ming INSTANCE = new Ming();
 *     @Override public Ri   left()  { return Ri.INSTANCE;  }
 *     @Override public Yue  right() { return Yue.INSTANCE; }
 *     @Override public String glyph() { return "\u660E"; }
 *     // ... pinyin(), strokes(), meaning(), etc.
 * }
 * }</pre>
 *
 * <p>Option A = this interface is a thin mixin. It extends
 * {@link ComposedBlock} (so instances fit as slot values in another
 * character's composition) and {@link Zi} (so they carry semantic
 * identity). Defaults provide {@link #character()},
 * {@link #structure()}, and {@link #composition()} bridges — the last
 * one delegates to the sibling layout interface (e.g.
 * {@link LeftRightT#legacyLayout()}) so callers of the legacy
 * {@code ComposedBlock.composition()} API keep working.</p>
 *
 * <p>There is no structural constraint on the layout: a record can
 * mix in <em>any</em> {@link CompositionLayoutT} variant. Because the
 * interface is {@code non-sealed}, downstream modules
 * (e.g. {@code kranji-common-perclass}) can define concrete
 * implementations freely.</p>
 */
public non-sealed interface ComposedZiT extends ComposedBlock, Zi {

    /**
     * The typed layout payload — every {@code ComposedZiT} implementation
     * must also implement exactly one {@link CompositionLayoutT} variant
     * (e.g. {@code LeftRightT<L,R>} or {@code TopBottomT<T,B>}). This
     * accessor exposes the record as its layout view so bridge code can
     * read slot values without casting.
     */
    CompositionLayoutT layout();

    // ── Zi bridge defaults ──────────────────────────────────────────

    /** Defaults to {@link #glyph()}. */
    @Override default String character() { return glyph(); }

    /** A ComposedZiT <em>is</em> its own structure. */
    @Override default BlockStructure structure() { return this; }

    /** Kangxi radical number — defaults to 0; override when known. */
    @Override default int radicalNo() { return 0; }

    /** Etymology — defaults to Pictograph; override as needed. */
    @Override default EtymologicalCategory etymology() {
        return new EtymologicalCategory.Pictograph();
    }

    // ── ComposedBlock bridge ────────────────────────────────────────

    /**
     * Projects the typed layout down to the legacy untyped
     * {@link CompositionLayout} form. Delegates to
     * {@link #layout()}'s {@link CompositionLayoutT#legacyLayout()}.
     */
    @Override default CompositionLayout composition() {
        return layout().legacyLayout();
    }
}
