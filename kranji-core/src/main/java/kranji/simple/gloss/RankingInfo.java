package kranji.simple.gloss;

import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.Objects;
import java.util.Optional;

/**
 * Where something sits among its siblings, and why.
 *
 * <h2>Two parts, doing different work</h2>
 *
 * <p>{@link #priority} is <b>authored</b>: a judgement that this meaning is the
 * one a reader meets, or an ordinary one, or a corner. {@link #order} is
 * <b>stamped</b>: the sequence the item was written in, kept only to break ties
 * within a band.</p>
 *
 * <p>Keeping both is what lets equal things stay equal. Ranking by number alone
 * forced an author to say a meaning was third when all they knew was that it
 * was not first; ranking by band alone would leave four equal senses in
 * whatever order a map happened to yield, which is the {@code Map.copyOf}
 * mistake this codebase has already made once. The band carries the judgement;
 * the stamp carries the sequence.</p>
 *
 * <p>{@link #because} is for a placement that would otherwise look like an
 * oversight — 地's particle sense outranking "earth" reads as a mistake until
 * someone says why.</p>
 */
public record RankingInfo(Priority priority, int order, Optional<String> because)
        implements ValueObject {

    public RankingInfo {
        Objects.requireNonNull(priority, "priority");
        if (order < 0) {
            throw new IllegalArgumentException("a meaning cannot come before the first");
        }
        because = because == null ? Optional.empty() : because.filter(s -> !s.isBlank());
    }

    /**
     * A purely positional ranking, for items whose sequence <i>is</i> their
     * standing — a phrase's senses, a sense's examples.
     *
     * <p>The band follows the position rather than being invented: first is
     * primary, the rest are secondary. That keeps {@link #isPrimary()} truthful
     * for positional users without asking them to state a judgement they are
     * not making.</p>
     */
    public static RankingInfo at(int order) {
        return new RankingInfo(order == 0 ? Priority.PRIMARY : Priority.SECONDARY,
                order, Optional.empty());
    }

    /** An authored band with its tie-breaking stamp. */
    public static RankingInfo of(Priority priority, int order) {
        return new RankingInfo(priority, order, Optional.empty());
    }

    /** The band a single-line view should show. */
    public boolean isPrimary() { return priority == Priority.PRIMARY; }

    /** First among its siblings — position, not standing. */
    public boolean isFirst() { return order == 0; }

    public RankingInfo explained(String because) {
        return new RankingInfo(priority, order,
                Optional.ofNullable(Objects.requireNonNull(because)));
    }
}
