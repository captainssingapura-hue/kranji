package kranji.simple.gloss;

import hue.captains.singapura.tao.ontology.ValueObject;
import kranji.pinyin.PinyinSyllable;
import kranji.zi.ZiCharUTF8;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Everything a character means when read one particular way.
 *
 * <p>The unit the rest of the system joins on: {@link #key()} is
 * {@code codePoint:reading}, byte-identical to the known set's key and the
 * article census's, so a pane holding one of those can ask this without taking
 * the pair apart.</p>
 *
 * <p>A reading carries several meanings because most do. 行 read xíng is both
 * <i>to go</i> and <i>all right</i>; collapsing those into one string is the
 * same mistake one level down as collapsing xíng and háng was above.</p>
 *
 * <p>{@code Map<Meaning, Sense>} rather than a list: a meaning is a pure value,
 * so the same gloss written twice under one reading cannot exist, and the order
 * lives in the {@link Sense} rather than in a position.</p>
 */
public record SoundGloss(ZiCharUTF8 zi, PinyinSyllable reading, Map<Meaning, Sense> senses)
        implements ValueObject {

    public SoundGloss {
        Objects.requireNonNull(zi, "zi");
        Objects.requireNonNull(reading, "reading");
        senses = Map.copyOf(Objects.requireNonNull(senses, "senses"));

        if (senses.isEmpty()) {
            throw new IllegalArgumentException(
                    zi + " " + reading.toDiacritic() + " has no meanings - omit the reading "
                  + "rather than record that it means nothing");
        }
        // The orders must be 0..n-1 with no gap or repeat. They are stamped
        // from the sequence the senses were written in rather than authored, so
        // this is no longer something a file can get wrong - it guards the
        // direct constructor, which a codec or a generator could still hand a
        // gap, and a gap would make the tie-break between two senses of equal
        // priority silently arbitrary.
        var orders = senses.values().stream().map(s -> s.ranking().order()).sorted().toList();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i) != i) {
                throw new IllegalArgumentException(
                        zi + " " + reading.toDiacritic() + " has meanings stamped "
                      + orders + " - expected 0.." + (orders.size() - 1));
            }
        }
    }

    /** The key the known set, the census and this all share. */
    public String key() {
        return zi.value().codePointAt(0) + ":" + reading.numbered();
    }

    /**
     * The meanings by priority, most central first.
     *
     * <p>Priority is the judgement; the stamp only breaks ties. Two senses an
     * author called equally primary come back in the sequence they were
     * written, which is the honest answer — the alternative is to invent a
     * ranking between them and have a reader believe it.</p>
     */
    public List<Meaning> orderedMeanings() {
        return senses.entrySet().stream()
                .sorted(Comparator.<Map.Entry<Meaning, Sense>>comparingInt(
                                e -> e.getValue().ranking().priority().ordinal())
                        .thenComparingInt(e -> e.getValue().ranking().order()))
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * The meaning to show when only one fits.
     *
     * <p>Guaranteed to exist, so callers never have to decide what to do when a
     * reading cannot answer the question.</p>
     */
    public Meaning primary() { return orderedMeanings().get(0); }

    /** Where a meaning sits and what shows it. */
    public Sense senseOf(Meaning meaning) { return senses.get(meaning); }
}
