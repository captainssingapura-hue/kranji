package kranji.layout;

/**
 * 礼让 — Expresses a component's willingness to yield space to siblings.
 *
 * <p>Used by {@link BlockLayoutEngine} to determine split ratios between
 * sibling components. Higher politeness means the component defers more
 * space to its neighbors.</p>
 *
 * <p>The engine converts politeness gaps between siblings into proportional
 * shares using a relative gap formula. Each ordinal step shifts the split
 * by approximately 10%.</p>
 */
public enum Politeness {

    /** Claims space aggressively — e.g. complex multi-stroke components. */
    ASSERTIVE,    // ordinal 0

    /** Default — fair share, no special deference. */
    NEUTRAL,      // ordinal 1

    /** Gives up some space — e.g. 口, 田, 日 as side components. */
    YIELDING,     // ordinal 2

    /** Maximally yields — e.g. 亻, 氵, 艹 (narrow/short radicals). */
    DEFERENTIAL;  // ordinal 3
}
