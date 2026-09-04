package kranji.zi;

/**
 * Which slice of the corpus a character belongs to.
 *
 * <h2>One rule, two consumers</h2>
 *
 * <p>The browser fetches readings and meanings a partition at a time, and the
 * workbench reviews them a partition at a time. Those look like different
 * concerns and are not: both need <em>the same character to land in the same
 * slice every time</em>, and the moment they disagree a reviewer signs off work
 * that a reader never receives.</p>
 *
 * <p>So the rule lives here, beside the codepoint it is computed from, rather
 * than in whichever server happened to need it first.</p>
 *
 * <h2>Modulo, not ranges</h2>
 *
 * <p>A client holding a character can compute its partition without an index —
 * which is what lets the reader ask for exactly the slice it needs on a page it
 * has only just parsed.</p>
 *
 * <p>Contiguous ranges would have grouped the corpus by radical, because the
 * Unified Ideographs block is ordered radical-then-stroke: forty consecutive
 * codepoints are forty hand-verbs, then forty feathers. That is a real property
 * and it was weighed and set aside — it helps a reader who needs context to
 * judge a gloss, and the people reviewing this do not.</p>
 *
 * <h2>Why the count is prime</h2>
 *
 * <p>Codepoints are not random. Han characters run in dense contiguous blocks
 * and radical-ordered ranges land on regular strides, so a power-of-two modulus
 * keeps the low bits and inherits that structure. A prime one does not: the
 * split stays even for reasons that do not depend on how Unicode happens to be
 * laid out.</p>
 *
 * <p>Measured over the 8,100 standard characters — 8,764 (character, reading)
 * pairs — the slices run 68 to 102 pairs, a spread of 1.5x.</p>
 */
public final class ZiPartition {

    /**
     * How many slices the corpus is cut into.
     *
     * <p>Changing it re-files every character. Anything that has recorded a
     * partition — a review queue, a cached module — is stale the moment it
     * moves, so it moves deliberately or not at all.</p>
     */
    public static final int COUNT = 101;

    private ZiPartition() {}

    /**
     * The partition a codepoint belongs to, always in {@code 0..COUNT-1}.
     *
     * <p>{@code floorMod} rather than {@code %}: Java's remainder keeps the
     * sign of its left operand, so a negative codepoint would yield a negative
     * index and read as a partition that does not exist. Codepoints are never
     * negative today, which is exactly the kind of assumption that is true
     * until someone passes a parsed {@code -1}.</p>
     */
    public static int of(int codePoint) {
        return Math.floorMod(codePoint, COUNT);
    }

    /** The partition a character belongs to. */
    public static int of(ZiCharUTF8 zi) {
        return of(zi.codePoint());
    }

    /** Whether an index names a partition at all — for parsing a request. */
    public static boolean exists(int partition) {
        return partition >= 0 && partition < COUNT;
    }
}
