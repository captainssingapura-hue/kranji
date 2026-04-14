package kranji.zi;

import kranji.classification.BlockRole;
import kranji.layout.HintedComponent;
import kranji.layout.LayoutHint;
import kranji.layout.Politeness;

/**
 * 独体字 / 偏旁 — An atomic structural block of Chinese characters.
 *
 * <p>Covers both standalone characters (where {@code glyph() == standalone()},
 * e.g. 木, 日, 口) and parts/偏旁 (where {@code glyph() != standalone()},
 * e.g. 氵 derives from 水, 亻 derives from 人).</p>
 *
 * <p>Not sealed — the set of atomic components is large (~500) and open
 * for extension as more components are catalogued across modules.</p>
 *
 * <p>Layout knowledge (politeness, position hints) is intrinsic: each
 * component knows its own calligraphic behavior via {@link #hintFor}
 * and {@link #politeness}.</p>
 */
public interface SingularBlock extends BlockStructure {

    // ── Identity ──────────────────────────────────────────────────────

    /** The visual glyph form (e.g. "氵", "木"). */
    @Override String glyph();

    /** The conventional Chinese name (e.g. "三点水"). Defaults to glyph. */
    default String name() { return glyph(); }

    /** The full standalone character this derives from (e.g. "水" for 氵). Defaults to glyph. */
    default String standalone() { return glyph(); }

    /** English semantic meaning (e.g. "water"). */
    default String meaning() { return ""; }

    /** Pinyin of the standalone form (e.g. "shuǐ"). */
    default String pinyinText() { return ""; }

    /** Stroke count of this component form. */
    default int strokes() { return 0; }

    // ── Intrinsic layout knowledge ────────────────────────────────────

    /**
     * Position-specific layout hint intrinsic to this component.
     *
     * <p>Override in concrete records to provide role-aware scaling or
     * glyph substitution. Returns {@code null} by default (no special
     * handling).</p>
     *
     * @param role the structural position this component occupies
     * @return a layout hint, or {@code null} for default behavior
     */
    default LayoutHint hintFor(BlockRole role) {
        return null;
    }

    /**
     * Position-specific politeness level for this component.
     *
     * <p>Determines how much space this component yields to siblings
     * during block layout.</p>
     *
     * @param role the structural position this component occupies
     * @return the politeness level (default {@link Politeness#NEUTRAL})
     */
    default Politeness politeness(BlockRole role) {
        return Politeness.NEUTRAL;
    }

    /**
     * Bind this component to a specific structural role for rendering.
     *
     * <p>Returns a {@link HintedComponent} that carries both this leaf
     * and the bound role.</p>
     *
     * @param role the structural position (e.g. {@code LeftRight.LEFT})
     */
    default HintedComponent as(BlockRole role) {
        return new HintedComponent(this, role);
    }

    // ── Fallback ──────────────────────────────────────────────────────

    /**
     * Create a plain SingularBlock with no position-specific hints.
     * Used for glyphs that don't yet have a dedicated record.
     */
    static SingularBlock plain(String glyph) {
        return new Plain(glyph);
    }

    /** A SingularBlock with no position-specific hints. */
    record Plain(String glyph) implements SingularBlock {}
}
