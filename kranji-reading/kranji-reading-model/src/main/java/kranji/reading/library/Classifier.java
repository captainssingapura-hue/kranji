package kranji.reading.library;

/**
 * What tells one telling of a work from another, under one {@link ArticleUmbrella}.
 *
 * <h2>Per type, not one flat list</h2>
 *
 * <p>A 科普 piece comes in <em>depths</em> — the same ants, told with more or
 * less. A 成语 story comes in <em>provenances</em> — the classical text it is
 * short for, and the modern retellings of it. Those are not points on one
 * scale: "the original" is not level zero of anything, and a level-two
 * retelling of a poem is not a thing. So the classifier is a family of small
 * sealed types, and an umbrella is typed on one family — an
 * {@code ArticleUmbrella<Provenance>} cannot be handed a {@code Level}, and the
 * compiler says so.</p>
 *
 * <p>The families are sealed under one root so that a consumer that only
 * wants to draw them — the library tree — can switch over every case
 * exhaustively without knowing which family it has. Adding a family adds a
 * case, and the compiler finds every switch that has not heard.</p>
 *
 * <h2>Rank</h2>
 *
 * <p>The order a reader meets them in. Lower first. An umbrella sorts its
 * editions by this, so a collection can declare them in whatever order reads
 * best in Java and the reader still sees the plain telling before the dense
 * one, and the retelling before the source.</p>
 */
public sealed interface Classifier {

    /** Where this telling sits among its siblings; lower is met first. */
    int rank();

    /**
     * What a reader calls this telling — 原文, 白话, 第二级.
     *
     * <p>On the classifier rather than in whichever pane draws it, so that the
     * tree today and an edition switch in the reader tomorrow cannot call the
     * same telling two things.</p>
     */
    String label();

    /** 一 to 九 for the levels a library will actually have; digits past that. */
    private static String numeral(int n) {
        return n >= 1 && n <= 9 ? String.valueOf("一二三四五六七八九".charAt(n - 1)) : String.valueOf(n);
    }

    // ── Depth: the same thing, told with more or less ──────────────────

    /**
     * Gradations of one subject — 科普, and anything else whose tellings differ
     * in how much they say rather than in what they are.
     */
    sealed interface Depth extends Classifier permits Level {}

    /** Level {@code n}, from 1 (the plainest) upward. */
    record Level(int n) implements Depth {
        public Level {
            if (n < 1) throw new IllegalArgumentException("a level starts at 1, not " + n);
        }
        @Override public int rank() { return n; }
        @Override public String label() { return "第" + numeral(n) + "级"; }
    }

    // ── Provenance: a source text, and what was made from it ───────────

    /**
     * A work with a source — a fable from 韩非子, an idiom from 吕氏春秋, a
     * poem — and the retellings that make it readable before the source is.
     */
    sealed interface Provenance extends Classifier permits Original, Retold {}

    /**
     * The text as it was written. Ranked last: a reader arrives at 文言 by
     * way of the retelling, not the other way round, and the tree should say
     * so by the order it lists them in.
     */
    record Original() implements Provenance {
        @Override public int rank() { return Integer.MAX_VALUE; }
        @Override public String label() { return "原文"; }
    }

    /**
     * A retelling, at level {@code n} from 1 (the plainest) upward.
     *
     * <p>Labelled 白话 alone at level 1, because most works will only ever have
     * the one retelling and "白话一" would promise a second that does not
     * exist. The numeral appears from level 2, where there is something to
     * count.</p>
     */
    record Retold(int n) implements Provenance {
        public Retold {
            if (n < 1) throw new IllegalArgumentException("a retelling's level starts at 1, not " + n);
        }
        @Override public int rank() { return n; }
        @Override public String label() { return n == 1 ? "白话" : "白话" + numeral(n); }
    }
}
