package kranji.simple.gloss;

import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A phrase and everything it can mean.
 *
 * <p>The registry's unit: {@link EgKey} identifies, this carries. Because the
 * key is the phrase alone, a phrase is defined <b>once</b> and referenced from
 * every character it teaches — 银行 explains 银 and 行 equally, and is written
 * once. Most examples are two-character words, so that roughly halves the
 * writing and yields the reverse index — <i>which words teach this
 * character</i> — as a by-product.</p>
 *
 * <h2>A phrase can mean more than one thing</h2>
 *
 * <p>东西 is <i>east and west</i> and also <i>a thing</i>, and the two are not
 * even read alike. An entry therefore carries a list of senses in order, sense
 * 0 being the commonest — the same stamping convention as everywhere else in
 * this tier — and a gloss references the sense it means rather than the phrase.
 * A reference that named only the phrase would silently assert whichever sense
 * happened to be written first.</p>
 */
public record ExampleEntry(EgKey key, List<ExampleSense> senses) implements ValueObject {

    public ExampleEntry {
        Objects.requireNonNull(key, "key");
        senses = List.copyOf(Objects.requireNonNull(senses, "senses"));

        if (senses.isEmpty()) {
            throw new IllegalArgumentException(
                    key + " has no meanings - omit the phrase rather than record an empty one");
        }
        for (int i = 0; i < senses.size(); i++) {
            if (senses.get(i).ranking().order() != i) {
                throw new IllegalArgumentException(
                        key + " has a sense stamped at " + senses.get(i).ranking().order()
                      + " sitting at position " + i);
            }
            for (var at : senses.get(i).sounds().keySet()) {
                if (at < 0 || at >= key.length()) {
                    throw new IllegalArgumentException(
                            key + " states a reading for position " + at
                          + ", outside a phrase of " + key.length());
                }
            }
        }
        var seen = new ArrayList<Meaning>();
        for (ExampleSense sense : senses) {
            if (seen.contains(sense.english())) {
                throw new IllegalArgumentException(
                        key + " gives the meaning \"" + sense.english() + "\" twice");
            }
            seen.add(sense.english());
        }
    }

    /** A phrase with one meaning, read the way the corpus expects. */
    public static ExampleEntry of(String phrase, String english) {
        return new ExampleEntry(EgKey.of(phrase),
                List.of(new ExampleSense(RankingInfo.at(0), Meaning.of(english),
                        java.util.Map.of())));
    }

    /**
     * Notes that a position in the <em>most recent</em> sense is not read as
     * the corpus principal.
     *
     * <p>Position, not character, because a phrase can repeat a character and
     * read it two ways.</p>
     */
    public ExampleEntry sound(int at, String reading) {
        var last = senses.get(senses.size() - 1);
        var next = new java.util.LinkedHashMap<>(last.sounds());
        next.put(at, kranji.pinyin.PinyinSyllable.parseCanonical(reading));
        var replaced = new ArrayList<>(senses);
        replaced.set(senses.size() - 1,
                new ExampleSense(last.ranking(), last.english(), next));
        return new ExampleEntry(key, replaced);
    }

    /** A further meaning of the same phrase, less common than the ones before it. */
    public ExampleEntry also(String english) {
        var next = new ArrayList<>(senses);
        next.add(new ExampleSense(RankingInfo.at(senses.size()), Meaning.of(english),
                java.util.Map.of()));
        return new ExampleEntry(key, next);
    }

    /** The sense a reference names, if the phrase has one that far. */
    public Optional<ExampleSense> sense(int index) {
        return index < senses.size() ? Optional.of(senses.get(index)) : Optional.empty();
    }

    /** What the phrase means when nothing says otherwise. */
    public ExampleSense primary() { return senses.get(0); }
}
