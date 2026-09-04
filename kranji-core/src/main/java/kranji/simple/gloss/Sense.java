package kranji.simple.gloss;

import hue.captains.singapura.tao.ontology.ValueObject;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Where a meaning sits, and what shows it.
 *
 * <p>The value half of {@code Map<Meaning, Sense>}: the meaning is the key and
 * says what it means, this says how central it is and which phrases demonstrate
 * it. The same shape one level down - {@code Map<EgKey, RankingInfo>} - so an
 * example is a reference into the registry with its order stamped beside it.</p>
 *
 * <p>Because the order is in the value at both levels, the maps' own iteration
 * order does not matter. That is what makes {@link Map#copyOf} safe despite its
 * making no ordering promise: anything needing the author's order sorts by the
 * stamp instead of trusting the container.</p>
 */
public record Sense(RankingInfo ranking, Map<EgRef, RankingInfo> examples)
        implements ValueObject {

    public Sense {
        Objects.requireNonNull(ranking, "ranking");
        examples = Map.copyOf(Objects.requireNonNull(examples, "examples"));
    }

    /** The phrases showing this sense, in the order they were written. */
    public List<EgRef> orderedExamples() {
        return examples.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> e.getValue().order()))
                .map(Map.Entry::getKey)
                .toList();
    }

    /** The phrase to show when only one fits. */
    public EgRef bestExample() { return orderedExamples().get(0); }

    /** The first sense of its reading - the one shown when only one fits. */
    public boolean isPrimary() { return ranking.isPrimary(); }
}
