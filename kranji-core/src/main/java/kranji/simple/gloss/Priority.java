package kranji.simple.gloss;

/**
 * How much a meaning matters among the others of its reading.
 *
 * <h2>Why not a number</h2>
 *
 * <p>A rank of 0, 1, 2 says a meaning is third without saying whether being
 * third matters. It also forces a decision nobody has: given four senses of
 * equal standing, an author has to invent an order and the file then asserts
 * one, which a reader will believe.</p>
 *
 * <p>Three bands say the thing that is actually known — this is the one a
 * reader meets, these are ordinary, these are corners — and leave the rest to
 * the order the senses were written in. Equal priority means equal standing,
 * and ties fall back to the natural sequence rather than to a fiction.</p>
 */
public enum Priority {

    /** What a reader meets first, and what a single-line view should show. */
    PRIMARY("P"),

    /** Ordinary and worth knowing, but not the one to lead with. */
    SECONDARY("S"),

    /** A corner: real, recorded for completeness, rarely the answer. */
    AUXILIARY("A");

    private final String code;

    Priority(String code) { this.code = code; }

    /** The single letter a file carries. */
    public String code() { return code; }

    /**
     * The band a letter names.
     *
     * @throws IllegalArgumentException on anything else — a file saying
     *     {@code X} is a file whose author meant something, and guessing which
     *     band would be worse than refusing.
     */
    public static Priority ofCode(String code) {
        String c = code == null ? "" : code.strip().toUpperCase();
        for (Priority p : values()) if (p.code.equals(c)) return p;
        throw new IllegalArgumentException(
                "'" + code + "' is not a priority - expected P, S or A "
              + "(primary, secondary, auxiliary)");
    }
}
