package kranji.layout;

/**
 * The structural role a block plays within its parent composition.
 *
 * <p>This captures WHERE a component sits in a composition — left, right,
 * top, bottom, wrapper, inner, etc. The role determines both the block's
 * proportions and its spatial position within the parent's bounding box.</p>
 */
public enum BlockRole {

    // ── Singular ───────────────────────────────────────────────
    /** The entire character is a single indivisible unit. */
    SINGULAR,

    // ── LeftRight ──────────────────────────────────────────────
    LEFT, RIGHT,

    // ── TopBottom ──────────────────────────────────────────────
    TOP, BOTTOM,

    // ── LeftMiddleRight ────────────────────────────────────────
    LEFT_OF_THREE, MIDDLE_H, RIGHT_OF_THREE,

    // ── TopMiddleBottom ────────────────────────────────────────
    TOP_OF_THREE, MIDDLE_V, BOTTOM_OF_THREE,

    // ── FullEnclosure ──────────────────────────────────────────
    /** The outer frame of a full enclosure (e.g. 囗 in 国). */
    OUTER_FRAME,
    /** The inner content of a full enclosure (e.g. 玉 in 国). */
    INNER,

    // ── Semi-enclosures ────────────────────────────────────────
    /** Wrapper component in a semi-enclosure (e.g. 辶, 疒, 勹, 门, 凵, 匚). */
    WRAPPER,
    /** Content component inside a semi-enclosure. */
    CONTENT,

    // ── Mosaic ─────────────────────────────────────────────────
    MOSAIC_TOP,
    MOSAIC_BOTTOM_LEFT,
    MOSAIC_BOTTOM_RIGHT
}
