package kranji.simple.gloss;

import hue.captains.singapura.tao.ontology.ValueObject;
import kranji.pinyin.PinyinSyllable;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * One thing a phrase means, and how it is read when it means that.
 *
 * <p>The two travel together because they covary. 东西 read dōngxī is <i>east
 * and west</i>; read dōngxi it is <i>a thing</i>. Putting the readings on the
 * phrase rather than on the sense would have forced one of those to be
 * wrong.</p>
 *
 * <p>The English is a {@link Meaning}, the same type a character's sense uses —
 * not a bare String with its own length check. An earlier version restated the
 * rule and the constant here, which is two places for one decision and the
 * usual way they drift apart.</p>
 *
 * <p>{@link #sounds()} carries an entry <b>only where the reading is not the
 * corpus principal</b>, so the entries that remain mark exactly the places a
 * reader would guess wrong.</p>
 */
public record ExampleSense(RankingInfo ranking, Meaning english,
                           Map<Integer, PinyinSyllable> sounds) implements ValueObject {

    public ExampleSense {
        Objects.requireNonNull(ranking, "ranking");
        Objects.requireNonNull(english, "english");
        sounds = Map.copyOf(Objects.requireNonNull(sounds, "sounds"));
    }

    /** The reading at a position, when it was worth stating. */
    public Optional<PinyinSyllable> soundAt(int at) {
        return Optional.ofNullable(sounds.get(at));
    }

    public boolean isPrimary() { return ranking.isPrimary(); }
}
