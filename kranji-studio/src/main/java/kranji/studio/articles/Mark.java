package kranji.studio.articles;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A punctuation mark, and the rules it has to follow.
 *
 * <h2>Why a type rather than three strings</h2>
 *
 * <p>The rules used to be spread over string tables — {@code Cells.CLOSING},
 * {@code Cells.OPENING}, a {@code STANDING} constant — and read with
 * {@code contains()} wherever a decision was needed. That works until the
 * questions multiply, and they did: may this begin a line, may it end one, may
 * it share a square, and with what. Four questions asked of three overlapping
 * strings in five places is how {@code ……} nearly ended up packed into one
 * square and how {@code （} ended up stranded at a margin.</p>
 *
 * <p>Each mark now names its {@link Role}, and the role carries the rules. A
 * new mark is one line; a changed rule is one field.</p>
 *
 * <h2>This is also the whitelist</h2>
 *
 * <p>Everything not Chinese and not a mark must be wrapped in {@code ‹…›} —
 * see the subset specification. {@link #of} is what the parser asks, so the
 * list of marks and the list of things needing no delimiters are the same list
 * by construction rather than by agreement.</p>
 */
public enum Mark {

    // ── 句读: they end a sentence or a clause ──────────────────────────

    FULL_STOP    ("。", Role.STOP),
    COMMA        ("，", Role.STOP),
    ENUM_COMMA   ("、", Role.STOP),
    SEMICOLON    ("；", Role.STOP),
    COLON        ("：", Role.STOP),
    QUESTION     ("？", Role.STOP),
    EXCLAMATION  ("！", Role.STOP),

    // ── Closing brackets and quotes ────────────────────────────────────

    CLOSE_ROUND  ("）", Role.CLOSE),
    CLOSE_SQUARE ("】", Role.CLOSE),
    CLOSE_ANGLE  ("》", Role.CLOSE),
    CLOSE_CORNER ("」", Role.CLOSE),
    CLOSE_HOLLOW ("』", Role.CLOSE),
    CLOSE_SINGLE ("’", Role.CLOSE),
    CLOSE_DOUBLE ("”", Role.CLOSE),

    // ── Opening brackets and quotes ────────────────────────────────────

    OPEN_ROUND   ("（", Role.OPEN),
    OPEN_SQUARE  ("【", Role.OPEN),
    OPEN_ANGLE   ("《", Role.OPEN),
    OPEN_CORNER  ("「", Role.OPEN),
    OPEN_HOLLOW  ("『", Role.OPEN),
    OPEN_SINGLE  ("‘", Role.OPEN),
    OPEN_DOUBLE  ("“", Role.OPEN),

    // ── Written double, a square each ──────────────────────────────────

    DASH         ("—", Role.DOUBLED),
    DASH_BAR     ("―", Role.DOUBLED),
    ELLIPSIS     ("…", Role.DOUBLED),

    // ── Joiners: they stand between two things ─────────────────────────

    MIDDLE_DOT   ("·", Role.JOINER),
    TILDE        ("～", Role.JOINER),
    SOLIDUS      ("／", Role.JOINER);

    /** Which end of the thing beside it a mark belongs to. */
    public enum Side {
        /** Belongs to what follows: an opening bracket or quote. */
        LEADING,
        /** Belongs to what precedes: 句读 and the closing brackets. */
        TRAILING,
        /** Belongs to neither, and therefore shares a square with neither. */
        ALONE
    }

    /**
     * What a mark does, and the rules that follow from it.
     *
     * @param side         which neighbour it belongs to, and so what it packs
     *                     with — marks pack only with their own side
     * @param mayBeginLine 禁则's first half. A mark that may not begin a line
     *                     hangs past the right edge instead of wrapping
     * @param mayEndLine   禁则's second half. A mark that may not end a line is
     *                     carried down onto the next row, where the thing it
     *                     opens is about to be written
     */
    public enum Role {

        /** 。，、；：？！ — never begin a line. */
        STOP(Side.TRAILING, false, true),

        /** ）】》」』’” — never begin a line. */
        CLOSE(Side.TRAILING, false, true),

        /** （【《「『‘“ — never end a line. */
        OPEN(Side.LEADING, true, false),

        /**
         * 破折号 and 省略号, written {@code ——} and {@code ……}.
         *
         * <p>{@code ALONE} because packing them would turn a dash into a
         * hyphen, and each half is a full square.</p>
         *
         * <p>{@code mayBeginLine = false} is the field to look at if this ever
         * seems wrong. The strict rule in GB/T 15834 is only that the pair must
         * not be <i>split</i> across two rows, and many houses do let 破折号
         * open a line. Barring it delivers the strict rule for nothing: the
         * second half hangs beside the first rather than opening the next row,
         * so the pair cannot come apart. Allowing it would mean moving both
         * halves down together, which is a rule this does not otherwise
         * need.</p>
         */
        DOUBLED(Side.ALONE, false, true),

        /**
         * 间隔号, 波浪号, the full-width solidus.
         *
         * <p>They join two things — 麦克·乔丹, 三～五 — so they should not open
         * a line. They may close one: the alternative is carrying them down,
         * and a joiner at the head of a row is the thing being avoided.</p>
         */
        JOINER(Side.ALONE, false, true);

        private final Side side;
        private final boolean mayBeginLine;
        private final boolean mayEndLine;

        Role(Side side, boolean mayBeginLine, boolean mayEndLine) {
            this.side = side;
            this.mayBeginLine = mayBeginLine;
            this.mayEndLine = mayEndLine;
        }

        public Side side()            { return side; }
        public boolean mayBeginLine() { return mayBeginLine; }
        public boolean mayEndLine()   { return mayEndLine; }
    }

    private static final Map<String, Mark> BY_TEXT = new HashMap<>();

    static {
        for (Mark m : values()) BY_TEXT.put(m.text, m);
    }

    private final String text;
    private final Role role;

    Mark(String text, Role role) {
        this.text = text;
        this.role = role;
    }

    public String text() { return text; }
    public Role role()   { return role; }

    public boolean mayBeginLine() { return role.mayBeginLine(); }
    public boolean mayEndLine()   { return role.mayEndLine(); }

    /**
     * Whether this mark will share a square with {@code other} written after
     * it.
     *
     * <p>Only with its own side. {@code ”。} is a closing quote and a full stop,
     * both belonging to what came before, and they share a box the way a hand
     * writes them. A {@code “} opening the next quotation does not, however
     * adjacent it is, because it belongs to what comes after.</p>
     */
    public boolean packsWith(Mark other) {
        return role.side() != Side.ALONE && role.side() == other.role.side();
    }

    /** The mark this character is, if it is one. This is the whitelist. */
    public static Optional<Mark> of(String ch) {
        return Optional.ofNullable(BY_TEXT.get(ch));
    }

    /** Whether this character is a mark, and so needs no {@code ‹…›}. */
    public static boolean is(String ch) {
        return BY_TEXT.containsKey(ch);
    }
}
