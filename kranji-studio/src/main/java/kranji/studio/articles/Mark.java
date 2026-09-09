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
 * <h2>Two of those four questions are gone</h2>
 *
 * <p>Marks do not share squares any more — one character, one square, marks
 * included — so <i>may it share</i> and <i>with what</i> have no answers to
 * give. What is left is 禁则, which is the pair this was really for.</p>
 *
 * <p>This also used to be the <b>whitelist</b>: everything not Chinese and not
 * a mark had to be wrapped in {@code ‹…›}, and {@link #of} was what the parser
 * asked so that the two lists were the same list by construction. Nothing needs
 * wrapping now, and this is a punctuation table and nothing more.</p>
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

    /**
     * What a mark does, and the two rules that follow from it.
     *
     * <p>There used to be a third thing here — a {@code Side}, saying which
     * neighbour a mark belonged to and therefore which marks shared a square.
     * Nothing shares a square any more, so what is left is 禁则 and only
     * 禁则.</p>
     *
     * @param mayBeginLine 禁则's first half. A mark that may not begin a line
     *                     brings the character before it down onto the next row
     *                     rather than opening one
     * @param mayEndLine   禁则's second half. A mark that may not end a line is
     *                     carried down onto the next row, where the thing it
     *                     opens is about to be written
     */
    public enum Role {

        /** 。，、；：？！ — never begin a line. */
        STOP(false, true),

        /** ）】》」』’” — never begin a line. */
        CLOSE(false, true),

        /** （【《「『‘“ — never end a line. */
        OPEN(true, false),

        /**
         * 破折号 and 省略号, written {@code ——} and {@code ……}.
         *
         * <p>{@code mayBeginLine = false} is the field to look at if this ever
         * seems wrong. The strict rule in GB/T 15834 is only that the pair must
         * not be <i>split</i> across two rows, and many houses do let 破折号
         * open a line.</p>
         *
         * <p>Barring it used to deliver the strict rule for nothing, back when
         * the second half would hang beside the first. It no longer does: two
         * squares can now be separated by a row boundary like any other two, and
         * what keeps the pair together is that the second half drags the first
         * down with it under the closing rule. Same outcome, stated rather than
         * fallen out.</p>
         */
        DOUBLED(false, true),

        /**
         * 间隔号, 波浪号, the full-width solidus.
         *
         * <p>They join two things — 麦克·乔丹, 三～五 — so they should not open
         * a line. They may close one: the alternative is carrying them down,
         * and a joiner at the head of a row is the thing being avoided.</p>
         */
        JOINER(false, true);

        private final boolean mayBeginLine;
        private final boolean mayEndLine;

        Role(boolean mayBeginLine, boolean mayEndLine) {
            this.mayBeginLine = mayBeginLine;
            this.mayEndLine = mayEndLine;
        }

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
     * The mark this character is, if it is one.
     *
     * <p>What the arrangement asks. A character that is not a mark and not
     * Chinese is simply a character, and gets a square like everything
     * else — there is nothing left for this to gate.</p>
     */
    public static Optional<Mark> of(String ch) {
        return Optional.ofNullable(BY_TEXT.get(ch));
    }
}
